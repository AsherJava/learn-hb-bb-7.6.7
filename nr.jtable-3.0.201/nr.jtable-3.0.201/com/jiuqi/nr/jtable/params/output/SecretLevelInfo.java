/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.context.infc.INRContext
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.jtable.params.output;

import com.jiuqi.nr.context.infc.INRContext;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.output.SecretLevelItem;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

@ApiModel(value="SecretLevelInfo", description="\u5bc6\u7ea7\u5c5e\u6027")
public class SecretLevelInfo
implements Serializable,
INRContext {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value="\u5f53\u524d\u73af\u5883", name="jtableContext")
    private JtableContext jtableContext;
    @ApiModelProperty(value="\u5bc6\u7ea7\u9879", name="secretLevelItem")
    private SecretLevelItem secretLevelItem;
    private String contextTaskKey;
    private String contextEntityId;
    private String contextFormSchemeKey;
    private String contextFilterExpression;

    public JtableContext getJtableContext() {
        return this.jtableContext;
    }

    public void setJtableContext(JtableContext jtableContext) {
        this.jtableContext = jtableContext;
    }

    public SecretLevelItem getSecretLevelItem() {
        return this.secretLevelItem;
    }

    public void setSecretLevelItem(SecretLevelItem secretLevelItem) {
        this.secretLevelItem = secretLevelItem;
    }

    public String getContextEntityId() {
        return this.contextEntityId;
    }

    public String getContextFilterExpression() {
        return this.contextFilterExpression;
    }
}

