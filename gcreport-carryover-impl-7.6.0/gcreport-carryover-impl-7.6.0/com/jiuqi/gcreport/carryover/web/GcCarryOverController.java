/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.carryover.api.GcCarryOverClient
 *  com.jiuqi.gcreport.carryover.vo.CarryOverTaskProcessVO
 *  com.jiuqi.gcreport.carryover.vo.QueryParamsVO
 *  com.jiuqi.gcreport.common.task.vo.OptionVO
 *  com.jiuqi.gcreport.common.task.vo.Scheme
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.consolidatedsystem.api.ConsolidatedSystemClient
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO
 *  com.jiuqi.gcreport.offsetitem.vo.DesignFieldDefineVO
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 *  com.jiuqi.gcreport.temp.dto.Message
 *  com.jiuqi.gcreport.temp.dto.MessageTypeEnum
 *  com.jiuqi.gcreport.temp.dto.TaskLog
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.service.IFormSchemeService
 *  com.jiuqi.util.StringUtils
 *  javax.servlet.http.HttpServletRequest
 *  org.springframework.http.MediaType
 *  org.springframework.http.ResponseEntity
 *  org.springframework.http.ResponseEntity$BodyBuilder
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.carryover.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.gcreport.carryover.api.GcCarryOverClient;
import com.jiuqi.gcreport.carryover.entity.CarryOverConfigEO;
import com.jiuqi.gcreport.carryover.entity.CarryOverLogEO;
import com.jiuqi.gcreport.carryover.service.GcCarryOverConfigService;
import com.jiuqi.gcreport.carryover.service.GcCarryOverLogService;
import com.jiuqi.gcreport.carryover.service.GcCarryOverProcessService;
import com.jiuqi.gcreport.carryover.service.GcCarryOverService;
import com.jiuqi.gcreport.carryover.vo.CarryOverTaskProcessVO;
import com.jiuqi.gcreport.carryover.vo.QueryParamsVO;
import com.jiuqi.gcreport.common.task.vo.OptionVO;
import com.jiuqi.gcreport.common.task.vo.Scheme;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.consolidatedsystem.api.ConsolidatedSystemClient;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO;
import com.jiuqi.gcreport.offsetitem.vo.DesignFieldDefineVO;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import com.jiuqi.gcreport.temp.dto.Message;
import com.jiuqi.gcreport.temp.dto.MessageTypeEnum;
import com.jiuqi.gcreport.temp.dto.TaskLog;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.service.IFormSchemeService;
import com.jiuqi.util.StringUtils;
import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GcCarryOverController
implements GcCarryOverClient {
    private static final Logger logger = LoggerFactory.getLogger(GcCarryOverController.class);
    @Autowired
    private GcCarryOverService gcCarryOverService;
    @Autowired
    private GcCarryOverConfigService gcCarryOverConfigService;
    @Autowired
    private GcCarryOverLogService gcCarryOverLogService;
    @Autowired
    private IFormSchemeService formSchemeService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private ConsolidatedSystemClient consolidatedSystemApi;
    @Autowired
    private ConsolidatedTaskService consolidatedTaskService;
    @Autowired
    private GcCarryOverProcessService carryOverProcessService;

    public BusinessResponseEntity<Object> doCarryOver(QueryParamsVO queryParamsVO) {
        this.gcCarryOverService.doCarryOver(queryParamsVO);
        return BusinessResponseEntity.ok();
    }

    public BusinessResponseEntity<TaskLog> getTaskLog(String taskLogId) {
        TaskLog taskLog = new TaskLog();
        CarryOverLogEO logEO = this.gcCarryOverLogService.getCarryOverLogById(taskLogId);
        String info = logEO.getInfo();
        ArrayList<Message> messages = null;
        try {
            messages = (ArrayList<Message>)JsonUtils.readValue((String)info, (TypeReference)new TypeReference<List<Message>>(){});
        }
        catch (Exception exception) {
            // empty catch block
        }
        if (messages == null) {
            messages = new ArrayList<Message>();
            String[] logLines = info.split("\n");
            int order = 0;
            for (String line : logLines) {
                Message message = new Message();
                if (line.startsWith("[\u4fe1\u606f]")) {
                    message.setMsgType(MessageTypeEnum.INFO);
                } else if (line.startsWith("[\u8b66\u544a]")) {
                    message.setMsgType(MessageTypeEnum.WARN);
                } else if (line.startsWith("[\u9519\u8bef]")) {
                    message.setMsgType(MessageTypeEnum.ERROR);
                }
                message.setOrder(Integer.valueOf(order));
                message.setKey(UUID.randomUUID().toString());
                message.setMessage(new StringBuffer(line));
                messages.add(message);
                ++order;
            }
        }
        taskLog.setMessages(messages);
        return BusinessResponseEntity.ok((Object)taskLog);
    }

    public BusinessResponseEntity<Pagination<Map<String, Object>>> listCarryOverLogTableData(QueryParamsVO queryParamsVO) {
        Pagination<Map<String, Object>> pagination = this.gcCarryOverService.listCarryOverLogInfo(queryParamsVO);
        return BusinessResponseEntity.ok(pagination);
    }

    public BusinessResponseEntity<Map<String, Object>> getTaskProcess(String taskLogId) {
        return BusinessResponseEntity.ok(this.gcCarryOverService.getTaskProcess(taskLogId));
    }

    public BusinessResponseEntity<CarryOverTaskProcessVO> queryTaskProcess(String taskId) {
        return BusinessResponseEntity.ok((Object)this.carryOverProcessService.queryTaskProcess(taskId));
    }

    @Transactional(rollbackFor={Exception.class})
    public ResponseEntity<Resource> downloadLog(String taskLogId, HttpServletRequest request) {
        CarryOverLogEO eo = this.gcCarryOverLogService.getCarryOverLogById(taskLogId);
        if (null == eo) {
            throw new BusinessRuntimeException("\u672a\u627e\u5230\u8be5\u6761\u65e5\u5fd7\u8bb0\u5f55\uff0c\u4e0b\u8f7d\u5931\u8d25\uff01");
        }
        String info = eo.getInfo();
        List messages = (List)JsonUtils.readValue((String)info, (TypeReference)new TypeReference<List<Message>>(){});
        StringBuilder sb = new StringBuilder();
        for (Message message : messages) {
            sb.append(message.getMessage()).append("\n");
        }
        CarryOverConfigEO carryOverConfigEO = this.gcCarryOverConfigService.getCarryOverConfigById(eo.getCarryOverSchemeId());
        String fileName = carryOverConfigEO.getTitle() + ".txt";
        String encode = "\u5e74\u7ed3\u65e5\u5fd7.txt";
        try {
            encode = URLEncoder.encode(fileName, "UTF-8");
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            // empty catch block
        }
        ByteArrayInputStream inputStream = new ByteArrayInputStream(sb.toString().getBytes());
        InputStreamResource resource = new InputStreamResource(inputStream);
        return ((ResponseEntity.BodyBuilder)ResponseEntity.ok().header("Content-Disposition", new String[]{"attachment;filename=\"" + encode + "\""})).contentType(MediaType.TEXT_PLAIN).body((Object)resource);
    }

    public String getAcctYearRange(Map<String, String> params) {
        TaskDefine taskDefine;
        String taskKey = params.get("taskKey");
        ObjectMapper mapper = new ObjectMapper();
        if (StringUtils.isNotEmpty((String)taskKey) && (taskDefine = this.runTimeViewController.queryTaskDefine(taskKey)) != null) {
            try {
                Integer fromAcctYear = null;
                Integer toAcctYear = null;
                ArrayList<OptionVO> acctYearList = new ArrayList<OptionVO>();
                String toFromPeriod = taskDefine.getFromPeriod();
                String toPeriod = taskDefine.getToPeriod();
                if (!StringUtils.isEmpty((String)toFromPeriod)) {
                    fromAcctYear = Integer.valueOf(toFromPeriod.substring(0, 4));
                }
                if (!com.jiuqi.common.base.util.StringUtils.isEmpty((String)toPeriod)) {
                    toAcctYear = Integer.valueOf(toPeriod.substring(0, 4));
                }
                Calendar date = Calendar.getInstance();
                int year = date.get(1);
                fromAcctYear = fromAcctYear == null ? year - 5 : fromAcctYear;
                toAcctYear = toAcctYear == null ? year + 5 : toAcctYear;
                for (int i = fromAcctYear.intValue(); i <= toAcctYear; ++i) {
                    acctYearList.add(new OptionVO((Object)i, i + ""));
                }
                return mapper.writeValueAsString(acctYearList.stream().map(optionVO -> {
                    HashMap<String, String> result = new HashMap<String, String>();
                    result.put("key", optionVO.getValue().toString());
                    result.put("title", optionVO.getLabel());
                    return result;
                }).collect(Collectors.toList()));
            }
            catch (JsonProcessingException e) {
                logger.error("\u83b7\u53d6\u65f6\u671f\u9009\u62e9\u8303\u56f4\u5931\u8d25:" + e.getMessage(), e);
                throw new BusinessRuntimeException("\u83b7\u53d6\u65f6\u671f\u9009\u62e9\u8303\u56f4\u5931\u8d25:" + e.getMessage());
            }
        }
        return null;
    }

    public String getConsSystem() {
        List consolidatedSystemVOS = (List)this.consolidatedSystemApi.getConsolidatedSystems(null).getData();
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(consolidatedSystemVOS.stream().map(vo -> {
                HashMap<String, String> result = new HashMap<String, String>();
                result.put("key", vo.getId());
                result.put("title", vo.getSystemName());
                return result;
            }).collect(Collectors.toList()));
        }
        catch (JsonProcessingException e) {
            logger.error("\u83b7\u53d6\u5408\u5e76\u4f53\u7cfb\u5931\u8d25:" + e.getMessage(), e);
            throw new BusinessRuntimeException("\u83b7\u53d6\u5408\u5e76\u4f53\u7cfb\u5931\u8d25:" + e.getMessage());
        }
    }

    public BusinessResponseEntity<Map<String, String>> getOrgVerAndType(String schemeId, String defaultAcctYear) {
        return BusinessResponseEntity.ok(this.gcCarryOverService.getOrgVerAndType(schemeId, defaultAcctYear));
    }

    public BusinessResponseEntity<Scheme> getSchemeByTaskKeyAndAcctYear(String taskKey, Integer acctYear) {
        return BusinessResponseEntity.ok((Object)this.gcCarryOverService.getSchemeByTaskKeyAndAcctYear(taskKey, acctYear));
    }

    public BusinessResponseEntity<List<DesignFieldDefineVO>> listCarryOverSumColumns() {
        return BusinessResponseEntity.ok(this.gcCarryOverService.listCarryOverSumColumns());
    }

    public BusinessResponseEntity<Boolean> checkAdjust(QueryParamsVO queryParamsVO) {
        boolean existAdjust = DimensionUtils.isExistAdjust((String)queryParamsVO.getTaskId());
        if (!existAdjust) {
            return BusinessResponseEntity.ok((Object)false);
        }
        List periodList = this.formSchemeService.queryAdjustPeriods(queryParamsVO.getSchemeId(), queryParamsVO.getPeriodStr());
        if (CollectionUtils.isEmpty((Collection)periodList) || periodList.size() <= 1) {
            return BusinessResponseEntity.ok((Object)false);
        }
        return BusinessResponseEntity.ok((Object)true);
    }

    public BusinessResponseEntity<String> getOrgTypeByTaskIdAndPeriod(QueryParamsVO queryParamsVO) {
        String corporateEntity = "";
        try {
            ConsolidatedTaskVO consolidatedTaskVO = this.consolidatedTaskService.getTaskByTaskKeyAndPeriodStr(queryParamsVO.getTaskId(), queryParamsVO.getPeriodStr());
            if (Objects.isNull(consolidatedTaskVO)) {
                return BusinessResponseEntity.ok((Object)corporateEntity);
            }
            corporateEntity = consolidatedTaskVO.getCorporateEntity();
        }
        catch (Exception e) {
            logger.error("\u83b7\u53d6\u5408\u5e76\u4f53\u7cfb\u914d\u7f6e\u5931\u8d25\uff0c\u539f\u56e0\uff1a{}", (Object)e.getMessage(), (Object)e);
        }
        if (StringUtils.isEmpty((String)corporateEntity)) {
            TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(queryParamsVO.getTaskId());
            if (Objects.isNull(taskDefine)) {
                return BusinessResponseEntity.ok((Object)corporateEntity);
            }
            corporateEntity = taskDefine.getDw();
            if (StringUtils.isEmpty((String)corporateEntity)) {
                return BusinessResponseEntity.ok((Object)corporateEntity);
            }
            corporateEntity = corporateEntity.replace("@ORG", "");
        }
        return BusinessResponseEntity.ok((Object)corporateEntity);
    }
}

