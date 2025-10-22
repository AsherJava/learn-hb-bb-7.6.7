/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.PagerInfo
 *  com.jiuqi.nr.context.infc.INRContext
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.jtable.params.input;

import com.jiuqi.nr.common.params.PagerInfo;
import com.jiuqi.nr.context.infc.INRContext;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.input.CellQueryInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;

@ApiModel(value="ReportDataQueryInfo", description="\u5355\u5143\u683c\u5220\u9009\u503c\u67e5\u8be2\u53c2\u6570")
public class CellValueQueryInfo
implements INRContext {
    @ApiModelProperty(value="jtable\u73af\u5883\u4fe1\u606f", name="context", required=true)
    private JtableContext context;
    @ApiModelProperty(value="\u94fe\u63a5key", name="cellKey", required=true)
    private String cellKey;
    @ApiModelProperty(value="\u5220\u9009\u503c\u8fc7\u6ee4\u6761\u4ef6", name="filter", required=false)
    private String filter;
    @ApiModelProperty(value="\u5355\u5143\u683c\u67e5\u8be2\u53c2\u6570", name="cells", required=false)
    private List<CellQueryInfo> cells = new ArrayList<CellQueryInfo>();
    @ApiModelProperty(value="\u5355\u5143\u683c\u6a21\u7cca\u67e5\u8be2\u53c2\u6570", name="fuzzyValue", required=false)
    private String fuzzyValue;
    @ApiModelProperty(value="\u5206\u9875\u67e5\u8be2\u6761\u4ef6", name="pagerInfo")
    private PagerInfo pagerInfo;
    private String contextTaskKey;
    private String contextEntityId;
    private String contextFormSchemeKey;
    private String contextFilterExpression;

    public JtableContext getContext() {
        return this.context;
    }

    public void setContext(JtableContext context) {
        this.context = context;
    }

    public String getCellKey() {
        return this.cellKey;
    }

    public void setCellKey(String cellKey) {
        this.cellKey = cellKey;
    }

    public String getFilter() {
        return this.filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public List<CellQueryInfo> getCells() {
        return this.cells;
    }

    public void setCells(List<CellQueryInfo> cells) {
        this.cells = cells;
    }

    public String getFuzzyValue() {
        return this.fuzzyValue;
    }

    public void setFuzzyValue(String fuzzyValue) {
        this.fuzzyValue = fuzzyValue;
    }

    public PagerInfo getPagerInfo() {
        return this.pagerInfo;
    }

    public void setPagerInfo(PagerInfo pagerInfo) {
        this.pagerInfo = pagerInfo;
    }

    public String getContextEntityId() {
        return this.contextEntityId;
    }

    public String getContextFilterExpression() {
        return this.contextFilterExpression;
    }
}

