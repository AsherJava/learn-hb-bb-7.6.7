/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.context.infc.INRContext
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.jtable.params.input;

import com.jiuqi.nr.context.infc.INRContext;
import com.jiuqi.nr.jtable.annotation.JtableLog;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.input.FindReplaceDataRegionSet;
import com.jiuqi.nr.jtable.uniformity.service.JUniformityService;
import io.swagger.annotations.ApiModelProperty;
import java.util.HashMap;
import java.util.Map;

public class FindReplaceDataCommitSet
extends JtableLog
implements JUniformityService,
INRContext {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value="jtable\u73af\u5883\u4fe1\u606f", name="context", required=true)
    private JtableContext context;
    @ApiModelProperty(value="\u533a\u57df\u6570\u636e\u4fdd\u5b58", name="commitData", required=true)
    private Map<String, FindReplaceDataRegionSet> commitData = new HashMap<String, FindReplaceDataRegionSet>();
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

    public Map<String, FindReplaceDataRegionSet> getCommitData() {
        return this.commitData;
    }

    public void setCommitData(Map<String, FindReplaceDataRegionSet> commitData) {
        this.commitData = commitData;
    }

    public String getContextEntityId() {
        return this.contextEntityId;
    }

    public String getContextFilterExpression() {
        return this.contextFilterExpression;
    }
}

