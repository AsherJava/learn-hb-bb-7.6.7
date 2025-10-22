/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.definition.internal.impl;

import com.jiuqi.np.definition.common.TableIndexType;
import com.jiuqi.np.definition.facade.DesignTableIndexDefine;
import com.jiuqi.np.definition.internal.anno.DBAnno;
import java.util.ArrayList;

@DBAnno.DBTable(dbTable="DES_SYS_TABLEINDEX")
public class DesignTableIndexDefineImpl
implements DesignTableIndexDefine {
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

    @Override
    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public void setName(String name) {
        this.title = name;
    }

    @Override
    public void setColumnsFieldKeys(String[] columnsFieldKeys) {
        this.columnsFieldKeys = columnsFieldKeys;
    }

    @Override
    public void setIndexType(TableIndexType indexType) {
        this.indexType = indexType;
    }

    @Override
    public String getDBName() {
        return this.dbName;
    }

    @Override
    public void setDBName(String dbName) {
        this.dbName = dbName;
    }

    @Override
    public String getTableKey() {
        return this.tableKey;
    }

    @Override
    public void setTableKey(String uuid) {
        this.tableKey = uuid;
    }
}

