/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.service.UserService
 *  com.jiuqi.nvwa.authority.extend.DefaultAuthQueryService
 *  org.apache.commons.lang3.ObjectUtils
 */
package com.jiuqi.gcreport.oauth2.service.impl;

import com.jiuqi.gcreport.oauth2.service.NvwaUiSchemeExtendService;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.service.UserService;
import com.jiuqi.nvwa.authority.extend.DefaultAuthQueryService;
import java.util.Optional;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service(value="nvwaUiSchemeExtendService")
public class NvwaUiSchemeExtendServiceImpl
implements NvwaUiSchemeExtendService {
    @Lazy
    @Autowired
    private UserService<User> userService;
    @Lazy
    @Autowired
    private DefaultAuthQueryService defaultAuthQueryService;

    @Override
    public boolean hasUiSchemeAuthority(String username, String uiSchemeCode) {
        Optional userOptional;
        if (ObjectUtils.isNotEmpty((Object)uiSchemeCode) && (userOptional = this.userService.findByUsername(username)).isPresent()) {
            return this.defaultAuthQueryService.hasAuth("route_privilege_read", ((User)userOptional.get()).getId(), (Object)uiSchemeCode);
        }
        return true;
    }
}

