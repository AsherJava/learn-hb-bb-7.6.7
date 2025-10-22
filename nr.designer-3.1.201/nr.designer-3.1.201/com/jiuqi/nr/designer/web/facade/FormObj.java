/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.jiuqi.nr.definition.datalinkmapping.vo.DataLinkMappingVO
 *  com.jiuqi.nr.definition.internal.impl.FillInAutomaticallyDue
 *  com.jiuqi.nvwa.grid2.Grid2Data
 */
package com.jiuqi.nr.designer.web.facade;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jiuqi.nr.definition.datalinkmapping.vo.DataLinkMappingVO;
import com.jiuqi.nr.definition.internal.impl.FillInAutomaticallyDue;
import com.jiuqi.nr.designer.web.facade.AnalysisFormParamObj;
import com.jiuqi.nr.designer.web.facade.EntityTables;
import com.jiuqi.nr.designer.web.facade.FormFoldingObj;
import com.jiuqi.nr.designer.web.facade.RegionObj;
import com.jiuqi.nvwa.grid2.Grid2Data;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown=true)
public class FormObj {
    @JsonProperty
    private int FormStatus;
    @JsonProperty(value="Code")
    private String code;
    @JsonProperty(value="TaskId")
    private String taskId;
    @JsonProperty(value="FormType")
    private int formType;
    @JsonProperty(value="OwnGroupId")
    private String ownGroupId;
    @JsonProperty(value="OwnGroupIdJoint")
    private String ownGroupIdJoint;
    @JsonProperty(value="UnGather")
    private boolean unGather;
    @JsonProperty(value="GatherNodeOnly")
    private boolean gatherNodeOnly;
    @JsonProperty(value="RootNodeOnly")
    private boolean rootNodeOnly;
    @JsonProperty(value="MobileView")
    private String mobileView;
    @JsonProperty(value="DataType")
    private int dataType;
    @JsonProperty(value="FormStyle")
    private Grid2Data formStyle;
    @JsonProperty(value="Published")
    private boolean published;
    @JsonProperty(value="HasRuntimeData")
    private boolean hasRuntimeData;
    @JsonProperty(value="Regions")
    private Map<String, RegionObj> regions;
    @JsonProperty(value="FormExtension")
    private String formExtension;
    @JsonProperty(value="ID")
    private String iD;
    @JsonProperty(value="Title")
    private String title;
    @JsonProperty(value="Order")
    private String order;
    @JsonProperty(value="IsNew")
    private boolean isNew = false;
    @JsonProperty(value="IsDeleted")
    private boolean isDeleted = false;
    @JsonProperty(value="IsDirty")
    private boolean isDirty = false;
    @JsonProperty(value="SerialNumber")
    private String serialNumber;
    @JsonProperty(value="MeasureUnit")
    private String measureUnit;
    @JsonProperty(value="MasterEntitiesKey")
    private String masterEntitiesKey;
    @JsonProperty(value="EntityList")
    private List<EntityTables> entityList;
    @JsonProperty(value="ActiveCondition")
    private String activeCondition;
    @JsonProperty(value="FillingGuide")
    private String fillingGuide;
    @JsonProperty(value="SecretRank")
    private int secretRank;
    @JsonProperty(value="LanguageType")
    private int languageType = 1;
    @JsonProperty(value="NextLanguageType")
    private int nextLanguageType;
    @JsonProperty(value="surveyData")
    private String surveyData;
    @JsonProperty(value="scriptEditor")
    private String scriptEditor;
    @JsonProperty(value="formOnlyRead")
    private String formOnlyRead;
    @JsonProperty(value="OrdersBygroup")
    private Map<String, String> ordersBygroup;
    @JsonProperty(value="FormFolding")
    private List<FormFoldingObj> formFoldingObjs;
    @JsonProperty(value="QuoteType")
    private boolean quoteType;
    @JsonProperty(value="EntitiesIsExtend")
    private boolean entitiesIsExtend = true;
    @JsonProperty(value="MeasureUnitIsExtend")
    private boolean measureUnitIsExtend = true;
    @JsonProperty(value="OwnerLevelAndId")
    private String ownerLevelAndId;
    @JsonProperty(value="SameServeCode")
    private boolean sameServeCode;
    @JsonProperty(value="AnalysisForm")
    private boolean analysisForm;
    @JsonProperty(value="AnalysisFormParam")
    private AnalysisFormParamObj analysisFormParam;
    @JsonProperty(value="referFieldLinkData")
    private List<DataLinkMappingVO> referFieldLinkData;
    @JsonProperty(value="InsertAnalysisGuid")
    private String insertAnalysisGuid;
    @JsonProperty(value="InsertAnalysisTitle")
    private String insertAnalysisTitle;
    private int rowSpace = 0;
    private String analysisReportKey;
    private boolean ledgerForm;
    @JsonProperty(value="FormScheme")
    private String formScheme;
    @JsonProperty(value="FillInAutomaticallyDueIsExtend")
    private boolean fillInAutomaticallyDueIsExtend = true;
    private FillInAutomaticallyDue fillInAutomaticallyDue;

