/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.jiuqi.va.domain.common.OrderNumUtil
 */
package com.jiuqi.va.domain.datamodel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jiuqi.va.domain.common.OrderNumUtil;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.util.StringUtils;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class DataModelIndex
implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonProperty(index=1)
    private String indexName;
    @JsonProperty(index=2)
    private String[] columnList;
    @JsonProperty(index=3)
    private Boolean unique;

    public DataModelIndex indexName(String indexName) {
        this.setIndexName(indexName);
        return this;
    }

    public DataModelIndex columnList(String ... columnList) {
        this.columnList = columnList;
        return this;
    }

    public DataModelIndex unique(Boolean unique) {
        this.unique = unique;
        return this;
    }

    public String getIndexName() {
        return this.indexName;
    }

    public void setIndexName(String indexName) {
        if (indexName == null || StringUtils.hasLength(indexName) && indexName.length() > 30) {
            this.setRandomIndexName();
        } else {
            this.indexName = indexName;
        }
    }

    public DataModelIndex randomIndexName() {
        this.setRandomIndexName();
        return this;
    }

    public void setRandomIndexName() {
        BigDecimal bd = OrderNumUtil.getOrderNumByCurrentTimeMillis();
        bd = bd.multiply(new BigDecimal(1000000L));
        bd = bd.setScale(0, RoundingMode.HALF_UP);
        this.indexName = "U" + bd.toPlainString();
    }

    public String[] getColumnList() {
        return this.columnList;
    }

    public void setColumnList(String ... columnList) {
        this.columnList = columnList;
    }

    public Boolean isUnique() {
        return this.unique == null ? Boolean.FALSE : this.unique;
    }

    public Boolean getUnique() {
        return this.unique;
    }

    public void setUnique(Boolean unique) {
        this.unique = unique;
    }
}

