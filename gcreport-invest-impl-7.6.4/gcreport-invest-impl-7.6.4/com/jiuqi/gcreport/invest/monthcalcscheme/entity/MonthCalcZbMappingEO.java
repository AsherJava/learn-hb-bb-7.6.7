/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.invest.monthcalcscheme.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;

@DBTable(name="GC_MONTHCALCZBMAPPING", title="\u6309\u6708\u8ba1\u7b97\u7ba1\u7406\u6307\u6807\u6620\u5c04\u8868")
public class MonthCalcZbMappingEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_MONTHCALCZBMAPPING";
    @DBColumn(nameInDB="MONTHCALCSCHEMEID", dbType=DBColumn.DBType.Varchar, length=36)
    private String monthCalcSchemeId;
    @DBColumn(nameInDB="ZB_Y", dbType=DBColumn.DBType.Varchar, length=60)
    private String zb_Y;
    @DBColumn(nameInDB="ZBTITLE", dbType=DBColumn.DBType.Varchar, length=60)
    private String zbTitle;
    @DBColumn(nameInDB="ZB_J", dbType=DBColumn.DBType.Varchar, length=60)
    private String zb_J;
    @DBColumn(nameInDB="ZB_H", dbType=DBColumn.DBType.Varchar, length=60)
    private String zb_H;
    @DBColumn(nameInDB="ZB_N", dbType=DBColumn.DBType.Text)
    private String zb_N;
    @DBColumn(nameInDB="SORTORDER", dbType=DBColumn.DBType.NVarchar)
    private String sortOrder;

    public String getMonthCalcSchemeId() {
        return this.monthCalcSchemeId;
    }

    public void setMonthCalcSchemeId(String monthCalcSchemeId) {
        this.monthCalcSchemeId = monthCalcSchemeId;
    }

    public String getZb_Y() {
        return this.zb_Y;
    }

    public void setZb_Y(String zb_Y) {
        this.zb_Y = zb_Y;
    }

    public String getZbTitle() {
        return this.zbTitle;
    }

    public void setZbTitle(String zbTitle) {
        this.zbTitle = zbTitle;
    }

    public String getZb_J() {
        return this.zb_J;
    }

    public void setZb_J(String zb_J) {
        this.zb_J = zb_J;
    }

    public String getZb_H() {
        return this.zb_H;
    }

    public void setZb_H(String zb_H) {
        this.zb_H = zb_H;
    }

    public String getZb_N() {
        return this.zb_N;
    }

    public void setZb_N(String zb_N) {
        this.zb_N = zb_N;
    }

    public String getSortOrder() {
        return this.sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }
}

