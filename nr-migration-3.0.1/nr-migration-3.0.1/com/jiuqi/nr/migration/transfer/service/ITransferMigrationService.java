/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgVersionDO
 */
package com.jiuqi.nr.migration.transfer.service;

import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgVersionDO;
import java.util.List;
import java.util.Map;

public interface ITransferMigrationService {
    public R importMDORG(List<OrgDO> var1) throws Exception;

    public R importOrgVersion(String var1, List<OrgVersionDO> var2) throws Exception;

    public R importOrgData(String var1, Map<String, List<OrgDO>> var2) throws Exception;

    public R splitOrgVersion(String var1, String var2) throws Exception;

    public R updateOrgData(String var1, Map<String, List<OrgDO>> var2) throws Exception;
}

