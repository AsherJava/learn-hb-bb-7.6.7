/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.client.orgcomb.vo.OrgCombDefineListVO
 *  com.jiuqi.dc.base.client.orgcomb.vo.OrgCombDefineVO
 *  com.jiuqi.dc.base.client.orgcomb.vo.OrgCombGroupVO
 *  com.jiuqi.va.domain.common.PageVO
 */
package com.jiuqi.dc.base.impl.orgcomb.service;

import com.jiuqi.dc.base.client.orgcomb.vo.OrgCombDefineListVO;
import com.jiuqi.dc.base.client.orgcomb.vo.OrgCombDefineVO;
import com.jiuqi.dc.base.client.orgcomb.vo.OrgCombGroupVO;
import com.jiuqi.dc.base.impl.orgcomb.dto.OrgCombDefineDTO;
import com.jiuqi.va.domain.common.PageVO;
import java.util.List;

public interface OrgCombDefineService {
    public OrgCombDefineVO add(OrgCombDefineVO var1);

    public OrgCombDefineVO update(OrgCombDefineVO var1);

    public void delete(String var1);

    public PageVO<OrgCombDefineListVO> listData(OrgCombDefineDTO var1);

    public List<OrgCombDefineVO> listData();

    public OrgCombDefineVO findData(String var1);

    public List<String> findOrgCombCodes(String var1);

    public Boolean updateGroupName(OrgCombGroupVO var1);
}

