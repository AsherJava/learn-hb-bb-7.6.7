/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.FieldSearchQuery
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.datascheme.web.param;

import com.jiuqi.nr.datascheme.api.FieldSearchQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="\u6570\u636e\u6761\u4ef6")
public class DataSchemeQueryPM {
    @ApiModelProperty(value="\u67e5\u8be2\u7b2c\u51e0\u9875")
    private Integer pageNum;
    @ApiModelProperty(value="\u67e5\u8be2\u5f53\u9875\u6570\u636e\u6761\u6570")
    private Integer pageCount;
    @ApiModelProperty(value="\u5173\u952e\u5b57", allowEmptyValue=true)
    private String keyword;
    @ApiModelProperty(value="\u8868")
    private String table;
    @ApiModelProperty(value="\u65b9\u6848key")
    private String scheme;
    @ApiModelProperty(value="\u7c7b\u522b", allowEmptyValue=true)
    private Integer kind;

    public FieldSearchQuery toQuery() {
        FieldSearchQuery query = new FieldSearchQuery();
        query.setKeyword(this.keyword);
        query.setTable(this.table);
        if (this.pageCount != null && this.pageNum != null) {
            query.setSkip(Integer.valueOf(this.pageCount * (this.pageNum - 1)));
            query.setLimit(Integer.valueOf(this.pageCount * this.pageNum));
        }
        query.setScheme(this.scheme);
        if (this.kind != null) {
            query.setKind(this.kind);
        }
        return query;
    }

    public Integer getPageNum() {
        return this.pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageCount() {
        return this.pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public String getKeyword() {
        return this.keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getTable() {
        return this.table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public Integer getKind() {
        return this.kind;
    }

    public void setKind(Integer kind) {
        this.kind = kind;
    }

    public String getScheme() {
        return this.scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }
}

