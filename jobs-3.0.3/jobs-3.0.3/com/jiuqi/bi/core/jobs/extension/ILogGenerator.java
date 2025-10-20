/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.core.jobs.extension;

import com.jiuqi.bi.core.jobs.extension.LogType;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public interface ILogGenerator {
    public String[] getLogCustomFields();

    public List<LogItem> getLastLogs(String var1, int var2, boolean var3) throws Exception;

    default public List<LogItem> getLastLogs(String instanceId, LogType logType, int count, boolean includeSubLog) throws Exception {
        return this.getLastLogs(instanceId, count, includeSubLog);
    }

    public List<LogItem> getLogsBefore(String var1, long var2, int var4, boolean var5) throws Exception;

    default public List<LogItem> getLogsBefore(String instanceId, LogType logType, long beforeTimeStamp, int count, boolean includeSubLog) throws Exception {
        return this.getLogsBefore(instanceId, beforeTimeStamp, count, includeSubLog);
    }

    public List<LogItem> getLastLogsAfter(String var1, long var2, boolean var4) throws Exception;

    default public List<LogItem> getLastLogsAfter(String instanceId, LogType logType, long afterTimeStamp, boolean includeSubLog) throws Exception {
        return this.getLastLogsAfter(instanceId, afterTimeStamp, includeSubLog);
    }

    public void iterateAllLogs(String var1, boolean var2, LogItemHandle var3) throws Exception;

    default public void iterateAllLogs(String instanceId, LogType logType, boolean includeSubLog, LogItemHandle handler) throws Exception {
        this.iterateAllLogs(instanceId, includeSubLog, handler);
    }

    public LogItemDetail getLogItemDetail(long var1);

    public static interface LogItemHandle {
        public void handleLogItem(LogItem var1, LogItemDetail var2) throws Exception;
    }

    public static class LogItemDetail {
        private String detail;

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public String getDetail() {
            return this.detail;
        }

        public void toJson(JSONObject json) throws JSONException {
            if (json == null) {
                throw new JSONException("JSON\u5bf9\u8c61\u4e0d\u80fd\u4e3a\u7a7a");
            }
            json.put("detail", (Object)this.detail);
        }
    }

    public static class LogItem
    implements Serializable {
        private static final long serialVersionUID = 1L;
        private long id;
        private long timestamp;
        private String level;
        private int logLevel;
        private String message;
        private String nodeName;
        private boolean hasDetail = false;
        private String[] customMessage;

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

        public String getLevel() {
            return this.level;
        }

        public void setLevel(String level) {
            if ("\u8c03\u8bd5".equals(level)) {
                this.logLevel = LogType.DEBUG.getValue();
            } else if ("\u4fe1\u606f".equals(level)) {
                this.logLevel = LogType.INFO.getValue();
            } else if ("\u8b66\u544a".equals(level)) {
                this.logLevel = LogType.WARN.getValue();
            } else if ("\u9519\u8bef".equals(level)) {
                this.logLevel = LogType.ERROR.getValue();
            }
            this.level = level;
        }

        public String getMessage() {
            return this.message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public boolean hasDetail() {
            return this.hasDetail;
        }

        public void setHasDetail(boolean hasDetail) {
            this.hasDetail = hasDetail;
        }

        public void setCustomMessage(String[] customMessage) {
            this.customMessage = customMessage;
        }

        public String[] getCustomMessage() {
            return this.customMessage;
        }

        public int getLogLevel() {
            return this.logLevel;
        }

        public void setLogLevel(int logLevel) {
            this.logLevel = logLevel;
        }

        public String getNodeName() {
            return this.nodeName;
        }

        public void setNodeName(String nodeName) {
            this.nodeName = nodeName;
        }

        public void toJson(JSONObject json) throws JSONException {
            if (json == null) {
                throw new JSONException("JSON\u5bf9\u8c61\u4e0d\u80fd\u4e3a\u7a7a");
            }
            String idStr = String.valueOf(this.id);
            json.put("id", (Object)idStr);
            json.put("timestamp", this.timestamp);
            SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm:ss.SSS");
            String dateTimeStr = sdf.format(new Date(this.timestamp));
            json.put("dateTime", (Object)dateTimeStr);
            json.put("level", (Object)this.level);
            json.put("logLevel", this.logLevel);
            json.put("message", (Object)this.message);
            json.put("hasDetail", this.hasDetail);
            json.put("nodeName", (Object)this.nodeName);
            JSONArray customMsgArr = new JSONArray();
            if (this.customMessage != null) {
                for (String msg : this.customMessage) {
                    customMsgArr.put((Object)msg);
                }
            }
            json.put("customMessage", (Object)customMsgArr);
        }
    }
}

