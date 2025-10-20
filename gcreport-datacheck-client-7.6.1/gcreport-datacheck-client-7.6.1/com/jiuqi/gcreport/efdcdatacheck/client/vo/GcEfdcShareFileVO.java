/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.workbench.share.bean.UserBase
 */
package com.jiuqi.gcreport.efdcdatacheck.client.vo;

import com.jiuqi.nvwa.workbench.share.bean.UserBase;
import java.util.List;

public class GcEfdcShareFileVO {
    private List<UserBase> users;
    private List<String> fileIds;

    public List<UserBase> getUsers() {
        return this.users;
    }

    public void setUsers(List<UserBase> users) {
        this.users = users;
    }

    public List<String> getFileIds() {
        return this.fileIds;
    }

    public void setFileIds(List<String> fileIds) {
        this.fileIds = fileIds;
    }
}

