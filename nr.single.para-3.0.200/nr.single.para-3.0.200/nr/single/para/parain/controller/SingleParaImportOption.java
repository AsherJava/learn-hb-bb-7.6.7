/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.parain.controller;

import java.io.Serializable;

public class SingleParaImportOption
implements Serializable {
    private static final long serialVersionUID = -1682053169380116070L;
    private boolean isUploadTask;
    private boolean isUploadFormScheme;
    private boolean isUploadEnum;
    private boolean isUploadForm;
    private boolean isUploadTaskLink;
    private boolean isUploadFormula;
    private boolean isUploadPrint;
    private boolean isUploadQuery;
    private boolean isHistoryPara;
    private boolean isAnalPara;
    private String syncTaskID;
    private int historyMatchType;
    private String historyFormSchemes;
    private boolean isHistoryUpdateEnumRef;
    private boolean overWriteAll;
    private String filePrefix;
    private String enumPrefix;
    private String dataSchemeCode;
    private String dataSchemeTitle;
    private String taskCode;
    private String taskTitle;
    private String fromPeriod;
    private String toPeriod;
    private String formSchemeTitle;
    String corpEntityId;
    String dateEntityId;
    String dimEntityIds;
    boolean isSelectEntity;
    boolean isFMDMFieldInEntity;

    public SingleParaImportOption() {
        this.SelectAll();
        this.isHistoryPara = false;
        this.isSelectEntity = true;
        this.isFMDMFieldInEntity = true;
    }

    public void SelectAll() {
        this.isUploadTask = true;
        this.isUploadFormScheme = true;
        this.isUploadEnum = true;
        this.isUploadForm = true;
        this.isUploadTaskLink = true;
        this.isUploadFormula = true;
        this.isUploadPrint = true;
        this.isUploadQuery = true;
    }

    public void NotSelectAll() {
        this.isUploadTask = false;
        this.isUploadFormScheme = false;
        this.isUploadEnum = false;
        this.isUploadForm = false;
        this.isUploadTaskLink = false;
        this.isUploadFormula = false;
        this.isUploadPrint = false;
        this.isUploadQuery = false;
        this.historyMatchType = 0;
    }

    public boolean isUploadTask() {
        return this.isUploadTask;
    }

    public void setUploadTask(boolean isUploadTask) {
        this.isUploadTask = isUploadTask;
    }

    public boolean isUploadFormScheme() {
        return this.isUploadFormScheme;
    }

    public void setUploadFormScheme(boolean isUploadFormScheme) {
        this.isUploadFormScheme = isUploadFormScheme;
    }

    public boolean isUploadEnum() {
        return this.isUploadEnum;
    }

    public void setUploadEnum(boolean isUploadEnum) {
        this.isUploadEnum = isUploadEnum;
    }

    public boolean isUploadForm() {
        return this.isUploadForm;
    }

    public void setUploadForm(boolean isUploadForm) {
        this.isUploadForm = isUploadForm;
    }

    public boolean isUploadTaskLink() {
        return this.isUploadTaskLink;
    }

    public void setUploadTaskLink(boolean isUploadTaskLink) {
        this.isUploadTaskLink = isUploadTaskLink;
    }

    public boolean isUploadFormula() {
        return this.isUploadFormula;
    }

    public void setUploadFormula(boolean isUploadFormula) {
        this.isUploadFormula = isUploadFormula;
    }

    public boolean isUploadPrint() {
        return this.isUploadPrint;
    }

    public void setUploadPrint(boolean isUploadPrint) {
        this.isUploadPrint = isUploadPrint;
    }

    public boolean isUploadQuery() {
        return this.isUploadQuery;
    }

    public void setUploadQuery(boolean isUploadQuery) {
        this.isUploadQuery = isUploadQuery;
    }

    public String getSyncTaskID() {
        return this.syncTaskID;
    }

    public void setSyncTaskID(String syncTaskID) {
        this.syncTaskID = syncTaskID;
    }

    public boolean isHistoryPara() {
        return this.isHistoryPara;
    }

    public void setHistoryPara(boolean isHistoryPara) {
        this.isHistoryPara = isHistoryPara;
    }

    public boolean isAnalPara() {
        return this.isAnalPara;
    }

    public void setAnalPara(boolean isAnalPara) {
        this.isAnalPara = isAnalPara;
    }

    public int getHistoryMatchType() {
        return this.historyMatchType;
    }

    public void setHistoryMatchType(int historyMatchType) {
        this.historyMatchType = historyMatchType;
    }

    public String getHistoryFormSchemes() {
        return this.historyFormSchemes;
    }

    public void setHistoryFormSchemes(String historyFormSchemes) {
        this.historyFormSchemes = historyFormSchemes;
    }

    public String getFilePrefix() {
        return this.filePrefix;
    }

    public void setFilePrefix(String filePrefix) {
        this.filePrefix = filePrefix;
    }

    public String getEnumPrefix() {
        return this.enumPrefix;
    }

    public void setEnumPrefix(String enumPrefix) {
        this.enumPrefix = enumPrefix;
    }

    public boolean isHistoryUpdateEnumRef() {
        return this.isHistoryUpdateEnumRef;
    }

    public void setHistoryUpdateEnumRef(boolean isHistoryUpdateEnumRef) {
        this.isHistoryUpdateEnumRef = isHistoryUpdateEnumRef;
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

    public boolean isSelectEntity() {
        return this.isSelectEntity;
    }

    public void setSelectEntity(boolean isSelectEntity) {
        this.isSelectEntity = isSelectEntity;
    }

    public boolean isFMDMFieldInEntity() {
        return this.isFMDMFieldInEntity;
    }

    public void setFMDMFieldInEntity(boolean isFMDMFieldInEntity) {
        this.isFMDMFieldInEntity = isFMDMFieldInEntity;
    }

    public boolean isOverWriteAll() {
        return this.overWriteAll;
    }

    public void setOverWriteAll(boolean overWriteAll) {
        this.overWriteAll = overWriteAll;
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
}

