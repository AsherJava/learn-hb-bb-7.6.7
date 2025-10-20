/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.user.User
 *  com.jiuqi.va.domain.common.R
 */
package com.jiuqi.nvwa.login.service;

import com.jiuqi.np.user.User;
import com.jiuqi.nvwa.login.domain.NvwaLoginUserDTO;
import com.jiuqi.va.domain.common.R;
import java.util.Map;

public interface NvwaLoginService {
    public R tryLogin(NvwaLoginUserDTO var1, boolean var2);

    public User validate(NvwaLoginUserDTO var1, R var2) throws Exception;

    public R getLoginContext();

    public R changeLoginContext(NvwaLoginUserDTO var1);

    public R refreshLoginContext();

    public R logout();

    public long getLoginSessionCount(String var1);

    public R forceLogout(String var1, String var2);

    public R forceLogout(String var1);

    public NvwaLoginUserDTO checkChangeLoginContext(Map<String, Object> var1);
}

