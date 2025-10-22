/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.jtable.params.output;

import com.jiuqi.nr.jtable.params.output.FormTableFields;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;

@ApiModel(value="FormTables", description="\u62a5\u8868\u6307\u6807\u5217\u8868")
public class FormTables {
    @ApiModelProperty(value="\u62a5\u8868\u5173\u8054\u7684\u5b58\u50a8\u8868\u5217\u8868", name="tables")
    private List<FormTableFields> tables = new ArrayList<FormTableFields>();

    public List<FormTableFields> getTables() {
        return this.tables;
    }

    public void setTables(List<FormTableFields> tables) {
        this.tables = tables;
    }
}

