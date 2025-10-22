/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.fasterxml.jackson.databind.DeserializationFeature
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.bi.util.StringHelper
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.np.definition.exception.DBParaException
 *  com.jiuqi.np.definition.facade.DesignFieldDefine
 *  com.jiuqi.np.definition.facade.DesignTableDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.conditionalstyle.controller.IDesignConditionalStyleController
 *  com.jiuqi.nr.conditionalstyle.facade.DesignConditionalStyle
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.definition.common.DataLinkEditMode
 *  com.jiuqi.nr.definition.common.DataLinkType
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.common.EnumDisplayMode
 *  com.jiuqi.nr.definition.common.FormFoldingDirEnum
 *  com.jiuqi.nr.definition.common.FormFoldingSpecialRegion
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.common.RegionEnterNext
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.datalinkmapping.vo.DataLinkMappingVO
 *  com.jiuqi.nr.definition.facade.DesignAnalysisFormParamDefine
 *  com.jiuqi.nr.definition.facade.DesignDataLinkDefine
 *  com.jiuqi.nr.definition.facade.DesignDataRegionDefine
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormFoldingDefine
 *  com.jiuqi.nr.definition.facade.DesignRegionSettingDefine
 *  com.jiuqi.nr.definition.facade.RowNumberSetting
 *  com.jiuqi.nr.definition.internal.controller.NRDesignTimeController
 *  com.jiuqi.nr.definition.internal.impl.AnalysisFormParamDefineImpl
 *  com.jiuqi.nr.definition.internal.impl.DesignFormDefineBigDataUtil
 *  com.jiuqi.nr.definition.internal.impl.DesignFormFoldingDefineImpl
 *  com.jiuqi.nr.definition.internal.impl.DesignFormGroupLink
 *  com.jiuqi.nr.definition.internal.impl.DesignRegionSettingDefineImpl
 *  com.jiuqi.nr.definition.internal.impl.DesignRowNumberSettingImpl
 *  com.jiuqi.nr.definition.internal.impl.FillInAutomaticallyDue
 *  com.jiuqi.nr.definition.internal.impl.FormExtendPropsDefaultValue
 *  com.jiuqi.nr.definition.internal.impl.RegionEdgeStyleData
 *  com.jiuqi.nr.definition.internal.impl.RegionTabSettingData
 *  com.jiuqi.nr.definition.internal.service.DesignFormDefineService
 *  com.jiuqi.nr.definition.internal.service.DesignFormFoldingService
 *  com.jiuqi.nr.definition.paramlanguage.service.DefaultLanguageService
 *  com.jiuqi.nr.definition.util.RecordCard
 *  com.jiuqi.nr.definition.util.ServeCodeService
 *  com.jiuqi.nr.definition.validation.CompareType
 *  com.jiuqi.nr.definition.validation.DataValidationExpression
 *  com.jiuqi.nr.definition.validation.DataValidationExpressionFactory
 *  com.jiuqi.nr.period.util.JacksonUtils
 *  com.jiuqi.nr.survey.model.FileQuestion
 *  com.jiuqi.nr.survey.model.SurveyModel
 *  com.jiuqi.nr.survey.model.common.QuestionType
 *  com.jiuqi.nr.survey.model.link.SurveyModelLinkHelp
 *  com.jiuqi.nr.survey.model.link.SurveyQuestion
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  org.json.JSONArray
 *  org.json.JSONObject
 */
