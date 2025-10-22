/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaType
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataservice.core.access.AuthType
 *  com.jiuqi.nr.dataservice.core.access.DataPermissionEvaluator
 *  com.jiuqi.nr.dataservice.core.access.EvaluatorParam
 *  com.jiuqi.nr.dataservice.core.access.MergeDataPermission
 *  com.jiuqi.nr.dataservice.core.access.ResouceType
 *  com.jiuqi.nr.dataservice.core.access.UnitPermission
 *  com.jiuqi.nr.dataservice.core.common.DimensionMapInfo
 *  com.jiuqi.nr.dataservice.core.common.DimensionMappingConverter
 *  com.jiuqi.nr.dataservice.core.common.IProviderStore
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue
 *  com.jiuqi.nr.dataservice.core.dimension.build.FixedDimBuilder
 *  com.jiuqi.nr.dataservice.core.dimension.build.SpecificDimBuilder
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 */
package com.jiuqi.nr.data.logic.internal.impl.ckd.copy;

import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.logic.api.ICKDCopyService;
import com.jiuqi.nr.data.logic.api.param.CheckDesFmlObj;
import com.jiuqi.nr.data.logic.api.param.FmlMappingObj;
import com.jiuqi.nr.data.logic.api.param.ckdcopy.CopyDesParam;
import com.jiuqi.nr.data.logic.api.param.ckdcopy.CopyDesResult;
import com.jiuqi.nr.data.logic.api.param.ckdcopy.UnsupportHandleParam;
import com.jiuqi.nr.data.logic.api.param.ckdcopy.UnsupportHandleResult;
import com.jiuqi.nr.data.logic.facade.param.input.CheckDesBatchSaveObj;
import com.jiuqi.nr.data.logic.facade.param.input.CheckDesObj;
import com.jiuqi.nr.data.logic.facade.param.input.CheckDesQueryParam;
import com.jiuqi.nr.data.logic.facade.param.output.CheckDescription;
import com.jiuqi.nr.data.logic.internal.impl.DataLogicServiceFactory;
import com.jiuqi.nr.data.logic.internal.impl.ckd.copy.DimMapper;
import com.jiuqi.nr.data.logic.internal.impl.ckd.copy.EmptyCopyDesMonitor;
import com.jiuqi.nr.data.logic.internal.impl.ckd.copy.FmlMapper;
import com.jiuqi.nr.data.logic.internal.obj.EntityData;
import com.jiuqi.nr.data.logic.internal.util.DimensionUtil;
import com.jiuqi.nr.data.logic.spi.ICKDCopyOptionProvider;
import com.jiuqi.nr.data.logic.spi.ICopyDesMonitor;
import com.jiuqi.nr.data.logic.spi.IFmlMappingProvider;
import com.jiuqi.nr.data.logic.spi.IUnsupportedDesHandler;
import com.jiuqi.nr.dataservice.core.access.AuthType;
import com.jiuqi.nr.dataservice.core.access.DataPermissionEvaluator;
import com.jiuqi.nr.dataservice.core.access.EvaluatorParam;
import com.jiuqi.nr.dataservice.core.access.MergeDataPermission;
import com.jiuqi.nr.dataservice.core.access.ResouceType;
import com.jiuqi.nr.dataservice.core.access.UnitPermission;
import com.jiuqi.nr.dataservice.core.common.DimensionMapInfo;
import com.jiuqi.nr.dataservice.core.common.DimensionMappingConverter;
import com.jiuqi.nr.dataservice.core.common.IProviderStore;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue;
import com.jiuqi.nr.dataservice.core.dimension.build.FixedDimBuilder;
import com.jiuqi.nr.dataservice.core.dimension.build.SpecificDimBuilder;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class CKDCopyServiceImpl
implements ICKDCopyService {
    private static final Logger log = LoggerFactory.getLogger(CKDCopyServiceImpl.class);
    private static final int DW_SPLIT_SIZE = 1000;
    private final DataLogicServiceFactory serviceFactory;
    private final IProviderStore providerStore;
    private final ICKDCopyOptionProvider optionProvider;
    private DimensionMappingConverter srcDimConverter;
    private IFmlMappingProvider srcFmlMappingProvider;
    private IUnsupportedDesHandler unsupportedDesHandler;

    public CKDCopyServiceImpl(DataLogicServiceFactory serviceFactory, IProviderStore providerStore, ICKDCopyOptionProvider optionProvider) {
        this.serviceFactory = serviceFactory;
        this.providerStore = providerStore;
        this.optionProvider = optionProvider;
        this.initUnsupportedHandler(serviceFactory, optionProvider);
    }

    private void initUnsupportedHandler(DataLogicServiceFactory serviceFactory, ICKDCopyOptionProvider optionProvider) {
        if (serviceFactory.getUnsupportedDesHandler() != null) {
            this.unsupportedDesHandler = serviceFactory.getUnsupportedDesHandler();
        }
        if (optionProvider.getUnsupportedDesHandler() != null) {
            this.unsupportedDesHandler = optionProvider.getUnsupportedDesHandler();
        }
        if (this.unsupportedDesHandler == null) {
            this.unsupportedDesHandler = (par, monitor) -> {
                UnsupportHandleResult result = new UnsupportHandleResult();
                result.setSuccessNum(0);
                return result;
            };
        }
    }

    @Override
    public CopyDesResult copy(CopyDesParam param) {
        return this.copy(param, EmptyCopyDesMonitor.getInstance());
    }

    @Override
    public CopyDesResult copy(CopyDesParam param, @NonNull ICopyDesMonitor monitor) {
        this.validParam(param);
        Context context = this.init(param, monitor);
        CopyDesResult copyDesResult = new CopyDesResult();
        if (context.initError) {
            return copyDesResult;
        }
        List<DimensionCollection> srcSplitDims = CKDCopyServiceImpl.splitDims(context);
        for (DimensionCollection srcSplitDim : srcSplitDims) {
            context.setSrcDimensionCollection(srcSplitDim);
            this.doCopy(context);
        }
        return copyDesResult;
    }

    @NonNull
    private static List<DimensionCollection> splitDims(Context context) {
        List dimensionCombinations = context.srcDimensionCollection.getDimensionCombinations();
        if (CollectionUtils.isEmpty(dimensionCombinations)) {
            throw new IllegalArgumentException("\u6765\u6e90\u7ef4\u5ea6\u4e3a\u7a7a");
        }
        DimensionValueSet merge = DimensionUtil.merge(dimensionCombinations.stream().map(DimensionCombination::toDimensionValueSet).collect(Collectors.toList()));
        Object value = merge.getValue(context.srcMainDimName);
        ArrayList<DimensionValueSet> splitDims = new ArrayList<DimensionValueSet>();
        if (value instanceof List) {
            List dws = (List)value;
            int totalSize = dws.size();
            merge.clearValue(context.srcMainDimName);
            for (int i = 0; i < totalSize; i += 1000) {
                int end = Math.min(i + 1000, totalSize);
                DimensionValueSet clearedDims = new DimensionValueSet(merge);
                clearedDims.setValue(context.srcMainDimName, new ArrayList(dws.subList(i, end)));
                splitDims.add(clearedDims);
            }
        }
        ArrayList<DimensionCollection> srcSplitDims = new ArrayList<DimensionCollection>(splitDims.size());
        if (splitDims.size() > 1) {
            for (DimensionValueSet splitDim : splitDims) {
                DimensionCollection dimensionCollection = CKDCopyServiceImpl.getDimensionCollection(splitDim);
                srcSplitDims.add(dimensionCollection);
            }
        } else {
            srcSplitDims.add(context.srcDimensionCollection);
        }
        return srcSplitDims;
    }

    @NonNull
    private void doCopy(Context context) {
        MergeDataPermission srcAccessFormInfos = this.getSrcFormReadAccessInfos(context.srcFormScheme, context.srcDimensionCollection, context.srcFormKeys);
        if (CollectionUtils.isEmpty(srcAccessFormInfos.getAccessResources())) {
            context.monitor.error(String.format("\u6765\u6e90\u5355\u4f4d%s\u5bf9\u62a5\u8868%s\u65e0\u6743\u9650", context.srcDimensionCollection.getDimensionValue(context.srcMainDimName), context.srcFormKeys));
            return;
        }
        this.setExpInfo(context);
        CopyData copyData = this.getCopyData(context, srcAccessFormInfos);
        this.handleSupport(context, copyData.saveData, copyData.saveDimCartesian);
        this.handleUnsupport(context, copyData.unsupportedSrcDes, copyData.unsupportedDstDesMap, copyData.unsupportedNum);
    }

    private static DimensionCollection getDimensionCollection(DimensionValueSet splitDim) {
        DimensionCollectionBuilder dimensionCollectionBuilder = new DimensionCollectionBuilder();
        for (int i = 0; i < splitDim.size(); ++i) {
            dimensionCollectionBuilder.setValue(splitDim.getName(i), new Object[]{splitDim.getValue(i)});
        }
        return dimensionCollectionBuilder.getCollection();
    }

    @NonNull
    private CopyData getCopyData(Context context, MergeDataPermission srcAccessFormInfos) {
        ArrayList<CheckDesObj> saveData = new ArrayList<CheckDesObj>();
        HashMap<String, Set<String>> saveDimCartesian = new HashMap<String, Set<String>>();
        ArrayList<CheckDesFmlObj> unsupportedSrcDes = new ArrayList<CheckDesFmlObj>();
        HashMap<String, List<CheckDesFmlObj>> unsupportedDstDesMap = new HashMap<String, List<CheckDesFmlObj>>();
        int unsupportedNum = 0;
        for (UnitPermission accessForm : srcAccessFormInfos.getAccessResources()) {
            Map dimensions = accessForm.getMasterKey();
            List formKeys = accessForm.getResourceIds();
            List<CheckDesObj> checkDesObjs = this.querySrcCkds(dimensions, context.srcFormScheme.getKey(), context.srcFormulaSchemeKey, formKeys);
            if (CollectionUtils.isEmpty(checkDesObjs)) {
                String msg = String.format("\u6765\u6e90\u7ef4\u5ea6%s\u3001\u62a5\u8868%s\u4e0b\u672a\u627e\u5230\u51fa\u9519\u8bf4\u660e\u6570\u636e", dimensions, formKeys);
                context.monitor.info(msg);
                continue;
            }
            MappedInfo mappedInfo = this.getMappedInfo(checkDesObjs, context);
            List<CKDMappedObj> mappedCkds = mappedInfo.mappedCkds;
            if (CollectionUtils.isEmpty(mappedInfo.mappedCkds) || CollectionUtils.isEmpty(mappedInfo.desDimCartesian)) {
                context.monitor.error(String.format("\u6765\u6e90\u7ef4\u5ea6%s\u3001\u62a5\u8868%s\u4e0b\u6ca1\u6709\u6620\u5c04\u6210\u529f\u7684\u8868\u5185\u516c\u5f0f\u8bf4\u660e\u6570\u636e", dimensions, formKeys));
                continue;
            }
            DimensionCollection desDimensionCollection = CKDCopyServiceImpl.getDimensionCollection(mappedInfo.desDimCartesian);
            DataPermissionEvaluator desAccessFormInfos = this.getDesAccessFormInfos(context.desFormScheme, desDimensionCollection, mappedInfo.desFormKeys);
            for (CKDMappedObj ckdMappedObj : mappedCkds) {
                CheckDesObj mappedCkd = ckdMappedObj.desObj;
                String formKey = mappedCkd.getFormKey();
                DimensionCombination masterKey = new DimensionCombinationBuilder(mappedCkd.getDimensionValueSet(context.desDimNames)).getCombination();
                if (this.formWriteAccess(desAccessFormInfos, masterKey, formKey, mappedInfo.desFormKeys)) {
                    if (this.supported(mappedCkd)) {
                        context.monitor.info("\u8bf4\u660e\u7684\u76ee\u6807\u62a5\u8868\u6709\u6743\u9650\uff0c\u4e14\u8bf4\u660e\u5c5e\u4e8e\u56fa\u5b9a\u8868\u6216\u4e0d\u5141\u8bb8\u91cd\u7801\u6570\u636e\u8868\u2014\u2014" + ckdMappedObj.mappedInfo);
                        saveData.add(mappedCkd);
                        this.addDim(saveDimCartesian, masterKey);
                        continue;
                    }
                    context.monitor.info("\u8bf4\u660e\u7684\u76ee\u6807\u62a5\u8868\u6709\u6743\u9650\uff0c\u4e14\u8bf4\u660e\u5c5e\u4e8e\u5141\u8bb8\u91cd\u7801\u6570\u636e\u8868\u2014\u2014" + ckdMappedObj.mappedInfo);
                    ++unsupportedNum;
                    CheckDesObj srcObj = ckdMappedObj.srcObj;
                    IParsedExpression desExp = context.srcExpressions.get(this.getExpMapKey(mappedCkd.getFormulaExpressionKey().substring(0, 36), mappedCkd.getFormKey(), mappedCkd.getGlobRow(), mappedCkd.getGlobCol()));
                    if (unsupportedDstDesMap.containsKey(srcObj.getRecordId())) {
                        ((List)unsupportedDstDesMap.get(srcObj.getRecordId())).add(new CheckDesFmlObj(mappedCkd, desExp));
                        continue;
                    }
                    IParsedExpression srcExp = context.srcExpressions.get(this.getExpMapKey(srcObj.getFormulaExpressionKey().substring(0, 36), srcObj.getFormKey(), srcObj.getGlobRow(), srcObj.getGlobCol()));
                    unsupportedSrcDes.add(new CheckDesFmlObj(srcObj, srcExp));
                    ArrayList<CheckDesFmlObj> checkDesFmlObjs = new ArrayList<CheckDesFmlObj>(3);
                    checkDesFmlObjs.add(new CheckDesFmlObj(mappedCkd, desExp));
                    unsupportedDstDesMap.put(srcObj.getRecordId(), checkDesFmlObjs);
                    continue;
                }
                context.monitor.info("\u8bf4\u660e\u7684\u76ee\u6807\u62a5\u8868\u65e0\u6743\u9650\u2014\u2014" + ckdMappedObj.mappedInfo);
            }
        }
        return new CopyData(saveData, saveDimCartesian, unsupportedSrcDes, unsupportedDstDesMap, unsupportedNum);
    }

    private void addDim(Map<String, Set<String>> targetDimCartesian, DimensionValueSet tarDim) {
        for (int i = 0; i < tarDim.size(); ++i) {
            String name = tarDim.getName(i);
            Object value = tarDim.getValue(i);
            if (targetDimCartesian.containsKey(name)) {
                targetDimCartesian.get(name).add(String.valueOf(value));
                continue;
            }
            HashSet<String> values = new HashSet<String>();
            values.add(String.valueOf(value));
            targetDimCartesian.put(name, values);
        }
    }

    private void addDim(Map<String, Set<String>> targetDimCartesian, DimensionCombination tarDim) {
        for (FixedDimensionValue fixedDimensionValue : tarDim) {
            if (targetDimCartesian.containsKey(fixedDimensionValue.getName())) {
                targetDimCartesian.get(fixedDimensionValue.getName()).add(String.valueOf(fixedDimensionValue.getValue()));
                continue;
            }
            HashSet<String> values = new HashSet<String>();
            values.add(String.valueOf(fixedDimensionValue.getValue()));
            targetDimCartesian.put(fixedDimensionValue.getName(), values);
        }
    }

    private void handleSupport(Context context, List<CheckDesObj> saveData, Map<String, Set<String>> targetDimCartesian) {
        if (CollectionUtils.isEmpty(saveData)) {
            return;
        }
        CheckDesBatchSaveObj checkDesBatchSaveObj = new CheckDesBatchSaveObj();
        CheckDesQueryParam checkDesQueryParam = new CheckDesQueryParam();
        checkDesQueryParam.setFormulaSchemeKey(Collections.singletonList(context.desFormulaSchemeKey));
        DimensionCollection dimensionCollection = CKDCopyServiceImpl.getDimensionCollection(targetDimCartesian);
        checkDesQueryParam.setDimensionCollection(dimensionCollection);
        checkDesBatchSaveObj.setCheckDesQueryParam(checkDesQueryParam);
        checkDesBatchSaveObj.setCheckDesObjs(saveData);
        this.serviceFactory.getCheckErrorDescriptionService().batchSaveFormulaCheckDes(checkDesBatchSaveObj);
        context.monitor.info(String.format("\u56fa\u5b9a\u8868\u53ca\u4e0d\u5141\u8bb8\u91cd\u7801\u6570\u636e\u8868\u4e14\u6709\u6620\u5c04\u6709\u6743\u9650\u7684\u51fa\u9519\u8bf4\u660e\u590d\u5236\u6210\u529f%s\u6761", saveData.size()));
    }

    private static DimensionCollection getDimensionCollection(Map<String, Set<String>> targetDimCartesian) {
        DimensionCollectionBuilder dimensionCollectionBuilder = new DimensionCollectionBuilder();
        for (Map.Entry<String, Set<String>> e : targetDimCartesian.entrySet()) {
            dimensionCollectionBuilder.setValue(e.getKey(), new Object[]{new ArrayList(e.getValue())});
        }
        return dimensionCollectionBuilder.getCollection();
    }

    private void handleUnsupport(Context context, List<CheckDesFmlObj> unsupportedSrcDes, Map<String, List<CheckDesFmlObj>> unsupportedDstDesMap, int unsupportedNum) {
        if (CollectionUtils.isEmpty(unsupportedSrcDes)) {
            return;
        }
        UnsupportHandleParam unsupportHandleParam = new UnsupportHandleParam();
        unsupportHandleParam.setSrcFormSchemeKey(context.srcFormScheme.getKey());
        unsupportHandleParam.setSrcFmlSchemeKey(context.srcFormulaSchemeKey);
        unsupportHandleParam.setDstFormSchemeKey(context.desFormScheme.getKey());
        unsupportHandleParam.setDstFmlSchemeKey(context.desFormulaSchemeKey);
        unsupportHandleParam.setUnsupportedSrcDes(unsupportedSrcDes);
        unsupportHandleParam.setUnsupportedDstDesMap(unsupportedDstDesMap);
        unsupportHandleParam.setCkdCopyOptionProvider(this.optionProvider);
        unsupportHandleParam.setProviderStore(this.providerStore);
        UnsupportHandleResult unsupportHandleResult = this.unsupportedDesHandler.handleUnsupportedDes(unsupportHandleParam, context.monitor);
        context.monitor.info(String.format("\u5141\u8bb8\u91cd\u7801\u6570\u636e\u8868\u4e14\u6709\u6620\u5c04\u6709\u6743\u9650\u7684\u51fa\u9519\u8bf4\u660e\u5171%s\u6761\uff0c\u590d\u5236\u6210\u529f%s\u6761", unsupportedNum, unsupportHandleResult.getSuccessNum()));
    }

    private void setExpInfo(Context context) {
        Map<String, IParsedExpression> desExpressionMap = this.getExpressionMap(context.desFormulaSchemeKey);
        Map<String, IParsedExpression> srcExpressionMap = this.getExpressionMap(context.srcFormulaSchemeKey);
        context.setDesExpressions(desExpressionMap);
        context.setSrcExpressions(srcExpressionMap);
    }

    private Map<String, IParsedExpression> getExpressionMap(String formulaSchemeKey) {
        List desExpressions = this.serviceFactory.getFormulaRunTimeController().getParsedExpressionByForm(formulaSchemeKey, null, DataEngineConsts.FormulaType.CHECK);
        return desExpressions.stream().collect(Collectors.toMap(o -> {
            String formKey = o.getFormKey();
            if (!StringUtils.hasText(formKey)) {
                formKey = "00000000-0000-0000-0000-000000000000";
            }
            IExpression realExpression = o.getRealExpression();
            return this.getExpMapKey(o.getSource().getId(), formKey, realExpression.getWildcardRow(), realExpression.getWildcardCol());
        }, Function.identity(), (o1, o2) -> o1));
    }

    private String getExpMapKey(String formulaKey, String formulaFormKey, int globRow, int globCol) {
        return Strings.join(Arrays.asList(formulaKey, formulaFormKey, String.valueOf(globRow), String.valueOf(globCol)), ';');
    }

    private boolean supported(CheckDesObj checkDesObj) {
        DimensionValue bizKeyOrder = checkDesObj.getDimensionSet().get("ID");
        return bizKeyOrder == null || !StringUtils.hasText(bizKeyOrder.getValue()) || "null".equals(bizKeyOrder.getValue());
    }

    private boolean formWriteAccess(DataPermissionEvaluator desAccessFormInfos, DimensionCombination masterKey, String formKey, Collection<String> desFormKeys) {
        if (formKey == null || "00000000-0000-0000-0000-000000000000".equals(formKey)) {
            for (String fsAllForm : desFormKeys) {
                if (!desAccessFormInfos.haveAccess(masterKey, fsAllForm, AuthType.SYS_WRITEABLE)) continue;
                return true;
            }
        } else {
            return desAccessFormInfos.haveAccess(masterKey, formKey, AuthType.SYS_WRITEABLE);
        }
        return false;
    }

    private MappedInfo getMappedInfo(List<CheckDesObj> checkDesObjs, Context context) {
        List<String> srcDimNames = this.serviceFactory.getEntityUtil().getFmSchemeEntities(context.srcFormScheme).stream().map(EntityData::getDimensionName).collect(Collectors.toList());
        ArrayList<CKDMappedObj> mappedCkds = new ArrayList<CKDMappedObj>();
        HashMap<String, Set<String>> desDimCartesian = new HashMap<String, Set<String>>();
        HashSet<String> desFormKeys = new HashSet<String>();
        for (CheckDesObj srcCkd : checkDesObjs) {
            DimensionValueSet srcDim = srcCkd.getDimensionValueSet(srcDimNames);
            List mappingMasterKey = this.srcDimConverter.getMappingMasterKey(srcDim);
            if (CollectionUtils.isEmpty(mappingMasterKey)) {
                context.monitor.error(String.format("\u8bf4\u660e\u6765\u6e90\u7ef4\u5ea6%s\u672a\u627e\u5230\u7ef4\u5ea6\u6620\u5c04\u503c", srcDim));
                continue;
            }
            FmlMappingObj srcFml = new FmlMappingObj(srcCkd.getFormulaExpressionKey().substring(0, 36), srcCkd.getFormKey(), srcCkd.getGlobRow(), srcCkd.getGlobCol());
            FmlMappingObj desFml = this.srcFmlMappingProvider.getMappedFml(srcFml);
            if (desFml == null) {
                context.monitor.error(String.format("\u8bf4\u660e\u6765\u6e90\u516c\u5f0f%s\u672a\u627e\u5230\u516c\u5f0f\u6620\u5c04\u503c", srcFml));
                continue;
            }
            IParsedExpression desExpression = context.desExpressions.get(desFml.toString());
            if (desExpression == null) {
                context.monitor.error(String.format("\u8bf4\u660e\u76ee\u6807\u516c\u5f0f%s\u672a\u627e\u5230", desFml));
                continue;
            }
            HashMap<String, DimensionValue> rowDim = new HashMap<String, DimensionValue>(srcCkd.getDimensionSet());
            srcDimNames.forEach(rowDim::remove);
            for (DimensionValueSet desDim : mappingMasterKey) {
                CheckDesObj desCkd = new CheckDesObj();
                Map<String, DimensionValue> desDimensionSet = DimensionUtil.getDimensionSet(desDim);
                desDimensionSet.putAll(rowDim);
                desCkd.setDimensionSet(desDimensionSet);
                desCkd.setFloatId(srcCkd.getFloatId());
                desCkd.setFormulaSchemeKey(context.desFormulaSchemeKey);
                desCkd.setFormKey(desFml.getFormulaFormKey());
                desCkd.setFormulaExpressionKey(desExpression.getKey());
                desCkd.setFormulaCode(desExpression.getSource().getCode());
                desCkd.setGlobRow(desFml.getGlobRow());
                desCkd.setGlobCol(desFml.getGlobCol());
                desCkd.setRecordId(null);
                CheckDescription checkDescription = new CheckDescription();
                checkDescription.setDescription(srcCkd.getCheckDescription().getDescription());
                desCkd.setCheckDescription(checkDescription);
                if (this.optionProvider.updateUserTime()) {
                    checkDescription.setUpdateTime(context.curTime);
                    checkDescription.setUserId(context.userId);
                    checkDescription.setUserNickName(context.userNickName);
                } else {
                    checkDescription.setUpdateTime(srcCkd.getCheckDescription().getUpdateTime());
                    checkDescription.setUserId(srcCkd.getCheckDescription().getUserId());
                    checkDescription.setUserNickName(srcCkd.getCheckDescription().getUserNickName());
                }
                String mappedInfo = String.format("\u6765\u6e90\u516c\u5f0f:%s,\u884c\u901a\u914d%s,\u5217\u901a\u914d%s,\u6240\u5c5e\u62a5\u8868%s-\u76ee\u6807\u516c\u5f0f:%s,\u884c\u901a\u914d%s,\u5217\u901a\u914d%s,\u6240\u5c5e\u62a5\u8868%s;\u6765\u6e90\u7ef4\u5ea6:%s,\u76ee\u6807\u7ef4\u5ea6%s", srcCkd.getFormulaCode(), srcCkd.getGlobRow(), srcCkd.getGlobCol(), srcCkd.getFormKey(), desCkd.getFormulaCode(), desCkd.getGlobRow(), desCkd.getGlobCol(), desCkd.getFormKey(), srcDim, desDim);
                context.monitor.info("\u6765\u6e90\u8bf4\u660e\u5339\u914d\u6620\u5c04\u6210\u529f\u2014\u2014" + mappedInfo);
                mappedCkds.add(new CKDMappedObj(desCkd, srcCkd, mappedInfo));
                if (!StringUtils.hasText(desCkd.getFormKey()) || "00000000-0000-0000-0000-000000000000".equals(desCkd.getFormKey())) continue;
                desFormKeys.add(desCkd.getFormKey());
                this.addDim(desDimCartesian, desDim);
            }
        }
        return new MappedInfo(desDimCartesian, desFormKeys, mappedCkds);
    }

    private List<CheckDesObj> querySrcCkds(Map<String, DimensionValue> dimensions, String srcFormSchemeKey, String srcFormulaSchemeKey, List<String> formKeys) {
        DimensionCollection dimensionCollection = this.serviceFactory.getDimensionBuildUtil().getDimensionCollection(dimensions, srcFormSchemeKey, (SpecificDimBuilder)FixedDimBuilder.getInstance());
        CheckDesQueryParam checkDesQueryParam = new CheckDesQueryParam();
        checkDesQueryParam.setDimensionCollection(dimensionCollection);
        checkDesQueryParam.setFormSchemeKey(srcFormSchemeKey);
        checkDesQueryParam.setFormulaSchemeKey(Collections.singletonList(srcFormulaSchemeKey));
        ArrayList<String> queryFormKeys = new ArrayList<String>(formKeys);
        if (this.optionProvider.copyBJFml()) {
            queryFormKeys.add("00000000-0000-0000-0000-000000000000");
        }
        checkDesQueryParam.setFormKey(queryFormKeys);
        return this.serviceFactory.getCheckErrorDescriptionService().queryFormulaCheckDes(checkDesQueryParam);
    }

    private DataPermissionEvaluator getDesAccessFormInfos(FormSchemeDefine desFormScheme, DimensionCollection desDimensionCollection, Collection<String> desFormKeys) {
        return this.getDataPermissionEvaluator(desFormScheme, desDimensionCollection, desFormKeys);
    }

    private MergeDataPermission getSrcFormReadAccessInfos(FormSchemeDefine srcFormScheme, DimensionCollection srcDimensionCollection, Collection<String> srcFormKeys) {
        DataPermissionEvaluator dataPermissionEvaluator = this.getDataPermissionEvaluator(srcFormScheme, srcDimensionCollection, srcFormKeys);
        return dataPermissionEvaluator.mergeAccess(srcDimensionCollection, srcFormKeys, AuthType.READABLE);
    }

    private DataPermissionEvaluator getDataPermissionEvaluator(FormSchemeDefine formScheme, DimensionCollection dimensionCollection, Collection<String> formKeys) {
        EvaluatorParam evaluatorParam = new EvaluatorParam(formScheme.getTaskKey(), formScheme.getKey(), ResouceType.FORM.getCode());
        return this.providerStore.getDataPermissionEvaluatorFactory().createEvaluator(evaluatorParam, dimensionCollection, formKeys);
    }

    private void validParam(CopyDesParam param) {
        if (param == null || !StringUtils.hasText(param.getFormSchemeKey()) || !StringUtils.hasText(param.getFormulaSchemeKey()) || param.getDims() == null || CollectionUtils.isEmpty(param.getDims().getDimensionCombinations()) || CollectionUtils.isEmpty(param.getFormKeys())) {
            throw new IllegalArgumentException("Illegal parameter:CopyDesParam");
        }
    }

    private Context init(CopyDesParam param, ICopyDesMonitor monitor) {
        Context context = new Context();
        if (log.isDebugEnabled()) {
            monitor = new DebugMonitor(monitor);
        }
        context.setMonitor(monitor);
        if (this.optionProvider.isPullMode()) {
            this.initPullMode(context, param);
        } else {
            this.initPushMode(context, param);
        }
        List<String> desDimNames = this.serviceFactory.getEntityUtil().getFmSchemeEntities(context.desFormScheme).stream().map(EntityData::getDimensionName).collect(Collectors.toList());
        String srcMainDimName = this.serviceFactory.getEntityMetaService().getDimensionName(context.srcFormScheme.getDw());
        context.setDesDimNames(desDimNames);
        context.setSrcMainDimName(srcMainDimName);
        return context;
    }

    private void initPullMode(Context context, CopyDesParam param) {
        FormSchemeDefine curFormScheme = this.serviceFactory.getRunTimeViewController().getFormScheme(param.getFormSchemeKey());
        context.setDesFormScheme(curFormScheme);
        context.setDesFormulaSchemeKey(param.getFormulaSchemeKey());
        this.reverseFmlMapping(context, param, curFormScheme);
        this.reverseDimMapping(context, param);
    }

    private void reverseDimMapping(Context context, CopyDesParam param) {
        DimensionMappingConverter dimensionMappingConverter = this.optionProvider.getDimensionMappingConverter();
        if (dimensionMappingConverter == null) {
            this.srcDimConverter = CKDCopyServiceImpl.getSameDimConverter();
            context.setSrcDimensionCollection(param.getDims());
        } else {
            List desDims = param.getDims().getDimensionCombinations().stream().map(DimensionCombination::toDimensionValueSet).collect(Collectors.toList());
            List mapDims = dimensionMappingConverter.getMappingMasterKeys(desDims);
            if (CollectionUtils.isEmpty(mapDims)) {
                context.monitor.error("\u7ef4\u5ea6\u6620\u5c04\u914d\u7f6e\u5168\u7a7a\uff0c\u4e0d\u6267\u884c\u51fa\u9519\u8bf4\u660e\u6570\u636e\u590d\u5236");
                context.setInitError(true);
                return;
            }
            HashMap<DimensionValueSet, List<DimensionValueSet>> dimMap = new HashMap<DimensionValueSet, List<DimensionValueSet>>();
            HashMap<String, Set<String>> srcDimCartesian = new HashMap<String, Set<String>>();
            for (DimensionMapInfo mapDim : mapDims) {
                DimensionValueSet source = mapDim.getSource();
                DimensionValueSet target = mapDim.getTarget();
                if (dimMap.containsKey(source)) {
                    ((List)dimMap.get(source)).add(target);
                    continue;
                }
                ArrayList<DimensionValueSet> targets = new ArrayList<DimensionValueSet>(3);
                targets.add(target);
                dimMap.put(source, targets);
                this.addDim(srcDimCartesian, source);
            }
            this.srcDimConverter = new DimMapper(dimMap);
            context.setSrcDimensionCollection(CKDCopyServiceImpl.getDimensionCollection(srcDimCartesian));
        }
    }

    private void reverseFmlMapping(Context context, CopyDesParam param, FormSchemeDefine curFormScheme) {
        IFmlMappingProvider fmlMappingProvider = this.optionProvider.getFmlMappingProvider();
        if (fmlMappingProvider == null) {
            this.srcFmlMappingProvider = CKDCopyServiceImpl.getSameFmlMappingProvider();
            context.setSrcFormScheme(curFormScheme);
            context.setSrcFormulaSchemeKey(param.getFormulaSchemeKey());
            context.setSrcFormKeys(param.getFormKeys());
        } else {
            String mappedFmlSchemeKey = fmlMappingProvider.getMappedFmlSchemeKey(param.getFormulaSchemeKey());
            if (!StringUtils.hasText(mappedFmlSchemeKey)) {
                throw new IllegalArgumentException("\u516c\u5f0f\u6620\u5c04\u672a\u914d\u7f6e\u6765\u6e90\u516c\u5f0f\u65b9\u6848");
            }
            FormulaSchemeDefine formulaSchemeDefine = this.serviceFactory.getFormulaRunTimeController().queryFormulaSchemeDefine(mappedFmlSchemeKey);
            if (formulaSchemeDefine == null) {
                throw new IllegalArgumentException("\u516c\u5f0f\u6620\u5c04\u914d\u7f6e\u7684\u6765\u6e90\u516c\u5f0f\u65b9\u6848\u4e0d\u5b58\u5728");
            }
            FormSchemeDefine srcFormScheme = this.serviceFactory.getRunTimeViewController().getFormScheme(formulaSchemeDefine.getFormSchemeKey());
            context.setSrcFormScheme(srcFormScheme);
            context.setSrcFormulaSchemeKey(mappedFmlSchemeKey);
            List desExpressions = this.serviceFactory.getFormulaRunTimeController().getParsedExpressionByForms(param.getFormulaSchemeKey(), param.getFormKeys(), DataEngineConsts.FormulaType.CHECK);
            HashSet<String> srcFormKeys = new HashSet<String>();
            HashMap<FmlMappingObj, FmlMappingObj> srcFmlMap = new HashMap<FmlMappingObj, FmlMappingObj>();
            HashMap<String, String> srcFmlSchemeMap = new HashMap<String, String>(1);
            srcFmlSchemeMap.put(mappedFmlSchemeKey, param.getFormulaSchemeKey());
            for (IParsedExpression desExpression : desExpressions) {
                String formKey = desExpression.getFormKey();
                if (!StringUtils.hasText(formKey)) {
                    formKey = "00000000-0000-0000-0000-000000000000";
                }
                IExpression realExpression = desExpression.getRealExpression();
                FmlMappingObj desFmlMappingObj = new FmlMappingObj(desExpression.getSource().getId(), formKey, realExpression.getWildcardRow(), realExpression.getWildcardCol());
                FmlMappingObj mappedFml = fmlMappingProvider.getMappedFml(desFmlMappingObj);
                if (mappedFml == null) continue;
                srcFmlMap.put(mappedFml, desFmlMappingObj);
                if ("00000000-0000-0000-0000-000000000000".equals(formKey)) continue;
                srcFormKeys.add(formKey);
            }
            if (CollectionUtils.isEmpty(srcFormKeys)) {
                throw new IllegalArgumentException("\u516c\u5f0f\u6620\u5c04\u672a\u627e\u5230\u53ef\u7528\u7684\u6765\u6e90\u516c\u5f0f");
            }
            context.setSrcFormKeys(new ArrayList<String>(srcFormKeys));
            this.srcFmlMappingProvider = new FmlMapper(srcFmlMap, srcFmlSchemeMap);
        }
    }

    private void initPushMode(Context context, CopyDesParam param) {
        FormSchemeDefine curFormScheme = this.serviceFactory.getRunTimeViewController().getFormScheme(param.getFormSchemeKey());
        context.setSrcFormScheme(curFormScheme);
        context.setSrcFormulaSchemeKey(param.getFormulaSchemeKey());
        context.setSrcDimensionCollection(param.getDims());
        context.setSrcFormKeys(param.getFormKeys());
        IFmlMappingProvider fmlMappingProvider = this.optionProvider.getFmlMappingProvider();
        this.srcFmlMappingProvider = fmlMappingProvider == null ? CKDCopyServiceImpl.getSameFmlMappingProvider() : fmlMappingProvider;
        String mappedFmlSchemeKey = this.srcFmlMappingProvider.getMappedFmlSchemeKey(param.getFormulaSchemeKey());
        if (!StringUtils.hasText(mappedFmlSchemeKey)) {
            throw new IllegalArgumentException("\u516c\u5f0f\u6620\u5c04\u672a\u914d\u7f6e\u76ee\u6807\u516c\u5f0f\u65b9\u6848");
        }
        FormulaSchemeDefine formulaSchemeDefine = this.serviceFactory.getFormulaRunTimeController().queryFormulaSchemeDefine(mappedFmlSchemeKey);
        if (formulaSchemeDefine == null) {
            throw new IllegalArgumentException("\u516c\u5f0f\u6620\u5c04\u914d\u7f6e\u7684\u76ee\u6807\u516c\u5f0f\u65b9\u6848\u4e0d\u5b58\u5728");
        }
        FormSchemeDefine desFormScheme = this.serviceFactory.getRunTimeViewController().getFormScheme(formulaSchemeDefine.getFormSchemeKey());
        context.setDesFormScheme(desFormScheme);
        context.setDesFormulaSchemeKey(mappedFmlSchemeKey);
        DimensionMappingConverter dimensionMappingConverter = this.optionProvider.getDimensionMappingConverter();
        this.srcDimConverter = dimensionMappingConverter == null ? CKDCopyServiceImpl.getSameDimConverter() : dimensionMappingConverter;
    }

    @NonNull
    private static DimensionMappingConverter getSameDimConverter() {
        return new DimensionMappingConverter(){

            public List<DimensionValueSet> getMappingMasterKey(DimensionValueSet curMasterKey) {
                ArrayList<DimensionValueSet> result = new ArrayList<DimensionValueSet>(1);
                result.add(curMasterKey);
                return result;
            }

            public List<DimensionMapInfo> getMappingMasterKeys(List<DimensionValueSet> curMasterKeys) {
                if (CollectionUtils.isEmpty(curMasterKeys)) {
                    return Collections.emptyList();
                }
                ArrayList<DimensionMapInfo> result = new ArrayList<DimensionMapInfo>(curMasterKeys.size());
                for (DimensionValueSet curMasterKey : curMasterKeys) {
                    DimensionMapInfo dimensionMapInfo = new DimensionMapInfo(curMasterKey, curMasterKey);
                    result.add(dimensionMapInfo);
                }
                return result;
            }
        };
    }

    @NonNull
    private static IFmlMappingProvider getSameFmlMappingProvider() {
        return new IFmlMappingProvider(){

            @Override
            public String getMappedFmlSchemeKey(String curFmlSchemeKey) {
                return curFmlSchemeKey;
            }

            @Override
            public FmlMappingObj getMappedFml(FmlMappingObj curFml) {
                return curFml;
            }
        };
    }

    private static class MappedInfo {
        List<CKDMappedObj> mappedCkds;
        Map<String, Set<String>> desDimCartesian;
        Set<String> desFormKeys;

        public MappedInfo(Map<String, Set<String>> desDimCartesian, Set<String> desFormKeys, List<CKDMappedObj> mappedCkds) {
            this.desDimCartesian = desDimCartesian;
            this.desFormKeys = desFormKeys;
            this.mappedCkds = mappedCkds;
        }
    }

    private static class CKDMappedObj {
        CheckDesObj desObj;
        CheckDesObj srcObj;
        String mappedInfo;

        public CKDMappedObj(CheckDesObj desObj, CheckDesObj srcObj, String mappedInfo) {
            this.desObj = desObj;
            this.srcObj = srcObj;
            this.mappedInfo = mappedInfo;
        }
    }

    private static class DebugMonitor
    implements ICopyDesMonitor {
        private final ICopyDesMonitor monitor;

        public DebugMonitor(ICopyDesMonitor monitor) {
            this.monitor = monitor;
        }

        @Override
        public void info(String msg) {
            log.debug(msg);
            this.monitor.info(msg);
        }

        @Override
        public void error(String msg) {
            log.error(msg);
            this.monitor.error(msg);
        }
    }

    private static class CopyData {
        public final List<CheckDesObj> saveData;
        public final Map<String, Set<String>> saveDimCartesian;
        public final List<CheckDesFmlObj> unsupportedSrcDes;
        public final Map<String, List<CheckDesFmlObj>> unsupportedDstDesMap;
        public final int unsupportedNum;

        public CopyData(List<CheckDesObj> saveData, Map<String, Set<String>> saveDimCartesian, List<CheckDesFmlObj> unsupportedSrcDes, Map<String, List<CheckDesFmlObj>> unsupportedDstDesMap, int unsupportedNum) {
            this.saveData = saveData;
            this.saveDimCartesian = saveDimCartesian;
            this.unsupportedSrcDes = unsupportedSrcDes;
            this.unsupportedDstDesMap = unsupportedDstDesMap;
            this.unsupportedNum = unsupportedNum;
        }
    }

    private class Context {
        ICopyDesMonitor monitor;
        FormSchemeDefine srcFormScheme;
        FormSchemeDefine desFormScheme;
        String srcFormulaSchemeKey;
        String desFormulaSchemeKey;
        DimensionCollection srcDimensionCollection;
        List<String> srcFormKeys;
        String srcMainDimName;
        List<String> desDimNames;
        Map<String, IParsedExpression> desExpressions;
        Map<String, IParsedExpression> srcExpressions;
        private final String userId = NpContextHolder.getContext().getUserId();
        private final String userNickName;
        private final Instant curTime;
        private boolean initError;

        public Context() {
            this.userNickName = CKDCopyServiceImpl.this.serviceFactory.getParamUtil().getUserNickNameById(this.userId);
            this.curTime = Instant.now(Clock.systemDefaultZone());
        }

        public void setDesExpressions(Map<String, IParsedExpression> desExpressions) {
            this.desExpressions = desExpressions;
        }

        public void setDesFormScheme(FormSchemeDefine desFormScheme) {
            this.desFormScheme = desFormScheme;
        }

        public void setDesFormulaSchemeKey(String desFormulaSchemeKey) {
            this.desFormulaSchemeKey = desFormulaSchemeKey;
        }

        public void setMonitor(ICopyDesMonitor monitor) {
            this.monitor = monitor;
        }

        public void setSrcDimensionCollection(DimensionCollection srcDimensionCollection) {
            this.srcDimensionCollection = srcDimensionCollection;
        }

        public void setSrcExpressions(Map<String, IParsedExpression> srcExpressions) {
            this.srcExpressions = srcExpressions;
        }

        public void setSrcFormKeys(List<String> srcFormKeys) {
            this.srcFormKeys = srcFormKeys;
        }

        public void setSrcFormScheme(FormSchemeDefine srcFormScheme) {
            this.srcFormScheme = srcFormScheme;
        }

        public void setSrcFormulaSchemeKey(String srcFormulaSchemeKey) {
            this.srcFormulaSchemeKey = srcFormulaSchemeKey;
        }

        public void setDesDimNames(List<String> desDimNames) {
            this.desDimNames = desDimNames;
        }

        public void setSrcMainDimName(String srcMainDimName) {
            this.srcMainDimName = srcMainDimName;
        }

        public void setInitError(boolean initError) {
            this.initError = initError;
        }
    }
}

