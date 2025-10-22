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
import com.jiuqi.nr.query.block.QueryComparisonType;
import com.jiuqi.nr.query.deserializer.QueryPreWarnItemDeserializer;
import com.jiuqi.nr.query.serializer.QueryPreWarnItemSerializer;
import org.springframework.stereotype.Service;

@Service
@JsonSerialize(using=QueryPreWarnItemSerializer.class)
@JsonDeserialize(using=QueryPreWarnItemDeserializer.class)
public class QueryPreWarnItem {
    public static final String ITEMVALUE = "value";
    public static final String ITEMCOLOR = "color";
    public static final String ITEMFIRSTSIGN = "firstSign";
    public static final String ITEMSECONDSIGN = "secondSign";
    private Double[] value = new Double[2];
    private String color = null;
    private QueryComparisonType firstSign;
    private QueryComparisonType secondSign;

    public Double[] getValue() {
        return this.value;
    }

    public void setValue(Double[] value) {
        this.value = value;
    }

    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public QueryComparisonType getFirstSign() {
        return this.firstSign;
    }

    public void setFirstSign(QueryComparisonType firstSign) {
        this.firstSign = firstSign;
    }

    public QueryComparisonType getSecondSign() {
        return this.secondSign;
    }

    public void setSecondSign(QueryComparisonType secondSign) {
        this.secondSign = secondSign;
    }
}