package com.jiuqi.nr.designer.helper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.bi.util.StringHelper;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.np.definition.facade.DesignFieldDefine;
import com.jiuqi.np.definition.facade.DesignTableDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.conditionalstyle.controller.IDesignConditionalStyleController;
import com.jiuqi.nr.conditionalstyle.facade.DesignConditionalStyle;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.definition.common.DataLinkEditMode;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.common.EnumDisplayMode;
import com.jiuqi.nr.definition.common.FormFoldingDirEnum;
import com.jiuqi.nr.definition.common.FormFoldingSpecialRegion;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.common.RegionEnterNext;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.datalinkmapping.vo.DataLinkMappingVO;
import com.jiuqi.nr.definition.facade.DesignAnalysisFormParamDefine;
import com.jiuqi.nr.definition.facade.DesignDataLinkDefine;
import com.jiuqi.nr.definition.facade.DesignDataRegionDefine;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormFoldingDefine;
import com.jiuqi.nr.definition.facade.DesignRegionSettingDefine;
import com.jiuqi.nr.definition.facade.RowNumberSetting;
import com.jiuqi.nr.definition.internal.controller.NRDesignTimeController;
import com.jiuqi.nr.definition.internal.impl.AnalysisFormParamDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignFormDefineBigDataUtil;
import com.jiuqi.nr.definition.internal.impl.DesignFormFoldingDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignFormGroupLink;
import com.jiuqi.nr.definition.internal.impl.DesignRegionSettingDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignRowNumberSettingImpl;
import com.jiuqi.nr.definition.internal.impl.FillInAutomaticallyDue;
import com.jiuqi.nr.definition.internal.impl.FormExtendPropsDefaultValue;
import com.jiuqi.nr.definition.internal.impl.RegionEdgeStyleData;
import com.jiuqi.nr.definition.internal.impl.RegionTabSettingData;
import com.jiuqi.nr.definition.internal.service.DesignFormDefineService;
import com.jiuqi.nr.definition.internal.service.DesignFormFoldingService;
import com.jiuqi.nr.definition.paramlanguage.service.DefaultLanguageService;
import com.jiuqi.nr.definition.util.RecordCard;
import com.jiuqi.nr.definition.util.ServeCodeService;
import com.jiuqi.nr.definition.validation.CompareType;
import com.jiuqi.nr.definition.validation.DataValidationExpression;
import com.jiuqi.nr.definition.validation.DataValidationExpressionFactory;
import com.jiuqi.nr.designer.common.EntityChangeInfo;
import com.jiuqi.nr.designer.common.NrDesingerErrorEnum;
import com.jiuqi.nr.designer.helper.CommonHelper;
import com.jiuqi.nr.designer.helper.RegionSurveyHelper;
import com.jiuqi.nr.designer.helper.SaveEntityLinkageHelper;
import com.jiuqi.nr.designer.util.EntityDefaultValueObj;
import com.jiuqi.nr.designer.web.facade.AnalysisFormParamObj;
import com.jiuqi.nr.designer.web.facade.FormFoldingObj;
import com.jiuqi.nr.designer.web.facade.FormObj;
import com.jiuqi.nr.designer.web.facade.LinkObj;
import com.jiuqi.nr.designer.web.facade.RegionEdgeStyleObj;
import com.jiuqi.nr.designer.web.facade.RegionObj;
import com.jiuqi.nr.designer.web.facade.RegionTabSettingObj;
import com.jiuqi.nr.designer.web.facade.ValidationObj;
import com.jiuqi.nr.designer.web.treebean.TableObject;
import com.jiuqi.nr.period.util.JacksonUtils;
import com.jiuqi.nr.survey.model.FileQuestion;
import com.jiuqi.nr.survey.model.SurveyModel;
import com.jiuqi.nr.survey.model.common.QuestionType;
import com.jiuqi.nr.survey.model.link.SurveyModelLinkHelp;
import com.jiuqi.nr.survey.model.link.SurveyQuestion;
import com.jiuqi.nvwa.grid2.Grid2Data;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class SaveFormHelper {
    private static final String FIELDFLOATORDER = "FLOATORDER";
    @Autowired
    private CommonHelper commonHelper;
    @Autowired
    private NRDesignTimeController nrDesignTimeController;
    @Autowired
    private DesignFormDefineService formService;
    @Autowired
    private SaveEntityLinkageHelper saveEntityLinkageHelper;
    @Autowired
    private ServeCodeService serveCodeService;
    @Autowired
    private IDesignDataSchemeService iDesignDataSchemeService;
    @Autowired
    private DefaultLanguageService defaultLanguageService;
    @Autowired
    private IDesignTimeViewController iDesignTimeViewController;
    @Autowired
    private IDesignConditionalStyleController desConditionalStyleController;
    @Autowired
    private DesignFormFoldingService designFormFoldingService;
    @Autowired
    private RegionSurveyHelper regionSurveyHelper;
    @Autowired
    private DesignFormDefineService formDefineService;

    public void saveFormObject(FormObj formObject) throws Exception {
        if (formObject == null || StringUtils.isEmpty((String)formObject.getTaskId())) {
            return;
        }
        HashMap<String, EntityChangeInfo> entityChangeMap = new HashMap<String, EntityChangeInfo>();
        String taskID = formObject.getTaskId();
        if (formObject.getFormType() == FormType.FORM_TYPE_ENTITY.getValue()) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_009);
        }
        String formID = formObject.getID();
        DesignFormDefine formDefine = this.nrDesignTimeController.queryFormByIdWithoutFormData(formID);
        if (formDefine != null && formDefine.getFormType() == FormType.FORM_TYPE_ENTITY) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_009);
        }
        this.saveFormFolding(formObject);
        if (formObject.isIsNew() && formDefine == null) {
            formDefine = this.nrDesignTimeController.createFormDefine();
            formObject.setOwnerLevelAndId(this.serveCodeService.getServeCode());
            this.initFormDefine(formDefine, formObject, entityChangeMap);
            this.nrDesignTimeController.insertFormDefine(formDefine, this.defaultLanguageService.getDefaultLanguage());
            this.saveFormGroupLink(formID, formObject.getOwnGroupIdJoint());
            this.saveRegionObject(formObject, formDefine, taskID, entityChangeMap);
        } else if (formObject.isIsDirty() && formDefine != null) {
            this.initFormDefine(formDefine, formObject, entityChangeMap);
            formDefine.setUpdateTime(new Date());
            this.nrDesignTimeController.updateFormDefine(formDefine, formObject.getLanguageType() == 0 ? this.defaultLanguageService.getDefaultLanguage() : formObject.getLanguageType());
            this.saveFormGroupLink(formID, formObject.getOwnGroupIdJoint());
            this.saveRegionObject(formObject, formDefine, taskID, entityChangeMap);
            this.saveDataLinkMapping(formID, formObject.getReferFieldLinkData());
        }
        this.saveAnalysisiParams(formObject);
        this.updateConditionStyle(formID);
    }

    private void saveFormFolding(FormObj formObj) throws DBParaException {
        List<FormFoldingObj> formFoldingObjs = formObj.getFormFoldingObjs();
        if (CollectionUtils.isEmpty(formFoldingObjs)) {
            this.designFormFoldingService.deleteByFormKey(formObj.getID());
            return;
        }
        ArrayList<DesignFormFoldingDefineImpl> foldingDefines = new ArrayList<DesignFormFoldingDefineImpl>();
        for (FormFoldingObj formFoldingObj : formFoldingObjs) {
            DesignFormFoldingDefineImpl define = new DesignFormFoldingDefineImpl();
            define.setKey(UUIDUtils.getKey());
            define.setFormKey(formFoldingObj.getFormKey());
            define.setStartIdx(formFoldingObj.getStartIdx());
            define.setEndIdx(formFoldingObj.getEndIdx());
            define.setHiddenRegion(formFoldingObj.getHiddenRegion());
            define.setDirection(FormFoldingDirEnum.valueOf((int)formFoldingObj.getDirection()));
            define.setFolding(formFoldingObj.isFolding());
            define.setUpdateTime(new Date());
            foldingDefines.add(define);
            if (!formFoldingObj.isFolding()) continue;
            this.handleFormStyle(formObj, formFoldingObj);
        }
        this.designFormFoldingService.deleteByFormKey(formObj.getID());
        this.designFormFoldingService.insert(foldingDefines.toArray(new DesignFormFoldingDefine[foldingDefines.size()]));
    }

    private void handleFormStyle(FormObj formObj, FormFoldingObj formFoldingObj) {
        Grid2Data formStyle = formObj.getFormStyle();
        Integer startIdx = formFoldingObj.getStartIdx();
        Integer endIdx = formFoldingObj.getEndIdx();
        List<FormFoldingSpecialRegion> hiddenRegions = formFoldingObj.getHiddenRegion();
        HashSet<Integer> hiddenRows = new HashSet<Integer>();
        if (!CollectionUtils.isEmpty(hiddenRegions)) {
            for (FormFoldingSpecialRegion hiddenRegion : hiddenRegions) {
                for (int i = hiddenRegion.getStartIdx().intValue(); i <= hiddenRegion.getEndIdx(); ++i) {
                    hiddenRows.add(i);
                }
            }
        }
        for (int i = startIdx.intValue(); i <= endIdx; ++i) {
            if (!CollectionUtils.isEmpty(hiddenRows) && hiddenRows.contains(i)) continue;
            formStyle.setRowHidden(i, false);
        }
    }

    private void saveAnalysisiParams(FormObj formObject) throws JQException {
        if (formObject.isIsDeleted()) {
            return;
        }
        boolean analysisForm = formObject.isAnalysisForm();
        AnalysisFormParamObj analysisFormParam = formObject.getAnalysisFormParam();
        if (analysisForm) {
            if (null != analysisFormParam && analysisFormParam.isDirty()) {
                AnalysisFormParamDefineImpl define = new AnalysisFormParamDefineImpl();
                AnalysisFormParamObj.toDefine(analysisFormParam, (DesignAnalysisFormParamDefine)define);
                this.nrDesignTimeController.updataAnalysisFormParamDefine(formObject.getID(), (DesignAnalysisFormParamDefine)define);
            }
        } else if (null != analysisFormParam) {
            this.nrDesignTimeController.deleteAnalysisFormParamDefine(formObject.getID());
        }
    }

    private void updateConditionStyle(String formKey) throws JQException {
        List allConditionStyle = this.desConditionalStyleController.getAllCSInForm(formKey);
        if (!CollectionUtils.isEmpty(allConditionStyle)) {
            List links;
            try {
                links = this.nrDesignTimeController.getAllLinksInForm(formKey);
            }
            catch (Exception e) {
                throw new RuntimeException(String.format("\u67e5\u8be2\u62a5\u8868[%s]\u94fe\u63a5\u51fa\u9519", formKey));
            }
            if (!CollectionUtils.isEmpty(links)) {
                Map<String, DesignDataLinkDefine> linkMap = links.stream().collect(Collectors.toMap(IBaseMetaItem::getKey, v -> v));
                for (DesignConditionalStyle conditionalStyle : allConditionStyle) {
                    String linkKey = conditionalStyle.getLinkKey();
                    if (!StringUtils.isEmpty((String)linkKey)) {
                        if (linkMap.keySet().contains(linkKey)) {
                            conditionalStyle.setPosX(linkMap.get(linkKey).getPosX());
                            conditionalStyle.setPosY(linkMap.get(linkKey).getPosY());
                            this.desConditionalStyleController.updateCS(Arrays.asList(conditionalStyle));
                            continue;
                        }
                        this.desConditionalStyleController.deleteCS(Arrays.asList(conditionalStyle));
                        continue;
                    }
                    this.desConditionalStyleController.deleteCS(Arrays.asList(conditionalStyle));
                }
            } else {
                this.desConditionalStyleController.deleteCS(allConditionStyle);
            }
        }
    }

    private void initFormDefine(DesignFormDefine designFormDefine, FormObj formObject, Map<String, EntityChangeInfo> entityChangeMap) throws Exception {
        String formKey = formObject.getID();
        designFormDefine.setKey(formKey);
        designFormDefine.setTitle(formObject.getTitle());
        designFormDefine.setOrder(formObject.getOrder());
        designFormDefine.setFormCode(formObject.getCode());
        if (formObject.getFormStyle() != null) {
            designFormDefine.setBinaryData(Grid2Data.gridToBytes((Grid2Data)formObject.getFormStyle()));
        }
        designFormDefine.setIsGather(formObject.isUnGather());
        designFormDefine.setSerialNumber(formObject.getSerialNumber());
        if (designFormDefine.getFormType() != FormType.FORM_TYPE_FMDM) {
            designFormDefine.setFormType(FormType.forValue((int)formObject.getFormType()));
        }
        if (designFormDefine.getFormType() == FormType.FORM_TYPE_SURVEY) {
            if (StringUtils.isNotEmpty((String)formObject.getSurveyData())) {
                designFormDefine.setSurveyData(formObject.getSurveyData());
            } else {
                DesignFormDefine formDefine = this.nrDesignTimeController.queryFormById(formKey);
                if (null != formDefine && null != formDefine.getSurveyData()) {
                    designFormDefine.setSurveyData(formDefine.getSurveyData());
                }
            }
        }
        designFormDefine.setQuoteType(formObject.getQuoteType());
        this.dealFormEntitiesIsExtend(formObject, designFormDefine, formObject.getOwnGroupId(), entityChangeMap, formKey);
        this.dealFormMeasureUnitIsExtend(formObject, designFormDefine);
        this.dealFillInAutomaticallyDue(formObject, designFormDefine);
        designFormDefine.setFormCondition(formObject.getActiveCondition());
        designFormDefine.setFillingGuide(formObject.getFillingGuide());
        designFormDefine.setSecretLevel(formObject.getSecretRank());
        designFormDefine.setScriptEditor(formObject.getScriptEditor());
        designFormDefine.setReadOnlyCondition(formObject.getformOnlyReadExp());
        designFormDefine.setOwnerLevelAndId(formObject.getOwnerLevelAndId());
        if (designFormDefine.getFormType() == FormType.FORM_TYPE_ANALYSISREPORT) {
            designFormDefine.addExtensions("reportKey", (Object)formObject.getAnalysisReportKey());
        }
        designFormDefine.setAnalysisForm(formObject.isAnalysisForm());
        designFormDefine.setLedgerForm(formObject.isLedgerForm());
        designFormDefine.setFormScheme(formObject.getFormScheme());
        if (designFormDefine.getFormType() == FormType.FORM_TYPE_INSERTANALYSIS) {
            designFormDefine.addExtensions("analysisGuid", (Object)formObject.getInsertAnalysisGuid());
            designFormDefine.addExtensions("analysisTitle", (Object)formObject.getInsertAnalysisTitle());
        }
    }

    private void saveFormGroupLink(String formKey, String groupsStr) throws Exception {
        String[] groupIdsArray = new String[]{};
        if (StringUtils.isNotEmpty((String)groupsStr)) {
            groupIdsArray = groupsStr.split(";");
        }
        HashSet<String> groupSet = new HashSet<String>(Arrays.asList(groupIdsArray));
        List designFormGroupLinks = this.nrDesignTimeController.getFormGroupLinksByFormId(formKey);
        DesignFormDefine formDefine = this.nrDesignTimeController.queryFormById(formKey);
        for (DesignFormGroupLink formGroupLink : designFormGroupLinks) {
            String groupID = formGroupLink.getGroupKey();
            if (groupSet.remove(groupID)) continue;
            this.nrDesignTimeController.removeFormFromGroup(formKey, groupID);
        }
        if (formDefine == null) {
            return;
        }
        for (String groupID : groupSet) {
            if (!StringUtils.isNotEmpty((String)groupID)) continue;
            this.nrDesignTimeController.addFormToGroup(formKey, groupID);
            this.setFMDMtoFirst(formDefine, groupID);
        }
    }

    private void setFMDMtoFirst(DesignFormDefine formDefine, String groupID) throws Exception {
        if (formDefine.getFormType().equals((Object)FormType.FORM_TYPE_NEWFMDM)) {
            List formGroupLinksByGroupId = this.nrDesignTimeController.getFormGroupLinksByGroupId(groupID);
            if (null == formGroupLinksByGroupId) {
                return;
            }
            List linkOrder = formGroupLinksByGroupId.stream().sorted((o1, o2) -> o1.getFormOrder().compareTo(o2.getFormOrder())).collect(Collectors.toList());
            if (null != linkOrder && linkOrder.size() != 0 && !((DesignFormGroupLink)linkOrder.get(0)).getFormKey().equals(formDefine.getKey())) {
                List fmdmLinks = linkOrder.stream().filter(e -> e.getFormKey().equals(formDefine.getKey())).collect(Collectors.toList());
                if (fmdmLinks.size() == 0) {
                    return;
                }
                DesignFormGroupLink fmdmLink = (DesignFormGroupLink)fmdmLinks.get(0);
                DesignFormGroupLink firstLink = (DesignFormGroupLink)linkOrder.get(0);
                String formOrder = firstLink.getFormOrder();
                String firstOrder = formOrder.substring(0, 6).concat("00");
                fmdmLink.setFormOrder(firstOrder);
                formDefine.setOrder(firstOrder);
                this.nrDesignTimeController.updateFormDefine(formDefine);
                this.nrDesignTimeController.updateDesignFormGroupLink(fmdmLink);
            }
        }
    }

    private void saveRegionObject(FormObj formObject, DesignFormDefine designFormDefin, String taskID, Map<String, EntityChangeInfo> entityChangeMap) throws Exception {
        RegionObj regionObj = null;
        DesignDataRegionDefine designDataRegionDefine = null;
        String regionID = null;
        HashMap<String, Object> attributeMap = null;
        ArrayList<String> useLessField = new ArrayList<String>();
        Map<String, RegionObj> regions = formObject.getRegions();
        if (regions == null) {
            return;
        }
        for (Map.Entry<String, RegionObj> entry : regions.entrySet()) {
            DesignDataRegionDefine designDataRegionDefine1;
            List allLinksInRegion;
            attributeMap = new HashMap<String, Object>();
            regionObj = entry.getValue();
            regionID = regionObj.getID();
            if (regionObj.isIsDeleted()) {
                if (regionID != null) {
                    this.nrDesignTimeController.deleteDataRegionDefine(regionID, true);
                }
            } else {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode extendAttrs = mapper.readTree(regionObj.getRegionExtension());
                if (!this.nodeIsEmpty(extendAttrs)) {
                    JsonNode attributeNode = extendAttrs.get("BizKeyFieldsID");
                    if (!this.nodeIsEmpty(attributeNode)) {
                        attributeMap.put("BizKeyFieldsID", attributeNode.textValue());
                    } else {
                        attributeMap.put("BizKeyFieldsID", "");
                    }
                }
                if (regionObj.isIsNew()) {
                    designDataRegionDefine = this.nrDesignTimeController.createDataRegionDefine();
                    regionID = designDataRegionDefine.getKey();
                    regionObj.setOwnerLevelAndId(this.serveCodeService.getServeCode());
                    designDataRegionDefine = this.initRegionDefine(designDataRegionDefine, regionObj, designFormDefin.getKey(), taskID, attributeMap, mapper, extendAttrs);
                    this.nrDesignTimeController.insertDataRegionDefine(designDataRegionDefine);
                } else if (regionObj.isIsDirty()) {
                    designDataRegionDefine = this.nrDesignTimeController.queryDataRegionDefine(regionID);
                    designDataRegionDefine = this.initRegionDefine(designDataRegionDefine, regionObj, designFormDefin.getKey(), taskID, attributeMap, mapper, extendAttrs);
                    this.nrDesignTimeController.updateDataRegionDefine(designDataRegionDefine);
                }
            }
            designDataRegionDefine = this.nrDesignTimeController.queryDataRegionDefine(regionID);
            if (!regionObj.isIsDirty() && !regionObj.isIsNew()) {
                if (designDataRegionDefine == null) {
                    attributeMap.put("regionNeedCheck", false);
                } else {
                    this.initRegionDefineHelper(designDataRegionDefine, attributeMap);
                }
            }
            this.saveLinkObject(regionObj.getLinks(), regionID, useLessField);
            if (designDataRegionDefine != null) {
                this.setShowAddress(regionObj, designDataRegionDefine);
            }
            if (null == (allLinksInRegion = this.iDesignTimeViewController.getAllLinksInRegion(regionID)) || allLinksInRegion.size() != 0 || null == (designDataRegionDefine1 = this.nrDesignTimeController.queryDataRegionDefine(regionID))) continue;
            designDataRegionDefine1.setInputOrderFieldKey(null);
            this.nrDesignTimeController.updateDataRegionDefine(designDataRegionDefine1);
        }
    }

    private void setShowAddress(RegionObj regionObj, DesignDataRegionDefine designDataRegionDefine) throws IOException, JQException {
        ObjectMapper mapper = new ObjectMapper();
        if (regionObj.getRegionExtension() == null) {
            return;
        }
        JsonNode extendAttrs = mapper.readTree(regionObj.getRegionExtension());
        if (extendAttrs != null) {
            String showAddress = null;
            if (extendAttrs.get("ShowAddress") != null) {
                String string = showAddress = extendAttrs.get("ShowAddress").isNull() ? "" : extendAttrs.get("ShowAddress").textValue();
            }
            if (StringUtils.isEmpty(showAddress) && designDataRegionDefine.getShowGatherSummaryRow()) {
                DesignRegionSettingDefine designRegionSettingDefine = this.nrDesignTimeController.getRegionSetting(designDataRegionDefine.getRegionSettingKey());
                showAddress = this.getShowAddress(designDataRegionDefine, designRegionSettingDefine.getRowNumberSetting());
            }
            designDataRegionDefine.setShowAddress(showAddress);
            this.nrDesignTimeController.updateDataRegionDefine(designDataRegionDefine);
        }
    }

    private DesignDataRegionDefine initRegionDefine(DesignDataRegionDefine designDataRegionDefine, RegionObj regionObj, String formUUID, String uuidTaskID, Map<String, Object> attributeMap, ObjectMapper mapper, JsonNode extendAttrs) throws Exception {
        designDataRegionDefine.setCode(regionObj.getCode());
        designDataRegionDefine.setTitle(regionObj.getTitle());
        designDataRegionDefine.setRowsInFloatRegion(regionObj.getRowsInFloatRegion());
        designDataRegionDefine.setRegionLeft(regionObj.getRegionLeft());
        designDataRegionDefine.setRegionRight(regionObj.getRegionRight());
        designDataRegionDefine.setRegionTop(regionObj.getRegionTop());
        designDataRegionDefine.setRegionBottom(regionObj.getRegionBottom());
        designDataRegionDefine.setFormKey(formUUID);
        designDataRegionDefine.setRegionKind(DataRegionKind.forValue((int)regionObj.getRegionKind()));
        designDataRegionDefine.setOwnerLevelAndId(regionObj.getOwnerLevelAndId());
        designDataRegionDefine.setRegionEnterNext(RegionEnterNext.forValue((int)regionObj.getRegionEnterNext()));
        designDataRegionDefine.setDisplayLevel(regionObj.getDisplayLevel());
        String floatID = null;
        int pageSize = -1;
        boolean allowDuplicateKey = false;
        if (designDataRegionDefine.getRegionKind() != DataRegionKind.DATA_REGION_SIMPLE) {
            String rowNumberSettingStr;
            Map rowNumberSettingMap;
            List saveDatas;
            JSONArray obj;
            String textValue;
            floatID = designDataRegionDefine.getInputOrderFieldKey();
            String checkFloatID = this.queryAndCreateFloatID(regionObj);
            if (null == floatID || !floatID.equals(checkFloatID)) {
                floatID = checkFloatID;
            }
            attributeMap.put("floatID", floatID);
            designDataRegionDefine.setInputOrderFieldKey(floatID);
            if (!this.nodeIsEmpty(extendAttrs)) {
                JsonNode attributeNode = extendAttrs.get("BizKeyFieldsID");
                if (attributeNode == null || StringUtils.isEmpty((String)attributeNode.textValue())) {
                    attributeMap.put("bizKeyOrderID", UUIDUtils.getKey());
                }
                boolean bl = allowDuplicateKey = !this.nodeIsEmpty(attributeNode = extendAttrs.get("AllowDuplicateKey")) && attributeNode.asBoolean();
                if (allowDuplicateKey) {
                    attributeMap.put("bizKeyOrderID", UUIDUtils.getKey());
                }
                attributeMap.put("GatherType", this.nodeIsEmpty(attributeNode = extendAttrs.get("GatherType")) ? -1 : attributeNode.asInt());
                attributeNode = extendAttrs.get("GatherFieldKeyStr");
                attributeMap.put("GatherFieldKeyStr", this.nodeIsEmpty(attributeNode) ? null : attributeNode.textValue());
                attributeNode = extendAttrs.get("PageSize");
                pageSize = this.nodeIsEmpty(attributeNode) ? -1 : attributeNode.asInt();
            }
            boolean showGatherDetailRows = false;
            if (extendAttrs.get("ShowGatherDetailRows") != null && !extendAttrs.get("ShowGatherDetailRows").isNull()) {
                showGatherDetailRows = extendAttrs.get("ShowGatherDetailRows").isNull() ? false : extendAttrs.get("ShowGatherDetailRows").asBoolean();
            }
            boolean showGatherDetailRowByOne = false;
            if (extendAttrs.get("ShowGatherDetailRowByOne") != null && !extendAttrs.get("ShowGatherDetailRowByOne").isNull()) {
                showGatherDetailRowByOne = extendAttrs.get("ShowGatherDetailRowByOne").isNull() ? false : extendAttrs.get("ShowGatherDetailRowByOne").asBoolean();
            }
            boolean showGatherSummaryRow = false;
            if (extendAttrs.get("ShowGatherSummaryRow") != null && !extendAttrs.get("ShowGatherSummaryRow").isNull()) {
                showGatherSummaryRow = extendAttrs.get("ShowGatherSummaryRow").isNull() ? false : extendAttrs.get("ShowGatherSummaryRow").asBoolean();
            }
            String gatherFieldsStr = "";
            if (extendAttrs.get("GatherFields") != null && !extendAttrs.get("GatherFields").isNull()) {
                String gatherFields;
                String string = gatherFields = extendAttrs.get("GatherFields").isNull() ? "" : extendAttrs.get("GatherFields").textValue();
                if (StringUtils.isNotEmpty((String)gatherFields)) {
                    gatherFieldsStr = gatherFields;
                }
            }
            String hideGatherFields = null;
            if (extendAttrs.get("HideGatherFields") != null && !extendAttrs.get("HideGatherFields").isNull() && !StringUtils.isEmpty((String)(textValue = extendAttrs.get("HideGatherFields").textValue()))) {
                hideGatherFields = textValue;
            }
            String cardRecord = "";
            if (extendAttrs.get("CardRecord") != null && !extendAttrs.get("CardRecord").isNull()) {
                cardRecord = extendAttrs.get("CardRecord").textValue();
            }
            String gatherFieldRanks = "";
            if (extendAttrs.get("GatherFieldRanks") != null && !extendAttrs.get("GatherFieldRanks").isNull()) {
                gatherFieldRanks = extendAttrs.get("GatherFieldRanks").isNull() ? "" : extendAttrs.get("GatherFieldRanks").textValue();
            }
            boolean canChangeRow = true;
            if (extendAttrs.get("CanChangeRow") != null && !extendAttrs.get("CanChangeRow").isNull()) {
                canChangeRow = extendAttrs.get("CanChangeRow").isNull() ? true : extendAttrs.get("CanChangeRow").asBoolean();
            }
            int maxRowCount = 0;
            if (extendAttrs.get("MaxRowCount") != null && !extendAttrs.get("MaxRowCount").isNull()) {
                maxRowCount = extendAttrs.get("MaxRowCount").isNull() ? 0 : extendAttrs.get("MaxRowCount").asInt();
            }
            int rowsInFloatRegion = 1;
            if (extendAttrs.get("RowsInFloatRegion") != null && !extendAttrs.get("RowsInFloatRegion").isNull()) {
                rowsInFloatRegion = extendAttrs.get("RowsInFloatRegion").isNull() ? 1 : extendAttrs.get("RowsInFloatRegion").asInt();
            }
            String sortFieldsList = "";
            if (extendAttrs.get("SortFieldsList") != null && !extendAttrs.get("SortFieldsList").isNull()) {
                sortFieldsList = extendAttrs.get("SortFieldsList").isNull() ? "" : extendAttrs.get("SortFieldsList").textValue();
            }
            String sortFieldsStr = "";
            if (StringUtils.isNotEmpty((String)sortFieldsList)) {
                sortFieldsStr = sortFieldsList;
            }
            String filterCondition = "";
            if (extendAttrs.get("FilterCondition") != null && !extendAttrs.get("FilterCondition").isNull()) {
                filterCondition = extendAttrs.get("FilterCondition").isNull() ? "" : extendAttrs.get("FilterCondition").textValue();
            }
            String readOnlyCondition = "";
            if (extendAttrs.get("ReadOnlyCondition") != null && !extendAttrs.get("ReadOnlyCondition").isNull()) {
                readOnlyCondition = extendAttrs.get("ReadOnlyCondition").isNull() ? "" : extendAttrs.get("ReadOnlyCondition").textValue();
            }
            String showAddress = "";
            if (extendAttrs.get("ShowAddress") != null && !extendAttrs.get("ShowAddress").isNull()) {
                showAddress = extendAttrs.get("ShowAddress").isNull() ? "" : extendAttrs.get("ShowAddress").textValue();
            }
            boolean isCanfold = false;
            if (extendAttrs.get("IsCanFold") != null && !extendAttrs.get("IsCanFold").isNull()) {
                isCanfold = extendAttrs.get("IsCanFold").isNull() ? false : extendAttrs.get("IsCanFold").asBoolean();
            }
            designDataRegionDefine.setIsCanFold(isCanfold);
            designDataRegionDefine.setPageSize(pageSize);
            designDataRegionDefine.setAllowDuplicateKey(allowDuplicateKey);
            designDataRegionDefine.setShowGatherDetailRows(showGatherDetailRows);
            designDataRegionDefine.setShowGatherSummaryRow(showGatherSummaryRow);
            designDataRegionDefine.setGatherFields(gatherFieldsStr);
            designDataRegionDefine.setHideZeroGatherFields(hideGatherFields);
            designDataRegionDefine.setGatherSetting(gatherFieldRanks);
            designDataRegionDefine.setCanInsertRow(canChangeRow);
            designDataRegionDefine.setCanDeleteRow(canChangeRow);
            designDataRegionDefine.setMaxRowCount(maxRowCount);
            designDataRegionDefine.setRowsInFloatRegion(rowsInFloatRegion);
            designDataRegionDefine.setSortFieldsList(sortFieldsStr);
            designDataRegionDefine.setFilterCondition(filterCondition);
            designDataRegionDefine.setReadOnlyCondition(readOnlyCondition);
            designDataRegionDefine.setShowGatherDetailRowByOne(showGatherDetailRowByOne);
            boolean newFlag = false;
            DesignRegionSettingDefine designRegionSettingDefine = null;
            if (designDataRegionDefine.getRegionSettingKey() != null) {
                designRegionSettingDefine = this.nrDesignTimeController.getRegionSetting(designDataRegionDefine.getRegionSettingKey());
            }
            if (designRegionSettingDefine == null) {
                newFlag = true;
                designRegionSettingDefine = new DesignRegionSettingDefineImpl();
                designRegionSettingDefine.setKey(UUIDUtils.getKey());
                designDataRegionDefine.setRegionSettingKey(designRegionSettingDefine.getKey());
            }
            String dictionaryFillLinks = "";
            if (extendAttrs.get("DictionaryFillLinks") != null && !extendAttrs.get("DictionaryFillLinks").isNull()) {
                dictionaryFillLinks = extendAttrs.get("DictionaryFillLinks").isNull() ? "" : extendAttrs.get("DictionaryFillLinks").textValue();
            }
            designRegionSettingDefine.setDictionaryFillLinks(dictionaryFillLinks);
            if (extendAttrs.get("RegionTabSettingDefines") != null && !extendAttrs.get("RegionTabSettingDefines").isNull()) {
                String regionTabSettingList = extendAttrs.get("RegionTabSettingDefines").toString();
                obj = new JSONArray(regionTabSettingList);
                saveDatas = new ArrayList();
                if (obj.length() > 0) {
                    saveDatas = (List)mapper.readValue(regionTabSettingList, (TypeReference)new TypeReference<List<RegionTabSettingObj>>(){});
                    ArrayList regionTabSettingDataList = new ArrayList();
                    for (Object regionTabSettingObj : saveDatas) {
                        RegionTabSettingData regionTabSettingData = new RegionTabSettingData();
                        if (UUIDUtils.isUUID((String)((RegionTabSettingObj)regionTabSettingObj).getID())) {
                            regionTabSettingData.setId(((RegionTabSettingObj)regionTabSettingObj).getID());
                        } else {
                            regionTabSettingData.setId(UUIDUtils.getKey());
                        }
                        regionTabSettingData.setTitle(((RegionTabSettingObj)regionTabSettingObj).getTitle());
                        regionTabSettingData.setDisplayCondition(((RegionTabSettingObj)regionTabSettingObj).getDisplayCondition());
                        regionTabSettingData.setFilterCondition(((RegionTabSettingObj)regionTabSettingObj).getFilterCondition());
                        regionTabSettingData.setBindingExpression(((RegionTabSettingObj)regionTabSettingObj).getBindingExpression());
                        regionTabSettingData.setOrder(OrderGenerator.newOrder());
                        regionTabSettingDataList.add(regionTabSettingData);
                    }
                    designRegionSettingDefine.setRegionTabSetting((List)regionTabSettingDataList);
                } else {
                    designRegionSettingDefine.setRegionTabSetting(new ArrayList());
                }
            }
            if (StringUtils.isEmpty((String)showAddress) && designDataRegionDefine.getShowGatherSummaryRow()) {
                showAddress = this.getShowAddress(designDataRegionDefine, designRegionSettingDefine.getRowNumberSetting());
            }
            designDataRegionDefine.setShowAddress(showAddress);
            if (extendAttrs.get("RowNumberSettings") != null && !extendAttrs.get("RowNumberSettings").isNull() && (rowNumberSettingMap = (Map)mapper.readValue(rowNumberSettingStr = extendAttrs.get("RowNumberSettings").isNull() ? "" : extendAttrs.get("RowNumberSettings").toString(), Map.class)) != null) {
                ArrayList<DesignRowNumberSettingImpl> rowNumberSettingList = new ArrayList<DesignRowNumberSettingImpl>();
                for (Object value : rowNumberSettingMap.values()) {
                    DesignRowNumberSettingImpl designRowNumberSettingImpl = new DesignRowNumberSettingImpl();
                    designRowNumberSettingImpl.setPosX(((Integer)value.get("posX")).intValue());
                    designRowNumberSettingImpl.setPosY(((Integer)value.get("posY")).intValue());
                    designRowNumberSettingImpl.setStartNumber(((Integer)value.get("startNumber")).intValue());
                    designRowNumberSettingImpl.setIncrement(((Integer)value.get("increment")).intValue());
                    rowNumberSettingList.add(designRowNumberSettingImpl);
                }
                designRegionSettingDefine.setRowNumberSetting(rowNumberSettingList);
            }
            if (extendAttrs.get("RegionEdgeStyleObjects") != null && !extendAttrs.get("RegionEdgeStyleObjects").isNull()) {
                String regionEdgeStyleList = extendAttrs.get("RegionEdgeStyleObjects").toString();
                obj = new JSONArray(regionEdgeStyleList);
                saveDatas = new ArrayList();
                if (obj.length() > 0) {
                    saveDatas = (List)mapper.readValue(regionEdgeStyleList, (TypeReference)new TypeReference<List<RegionEdgeStyleObj>>(){});
                    ArrayList<RegionEdgeStyleData> regionEdgeStyleDataList = new ArrayList<RegionEdgeStyleData>();
                    for (Object regionTabSettingObj : saveDatas) {
                        RegionEdgeStyleData regionEdgeStyleData = new RegionEdgeStyleData();
                        regionEdgeStyleData.setStartIndex(((RegionEdgeStyleObj)regionTabSettingObj).getStartIndex());
                        regionEdgeStyleData.setEndIndex(((RegionEdgeStyleObj)regionTabSettingObj).getEndIndex());
                        regionEdgeStyleData.setEdgeLineStyle(((RegionEdgeStyleObj)regionTabSettingObj).getEdgeStyle());
                        regionEdgeStyleData.setEdgeLineColor(((RegionEdgeStyleObj)regionTabSettingObj).getEdgeLineColorToInt());
                        regionEdgeStyleDataList.add(regionEdgeStyleData);
                    }
                    designRegionSettingDefine.setLastRowStyle(regionEdgeStyleDataList);
                } else {
                    designRegionSettingDefine.setLastRowStyle(new ArrayList());
                }
            }
            if (StringUtils.isNotEmpty((String)cardRecord)) {
                RecordCard parseObject = (RecordCard)JacksonUtils.jsonToObject((String)cardRecord, RecordCard.class);
                designRegionSettingDefine.setCardRecord(parseObject);
            } else {
                designRegionSettingDefine.setCardRecord(null);
            }
            String regionCardSurvey = regionObj.getRegionCardSurvey();
            if (StringUtils.isNotEmpty((String)regionCardSurvey)) {
                String rightRegionCardSurvey = this.checkRegionCardSurvey(regionObj, regionCardSurvey);
                designRegionSettingDefine.setRegionSurvey(rightRegionCardSurvey);
            } else {
                designRegionSettingDefine.setRegionSurvey(null);
            }
            if (extendAttrs.get("EntityDefaultValue") != null && !extendAttrs.get("EntityDefaultValue").asText().isEmpty()) {
                JsonNode defaultValueNode = extendAttrs.get("EntityDefaultValue");
                String defaultValue = defaultValueNode.asText();
                List valueObjs = (List)JacksonUtils.jsonToObject((String)defaultValue, (TypeReference)new TypeReference<List<EntityDefaultValueObj>>(){});
                List collect = valueObjs.stream().filter(Objects::nonNull).map(EntityDefaultValueObj::convert).collect(Collectors.toList());
                designRegionSettingDefine.setEntityDefaultValue(JacksonUtils.objectToJson(collect));
            }
            if (newFlag) {
                this.nrDesignTimeController.addRegionSetting(designRegionSettingDefine);
            } else {
                this.nrDesignTimeController.updateRegionSetting(designRegionSettingDefine);
            }
        }
        return designDataRegionDefine;
    }

    private String checkRegionCardSurvey(RegionObj regionObj, String cardSurvey) {
        String finalCardSurvey = "";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            SurveyModel surveyModel = (SurveyModel)objectMapper.readValue(cardSurvey, SurveyModel.class);
            if (surveyModel != null) {
                List surveyQuestions = SurveyModelLinkHelp.getAllSurveyQuestion((SurveyModel)surveyModel);
                List needHandleQuestions = surveyQuestions.stream().filter(surveyQuestion -> surveyQuestion.getType() == QuestionType.FILE).collect(Collectors.toList());
                if (CollectionUtils.isEmpty(needHandleQuestions)) {
                    return cardSurvey;
                }
                Map<String, LinkObj> links = regionObj.getLinks();
                for (SurveyQuestion needHandleQuestion : needHandleQuestions) {
                    byte[] bigData;
                    FileQuestion fileQuestion = (FileQuestion)needHandleQuestion.getQuestion();
                    String attachment = "";
                    LinkObj linkObj = links.get(fileQuestion.getLinkId());
                    if (linkObj != null) {
                        attachment = linkObj.getAttachment();
                    }
                    if (StringUtils.isEmpty((String)attachment) && (bigData = this.formDefineService.getBigData(fileQuestion.getLinkId(), "ATTACHMENT")) != null) {
                        attachment = DesignFormDefineBigDataUtil.bytesToString((byte[])bigData);
                    }
                    if (StringUtils.isNotEmpty((String)attachment)) {
                        JSONObject jsonObject = new JSONObject(attachment);
                        String maxNumberStr = jsonObject.getString("maxNumber");
                        if (StringUtils.isNotEmpty((String)maxNumberStr)) {
                            int maxNumber = Integer.parseInt(maxNumberStr);
                            if (maxNumber != fileQuestion.getMaxNums()) {
                                fileQuestion.setMaxNums(maxNumber);
                            }
                        } else if (fileQuestion.getMaxNums() != 0) {
                            fileQuestion.setMaxNums(0);
                        }
                        if (fileQuestion.getMaxNums() == 1) {
                            fileQuestion.setAllowMultiple(false);
                        } else {
                            fileQuestion.setAllowMultiple(true);
                        }
                        String maxSizeStr = jsonObject.getString("maxSize");
                        if (StringUtils.isNotEmpty((String)maxSizeStr)) {
                            int maxSize = Integer.parseInt(maxSizeStr);
                            int maxSizeByte = maxSize * 0x100000;
                            if (maxSizeByte != fileQuestion.getMaxSize()) {
                                fileQuestion.setMaxSize(maxSizeByte);
                            }
                        } else if (fileQuestion.getMaxSize() != 0) {
                            fileQuestion.setMaxSize(0);
                        }
                        ArrayList<JSONArray> arrays = new ArrayList<JSONArray>();
                        arrays.add(jsonObject.getJSONArray("document"));
                        arrays.add(jsonObject.getJSONArray("zip"));
                        arrays.add(jsonObject.getJSONArray("img"));
                        arrays.add(jsonObject.getJSONArray("vedio"));
                        arrays.add(jsonObject.getJSONArray("stadio"));
                        String acceptTypes = this.getAcceptTypes(arrays);
                        if (StringUtils.isNotEmpty((String)acceptTypes)) {
                            if (acceptTypes.equals(fileQuestion.getAcceptedTypes())) continue;
                            fileQuestion.setAcceptedTypes(acceptTypes);
                            continue;
                        }
                        fileQuestion.setAcceptedTypes("");
                        continue;
                    }
                    fileQuestion.setAcceptedTypes("");
                    fileQuestion.setAllowMultiple(true);
                    fileQuestion.setMaxNums(0);
                    fileQuestion.setMaxSize(0);
                }
                String style = this.regionSurveyHelper.toStyle(surveyModel);
                byte[] surveyData = DesignFormDefineBigDataUtil.StringToBytes((String)style);
                finalCardSurvey = DesignFormDefineBigDataUtil.bytesToString((byte[])surveyData);
            } else {
                finalCardSurvey = cardSurvey;
            }
        }
        catch (Exception e) {
            throw new RuntimeException("\u68c0\u67e5\u6d6e\u52a8\u533a\u57df\u95ee\u5377\u5931\u8d25", e);
        }
        return finalCardSurvey;
    }

    private String getAcceptTypes(List<JSONArray> objects) {
        StringBuilder acceptTypes = new StringBuilder();
        int count = 0;
        for (JSONArray object : objects) {
            String acceptType;
            if (count > 0) {
                acceptTypes.append(",");
            }
            if (!StringUtils.isNotEmpty((String)(acceptType = this.getAcceptType(object)))) continue;
            acceptTypes.append(acceptType);
            ++count;
        }
        return acceptTypes.toString();
    }

    private String getAcceptType(JSONArray objects) {
        StringBuilder acceptType = new StringBuilder();
        if (!objects.isEmpty()) {
            int count = 0;
            for (int i = 0; i < objects.length(); ++i) {
                String allowTypes = objects.getString(i);
                if (allowTypes.contains("/")) {
                    String[] allowType = allowTypes.split("/");
                    if (count > 0) {
                        acceptType.append(",");
                    }
                    acceptType.append(".");
                    acceptType.append(allowType[0]);
                    acceptType.append(",");
                    acceptType.append(".");
                    acceptType.append(allowType[1]);
                } else {
                    if (count > 0) {
                        acceptType.append(',');
                    }
                    acceptType.append(".");
                    acceptType.append(allowTypes);
                }
                ++count;
            }
        }
        return acceptType.toString();
    }

    private void initRegionDefineHelper(DesignDataRegionDefine designDataRegionDefine, Map<String, Object> attributeMap) throws JQException {
        if (designDataRegionDefine.getRegionKind() != DataRegionKind.DATA_REGION_SIMPLE) {
            List designTableDefines;
            String floatUUID = (String)attributeMap.get("floatID");
            if (floatUUID == null) {
                floatUUID = designDataRegionDefine.getInputOrderFieldKey();
                attributeMap.put("floatID", floatUUID);
            }
            if (designDataRegionDefine.getAllowDuplicateKey()) {
                attributeMap.put("bizKeyOrderID", UUIDUtils.getKey());
            }
            if ((designTableDefines = this.nrDesignTimeController.queryAllTableDefineInRegion(designDataRegionDefine.getKey(), false)) != null && designTableDefines.size() > 0) {
                DesignTableDefine designTableDefine = (DesignTableDefine)designTableDefines.get(0);
                attributeMap.put("bizKeyFieldsIDByTable", designTableDefine.getBizKeyFieldsStr());
            }
        }
    }

    private void saveLinkObject(Map<String, LinkObj> links, String regionUUID, List<String> useLessField) throws Exception {
        DesignDataLinkDefine designDataLinkDefine = null;
        LinkObj linkObj = null;
        for (Map.Entry<String, LinkObj> entry : links.entrySet()) {
            linkObj = entry.getValue();
            if (linkObj.isIsDeleted()) {
                this.nrDesignTimeController.deleteDataLinkDefine(linkObj.getID());
                continue;
            }
            if (linkObj.isIsNew() && !useLessField.contains(linkObj.getLinkExpression())) {
                designDataLinkDefine = this.nrDesignTimeController.createDataLinkDefine();
                designDataLinkDefine.setKey(linkObj.getID());
                designDataLinkDefine.setOwnerLevelAndId(this.serveCodeService.getServeCode());
                designDataLinkDefine = this.saveLinkObjectHelper(designDataLinkDefine, linkObj, regionUUID);
                this.nrDesignTimeController.insertDataLinkDefine(designDataLinkDefine);
                this.saveAttachment(linkObj);
                continue;
            }
            if (!linkObj.isIsDirty()) continue;
            designDataLinkDefine = this.nrDesignTimeController.queryDataLinkDefine(linkObj.getID());
            if (designDataLinkDefine == null) {
                designDataLinkDefine = this.nrDesignTimeController.createDataLinkDefine();
                designDataLinkDefine.setKey(linkObj.getID());
                designDataLinkDefine = this.saveLinkObjectHelper(designDataLinkDefine, linkObj, regionUUID);
                this.nrDesignTimeController.insertDataLinkDefine(designDataLinkDefine);
            } else {
                designDataLinkDefine = this.saveLinkObjectHelper(designDataLinkDefine, linkObj, regionUUID);
                this.nrDesignTimeController.updateDataLinkDefine(designDataLinkDefine);
            }
            this.saveAttachment(linkObj);
        }
    }

    private void saveAttachment(LinkObj linkObj) throws Exception {
        String linkId = linkObj.getID();
        this.formService.updateBigDataDefine(linkId, "ATTACHMENT", DesignFormDefineBigDataUtil.StringToBytes((String)linkObj.getAttachment()));
    }

    private DesignDataLinkDefine saveLinkObjectHelper(DesignDataLinkDefine designDataLinkDefine, LinkObj linkObj, String regionUUID) throws Exception {
        designDataLinkDefine.setTitle(linkObj.getTitle());
        designDataLinkDefine.setPosX(linkObj.getPosX());
        designDataLinkDefine.setPosY(linkObj.getPosY());
        designDataLinkDefine.setRowNum(linkObj.getRowNum());
        designDataLinkDefine.setColNum(linkObj.getColNum());
        designDataLinkDefine.setEnumLinkage(linkObj.getEnumLinkage());
        designDataLinkDefine.setEnumLinkageStatus(linkObj.getEnumLinkageStatus());
        designDataLinkDefine.setType(DataLinkType.forValue((int)linkObj.getIsFormulaOrField()));
        designDataLinkDefine.setEnumTitleField(linkObj.getEnumTitleField());
        String linkExpression = linkObj.getLinkExpression();
        designDataLinkDefine.setLinkExpression(linkObj.getLinkExpression());
        designDataLinkDefine.setUniqueCode(linkObj.getUniqueCode());
        designDataLinkDefine.setRegionKey(regionUUID);
        designDataLinkDefine.setEditMode(DataLinkEditMode.forValue((int)linkObj.getEditMode()));
        designDataLinkDefine.setDisplayMode(EnumDisplayMode.forValue((int)linkObj.getDisplayMode()));
        designDataLinkDefine.setAllowNotLeafNodeRefer(linkObj.isAllowNotLeafNodeRefer());
        designDataLinkDefine.setEnumCount(linkObj.getEnumCount());
        designDataLinkDefine.setAllowUndefinedCode(linkObj.isAllowUndefinedCode());
        designDataLinkDefine.setAllowNullAble(linkObj.isAllowNullAble());
        designDataLinkDefine.setCaptionFieldsString(linkObj.getCaptionFieldsKeys());
        designDataLinkDefine.setDropDownFieldsString(linkObj.getDropDownFieldsString());
        designDataLinkDefine.setEnumShowFullPath(linkObj.getShowFullPath());
        designDataLinkDefine.setFormatProperties(linkObj.getFormatProperties());
        designDataLinkDefine.setEnumPos(JacksonUtils.objectToJson(linkObj.getEnumPosMap()));
        if (linkObj.getFilterSettingType() != null && linkObj.getFilterSettingType() == 1) {
            designDataLinkDefine.setFilterExpression(linkObj.getFilterExpression());
            designDataLinkDefine.setFilterTemplate(null);
        } else {
            designDataLinkDefine.setFilterTemplate(linkObj.getFilterTemplate());
            designDataLinkDefine.setFilterExpression(null);
        }
        if (designDataLinkDefine.getType() == DataLinkType.DATA_LINK_TYPE_FORMULA) {
            if (linkObj.getMeasureUnit() == null) {
                designDataLinkDefine.setMeasureUnit("9493b4eb-6516-48a8-a878-25a63a23e63a;NotDimession");
            } else {
                designDataLinkDefine.setMeasureUnit(linkObj.getMeasureUnit());
            }
        } else {
            designDataLinkDefine.setMeasureUnit(null);
        }
        designDataLinkDefine.setIgnorePermissions(linkObj.isIgnorePermissions());
        designDataLinkDefine.setOrder(linkObj.getOrder());
        this.saveLinkDataValidationHelper(designDataLinkDefine, linkObj, linkExpression);
        return designDataLinkDefine;
    }

    private void saveLinkDataValidationHelper(DesignDataLinkDefine designDataLinkDefine, LinkObj linkObj, String linkExpression) throws Exception {
        if (linkObj.getDataValidation() != null && linkObj.getDataValidation().size() > 0) {
            DesignFieldDefine designFieldDefine = this.nrDesignTimeController.queryFieldDefine(linkExpression);
            ValidationObj validationObj = linkObj.getDataValidation().get(0);
            if (validationObj != null && validationObj.getDataValidationMap() != null) {
                ArrayList<String> validationExpressionList = new ArrayList<String>();
                for (Map.Entry<String, String> entry : validationObj.getDataValidationMap().entrySet()) {
                    DataValidationExpression dataValidationExpression;
                    String value = entry.getValue();
                    if (StringHelper.isNull((String)value) || "|".equals(value.trim()) || value.trim().startsWith("|") || value.trim().endsWith("|")) continue;
                    int dataValidationType = Integer.valueOf(entry.getKey());
                    if (dataValidationType == 1 || dataValidationType == 2) {
                        dataValidationExpression = DataValidationExpressionFactory.createExpression((FieldDefine)designFieldDefine, (CompareType)CompareType.fromType((int)dataValidationType), (Object)value.split("\\|")[0], (Object)(value.split("\\|").length > 1 ? value.split("\\|")[1] : Integer.valueOf(0)));
                        validationExpressionList.add(dataValidationExpression.toFormula());
                        continue;
                    }
                    dataValidationExpression = DataValidationExpressionFactory.createExpression((FieldDefine)designFieldDefine, (CompareType)CompareType.fromType((int)dataValidationType), (Object)value);
                    validationExpressionList.add(dataValidationExpression.toFormula());
                }
                designDataLinkDefine.setDataValidation(validationExpressionList);
            }
        }
    }

    private String queryAndCreateFloatID(RegionObj regionObj) {
        String floatID = null;
        Map<String, TableObject> tables = regionObj.getTables();
        if (tables != null) {
            Collection<TableObject> values = tables.values();
            for (TableObject table : values) {
                if (table == null) continue;
                String id = table.getID();
                try {
                    String tableKey = id;
                    DesignDataField field = this.iDesignDataSchemeService.getDataFieldByTableKeyAndCode(tableKey, FIELDFLOATORDER);
                    if (field == null) continue;
                    floatID = field.getKey();
                }
                catch (Exception e) {}
            }
        }
        if (floatID == null) {
            // empty if block
        }
        return floatID;
    }

    private boolean nodeIsEmpty(JsonNode node) {
        return node == null || node.isNull();
    }

    private String getShowAddress(DesignDataRegionDefine designDataRegionDefine, List<RowNumberSetting> rowNumberSettingList) {
        List designDataLinkDefineList = this.nrDesignTimeController.getAllLinksInRegion(designDataRegionDefine.getKey());
        String showAddress = designDataRegionDefine.getShowAddress();
        if (showAddress == null) {
            if (rowNumberSettingList != null) {
                int x = rowNumberSettingList.get(rowNumberSettingList.size() - 1).getPosX() + 1;
                int y = rowNumberSettingList.get(rowNumberSettingList.size() - 1).getPosY();
                String posXString = this.numberToLetter(x);
                if (posXString != null) {
                    showAddress = posXString + y;
                }
            } else {
                String posXString;
                int posX = 0;
                if (designDataLinkDefineList.size() != 0) {
                    posX = ((DesignDataLinkDefine)designDataLinkDefineList.get(0)).getPosX();
                }
                if ((posXString = this.numberToLetter(posX)) != null) {
                    showAddress = posXString + ((DesignDataLinkDefine)designDataLinkDefineList.get(0)).getPosY();
                }
            }
        }
        return showAddress;
    }

    private String numberToLetter(int num) {
        if (num <= 0) {
            return null;
        }
        String letter = "";
        --num;
        do {
            if (letter.length() > 0) {
                --num;
            }
            letter = (char)(num % 26 + 65) + letter;
        } while ((num = (num - num % 26) / 26) > 0);
        return letter;
    }

    private void dealFormEntitiesIsExtend(FormObj formObject, DesignFormDefine designFormDefine, String formGroupKey, Map<String, EntityChangeInfo> entityChangeMap, String formKey) throws JQException {
        designFormDefine.setMasterEntitiesKey(FormExtendPropsDefaultValue.MASTER_ENTITIES_KEY_EXTEND_VALUE);
    }

    private void dealFormMeasureUnitIsExtend(FormObj formObject, DesignFormDefine designFormDefine) throws JQException {
        try {
            if (formObject.getMeasureUnitIsExtend()) {
                designFormDefine.setMeasureUnit(FormExtendPropsDefaultValue.MEASURE_UNIT_EXTEND_VALUE);
            } else {
                designFormDefine.setMeasureUnit(formObject.getMeasureUnit());
            }
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_115, (Throwable)e);
        }
    }

    private void dealFillInAutomaticallyDue(FormObj formObject, DesignFormDefine designFormDefine) {
        if (formObject.isFillInAutomaticallyDueIsExtend()) {
            designFormDefine.setFillInAutomaticallyDue(new FillInAutomaticallyDue(FormExtendPropsDefaultValue.FILL_IN_AUTOMATICALLY_DUE));
        } else {
            designFormDefine.setFillInAutomaticallyDue(formObject.getFillInAutomaticallyDue());
        }
    }

    private void saveDataLinkMapping(String formKey, List<DataLinkMappingVO> content) throws Exception {
        this.iDesignTimeViewController.saveOrUpdateDataLinkMapping(formKey, content);
    }
}

