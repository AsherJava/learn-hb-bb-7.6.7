/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationImpl
 *  com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IFormObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.FormObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.IDimensionObjectMapping
 *  com.jiuqi.nr.workflow2.engine.dflt.process.io.dimension.ProcessDimensionCollection
 *  com.jiuqi.nr.workflow2.service.helper.IProcessDimensionsBuilder
 *  com.jiuqi.nr.workflow2.service.helper.IProcessRuntimeParamHelper
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.nr.workflow2.form.reject.service;

import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationImpl;
import com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IFormObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.FormObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.IDimensionObjectMapping;
import com.jiuqi.nr.workflow2.engine.dflt.process.io.dimension.ProcessDimensionCollection;
import com.jiuqi.nr.workflow2.form.reject.entity.IRejectFormRecordEntity;
import com.jiuqi.nr.workflow2.form.reject.entity.IRejectOperateFormResultSet;
import com.jiuqi.nr.workflow2.form.reject.entity.IRejectOperateRecordEntity;
import com.jiuqi.nr.workflow2.form.reject.entity.RejectFormRecordEntity;
import com.jiuqi.nr.workflow2.form.reject.entity.RejectOperateRecordEntity;
import com.jiuqi.nr.workflow2.form.reject.entity.dao.IRejectFormRecordDao;
import com.jiuqi.nr.workflow2.form.reject.entity.dao.IRejectOperateRecordDao;
import com.jiuqi.nr.workflow2.form.reject.enumeration.FormRejectStatus;
import com.jiuqi.nr.workflow2.form.reject.model.FROperateTable;
import com.jiuqi.nr.workflow2.form.reject.model.FROperateTableModelDefine;
import com.jiuqi.nr.workflow2.form.reject.model.FRStatusTable;
import com.jiuqi.nr.workflow2.form.reject.model.FRStatusTableModelDefine;
import com.jiuqi.nr.workflow2.form.reject.service.IFormRejectQueryService;
import com.jiuqi.nr.workflow2.service.helper.IProcessDimensionsBuilder;
import com.jiuqi.nr.workflow2.service.helper.IProcessRuntimeParamHelper;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;

