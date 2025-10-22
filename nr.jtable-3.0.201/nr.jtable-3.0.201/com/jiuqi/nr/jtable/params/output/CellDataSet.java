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
import java.util.ArrayList;
import java.util.List;

@ApiModel(value="CellDataSet", description="\u5355\u5143\u683c\u5220\u9009\u503c\u7ed3\u679c")
public class CellDataSet {
    @ApiModelProperty(value="\u67e5\u8be2\u7ed3\u679c\u4fe1\u606f", name="message")
    private String message;
    @ApiModelProperty(value="\u94fe\u63a5key", name="cellKey")
    private String cellKey;
    @ApiModelProperty(value="\u5355\u5143\u683c\u5220\u9009\u503c\u5217\u8868", name="cellKey")
    private List<Object> data = new ArrayList<Object>();
    @ApiModelProperty(value="\u5355\u5143\u683c\u5220\u9009\u503c\u6570\u91cf", name="count")
    private int count;
    @ApiModelProperty(value="\u5f53\u524d\u9875", name="currentPage")
    private int currentPage;

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCellKey() {
        return this.cellKey;
    }

    public void setCellKey(String cellKey) {
        this.cellKey = cellKey;
    }

    public List<Object> getData() {
        return this.data;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCurrentPage() {
        return this.currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}

