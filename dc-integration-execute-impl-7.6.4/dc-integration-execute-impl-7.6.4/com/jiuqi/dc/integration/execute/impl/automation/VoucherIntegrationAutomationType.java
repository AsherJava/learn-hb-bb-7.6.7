/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.common.automation.annotation.CommonAutomation
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.integration.execute.client.dto.ConvertExecuteDTO
 *  com.jiuqi.dc.integration.execute.client.dto.ConvertRefDefineDTO
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSchemeService
 *  com.jiuqi.nvwa.core.automation.annotation.AutomationType
 *  com.jiuqi.nvwa.core.automation.annotation.ExecuteOperation
 *  com.jiuqi.nvwa.core.automation.annotation.MetaOperation
 *  com.jiuqi.nvwa.core.automation.annotation.WriteOperation
 *  com.jiuqi.nvwa.framework.automation.api.AutomationFieldInfo
 *  com.jiuqi.nvwa.framework.automation.api.AutomationMetaInfo
 *  com.jiuqi.nvwa.framework.automation.api.AutomationParameter
 *  com.jiuqi.nvwa.framework.automation.bean.ExecuteContext
 *  com.jiuqi.nvwa.framework.automation.enums.AutomationParameterDataTypeEnum
 *  com.jiuqi.nvwa.framework.automation.enums.AutomationValueResultDataTypeEnum
 *  com.jiuqi.nvwa.framework.automation.exception.AutomationException
 *  com.jiuqi.nvwa.framework.automation.invoker.IMetaInvoker
 *  com.jiuqi.nvwa.framework.automation.invoker.IOperationInvoker
 *  com.jiuqi.nvwa.framework.automation.result.DatasetColumnInfo
 *  com.jiuqi.nvwa.framework.automation.result.ValueResult
 */
package com.jiuqi.dc.integration.execute.impl.automation;

import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.common.automation.annotation.CommonAutomation;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.integration.execute.client.dto.ConvertExecuteDTO;
import com.jiuqi.dc.integration.execute.client.dto.ConvertRefDefineDTO;
import com.jiuqi.dc.integration.execute.impl.data.UnitCodeConvertParam;
import com.jiuqi.dc.integration.execute.impl.mq.model.bizdata.BizVoucherConvertTaskHandler;
import com.jiuqi.dc.integration.execute.impl.service.ConvertService;
import com.jiuqi.dc.mappingscheme.impl.service.DataSchemeService;
import com.jiuqi.nvwa.core.automation.annotation.AutomationType;
import com.jiuqi.nvwa.core.automation.annotation.ExecuteOperation;
import com.jiuqi.nvwa.core.automation.annotation.MetaOperation;
import com.jiuqi.nvwa.core.automation.annotation.WriteOperation;
import com.jiuqi.nvwa.framework.automation.api.AutomationFieldInfo;
import com.jiuqi.nvwa.framework.automation.api.AutomationMetaInfo;
import com.jiuqi.nvwa.framework.automation.api.AutomationParameter;
import com.jiuqi.nvwa.framework.automation.bean.ExecuteContext;
import com.jiuqi.nvwa.framework.automation.enums.AutomationParameterDataTypeEnum;
import com.jiuqi.nvwa.framework.automation.enums.AutomationValueResultDataTypeEnum;
import com.jiuqi.nvwa.framework.automation.exception.AutomationException;
import com.jiuqi.nvwa.framework.automation.invoker.IMetaInvoker;
import com.jiuqi.nvwa.framework.automation.invoker.IOperationInvoker;
import com.jiuqi.nvwa.framework.automation.result.DatasetColumnInfo;
import com.jiuqi.nvwa.framework.automation.result.ValueResult;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@AutomationType(category="datacenter", id="datacenter-voucherIntegration-auto", title="\u6570\u636e\u6574\u5408", icon="icon-64-xxx")
@CommonAutomation(name="voucherIntegration", title="\u51ed\u8bc1\u6570\u636e\u6574\u5408", path="/\u6570\u636e\u6574\u5408")
public class VoucherIntegrationAutomationType {
    public static final String AUTOMATION_INTEGRATION_PARAM_UNITSET = "unitSet";
    public static final String AUTOMATION_REBUILD_PARAM_UNIT = "unit";
    public static final String AUTOMATION_INTEGRATION_PARAM_UNITTYPE = "unitType";
    public static final String AUTOMATION_INTEGRATION_PARAM_DATASCHEMECODE = "dataSchemeCode";
    public static final String AUTOMATION_REBUILD_DATASET_UNITCODE = "UNITCODE";
    public static final String AUTOMATION_REBUILD_DATASET_SCHEMECODE = "SCHEMECODE";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private BizVoucherConvertTaskHandler bizVoucherConvertTaskHandler;
    @Autowired
    private DataSchemeService dataSchemeService;
    @Autowired
    private ConvertService executeService;

