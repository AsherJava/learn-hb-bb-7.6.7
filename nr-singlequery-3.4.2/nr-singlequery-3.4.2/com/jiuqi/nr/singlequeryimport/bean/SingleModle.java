/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.singlequeryimport.bean;

import com.jiuqi.np.definition.internal.anno.DBAnno;
import java.sql.Clob;

@DBAnno.DBTable(dbTable="single_modle")
public class SingleModle {
    @DBAnno.DBField(dbField="name", isPk=true)
    private String name;
    @DBAnno.DBField(dbField="grid_cell", tranWith="transBytes", dbType=Clob.class, appType=String.class)
    private String gridCell;
    @DBAnno.DBField(dbField="megre_cell", tranWith="transBytes", dbType=Clob.class, appType=String.class)
    private String megreCell;
    @DBAnno.DBField(dbField="head_count")
    private Integer headCount;
    @DBAnno.DBField(dbField="USE_ROW_NUM")
    private Integer useRowNum;
    @DBAnno.DBField(dbField="USE_COL_NUM")
    private Integer useColNum;

    public Integer getUseRowNum() {
        return this.useRowNum;
    }

    public void setUseRowNum(Integer useRowNum) {
        this.useRowNum = useRowNum;
    }

    public Integer getUseColNum() {
        return this.useColNum;
    }

    public void setUseColNum(Integer useColNum) {
        this.useColNum = useColNum;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGridCell() {
        return this.gridCell;
    }

    public void setGridCell(String gridCell) {
        this.gridCell = gridCell;
    }

    public String getMegreCell() {
        return this.megreCell;
    }

    public void setMegreCell(String megreCell) {
        this.megreCell = megreCell;
    }

    public Integer getHeadCount() {
        return this.headCount;
    }

    public void setHeadCount(Integer headCount) {
        this.headCount = headCount;
    }
}

