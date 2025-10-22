/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.context.infc.INRContext
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.jtable.params.input;

import com.jiuqi.nr.context.infc.INRContext;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApiModel(value="PasteFormatDataInfo", description="\u683c\u5f0f\u5316\u6570\u636e\u6761\u4ef6")
public class PasteFormatDataInfo
implements INRContext {
    @ApiModelProperty(value="jtable\u73af\u5883\u4fe1\u606f", name="context", required=true)
    private JtableContext context;
    @ApiModelProperty(value="\u683c\u5f0f\u5316\u6570\u636e\u4fe1\u606f\uff08key:\u94fe\u63a5key, value: \u683c\u5f0f\u5316\u6570\u636e\u5217\u8868\uff09", name="dataLinkMaps", required=true)
    private Map<String, List<String>> dataLinkMaps = new HashMap<String, List<String>>();
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

    public Map<String, List<String>> getDataLinkMaps() {
        return this.dataLinkMaps;
    }

    public void setDataLinkMaps(Map<String, List<String>> dataLinkMaps) {
        this.dataLinkMaps = dataLinkMaps;
    }

    public String getContextEntityId() {
        return this.contextEntityId;
    }

    public String getContextFilterExpression() {
        return this.contextFilterExpression;
    }
}

