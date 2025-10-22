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

@ApiModel(value="\u67e5\u8be2\u6279\u6ce8\u7c7b\u578b\u6811\u5f62", description="\u67e5\u8be2\u6279\u6ce8\u7c7b\u578b\u6811\u5f62")
public class AnnotationTypeQueryCondition
implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value="jtable\u73af\u5883\u4fe1\u606f", name="context", required=true)
    private JtableContext context;
    @ApiModelProperty(value="\u6279\u6ce8\u7c7b\u578b\u503c\uff08\u4e0d\u4f20\u67e5\u8be2\u6240\u6709\u6839\u8282\u70b9\uff0c\u4f20\u67e5\u8be2\u8be5\u8282\u70b9\u7684\u6240\u6709\u76f4\u63a5\u4e0b\u7ea7\uff09", name="entityCode")
    private String entityCode;

    public JtableContext getContext() {
        return this.context;
    }

    public void setContext(JtableContext context) {
        this.context = context;
    }

    public String getEntityCode() {
        return this.entityCode;
    }

    public void setEntityCode(String entityCode) {
        this.entityCode = entityCode;
    }
}

