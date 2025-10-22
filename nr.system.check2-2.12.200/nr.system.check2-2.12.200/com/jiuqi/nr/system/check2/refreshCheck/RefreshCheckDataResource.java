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
package com.jiuqi.nr.system.check2.refreshCheck;

import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.common.systemcheck.spi.ICheckAction;
import com.jiuqi.nr.common.systemcheck.spi.ICheckResource;
import com.jiuqi.nr.system.check2.refreshCheck.RefreshCheckDataAction;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class RefreshCheckDataResource
implements ICheckResource {
    public static final String REFRESH_CHECK_DATA_KEY = "resource-0000-0000-refresh-check-data";
    public static final String REFRESH_CHECK_DATA_TITLE = "\u5237\u65b0\u51fa\u9519\u8bf4\u660e";
    public static final String REFRESH_CHECK_RESOURCE_MESSAGE = "\u8fd9\u662f\u53c2\u6570\u68c0\u67e5\uff01";
    @Resource
    private SystemIdentityService identityService;
    @Resource
    private RefreshCheckDataAction refreshCheckDataOption;

    public String getKey() {
        return REFRESH_CHECK_DATA_KEY;
    }

    public String getTitle() {
        return REFRESH_CHECK_DATA_TITLE;
    }

    public String getGroupKey() {
        return "group-0000-other_group";
    }

    public Double getOrder() {
        return 4.0;
    }

    public String getIcon() {
        return null;
    }

    public String getMessage() {
        return REFRESH_CHECK_RESOURCE_MESSAGE;
    }

    public List<String> getTagMessages() {
        ArrayList<String> tagMessages = new ArrayList<String>();
        tagMessages.add(REFRESH_CHECK_DATA_TITLE);
        tagMessages.add("\u51fa\u9519\u8bf4\u660e");
        tagMessages.add("\u5176\u5b83");
        return tagMessages;
    }

    public boolean checkAuth(String userID) {
        userID = NpContextHolder.getContext().getUserId();
        return this.identityService.isSystemIdentity(userID);
    }

    public ICheckAction getCheckOption() {
        return this.refreshCheckDataOption;
    }
}

