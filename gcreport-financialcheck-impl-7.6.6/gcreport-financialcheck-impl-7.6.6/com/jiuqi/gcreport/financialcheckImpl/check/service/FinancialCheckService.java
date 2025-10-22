/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.gcreport.financialcheckapi.check.vo.CheckResult
 *  com.jiuqi.gcreport.financialcheckapi.check.vo.FinancialCheckIniDataVO
 *  com.jiuqi.gcreport.financialcheckapi.check.vo.FinancialCheckQueryAmtSumConditionVO
 *  com.jiuqi.gcreport.financialcheckapi.check.vo.FinancialCheckQueryAmtSumVO
 *  com.jiuqi.gcreport.financialcheckapi.check.vo.FinancialCheckQueryConditionVO
 *  com.jiuqi.gcreport.financialcheckapi.check.vo.FinancialCheckQueryVO
 *  com.jiuqi.gcreport.financialcheckapi.check.vo.FinancialClbrCodeInfoVO
 *  com.jiuqi.gcreport.financialcheckapi.check.vo.ManualCheckParam
 *  com.jiuqi.gcreport.financialcheckapi.check.vo.UnitCheckParam
 *  com.jiuqi.gcreport.financialcheckapi.common.vo.TableColumnVO
 */
package com.jiuqi.gcreport.financialcheckImpl.check.service;

import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.gcreport.financialcheckImpl.taskscheduling.param.RealTimeCheckOrOffsetParam;
import com.jiuqi.gcreport.financialcheckapi.check.vo.CheckResult;
import com.jiuqi.gcreport.financialcheckapi.check.vo.FinancialCheckIniDataVO;
import com.jiuqi.gcreport.financialcheckapi.check.vo.FinancialCheckQueryAmtSumConditionVO;
import com.jiuqi.gcreport.financialcheckapi.check.vo.FinancialCheckQueryAmtSumVO;
import com.jiuqi.gcreport.financialcheckapi.check.vo.FinancialCheckQueryConditionVO;
import com.jiuqi.gcreport.financialcheckapi.check.vo.FinancialCheckQueryVO;
import com.jiuqi.gcreport.financialcheckapi.check.vo.FinancialClbrCodeInfoVO;
import com.jiuqi.gcreport.financialcheckapi.check.vo.ManualCheckParam;
import com.jiuqi.gcreport.financialcheckapi.check.vo.UnitCheckParam;
import com.jiuqi.gcreport.financialcheckapi.common.vo.TableColumnVO;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface FinancialCheckService {
    public FinancialCheckIniDataVO getIniData();

    public CheckResult autoCheck(FinancialCheckQueryConditionVO var1);

    public String cancelCheck(Set<String> var1);

    public String cancelCheckBySchemeIds(Set<String> var1);

    public String saveUnCheckDesc(List<String> var1, String var2, String var3);

    public String authUnCheckDesc(FinancialCheckQueryConditionVO var1, List<String> var2);

    public String deleteUnCheckDesc(List<String> var1);

    public void realTimeCheck(int var1, Set<String> var2);

    public List<FinancialCheckQueryVO> manualCheck(ManualCheckParam var1);

    public void saveManualCheckData(List<FinancialCheckQueryVO> var1);

    public PageInfo<FinancialCheckQueryVO> queryChecked(FinancialCheckQueryConditionVO var1);

    public PageInfo<FinancialCheckQueryVO> queryUncheckedGroupByUnit(FinancialCheckQueryConditionVO var1);

    public PageInfo<FinancialCheckQueryVO> queryUncheckedGroupByOppUnit(FinancialCheckQueryConditionVO var1);

    public PageInfo<FinancialCheckQueryVO> queryUncheckedGroupByScheme(FinancialCheckQueryConditionVO var1);

    public CheckResult realTimeCheck(RealTimeCheckOrOffsetParam var1);

    public Map<String, String> getExcelColumnTitleMap(String var1, TableColumnVO[] var2);

    public List<Map<String, Object>> exportData(List<FinancialCheckQueryVO> var1);

    public FinancialCheckQueryAmtSumVO queryAmtSum(FinancialCheckQueryAmtSumConditionVO var1);

    public FinancialClbrCodeInfoVO queryClbrCodeInfo(String var1);

    public boolean checkUnitState(UnitCheckParam var1);

    public boolean checkCanDoManualCheck(ManualCheckParam var1);
}

