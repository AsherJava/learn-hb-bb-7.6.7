/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gc.financial.status.enums.FinancialStatusEnum
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.financialcheckapi.check.vo.FinancialCheckQueryVO
 *  com.jiuqi.gcreport.financialcheckcore.check.enums.VchrSrcWayEnum
 *  com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO
 *  com.jiuqi.gcreport.financialcheckcore.utils.PeriodUtils
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 */
package com.jiuqi.gcreport.financialcheckImpl.check;

import com.jiuqi.gc.financial.status.enums.FinancialStatusEnum;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.financialcheckImpl.checkconfig.utils.FinancialCheckConfigUtils;
import com.jiuqi.gcreport.financialcheckImpl.util.UnitStateUtils;
import com.jiuqi.gcreport.financialcheckapi.check.vo.FinancialCheckQueryVO;
import com.jiuqi.gcreport.financialcheckcore.check.enums.VchrSrcWayEnum;
import com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO;
import com.jiuqi.gcreport.financialcheckcore.utils.PeriodUtils;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import java.util.HashMap;
import java.util.Objects;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

public class FinancialCheckQueryEO2VOHelper {
    private HashMap<String, String> subjectTitleCache = new HashMap(10);
    private HashMap<String, String> currencyTitleCache = new HashMap(10);
    private HashMap<String, GcOrgCenterService> instanceCache = new HashMap(10);
    private HashMap<String, HashMap<String, String>> unitTitleCache = new HashMap(10);
    private HashMap<String, FinancialStatusEnum> unitStatusCache = new HashMap(10);

    public static FinancialCheckQueryEO2VOHelper newInstance() {
        FinancialCheckQueryEO2VOHelper helper = new FinancialCheckQueryEO2VOHelper();
        return helper;
    }

    public FinancialCheckQueryVO convertToCheckQueryVO(GcRelatedItemEO gcRelatedItemEO) {
        GcBaseData mdVchrtype;
        String cfItemCode;
        FinancialCheckQueryVO financialCheckQueryVO = new FinancialCheckQueryVO();
        BeanUtils.copyProperties(gcRelatedItemEO, financialCheckQueryVO);
        if (VchrSrcWayEnum.BATCHINPUT.name().equals(gcRelatedItemEO.getInputWay())) {
            financialCheckQueryVO.setInputWay("\u624b\u5de5\u5f55\u5165");
        } else {
            financialCheckQueryVO.setInputWay("\u81ea\u52a8\u62bd\u53d6");
        }
        if (StringUtils.hasText(financialCheckQueryVO.getSubjectCode())) {
            if (VchrSrcWayEnum.DATASYNC_CF.name().equals(gcRelatedItemEO.getInputWay())) {
                financialCheckQueryVO.setSubjectCode("");
            } else {
                String subjectTitle = this.getSubjectTitle(financialCheckQueryVO.getSubjectCode());
                financialCheckQueryVO.setSubjectCode(subjectTitle);
            }
        }
        if (StringUtils.hasText(cfItemCode = financialCheckQueryVO.getCfItemCode()) && !"#".equals(cfItemCode)) {
            String cfItemTitle = this.getSubjectTitle(cfItemCode);
            financialCheckQueryVO.setCfItemCode(cfItemTitle);
        } else {
            financialCheckQueryVO.setCfItemCode("");
        }
        financialCheckQueryVO.setUnitKey(financialCheckQueryVO.getUnitId());
        int period = PeriodUtils.standardPeriod((int)gcRelatedItemEO.getAcctPeriod());
        Integer acctYear = gcRelatedItemEO.getAcctYear();
        YearPeriodObject yearMonth = new YearPeriodObject(acctYear.intValue(), period);
        financialCheckQueryVO.setUnitId(this.getUnitTitle(yearMonth, financialCheckQueryVO.getUnitId()));
        financialCheckQueryVO.setOppUnitId(this.getUnitTitle(yearMonth, financialCheckQueryVO.getOppUnitId()));
        if (StringUtils.hasText(financialCheckQueryVO.getOriginalCurr())) {
            String currencyTitle = this.getCurrencyTitle(financialCheckQueryVO.getOriginalCurr());
            financialCheckQueryVO.setOriginalCurr(currencyTitle);
        }
        if (StringUtils.hasText(financialCheckQueryVO.getVchrSourceType()) && Objects.nonNull(mdVchrtype = GcBaseDataCenterTool.getInstance().queryBasedataByCode("MD_VCHRTYPE", financialCheckQueryVO.getVchrSourceType()))) {
            financialCheckQueryVO.setVchrSourceType(mdVchrtype.getTitle());
        }
        double checkAmt = financialCheckQueryVO.getChkAmtC() == 0.0 ? financialCheckQueryVO.getChkAmtD() : financialCheckQueryVO.getChkAmtC();
        financialCheckQueryVO.setChkAmt(Double.valueOf(checkAmt));
        double amt = financialCheckQueryVO.getDebitOrig() == 0.0 ? financialCheckQueryVO.getCreditOrig() : financialCheckQueryVO.getDebitOrig();
        financialCheckQueryVO.setDiffAmount(Double.valueOf(amt - checkAmt));
        financialCheckQueryVO.setCurrencyCode(financialCheckQueryVO.getCurrency());
        if (StringUtils.hasText(financialCheckQueryVO.getCurrency())) {
            String currencyTitle = this.getCurrencyTitle(financialCheckQueryVO.getCurrency());
            financialCheckQueryVO.setCurrency(currencyTitle);
        }
        if ("SystemDefault".equals(gcRelatedItemEO.getGcNumber())) {
            financialCheckQueryVO.setGcNumber(null);
        }
        return financialCheckQueryVO;
    }

