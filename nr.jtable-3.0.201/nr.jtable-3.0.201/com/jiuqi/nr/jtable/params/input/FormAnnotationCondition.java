/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.annotation.message.CellAnnotationInfo
 *  com.spire.ms.System.Collections.ArrayList
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.jtable.params.input;

import com.jiuqi.nr.annotation.message.CellAnnotationInfo;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.spire.ms.System.Collections.ArrayList;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;

@ApiModel(value="\u4fee\u6539\u6216\u8005\u589e\u52a0\u6279\u6ce8\u7684\u8bf7\u6c42\u4fe1\u606f", description="\u4fee\u6539\u6216\u8005\u589e\u52a0\u6279\u6ce8\u7684\u8bf7\u6c42\u4fe1\u606f")
public class FormAnnotationCondition
implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value="jtable\u73af\u5883\u4fe1\u606f", name="context", required=true)
    private JtableContext context;
    @ApiModelProperty(value="id,\u6709\u66f4\u65b0\uff0c\u65e0\u65b0\u589e", name="id", required=false)
    private String id;
    @ApiModelProperty(value="\u6279\u6ce8\u5185\u5bb9", name="content", required=true)
    private String content;
    @ApiModelProperty(value="\u6279\u6ce8\u7c7b\u578b", name="types")
    private List<String> types;
    @ApiModelProperty(value="\u5355\u5143\u683c\u4fe1\u606f(\u4fee\u6539\u7684\u65f6\u5019\uff0c\u53ef\u4ee5\u4e0d\u4f20\u9012)", name="cells", required=false)
    private List<CellAnnotationInfo> cells = new ArrayList();

    public JtableContext getContext() {
        return this.context;
    }

    public void setContext(JtableContext context) {
        this.context = context;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<CellAnnotationInfo> getCells() {
        return this.cells;
    }

    public void setCells(List<CellAnnotationInfo> cells) {
        this.cells = cells;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getTypes() {
        return this.types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }
}

