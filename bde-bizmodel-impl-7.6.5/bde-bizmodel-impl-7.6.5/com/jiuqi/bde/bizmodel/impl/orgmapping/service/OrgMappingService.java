/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.OrgMappingDTO
 */
package com.jiuqi.bde.bizmodel.impl.orgmapping.service;

import com.jiuqi.bde.common.dto.OrgMappingDTO;

public interface OrgMappingService {
    public static final String DEFAULT = "DEFAULT";

    public String getCode();

    public OrgMappingDTO getOrgMapping(String var1);
}

