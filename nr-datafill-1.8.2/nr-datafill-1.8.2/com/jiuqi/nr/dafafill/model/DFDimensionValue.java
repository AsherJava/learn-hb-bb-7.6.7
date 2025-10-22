/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.dafafill.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

@ApiModel(value="DFDimensionValue", description="\u7ef4\u5ea6\u4fe1\u606f")
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class DFDimensionValue
implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value="\u7ef4\u5ea6\u540d", name="name", required=true, example="DATETIME")
    private String name;
    @ApiModelProperty(value="\u7ef4\u5ea6\u503c", name="values", required=true, example="2018N0001;2018N0001;20220701;20220101")
    private String values;
    @ApiModelProperty(value="\u6700\u5927\u7ef4\u5ea6\u503c", name="maxValue", required=true, example="2022N0001")
    private String maxValue;
    @ApiModelProperty(value="NR\u7ef4\u5ea6\u503c\u7f13\u5b58", required=true, example="2018N0001;2018N0001")
    private String _values;
    @ApiModelProperty(value="NR\u7ef4\u5ea6\u503c\u7f13\u5b58", required=true, example="2018N0001;2018N0001")
    private String _maxValues;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Deprecated
    public String getMaxValue() {
        return this.maxValue;
    }

    public void setMaxValue(String maxValue) {
        this.maxValue = maxValue;
        this._maxValues = null;
    }

    @Deprecated
    public String getValues() {
        return this.values;
    }

    public void setValues(String values) {
        this.values = values;
        this._values = null;
    }

    String get_values() {
        return this._values;
    }

    void set_values(String _values) {
        this._values = _values;
    }

    String get_maxValues() {
        return this._maxValues;
    }

    void set_maxValues(String _maxValues) {
        this._maxValues = _maxValues;
    }

    public static long getSerialversionuid() {
        return 1L;
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.maxValue == null ? 0 : this.maxValue.hashCode());
        result = 31 * result + (this.name == null ? 0 : this.name.hashCode());
        result = 31 * result + (this.values == null ? 0 : this.values.hashCode());
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
        DFDimensionValue other = (DFDimensionValue)obj;
        if (this.maxValue == null ? other.maxValue != null : !this.maxValue.equals(other.maxValue)) {
            return false;
        }
        if (this.name == null ? other.name != null : !this.name.equals(other.name)) {
            return false;
        }
        return !(this.values == null ? other.values != null : !this.values.equals(other.values));
    }

    public String toString() {
        return "DFDimensionValue [name=" + this.name + ", values=" + this.values + ", maxValue=" + this.maxValue + "]";
    }
}

