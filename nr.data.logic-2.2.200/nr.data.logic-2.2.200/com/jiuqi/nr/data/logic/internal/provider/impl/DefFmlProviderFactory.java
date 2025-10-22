/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaType
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  com.jiuqi.nr.data.access.common.AccessLevel$FormAccessLevel
 *  com.jiuqi.nr.data.access.param.AccessFormParam
 *  com.jiuqi.nr.data.access.param.DimensionAccessFormInfo
 *  com.jiuqi.nr.data.access.param.DimensionAccessFormInfo$AccessFormInfo
 *  com.jiuqi.nr.data.access.param.IAccessResult
 *  com.jiuqi.nr.data.access.service.IDataAccessFormService
 *  com.jiuqi.nr.data.access.service.IDataAccessService
 *  com.jiuqi.nr.data.access.service.IDataAccessServiceProvider
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.dataservice.core.access.DataPermissionEvaluator
 *  com.jiuqi.nr.dataservice.core.access.DataPermissionEvaluatorFactory
 *  com.jiuqi.nr.dataservice.core.access.EvaluatorParam
 *  com.jiuqi.nr.dataservice.core.access.ResouceType
 *  com.jiuqi.nr.dataservice.core.common.IProviderStore
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue
 *  com.jiuqi.nr.definition.api.IRunTimeFormulaController
 *  com.jiuqi.nr.definition.common.DataLinkType
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaDefine
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 */
package com.jiuqi.nr.data.logic.internal.provider.impl;

