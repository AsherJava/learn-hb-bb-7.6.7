/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.impl.data;

import com.jiuqi.va.biz.impl.data.DataFieldDeclare;
import com.jiuqi.va.biz.impl.data.DataFieldDefineImpl;
import com.jiuqi.va.biz.impl.data.DataFieldDefineMerge;
import com.jiuqi.va.biz.impl.data.DataTableDefineImpl;
import com.jiuqi.va.biz.impl.value.NamedContainerImpl;
import com.jiuqi.va.biz.intf.data.DataTableDefine;
import com.jiuqi.va.biz.intf.data.DataTableType;
import com.jiuqi.va.biz.intf.model.Declare;
import com.jiuqi.va.biz.intf.model.DeclareHost;
import java.util.UUID;

public class DataTableDeclare<T extends DeclareHost<? super DataTableDefineImpl>>
implements DataTableDefine,
Declare<DataTableDefineImpl, T>,
DeclareHost<DataFieldDefineImpl> {
    private T host;
    private DataTableDefineImpl impl;

    public DataTableDeclare(T host) {
        this.host = host;
        this.impl = new DataTableDefineImpl();
    }

    public DataFieldDeclare<DataTableDeclare<T>> declareField() {
        return new DataFieldDeclare<DataTableDeclare<T>>(this);
    }

    @Override
    public T endDeclare() {
        if (this.host != null && this.impl != null) {
            this.host.accept((DataTableDefineImpl)this.impl);
            this.host = null;
            this.impl = null;
        }
        return this.host;
    }

    @Override
    public void accept(DataFieldDefineImpl declareImpl) {
        declareImpl.setTable(this.impl);
        DataFieldDefineImpl target = (DataFieldDefineImpl)((NamedContainerImpl)this.impl.getFields()).find(declareImpl.getName());
        if (target == null) {
            this.impl.addField(declareImpl);
        } else {
            DataFieldDefineMerge.merge(target, declareImpl);
        }
    }

    @Override
    public UUID getId() {
        return this.impl.getId();
    }

    public DataTableDeclare<T> setId(UUID id) {
        this.impl.setId(id);
        return this;
    }

    @Override
    public String getName() {
        return this.impl.getName();
    }

    public DataTableDeclare<T> setName(String name) {
        this.impl.setName(name);
        return this;
    }

    @Override
    public String getTitle() {
        return this.impl.getTitle();
    }

    public DataTableDeclare<T> setTitle(String title) {
        this.impl.setTitle(title);
        return this;
    }

    @Override
    public DataTableType getTableType() {
        return this.impl.getTableType();
    }

    public DataTableDeclare<T> setTableType(DataTableType tableType) {
        this.impl.setTableType(tableType);
        return this;
    }

    @Override
    public String getTableName() {
        return this.impl.getTableName();
    }

    public DataTableDeclare<T> setTableName(String tableName) {
        this.impl.setTableName(tableName);
        return this;
    }

    @Override
    public UUID getParentId() {
        return this.impl.getParentId();
    }

    public DataTableDeclare<T> setParentId(UUID parentId) {
        this.impl.setParentId(parentId);
        return this;
    }

    @Override
    public boolean isRequired() {
        return this.impl.isRequired();
    }

    public DataTableDeclare<T> setRequired(boolean required) {
        this.impl.setRequired(required);
        return this;
    }

    @Override
    public boolean isReadonly() {
        return this.impl.isReadonly();
    }

    public DataTableDeclare<T> setReadonly(boolean readonly) {
        this.impl.setReadonly(readonly);
        return this;
    }

    @Override
    public boolean isAddRow() {
        return this.impl.isAddRow();
    }

    public DataTableDeclare<T> setAddRow(boolean addRow) {
        this.impl.setAddRow(addRow);
        return this;
    }

    @Override
    public boolean isDelRow() {
        return this.impl.isDelRow();
    }

    public DataTableDeclare<T> setDelRow(boolean delRow) {
        this.impl.setDelRow(delRow);
        return this;
    }

    @Override
    public boolean isSingle() {
        return this.impl.isSingle();
    }

    public DataTableDeclare<T> setSingle(boolean single) {
        this.impl.setSingle(single);
        return this;
    }

    @Override
    public boolean isFixed() {
        return this.impl.isFixed();
    }

    @Override
    public boolean isEnableFilter() {
        return this.impl.isEnableFilter();
    }

    @Override
    public boolean isFilterCondition() {
        return this.impl.isFilterCondition();
    }

    public DataTableDeclare<T> setFixed(boolean fixed) {
        this.impl.setFixed(fixed);
        return this;
    }

    @Override
    public boolean isHideCkeckRowNum() {
        return this.impl.isHideCkeckRowNum();
    }

    public DataTableDeclare<T> setHideCkeckRowNum(boolean hideCkeckRowNum) {
        this.impl.setHideCkeckRowNum(hideCkeckRowNum);
        return this;
    }

    public int getInitRows() {
        return this.impl.getInitRows();
    }

    public DataTableDeclare<T> setInitRows(int initRows) {
        this.impl.setInitRows(initRows);
        return this;
    }

    public NamedContainerImpl<? extends DataFieldDefineImpl> getFields() {
        return this.impl.getFields();
    }

    public boolean isSolidified() {
        return this.impl.isSolidified();
    }

    public DataTableDeclare<T> setSolidified(boolean solidified) {
        this.impl.setSolidified(solidified);
        return this;
    }
}

