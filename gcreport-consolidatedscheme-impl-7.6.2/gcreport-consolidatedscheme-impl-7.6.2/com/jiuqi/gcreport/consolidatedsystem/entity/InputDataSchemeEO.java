/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBIndex
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.consolidatedsystem.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBIndex;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;

@DBTable(name="GC_INPUTDATASCHEME", title="\u5185\u90e8\u5f55\u5165\u8868\u4e0e\u6570\u636e\u65b9\u6848\u5173\u8054\u8868", indexs={@DBIndex(name="IDX_INPUTDATASCHEME_DSKEY", columnsFields={"DATASCHEMEKEY"})})
public class InputDataSchemeEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_INPUTDATASCHEME";
    private static final long serialVersionUID = -2138923242302368991L;
    @DBColumn(nameInDB="TABLEKEY", title="\u8bbe\u8ba1\u671f\u6570\u636e\u8868ID", dbType=DBColumn.DBType.Varchar, length=36, isRequired=true)
    private String tableKey;
    @DBColumn(nameInDB="DATASCHEMEKEY", title="\u6570\u636e\u65b9\u6848ID", dbType=DBColumn.DBType.Varchar, length=36, isRequired=true)
    private String dataSchemeKey;
    @DBColumn(nameInDB="TABLECODE", title="\u8bbe\u8ba1\u671f\u6570\u636e\u8868\u540d", dbType=DBColumn.DBType.Varchar, length=36, isRequired=true)
    private String tableCode;

    public String getTableKey() {
        return this.tableKey;
    }

    public void setTableKey(String tableKey) {
        this.tableKey = tableKey;
    }

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    public String getTableCode() {
        return this.tableCode;
    }

    public void setTableCode(String tableCode) {
        this.tableCode = tableCode;
    }
}

