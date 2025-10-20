/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  org.json.JSONArray
 *  org.json.JSONObject
 */
package com.jiuqi.bi.core.jobs.bean;

import com.jiuqi.bi.core.jobs.oss.AbstractJobResult;
import com.jiuqi.bi.util.StringUtils;
import java.io.Serializable;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class JobExecResultBean
implements Serializable {
    private static final long serialVersionUID = 3357786011765930961L;
    private String guid;
    private String instanceId;
    private String bucket;
    private String fileName;
    private String extName;
    private String userGuid;
    private long byteSize;
    private long expireTime;
    private String description;
    private boolean exist = true;

    public JobExecResultBean() {
    }

    public JobExecResultBean(String guid, String instanceId, String bucketId, AbstractJobResult jobResult) {
        this.guid = guid;
        this.instanceId = instanceId;
        this.bucket = bucketId;
        this.fileName = jobResult.getName();
        this.extName = jobResult.getExtName();
        this.userGuid = jobResult.getUserGuid();
        this.description = jobResult.getDesc();
    }

    public String getGuid() {
        return this.guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getInstanceId() {
        return this.instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getBucket() {
        return this.bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getFileName() {
        return this.fileName;
    }

    public String getFileFullName() {
        String fullName = this.fileName;
        if (StringUtils.isNotEmpty((String)this.extName)) {
            fullName = fullName + "." + this.extName;
        }
        return fullName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getExtName() {
        return this.extName;
    }

    public void setExtName(String extName) {
        this.extName = extName;
    }

    public String getUserGuid() {
        return this.userGuid;
    }

    public void setUserGuid(String userGuid) {
        this.userGuid = userGuid;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getByteSize() {
        return this.byteSize;
    }

    public void setByteSize(long byteSize) {
        this.byteSize = byteSize;
    }

    public long getExpireTime() {
        return this.expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    public boolean isExist() {
        return this.exist;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("guid", (Object)this.guid);
        json.put("instanceId", (Object)this.instanceId);
        json.put("bucket", (Object)this.bucket);
        json.put("fileName", (Object)this.fileName);
        json.put("fileFullName", (Object)this.getFileFullName());
        json.put("extName", (Object)this.extName);
        json.put("userGuid", (Object)this.userGuid);
        json.put("byteSize", this.byteSize);
        json.put("expireTime", this.expireTime);
        json.put("description", (Object)this.description);
        json.put("exist", this.exist);
        return json;
    }

    public static JSONArray list2Json(List<JobExecResultBean> resultBeans) {
        JSONArray resultJson = new JSONArray();
        if (resultBeans == null || resultBeans.isEmpty()) {
            return resultJson;
        }
        for (JobExecResultBean resultBean : resultBeans) {
            resultJson.put((Object)resultBean.toJson());
        }
        return resultJson;
    }
}

