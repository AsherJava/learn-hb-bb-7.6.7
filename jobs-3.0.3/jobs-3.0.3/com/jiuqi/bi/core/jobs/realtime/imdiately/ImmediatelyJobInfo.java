/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package com.jiuqi.bi.core.jobs.realtime.imdiately;

import com.jiuqi.bi.core.jobs.monitor.State;
import java.io.Serializable;
import org.json.JSONObject;

public class ImmediatelyJobInfo
implements Serializable {
    private String instanceId;
    private String type;
    private String nodeName;
    private String prompt;
    private int progress;
    private int state = State.WAITING.getValue();
    private int result = 1;
    private String resultMessage;

    public String getInstanceId() {
        return this.instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getProgress() {
        return this.progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getPrompt() {
        return this.prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getResult() {
        return this.result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getResultMessage() {
        return this.resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public String getNodeName() {
        return this.nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.putOpt("instanceId", (Object)this.instanceId);
        json.putOpt("type", (Object)this.type);
        json.putOpt("progress", (Object)this.progress);
        json.putOpt("prompt", (Object)this.prompt);
        json.putOpt("state", (Object)this.state);
        json.putOpt("nodeName", (Object)this.nodeName);
        json.putOpt("result", (Object)this.result);
        json.putOpt("resultMessage", (Object)this.resultMessage);
        return json;
    }

    public ImmediatelyJobInfo fromJson(JSONObject json) {
        this.instanceId = json.optString("instanceId");
        this.type = json.optString("type");
        this.progress = json.optInt("progress");
        this.prompt = json.optString("prompt");
        this.state = json.optInt("state");
        this.nodeName = json.optString("nodeName");
        this.result = json.optInt("result");
        this.resultMessage = json.optString("resultMessage");
        return this;
    }
}

