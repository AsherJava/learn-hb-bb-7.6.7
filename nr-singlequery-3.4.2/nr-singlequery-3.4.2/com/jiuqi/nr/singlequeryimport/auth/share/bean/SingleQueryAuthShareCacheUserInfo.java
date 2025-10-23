/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 */
package com.jiuqi.nr.singlequeryimport.auth.share.bean;

import com.jiuqi.np.definition.facade.IBaseMetaItem;

public class SingleQueryAuthShareCacheUserInfo
implements IBaseMetaItem {
    private static final long serialVersionUID = -8586893756141171632L;
    private String userKey;
    private String userCode;
    private String userName;
    private String identityid;
    private boolean canRead;
    private boolean canEdit;
    private boolean canAuthorize;

    public String getUserKey() {
        return this.userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public String getUserCode() {
        return this.userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIdentityid() {
        return this.identityid;
    }

    public void setIdentityid(String identityid) {
        this.identityid = identityid;
    }

    public boolean getCanRead() {
        return this.canRead;
    }

    public void setCanRead(boolean canRead) {
        this.canRead = canRead;
    }

    public boolean getCanEdit() {
        return this.canEdit;
    }

    public void setCanEdit(boolean canEdit) {
        this.canEdit = canEdit;
    }

    public boolean getCanAuthorize() {
        return this.canAuthorize;
    }

    public void setCanAuthorize(boolean canAuthorize) {
        this.canAuthorize = canAuthorize;
    }

    public SingleQueryAuthShareCacheUserInfo(String userid, String userName, String userCode, String identityid) {
        this.userKey = userid;
        this.userCode = userCode;
        this.userName = userName;
        this.identityid = identityid;
        this.canEdit = false;
        this.canRead = false;
        this.canAuthorize = false;
    }

    public SingleQueryAuthShareCacheUserInfo() {
    }

    public String getKey() {
        return null;
    }

    public String getTitle() {
        return null;
    }

    public String getOrder() {
        return null;
    }

    public String getVersion() {
        return null;
    }

    public String getOwnerLevelAndId() {
        return null;
    }
}

