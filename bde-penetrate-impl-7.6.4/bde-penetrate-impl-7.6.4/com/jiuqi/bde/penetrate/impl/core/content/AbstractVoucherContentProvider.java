/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.intf.Dimension
 *  com.jiuqi.bde.penetrate.client.dto.VoucherPenetrateDTO
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.va.domain.common.PageVO
 */
package com.jiuqi.bde.penetrate.impl.core.content;

import com.jiuqi.bde.common.intf.Dimension;
import com.jiuqi.bde.penetrate.client.dto.VoucherPenetrateDTO;
import com.jiuqi.bde.penetrate.impl.common.PenetrateTypeEnum;
import com.jiuqi.bde.penetrate.impl.common.RowTypeEnum;
import com.jiuqi.bde.penetrate.impl.core.AbstractPenetrateContentProvider;
import com.jiuqi.bde.penetrate.impl.core.intf.PenetrateVoucher;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.va.domain.common.PageVO;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractVoucherContentProvider
extends AbstractPenetrateContentProvider<VoucherPenetrateDTO, PenetrateVoucher> {
    @Override
    public PenetrateTypeEnum getPenetrateType() {
        return PenetrateTypeEnum.VOUCHER;
    }

    @Override
    protected PageVO<PenetrateVoucher> processResult(VoucherPenetrateDTO condi, PageVO<PenetrateVoucher> queryResult) {
        PenetrateVoucher total = new PenetrateVoucher();
        total.setRowType(RowTypeEnum.TOTAL.ordinal());
        total.setSubjectCode(GcI18nUtil.getMessage((String)"bde.accountant.total"));
        queryResult.getRows().forEach(item -> {
            total.setDebit(NumberUtils.sum((BigDecimal)item.getDebit(), (BigDecimal)total.getDebit()));
            total.setOrgnd(NumberUtils.sum((BigDecimal)item.getOrgnd(), (BigDecimal)total.getOrgnd()));
            total.setCredit(NumberUtils.sum((BigDecimal)item.getCredit(), (BigDecimal)total.getCredit()));
            total.setOrgnc(NumberUtils.sum((BigDecimal)item.getOrgnc(), (BigDecimal)total.getOrgnc()));
            total.setYe(NumberUtils.sum((BigDecimal)item.getYe(), (BigDecimal)total.getYe()));
            total.setOrgnYe(NumberUtils.sum((BigDecimal)item.getOrgnYe(), (BigDecimal)total.getOrgnYe()));
            item.setAssTypeList(this.formatAssType(item.getAssTypeList()));
        });
        queryResult.getRows().add(total);
        return queryResult;
    }

    private List<Dimension> formatAssType(List<Dimension> assTypeList) {
        if (CollectionUtils.isEmpty(assTypeList)) {
            return assTypeList;
        }
        return assTypeList.stream().filter(item -> !StringUtils.isNull((String)item.getDimValue()) && !"#".equals(item.getDimValue())).collect(Collectors.toList());
    }
}