import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.nr.data.access.common.AccessLevel;
import com.jiuqi.nr.data.access.param.AccessFormParam;
import com.jiuqi.nr.data.access.param.DimensionAccessFormInfo;
import com.jiuqi.nr.data.access.param.IAccessResult;
import com.jiuqi.nr.data.access.service.IDataAccessFormService;
import com.jiuqi.nr.data.access.service.IDataAccessService;
import com.jiuqi.nr.data.access.service.IDataAccessServiceProvider;
import com.jiuqi.nr.data.logic.common.CommonUtils;
import com.jiuqi.nr.data.logic.exeception.LogicMappingException;
import com.jiuqi.nr.data.logic.facade.extend.IFmlExecInfoProvider;
import com.jiuqi.nr.data.logic.facade.extend.IFmlExecInfoProviderFactory;
import com.jiuqi.nr.data.logic.facade.extend.param.AutoCalStrategy;
import com.jiuqi.nr.data.logic.facade.extend.param.BaseCalFormFmlParam;
import com.jiuqi.nr.data.logic.facade.extend.param.BaseFmlFactoryParam;
import com.jiuqi.nr.data.logic.facade.extend.param.DefaultFmlFactoryParam;
import com.jiuqi.nr.data.logic.facade.extend.param.FmlExecInfo;
import com.jiuqi.nr.data.logic.internal.provider.impl.FmlExecInfoProvider;
import com.jiuqi.nr.data.logic.internal.service.IFmlGraphDataCollector;
import com.jiuqi.nr.data.logic.internal.service.impl.DftFmlGraphAccessVerifier;
import com.jiuqi.nr.data.logic.internal.util.BeanHelper;
import com.jiuqi.nr.data.logic.internal.util.ParamUtil;
import com.jiuqi.nr.data.logic.internal.util.entity.EntityUtil;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.dataservice.core.access.DataPermissionEvaluator;
import com.jiuqi.nr.dataservice.core.access.DataPermissionEvaluatorFactory;
import com.jiuqi.nr.dataservice.core.access.EvaluatorParam;
import com.jiuqi.nr.dataservice.core.access.ResouceType;
import com.jiuqi.nr.dataservice.core.common.IProviderStore;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue;
import com.jiuqi.nr.definition.api.IRunTimeFormulaController;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class DefFmlProviderFactory
implements IFmlExecInfoProviderFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefFmlProviderFactory.class);
    @Autowired
    protected IFmlGraphDataCollector fmlGraphDataCollector;
    @Autowired
    protected IDataAccessServiceProvider dataAccessServiceProvider;
    @Autowired
    protected IRunTimeViewController runTimeViewController;
    @Autowired
    protected IFormulaRunTimeController formulaRunTimeController;
    @Autowired
    protected IRunTimeFormulaController formulaController;
    @Autowired
    protected EntityUtil entityUtil;
    @Autowired
    protected IEntityMetaService entityMetaService;
    @Autowired
    protected IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    protected ParamUtil paramUtil;
    @Value(value="${jiuqi.nr.data.logic.auto-find-fml-cal:true}")
    protected boolean autoFindFmlCal;
    @Autowired
    protected IProviderStore providerStore;
    @Autowired
    protected BeanHelper beanHelper;

    @Override
    public String getFactoryName() {
        return "DEFAULT";
    }

    @Override
    public IFmlExecInfoProvider getProvider(BaseFmlFactoryParam param) {
        if (param instanceof DefaultFmlFactoryParam) {
            return this.buildDefault((DefaultFmlFactoryParam)param);
        }
        if (param instanceof BaseCalFormFmlParam) {
            BaseCalFormFmlParam calFormFmlParam = (BaseCalFormFmlParam)param;
            this.logStrategy(calFormFmlParam);
            if (AutoCalStrategy.FIND_FML == calFormFmlParam.getStrategy()) {
                boolean parsedFormulaFieldException = this.formulaController.isParsedFormulaFieldException(calFormFmlParam.getFormulaSchemeKey());
                LOGGER.debug("AUTO_FIND_FML_CAL_OPT:{}-{}-PARSED_FORMULA_FIELD_EXCEPTION:{}", this.autoFindFmlCal, calFormFmlParam.getFormulaSchemeKey(), parsedFormulaFieldException);
                if (this.autoFindFmlCal && !parsedFormulaFieldException) {
                    return this.buildCalAutoByForm(calFormFmlParam);
                }
                return this.getProviderByStrategy(calFormFmlParam, calFormFmlParam.getDowngradeStrategy());
            }
            return this.getProviderByStrategy(calFormFmlParam, calFormFmlParam.getStrategy());
        }
        return null;
    }

    protected IFmlExecInfoProvider getProviderByStrategy(BaseCalFormFmlParam param, AutoCalStrategy calStrategy) {
        switch (calStrategy) {
            case BJ_FORM: {
                return this.buildBJForm(param, true);
            }
            case FORM: {
                return this.buildBJForm(param, false);
            }
            case ALL: {
                return this.buildAllFml(param);
            }
            case EXCEPTION: {
                throw new LogicMappingException("\u5f53\u524d\u73af\u5883\u4e0d\u652f\u6301\u81ea\u52a8\u67e5\u627e\u516c\u5f0f\uff0c\u4e0d\u6267\u884c\u8fd0\u7b97\uff01");
            }
        }
        FormSchemeDefine formScheme = this.paramUtil.getFormSchemeByFormulaSchemeKey(param.getFormulaSchemeKey());
        DimensionCollection dimensionCollection = this.getDimensionCollection(param.getDimensionCombination(), formScheme);
        return new FmlExecInfoProvider(null, param.getFormulaSchemeKey(), dimensionCollection);
    }

    private void logStrategy(BaseCalFormFmlParam calFormFmlParam) {
        if (LOGGER.isDebugEnabled()) {
            AutoCalStrategy strategy = calFormFmlParam.getStrategy();
            LOGGER.debug("AUTO_CAL_STRATEGY-{}", (Object)(strategy == null ? "NULL" : strategy.getDesc()));
            AutoCalStrategy downgradeStrategy = calFormFmlParam.getDowngradeStrategy();
            LOGGER.debug("AUTO_CAL_DOWNGRADE_STRATEGY-{}", (Object)(downgradeStrategy == null ? "NULL" : downgradeStrategy.getDesc()));
        }
    }

    protected IFmlExecInfoProvider buildDefault(DefaultFmlFactoryParam param) {
        throw new UnsupportedOperationException();
    }

    protected IFmlExecInfoProvider buildCalAutoByForm(BaseCalFormFmlParam param) {
        String formulaSchemeKey = param.getFormulaSchemeKey();
        List<String> calFmlForms = this.getCalFmlForms(formulaSchemeKey);
        DimensionCombination dimensionCombination = param.getDimensionCombination();
        FormSchemeDefine formScheme = this.paramUtil.getFormSchemeByFormulaSchemeKey(param.getFormulaSchemeKey());
        DimensionCollection dimensionCollection = this.getDimensionCollection(dimensionCombination, formScheme);
        String formKey = param.getFormKey();
        String logId = null;
        if (LOGGER.isDebugEnabled()) {
            logId = UUID.randomUUID().toString();
            LOGGER.debug("logId:{}\nformSchemeKey:{}\nformulaSchemeKey:{}\nformKey:{}\ncalFmlForms:{}\ndimension:{}", logId, formScheme.getKey(), formulaSchemeKey, formKey, calFmlForms, dimensionCombination);
        }
        if (!CollectionUtils.isEmpty(calFmlForms)) {
            List<String> colIDs = this.getColIDs(formKey, formScheme);
            LOGGER.debug("logId:{}\ncolIDs:{}", (Object)logId, (Object)colIDs);
            if (!CollectionUtils.isEmpty(colIDs)) {
                DimensionAccessFormInfo.AccessFormInfo accessFormInfo;
                List accessFormKeys;
                List<DimensionAccessFormInfo.AccessFormInfo> accessForms = this.getAccessFormInfos(calFmlForms, formScheme, dimensionCollection);
                if (!CollectionUtils.isEmpty(accessForms) && !CollectionUtils.isEmpty(accessFormKeys = (accessFormInfo = accessForms.get(0)).getFormKeys())) {
                    DataPermissionEvaluator dataPermissionEvaluator = this.providerStore.getDataPermissionEvaluatorFactory().createEvaluator(new EvaluatorParam(formScheme.getTaskKey(), formScheme.getKey(), ResouceType.ZB.getCode()));
                    DftFmlGraphAccessVerifier verifier = new DftFmlGraphAccessVerifier(new HashSet<String>(accessFormKeys), dataPermissionEvaluator, this.beanHelper);
                    List<IParsedExpression> expressions = this.fmlGraphDataCollector.collect(param.getFormulaSchemeKey(), dimensionCombination, colIDs, verifier);
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("logId:{}\naccessFormKeys:{}\nexpressions:{}", logId, accessFormKeys, expressions);
                    }
                    FmlExecInfo fmlExecInfo = new FmlExecInfo(dimensionCombination.toDimensionValueSet(), expressions);
                    return new FmlExecInfoProvider(Collections.singletonList(fmlExecInfo), formulaSchemeKey, dimensionCollection);
                }
                LOGGER.debug("logId:{}\u65e0\u6743\u9650", (Object)logId);
            }
        }
        return new FmlExecInfoProvider(Collections.emptyList(), formulaSchemeKey, dimensionCollection);
    }

    private List<String> getCalFmlForms(String formulaSchemeKey) {
        List calculateFormulasInScheme = this.formulaRunTimeController.getCalculateFormulasInScheme(formulaSchemeKey);
        return calculateFormulasInScheme.stream().filter(o -> StringUtils.hasText(o.getFormKey())).filter(CommonUtils.distinctByKey(FormulaDefine::getFormKey)).map(FormulaDefine::getFormKey).collect(Collectors.toList());
    }

    protected IFmlExecInfoProvider buildBJForm(BaseCalFormFmlParam param, boolean includeBJ) {
        String formulaSchemeKey = param.getFormulaSchemeKey();
        FormSchemeDefine formScheme = this.paramUtil.getFormSchemeByFormulaSchemeKey(param.getFormulaSchemeKey());
        IDataAccessService dataAccessService = this.dataAccessServiceProvider.getDataAccessService(formScheme.getTaskKey(), formScheme.getKey());
        DimensionCombination dimensionCombination = param.getDimensionCombination();
        IAccessResult accessResult = dataAccessService.sysWriteable(dimensionCombination, param.getFormKey());
        DimensionCollection dimensionCollection = this.getDimensionCollection(dimensionCombination, formScheme);
        try {
            if (accessResult.haveAccess()) {
                ArrayList<IParsedExpression> fml = new ArrayList<IParsedExpression>(this.formulaRunTimeController.getParsedExpressionByForm(formulaSchemeKey, param.getFormKey(), DataEngineConsts.FormulaType.CALCULATE));
                if (includeBJ) {
                    List bjFml = this.formulaRunTimeController.getParsedExpressionBetweenTable(formulaSchemeKey, DataEngineConsts.FormulaType.CALCULATE);
                    List<IParsedExpression> accessBJFml = this.beanHelper.getCalculateHelper().getZBWriteAccessFml(formScheme, this.providerStore.getDataPermissionEvaluatorFactory(), dimensionCombination, bjFml);
                    fml.addAll(accessBJFml);
                }
                FmlExecInfo fmlExecInfo = new FmlExecInfo(dimensionCombination.toDimensionValueSet(), fml);
                return new FmlExecInfoProvider(Collections.singletonList(fmlExecInfo), formulaSchemeKey, dimensionCollection);
            }
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return new FmlExecInfoProvider(null, formulaSchemeKey, dimensionCollection);
    }

    protected IFmlExecInfoProvider buildAllFml(BaseCalFormFmlParam param) {
        DimensionAccessFormInfo.AccessFormInfo accessFormInfo;
        List accessFormKeys;
        DimensionCollection dimensionCollection;
        FormSchemeDefine formScheme;
        List<String> calFmlForms = this.getCalFmlForms(param.getFormulaSchemeKey());
        List<DimensionAccessFormInfo.AccessFormInfo> accessForms = this.getAccessFormInfos(calFmlForms, formScheme = this.paramUtil.getFormSchemeByFormulaSchemeKey(param.getFormulaSchemeKey()), dimensionCollection = this.getDimensionCollection(param.getDimensionCombination(), formScheme));
        if (!CollectionUtils.isEmpty(accessForms) && !CollectionUtils.isEmpty(accessFormKeys = (accessFormInfo = accessForms.get(0)).getFormKeys())) {
            return this.buildAllFml(param, formScheme, dimensionCollection, new HashSet<String>(accessFormKeys), this.providerStore.getDataPermissionEvaluatorFactory());
        }
        return new FmlExecInfoProvider(null, param.getFormulaSchemeKey(), dimensionCollection);
    }

    private IFmlExecInfoProvider buildAllFml(BaseCalFormFmlParam param, FormSchemeDefine formScheme, DimensionCollection dimensionCollection, Set<String> accessForms, DataPermissionEvaluatorFactory dataPermissionEvaluatorFactory) {
        if (CollectionUtils.isEmpty(accessForms)) {
            return new FmlExecInfoProvider(null, param.getFormulaSchemeKey(), dimensionCollection);
        }
        List allFml = this.formulaRunTimeController.getParsedExpressionByForm(param.getFormulaSchemeKey(), null, DataEngineConsts.FormulaType.CALCULATE);
        ArrayList<IParsedExpression> fml = new ArrayList<IParsedExpression>();
        allFml.forEach(o -> {
            if (o.getFormKey() == null || accessForms.contains(o.getFormKey())) {
                fml.add((IParsedExpression)o);
            }
        });
        List<IParsedExpression> zbWriteAccessFml = this.beanHelper.getCalculateHelper().getZBWriteAccessFml(formScheme, dataPermissionEvaluatorFactory, param.getDimensionCombination(), fml);
        FmlExecInfo fmlExecInfo = new FmlExecInfo(param.getDimensionCombination().toDimensionValueSet(), zbWriteAccessFml);
        return new FmlExecInfoProvider(Collections.singletonList(fmlExecInfo), param.getFormulaSchemeKey(), dimensionCollection);
    }

    private DimensionCollection getDimensionCollection(DimensionCombination dimensionCombination, FormSchemeDefine formScheme) {
        DimensionCollectionBuilder dimensionCollectionBuilder = new DimensionCollectionBuilder();
        for (FixedDimensionValue f : dimensionCombination) {
            if (this.entityUtil.getContextMainDimId(formScheme.getDw()).equals(f.getEntityID())) {
                dimensionCollectionBuilder.setDWValue(f.getName(), f.getEntityID(), new Object[]{f.getValue()});
                continue;
            }
            dimensionCollectionBuilder.setEntityValue(f.getName(), f.getEntityID(), new Object[]{f.getValue()});
        }
        return dimensionCollectionBuilder.getCollection();
    }

    private List<DimensionAccessFormInfo.AccessFormInfo> getAccessFormInfos(List<String> formKeys, FormSchemeDefine formScheme, DimensionCollection dimensionCollection) {
        IDataAccessFormService dataAccessFormService = this.dataAccessServiceProvider.getDataAccessFormService();
        AccessFormParam accessFormParam = new AccessFormParam();
        accessFormParam.setTaskKey(formScheme.getTaskKey());
        accessFormParam.setFormSchemeKey(formScheme.getKey());
        accessFormParam.setFormKeys(formKeys);
        accessFormParam.setCollectionMasterKey(dimensionCollection);
        accessFormParam.setFormAccessLevel(AccessLevel.FormAccessLevel.FORM_DATA_SYSTEM_WRITE);
        DimensionAccessFormInfo batchAccessForms = dataAccessFormService.getBatchAccessForms(accessFormParam);
        return batchAccessForms.getAccessForms();
    }

    private List<String> getColIDs(String formKey, FormSchemeDefine formSchemeDefine) {
        ArrayList<String> result = new ArrayList<String>();
        List allLinksInForm = this.runTimeViewController.getAllLinksInForm(formKey);
        Map<DataLinkType, List<DataLinkDefine>> collect = allLinksInForm.stream().filter(o -> o != null && DataLinkType.DATA_LINK_TYPE_FORMULA != o.getType() && StringUtils.hasText(o.getLinkExpression())).filter(CommonUtils.distinctByKey(DataLinkDefine::getLinkExpression)).collect(Collectors.groupingBy(DataLinkDefine::getType));
        for (Map.Entry<DataLinkType, List<DataLinkDefine>> e : collect.entrySet()) {
            if (DataLinkType.DATA_LINK_TYPE_FMDM == e.getKey()) {
                String mainDimId = this.entityUtil.getContextMainDimId(formSchemeDefine.getDw());
                IEntityModel entityModel = this.entityMetaService.getEntityModel(mainDimId);
                for (DataLinkDefine dataLinkDefine : e.getValue()) {
                    IEntityAttribute attribute = entityModel.getAttribute(dataLinkDefine.getLinkExpression());
                    if (attribute == null) {
                        LOGGER.debug("\u5f53\u524d\u62a5\u8868\u4e0b\u7684\u5c01\u9762\u4ee3\u7801\u94fe\u63a5{}\u672a\u627e\u5230\u5173\u8054\u7684\u4e3b\u7ef4\u5ea6\u5b9e\u4f53\u5c5e\u6027{}", (Object)dataLinkDefine.getKey(), (Object)dataLinkDefine.getLinkExpression());
                        continue;
                    }
                    String colId = attribute.getID();
                    result.add(colId);
                }
                continue;
            }
            List deployInfoByDataFieldKeys = this.runtimeDataSchemeService.getDeployInfoByDataFieldKeys((String[])e.getValue().stream().map(DataLinkDefine::getLinkExpression).toArray(String[]::new));
            if (CollectionUtils.isEmpty(deployInfoByDataFieldKeys)) continue;
            result.addAll(deployInfoByDataFieldKeys.stream().filter(Objects::nonNull).map(DataFieldDeployInfo::getColumnModelKey).collect(Collectors.toList()));
        }
        return result;
    }
}

