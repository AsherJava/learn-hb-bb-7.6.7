/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.impl.dimension.util.BdeAssistDimUtils
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

import com.jiuqi.bde.bizmodel.impl.dimension.util.BdeAssistDimUtils;
import com.jiuqi.bde.common.dto.OrgMappingItem;
import com.jiuqi.bde.common.i18n.util.BdeI18nHelper;
import com.jiuqi.bde.common.intf.PenetrateColumn;
import com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO;
import com.jiuqi.bde.penetrate.impl.common.ColumnAlignmentEnum;
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

public abstract class AbstractBalancePenetrateColumnBuilder
extends AbstractPenetrateColumnBuilder<PenetrateBaseDTO> {
    @Autowired
    private BdeI18nHelper bdeI18nHelper;
    @Autowired
    protected INvwaSystemOptionService optionService;

    @Override
    public List<PenetrateColumn> createQueryColumns(PenetrateBaseDTO condi) {
        List assTypeList;
        ArrayList<PenetrateColumn> columns = new ArrayList<PenetrateColumn>();
        Map<String, DimensionVO> assistDimMap = BdeAssistDimUtils.listAssistDim().stream().collect(Collectors.toMap(DimensionVO::getCode, item -> item, (k1, k2) -> k2));
        boolean showOrgnAmnt = Boolean.TRUE.equals(condi.getShowOrgnAmnt());
        if (Boolean.TRUE.equals(condi.getIncludeAdjustVchr())) {
            PenetrateColumn vchrSrcType = this.createStringCloumn("VCHRSRCTYPE", this.bdeI18nHelper.getMessageByColumnCode("VCHRSRCTYPE")).setFixed(Boolean.valueOf(true)).setWidth(Integer.valueOf(135));
            columns.add(vchrSrcType);
        }
        PenetrateColumn subjectCode = this.createStringCloumn("SUBJECTCODE", this.bdeI18nHelper.getMessageByColumnCode("SUBJECTCODE")).setFixed(Boolean.valueOf(true)).setWidth(Integer.valueOf(135));
        columns.add(subjectCode);
        PenetrateColumn subjectName = this.createStringCloumn("SUBJECTNAME", this.bdeI18nHelper.getMessageByColumnCode("SUBJECTNAME")).setFixed(Boolean.valueOf(true)).setWidth(null).setMinWidth(Integer.valueOf(200));
        columns.add(subjectName);
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
        if (!CollectionUtils.isEmpty((Collection)(assTypeList = condi.getAssTypeList()))) {
            assTypeList.forEach(item -> {
                String name = assistDimMap.get(item.getDimCode()) == null ? item.getDimCode() : ((DimensionVO)assistDimMap.get(item.getDimCode())).getTitle();
                columns.add(this.createStringCloumn(item.getDimCode(), this.bdeI18nHelper.getMessage(item.getDimCode() + "_CODE", name + "\u4ee3\u7801")).setFixed(Boolean.valueOf(true)).setWidth(Integer.valueOf(135)));
                columns.add(this.createStringCloumn(item.getDimCode() + "_NAME", this.bdeI18nHelper.getMessage(item.getDimCode() + "_NAME", name + "\u540d\u79f0")).setFixed(Boolean.valueOf(true)).setWidth(Integer.valueOf(200)));
            });
        }
        PenetrateColumn nc = this.createParentCloumn("NC", this.bdeI18nHelper.getMessageByColumnCode("NC"));
        columns.add(nc);
        nc.addChild(this.createStringCloumn("NCORIENT", this.bdeI18nHelper.getMessageByColumnCode("ORIENT")).setAlignment(ColumnAlignmentEnum.CENTER.getCode()).setWidth(Integer.valueOf(60)));
        nc.addChild(this.createNumericCloumn("NC", this.bdeI18nHelper.getMessageByColumnCode("AMNT")));
        if (showOrgnAmnt) {
            nc.addChild(this.createNumericCloumn("ORGNNC", this.bdeI18nHelper.getMessageByColumnCode("ORGN")));
        }
        PenetrateColumn qc = this.createParentCloumn("QC", this.bdeI18nHelper.getMessageByColumnCode("QC"));
        columns.add(qc);
        qc.addChild(this.createStringCloumn("QCORIENT", this.bdeI18nHelper.getMessageByColumnCode("ORIENT")).setAlignment(ColumnAlignmentEnum.CENTER.getCode()).setWidth(Integer.valueOf(60)));
        qc.addChild(this.createNumericCloumn("QC", this.bdeI18nHelper.getMessageByColumnCode("AMNT")));
        if (showOrgnAmnt) {
            qc.addChild(this.createNumericCloumn("ORGNQC", this.bdeI18nHelper.getMessageByColumnCode("ORGN")));
        }
        PenetrateColumn bq = this.createParentCloumn("BQ", this.bdeI18nHelper.getMessageByColumnCode("BQ"));
        columns.add(bq);
        bq.addChild(this.createNumericCloumn("DEBIT", this.bdeI18nHelper.getMessageByColumnCode("DEBIT")));
        bq.addChild(this.createNumericCloumn("CREDIT", this.bdeI18nHelper.getMessageByColumnCode("CREDIT")));
        if (showOrgnAmnt) {
            bq.addChild(this.createNumericCloumn("ORGND", this.bdeI18nHelper.getMessageByColumnCode("ORGND")));
            bq.addChild(this.createNumericCloumn("ORGNC", this.bdeI18nHelper.getMessageByColumnCode("ORGNC")));
        }
        PenetrateColumn lj = this.createParentCloumn("LJ", this.bdeI18nHelper.getMessageByColumnCode("LJ"));
        columns.add(lj);
        lj.addChild(this.createNumericCloumn("DSUM", this.bdeI18nHelper.getMessageByColumnCode("DSUM")));
        lj.addChild(this.createNumericCloumn("CSUM", this.bdeI18nHelper.getMessageByColumnCode("CSUM")));
        if (showOrgnAmnt) {
            lj.addChild(this.createNumericCloumn("ORGNDSUM", this.bdeI18nHelper.getMessageByColumnCode("ORGND")));
            lj.addChild(this.createNumericCloumn("ORGNCSUM", this.bdeI18nHelper.getMessageByColumnCode("ORGNC")));
        }
        PenetrateColumn ye = this.createParentCloumn("YE", this.bdeI18nHelper.getMessageByColumnCode("YE"));
        columns.add(ye);
        ye.addChild(this.createStringCloumn("YE_ORIENT", this.bdeI18nHelper.getMessageByColumnCode("ORIENT")).setAlignment(ColumnAlignmentEnum.CENTER.getCode()).setWidth(Integer.valueOf(60)));
        ye.addChild(this.createNumericCloumn("YE", this.bdeI18nHelper.getMessageByColumnCode("AMNT")));
        if (showOrgnAmnt) {
            ye.addChild(this.createNumericCloumn("ORGNYE", this.bdeI18nHelper.getMessageByColumnCode("ORGN")));
        }
        return columns;
    }
}

