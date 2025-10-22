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
package com.jiuqi.nr.system.check2.unitInformationTableUpgrade;

import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.common.systemcheck.spi.ICheckAction;
import com.jiuqi.nr.common.systemcheck.spi.ICheckResource;
import com.jiuqi.nr.system.check2.unitInformationTableUpgrade.UnitInformationTableUpgradeAction;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class UnitInformationTableUpgradeResource
implements ICheckResource {
    public static final String UNIT_INFORMATION_TABLE_UPGRADE_KEY = "resource-unit-information-table-upgrade";
    public static final String UNIT_INFORMATION_TABLE_UPGRADE_TITLE = "\u5355\u4f4d\u4fe1\u606f\u8868\u5347\u7ea7";
    public static final String UNIT_INFORMATION_TABLE_UPGRADE__MESSAGE = "\u8fd9\u662f\u5355\u4f4d\u4fe1\u606f\u8868\u5347\u7ea7\uff01";
    @Resource
    private SystemIdentityService identityService;
    @Resource
    private UnitInformationTableUpgradeAction unitInformationTableUpgradeAction;

    public String getKey() {
        return UNIT_INFORMATION_TABLE_UPGRADE_KEY;
    }

    public String getTitle() {
        return UNIT_INFORMATION_TABLE_UPGRADE_TITLE;
    }

    public String getGroupKey() {
        return "group-0000-param-tool";
    }

    public Double getOrder() {
        return 9.0;
    }

    public String getIcon() {
        return null;
    }

    public String getMessage() {
        return UNIT_INFORMATION_TABLE_UPGRADE__MESSAGE;
    }

    public List<String> getTagMessages() {
        ArrayList<String> tagMessages = new ArrayList<String>();
        tagMessages.add(UNIT_INFORMATION_TABLE_UPGRADE_TITLE);
        tagMessages.add("\u53c2\u6570\u5de5\u5177");
        return tagMessages;
    }

    public boolean checkAuth(String userID) {
        userID = NpContextHolder.getContext().getUserId();
        return this.identityService.isSystemIdentity(userID);
    }

    public ICheckAction getCheckOption() {
        return this.unitInformationTableUpgradeAction;
    }
}

