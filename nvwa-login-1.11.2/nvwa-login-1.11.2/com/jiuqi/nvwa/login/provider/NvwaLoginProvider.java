/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.user.User
 *  com.jiuqi.va.domain.common.R
 */
package com.jiuqi.nvwa.login.provider;

import com.jiuqi.np.user.User;
import com.jiuqi.nvwa.login.domain.NvwaContext;
import com.jiuqi.nvwa.login.domain.NvwaLoginUserDTO;
import com.jiuqi.va.domain.common.R;

public interface NvwaLoginProvider {
    default public R loginBefore(NvwaLoginUserDTO userDTO) {
        return R.ok();
    }

    default public void loginPasswordError(NvwaLoginUserDTO userDTO, User user, R r) {
    }

    default public void loginAfter(User npUser, R loginRs) {
    }

    default public void loginAfter(NvwaLoginUserDTO userDTO, User npUser, R loginRs) {
        this.loginAfter(npUser, loginRs);
    }

    default public void loginContextChangeBefore(NvwaContext nvwaContext) {
    }

    default public void logout(R logoutRs) {
    }
}

