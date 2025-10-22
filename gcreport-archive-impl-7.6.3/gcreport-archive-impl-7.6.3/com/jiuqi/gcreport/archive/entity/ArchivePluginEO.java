/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.archive.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;

@DBTable(name="GC_ARCHIVEPLUGIN", title="\u62a5\u8868\u5f52\u6863\u63d2\u4ef6\u8868", inStorage=true, indexs={})
public class ArchivePluginEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_ARCHIVEPLUGIN";
    @DBColumn(nameInDB="PLUGINCODE", title="\u63d2\u4ef6\u4ee3\u7801", dbType=DBColumn.DBType.Varchar, length=40, isRequired=true)
    private String pluginCode;

    public static String getTABLENAME() {
        return TABLENAME;
    }

    public ArchivePluginEO() {
    }

    public ArchivePluginEO(String pluginCode) {
        this.pluginCode = pluginCode;
    }

    public String getPluginCode() {
        return this.pluginCode;
    }

    public void setPluginCode(String pluginCode) {
        this.pluginCode = pluginCode;
    }
}

