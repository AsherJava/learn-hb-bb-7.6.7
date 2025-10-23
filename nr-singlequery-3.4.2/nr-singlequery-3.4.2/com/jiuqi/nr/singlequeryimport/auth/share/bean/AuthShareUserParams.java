/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.singlequeryimport.auth.share.bean;

import com.jiuqi.nr.singlequeryimport.auth.share.bean.SingleQueryAuthShareCacheUserInfo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AuthShareUserParams
implements Serializable {
    private static final long serialVersionUID = -2171832767712749878L;
    private String taskKey;
    private String formSchemeKey;
    private List<String> groupKeys = new ArrayList<String>();
    private List<String> modelIds = new ArrayList<String>();
    private List<SingleQueryAuthShareCacheUserInfo> userInfos = new ArrayList<SingleQueryAuthShareCacheUserInfo>();

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public List<String> getGroupKeys() {
        return this.groupKeys;
    }

    public void setGroupKeys(List<String> groupKeys) {
        this.groupKeys = groupKeys;
    }

    public List<String> getModelIds() {
        return this.modelIds;
    }

    public void setModelIds(List<String> modelIds) {
        this.modelIds = modelIds;
    }

    public List<SingleQueryAuthShareCacheUserInfo> getUserInfos() {
        return this.userInfos;
    }

    public void setUserInfos(List<SingleQueryAuthShareCacheUserInfo> userInfos) {
        this.userInfos = userInfos;
    }
}

