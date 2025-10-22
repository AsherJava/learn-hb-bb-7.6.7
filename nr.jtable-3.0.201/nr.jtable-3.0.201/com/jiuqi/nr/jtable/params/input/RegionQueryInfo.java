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
import com.jiuqi.nr.jtable.params.input.RegionFilterInfo;
import com.jiuqi.nr.jtable.params.input.RegionRestructureInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="RegionQueryInfo", description="\u533a\u57df\u6570\u636e\u67e5\u8be2\u53c2\u6570")
public class RegionQueryInfo
implements INRContext {
    @ApiModelProperty(value="jtable\u73af\u5883\u4fe1\u606f", name="context", required=true)
    private JtableContext context;
    @ApiModelProperty(value="\u533a\u57dfKey", name="regionKey", required=true)
    private String regionKey;
    @ApiModelProperty(value="\u533a\u57df\u8fc7\u6ee4\u4fe1\u606f", name="filter")
    private RegionFilterInfo filterInfo = new RegionFilterInfo();
    @ApiModelProperty(value="\u5206\u9875\u67e5\u8be2\u6761\u4ef6", name="pagerInfo")
    private PagerInfo pagerInfo;
    @ApiModelProperty(value="\u533a\u57df\u6570\u636e\u91cd\u6784\u6761\u4ef6", name="restructureInfo")
    private RegionRestructureInfo restructureInfo = new RegionRestructureInfo();
    @ApiModelProperty(value="\u6570\u636e\u8131\u654f", name="desensitized")
    private boolean desensitized = false;
    @ApiModelProperty(value="\u5f53\u524d\u9875\u6570\u636e\u4e3a\u7a7a\u65f6\u662f\u5426\u81ea\u52a8\u67e5\u8be2\u4e0a\u4e00\u9875\u6570\u636e", name="queryPreData")
    private boolean queryPreData = true;
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

    public RegionFilterInfo getFilterInfo() {
        return this.filterInfo;
    }

    public void setFilterInfo(RegionFilterInfo filterInfo) {
        this.filterInfo = filterInfo;
    }

    public PagerInfo getPagerInfo() {
        return this.pagerInfo;
    }

    public void setPagerInfo(PagerInfo pagerInfo) {
        this.pagerInfo = pagerInfo;
    }

    public RegionRestructureInfo getRestructureInfo() {
        return this.restructureInfo;
    }

    public void setRestructureInfo(RegionRestructureInfo restructureInfo) {
        this.restructureInfo = restructureInfo;
    }

    public boolean isDesensitized() {
        return this.desensitized;
    }

    public void setDesensitized(boolean desensitized) {
        this.desensitized = desensitized;
    }

    public boolean isQueryPreData() {
        return this.queryPreData;
    }

    public void setQueryPreData(boolean queryPreData) {
        this.queryPreData = queryPreData;
    }

    public String getContextEntityId() {
        return this.contextEntityId;
    }

    public String getContextFilterExpression() {
        return this.contextFilterExpression;
    }
}

