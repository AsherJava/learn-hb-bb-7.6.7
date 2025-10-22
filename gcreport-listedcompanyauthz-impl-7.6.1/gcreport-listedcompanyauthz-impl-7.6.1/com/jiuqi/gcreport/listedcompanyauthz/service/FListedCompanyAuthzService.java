/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.listedcompanyauthz.vo.ListedCompanyAuthzVO
 *  com.jiuqi.gcreport.listedcompanyauthz.vo.param.UserQueryParam
 */
package com.jiuqi.gcreport.listedcompanyauthz.service;

import com.jiuqi.gcreport.listedcompanyauthz.entity.ListedCompanyAuthzEO;
import com.jiuqi.gcreport.listedcompanyauthz.vo.ListedCompanyAuthzVO;
import com.jiuqi.gcreport.listedcompanyauthz.vo.param.UserQueryParam;
import java.util.List;

public interface FListedCompanyAuthzService {
    public List<ListedCompanyAuthzVO> query(ListedCompanyAuthzVO var1);

    public List<ListedCompanyAuthzVO> queryUserByOrgs(UserQueryParam var1);

    public ListedCompanyAuthzEO get(String var1);

    public int delete(ListedCompanyAuthzEO var1);

    public boolean save(List<ListedCompanyAuthzVO> var1);

    public boolean checkPenetratePermission(String var1, String var2);
}

