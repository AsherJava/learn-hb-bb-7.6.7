/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.automation.annotation.CommonAutomation
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.nvwa.core.automation.annotation.AutomationType
 *  com.jiuqi.nvwa.core.automation.annotation.MetaOperation
 *  com.jiuqi.nvwa.framework.automation.api.AutomationMetaInfo
 *  com.jiuqi.nvwa.framework.automation.api.AutomationParameter
 *  com.jiuqi.nvwa.framework.automation.enums.AutomationParameterDataTypeEnum
 *  com.jiuqi.nvwa.framework.automation.enums.AutomationValueResultDataTypeEnum
 *  com.jiuqi.nvwa.framework.automation.invoker.IMetaInvoker
 *  com.jiuqi.nvwa.framework.automation.invoker.IOperationInvoker
 *  com.jiuqi.nvwa.framework.automation.result.ValueResult
 */
package com.jiuqi.gcreport.financialcheckImpl.automation;

import com.jiuqi.common.automation.annotation.CommonAutomation;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.financialcheckImpl.automation.FinancialCheckOperation;
import com.jiuqi.gcreport.financialcheckImpl.check.service.FinancialCheckService;
import com.jiuqi.nvwa.core.automation.annotation.AutomationType;
import com.jiuqi.nvwa.core.automation.annotation.MetaOperation;
import com.jiuqi.nvwa.framework.automation.api.AutomationMetaInfo;
import com.jiuqi.nvwa.framework.automation.api.AutomationParameter;
import com.jiuqi.nvwa.framework.automation.enums.AutomationParameterDataTypeEnum;
import com.jiuqi.nvwa.framework.automation.enums.AutomationValueResultDataTypeEnum;
import com.jiuqi.nvwa.framework.automation.invoker.IMetaInvoker;
import com.jiuqi.nvwa.framework.automation.invoker.IOperationInvoker;
import com.jiuqi.nvwa.framework.automation.result.ValueResult;
import java.util.ArrayList;
import java.util.HashSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@AutomationType(category="gcreport", id="gcreport-related-financial-check-auto", title="\u81ea\u52a8\u5bf9\u8d26", icon="icon-64-xxx")
@CommonAutomation(path="/\u5173\u8054\u4ea4\u6613/\u8d22\u52a1\u6838\u5bf9")
public class FinancialCheckType {
    private Logger logger = LoggerFactory.getLogger(FinancialCheckType.class);
    @Autowired
    private FinancialCheckService financialCheckService;

    @FinancialCheckOperation
    public IOperationInvoker<ValueResult> financialCheckAuto() {
        return (instance, context) -> {
            String acctYearStr = null;
            String unitId = null;
            try {
                acctYearStr = context.getParameterValue("year");
                int year = Integer.parseInt(acctYearStr);
                unitId = context.getParameterValue("unitId");
                HashSet unitSet = CollectionUtils.newHashSet();
                if (!StringUtils.isEmpty((String)unitId)) {
                    unitSet.add(unitId);
                }
                this.financialCheckService.realTimeCheck(year, unitSet);
            }
            catch (Exception e) {
                this.logger.error(String.format("\u5e74\u5ea6\u3010%1$s\u3011\u5355\u4f4d\u3010%2$s\u3011\u81ea\u52a8\u5bf9\u8d26\u5931\u8d25\uff01", acctYearStr, unitId), e);
                return new ValueResult((Object)("\u81ea\u52a8\u5bf9\u8d26\u5931\u8d25,\u5931\u8d25\u539f\u56e0\uff1a" + e.getMessage()), AutomationValueResultDataTypeEnum.STRING);
            }
            return new ValueResult((Object)"\u81ea\u52a8\u5bf9\u8d26\u7ed3\u675f", AutomationValueResultDataTypeEnum.STRING);
        };
    }

    @MetaOperation
    public IMetaInvoker meta() {
        return instance -> {
            AutomationMetaInfo metaInfo = new AutomationMetaInfo();
            AutomationParameter acctYearParameter = new AutomationParameter("year", "\u4f1a\u8ba1\u5e74\u5ea6", AutomationParameterDataTypeEnum.INTEGER, null);
            AutomationParameter unitParameter = new AutomationParameter("unitId", "\u5bf9\u8d26\u5355\u4f4dID", AutomationParameterDataTypeEnum.STRING, null);
            ArrayList<AutomationParameter> parameterList = new ArrayList<AutomationParameter>();
            parameterList.add(acctYearParameter);
            parameterList.add(unitParameter);
            metaInfo.setParameterList(parameterList);
            ArrayList fieldInfoList = new ArrayList();
            metaInfo.setFieldInfoList(fieldInfoList);
            return metaInfo;
        };
    }
}

