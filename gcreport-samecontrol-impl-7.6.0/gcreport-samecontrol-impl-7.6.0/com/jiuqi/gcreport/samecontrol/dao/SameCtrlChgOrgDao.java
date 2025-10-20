/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 *  com.jiuqi.gcreport.samecontrol.vo.ChangeOrgCondition
 */
package com.jiuqi.gcreport.samecontrol.dao;

import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import com.jiuqi.gcreport.samecontrol.entity.SameCtrlChgOrgEO;
import com.jiuqi.gcreport.samecontrol.vo.ChangeOrgCondition;
import java.util.Date;
import java.util.List;
import java.util.Set;

public interface SameCtrlChgOrgDao
extends IDbSqlGenericDAO<SameCtrlChgOrgEO, String> {
    public List<SameCtrlChgOrgEO> listSameCtrlChgOrgs(String var1);

    public Set<String> listVirtualCodeByVirtualParentCode(String var1, Date var2, Date var3);

    public Set<String> listChangedCodeByChangedParentCode(String var1, Date var2, Date var3);

    public Set<String> listVirtualCodeBySameParentCode(String var1, Date var2, Date var3);

    public List<SameCtrlChgOrgEO> listSameCtrlChgOrgsByDisposalDate(ChangeOrgCondition var1, Date var2);

    public SameCtrlChgOrgEO listSameCtrlChgOrgsByChangeCodeAndDate(String var1, Date var2);

    public List<SameCtrlChgOrgEO> listSameCtrlChgOrgsByChangedAndDisposeDate(String var1, Date var2, Date var3);

    public List<String> listVirtualCodesByChangeCode(String var1);

    public List<SameCtrlChgOrgEO> listSameCtrlChgOrgsByIds(List<String> var1);

    public List<SameCtrlChgOrgEO> listChgOrgsByDisposOrg(String var1, Date var2, Date var3);

    public List<SameCtrlChgOrgEO> listChgOrgsByAcquisitionOrg(String var1, Date var2, Date var3);

    public List<SameCtrlChgOrgEO> listChgOrgsBySameParentOrg(String var1, Date var2, Date var3);

    public List<SameCtrlChgOrgEO> listChgOrgsChangeOrg(String var1, Date var2, Date var3);

    public List<SameCtrlChgOrgEO> listSameCtrlChgOrgsByDisposeOrg(ChangeOrgCondition var1, Date var2, Date var3);

    public List<SameCtrlChgOrgEO> listSameCtrlChgOrgByMRecid(String var1);

    public void updateManageChangedOrg(ChangeOrgCondition var1);

    public void deleteById(String var1);

    public void deleteByMRecid(List<String> var1);

    public List<SameCtrlChgOrgEO> listSameCtrlChgOrgByParents(ChangeOrgCondition var1);

    public List<SameCtrlChgOrgEO> listSameCtrlChangedCodeByParam(ChangeOrgCondition var1);

    public void disEnable(List<String> var1);

    public void enable(List<String> var1);
}

