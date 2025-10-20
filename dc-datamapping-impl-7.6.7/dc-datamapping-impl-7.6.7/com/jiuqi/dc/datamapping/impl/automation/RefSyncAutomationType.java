/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.automation.annotation.CommonAutomation
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 *  com.jiuqi.dc.mappingscheme.client.vo.AdvancedMappingVO
 *  com.jiuqi.dc.mappingscheme.client.vo.AssistMappingVO
 *  com.jiuqi.dc.mappingscheme.client.vo.BizMappingVO
 *  com.jiuqi.dc.mappingscheme.client.vo.DataMappingVO
 *  com.jiuqi.dc.mappingscheme.impl.service.impl.DataSchemeCacheProvider
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
 */
package com.jiuqi.dc.datamapping.impl.automation;

import com.jiuqi.common.automation.annotation.CommonAutomation;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.datamapping.impl.service.impl.IsolateRefDefineCacheProvider;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import com.jiuqi.dc.mappingscheme.client.vo.AdvancedMappingVO;
import com.jiuqi.dc.mappingscheme.client.vo.AssistMappingVO;
import com.jiuqi.dc.mappingscheme.client.vo.BizMappingVO;
import com.jiuqi.dc.mappingscheme.client.vo.DataMappingVO;
import com.jiuqi.dc.mappingscheme.impl.service.impl.DataSchemeCacheProvider;
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
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

@AutomationType(category="datacenter", id="datacenter-refSync-auto", title="\u6570\u636e\u6574\u5408", icon="icon-64-xxx")
@CommonAutomation(name="refSync", title="\u7f13\u5b58\u540c\u6b65", path="/\u6620\u5c04\u65b9\u6848")
public class RefSyncAutomationType {
    private final Logger logger = LoggerFactory.getLogger(RefSyncAutomationType.class);
    public static final String AUTOMATION_INTEGRATION_PARAM_DATASCHEMECODE = "dataSchemeCode";
    public static final String AUTOMATION_REBUILD_PARAM_DIM = "dim";
    @Autowired
    private IsolateRefDefineCacheProvider cacheProvider;
    @Autowired
    private DataSchemeCacheProvider dataSchemeCacheProvider;

    @ExecuteOperation
    public IOperationInvoker<ValueResult> refSync() {
        return (instance, context) -> {
            try {
                String dataSchemeCode = context.getParameterValueAsString(AUTOMATION_INTEGRATION_PARAM_DATASCHEMECODE);
                String dim = context.getParameterValueAsString(AUTOMATION_REBUILD_PARAM_DIM);
                if (StringUtils.isEmpty((String)dataSchemeCode) && !StringUtils.isEmpty((String)dim)) {
                    return new ValueResult((Object)"\u4e0d\u652f\u6301\u53ea\u6309\u7167\u7ef4\u5ea6\u66f4\u65b0\u7f13\u5b58\uff0c\u9700\u586b\u5165\u6570\u636e\u65b9\u6848\u4ee3\u7801", AutomationValueResultDataTypeEnum.STRING);
                }
                if (!StringUtils.isEmpty((String)dataSchemeCode) && !StringUtils.isEmpty((String)dim)) {
                    DataSchemeDTO dataSchemeDTO = (DataSchemeDTO)this.dataSchemeCacheProvider.get(dataSchemeCode);
                    DataMappingVO dataMapping = dataSchemeDTO.getDataMapping();
                    Boolean flag = true;
                    if (dim.equals(dataMapping.getOrgMapping().getCode()) || !ObjectUtils.isEmpty(dataMapping.getSubjectMapping()) && dim.equals(dataMapping.getSubjectMapping().getCode()) || !ObjectUtils.isEmpty(dataMapping.getCfitemMapping()) && dim.equals(dataMapping.getCfitemMapping().getCode())) {
                        flag = false;
                    }
                    List assistMappings = dataMapping.getAssistMapping();
                    for (AssistMappingVO assistMapping : assistMappings) {
                        if (StringUtils.isEmpty((String)assistMapping.getAdvancedSql()) || "CUSTOMFIELD".equals(assistMapping.getOdsFieldName()) || !dim.equals(assistMapping.getCode())) continue;
                        flag = false;
                        break;
                    }
                    List advancedMappings = dataMapping.getAdvancedMapping();
                    block3: for (AdvancedMappingVO advancedMapping : advancedMappings) {
                        BizMappingVO bizMapping = advancedMapping.getBizMapping();
                        for (String value : bizMapping.values()) {
                            if (StringUtils.isEmpty((String)value) || !dim.equals(advancedMapping.getCode())) continue;
                            flag = false;
                            continue block3;
                        }
                    }
                    if (flag.booleanValue()) {
                        return new ValueResult((Object)"\u8be5\u6620\u5c04\u65b9\u6848\u672a\u914d\u7f6e\u8be5\u7ef4\u5ea6", AutomationValueResultDataTypeEnum.STRING);
                    }
                    this.cacheProvider.syncCache(dataSchemeCode, dim, null);
                } else {
                    this.cacheProvider.noClearLoadCache(dataSchemeCode);
                }
            }
            catch (Exception e) {
                this.logger.error("\u6620\u5c04\u65b9\u6848\u7f13\u5b58\u540c\u6b65\u5931\u8d25", e);
                return new ValueResult((Object)("\u6620\u5c04\u65b9\u6848\u7f13\u5b58\u540c\u6b65\u5931\u8d25,\u5931\u8d25\u539f\u56e0\uff1a" + e.getMessage()), AutomationValueResultDataTypeEnum.STRING);
            }
            return new ValueResult((Object)"\u6620\u5c04\u65b9\u6848\u7f13\u5b58\u540c\u6b65\u7ed3\u675f", AutomationValueResultDataTypeEnum.STRING);
        };
    }

    @MetaOperation
    public IMetaInvoker meta() {
        return instance -> {
            AutomationMetaInfo metaInfo = new AutomationMetaInfo();
            metaInfo.setParameterList((List)CollectionUtils.newArrayList((Object[])new AutomationParameter[]{new AutomationParameter(AUTOMATION_INTEGRATION_PARAM_DATASCHEMECODE, "\u6570\u636e\u65b9\u6848\u4ee3\u7801", AutomationParameterDataTypeEnum.STRING, "", false), new AutomationParameter(AUTOMATION_REBUILD_PARAM_DIM, "\u7ef4\u5ea6\u4ee3\u7801", AutomationParameterDataTypeEnum.STRING, "", false)}));
            ArrayList fieldInfoList = new ArrayList();
            metaInfo.setFieldInfoList(fieldInfoList);
            return metaInfo;
        };
    }
}

