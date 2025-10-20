/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.define.AbstractFinComputationModel
 *  com.jiuqi.bde.bizmodel.define.datamodel.XjllDataModel
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 *  com.jiuqi.bde.common.constant.FetchFixedFieldEnum
 *  com.jiuqi.bde.common.constant.FetchTypeEnum
 *  com.jiuqi.bde.common.constant.MatchRuleEnum
 *  com.jiuqi.bde.common.constant.OptionItemEnum
 *  com.jiuqi.bde.common.dto.ColumnDefineVO
 *  com.jiuqi.bde.common.dto.FetchSettingVO
 *  com.jiuqi.bde.common.dto.SelectOptionVO
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.va.domain.common.JSONUtil
 */
package com.jiuqi.bde.bizmodel.execute.model.xjll;

import com.jiuqi.bde.bizmodel.define.AbstractFinComputationModel;
import com.jiuqi.bde.bizmodel.define.datamodel.XjllDataModel;
import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.bde.common.constant.FetchFixedFieldEnum;
import com.jiuqi.bde.common.constant.FetchTypeEnum;
import com.jiuqi.bde.common.constant.MatchRuleEnum;
import com.jiuqi.bde.common.constant.OptionItemEnum;
import com.jiuqi.bde.common.dto.ColumnDefineVO;
import com.jiuqi.bde.common.dto.FetchSettingVO;
import com.jiuqi.bde.common.dto.SelectOptionVO;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.va.domain.common.JSONUtil;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

public class XjllBalanceComputationModel
extends AbstractFinComputationModel {
    @Autowired
    private XjllDataModel dataModel;

    public String getCode() {
        return ComputationModelEnum.XJLLBALANCE.getCode();
    }

    public String getName() {
        return ComputationModelEnum.XJLLBALANCE.getName();
    }

    public String getIcon() {
        return "#icon-a-16_TY_E_GC_xianjinliuliangyuebiao";
    }

    public String getBizDataCode() {
        return this.dataModel.getCode();
    }

    public int getOrder() {
        return 30;
    }

    public List<SelectOptionVO> getFetchTypes() {
        ArrayList<FetchTypeEnum> fetchTypeEnums = new ArrayList<FetchTypeEnum>();
        fetchTypeEnums.add(FetchTypeEnum.BQNUM);
        fetchTypeEnums.add(FetchTypeEnum.LJNUM);
        if (this.enableOrgnCurrency().booleanValue()) {
            fetchTypeEnums.add(FetchTypeEnum.WBQNUM);
            fetchTypeEnums.add(FetchTypeEnum.WLJNUM);
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
        fixedFields.add(new ColumnDefineVO(FetchFixedFieldEnum.CASHCODE.getCode(), GcI18nUtil.getMessage((String)"bde.fixe.column.cashCode"), true, "SINGLE,MULTIPLE,RANGE", "INPUT", null, null));
        fixedFields.add(new ColumnDefineVO(FetchFixedFieldEnum.SUBJECTCODE.getCode(), GcI18nUtil.getMessage((String)"bde.fixe.column.subjectCode"), false, "SINGLE", "INPUT", null, null));
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
        return this.listDimensionOptionByBizDataModelCode(this.getBizDataCode());
    }

    public String getOptimizeRuleGroup(FetchSettingVO fetchSettingDes) {
        LinkedHashMap<String, Object> dimCombs = new LinkedHashMap<String, Object>(16);
        dimCombs.put(OptionItemEnum.ACCTYEAR.getCode(), StringUtils.isEmpty((String)fetchSettingDes.getAcctYear()) ? "" : fetchSettingDes.getAcctYear());
        dimCombs.put(OptionItemEnum.ACCTPERIOD.getCode(), StringUtils.isEmpty((String)fetchSettingDes.getAcctPeriod()) ? "" : fetchSettingDes.getAcctPeriod());
        dimCombs.put(OptionItemEnum.ORGCODE.getCode(), StringUtils.isEmpty((String)fetchSettingDes.getOrgCode()) ? "" : fetchSettingDes.getOrgCode());
        dimCombs.put(OptionItemEnum.DATASOURCECODE.getCode(), StringUtils.isEmpty((String)fetchSettingDes.getDataSourceCode()) ? "" : fetchSettingDes.getDataSourceCode());
        LinkedHashMap<String, String> dimComb = new LinkedHashMap<String, String>(16);
        if (!StringUtils.isEmpty((String)fetchSettingDes.getSubjectCode())) {
            dimComb.put("SUBJECTCODE", MatchRuleEnum.LIKE.getCode());
        }
        if (!CollectionUtils.isEmpty(fetchSettingDes.getDimComb()) && !StringUtils.isEmpty((String)fetchSettingDes.getDimensionSetting())) {
            List dimJsonArray = JSONUtil.parseMapArray((String)fetchSettingDes.getDimensionSetting());
            for (String dimCode : fetchSettingDes.getDimComb()) {
                for (Map jsonMap : dimJsonArray) {
                    if (!dimCode.equalsIgnoreCase(jsonMap.get("dimCode").toString()) || jsonMap.get("dimValue") == null || StringUtils.isEmpty((String)((String)jsonMap.get("dimValue")))) continue;
                    dimComb.put(dimCode, null == jsonMap.get("dimRule") ? "" : jsonMap.get("dimRule"));
                }
            }
        }
        if (!dimComb.isEmpty()) {
            dimCombs.put("dimComb", dimComb);
        }
        return JsonUtils.writeValueAsString(dimCombs);
    }

    public String getMemo(FetchSettingVO fetchSetting) {
        StringBuilder memo = new StringBuilder();
        memo.append(FetchTypeEnum.getEnumByCode((String)fetchSetting.getFetchType()).getName()).append(",");
        memo.append(FetchFixedFieldEnum.CASHCODE.getName()).append(fetchSetting.getCashCode()).append(",");
        memo.append(this.buildSubjectMemo(fetchSetting));
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