    @ExecuteOperation
    public IOperationInvoker<ValueResult> balanceIntegration() {
        return (instance, context) -> {
            try {
                ConvertExecuteDTO executeParam = this.getConvertExecuteDTO(context);
                Map<String, String> convertMap = this.executeService.convert(executeParam, true);
                return new ValueResult((Object)JsonUtils.writeValueAsString(convertMap), AutomationValueResultDataTypeEnum.STRING);
            }
            catch (Exception e) {
                this.logger.error("\u6570\u636e\u6574\u5408\u53d1\u751f\u5f02\u5e38", e);
                return new ValueResult((Object)("\u6570\u636e\u6574\u5408\u53d1\u751f\u5f02\u5e38,\u5931\u8d25\u539f\u56e0\uff1a" + e.getMessage()), AutomationValueResultDataTypeEnum.STRING);
            }
        };
    }

    @WriteOperation
    public IOperationInvoker<ValueResult> balanceBatchIntegration() {
        return (instance, context) -> {
            try {
                Column column = new Column(AUTOMATION_REBUILD_DATASET_UNITCODE, 6, "\u5355\u4f4d\u4ee3\u7801", (Object)new DatasetColumnInfo());
                Column schemeColumn = new Column(AUTOMATION_REBUILD_DATASET_SCHEMECODE, 6, "\u65b9\u6848\u4ee3\u7801", (Object)new DatasetColumnInfo());
                List<ConvertExecuteDTO> executeParamList = this.getConvertExecuteList(context, (Column<DatasetColumnInfo>)column, (Column<DatasetColumnInfo>)schemeColumn);
                ArrayList taskIdList = CollectionUtils.newArrayList();
                executeParamList.forEach(executeParam -> {
                    Map<String, String> convertMap = this.executeService.convert((ConvertExecuteDTO)executeParam, true);
                    taskIdList.add(convertMap.get("BizBalanceInitConvert"));
                });
                return new ValueResult((Object)JsonUtils.writeValueAsString((Object)taskIdList), AutomationValueResultDataTypeEnum.STRING);
            }
            catch (Exception e) {
                this.logger.error("\u6570\u636e\u6574\u5408\u53d1\u751f\u5f02\u5e38", e);
                return new ValueResult((Object)("\u6570\u636e\u6574\u5408\u53d1\u751f\u5f02\u5e38,\u5931\u8d25\u539f\u56e0\uff1a" + e.getMessage()), AutomationValueResultDataTypeEnum.STRING);
            }
        };
    }

    private List<ConvertExecuteDTO> getConvertExecuteList(ExecuteContext context, Column<DatasetColumnInfo> column, Column<DatasetColumnInfo> schemeColumn) throws AutomationException {
        int i;
        HashMap schemeMap = CollectionUtils.newHashMap();
        String unitType = context.getParameterValueAsString(AUTOMATION_INTEGRATION_PARAM_UNITTYPE);
        MemoryDataSet unitSet = context.getParameterValueAsDataset(AUTOMATION_INTEGRATION_PARAM_UNITSET, (List)CollectionUtils.newArrayList((Object[])new Column[]{column, schemeColumn}));
        List<String> unitList = this.getUnitCodeList((MemoryDataSet<DatasetColumnInfo>)unitSet);
        ArrayList<String> schemeList = new ArrayList<String>();
        for (i = 0; i < unitList.size(); ++i) {
            schemeList.add(unitSet.get(i).getString(0));
        }
        for (i = 0; i < schemeList.size(); ++i) {
            schemeMap.computeIfAbsent(schemeList.get(i), k -> CollectionUtils.newArrayList()).add(unitList.get(i));
        }
        ArrayList condiList = CollectionUtils.newArrayList();
        schemeMap.forEach((k, v) -> {
            ConvertExecuteDTO condi = new ConvertExecuteDTO();
            condi.setDataScheme(this.dataSchemeService.getByCode(k));
            ArrayList convertRefDefineDTOList = CollectionUtils.newArrayList();
            ConvertRefDefineDTO convertRefDefineDTO = new ConvertRefDefineDTO();
            convertRefDefineDTO.setCode(this.bizVoucherConvertTaskHandler.getName());
            convertRefDefineDTO.setName(this.bizVoucherConvertTaskHandler.getTitle());
            UnitCodeConvertParam unitCodeConvertParam = new UnitCodeConvertParam();
            unitCodeConvertParam.setUnitType(unitType);
            unitCodeConvertParam.setUnitCodes((List<String>)v);
            convertRefDefineDTO.setParam(JsonUtils.writeValueAsString((Object)unitCodeConvertParam));
            convertRefDefineDTOList.add(convertRefDefineDTO);
            condi.setBizDataRefs((List)convertRefDefineDTOList);
            condiList.add(condi);
        });
        return condiList;
    }

