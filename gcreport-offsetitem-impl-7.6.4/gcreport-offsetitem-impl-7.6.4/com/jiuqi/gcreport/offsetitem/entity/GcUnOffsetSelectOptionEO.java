/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.offsetitem.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;

@DBTable(name="GC_UNOFFSETSELECTOPTION", title="\u672a\u62b5\u9500\u52a8\u6001\u6269\u5c55\u5b58\u50a8\u8868", inStorage=true)
public class GcUnOffsetSelectOptionEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_UNOFFSETSELECTOPTION";
    @DBColumn(nameInDB="ID", title="\u884c\u6807\u8bc6", dbType=DBColumn.DBType.Varchar, isRecid=true)
    protected String id;
    @DBColumn(nameInDB="CODE", title="\u4ee3\u7801", dbType=DBColumn.DBType.Varchar)
    protected String code;
    @DBColumn(nameInDB="TITLE", title="\u540d\u79f0", dbType=DBColumn.DBType.Varchar)
    protected String title;
    @DBColumn(nameInDB="EFFECTRANGE", title="\u751f\u6548\u8303\u56f4", dbType=DBColumn.DBType.Varchar)
    protected String effectRange;
    @DBColumn(nameInDB="CONTENT", title="\u5185\u5bb9", dbType=DBColumn.DBType.Varchar)
    protected String content;
    @DBColumn(nameInDB="ORDINAL", title="\u987a\u5e8f\u53f7", dbType=DBColumn.DBType.Int)
    protected Integer ordinal;
    @DBColumn(nameInDB="SORTTYPE", title="\u6392\u5e8f\u65b9\u5f0f", dbType=DBColumn.DBType.Int)
    protected Integer sortType;
    @DBColumn(nameInDB="DATASOURCE", title="\u6570\u636e\u6e90", dbType=DBColumn.DBType.Varchar)
    protected String dataSource;
    @DBColumn(nameInDB="DATASOURCESTATE", title="\u6570\u636e\u6e90", dbType=DBColumn.DBType.Varchar)
    protected String dataSourceState;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEffectRange() {
        return this.effectRange;
    }

    public void setEffectRange(String effectRange) {
        this.effectRange = effectRange;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getOrdinal() {
        return this.ordinal;
    }

    public void setOrdinal(Integer ordinal) {
        this.ordinal = ordinal;
    }

    public Integer getSortType() {
        return this.sortType;
    }

    public void setSortType(Integer sortType) {
        this.sortType = sortType;
    }

    public String getDataSource() {
        return this.dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getDataSourceState() {
        return this.dataSourceState;
    }

    public void setDataSourceState(String dataSourceState) {
        this.dataSourceState = dataSourceState;
    }
}

