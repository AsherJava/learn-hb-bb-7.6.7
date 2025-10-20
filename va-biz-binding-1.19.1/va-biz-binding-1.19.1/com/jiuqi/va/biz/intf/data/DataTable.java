/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.data;

import com.jiuqi.va.biz.intf.data.Data;
import com.jiuqi.va.biz.intf.data.DataField;
import com.jiuqi.va.biz.intf.data.DataRow;
import com.jiuqi.va.biz.intf.data.DataTableDefine;
import com.jiuqi.va.biz.intf.data.DataTableNode;
import com.jiuqi.va.biz.intf.value.ListContainer;
import com.jiuqi.va.biz.intf.value.NamedContainer;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public interface DataTable
extends DataTableNode {
    public Data getData();

    public DataTableDefine getDefine();

    public NamedContainer<? extends DataField> getFields();

    public void sort(Comparator<? super DataRow> var1);

    public ListContainer<? extends DataRow> getRows();

    public DataRow insertRow(int var1);

    public DataRow insertRow(int var1, Map<String, Object> var2);

    public List<? extends DataRow> insertRow(int var1, int var2);

    public List<? extends DataRow> insertRow(int var1, int var2, Map<String, Object> var3);

    public List<? extends DataRow> insertRow(int var1, int var2, List<Object> var3, List<Object> var4);

    public List<? extends DataRow> insertRowWithCheck(int var1, int var2, Map<String, Object> var3);

    public DataRow appendRow();

    public DataRow appendRow(Map<String, Object> var1);

    public DataRow appendRow(List<Object> var1, List<Object> var2);

    public DataRow appendRowWithCheck(Map<String, Object> var1);

    public List<? extends DataRow> appendRow(int var1);

    public List<? extends DataRow> appendRow(int var1, Map<String, Object> var2);

    public void deleteRow(int var1);

    public void deleteRow(int var1, int var2);

    public void deleteRowById(Object var1);

    public void deleteRow(DataRow var1);

    public void removeRow(int var1);

    public void removeRow(int var1, int var2);

    public void removeRowById(Object var1);

    public void removeRow(DataRow var1);

    public DataRow getRowById(Object var1);

    public Stream<? extends DataRow> getDeletedRows();

    public List<Map<String, Object>> getInsertData();

    public List<Map<String, Object>> getDeleteData();

    public List<Map<String, Object>> getUpdateData();

    public List<Map<String, Object>> getRowsData();

    public void insertRows(List<Map<String, Object>> var1);

    public void updateRows(List<Map<String, Object>> var1);

    public void setRowsData(List<Map<String, Object>> var1);

    public void setRowsData(List<Object> var1, List<List<Object>> var2);

    public List<Map<String, Object>> getFilterRowsData();
}

