/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.nr.workflow2.events.executor.msg.enumeration.MessageType
 */
package com.jiuqi.nr.workflow2.settings.upgrade;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.nr.workflow2.events.executor.msg.enumeration.MessageType;
import com.jiuqi.nr.workflow2.settings.message.dao.MessageSampleDao;
import com.jiuqi.nr.workflow2.settings.message.dto.MessageSampleSaveAsContext;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

public class MessageSampleInit
implements CustomClassExecutor {
    public static final String NON_BREAKING_SPACE_HALF_WIDTH = "&nbsp;";
    public static final String NON_BREAKING_SPACE_FULL_WIDTH = "&emsp;";
    public static final String P_TAG = "<p>";
    public static final String P_TAG_END = "</p>";
    public static final String SPAN_TAG_PREFIX = "<span";
    public static final String SPAN_TAG_END = "</span>";
    public static final String VARIABLE_CURRENT_NODE_NAME = "<span class=\"w-e-tag\" data-w-e-type=\"tag\"  data-title=\"\u5f53\u524d\u8282\u70b9\u540d\u79f0\" data-code=\"currentNodeName\">\u3010\u5f53\u524d\u8282\u70b9\u540d\u79f0\u3011</span>";
    public static final String VARIABLE_NEXT_NODE_NAME = "<span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u4e0b\u4e2a\u8282\u70b9\u540d\u79f0\" data-code=\"nextNodeName\">\u3010\u4e0b\u4e2a\u8282\u70b9\u540d\u79f0\u3011</span>";
    public static final String VARIABLE_FORM_SCHEME_NAME = "<span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u62a5\u8868\u65b9\u6848\u540d\u79f0\" data-code=\"formSchemeName\">\u3010\u62a5\u8868\u65b9\u6848\u540d\u79f0\u3011</span>";
    public static final String VARIABLE_MD_NAME = "<span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u7ec4\u7ec7\u673a\u6784\u540d\u79f0\" data-code=\"mdName\">\u3010\u7ec4\u7ec7\u673a\u6784\u540d\u79f0\u3011</span>";
    public static final String VARIABLE_GROUP_NAME = "<span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u5206\u7ec4\u540d\u79f0\" data-code=\"groupName\">\u3010\u5206\u7ec4\u540d\u79f0\u3011</span>";
    public static final String VARIABLE_REPORT_NAME = "<span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u62a5\u8868\u540d\u79f0\" data-code=\"reportName\">\u3010\u62a5\u8868\u540d\u79f0\u3011</span>";
    public static final String VARIABLE_OPERATOR = "<span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u64cd\u4f5c\u7528\u6237\" data-code=\"operator\">\u3010\u64cd\u4f5c\u7528\u6237\u3011</span>";
    public static final String VARIABLE_OPERATE_EXPLANATION = "<span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u64cd\u4f5c\u8bf4\u660e\" data-code=\"operateExplanation\">\u3010\u64cd\u4f5c\u8bf4\u660e\u3011</span>";
    public static final String VARIABLE_TASK_NAME = "<span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u4efb\u52a1\u540d\u79f0\" data-code=\"taskName\">\u3010\u4efb\u52a1\u540d\u79f0\u3011</span>";
    public static final String VARIABLE_PERIOD = "<span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u65f6\u671f\" data-code=\"period\">\u3010\u65f6\u671f\u3011</span>";
    public static final String VARIABLE_ENTITY_CALIBER = "<span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u5355\u4f4d\u53e3\u5f84\" data-code=\"entityCaliber\">\u3010\u5355\u4f4d\u53e3\u5f84\u3011</span>";
    public static final String TEXT_SUBMIT_SUBMIT_ACTION = "\u5df2\u9001\u5ba1\uff0c\u8bf7\u53ca\u65f6\u4e0a\u62a5\u3002";
    public static final String TEXT_REPORT_REPORT_ACTION = "\u5df2\u4e0a\u62a5\uff0c\u8bf7\u53ca\u65f6\u5ba1\u6279\u3002";
    public static final String TEXT_REPORT_BACK_ACTION = "\u5df2\u9000\u5ba1\uff0c\u8bf7\u53ca\u65f6\u9001\u5ba1\u3002";
    public static final String TEXT_REPORT_APPLY_REJECT_ACTION = "\u5df2\u7533\u8bf7\u9000\u56de\uff0c\u8bf7\u53ca\u65f6\u5ba1\u6279\u3002";
    public static final String TEXT_AUDIT_CONFIRM_ACTION = "\u5df2\u786e\u8ba4\u3002";
    public static final String TEXT_AUDIT_REJECT_ACTION_SUBMIT = "\u5df2\u9000\u56de\uff0c\u8bf7\u53ca\u65f6\u9001\u5ba1\u3002";
    public static final String TEXT_AUDIT_REJECT_ACTION_REPORT = "\u5df2\u9000\u56de\uff0c\u8bf7\u53ca\u65f6\u4e0a\u62a5\u3002";
    public static final String HONORIFIC_USER = "\u5c0a\u656c\u7684\u7528\u6237\uff1a";
    public static final String HONORIFIC_HELLO = "\u4f60\u597d\uff01";
    public static final String DEFAULT_MESSAGE_ID_PREFIX = "DEFAULT_MESSAGE_ID_";
    public static final String DEFAULT_EMAIL_ID_PREFIX = "DEFAULT_EMAIL_ID_";
    public static final String DEFAULT_SHORT_MESSAGE_ID_PREFIX = "DEFAULT_SHORT_MESSAGE_ID_";
    private final MessageSampleDao messageSampleDao = (MessageSampleDao)SpringBeanUtils.getBean(MessageSampleDao.class);

    public void execute(DataSource dataSource) {
        this.messageSampleDao.clearMessageSample();
        ArrayList<MessageSampleSaveAsContext> initSample = new ArrayList<MessageSampleSaveAsContext>();
        initSample.addAll(this.buildMessageInitSample());
        initSample.addAll(this.buildEmailInitSample());
        initSample.addAll(this.buildShortMessageInitSample());
        this.messageSampleDao.batchAddMessageSample(initSample);
    }

    private List<MessageSampleSaveAsContext> buildMessageInitSample() {
        ArrayList<MessageSampleSaveAsContext> messageInitSample = new ArrayList<MessageSampleSaveAsContext>();
        MessageSampleSaveAsContext submit_submitAction_sample = new MessageSampleSaveAsContext();
        submit_submitAction_sample.setId("DEFAULT_MESSAGE_ID_SUBMIT");
        submit_submitAction_sample.setType(MessageType.MESSAGE.code);
        submit_submitAction_sample.setActionCode("act_submit");
        submit_submitAction_sample.setTitle("\u9001\u5ba1\u901a\u77e5-\u6d88\u606f");
        submit_submitAction_sample.setSubject(null);
        String submit_submitAction_sample_content = "<p><span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u4efb\u52a1\u540d\u79f0\" data-code=\"taskName\">\u3010\u4efb\u52a1\u540d\u79f0\u3011</span><span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u65f6\u671f\" data-code=\"period\">\u3010\u65f6\u671f\u3011</span><span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u7ec4\u7ec7\u673a\u6784\u540d\u79f0\" data-code=\"mdName\">\u3010\u7ec4\u7ec7\u673a\u6784\u540d\u79f0\u3011</span>\u5df2\u9001\u5ba1\uff0c\u8bf7\u53ca\u65f6\u4e0a\u62a5\u3002</p>";
        submit_submitAction_sample.setContent(submit_submitAction_sample_content);
        messageInitSample.add(submit_submitAction_sample);
        MessageSampleSaveAsContext report_reportAction_sample = new MessageSampleSaveAsContext();
        report_reportAction_sample.setId("DEFAULT_MESSAGE_ID_REPORT");
        report_reportAction_sample.setType(MessageType.MESSAGE.code);
        report_reportAction_sample.setActionCode("act_upload");
        report_reportAction_sample.setTitle("\u4e0a\u62a5\u901a\u77e5-\u6d88\u606f");
        report_reportAction_sample.setSubject(null);
        String report_reportAction_sample_content = "<p><span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u4efb\u52a1\u540d\u79f0\" data-code=\"taskName\">\u3010\u4efb\u52a1\u540d\u79f0\u3011</span><span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u65f6\u671f\" data-code=\"period\">\u3010\u65f6\u671f\u3011</span><span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u7ec4\u7ec7\u673a\u6784\u540d\u79f0\" data-code=\"mdName\">\u3010\u7ec4\u7ec7\u673a\u6784\u540d\u79f0\u3011</span>\u5df2\u4e0a\u62a5\uff0c\u8bf7\u53ca\u65f6\u5ba1\u6279\u3002</p>";
        report_reportAction_sample.setContent(report_reportAction_sample_content);
        messageInitSample.add(report_reportAction_sample);
        MessageSampleSaveAsContext report_backAction_sample = new MessageSampleSaveAsContext();
        report_backAction_sample.setId("DEFAULT_MESSAGE_ID_BACK");
        report_backAction_sample.setType(MessageType.MESSAGE.code);
        report_backAction_sample.setActionCode("act_return");
        report_backAction_sample.setTitle("\u9000\u5ba1\u901a\u77e5-\u6d88\u606f");
        String report_backAction_sample_content = "<p><span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u4efb\u52a1\u540d\u79f0\" data-code=\"taskName\">\u3010\u4efb\u52a1\u540d\u79f0\u3011</span><span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u65f6\u671f\" data-code=\"period\">\u3010\u65f6\u671f\u3011</span><span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u7ec4\u7ec7\u673a\u6784\u540d\u79f0\" data-code=\"mdName\">\u3010\u7ec4\u7ec7\u673a\u6784\u540d\u79f0\u3011</span>\u5df2\u9000\u5ba1\uff0c\u8bf7\u53ca\u65f6\u9001\u5ba1\u3002</p>";
        report_backAction_sample.setSubject(null);
        report_backAction_sample.setContent(report_backAction_sample_content);
        messageInitSample.add(report_backAction_sample);
        MessageSampleSaveAsContext report_applyForRejectAction_sample = new MessageSampleSaveAsContext();
        report_applyForRejectAction_sample.setId("DEFAULT_MESSAGE_ID_APPLY_FOR_REJECT");
        report_applyForRejectAction_sample.setType(MessageType.MESSAGE.code);
        report_applyForRejectAction_sample.setActionCode("act_apply_reject");
        report_applyForRejectAction_sample.setTitle("\u7533\u8bf7\u9000\u56de\u901a\u77e5-\u6d88\u606f");
        String report_applyForRejectAction_sample_content = "<p><span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u4efb\u52a1\u540d\u79f0\" data-code=\"taskName\">\u3010\u4efb\u52a1\u540d\u79f0\u3011</span><span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u65f6\u671f\" data-code=\"period\">\u3010\u65f6\u671f\u3011</span><span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u7ec4\u7ec7\u673a\u6784\u540d\u79f0\" data-code=\"mdName\">\u3010\u7ec4\u7ec7\u673a\u6784\u540d\u79f0\u3011</span>\u5df2\u7533\u8bf7\u9000\u56de\uff0c\u8bf7\u53ca\u65f6\u5ba1\u6279\u3002</p>";
        report_applyForRejectAction_sample.setSubject(null);
        report_applyForRejectAction_sample.setContent(report_applyForRejectAction_sample_content);
        messageInitSample.add(report_applyForRejectAction_sample);
        MessageSampleSaveAsContext audit_rejectAction_sample = new MessageSampleSaveAsContext();
        audit_rejectAction_sample.setId("DEFAULT_MESSAGE_ID_REJECT");
        audit_rejectAction_sample.setType(MessageType.MESSAGE.code);
        audit_rejectAction_sample.setActionCode("act_reject");
        audit_rejectAction_sample.setTitle("\u9000\u56de\u901a\u77e5-\u6d88\u606f");
        audit_rejectAction_sample.setSubject(null);
        String audit_rejectAction_sample_content = "<p><span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u4efb\u52a1\u540d\u79f0\" data-code=\"taskName\">\u3010\u4efb\u52a1\u540d\u79f0\u3011</span><span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u65f6\u671f\" data-code=\"period\">\u3010\u65f6\u671f\u3011</span><span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u7ec4\u7ec7\u673a\u6784\u540d\u79f0\" data-code=\"mdName\">\u3010\u7ec4\u7ec7\u673a\u6784\u540d\u79f0\u3011</span>\u5df2\u9000\u56de\uff0c\u8bf7\u53ca\u65f6\u4e0a\u62a5\u3002</p>";
        audit_rejectAction_sample.setContent(audit_rejectAction_sample_content);
        messageInitSample.add(audit_rejectAction_sample);
        MessageSampleSaveAsContext audit_confirmAction_sample = new MessageSampleSaveAsContext();
        audit_confirmAction_sample.setId("DEFAULT_MESSAGE_ID_CONFIRM");
        audit_confirmAction_sample.setType(MessageType.MESSAGE.code);
        audit_confirmAction_sample.setActionCode("act_confirm");
        audit_confirmAction_sample.setTitle("\u786e\u8ba4\u901a\u77e5-\u6d88\u606f");
        audit_confirmAction_sample.setSubject(null);
        String audit_confirmAction_sample_content = "<p><span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u4efb\u52a1\u540d\u79f0\" data-code=\"taskName\">\u3010\u4efb\u52a1\u540d\u79f0\u3011</span><span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u65f6\u671f\" data-code=\"period\">\u3010\u65f6\u671f\u3011</span><span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u7ec4\u7ec7\u673a\u6784\u540d\u79f0\" data-code=\"mdName\">\u3010\u7ec4\u7ec7\u673a\u6784\u540d\u79f0\u3011</span>\u5df2\u786e\u8ba4\u3002</p>";
        audit_confirmAction_sample.setContent(audit_confirmAction_sample_content);
        messageInitSample.add(audit_confirmAction_sample);
        return messageInitSample;
    }

    private List<MessageSampleSaveAsContext> buildEmailInitSample() {
        String honorific_user = "<p>\u5c0a\u656c\u7684\u7528\u6237\uff1a</p>";
        String honorific_hello = "<p>&nbsp;&nbsp;\u4f60\u597d\uff01</p>";
        ArrayList<MessageSampleSaveAsContext> emailInitSample = new ArrayList<MessageSampleSaveAsContext>();
        MessageSampleSaveAsContext submit_submitAction_sample = new MessageSampleSaveAsContext();
        submit_submitAction_sample.setId("DEFAULT_EMAIL_ID_SUBMIT");
        submit_submitAction_sample.setType(MessageType.EMAIL.code);
        submit_submitAction_sample.setActionCode("act_submit");
        submit_submitAction_sample.setTitle("\u9001\u5ba1\u901a\u77e5-\u90ae\u4ef6");
        submit_submitAction_sample.setSubject("\u9001\u5ba1\u901a\u77e5-\u90ae\u4ef6");
        String submit_submitAction_sample_content = "<p>&nbsp;&nbsp;<span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u4efb\u52a1\u540d\u79f0\" data-code=\"taskName\">\u3010\u4efb\u52a1\u540d\u79f0\u3011</span><span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u65f6\u671f\" data-code=\"period\">\u3010\u65f6\u671f\u3011</span><span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u7ec4\u7ec7\u673a\u6784\u540d\u79f0\" data-code=\"mdName\">\u3010\u7ec4\u7ec7\u673a\u6784\u540d\u79f0\u3011</span>\u5df2\u9001\u5ba1\uff0c\u8bf7\u53ca\u65f6\u4e0a\u62a5\u3002</p>";
        submit_submitAction_sample.setContent(honorific_user + honorific_hello + submit_submitAction_sample_content);
        emailInitSample.add(submit_submitAction_sample);
        MessageSampleSaveAsContext report_reportAction_sample = new MessageSampleSaveAsContext();
        report_reportAction_sample.setId("DEFAULT_EMAIL_ID_REPORT");
        report_reportAction_sample.setType(MessageType.EMAIL.code);
        report_reportAction_sample.setActionCode("act_upload");
        report_reportAction_sample.setTitle("\u4e0a\u62a5\u901a\u77e5-\u90ae\u4ef6");
        report_reportAction_sample.setSubject("\u4e0a\u62a5\u901a\u77e5-\u90ae\u4ef6");
        String report_reportAction_sample_content = "<p>&nbsp;&nbsp;<span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u4efb\u52a1\u540d\u79f0\" data-code=\"taskName\">\u3010\u4efb\u52a1\u540d\u79f0\u3011</span><span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u65f6\u671f\" data-code=\"period\">\u3010\u65f6\u671f\u3011</span><span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u7ec4\u7ec7\u673a\u6784\u540d\u79f0\" data-code=\"mdName\">\u3010\u7ec4\u7ec7\u673a\u6784\u540d\u79f0\u3011</span>\u5df2\u4e0a\u62a5\uff0c\u8bf7\u53ca\u65f6\u5ba1\u6279\u3002</p>";
        report_reportAction_sample.setContent(honorific_user + honorific_hello + report_reportAction_sample_content);
        emailInitSample.add(report_reportAction_sample);
        MessageSampleSaveAsContext report_backAction_sample = new MessageSampleSaveAsContext();
        report_backAction_sample.setId("DEFAULT_EMAIL_ID_BACK");
        report_backAction_sample.setType(MessageType.EMAIL.code);
        report_backAction_sample.setActionCode("act_return");
        report_backAction_sample.setTitle("\u9000\u5ba1\u901a\u77e5-\u90ae\u4ef6");
        report_backAction_sample.setSubject("\u9000\u5ba1\u901a\u77e5-\u90ae\u4ef6");
        String report_backAction_sample_content = "<p>&nbsp;&nbsp;<span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u4efb\u52a1\u540d\u79f0\" data-code=\"taskName\">\u3010\u4efb\u52a1\u540d\u79f0\u3011</span><span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u65f6\u671f\" data-code=\"period\">\u3010\u65f6\u671f\u3011</span><span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u7ec4\u7ec7\u673a\u6784\u540d\u79f0\" data-code=\"mdName\">\u3010\u7ec4\u7ec7\u673a\u6784\u540d\u79f0\u3011</span>\u5df2\u9000\u5ba1\uff0c\u8bf7\u53ca\u65f6\u9001\u5ba1\u3002</p>";
        report_backAction_sample.setContent(honorific_user + honorific_hello + report_backAction_sample_content);
        emailInitSample.add(report_backAction_sample);
        MessageSampleSaveAsContext report_applyForRejectAction_sample = new MessageSampleSaveAsContext();
        report_applyForRejectAction_sample.setId("DEFAULT_EMAIL_ID_APPLY_FOR_REJECT");
        report_applyForRejectAction_sample.setType(MessageType.EMAIL.code);
        report_applyForRejectAction_sample.setActionCode("act_apply_reject");
        report_applyForRejectAction_sample.setTitle("\u7533\u8bf7\u9000\u56de\u901a\u77e5-\u90ae\u4ef6");
        report_applyForRejectAction_sample.setSubject("\u7533\u8bf7\u9000\u56de\u901a\u77e5-\u90ae\u4ef6");
        String report_applyForRejectAction_sample_content = "<p>&nbsp;&nbsp;<span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u4efb\u52a1\u540d\u79f0\" data-code=\"taskName\">\u3010\u4efb\u52a1\u540d\u79f0\u3011</span><span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u65f6\u671f\" data-code=\"period\">\u3010\u65f6\u671f\u3011</span><span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u7ec4\u7ec7\u673a\u6784\u540d\u79f0\" data-code=\"mdName\">\u3010\u7ec4\u7ec7\u673a\u6784\u540d\u79f0\u3011</span>\u5df2\u7533\u8bf7\u9000\u56de\uff0c\u8bf7\u53ca\u65f6\u5ba1\u6279\u3002</p>";
        report_applyForRejectAction_sample.setContent(honorific_user + honorific_hello + report_applyForRejectAction_sample_content);
        emailInitSample.add(report_applyForRejectAction_sample);
        MessageSampleSaveAsContext audit_rejectAction_sample = new MessageSampleSaveAsContext();
        audit_rejectAction_sample.setId("DEFAULT_EMAIL_ID_REJECT");
        audit_rejectAction_sample.setType(MessageType.EMAIL.code);
        audit_rejectAction_sample.setActionCode("act_reject");
        audit_rejectAction_sample.setTitle("\u9000\u56de\u901a\u77e5-\u90ae\u4ef6");
        audit_rejectAction_sample.setSubject("\u9000\u56de\u901a\u77e5-\u90ae\u4ef6");
        String audit_rejectAction_sample_content = "<p>&nbsp;&nbsp;<span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u4efb\u52a1\u540d\u79f0\" data-code=\"taskName\">\u3010\u4efb\u52a1\u540d\u79f0\u3011</span><span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u65f6\u671f\" data-code=\"period\">\u3010\u65f6\u671f\u3011</span><span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u7ec4\u7ec7\u673a\u6784\u540d\u79f0\" data-code=\"mdName\">\u3010\u7ec4\u7ec7\u673a\u6784\u540d\u79f0\u3011</span>\u5df2\u9000\u56de\uff0c\u8bf7\u53ca\u65f6\u4e0a\u62a5\u3002</p>";
        audit_rejectAction_sample.setContent(honorific_user + honorific_hello + audit_rejectAction_sample_content);
        emailInitSample.add(audit_rejectAction_sample);
        MessageSampleSaveAsContext audit_confirmAction_sample = new MessageSampleSaveAsContext();
        audit_confirmAction_sample.setId("DEFAULT_EMAIL_ID_CONFIRM");
        audit_confirmAction_sample.setType(MessageType.EMAIL.code);
        audit_confirmAction_sample.setActionCode("act_confirm");
        audit_confirmAction_sample.setTitle("\u786e\u8ba4\u901a\u77e5-\u90ae\u4ef6");
        audit_confirmAction_sample.setSubject("\u786e\u8ba4\u901a\u77e5-\u90ae\u4ef6");
        String audit_confirmAction_sample_content = "<p>&nbsp;&nbsp;<span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u4efb\u52a1\u540d\u79f0\" data-code=\"taskName\">\u3010\u4efb\u52a1\u540d\u79f0\u3011</span><span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u65f6\u671f\" data-code=\"period\">\u3010\u65f6\u671f\u3011</span><span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u7ec4\u7ec7\u673a\u6784\u540d\u79f0\" data-code=\"mdName\">\u3010\u7ec4\u7ec7\u673a\u6784\u540d\u79f0\u3011</span>\u5df2\u786e\u8ba4\u3002</p>";
        audit_confirmAction_sample.setContent(honorific_user + honorific_hello + audit_confirmAction_sample_content);
        emailInitSample.add(audit_confirmAction_sample);
        return emailInitSample;
    }

    private List<MessageSampleSaveAsContext> buildShortMessageInitSample() {
        ArrayList<MessageSampleSaveAsContext> shortMessageInitSample = new ArrayList<MessageSampleSaveAsContext>();
        MessageSampleSaveAsContext submit_submitAction_sample = new MessageSampleSaveAsContext();
        submit_submitAction_sample.setId("DEFAULT_SHORT_MESSAGE_ID_SUBMIT");
        submit_submitAction_sample.setType(MessageType.SHORT_MESSAGE.code);
        submit_submitAction_sample.setActionCode("act_submit");
        submit_submitAction_sample.setTitle("\u9001\u5ba1\u901a\u77e5-\u77ed\u4fe1");
        submit_submitAction_sample.setSubject(null);
        String submit_submitAction_sample_content = "<p><span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u4efb\u52a1\u540d\u79f0\" data-code=\"taskName\">\u3010\u4efb\u52a1\u540d\u79f0\u3011</span><span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u65f6\u671f\" data-code=\"period\">\u3010\u65f6\u671f\u3011</span><span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u7ec4\u7ec7\u673a\u6784\u540d\u79f0\" data-code=\"mdName\">\u3010\u7ec4\u7ec7\u673a\u6784\u540d\u79f0\u3011</span>\u5df2\u9001\u5ba1\uff0c\u8bf7\u53ca\u65f6\u4e0a\u62a5\u3002</p>";
        submit_submitAction_sample.setContent(submit_submitAction_sample_content);
        shortMessageInitSample.add(submit_submitAction_sample);
        MessageSampleSaveAsContext report_reportAction_sample = new MessageSampleSaveAsContext();
        report_reportAction_sample.setId("DEFAULT_SHORT_MESSAGE_ID_REPORT");
        report_reportAction_sample.setType(MessageType.SHORT_MESSAGE.code);
        report_reportAction_sample.setActionCode("act_upload");
        report_reportAction_sample.setTitle("\u4e0a\u62a5\u901a\u77e5-\u77ed\u4fe1");
        report_reportAction_sample.setSubject(null);
        String report_reportAction_sample_content = "<p><span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u4efb\u52a1\u540d\u79f0\" data-code=\"taskName\">\u3010\u4efb\u52a1\u540d\u79f0\u3011</span><span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u65f6\u671f\" data-code=\"period\">\u3010\u65f6\u671f\u3011</span><span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u7ec4\u7ec7\u673a\u6784\u540d\u79f0\" data-code=\"mdName\">\u3010\u7ec4\u7ec7\u673a\u6784\u540d\u79f0\u3011</span>\u5df2\u4e0a\u62a5\uff0c\u8bf7\u53ca\u65f6\u5ba1\u6279\u3002</p>";
        report_reportAction_sample.setContent(report_reportAction_sample_content);
        shortMessageInitSample.add(report_reportAction_sample);
        MessageSampleSaveAsContext report_backAction_sample = new MessageSampleSaveAsContext();
        report_backAction_sample.setId("DEFAULT_SHORT_MESSAGE_ID_BACK");
        report_backAction_sample.setType(MessageType.SHORT_MESSAGE.code);
        report_backAction_sample.setActionCode("act_return");
        report_backAction_sample.setTitle("\u9000\u5ba1\u901a\u77e5-\u77ed\u4fe1");
        report_backAction_sample.setSubject(null);
        String report_backAction_sample_content = "<p><span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u4efb\u52a1\u540d\u79f0\" data-code=\"taskName\">\u3010\u4efb\u52a1\u540d\u79f0\u3011</span><span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u65f6\u671f\" data-code=\"period\">\u3010\u65f6\u671f\u3011</span><span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u7ec4\u7ec7\u673a\u6784\u540d\u79f0\" data-code=\"mdName\">\u3010\u7ec4\u7ec7\u673a\u6784\u540d\u79f0\u3011</span>\u5df2\u9000\u5ba1\uff0c\u8bf7\u53ca\u65f6\u9001\u5ba1\u3002</p>";
        report_backAction_sample.setContent(report_backAction_sample_content);
        shortMessageInitSample.add(report_backAction_sample);
        MessageSampleSaveAsContext report_applyForRejectAction_sample = new MessageSampleSaveAsContext();
        report_applyForRejectAction_sample.setId("DEFAULT_SHORT_MESSAGE_ID_APPLY_FOR_REJECT");
        report_applyForRejectAction_sample.setType(MessageType.SHORT_MESSAGE.code);
        report_applyForRejectAction_sample.setActionCode("act_apply_reject");
        report_applyForRejectAction_sample.setTitle("\u7533\u8bf7\u9000\u56de\u901a\u77e5-\u77ed\u4fe1");
        report_applyForRejectAction_sample.setSubject(null);
        String report_applyForRejectAction_sample_content = "<p><span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u4efb\u52a1\u540d\u79f0\" data-code=\"taskName\">\u3010\u4efb\u52a1\u540d\u79f0\u3011</span><span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u65f6\u671f\" data-code=\"period\">\u3010\u65f6\u671f\u3011</span><span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u7ec4\u7ec7\u673a\u6784\u540d\u79f0\" data-code=\"mdName\">\u3010\u7ec4\u7ec7\u673a\u6784\u540d\u79f0\u3011</span>\u5df2\u7533\u8bf7\u9000\u56de\uff0c\u8bf7\u53ca\u65f6\u5ba1\u6279\u3002</p>";
        report_applyForRejectAction_sample.setContent(report_applyForRejectAction_sample_content);
        shortMessageInitSample.add(report_applyForRejectAction_sample);
        MessageSampleSaveAsContext audit_rejectAction_sample = new MessageSampleSaveAsContext();
        audit_rejectAction_sample.setId("DEFAULT_SHORT_MESSAGE_ID_REJECT");
        audit_rejectAction_sample.setType(MessageType.SHORT_MESSAGE.code);
        audit_rejectAction_sample.setActionCode("act_reject");
        audit_rejectAction_sample.setTitle("\u9000\u56de\u901a\u77e5-\u77ed\u4fe1");
        audit_rejectAction_sample.setSubject(null);
        String audit_rejectAction_sample_content = "<p><span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u4efb\u52a1\u540d\u79f0\" data-code=\"taskName\">\u3010\u4efb\u52a1\u540d\u79f0\u3011</span><span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u65f6\u671f\" data-code=\"period\">\u3010\u65f6\u671f\u3011</span><span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u7ec4\u7ec7\u673a\u6784\u540d\u79f0\" data-code=\"mdName\">\u3010\u7ec4\u7ec7\u673a\u6784\u540d\u79f0\u3011</span>\u5df2\u9000\u56de\uff0c\u8bf7\u53ca\u65f6\u4e0a\u62a5\u3002</p>";
        audit_rejectAction_sample.setContent(audit_rejectAction_sample_content);
        shortMessageInitSample.add(audit_rejectAction_sample);
        MessageSampleSaveAsContext audit_confirmAction_sample = new MessageSampleSaveAsContext();
        audit_confirmAction_sample.setId("DEFAULT_SHORT_MESSAGE_ID_CONFIRM");
        audit_confirmAction_sample.setType(MessageType.SHORT_MESSAGE.code);
        audit_confirmAction_sample.setActionCode("act_confirm");
        audit_confirmAction_sample.setTitle("\u786e\u8ba4\u901a\u77e5-\u77ed\u4fe1");
        audit_confirmAction_sample.setSubject(null);
        String audit_confirmAction_sample_content = "<p><span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u4efb\u52a1\u540d\u79f0\" data-code=\"taskName\">\u3010\u4efb\u52a1\u540d\u79f0\u3011</span><span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u65f6\u671f\" data-code=\"period\">\u3010\u65f6\u671f\u3011</span><span class=\"w-e-tag\" data-w-e-type=\"tag\" data-title=\"\u7ec4\u7ec7\u673a\u6784\u540d\u79f0\" data-code=\"mdName\">\u3010\u7ec4\u7ec7\u673a\u6784\u540d\u79f0\u3011</span>\u5df2\u786e\u8ba4\u3002</p>";
        audit_confirmAction_sample.setContent(audit_confirmAction_sample_content);
        shortMessageInitSample.add(audit_confirmAction_sample);
        return shortMessageInitSample;
    }
}

