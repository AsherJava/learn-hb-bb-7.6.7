/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.samecontrol.vo.ChangeOrgCondition
 *  com.jiuqi.gcreport.samecontrol.vo.SameCtrlChgOrgVO
 */
package com.jiuqi.gcreport.samecontrol.service;

import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.samecontrol.entity.SameCtrlChgOrgEO;
import com.jiuqi.gcreport.samecontrol.vo.ChangeOrgCondition;
import com.jiuqi.gcreport.samecontrol.vo.SameCtrlChgOrgVO;
import java.util.List;
import java.util.Set;

public interface SameCtrlChgOrgService {
    public List<SameCtrlChgOrgEO> listSameCtrlChgOrgs(String var1, String var2);

    public Set<String> listOneYearVirtualCodeByVirtualParentCode(String var1, String var2, String var3);

    public Set<String> listOneYearChangedCodeByChangedParentCode(String var1, String var2, String var3);

    public Set<String> listOneYearVirtualCodeBySameParentCode(String var1, String var2, String var3);

    public void autoCreateSameCtrlChgOrg(String var1, String var2);

    public List<SameCtrlChgOrgVO> listSameCtrlChgOrgs(ChangeOrgCondition var1);

    public List<SameCtrlChgOrgEO> listSameCtrlChgOrgsByDisposeOrg(ChangeOrgCondition var1);

    public void addChangedOrg(ChangeOrgCondition var1);

    public void changedCtrlOrgsState(List<String> var1);

    public List<GcOrgCacheVO> getDisposeAndAcquisitionOrg(ChangeOrgCondition var1);

    public List<SameCtrlChgOrgEO> listCurrYearChgOrgsByDisposOrg(String var1, String var2);

    public List<SameCtrlChgOrgEO> listCurrYearChgOrgsByAcquisitionOrg(String var1, String var2);

    public List<SameCtrlChgOrgEO> listCurrYearChgOrgsBySameParentOrg(String var1, String var2);

    public List<SameCtrlChgOrgEO> listAllYearChgOrgsByDisposOrg(String var1, String var2);

    public List<SameCtrlChgOrgEO> listAllYearChgOrgsByAcquisitionOrg(String var1, String var2);

    public List<SameCtrlChgOrgEO> listAllYearChgOrgsBySameParentOrg(String var1, String var2);

    public boolean contasinsChangeOrg(String var1, String var2);

    public List<GcBaseData> listVirtualOrgTypeBaseData();

    public void addManageChangedOrg(ChangeOrgCondition var1);

    public void updateManageChangedOrg(ChangeOrgCondition var1);

    public void deleteManageChangedOrgByMRecid(List<String> var1);

    public void deleteManageChangedOrg(String var1);

    public List<SameCtrlChgOrgVO> listManageChangedOrg(ChangeOrgCondition var1);
}

