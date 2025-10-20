/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.util.ModelExecuteUtil
 *  com.jiuqi.bde.bizmodel.impl.dimension.util.BdeAssistDimUtils
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 *  com.jiuqi.bde.common.i18n.util.BdeI18nHelper
 *  com.jiuqi.bde.common.intf.PenetrateColumn
 *  com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 */
package com.jiuqi.bde.penetrate.impl.core.columns;

import com.jiuqi.bde.bizmodel.execute.util.ModelExecuteUtil;
import com.jiuqi.bde.bizmodel.impl.dimension.util.BdeAssistDimUtils;
import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.bde.common.i18n.util.BdeI18nHelper;
import com.jiuqi.bde.common.intf.PenetrateColumn;
import com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO;
import com.jiuqi.bde.penetrate.impl.common.ColumnAlignmentEnum;
import com.jiuqi.bde.penetrate.impl.common.PenetrateTypeEnum;
import com.jiuqi.bde.penetrate.impl.core.AbstractPenetrateColumnBuilder;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class XjllDetailLedgerPenetrateColumnProvider
extends AbstractPenetrateColumnBuilder<PenetrateBaseDTO> {
    @Autowired
    private BdeI18nHelper bdeI18nHelper;

    @Override
    public String getBizModel() {
        return ComputationModelEnum.XJLLBALANCE.getCode();
    }

    @Override
    public PenetrateTypeEnum getPenetrateType() {
        return PenetrateTypeEnum.DETAILLEDGER;
    }

    @Override
    public List<PenetrateColumn> createQueryColumns(PenetrateBaseDTO condi) {
        ArrayList<PenetrateColumn> columns = new ArrayList<PenetrateColumn>();
        List assTypeList = condi.getAssTypeList();
        Map<String, DimensionVO> assistDimMap = BdeAssistDimUtils.listAssistDim().stream().collect(Collectors.toMap(DimensionVO::getCode, item -> item, (k1, k2) -> k2));
        boolean showOrgnAmnt = Boolean.TRUE.equals(condi.getShowOrgnAmnt());
        if (Boolean.TRUE.equals(condi.getIncludeAdjustVchr())) {
            PenetrateColumn vchrSrcType = this.createStringCloumn("VCHRSRCTYPE", this.bdeI18nHelper.getMessageByColumnCode("VCHRSRCTYPE")).setFixed(Boolean.valueOf(true)).setWidth(Integer.valueOf(135));
            columns.add(vchrSrcType);
        }
        PenetrateColumn acctYear = this.createStringCloumn("ACCTYEAR", this.bdeI18nHelper.getMessageByColumnCode("ACCTYEAR")).setAlignment(ColumnAlignmentEnum.CENTER.getCode()).setFixed(Boolean.valueOf(true)).setWidth(Integer.valueOf(80));
        columns.add(acctYear);
        PenetrateColumn acctPeriod = this.createStringCloumn("ACCTPERIOD", this.bdeI18nHelper.getMessageByColumnCode("ACCTPERIOD")).setAlignment(ColumnAlignmentEnum.CENTER.getCode()).setFixed(Boolean.valueOf(true)).setWidth(Integer.valueOf(55));
        columns.add(acctPeriod);
        PenetrateColumn acctDay = this.createStringCloumn("ACCTDAY", this.bdeI18nHelper.getMessageByColumnCode("ACCTDAY")).setAlignment(ColumnAlignmentEnum.CENTER.getCode()).setFixed(Boolean.valueOf(true)).setWidth(Integer.valueOf(55));
        columns.add(acctDay);
        PenetrateColumn vchrType = this.createStringCloumn("VCHRTYPE", this.bdeI18nHelper.getMessageByColumnCode("VCHRTYPE")).setFixed(Boolean.valueOf(true)).setWidth(Integer.valueOf(150));
        columns.add(vchrType);
        PenetrateColumn cashCode = this.createStringCloumn("CASHCODE", this.bdeI18nHelper.getMessageByColumnCode("CASHCODE")).setFixed(Boolean.valueOf(true)).setWidth(Integer.valueOf(135));
        columns.add(cashCode);
        PenetrateColumn cashName = this.createStringCloumn("CASHNAME", this.bdeI18nHelper.getMessageByColumnCode("CASHNAME")).setFixed(Boolean.valueOf(true)).setWidth(null).setMinWidth(Integer.valueOf(200));
        columns.add(cashName);
        if (!StringUtils.isEmpty((String)condi.getSubjectCode())) {
            PenetrateColumn subjectCode = this.createStringCloumn("SUBJECTCODE", this.bdeI18nHelper.getMessageByColumnCode("SUBJECTCODE")).setFixed(Boolean.valueOf(true)).setWidth(Integer.valueOf(135));
            columns.add(subjectCode);
            PenetrateColumn subjectName = this.createStringCloumn("SUBJECTNAME", this.bdeI18nHelper.getMessageByColumnCode("SUBJECTNAME")).setFixed(Boolean.valueOf(true)).setWidth(null).setMinWidth(Integer.valueOf(200));
            columns.add(subjectName);
        }
        if (!CollectionUtils.isEmpty((Collection)assTypeList)) {
            assTypeList.forEach(item -> {
                String name = assistDimMap.get(item.getDimCode()) == null ? item.getDimCode() : ((DimensionVO)assistDimMap.get(item.getDimCode())).getTitle();
                columns.add(this.createStringCloumn(item.getDimCode(), this.bdeI18nHelper.getMessage(item.getDimCode() + "_CODE", name + "\u4ee3\u7801")).setFixed(Boolean.valueOf(true)).setWidth(Integer.valueOf(135)));
                columns.add(this.createStringCloumn(ModelExecuteUtil.getAssistFieldName((String)item.getDimCode()), this.bdeI18nHelper.getMessage(item.getDimCode() + "_NAME", name + "\u540d\u79f0")).setFixed(Boolean.valueOf(true)).setWidth(Integer.valueOf(200)));
            });
        }
        PenetrateColumn digest = this.createStringCloumn("DIGEST", this.bdeI18nHelper.getMessageByColumnCode("DIGEST")).setFixed(Boolean.valueOf(true)).setWidth(null).setMinWidth(Integer.valueOf(300));
        columns.add(digest);
        PenetrateColumn bq = this.createParentCloumn("BQ", this.bdeI18nHelper.getMessageByColumnCode("BQ"));
        columns.add(bq);
        bq.addChild(this.createNumericCloumn("DEBIT", this.bdeI18nHelper.getMessageByColumnCode("DEBIT")));
        bq.addChild(this.createNumericCloumn("CREDIT", this.bdeI18nHelper.getMessageByColumnCode("CREDIT")));
        PenetrateColumn ye = this.createParentCloumn("YE", this.bdeI18nHelper.getMessageByColumnCode("YE"));
        columns.add(ye);
        ye.addChild(this.createStringCloumn("YE_ORIENT", this.bdeI18nHelper.getMessageByColumnCode("ORIENT")).setAlignment(ColumnAlignmentEnum.CENTER.getCode()).setWidth(Integer.valueOf(60)));
        ye.addChild(this.createNumericCloumn("YE", this.bdeI18nHelper.getMessageByColumnCode("AMNT")));
        if (showOrgnAmnt) {
            bq.addChild(this.createNumericCloumn("ORGND", this.bdeI18nHelper.getMessageByColumnCode("ORGND")));
            bq.addChild(this.createNumericCloumn("ORGNC", this.bdeI18nHelper.getMessageByColumnCode("ORGNC")));
            ye.addChild(this.createNumericCloumn("ORGNYE", this.bdeI18nHelper.getMessageByColumnCode("ORGN")));
        }
        return columns;
    }
}

