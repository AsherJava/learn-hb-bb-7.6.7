/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.nr.common.params.DimensionValue
 */
package com.jiuqi.nr.io.asynctask.bean;

import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.io.params.base.CSVRange;
import com.jiuqi.nr.io.params.input.ExpViewFields;
import com.jiuqi.nr.io.params.input.OptTypes;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class RegionDataSetContext
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String taskKey;
    private String formSchemeKey;
    private String formKey;
    private String regionKey;
    private Map<String, DimensionValue> dimensionSetMap;
    private Map<String, DimensionValue> dimensionSetRangeMap;
    private OptTypes optType = OptTypes.FORM;
    private String fileType = ".txt";
    private String split = ",";
    private boolean isAttachment = false;
    private String attachmentArea = "default";
    private ExpViewFields expEntryFields = ExpViewFields.CODE;
    private ExpViewFields expEnumFields = ExpViewFields.CODE;
    private int floatImpOpt = 2;
    private String pwd;
    private boolean exportBizkeyorder = false;
    private boolean importBizkeyorder = false;
    private boolean validEntityExist = false;
    private boolean returnBizKeyValue;
    private Integer dataLineIndex = null;
    private boolean multistageEliminateBizKey = false;
    private List<Variable> variables = null;
    private CSVRange csvRange;
    private boolean newFileGroup = false;
    private String measure;
    private String decimal;
    private int checkType;
    private List<String> sortFields;
    private boolean isOrdered = true;

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

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getRegionKey() {
        return this.regionKey;
    }

    public void setRegionKey(String regionKey) {
        this.regionKey = regionKey;
    }

    public Map<String, DimensionValue> getDimensionSetMap() {
        return this.dimensionSetMap;
    }

    public void setDimensionSetMap(Map<String, DimensionValue> dimensionSetMap) {
        this.dimensionSetMap = dimensionSetMap;
    }

    public Map<String, DimensionValue> getDimensionSetRangeMap() {
        return this.dimensionSetRangeMap;
    }

    public void setDimensionSetRangeMap(Map<String, DimensionValue> dimensionSetRangeMap) {
        this.dimensionSetRangeMap = dimensionSetRangeMap;
    }

    public OptTypes getOptType() {
        return this.optType;
    }

    public void setOptType(OptTypes optType) {
        this.optType = optType;
    }

    public String getFileType() {
        return this.fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getSplit() {
        return this.split;
    }

    public void setSplit(String split) {
        this.split = split;
    }

    public boolean isAttachment() {
        return this.isAttachment;
    }

    public void setAttachment(boolean attachment) {
        this.isAttachment = attachment;
    }

    public String getAttachmentArea() {
        return this.attachmentArea;
    }

    public void setAttachmentArea(String attachmentArea) {
        this.attachmentArea = attachmentArea;
    }

    public ExpViewFields getExpEntryFields() {
        return this.expEntryFields;
    }

    public void setExpEntryFields(ExpViewFields expEntryFields) {
        this.expEntryFields = expEntryFields;
    }

    public ExpViewFields getExpEnumFields() {
        return this.expEnumFields;
    }

    public void setExpEnumFields(ExpViewFields expEnumFields) {
        this.expEnumFields = expEnumFields;
    }

    public int getFloatImpOpt() {
        return this.floatImpOpt;
    }

    public void setFloatImpOpt(int floatImpOpt) {
        this.floatImpOpt = floatImpOpt;
    }

    public String getPwd() {
        return this.pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public boolean isExportBizkeyorder() {
        return this.exportBizkeyorder;
    }

    public void setExportBizkeyorder(boolean exportBizkeyorder) {
        this.exportBizkeyorder = exportBizkeyorder;
    }

    public boolean isImportBizkeyorder() {
        return this.importBizkeyorder;
    }

    public void setImportBizkeyorder(boolean importBizkeyorder) {
        this.importBizkeyorder = importBizkeyorder;
    }

    public boolean isValidEntityExist() {
        return this.validEntityExist;
    }

    public void setValidEntityExist(boolean validEntityExist) {
        this.validEntityExist = validEntityExist;
    }

    public boolean isReturnBizKeyValue() {
        return this.returnBizKeyValue;
    }

    public void setReturnBizKeyValue(boolean returnBizKeyValue) {
        this.returnBizKeyValue = returnBizKeyValue;
    }

    public Integer getDataLineIndex() {
        return this.dataLineIndex;
    }

    public void setDataLineIndex(Integer dataLineIndex) {
        this.dataLineIndex = dataLineIndex;
    }

    public boolean isMultistageEliminateBizKey() {
        return this.multistageEliminateBizKey;
    }

    public void setMultistageEliminateBizKey(boolean multistageEliminateBizKey) {
        this.multistageEliminateBizKey = multistageEliminateBizKey;
    }

    public List<Variable> getVariables() {
        return this.variables;
    }

    public void setVariables(List<Variable> variables) {
        this.variables = variables;
    }

    public CSVRange getCsvRange() {
        return this.csvRange;
    }

    public void setCsvRange(CSVRange csvRange) {
        this.csvRange = csvRange;
    }

    public boolean isNewFileGroup() {
        return this.newFileGroup;
    }

    public void setNewFileGroup(boolean newFileGroup) {
        this.newFileGroup = newFileGroup;
    }

    public String getMeasure() {
        return this.measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getDecimal() {
        return this.decimal;
    }

    public void setDecimal(String decimal) {
        this.decimal = decimal;
    }

    public int getCheckType() {
        return this.checkType;
    }

    public void setCheckType(int checkType) {
        this.checkType = checkType;
    }

    public List<String> getSortFields() {
        return this.sortFields;
    }

    public void setSortFields(List<String> sortFields) {
        this.sortFields = sortFields;
    }

    public boolean isOrdered() {
        return this.isOrdered;
    }

    public void setOrdered(boolean ordered) {
        this.isOrdered = ordered;
    }
}

