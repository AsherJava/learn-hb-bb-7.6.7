/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.listedcompanyauthz.vo.ListedCompanyAuthzVO
 *  org.springframework.dao.DataAccessException
 */
package com.jiuqi.gcreport.listedcompanyauthz.dao;

import com.jiuqi.gcreport.listedcompanyauthz.domain.ListedCompanySaveParam;
import com.jiuqi.gcreport.listedcompanyauthz.entity.ListedCompanyAuthzEO;
import com.jiuqi.gcreport.listedcompanyauthz.vo.ListedCompanyAuthzVO;
import java.util.List;
import org.springframework.dao.DataAccessException;

public interface FListedCompanyAuthzDao {
    public List<ListedCompanyAuthzEO> query(ListedCompanyAuthzVO var1);

    public List<String> getNoAuthzOrgByUser(String var1);

    public ListedCompanyAuthzEO get(String var1);

    public void saveAll(ListedCompanySaveParam<ListedCompanyAuthzEO> var1) throws DataAccessException;

    public int delete(ListedCompanyAuthzEO var1);

    public List<String> queryAuthzOrgByUser(String var1);
}