    private String getSubjectTitle(String code) {
        if (this.subjectTitleCache.containsKey(code)) {
            return this.subjectTitleCache.get(code);
        }
        GcBaseData subject = GcBaseDataCenterTool.getInstance().queryBasedataByCode("MD_ACCTSUBJECT", code);
        if (Objects.nonNull(subject)) {
            String title = subject.getTitle();
            this.subjectTitleCache.put(code, title);
            return title;
        }
        return code;
    }

    private String getCurrencyTitle(String code) {
        if (this.currencyTitleCache.containsKey(code)) {
            return this.currencyTitleCache.get(code);
        }
        GcBaseData curr = GcBaseDataCenterTool.getInstance().queryBasedataByCode("MD_CURRENCY", code);
        if (Objects.nonNull(curr)) {
            String title = curr.getTitle();
            this.currencyTitleCache.put(code, title);
            return title;
        }
        return code;
    }

    private String getUnitTitle(YearPeriodObject yearMonth, String code) {
        GcOrgCenterService instance;
        String ytm = yearMonth.getYtm();
        HashMap<String, String> unitTitleMap = this.unitTitleCache.get(ytm);
        if (unitTitleMap != null && unitTitleMap.containsKey(code)) {
            return unitTitleMap.get(code);
        }
        if (this.instanceCache.containsKey(ytm)) {
            instance = this.instanceCache.get(ytm);
        } else {
            instance = GcOrgPublicTool.getInstance((String)FinancialCheckConfigUtils.getCheckOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yearMonth);
            this.instanceCache.put(ytm, instance);
        }
        GcOrgCacheVO unit = instance.getOrgByCode(code);
        if (Objects.nonNull(unit)) {
            String title = unit.getTitle();
            if (unitTitleMap == null) {
                this.unitTitleCache.put(ytm, new HashMap(4));
            }
            this.unitTitleCache.get(ytm).put(code, title);
            return title;
        }
        return code;
    }

    public FinancialStatusEnum getFinancialStatus(String unitId, String oppUnitId, int year, int period) {
        StringBuilder unionKey = new StringBuilder(unitId);
        unionKey.append("|").append(oppUnitId).append("|").append(year).append("|").append(period);
        if (this.unitStatusCache.containsKey(unionKey.toString())) {
            return this.unitStatusCache.get(unionKey.toString());
        }
        FinancialStatusEnum state = UnitStateUtils.getState(unitId, oppUnitId, year, period);
        this.unitStatusCache.put(unionKey.toString(), state);
        return state;
    }
}

