/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.core.jobs.model;

import com.jiuqi.bi.core.jobs.certification.CertificationInfo;
import com.jiuqi.bi.core.jobs.model.AbstractScheduleMethod;
import com.jiuqi.bi.core.jobs.model.JobParameter;
import com.jiuqi.bi.core.jobs.model.schedulemethod.NoneScheduleMethod;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JobModel
implements Cloneable {
    private String guid;
    private String title;
    private String category;
    private String desc;
    private boolean enable = true;
    private long startTime;
    private long endTime;
    private String folderGuid;
    private long lastModifyTime;
    private boolean concurrency;
    private List<JobParameter> parameters = new ArrayList<JobParameter>();
    private AbstractScheduleMethod scheduleMethod = new NoneScheduleMethod();
    private String extendedConfig;
    private String user;
    private CertificationInfo certification;

    public void setScheduleMethod(AbstractScheduleMethod scheduleMethod) {
        this.scheduleMethod = scheduleMethod;
    }

    public AbstractScheduleMethod getScheduleMethod() {
        return this.scheduleMethod;
    }

    public String getGuid() {
        return this.guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public long getStartTime() {
        return this.startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return this.endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getFolderGuid() {
        return this.folderGuid;
    }

    public void setFolderGuid(String folderGuid) {
        this.folderGuid = folderGuid;
    }

    public long getLastModifyTime() {
        return this.lastModifyTime;
    }

    public void setLastModifyTime(long lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    public List<JobParameter> getParameters() {
        return this.parameters;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public boolean isEnable() {
        return this.enable;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUser() {
        return this.user;
    }

    public void setCertification(CertificationInfo certification) {
        this.certification = certification;
    }

    public CertificationInfo getCertification() {
        return this.certification;
    }

    public void setExtendedConfig(String extendedConfig) {
        this.extendedConfig = extendedConfig;
    }

    public String getExtendedConfig() {
        return this.extendedConfig;
    }

    public boolean isConcurrency() {
        return this.concurrency;
    }

    public void setConcurrency(boolean concurrency) {
        this.concurrency = concurrency;
    }

    public JobModel clone() {
        try {
            JobModel cloned = (JobModel)super.clone();
            cloned.parameters = new ArrayList<JobParameter>();
            for (JobParameter jp : this.parameters) {
                cloned.parameters.add(jp.clone());
            }
            cloned.scheduleMethod = this.scheduleMethod.clone();
            return cloned;
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public void toJson(JSONObject json) throws JSONException {
        if (json == null) {
            json = new JSONObject();
        }
        json.put("guid", (Object)this.guid);
        json.put("title", (Object)this.title);
        json.put("category", (Object)this.category);
        json.put("desc", (Object)this.desc);
        json.put("enable", this.enable);
        json.put("startTime", this.startTime);
        json.put("endTime", this.endTime);
        json.put("folderGuid", (Object)this.folderGuid);
        json.put("lastModifyTime", this.lastModifyTime);
        json.put("concurrency", this.concurrency);
        json.putOpt("user", (Object)this.user);
        if (this.certification != null) {
            json.putOpt("certification", (Object)this.certification.toJson());
        }
        if (this.extendedConfig != null && this.extendedConfig.length() > 0) {
            try {
                JSONObject configJson = new JSONObject(this.extendedConfig);
                json.put("configtype", (Object)"json");
                json.put("config", (Object)configJson);
            }
            catch (JSONException e) {
                json.put("configtype", (Object)"string");
                json.put("config", (Object)this.extendedConfig);
            }
        }
        JSONArray params = new JSONArray();
        for (JobParameter parameter : this.parameters) {
            params.put((Object)parameter.toJson());
        }
        json.put("parameters", (Object)params);
        JSONObject scheduleJson = new JSONObject();
        this.scheduleMethod.toJson(scheduleJson);
        json.put("scheduleMethod", (Object)scheduleJson);
    }
}

