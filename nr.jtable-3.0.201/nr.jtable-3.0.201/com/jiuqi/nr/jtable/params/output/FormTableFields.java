/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.jtable.params.output;

import com.jiuqi.nr.jtable.params.base.FieldData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;

@ApiModel(value="FormTableFields", description="\u5b58\u50a8\u8868\u6307\u6807\u5217\u8868")
public class FormTableFields {
    @ApiModelProperty(value="\u5b58\u50a8\u8868key", name="key")
    private String key;
    @ApiModelProperty(value="\u5b58\u50a8\u8868code", name="code")
    private String code;
    @ApiModelProperty(value="\u5b58\u50a8\u8868\u6807\u9898", name="title")
    private String title;
    @ApiModelProperty(value="\u6307\u6807\u5217\u8868", name="fields")
    private List<FieldData> fields = new ArrayList<FieldData>();

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
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

    public List<FieldData> getFields() {
        return this.fields;
    }

    public void setFields(List<FieldData> fields) {
        this.fields = fields;
    }
}

