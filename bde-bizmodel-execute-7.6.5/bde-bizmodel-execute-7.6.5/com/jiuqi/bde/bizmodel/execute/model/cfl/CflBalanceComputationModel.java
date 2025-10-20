/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.define.AbstractFinComputationModel
 *  com.jiuqi.bde.bizmodel.define.datamodel.AssBalanceDataModel
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 *  com.jiuqi.bde.common.constant.FetchFixedFieldEnum
 *  com.jiuqi.bde.common.constant.FetchTypeEnum
 *  com.jiuqi.bde.common.constant.OptionItemEnum
 *  com.jiuqi.bde.common.constant.SumTypeEnum
 *  com.jiuqi.bde.common.dto.ColumnDefineVO
 *  com.jiuqi.bde.common.dto.FetchSettingVO
 *  com.jiuqi.bde.common.dto.SelectOptionVO
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 */
package com.jiuqi.bde.bizmodel.execute.model.cfl;

import com.jiuqi.bde.bizmodel.define.AbstractFinComputationModel;
import com.jiuqi.bde.bizmodel.define.datamodel.AssBalanceDataModel;
import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.bde.common.constant.FetchFixedFieldEnum;
import com.jiuqi.bde.common.constant.FetchTypeEnum;
import com.jiuqi.bde.common.constant.OptionItemEnum;
import com.jiuqi.bde.common.constant.SumTypeEnum;
import com.jiuqi.bde.common.dto.ColumnDefineVO;
import com.jiuqi.bde.common.dto.FetchSettingVO;
import com.jiuqi.bde.common.dto.SelectOptionVO;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;

