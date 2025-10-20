/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.va.domain.common.PageVO
 */
package com.jiuqi.bde.penetrate.impl.core.content;

import com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO;
import com.jiuqi.bde.penetrate.impl.common.PenetrateTypeEnum;
import com.jiuqi.bde.penetrate.impl.common.RowTypeEnum;
import com.jiuqi.bde.penetrate.impl.core.AbstractPenetrateContentProvider;
import com.jiuqi.bde.penetrate.impl.core.intf.PenetrateBalance;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.va.domain.common.PageVO;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class AbstractXjllBalanceContentProvider
extends AbstractPenetrateContentProvider<PenetrateBaseDTO, PenetrateBalance> {
    @Override
    public PenetrateTypeEnum getPenetrateType() {
        return PenetrateTypeEnum.BALANCE;
    }

    @Override
    protected PageVO<PenetrateBalance> processResult(PenetrateBaseDTO condi, PageVO<PenetrateBalance> queryResult) {
        PenetrateBalance total = new PenetrateBalance();
        total.setRowType(RowTypeEnum.TOTAL.ordinal());
        total.setCashCode(GcI18nUtil.getMessage((String)"bde.accountant.total"));
        Map<Boolean, List<PenetrateBalance>> partitioned = queryResult.getRows().stream().collect(Collectors.partitioningBy(penetrateBalance -> GcI18nUtil.getMessage((String)"bde.accountant.total").equals(penetrateBalance.getCashCode())));
        List<PenetrateBalance> detailList = partitioned.get(false);
        List<PenetrateBalance> totalList = partitioned.get(true);
        detailList.forEach(item -> {
            total.setBqNum(NumberUtils.sum((BigDecimal)item.getBqNum(), (BigDecimal)total.getBqNum()));
            total.setLjNum(NumberUtils.sum((BigDecimal)item.getLjNum(), (BigDecimal)total.getLjNum()));
            total.setWbqNum(NumberUtils.sum((BigDecimal)item.getWbqNum(), (BigDecimal)total.getWbqNum()));
            total.setWljNum(NumberUtils.sum((BigDecimal)item.getWljNum(), (BigDecimal)total.getWljNum()));
        });
        ArrayList result = CollectionUtils.newArrayList((Object[])new PenetrateBalance[]{total});
        if (totalList != null && !totalList.isEmpty()) {
            result.addAll(totalList);
        }
        List detailData = detailList.stream().skip(condi.getOffset().intValue()).limit(condi.getLimit().intValue()).collect(Collectors.toList());
        result.addAll(detailData);
        queryResult.setRows((List)result);
        return queryResult;
    }
}

