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
import java.util.stream.Collectors;

public abstract class AbstractVchrBalanceContentProvider
extends AbstractPenetrateContentProvider<PenetrateBaseDTO, PenetrateBalance> {
    @Override
    public final PenetrateTypeEnum getPenetrateType() {
        return PenetrateTypeEnum.BALANCE;
    }

    @Override
    protected PageVO<PenetrateBalance> processResult(PenetrateBaseDTO condi, PageVO<PenetrateBalance> queryResult) {
        PenetrateBalance total = new PenetrateBalance();
        total.setRowType(RowTypeEnum.TOTAL.ordinal());
        total.setSubjectCode(GcI18nUtil.getMessage((String)"bde.accountant.total"));
        queryResult.getRows().forEach(item -> {
            total.setDebit(NumberUtils.sum((BigDecimal)item.getDebit(), (BigDecimal)total.getDebit()));
            total.setOrgnd(NumberUtils.sum((BigDecimal)item.getOrgnd(), (BigDecimal)total.getOrgnd()));
            total.setCredit(NumberUtils.sum((BigDecimal)item.getCredit(), (BigDecimal)total.getCredit()));
            total.setOrgnc(NumberUtils.sum((BigDecimal)item.getOrgnc(), (BigDecimal)total.getOrgnc()));
            total.setDsum(NumberUtils.sum((BigDecimal)item.getDsum(), (BigDecimal)total.getDsum()));
            total.setOrgnDsum(NumberUtils.sum((BigDecimal)item.getOrgnDsum(), (BigDecimal)total.getOrgnDsum()));
            total.setCsum(NumberUtils.sum((BigDecimal)item.getCsum(), (BigDecimal)total.getCsum()));
            total.setOrgnCsum(NumberUtils.sum((BigDecimal)item.getOrgnCsum(), (BigDecimal)total.getOrgnCsum()));
            this.formatBalanceOrient((PenetrateBalance)item);
        });
        this.formatBalanceOrient(total);
        ArrayList result = CollectionUtils.newArrayList((Object[])new PenetrateBalance[]{total});
        List detailData = queryResult.getRows().stream().skip(condi.getOffset().intValue()).limit(condi.getLimit().intValue()).collect(Collectors.toList());
        result.addAll(detailData);
        queryResult.setRows((List)result);
        return queryResult;
    }

    private void formatBalanceOrient(PenetrateBalance item) {
        item.setNcOrient(this.formatOrient(item.getNc()));
        item.setNc(item.getNc().abs());
        item.setOrgnNc(item.getOrgnNc().abs());
        item.setQcOrient(this.formatOrient(item.getQc()));
        item.setQc(item.getQc().abs());
        item.setOrgnQc(item.getOrgnQc().abs());
        item.setYeOrient(this.formatOrient(item.getYe()));
        item.setYe(item.getYe().abs());
        item.setOrgnYe(item.getOrgnYe().abs());
    }
}

