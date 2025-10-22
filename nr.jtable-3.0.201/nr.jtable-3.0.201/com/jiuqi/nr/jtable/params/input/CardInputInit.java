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
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

@ApiModel(value="CardInputInit", description="\u5361\u7247\u5f55\u5165\u7684\u521d\u59cb\u5316\u4fe1\u606f")
public class CardInputInit
implements Serializable,
INRContext {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value="jtable\u73af\u5883\u4fe1\u606f", name="context", required=true)
    private JtableContext context;
    @ApiModelProperty(value="\u533a\u57dfkey", name="regionKey", required=true)
    private String regionKey;
    @ApiModelProperty(value="\u5f53\u524d\u884cid", name="rowId", required=true)
    private String rowId;
    @ApiModelProperty(value="\u504f\u79fb\u91cf", name="offset")
    private int offset;
    @ApiModelProperty(value="\u53ea\u8fd4\u56de\u884c\u6570\u636e", name="onlyRowData")
    private boolean onlyRowData;
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

    public String getRegionKey() {
        return this.regionKey;
    }

    public void setRegionKey(String regionKey) {
        this.regionKey = regionKey;
    }

    public String getRowId() {
        return this.rowId;
    }

    public void setRowId(String rowId) {
        this.rowId = rowId;
    }

    public int getOffset() {
        return this.offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public boolean isOnlyRowData() {
        return this.onlyRowData;
    }

    public void setOnlyRowData(boolean onlyRowData) {
        this.onlyRowData = onlyRowData;
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

