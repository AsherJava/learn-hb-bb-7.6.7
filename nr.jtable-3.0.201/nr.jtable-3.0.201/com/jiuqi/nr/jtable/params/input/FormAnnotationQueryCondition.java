/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.PagerInfo
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.jtable.params.input;

import com.jiuqi.nr.common.params.PagerInfo;
import com.jiuqi.nr.jtable.annotation.JtableLog;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;

@ApiModel(value="\u6279\u6ce8\u67e5\u8be2\u7684\u8bf7\u6c42\u4fe1\u606f", description="\u6279\u6ce8\u67e5\u8be2\u7684\u8bf7\u6c42\u4fe1\u606f")
public class FormAnnotationQueryCondition
extends JtableLog
implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value="jtable\u73af\u5883\u4fe1\u606f formKey \u53ef\u4ee5\u662f\u591a\u4e2a\uff0c\u4ee3\u8868\u67e5\u8be2\u591a\u8868", name="context")
    private JtableContext context;
    @ApiModelProperty(value="\u6279\u6ce8\u7c7b\u578b", name="types")
    private List<String> types;
    @ApiModelProperty(value="\u5206\u9875\u4fe1\u606f \u5982\u679c\u6709\u5c31\u5206\u9875\u67e5\u8be2\uff0c\u53ef\u4ee5\u4e3a\u7a7a\u3002  offset = 0\uff0climit=30  \u4ee3\u8868\u67e5\u8be2 \u7b2c\u4e00\u9875\u6570\u636e", name="pagerInfo")
    private PagerInfo pagerInfo;

    @Override
    public JtableContext getContext() {
        return this.context;
    }

    public void setContext(JtableContext context) {
        this.context = context;
    }

    public List<String> getTypes() {
        return this.types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public PagerInfo getPagerInfo() {
        return this.pagerInfo;
    }

    public void setPagerInfo(PagerInfo pagerInfo) {
        this.pagerInfo = pagerInfo;
    }
}

