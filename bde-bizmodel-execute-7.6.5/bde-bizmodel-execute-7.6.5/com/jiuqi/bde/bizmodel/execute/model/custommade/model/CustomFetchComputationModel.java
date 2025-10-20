/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.dto.CustomBizModelDTO
 *  com.jiuqi.bde.bizmodel.client.intf.IBizComputationModel
 *  com.jiuqi.bde.bizmodel.client.vo.SelectField
 *  com.jiuqi.bde.bizmodel.define.datamodel.CustomFetchDataModel
 *  com.jiuqi.bde.bizmodel.impl.model.service.BizModelService
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
package com.jiuqi.bde.bizmodel.execute.model.custommade.model;

import com.jiuqi.bde.bizmodel.client.dto.CustomBizModelDTO;
import com.jiuqi.bde.bizmodel.client.intf.IBizComputationModel;
import com.jiuqi.bde.bizmodel.client.vo.SelectField;
import com.jiuqi.bde.bizmodel.define.datamodel.CustomFetchDataModel;
import com.jiuqi.bde.bizmodel.impl.model.service.BizModelService;
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

public class CustomFetchComputationModel
implements IBizComputationModel {
    @Autowired
    private CustomFetchDataModel dataModel;
    @Autowired
    private BizModelService bizModeService;

    public String getCode() {
        return ComputationModelEnum.CUSTOMFETCH.getCode();
    }

    public String getName() {
        return ComputationModelEnum.CUSTOMFETCH.getName();
    }

    public String getIcon() {
        return "#icon-a-16_TY_E_GC_zidingyiqushu";
    }

    public String getBizDataCode() {
        return this.dataModel.getCode();
    }

    public int getOrder() {
        return 22;
    }

    public List<SelectOptionVO> getFetchTypes() {
        return null;
    }

    public List<SelectOptionVO> getDimensions() {
        return null;
    }

    public List<ColumnDefineVO> getFixedFields() {
        ArrayList<ColumnDefineVO> fixedFields = new ArrayList<ColumnDefineVO>();
        fixedFields.add(this.buildSignColumn());
        ColumnDefineVO fetchField = new ColumnDefineVO(FetchFixedFieldEnum.FETCHTYPE.getCode(), GcI18nUtil.getMessage((String)"bde.fixe.column.fetchField"), true, "SINGLE", "SELECT", null, null);
        fetchField.setWidth(Integer.valueOf(120));
        fixedFields.add(fetchField);
        return fixedFields;
    }

    public List<SelectOptionVO> getOptionItems() {
        ArrayList<OptionItemEnum> optionItems = new ArrayList<OptionItemEnum>();
        optionItems.add(OptionItemEnum.DATASOURCECODE);
        return this.getOptionItemsByOptionItemEnums(optionItems);
    }

    public String getOptimizeRuleGroup(FetchSettingVO fetchSetting) {
        LinkedHashMap<String, String> groupMap = new LinkedHashMap<String, String>();
        groupMap.put("FETCH_SOURCE_CODE", fetchSetting.getFetchSourceCode());
        groupMap.put(OptionItemEnum.DATASOURCECODE.getCode(), StringUtils.isEmpty((String)fetchSetting.getDataSourceCode()) ? "" : fetchSetting.getDataSourceCode());
        return JsonUtils.writeValueAsString(groupMap);
    }

    public String getMemo(FetchSettingVO fetchSetting) {
        StringBuilder memo = new StringBuilder();
        CustomBizModelDTO bizModel = (CustomBizModelDTO)this.bizModeService.get(fetchSetting.getFetchSourceCode());
        memo.append(String.format("\u81ea\u5b9a\u4e49\u53d6\u6570\u6a21\u578b\u4ee3\u7801:%1$s,", fetchSetting.getFetchSourceCode()));
        memo.append(String.format("\u53d6\u6570\u5b57\u6bb5:%1$s,", fetchSetting.getFetchType()));
        memo.append(String.format("\u53d6\u6570\u7c7b\u578b:%1$s,", ((SelectField)bizModel.getSelectFieldMap().get(fetchSetting.getFetchType())).getAggregateFuncCode()));
        if (!StringUtils.isEmpty((String)fetchSetting.getDataSourceCode())) {
            memo.append(OptionItemEnum.DATASOURCECODE.getName()).append(fetchSetting.getDataSourceCode()).append(",");
        }
        memo.delete(memo.length() - 1, memo.length());
        return memo.toString();
    }
}