    public int getRowSpace() {
        return this.rowSpace;
    }

    public void setRowSpace(int rowSpace) {
        this.rowSpace = rowSpace;
    }

    public String getInsertAnalysisGuid() {
        return this.insertAnalysisGuid;
    }

    public void setInsertAnalysisGuid(String insertAnalysisGuid) {
        this.insertAnalysisGuid = insertAnalysisGuid;
    }

    public String getInsertAnalysisTitle() {
        return this.insertAnalysisTitle;
    }

    public void setInsertAnalysisTitle(String insertAnalysisTitle) {
        this.insertAnalysisTitle = insertAnalysisTitle;
    }

    public List<DataLinkMappingVO> getReferFieldLinkData() {
        return this.referFieldLinkData;
    }

    public void setReferFieldLinkData(List<DataLinkMappingVO> referFieldLinkData) {
        this.referFieldLinkData = referFieldLinkData;
    }

    public List<FormFoldingObj> getFormFoldingObjs() {
        return this.formFoldingObjs;
    }

    public void setFormFoldingObjs(List<FormFoldingObj> formFoldingObjs) {
        this.formFoldingObjs = formFoldingObjs;
    }

    @JsonIgnore
    public String getFormScheme() {
        return this.formScheme;
    }

    @JsonIgnore
    public void setFormScheme(String formScheme) {
        this.formScheme = formScheme;
    }

    public boolean isFillInAutomaticallyDueIsExtend() {
        return this.fillInAutomaticallyDueIsExtend;
    }

    public void setFillInAutomaticallyDueIsExtend(boolean fillInAutomaticallyDueIsExtend) {
        this.fillInAutomaticallyDueIsExtend = fillInAutomaticallyDueIsExtend;
    }

    public FillInAutomaticallyDue getFillInAutomaticallyDue() {
        return this.fillInAutomaticallyDue;
    }

    public void setFillInAutomaticallyDue(FillInAutomaticallyDue fillInAutomaticallyDue) {
        this.fillInAutomaticallyDue = fillInAutomaticallyDue;
    }

    @JsonIgnore
    public boolean getEntitiesIsExtend() {
        return this.entitiesIsExtend;
    }

    @JsonIgnore
    public void setEntitiesIsExtend(boolean entitiesIsExtend) {
        this.entitiesIsExtend = entitiesIsExtend;
    }

    @JsonIgnore
    public boolean getMeasureUnitIsExtend() {
        return this.measureUnitIsExtend;
    }

    @JsonIgnore
    public void setMeasureUnitIsExtend(boolean measureUnitIsExtend) {
        this.measureUnitIsExtend = measureUnitIsExtend;
    }

    @JsonIgnore
    public String getMobileView() {
        return this.mobileView;
    }

    @JsonIgnore
    public void setMobileView(String mobileView) {
        this.mobileView = mobileView;
    }

    @JsonIgnore
    public boolean isPublished() {
        return this.published;
    }

    @JsonIgnore
    public void setPublished(boolean published) {
        this.published = published;
    }

    @JsonIgnore
    public boolean isHasRuntimeData() {
        return this.hasRuntimeData;
    }

