/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.google.common.util.concurrent.AtomicDouble
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.expimp.progress.common.ProgressData
 *  com.jiuqi.common.expimp.progress.service.ProgressService
 *  com.jiuqi.gcreport.financialcheckapi.check.FinancialCheckClient
 *  com.jiuqi.gcreport.financialcheckapi.check.vo.CheckResult
 *  com.jiuqi.gcreport.financialcheckapi.check.vo.FinancialCheckIniDataVO
 *  com.jiuqi.gcreport.financialcheckapi.check.vo.FinancialCheckQueryAmtSumConditionVO
 *  com.jiuqi.gcreport.financialcheckapi.check.vo.FinancialCheckQueryAmtSumVO
 *  com.jiuqi.gcreport.financialcheckapi.check.vo.FinancialCheckQueryConditionVO
 *  com.jiuqi.gcreport.financialcheckapi.check.vo.FinancialCheckQueryVO
 *  com.jiuqi.gcreport.financialcheckapi.check.vo.FinancialCheckVO
 *  com.jiuqi.gcreport.financialcheckapi.check.vo.FinancialClbrCodeInfoVO
 *  com.jiuqi.gcreport.financialcheckapi.check.vo.ManualCheckParam
 *  com.jiuqi.gcreport.financialcheckapi.check.vo.UnitCheckParam
 *  com.jiuqi.gcreport.financialcheckcore.gather.GcFinancialCheckExecutor
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.financialcheckImpl.check.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.util.concurrent.AtomicDouble;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.expimp.progress.common.ProgressData;
import com.jiuqi.common.expimp.progress.service.ProgressService;
import com.jiuqi.gcreport.financialcheckImpl.check.service.FinancialCheckService;
import com.jiuqi.gcreport.financialcheckapi.check.FinancialCheckClient;
import com.jiuqi.gcreport.financialcheckapi.check.vo.CheckResult;
import com.jiuqi.gcreport.financialcheckapi.check.vo.FinancialCheckIniDataVO;
import com.jiuqi.gcreport.financialcheckapi.check.vo.FinancialCheckQueryAmtSumConditionVO;
import com.jiuqi.gcreport.financialcheckapi.check.vo.FinancialCheckQueryAmtSumVO;
import com.jiuqi.gcreport.financialcheckapi.check.vo.FinancialCheckQueryConditionVO;
import com.jiuqi.gcreport.financialcheckapi.check.vo.FinancialCheckQueryVO;
import com.jiuqi.gcreport.financialcheckapi.check.vo.FinancialCheckVO;
import com.jiuqi.gcreport.financialcheckapi.check.vo.FinancialClbrCodeInfoVO;
import com.jiuqi.gcreport.financialcheckapi.check.vo.ManualCheckParam;
import com.jiuqi.gcreport.financialcheckapi.check.vo.UnitCheckParam;
import com.jiuqi.gcreport.financialcheckcore.gather.GcFinancialCheckExecutor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Primary
public class FinancialCheckController
implements FinancialCheckClient {
    @Autowired
    private FinancialCheckService service;
    @Autowired
    private ProgressService<ProgressData<String>, String> progressService;
    @Autowired
    private GcFinancialCheckExecutor gcFinancialCheckExecutor;

    public BusinessResponseEntity<FinancialCheckIniDataVO> getIniData() {
        FinancialCheckIniDataVO vo = this.service.getIniData();
        return BusinessResponseEntity.ok((Object)vo);
    }

    public BusinessResponseEntity<CheckResult> autoCheck(@RequestBody FinancialCheckQueryConditionVO condition) {
        return BusinessResponseEntity.ok((Object)this.service.autoCheck(condition));
    }

    public BusinessResponseEntity<List<FinancialCheckQueryVO>> manualCheck(@RequestBody ManualCheckParam params) {
        List<FinancialCheckQueryVO> result = this.service.manualCheck(params);
        return BusinessResponseEntity.ok(result);
    }

    public BusinessResponseEntity<String> cancelCheck(@RequestBody Map<String, Object> params) {
        Set checkIds = (Set)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)params.get("checkIds")), (TypeReference)new TypeReference<Set<String>>(){});
        return BusinessResponseEntity.ok((Object)this.service.cancelCheck(checkIds));
    }

    public BusinessResponseEntity<String> cancelCheckByCheckSchemeIds(@RequestBody Map<String, Object> params) {
        Set checkSchemeIds = (Set)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)params.get("checkSchemeIds")), (TypeReference)new TypeReference<Set<String>>(){});
        return BusinessResponseEntity.ok((Object)this.service.cancelCheckBySchemeIds(checkSchemeIds));
    }

    public BusinessResponseEntity<FinancialCheckVO> querySnStartProgress(@PathVariable(value="sn") String sn) {
        ProgressData autoCheckProcess = this.progressService.queryProgressData(sn);
        CheckResult checkResult = new CheckResult();
        checkResult.setProcessRate(new AtomicDouble(autoCheckProcess.getProgressValue()));
        String result = (String)autoCheckProcess.getResult();
        if (autoCheckProcess.getProgressValue() >= 1.0 && !"".equals(result)) {
            Map resultMap = (Map)JsonUtils.readValue((String)result, HashMap.class);
            checkResult.setCheckedGroupCount(((Integer)resultMap.get("CheckedGroupCount")).intValue());
            checkResult.setCheckedItemCount(((Integer)resultMap.get("CheckedItemCount")).intValue());
        }
        FinancialCheckVO vo = new FinancialCheckVO();
        vo.setCheckResult(checkResult);
        return BusinessResponseEntity.ok((Object)vo);
    }

    public BusinessResponseEntity<Object> deleteSnStartProgress(@PathVariable(value="sn") String sn) {
        this.progressService.removeProgressData(sn);
        return BusinessResponseEntity.ok();
    }

    public BusinessResponseEntity<String> saveUnCheckDesc(Map<String, Object> params) {
        List uncheckedDataIds = (List)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)params.get("uncheckedDataIds")), (TypeReference)new TypeReference<List<String>>(){});
        String type = String.valueOf(params.get("type"));
        String desc = String.valueOf(params.get("desc"));
        return BusinessResponseEntity.ok((Object)this.service.saveUnCheckDesc(uncheckedDataIds, desc, type));
    }

    public BusinessResponseEntity<String> authUnCheckDesc(Map<String, Object> params) {
        FinancialCheckQueryConditionVO condition = (FinancialCheckQueryConditionVO)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)params.get("condition")), FinancialCheckQueryConditionVO.class);
        List uncheckedDataIds = (List)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)params.get("uncheckedDataIds")), (TypeReference)new TypeReference<List<String>>(){});
        return BusinessResponseEntity.ok((Object)this.service.authUnCheckDesc(condition, uncheckedDataIds));
    }

    public BusinessResponseEntity<String> deleteUnCheckDesc(Map<String, Object> params) {
        List uncheckedDataIds = (List)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)params.get("uncheckedDataIds")), (TypeReference)new TypeReference<List<String>>(){});
        return BusinessResponseEntity.ok((Object)this.service.deleteUnCheckDesc(uncheckedDataIds));
    }

    public BusinessResponseEntity saveManualCheckData(@RequestBody List<FinancialCheckQueryVO> items) {
        this.service.saveManualCheckData(items);
        return BusinessResponseEntity.ok();
    }

    public BusinessResponseEntity<PageInfo<FinancialCheckQueryVO>> queryChecked(FinancialCheckQueryConditionVO condition) {
        return BusinessResponseEntity.ok(this.service.queryChecked(condition));
    }

    public BusinessResponseEntity<FinancialCheckQueryAmtSumVO> queryAmtSum(FinancialCheckQueryAmtSumConditionVO condition) {
        return BusinessResponseEntity.ok((Object)this.service.queryAmtSum(condition));
    }

    public BusinessResponseEntity<Object> queryUnchecked(FinancialCheckQueryConditionVO condition) {
        return BusinessResponseEntity.ok((Object)this.gcFinancialCheckExecutor.execute((Object)condition, condition.getShowType(), "query"));
    }

    public BusinessResponseEntity<FinancialClbrCodeInfoVO> queryClbrCodeInfo(String id) {
        FinancialClbrCodeInfoVO financialClbrCodeInfoVO = this.service.queryClbrCodeInfo(id);
        return BusinessResponseEntity.ok((Object)financialClbrCodeInfoVO);
    }

    public BusinessResponseEntity<Object> checkUnitState(UnitCheckParam unitCheckParam) {
        this.service.checkUnitState(unitCheckParam);
        return BusinessResponseEntity.ok((Object)true);
    }

    public BusinessResponseEntity<Object> checkCanDoManualCheck(ManualCheckParam param) {
        return BusinessResponseEntity.ok((Object)this.service.checkCanDoManualCheck(param));
    }
}