    private ConvertExecuteDTO getConvertExecuteDTO(ExecuteContext context) throws AutomationException {
        String unitType = context.getParameterValueAsString(AUTOMATION_INTEGRATION_PARAM_UNITTYPE);
        ConvertExecuteDTO condi = new ConvertExecuteDTO();
        condi.setDataScheme(this.dataSchemeService.getByCode(context.getParameterValueAsString(AUTOMATION_INTEGRATION_PARAM_DATASCHEMECODE)));
        ArrayList convertRefDefineDTOList = CollectionUtils.newArrayList();
        ConvertRefDefineDTO convertRefDefineDTO = new ConvertRefDefineDTO();
        convertRefDefineDTO.setCode(this.bizVoucherConvertTaskHandler.getName());
        convertRefDefineDTO.setName(this.bizVoucherConvertTaskHandler.getTitle());
        UnitCodeConvertParam unitCodeConvertParam = new UnitCodeConvertParam();
        unitCodeConvertParam.setUnitType(unitType);
        String units = context.getParameterValueAsString(AUTOMATION_REBUILD_PARAM_UNIT);
        unitCodeConvertParam.setUnitCodes(this.getUnitCodeList(units));
        convertRefDefineDTO.setParam(JsonUtils.writeValueAsString((Object)unitCodeConvertParam));
        convertRefDefineDTOList.add(convertRefDefineDTO);
        condi.setBizDataRefs((List)convertRefDefineDTOList);
        return condi;
    }

    private List<String> getUnitCodeList(String units) {
        if (StringUtils.isEmpty((String)units)) {
            return CollectionUtils.newArrayList();
        }
        return Arrays.stream(units.split(";")).map(String::trim).collect(Collectors.toList());
    }

    private List<String> getUnitCodeList(MemoryDataSet<DatasetColumnInfo> units) {
        ArrayList result = CollectionUtils.newArrayList();
        units.forEach(e -> result.add(e.getString(1)));
        return result;
    }

    @MetaOperation
    public IMetaInvoker meta() {
        return instance -> {
            AutomationMetaInfo metaInfo = new AutomationMetaInfo();
            AutomationParameter unit = new AutomationParameter(AUTOMATION_REBUILD_PARAM_UNIT, "\u7ec4\u7ec7\u673a\u6784(\u591a\u5bb6\u5355\u4f4d\u4ee5\u82f1\u6587\u5206\u53f7\u8fde\u63a5,\u4e0d\u586b\u9ed8\u8ba4\u4e3a\u5168\u90e8\u5355\u4f4d)", AutomationParameterDataTypeEnum.STRING, null);
            unit.setScopes(new HashSet<String>(Collections.singletonList("execute")));
            AutomationParameter unitSet = new AutomationParameter(AUTOMATION_INTEGRATION_PARAM_UNITSET, "\u7ec4\u7ec7\u673a\u6784\u6570\u636e\u96c6(\u4e0d\u586b\u9ed8\u8ba4\u4e3a\u5168\u90e8\u5355\u4f4d)", AutomationParameterDataTypeEnum.FILE, null);
            unitSet.setScopes(new HashSet<String>(Collections.singletonList("write")));
            AutomationParameter dataSchemeCode = new AutomationParameter(AUTOMATION_INTEGRATION_PARAM_DATASCHEMECODE, "\u6570\u636e\u65b9\u6848\u4ee3\u7801", AutomationParameterDataTypeEnum.STRING, "");
            dataSchemeCode.setScopes(new HashSet<String>(Collections.singletonList("execute")));
            AutomationParameter unitType = new AutomationParameter(AUTOMATION_INTEGRATION_PARAM_UNITTYPE, "\u5355\u4f4d\u7c7b\u578b(\u4e00\u672c\u8d26\u5355\u4f4d:dcUnit,\u62a5\u8868\u5355\u4f4d:reportUnit,\u9ed8\u8ba4\u4e00\u672c\u8d26\u5355\u4f4d)", AutomationParameterDataTypeEnum.STRING, "dcUnit", true);
            ArrayList<AutomationParameter> parameterList = new ArrayList<AutomationParameter>();
            parameterList.add(dataSchemeCode);
            parameterList.add(unit);
            parameterList.add(unitSet);
            parameterList.add(unitType);
            metaInfo.setParameterList(parameterList);
            ArrayList<AutomationFieldInfo> fieldInfoList = new ArrayList<AutomationFieldInfo>();
            AutomationFieldInfo automationUnitInfo = new AutomationFieldInfo(AUTOMATION_REBUILD_DATASET_UNITCODE, "\u5355\u4f4d\u4ee3\u7801", Integer.valueOf(6));
            AutomationFieldInfo automationSchemeInfo = new AutomationFieldInfo(AUTOMATION_REBUILD_DATASET_SCHEMECODE, "\u65b9\u6848\u4ee3\u7801", Integer.valueOf(6));
            fieldInfoList.add(automationSchemeInfo);
            fieldInfoList.add(automationUnitInfo);
            metaInfo.setFieldInfoList(fieldInfoList);
            return metaInfo;
        };
    }
}

