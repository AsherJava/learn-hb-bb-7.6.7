/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.web.bind.annotation.PostMapping
 */
package com.jiuqi.gcreport.org.api.tool;

import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.vo.tool.OrgCenterParam;
import org.springframework.web.bind.annotation.PostMapping;

public interface GcOrgCenterFactory {
    @PostMapping(value={"gcOrg/GcOrgCenterService"})
    public GcOrgCenterService getInstance(OrgCenterParam var1);
}

