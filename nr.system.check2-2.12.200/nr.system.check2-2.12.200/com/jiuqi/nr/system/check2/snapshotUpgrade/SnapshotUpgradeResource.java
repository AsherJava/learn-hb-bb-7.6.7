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
package com.jiuqi.nr.system.check2.snapshotUpgrade;

import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.common.systemcheck.spi.ICheckAction;
import com.jiuqi.nr.common.systemcheck.spi.ICheckResource;
import com.jiuqi.nr.system.check2.snapshotUpgrade.SnapshotUpgradeAction;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class SnapshotUpgradeResource
implements ICheckResource {
    public static final String SNAPSHOT_UPGRADE_KEY = "resource-snapshot-upgrade";
    public static final String SNAPSHOT_UPGRADE_TITLE = "\u5feb\u7167\u5347\u7ea7";
    public static final String SNAPSHOT_UPGRADE_MESSAGE = "\u8fd9\u662f\u5feb\u7167\u5347\u7ea7\uff01";
    @Resource
    private SystemIdentityService identityService;
    @Resource
    private SnapshotUpgradeAction snapshotUpgradeAction;

    public String getKey() {
        return SNAPSHOT_UPGRADE_KEY;
    }

    public String getTitle() {
        return SNAPSHOT_UPGRADE_TITLE;
    }

    public String getGroupKey() {
        return "group-0000-state_management";
    }

    public Double getOrder() {
        return 6.0;
    }

    public String getIcon() {
        return null;
    }

    public String getMessage() {
        return SNAPSHOT_UPGRADE_MESSAGE;
    }

    public List<String> getTagMessages() {
        ArrayList<String> tagMessages = new ArrayList<String>();
        tagMessages.add(SNAPSHOT_UPGRADE_TITLE);
        tagMessages.add("\u5176\u5b83");
        return tagMessages;
    }

    public boolean checkAuth(String userID) {
        userID = NpContextHolder.getContext().getUserId();
        return this.identityService.isSystemIdentity(userID);
    }

    public ICheckAction getCheckOption() {
        return this.snapshotUpgradeAction;
    }
}

