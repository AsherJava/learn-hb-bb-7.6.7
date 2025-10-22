/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.context.infc.impl.NRContext
 */
package com.jiuqi.nr.dataentry.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.context.infc.impl.NRContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.util.CollectionUtils;

@JsonIgnoreProperties(ignoreUnknown=true)
public class UploadParam
extends NRContext
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String formSchemeKey;
    private String formulaSchemeKey;
    private Map<String, DimensionValue> dimensionSet;
    private String taskKey;
    private String configKey;
    private String fileLocation;
    private String fileType;
    private boolean isMergeSplit;
    private List<String> splitMark;
    private Map<String, Object> variableMap;
    private String fileKey;
    private String fileKeyOfSOss;
    private String fileNameInfo;
    private String autoCacl;
    private boolean isAppending = false;
    private boolean deleteData = false;
    private String filePath;
    private Boolean deleteFilePath = true;

    public List<Variable> getVariables() {
        if (!CollectionUtils.isEmpty(this.variableMap)) {
            ArrayList<Variable> variables = new ArrayList<Variable>();
            for (String variableName : this.variableMap.keySet()) {
                Object variableValue = this.variableMap.get(variableName);
                Variable variable = new Variable(variableName, 6);
                variable.setVarValue(variableValue);
                variables.add(variable);
            }
            return variables;
        }
        return Collections.emptyList();
    }

    public Boolean getDeleteFilePath() {
        return this.deleteFilePath;
    }

    public void setDeleteFilePath(Boolean deleteFilePath) {
        this.deleteFilePath = deleteFilePath;
    }

    public boolean isAppending() {
        return this.isAppending;
    }

    public void setIsAppending(boolean isAppending) {
        this.isAppending = isAppending;
    }

    public List<String> getSplitMark() {
        return this.splitMark;
    }

    public void setSplitMark(List<String> splitMark) {
        this.splitMark = splitMark;
    }

    public boolean isMergeSplit() {
        return this.isMergeSplit;
    }

    public void setIsMergeSplit(boolean isMergeSplit) {
        this.isMergeSplit = isMergeSplit;
    }

    public String getFileType() {
        return this.fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public Map<String, DimensionValue> getDimensionSet() {
        return this.dimensionSet;
    }

    public void setDimensionSet(Map<String, DimensionValue> dimensionSet) {
        this.dimensionSet = dimensionSet;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getFormulaSchemeKey() {
        return this.formulaSchemeKey;
    }

    public void setFormulaSchemeKey(String formulaSchemeKey) {
        this.formulaSchemeKey = formulaSchemeKey;
    }

    public String getConfigKey() {
        return this.configKey;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }

    public String getFileLocation() {
        return this.fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public Map<String, Object> getVariableMap() {
        return this.variableMap;
    }

    public void setVariableMap(Map<String, Object> variableMap) {
        this.variableMap = variableMap;
    }

    public String getFileKey() {
        return this.fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    public String getFileKeyOfSOss() {
        return this.fileKeyOfSOss;
    }

    public void setFileKeyOfSOss(String fileKeyOfSOss) {
        this.fileKeyOfSOss = fileKeyOfSOss;
    }

    public String getFileNameInfo() {
        return this.fileNameInfo;
    }

    public void setFileNameInfo(String fileNameInfo) {
        this.fileNameInfo = fileNameInfo;
    }

    public boolean isDeleteData() {
        return this.deleteData;
    }

    public void setDeleteData(boolean deleteData) {
        this.deleteData = deleteData;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getAutoCacl() {
        return this.autoCacl;
    }

    public void setAutoCacl(String autoCacl) {
        this.autoCacl = autoCacl;
    }
}

