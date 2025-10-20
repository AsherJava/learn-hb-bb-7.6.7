/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.util.ModelExecuteUtil
 *  com.jiuqi.bde.bizmodel.impl.dimension.util.BdeAssistDimUtils
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 *  com.jiuqi.bde.common.dto.OrgMappingItem
 *  com.jiuqi.bde.common.i18n.util.BdeI18nHelper
 *  com.jiuqi.bde.common.intf.PenetrateColumn
 *  com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 */
package com.jiuqi.bde.penetrate.impl.core.columns;

import com.jiuqi.bde.bizmodel.execute.util.ModelExecuteUtil;
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
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class XjllBalancePenetrateColumnProvider
extends AbstractPenetrateColumnBuilder<PenetrateBaseDTO> {
    @Autowired
    private BdeI18nHelper bdeI18nHelper;
    @Autowired
    protected INvwaSystemOptionService optionService;

    @Override
    public String getBizModel() {
        return ComputationModelEnum.XJLLBALANCE.getCode();
    }

    @Override
    public PenetrateTypeEnum getPenetrateType() {
        return PenetrateTypeEnum.BALANCE;
    }

    @Override
    public List<PenetrateColumn> createQueryColumns(PenetrateBaseDTO condi) {
        List assTypeList;
        ArrayList<PenetrateColumn> columns = new ArrayList<PenetrateColumn>();
        if (this.supportPenetrateShowMultiOrg(condi.getOrgMapping().getPluginType()) && condi.getOrgMapping() != null && !CollectionUtils.isEmpty((Collection)condi.getOrgMapping().getOrgMappingItems())) {
            if (!StringUtils.isEmpty((String)((OrgMappingItem)condi.getOrgMapping().getOrgMappingItems().get(0)).getAcctOrgCode())) {
                PenetrateColumn acctOrgCode = this.createStringCloumn("ACCTORGCODE", this.bdeI18nHelper.getMessageByColumnCode("ACCTORGCODE")).setFixed(Boolean.valueOf(true)).setWidth(Integer.valueOf(135));
                columns.add(acctOrgCode);
                PenetrateColumn acctOrgName = this.createStringCloumn("ACCTORGNAME", this.bdeI18nHelper.getMessageByColumnCode("ACCTORGNAME")).setFixed(Boolean.valueOf(true)).setWidth(null).setMinWidth(Integer.valueOf(200));
                columns.add(acctOrgName);
            }
            if (!StringUtils.isEmpty((String)((OrgMappingItem)condi.getOrgMapping().getOrgMappingItems().get(0)).getAssistCode())) {
                PenetrateColumn assistCode = this.createStringCloumn("ASSISTCODE", this.bdeI18nHelper.getMessageByColumnCode("ASSISTCODE")).setFixed(Boolean.valueOf(true)).setWidth(Integer.valueOf(135));
                columns.add(assistCode);
                PenetrateColumn assistName = this.createStringCloumn("ASSISTNAME", this.bdeI18nHelper.getMessageByColumnCode("ASSISTNAME")).setFixed(Boolean.valueOf(true)).setWidth(null).setMinWidth(Integer.valueOf(200));
                columns.add(assistName);
            }
        }
        Map<String, DimensionVO> assistDimMap = BdeAssistDimUtils.listAssistDim().stream().collect(Collectors.toMap(DimensionVO::getCode, item -> item, (k1, k2) -> k2));
        if (Boolean.TRUE.equals(condi.getIncludeAdjustVchr())) {
            PenetrateColumn vchrSrcType = this.createStringCloumn("VCHRSRCTYPE", this.bdeI18nHelper.getMessageByColumnCode("VCHRSRCTYPE")).setFixed(Boolean.valueOf(true)).setWidth(Integer.valueOf(135));
            columns.add(vchrSrcType);
        }
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
        if (!CollectionUtils.isEmpty((Collection)(assTypeList = condi.getAssTypeList()))) {
            assTypeList.forEach(item -> {
                String name = assistDimMap.get(item.getDimCode()) == null ? item.getDimCode() : ((DimensionVO)assistDimMap.get(item.getDimCode())).getTitle();
                columns.add(this.createStringCloumn(item.getDimCode(), this.bdeI18nHelper.getMessage(item.getDimCode() + "_CODE", name + "\u4ee3\u7801")).setFixed(Boolean.valueOf(true)).setWidth(Integer.valueOf(135)));
                columns.add(this.createStringCloumn(ModelExecuteUtil.getAssistFieldName((String)item.getDimCode()), this.bdeI18nHelper.getMessage(item.getDimCode() + "_NAME", name + "\u540d\u79f0")).setFixed(Boolean.valueOf(true)).setWidth(Integer.valueOf(200)));
            });
        }
        PenetrateColumn bqNum = this.createNumericCloumn("BQNUM", this.bdeI18nHelper.getMessageByColumnCode("BQNUM"));
        columns.add(bqNum);
        PenetrateColumn ljNum = this.createNumericCloumn("LJNUM", this.bdeI18nHelper.getMessageByColumnCode("LJNUM"));
        columns.add(ljNum);
        PenetrateColumn wbqNum = this.createNumericCloumn("WBQNUM", this.bdeI18nHelper.getMessageByColumnCode("WBQNUM"));
        columns.add(wbqNum);
        PenetrateColumn wljNum = this.createNumericCloumn("WLJNUM", this.bdeI18nHelper.getMessageByColumnCode("WLJNUM"));
        columns.add(wljNum);
        return columns;
    }
}

