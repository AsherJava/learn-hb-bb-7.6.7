/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.va.attachment.domain.AttachmentBizDO
 *  com.jiuqi.va.attachment.domain.AttachmentBizDTO
 *  com.jiuqi.va.attachment.feign.client.VaAttachmentFeignClient
 *  com.jiuqi.va.biz.utils.R
 *  com.jiuqi.va.biz.utils.Utils
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.workflow.ProcessDO
 *  com.jiuqi.va.domain.workflow.ProcessDTO
 *  com.jiuqi.va.domain.workflow.ProcessNodeDO
 *  com.jiuqi.va.domain.workflow.ProcessNodeDTO
 *  com.jiuqi.va.feign.client.WorkflowServerClient
 */
package com.jiuqi.va.bill.action;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.va.attachment.domain.AttachmentBizDO;
import com.jiuqi.va.attachment.domain.AttachmentBizDTO;
import com.jiuqi.va.attachment.feign.client.VaAttachmentFeignClient;
import com.jiuqi.va.bill.impl.BillActionBase;
import com.jiuqi.va.bill.intf.BillModel;
import com.jiuqi.va.bill.utils.BillCoreI18nUtil;
import com.jiuqi.va.biz.utils.R;
import com.jiuqi.va.biz.utils.Utils;
import com.jiuqi.va.domain.workflow.ProcessDO;
import com.jiuqi.va.domain.workflow.ProcessDTO;
import com.jiuqi.va.domain.workflow.ProcessNodeDO;
import com.jiuqi.va.domain.workflow.ProcessNodeDTO;
import com.jiuqi.va.feign.client.WorkflowServerClient;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AttachmentDeleteAction
extends BillActionBase {
    private static final Logger logger = LoggerFactory.getLogger(AttachmentDeleteAction.class);
    @Autowired
    private VaAttachmentFeignClient vaAttachmentFeignClient;
    @Autowired
    private WorkflowServerClient workflowServerClient;

    public String getName() {
        return "bill-attachment-delete";
    }

    public String getTitle() {
        return "\u9644\u4ef6\u5220\u9664";
    }

    public boolean isInner() {
        return true;
    }

    @Override
    public Object executeReturn(BillModel model, Map<String, Object> params) {
        ArrayList<AttachmentBizDO> list;
        AttachmentBizDO attachmentBizDO;
        Map attachment = (Map)params.get("attachment");
        Object ignore = params.get("ignore");
        if (ignore != null && !Boolean.valueOf(ignore.toString()).booleanValue() && params.get("bizcode") != null) {
            String createtime = attachment.get("createtime").toString();
            Date attachmentCreateTime = Utils.parseDateTime((String)createtime);
            ProcessDTO processDTO = new ProcessDTO();
            processDTO.setBizcode(params.get("bizcode").toString());
            com.jiuqi.va.domain.common.R process = this.workflowServerClient.getProcess(processDTO);
            Object result = process.get((Object)"process");
            if (result != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                objectMapper.setDateFormat((DateFormat)simpleDateFormat);
                ProcessDO processDO = new ProcessDO();
                try {
                    processDO = (ProcessDO)objectMapper.readValue(objectMapper.writeValueAsString(result), ProcessDO.class);
                }
                catch (JsonProcessingException e) {
                    logger.error(e.getMessage(), e);
                }
                Date starttime = processDO.getStarttime();
                ProcessNodeDTO processNodeDTO = new ProcessNodeDTO();
                processNodeDTO.setProcessid(processDO.getId());
                processNodeDTO.setSort("ordernum");
                processNodeDTO.setSyscode("WORKFLOW");
                processNodeDTO.setTraceId(Utils.getTraceId());
                List processNodeDOS = this.workflowServerClient.listProcessNode(processNodeDTO);
                List collect = processNodeDOS.stream().filter(o -> o.getCompletetime() != null).collect(Collectors.toList());
                ProcessNodeDO processNodeDO = (ProcessNodeDO)collect.get(collect.size() - 1);
                Date completetime = processNodeDO.getCompletetime();
                if (Boolean.valueOf(params.get("createuser").toString()) != false ? attachmentCreateTime.compareTo(completetime) < 0 : attachmentCreateTime.compareTo(starttime) > 0 && attachmentCreateTime.compareTo(completetime) < 0) {
                    return R.error((String)BillCoreI18nUtil.getMessage("va.bill.core.attconfirm.notdel"));
                }
            }
        }
        String quotecode = attachment.get("quotecode").toString();
        String id = attachment.get("id").toString();
        if (params.get("type").equals("update")) {
            attachmentBizDO = new AttachmentBizDO();
            attachmentBizDO.setQuotecode(quotecode);
            attachmentBizDO.setStatus(Integer.valueOf(2));
            attachmentBizDO.setId(UUID.fromString(id));
            attachmentBizDO.setTraceId(Utils.getTraceId());
            list = new ArrayList<AttachmentBizDO>();
            list.add(attachmentBizDO);
            return this.vaAttachmentFeignClient.update(quotecode, list);
        }
        attachmentBizDO = new AttachmentBizDTO();
        attachmentBizDO.setQuotecode(quotecode);
        attachmentBizDO.setId(UUID.fromString(id));
        attachmentBizDO.setTraceId(Utils.getTraceId());
        attachmentBizDO.setName((String)attachment.get("name"));
        attachmentBizDO.setFilepath((String)attachment.get("filepath"));
        list = new ArrayList();
        list.add(attachmentBizDO);
        return this.vaAttachmentFeignClient.remove(quotecode, list);
    }

    public String getActionPriority() {
        return "029";
    }
}

