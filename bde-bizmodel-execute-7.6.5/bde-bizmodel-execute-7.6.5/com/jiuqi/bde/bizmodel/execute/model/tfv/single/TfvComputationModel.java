/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.intf.IBizComputationModel
 *  com.jiuqi.bde.bizmodel.define.datamodel.TfvDataModel
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 *  com.jiuqi.bde.common.constant.FetchFixedFieldEnum
 *  com.jiuqi.bde.common.constant.OptionItemEnum
 *  com.jiuqi.bde.common.dto.ColumnDefineVO
 *  com.jiuqi.bde.common.dto.FetchSettingVO
 *  com.jiuqi.bde.common.dto.SelectOptionVO
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 */
package com.jiuqi.bde.bizmodel.execute.model.tfv.single;

import com.jiuqi.bde.bizmodel.client.intf.IBizComputationModel;
import com.jiuqi.bde.bizmodel.define.datamodel.TfvDataModel;
import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.bde.common.constant.FetchFixedFieldEnum;
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

public class TfvComputationModel
implements IBizComputationModel {
    @Autowired
    private TfvDataModel dataModel;

    public String getCode() {
        return ComputationModelEnum.TFV.getCode();
    }

    public String getName() {
        return ComputationModelEnum.TFV.getName();
    }

    public String getIcon() {
        return "#icon-a-16_TY_E_GC_zidingyisqlyewumoxing";
    }

    public String getBizDataCode() {
        return this.dataModel.getCode();
    }

    public int getOrder() {
        return 11;
    }

    public List<SelectOptionVO> getFetchTypes() {
        return null;
    }

    public List<ColumnDefineVO> getFixedFields() {
        ArrayList<ColumnDefineVO> fixedFields = new ArrayList<ColumnDefineVO>();
        fixedFields.add(this.buildSignColumn());
        fixedFields.add(new ColumnDefineVO(FetchFixedFieldEnum.FORMULA.getCode(), GcI18nUtil.getMessage((String)"bde.fixe.column.formula"), true, null, "INPUT", null, null));
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
        dimComb.put(OptionItemEnum.DATASOURCECODE.getCode(), StringUtils.isEmpty((String)fetchSettingDes.getDataSourceCode()) ? "" : fetchSettingDes.getDataSourceCode());
        return JsonUtils.writeValueAsString(dimComb);
    }

    public String getMemo(FetchSettingVO fetchSetting) {
        StringBuilder memo = new StringBuilder();
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
        memo.append("\u53d6\u6570SQL:").append(fetchSetting.getFormula());
        return memo.toString();
    }
}

