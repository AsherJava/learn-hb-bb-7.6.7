/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.OrgMappingDTO
 */
package com.jiuqi.bde.bizmodel.impl.orgmapping.service;

import com.jiuqi.bde.common.dto.OrgMappingDTO;

public interface OrgMappingQueryService {
    public OrgMappingDTO getOrgMappingByAcctOrgCode(String var1);

    public OrgMappingDTO getDatacenterOrgMappingByRpUnitCode(String var1);
}

