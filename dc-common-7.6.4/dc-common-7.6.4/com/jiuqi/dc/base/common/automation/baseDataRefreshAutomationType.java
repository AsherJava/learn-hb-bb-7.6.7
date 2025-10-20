/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.automation.annotation.CommonAutomation
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.nvwa.core.automation.annotation.AutomationType
 *  com.jiuqi.nvwa.core.automation.annotation.ExecuteOperation
 *  com.jiuqi.nvwa.core.automation.annotation.MetaOperation
 *  com.jiuqi.nvwa.framework.automation.api.AutomationMetaInfo
 *  com.jiuqi.nvwa.framework.automation.api.AutomationParameter
 *  com.jiuqi.nvwa.framework.automation.enums.AutomationParameterDataTypeEnum
 *  com.jiuqi.nvwa.framework.automation.enums.AutomationValueResultDataTypeEnum
 *  com.jiuqi.nvwa.framework.automation.invoker.IMetaInvoker
 *  com.jiuqi.nvwa.framework.automation.invoker.IOperationInvoker
 *  com.jiuqi.nvwa.framework.automation.result.ValueResult
 *  com.jiuqi.va.domain.basedata.BaseDataCacheDTO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.org.OrgCategoryDTO
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  com.jiuqi.va.feign.client.OrgDataClient
 */
package com.jiuqi.dc.base.common.automation;

import com.jiuqi.common.automation.annotation.CommonAutomation;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.nvwa.core.automation.annotation.AutomationType;
import com.jiuqi.nvwa.core.automation.annotation.ExecuteOperation;
import com.jiuqi.nvwa.core.automation.annotation.MetaOperation;
import com.jiuqi.nvwa.framework.automation.api.AutomationMetaInfo;
import com.jiuqi.nvwa.framework.automation.api.AutomationParameter;
import com.jiuqi.nvwa.framework.automation.enums.AutomationParameterDataTypeEnum;
import com.jiuqi.nvwa.framework.automation.enums.AutomationValueResultDataTypeEnum;
import com.jiuqi.nvwa.framework.automation.invoker.IMetaInvoker;
import com.jiuqi.nvwa.framework.automation.invoker.IOperationInvoker;
import com.jiuqi.nvwa.framework.automation.result.ValueResult;
import com.jiuqi.va.domain.basedata.BaseDataCacheDTO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.org.OrgCategoryDTO;
import com.jiuqi.va.feign.client.BaseDataClient;
import com.jiuqi.va.feign.client.OrgDataClient;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@AutomationType(category="datacenter", id="basedata-refresh-auto", title="\u57fa\u7840\u6570\u636e", icon="icon-64-xxx")
@CommonAutomation(name="baseDataRefresh", title="\u7f13\u5b58\u5237\u65b0", path="/\u57fa\u7840\u6570\u636e")
public class baseDataRefreshAutomationType {
    public static final String AUTOMATION_DATA_REFRESH_PARAM_BASEDATA_TYPE = "baseDataType";
    @Autowired
    private OrgDataClient orgDataClient;
    @Autowired
    private BaseDataClient baseDataClient;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExecuteOperation
    public IOperationInvoker<ValueResult> baseDataRefresh() {
        return (instance, context) -> {
            try {
                String baseDataType = context.getParameterValueAsString(AUTOMATION_DATA_REFRESH_PARAM_BASEDATA_TYPE);
                if (StringUtils.isEmpty((String)baseDataType) || "MD_ORG".equals(baseDataType)) {
                    R r = this.orgDataClient.cleanCache(new OrgCategoryDTO());
                    return new ValueResult((Object)JsonUtils.writeValueAsString((Object)r), AutomationValueResultDataTypeEnum.STRING);
                }
                BaseDataCacheDTO dto = new BaseDataCacheDTO();
                dto.setTableName(baseDataType);
                R r = this.baseDataClient.cleanCache(dto);
                return new ValueResult((Object)JsonUtils.writeValueAsString((Object)r), AutomationValueResultDataTypeEnum.STRING);
            }
            catch (Exception e) {
                this.logger.error("\u57fa\u7840\u6570\u636e\u7f13\u5b58\u5237\u65b0\u53d1\u751f\u5f02\u5e38", e);
                return new ValueResult((Object)("\u57fa\u7840\u6570\u636e\u7f13\u5b58\u5237\u65b0\u53d1\u751f\u5f02\u5e38\uff0c\u5931\u8d25\u539f\u56e0\uff1a" + e.getMessage()), AutomationValueResultDataTypeEnum.STRING);
            }
        };
    }

    @MetaOperation
    public IMetaInvoker meta() {
        return instance -> {
            AutomationMetaInfo metaInfo = new AutomationMetaInfo();
            AutomationParameter baseDataType = new AutomationParameter(AUTOMATION_DATA_REFRESH_PARAM_BASEDATA_TYPE, "\u57fa\u7840\u6570\u636e\u7c7b\u578b\uff08\u586bMD_XX\uff1b\u586bMD_ORG\u6216\u4e0d\u586b\u5c31\u662f\u5237\u65b0\u7ec4\u7ec7\u673a\u6784\u7f13\u5b58\uff09", AutomationParameterDataTypeEnum.STRING, "MD_ORG");
            ArrayList<AutomationParameter> parameterList = new ArrayList<AutomationParameter>();
            parameterList.add(baseDataType);
            metaInfo.setParameterList(parameterList);
            ArrayList fieldInfoList = new ArrayList();
            metaInfo.setFieldInfoList(fieldInfoList);
            return metaInfo;
        };
    }
}

