/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.jtable.params.output;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

@ApiModel(value="MeasureData", description="\u91cf\u7eb2\u6570\u636e\u6761\u76ee")
public class MeasureData
implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value="\u91cf\u7eb2\u6761\u76eeid", name="id")
    private String id;
    @ApiModelProperty(value="\u91cf\u7eb2\u6761\u76ee\u4ee3\u7801", name="code")
    private String code;
    @ApiModelProperty(value="\u91cf\u7eb2\u6761\u76ee\u6807\u9898", name="title")
    private String title;
    @ApiModelProperty(value="\u91cf\u7eb2\u6761\u76ee\u7c7b\u578b", name="type")
    private String type;
    @ApiModelProperty(value="\u662f\u5426\u57fa\u51c6", name="base")
    private boolean base;
    @ApiModelProperty(value="\u4e0e\u57fa\u51c6\u6bd4\u7387", name="rate")
    private double rate;

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

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isBase() {
        return this.base;
    }

    public void setBase(boolean base) {
        this.base = base;
    }

    public double getRate() {
        return this.rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.code == null ? 0 : this.code.hashCode());
        result = 31 * result + (this.title == null ? 0 : this.title.hashCode());
        result = 31 * result + (this.type == null ? 0 : this.type.hashCode());
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
        MeasureData other = (MeasureData)obj;
        if (this.code == null ? other.code != null : !this.code.equals(other.code)) {
            return false;
        }
        if (this.title == null ? other.title != null : !this.title.equals(other.title)) {
            return false;
        }
        return !(this.type == null ? other.type != null : !this.type.equals(other.type));
    }
}

