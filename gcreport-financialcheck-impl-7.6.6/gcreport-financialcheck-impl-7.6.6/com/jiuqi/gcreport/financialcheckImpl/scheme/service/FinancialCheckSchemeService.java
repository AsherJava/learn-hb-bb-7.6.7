/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.financialcheckapi.scheme.vo.CheckBusinessRoleOptionVO
 *  com.jiuqi.gcreport.financialcheckapi.scheme.vo.FinancialCheckGroupVO
 *  com.jiuqi.gcreport.financialcheckapi.scheme.vo.FinancialCheckMatchSchemeVO
 *  com.jiuqi.gcreport.financialcheckapi.scheme.vo.FinancialCheckSchemeBaseDataVO
 *  com.jiuqi.gcreport.financialcheckapi.scheme.vo.FinancialCheckSchemeInitDataVO
 *  com.jiuqi.gcreport.financialcheckapi.scheme.vo.FinancialCheckSchemeNumVO
 *  com.jiuqi.gcreport.financialcheckapi.scheme.vo.FinancialCheckSchemeTreeVO
 *  com.jiuqi.gcreport.financialcheckapi.scheme.vo.FinancialCheckSchemeVO
 *  com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO
 *  com.jiuqi.gcreport.financialcheckcore.scheme.entity.FinancialCheckSchemeEO
 */
package com.jiuqi.gcreport.financialcheckImpl.scheme.service;

import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.financialcheckapi.scheme.vo.CheckBusinessRoleOptionVO;
import com.jiuqi.gcreport.financialcheckapi.scheme.vo.FinancialCheckGroupVO;
import com.jiuqi.gcreport.financialcheckapi.scheme.vo.FinancialCheckMatchSchemeVO;
import com.jiuqi.gcreport.financialcheckapi.scheme.vo.FinancialCheckSchemeBaseDataVO;
import com.jiuqi.gcreport.financialcheckapi.scheme.vo.FinancialCheckSchemeInitDataVO;
import com.jiuqi.gcreport.financialcheckapi.scheme.vo.FinancialCheckSchemeNumVO;
import com.jiuqi.gcreport.financialcheckapi.scheme.vo.FinancialCheckSchemeTreeVO;
import com.jiuqi.gcreport.financialcheckapi.scheme.vo.FinancialCheckSchemeVO;
import com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO;
import com.jiuqi.gcreport.financialcheckcore.scheme.entity.FinancialCheckSchemeEO;
import java.util.List;
import java.util.Map;

public interface FinancialCheckSchemeService {
    public FinancialCheckSchemeInitDataVO initData();

    public FinancialCheckSchemeVO addOrUpdate(FinancialCheckSchemeVO var1);

    public FinancialCheckSchemeTreeVO save(FinancialCheckSchemeVO var1);

    public String validCheckScheme(String var1);

    public void delete(String var1);

    public List<FinancialCheckSchemeTreeVO> treeCheckGroup(FinancialCheckGroupVO var1, Boolean var2);

    public List<FinancialCheckSchemeBaseDataVO> treeEnableScheme(FinancialCheckGroupVO var1);

    public FinancialCheckSchemeBaseDataVO singleEnableScheme(FinancialCheckGroupVO var1);

    public List<FinancialCheckSchemeBaseDataVO> allEnableScheme(FinancialCheckGroupVO var1);

    public FinancialCheckSchemeNumVO countScheme(FinancialCheckGroupVO var1);

    public FinancialCheckSchemeVO queryCheckScheme(String var1);

    public void start(String var1, boolean var2);

    public void move(String var1, double var2);

    public FinancialCheckSchemeEO queryById(String var1);

    public List<CheckBusinessRoleOptionVO> queryBusinessRoleOptions(Map<String, String> var1);

    public void matchScheme(FinancialCheckMatchSchemeVO var1);

    public void matchCheckSchemes(List<GcRelatedItemEO> var1, List<String> var2);

    public void cancelCheckScheme(String var1, Integer var2, Integer var3, List<String> var4);

    public List<GcRelatedItemEO> cancelCheckScheme(List<GcRelatedItemEO> var1);

    public List<FinancialCheckSchemeEO> listSchemeByYear(Integer var1);

    public boolean filterBySchemeCondition(String var1, GcRelatedItemEO var2);

    public Map<String, Map<Integer, List<GcBaseData>>> queryOneLevelSubjectsBySchemeAndCHeckAttribute(String var1, List<String> var2);
}

