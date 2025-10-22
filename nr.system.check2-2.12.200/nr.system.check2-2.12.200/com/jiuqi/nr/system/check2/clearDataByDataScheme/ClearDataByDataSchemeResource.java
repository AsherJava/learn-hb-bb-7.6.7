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
package com.jiuqi.nr.system.check2.clearDataByDataScheme;

import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.common.systemcheck.spi.ICheckAction;
import com.jiuqi.nr.common.systemcheck.spi.ICheckResource;
import com.jiuqi.nr.system.check2.clearDataByDataScheme.ClearDataByDataSchemeAction;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class ClearDataByDataSchemeResource
implements ICheckResource {
    public static final String CLEAR_DATA_BY_DATA_SCHEME_KEY = "resource-clear-data-by-data-scheme";
    public static final String CLEAR_DATA_BY_DATA_SCHEME_TITLE = "\u6e05\u9664\u6570\u636e(\u6309\u6570\u636e\u65b9\u6848)";
    public static final String CLEAR_DATA_BY_DATA_SCHEME_MESSAGE = "\u8fd9\u662f\u6309\u6570\u636e\u65b9\u6848\u6e05\u9664\u6570\u636e(\u6309\u6570\u636e\u65b9\u6848)\uff01";
    @Resource
    private SystemIdentityService identityService;
    @Resource
    private ClearDataByDataSchemeAction clearDataByDataSchemeAction;

    public String getKey() {
        return CLEAR_DATA_BY_DATA_SCHEME_KEY;
    }

    public String getTitle() {
        return CLEAR_DATA_BY_DATA_SCHEME_TITLE;
    }

    public String getGroupKey() {
        return "group-0000-data-management-tool";
    }

    public Double getOrder() {
        return 1.0;
    }

    public String getIcon() {
        return null;
    }

    public String getMessage() {
        return CLEAR_DATA_BY_DATA_SCHEME_MESSAGE;
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
        return this.clearDataByDataSchemeAction;
    }
}

