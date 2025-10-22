/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.jiuqi.bi.util.StringUtils
 */
package nr.single.map.data.internal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jiuqi.bi.util.StringUtils;
import java.util.Date;
import nr.single.map.data.facade.SingleFileTaskInfo;

public class SingleFileTaskInfoImpl
implements SingleFileTaskInfo {
    private static final long serialVersionUID = -6318464532745335947L;
    private String singleTaskFlag;
    private String singleTaskTitle;
    private String singleFileFlag;
    private String singleTaskYear;
    private String singleTaskTime;
    private String singleTaskPeriod;
    private String singleFloatOrderField;
    private String netTaskKey;
    private String netTaskFlag;
    private String netTaskTitle;
    private String netFormSchemeKey;
    private String netFormSchemeFlag;
    private String netFormSchemeTitle;
    private Date updateTime;
    private String paraVersion;
    private String uploadFileName;

    @Override
    public String getSingleTaskFlag() {
        return this.singleTaskFlag;
    }

    @Override
    public void setSingleTaskFlag(String taskFlag) {
        this.singleTaskFlag = taskFlag;
    }

    @Override
    public String getSingleTaskTitle() {
        return this.singleTaskTitle;
    }

    @Override
    public void setSingleTaskTitle(String taskTitle) {
        this.singleTaskTitle = taskTitle;
    }

    @Override
    public String getSingleFileFlag() {
        return this.singleFileFlag;
    }

    @Override
    public void setSingleFileFlag(String fileFlag) {
        this.singleFileFlag = fileFlag;
    }

    @Override
    public String getSingleTaskYear() {
        return this.singleTaskYear;
    }

    @Override
    public void setSingleTaskYear(String taskYear) {
        this.singleTaskYear = taskYear;
    }

    @Override
    @JsonProperty(value="singleFloatOrderField")
    public String getSingleFloatOrderFiled() {
        return this.singleFloatOrderField;
    }

    @Override
    @JsonProperty(value="singleFloatOrderField")
    public void setSingleFloatOrderField(String fieldName) {
        this.singleFloatOrderField = fieldName;
    }

    @Override
    public String getNetTaskKey() {
        return this.netTaskKey;
    }

    @Override
    public void setNetTaskKey(String taskKey) {
        this.netTaskKey = taskKey;
    }

    @Override
    public String getNetTaskFlag() {
        return this.netTaskFlag;
    }

    @Override
    public void setNetTaskFlag(String taskFlag) {
        this.netTaskFlag = taskFlag;
    }

    @Override
    public String getNetTaskTitle() {
        return this.netTaskTitle;
    }

    @Override
    public void setNetTaskTitle(String taskTitle) {
        this.netTaskTitle = taskTitle;
    }

    @Override
    public String getNetFormSchemeKey() {
        return this.netFormSchemeKey;
    }

    @Override
    public void setNetFormSchemeKey(String formSchemeKey) {
        this.netFormSchemeKey = formSchemeKey;
    }

    @Override
    public String getNetFormSchemeFlag() {
        return this.netFormSchemeFlag;
    }

    @Override
    public void setNetFormSchemeFlag(String formSchemeFlag) {
        this.netFormSchemeFlag = formSchemeFlag;
    }

    @Override
    public String getNetFormSchemeTitle() {
        return this.netFormSchemeTitle;
    }

    @Override
    public void setNetFormSchemeTitle(String formSchemeTitle) {
        this.netFormSchemeTitle = formSchemeTitle;
    }

    @Override
    public String getSingleTaskPeriod() {
        return this.singleTaskPeriod;
    }

    @Override
    public void setSingleTaskPeriod(String taskPeroid) {
        this.singleTaskPeriod = taskPeroid;
    }

    @Override
    public String getSingleTaskTime() {
        if (StringUtils.isEmpty((String)this.singleTaskTime)) {
            this.singleTaskTime = "0";
        }
        return this.singleTaskTime;
    }

    @Override
    public void setSingleTaskTime(String taskTime) {
        this.singleTaskTime = taskTime;
    }

    @Override
    public Date getUpdateTime() {
        return this.updateTime;
    }

    @Override
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public void copyFrom(SingleFileTaskInfo info) {
        this.singleTaskFlag = info.getSingleTaskFlag();
        this.singleTaskTitle = info.getSingleTaskTitle();
        this.singleFileFlag = info.getSingleFileFlag();
        this.singleTaskYear = info.getSingleTaskYear();
        this.singleTaskTime = info.getSingleTaskTime();
        this.singleTaskPeriod = info.getSingleTaskPeriod();
        this.netTaskKey = info.getNetTaskKey();
        this.netTaskFlag = info.getNetTaskFlag();
        this.netTaskTitle = info.getNetTaskTitle();
        this.netFormSchemeKey = info.getNetFormSchemeKey();
        this.netFormSchemeFlag = info.getNetFormSchemeFlag();
        this.netFormSchemeTitle = info.getNetFormSchemeTitle();
        this.updateTime = info.getUpdateTime();
        this.paraVersion = info.getParaVersion();
        this.uploadFileName = info.getUploadFileName();
        this.singleFloatOrderField = info.getSingleFloatOrderFiled();
    }

    @Override
    public String getParaVersion() {
        return this.paraVersion;
    }

    @Override
    public void setParaVersion(String paraVersion) {
        this.paraVersion = paraVersion;
    }

    @Override
    public String getUploadFileName() {
        return this.uploadFileName;
    }

    @Override
    public void setUploadFileName(String fileName) {
        this.uploadFileName = fileName;
    }
}

