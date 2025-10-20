/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.user.User
 */
package com.jiuqi.gcreport.common.context;

import com.jiuqi.np.user.User;
import java.util.Date;
import java.util.List;

public interface GcEnvContext {
    public User getUser();

    public Date getLoginTime();

    public String getTenant();

    public String getIdentityId();

    public String getIdentityName();

    public String getIdentityTitle();

    public List<?> getFunction();

    public List<?> getTopfunction();

    public boolean isManage();
}

