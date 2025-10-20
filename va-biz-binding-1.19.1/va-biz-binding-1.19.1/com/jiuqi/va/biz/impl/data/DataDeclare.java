/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.impl.data;

import com.jiuqi.va.biz.impl.data.DataDefineImpl;
import com.jiuqi.va.biz.impl.data.DataTableDeclare;
import com.jiuqi.va.biz.impl.data.DataTableDefineImpl;
import com.jiuqi.va.biz.impl.data.DataTableDefineMerge;
import com.jiuqi.va.biz.impl.data.DataTableNodeContainerImpl;
import com.jiuqi.va.biz.intf.data.DataDefine;
import com.jiuqi.va.biz.intf.data.DataFieldType;
import com.jiuqi.va.biz.intf.model.Declare;
import com.jiuqi.va.biz.intf.model.DeclareHost;
import com.jiuqi.va.biz.intf.model.PluginDefine;
import com.jiuqi.va.biz.intf.value.ValueType;

public class DataDeclare<T extends DeclareHost<? super DataDefineImpl>>
implements DataDefine,
PluginDefine,
Declare<DataDefineImpl, T>,
DeclareHost<DataTableDefineImpl> {
    private T host;
    private DataDefineImpl impl;

    public DataDeclare(T host) {
        this.host = host;
        this.impl = new DataDefineImpl();
        this.impl.setType("data");
    }

    public DataTableDeclare<DataDeclare<T>> declareMasterTable() {
        DataTableDeclare<DataDeclare<T>> declareTable = new DataTableDeclare<DataDeclare<T>>(this);
        declareTable.declareField().setName("ID").setTitle("\u6807\u8bc6").setFieldType(DataFieldType.DATA).setFieldName("ID").setValueType(ValueType.IDENTIFY).setReadonly(true).setRequired(true).setSolidified(true).endDeclare();
        declareTable.declareField().setName("VER").setTitle("\u7248\u672c").setFieldType(DataFieldType.DATA).setFieldName("VER").setValueType(ValueType.LONG).setReadonly(true).setRequired(true).setSolidified(true).endDeclare();
        return declareTable;
    }

    public DataTableDeclare<DataDeclare<T>> declareDetailTable() {
        DataTableDeclare<DataDeclare<T>> declareTable = new DataTableDeclare<DataDeclare<T>>(this);
        declareTable.declareField().setName("ID").setTitle("\u6807\u8bc6").setFieldType(DataFieldType.DATA).setFieldName("ID").setValueType(ValueType.IDENTIFY).setReadonly(true).setRequired(true).setSolidified(true).endDeclare();
        declareTable.declareField().setName("MASTERID").setTitle("\u4e3b\u8868\u6807\u8bc6").setFieldType(DataFieldType.DATA).setFieldName("MASTERID").setValueType(ValueType.IDENTIFY).setReadonly(true).setRequired(true).setSolidified(true).endDeclare();
        declareTable.declareField().setName("ORDINAL").setTitle("\u6392\u5e8f\u503c").setFieldType(DataFieldType.DATA).setFieldName("ORDINAL").setValueType(ValueType.DOUBLE).setSolidified(true).endDeclare();
        declareTable.declareField().setName("VER").setTitle("\u7248\u672c").setFieldType(DataFieldType.DATA).setFieldName("VER").setValueType(ValueType.LONG).setReadonly(true).setRequired(false).setSolidified(true).endDeclare();
        return declareTable;
    }

    public DataTableDeclare<DataDeclare<T>> declareAssistTable() {
        DataTableDeclare<DataDeclare<T>> declareTable = new DataTableDeclare<DataDeclare<T>>(this);
        declareTable.declareField().setName("ID").setTitle("\u6807\u8bc6").setFieldType(DataFieldType.DATA).setFieldName("ID").setValueType(ValueType.IDENTIFY).setReadonly(true).setRequired(true).setSolidified(true).endDeclare();
        declareTable.declareField().setName("MASTERID").setTitle("\u4e3b\u8868\u6807\u8bc6").setFieldType(DataFieldType.DATA).setFieldName("MASTERID").setValueType(ValueType.IDENTIFY).setReadonly(true).setRequired(true).setSolidified(true).endDeclare();
        declareTable.declareField().setName("GROUPID").setTitle("\u5206\u7ec4\u6807\u8bc6").setFieldType(DataFieldType.DATA).setFieldName("GROUPID").setValueType(ValueType.IDENTIFY).setRequired(true).setSolidified(true).endDeclare();
        declareTable.declareField().setName("ORDINAL").setTitle("\u6392\u5e8f\u503c").setFieldType(DataFieldType.DATA).setFieldName("ORDINAL").setValueType(ValueType.DOUBLE).setSolidified(true).endDeclare();
        declareTable.declareField().setName("VER").setTitle("\u7248\u672c").setFieldType(DataFieldType.DATA).setFieldName("VER").setValueType(ValueType.LONG).setReadonly(true).setRequired(false).setSolidified(true).endDeclare();
        return declareTable;
    }

    @Override
    public T endDeclare() {
        if (this.host != null && this.impl != null) {
            this.host.accept((DataDefineImpl)this.impl);
            this.host = null;
            this.impl = null;
        }
        return this.host;
    }

    @Override
    public void accept(DataTableDefineImpl declareImpl) {
        DataTableDefineImpl target = (DataTableDefineImpl)((DataTableNodeContainerImpl)this.impl.getTables()).find(declareImpl.getName());
        if (target == null) {
            this.impl.addTable(declareImpl);
        } else {
            DataTableDefineMerge.merge(target, declareImpl);
        }
    }

    @Override
    public String getType() {
        return this.impl.getType();
    }

    public DataTableNodeContainerImpl<? extends DataTableDefineImpl> getTables() {
        return this.impl.getTables();
    }
}

