/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.np.authz2.service.EntityIdentityService
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.definitions.DefinitionsCache
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.nr.definition.common.IEntityUpgrader
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 */
package com.jiuqi.nr.reminder.untils;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.np.authz2.service.EntityIdentityService;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.definitions.DefinitionsCache;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.nr.definition.common.IEntityUpgrader;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EntityUtil
implements IEntityUpgrader {
    private static final Logger log = LoggerFactory.getLogger(EntityUtil.class);
    @Autowired
    EntityIdentityService entityIdentityService;
    @Autowired
    private IDataDefinitionRuntimeController tbRtCtl;
    @Autowired
    private IEntityViewRunTimeController viewRtCtl;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IEntityDataService iEntityDataService;

    public List<IEntityRow> getentityRow(List<String> entryKey, EntityViewDefine view, String formSchemeKey) {
        List<Object> rowData = new ArrayList<IEntityRow>();
        try {
            DimensionValueSet valueSet = new DimensionValueSet();
            String dimName = this.entityMetaService.getDimensionName(view.getEntityId());
            valueSet.setValue(dimName, entryKey);
            EntityViewDefine entityViewDefine = this.runTimeViewController.getViewByFormSchemeKey(formSchemeKey);
            IEntityQuery iEntityQuery = this.iEntityDataService.newEntityQuery();
            iEntityQuery.setEntityView(entityViewDefine);
            iEntityQuery.setMasterKeys(valueSet);
            IEntityTable iEntityTable = iEntityQuery.executeReader((IContext)new com.jiuqi.nr.entity.engine.executors.ExecutorContext(this.tbRtCtl));
            rowData = iEntityTable.getAllRows();
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return rowData;
    }

    public FieldDefine getKeyField(String tableKey) throws Exception {
        FieldDefine target = null;
        TableDefine tableDefine = this.queryTableByKey(tableKey);
        if (tableDefine == null || tableDefine.getBizKeyFieldsID().length <= 0) {
            return target;
        }
        FieldDefine keyField = this.queryFieldByKey(tableDefine.getBizKeyFieldsID()[0]);
        return keyField;
    }

    public TableDefine queryTableByKey(String tableKey) {
        try {
            return this.tbRtCtl.queryTableDefine(tableKey);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    public FieldDefine queryFieldByKey(String fieldKey) {
        try {
            if (fieldKey != null) {
                return this.tbRtCtl.queryFieldDefine(fieldKey);
            }
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public String getDimName(FieldDefine field) throws ParseException {
        String dimName = null;
        ExecutorContext context = new ExecutorContext(this.tbRtCtl);
        DefinitionsCache cache = new DefinitionsCache(context);
        dimName = cache.getDataDefinitionsCache().getDimensionName(field);
        return dimName;
    }

    public ExecutorContext buildExecutorContext() {
        ExecutorContext context = new ExecutorContext(this.tbRtCtl);
        return context;
    }
}

