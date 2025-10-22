/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.spire.ms.System.Collections.ArrayList
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.jtable.params.output;

import com.jiuqi.nr.jtable.params.output.CellAnnotationComment;
import com.jiuqi.nr.jtable.params.output.CellAnnotationRelation;
import com.spire.ms.System.Collections.ArrayList;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;

@ApiModel(value="\u6279\u6ce8\u4fe1\u606f", description="\u6279\u6ce8\u4fe1\u606f")
public class CellAnnotationContent
implements Serializable,
Comparable<CellAnnotationContent> {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value="\u6279\u6ce8\u7684\u4e3b\u952e", name="id")
    private String id;
    @ApiModelProperty(value="\u5185\u5bb9", name="content")
    private String content;
    @ApiModelProperty(value="\u4fee\u6539\u4eba", name="userName")
    private String userName;
    @ApiModelProperty(value="\u4fee\u6539\u65f6\u95f4", name="date")
    private long date;
    @ApiModelProperty(value="\u6279\u6ce8\u7684\u5173\u8054\u4fe1\u606f", name="relations")
    private List<CellAnnotationRelation> relations = new ArrayList();
    @ApiModelProperty(value="\u8bc4\u8bba\u4fe1\u606f", name="comments")
    private List<CellAnnotationComment> comments = new ArrayList();
    @ApiModelProperty(value="\u5f53\u524d\u7528\u6237\u662f\u5426\u53ef\u4ee5\u4fee\u6539", name="canEdit")
    private boolean canEdit;
    @ApiModelProperty(value="\u5f53\u524d\u7528\u6237\u662f\u5426\u53ef\u4ee5\u5220\u9664", name="canDelete")
    private boolean canDelete;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getDate() {
        return this.date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public List<CellAnnotationComment> getComments() {
        return this.comments;
    }

    public void setComments(List<CellAnnotationComment> comments) {
        this.comments = comments;
    }

    public List<CellAnnotationRelation> getRelations() {
        return this.relations;
    }

    public void setRelations(List<CellAnnotationRelation> relations) {
        this.relations = relations;
    }

    public boolean isCanEdit() {
        return this.canEdit;
    }

    public void setCanEdit(boolean canEdit) {
        this.canEdit = canEdit;
    }

    public boolean isCanDelete() {
        return this.canDelete;
    }

    public void setCanDelete(boolean canDelete) {
        this.canDelete = canDelete;
    }

    @Override
    public int compareTo(CellAnnotationContent o) {
        return o.getDate() > this.date ? 1 : -1;
    }
}