    @JsonIgnore
    public void setHasRuntimeData(boolean hasRuntimeData) {
        this.hasRuntimeData = hasRuntimeData;
    }

    @JsonIgnore
    public int getFormStatus() {
        return this.FormStatus;
    }

    @JsonIgnore
    public void setFormStatus(int formStatus) {
        this.FormStatus = formStatus;
    }

    @JsonIgnore
    public String getID() {
        return this.iD;
    }

    @JsonIgnore
    public void setID(String iD) {
        this.iD = iD;
    }

    @JsonIgnore
    public boolean isIsNew() {
        return this.isNew;
    }

    @JsonIgnore
    public void setIsNew(boolean isNew) {
        this.isNew = isNew;
    }

    @JsonIgnore
    public boolean isIsDeleted() {
        return this.isDeleted;
    }

    @JsonIgnore
    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    @JsonIgnore
    public boolean isIsDirty() {
        return this.isDirty;
    }

    @JsonIgnore
    public void setIsDirty(boolean isDirty) {
        this.isDirty = isDirty;
    }

    @JsonIgnore
    public String getOwnGroupId() {
        return this.ownGroupId;
    }

    @JsonIgnore
    public void setOwnGroupId(String ownGroupId) {
        this.ownGroupId = ownGroupId;
    }

    @JsonIgnore
    public String getOwnGroupIdJoint() {
        return this.ownGroupIdJoint;
    }

    @JsonIgnore
    public void setOwnGroupIdJoint(String ownGroupIdJoint) {
        this.ownGroupIdJoint = ownGroupIdJoint;
    }

    @JsonIgnore
    public Grid2Data getFormStyle() {
        return this.formStyle;
    }

    @JsonIgnore
    public void setFormStyle(Grid2Data formStyle) {
        this.formStyle = formStyle;
    }

    @JsonIgnore
    public String getTitle() {
        return this.title;
    }

    @JsonIgnore
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonIgnore
    public String getOrder() {
        return this.order;
    }

    @JsonIgnore
    public void setOrder(String order) {
        this.order = order;
    }

    @JsonIgnore
    public String getCode() {
        return this.code;
    }

    @JsonIgnore
    public void setCode(String code) {
        this.code = code;
    }

    @JsonIgnore
    public String getTaskId() {
        return this.taskId;
    }

    @JsonIgnore
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    @JsonIgnore
    public int getFormType() {
        return this.formType;
    }

    @JsonIgnore
    public void setFormType(int formType) {
        this.formType = formType;
    }

    @JsonIgnore
    public boolean isUnGather() {
        return this.unGather;
    }

    @JsonIgnore
    public void setUnGather(boolean unGather) {
        this.unGather = unGather;
    }

    @JsonIgnore
    public boolean isGatherNodeOnly() {
        return this.gatherNodeOnly;
    }

    @JsonIgnore
    public void setGatherNodeOnly(boolean gatherNodeOnly) {
        this.gatherNodeOnly = gatherNodeOnly;
    }

    @JsonIgnore
    public boolean isRootNodeOnly() {
        return this.rootNodeOnly;
    }

    @JsonIgnore
    public void setRootNodeOnly(boolean rootNodeOnly) {
        this.rootNodeOnly = rootNodeOnly;
    }

    @JsonIgnore
    public int getDataType() {
        return this.dataType;
    }

    @JsonIgnore
    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    @JsonIgnore
    public String getFormExtension() {
        return this.formExtension;
    }

    @JsonIgnore
    public void setFormExtension(String formExtension) {
        this.formExtension = formExtension;
    }

    @JsonIgnore
    public Map<String, RegionObj> getRegions() {
        return this.regions;
    }

    @JsonIgnore
    public void setRegions(Map<String, RegionObj> regions) {
        this.regions = regions;
    }

    @JsonIgnore
    public String getSerialNumber() {
        return this.serialNumber;
    }

    @JsonIgnore
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    @JsonIgnore
    public String getMeasureUnit() {
        return this.measureUnit;
    }

