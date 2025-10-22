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
import com.jiuqi.nr.jtable.annotation.JtableLog;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.input.RegionQueryInfo;
import com.jiuqi.nr.jtable.uniformity.service.JUniformityService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.HashMap;
import java.util.Map;

@ApiModel(value="ReportDataQueryInfo", description="\u62a5\u8868\u6570\u636e\u67e5\u8be2\u53c2\u6570")
public class ReportDataQueryInfo
extends JtableLog
implements JUniformityService,
INRContext {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value="jtable\u73af\u5883\u4fe1\u606f", name="context", required=true)
    private JtableContext context;
    @ApiModelProperty(value="\u533a\u57df\u6570\u636e\u67e5\u8be2\u53c2\u6570", name="regionQueryInfo")
    private Map<String, RegionQueryInfo> regionQueryInfo = new HashMap<String, RegionQueryInfo>();
    private String contextTaskKey;
    private String contextEntityId;
    private String contextFormSchemeKey;
    private String contextFilterExpression;

    @Override
    public JtableContext getContext() {
        return this.context;
    }

    public void setContext(JtableContext context) {
        this.context = context;
    }

    public Map<String, RegionQueryInfo> getRegionQueryInfo() {
        return this.regionQueryInfo;
    }

    public void setRegionQueryInfo(Map<String, RegionQueryInfo> regionQueryInfo) {
        this.regionQueryInfo = regionQueryInfo;
    }

    public String getContextEntityId() {
        return this.contextEntityId;
    }

    public String getContextFilterExpression() {
        return this.contextFilterExpression;
    }
}

