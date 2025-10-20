/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.consolidatedsystem.entity.option;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;

@DBTable(name="GC_CONSOPTION", title="\u5408\u5e76\u9009\u9879", inStorage=true)
public class ConsolidatedOptionEO
extends DefaultTableEntity {
    private static final long serialVersionUID = 1L;
    public static final String ENTITYNAME = "consOption";
    public static final String TABLENAME = "GC_CONSOPTION";
    @DBColumn(nameInDB="SYSTEMID", dbType=DBColumn.DBType.Varchar, length=36)
    private String systemId;
    @DBColumn(nameInDB="OPTIONDATA", dbType=DBColumn.DBType.NText)
    private String data;

    public String getSystemId() {
        return this.systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }
}

