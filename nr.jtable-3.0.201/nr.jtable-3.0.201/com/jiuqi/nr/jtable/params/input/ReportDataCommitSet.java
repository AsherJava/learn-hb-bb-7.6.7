/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.nr.context.infc.INRContext
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.jtable.params.input;

import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.nr.context.infc.INRContext;
import com.jiuqi.nr.jtable.annotation.JtableLog;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.input.RegionDataCommitSet;
import com.jiuqi.nr.jtable.uniformity.service.JUniformityService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApiModel(value="ReportDataCommitSet", description="\u62a5\u8868\u6570\u636e\u4fdd\u5b58")
public class ReportDataCommitSet
extends JtableLog
implements JUniformityService,
INRContext {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value="jtable\u73af\u5883\u4fe1\u606f", name="context", required=true)
    private JtableContext context;
    @ApiModelProperty(value="\u533a\u57df\u6570\u636e\u4fdd\u5b58", name="commitData", required=true)
    private Map<String, RegionDataCommitSet> commitData = new HashMap<String, RegionDataCommitSet>();
    @ApiModelProperty(value="\u5355\u4f4d\u6811\u4e0a\u65b0\u589e\u5c01\u9762\u4ee3\u7801", name="unitAdd", required=true)
    private boolean unitAdd;
    @ApiModelProperty(value="\u5c01\u9762\u4ee3\u7801\u5c5e\u6027", name="fmdmAttributes", required=false)
    private Map<String, Object> fmdmAttributes;
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

    public Map<String, RegionDataCommitSet> getCommitData() {
        return this.commitData;
    }

    public void setCommitData(Map<String, RegionDataCommitSet> commitData) {
        this.commitData = commitData;
    }

    public boolean isUnitAdd() {
        return this.unitAdd;
    }

    public void setUnitAdd(boolean unitAdd) {
        this.unitAdd = unitAdd;
    }

    public Map<String, Object> getFmdmAttributes() {
        return this.fmdmAttributes;
    }

    public void setFmdmAttributes(Map<String, Object> fmdmAttributes) {
        this.fmdmAttributes = fmdmAttributes;
    }

    public String getContextEntityId() {
        return this.contextEntityId;
    }

    public String getContextFilterExpression() {
        return this.contextFilterExpression;
    }

    public List<Variable> getVariables() {
        return this.context.getVariables();
    }
}

