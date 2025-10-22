/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.service.SystemIdentityService
 *  com.jiuqi.np.core.context.NpContextHolder
 *  javax.annotation.PostConstruct
 */
package com.jiuqi.nr.query.dataset.Authority;

import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.core.context.NpContextHolder;
import java.util.Objects;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataSetAuthUtil {
    @Autowired
    private SystemIdentityService systemIdentityService;
    public static DataSetAuthUtil instance = new DataSetAuthUtil();

    @PostConstruct
    public void init() {
        instance = this;
    }

    public boolean isSystemIdentity() {
        String identityId = NpContextHolder.getContext().getIdentityId();
        if (Objects.isNull(identityId)) {
            return false;
        }
        return this.systemIdentityService.isSystemIdentity(identityId);
    }
}

