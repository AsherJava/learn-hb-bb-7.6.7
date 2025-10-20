/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.va.domain.common.PageVO
 */
package com.jiuqi.bde.penetrate.impl.core.content;

import com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO;
import com.jiuqi.bde.penetrate.impl.common.PenetrateTypeEnum;
import com.jiuqi.bde.penetrate.impl.common.RowTypeEnum;
import com.jiuqi.bde.penetrate.impl.core.AbstractPenetrateContentProvider;
import com.jiuqi.bde.penetrate.impl.core.intf.PenetrateDetailLedger;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.va.domain.common.PageVO;
import java.math.BigDecimal;
import java.util.stream.Collectors;

public abstract class AbstractDetailLedgerContentProvider
extends AbstractPenetrateContentProvider<PenetrateBaseDTO, PenetrateDetailLedger> {
    @Override
    public final PenetrateTypeEnum getPenetrateType() {
        return PenetrateTypeEnum.DETAILLEDGER;
    }

    @Override
    protected PageVO<PenetrateDetailLedger> processResult(PenetrateBaseDTO condi, PageVO<PenetrateDetailLedger> queryResult) {
        BigDecimal ye = BigDecimal.ZERO;
        BigDecimal orgnYe = BigDecimal.ZERO;
        int rowSize = Boolean.FALSE.equals(condi.getPagination()) ? queryResult.getRows().size() : Math.min(queryResult.getRows().size(), condi.getOffset() + condi.getLimit());
        PenetrateDetailLedger item = null;
        for (int idx = 0; idx < rowSize; ++idx) {
            item = (PenetrateDetailLedger)queryResult.getRows().get(idx);
            if (idx == 0 && item.getRowType().compareTo(RowTypeEnum.TOTAL.ordinal()) == 0) {
                ye = NumberUtils.sum((BigDecimal)ye, (BigDecimal)item.getYe());
                orgnYe = NumberUtils.sum((BigDecimal)orgnYe, (BigDecimal)item.getOrgnYe());
            } else {
                ye = NumberUtils.sum((BigDecimal)ye, (BigDecimal)NumberUtils.sub((BigDecimal)item.getDebit(), (BigDecimal)item.getCredit()));
                orgnYe = NumberUtils.sum((BigDecimal)orgnYe, (BigDecimal)NumberUtils.sub((BigDecimal)item.getOrgnd(), (BigDecimal)item.getOrgnc()));
            }
            item.setYe(ye);
            item.setOrgnYe(orgnYe);
            item.setYeOrient(this.formatOrient(item.getYe()));
            item.setYe(item.getYe().abs());
            item.setOrgnYe(item.getOrgnYe().abs());
        }
        queryResult.setRows(queryResult.getRows().stream().skip(condi.getOffset().intValue()).limit(condi.getLimit().intValue()).collect(Collectors.toList()));
        return queryResult;
    }
}

