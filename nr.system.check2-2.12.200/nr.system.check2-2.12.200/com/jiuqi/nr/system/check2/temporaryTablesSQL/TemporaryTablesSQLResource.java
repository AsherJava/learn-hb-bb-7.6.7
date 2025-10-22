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
package com.jiuqi.nr.system.check2.temporaryTablesSQL;

import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.common.systemcheck.spi.ICheckAction;
import com.jiuqi.nr.common.systemcheck.spi.ICheckResource;
import com.jiuqi.nr.system.check2.temporaryTablesSQL.TemporaryTablesSQLAction;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class TemporaryTablesSQLResource
implements ICheckResource {
    public static final String TEMPORARY_TABLE_SQL_KEY = "resource-temporary-tables-sql";
    public static final String TEMPORARY_TABLE_SQL_TITLE = "\u4e34\u65f6\u8868\u751f\u6210SQL";
    public static final String TEMPORARY_TABLE_SQL_MESSAGE = "\u8fd9\u662f\u4e34\u65f6\u8868\u751f\u6210SQL\uff01";
    @Resource
    private SystemIdentityService identityService;
    @Resource
    private TemporaryTablesSQLAction temporaryTablesSQLAction;

    public String getKey() {
        return TEMPORARY_TABLE_SQL_KEY;
    }

    public String getTitle() {
        return TEMPORARY_TABLE_SQL_TITLE;
    }

    public String getGroupKey() {
        return "group-0000-state_management";
    }

    public Double getOrder() {
        return 7.0;
    }

    public String getIcon() {
        return null;
    }

    public String getMessage() {
        return TEMPORARY_TABLE_SQL_MESSAGE;
    }

    public List<String> getTagMessages() {
        ArrayList<String> tagMessages = new ArrayList<String>();
        tagMessages.add(TEMPORARY_TABLE_SQL_TITLE);
        tagMessages.add("\u5176\u5b83");
        return tagMessages;
    }

    public boolean checkAuth(String userID) {
        userID = NpContextHolder.getContext().getUserId();
        return this.identityService.isSystemIdentity(userID);
    }

    public ICheckAction getCheckOption() {
        return this.temporaryTablesSQLAction;
    }
}

