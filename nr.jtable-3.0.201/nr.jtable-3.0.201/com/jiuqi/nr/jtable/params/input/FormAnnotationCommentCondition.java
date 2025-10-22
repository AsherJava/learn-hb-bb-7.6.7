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

@ApiModel(value="\u4fee\u6539\u6216\u8005\u589e\u52a0\u6279\u6ce8\u8bc4\u8bba\u7684\u8bf7\u6c42\u4fe1\u606f", description="\u4fee\u6539\u6216\u8005\u589e\u52a0\u6279\u6ce8\u8bc4\u8bba\u7684\u8bf7\u6c42\u4fe1\u606f")
public class FormAnnotationCommentCondition
implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value="jtable\u73af\u5883\u4fe1\u606f", name="context", required=true)
    private JtableContext context;
    @ApiModelProperty(value="\u8bc4\u8bba\u7684\u4e3b\u952e\uff0c\u53ef\u4ee5\u4e3a\u7a7a\uff0c\u4e3a\u7a7a\u65b0\u589e\uff0c\u5426\u5219\u4fee\u6539", name="id", required=false)
    private String id;
    @ApiModelProperty(value="\u5173\u8054\u7684id", name="annotationId", required=true)
    private String annotationId;
    @ApiModelProperty(value="\u8bc4\u8bba\u5185\u5bb9", name="content", required=true)
    private String content;
    @ApiModelProperty(value="\u5bf9\u67d0\u4e2a\u7528\u6237\u7684\u8bc4\u8bba", name="repyId", required=false)
    private String repyId;

    public JtableContext getContext() {
        return this.context;
    }

    public void setContext(JtableContext context) {
        this.context = context;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAnnotationId() {
        return this.annotationId;
    }

    public void setAnnotationId(String annotationId) {
        this.annotationId = annotationId;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRepyId() {
        return this.repyId;
    }

    public void setRepyId(String repyId) {
        this.repyId = repyId;
    }
}

