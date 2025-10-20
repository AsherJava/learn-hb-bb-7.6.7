/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IEntityQuery
 *  com.jiuqi.np.dataengine.intf.IEntityRow
 *  com.jiuqi.np.dataengine.intf.IEntityTable
 *  com.jiuqi.np.dataengine.intf.IModifierEntityTable
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.definition.internal.controller.NRDesignTimeController
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.designer.service.impl;

import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IEntityQuery;
import com.jiuqi.np.dataengine.intf.IEntityRow;
import com.jiuqi.np.dataengine.intf.IEntityTable;
import com.jiuqi.np.dataengine.intf.IModifierEntityTable;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.definition.internal.controller.NRDesignTimeController;
import com.jiuqi.nr.designer.service.IRunTimeService;
import com.jiuqi.nr.designer.web.treebean.FieldTableObj;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IRuntimeServiceImpl
implements IRunTimeService {
    @Resource
    private IDataDefinitionRuntimeController runtimeCtrl;
    @Autowired
    private NRDesignTimeController nrDesignTimeController;
    @Autowired
    private IDataAccessProvider dataAccessProvider;

    @Override
    public IEntityTable createRunTimeQueryEntity(String tableKey) throws Exception {
        IModifierEntityTable entityTable = null;
        IEntityQuery entityQuery = this.dataAccessProvider.newEntityQuery();
        entityQuery.setMasterKeys(new DimensionValueSet());
        List allFields = this.runtimeCtrl.getAllFieldsInTable(tableKey);
        for (FieldDefine field : allFields) {
            entityQuery.addColumn(field);
        }
        ExecutorContext context = new ExecutorContext(this.runtimeCtrl);
        entityTable = entityQuery.executeQuery(context);
        return entityTable;
    }

    @Override
    public List<FieldTableObj> getAllRows(IEntityTable entityTable, String ownKey) {
        ArrayList<FieldTableObj> fieldTableObjs = new ArrayList<FieldTableObj>();
        List entityRows = entityTable.getAllRows();
        FieldTableObj fieldTableObj = new FieldTableObj();
        for (IEntityRow entityRow : entityRows) {
            fieldTableObj = this.getRowData(entityRow, ownKey);
            if (fieldTableObj == null) continue;
            fieldTableObjs.add(fieldTableObj);
        }
        return fieldTableObjs;
    }

    private FieldTableObj getRowData(IEntityRow entityRow, String ownKey) {
        return null;
    }

    @Override
    public List<String> getCodes(IEntityTable entityTable) {
        ArrayList<String> codes = new ArrayList<String>();
        List entityRows = entityTable.getAllRows();
        String code = null;
        for (IEntityRow entityRow : entityRows) {
            code = entityRow.getCode();
            codes.add(code);
        }
        return codes;
    }

    @Override
    public IModifierEntityTable createHandleEntity(String tableKey, List<FieldDefine> allFields) throws Exception {
        ExecutorContext context = new ExecutorContext(this.runtimeCtrl);
        IEntityQuery entityQuery = this.buildIentityTable(allFields);
        return entityQuery.executeQuery(context);
    }

    private IEntityQuery buildIentityTable(List<FieldDefine> allFields) {
        DimensionValueSet valueSet = new DimensionValueSet();
        IEntityQuery entityQuery = this.dataAccessProvider.newEntityQuery();
        entityQuery.setMasterKeys(valueSet);
        for (FieldDefine field : allFields) {
            entityQuery.addColumn(field);
        }
        return entityQuery;
    }
}

