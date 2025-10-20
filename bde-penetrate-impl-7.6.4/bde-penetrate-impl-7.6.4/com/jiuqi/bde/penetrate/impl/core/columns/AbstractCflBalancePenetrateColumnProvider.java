/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.impl.dimension.util.BdeAssistDimUtils
 *  com.jiuqi.bde.common.i18n.util.BdeI18nHelper
 *  com.jiuqi.bde.common.intf.PenetrateColumn
 *  com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 */
package com.jiuqi.bde.penetrate.impl.core.columns;

import com.jiuqi.bde.bizmodel.impl.dimension.util.BdeAssistDimUtils;
import com.jiuqi.bde.common.i18n.util.BdeI18nHelper;
import com.jiuqi.bde.common.intf.PenetrateColumn;
import com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO;
import com.jiuqi.bde.penetrate.impl.common.PenetrateTypeEnum;
import com.jiuqi.bde.penetrate.impl.core.AbstractPenetrateColumnBuilder;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractCflBalancePenetrateColumnProvider
extends AbstractPenetrateColumnBuilder<PenetrateBaseDTO> {
    @Autowired
    private BdeI18nHelper bdeI18nHelper;

    @Override
    public PenetrateTypeEnum getPenetrateType() {
        return PenetrateTypeEnum.BALANCE;
    }

    @Override
    public List<PenetrateColumn> createQueryColumns(PenetrateBaseDTO condi) {
        ArrayList<PenetrateColumn> columns = new ArrayList<PenetrateColumn>();
        Map<String, DimensionVO> assistDimMap = BdeAssistDimUtils.listAssistDim().stream().collect(Collectors.toMap(DimensionVO::getCode, item -> item, (k1, k2) -> k2));
        boolean showOrgnAmnt = Boolean.TRUE.equals(condi.getShowOrgnAmnt());
        PenetrateColumn subjectCode = this.createStringCloumn("SUBJECTCODE", this.bdeI18nHelper.getMessageByColumnCode("SUBJECTCODE")).setFixed(Boolean.valueOf(true)).setWidth(Integer.valueOf(135));
        columns.add(subjectCode);
        PenetrateColumn subjectName = this.createStringCloumn("SUBJECTNAME", this.bdeI18nHelper.getMessageByColumnCode("SUBJECTNAME")).setFixed(Boolean.valueOf(true)).setWidth(null).setMinWidth(Integer.valueOf(200));
        columns.add(subjectName);
        List assTypeList = condi.getAssTypeList();
        if (!CollectionUtils.isEmpty((Collection)assTypeList)) {
            assTypeList.forEach(item -> {
                String name = assistDimMap.get(item.getDimCode()) == null ? item.getDimCode() : ((DimensionVO)assistDimMap.get(item.getDimCode())).getTitle();
                columns.add(this.createStringCloumn(item.getDimCode(), this.bdeI18nHelper.getMessage(item.getDimCode() + "_CODE", name + "\u4ee3\u7801")).setFixed(Boolean.valueOf(true)).setWidth(Integer.valueOf(135)));
                columns.add(this.createStringCloumn(item.getDimCode() + "_NAME", this.bdeI18nHelper.getMessage(item.getDimCode() + "_NAME", name + "\u540d\u79f0")).setFixed(Boolean.valueOf(true)).setWidth(Integer.valueOf(200)));
            });
        }
        PenetrateColumn nc = this.createParentCloumn("NC", this.bdeI18nHelper.getMessageByColumnCode("NC"));
        columns.add(nc);
        nc.addChild(this.createNumericCloumn("JNC", this.bdeI18nHelper.getMessageByColumnCode("JNC")));
        nc.addChild(this.createNumericCloumn("DNC", this.bdeI18nHelper.getMessageByColumnCode("DNC")));
        if (showOrgnAmnt) {
            nc.addChild(this.createNumericCloumn("WJNC", this.bdeI18nHelper.getMessageByColumnCode("WJNC")));
            nc.addChild(this.createNumericCloumn("WDNC", this.bdeI18nHelper.getMessageByColumnCode("WDNC")));
        }
        PenetrateColumn lj = this.createParentCloumn("LJ", this.bdeI18nHelper.getMessageByColumnCode("LJ"));
        columns.add(lj);
        lj.addChild(this.createNumericCloumn("DSUM", this.bdeI18nHelper.getMessageByColumnCode("DSUM")));
        lj.addChild(this.createNumericCloumn("CSUM", this.bdeI18nHelper.getMessageByColumnCode("CSUM")));
        if (showOrgnAmnt) {
            lj.addChild(this.createNumericCloumn("ORGNDSUM", this.bdeI18nHelper.getMessageByColumnCode("ORGNDSUM")));
            lj.addChild(this.createNumericCloumn("ORGNCSUM", this.bdeI18nHelper.getMessageByColumnCode("ORGNCSUM")));
        }
        PenetrateColumn ye = this.createParentCloumn("YE", this.bdeI18nHelper.getMessageByColumnCode("YE"));
        columns.add(ye);
        ye.addChild(this.createNumericCloumn("JYH", this.bdeI18nHelper.getMessageByColumnCode("JYH")));
        ye.addChild(this.createNumericCloumn("DYH", this.bdeI18nHelper.getMessageByColumnCode("DYH")));
        if (showOrgnAmnt) {
            ye.addChild(this.createNumericCloumn(this.bdeI18nHelper.getMessageByColumnCode("WJYH"), this.bdeI18nHelper.getMessageByColumnCode("WJYH")));
            ye.addChild(this.createNumericCloumn(this.bdeI18nHelper.getMessageByColumnCode("WDYH"), this.bdeI18nHelper.getMessageByColumnCode("WDYH")));
        }
        return columns;
    }
}

