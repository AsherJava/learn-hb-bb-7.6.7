/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.dto.BaseDataBizModelDTO
 *  com.jiuqi.bde.bizmodel.client.intf.IBizComputationModel
 *  com.jiuqi.bde.bizmodel.define.datamodel.BaseDataModel
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
 *  com.jiuqi.va.datamodel.service.VaDataModelPublishedService
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 */
package com.jiuqi.bde.bizmodel.execute.model.basedata;

import com.jiuqi.bde.bizmodel.client.dto.BaseDataBizModelDTO;
import com.jiuqi.bde.bizmodel.client.intf.IBizComputationModel;
import com.jiuqi.bde.bizmodel.define.datamodel.BaseDataModel;
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
import com.jiuqi.va.datamodel.service.VaDataModelPublishedService;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseDataComputationModel
implements IBizComputationModel {
    @Autowired
    private BaseDataModel baseDataModel;
    @Autowired
    private BizModelService bizModelService;
    @Autowired
    private VaDataModelPublishedService dataModelService;

    public String getCode() {
        return ComputationModelEnum.BASEDATA.getCode();
    }

    public String getName() {
        return ComputationModelEnum.BASEDATA.getName();
    }

    public String getIcon() {
        return "#icon-a-16_TY_E_GC_jichushuju";
    }

    public String getBizDataCode() {
        return this.baseDataModel.getCode();
    }

    public int getOrder() {
        return 12;
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
        ColumnDefineVO fetchTypeColumn = new ColumnDefineVO(FetchFixedFieldEnum.FETCHTYPE.getCode(), GcI18nUtil.getMessage((String)"bde.fixe.column.fetchField"), true, "SINGLE", "SELECT", null, null);
        fetchTypeColumn.setWidth(Integer.valueOf(120));
        fixedFields.add(fetchTypeColumn);
        fixedFields.add(new ColumnDefineVO(FetchFixedFieldEnum.SUBJECTCODE.getCode(), GcI18nUtil.getMessage((String)"bde.fixe.column.baseDataCode"), true, "SINGLE,MULTIPLE,RANGE", "INPUT", null, null));
        return fixedFields;
    }

    public List<SelectOptionVO> getOptionItems() {
        return Collections.emptyList();
    }

    public String getOptimizeRuleGroup(FetchSettingVO fetchSetting) {
        LinkedHashMap<String, String> dimCombs = new LinkedHashMap<String, String>(16);
        dimCombs.put(OptionItemEnum.ACCTYEAR.getCode(), StringUtils.isEmpty((String)fetchSetting.getAcctYear()) ? "" : fetchSetting.getAcctYear());
        dimCombs.put(OptionItemEnum.ACCTPERIOD.getCode(), StringUtils.isEmpty((String)fetchSetting.getAcctPeriod()) ? "" : fetchSetting.getAcctPeriod());
        dimCombs.put(OptionItemEnum.ORGCODE.getCode(), StringUtils.isEmpty((String)fetchSetting.getOrgCode()) ? "" : fetchSetting.getOrgCode());
        String baseDataDefine = this.getBaseDataDefine(fetchSetting.getFetchSourceCode());
        dimCombs.put("baseCodeDefine", StringUtils.isEmpty((String)baseDataDefine) ? "" : baseDataDefine);
        return JsonUtils.writeValueAsString(dimCombs);
    }

    public String getMemo(FetchSettingVO fetchSetting) {
        String fetchType = fetchSetting.getFetchType();
        String baseDataDefine = this.getBaseDataDefine(fetchSetting.getFetchSourceCode());
        if (!StringUtils.isEmpty((String)baseDataDefine)) {
            DataModelDTO param = new DataModelDTO();
            param.setName(baseDataDefine);
            DataModelDO dataModelDO = this.dataModelService.get(param);
            for (DataModelColumn column : dataModelDO.getColumns()) {
                if (!column.getColumnName().equals(fetchType)) continue;
                fetchType = column.getColumnTitle();
                break;
            }
        }
        StringBuilder memo = new StringBuilder();
        memo.append(this.buildGenericMemo("\u53d6\u6570\u5b57\u6bb5", fetchType));
        memo.append(this.buildBaseDataMemo(fetchSetting.getSubjectCode()));
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
        memo.delete(memo.length() - 1, memo.length());
        return memo.toString();
    }

    private String getBaseDataDefine(String bizModelCode) {
        BaseDataBizModelDTO bizModel = (BaseDataBizModelDTO)this.bizModelService.get(bizModelCode);
        String baseDataDefine = bizModel.getBaseDataDefine();
        return baseDataDefine;
    }

    private final String buildBaseDataMemo(String subjectCode) {
        if (StringUtils.isEmpty((String)subjectCode)) {
            return "";
        }
        return this.buildGenericMemo("\u57fa\u7840\u6570\u636e\u4ee3\u7801", subjectCode);
    }
}

