/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.samecontrol.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;

@DBTable(name="GC_SAMECTRLORGVIEW", title="\u540c\u63a7\u53d8\u52a8\u5355\u4f4d\u89c6\u56fe\u6620\u5c04\u8868")
public class SameCtrlOrgViewEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_SAMECTRLORGVIEW";
    @DBColumn(title="\u539f\u59cb\u89c6\u56fe", dbType=DBColumn.DBType.Varchar, isRequired=true)
    private String originalViewId;
    @DBColumn(title="\u540c\u63a7\u89c6\u56fe", dbType=DBColumn.DBType.Varchar, isRequired=true)
    private String sameCtrlViewId;

    public String getOriginalViewId() {
        return this.originalViewId;
    }

    public void setOriginalViewId(String originalViewId) {
        this.originalViewId = originalViewId;
    }

    public String getSameCtrlViewId() {
        return this.sameCtrlViewId;
    }

    public void setSameCtrlViewId(String sameCtrlViewId) {
        this.sameCtrlViewId = sameCtrlViewId;
    }
}

