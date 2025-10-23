/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.common.definition.model.UserActionTemplate
 *  com.jiuqi.nr.workflow2.events.executor.msg.enumeration.MessageType
 */
package com.jiuqi.nr.workflow2.settings.message.service;

import com.jiuqi.nr.workflow2.engine.common.definition.model.UserActionTemplate;
import com.jiuqi.nr.workflow2.events.executor.msg.enumeration.MessageType;
import com.jiuqi.nr.workflow2.settings.message.dao.MessageSampleDao;
import com.jiuqi.nr.workflow2.settings.message.domain.MessageSampleDO;
import com.jiuqi.nr.workflow2.settings.message.dto.MessageSampleSaveAsContext;
import com.jiuqi.nr.workflow2.settings.message.service.MessageSampleService;
import com.jiuqi.nr.workflow2.settings.message.vo.MessageSampleVO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;

public class MessageSampleServiceImpl
implements MessageSampleService {
    @Autowired
    private MessageSampleDao messageSampleDao;

    @Override
    public List<MessageSampleVO> queryMessageSample(String filterType, String filterActionCode) {
        List<MessageSampleDO> messageSampleDOList = this.messageSampleDao.queryMessageSample(filterType, filterActionCode);
        return messageSampleDOList.stream().map(e -> {
            MessageSampleVO vo = new MessageSampleVO();
            vo.setId(e.getId());
            vo.setTitle(e.getTitle());
            vo.setSubject(e.getSubject());
            vo.setContent(e.getContent());
            return vo;
        }).collect(Collectors.toList());
    }

    @Override
    public MessageSampleDO queryMessageSampleByTitle(String title) {
        return this.messageSampleDao.queryMessageSampleByTitle(title);
    }

    @Override
    public Map<String, Object> getFilterConditionSource(String taskId, String workflowNode) {
        HashMap<String, Object> conditionSource = new HashMap<String, Object>();
        ArrayList typeSource = new ArrayList();
        for (MessageType messageType : MessageType.values()) {
            HashMap<String, String> type = new HashMap<String, String>();
            type.put("code", messageType.code);
            type.put("title", messageType.title);
            typeSource.add(type);
        }
        conditionSource.put("typeSource", typeSource);
        ArrayList actionSource = new ArrayList();
        HashMap<String, String> allOption = new HashMap<String, String>();
        allOption.put("code", "all");
        allOption.put("title", "\u5168\u90e8");
        actionSource.add(allOption);
        HashMap<String, String> submitAction = new HashMap<String, String>();
        submitAction.put("code", UserActionTemplate.SUBMIT.getCode());
        submitAction.put("title", UserActionTemplate.SUBMIT.getTitle());
        actionSource.add(submitAction);
        HashMap<String, String> backAction = new HashMap<String, String>();
        backAction.put("code", UserActionTemplate.BACK.getCode());
        backAction.put("title", UserActionTemplate.BACK.getTitle());
        actionSource.add(backAction);
        HashMap<String, String> reportAction = new HashMap<String, String>();
        reportAction.put("code", UserActionTemplate.REPORT.getCode());
        reportAction.put("title", UserActionTemplate.REPORT.getTitle());
        actionSource.add(reportAction);
        HashMap<String, String> rejectAction = new HashMap<String, String>();
        rejectAction.put("code", UserActionTemplate.REJECT.getCode());
        rejectAction.put("title", UserActionTemplate.REJECT.getTitle());
        actionSource.add(rejectAction);
        HashMap<String, String> confirmAction = new HashMap<String, String>();
        confirmAction.put("code", UserActionTemplate.CONFIRM.getCode());
        confirmAction.put("title", UserActionTemplate.CONFIRM.getTitle());
        actionSource.add(confirmAction);
        HashMap<String, String> retrieveAction = new HashMap<String, String>();
        retrieveAction.put("code", UserActionTemplate.RETRIEVE.getCode());
        retrieveAction.put("title", UserActionTemplate.RETRIEVE.getTitle());
        actionSource.add(retrieveAction);
        HashMap<String, String> applyForRejectAction = new HashMap<String, String>();
        applyForRejectAction.put("code", UserActionTemplate.APPLY_FOR_REJECT.getCode());
        applyForRejectAction.put("title", UserActionTemplate.APPLY_FOR_REJECT.getTitle());
        actionSource.add(applyForRejectAction);
        conditionSource.put("actionSource", actionSource);
        return conditionSource;
    }

    @Override
    public boolean saveMessageSampleAs(MessageSampleSaveAsContext context) {
        return this.messageSampleDao.addMessageSample(context);
    }

    @Override
    public boolean deleteMessageSample(String id) {
        return this.messageSampleDao.deleteMessageSample(id);
    }
}

