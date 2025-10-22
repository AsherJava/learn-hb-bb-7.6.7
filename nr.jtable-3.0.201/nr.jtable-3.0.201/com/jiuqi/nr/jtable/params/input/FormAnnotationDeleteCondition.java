/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.spire.ms.System.Collections.ArrayList
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.jtable.params.input;

import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.spire.ms.System.Collections.ArrayList;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;

@ApiModel(value="\u6279\u6ce8\u5220\u9664\u76f8\u5173\u8bf7\u6c42", description="\u6279\u6ce8\u5220\u9664\u76f8\u5173\u8bf7\u6c42")
public class FormAnnotationDeleteCondition
implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value="jtable\u73af\u5883\u4fe1\u606f", name="context", required=true)
    private JtableContext context;
    @ApiModelProperty(value="\u4e3b\u952eid\u96c6\u5408", name="ids", required=true)
    private List<String> ids = new ArrayList();

    public JtableContext getContext() {
        return this.context;
    }

    public void setContext(JtableContext context) {
        this.context = context;
    }

    public List<String> getIds() {
        return this.ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }
}

