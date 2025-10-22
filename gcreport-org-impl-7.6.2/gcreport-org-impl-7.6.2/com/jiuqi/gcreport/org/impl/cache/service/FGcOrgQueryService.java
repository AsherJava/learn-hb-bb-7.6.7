/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.OrgTypeVO
 *  com.jiuqi.gcreport.org.api.vo.OrgVersionVO
 */
package com.jiuqi.gcreport.org.impl.cache.service;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.OrgTypeVO;
import com.jiuqi.gcreport.org.api.vo.OrgVersionVO;
import com.jiuqi.gcreport.org.impl.base.GcOrgBaseParam;
import com.jiuqi.gcreport.org.impl.base.GcOrgCodeConfig;
import com.jiuqi.gcreport.org.impl.base.GcOrgParamInterface;
import com.jiuqi.gcreport.org.impl.cache.dao.FGcOrgTypeVersionDao;
import com.jiuqi.gcreport.org.impl.cache.impl.GcOrgParam;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface FGcOrgQueryService<T> {
    public OrgTypeVO getOrgType(String var1);

    public OrgVersionVO getOrgVersion(OrgTypeVO var1, Date var2);

    public List<OrgVersionVO> listOrgVersion(OrgTypeVO var1);

    public int getOrgCodeLength();

    default public GcOrgCodeConfig getOrgCodeConfig() {
        return ((FGcOrgTypeVersionDao)SpringContextUtils.getBean(FGcOrgTypeVersionDao.class)).getGcOrgCodeConfig();
    }

    public List<T> getOrgTree(GcOrgBaseParam var1, String var2);

    public Map<String, Object> getTableDetail(String var1, String var2);

    public T getByCode(GcOrgBaseParam var1, String var2);

    public T getById(GcOrgBaseParam var1, String var2);

    public T getBaseUnit(GcOrgParam var1);

    public List<T> list(GcOrgBaseParam var1, String var2);

    public List<T> list(GcOrgBaseParam var1, String var2, String var3);

    public List<T> listByParam(GcOrgBaseParam var1, GcOrgParamInterface<GcOrgParam> var2);

    public List<T> listSubordinate(GcOrgBaseParam var1, String var2);

    public List<T> listDirectSubordinate(GcOrgBaseParam var1, String var2);

    public List<T> listSuperior(GcOrgBaseParam var1, String var2);

    public List<GcOrgCacheVO> collectionToTree(Collection<GcOrgCacheVO> var1);

    default public void initOrgCache() {
    }
}

