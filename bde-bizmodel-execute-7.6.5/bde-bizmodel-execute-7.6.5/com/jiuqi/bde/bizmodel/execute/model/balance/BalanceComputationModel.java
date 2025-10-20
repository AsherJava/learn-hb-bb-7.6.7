/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.define.AbstractFinComputationModel
 *  com.jiuqi.bde.bizmodel.define.datamodel.BalanceDataModel
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 *  com.jiuqi.bde.common.constant.FetchFixedFieldEnum
 *  com.jiuqi.bde.common.constant.FetchTypeEnum
 *  com.jiuqi.bde.common.constant.OptionItemEnum
 *  com.jiuqi.bde.common.dto.ColumnDefineVO
 *  com.jiuqi.bde.common.dto.FetchSettingVO
 *  com.jiuqi.bde.common.dto.SelectOptionVO
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 */
package com.jiuqi.bde.bizmodel.execute.model.balance;

import com.jiuqi.bde.bizmodel.define.AbstractFinComputationModel;
import com.jiuqi.bde.bizmodel.define.datamodel.BalanceDataModel;
import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.bde.common.constant.FetchFixedFieldEnum;
import com.jiuqi.bde.common.constant.FetchTypeEnum;
import com.jiuqi.bde.common.constant.OptionItemEnum;
import com.jiuqi.bde.common.dto.ColumnDefineVO;
import com.jiuqi.bde.common.dto.FetchSettingVO;
import com.jiuqi.bde.common.dto.SelectOptionVO;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class BalanceComputationModel
extends AbstractFinComputationModel {
    @Autowired
    private BalanceDataModel balanceDataModel;

    public String getCode() {
        return ComputationModelEnum.BALANCE.getCode();
    }

    public String getBizDataCode() {
        return this.balanceDataModel.getCode();
    }

    public String getName() {
        return ComputationModelEnum.BALANCE.getName();
    }

    public String getIcon() {
        return "#icon-a-16_TY_E_GC_kemuyuebiao";
    }

    public int getOrder() {
        return 10;
    }

    public List<SelectOptionVO> getFetchTypes() {
        ArrayList<FetchTypeEnum> fetchTypeEnums = new ArrayList<FetchTypeEnum>();
        fetchTypeEnums.add(FetchTypeEnum.NC);
        fetchTypeEnums.add(FetchTypeEnum.C);
        fetchTypeEnums.add(FetchTypeEnum.JF);
        fetchTypeEnums.add(FetchTypeEnum.DF);
        fetchTypeEnums.add(FetchTypeEnum.JL);
        fetchTypeEnums.add(FetchTypeEnum.DL);
        fetchTypeEnums.add(FetchTypeEnum.YE);
        fetchTypeEnums.add(FetchTypeEnum.WNC);
        fetchTypeEnums.add(FetchTypeEnum.WC);
        fetchTypeEnums.add(FetchTypeEnum.WJF);
        fetchTypeEnums.add(FetchTypeEnum.WDF);
        fetchTypeEnums.add(FetchTypeEnum.WJL);
        fetchTypeEnums.add(FetchTypeEnum.WDL);
        fetchTypeEnums.add(FetchTypeEnum.WYE);
        fetchTypeEnums.add(FetchTypeEnum.BQNUM);
        fetchTypeEnums.add(FetchTypeEnum.LJNUM);
        fetchTypeEnums.add(FetchTypeEnum.WBQNUM);
        fetchTypeEnums.add(FetchTypeEnum.WLJNUM);
        return this.getFetchTypesByFetchTypeEnums(fetchTypeEnums);
    }

    public List<ColumnDefineVO> getFixedFields() {
        ArrayList<ColumnDefineVO> fixedFields = new ArrayList<ColumnDefineVO>();
        fixedFields.add(this.buildSignColumn());
        fixedFields.add(this.buildFetchTypeColumn());
        fixedFields.add(new ColumnDefineVO(FetchFixedFieldEnum.SUBJECTCODE.getCode(), GcI18nUtil.getMessage((String)"bde.fixe.column.subjectCode"), true, "SINGLE,MULTIPLE,RANGE", "INPUT", null, null));
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
        LinkedHashMap<String, String> dimComb = new LinkedHashMap<String, String>(16);
        dimComb.put(OptionItemEnum.ACCTYEAR.getCode(), StringUtils.isEmpty((String)fetchSettingDes.getAcctYear()) ? "" : fetchSettingDes.getAcctYear());
        dimComb.put(OptionItemEnum.ACCTPERIOD.getCode(), StringUtils.isEmpty((String)fetchSettingDes.getAcctPeriod()) ? "" : fetchSettingDes.getAcctPeriod());
        dimComb.put(OptionItemEnum.ORGCODE.getCode(), StringUtils.isEmpty((String)fetchSettingDes.getOrgCode()) ? "" : fetchSettingDes.getOrgCode());
        dimComb.put(OptionItemEnum.DATASOURCECODE.getCode(), StringUtils.isEmpty((String)fetchSettingDes.getDataSourceCode()) ? "" : fetchSettingDes.getDataSourceCode());
        return JsonUtils.writeValueAsString(dimComb);
    }

    public String getMemo(FetchSettingVO fetchSetting) {
        StringBuilder memo = new StringBuilder();
        memo.append(FetchTypeEnum.getEnumByCode((String)fetchSetting.getFetchType()).getName()).append(",");
        memo.append(this.buildSubjectMemo(fetchSetting));
        memo.append(this.buildExcludeSubjectMemo(fetchSetting));
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

