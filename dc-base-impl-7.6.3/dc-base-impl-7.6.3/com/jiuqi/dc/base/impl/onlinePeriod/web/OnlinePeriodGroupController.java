/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.dc.base.client.onlinePeriod.OnlinePeriodGroupClient
 *  com.jiuqi.dc.base.client.onlinePeriod.vo.OnlinePeriodGroupVO
 *  com.jiuqi.dc.base.client.orgcomb.dto.OrgCombGroupDTO
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.dc.base.impl.onlinePeriod.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.dc.base.client.onlinePeriod.OnlinePeriodGroupClient;
import com.jiuqi.dc.base.client.onlinePeriod.vo.OnlinePeriodGroupVO;
import com.jiuqi.dc.base.client.orgcomb.dto.OrgCombGroupDTO;
import com.jiuqi.dc.base.impl.onlinePeriod.service.OnlinePeriodGroupService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OnlinePeriodGroupController
implements OnlinePeriodGroupClient {
    @Autowired
    private OnlinePeriodGroupService onlinePeriodGroupService;

    public BusinessResponseEntity<List<OnlinePeriodGroupVO>> getAllGroup() {
        return BusinessResponseEntity.ok(this.onlinePeriodGroupService.getAllGroup());
    }

    public BusinessResponseEntity<List<OrgCombGroupDTO>> getUnitGroup() {
        return BusinessResponseEntity.ok(this.onlinePeriodGroupService.getUnitGroup());
    }
}

