/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.automation.annotation.CommonAutomation
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.gcreport.webserviceclient.utils.DataIntegrationUtil
 *  com.jiuqi.nr.data.access.util.DimCollectionBuildUtil
 *  com.jiuqi.nr.data.logic.facade.param.input.CalculateParam
 *  com.jiuqi.nr.data.logic.facade.param.input.Mode
 *  com.jiuqi.nr.data.logic.facade.service.ICalculateService
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nvwa.core.automation.annotation.AutomationType
 *  com.jiuqi.nvwa.core.automation.annotation.ExecuteOperation
 *  com.jiuqi.nvwa.core.automation.annotation.MetaOperation
 *  com.jiuqi.nvwa.framework.automation.api.AutomationFieldInfo
 *  com.jiuqi.nvwa.framework.automation.api.AutomationMetaInfo
 *  com.jiuqi.nvwa.framework.automation.api.AutomationParameter
 *  com.jiuqi.nvwa.framework.automation.bean.ExecuteContext
 *  com.jiuqi.nvwa.framework.automation.enums.AutomationParameterDataTypeEnum
 *  com.jiuqi.nvwa.framework.automation.exception.AutomationExecuteException
 *  com.jiuqi.nvwa.framework.automation.invoker.IMetaInvoker
 *  com.jiuqi.nvwa.framework.automation.invoker.IOperationInvoker
 *  com.jiuqi.nvwa.framework.automation.result.JsonResult
 */
package com.jiuqi.gcreport.nr.impl.automation;

import com.jiuqi.common.automation.annotation.CommonAutomation;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.gcreport.webserviceclient.utils.DataIntegrationUtil;
import com.jiuqi.nr.data.access.util.DimCollectionBuildUtil;
import com.jiuqi.nr.data.logic.facade.param.input.CalculateParam;
import com.jiuqi.nr.data.logic.facade.param.input.Mode;
import com.jiuqi.nr.data.logic.facade.service.ICalculateService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nvwa.core.automation.annotation.AutomationType;
import com.jiuqi.nvwa.core.automation.annotation.ExecuteOperation;
import com.jiuqi.nvwa.core.automation.annotation.MetaOperation;
import com.jiuqi.nvwa.framework.automation.api.AutomationFieldInfo;
import com.jiuqi.nvwa.framework.automation.api.AutomationMetaInfo;
import com.jiuqi.nvwa.framework.automation.api.AutomationParameter;
import com.jiuqi.nvwa.framework.automation.bean.ExecuteContext;
import com.jiuqi.nvwa.framework.automation.enums.AutomationParameterDataTypeEnum;
import com.jiuqi.nvwa.framework.automation.exception.AutomationExecuteException;
import com.jiuqi.nvwa.framework.automation.invoker.IMetaInvoker;
import com.jiuqi.nvwa.framework.automation.invoker.IOperationInvoker;
import com.jiuqi.nvwa.framework.automation.result.JsonResult;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@AutomationType(category="gcreport", id="formula-scheme-execute-type", title="\u516c\u5f0f\u65b9\u6848\u6267\u884c", icon="icon-64-xxx")
@CommonAutomation(path="/\u6570\u636e\u5f55\u5165")
public class FormulaSchemeType {
    private Logger logger = LoggerFactory.getLogger(FormulaSchemeType.class);
    @Autowired
    IFormulaRunTimeController formulaRunTimeController;
    @Autowired
    ICalculateService calculateService;
    @Autowired
    DimCollectionBuildUtil dimCollectionBuildUtil;

    @ExecuteOperation
    public IOperationInvoker<JsonResult> invoke() {
        return (instance, context) -> {
            try {
                String formulaSchemeName = context.getParameterValueAsString("formulaSchemeName");
                String taskKey = context.getParameterValueAsString("TASKKEY");
                String formSchemeKey = context.getParameterValueAsString("SCHEMEKEY");
                List allRPTFormulaSchemeDefinesByFormScheme = this.formulaRunTimeController.getAllRPTFormulaSchemeDefinesByFormScheme(formSchemeKey);
                Optional<FormulaSchemeDefine> formulaSchemeDefineOptional = allRPTFormulaSchemeDefinesByFormScheme.stream().filter(formulaSchemeDefine -> formulaSchemeName.equals(formulaSchemeDefine.getTitle())).findFirst();
                if (!formulaSchemeDefineOptional.isPresent()) {
                    this.logger.error("\u516c\u5f0f\u65b9\u6848\u4e0d\u5b58\u5728\u3002");
                    throw new AutomationExecuteException("\u516c\u5f0f\u65b9\u6848\u4e0d\u5b58\u5728\u3002");
                }
                Map dimensionSet = DataIntegrationUtil.getDimensionSet((ExecuteContext)context, (String)taskKey);
                CalculateParam calculateParam = new CalculateParam();
                calculateParam.setFormulaSchemeKey(formulaSchemeDefineOptional.get().getKey());
                calculateParam.setMode(Mode.FORMULA);
                calculateParam.setRangeKeys(new ArrayList());
                DimensionCollection dimensionCollection = this.dimCollectionBuildUtil.buildDimensionCollection(dimensionSet, formSchemeKey);
                calculateParam.setDimensionCollection(dimensionCollection);
                String calculate = this.calculateService.calculate(calculateParam);
                return new JsonResult(JsonUtils.writeValueAsString((Object)calculate));
            }
            catch (Exception e) {
                this.logger.error("\u516c\u5f0f\u65b9\u6848\u81ea\u52a8\u5316\u6267\u884c\u5f02\u5e38\uff1a" + e.getMessage(), e);
                throw new AutomationExecuteException("\u81ea\u52a8\u5316\u5bf9\u8c61\u6267\u884c\u5f02\u5e38\u3002");
            }
        };
    }

    @MetaOperation
    public IMetaInvoker meta() {
        return instance -> {
            AutomationMetaInfo metaInfo = new AutomationMetaInfo();
            ArrayList<AutomationFieldInfo> fieldInfoList = new ArrayList<AutomationFieldInfo>();
            fieldInfoList.add(new AutomationFieldInfo("TASKKEY", "\u4efb\u52a1", Integer.valueOf(6)));
            fieldInfoList.add(new AutomationFieldInfo("SCHEMEKEY", "\u62a5\u8868\u65b9\u6848", Integer.valueOf(6)));
            fieldInfoList.add(new AutomationFieldInfo("UNITCODE", "\u5355\u4f4d", Integer.valueOf(6)));
            fieldInfoList.add(new AutomationFieldInfo("DATATIME", "\u65f6\u671f", Integer.valueOf(6)));
            fieldInfoList.add(new AutomationFieldInfo("CURRENCY", "\u5e01\u79cd", Integer.valueOf(6)));
            fieldInfoList.add(new AutomationFieldInfo("ORGTYPE", "\u5355\u4f4d\u7c7b\u578b", Integer.valueOf(6)));
            metaInfo.setFieldInfoList(fieldInfoList);
            List parameterList = fieldInfoList.stream().map(fieldInfo -> new AutomationParameter(fieldInfo.getName(), fieldInfo.getTitle(), AutomationParameterDataTypeEnum.STRING, null, true)).collect(Collectors.toList());
            parameterList.add(new AutomationParameter("formulaSchemeName", "\u516c\u5f0f\u65b9\u6848\u540d\u79f0", AutomationParameterDataTypeEnum.STRING, null, true));
            metaInfo.setParameterList(parameterList);
            return metaInfo;
        };
    }
}

