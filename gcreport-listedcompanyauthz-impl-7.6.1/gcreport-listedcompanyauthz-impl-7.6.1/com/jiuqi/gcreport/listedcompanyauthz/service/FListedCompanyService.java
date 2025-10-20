/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.listedcompanyauthz.vo.ListedCompanyVO
 */
package com.jiuqi.gcreport.listedcompanyauthz.service;

import com.jiuqi.gcreport.listedcompanyauthz.entity.ListedCompanyEO;
import com.jiuqi.gcreport.listedcompanyauthz.vo.ListedCompanyVO;
import java.util.List;

public interface FListedCompanyService {
    public List<ListedCompanyEO> query(ListedCompanyVO var1);

    public ListedCompanyEO get(String var1);

    public boolean save(List<ListedCompanyVO> var1);

    public int delete(ListedCompanyEO var1);
}

