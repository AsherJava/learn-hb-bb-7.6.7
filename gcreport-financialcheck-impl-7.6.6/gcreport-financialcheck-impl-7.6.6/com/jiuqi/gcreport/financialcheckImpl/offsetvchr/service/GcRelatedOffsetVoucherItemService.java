/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.financialcheckapi.check.vo.ManualCheckParam
 *  com.jiuqi.gcreport.financialcheckapi.offsetvoucher.vo.GcRelatedOffsetVoucherInfoVO
 *  com.jiuqi.gcreport.financialcheckcore.check.dto.BalanceCondition
 *  com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO
 *  com.jiuqi.gcreport.financialcheckcore.offsetvoucher.entity.GcRelatedOffsetVoucherItemEO
 */
package com.jiuqi.gcreport.financialcheckImpl.offsetvchr.service;

import com.jiuqi.gcreport.financialcheckapi.check.vo.ManualCheckParam;
import com.jiuqi.gcreport.financialcheckapi.offsetvoucher.vo.GcRelatedOffsetVoucherInfoVO;
import com.jiuqi.gcreport.financialcheckcore.check.dto.BalanceCondition;
import com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO;
import com.jiuqi.gcreport.financialcheckcore.offsetvoucher.entity.GcRelatedOffsetVoucherItemEO;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface GcRelatedOffsetVoucherItemService {
    public void addByCheckGroup(List<GcRelatedItemEO> var1);

    public void deleteByCheckIdAndOffsetPeriod(Collection<String> var1, Integer var2);

    public List<GcRelatedOffsetVoucherItemEO> queryByOffsetCondition(BalanceCondition var1);

    public List<GcRelatedOffsetVoucherInfoVO> queryOffSetVoucherInfo(String var1);

    public List<GcRelatedOffsetVoucherInfoVO> queryByClbrCode(String var1);

    public List<GcRelatedOffsetVoucherInfoVO> queryManualOffsetResult(ManualCheckParam var1);

    public void saveRelatedOffsetVchrInfo(List<GcRelatedOffsetVoucherInfoVO> var1);

    public List<GcRelatedOffsetVoucherItemEO> queryByIds(Set<String> var1);
}

