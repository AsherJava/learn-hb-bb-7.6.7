/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.client.onlinePeriod.vo.OnlinePeriodGroupVO
 *  com.jiuqi.dc.base.client.orgcomb.dto.OrgCombGroupDTO
 */
package com.jiuqi.dc.base.impl.onlinePeriod.service;

import com.jiuqi.dc.base.client.onlinePeriod.vo.OnlinePeriodGroupVO;
import com.jiuqi.dc.base.client.orgcomb.dto.OrgCombGroupDTO;
import java.util.List;

public interface OnlinePeriodGroupService {
    public List<OnlinePeriodGroupVO> getAllGroup();

    public List<OrgCombGroupDTO> getUnitGroup();
}

