/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.jtable.params.input;

import com.jiuqi.nr.jtable.params.base.JtableContext;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

@ApiModel(value="\u5b9a\u4f4d\u6279\u6ce8\u7c7b\u578b", description="\u5b9a\u4f4d\u6279\u6ce8\u7c7b\u578b")
public class AnnotationTypePositionCondition
implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value="jtable\u73af\u5883\u4fe1\u606f", name="context", required=true)
    private JtableContext context;
    @ApiModelProperty(value="\u6279\u6ce8\u7c7b\u578b\u6807\u8bc6", name="entityKeyData", required=true)
    private String entityKeyData;

    public JtableContext getContext() {
        return this.context;
    }

    public void setContext(JtableContext context) {
        this.context = context;
    }

    public String getEntityKeyData() {
        return this.entityKeyData;
    }

    public void setEntityKeyData(String entityKeyData) {
        this.entityKeyData = entityKeyData;
    }
}

