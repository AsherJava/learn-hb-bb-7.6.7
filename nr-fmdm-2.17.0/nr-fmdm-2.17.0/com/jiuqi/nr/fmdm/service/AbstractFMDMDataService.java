/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.authz2.service.SystemIdentityService
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.dataservice.core.access.AuthType
 *  com.jiuqi.nr.dataservice.core.access.DataPermission
 *  com.jiuqi.nr.dataservice.core.access.DataPermissionEvaluator
 *  com.jiuqi.nr.dataservice.core.access.DataPermissionEvaluatorFactory
 *  com.jiuqi.nr.dataservice.core.access.DataPermissionResource
 *  com.jiuqi.nr.dataservice.core.access.EvaluatorParam
 *  com.jiuqi.nr.dataservice.core.access.ResouceType
 *  com.jiuqi.nr.dataservice.core.common.IProviderStore
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionUtil
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.engine.result.CheckFailNodeInfo
 *  com.jiuqi.nr.entity.engine.result.EntityCheckResult
 *  com.jiuqi.nr.entity.engine.result.EntityUpdateResult
 *  com.jiuqi.nr.entity.exception.UnauthorizedEntityException
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityAuthorityService
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.common.utils.PeriodUtils
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  org.apache.shiro.util.Assert
 *  org.jetbrains.annotations.NotNull
 */
