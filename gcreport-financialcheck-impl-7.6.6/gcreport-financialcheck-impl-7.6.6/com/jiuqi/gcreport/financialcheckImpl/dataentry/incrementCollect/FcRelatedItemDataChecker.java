/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.dimension.internal.entity.DimensionEO
 *  com.jiuqi.gcreport.financialcheckcore.check.enums.ReconciliationModeEnum
 *  com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 */
package com.jiuqi.gcreport.financialcheckImpl.dataentry.incrementCollect;

import com.jiuqi.gcreport.dimension.internal.entity.DimensionEO;
import com.jiuqi.gcreport.financialcheckImpl.checkconfig.utils.FinancialCheckConfigUtils;
import com.jiuqi.gcreport.financialcheckcore.check.enums.ReconciliationModeEnum;
import com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class FcRelatedItemDataChecker {
    private static final Double ZERO = 0.0;

    public boolean isEqual(GcRelatedItemEO item1, GcRelatedItemEO item2, List<DimensionEO> dims) {
        if (Objects.isNull(item1) || Objects.isNull(item2)) {
            return false;
        }
        if (item1 == item2) {
            return true;
        }
        boolean baseInfoIsEqual = Objects.equals(item1.getAcctYear(), item2.getAcctYear()) && Objects.equals(item1.getAcctPeriod(), item2.getAcctPeriod()) && Objects.equals(item1.getSubjectCode(), item2.getSubjectCode()) && Objects.equals(item1.getUnitId(), item2.getUnitId()) && Objects.equals(item1.getOppUnitId(), item2.getOppUnitId()) && Objects.equals(item1.getOriginalCurr(), item2.getOriginalCurr()) && Objects.equals(item1.getCreditOrig(), item2.getCreditOrig()) && Objects.equals(item1.getDebitOrig(), item2.getDebitOrig()) && Objects.equals(item1.getCredit(), item2.getCredit()) && Objects.equals(item1.getDebit(), item2.getDebit()) && Objects.equals(item1.getCurrency(), item2.getCurrency());
        boolean itemInfoIsEqual = true;
        if (ReconciliationModeEnum.ITEM.equals((Object)FinancialCheckConfigUtils.getCheckWay())) {
            itemInfoIsEqual = Objects.equals(item1.getVchrSourceType(), item2.getVchrSourceType()) && Objects.equals(item1.getBillCode(), item2.getBillCode()) && Objects.equals(item1.getDigest(), item2.getDigest()) && Objects.equals(item1.getCreateDate(), item2.getCreateDate()) && Objects.equals(item1.getVchrNum(), item2.getVchrNum()) && Objects.equals(item1.getVchrType(), item2.getVchrType()) && Objects.equals(item1.getGcNumber(), item2.getGcNumber()) && Objects.equals(item1.getRealGcNumber(), item2.getRealGcNumber()) && Objects.equals(item1.getCfItemCode(), item2.getCfItemCode()) && Objects.equals(item1.getVchrId(), item2.getVchrId()) && Objects.equals(item1.getReclassifySubjCode(), item2.getReclassifySubjCode()) && Objects.equals(item1.getMemo(), item2.getMemo());
        }
        boolean dimInfoIsEqual = true;
        if (!CollectionUtils.isEmpty(dims)) {
            dimInfoIsEqual = !dims.stream().anyMatch(dim -> !Objects.equals(item1.getFieldValue(dim.getCode()), item2.getFieldValue(dim.getCode())));
        }
        return baseInfoIsEqual && itemInfoIsEqual && dimInfoIsEqual;
    }

    public String saveCheck(GcRelatedItemEO item, String orgType) {
        if (StringUtils.isEmpty(item.getUnitId())) {
            return "\u672c\u65b9\u5355\u4f4d\u4e0d\u80fd\u4e3a\u7a7a\u3002";
        }
        if (StringUtils.isEmpty(item.getOppUnitId())) {
            return "\u5bf9\u65b9\u5355\u4f4d\u4e0d\u80fd\u4e3a\u7a7a\u3002";
        }
        if (StringUtils.isEmpty(item.getSubjectCode())) {
            return "\u79d1\u76ee\u4e0d\u80fd\u4e3a\u7a7a\u3002";
        }
        if (StringUtils.isEmpty(item.getOriginalCurr())) {
            return "\u5e01\u79cd(\u539f\u5e01)\u4e0d\u80fd\u4e3a\u7a7a\u3002";
        }
        if (StringUtils.isEmpty(item.getCurrency())) {
            return "\u5e01\u79cd(\u672c\u4f4d\u5e01)\u4e0d\u80fd\u4e3a\u7a7a\u3002";
        }
        if (Objects.equals(ZERO, item.getDebit()) && Objects.equals(ZERO, item.getCredit())) {
            return "\u672c\u4f4d\u5e01\u91d1\u989d\u4e0d\u80fd\u90fd\u4e3a\u96f6\u6216\u7a7a\u3002";
        }
        if (Objects.equals(ZERO, item.getDebitOrig()) && Objects.equals(ZERO, item.getCreditOrig())) {
            return "\u539f\u5e01\u91d1\u989d\u4e0d\u80fd\u90fd\u4e3a\u96f6\u6216\u7a7a\u3002";
        }
        YearPeriodObject yp = new YearPeriodObject(item.getAcctYear().intValue(), item.getAcctPeriod() == 0 ? 1 : item.getAcctPeriod());
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        GcOrgCacheVO localOrg = tool.getOrgByCode(item.getUnitId());
        if (Objects.isNull(localOrg)) {
            return "\u672c\u65b9\u5355\u4f4d" + item.getUnitId() + "\u5728\u5173\u8054\u4ea4\u6613\u6811\u5f62\u4e0a\u4e0d\u5b58\u5728\u3002";
        }
        GcOrgCacheVO oppOrg = tool.getOrgByCode(item.getOppUnitId());
        if (Objects.isNull(oppOrg)) {
            return "\u5bf9\u65b9\u5355\u4f4d" + item.getOppUnitId() + "\u5728\u5173\u8054\u4ea4\u6613\u6811\u5f62\u4e0a\u4e0d\u5b58\u5728\u3002";
        }
        GcOrgCacheVO mergeOrg = tool.getCommonUnit(localOrg, oppOrg);
        if (Objects.isNull(mergeOrg)) {
            return MessageFormat.format("\u672c\u5bf9\u65b9\u5355\u4f4d\uff1a{0} \u3001{1} \u6ca1\u6709\u5171\u540c\u4e0a\u7ea7", localOrg.getTitle(), oppOrg.getTitle());
        }
        return "";
    }
}

