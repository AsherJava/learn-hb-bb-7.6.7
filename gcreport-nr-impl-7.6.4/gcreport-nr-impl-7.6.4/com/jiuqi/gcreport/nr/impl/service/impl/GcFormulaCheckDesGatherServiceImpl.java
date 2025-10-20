/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.util.JsonUtils
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.nr.vo.GcFormulaCheckDesGatherParam
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.QueryEnvironment
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaType
 *  com.jiuqi.np.dataengine.common.DataModelLinkColumn
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataTable
 *  com.jiuqi.np.dataengine.node.DynamicDataNode
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.access.util.DimCollectionBuildUtil
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.data.logic.facade.param.input.CheckDesBatchSaveObj
 *  com.jiuqi.nr.data.logic.facade.param.input.CheckDesObj
 *  com.jiuqi.nr.data.logic.facade.param.input.CheckDesQueryParam
 *  com.jiuqi.nr.data.logic.facade.param.output.CheckDescription
 *  com.jiuqi.nr.data.logic.facade.service.ICheckErrorDescriptionService
 *  com.jiuqi.nr.data.logic.internal.util.CheckResultUtil
 *  com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.unit.treeimpl.web.CaptionFieldSettingsController
 *  com.jiuqi.nr.unit.treestore.fmdmdisplay.bean.FMDMCaptionFieldSettingInfo
 *  com.jiuqi.nr.unit.treestore.fmdmdisplay.bean.FMDMCaptionFiledInfo
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.nr.impl.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.common.util.JsonUtils;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.nr.impl.service.GcFormulaCheckDesGatherService;
import com.jiuqi.gcreport.nr.impl.uploadstate.util.FormUploadStateTool;
import com.jiuqi.gcreport.nr.impl.uploadstate.vo.DimensionParamsVO;
import com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils;
import com.jiuqi.gcreport.nr.vo.GcFormulaCheckDesGatherParam;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.QueryEnvironment;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DataModelLinkColumn;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataTable;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.access.util.DimCollectionBuildUtil;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.data.logic.facade.param.input.CheckDesBatchSaveObj;
import com.jiuqi.nr.data.logic.facade.param.input.CheckDesObj;
import com.jiuqi.nr.data.logic.facade.param.input.CheckDesQueryParam;
import com.jiuqi.nr.data.logic.facade.param.output.CheckDescription;
import com.jiuqi.nr.data.logic.facade.service.ICheckErrorDescriptionService;
import com.jiuqi.nr.data.logic.internal.util.CheckResultUtil;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.unit.treeimpl.web.CaptionFieldSettingsController;
import com.jiuqi.nr.unit.treestore.fmdmdisplay.bean.FMDMCaptionFieldSettingInfo;
import com.jiuqi.nr.unit.treestore.fmdmdisplay.bean.FMDMCaptionFiledInfo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GcFormulaCheckDesGatherServiceImpl
implements GcFormulaCheckDesGatherService {
    @Autowired
    private ICheckErrorDescriptionService iCheckErrorDescriptionService;
    @Autowired
    private DimCollectionBuildUtil dimCollectionBuildUtil;
    @Autowired
    private IFormulaRunTimeController iFormulaRunTimeController;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IRuntimeDataSchemeService iRuntimeDataSchemeService;
    @Autowired
    private IDataAccessProvider iDataAccessProvider;
    @Autowired
    private IDataDefinitionRuntimeController iDataDefinitionRuntimeController;
    private Logger logger = LoggerFactory.getLogger(GcFormulaCheckDesGatherServiceImpl.class);
    private final String ORGSHOWFIELDCODE = "CODE";
    private final String ID = "ID";

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void formulaCheckDesGather(GcFormulaCheckDesGatherParam formulaCheckDesGatherParam, AsyncTaskMonitor asyncTaskMonitor) {
        JtableContext jtableContext = formulaCheckDesGatherParam.getJtableContext();
        String orgCategory = GcOrgTypeUtils.getOrgTypeByContextOrTaskId(jtableContext.getTaskKey());
        Map dimensionSet = jtableContext.getDimensionSet();
        String dataTime = ((DimensionValue)dimensionSet.get("DATATIME")).getValue();
        YearPeriodObject yearPeriodObject = new YearPeriodObject(jtableContext.getFormSchemeKey(), dataTime);
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)Objects.requireNonNull(orgCategory), (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yearPeriodObject);
        GcOrgCacheVO targetOrg = tool.getOrgByCode(formulaCheckDesGatherParam.getTargetUnitCode());
        List<String> formKeys = this.getFormKeys(dimensionSet, formulaCheckDesGatherParam, jtableContext, targetOrg, orgCategory);
        if (CollectionUtils.isEmpty(formKeys)) {
            this.logger.warn("\u76ee\u6807\u5355\u4f4d:" + formulaCheckDesGatherParam.getTargetUnitCode() + ",\u9009\u62e9\u8868\u5355\u5747\u5df2\u4e0a\u62a5,\u8868\u5355:" + formulaCheckDesGatherParam.getFormIds());
            return;
        }
        formulaCheckDesGatherParam.setFormIds(formKeys);
        List gatherOrgs = formulaCheckDesGatherParam.getOrgIds();
        HashMap<String, GcOrgCacheVO> gcOrgCacheGroupByCode = new HashMap<String, GcOrgCacheVO>();
        this.setProgressAndMessage(asyncTaskMonitor, 0.1, GcI18nUtil.getMessage((String)"gc.org.nr.formulaCheckDes.gather.getGatherOrgInfo"));
        for (String orgId : gatherOrgs) {
            GcOrgCacheVO gatherOrg = tool.getOrgByCode(orgId);
            if (!Objects.nonNull(gcOrgCacheGroupByCode)) continue;
            gcOrgCacheGroupByCode.put(orgId, gatherOrg);
        }
        this.setProgressAndMessage(asyncTaskMonitor, 0.2, GcI18nUtil.getMessage((String)"gc.org.nr.formulaCheckDes.gather.getGatherOrg"));
        DimensionCollection dimensionCollection = this.getDimensionCollection(dimensionSet, gcOrgCacheGroupByCode.keySet(), null, jtableContext.getFormSchemeKey());
        CheckDesQueryParam checkDesQueryParam = this.getCheckDesQueryParam(jtableContext.getFormulaSchemeKey(), formulaCheckDesGatherParam.getFormIds(), dimensionCollection);
        this.setProgressAndMessage(asyncTaskMonitor, 0.3, GcI18nUtil.getMessage((String)"gc.org.nr.formulaCheckDes.gather.getOrgCheckDesMessage"));
        List checkDesObjs = this.iCheckErrorDescriptionService.queryFormulaCheckDes(checkDesQueryParam);
        if (CollectionUtils.isEmpty((Collection)checkDesObjs)) {
            this.logger.warn("\u5355\u4f4d\u8303\u56f4:" + gatherOrgs.toString() + ",\u83b7\u53d6\u6c47\u603b\u5355\u4f4d\u51fa\u9519\u8bf4\u660e\u4fe1\u606f\u4e3a\u7a7a...");
            return;
        }
        this.setProgressAndMessage(asyncTaskMonitor, 0.45, GcI18nUtil.getMessage((String)"gc.org.nr.formulaCheckDes.gather.getTargetOrgCheckDesSaveParms"));
        CheckDesQueryParam targetUnitCheckDesQueryParam = this.getCheckDesQueryParamByTargetUnitCode(jtableContext, targetOrg, formulaCheckDesGatherParam.getFormIds());
        this.setProgressAndMessage(asyncTaskMonitor, 0.5, GcI18nUtil.getMessage((String)"gc.org.nr.formulaCheckDes.gather.getTargetOrgCheckDesMessage"));
        CheckDesBatchSaveObj checkDesBatchSaveObj = this.getCheckDesBatchSaveObj(targetUnitCheckDesQueryParam, checkDesObjs, dimensionSet, targetOrg, gcOrgCacheGroupByCode, jtableContext.getFormSchemeKey());
        this.setProgressAndMessage(asyncTaskMonitor, 0.7, GcI18nUtil.getMessage((String)"gc.org.nr.formulaCheckDes.gather.getTargetOrgCheckDesSave"));
        if (Objects.nonNull(checkDesBatchSaveObj)) {
            checkDesBatchSaveObj.setUpdateCurUsrTime(true);
            this.iCheckErrorDescriptionService.batchSaveFormulaCheckDes(checkDesBatchSaveObj);
        }
    }

    private CheckDesQueryParam getCheckDesQueryParamByTargetUnitCode(JtableContext jtableContext, GcOrgCacheVO targetOrg, List<String> formIds) {
        HashSet<String> codes = new HashSet<String>();
        codes.add(targetOrg.getCode());
        DimensionCollection dimensionCollection = this.getDimensionCollection(jtableContext.getDimensionSet(), codes, targetOrg.getOrgTypeId(), jtableContext.getFormSchemeKey());
        CheckDesQueryParam checkDesQueryParam = this.getCheckDesQueryParam(jtableContext.getFormulaSchemeKey(), formIds, dimensionCollection);
        return checkDesQueryParam;
    }

    private CheckDesQueryParam getCheckDesQueryParam(String formulaSchemeKey, List<String> formIds, DimensionCollection dimensionCollection) {
        CheckDesQueryParam checkDesQueryParam = new CheckDesQueryParam();
        checkDesQueryParam.setFormulaSchemeKey(Arrays.asList(formulaSchemeKey));
        checkDesQueryParam.setFormKey(formIds);
        checkDesQueryParam.setDimensionCollection(dimensionCollection);
        return checkDesQueryParam;
    }

    private DimensionCollection getDimensionCollection(Map<String, DimensionValue> dimensionSet, Set<String> orgCodes, String orgTypeId, String formSchemeKey) {
        Map<String, DimensionValue> dimensionSetCopy = this.getDimensionValue(dimensionSet, orgCodes.stream().collect(Collectors.joining(";")), orgTypeId);
        DimensionCollection dimensionCollection = this.dimCollectionBuildUtil.buildDimensionCollection(dimensionSetCopy, formSchemeKey);
        return dimensionCollection;
    }

    private Map<String, DimensionValue> getDimensionValue(Map<String, DimensionValue> dimensionSet, String orgCode, String orgTypeId) {
        Map dimensionSetCopy = (Map)JsonUtils.readValue((String)JsonUtils.writeValueAsString(dimensionSet), (TypeReference)new TypeReference<Map<String, DimensionValue>>(){});
        ((DimensionValue)dimensionSetCopy.get("MD_ORG")).setValue(orgCode);
        if (dimensionSetCopy.containsKey("MD_GCORGTYPE")) {
            ((DimensionValue)dimensionSetCopy.get("MD_GCORGTYPE")).setValue(orgTypeId);
        }
        return dimensionSetCopy;
    }

    private CheckDesBatchSaveObj getCheckDesBatchSaveObj(CheckDesQueryParam checkDesQueryParam, List<CheckDesObj> checkDesObjs, Map<String, DimensionValue> dimensionSet, GcOrgCacheVO targetOrg, Map<String, GcOrgCacheVO> gcOrgCacheTitleGroupByCode, String formSchemeKey) {
        CheckDesBatchSaveObj checkDesBatchSaveObj = new CheckDesBatchSaveObj();
        checkDesBatchSaveObj.setCheckDesQueryParam(checkDesQueryParam);
        List<CheckDesObj> targetCheckDesObjs = this.listCheckDesObj(checkDesQueryParam, checkDesObjs, dimensionSet, targetOrg, gcOrgCacheTitleGroupByCode, formSchemeKey);
        if (CollectionUtils.isEmpty(targetCheckDesObjs)) {
            return null;
        }
        checkDesBatchSaveObj.setCheckDesObjs(targetCheckDesObjs);
        return checkDesBatchSaveObj;
    }

    private List<String> getFormKeys(Map<String, DimensionValue> dimensionSet, GcFormulaCheckDesGatherParam formulaCheckDesGatherParam, JtableContext jtableContext, GcOrgCacheVO targetOrg, String orgCategory) {
        DimensionValue adjustDim;
        DimensionParamsVO params = new DimensionParamsVO();
        params.setTaskId(jtableContext.getTaskKey());
        params.setSchemeId(jtableContext.getFormSchemeKey());
        params.setOrgId(targetOrg.getCode());
        params.setOrgType(Objects.requireNonNull(orgCategory));
        params.setOrgTypeId(targetOrg.getOrgTypeId());
        params.setPeriodStr(dimensionSet.get("DATATIME").getValue());
        DimensionValue currencyDim = dimensionSet.get("MD_CURRENCY");
        if (Objects.nonNull(currencyDim)) {
            params.setCurrency(currencyDim.getValue());
            params.setCurrencyId(currencyDim.getValue());
        }
        if (Objects.nonNull(adjustDim = dimensionSet.get("ADJUST"))) {
            params.setSelectAdjustCode(adjustDim.getValue());
        }
        List<ReadWriteAccessDesc> readWriteAccessDescs = FormUploadStateTool.getInstance().writeableExcludeFormLocked(params, formulaCheckDesGatherParam.getFormIds());
        ArrayList<String> formKeys = new ArrayList<String>();
        List gatherFormKeys = formulaCheckDesGatherParam.getFormIds();
        for (int i = 0; i < gatherFormKeys.size(); ++i) {
            ReadWriteAccessDesc accessDesc = readWriteAccessDescs.get(i);
            if (!accessDesc.getAble().booleanValue()) continue;
            formKeys.add((String)gatherFormKeys.get(i));
        }
        return formKeys;
    }

    private void setProgressAndMessage(AsyncTaskMonitor asyncTaskMonitor, double progress, String message) {
        if (Objects.nonNull(asyncTaskMonitor)) {
            asyncTaskMonitor.progressAndMessage(progress, message);
            if (asyncTaskMonitor.isCancel()) {
                asyncTaskMonitor.canceled(GcI18nUtil.getMessage((String)"gc.org.nr.formulaCheckDes.gather.taskCancel"), (Object)GcI18nUtil.getMessage((String)"gc.org.nr.formulaCheckDes.gather.taskCancelMessage"));
            }
        }
    }

    private String getShowFieldCode(String formSchemeKey) {
        CaptionFieldSettingsController captionFieldSettingsController = (CaptionFieldSettingsController)SpringContextUtils.getBean(CaptionFieldSettingsController.class);
        FMDMCaptionFieldSettingInfo inquireCaptionFields = captionFieldSettingsController.inquireCaptionFields(formSchemeKey);
        if (Objects.isNull(inquireCaptionFields) || CollectionUtils.isEmpty((Collection)inquireCaptionFields.getCaptionFields())) {
            return "CODE";
        }
        Optional<FMDMCaptionFiledInfo> fmdmCaptionFiledInfo = inquireCaptionFields.getCaptionFields().stream().filter(item -> item.isChecked()).findFirst();
        String fieldCode = "CODE";
        if (fmdmCaptionFiledInfo.isPresent()) {
            fieldCode = fmdmCaptionFiledInfo.get().getCode();
        }
        return fieldCode;
    }

    private DataRegionDefine getDataRegionDefine(Map<String, DataRegionDefine> regionGroupByFormulaExpressionKey, Map<String, IParsedExpression> iParsedExpressionMap, String formulaExpressionKey, Map<String, DataTable> dataTableGroupByRegionKey) {
        DataRegionDefine dataRegionDefine = regionGroupByFormulaExpressionKey.get(formulaExpressionKey);
        if (!Objects.isNull(dataRegionDefine)) {
            return dataRegionDefine;
        }
        IParsedExpression iParsedExpression = iParsedExpressionMap.get(formulaExpressionKey);
        Map<String, DataRegionDefine> regionMap = this.buildRegionMap(iParsedExpression);
        IExpression iExpression = iParsedExpression.getRealExpression();
        String regionKey = "";
        String columModelKey = "";
        for (IASTNode node : iExpression.getChild(0)) {
            DataModelLinkColumn column;
            if (!(node instanceof DynamicDataNode) || (column = ((DynamicDataNode)node).getDataModelLink()) == null || StringUtils.isEmpty((String)column.getRegion())) continue;
            String currentRegion = column.getRegion();
            if (regionMap.isEmpty()) {
                regionKey = currentRegion;
                columModelKey = column.getColumModel().getID();
                break;
            }
            if (!regionMap.containsKey(currentRegion)) continue;
            regionKey = currentRegion;
            columModelKey = column.getColumModel().getID();
            break;
        }
        dataRegionDefine = regionMap.isEmpty() ? this.runTimeViewController.queryDataRegionDefine(regionKey) : regionMap.get(regionKey);
        DataField dataField = this.iRuntimeDataSchemeService.getDataFieldByColumnKey(columModelKey);
        DataTable dataTable = this.iRuntimeDataSchemeService.getDataTable(dataField.getDataTableKey());
        dataTableGroupByRegionKey.put(regionKey, dataTable);
        regionGroupByFormulaExpressionKey.put(formulaExpressionKey, dataRegionDefine);
        return dataRegionDefine;
    }

    private Map<String, DataRegionDefine> buildRegionMap(IParsedExpression iParsedExpression) {
        String formKey = iParsedExpression.getFormKey();
        if (StringUtils.isEmpty((String)formKey)) {
            return Collections.emptyMap();
        }
        List allRegionsInForm = this.runTimeViewController.getAllRegionsInForm(formKey);
        if (Objects.isNull(allRegionsInForm)) {
            throw new BusinessRuntimeException("\u6d6e\u52a8\u8868\u4e2d\u4e0d\u5b58\u5728\u533a\u57df\uff0cformKey:" + formKey);
        }
        return allRegionsInForm.stream().collect(Collectors.toMap(IBaseMetaItem::getKey, Function.identity()));
    }

    private boolean getRepeatCode(DataRegionDefine dataRegionDefine, Map<String, DataTable> dataTableGroupByRegionKey) {
        boolean repeatCodeFlag = false;
        if (dataRegionDefine.getRegionKind() != DataRegionKind.DATA_REGION_SIMPLE) {
            DataTable dataTable = dataTableGroupByRegionKey.get(dataRegionDefine.getKey());
            Boolean repeatCode = dataTable.getRepeatCode();
            repeatCodeFlag = Objects.nonNull(dataTable.getRepeatCode()) ? repeatCode : false;
        }
        return repeatCodeFlag;
    }

    private Map<String, DimensionValue> getDimensionValue(Map<String, DimensionValue> checkDesDimension, Map<String, DimensionValue> dimensionSet, GcOrgCacheVO targetOrg, boolean repeatCodeFlag, StringBuilder builder) {
        Map<String, DimensionValue> dimensionSetTarget = this.getDimensionValue(dimensionSet, targetOrg.getCode(), targetOrg.getOrgTypeId());
        for (String dimCode : checkDesDimension.keySet()) {
            DimensionValue dimensionValue;
            if (dimCode.equals("ID") && repeatCodeFlag || !Objects.isNull(dimensionValue = dimensionSetTarget.get(dimCode))) continue;
            DimensionValue dimValue = checkDesDimension.get(dimCode);
            dimensionSetTarget.put(dimCode, dimValue);
            builder.append(dimCode).append(";").append(dimValue.getValue());
        }
        return dimensionSetTarget;
    }

    private String getFloatTableRecordKey(String formSchemeKey, String formulaSchemeKey, DataRegionDefine dataRegionDefine, Map<String, DimensionValue> dimensionSetTarget, boolean repeatCodeFlag) {
        String recordKey = null;
        if (repeatCodeFlag) {
            try {
                QueryEnvironment queryEnvironment = new QueryEnvironment();
                queryEnvironment.setFormSchemeKey(formSchemeKey);
                queryEnvironment.setRegionKey(dataRegionDefine.getKey());
                queryEnvironment.setFormulaSchemeKey(formulaSchemeKey);
                queryEnvironment.setFormKey(dataRegionDefine.getFormKey());
                IDataQuery dataQuery = this.iDataAccessProvider.newDataQuery(queryEnvironment);
                ExecutorContext context = new ExecutorContext(this.iDataDefinitionRuntimeController);
                context.setUseDnaSql(false);
                context.setVarDimensionValueSet(DimensionValueSetUtil.getDimensionValueSet(dimensionSetTarget));
                List fieldKeys = this.runTimeViewController.getFieldKeysInRegion(dataRegionDefine.getKey());
                FieldDefine field = this.runTimeViewController.queryFieldDefine((String)fieldKeys.get(0));
                dataQuery.addColumn(field);
                dataQuery.setMasterKeys(DimensionValueSetUtil.getDimensionValueSet(dimensionSetTarget));
                IDataTable dataTable = dataQuery.executeQuery(context);
                int count = dataTable.getCount();
                if (count <= 0) {
                    return recordKey;
                }
                recordKey = String.valueOf(dataTable.getItem(0).getRowKeys().getValue("RECORDKEY"));
            }
            catch (Exception e) {
                this.logger.error("\u6d6e\u52a8\u8868\u51fa\u9519\u8bf4\u660e\u6c47\u603b\u5931\u8d25\uff0c\u83b7\u53d6\u6d6e\u52a8\u884cid\u6570\u636e\u65f6\u5f02\u5e38", e);
            }
        }
        return recordKey;
    }

    private List<CheckDesObj> listCheckDesObj(CheckDesQueryParam checkDesQueryParam, List<CheckDesObj> checkDesObjs, Map<String, DimensionValue> dimensionSet, GcOrgCacheVO targetOrg, Map<String, GcOrgCacheVO> gcOrgCacheTitleGroupByCode, String formSchemeKey) {
        HashMap<String, CheckDesObj> checkDesObjGroupByRecordUUIDKey = new HashMap<String, CheckDesObj>();
        List curCheckExpressions = this.iFormulaRunTimeController.getParsedExpressionByForms((String)checkDesQueryParam.getFormulaSchemeKey().get(0), checkDesQueryParam.getFormKey(), DataEngineConsts.FormulaType.CHECK);
        Map<String, IParsedExpression> iParsedExpressionMap = curCheckExpressions.stream().collect(Collectors.toMap(IParsedExpression::getKey, iParsedExpression -> iParsedExpression));
        HashMap<String, DataRegionDefine> regionGroupByFormulaExpressionKey = new HashMap<String, DataRegionDefine>();
        HashMap<String, DataTable> dataTableGroupByRegionKey = new HashMap<String, DataTable>();
        String fieldCode = this.getShowFieldCode(formSchemeKey);
        for (CheckDesObj checkDesObj : checkDesObjs) {
            String recordUUIDKey;
            StringBuilder builder = new StringBuilder();
            builder.append(checkDesObj.getFormulaSchemeKey()).append(";").append(checkDesObj.getFormKey()).append(";").append(checkDesObj.getFormulaExpressionKey().substring(0, 36)).append(";").append(DataEngineConsts.FormulaType.CHECK.getValue()).append(";").append(checkDesObj.getGlobRow()).append(";").append(checkDesObj.getGlobCol());
            String orgCode = ((DimensionValue)checkDesObj.getDimensionSet().get("MD_ORG")).getValue();
            GcOrgCacheVO orgCacheVO = gcOrgCacheTitleGroupByCode.get(orgCode);
            Map checkDim = checkDesObj.getDimensionSet();
            DimensionValue orgTypeValue = (DimensionValue)checkDim.get("MD_GCORGTYPE");
            if (Objects.nonNull(orgTypeValue) && !StringUtils.isEmpty((String)orgTypeValue.getValue()) && !orgTypeValue.getValue().equals(orgCacheVO.getOrgTypeId())) continue;
            String description = "";
            if (CollectionUtils.isEmpty((Collection)orgCacheVO.getChildren())) {
                description = orgCacheVO.getBaseFieldValue(fieldCode) + ":";
            }
            String formulaExpressionKey = checkDesObj.getFormulaExpressionKey();
            DataRegionDefine dataRegionDefine = this.getDataRegionDefine(regionGroupByFormulaExpressionKey, iParsedExpressionMap, formulaExpressionKey, dataTableGroupByRegionKey);
            boolean repeatCodeFlag = this.getRepeatCode(dataRegionDefine, dataTableGroupByRegionKey);
            CheckDescription gatherCheckDescription = checkDesObj.getCheckDescription();
            gatherCheckDescription.setDescription(description + gatherCheckDescription.getDescription());
            Map<String, DimensionValue> dimensionSetTarget = this.getDimensionValue(checkDesObj.getDimensionSet(), dimensionSet, targetOrg, repeatCodeFlag, builder);
            if (repeatCodeFlag) {
                String id = this.getFloatTableRecordKey(formSchemeKey, checkDesObj.getFormulaSchemeKey(), dataRegionDefine, dimensionSetTarget, repeatCodeFlag);
                if (StringUtils.isEmpty((String)id)) continue;
                DimensionValue dimValue = new DimensionValue();
                dimValue.setName("ID");
                dimValue.setValue(id);
                dimensionSetTarget.put("ID", dimValue);
                builder.append("ID").append(";").append(dimValue.getValue());
            }
            if (!checkDesObjGroupByRecordUUIDKey.containsKey(recordUUIDKey = CheckResultUtil.toFakeUUID((String)builder.toString()).toString())) {
                checkDesObj.setRecordId(null);
                checkDesObj.setDimensionSet(dimensionSetTarget);
                checkDesObj.setCheckDescription(gatherCheckDescription);
                checkDesObjGroupByRecordUUIDKey.put(recordUUIDKey, checkDesObj);
                continue;
            }
            CheckDesObj checkDesObjTargetUnit = (CheckDesObj)checkDesObjGroupByRecordUUIDKey.get(recordUUIDKey);
            CheckDescription checkDescription = checkDesObjTargetUnit.getCheckDescription();
            checkDescription.setDescription(checkDescription.getDescription() + ";" + gatherCheckDescription.getDescription());
            checkDesObjTargetUnit.setCheckDescription(checkDescription);
        }
        List<CheckDesObj> targetCheckDesObjs = checkDesObjGroupByRecordUUIDKey.values().stream().filter(item -> {
            CheckDescription checkDescription = item.getCheckDescription();
            if (Objects.isNull(checkDescription) || StringUtils.isEmpty((String)checkDescription.getDescription())) {
                return false;
            }
            String description = checkDescription.getDescription();
            if (description.length() >= 172) {
                checkDescription.setDescription(description.substring(0, 169) + "...");
            }
            return true;
        }).collect(Collectors.toList());
        return targetCheckDesObjs;
    }
}

