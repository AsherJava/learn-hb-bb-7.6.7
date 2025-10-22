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
package com.jiuqi.nr.system.check2.zbScheme;

import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.common.systemcheck.spi.ICheckAction;
import com.jiuqi.nr.common.systemcheck.spi.ICheckResource;
import com.jiuqi.nr.system.check2.zbScheme.ZbSchemeReverseAction;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ZbSchemeReverseResource
implements ICheckResource {
    public static final String RESOURCE_KEY = ZbSchemeReverseResource.class.getName();
    @Resource
    private SystemIdentityService identityService;
    @Autowired
    private ZbSchemeReverseAction zbSchemeReverseAction;

    public String getKey() {
        return RESOURCE_KEY;
    }

    public String getTitle() {
        return "\u6307\u6807\u4f53\u7cfb\u751f\u6210\u5de5\u5177";
    }

    public String getGroupKey() {
        return "group-0000-data-management-tool";
    }

    public Double getOrder() {
        return 12.0;
    }

    public String getIcon() {
        return null;
    }

    public String getMessage() {
        return "\u6570\u636e\u65b9\u6848\u9006\u5411\u751f\u6210\u6307\u6807\u4f53\u7cfb";
    }

    public List<String> getTagMessages() {
        ArrayList<String> tagMessages = new ArrayList<String>();
        tagMessages.add("\u6570\u636e\u65b9\u6848");
        tagMessages.add("\u6307\u6807\u4f53\u7cfb");
        tagMessages.add("\u9006\u5411\u751f\u6210");
        return tagMessages;
    }

    public boolean checkAuth(String userID) {
        String user = NpContextHolder.getContext().getUserId();
        return this.identityService.isSystemIdentity(user);
    }

    public ICheckAction getCheckOption() {
        return this.zbSchemeReverseAction;
    }
}