    @JsonIgnore
    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }

    @JsonIgnore
    public String getMasterEntitiesKey() {
        return this.masterEntitiesKey;
    }

    @JsonIgnore
    public void setMasterEntitiesKey(String masterEntitiesKey) {
        this.masterEntitiesKey = masterEntitiesKey;
    }

    @JsonIgnore
    public List<EntityTables> getEntityList() {
        return this.entityList;
    }

    @JsonIgnore
    public void setEntityList(List<EntityTables> entityList) {
        this.entityList = entityList;
    }

    @JsonIgnore
    public String getActiveCondition() {
        return this.activeCondition;
    }

    @JsonIgnore
    public void setActiveCondition(String activeCondition) {
        this.activeCondition = activeCondition;
    }

    @JsonIgnore
    public String getFillingGuide() {
        return this.fillingGuide;
    }

    @JsonIgnore
    public void setFillingGuide(String fillingGuide) {
        this.fillingGuide = fillingGuide;
    }

    @JsonIgnore
    public int getSecretRank() {
        return this.secretRank;
    }

    @JsonIgnore
    public void setSecretRank(int secretRank) {
        this.secretRank = secretRank;
    }

    @JsonIgnore
    public int getLanguageType() {
        return this.languageType;
    }

    @JsonIgnore
    public void setLanguageType(int languageType) {
        this.languageType = languageType;
    }

    @JsonIgnore
    public int getNextLanguageType() {
        return this.nextLanguageType;
    }

    @JsonIgnore
    public void setNextLanguageType(int nextLanguageType) {
        this.nextLanguageType = nextLanguageType;
    }

    @JsonIgnore
    public String getSurveyData() {
        return this.surveyData;
    }

    @JsonIgnore
    public void setSurveyData(String surveyData) {
        this.surveyData = surveyData;
    }

    @JsonIgnore
    public String getScriptEditor() {
        return this.scriptEditor;
    }

    @JsonIgnore
    public void setScriptEditor(String scriptEditor) {
        this.scriptEditor = scriptEditor;
    }

    @JsonIgnore
    public Map<String, String> getOrdersBygroup() {
        return this.ordersBygroup;
    }

    @JsonIgnore
    public void setOrdersBygroup(Map<String, String> ordersBygroup) {
        this.ordersBygroup = ordersBygroup;
    }

    @JsonIgnore
    public String getformOnlyReadExp() {
        return this.formOnlyRead;
    }

    @JsonIgnore
    public void setformOnlyReadExp(String formOnlyRead) {
        this.formOnlyRead = formOnlyRead;
    }

    @JsonIgnore
    public String getOwnerLevelAndId() {
        return this.ownerLevelAndId;
    }

    @JsonIgnore
    public void setOwnerLevelAndId(String ownerLevelAndId) {
        this.ownerLevelAndId = ownerLevelAndId;
    }

    @JsonIgnore
    public boolean getSameServeCode() {
        return this.sameServeCode;
    }

    @JsonIgnore
    public void setSameServeCode(boolean sameServeCode) {
        this.sameServeCode = sameServeCode;
    }

    @JsonIgnore
    public boolean getQuoteType() {
        return this.quoteType;
    }

    @JsonIgnore
    public void setQuoteType(boolean quoteType) {
        this.quoteType = quoteType;
    }

    @JsonIgnore
    public boolean isAnalysisForm() {
        return this.analysisForm;
    }

    @JsonIgnore
    public void setAnalysisForm(boolean analysisForm) {
        this.analysisForm = analysisForm;
    }

    @JsonIgnore
    public AnalysisFormParamObj getAnalysisFormParam() {
        return this.analysisFormParam;
    }

    @JsonIgnore
    public void setAnalysisFormParam(AnalysisFormParamObj analysisFormParam) {
        this.analysisFormParam = analysisFormParam;
    }

    public boolean isLedgerForm() {
        return this.ledgerForm;
    }

    public void setLedgerForm(boolean ledgerForm) {
        this.ledgerForm = ledgerForm;
    }

    public String getAnalysisReportKey() {
        return this.analysisReportKey;
    }

    public void setAnalysisReportKey(String analysisReportKey) {
        this.analysisReportKey = analysisReportKey;
    }
}

