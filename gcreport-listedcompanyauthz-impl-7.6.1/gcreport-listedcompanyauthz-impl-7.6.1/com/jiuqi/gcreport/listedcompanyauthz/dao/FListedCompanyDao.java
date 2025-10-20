/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.listedcompanyauthz.vo.ListedCompanyVO
 */
package com.jiuqi.gcreport.listedcompanyauthz.dao;

import com.jiuqi.gcreport.listedcompanyauthz.domain.ListedCompanySaveParam;
import com.jiuqi.gcreport.listedcompanyauthz.entity.ListedCompanyEO;
import com.jiuqi.gcreport.listedcompanyauthz.vo.ListedCompanyVO;
import java.io.Serializable;
import java.util.List;

public interface FListedCompanyDao {
    public List<ListedCompanyEO> query(ListedCompanyVO var1);

    public ListedCompanyEO get(String var1);

    public void saveAll(ListedCompanySaveParam<ListedCompanyEO> var1);

    public Serializable save(ListedCompanyEO var1);

    public int update(ListedCompanyEO var1);

    public int delete(ListedCompanyEO var1);

    public List<String> queryAllListedCompany();
}

