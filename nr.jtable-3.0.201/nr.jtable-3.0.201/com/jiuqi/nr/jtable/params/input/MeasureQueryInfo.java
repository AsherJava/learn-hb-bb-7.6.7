/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.context.infc.INRContext
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.jtable.params.input;

import com.jiuqi.nr.context.infc.INRContext;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="EntityQueryByViewInfo", description="\u91cf\u7eb2\u6570\u636e\u67e5\u8be2\u53c2\u6570")
public class MeasureQueryInfo
implements INRContext {
    @ApiModelProperty(value="\u91cf\u7eb2\u89c6\u56fekey", name="measureViewKey")
    private String measureViewKey;
    @ApiModelProperty(value="\u91cf\u7eb2\u5206\u7ec4", name="measureGroup")
    private String measureGroup;
    @ApiModelProperty(value="\u91cf\u7eb2\u6761\u76eekey", name="measureValue")
    private String measureValue;
    private String contextTaskKey;
    private String contextEntityId;
    private String contextFormSchemeKey;
    private String contextFilterExpression;

    public String getMeasureViewKey() {
        return this.measureViewKey;
    }

    public void setMeasureViewKey(String measureViewKey) {
        this.measureViewKey = measureViewKey;
    }

    public String getMeasureGroup() {
        return this.measureGroup;
    }

    public void setMeasureGroup(String measureGroup) {
        this.measureGroup = measureGroup;
    }

    public String getMeasureValue() {
        return this.measureValue;
    }

    public void setMeasureValue(String measureValue) {
        this.measureValue = measureValue;
    }

    public String getContextEntityId() {
        return this.contextEntityId;
    }

    public String getContextFilterExpression() {
        return this.contextFilterExpression;
    }
}

