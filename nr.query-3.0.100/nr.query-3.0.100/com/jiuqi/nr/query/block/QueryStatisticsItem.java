/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 */
package com.jiuqi.nr.query.block;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiuqi.nr.query.common.BusinessObject;
import com.jiuqi.nr.query.common.StatisticsFieldType;
import com.jiuqi.nr.query.deserializer.QueryStatisticsItemDeserializre;
import com.jiuqi.nr.query.serializer.QueryStatisticsItemSerializer;
import org.springframework.beans.factory.annotation.Autowired;

@JsonSerialize(using=QueryStatisticsItemSerializer.class)
@JsonDeserialize(using=QueryStatisticsItemDeserializre.class)
public class QueryStatisticsItem
extends BusinessObject {
    @Autowired
    public static final String STATISTICSITEM_FORMULAEXPRESSION = "formulaExpression";
    public static final String STATISTICSITEM_BUILTIN = "builtIn";
    public static final String STATISTICSITEM_TITLE = "title";
    private String formulaExpression = null;
    private StatisticsFieldType builtIn = StatisticsFieldType.NONE;

    public String getFormulaExpression() {
        return this.formulaExpression;
    }

    public void setFormulaExpression(String formulaExpression) {
        this.formulaExpression = formulaExpression;
    }

    public String getBuiltIn() {
        return this.builtIn.name();
    }

    public void setBuiltIn(String builtIn) {
        this.builtIn = StatisticsFieldType.valueOf(builtIn);
    }

    public void setBuiltIn(StatisticsFieldType builtIn) {
        this.builtIn = builtIn;
    }
}

