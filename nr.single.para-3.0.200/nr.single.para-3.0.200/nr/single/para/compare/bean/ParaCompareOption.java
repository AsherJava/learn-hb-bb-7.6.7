/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.compare.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import nr.single.para.compare.definition.common.CompareContextType;
import nr.single.para.compare.definition.common.CompareParaType;
import nr.single.para.compare.definition.common.CompareUpdateType;

public class ParaCompareOption
implements Serializable {
    private static final long serialVersionUID = 1L;
    private CompareParaType compareType;
    private boolean uploadBaseParam;
    private boolean uploadFormula;
    private boolean uploadPrint;
    private boolean uploadQuery;
    private boolean fieldContainForm;
    private boolean useFormLevel;
    private boolean overWriteAll;
    private CompareContextType enumCompareType;
    private CompareContextType itemCompareType;
    private CompareContextType floatRegionCompareType;
    private transient CompareUpdateType updateType;
    private String corpEntityId;
    private String dateEntityId;
    private String dimEntityIds;
    private String dataPrefix;
    private String enumPrefix;
    private String dataSchemeCode;
    private String dataSchemeTitle;
    private String taskCode;
    private String taskTitle;
    private String fromPeriod;
    private String toPeriod;
    private String formSchemeTitle;
    private boolean isHistoryPara;
    private Map<String, String> variableMap;

    public CompareParaType getCompareType() {
        return this.compareType;
    }

    public void setCompareType(CompareParaType compareType) {
        this.compareType = compareType;
    }

    public boolean isUploadFormula() {
        return this.uploadFormula;
    }

    public void setUploadFormula(boolean uploadFormula) {
        this.uploadFormula = uploadFormula;
    }

    public boolean isUploadPrint() {
        return this.uploadPrint;
    }

    public void setUploadPrint(boolean uploadPrint) {
        this.uploadPrint = uploadPrint;
    }

    public boolean isUploadQuery() {
        return this.uploadQuery;
    }

    public void setUploadQuery(boolean uploadQuery) {
        this.uploadQuery = uploadQuery;
    }

    public boolean isFieldContainForm() {
        return this.fieldContainForm;
    }

    public void setFieldContainForm(boolean fieldContainForm) {
        this.fieldContainForm = fieldContainForm;
    }

    public boolean isUseFormLevel() {
        return this.useFormLevel;
    }

    public void setUseFormLevel(boolean useFormLevel) {
        this.useFormLevel = useFormLevel;
    }

    public boolean isOverWriteAll() {
        return this.overWriteAll;
    }

    public void setOverWriteAll(boolean overWriteAll) {
        this.overWriteAll = overWriteAll;
    }

    public CompareContextType getEnumCompareType() {
        return this.enumCompareType;
    }

    public void setEnumCompareType(CompareContextType enumCompareType) {
        this.enumCompareType = enumCompareType;
    }

    public String getCorpEntityId() {
        return this.corpEntityId;
    }

    public void setCorpEntityId(String corpEntityId) {
        this.corpEntityId = corpEntityId;
    }

    public String getDateEntityId() {
        return this.dateEntityId;
    }

    public void setDateEntityId(String dateEntityId) {
        this.dateEntityId = dateEntityId;
    }

    public String getDimEntityIds() {
        return this.dimEntityIds;
    }

    public void setDimEntityIds(String dimEntityIds) {
        this.dimEntityIds = dimEntityIds;
    }

    public String getDataPrefix() {
        return this.dataPrefix;
    }

    public void setDataPrefix(String dataPrefix) {
        this.dataPrefix = dataPrefix;
    }

    public String getEnumPrefix() {
        return this.enumPrefix;
    }

    public void setEnumPrefix(String enumPrefix) {
        this.enumPrefix = enumPrefix;
    }

    public CompareContextType getItemCompareType() {
        return this.itemCompareType;
    }

    public void setItemCompareType(CompareContextType itemCompareType) {
        this.itemCompareType = itemCompareType;
    }

    public String getDataSchemeCode() {
        return this.dataSchemeCode;
    }

    public void setDataSchemeCode(String dataSchemeCode) {
        this.dataSchemeCode = dataSchemeCode;
    }

    public String getDataSchemeTitle() {
        return this.dataSchemeTitle;
    }

    public void setDataSchemeTitle(String dataSchemeTitle) {
        this.dataSchemeTitle = dataSchemeTitle;
    }

    public Map<String, String> getVariableMap() {
        if (this.variableMap == null) {
            this.variableMap = new HashMap<String, String>();
        }
        return this.variableMap;
    }

    public void setVariableMap(Map<String, String> variableMap) {
        this.variableMap = variableMap;
    }

    public Boolean getIsHistoryPara() {
        return this.isHistoryPara;
    }

    public void setIsHistoryPara(Boolean isHistoryPara) {
        this.isHistoryPara = isHistoryPara;
    }

    public void setHistoryPara(boolean isHistoryPara) {
        this.isHistoryPara = isHistoryPara;
    }

    public String getTaskCode() {
        return this.taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public String getTaskTitle() {
        return this.taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getFormSchemeTitle() {
        return this.formSchemeTitle;
    }

    public void setFormSchemeTitle(String formSchemeTitle) {
        this.formSchemeTitle = formSchemeTitle;
    }

    public String getFromPeriod() {
        return this.fromPeriod;
    }

    public void setFromPeriod(String fromPeriod) {
        this.fromPeriod = fromPeriod;
    }

    public String getToPeriod() {
        return this.toPeriod;
    }

    public void setToPeriod(String toPeriod) {
        this.toPeriod = toPeriod;
    }

    public boolean isUploadBaseParam() {
        return this.uploadBaseParam;
    }

    public void setUploadBaseParam(boolean uploadBaseParam) {
        this.uploadBaseParam = uploadBaseParam;
    }

    public CompareUpdateType getUpdateType() {
        return this.updateType;
    }

    public void setUpdateType(CompareUpdateType updateType) {
        this.updateType = updateType;
    }

    public CompareContextType getFloatRegionCompareType() {
        return this.floatRegionCompareType;
    }

    public void setFloatRegionCompareType(CompareContextType floatRegionCompareType) {
        this.floatRegionCompareType = floatRegionCompareType;
    }
}