public class FormRejectQueryService
implements IFormRejectQueryService {
    @Autowired
    protected DataModelService dataModelService;
    @Autowired
    protected IRejectFormRecordDao rejectFormRecordDao;
    @Autowired
    protected IRejectOperateRecordDao operateRecordDao;
    @Autowired
    protected IProcessRuntimeParamHelper processRuntimeParamHelper;
    @Autowired
    private IProcessDimensionsBuilder processDimensionsBuilder;
    @Autowired
    private IRunTimeViewController runTimeViewController;

    @Override
    public FRStatusTableModelDefine getFRStatusTableModelDefine(String taskKey, String period) {
        FormSchemeDefine formScheme = this.processRuntimeParamHelper.getFormScheme(taskKey, period);
        FRStatusTable statusTable = new FRStatusTable(formScheme);
        return new FRStatusTableModelDefine(statusTable, this.dataModelService);
    }

    @Override
    public FROperateTableModelDefine getFROperateTableModelDefine(String taskKey, String period) {
        FormSchemeDefine formScheme = this.processRuntimeParamHelper.getFormScheme(taskKey, period);
        FROperateTable operateTable = new FROperateTable(formScheme);
        return new FROperateTableModelDefine(operateTable, this.dataModelService);
    }

    @Override
    public List<IRejectFormRecordEntity> queryRejectFormRecordsInUnit(String taskKey, String period, DimensionCombination combination) {
        FRStatusTableModelDefine statusTableModelDefine = this.getFRStatusTableModelDefine(taskKey, period);
        ArrayList<IRejectFormRecordEntity> formRecordEntities = new ArrayList<IRejectFormRecordEntity>();
        List<RejectFormRecordEntity> list = this.rejectFormRecordDao.queryRows(statusTableModelDefine, combination);
        for (RejectFormRecordEntity entity : list) {
            entity.setFormObject((IFormObject)new FormObject(combination, entity.getFormObject().getFormKey()));
            formRecordEntities.add(entity);
        }
        return formRecordEntities;
    }

    @Override
    public boolean isRejectAllFormsInUnit(String taskKey, String period, DimensionCombination combination) {
        List<IRejectFormRecordEntity> allFormRecordEntities = this.queryAllFormRecordsInUnit(taskKey, period, combination);
        return allFormRecordEntities.stream().allMatch(e -> e.getStatus() == FormRejectStatus.rejected);
    }

    @Override
    public List<IRejectFormRecordEntity> queryAllFormRecordsInUnit(String taskKey, String period, DimensionCombination combination) {
        Map<IFormObject, IRejectFormRecordEntity> statusMap = this.queryRejectFormRecordsMap(taskKey, period, combination);
        ArrayList<IRejectFormRecordEntity> allEntitiesInUnit = new ArrayList<IRejectFormRecordEntity>();
        List<IFormObject> formObjectsInUnit = this.getFormObjectsInUnit(taskKey, period, combination);
        for (IFormObject formObject : formObjectsInUnit) {
            if (statusMap.containsKey(formObject)) {
                allEntitiesInUnit.add(statusMap.get(formObject));
                continue;
            }
            RejectFormRecordEntity unRecordEntity = new RejectFormRecordEntity();
            unRecordEntity.setFormObject(formObject);
            unRecordEntity.setStatus(FormRejectStatus.locked);
            allEntitiesInUnit.add(unRecordEntity);
        }
        return allEntitiesInUnit;
    }

    @Override
    public List<IRejectOperateFormResultSet> queryUnitOperateForms(String taskKey, String period, DimensionCombination combination) {
        ArrayList<IRejectOperateFormResultSet> resultSets = new ArrayList<IRejectOperateFormResultSet>();
        FROperateTableModelDefine frOperateTableModelDefine = this.getFROperateTableModelDefine(taskKey, period);
        List<IRejectOperateRecordEntity> recordEntities = this.operateRecordDao.queryRows(frOperateTableModelDefine, combination);
        Map<String, List<IRejectOperateRecordEntity>> groupByOptId = recordEntities.stream().collect(Collectors.groupingBy(IRejectOperateRecordEntity::getOptId));
        for (Map.Entry<String, List<IRejectOperateRecordEntity>> entry : groupByOptId.entrySet()) {
            List<IRejectOperateRecordEntity> value = entry.getValue();
            if (value.isEmpty()) continue;
            IRejectOperateRecordEntity recordEntity = value.get(0);
            List<String> optFormIds = entry.getValue().stream().map(e -> e.getFormObject().getFormKey()).collect(Collectors.toList());
            RejectOperateRecordEntity operateRecordEntity = new RejectOperateRecordEntity(combination, optFormIds);
            operateRecordEntity.setOptId(recordEntity.getOptId());
            operateRecordEntity.setOptUser(recordEntity.getOptUser());
            operateRecordEntity.setOptTime(recordEntity.getOptTime());
            operateRecordEntity.setFormObject(recordEntity.getFormObject());
            operateRecordEntity.setOptComment(recordEntity.getOptComment());
            resultSets.add(operateRecordEntity);
        }
        return resultSets;
    }

    @Override
    public Map<IFormObject, IRejectFormRecordEntity> queryRejectFormRecordsMap(String taskKey, String period, DimensionCombination combination) {
        List<IRejectFormRecordEntity> recordEntities = this.queryRejectFormRecordsInUnit(taskKey, period, combination);
        HashMap<IFormObject, IRejectFormRecordEntity> statusMap = new HashMap<IFormObject, IRejectFormRecordEntity>();
        for (IRejectFormRecordEntity recordEntity : recordEntities) {
            statusMap.put(recordEntity.getFormObject(), recordEntity);
        }
        return statusMap;
    }

    @Override
    public Map<IFormObject, IRejectFormRecordEntity> queryRejectFormRecordsMap(String taskKey, String period, Collection<DimensionCombination> combinations) {
        HashMap<IFormObject, IRejectFormRecordEntity> statusMap = new HashMap<IFormObject, IRejectFormRecordEntity>();
        for (DimensionCombination combination : combinations) {
            statusMap.putAll(this.queryRejectFormRecordsMap(taskKey, period, combination));
        }
        return statusMap;
    }

    public IFormObject transferFormObject(FRStatusTableModelDefine tableModelDefine, DimensionCombination targetCombination, IFormObject targetFormObject) {
        DimensionCombinationImpl combination = new DimensionCombinationImpl();
        List<ColumnModelDefine> bizKeyColumnDefinesExceptForm = tableModelDefine.getCombinationColumnDefines();
        for (ColumnModelDefine columnModelDefine : bizKeyColumnDefinesExceptForm) {
            String columnName = columnModelDefine.getName();
            if (tableModelDefine.getStatusTable().getUnitColumnCode().equals(columnModelDefine.getCode())) {
                FixedDimensionValue dwDimensionValue = targetCombination.getDWDimensionValue();
                combination.setDWValue(dwDimensionValue.getName(), dwDimensionValue.getEntityID(), dwDimensionValue.getValue());
                continue;
            }
            if (tableModelDefine.getStatusTable().getPeriodColumnCode().equals(columnModelDefine.getCode())) {
                FixedDimensionValue periodDimensionValue = targetCombination.getPeriodDimensionValue();
                combination.setValue(periodDimensionValue.getName(), periodDimensionValue.getEntityID(), periodDimensionValue.getValue());
                continue;
            }
            FixedDimensionValue fixedDimensionValue = targetCombination.getFixedDimensionValue(columnName);
            combination.setValue(columnName, columnModelDefine.getReferTableID(), fixedDimensionValue.getValue());
        }
        return new FormObject((DimensionCombination)combination, targetFormObject.getFormKey());
    }

    public List<IFormObject> getFormObjectsInUnit(String taskKey, String period, DimensionCombination combination) {
        FormSchemeDefine formScheme = this.processRuntimeParamHelper.getFormScheme(taskKey, period);
        ProcessDimensionCollection dimensionCollection = new ProcessDimensionCollection(combination);
        List formDefines = this.runTimeViewController.listFormByFormScheme(formScheme.getKey());
        IDimensionObjectMapping dimensionObjectMapping = this.processDimensionsBuilder.processDimToFormDefinesMap(formScheme, (DimensionCollection)dimensionCollection, (Collection)formDefines.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList()));
        Collection formIds = dimensionObjectMapping.getObject(combination);
        ArrayList<IFormObject> formObjects = new ArrayList<IFormObject>();
        for (String formId : formIds) {
            formObjects.add((IFormObject)new FormObject(combination, formId));
        }
        return formObjects;
    }
}

