/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 */
package com.jiuqi.nr.singlequeryimport.auth.share.service;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.singlequeryimport.auth.FinalaccountQueryAuthResourceType;
import com.jiuqi.nr.singlequeryimport.auth.share.bean.AuthShareParams;
import com.jiuqi.nr.singlequeryimport.auth.share.bean.AuthShareUserParams;
import com.jiuqi.nr.singlequeryimport.auth.share.bean.ReturnObject;
import com.jiuqi.nr.singlequeryimport.auth.share.bean.SingleQueryAuthShareCacheUserInfo;
import com.jiuqi.nr.singlequeryimport.bean.QueryModelNode;
import java.util.List;

public interface AuthShareService {
    public ReturnObject getChildUsers(AuthShareParams var1);

    public List<QueryModelNode> getModelGroupWithAuth(String var1) throws Exception;

    public List<QueryModelNode> getModelWithAuth(String var1, String var2) throws Exception;

    public boolean setUsersWithModelAuth(AuthShareUserParams var1);

    public boolean batchSetUsersWithModelAuth(AuthShareUserParams var1, AsyncTaskMonitor var2);

    public SingleQueryAuthShareCacheUserInfo getCurUserAuthByModals(String var1, List<String> var2);

    public SingleQueryAuthShareCacheUserInfo getCurUserAuthByGroup(String var1, List<String> var2);

    public boolean addCurUserPrivilege(String var1, FinalaccountQueryAuthResourceType var2);

    public boolean addCurUserGroupPrivilege(String var1, String var2, FinalaccountQueryAuthResourceType var3);

    public boolean addCurUserGroupReadPrivilege(String var1, String var2, FinalaccountQueryAuthResourceType var3);
}

