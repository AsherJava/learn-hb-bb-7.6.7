/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.org.api.vo.OrgTypeVO
 *  com.jiuqi.gcreport.org.api.vo.OrgVersionVO
 *  com.jiuqi.va.domain.org.OrgCategoryDO
 *  com.jiuqi.va.domain.org.OrgVersionDO
 *  com.jiuqi.va.domain.org.ZB
 */
package com.jiuqi.gcreport.org.impl.cache.dao;

import com.jiuqi.gcreport.org.api.vo.OrgTypeVO;
import com.jiuqi.gcreport.org.api.vo.OrgVersionVO;
import com.jiuqi.gcreport.org.impl.base.GcOrgCodeConfig;
import com.jiuqi.va.domain.org.OrgCategoryDO;
import com.jiuqi.va.domain.org.OrgVersionDO;
import com.jiuqi.va.domain.org.ZB;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface FGcOrgTypeVersionDao {
    public OrgTypeVO getOrgType(String var1);

    public List<OrgTypeVO> listOrgType();

    public Map<String, ZB> getTypeExtFieldsByName(String var1);

    public boolean addOrgType(OrgCategoryDO var1);

    public boolean updateOrgType(OrgCategoryDO var1);

    public boolean removeOrgType(OrgCategoryDO var1);

    public List<OrgVersionVO> listOrgVersion();

    public List<OrgVersionVO> listOrgVersionByType(OrgTypeVO var1);

    public OrgVersionVO getOrgVersion(OrgTypeVO var1, Date var2);

    public OrgVersionVO getOrgVersion(OrgTypeVO var1, String var2);

    public boolean addOrgVersion(OrgVersionDO var1);

    public boolean updateOrgVersion(OrgVersionDO var1);

    public boolean removeOrgVersion(OrgVersionDO var1);

    public boolean splitOrgVersion(OrgVersionDO var1);

    public int getOrgCodeLength();

    public GcOrgCodeConfig getGcOrgCodeConfig();
}

