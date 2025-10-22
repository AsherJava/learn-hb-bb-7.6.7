/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.org.api.vo.OrgTypeVO
 *  com.jiuqi.va.domain.common.R
 */
package com.jiuqi.gcreport.org.impl.fieldManager.service;

import com.jiuqi.gcreport.org.api.vo.OrgTypeVO;
import com.jiuqi.va.domain.common.R;

public interface GcOrgTypeService {
    public R copyOrgType(OrgTypeVO var1, String var2, String var3);

    public void addOrgTypeBaseData(OrgTypeVO var1);

    public void updateOrgTypeBaseData(OrgTypeVO var1);
}

