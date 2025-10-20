/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.common.enums.TaskStateEnum
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 */
package com.jiuqi.gcreport.temp.dto;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.common.enums.TaskStateEnum;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.temp.dto.Message;
import com.jiuqi.gcreport.temp.dto.MessageTypeEnum;
import com.jiuqi.gcreport.temp.dto.OnekeyProgressDataImpl;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.springframework.beans.BeanUtils;

public class TaskLog {
    private OnekeyProgressDataImpl progressData;
    private Message warn = new Message(MessageTypeEnum.WARN);
    private Message info = new Message(MessageTypeEnum.INFO);
    private Message error = new Message(MessageTypeEnum.ERROR);
    private List<Message> messages = Collections.synchronizedList(new ArrayList());
    private List<Message> completeMessage = Collections.synchronizedList(new ArrayList());
    private Integer doneNum = 0;
    private Integer totalNum = 0;
    private boolean finish = false;
    private TaskStateEnum state = TaskStateEnum.WAITTING;
    private float processPercent = 0.0f;
    private int process = 0;
    private Date createDate;
    private String startTime;
    private String userName;

    public TaskLog() {
    }

    public TaskLog(OnekeyProgressDataImpl onekeyProgressData) {
        this.progressData = onekeyProgressData;
    }

    public TaskLog writeInfoLog(String logs, Float percent) {
        if (percent != null) {
            this.processPercent = percent.floatValue();
        }
        StringBuffer log = new StringBuffer(logs);
        Message info = new Message(log, MessageTypeEnum.INFO);
        info.setKey(UUIDUtils.newUUIDStr());
        this.recordLog(info);
        return this;
    }

    public TaskLog writeWarnLog(String logs, Float percent, Integer order) {
        if (percent != null) {
            this.processPercent = percent.floatValue();
        }
        StringBuffer log = new StringBuffer(logs);
        Message info = new Message(log, MessageTypeEnum.WARN, order);
        info.setKey(UUIDUtils.newUUIDStr());
        this.recordLog(info);
        return this;
    }

    public TaskLog writeWarnLog(String logs, Float percent) {
        if (percent != null) {
            this.processPercent = percent.floatValue();
        }
        StringBuffer log = new StringBuffer(logs);
        Message info = new Message(log, MessageTypeEnum.WARN);
        info.setKey(UUIDUtils.newUUIDStr());
        this.recordLog(info);
        return this;
    }

    public TaskLog writeErrorLog(String logs, Float percent) {
        if (percent != null) {
            this.processPercent = percent.floatValue();
        }
        StringBuffer log = new StringBuffer(logs);
        Message info = new Message(log, MessageTypeEnum.ERROR);
        info.setKey(UUIDUtils.newUUIDStr());
        this.recordLog(info);
        return this;
    }

    private void recordLog(Message message) {
        Integer order = message.getOrder();
        if (null == order) {
            message.setOrder(this.completeMessage.size());
        }
        Message copyMessage = new Message(message.getMessage(), message.getMsgType(), message.getOrder());
        this.completeMessage.add(copyMessage);
        this.generateLog(message);
        this.messages.add(message);
        this.syncTaskLog();
    }

    public void syncTaskLog() {
        if (null == this.progressData) {
            throw new BusinessRuntimeException("\u65e5\u5fd7\u8bb0\u5f55\u5b9e\u73b0\u4e3a\u7a7a");
        }
        Message.ProgressResult progressResult = new Message.ProgressResult();
        BeanUtils.copyProperties(this, progressResult);
        this.progressData.setResult(progressResult);
        this.progressData.setProgressValueAndRefresh(this.processPercent / 100.0f);
    }

    private void generateLog(Message message) {
        String title = GcI18nUtil.getMessage((String)("gc.calculate.onekeymerge.calcDone.message." + message.getMsgType().getCode()));
        String log = message.getMessage().toString();
        if (log.length() > 300) {
            log = log.substring(0, 300) + "...";
        }
        StringBuffer detail = new StringBuffer("[" + title + "]").append(log);
        message.setMessage(detail);
    }

    public String getCompleteJson() {
        return JsonUtils.writeValueAsString(this.completeMessage);
    }

    public List<Message> getMessages() {
        return this.messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public Message getWarn() {
        return this.warn;
    }

    public void setWarn(Message warn) {
        this.warn = warn;
    }

    public Message getInfo() {
        return this.info;
    }

    public void setInfo(Message info) {
        this.info = info;
    }

    public Message getError() {
        return this.error;
    }

    public void setError(Message error) {
        this.error = error;
    }

    public List<Message> getCompleteMessage() {
        return this.completeMessage;
    }

    public void setCompleteMessage(List<Message> completeMessage) {
        this.completeMessage = completeMessage;
    }

    public boolean isFinish() {
        return this.finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    public float getProcessPercent() {
        return this.processPercent;
    }

    public void setProcessPercent(Float processPercent) {
        this.processPercent = processPercent.floatValue();
    }

    public TaskStateEnum getState() {
        return this.state;
    }

    public void setState(TaskStateEnum state) {
        this.state = state;
    }

    public int getProcess() {
        return this.process;
    }

    public void setProcess(int process) {
        this.process = process;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getDoneNum() {
        return this.doneNum;
    }

    public void setDoneNum(Integer doneNum) {
        this.doneNum = doneNum;
    }

    public Integer getTotalNum() {
        return this.totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public void endTask() {
        this.processPercent = 100.0f;
        this.setFinish(true);
        this.syncTaskLog();
    }

    public String getStartTime() {
        return this.startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

