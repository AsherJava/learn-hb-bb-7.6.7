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
package com.jiuqi.nr.system.check2.refreshStatus;

import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.common.systemcheck.spi.ICheckAction;
import com.jiuqi.nr.common.systemcheck.spi.ICheckResource;
import com.jiuqi.nr.system.check2.refreshStatus.RefreshStatusAction;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class RefreshStatusResource
implements ICheckResource {
    public static final String REFRESH_STATUS_KEY = "resource-refresh-status";
    public static final String REFRESH_STATUS_TITLE = "\u5237\u65b0\u72b6\u6001";
    public static final String REFRESH_STATUS_MESSAGE = "\u8fd9\u662f\u5237\u65b0\u72b6\u6001\uff01";
    @Resource
    private SystemIdentityService identityService;
    @Resource
    private RefreshStatusAction refreshStatusAction;

    public String getKey() {
        return REFRESH_STATUS_KEY;
    }

    public String getTitle() {
        return REFRESH_STATUS_TITLE;
    }

    public String getGroupKey() {
        return "group-0000-state_management";
    }

    public Double getOrder() {
        return 2.0;
    }

    public String getIcon() {
        return null;
    }

    public String getMessage() {
        return REFRESH_STATUS_MESSAGE;
    }

    public List<String> getTagMessages() {
        ArrayList<String> tagMessages = new ArrayList<String>();
        tagMessages.add("\u6e05\u9664\u6570\u636e");
        tagMessages.add("\u6570\u636e\u65b9\u6848");
        tagMessages.add("\u6570\u636e\u7ba1\u7406\u5de5\u5177");
        return tagMessages;
    }

    public boolean checkAuth(String userID) {
        userID = NpContextHolder.getContext().getUserId();
        return this.identityService.isSystemIdentity(userID);
    }

    public ICheckAction getCheckOption() {
        return this.refreshStatusAction;
    }
}

