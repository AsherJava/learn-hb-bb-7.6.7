/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.de.dataflow.sendmsg.thread;

import com.jiuqi.nr.bpm.de.dataflow.sendmsg.MessageEventListener;
import com.jiuqi.nr.bpm.de.dataflow.sendmsg.SendMessageTaskConfig;
import com.jiuqi.nr.bpm.de.dataflow.sendmsg.event.BatchMessageEvent;
import com.jiuqi.nr.bpm.exception.TodoMsgException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BatchMessageManager {
    private final Logger logger = LoggerFactory.getLogger(BatchMessageManager.class);
    private int upperLimitValue;
    private int sumTotal;
    private List<BatchMessageEvent> messageObjs = new ArrayList<BatchMessageEvent>();
    private MessageEventListener messageEventListener;
    private int count;

    public void setUpperLimitValue(int num) {
        this.upperLimitValue = num;
    }

    public void setMessageEventListener(MessageEventListener messageEventListener) {
        this.messageEventListener = messageEventListener;
    }

    public void setSumTotal(int sumTotal) {
        this.sumTotal = sumTotal;
    }

    public void addMessageObj(BatchMessageEvent messageBody) {
        if (messageBody != null) {
            this.messageObjs.add(messageBody);
        }
        this.check();
    }

    public void check() {
        if (this.messageObjs != null && this.messageObjs.size() == this.upperLimitValue) {
            this.sendTodo(this.messageObjs);
            this.logger.info("BatchMessageManager >>>> \u53d1\u9001\u4ee3\u529e\uff0c\u7b2c" + ++this.count + "\u6b21\u53d1\u9001\uff0c\u5bb9\u91cf\u5927\u5c0f\uff1a" + this.messageObjs.size() + "\u6761");
            this.messageObjs.clear();
        }
    }

    public void clear() {
        if (this.messageObjs != null && !this.messageObjs.isEmpty()) {
            this.sendTodo(this.messageObjs);
            this.logger.info("BatchMessageManager >>>> \u53d1\u9001\u4ee3\u529e\uff0c\u7b2c" + ++this.count + "\u6b21\uff0c\u6700\u540e\u4e00\u6b21\u53d1\u9001\uff0c\u5bb9\u91cf\u5927\u5c0f\uff1a" + this.messageObjs.size() + "\u6761");
            this.messageObjs.clear();
        }
    }

    private void sendTodo(List<BatchMessageEvent> subList) {
        this.logger.error("sendTodo\u65b9\u6cd5\uff0csubList\u7684\u5bb9\u91cf" + subList.size());
        if (SendMessageTaskConfig.canSendMessage()) {
            try {
                if (this.messageEventListener == null) {
                    this.logger.error("messageEventListener\u5bf9\u8c61\u4e3a\u7a7a");
                }
                this.messageEventListener.sendBatchMessage(subList);
            }
            catch (TodoMsgException e) {
                this.logger.error("\u5f85\u529e\u53d1\u9001\u5f02\u5e38");
            }
        }
    }
}

