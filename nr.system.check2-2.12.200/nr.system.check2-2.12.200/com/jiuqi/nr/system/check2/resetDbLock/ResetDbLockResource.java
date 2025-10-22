/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.service.SystemIdentityService
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.common.systemcheck.spi.ICheckAction
 *  com.jiuqi.nr.common.systemcheck.spi.ICheckResource
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.system.check2.resetDbLock;

import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.common.systemcheck.spi.ICheckAction;
import com.jiuqi.nr.common.systemcheck.spi.ICheckResource;
import com.jiuqi.nr.system.check2.resetDbLock.ResetDbLockAction;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class ResetDbLockResource
implements ICheckResource {
    public static final String RESET_DB_LUCK_KEY = "resource-reset-db-lock";
    public static final String RESET_DB_LUCK_TITLE = "\u4fee\u590d\u53c2\u6570\u8bfb\u5199\u9501";
    public static final String RESET_DB_LUCK_MESSAGE = "\u8fd9\u662f\u4fee\u590d\u53c2\u6570\u8bfb\u5199\u9501\uff01";
    @Resource
    private SystemIdentityService identityService;
    @Resource
    private ResetDbLockAction resetDbLockAction;

    public String getKey() {
        return RESET_DB_LUCK_KEY;
    }

    public String getTitle() {
        return RESET_DB_LUCK_TITLE;
    }

    public String getGroupKey() {
        return "group-0000-caching-tool";
    }

    public Double getOrder() {
        return 10.0;
    }

    public String getIcon() {
        return null;
    }

    public String getMessage() {
        return RESET_DB_LUCK_MESSAGE;
    }

    public List<String> getTagMessages() {
        ArrayList<String> tagMessages = new ArrayList<String>();
        tagMessages.add(RESET_DB_LUCK_TITLE);
        tagMessages.add("\u9501");
        tagMessages.add("\u7f13\u5b58\u5de5\u5177");
        return tagMessages;
    }

    public boolean checkAuth(String userID) {
        userID = NpContextHolder.getContext().getUserId();
        return this.identityService.isSystemIdentity(userID);
    }

    public ICheckAction getCheckOption() {
        return this.resetDbLockAction;
    }
}

