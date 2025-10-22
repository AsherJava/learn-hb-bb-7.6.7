/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.table.df.DataFrame
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.dafafill.model;

import com.jiuqi.nr.dafafill.model.DFDimensionValue;
import com.jiuqi.nr.dafafill.model.Options;
import com.jiuqi.nr.dafafill.model.PageInfo;
import com.jiuqi.nr.dafafill.model.enums.TableType;
import com.jiuqi.nr.dafafill.model.table.DataFillBaseCell;
import com.jiuqi.nr.table.df.DataFrame;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;

@ApiModel(value="DataFillDataResult", description="\u6570\u636e\u67e5\u8be2\u7ed3\u679c")
public class DataFillDataResult
implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value="\u662f\u5426\u6210\u529f", name="success")
    private boolean success;
    @ApiModelProperty(value="\u63d0\u793a\u4fe1\u606f", name="message")
    private String message;
    @ApiModelProperty(value="\u516c\u5171\u7ef4\u5ea6\u5217\u8868", name="publicDimensions")
    private List<DFDimensionValue> publicDimensions;
    @ApiModelProperty(value="\u8868\u683c\u6570\u636e", name="table")
    private DataFrame<DataFillBaseCell> table;
    @ApiModelProperty(value="\u8868\u683c\u7c7b\u578b", name="tableType")
    private TableType tableType;
    @ApiModelProperty(value="\u603b\u884c\u6570", name="totalCount")
    private int totalCount;
    @ApiModelProperty(value="\u5f53\u524d\u5206\u9875\u4fe1\u606f", name="\u5206\u9875\u4fe1\u606f")
    private PageInfo pageInfo;
    @ApiModelProperty(value="\u5176\u4ed6\u9009\u9879\u4fe1\u606f", name="\u5176\u4ed6\u9009\u9879\u4fe1\u606f")
    private Options otherOption;

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DFDimensionValue> getPublicDimensions() {
        return this.publicDimensions;
    }

    public void setPublicDimensions(List<DFDimensionValue> publicDimensions) {
        this.publicDimensions = publicDimensions;
    }

    public int getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public PageInfo getPageInfo() {
        return this.pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public TableType getTableType() {
        return this.tableType;
    }

    public void setTableType(TableType tableType) {
        this.tableType = tableType;
    }

    public DataFrame<DataFillBaseCell> getTable() {
        return this.table;
    }

    public void setTable(DataFrame<DataFillBaseCell> table) {
        this.table = table;
    }

    public Options getOtherOption() {
        return this.otherOption;
    }

    public void setOtherOption(Options otherOption) {
        this.otherOption = otherOption;
    }
}

