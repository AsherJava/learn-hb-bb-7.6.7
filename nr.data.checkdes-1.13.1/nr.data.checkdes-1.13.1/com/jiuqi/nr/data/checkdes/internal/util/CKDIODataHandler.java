/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.monitor.IProgressMonitor
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  com.jiuqi.nr.data.access.param.DimensionAccessFormInfo
 *  com.jiuqi.nr.data.access.param.DimensionAccessFormInfo$AccessFormInfo
 *  com.jiuqi.nr.data.access.util.DataPermissionUtil
 *  com.jiuqi.nr.data.common.Message
 *  com.jiuqi.nr.data.logic.facade.param.input.CheckDesBatchSaveObj
 *  com.jiuqi.nr.data.logic.facade.param.input.CheckDesObj
 *  com.jiuqi.nr.data.logic.facade.param.input.CheckDesQueryParam
 *  com.jiuqi.nr.dataservice.core.access.AuthType
 *  com.jiuqi.nr.dataservice.core.access.DataPermission
 *  com.jiuqi.nr.dataservice.core.access.DataPermissionEvaluator
 *  com.jiuqi.nr.dataservice.core.access.EvaluatorParam
 *  com.jiuqi.nr.dataservice.core.access.ResouceType
 *  com.jiuqi.nr.dataservice.core.common.IProviderStore
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.build.FixedDimBuilder
 *  com.jiuqi.nr.dataservice.core.dimension.build.SpecificDimBuilder
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 */
package com.jiuqi.nr.data.checkdes.internal.util;

import com.jiuqi.bi.monitor.IProgressMonitor;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.nr.data.access.param.DimensionAccessFormInfo;
import com.jiuqi.nr.data.access.util.DataPermissionUtil;
import com.jiuqi.nr.data.checkdes.api.ICKDParamMapping;
import com.jiuqi.nr.data.checkdes.exception.CKDIOException;
import com.jiuqi.nr.data.checkdes.facade.obj.CKDImpMes;
import com.jiuqi.nr.data.checkdes.facade.obj.ImpSuccessInfo;
import com.jiuqi.nr.data.checkdes.internal.ctxt.ExpContext;
import com.jiuqi.nr.data.checkdes.internal.ctxt.ImpContext;
import com.jiuqi.nr.data.checkdes.internal.impl.ImpDataConsumer;
import com.jiuqi.nr.data.checkdes.internal.io.CKDExpEntity;
import com.jiuqi.nr.data.checkdes.internal.io.FileDataReader;
import com.jiuqi.nr.data.checkdes.internal.io.FileDataWriter;
import com.jiuqi.nr.data.checkdes.internal.util.IOUtils;
import com.jiuqi.nr.data.checkdes.obj.BasePar;
import com.jiuqi.nr.data.checkdes.obj.CKDExpPar;
import com.jiuqi.nr.data.checkdes.obj.CKDImpFilter;
import com.jiuqi.nr.data.checkdes.obj.CKDImpPar;
import com.jiuqi.nr.data.checkdes.obj.FilterType;
import com.jiuqi.nr.data.checkdes.obj.InfoCollection;
import com.jiuqi.nr.data.common.Message;
import com.jiuqi.nr.data.logic.facade.param.input.CheckDesBatchSaveObj;
import com.jiuqi.nr.data.logic.facade.param.input.CheckDesObj;
import com.jiuqi.nr.data.logic.facade.param.input.CheckDesQueryParam;
import com.jiuqi.nr.dataservice.core.access.AuthType;
import com.jiuqi.nr.dataservice.core.access.DataPermission;
import com.jiuqi.nr.dataservice.core.access.DataPermissionEvaluator;
import com.jiuqi.nr.dataservice.core.access.EvaluatorParam;
import com.jiuqi.nr.dataservice.core.access.ResouceType;
import com.jiuqi.nr.dataservice.core.common.IProviderStore;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.build.FixedDimBuilder;
import com.jiuqi.nr.dataservice.core.dimension.build.SpecificDimBuilder;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

