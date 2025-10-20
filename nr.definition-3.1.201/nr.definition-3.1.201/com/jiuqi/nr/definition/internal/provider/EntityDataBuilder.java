/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IEntityQuery
 *  com.jiuqi.np.dataengine.intf.IEntityTable
 *  com.jiuqi.np.dataengine.setting.AuthorityType
 *  com.jiuqi.np.definition.common.EntityUtils
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.entity.common.TableException
 */
package com.jiuqi.nr.definition.internal.provider;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IEntityQuery;
import com.jiuqi.np.dataengine.intf.IEntityTable;
import com.jiuqi.np.dataengine.setting.AuthorityType;
import com.jiuqi.np.definition.common.EntityUtils;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.entity.common.TableException;
import java.util.List;

public class EntityDataBuilder {
    private IDataDefinitionRuntimeController runtimeDateCtrl;
    private IEntityViewRunTimeController runtimeViewCtrl;
    private IDataAccessProvider dataAccessProvider;
    private IEntityQuery entityQuery;

    public EntityDataBuilder(IDataDefinitionRuntimeController runtimeDateCtrl, IEntityViewRunTimeController runtimeViewCtrl, IDataAccessProvider iDataAccessProvider) {
        this.runtimeDateCtrl = runtimeDateCtrl;
        this.runtimeViewCtrl = runtimeViewCtrl;
        this.dataAccessProvider = iDataAccessProvider;
    }

    public EntityDataBuilder getEntityReader(String entityId) throws JQException {
        List allFieldsInTable;
        this.entityQuery = this.dataAccessProvider.newEntityQuery();
        this.entityQuery.setMasterKeys(new DimensionValueSet());
        this.entityQuery.setAuthorityOperations(AuthorityType.NONE);
        try {
            allFieldsInTable = this.runtimeDateCtrl.getAllFieldsInTable(EntityUtils.getId((String)entityId));
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)TableException.QUERY_FIELD_ERROR);
        }
        for (FieldDefine field : allFieldsInTable) {
            this.entityQuery.addColumn(field);
        }
        return this;
    }

    public EntityDataBuilder setMasterKeys(DimensionValueSet dimensionValueSet) {
        this.entityQuery.setMasterKeys(dimensionValueSet);
        return this;
    }

    public EntityDataBuilder setIgnoreParentView(boolean ignoreParentView) {
        this.entityQuery.setIgnoreParentView(ignoreParentView);
        return this;
    }

    public EntityDataBuilder setFilterDataByAuthority(boolean enable) {
        this.entityQuery.setFilterDataByAuthority(enable);
        return this;
    }

    public EntityDataBuilder setAuthorityOperations(AuthorityType authorityType) {
        this.entityQuery.setAuthorityOperations(authorityType);
        return this;
    }

    public EntityDataBuilder setRowFilter(String condition) {
        this.entityQuery.setRowFilter(condition);
        return this;
    }

    public IEntityTable build() throws JQException {
        IEntityTable iEntityTable;
        ExecutorContext context = new ExecutorContext(this.runtimeDateCtrl);
        try {
            iEntityTable = this.entityQuery.executeReader(context);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)TableException.QUERY_DATA_ERROR, e.getMessage());
        }
        return iEntityTable;
    }

    public IEntityTable build(ExecutorContext context) throws JQException {
        IEntityTable iEntityTable;
        try {
            iEntityTable = this.entityQuery.executeReader(context);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)TableException.QUERY_DATA_ERROR, e.getMessage());
        }
        return iEntityTable;
    }
}

