/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.org.api.vo.OrgTypeVO
 *  com.jiuqi.gcreport.org.api.vo.OrgVersionVO
 */
package com.jiuqi.gcreport.org.impl.cache.dao;

import com.jiuqi.gcreport.org.api.vo.OrgTypeVO;
import com.jiuqi.gcreport.org.api.vo.OrgVersionVO;
import com.jiuqi.gcreport.org.impl.cache.impl.GcOrgParam;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface FGcOrgQueryDao {
    public List<OrgTypeVO> listOrgType();

    public List<OrgVersionVO> listOrgVersion(OrgTypeVO var1);

    public OrgTypeVO getOrgType(String var1);

    public OrgVersionVO getOrgVersion(OrgTypeVO var1, Date var2);

    public int getOrgCodeLength();

    @Deprecated
    public <T> List<T> getOrgTree(GcOrgParam var1, Class<T> var2);

    public <T> Map<String, Object> getTableDetail(String var1, String var2);

    public <T> T get(GcOrgParam var1, Class<T> var2);

    public <T> List<T> list(GcOrgParam var1, Class<T> var2);

    public <T> List<T> listSuperior(GcOrgParam var1, Class<T> var2);

    public <T> List<T> listSubordinate(GcOrgParam var1, Class<T> var2);

    public <T> List<T> listDirectSubordinate(GcOrgParam var1, Class<T> var2);
}

