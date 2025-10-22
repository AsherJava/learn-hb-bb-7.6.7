/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.dafafill.model.table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.nr.dafafill.model.enums.ColWidthType;
import com.jiuqi.nr.dafafill.model.enums.FieldType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import org.springframework.util.StringUtils;

@ApiModel(value="DataFillIndexData", description="\u57fa\u7840\u7d22\u5f15")
@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class DataFillIndexData
implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value="\u503c")
    private String value;
    @JsonIgnore
    @ApiModelProperty(value="code")
    private String code;
    @ApiModelProperty(value="\u5c55\u793a\u503c")
    private String title;
    @ApiModelProperty(value="\u5c42\u7ea7")
    private Integer level;
    @ApiModelProperty(value="\u7c7b\u578b")
    private FieldType type;
    @ApiModelProperty(value="\u5217\u5bbd\u7c7b\u578b")
    private ColWidthType colWidthType;
    @ApiModelProperty(value="\u81ea\u5b9a\u5217\u5bbd\uff0c\u4ec5\u5217\u5bbd\u7c7b\u578b\u4e3a\u81ea\u5b9a\u4e49\u65f6\u6709\u6548")
    private Integer colWidth;
    @ApiModelProperty(value="\u662f\u5426\u53ea\u8bfb")
    private Boolean readOnly;
    @ApiModelProperty(value="\u662f\u5426\u9690\u85cf")
    private Boolean hide;
    @ApiModelProperty(value="\u6d6e\u52a8\u884c\u901a\u914d\u516c\u5f0f")
    private String generalFormale;
    @ApiModelProperty(value="\u9ed8\u8ba4\u503c")
    private String defaultValue;

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public FieldType getType() {
        return this.type;
    }

    public void setType(FieldType type) {
        this.type = type;
    }

    public ColWidthType getColWidthType() {
        return this.colWidthType;
    }

    public void setColWidthType(ColWidthType colWidthType) {
        this.colWidthType = colWidthType;
    }

    public Integer getColWidth() {
        return this.colWidth;
    }

    public void setColWidth(Integer colWidth) {
        this.colWidth = colWidth;
    }

    public Boolean getReadOnly() {
        return this.readOnly;
    }

    public void setReadOnly(Boolean readOnly) {
        this.readOnly = readOnly;
    }

    public Boolean getHide() {
        return this.hide;
    }

    public void setHide(Boolean hide) {
        this.hide = hide;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getLevel() {
        return this.level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getGeneralFormale() {
        return this.generalFormale;
    }

    public void setGeneralFormale(String generalFormale) {
        this.generalFormale = generalFormale;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String toString() {
        return (StringUtils.hasLength(this.code) ? this.code : this.value) + "|" + this.title;
    }
}

