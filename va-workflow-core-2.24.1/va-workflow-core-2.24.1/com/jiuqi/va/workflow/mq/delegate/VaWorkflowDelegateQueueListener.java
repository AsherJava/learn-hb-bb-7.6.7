/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.DateTimeUtil
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.delegate.DelegateDO
 *  com.jiuqi.va.domain.delegate.DelegateDTO
 *  com.jiuqi.va.domain.delegate.DelegateService
 *  com.jiuqi.va.domain.delegate.DelegateType
 *  com.jiuqi.va.domain.delegate.DelegateVO
 *  com.jiuqi.va.domain.schedule.ScheduleTaskFeedback
 *  com.jiuqi.va.domain.todo.TaskDTO
 *  com.jiuqi.va.feign.client.TodoClient
 *  com.jiuqi.va.join.api.common.JoinTemplate
 *  com.jiuqi.va.join.api.domain.JoinListener
 *  com.jiuqi.va.join.api.domain.ReplyStatus
 *  com.jiuqi.va.join.api.domain.ReplyTo
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  org.apache.shiro.util.ThreadContext
 */
package com.jiuqi.va.workflow.mq.delegate;

import com.jiuqi.va.domain.common.DateTimeUtil;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.delegate.DelegateDO;
import com.jiuqi.va.domain.delegate.DelegateDTO;
import com.jiuqi.va.domain.delegate.DelegateService;
import com.jiuqi.va.domain.delegate.DelegateType;
import com.jiuqi.va.domain.delegate.DelegateVO;
import com.jiuqi.va.domain.schedule.ScheduleTaskFeedback;
import com.jiuqi.va.domain.todo.TaskDTO;
import com.jiuqi.va.feign.client.TodoClient;
import com.jiuqi.va.join.api.common.JoinTemplate;
import com.jiuqi.va.join.api.domain.JoinListener;
import com.jiuqi.va.join.api.domain.ReplyStatus;
import com.jiuqi.va.join.api.domain.ReplyTo;
import com.jiuqi.va.mapper.domain.TenantDO;
import com.jiuqi.va.workflow.service.impl.DelegateHandler;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.apache.shiro.util.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class VaWorkflowDelegateQueueListener
implements JoinListener {
    private static final Logger log = LoggerFactory.getLogger(VaWorkflowDelegateQueueListener.class);
    @Autowired
    private DelegateHandler delegateHandler;
    @Autowired
    private TodoClient todoClient;
    @Autowired
    private DelegateService delegateService;
    @Autowired
    private JoinTemplate joinTemplate;

    public String getJoinName() {
        return "VA_WORKFLOW_DELEGATE_SCHEDULE";
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public ReplyTo onMessage(String message) {
        TenantDO tenantDO = (TenantDO)JSONUtil.parseObject((String)message, TenantDO.class);
        String tenantName = tenantDO.getTenantName();
        Map extInfo = tenantDO.getExtInfo();
        String beginDate = (String)extInfo.get("beginDate");
        String endDate = (String)extInfo.get("endDate");
        String logid = (String)extInfo.get("_logid");
        if (StringUtils.hasText(beginDate)) {
            DateTimeUtil.strToDate((String)beginDate, (String)"yyyy-MM-dd");
        }
        if (StringUtils.hasText(endDate)) {
            DateTimeUtil.strToDate((String)endDate, (String)"yyyy-MM-dd");
        }
        ThreadContext.put((Object)"SECURITY_TENANT_KEY", (Object)tenantName);
        ThreadContext.put((Object)"NONE_AUTH_KEY", (Object)"true");
        try {
            HashMap resmap = new HashMap();
            DelegateDTO delegateDTO = new DelegateDTO();
            delegateDTO.setHisdeleflag(BigDecimal.ONE);
            delegateDTO.setQuerytype(DelegateType.INEFFECT.getValue());
            delegateDTO.setBackendrequest(true);
            List delegates = this.delegateService.query(delegateDTO);
            String traceId = tenantDO.getTraceId();
            if (!delegates.isEmpty()) {
                HashMap<String, Object> sendmap = new HashMap<String, Object>();
                HashMap<String, Object> sendbizmap = new HashMap<String, Object>();
                for (DelegateVO delegateVO : delegates) {
                    TaskDTO taskDTO = new TaskDTO();
                    taskDTO.setParticipant(delegateVO.getDelegateuser());
                    taskDTO.setBackendRequest(true);
                    taskDTO.setFilterDelegate(true);
                    taskDTO.setTraceId(traceId);
                    PageVO listUnfinished = this.todoClient.listUnfinished(taskDTO);
                    if (listUnfinished.getRs().getCode() != 0 || listUnfinished.getRows().isEmpty()) continue;
                    ArrayList<Map<String, Object>> reslist = new ArrayList<Map<String, Object>>();
                    try {
                        delegateDTO.setDeleconditions(JSONUtil.parseMapArray((String)delegateVO.getDelecondition()));
                        List list = this.delegateService.filterByCondition(delegateDTO, listUnfinished.getRows());
                        for (Map todo : list) {
                            TaskDTO taskDTOTemp = new TaskDTO();
                            taskDTOTemp.setBackendRequest(true);
                            taskDTOTemp.setDelegateId(delegateVO.getId());
                            taskDTOTemp.setTaskId((String)todo.get("TASKID"));
                            taskDTOTemp.setTraceId(traceId);
                            PageVO listTemp = this.todoClient.listUnfinished(taskDTOTemp);
                            if (!listTemp.getRows().isEmpty()) continue;
                            reslist.add(todo);
                        }
                    }
                    catch (Exception e) {
                        log.error(e.getMessage(), e);
                        sendmap.put(delegateVO.getDelegatetitle(), "\u89e3\u6790\u5931\u8d25");
                        continue;
                    }
                    this.delegateHandler.send(reslist, (DelegateDO)delegateVO, sendbizmap);
                    if (sendbizmap.isEmpty()) continue;
                    sendmap.put(delegateVO.getDelegatetitle(), sendbizmap);
                }
                if (!sendmap.isEmpty()) {
                    resmap.put("\u53d1\u9001\u59d4\u6258\u5f85\u529e", sendmap);
                }
            }
            DelegateDTO delegateDTOTemp = new DelegateDTO();
            delegateDTOTemp.setQuerytype(DelegateType.EXPIRED.getValue());
            delegateDTOTemp.setBackendrequest(true);
            List delegatesTemp = this.delegateService.query(delegateDTOTemp);
            if (!delegatesTemp.isEmpty()) {
                HashMap<String, Object> retakemap = new HashMap<String, Object>();
                HashMap<String, Object> retakebizmap = new HashMap<String, Object>();
                for (DelegateVO delegateVO : delegatesTemp) {
                    TaskDTO taskDTO = new TaskDTO();
                    taskDTO.setDelegateId(delegateVO.getId());
                    taskDTO.setBackendRequest(true);
                    taskDTO.setTraceId(traceId);
                    PageVO listUnfinished = this.todoClient.listUnfinished(taskDTO);
                    if (listUnfinished.getRs().getCode() != 0 || listUnfinished.getRows().isEmpty()) continue;
                    delegateDTOTemp.setDeleconditions(JSONUtil.parseMapArray((String)delegateVO.getDelecondition()));
                    List list = null;
                    try {
                        list = this.delegateService.filterByCondition(delegateDTOTemp, listUnfinished.getRows());
                    }
                    catch (Exception e) {
                        retakemap.put(delegateVO.getDelegatetitle(), "\u89e3\u6790\u5931\u8d25");
                        continue;
                    }
                    this.delegateHandler.retake(list, (DelegateDO)delegateVO, retakebizmap);
                    if (retakebizmap.isEmpty()) continue;
                    retakemap.put(delegateVO.getDelegatetitle(), retakebizmap);
                }
                if (!retakemap.isEmpty()) {
                    resmap.put("\u6536\u56de\u59d4\u6258\u5f85\u529e", retakemap);
                }
            }
            ScheduleTaskFeedback feedback = new ScheduleTaskFeedback();
            feedback.setFdstatus(Integer.valueOf(1));
            feedback.setLogid(UUID.fromString(logid));
            feedback.setTenantName(tenantName);
            feedback.setFdmessage("9797-" + ((Object)resmap).toString());
            this.joinTemplate.send("MQ_SCHEDULE_TASK_FEEDBACK", JSONUtil.toJSONString((Object)feedback));
        }
        catch (Exception e) {
            log.error("\u81ea\u52a8\u53d1\u9001\u548c\u6536\u56de\u59d4\u6258\u5f85\u529e\u8ba1\u5212\u4efb\u52a1\u6267\u884c\u5f02\u5e38", e);
            ScheduleTaskFeedback feedback = new ScheduleTaskFeedback();
            feedback.setFdmessage("9797" + e.getMessage());
            feedback.setFdstatus(Integer.valueOf(2));
            feedback.setLogid(UUID.fromString(logid));
            feedback.setTenantName(tenantName);
            this.joinTemplate.send("MQ_SCHEDULE_TASK_FEEDBACK", JSONUtil.toJSONString((Object)feedback));
        }
        finally {
            ThreadContext.remove((Object)"SECURITY_TENANT_KEY");
            ThreadContext.remove((Object)"NONE_AUTH_KEY");
        }
        return new ReplyTo(ReplyStatus.SUCESS, JSONUtil.toJSONString((Object)R.ok()));
    }
}

