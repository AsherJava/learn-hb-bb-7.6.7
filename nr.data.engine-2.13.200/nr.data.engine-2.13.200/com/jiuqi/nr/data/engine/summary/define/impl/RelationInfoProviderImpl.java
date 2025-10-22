/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionSet
 *  com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache
 *  com.jiuqi.np.dataengine.definitions.TableModelRunInfo
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.model.IEntityRefer
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nvwa.definition.common.TableDictType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.nr.data.engine.summary.define.impl;

import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.data.engine.summary.define.DimRelationInfo;
import com.jiuqi.nr.data.engine.summary.define.IRelationInfoProvider;
import com.jiuqi.nr.data.engine.summary.define.RelationInfo;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.model.IEntityRefer;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nvwa.definition.common.TableDictType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RelationInfoProviderImpl
implements IRelationInfoProvider {
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IPeriodEntityAdapter periodEntityAdapter;

    @Override
    public String getType() {
        return "nr";
    }

    @Override
    public DimRelationInfo findDimRelationInfo(ExecutorContext context, String srcTableName) throws Exception {
        DataModelDefinitionsCache dataDefinitionsCache = context.getCache().getDataModelDefinitionsCache();
        TableModelRunInfo srcTableInfo = dataDefinitionsCache.getTableInfo(srcTableName);
        DimRelationInfo dimRelationInfo = null;
        for (ColumnModelDefine column : srcTableInfo.getDimFields()) {
            FieldDefine keyField = dataDefinitionsCache.getFieldDefine(column);
            String entityId = keyField.getEntityKey();
            ReportFmlExecEnvironment rEnv = (ReportFmlExecEnvironment)context.getEnv();
            FormSchemeDefine formScheme = rEnv.getFormSchemeDefine();
            String entitiesKey = formScheme.getMasterEntitiesKey();
            String viewKey = entitiesKey.split(";")[0];
            IEntityDefine masterEntityDefine = this.entityMetaService.queryEntity(viewKey);
            if (entityId == null || this.periodEntityAdapter.isPeriodEntity(entityId)) continue;
            IEntityDefine entityDefine = this.entityMetaService.queryEntity(entityId);
            if (entityDefine.getDimensionName().equals(masterEntityDefine.getDimensionName())) {
                entityDefine = masterEntityDefine;
                entityId = masterEntityDefine.getId();
            }
            if (entityDefine == null) continue;
            IEntityModel entityModel = this.entityMetaService.getEntityModel(entityId);
            TableModelDefine tableModel = this.entityMetaService.getTableModel(entityId);
            dimRelationInfo = new DimRelationInfo();
            dimRelationInfo.setRelationType(RelationInfo.RelationType.R_1V1);
            dimRelationInfo.setSrcTableName(srcTableName);
            dimRelationInfo.setDimName(entityDefine.getDimensionName());
            dimRelationInfo.setSupportZipperVersion(tableModel.getDictType() == TableDictType.ZIPPER);
            dimRelationInfo.setStartDateFieldName(entityModel.getBeginDateField().getName());
            dimRelationInfo.setEndDateFieldName(entityModel.getEndDateField().getName());
            dimRelationInfo.setDestTableName(tableModel.getCode());
            dimRelationInfo.getRelationFieldMap().put(column.getName(), entityModel.getBizKeyField().getName());
            List entityRefers = this.entityMetaService.getEntityRefer(entityId);
            if (entityRefers == null) continue;
            for (IEntityRefer entityRefer : entityRefers) {
                IEntityDefine refEntityDefine = this.entityMetaService.queryEntity(entityRefer.getReferEntityId());
                IEntityModel refEntityModel = this.entityMetaService.getEntityModel(entityRefer.getReferEntityId());
                TableModelDefine refTableModel = this.entityMetaService.getTableModel(entityRefer.getReferEntityId());
                DimRelationInfo dimRefRelationInfo = new DimRelationInfo();
                dimRefRelationInfo.setRelationType(RelationInfo.RelationType.R_1V1);
                dimRefRelationInfo.setSrcTableName(tableModel.getCode());
                dimRefRelationInfo.setDestTableName(refTableModel.getName());
                dimRefRelationInfo.setDimName(refEntityDefine.getDimensionName());
                dimRefRelationInfo.setSupportZipperVersion(refTableModel.getDictType() == TableDictType.ZIPPER);
                dimRefRelationInfo.setStartDateFieldName(refEntityModel.getBeginDateField().getName());
                dimRefRelationInfo.setEndDateFieldName(refEntityModel.getEndDateField().getName());
                dimRefRelationInfo.getRelationFieldMap().put(entityRefer.getOwnField(), entityRefer.getReferEntityField());
            }
        }
        return dimRelationInfo;
    }

    @Override
    public RelationInfo findRelationInfo(ExecutorContext context, String srcTableName, String destTableName) throws Exception {
        DataModelDefinitionsCache dataDefinitionsCache = context.getCache().getDataModelDefinitionsCache();
        TableModelRunInfo destTableInfo = dataDefinitionsCache.getTableInfo(destTableName);
        RelationInfo relationInfo = new RelationInfo();
        relationInfo.setRelationType(RelationInfo.RelationType.R_1V1);
        relationInfo.setSrcTableName(srcTableName);
        relationInfo.setDestTableName(destTableName);
        TableModelRunInfo srcTableInfo = dataDefinitionsCache.getTableInfo(srcTableName);
        DimensionSet srcDimensions = srcTableInfo.getDimensions();
        for (int i = 0; i < srcDimensions.size(); ++i) {
            String dim = srcDimensions.get(i);
            if (!destTableInfo.getDimensions().contains(dim)) continue;
            relationInfo.getRelationFieldMap().put(srcTableInfo.getDimensionField(dim).getName(), destTableInfo.getDimensionField(dim).getName());
        }
        return relationInfo;
    }

    @Override
    public String findMainTableName(ExecutorContext context, String srcTableName) throws Exception {
        return srcTableName;
    }
}

