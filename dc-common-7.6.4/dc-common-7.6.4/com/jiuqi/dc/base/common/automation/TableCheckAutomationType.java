/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.automation.annotation.CommonAutomation
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
 */
package com.jiuqi.dc.base.common.automation;

import com.jiuqi.common.automation.annotation.CommonAutomation;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.definition.service.ITableCheckService;
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
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@AutomationType(category="datacenter", id="datacenter-tableCheck-auto", title="\u8868\u7ed3\u6784\u68c0\u67e5", icon="icon-64-xxx")
@CommonAutomation(name="tableCheck", title="\u4e00\u672c\u8d26\u68c0\u67e5", path="/\u8868\u7ed3\u6784\u68c0\u67e5")
public class TableCheckAutomationType {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ITableCheckService tableCheckService;

    @ExecuteOperation
    public IOperationInvoker<ValueResult> balanceReCalcu() {
        return (instance, context) -> {
            String result;
            try {
                String checkRange = context.getParameterValueAsString("checkRange");
                Boolean isRepair = context.getParameterValueAsBoolean("isRepair");
                if (StringUtils.isEmpty((String)checkRange)) {
                    result = this.tableCheckService.tableCheck(null, isRepair);
                } else {
                    Set<String> checkRangeList = Arrays.stream(checkRange.split(";")).collect(Collectors.toSet());
                    result = this.tableCheckService.tableCheck(checkRangeList, isRepair);
                }
            }
            catch (Exception e) {
                this.logger.error("\u8868\u7ed3\u6784\u68c0\u67e5\u53d1\u751f\u5f02\u5e38", e);
                return new ValueResult((Object)("\u8868\u7ed3\u6784\u68c0\u67e5\u53d1\u751f\u5f02\u5e38,\u5931\u8d25\u539f\u56e0\uff1a" + e.getMessage()), AutomationValueResultDataTypeEnum.STRING);
            }
            return new ValueResult((Object)result, AutomationValueResultDataTypeEnum.STRING);
        };
    }

    @MetaOperation
    public IMetaInvoker meta() {
        return instance -> {
            AutomationMetaInfo metaInfo = new AutomationMetaInfo();
            ArrayList<AutomationParameter> parameterList = new ArrayList<AutomationParameter>();
            metaInfo.setParameterList(parameterList);
            AutomationParameter tableRange = new AutomationParameter("checkRange", "\u6307\u5b9a\u8981\u68c0\u67e5\u7684\u8868\uff08\u591a\u4e2a\u8868\u7528\u82f1\u6587\u5206\u53f7\u5206\u9694\uff1b\u9ed8\u8ba4\u4e3a\u7a7a\u8868\u793a\u68c0\u67e5\u5168\u90e8\u8868\uff09\u3002", AutomationParameterDataTypeEnum.STRING, null);
            AutomationParameter isRepair = new AutomationParameter("isRepair", "\u662f\u5426\u4fee\u590d(\u9ed8\u8ba4\u4e3a\u662f)", AutomationParameterDataTypeEnum.BOOLEAN, Boolean.TRUE.toString());
            parameterList.add(tableRange);
            parameterList.add(isRepair);
            ArrayList fieldInfoList = new ArrayList();
            metaInfo.setFieldInfoList(fieldInfoList);
            return metaInfo;
        };
    }
}

