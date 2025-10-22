/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.batch.summary.service.ext.entityframe.EntityFrameExtendHelper
 *  com.jiuqi.nr.batch.summary.storage.entity.CustomCalibreRow
 *  com.jiuqi.nr.batch.summary.storage.entity.SingleDim
 *  com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.engine.exception.EntityUpdateException
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityItem
 *  com.jiuqi.nr.entity.engine.intf.IEntityModify
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.intf.IModifyRow
 *  com.jiuqi.nr.entity.engine.intf.IModifyTable
 *  com.jiuqi.nr.entity.engine.result.EntityUpdateResult
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.util.StringUtils
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.batch.gather.gzw.service.executor;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.batch.gather.gzw.service.dao.IGatherEntityCodeMappingDao;
import com.jiuqi.nr.batch.gather.gzw.service.entity.GatherEntityCodeMapping;
import com.jiuqi.nr.batch.summary.service.ext.entityframe.EntityFrameExtendHelper;
import com.jiuqi.nr.batch.summary.storage.entity.CustomCalibreRow;
import com.jiuqi.nr.batch.summary.storage.entity.SingleDim;
import com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.engine.exception.EntityUpdateException;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityItem;
import com.jiuqi.nr.entity.engine.intf.IEntityModify;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.intf.IModifyRow;
import com.jiuqi.nr.entity.engine.intf.IModifyTable;
import com.jiuqi.nr.entity.engine.result.EntityUpdateResult;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class EntityExecutor {
    public static final String CUSTOMIZED_CONDITION_ENTITY_BBLX = "H";
    public static final String SHORTNAME_FIELD_KEY = "SHORTNAME";
    @Resource
    private IRunTimeViewController iRunTimeViewController;
    @Resource
    private IRuntimeDataSchemeService iRuntimeDataSchemeService;
    @Resource
    private IEntityDataService entityDataService;
    @Resource
    private IEntityViewRunTimeController runTimeController;
    @Resource
    private IDataDefinitionRuntimeController definitionRuntimeController;
    @Resource
    private IEntityMetaService iEntityMetaService;
    @Resource
    private IGatherEntityCodeMappingDao gatherEntityCodeMappingDao;
    @Resource
    private EntityFrameExtendHelper entityFrameExtendHelper;

    public void updateCustomizedConditionToEntity(SummaryScheme summaryScheme, String taskId, List<String> periods) {
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(taskId);
        String contextEntityId = DsContextHolder.getDsContext().getContextEntityId();
        if (StringUtils.isEmpty((String)contextEntityId)) {
            contextEntityId = taskDefine.getDw();
        }
        IEntityModel entityModel = this.iEntityMetaService.getEntityModel(contextEntityId);
        String dwDimName = this.iEntityMetaService.getDimensionName(contextEntityId);
        for (String period : periods) {
            DimensionValueSet dimensionValueSet = new DimensionValueSet();
            dimensionValueSet.setValue("DATATIME", (Object)period);
            Set entityCodeOfCustomCondition = this.getEntityData(contextEntityId, dimensionValueSet, taskDefine.getDateTime()).stream().filter(row -> {
                String bblx = row.getValue(entityModel.getBblxField().getCode()).getAsString();
                if (bblx == null || bblx.isEmpty()) {
                    return false;
                }
                return bblx.equals(CUSTOMIZED_CONDITION_ENTITY_BBLX);
            }).map(IEntityItem::getEntityKeyData).collect(Collectors.toSet());
            ArrayList<GatherEntityCodeMapping> newMappings = new ArrayList<GatherEntityCodeMapping>();
            for (CustomCalibreRow row2 : summaryScheme.getTargetDim().getCustomCalibreRows()) {
                String entityCode = EntityExecutor.getGatherEntityCode(summaryScheme, row2.getCode());
                GatherEntityCodeMapping newMapping = new GatherEntityCodeMapping();
                newMapping.setEntityCode(entityCode);
                newMapping.setTask(taskId);
                newMapping.setPeriod(period);
                newMapping.setGatherSchemeKey(summaryScheme.getKey());
                newMapping.setCustomizedConditionCode(row2.getCode());
                newMapping.setEntityId(DsContextHolder.getDsContext().getContextEntityId());
                newMappings.add(newMapping);
                HashMap<String, Object> rowData = new HashMap<String, Object>();
                rowData.put(entityModel.getNameField().getCode(), row2.getTitle());
                rowData.put(entityModel.getParentField().getCode(), "-");
                if (!StringUtils.isEmpty((String)row2.getParentCode())) {
                    rowData.put(entityModel.getParentField().getCode(), EntityExecutor.getGatherEntityCode(summaryScheme, row2.getParentCode()));
                }
                List singleDimensions = this.entityFrameExtendHelper.getSingleDimensions(taskDefine);
                for (DataDimension singleDimension : singleDimensions) {
                    List singleDims = summaryScheme.getSingleDims();
                    Optional<SingleDim> first = singleDims.stream().filter(singleDim -> singleDim.getEntityId().equals(singleDimension.getDimKey())).findFirst();
                    if (first.isPresent()) {
                        rowData.put(singleDimension.getDimAttribute(), first.get().getValue());
                        continue;
                    }
                    throw new IllegalArgumentException("\u6c47\u603b\u65b9\u6848" + summaryScheme.getKey() + "\u4e2d\u4e0d\u5b58\u5728\u7ef4\u5ea6" + singleDimension.getDimKey() + "\u7684\u9ed8\u8ba4\u503c");
                }
                Optional<DataDimension> reportCurrency = this.iRuntimeDataSchemeService.getDataSchemeDimension(taskDefine.getDataScheme(), DimensionType.DIMENSION).stream().filter(dim -> this.isReportCurrency(taskDefine, (DataDimension)dim)).findFirst();
                if (reportCurrency.isPresent()) {
                    String reportCurrencyParam = this.getEntityData(reportCurrency.get().getDimKey(), dimensionValueSet, taskDefine.getDateTime()).stream().map(IEntityItem::getEntityKeyData).collect(Collectors.joining(";"));
                    rowData.put(reportCurrency.get().getDimAttribute(), reportCurrencyParam);
                }
                if (entityCodeOfCustomCondition.contains(entityCode)) {
                    dimensionValueSet.setValue(dwDimName, (Object)entityCode);
                    rowData.put(entityModel.getNameField().getCode(), row2.getTitle());
                    rowData.put(SHORTNAME_FIELD_KEY, row2.getTitle());
                    this.updateEntityData(contextEntityId, rowData, dimensionValueSet, taskDefine.getDateTime());
                    continue;
                }
                rowData.put(entityModel.getBizKeyField().getCode(), entityCode);
                rowData.put(entityModel.getCodeField().getCode(), entityCode);
                rowData.put(entityModel.getBblxField().getCode(), CUSTOMIZED_CONDITION_ENTITY_BBLX);
                this.addEntityData(contextEntityId, rowData, dimensionValueSet, taskDefine.getDateTime());
            }
            this.gatherEntityCodeMappingDao.deleteCodeMappings(summaryScheme.getKey(), period);
            this.gatherEntityCodeMappingDao.insertCodeMappings(newMappings);
        }
    }

    public List<IEntityRow> getEntityData(String entityId, DimensionValueSet dimensionValueSet, String periodView) {
        EntityViewDefine entityViewDefine = this.runTimeController.buildEntityView(entityId);
        IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
        entityQuery.setEntityView(entityViewDefine);
        entityQuery.setMasterKeys(dimensionValueSet);
        entityQuery.sorted(true);
        ExecutorContext executorContext = new ExecutorContext(this.definitionRuntimeController);
        executorContext.setPeriodView(periodView);
        IEntityTable iEntityTable = null;
        try {
            iEntityTable = entityQuery.executeReader((IContext)executorContext);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        return iEntityTable.getAllRows();
    }

    public static String getGatherEntityCode(SummaryScheme summaryScheme, String conditionCode) {
        int lengthOfConditionCode;
        StringBuilder entityCode = new StringBuilder(summaryScheme.getCode());
        int lengthOfSchemeCode = summaryScheme.getCode().length();
        int quantityOfZero = 18 - lengthOfSchemeCode - (lengthOfConditionCode = conditionCode.length());
        if (quantityOfZero > 0) {
            entityCode.append(StringUtils.repeat((String)"0", (int)quantityOfZero)).append(conditionCode);
        } else {
            entityCode.append(conditionCode);
        }
        return entityCode.toString();
    }

    public List<IEntityRow> getEntityDataByMasterKeys(String entityId, String periodView, DimensionValueSet dimensionValueSet) {
        EntityViewDefine entityViewDefine = this.runTimeController.buildEntityView(entityId);
        IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
        entityQuery.setEntityView(entityViewDefine);
        entityQuery.setMasterKeys(dimensionValueSet);
        ExecutorContext executorContext = new ExecutorContext(this.definitionRuntimeController);
        executorContext.setPeriodView(periodView);
        IEntityTable iEntityTable = null;
        try {
            iEntityTable = entityQuery.executeReader((IContext)executorContext);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        return iEntityTable.getAllRows();
    }

    private void addEntityData(String entityId, Map<String, Object> insertValue, DimensionValueSet dimensionValueSet, String periodView) {
        EntityViewDefine entityViewDefine = this.runTimeController.buildEntityView(entityId);
        IEntityModify iEntityModify = this.entityDataService.newEntityUpdate();
        iEntityModify.setEntityView(entityViewDefine);
        iEntityModify.setMasterKeys(dimensionValueSet);
        ExecutorContext executorContext = new ExecutorContext(this.definitionRuntimeController);
        executorContext.setPeriodView(periodView);
        executorContext.setVarDimensionValueSet(dimensionValueSet);
        try {
            IModifyTable iModifyTable = iEntityModify.executeUpdate((IContext)executorContext);
            IModifyRow iModifyRow = iModifyTable.appendNewRow();
            Set<String> keySet = insertValue.keySet();
            for (String key : keySet) {
                iModifyRow.setValue(key, insertValue.get(key));
            }
            iModifyRow.buildRow();
            EntityUpdateResult entityUpdateResult = iModifyTable.commitChange();
        }
        catch (JQException | EntityUpdateException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateEntityData(String entityId, Map<String, Object> insertValue, DimensionValueSet dimensionValueSet, String periodView) {
        EntityViewDefine entityViewDefine = this.runTimeController.buildEntityView(entityId);
        IEntityModify iEntityModify = this.entityDataService.newEntityUpdate();
        iEntityModify.setEntityView(entityViewDefine);
        iEntityModify.setMasterKeys(dimensionValueSet);
        ExecutorContext executorContext = new ExecutorContext(this.definitionRuntimeController);
        executorContext.setPeriodView(periodView);
        executorContext.setVarDimensionValueSet(dimensionValueSet);
        try {
            IModifyTable iModifyTable = iEntityModify.executeUpdate((IContext)executorContext);
            IModifyRow iModifyRow = iModifyTable.appendModifyRow(dimensionValueSet);
            Set<String> keySet = insertValue.keySet();
            for (String key : keySet) {
                iModifyRow.setValue(key, insertValue.get(key));
            }
            iModifyRow.buildRow();
            EntityUpdateResult entityUpdateResult = iModifyTable.commitChange();
        }
        catch (JQException | EntityUpdateException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isReportCurrency(TaskDefine taskDefine, DataDimension dimension) {
        String dimAttribute = dimension.getDimAttribute();
        IEntityModel dwEntityModel = this.iEntityMetaService.getEntityModel(taskDefine.getDw());
        IEntityAttribute attribute = dwEntityModel.getAttribute(dimAttribute);
        String dimReferAttr = this.getDimAttributeByReportDim(taskDefine, dimension.getDimKey());
        return dimension.getDimensionType() == DimensionType.DIMENSION && attribute != null && attribute.isMultival() && StringUtils.isNotEmpty((String)dimReferAttr);
    }

    private String getDimAttributeByReportDim(TaskDefine taskDefine, String dimKey) {
        String dataSchemeKey = taskDefine.getDataScheme();
        if (dataSchemeKey == null) {
            return null;
        }
        List dimensions = this.iRuntimeDataSchemeService.getDataSchemeDimension(dataSchemeKey, DimensionType.DIMENSION);
        DataDimension report = dimensions.stream().filter(dataDimension -> dimKey.equals(dataDimension.getDimKey())).findFirst().orElse(null);
        return report == null ? null : report.getDimAttribute();
    }
}

