/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.organization.api.vo.OrgDataParam
 *  com.jiuqi.gcreport.organization.api.vo.OrgDataVO
 *  com.jiuqi.gcreport.organization.api.vo.tree.ITree
 */
package com.jiuqi.gcreport.organization.impl.service;

import com.jiuqi.gcreport.organization.api.vo.OrgDataParam;
import com.jiuqi.gcreport.organization.api.vo.OrgDataVO;
import com.jiuqi.gcreport.organization.api.vo.tree.ITree;
import com.jiuqi.gcreport.organization.impl.bean.OrgDataDO;
import java.util.List;
import java.util.Map;

public interface GcOrgDataService {
    public List<OrgDataDO> list(OrgDataParam var1);

    public List<OrgDataDO> listDirectChildren(OrgDataParam var1);

    public List<OrgDataDO> listAllChildren(OrgDataParam var1);

    public List<OrgDataDO> listAllChildrenWithSelf(OrgDataParam var1);

    public List<OrgDataDO> listSuperior(OrgDataParam var1);

    public List<OrgDataDO> listBySearch(OrgDataParam var1);

    public Map<String, Object> loadStaticResource(OrgDataParam var1);

    public List<ITree<OrgDataVO>> buildTreeNode(List<OrgDataDO> var1, OrgDataParam var2);

    public List<OrgDataVO> buildListNode(List<OrgDataDO> var1, OrgDataParam var2);

    public OrgDataVO convert(OrgDataDO var1);
}

