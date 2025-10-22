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
package com.jiuqi.nr.system.check2.entityViewUpgrade;

import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.common.systemcheck.spi.ICheckAction;
import com.jiuqi.nr.common.systemcheck.spi.ICheckResource;
import com.jiuqi.nr.system.check2.entityViewUpgrade.EntityViewUpgradeAction;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class EntityViewUpgradeResource
implements ICheckResource {
    public static final String ENTITY_VIEW_UPGRADE_KEY = "resource-0000-entity-view-upgrade";
    public static final String ENTITY_VIEW_UPGRADE_TITLE = "\u8fc7\u6ee4\u6a21\u677f\u5347\u7ea7";
    public static final String ENTITY_VIEW_UPGRADE_RESOURCE_MESSAGE = "\u8fd9\u662f\u8fc7\u6ee4\u6a21\u677f\u5347\u7ea7\uff01";
    @Resource
    private EntityViewUpgradeAction entityViewUpgradeAction;
    @Resource
    private SystemIdentityService identityService;

    public String getKey() {
        return ENTITY_VIEW_UPGRADE_KEY;
    }

    public String getTitle() {
        return ENTITY_VIEW_UPGRADE_TITLE;
    }

    public String getGroupKey() {
        return "group-0000-param-tool";
    }

    public Double getOrder() {
        return 8.0;
    }

    public String getIcon() {
        return null;
    }

    public String getMessage() {
        return ENTITY_VIEW_UPGRADE_RESOURCE_MESSAGE;
    }

    public List<String> getTagMessages() {
        ArrayList<String> tagMessages = new ArrayList<String>();
        tagMessages.add("\u8fc7\u6ee4\u6a21\u677f");
        tagMessages.add("\u53c2\u6570\u5de5\u5177");
        return tagMessages;
    }

    public boolean checkAuth(String userID) {
        userID = NpContextHolder.getContext().getUserId();
        return this.identityService.isSystemIdentity(userID);
    }

    public ICheckAction getCheckOption() {
        return this.entityViewUpgradeAction;
    }
}

