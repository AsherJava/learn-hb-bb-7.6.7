/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.web.bind.annotation.PostMapping
 */
package com.jiuqi.dc.base.client.onlinePeriod;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.dc.base.client.onlinePeriod.vo.OnlinePeriodGroupVO;
import com.jiuqi.dc.base.client.orgcomb.dto.OrgCombGroupDTO;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;

public interface OnlinePeriodGroupClient {
    public static final String API_BASE_PATH = "/api/datacenter/v1/base/period";

    @PostMapping(value={"/api/datacenter/v1/base/period/group"})
    public BusinessResponseEntity<List<OnlinePeriodGroupVO>> getAllGroup();

    @PostMapping(value={"/api/datacenter/v1/base/period/unitgroup"})
    public BusinessResponseEntity<List<OrgCombGroupDTO>> getUnitGroup();
}

