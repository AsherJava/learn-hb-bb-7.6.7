/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.client.orgcomb.vo.OrgCombGroupTreeNodeVO
 *  com.jiuqi.dc.base.client.orgcomb.vo.OrgCombGroupVO
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.dc.base.impl.orgcomb.service;

import com.jiuqi.dc.base.client.orgcomb.vo.OrgCombGroupTreeNodeVO;
import com.jiuqi.dc.base.client.orgcomb.vo.OrgCombGroupVO;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

public interface OrgCombGroupService {
    public List<OrgCombGroupTreeNodeVO> getTreeData();

    public Boolean updateTreeNode(OrgCombGroupVO var1);

    public Boolean deleteTreeNode(OrgCombGroupTreeNodeVO var1);

    public Boolean addTreeNode(String var1);

    public List<String> searchUnitData(List<String> var1);

    public Object checkOrgCombImportData(String var1, String var2, List<Object[]> var3);

    public void exportSchemes(String var1, boolean var2, HttpServletResponse var3);
}

