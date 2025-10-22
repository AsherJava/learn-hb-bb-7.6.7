/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.jtable.params.input;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ApiModel(value="FloatOrderStructure", description="\u6d6e\u52a8\u884cfloatOrder\u8ba1\u7b97")
public class FloatOrderStructure
implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value="\u5f53\u524d\u5206\u9875\u9996\u884c\u7684floatOrder", name="firstFloatOrder")
    private String firstFloatOrder;
    @ApiModelProperty(value="\u5f53\u524d\u5206\u9875\u5c3e\u884c\u7684floatOrder", name="lastFloatOrder")
    private String lastFloatOrder;
    @ApiModelProperty(value="\u5f53\u524d\u5206\u9875\u9996\u884c\u7684rowID", name="firstFloatOrder")
    private String firstRowID;
    @ApiModelProperty(value="\u5f53\u524d\u5206\u9875\u5c3e\u884c\u7684rowID", name="lastFloatOrder")
    private String lastRowID;
    @ApiModelProperty(value="\u5f53\u524d\u5206\u9875\u524d\u63d2\u884c\u7684rowid\u96c6\u5408", name="beforeInsertRowIds")
    private List<String> beforeInsertRowIds = new ArrayList<String>();
    @ApiModelProperty(value="\u5f53\u524d\u5206\u9875\u540e\u63d2\u884c\u7684rowid\u96c6\u5408", name="afterInsertRowIds")
    private List<String> afterInsertRowIds = new ArrayList<String>();

    public FloatOrderStructure() {
    }

    public FloatOrderStructure(String firstFloatOrder, String lastFloatOrder, List<String> beforeInsertRowIds, List<String> afterInsertRowIds) {
        this.firstFloatOrder = firstFloatOrder;
        this.lastFloatOrder = lastFloatOrder;
        this.beforeInsertRowIds = beforeInsertRowIds;
        this.afterInsertRowIds = afterInsertRowIds;
    }

    public String getFirstFloatOrder() {
        return this.firstFloatOrder;
    }

    public void setFirstFloatOrder(String firstFloatOrder) {
        this.firstFloatOrder = firstFloatOrder;
    }

    public String getLastFloatOrder() {
        return this.lastFloatOrder;
    }

    public void setLastFloatOrder(String lastFloatOrder) {
        this.lastFloatOrder = lastFloatOrder;
    }

    public List<String> getBeforeInsertRowIds() {
        return this.beforeInsertRowIds;
    }

    public void setBeforeInsertRowIds(List<String> beforeInsertRowIds) {
        this.beforeInsertRowIds = beforeInsertRowIds;
    }

    public List<String> getAfterInsertRowIds() {
        return this.afterInsertRowIds;
    }

    public void setAfterInsertRowIds(List<String> afterInsertRowIds) {
        this.afterInsertRowIds = afterInsertRowIds;
    }

    public String getFirstRowID() {
        return this.firstRowID;
    }

    public void setFirstRowID(String firstRowID) {
        this.firstRowID = firstRowID;
    }

    public String getLastRowID() {
        return this.lastRowID;
    }

    public void setLastRowID(String lastRowID) {
        this.lastRowID = lastRowID;
    }
}

