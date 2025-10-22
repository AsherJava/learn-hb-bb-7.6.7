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
package com.jiuqi.nr.system.check2.fmdmGuidAutoWidth;

import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.common.systemcheck.spi.ICheckAction;
import com.jiuqi.nr.common.systemcheck.spi.ICheckResource;
import com.jiuqi.nr.system.check2.fmdmGuidAutoWidth.FMGuidAutoWidthAction;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class FMGuidAutoWidthResource
implements ICheckResource {
    public static final String FM_GUID_AUTO_WIDTH_KEY = "fm-guid-auto-width";
    public static final String FM_GUID_AUTO_WIDTH_TITLE = "\u5c01\u9762\u4ee3\u7801\u81ea\u52a8\u5217\u5bbd";
    public static final String FM_GUID_AUTO_WIDTH_MESSAGE = "\u53d6\u6d88\u5c01\u9762\u4ee3\u7801\u9996\u5217\u81ea\u52a8\u5217\u5bbd\uff01";
    @Resource
    private FMGuidAutoWidthAction fmGuidAutoWidthAction;
    @Resource
    private SystemIdentityService identityService;

    public String getKey() {
        return FM_GUID_AUTO_WIDTH_KEY;
    }

    public String getTitle() {
        return FM_GUID_AUTO_WIDTH_TITLE;
    }

    public String getGroupKey() {
        return "group-0000-param-tool";
    }

    public Double getOrder() {
        return 12.0;
    }

    public String getIcon() {
        return null;
    }

    public String getMessage() {
        return FM_GUID_AUTO_WIDTH_MESSAGE;
    }

    public List<String> getTagMessages() {
        ArrayList<String> tagMessages = new ArrayList<String>();
        tagMessages.add("\u5c01\u9762\u4ee3\u7801");
        tagMessages.add("\u81ea\u52a8\u5217\u5bbd");
        tagMessages.add("\u53c2\u6570\u5de5\u5177");
        return tagMessages;
    }

    public boolean checkAuth(String userID) {
        userID = NpContextHolder.getContext().getUserId();
        return this.identityService.isSystemIdentity(userID);
    }

    public ICheckAction getCheckOption() {
        return this.fmGuidAutoWidthAction;
    }
}

