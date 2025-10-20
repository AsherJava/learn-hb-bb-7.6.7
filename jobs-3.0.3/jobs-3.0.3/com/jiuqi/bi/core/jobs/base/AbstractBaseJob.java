/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.jobs.base;

import com.jiuqi.bi.core.jobs.base.AbstractBaseJobContext;
import com.jiuqi.bi.core.jobs.base.IJobExecuteAble;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractBaseJob
implements Serializable,
IJobExecuteAble<AbstractBaseJobContext> {
    private Map<String, String> params = new HashMap<String, String>();
    private String userGuid;
    private String userName;

    public Map<String, String> getParams() {
        return this.params;
    }

    public String getUserGuid() {
        return this.userGuid;
    }

    public void setUserGuid(String userGuid) {
        this.userGuid = userGuid;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

