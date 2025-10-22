/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.OrgToJsonVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.samecontrol.vo.SameChgOrgVersionVO
 */
package com.jiuqi.gcreport.samecontrol.service;

import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.OrgToJsonVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.samecontrol.vo.SameChgOrgVersionVO;
import java.util.List;

public interface VirtualOrgService {
    public List<SameChgOrgVersionVO> listChangeOrg(String var1, String var2);

    public List<SameChgOrgVersionVO> listNoSameCtrlDisposalOrg(String var1, String var2);

    public SameChgOrgVersionVO getChangeOrg(String var1, String var2, String var3);

    public OrgToJsonVO generateVirtualOrg(GcOrgCacheVO var1);

    public String getCommonUnit(String[] var1, String[] var2);

    public YearPeriodObject getPreviousPeriodStr(String var1);
}

