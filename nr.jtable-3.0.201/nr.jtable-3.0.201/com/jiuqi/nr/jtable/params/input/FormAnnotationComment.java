/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.jtable.params.input;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

@ApiModel(value="\u4e00\u4e2a\u8bc4\u8bba", description="\u4e00\u4e2a\u8bc4\u8bba")
public class FormAnnotationComment
implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value="\u8bc4\u8bba\u7684\u4e3b\u952e\uff0c\u53ef\u4ee5\u4e3a\u7a7a\uff0c\u4e3a\u7a7a\u65b0\u589e\uff0c\u5426\u5219\u4fee\u6539", name="id", required=false)
    private String id;
    @ApiModelProperty(value="\u5173\u8054\u7684id", name="annotationId", required=true)
    private String annotationId;
    @ApiModelProperty(value="\u8bc4\u8bba\u5185\u5bb9", name="content", required=true)
    private String content;

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
}

