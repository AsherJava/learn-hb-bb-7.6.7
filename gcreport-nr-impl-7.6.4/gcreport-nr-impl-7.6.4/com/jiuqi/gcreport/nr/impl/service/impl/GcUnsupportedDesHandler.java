/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.StringUtils
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
 *  com.jiuqi.nr.data.logic.facade.param.input.CheckDesQueryParam
 *  com.jiuqi.nr.data.logic.facade.service.ICheckErrorDescriptionService
 *  com.jiuqi.nr.dataentry.copydes.CheckDesFmlObj
 *  com.jiuqi.nr.dataentry.copydes.HandleParam
 *  com.jiuqi.nr.dataentry.copydes.IUnsupportedDesHandler
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 */
package com.jiuqi.gcreport.nr.impl.service.impl;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.StringUtils;
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
import com.jiuqi.nr.data.logic.facade.param.input.CheckDesQueryParam;
import com.jiuqi.nr.data.logic.facade.service.ICheckErrorDescriptionService;
import com.jiuqi.nr.dataentry.copydes.CheckDesFmlObj;
import com.jiuqi.nr.dataentry.copydes.HandleParam;
import com.jiuqi.nr.dataentry.copydes.IUnsupportedDesHandler;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class GcUnsupportedDesHandler
implements IUnsupportedDesHandler {
    @Autowired
    private ICheckErrorDescriptionService iCheckErrorDescriptionService;
    @Autowired
    private DimCollectionBuildUtil dimCollectionBuildUtil;
    @Autowired
    private IFormulaRunTimeController iFormulaRunTimeController;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IDataAccessProvider iDataAccessProvider;
    @Autowired
    private IDataDefinitionRuntimeController iDataDefinitionRuntimeController;
    private Logger logger = LoggerFactory.getLogger(GcUnsupportedDesHandler.class);
    private final String ID = "ID";

    public int handleUnsupportedDes(HandleParam par) {
        Map unsupportedDstDesMap = par.getUnsupportedDstDesMap();
        if (CollectionUtils.isEmpty(unsupportedDstDesMap)) {
            return 0;
        }
        int saveCount = 0;
        try {
            saveCount = this.batchSaveFormulaCheckDes(par, unsupportedDstDesMap);
        }
        catch (Exception e) {
            this.logger.error("\u6d6e\u52a8\u8868\u5141\u8bb8\u91cd\u7801\u51fa\u9519\u8bf4\u660e\u540c\u6b65\u5f02\u5e38", e);
        }
        return saveCount;
    }

    private int batchSaveFormulaCheckDes(HandleParam par, Map<String, List<CheckDesFmlObj>> unsupportedDstDesMap) {
        int saveCount = 0;
        HashMap<String, String> recordKeyGroupByTarget = new HashMap<String, String>();
        HashMap<String, Set<String>> recordKeyGroupByDimValue = new HashMap<String, Set<String>>();
        HashMap<String, DataRegionDefine> regionGroupByFormulaExpressionKey = new HashMap<String, DataRegionDefine>();
        HashMap<String, Map<String, IParsedExpression>> iParsedExpressionMapGroupByFormKey = new HashMap<String, Map<String, IParsedExpression>>();
        for (List<CheckDesFmlObj> checkDesFmlObjs : unsupportedDstDesMap.values()) {
            ArrayList<CheckDesFmlObj> checkDesObjs = new ArrayList<CheckDesFmlObj>();
            ArrayList<String> formIds = new ArrayList<String>();
            Map<String, DimensionValue> dimensionSetTarget = null;
            for (CheckDesFmlObj checkDesFmlObj : checkDesFmlObjs) {
                Map dimensionSet = checkDesFmlObj.getDimensionSet();
                StringBuilder builder = new StringBuilder(checkDesFmlObj.getFormulaSchemeKey()).append(checkDesFmlObj.getFormKey());
                String srcRecordKey = ((DimensionValue)dimensionSet.get("ID")).getValue();
                dimensionSetTarget = this.getDimensionValue(dimensionSet, builder);
                Map<String, IParsedExpression> iParsedExpressionMap = this.getIParsedExpressionGroupByFormKey(par.getDstFmlSchemeKey(), checkDesFmlObj.getFormKey(), iParsedExpressionMapGroupByFormKey);
                DataRegionDefine dataRegionDefine = this.getDataRegionDefine(regionGroupByFormulaExpressionKey, iParsedExpressionMap, checkDesFmlObj.getFormulaExpressionKey());
                String targetRecordKey = this.getRecordKey(par.getDstFormSchemeKey(), par.getDstFmlSchemeKey(), checkDesFmlObj.getFormKey(), dimensionSetTarget, dataRegionDefine, builder.toString(), recordKeyGroupByDimValue, srcRecordKey, recordKeyGroupByTarget);
                if (StringUtils.isEmpty((String)targetRecordKey)) continue;
                DimensionValue dimValue = (DimensionValue)dimensionSet.get("ID");
                dimValue.setValue(targetRecordKey);
                dimensionSet.put("ID", dimValue);
                checkDesObjs.add(checkDesFmlObj);
                if (formIds.contains(checkDesFmlObj.getFormKey())) continue;
                formIds.add(checkDesFmlObj.getFormKey());
            }
            CheckDesBatchSaveObj checkDesBatchSaveObj = new CheckDesBatchSaveObj();
            checkDesBatchSaveObj.setCheckDesObjs(checkDesObjs);
            checkDesBatchSaveObj.setUpdateCurUsrTime(par.isUpdateUserTime());
            CheckDesQueryParam checkDesQueryParam = new CheckDesQueryParam();
            checkDesQueryParam.setFormulaSchemeKey(Arrays.asList(par.getDstFmlSchemeKey()));
            checkDesQueryParam.setFormKey(formIds);
            checkDesQueryParam.setDimensionCollection(this.dimCollectionBuildUtil.buildDimensionCollection(dimensionSetTarget, par.getDstFormSchemeKey()));
            checkDesBatchSaveObj.setCheckDesQueryParam(checkDesQueryParam);
            this.iCheckErrorDescriptionService.batchSaveFormulaCheckDes(checkDesBatchSaveObj);
            saveCount += checkDesObjs.size();
        }
        return saveCount;
    }

    private Map<String, DimensionValue> getDimensionValue(Map<String, DimensionValue> checkDesDimension, StringBuilder builder) {
        HashMap<String, DimensionValue> dimensionSetTarget = new HashMap<String, DimensionValue>();
        for (String dimCode : checkDesDimension.keySet()) {
            if (dimCode.equals("ID")) continue;
            DimensionValue dimValue = checkDesDimension.get(dimCode);
            dimensionSetTarget.put(dimCode, dimValue);
            if (!Objects.nonNull(dimValue)) continue;
            builder.append(dimCode).append(";").append(dimValue.getValue());
        }
        return dimensionSetTarget;
    }

    private DataRegionDefine getDataRegionDefine(Map<String, DataRegionDefine> regionGroupByFormulaExpressionKey, Map<String, IParsedExpression> iParsedExpressionMap, String formulaExpressionKey) {
        DataRegionDefine dataRegionDefine = regionGroupByFormulaExpressionKey.get(formulaExpressionKey);
        if (!Objects.isNull(dataRegionDefine)) {
            return dataRegionDefine;
        }
        IParsedExpression iParsedExpression = iParsedExpressionMap.get(formulaExpressionKey);
        Map<String, DataRegionDefine> regionMap = this.buildRegionMap(iParsedExpression);
        IExpression iExpression = iParsedExpression.getRealExpression();
        String regionKey = this.findRegionKeyFromExpression(iExpression, regionMap);
        dataRegionDefine = regionMap.isEmpty() ? this.runTimeViewController.queryDataRegionDefine(regionKey) : regionMap.get(regionKey);
        regionGroupByFormulaExpressionKey.put(formulaExpressionKey, dataRegionDefine);
        return dataRegionDefine;
    }

    private String findRegionKeyFromExpression(IExpression rootExpr, Map<String, DataRegionDefine> regions) {
        for (IASTNode node : rootExpr.getChild(0)) {
            DataModelLinkColumn link;
            if (!(node instanceof DynamicDataNode) || (link = ((DynamicDataNode)node).getDataModelLink()) == null || StringUtils.isEmpty((String)link.getRegion())) continue;
            String currentRegion = link.getRegion();
            if (regions.isEmpty()) {
                return currentRegion;
            }
            if (!regions.containsKey(currentRegion)) continue;
            return currentRegion;
        }
        return "";
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

    private Map<String, IParsedExpression> getIParsedExpressionGroupByFormKey(String dstFmlSchemeKey, String formKey, Map<String, Map<String, IParsedExpression>> iParsedExpressionMapGroupByFormKey) {
        if (iParsedExpressionMapGroupByFormKey.containsKey(formKey)) {
            return iParsedExpressionMapGroupByFormKey.get(formKey);
        }
        List curCheckExpressions = this.iFormulaRunTimeController.getParsedExpressionByForms(dstFmlSchemeKey, Arrays.asList(formKey), DataEngineConsts.FormulaType.CHECK);
        Map<String, IParsedExpression> iParsedExpressionMap = curCheckExpressions.stream().collect(Collectors.toMap(IParsedExpression::getKey, iParsedExpression -> iParsedExpression));
        iParsedExpressionMapGroupByFormKey.put(formKey, iParsedExpressionMap);
        return iParsedExpressionMap;
    }

    private String getRecordKey(String formSchemeKey, String formulaSchemeKey, String formKey, Map<String, DimensionValue> dimensionSetTarget, DataRegionDefine dataRegionDefine, String dimRecordKey, Map<String, Set<String>> recordKeyGroupByDimValue, String srcRecordKey, Map<String, String> recordKeyGroupByTarget) {
        if (recordKeyGroupByTarget.containsKey(srcRecordKey)) {
            return recordKeyGroupByTarget.get(srcRecordKey);
        }
        Set<String> recordKeys = this.listRecordKey(formSchemeKey, formulaSchemeKey, formKey, dimensionSetTarget, dataRegionDefine, dimRecordKey, recordKeyGroupByDimValue);
        for (String key : recordKeys) {
            if (recordKeyGroupByTarget.values().contains(key)) continue;
            recordKeyGroupByTarget.put(srcRecordKey, key);
            return key;
        }
        return null;
    }

    private Set<String> listRecordKey(String formSchemeKey, String formulaSchemeKey, String formKey, Map<String, DimensionValue> dimensionSetTarget, DataRegionDefine dataRegionDefine, String dimRecordKey, Map<String, Set<String>> recordKeyGroupByDimValue) {
        if (recordKeyGroupByDimValue.containsKey(dimRecordKey)) {
            return recordKeyGroupByDimValue.get(dimRecordKey);
        }
        HashSet<String> recordKeys = new HashSet<String>();
        try {
            QueryEnvironment queryEnvironment = new QueryEnvironment();
            queryEnvironment.setFormSchemeKey(formSchemeKey);
            queryEnvironment.setRegionKey(dataRegionDefine.getKey());
            queryEnvironment.setFormulaSchemeKey(formulaSchemeKey);
            queryEnvironment.setFormKey(formKey);
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
                return null;
            }
            for (int index = 0; index < dataTable.getCount(); ++index) {
                String targetRecordKey = String.valueOf(dataTable.getItem(index).getRowKeys().getValue("RECORDKEY"));
                recordKeys.add(targetRecordKey);
            }
            recordKeyGroupByDimValue.put(dimRecordKey, recordKeys);
        }
        catch (Exception e) {
            this.logger.error("\u6d6e\u52a8\u8868\u51fa\u9519\u8bf4\u660e\u540c\u6b65\uff0c\u83b7\u53d6\u6d6e\u52a8\u884cid\u6570\u636e\u65f6\u5f02\u5e38", e);
        }
        return recordKeys;
    }
}

