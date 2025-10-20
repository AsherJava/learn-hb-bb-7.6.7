/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.impl.dimension.util.BdeAssistDimUtils
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 *  com.jiuqi.bde.common.dto.OrgMappingItem
 *  com.jiuqi.bde.common.i18n.util.BdeI18nHelper
 *  com.jiuqi.bde.common.intf.PenetrateColumn
 *  com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 */
package com.jiuqi.bde.penetrate.impl.core.columns;

import com.jiuqi.bde.bizmodel.impl.dimension.util.BdeAssistDimUtils;
import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.bde.common.dto.OrgMappingItem;
import com.jiuqi.bde.common.i18n.util.BdeI18nHelper;
import com.jiuqi.bde.common.intf.PenetrateColumn;
import com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO;
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
public class AbstractVchrBalancePenetrateColumnBuilder
extends AbstractPenetrateColumnBuilder<PenetrateBaseDTO> {
    @Autowired
    private BdeI18nHelper bdeI18nHelper;

    @Override
    public String getBizModel() {
        return ComputationModelEnum.VOUCHER.getCode();
    }

    @Override
    public PenetrateTypeEnum getPenetrateType() {
        return PenetrateTypeEnum.BALANCE;
    }

    @Override
    public List<PenetrateColumn> createQueryColumns(PenetrateBaseDTO condi) {
        List assTypeList;
        ArrayList<PenetrateColumn> columns = new ArrayList<PenetrateColumn>();
        Map<String, DimensionVO> assistDimMap = BdeAssistDimUtils.listAssistDim().stream().collect(Collectors.toMap(DimensionVO::getCode, item -> item, (k1, k2) -> k2));
        PenetrateColumn subjectCode = this.createStringCloumn("SUBJECTCODE", this.bdeI18nHelper.getMessageByColumnCode("SUBJECTCODE")).setFixed(Boolean.valueOf(true)).setWidth(Integer.valueOf(135));
        columns.add(subjectCode);
        PenetrateColumn subjectName = this.createStringCloumn("SUBJECTNAME", this.bdeI18nHelper.getMessageByColumnCode("SUBJECTNAME")).setFixed(Boolean.valueOf(true)).setWidth(null).setMinWidth(Integer.valueOf(200));
        columns.add(subjectName);
        if (!CollectionUtils.isEmpty((Collection)condi.getOrgMapping().getOrgMappingItems())) {
            if (!StringUtils.isEmpty((String)((OrgMappingItem)condi.getOrgMapping().getOrgMappingItems().get(0)).getAcctOrgCode())) {
                // empty if block
            }
            if (!StringUtils.isEmpty((String)((OrgMappingItem)condi.getOrgMapping().getOrgMappingItems().get(0)).getAssistCode())) {
                // empty if block
            }
        }
        if (!CollectionUtils.isEmpty((Collection)(assTypeList = condi.getAssTypeList()))) {
            assTypeList.forEach(item -> {
                String name = assistDimMap.get(item.getDimCode()) == null ? item.getDimCode() : ((DimensionVO)assistDimMap.get(item.getDimCode())).getTitle();
                columns.add(this.createStringCloumn(item.getDimCode(), this.bdeI18nHelper.getMessage(item.getDimCode() + "_CODE", name + "\u4ee3\u7801")).setFixed(Boolean.valueOf(true)).setWidth(Integer.valueOf(135)));
                columns.add(this.createStringCloumn(item.getDimCode() + "_NAME", this.bdeI18nHelper.getMessage(item.getDimCode() + "_NAME", name + "\u540d\u79f0")).setFixed(Boolean.valueOf(true)).setWidth(Integer.valueOf(150)));
            });
        }
        PenetrateColumn bq = this.createParentCloumn("BQ", this.bdeI18nHelper.getMessageByColumnCode("BQ"));
        columns.add(bq);
        bq.addChild(this.createNumericCloumn("DEBIT", this.bdeI18nHelper.getMessageByColumnCode("DEBIT")));
        bq.addChild(this.createNumericCloumn("CREDIT", this.bdeI18nHelper.getMessageByColumnCode("CREDIT")));
        bq.addChild(this.createNumericCloumn("ORGND", this.bdeI18nHelper.getMessageByColumnCode("ORGND")));
        bq.addChild(this.createNumericCloumn("ORGNC", this.bdeI18nHelper.getMessageByColumnCode("ORGNC")));
        PenetrateColumn lj = this.createParentCloumn("LJ", this.bdeI18nHelper.getMessageByColumnCode("LJ"));
        columns.add(lj);
        lj.addChild(this.createNumericCloumn("DSUM", this.bdeI18nHelper.getMessageByColumnCode("DSUM")));
        lj.addChild(this.createNumericCloumn("CSUM", this.bdeI18nHelper.getMessageByColumnCode("CSUM")));
        lj.addChild(this.createNumericCloumn("ORGNDSUM", this.bdeI18nHelper.getMessageByColumnCode("ORGNDSUM")));
        lj.addChild(this.createNumericCloumn("ORGNCSUM", this.bdeI18nHelper.getMessageByColumnCode("ORGNCSUM")));
        return columns;
    }
}

