/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme
 *  com.jiuqi.nr.context.infc.impl.NRContext
 */
package com.jiuqi.nr.batch.gather.gzw.service.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme;
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
    private SummaryScheme scheme;
    private String fileLocation;
    private String fileType;
    private boolean isMergeSplit;
    private List<String> splitMark;
    private String filePath;
    private transient Map<String, Object> variableMap;
    private String fileKey;
    private String fileKeyOfSOss;
    private String fileNameInfo;

    public String getFilePath() {
        return this.filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public static long getSerialVersionUID() {
        return 1L;
    }

    public SummaryScheme getScheme() {
        return this.scheme;
    }

    public void setScheme(SummaryScheme scheme) {
        this.scheme = scheme;
    }

    public String getFileLocation() {
        return this.fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public String getFileType() {
        return this.fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public boolean isMergeSplit() {
        return this.isMergeSplit;
    }

    public void setMergeSplit(boolean mergeSplit) {
        this.isMergeSplit = mergeSplit;
    }

    public List<String> getSplitMark() {
        return this.splitMark;
    }

    public void setSplitMark(List<String> splitMark) {
        this.splitMark = splitMark;
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
}

