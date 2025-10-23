/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.var.VariableManager
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.adapter.impl.org.util.OrgAdapterUtil
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityModify
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.intf.IModifyRow
 *  com.jiuqi.nr.entity.engine.intf.IModifyTable
 *  com.jiuqi.nr.entity.engine.intf.impl.EntityRowImpl
 *  com.jiuqi.nr.entity.engine.intf.impl.ReadonlyTableImpl
 *  com.jiuqi.nr.entity.engine.result.EntityCheckResult
 *  com.jiuqi.nr.entity.engine.result.EntityUpdateResult
 *  com.jiuqi.nr.entity.engine.var.ReferRelation
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityModel
 */
package com.jiuqi.nr.fmdm.service.impl;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.var.VariableManager;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.adapter.impl.org.util.OrgAdapterUtil;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityModify;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.intf.IModifyRow;
import com.jiuqi.nr.entity.engine.intf.IModifyTable;
import com.jiuqi.nr.entity.engine.intf.impl.EntityRowImpl;
import com.jiuqi.nr.entity.engine.intf.impl.ReadonlyTableImpl;
import com.jiuqi.nr.entity.engine.result.EntityCheckResult;
import com.jiuqi.nr.entity.engine.result.EntityUpdateResult;
import com.jiuqi.nr.entity.engine.var.ReferRelation;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.fmdm.FMDMDataDTO;
import com.jiuqi.nr.fmdm.common.Utils;
import com.jiuqi.nr.fmdm.domain.EntityDataDO;
import com.jiuqi.nr.fmdm.domain.FMDMModifyDTO;
import com.jiuqi.nr.fmdm.exception.FMDMDataException;
import com.jiuqi.nr.fmdm.exception.FMDMQueryException;
import com.jiuqi.nr.fmdm.internal.dto.QueryParamDTO;
import com.jiuqi.nr.fmdm.service.AbstractFMDMDataService;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class EntityDataService
extends AbstractFMDMDataService {
    private static final String DEFAULT_PARENT = "-";
    private static final String SYNCCACHEFIRST_KEY = "syncCacheFirst";
    private static final boolean SYNCCACHEFIRST_VALUE = true;
    private static final Logger log = LoggerFactory.getLogger(EntityDataService.class);

    @Override
    public void dataPreProcessing(FMDMModifyDTO modifyDTO, String formKey) {
        if (!modifyDTO.isCompatibility()) {
            return;
        }
        modifyDTO.getEntityModify().putAll(modifyDTO.getModifyValueMap());
    }

    @Override
    public void batchDataPreProcessing(List<FMDMDataDTO> modifyDTO, String formKey) {
        for (FMDMModifyDTO fMDMModifyDTO : modifyDTO) {
            fMDMModifyDTO.getEntityModify().putAll(fMDMModifyDTO.getModifyValueMap());
        }
    }

    public List<? extends EntityDataDO> list(QueryParamDTO queryParamDTO) {
        Assert.isTrue(queryParamDTO.getEntityId() != null, "entityId can not be null");
        List<EntityDataDO> dataList = queryParamDTO.getEntityList();
        String dwDimsionName = queryParamDTO.getEntityDimsionName();
        DimensionCollection dimensionCollection = queryParamDTO.getDimensionCollection();
        DimensionValueSet dimensionValueSet = queryParamDTO.getDimensionCombination().toDimensionValueSet();
        if (Objects.isNull(dataList)) {
            IEntityTable iEntityTable;
            IEntityQuery iEntityQuery = this.entityDataService.newEntityQuery();
            DimensionValueSet queryDimension = Objects.nonNull(dimensionCollection) ? DimensionValueSetUtil.mergeDimensionValueSet((DimensionCollection)dimensionCollection) : dimensionValueSet;
            iEntityQuery.setMasterKeys(queryDimension);
            List<ReferRelation> referRelations = this.buildRefer(queryParamDTO);
            for (ReferRelation relation : referRelations) {
                iEntityQuery.addReferRelation(relation);
            }
            iEntityQuery.setEntityView(this.entityViewRunTimeController.buildEntityView(queryParamDTO.getEntityId(), queryParamDTO.getFilterExpression()));
            iEntityQuery.setExpression(queryParamDTO.getExpression());
            iEntityQuery.sorted(queryParamDTO.getSorted().booleanValue());
            iEntityQuery.sortedByQuery(queryParamDTO.getSortedByQuery().booleanValue());
            iEntityQuery.setAuthorityOperations(queryParamDTO.getAuthorityType());
            if (queryParamDTO.isDataMasking()) {
                iEntityQuery.maskedData();
            }
            try {
                iEntityTable = iEntityQuery.executeReader(this.getContext(queryParamDTO, null));
            }
            catch (Exception e) {
                log.error("\u67e5\u8be2\u5c01\u9762\u4ee3\u7801\u5b9e\u4f53\u8868\u6570\u636e\u65f6\u53d1\u751f\u9519\u8bef\uff1a{}", (Object)e.getMessage(), (Object)e);
                throw new FMDMQueryException("\u67e5\u8be2\u5c01\u9762\u4ee3\u7801\u5b9e\u4f53\u8868\u6570\u636e\u65f6\u53d1\u751f\u9519\u8bef", e);
            }
            List allRows = queryParamDTO.getPageCondition() == null ? iEntityTable.getAllRows() : iEntityTable.getPageRows(queryParamDTO.getPageCondition());
            dataList = new ArrayList<EntityDataDO>();
            if (Objects.nonNull(dimensionCollection)) {
                List<DimensionValueSet> dimensionValueSets = queryParamDTO.getMergedByOrgCodeDims();
                HashMap dimOrgMap = new HashMap();
                IEntityModel entityModel = this.entityMetaService.getEntityModel(queryParamDTO.getEntityId());
                HashMap<String, Object> dimNameFieldCodeMap = new HashMap<String, Object>();
                for (ReferRelation referRelation : referRelations) {
                    String dimName = this.entityMetaService.getDimensionName(referRelation.getViewDefine().getEntityId());
                    String fieldCode = referRelation.getRefer().getOwnField();
                    if (entityModel.getAttribute(fieldCode).isMultival()) continue;
                    dimNameFieldCodeMap.put(dimName, fieldCode);
                }
                for (DimensionValueSet dim : dimensionValueSets) {
                    DimensionValueSet nullOrgDim = new DimensionValueSet(dim);
                    for (Map.Entry entry : dimNameFieldCodeMap.entrySet()) {
                        nullOrgDim.clearValue((String)entry.getKey());
                    }
                    HashSet<Object> orgCodeSet = new HashSet();
                    Object orgCodes = nullOrgDim.getValue(dwDimsionName);
                    if (orgCodes instanceof List) {
                        orgCodeSet = new HashSet((List)orgCodes);
                    } else if (orgCodes instanceof String) {
                        orgCodeSet = new HashSet<String>(Arrays.asList(((String)orgCodes).split(";")));
                    }
                    nullOrgDim.setValue(dwDimsionName, (Object)"");
                    HashSet dimOrgSet = (HashSet)dimOrgMap.get(nullOrgDim);
                    if (dimOrgSet == null) {
                        dimOrgSet = new HashSet();
                    }
                    dimOrgSet.addAll(orgCodeSet);
                    dimOrgMap.put(nullOrgDim, dimOrgSet);
                }
                for (int i = 0; i < allRows.size(); ++i) {
                    IEntityRow row = (IEntityRow)allRows.get(i);
                    String code = row.getValue("CODE").getAsString();
                    for (Map.Entry entry : dimOrgMap.entrySet()) {
                        if (!((Set)entry.getValue()).contains(code)) continue;
                        DimensionValueSet newDim = new DimensionValueSet((DimensionValueSet)entry.getKey());
                        newDim.setValue(dwDimsionName, (Object)code);
                        for (Map.Entry contrast : dimNameFieldCodeMap.entrySet()) {
                            String dimValue = row.getValue((String)contrast.getValue()).getAsString();
                            newDim.setValue((String)contrast.getKey(), (Object)dimValue);
                        }
                        EntityRowImpl newRow = new EntityRowImpl((ReadonlyTableImpl)iEntityTable, newDim, i + 1);
                        dataList.add(new EntityDataDO((IEntityRow)newRow));
                    }
                }
            } else {
                for (IEntityRow row : allRows) {
                    dataList.add(new EntityDataDO(row));
                }
            }
            queryParamDTO.setEntityList(dataList);
        }
        if (Objects.nonNull(dimensionCollection)) {
            ArrayList<EntityDataDO> returnList = new ArrayList<EntityDataDO>();
            DimensionValueSet queryParamDim = new DimensionValueSet(dimensionValueSet);
            for (EntityDataDO entityDataDO : dataList) {
                queryParamDim.setValue(dwDimsionName, entityDataDO.getMasterKey().getValue(dwDimsionName));
                if (!queryParamDim.equals((Object)entityDataDO.getMasterKey())) continue;
                returnList.add(entityDataDO);
            }
            return returnList;
        }
        return dataList;
    }

    private IContext getContext(QueryParamDTO queryParamDTO, String periodView) {
        IContext context = queryParamDTO.getContext();
        if (context instanceof com.jiuqi.np.dataengine.executors.ExecutorContext) {
            ExecutorContext newContext = new ExecutorContext(this.runtimeController);
            Map<String, Boolean> queryParamMap = new HashMap<String, Boolean>();
            if (newContext.getQueryParam() != null) {
                queryParamMap = newContext.getQueryParam();
            }
            queryParamMap.put(SYNCCACHEFIRST_KEY, true);
            newContext.setQueryParam(queryParamMap);
            if (periodView == null) {
                FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(queryParamDTO.getFormSchemeKey());
                periodView = formScheme.getDateTime();
            }
            newContext.setPeriodView(periodView);
            newContext.setVarDimensionValueSet(((com.jiuqi.np.dataengine.executors.ExecutorContext)context).getVarDimensionValueSet());
            IFmlExecEnvironment env = ((com.jiuqi.np.dataengine.executors.ExecutorContext)context).getEnv();
            if (env != null) {
                newContext.setEnv(env);
            }
            newContext.setJQReportModel(((com.jiuqi.np.dataengine.executors.ExecutorContext)context).isJQReportModel());
            VariableManager variableManager = ((com.jiuqi.np.dataengine.executors.ExecutorContext)context).getVariableManager();
            if (variableManager != null) {
                variableManager.getAllVars().forEach(variable -> newContext.getVariableManager().add(variable));
            }
            context = newContext;
        }
        return context;
    }

    private List<ReferRelation> buildRefer(QueryParamDTO queryParamDTO) {
        ArrayList<ReferRelation> referRelations = new ArrayList<ReferRelation>();
        String formSchemeKey = queryParamDTO.getFormSchemeKey();
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
        List reportDimension = this.runtimeDataSchemeService.getDataSchemeDimension(taskDefine.getDataScheme(), DimensionType.DIMENSION);
        DimensionCollection dimensionCollection = queryParamDTO.getDimensionCollection();
        DimensionValueSet dimensionValueSet = Objects.nonNull(dimensionCollection) ? DimensionValueSetUtil.mergeDimensionValueSet((DimensionCollection)dimensionCollection) : queryParamDTO.getDimensionCombination().toDimensionValueSet();
        List refer = this.entityMetaService.getEntityRefer(queryParamDTO.getEntityId());
        for (DataDimension dimension : reportDimension) {
            Object value;
            String dimKey;
            String dimensionName;
            if (!DimensionType.DIMENSION.equals((Object)dimension.getDimensionType()) || dimension.getDimAttribute() == null || !StringUtils.hasText(dimensionName = this.entityMetaService.getDimensionName(dimKey = dimension.getDimKey())) || (value = dimensionValueSet.getValue(dimensionName)) == null || "".equals(value)) continue;
            ReferRelation referRelation = new ReferRelation();
            referRelation.setViewDefine(this.entityViewRunTimeController.buildEntityView(dimKey));
            if (value instanceof List) {
                referRelation.setRange((List)value);
            } else {
                ArrayList<String> keys = new ArrayList<String>(1);
                keys.add(value.toString());
                referRelation.setRange(keys);
            }
            refer.stream().filter(e -> e.getOwnField().equals(dimension.getDimAttribute())).findFirst().ifPresent(arg_0 -> ((ReferRelation)referRelation).setRefer(arg_0));
            referRelations.add(referRelation);
        }
        return referRelations;
    }

    @Override
    public EntityUpdateResult add(QueryParamDTO queryParamDTO, FMDMModifyDTO fmdmData) {
        Map<String, Object> valueMap = fmdmData.getEntityModify();
        if (CollectionUtils.isEmpty(valueMap)) {
            log.error("\u65b0\u589e\u5c01\u9762\u7684\u5b9e\u4f53\u5c5e\u6027\u4e3a\u7a7a");
            throw new FMDMDataException("\u65b0\u589e\u5c01\u9762\u7684\u5b9e\u4f53\u5c5e\u6027\u4e3a\u7a7a");
        }
        DimensionValueSet queryDimension = this.getDataTimeDimension(fmdmData);
        IEntityModify iEntityModify = this.entityDataService.newEntityUpdate();
        iEntityModify.setMasterKeys(queryDimension);
        String entityId = queryParamDTO.getEntityId();
        EntityViewDefine entityViewDefine = this.entityViewRunTimeController.buildEntityView(entityId);
        iEntityModify.setEntityView(entityViewDefine);
        iEntityModify.batchUpdateModel(false);
        iEntityModify.setAuthorityOperations(queryParamDTO.getAuthorityType());
        boolean autoGenerateData = this.bblxDataDetermineService.needAutoGenerate(valueMap, entityId);
        String periodView = this.getPeriodView(queryParamDTO.getFormSchemeKey());
        IEntityModel entityModel = this.entityMetaService.getEntityModel(entityId);
        IEntityAttribute codeField = entityModel.getCodeField();
        IEntityAttribute bizKeyField = entityModel.getBizKeyField();
        Object codeValue = valueMap.get(codeField.getCode());
        if (Utils.emptyObject(codeValue)) {
            log.error("\u65b0\u589e\u5c01\u9762\u7684\u5b9e\u4f53\u6807\u8bc6\u4e3a\u7a7a");
            throw new FMDMDataException("\u65b0\u589e\u5c01\u9762\u7684\u5b9e\u4f53\u6807\u8bc6\u4e3a\u7a7a");
        }
        String orgCode = codeValue.toString().toUpperCase();
        valueMap.put(codeField.getCode(), orgCode);
        if (OrgAdapterUtil.isOrg((String)entityModel.getEntityId())) {
            this.setCode(valueMap, bizKeyField.getCode(), orgCode);
        }
        IContext context = this.getContext(queryParamDTO, periodView);
        try {
            IModifyTable iModifyTable = iEntityModify.executeUpdate(context);
            IModifyRow iModifyRow = iModifyTable.appendNewRow();
            this.setValue(valueMap, entityModel, iModifyRow);
            boolean check = this.checkFilter.check(valueMap, entityId, this.getTime(queryDimension, periodView));
            iModifyRow.setIgnoreCodeCheck(!check);
            iModifyRow.setNeedSync(!autoGenerateData);
            iModifyRow.buildRow();
            if (queryParamDTO.isPreCheck()) {
                EntityUpdateResult entityAddResult = new EntityUpdateResult();
                EntityCheckResult checkResult = iModifyTable.checkData();
                entityAddResult.setCheckResult(checkResult);
                return entityAddResult;
            }
            return iModifyTable.commitChange();
        }
        catch (Exception e) {
            log.error("\u5c01\u9762\u4ee3\u7801\u65b0\u589e\u7ec4\u7ec7\u673a\u6784\u65f6\u51fa\u9519\uff1a{}", (Object)e.getMessage(), (Object)e);
            throw new FMDMDataException(e.getMessage(), e);
        }
    }

    private void setValue(Map<String, Object> valueMap, IEntityModel entityModel, IModifyRow iModifyRow) {
        IEntityAttribute parentField = entityModel.getParentField();
        valueMap.forEach((key, value) -> {
            IEntityAttribute attribute = entityModel.getAttribute(key);
            if (attribute != null) {
                if (parentField != null && parentField.getID().equals(attribute.getID()) && Utils.emptyObject(value)) {
                    value = DEFAULT_PARENT;
                }
                iModifyRow.setValue(key, value);
            }
        });
    }

    @Override
    public EntityUpdateResult update(QueryParamDTO queryParamDTO, FMDMModifyDTO fmdmData) {
        Map<String, Object> valueMap = fmdmData.getEntityModify();
        if (CollectionUtils.isEmpty(valueMap)) {
            return new EntityUpdateResult();
        }
        String entityId = queryParamDTO.getEntityId();
        String dimensionName = this.entityMetaService.getDimensionName(entityId);
        IEntityModify iEntityModify = this.entityDataService.newEntityUpdate();
        EntityViewDefine entityViewDefine = this.entityViewRunTimeController.buildEntityView(entityId);
        DimensionValueSet updateDimension = fmdmData.getDimensionCombination().toDimensionValueSet();
        iEntityModify.setMasterKeys(updateDimension);
        iEntityModify.setEntityView(entityViewDefine);
        iEntityModify.batchUpdateModel(false);
        iEntityModify.setAuthorityOperations(queryParamDTO.getAuthorityType());
        String periodView = this.getPeriodView(queryParamDTO.getFormSchemeKey());
        try {
            IModifyTable iModifyTable = iEntityModify.executeUpdate(this.getContext(queryParamDTO, periodView));
            DimensionValueSet dimensionValueSet = queryParamDTO.getDimensionCombination().toDimensionValueSet();
            IModifyRow iModifyRow = iModifyTable.appendModifyRow(dimensionValueSet);
            IEntityModel entityModel = this.entityMetaService.getEntityModel(entityId);
            String orgCodeName = entityModel.getCodeField().getCode();
            Object orgCode = valueMap.get(orgCodeName);
            if (Objects.nonNull(orgCode)) {
                valueMap.put(orgCodeName, orgCode.toString().toUpperCase());
            }
            this.setValue(valueMap, entityModel, iModifyRow);
            valueMap.put(entityModel.getBizKeyField().getCode(), dimensionValueSet.getValue(dimensionName));
            boolean check = this.checkFilter.check(valueMap, entityId, this.getTime(updateDimension, periodView));
            iModifyRow.setIgnoreCodeCheck(!check);
            iModifyRow.buildRow();
            if (queryParamDTO.isPreCheck()) {
                EntityUpdateResult entityAddResult = new EntityUpdateResult();
                EntityCheckResult checkResult = iModifyTable.checkData();
                entityAddResult.setCheckResult(checkResult);
                return entityAddResult;
            }
            return iModifyTable.commitChange();
        }
        catch (Exception e) {
            log.error("\u5c01\u9762\u4ee3\u7801\u66f4\u65b0\u7ec4\u7ec7\u673a\u6784\u65f6\u51fa\u9519\uff1a{}", (Object)e.getMessage(), (Object)e);
            throw new FMDMDataException(e.getMessage(), e);
        }
    }

    @Override
    public EntityUpdateResult delete(QueryParamDTO queryParamDTO) {
        IEntityModify iEntityModify = this.entityDataService.newEntityUpdate();
        String entityId = queryParamDTO.getEntityId();
        EntityViewDefine entityViewDefine = this.entityViewRunTimeController.buildEntityView(entityId);
        try {
            iEntityModify.setMasterKeys(queryParamDTO.getDimensionCombination().toDimensionValueSet());
            iEntityModify.setEntityView(entityViewDefine);
            iEntityModify.setAuthorityOperations(queryParamDTO.getAuthorityType());
            IModifyTable iModifyTable = iEntityModify.executeUpdate(this.getContext(queryParamDTO, null));
            iModifyTable.deleteAll();
            return iModifyTable.commitChange();
        }
        catch (Exception e) {
            log.error("\u5c01\u9762\u4ee3\u7801\u5220\u9664\u7ec4\u7ec7\u673a\u6784\u65f6\u51fa\u9519\uff1a{}", (Object)e.getMessage(), (Object)e);
            throw new FMDMDataException(e.getMessage(), e);
        }
    }

    @Override
    public EntityUpdateResult batchAdd(QueryParamDTO queryParamDTO, List<FMDMModifyDTO> updateList) {
        String entityId = queryParamDTO.getEntityId();
        EntityViewDefine entityViewDefine = this.entityViewRunTimeController.buildEntityView(entityId);
        IEntityModel entityModel = this.entityMetaService.getEntityModel(entityId);
        IEntityAttribute bizKeyField = entityModel.getBizKeyField();
        IEntityAttribute codeField = entityModel.getCodeField();
        IEntityModify iEntityModify = this.entityDataService.newEntityUpdate();
        DimensionValueSet queryDimension = queryParamDTO.getDimensionCombination().toDimensionValueSet();
        if (queryDimension == null) {
            queryDimension = this.getDataTimeDimension(updateList.get(0));
        }
        iEntityModify.setMasterKeys(queryDimension);
        iEntityModify.setEntityView(entityViewDefine);
        iEntityModify.batchUpdateModel(true);
        iEntityModify.setAuthorityOperations(queryParamDTO.getAuthorityType());
        iEntityModify.ignoreCheckErrorData(queryParamDTO.isIgnoreErrorData());
        ArrayList<Map<String, Object>> filterList = new ArrayList<Map<String, Object>>();
        for (FMDMModifyDTO fmdmDataDO : updateList) {
            Object orgCode;
            Map<String, Object> valueMap = fmdmDataDO.getEntityModify();
            Object code = valueMap.get(bizKeyField.getCode());
            if (code != null) {
                valueMap.put(bizKeyField.getCode(), code.toString().toUpperCase());
            }
            if ((orgCode = valueMap.get(codeField.getCode())) != null) {
                valueMap.put(codeField.getCode(), orgCode.toString().toUpperCase());
            }
            filterList.add(valueMap);
        }
        List<Boolean> autoGenerateData = this.bblxDataDetermineService.filterUnAutoGenerateData(filterList, entityId);
        String periodView = this.getPeriodView(queryParamDTO.getFormSchemeKey());
        List<Boolean> checkList = this.checkFilter.check(filterList, entityId, this.getTime(queryDimension, periodView));
        IContext context = this.getContext(queryParamDTO, periodView);
        boolean isOrg = OrgAdapterUtil.isOrg((String)entityModel.getEntityId());
        try {
            IModifyTable iModifyTable = iEntityModify.executeUpdate(context);
            HashSet<String> addCodes = new HashSet<String>(updateList.size());
            for (int i = 0; i < updateList.size(); ++i) {
                FMDMModifyDTO fmdmData = updateList.get(i);
                Map<String, Object> valueMap = fmdmData.getEntityModify();
                Object codeValue = valueMap.get(codeField.getCode());
                if (Utils.emptyObject(codeValue) || addCodes.contains(codeValue.toString())) continue;
                addCodes.add(codeValue.toString());
                if (isOrg) {
                    this.setCode(valueMap, bizKeyField.getCode(), codeValue.toString());
                }
                IModifyRow iModifyRow = iModifyTable.appendNewRow();
                valueMap.forEach((key, value) -> {
                    IEntityAttribute attribute = entityModel.getAttribute(key);
                    if (attribute != null) {
                        iModifyRow.setValue(key, value);
                    }
                });
                iModifyRow.setIgnoreCodeCheck(checkList.get(i) == false);
                iModifyRow.setNeedSync(autoGenerateData.get(i) == false);
                iModifyRow.buildRow();
            }
            return iModifyTable.commitChange();
        }
        catch (Exception e) {
            log.error("\u5c01\u9762\u4ee3\u7801\u6279\u91cf\u65b0\u589e\u7ec4\u7ec7\u673a\u6784\u65f6\u51fa\u9519\uff1a{}", (Object)e.getMessage(), (Object)e);
            throw new FMDMDataException(e.getMessage(), e);
        }
    }

    @Override
    public EntityUpdateResult batchUpdate(QueryParamDTO queryParamDTO, List<FMDMModifyDTO> updateList) {
        String entityId = queryParamDTO.getEntityId();
        EntityViewDefine entityViewDefine = this.entityViewRunTimeController.buildEntityView(entityId);
        IEntityModify iEntityModify = this.entityDataService.newEntityUpdate();
        String dimensionName = this.entityMetaService.getDimensionName(entityId);
        IEntityModel entityModel = this.entityMetaService.getEntityModel(entityId);
        DimensionValueSet queryDimension = queryParamDTO.getDimensionCombination().toDimensionValueSet();
        if (queryDimension == null) {
            queryDimension = this.getDataTimeDimension(updateList.get(0));
        }
        iEntityModify.setMasterKeys(queryDimension);
        iEntityModify.setEntityView(entityViewDefine);
        iEntityModify.setAuthorityOperations(queryParamDTO.getAuthorityType());
        iEntityModify.ignoreCheckErrorData(queryParamDTO.isIgnoreErrorData());
        ArrayList<Map<String, Object>> filterList = new ArrayList<Map<String, Object>>(updateList.size());
        String orgCodeField = entityModel.getCodeField().getCode();
        String codeField = entityModel.getBizKeyField().getCode();
        for (FMDMModifyDTO fmdmDataDO : updateList) {
            Map<String, Object> modifyValueMap = fmdmDataDO.getEntityModify();
            DimensionValueSet dimensionValueSet = fmdmDataDO.getDimensionCombination().toDimensionValueSet();
            modifyValueMap.put(codeField, dimensionValueSet.getValue(dimensionName));
            Object orgCode = modifyValueMap.get(orgCodeField);
            if (orgCode != null) {
                modifyValueMap.put(orgCodeField, orgCode.toString().toUpperCase());
            }
            filterList.add(modifyValueMap);
        }
        String periodView = this.getPeriodView(queryParamDTO.getFormSchemeKey());
        List<Boolean> checkList = this.checkFilter.check(filterList, entityId, this.getTime(queryDimension, periodView));
        try {
            IModifyTable iModifyTable = iEntityModify.executeUpdate(this.getContext(queryParamDTO, periodView));
            for (int i = 0; i < updateList.size(); ++i) {
                DimensionValueSet dimensionValueSet = updateList.get(i).getDimensionCombination().toDimensionValueSet();
                IModifyRow iModifyRow = iModifyTable.appendModifyRow(dimensionValueSet);
                Map<String, Object> valueMap = updateList.get(i).getEntityModify();
                valueMap.forEach((key, value) -> {
                    IEntityAttribute attribute = entityModel.getAttribute(key);
                    if (attribute != null) {
                        iModifyRow.setValue(key, value);
                    }
                });
                iModifyRow.setIgnoreCodeCheck(checkList.get(i) == false);
                iModifyRow.buildRow();
            }
            return iModifyTable.commitChange();
        }
        catch (Exception e) {
            log.error("\u5c01\u9762\u4ee3\u7801\u6279\u91cf\u4fee\u6539\u7ec4\u7ec7\u673a\u6784\u65f6\u51fa\u9519\uff1a{}", (Object)e.getMessage(), (Object)e);
            throw new FMDMDataException(e.getMessage(), e);
        }
    }

    private DimensionValueSet getDataTimeDimension(FMDMModifyDTO fmdmData) {
        DimensionValueSet queryDimension = new DimensionValueSet();
        DimensionValueSet dimensionValueSet = fmdmData.getDimensionCombination().toDimensionValueSet();
        if (dimensionValueSet == null) {
            return queryDimension;
        }
        Object periodDimension = dimensionValueSet.getValue("DATATIME");
        if (periodDimension != null) {
            queryDimension.setValue("DATATIME", periodDimension);
        }
        return queryDimension;
    }

    private Date getTime(DimensionValueSet dimensionValueSet, String periodView) {
        Object value = dimensionValueSet.getValue("DATATIME");
        if (value != null) {
            try {
                return this.periodEngineService.getPeriodAdapter().getPeriodProvider(periodView).getPeriodDateRegion(value.toString())[1];
            }
            catch (ParseException e) {
                return new Date();
            }
        }
        return new Date();
    }

    private String getPeriodView(String formSchemeKey) {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        return formScheme.getDateTime();
    }

    private void setCode(Map<String, Object> valueMap, String codeField, String orgCodeValue) {
        if (this.orgCodeEditOption.isAllow()) {
            return;
        }
        Object codeValue = valueMap.get(codeField);
        if (Utils.emptyObject(codeValue)) {
            valueMap.put(codeField, orgCodeValue);
        }
    }
}

