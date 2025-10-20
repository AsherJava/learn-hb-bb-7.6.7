/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.i18n.util.BdeI18nHelper
 *  com.jiuqi.bde.common.intf.PenetrateColumn
 *  com.jiuqi.bde.penetrate.client.dto.VoucherPenetrateDTO
 */
package com.jiuqi.bde.penetrate.impl.core.columns;

import com.jiuqi.bde.common.i18n.util.BdeI18nHelper;
import com.jiuqi.bde.common.intf.PenetrateColumn;
import com.jiuqi.bde.penetrate.client.dto.VoucherPenetrateDTO;
import com.jiuqi.bde.penetrate.impl.common.PenetrateTypeEnum;
import com.jiuqi.bde.penetrate.impl.core.AbstractPenetrateColumnBuilder;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VoucherPenetrateColumnProvider
extends AbstractPenetrateColumnBuilder<VoucherPenetrateDTO> {
    @Autowired
    private BdeI18nHelper bdeI18nHelper;

    @Override
    public String getBizModel() {
        return "DEFAULT_COLUMNBUILDER";
    }

    @Override
    public PenetrateTypeEnum getPenetrateType() {
        return PenetrateTypeEnum.VOUCHER;
    }

    @Override
    public List<PenetrateColumn> createQueryColumns(VoucherPenetrateDTO condi) {
        ArrayList<PenetrateColumn> columns = new ArrayList<PenetrateColumn>();
        PenetrateColumn digest = this.createStringCloumn("DIGEST", this.bdeI18nHelper.getMessageByColumnCode("DIGEST")).setFixed(Boolean.valueOf(true)).setWidth(null).setMinWidth(Integer.valueOf(300));
        columns.add(digest);
        PenetrateColumn subjectCode = this.createStringCloumn("SUBJECTCODE", this.bdeI18nHelper.getMessageByColumnCode("SUBJECTCODE")).setFixed(Boolean.valueOf(true)).setWidth(Integer.valueOf(135));
        columns.add(subjectCode);
        PenetrateColumn subjectName = this.createStringCloumn("SUBJECTNAME", this.bdeI18nHelper.getMessageByColumnCode("SUBJECTNAME")).setFixed(Boolean.valueOf(true)).setWidth(null).setMinWidth(Integer.valueOf(200));
        columns.add(subjectName);
        PenetrateColumn orgn = this.createParentCloumn("ORGN", this.bdeI18nHelper.getMessageByColumnCode("ORGN"));
        columns.add(orgn);
        orgn.addChild(this.createNumericCloumn("ORGND", this.bdeI18nHelper.getMessageByColumnCode("ORGND")));
        orgn.addChild(this.createNumericCloumn("ORGNC", this.bdeI18nHelper.getMessageByColumnCode("ORGNC")));
        PenetrateColumn amnt = this.createParentCloumn("AMNT", this.bdeI18nHelper.getMessageByColumnCode("AMNT"));
        columns.add(amnt);
        amnt.addChild(this.createNumericCloumn("DEBIT", this.bdeI18nHelper.getMessageByColumnCode("DEBIT")));
        amnt.addChild(this.createNumericCloumn("CREDIT", this.bdeI18nHelper.getMessageByColumnCode("CREDIT")));
        PenetrateColumn assistComb = this.createStringCloumn("ASSISTCOMB", this.bdeI18nHelper.getMessageByColumnCode("ASSISTCOMB")).setFixed(Boolean.valueOf(true)).setWidth(null).setMinWidth(Integer.valueOf(500));
        columns.add(assistComb);
        return columns;
    }
}

