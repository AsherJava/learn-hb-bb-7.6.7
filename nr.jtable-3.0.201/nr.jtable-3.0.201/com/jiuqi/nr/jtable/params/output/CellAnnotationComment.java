/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.jtable.params.output;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

@ApiModel(value="\u8bc4\u8bba\u4fe1\u606f", description="\u8bc4\u8bba\u4fe1\u606f")
public class CellAnnotationComment
implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value="\u8bc4\u8bbaid", name="id")
    private String id;
    @ApiModelProperty(value="\u8bc4\u8bba\u5185\u5bb9", name="content")
    private String content;
    @ApiModelProperty(value="\u8bc4\u8bba\u4ebaId", name="userId")
    private String userId;
    @ApiModelProperty(value="\u8bc4\u8bba\u4eba", name="userName")
    private String userName;
    @ApiModelProperty(value="\u88ab\u56de\u590d\u7684\u4eba", name="repyUserName", required=false)
    private String repyUserName;
    @ApiModelProperty(value="\u4fee\u6539\u65f6\u95f4", name="date")
    private long date;
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

    public String getRepyUserName() {
        return this.repyUserName;
    }

    public void setRepyUserName(String repyUserName) {
        this.repyUserName = repyUserName;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
}

