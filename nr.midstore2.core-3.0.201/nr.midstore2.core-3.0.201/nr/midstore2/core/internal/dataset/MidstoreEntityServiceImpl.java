/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.definition.common.TableKind
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.io.params.base.TableContext
 *  com.jiuqi.nr.io.params.output.ExportEntity
 *  com.jiuqi.nr.io.service.IOEntityIsolateCondition
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 */
package nr.midstore2.core.internal.dataset;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.definition.common.TableKind;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.io.params.base.TableContext;
import com.jiuqi.nr.io.params.output.ExportEntity;
import com.jiuqi.nr.io.service.IOEntityIsolateCondition;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import java.util.List;
import nr.midstore2.core.dataset.IMidstoreEntityService;
import nr.midstore2.core.dataset.MidsotreTableContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MidstoreEntityServiceImpl
implements IMidstoreEntityService {
    private static final Logger log = LoggerFactory.getLogger(MidstoreEntityServiceImpl.class);
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private IPeriodEntityAdapter iPeriodEntityAdapter;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired(required=false)
    private IOEntityIsolateCondition entityIsolateCondition;

    @Override
    public IEntityTable getEntityTable(TableContext tableContext, String key, ExecutorContext executorContext) {
        IEntityTable entityTables = null;
        IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
        try {
            EntityViewDefine entityView = this.entityViewRunTimeController.buildEntityView(key);
            entityQuery.setEntityView(entityView);
            entityQuery.setAuthorityOperations(AuthorityType.None);
            if (this.entityIsolateCondition != null) {
                try {
                    String isolateCondition = this.entityIsolateCondition.queryIsoCondition(tableContext.getTaskKey(), tableContext.getDimensionSet().getValue("DATATIME").toString(), entityView.getEntityId());
                    entityQuery.setIsolateCondition(isolateCondition);
                }
                catch (Exception e) {
                    log.debug(e.getMessage());
                }
            }
            entityTables = entityQuery.executeFullBuild((IContext)executorContext);
        }
        catch (Exception e) {
            log.info("\u67e5\u8be2\u4e3b\u4f53\u5b9e\u4f53\u7ed3\u679c\u51fa\u9519{}", (Object)e.getMessage());
        }
        return entityTables;
    }

    @Override
    public void importEntitys(TableContext tableContext, ExecutorContext context, List<ExportEntity> entitys) {
    }

    @Override
    public FieldDefine getUnitField(TableDefine table, TableDefine tableDefine, List<FieldDefine> bizKeyFieldDef) throws Exception {
        for (FieldDefine def : bizKeyFieldDef) {
            TableDefine td;
            FieldDefine fieldDefine;
            DataField df = this.runtimeDataSchemeService.getDataField(def.getKey());
            if (df.getRefDataEntityKey() != null && !df.getRefDataEntityKey().equals("") && (fieldDefine = this.getReferField(def)) != null && (td = this.dataDefinitionRuntimeController.queryTableDefine(fieldDefine.getOwnerTableKey())).getKind().equals((Object)TableKind.TABLE_KIND_ENTITY) && tableDefine != null && tableDefine.getKey().equals(td.getKey())) {
                return def;
            }
            List deployInfoByDataTableKey = this.runtimeDataSchemeService.getDeployInfoByDataTableKey(def.getOwnerTableKey());
            if (deployInfoByDataTableKey == null || deployInfoByDataTableKey.isEmpty() || !def.getCode().equals(((DataFieldDeployInfo)deployInfoByDataTableKey.get(0)).getTableName()) && !def.getCode().equals(table.getCode())) continue;
            return def;
        }
        return null;
    }

    private FieldDefine getReferField(FieldDefine def) throws Exception {
        FieldDefine fieldDefine = null;
        DataField df = this.runtimeDataSchemeService.getDataField(def.getKey());
        try {
            fieldDefine = this.dataDefinitionRuntimeController.queryFieldDefine(df.getRefDataEntityKey());
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        if (fieldDefine != null && df.getRefDataEntityKey() != null) {
            fieldDefine = this.getReferField(fieldDefine);
        }
        return fieldDefine;
    }

    @Override
    public IEntityTable getIEntityTable(EntityViewDefine entityView, MidsotreTableContext tableContext, ExecutorContext context) throws Exception {
        IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
        entityQuery.setEntityView(entityView);
        entityQuery.setAuthorityOperations(AuthorityType.None);
        DimensionValueSet masterKey = new DimensionValueSet();
        masterKey.setValue("DATATIME", tableContext.getDimensionSet().getValue("DATATIME"));
        entityQuery.setMasterKeys(masterKey);
        if (this.entityIsolateCondition != null) {
            try {
                String isolateCondition = this.entityIsolateCondition.queryIsoCondition(tableContext.getTaskKey(), tableContext.getDimensionSet().getValue("DATATIME").toString(), entityView.getEntityId());
                entityQuery.setIsolateCondition(isolateCondition);
            }
            catch (Exception e) {
                log.debug(e.getMessage());
            }
        }
        IEntityTable entityTables = entityQuery.executeFullBuild((IContext)context);
        return entityTables;
    }

    @Override
    public boolean isPeriod(String periodKey) {
        return this.iPeriodEntityAdapter.isPeriodEntity(periodKey);
    }
}

