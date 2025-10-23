/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.nr.fielddatacrud.ImpMode
 */
package com.jiuqi.nr.transmission.data.intf;

import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.nr.fielddatacrud.ImpMode;
import com.jiuqi.nr.transmission.data.intf.UserInfoParam;
import java.util.List;

public class ImportParam {
    private int importType;
    private String executeKey;
    private String fileKey;
    private int doUpload;
    private int allowForceUpload;
    private String description;
    private String mappingSchemeKey;
    private UserInfoParam userInfoParam;
    private String taskKey;
    private String formSchemeKey;
    private List<Variable> variables;
    private boolean notImportFMDMEntity;
    private ImpMode mode = ImpMode.FULL;

    public int getImportType() {
        return this.importType;
    }

    public void setImportType(int importType) {
        this.importType = importType;
    }

    public String getExecuteKey() {
        return this.executeKey;
    }

    public void setExecuteKey(String executeKey) {
        this.executeKey = executeKey;
    }

    public String getFileKey() {
        return this.fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    public int getDoUpload() {
        return this.doUpload;
    }

    public void setDoUpload(int doUpload) {
        this.doUpload = doUpload;
    }

    public int getAllowForceUpload() {
        return this.allowForceUpload;
    }

    public void setAllowForceUpload(int allowForceUpload) {
        this.allowForceUpload = allowForceUpload;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMappingSchemeKey() {
        return this.mappingSchemeKey;
    }

    public void setMappingSchemeKey(String mappingSchemeKey) {
        this.mappingSchemeKey = mappingSchemeKey;
    }

    public UserInfoParam getUserInfoParam() {
        return this.userInfoParam;
    }

    public void setUserInfoParam(UserInfoParam userInfoParam) {
        this.userInfoParam = userInfoParam;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public List<Variable> getVariables() {
        return this.variables;
    }

    public void setVariables(List<Variable> variables) {
        this.variables = variables;
    }

    public boolean isNotImportFMDMEntity() {
        return this.notImportFMDMEntity;
    }

    public boolean getNotImportFMDMEntity() {
        return this.notImportFMDMEntity;
    }

    public void setNotImportFMDMEntity(boolean notImportFMDMEntity) {
        this.notImportFMDMEntity = notImportFMDMEntity;
    }

    public ImpMode getMode() {
        return this.mode;
    }

    public void setMode(ImpMode mode) {
        this.mode = mode;
    }
}

