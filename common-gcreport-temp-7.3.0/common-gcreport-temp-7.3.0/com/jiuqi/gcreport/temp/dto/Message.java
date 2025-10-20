/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.enums.TaskStateEnum
 */
package com.jiuqi.gcreport.temp.dto;

import com.jiuqi.gcreport.common.enums.TaskStateEnum;
import com.jiuqi.gcreport.temp.dto.MessageTypeEnum;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Message {
    private String key;
    private StringBuffer message;
    private MessageTypeEnum msgType;
    private Integer order;

    public Message(StringBuffer message, MessageTypeEnum msgType, Integer order) {
        this.message = message;
        this.msgType = msgType;
        this.order = order;
    }

    public Message(StringBuffer message, MessageTypeEnum msgType) {
        this.message = message;
        this.msgType = msgType;
    }

    public Message(MessageTypeEnum msgType) {
        this.msgType = msgType;
    }

    public Message() {
    }

    public Integer getOrder() {
        return this.order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public StringBuffer getMessage() {
        return this.message;
    }

    public void setMessage(StringBuffer message) {
        this.message = message;
    }

    public MessageTypeEnum getMsgType() {
        return this.msgType;
    }

    public void setMsgType(MessageTypeEnum msgType) {
        this.msgType = msgType;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String toString() {
        return "Message{key='" + this.key + '\'' + ", message=" + this.message + ", msgType=" + (Object)((Object)this.msgType) + ", order=" + this.order + '}';
    }

    public static class ProgressResult {
        private List<Message> messages = Collections.synchronizedList(new ArrayList());
        private Integer doneNum = 0;
        private Integer totalNum = 0;
        private boolean finish = false;
        private TaskStateEnum state = TaskStateEnum.WAITTING;
        private float processPercent = 0.0f;
        private int process = 0;

        public List<Message> getMessages() {
            return this.messages;
        }

        public void setMessages(List<Message> messages) {
            this.messages = messages;
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

        public boolean isFinish() {
            return this.finish;
        }

        public void setFinish(boolean finish) {
            this.finish = finish;
        }

        public TaskStateEnum getState() {
            return this.state;
        }

        public void setState(TaskStateEnum state) {
            this.state = state;
        }

        public float getProcessPercent() {
            return this.processPercent;
        }

        public void setProcessPercent(float processPercent) {
            this.processPercent = processPercent;
        }

        public int getProcess() {
            return this.process;
        }

        public void setProcess(int process) {
            this.process = process;
        }
    }
}