public class CflBalanceComputationModel
extends AbstractFinComputationModel {
    @Autowired
    private AssBalanceDataModel dataModel;

    public String getCode() {
        return ComputationModelEnum.CFLBALANCE.getCode();
    }

    public String getName() {
        return ComputationModelEnum.CFLBALANCE.getName();
    }

    public String getIcon() {
        return "#icon-a-16_TY_E_GC_zhongfenleiyuebiao";
    }

    public String getBizDataCode() {
        return this.dataModel.getCode();
    }

    public int getOrder() {
        return 40;
    }

    public List<SelectOptionVO> getFetchTypes() {
        ArrayList<FetchTypeEnum> fetchTypeEnums = new ArrayList<FetchTypeEnum>();
        fetchTypeEnums.add(FetchTypeEnum.JNC);
        fetchTypeEnums.add(FetchTypeEnum.DNC);
        fetchTypeEnums.add(FetchTypeEnum.JYH);
        fetchTypeEnums.add(FetchTypeEnum.DYH);
        if (this.enableOrgnCurrency().booleanValue()) {
            fetchTypeEnums.add(FetchTypeEnum.WJNC);
            fetchTypeEnums.add(FetchTypeEnum.WDNC);
            fetchTypeEnums.add(FetchTypeEnum.WJYH);
            fetchTypeEnums.add(FetchTypeEnum.WDYH);
        }
        return this.getFetchTypesByFetchTypeEnums(fetchTypeEnums);
    }

    protected Boolean enableOrgnCurrency() {
        return true;
    }

    public List<ColumnDefineVO> getFixedFields() {
        ArrayList<ColumnDefineVO> fixedFields = new ArrayList<ColumnDefineVO>();
        fixedFields.add(this.buildSignColumn());
        fixedFields.add(this.buildFetchTypeColumn());
        ArrayList<SelectOptionVO> dimTypeSelectOptions = new ArrayList<SelectOptionVO>();
        dimTypeSelectOptions.add(new SelectOptionVO("SUBJECTCODE", "\u79d1\u76ee"));
        dimTypeSelectOptions.addAll(this.listDimensionOptionByBizDataModelCode(this.getBizDataCode()));
        fixedFields.add(new ColumnDefineVO(FetchFixedFieldEnum.DIMTYPE.getCode(), GcI18nUtil.getMessage((String)"bde.fixe.column.dimType"), true, "MULTIPLE", "SELECT", null, dimTypeSelectOptions));
        fixedFields.add(this.buildSumTypeColumn());
        fixedFields.add(new ColumnDefineVO(FetchFixedFieldEnum.SUBJECTCODE.getCode(), GcI18nUtil.getMessage((String)"bde.fixe.column.reclass"), true, "SINGLE", "INPUT", null, null));
        fixedFields.add(new ColumnDefineVO(FetchFixedFieldEnum.EXCLUDESUBJECTCODE.getCode(), GcI18nUtil.getMessage((String)"bde.fixe.column.excludeSubjectCode"), false, "SINGLE,MULTIPLE", "INPUT", null, null));
        return fixedFields;
    }

    public List<SelectOptionVO> getOptionItems() {
        ArrayList<OptionItemEnum> optionItems = new ArrayList<OptionItemEnum>();
        optionItems.add(OptionItemEnum.ACCTYEAR);
        optionItems.add(OptionItemEnum.ACCTPERIOD);
        optionItems.add(OptionItemEnum.ORGCODE);
        optionItems.add(OptionItemEnum.CURRENCYCODE);
        optionItems.add(OptionItemEnum.DATASOURCECODE);
        return this.getOptionItemsByOptionItemEnums(optionItems);
    }

    public List<SelectOptionVO> getDimensions() {
        return null;
    }

    public String getOptimizeRuleGroup(FetchSettingVO fetchSettingDes) {
        LinkedHashMap<String, String> dimCombs = new LinkedHashMap<String, String>(16);
        dimCombs.put(OptionItemEnum.ACCTYEAR.getCode(), StringUtils.isEmpty((String)fetchSettingDes.getAcctYear()) ? "" : fetchSettingDes.getAcctYear());
        dimCombs.put(OptionItemEnum.ACCTPERIOD.getCode(), StringUtils.isEmpty((String)fetchSettingDes.getAcctPeriod()) ? "" : fetchSettingDes.getAcctPeriod());
        dimCombs.put(OptionItemEnum.ORGCODE.getCode(), StringUtils.isEmpty((String)fetchSettingDes.getOrgCode()) ? "" : fetchSettingDes.getOrgCode());
        dimCombs.put(FetchFixedFieldEnum.DIMTYPE.getCode(), StringUtils.isEmpty((String)fetchSettingDes.getDimType()) ? "" : fetchSettingDes.getDimType());
        dimCombs.put(OptionItemEnum.DATASOURCECODE.getCode(), StringUtils.isEmpty((String)fetchSettingDes.getDataSourceCode()) ? "" : fetchSettingDes.getDataSourceCode());
        return JsonUtils.writeValueAsString(dimCombs);
    }

    public String getMemo(FetchSettingVO fetchSetting) {
        StringBuilder memo = new StringBuilder();
        memo.append(FetchTypeEnum.getEnumByCode((String)fetchSetting.getFetchType()).getName()).append(",");
        if (!StringUtils.isEmpty((String)fetchSetting.getDimType())) {
            Map<String, String> dimNameMap = this.listDimensionByDataModel(this.dataModel.getCode()).stream().collect(Collectors.toMap(DimensionVO::getCode, DimensionVO::getTitle, (k1, k2) -> k2));
            dimNameMap.put("SUBJECTCODE", "\u79d1\u76ee");
            memo.append(FetchFixedFieldEnum.DIMTYPE.getName());
            String[] dimTypeArr = fetchSetting.getDimType().split(",");
            if (dimTypeArr.length == 1) {
                memo.append(StringUtils.isEmpty((String)dimNameMap.get(dimTypeArr[0])) ? dimTypeArr[0] : dimNameMap.get(dimTypeArr[0])).append(",");
            } else {
                memo.append("\"");
                for (String dimType : dimTypeArr) {
                    memo.append(StringUtils.isEmpty((String)dimNameMap.get(dimType)) ? dimType : dimNameMap.get(dimType)).append(":");
                }
                memo.delete(memo.length() - 1, memo.length());
                memo.append("\"");
                memo.append(",");
            }
        }
        memo.append(GcI18nUtil.getMessage((String)"bde.fixe.column.sumType")).append(SumTypeEnum.fromCode((String)fetchSetting.getSumType()).getName()).append(",");
        memo.append(this.buildGenericMemo("\u91cd\u5206\u7c7b\u79d1\u76ee", fetchSetting.getSubjectCode()));
        memo.append(this.buildExcludeSubjectMemo(fetchSetting));
        memo.append(this.buildDimMemo(fetchSetting));
        if (!StringUtils.isEmpty((String)fetchSetting.getAcctYear())) {
            memo.append(OptionItemEnum.ACCTYEAR.getName()).append(fetchSetting.getAcctYear()).append(",");
        }
        if (!StringUtils.isEmpty((String)fetchSetting.getAcctPeriod())) {
            memo.append(OptionItemEnum.ACCTPERIOD.getName()).append(fetchSetting.getAcctPeriod()).append(",");
        }
        if (!StringUtils.isEmpty((String)fetchSetting.getOrgCode())) {
            memo.append(OptionItemEnum.ORGCODE.getName()).append(fetchSetting.getOrgCode()).append(",");
        }
        if (!StringUtils.isEmpty((String)fetchSetting.getCurrencyCode())) {
            memo.append(OptionItemEnum.CURRENCYCODE.getName()).append(fetchSetting.getCurrencyCode()).append(",");
        }
        if (!StringUtils.isEmpty((String)fetchSetting.getDataSourceCode())) {
            memo.append(OptionItemEnum.DATASOURCECODE.getName()).append(fetchSetting.getDataSourceCode()).append(",");
        }
        memo.delete(memo.length() - 1, memo.length());
        return memo.toString();
    }
}

