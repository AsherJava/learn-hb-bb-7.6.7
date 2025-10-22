/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.basedata.api.enums.AuthType
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 */
package com.jiuqi.gcreport.consolidatedsystem.service.subject;

import com.jiuqi.gcreport.basedata.api.enums.AuthType;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import java.util.List;

public interface ConsolidatedSubjectBaseDataService {
    public List<GcBaseData> lazyLoadDirectBasedataItemsByParentid(String var1, String var2, AuthType var3, boolean var4);

    public List<GcBaseData> queryBasedataItemsBySearch(String var1, String var2, AuthType var3, boolean var4);

    public GcBaseData queryBasedataByCode(String var1, String var2, boolean var3);

    public List<GcBaseData> queryAllWithSelfItemsByParentid(String var1, String var2, AuthType var3, boolean var4);
}