package com.jiuqi.nr.fmdm.service;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.dataservice.core.access.AuthType;
import com.jiuqi.nr.dataservice.core.access.DataPermission;
import com.jiuqi.nr.dataservice.core.access.DataPermissionEvaluator;
import com.jiuqi.nr.dataservice.core.access.DataPermissionEvaluatorFactory;
import com.jiuqi.nr.dataservice.core.access.DataPermissionResource;
import com.jiuqi.nr.dataservice.core.access.EvaluatorParam;
import com.jiuqi.nr.dataservice.core.access.ResouceType;
import com.jiuqi.nr.dataservice.core.common.IProviderStore;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionUtil;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.engine.result.CheckFailNodeInfo;
import com.jiuqi.nr.entity.engine.result.EntityCheckResult;
import com.jiuqi.nr.entity.engine.result.EntityUpdateResult;
import com.jiuqi.nr.entity.exception.UnauthorizedEntityException;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityAuthorityService;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.fmdm.BatchFMDMDTO;
import com.jiuqi.nr.fmdm.FMDMDataDTO;
import com.jiuqi.nr.fmdm.IFMDMData;
import com.jiuqi.nr.fmdm.IFMDMDataService;
import com.jiuqi.nr.fmdm.common.FMDMModifyTypeEnum;
import com.jiuqi.nr.fmdm.common.Utils;
import com.jiuqi.nr.fmdm.domain.AbstractFMDMDataDO;
import com.jiuqi.nr.fmdm.domain.EntityDataDO;
import com.jiuqi.nr.fmdm.domain.FMDMModifyDTO;
import com.jiuqi.nr.fmdm.domain.FMDMUpdateResult;
import com.jiuqi.nr.fmdm.exception.FMDMNoWriteAccessException;
import com.jiuqi.nr.fmdm.exception.FMDMUpdateException;
import com.jiuqi.nr.fmdm.internal.check.CheckNodeInfo;
import com.jiuqi.nr.fmdm.internal.check.FMDMCheckFailNodeInfo;
import com.jiuqi.nr.fmdm.internal.check.FMDMCheckResult;
import com.jiuqi.nr.fmdm.internal.dto.QueryParamDTO;
import com.jiuqi.nr.fmdm.internal.service.ZBQueryService;
import com.jiuqi.nr.fmdm.option.OrgCodeEditOption;
import com.jiuqi.nr.fmdm.service.IBBLXDataDetermineService;
import com.jiuqi.nr.fmdm.service.ICheckFilter;
import com.jiuqi.nr.fmdm.validator.DataAddValidator;
import com.jiuqi.nr.fmdm.validator.DataUpdateValidator;
import com.jiuqi.nr.period.common.utils.PeriodUtils;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import org.apache.shiro.util.Assert;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public abstract class AbstractFMDMDataService
implements IFMDMDataService {
    private static final Logger logs = LoggerFactory.getLogger(AbstractFMDMDataService.class);
    @Autowired
    protected IEntityDataService entityDataService;
    @Autowired
    protected IEntityMetaService entityMetaService;
    @Autowired
    protected IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    protected IBBLXDataDetermineService bblxDataDetermineService;
    @Autowired
    protected IDataDefinitionRuntimeController runtimeController;
    @Autowired
    protected IRunTimeViewController runTimeViewController;
    @Autowired
    protected IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    protected ICheckFilter checkFilter;
    @Autowired
    protected PeriodEngineService periodEngineService;
    @Autowired
    protected OrgCodeEditOption orgCodeEditOption;
    @Autowired
    protected ZBQueryService zbQueryService;
    @Autowired
    private List<DataAddValidator> addValidators;
    @Autowired
    private List<DataUpdateValidator> updateValidators;
    @Autowired
    private SystemIdentityService systemIdentityService;
    @Autowired
    private IEntityAuthorityService entityAuthorityService;
    @Autowired
    private IPeriodEntityAdapter periodEntityAdapter;
    private static final String DEFAULT_PARENT_CODE = "-";

    @Override
    public List<IFMDMData> list(FMDMDataDTO fmdmDataDTO) {
        if (fmdmDataDTO.getDimensionCombination() == null && fmdmDataDTO.getDimensionValueSet() != null) {
            DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder(fmdmDataDTO.getDimensionValueSet());
            fmdmDataDTO.setDimensionCombination(dimensionCombinationBuilder.getCombination());
        }
        QueryParamDTO queryParamDTO = QueryParamDTO.getInstance(fmdmDataDTO);
        List<? extends AbstractFMDMDataDO> entityData = this.doList(queryParamDTO);
        return new ArrayList<IFMDMData>(entityData);
    }

    @Override
    public IFMDMData queryFmdmData(FMDMDataDTO fmdmDataDTO, DimensionCombination dimensionCombination) {
        fmdmDataDTO.setDimensionCombination(dimensionCombination);
        QueryParamDTO queryParamDTO = QueryParamDTO.getInstance(fmdmDataDTO);
        List<? extends AbstractFMDMDataDO> entityData = this.doList(queryParamDTO);
        return entityData.isEmpty() ? null : (IFMDMData)entityData.get(0);
    }

    @Override
    public List<IFMDMData> list(FMDMDataDTO fmdmDataDTO, DimensionCollection dimensionCollection) {
        ArrayList<IFMDMData> resultList = new ArrayList<IFMDMData>();
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(fmdmDataDTO.getFormSchemeKey());
        IEntityDefine queryEntity = this.entityMetaService.queryEntity(formScheme.getDw());
        List<DimensionValueSet> dimensionValueSets = this.mergeDimensionByOrgcode(dimensionCollection, queryEntity.getDimensionName());
        QueryParamDTO queryParamDTO = QueryParamDTO.getInstance(fmdmDataDTO);
        queryParamDTO.setDimensionCollection(dimensionCollection);
        queryParamDTO.setEntityDimsionName(queryEntity.getDimensionName());
        queryParamDTO.setMergedByOrgCodeDims(dimensionValueSets);
        boolean isMultOrg = dimensionValueSets.size() > 1;
        for (DimensionValueSet dimensionValueSet : dimensionValueSets) {
            if (dimensionValueSet.getValue(queryEntity.getDimensionName()) instanceof List && ((List)dimensionValueSet.getValue(queryEntity.getDimensionName())).size() > 1) {
                isMultOrg = true;
            }
            DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder(dimensionValueSet);
            queryParamDTO.setDimensionCombination(dimensionCombinationBuilder.getCombination());
            List<? extends AbstractFMDMDataDO> entityData = this.doList(queryParamDTO);
            resultList.addAll(entityData);
        }
        if (fmdmDataDTO.getSorted().booleanValue() && !fmdmDataDTO.getSortedByQuery().booleanValue() && isMultOrg) {
            List<EntityDataDO> entityData = queryParamDTO.getEntityList();
            ArrayList<IFMDMData> temList = new ArrayList<IFMDMData>();
            block1: for (EntityDataDO orderDO : entityData) {
                for (IFMDMData resultData : resultList) {
                    if (!orderDO.getFMDMKey().equals(resultData.getFMDMKey())) continue;
                    temList.add(resultData);
                    resultList.remove(resultData);
                    continue block1;
                }
            }
            return temList;
        }
        return resultList;
    }

    private List<? extends AbstractFMDMDataDO> doList(QueryParamDTO queryParamDTO) {
        String formSchemeKey = queryParamDTO.getFormSchemeKey();
        Assert.notNull((Object)formSchemeKey, (String)"\u53c2\u6570fmdmDataDTO\u4e2d\u62a5\u8868\u65b9\u6848\u7684\u503c\u4e0d\u80fd\u4e3a\u7a7a");
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        this.buildEnv(queryParamDTO, formScheme);
        return this.list(queryParamDTO);
    }

    private void buildEnv(QueryParamDTO queryParamDTO, FormSchemeDefine formScheme) {
        queryParamDTO.setEntityId(Utils.getEntityId(formScheme, queryParamDTO.getDwEntityId()));
        if (queryParamDTO.getContext() == null) {
            ReportFmlExecEnvironment iFmlExecEnvironment = new ReportFmlExecEnvironment(this.runTimeViewController, this.runtimeController, this.entityViewRunTimeController, formScheme.getKey());
            ExecutorContext context = new ExecutorContext(this.runtimeController);
            context.setEnv((IFmlExecEnvironment)iFmlExecEnvironment);
            queryParamDTO.setContext((IContext)context);
        }
        if (queryParamDTO.isFilter()) {
            String filterExpression = Utils.getFilterExpression();
            if (!StringUtils.hasText(filterExpression)) {
                EntityViewDefine entityViewDefine = this.runTimeViewController.getViewByFormSchemeKey(formScheme.getKey());
                filterExpression = entityViewDefine.getRowFilterExpression();
            }
            queryParamDTO.setFilterExpression(filterExpression);
        }
        Optional<FormDefine> formDefine = this.getFormDefine(formScheme.getKey());
        formDefine.ifPresent(define -> queryParamDTO.setFormKey(define.getKey()));
    }

    @Override
    public FMDMUpdateResult add(FMDMDataDTO fmdmDataDTO) throws FMDMUpdateException {
        IEntityModel entityModel;
        IEntityAttribute parentField;
        if (fmdmDataDTO.getDimensionCombination() == null && fmdmDataDTO.getDimensionValueSet() != null) {
            DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder(fmdmDataDTO.getDimensionValueSet());
            fmdmDataDTO.setDimensionCombination(dimensionCombinationBuilder.getCombination());
        }
        String formSchemeKey = fmdmDataDTO.getFormSchemeKey();
        Assert.notNull((Object)formSchemeKey, (String)"\u53c2\u6570fmdmDataDTO\u4e2d\u62a5\u8868\u65b9\u6848\u7684\u503c\u4e0d\u80fd\u4e3a\u7a7a");
        ArrayList<FMDMCheckFailNodeInfo> failNodeInfos = new ArrayList<FMDMCheckFailNodeInfo>();
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        if (!CollectionUtils.isEmpty(this.addValidators)) {
            for (DataAddValidator addValidator : this.addValidators) {
                fmdmDataDTO.setModifyType(FMDMModifyTypeEnum.ADD);
                List<FMDMCheckFailNodeInfo> fails = addValidator.check(fmdmDataDTO);
                if (fails == null) continue;
                failNodeInfos.addAll(fails);
            }
        }
        QueryParamDTO queryParamDTO = QueryParamDTO.getInstance(fmdmDataDTO);
        this.buildEnv(queryParamDTO, formScheme);
        if (!fmdmDataDTO.isIgnoreAccess() && (parentField = (entityModel = this.entityMetaService.getEntityModel(queryParamDTO.getEntityId())).getParentField()) != null && queryParamDTO.getEntityModify() != null) {
            String parentCodeValue = (String)queryParamDTO.getEntityModify().get(parentField.getCode());
            if (StringUtils.hasText(parentCodeValue) && !DEFAULT_PARENT_CODE.equals(parentCodeValue)) {
                String unitDimName = this.entityMetaService.getDimensionName(formScheme.getDw());
                DimensionValueSet dimensionValueSet = fmdmDataDTO.getDimensionCombination().toDimensionValueSet();
                dimensionValueSet.setValue(unitDimName, (Object)parentCodeValue);
                boolean canWrite = this.writeable(formSchemeKey, new DimensionCombinationBuilder(dimensionValueSet).getCombination());
                if (!canWrite) {
                    throw new FMDMNoWriteAccessException("\u8be5\u8282\u70b9\u7236\u7ea7\u6ca1\u6709\u7ba1\u7406\u6743\u9650\uff0c\u5c01\u9762\u4ee3\u7801\u4e0d\u53ef\u65b0\u589e\u4e0b\u7ea7\uff01");
                }
            } else if (!this.systemIdentityService.isAdmin()) {
                throw new FMDMNoWriteAccessException("\u4e0d\u5141\u8bb8\u65b0\u589e\u6839\u8282\u70b9\uff01");
            }
        }
        queryParamDTO.setPreCheck(!CollectionUtils.isEmpty(failNodeInfos));
        this.dataPreProcessing(fmdmDataDTO, queryParamDTO.getFormKey());
        EntityUpdateResult entityAddResult = this.add(queryParamDTO, fmdmDataDTO);
        return this.buildResult(failNodeInfos, queryParamDTO, entityAddResult);
    }

    @NotNull
    private FMDMUpdateResult buildResult(List<FMDMCheckFailNodeInfo> failNodeInfos, QueryParamDTO queryParamDTO, EntityUpdateResult entityAddResult) {
        Map codeToKey = entityAddResult.getCodeToKey();
        FMDMCheckResult entityCheckResult = this.mergeCheckResult(failNodeInfos, entityAddResult);
        FMDMUpdateResult result = new FMDMUpdateResult();
        result.setCodeToMap(codeToKey);
        result.setFMDMCheckResult(entityCheckResult);
        if (!CollectionUtils.isEmpty(codeToKey)) {
            ArrayList<DimensionValueSet> dimensionValueSets = new ArrayList<DimensionValueSet>();
            dimensionValueSets.add(queryParamDTO.getDimensionCombination().toDimensionValueSet());
            result.setDimensionValueSet(dimensionValueSets);
        }
        return result;
    }

    private Optional<FormDefine> getFormDefine(String formSchemeKey) {
        List formDefines = this.runTimeViewController.queryAllFormDefinesByFormScheme(formSchemeKey);
        return formDefines.stream().filter(e -> FormType.FORM_TYPE_NEWFMDM.equals((Object)e.getFormType())).findFirst();
    }

    private List<FMDMCheckFailNodeInfo> getEntityCheckResult(EntityUpdateResult entityCheck) {
        ArrayList<FMDMCheckFailNodeInfo> failNodeInfos = new ArrayList<FMDMCheckFailNodeInfo>();
        EntityCheckResult checkResult = entityCheck.getCheckResult();
        List failInfos = checkResult.getFailInfos();
        Map<String, List<CheckFailNodeInfo>> checkMap = failInfos.stream().collect(Collectors.groupingBy(CheckFailNodeInfo::getAttributeCode));
        int errorType = -1;
        checkMap.forEach((key, value) -> {
            FMDMCheckFailNodeInfo nodeInfo = new FMDMCheckFailNodeInfo();
            CheckFailNodeInfo checkFailNodeInfo = (CheckFailNodeInfo)value.get(0);
            nodeInfo.setFieldTitle(checkFailNodeInfo.getAttributeCode());
            nodeInfo.setFieldCode(checkFailNodeInfo.getAttributeCode());
            for (CheckFailNodeInfo failNodeInfo : value) {
                CheckNodeInfo node = new CheckNodeInfo();
                node.setType(errorType);
                node.setContent(failNodeInfo.getMessage());
                nodeInfo.addNode(node);
            }
            failNodeInfos.add(nodeInfo);
        });
        return failNodeInfos;
    }

    private FMDMCheckResult mergeCheckResult(List<FMDMCheckFailNodeInfo> formulaCheck, EntityUpdateResult entityCheck) {
        FMDMCheckResult checkResult = new FMDMCheckResult();
        checkResult.addResult(formulaCheck);
        if (entityCheck != null) {
            checkResult.addResult(this.getEntityCheckResult(entityCheck));
        }
        List<FMDMCheckFailNodeInfo> results = checkResult.getResults();
        Map<String, List<FMDMCheckFailNodeInfo>> codeGroup = results.stream().collect(Collectors.groupingBy(FMDMCheckFailNodeInfo::getFieldCode));
        checkResult.getResults().clear();
        codeGroup.forEach((key, value) -> {
            FMDMCheckFailNodeInfo failNodeInfo = (FMDMCheckFailNodeInfo)value.get(0);
            ArrayList<CheckNodeInfo> nodes = new ArrayList<CheckNodeInfo>();
            for (FMDMCheckFailNodeInfo nodeInfo : value) {
                nodes.addAll(nodeInfo.getNodes());
            }
            failNodeInfo.setNodes(nodes);
            checkResult.addResult(failNodeInfo);
        });
        return checkResult;
    }

    @Override
    public FMDMUpdateResult update(FMDMDataDTO fmdmDataDTO) throws FMDMUpdateException {
        boolean haveAccess;
        EvaluatorParam evaluatorParam;
        DataPermissionEvaluatorFactory evaluatorFactory;
        DataPermissionEvaluator evaluator;
        if (fmdmDataDTO.getDimensionCombination() == null && fmdmDataDTO.getDimensionValueSet() != null) {
            DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder(fmdmDataDTO.getDimensionValueSet());
            fmdmDataDTO.setDimensionCombination(dimensionCombinationBuilder.getCombination());
        }
        String formSchemeKey = fmdmDataDTO.getFormSchemeKey();
        Assert.notNull((Object)formSchemeKey, (String)"\u53c2\u6570fmdmDataDTO\u4e2d\u62a5\u8868\u65b9\u6848\u7684\u503c\u4e0d\u80fd\u4e3a\u7a7a");
        ArrayList<FMDMCheckFailNodeInfo> failNodeInfos = new ArrayList<FMDMCheckFailNodeInfo>();
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        if (!CollectionUtils.isEmpty(this.updateValidators)) {
            for (DataUpdateValidator updateValidator : this.updateValidators) {
                fmdmDataDTO.setModifyType(FMDMModifyTypeEnum.UPDATE);
                List<FMDMCheckFailNodeInfo> fails = updateValidator.check(fmdmDataDTO);
                if (fails == null) continue;
                failNodeInfos.addAll(fails);
            }
        }
        QueryParamDTO queryParamDTO = QueryParamDTO.getInstance(fmdmDataDTO);
        this.buildEnv(queryParamDTO, formScheme);
        queryParamDTO.setPreCheck(!CollectionUtils.isEmpty(failNodeInfos));
        IProviderStore providerStore = fmdmDataDTO.getProviderStore();
        if (providerStore != null && (evaluator = (evaluatorFactory = providerStore.getDataPermissionEvaluatorFactory()).createEvaluator(evaluatorParam = new EvaluatorParam(formScheme.getTaskKey(), formSchemeKey, ResouceType.FORM.getCode()), queryParamDTO.getDimensionCombination(), Collections.singletonList(queryParamDTO.getFormKey()))) != null && !(haveAccess = evaluator.haveAccess(fmdmDataDTO.getDimensionCombination(), queryParamDTO.getFormKey(), AuthType.WRITEABLE, fmdmDataDTO.getIgnorePermissions()))) {
            throw new FMDMUpdateException("\u5f53\u524d\u7ef4\u5ea6\u3010" + fmdmDataDTO.getDimensionCombination() + "\u3011\u6ca1\u6709\u5199\u5165\u6743\u9650\uff0c\u4e0d\u6267\u884c\u66f4\u65b0\u64cd\u4f5c\uff01");
        }
        this.dataPreProcessing(fmdmDataDTO, queryParamDTO.getFormKey());
        EntityUpdateResult entityUpdateResult = this.update(queryParamDTO, fmdmDataDTO);
        return this.buildResult(failNodeInfos, queryParamDTO, entityUpdateResult);
    }

    @Override
    public FMDMUpdateResult delete(FMDMDataDTO fmdmDataDTO) throws FMDMUpdateException {
        boolean canWrite;
        if (fmdmDataDTO.getDimensionCombination() == null && fmdmDataDTO.getDimensionValueSet() != null) {
            DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder(fmdmDataDTO.getDimensionValueSet());
            fmdmDataDTO.setDimensionCombination(dimensionCombinationBuilder.getCombination());
        }
        String formSchemeKey = fmdmDataDTO.getFormSchemeKey();
        Assert.notNull((Object)formSchemeKey, (String)"\u53c2\u6570fmdmDataDTO\u4e2d\u62a5\u8868\u65b9\u6848\u7684\u503c\u4e0d\u80fd\u4e3a\u7a7a");
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        if (!fmdmDataDTO.isIgnoreAccess() && !(canWrite = this.writeable(formSchemeKey, fmdmDataDTO.getDimensionCombination()))) {
            throw new FMDMNoWriteAccessException("\u8be5\u8282\u70b9\u6ca1\u6709\u7ba1\u7406\u6743\u9650\uff0c\u5c01\u9762\u4ee3\u7801\u4e0d\u53ef\u5220\u9664\uff01");
        }
        FMDMUpdateResult result = new FMDMUpdateResult();
        QueryParamDTO queryParamDTO = QueryParamDTO.getInstance(fmdmDataDTO);
        this.buildEnv(queryParamDTO, formScheme);
        EntityUpdateResult entityDeleteResult = this.delete(queryParamDTO);
        List successKeys = entityDeleteResult.getSuccessKeys();
        result.setCodeToMap(entityDeleteResult.getCodeToKey());
        String dimensionName = this.entityMetaService.getDimensionName(queryParamDTO.getEntityId());
        ArrayList<DimensionValueSet> dimensionValueSets = new ArrayList<DimensionValueSet>();
        for (String successKey : successKeys) {
            this.assignDimension(queryParamDTO.getDimensionCombination().toDimensionValueSet(), dimensionName, successKey, dimensionValueSets);
        }
        result.setDimensionValueSet(dimensionValueSets);
        return result;
    }

    private void assignDimension(DimensionValueSet queryDimension, String dimensionName, Object value, List<DimensionValueSet> dimensionValueSets) {
        DimensionValueSet dimension = new DimensionValueSet();
        dimension.assign(queryDimension);
        dimension.setValue(dimensionName, value);
        dimensionValueSets.add(dimension);
    }

    @Override
    public FMDMUpdateResult batchAddFMDM(BatchFMDMDTO batchFMDMDTO) throws FMDMUpdateException {
        FMDMUpdateResult updateResult;
        try {
            updateResult = this.batchExecute(batchFMDMDTO, this::batchAdd);
        }
        catch (Exception e) {
            logs.error("\u5c01\u9762\u4ee3\u7801\u6279\u91cf\u65b0\u589e\u65f6\u53d1\u751f\u9519\u8bef\uff1a{}", (Object)e.getMessage(), (Object)e);
            throw new FMDMUpdateException("\u5c01\u9762\u4ee3\u7801\u6279\u91cf\u66f4\u65b0\u51fa\u9519\uff1a" + e.getMessage(), e);
        }
        return updateResult;
    }

    @Override
    public FMDMUpdateResult batchUpdateFMDM(BatchFMDMDTO batchFMDMDTO) throws FMDMUpdateException {
        FMDMUpdateResult updateResult = null;
        List<FMDMDataDTO> fmdmList = batchFMDMDTO.getFmdmList();
        String formSchemeKey = batchFMDMDTO.getFormSchemeKey();
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(formSchemeKey);
        Optional<FormDefine> formDefine = this.getFormDefine(formSchemeKey);
        HashSet<DimensionCombination> noAccessDims = new HashSet<DimensionCombination>();
        if (formSchemeDefine != null && formDefine.isPresent() && !fmdmList.isEmpty()) {
            EvaluatorParam evaluatorParam;
            DataPermissionEvaluatorFactory evaluatorFactory;
            DataPermissionEvaluator evaluator;
            String formKey = formDefine.get().getKey();
            List dimensionCombinations = fmdmList.stream().map(e -> {
                DimensionCombination dimensionCombination = e.getDimensionCombination();
                if (dimensionCombination == null && e.getDimensionValueSet() != null) {
                    DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder(e.getDimensionValueSet());
                    dimensionCombination = dimensionCombinationBuilder.getCombination();
                    e.setDimensionCombination(dimensionCombination);
                }
                return dimensionCombination;
            }).collect(Collectors.toList());
            DimensionCollection dimensionCollection = DimensionCollectionUtil.buildCollection(dimensionCombinations);
            IProviderStore providerStore = batchFMDMDTO.getProviderStore();
            if (providerStore != null && (evaluator = (evaluatorFactory = providerStore.getDataPermissionEvaluatorFactory()).createEvaluator(evaluatorParam = new EvaluatorParam(formSchemeDefine.getTaskKey(), formSchemeKey, ResouceType.FORM.getCode()), dimensionCollection, Collections.singletonList(formKey))) != null) {
                DataPermission dataPermission = evaluator.haveAccess(dimensionCollection, Collections.singleton(formKey), AuthType.WRITEABLE, batchFMDMDTO.getIgnorePermissions());
                Collection unAccessResources = dataPermission.getUnAccessResources();
                Iterator<FMDMDataDTO> iterator = fmdmList.iterator();
                block2: while (iterator.hasNext()) {
                    FMDMDataDTO fmdmDataDTO = iterator.next();
                    DimensionCombination dimensionCombination = fmdmDataDTO.getDimensionCombination();
                    for (DataPermissionResource unAccessResource : unAccessResources) {
                        if (!unAccessResource.getDimensionCombination().equals(dimensionCombination)) continue;
                        noAccessDims.add(dimensionCombination);
                        iterator.remove();
                        continue block2;
                    }
                }
            }
        }
        if (!fmdmList.isEmpty()) {
            try {
                updateResult = this.batchExecute(batchFMDMDTO, this::batchUpdate);
            }
            catch (Exception e2) {
                logs.error("\u5c01\u9762\u4ee3\u7801\u6279\u91cf\u66f4\u65b0\u65f6\u53d1\u751f\u9519\u8bef\uff1a{}", (Object)e2.getMessage(), (Object)e2);
                throw new FMDMUpdateException("\u5c01\u9762\u4ee3\u7801\u6279\u91cf\u66f4\u65b0\u51fa\u9519\uff1a" + e2.getMessage(), e2);
            }
        }
        if (!noAccessDims.isEmpty()) {
            if (updateResult == null) {
                updateResult = new FMDMUpdateResult();
                updateResult.setCodeToMap(new HashMap<String, String>());
            }
            updateResult.getNoAccessDims().addAll(noAccessDims);
        }
        return updateResult;
    }

    public FMDMUpdateResult batchExecute(BatchFMDMDTO batchFMDMDTO, BiFunction<QueryParamDTO, List<FMDMModifyDTO>, EntityUpdateResult> function) {
        String formSchemeKey = batchFMDMDTO.getFormSchemeKey();
        Assert.notNull((Object)formSchemeKey, (String)"\u53c2\u6570fmdmDataDTO\u4e2d\u62a5\u8868\u65b9\u6848\u7684\u503c\u4e0d\u80fd\u4e3a\u7a7a");
        List<FMDMDataDTO> fmdmList = batchFMDMDTO.getFmdmList();
        if (CollectionUtils.isEmpty(fmdmList)) {
            logs.info("\u6279\u91cf\u66f4\u65b0\u5c01\u9762\u4ee3\u7801\u7684\u6570\u636e\u4e3a\u7a7a.");
            return null;
        }
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        QueryParamDTO queryParamDTO = new QueryParamDTO(Utils.getEntityId(formScheme), formSchemeKey, batchFMDMDTO.getContext());
        queryParamDTO.setDataMasking(batchFMDMDTO.isDataMasking());
        this.buildEnv(queryParamDTO, formScheme);
        this.batchDataPreProcessing(fmdmList, queryParamDTO.getFormKey());
        queryParamDTO.setIgnoreErrorData(batchFMDMDTO.isIgnoreCheckErrorData());
        DimensionValueSet qpDimensionValueSet = new DimensionValueSet();
        if (batchFMDMDTO.getDimensionValueSet() != null) {
            qpDimensionValueSet.setValue("DATATIME", batchFMDMDTO.getDimensionValueSet().getValue("DATATIME"));
        }
        DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder(qpDimensionValueSet);
        queryParamDTO.setDimensionCombination(dimensionCombinationBuilder.getCombination());
        ArrayList<FMDMDataDTO> updateList = new ArrayList<FMDMDataDTO>(fmdmList);
        for (FMDMModifyDTO fMDMModifyDTO : updateList) {
            if (fMDMModifyDTO.getDimensionCombination() != null || fMDMModifyDTO.getDimensionValueSet() == null) continue;
            DimensionCombinationBuilder dcBuilder = new DimensionCombinationBuilder(fMDMModifyDTO.getDimensionValueSet());
            fMDMModifyDTO.setDimensionCombination(dcBuilder.getCombination());
        }
        EntityUpdateResult entityUpdateResult = function.apply(queryParamDTO, updateList);
        FMDMCheckResult fMDMCheckResult = this.mergeCheckResult(new ArrayList<FMDMCheckFailNodeInfo>(), entityUpdateResult);
        FMDMUpdateResult result = new FMDMUpdateResult();
        result.setCodeToMap(entityUpdateResult.getCodeToKey());
        result.setFMDMCheckResult(fMDMCheckResult);
        ArrayList<DimensionValueSet> dimensionValueSets = new ArrayList<DimensionValueSet>(updateList.size());
        for (FMDMModifyDTO fMDMModifyDTO : updateList) {
            DimensionValueSet dimensionValueSet = fMDMModifyDTO.getDimensionCombination().toDimensionValueSet();
            dimensionValueSets.add(dimensionValueSet);
        }
        result.setDimensionValueSet(dimensionValueSets);
        return result;
    }

    private List<DimensionValueSet> mergeDimensionByOrgcode(DimensionCollection dimensionCollection, String dimensionName) {
        DimensionValueSet dimensionValueSet;
        ArrayList<DimensionValueSet> dimensionValueSets = new ArrayList<DimensionValueSet>();
        List dimensionCombinations = dimensionCollection.getDimensionCombinations();
        HashMap temMap = new HashMap();
        for (DimensionCombination dimensionCombination : dimensionCombinations) {
            dimensionValueSet = dimensionCombination.toDimensionValueSet();
            dimensionValueSet.clearValue(dimensionName);
            if (Objects.nonNull(temMap.get(dimensionValueSet))) {
                ((List)temMap.get(dimensionValueSet)).add((String)dimensionCombination.getValue(dimensionName));
                continue;
            }
            ArrayList<String> orgList = new ArrayList<String>();
            orgList.add((String)dimensionCombination.getValue(dimensionName));
            temMap.put(dimensionValueSet, orgList);
        }
        for (Map.Entry entry : temMap.entrySet()) {
            dimensionValueSet = (DimensionValueSet)entry.getKey();
            dimensionValueSet.setValue(dimensionName, entry.getValue());
            dimensionValueSets.add(dimensionValueSet);
        }
        return dimensionValueSets;
    }

    private boolean writeable(String formSchemeKey, DimensionCombination collectionKey) {
        if (this.systemIdentityService.isAdmin()) {
            return true;
        }
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        DimensionValueSet masterKey = collectionKey.toDimensionValueSet();
        String unitDimName = this.entityMetaService.getDimensionName(formScheme.getDw());
        String periodDimName = this.periodEntityAdapter.getPeriodEntity(formScheme.getDateTime()).getDimensionName();
        String periodValue = String.valueOf(masterKey.getValue(periodDimName));
        Date startDate = PeriodUtils.getStartDateOfPeriod((String)periodValue, (boolean)true);
        Date endDate = PeriodUtils.getStartDateOfPeriod((String)periodValue, (boolean)false);
        String unitKey = String.valueOf(masterKey.getValue(unitDimName));
        try {
            return this.entityAuthorityService.canEditEntity(formScheme.getDw(), unitKey, startDate, endDate);
        }
        catch (UnauthorizedEntityException e) {
            logs.error("\u5c01\u9762\u4ee3\u7801\u8bfb\u5199\u6743\u9650\u5224\u65ad\u5f02\u5e38");
            return true;
        }
    }

    public Map<String, Boolean> getBatchWriteable(String formSchemeKey, Set<String> unitKeys, String periodValue) {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        Date endDate = PeriodUtils.getStartDateOfPeriod((String)periodValue, (boolean)false);
        Map<String, Boolean> batchWriteable = unitKeys.stream().collect(Collectors.toMap(e -> e, e -> true));
        if (this.systemIdentityService.isAdmin()) {
            return batchWriteable;
        }
        try {
            return this.entityAuthorityService.canEditEntity(formScheme.getDw(), unitKeys, endDate);
        }
        catch (UnauthorizedEntityException e2) {
            logs.error("\u6279\u91cf\u5199\u6743\u9650\u5224\u65ad\u51fa\u9519\uff01", e2);
            return batchWriteable;
        }
    }

    public abstract void dataPreProcessing(FMDMModifyDTO var1, String var2);

    public abstract void batchDataPreProcessing(List<FMDMDataDTO> var1, String var2);

    public abstract List<? extends AbstractFMDMDataDO> list(QueryParamDTO var1);

    public abstract EntityUpdateResult add(QueryParamDTO var1, FMDMModifyDTO var2);

    public abstract EntityUpdateResult update(QueryParamDTO var1, FMDMModifyDTO var2);

    public abstract EntityUpdateResult delete(QueryParamDTO var1);

    public abstract EntityUpdateResult batchAdd(QueryParamDTO var1, List<FMDMModifyDTO> var2);

    public abstract EntityUpdateResult batchUpdate(QueryParamDTO var1, List<FMDMModifyDTO> var2);
}

