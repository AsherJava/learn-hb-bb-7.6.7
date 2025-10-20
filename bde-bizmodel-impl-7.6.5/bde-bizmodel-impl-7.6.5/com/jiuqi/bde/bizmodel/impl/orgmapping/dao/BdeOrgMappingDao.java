/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.OrgMappingDTO
 */
package com.jiuqi.bde.bizmodel.impl.orgmapping.dao;

import com.jiuqi.bde.common.dto.OrgMappingDTO;

public interface BdeOrgMappingDao {
    public OrgMappingDTO get(String var1);

    public OrgMappingDTO getByAcctOrgCode(String var1);
}

