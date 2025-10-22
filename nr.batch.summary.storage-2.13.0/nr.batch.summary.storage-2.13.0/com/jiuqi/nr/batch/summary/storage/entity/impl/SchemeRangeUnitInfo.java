/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  javax.annotation.Resource
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.batch.summary.storage.entity.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jiuqi.nr.batch.summary.storage.entity.SchemeRangeUnit;
import com.jiuqi.nr.batch.summary.storage.enumeration.RangeUnitType;
import com.jiuqi.nr.batch.summary.storage.utils.BatchSummaryUtils;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.jdbc.core.JdbcTemplate;

public class SchemeRangeUnitInfo
implements SchemeRangeUnit {
    private RangeUnitType rangeUnitType = RangeUnitType.ALL;
    private List<String> checkList;
    private String expression;
    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public RangeUnitType getRangeUnitType() {
        return this.rangeUnitType;
    }

    public void setRangeUnitType(RangeUnitType rangeUnitType) {
        this.rangeUnitType = rangeUnitType;
    }

    @Override
    public List<String> getCheckList() {
        return this.checkList;
    }

    public void setCheckList(List<String> checkList) {
        this.checkList = checkList;
    }

    @Override
    public String getExpression() {
        return this.expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    @Override
    @JsonIgnore
    public String valueToClob() {
        switch (this.rangeUnitType) {
            case CHECK_UNIT: {
                return BatchSummaryUtils.toJSONStr(this.checkList);
            }
            case EXPRESSION: {
                return this.expression;
            }
        }
        return null;
    }

    @JsonIgnore
    public void transformAndSetCheckList(String jsonStr) {
        switch (this.rangeUnitType) {
            case CHECK_UNIT: {
                this.checkList = BatchSummaryUtils.toJavaArray(jsonStr, String.class);
                break;
            }
            case EXPRESSION: {
                this.expression = jsonStr;
            }
        }
    }
}