public class CKDIODataHandler {
    private static final Logger logger = LoggerFactory.getLogger(CKDIODataHandler.class);

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static InfoCollection exportData(ExpContext context, FileDataWriter fileDataWriter, IProgressMonitor progressMonitor) throws IOException {
        String taskName = "com.jiuqi.nr.data.checkdes.internal.util.CKDIODataHandler.exportData";
        try {
            progressMonitor.startTask(taskName, new int[]{2, 1});
            InfoCollection infoCollection = CKDIODataHandler.handleExportData(context, fileDataWriter, progressMonitor);
            progressMonitor.stepIn();
            fileDataWriter.write();
            progressMonitor.stepIn();
            InfoCollection infoCollection2 = infoCollection;
            return infoCollection2;
        }
        finally {
            progressMonitor.finishTask(taskName);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static InfoCollection handleExportData(ExpContext context, Consumer<CKDExpEntity> consumer, IProgressMonitor progressMonitor) {
        String taskName = "com.jiuqi.nr.data.checkdes.internal.util.CKDIODataHandler.handleExportData";
        try {
            progressMonitor.startTask(taskName, new int[]{2, 1, 2});
            CKDExpPar ckdExpPar = context.getCkdExpPar();
            context.getCommonUtil().checkPar(ckdExpPar);
            DimensionAccessFormInfo readAccessInfo = CKDIODataHandler.getReadAccessInfo(context);
            progressMonitor.stepIn();
            List<CheckDesObj> checkDesObjs = CKDIODataHandler.getCheckDesObjs(context, readAccessInfo);
            progressMonitor.stepIn();
            InfoCollection infoCollection = new InfoCollection();
            ICKDParamMapping paramMapping = ckdExpPar.getCkdParamMapping();
            for (CheckDesObj checkDesObj : checkDesObjs) {
                CKDExpEntity ckdExpEntity = context.getCommonUtil().getHelper().transCKDExpEntity(context, checkDesObj);
                if (ckdExpEntity == null) continue;
                CKDIODataHandler.fillInfoCollection(ckdExpEntity, checkDesObj, infoCollection);
                if (paramMapping != null) {
                    ckdExpEntity = IOUtils.handleMapping(paramMapping, ckdExpEntity);
                }
                consumer.accept(ckdExpEntity);
            }
            progressMonitor.stepIn();
            InfoCollection infoCollection2 = infoCollection;
            return infoCollection2;
        }
        finally {
            progressMonitor.finishTask(taskName);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static Message<CKDImpMes> importData(ImpContext context, FileDataReader fileDataReader, IProgressMonitor progressMonitor) throws CKDIOException {
        String taskName = "com.jiuqi.nr.data.checkdes.internal.util.CKDIODataHandler.importData";
        try {
            progressMonitor.startTask(taskName, 2);
            CKDImpPar param = context.getImpPar();
            context.getCommonUtil().checkPar(param);
            List<CKDImpFilter> filters = CKDIODataHandler.getFilters(param);
            try {
                DataPermissionEvaluator dataPermissionEvaluator = CKDIODataHandler.getDataPermissionEvaluator(context);
                ImpDataConsumer impDataConsumer = new ImpDataConsumer(context, dataPermissionEvaluator, filters);
                fileDataReader.read(context, impDataConsumer);
                progressMonitor.stepIn();
                CheckDesBatchSaveObj saveObj = CKDIODataHandler.getCheckDesBatchSaveObj(param, impDataConsumer.getEffectiveData());
                context.getCommonUtil().getHelper().getCkdService().batchSaveFormulaCheckDes(saveObj);
                context.getCkdImpMes().setSuccessInfos(saveObj.getCheckDesObjs().stream().map(o -> {
                    ImpSuccessInfo impSuccessInfo = new ImpSuccessInfo();
                    impSuccessInfo.setRecordId(o.getRecordId());
                    impSuccessInfo.setFormulaSchemeKey(o.getFormulaSchemeKey());
                    return impSuccessInfo;
                }).collect(Collectors.toList()));
                CKDIODataHandler.fillImpMes(context, impDataConsumer.getInfoCollection());
                progressMonitor.stepIn();
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
                throw new CKDIOException("\u5bfc\u5165\u51fa\u9519\u8bf4\u660e\u5f02\u5e38:" + e.getMessage(), e);
            }
            CKDImpMes cKDImpMes = context.getCkdImpMes();
            return cKDImpMes;
        }
        finally {
            progressMonitor.finishTask(taskName);
        }
    }

    private static CheckDesBatchSaveObj getCheckDesBatchSaveObj(CKDImpPar param, List<CheckDesObj> data) {
        CheckDesBatchSaveObj saveObj = new CheckDesBatchSaveObj();
        saveObj.setCheckDesObjs(data);
        CheckDesQueryParam queryParam = new CheckDesQueryParam();
        queryParam.setDimensionCollection(param.getDimensionCollection());
        queryParam.setFormulaSchemeKey(param.getFormulaSchemeKeys());
        saveObj.setCheckDesQueryParam(queryParam);
        return saveObj;
    }

    private static DimensionAccessFormInfo getReadAccessInfo(ExpContext context) {
        IProviderStore providerStore = context.getProviderStore();
        FormSchemeDefine formScheme = context.getFormSchemeDefine();
        EvaluatorParam evaluatorParam = new EvaluatorParam(formScheme.getTaskKey(), formScheme.getKey(), ResouceType.FORM.getCode());
        CKDExpPar ckdExpPar = context.getCkdExpPar();
        List<String> formKeys = CKDIODataHandler.getAccessValidFormKeys(ckdExpPar, context.getFormSchemeDefine().getKey(), context.getCommonUtil().getHelper().getRunTimeViewController());
        DataPermissionEvaluator dataPermissionEvaluator = providerStore.getDataPermissionEvaluatorFactory().createEvaluator(evaluatorParam, ckdExpPar.getDimensionCollection(), formKeys);
        DataPermission dataPermission = dataPermissionEvaluator.haveAccess(ckdExpPar.getDimensionCollection(), formKeys, AuthType.READABLE);
        String dwDimName = context.getMdDimName();
        return DataPermissionUtil.toBatchAccessForms((DataPermission)dataPermission, (String)dwDimName);
    }

    private static List<String> getAccessValidFormKeys(BasePar par, String formSchemeKey, IRunTimeViewController runTimeViewController) {
        ArrayList<String> formKeys;
        if (CollectionUtils.isEmpty(par.getFormKeys())) {
            formKeys = runTimeViewController.queryAllFormKeysByFormScheme(formSchemeKey);
        } else {
            formKeys = new ArrayList<String>(par.getFormKeys());
            formKeys.remove("00000000-0000-0000-0000-000000000000");
        }
        return formKeys;
    }

    private static void fillImpMes(ImpContext context, InfoCollection infoCollection) {
        CKDImpMes ckdImpMes = context.getCkdImpMes();
        if (!CollectionUtils.isEmpty(infoCollection.getDimensionValueSets())) {
            DimensionValueSet dimensionValueSet = context.getCommonUtil().mergeDimensionValueSet(infoCollection.getDimensionValueSets());
            DimensionCollection dimensionCollection = null;
            try {
                dimensionCollection = context.getCommonUtil().getHelper().getDimensionBuildUtil().getDimensionCollection(dimensionValueSet, context.getFormSchemeDefine().getKey(), (SpecificDimBuilder)FixedDimBuilder.getInstance());
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            ckdImpMes.setDimensions(dimensionCollection);
        }
        ckdImpMes.setSuccessDW(new ArrayList<String>(infoCollection.getDwSet()));
        ckdImpMes.setFormulaSchemeKeys(new ArrayList<String>(infoCollection.getFormulaSchemes()));
        ckdImpMes.setForms(new ArrayList<String>(infoCollection.getForms()));
    }

    private static DataPermissionEvaluator getDataPermissionEvaluator(ImpContext context) {
        FormSchemeDefine formScheme = context.getFormSchemeDefine();
        EvaluatorParam evaluatorParam = new EvaluatorParam(formScheme.getTaskKey(), formScheme.getKey(), ResouceType.FORM.getCode());
        DimensionCollection dimensionCollection = context.getImpPar().getDimensionCollection();
        List<String> formKeys = CKDIODataHandler.getAccessValidFormKeys(context.getImpPar(), formScheme.getKey(), context.getCommonUtil().getHelper().getRunTimeViewController());
        IProviderStore providerStore = context.getProviderStore();
        return providerStore.getDataPermissionEvaluatorFactory().createEvaluator(evaluatorParam, dimensionCollection, formKeys);
    }

    private static List<CKDImpFilter> getFilters(CKDImpPar param) {
        List<String> formulaKeys;
        List<String> formKeys;
        ArrayList<CKDImpFilter> filters = new ArrayList<CKDImpFilter>();
        DimensionCollection dimensionCollection = param.getDimensionCollection();
        Set dimensionValueSets = dimensionCollection.getDimensionCombinations().stream().map(DimensionCombination::toDimensionValueSet).collect(Collectors.toSet());
        filters.add(new CKDImpFilter(dimensionValueSets, FilterType.DIMENSION));
        List<String> formulaSchemeKeys = param.getFormulaSchemeKeys();
        if (!CollectionUtils.isEmpty(formulaSchemeKeys)) {
            HashSet<String> strings = new HashSet<String>(formulaSchemeKeys);
            filters.add(new CKDImpFilter(strings, FilterType.FORMULA_SCHEME));
        }
        if (!CollectionUtils.isEmpty(formKeys = param.getFormKeys())) {
            HashSet<String> strings = new HashSet<String>(formKeys);
            filters.add(new CKDImpFilter(strings, FilterType.FORM));
        }
        if (!CollectionUtils.isEmpty(formulaKeys = param.getFormulaKeys())) {
            HashSet<String> strings = new HashSet<String>(formulaKeys);
            filters.add(new CKDImpFilter(strings, FilterType.FORMULA));
        }
        return filters;
    }

    private static List<CheckDesObj> getCheckDesObjs(ExpContext context, DimensionAccessFormInfo dimensionAccessFormInfo) {
        CKDExpPar param = context.getCkdExpPar();
        ArrayList<CheckDesObj> exportData = new ArrayList<CheckDesObj>();
        List<String> formulaKeys = param.getFormulaKeys();
        List<String> formulaSchemeKeys = param.getFormulaSchemeKeys();
        List<IParsedExpression> parsedExpression = context.getCommonUtil().getHelper().getParsedExpression(param.getFormSchemeKey(), formulaSchemeKeys, formulaKeys);
        boolean containsBJ = param.getFormKeys() == null || param.getFormKeys().contains("00000000-0000-0000-0000-000000000000");
        for (DimensionAccessFormInfo.AccessFormInfo accessForm : dimensionAccessFormInfo.getAccessForms()) {
            Map dimensions = accessForm.getDimensions();
            ArrayList<String> formKeys = accessForm.getFormKeys();
            CheckDesQueryParam queryParam = new CheckDesQueryParam();
            queryParam.setDimensionCollection(context.getCommonUtil().getHelper().getDimensionBuildUtil().getDimensionCollection(dimensions, param.getFormSchemeKey()));
            queryParam.setFormSchemeKey(param.getFormSchemeKey());
            queryParam.setFormulaSchemeKey(formulaSchemeKeys);
            if (containsBJ) {
                formKeys = new ArrayList<String>(formKeys);
                formKeys.add("00000000-0000-0000-0000-000000000000");
            }
            queryParam.setFormKey((List)formKeys);
            queryParam.setFormulaKey(parsedExpression.stream().map(IParsedExpression::getKey).collect(Collectors.toList()));
            List checkDesObjs = context.getCommonUtil().getHelper().getCkdService().queryFormulaCheckDes(queryParam);
            if (CollectionUtils.isEmpty(checkDesObjs)) continue;
            exportData.addAll(checkDesObjs);
        }
        return exportData;
    }

    private static void fillInfoCollection(CKDExpEntity ckdExpEntity, CheckDesObj checkDesObj, InfoCollection infoCollection) {
        infoCollection.getFormulaSchemes().add(ckdExpEntity.getFormulaSchemeTitle());
        infoCollection.getForms().add(ckdExpEntity.getFormCode());
        infoCollection.getDimensionValueSets().add(checkDesObj.getDimensionValueSet());
    }
}

