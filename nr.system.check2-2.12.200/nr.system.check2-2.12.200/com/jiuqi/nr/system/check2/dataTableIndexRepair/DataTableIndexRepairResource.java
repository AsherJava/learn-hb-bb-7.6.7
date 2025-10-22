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
package com.jiuqi.nr.system.check2.dataTableIndexRepair;

import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.common.systemcheck.spi.ICheckAction;
import com.jiuqi.nr.common.systemcheck.spi.ICheckResource;
import com.jiuqi.nr.system.check2.dataTableIndexRepair.DataTableIndexRepairAction;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class DataTableIndexRepairResource
implements ICheckResource {
    public static final String DATA_TABLE_INDEX_REPAIR_KEY = "resource-data-table-index-repair";
    public static final String DATA_TABLE_INDEX_REPAIR_TITLE = "\u6570\u636e\u8868\u7d22\u5f15\u4fee\u590d";
    public static final String DATA_TABLE_INDEX_REPAIR_MESSAGE = "\u8fd9\u662f\u6570\u636e\u8868\u7d22\u5f15\u4fee\u590d\uff01";
    @Resource
    private SystemIdentityService identityService;
    @Resource
    private DataTableIndexRepairAction dataTableIndexRepairAction;

    public String getKey() {
        return DATA_TABLE_INDEX_REPAIR_KEY;
    }

    public String getTitle() {
        return DATA_TABLE_INDEX_REPAIR_TITLE;
    }

    public String getGroupKey() {
        return "group-0000-data-management-tool";
    }

    public Double getOrder() {
        return 11.0;
    }

    public String getIcon() {
        return null;
    }

    public String getMessage() {
        return DATA_TABLE_INDEX_REPAIR_MESSAGE;
    }

    public List<String> getTagMessages() {
        ArrayList<String> tagMessages = new ArrayList<String>();
        tagMessages.add(DATA_TABLE_INDEX_REPAIR_TITLE);
        tagMessages.add("\u6570\u636e\u7ba1\u7406\u5de5\u5177");
        return tagMessages;
    }

    public boolean checkAuth(String userID) {
        userID = NpContextHolder.getContext().getUserId();
        return this.identityService.isSystemIdentity(userID);
    }

    public ICheckAction getCheckOption() {
        return this.dataTableIndexRepairAction;
    }
}

