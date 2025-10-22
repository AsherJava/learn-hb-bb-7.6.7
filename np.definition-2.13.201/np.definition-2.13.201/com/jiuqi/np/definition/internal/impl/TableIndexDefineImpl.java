/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.definition.internal.impl;

import com.jiuqi.np.definition.common.TableIndexType;
import com.jiuqi.np.definition.facade.TableIndexDefine;
import com.jiuqi.np.definition.internal.anno.DBAnno;
import java.util.ArrayList;

@DBAnno.DBTable(dbTable="SYS_TABLEINDEX")
public class TableIndexDefineImpl
implements TableIndexDefine {
    @DBAnno.DBField(dbField="ti_key", isPk=true)
    private String key;
    @DBAnno.DBField(dbField="ti_title")
    private String title;
    @DBAnno.DBField(dbField="TI_COLUMNS", set="setColumnsFieldKeysStr", get="getColumnsFieldKeysStr")
    private String[] columnsFieldKeys;
    @DBAnno.DBField(dbField="ti_type", appType=TableIndexType.class, dbType=Integer.class, tranWith="transTableIndexType")
    private TableIndexType indexType;
    @DBAnno.DBField(dbField="ti_dbName")
    private String dbName;
    @DBAnno.DBField(dbField="ti_tableKey")
    private String tableKey;

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public String getName() {
        return this.title;
    }

    @Override
    public String[] getColumnsFieldKeys() {
        return this.columnsFieldKeys;
    }

    @Override
    public TableIndexType getIndexType() {
        return this.indexType;
    }

    public String getColumnsFieldKeysStr() {
        StringBuffer keys = new StringBuffer();
        for (int i = 0; this.columnsFieldKeys != null && i < this.columnsFieldKeys.length; ++i) {
            if (i > 0) {
                keys.append(";");
            }
            keys.append(this.columnsFieldKeys[i].toString());
        }
        return keys.toString();
    }

    public void setColumnsFieldKeysStr(String columnsStr) {
        ArrayList<String> list = new ArrayList<String>();
        if (columnsStr != null) {
            String[] columns;
            for (String column : columns = columnsStr.split(";")) {
                list.add(column);
            }
        }
        this.columnsFieldKeys = list.toArray(new String[1]);
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setName(String name) {
        this.title = name;
    }

    public void setColumnsFieldKeys(String[] columnsFieldKeys) {
        this.columnsFieldKeys = columnsFieldKeys;
    }

    public void setIndexType(TableIndexType indexType) {
        this.indexType = indexType;
    }

    @Override
    public String getDBName() {
        return this.dbName;
    }

    public void setDBName(String dbName) {
        this.dbName = dbName;
    }

    @Override
    public String getTableKey() {
        return this.tableKey;
    }

    public void setTableKey(String String2) {
        this.tableKey = String2;
    }
}

