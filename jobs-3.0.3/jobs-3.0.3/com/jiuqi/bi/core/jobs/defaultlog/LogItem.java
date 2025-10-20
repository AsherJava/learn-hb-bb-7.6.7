/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.jobs.defaultlog;

import com.jiuqi.bi.core.jobs.extension.ILogGenerator;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

class LogItem
implements Serializable {
    private static final long serialVersionUID = 1L;
    private long id;
    private long timestamp;
    private int level;
    private String message;
    private boolean hasDetail;
    private String nodeName;
    private String instanceId;
    private String detail;

    LogItem() {
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isHasDetail() {
        return this.hasDetail;
    }

    public void setHasDetail(boolean hasDetail) {
        this.hasDetail = hasDetail;
    }

    public String getInstanceId() {
        return this.instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getDetail() {
        return this.detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getNodeName() {
        return this.nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public ILogGenerator.LogItemDetail getLogDetail() {
        ILogGenerator.LogItemDetail logItemDetail = new ILogGenerator.LogItemDetail();
        logItemDetail.setDetail(this.detail);
        return logItemDetail;
    }

    public ILogGenerator.LogItem getLogItem() {
        return LogItem.toJobLogItem(this);
    }

    public static ILogGenerator.LogItem toJobLogItem(LogItem logItem) {
        ILogGenerator.LogItem curItem = new ILogGenerator.LogItem();
        curItem.setId(logItem.getId());
        curItem.setTimestamp(logItem.getTimestamp());
        curItem.setNodeName(logItem.getNodeName());
        String levelStr = "";
        switch (logItem.getLevel()) {
            case 0: {
                levelStr = "\u8ddf\u8e2a";
                break;
            }
            case 10: {
                levelStr = "\u8c03\u8bd5";
                break;
            }
            case 30: {
                levelStr = "\u8b66\u544a";
                break;
            }
            case 40: {
                levelStr = "\u9519\u8bef";
                break;
            }
            default: {
                levelStr = "\u4fe1\u606f";
            }
        }
        curItem.setLevel(levelStr);
        curItem.setLogLevel(logItem.getLevel());
        curItem.setMessage(logItem.getMessage());
        curItem.setHasDetail(logItem.isHasDetail());
        return curItem;
    }

    public static List<ILogGenerator.LogItem> toJobLogItems(List<LogItem> logItems) {
        ArrayList<ILogGenerator.LogItem> resultLogItems = new ArrayList<ILogGenerator.LogItem>();
        if (logItems != null && !logItems.isEmpty()) {
            for (LogItem logItem : logItems) {
                resultLogItems.add(LogItem.toJobLogItem(logItem));
            }
        }
        return resultLogItems;
    }
}

