/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.ArrayKey
 *  com.jiuqi.gcreport.rewritesetting.vo.ReWriteSubject
 */
package com.jiuqi.gcreport.calculate.vo;

import com.jiuqi.bi.util.ArrayKey;
import com.jiuqi.gcreport.rewritesetting.vo.ReWriteSubject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DebugZbInfoVO {
    private ArrayKey debugZb;
    private List<String> logList;
    private Map<String, List<? extends Object>> logDetailMap;
    private ReWriteSubject reWriteSubject;

    public DebugZbInfoVO(ArrayKey zbCodeArrayKey) {
        this.debugZb = zbCodeArrayKey;
        this.logList = new ArrayList<String>();
    }

    public ArrayKey getDebugZb() {
        return this.debugZb;
    }

    public void setDebugZb(ArrayKey debugZb) {
        this.debugZb = debugZb;
    }

    public List<String> getLogList() {
        return this.logList;
    }

    public void writeMessage(String message) {
        this.logList.add(message);
    }

    public void writeMessage(List<String> messageList) {
        this.logList.addAll(messageList);
    }

    public void setLogList(List<String> logList) {
        this.logList = logList;
    }

    public ReWriteSubject getReWriteSubject() {
        return this.reWriteSubject;
    }

    public void setReWriteSubject(ReWriteSubject reWriteSubject) {
        this.reWriteSubject = reWriteSubject;
    }

    public String calcTableName() {
        if (null != this.debugZb) {
            return (String)this.debugZb.get(0);
        }
        return null;
    }

    public String calcZbCode() {
        if (null != this.debugZb) {
            return (String)this.debugZb.get(1);
        }
        return null;
    }

    public void logDetail(String key, List<? extends Object> entities) {
        if (null == this.logDetailMap) {
            this.logDetailMap = new HashMap<String, List<? extends Object>>();
        }
        this.logDetailMap.put(key, entities);
    }

    public Map<String, List<? extends Object>> getLogDetailMap() {
        return this.logDetailMap;
    }

    public void setLogDetailMap(Map<String, List<? extends Object>> logDetailMap) {
        this.logDetailMap = logDetailMap;
    }
}

