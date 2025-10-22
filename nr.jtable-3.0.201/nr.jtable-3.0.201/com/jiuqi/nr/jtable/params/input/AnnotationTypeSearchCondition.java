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

@ApiModel(value="\u641c\u7d22\u6279\u6ce8\u7c7b\u578b", description="\u641c\u7d22\u6279\u6ce8\u7c7b\u578b")
public class AnnotationTypeSearchCondition
implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value="jtable\u73af\u5883\u4fe1\u606f", name="context", required=true)
    private JtableContext context;
    @ApiModelProperty(value="\u641c\u7d22\u4fe1\u606f\uff08\u5148\u5339\u914dname\u518d\u5339\u914dcode\uff09", name="searchInfo")
    private String searchInfo;

    public JtableContext getContext() {
        return this.context;
    }

    public void setContext(JtableContext context) {
        this.context = context;
    }

    public String getSearchInfo() {
        return this.searchInfo;
    }

    public void setSearchInfo(String searchInfo) {
        this.searchInfo = searchInfo;
    }
}

