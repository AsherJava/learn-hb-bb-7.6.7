/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.common.params;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

@ApiModel(value="DimensionValue", description="\u7ef4\u5ea6\u4fe1\u606f")
@JsonIgnoreProperties(ignoreUnknown=true)
public class DimensionValue
implements Serializable {
    private static final long serialVersionUID = -2953147379262581603L;
    @ApiModelProperty(value="\u7ef4\u5ea6\u540d", name="name", required=true, example="DATETIME")
    private String name;
    @ApiModelProperty(value="\u7ef4\u5ea6\u503c", name="value", required=true, example="2018N0001")
    private String value;
    @ApiModelProperty(value="\u7ef4\u5ea6\u7c7b\u578b\uff08\u53ef\u4ee5\u4e0d\u4f20\uff09", name="type", required=false)
    private int type;

    public DimensionValue() {
    }

    public DimensionValue(DimensionValue dimensionValue) {
        this.name = dimensionValue.getName();
        this.value = dimensionValue.getValue();
        this.type = dimensionValue.getType();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.name == null ? 0 : this.name.hashCode());
        result = 31 * result + (this.value == null ? 0 : this.value.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        DimensionValue other = (DimensionValue)obj;
        if (this.name == null ? other.name != null : !this.name.equals(other.name)) {
            return false;
        }
        return !(this.value == null ? other.value != null : !this.value.equals(other.value));
    }
}

