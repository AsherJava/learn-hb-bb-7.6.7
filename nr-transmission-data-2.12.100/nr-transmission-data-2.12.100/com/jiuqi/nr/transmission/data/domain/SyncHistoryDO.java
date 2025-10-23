/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.jiuqi.nr.period.util.JacksonUtils
 */
package com.jiuqi.nr.transmission.data.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jiuqi.nr.period.util.JacksonUtils;
import com.jiuqi.nr.transmission.data.domain.SyncSchemeParamDO;
import com.jiuqi.nr.transmission.data.intf.DataImportResult;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SyncHistoryDO {
    private static final Logger log = LoggerFactory.getLogger(SyncHistoryDO.class);
    private String key;
    private String schemeKey;
    private int status;
    private String detail;
    private SyncSchemeParamDO syncSchemeParamDO;
    private int syncTimes;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date startTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date endTime;
    private String fileKey;
    private String userId;
    private String finishEntity;
    private int type;
    private String instanceId;
    private DataImportResult result;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSchemeKey() {
        return this.schemeKey;
    }

    public void setSchemeKey(String schemeKey) {
        this.schemeKey = schemeKey;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDetail() {
        return this.detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getSyncTimes() {
        return this.syncTimes;
    }

    public void setSyncTimes(int syncTimes) {
        this.syncTimes = syncTimes;
    }

    public Date getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return this.endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public SyncSchemeParamDO getSyncSchemeParamDO() {
        return this.syncSchemeParamDO;
    }

    public void setSyncSchemeParamDO(SyncSchemeParamDO syncSchemeParamDO) {
        this.syncSchemeParamDO = syncSchemeParamDO;
    }

    public String getFileKey() {
        return this.fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFinishEntity() {
        return this.finishEntity;
    }

    public void setFinishEntity(String finishEntity) {
        this.finishEntity = finishEntity;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getInstanceId() {
        return this.instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public DataImportResult getResult() {
        return this.result;
    }

    public void setResult(DataImportResult result) {
        this.result = result;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("TH_KEY", this.getKey());
        map.put("TH_SCHEME_KEY", this.getSchemeKey());
        map.put("TH_STATUS", this.getStatus());
        map.put("TH_DETAIL", this.getDetail());
        map.put("TH_PARAM", JacksonUtils.objectToJson((Object)this.getSyncSchemeParamDO()));
        map.put("TH_START_TIME", this.getStartTime());
        map.put("TH_END_TIME", this.getEndTime());
        map.put("TH_FILE_KEY", this.getFileKey());
        map.put("TH_USER_ID", this.getUserId());
        map.put("TH_FINISH_ENTITY", this.getFinishEntity());
        map.put("TH_TYPE", this.getType());
        map.put("TH_INSTANCE_ID", this.getInstanceId());
        map.put("TH_RESULT", JacksonUtils.objectToJson((Object)this.getResult()));
        return map;
    }

    public String getStringStatus() {
        String stringStatus = "";
        switch (this.status) {
            case 1: {
                return "\u6ca1\u6709\u6267\u884c\u540c\u6b65";
            }
            case 2: {
                return "\u540c\u6b65\u4e2d";
            }
            case 3: {
                return "\u540c\u6b65\u6210\u529f";
            }
            case 4: {
                return "\u540c\u6b65\u5931\u8d25";
            }
            case 5: {
                return "\u7b49\u5f85\u88c5\u5165";
            }
            case 6: {
                return "\u90e8\u5206\u6210\u529f";
            }
        }
        return "\u7ed3\u679c\u672a\u77e5";
    }
}

