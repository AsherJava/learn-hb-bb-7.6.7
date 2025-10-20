/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.org.api.tool;

import com.jiuqi.gcreport.org.api.tool.GcOrgTypeVerCacheTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.OrgTypeVO;
import com.jiuqi.gcreport.org.api.vo.OrgVersionVO;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface GcOrgCenterService {
    public int getOrgCodeLength();

    public List<GcOrgCacheVO> getOrgTree();

    public List<GcOrgCacheVO> getOrgTree(String var1);

    public List<GcOrgCacheVO> getOrgChildrenTree(String var1);

    public List<GcOrgCacheVO> getParentOrg(String var1);

    public Map<String, List<GcOrgCacheVO>> getOrgCode2ParentOrgMap();

    public Map<String, List<String>> getOrgId2DirectChildOrgCodesMap();

    public GcOrgCacheVO getOrgByPrimaryID(String var1);

    @Deprecated
    public GcOrgCacheVO getOrgByID(String var1);

    public GcOrgCacheVO getOrgByCode(String var1);

    @Deprecated
    public List<GcOrgCacheVO> listAllOrgByParentId(String var1);

    public List<GcOrgCacheVO> listAllOrgByParentIdContainsSelf(String var1);

    public List<GcOrgCacheVO> listOrgBySearch(String var1);

    public List<GcOrgCacheVO> listOrgBySearch(String var1, String var2);

    public GcOrgTypeVerCacheTool getTypeVerInstance();

    public OrgTypeVO getCurrOrgType();

    public OrgVersionVO getCurrOrgVersion();

    public GcOrgCacheVO getBaseOrgById(String var1);

    public GcOrgCacheVO getBaseOrgByCode(String var1);

    public GcOrgCacheVO getMergeUnitByDifference(String var1);

    public GcOrgCacheVO getUnionUnitByGrade(String var1);

    public GcOrgCacheVO getCommonUnit(GcOrgCacheVO var1, GcOrgCacheVO var2);

    public boolean checkCommonUnit(String var1, String var2, String var3);

    public Map<String, Object> getTableDetail(String var1, String var2);

    public String getDeepestBaseUnitId(String var1);

    public List<GcOrgCacheVO> collectionToTree(Collection<GcOrgCacheVO> var1);
}

