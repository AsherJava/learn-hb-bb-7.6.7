/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.bi.util.Guid
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nvwa.core.automation.annotation.AutomationType
 *  com.jiuqi.nvwa.core.automation.annotation.ExportOperation
 *  com.jiuqi.nvwa.core.automation.annotation.MetaOperation
 *  com.jiuqi.nvwa.core.automation.annotation.QueryOperation
 *  com.jiuqi.nvwa.framework.automation.api.AutomationMetaInfo
 *  com.jiuqi.nvwa.framework.automation.api.AutomationParameter
 *  com.jiuqi.nvwa.framework.automation.api.AutomationParameterOption
 *  com.jiuqi.nvwa.framework.automation.bean.ExecuteContext
 *  com.jiuqi.nvwa.framework.automation.enums.AutomationParameterDataTypeEnum
 *  com.jiuqi.nvwa.framework.automation.enums.AutomationParameterValueModeEnum
 *  com.jiuqi.nvwa.framework.automation.exception.AutomationExecuteException
 *  com.jiuqi.nvwa.framework.automation.invoker.IMetaInvoker
 *  com.jiuqi.nvwa.framework.automation.invoker.IOperationInvoker
 *  com.jiuqi.nvwa.framework.automation.result.GridResult
 *  com.jiuqi.nvwa.framework.parameter.IParameterEnv
 *  com.jiuqi.nvwa.framework.parameter.ParameterCalculator
 *  com.jiuqi.nvwa.framework.parameter.ParameterEnv
 *  com.jiuqi.nvwa.framework.parameter.ParameterException
 *  com.jiuqi.nvwa.framework.parameter.ParameterResultset
 *  com.jiuqi.nvwa.framework.parameter.ParameterUtils
 *  com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterModel
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterSelectMode
 *  com.jiuqi.nvwa.framework.parameter.model.value.IParameterValueFormat
 */
package com.jiuqi.nr.zbquery.automation;

import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.util.Guid;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.zbquery.common.ZBQueryErrorEnum;
import com.jiuqi.nr.zbquery.engine.ZBQueryEngine;
import com.jiuqi.nr.zbquery.engine.ZBQueryResult;
import com.jiuqi.nr.zbquery.model.ConditionValues;
import com.jiuqi.nr.zbquery.model.PageInfo;
import com.jiuqi.nr.zbquery.model.ZBQueryModel;
import com.jiuqi.nr.zbquery.service.ZBQueryInfoService;
import com.jiuqi.nr.zbquery.util.ParameterBuilder;
import com.jiuqi.nr.zbquery.util.ZBQueryLogHelper;
import com.jiuqi.nvwa.core.automation.annotation.AutomationType;
import com.jiuqi.nvwa.core.automation.annotation.ExportOperation;
import com.jiuqi.nvwa.core.automation.annotation.MetaOperation;
import com.jiuqi.nvwa.core.automation.annotation.QueryOperation;
import com.jiuqi.nvwa.framework.automation.api.AutomationMetaInfo;
import com.jiuqi.nvwa.framework.automation.api.AutomationParameter;
import com.jiuqi.nvwa.framework.automation.api.AutomationParameterOption;
import com.jiuqi.nvwa.framework.automation.bean.ExecuteContext;
import com.jiuqi.nvwa.framework.automation.enums.AutomationParameterDataTypeEnum;
import com.jiuqi.nvwa.framework.automation.enums.AutomationParameterValueModeEnum;
import com.jiuqi.nvwa.framework.automation.exception.AutomationExecuteException;
import com.jiuqi.nvwa.framework.automation.invoker.IMetaInvoker;
import com.jiuqi.nvwa.framework.automation.invoker.IOperationInvoker;
import com.jiuqi.nvwa.framework.automation.result.GridResult;
import com.jiuqi.nvwa.framework.parameter.IParameterEnv;
import com.jiuqi.nvwa.framework.parameter.ParameterCalculator;
import com.jiuqi.nvwa.framework.parameter.ParameterEnv;
import com.jiuqi.nvwa.framework.parameter.ParameterException;
import com.jiuqi.nvwa.framework.parameter.ParameterResultset;
import com.jiuqi.nvwa.framework.parameter.ParameterUtils;
import com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel;
import com.jiuqi.nvwa.framework.parameter.model.ParameterModel;
import com.jiuqi.nvwa.framework.parameter.model.ParameterSelectMode;
import com.jiuqi.nvwa.framework.parameter.model.value.IParameterValueFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;

@AutomationType(category="nvwa-dataanalysis", id="com.jiuqi.nr.zbquery.manage", title="\u6307\u6807\u67e5\u8be2", icon="nr-iconfont icon-16_DH_A_NR_guoluchaxun")
public class ZBQueryAutomationType {
    @Autowired
    private ZBQueryInfoService zbQueryInfoService;

    @QueryOperation
    public IOperationInvoker<GridResult> query() throws AutomationExecuteException {
        return (automationElement, executeContext) -> {
            try {
                ZBQueryModel zbQueryModel = this.zbQueryInfoService.getQueryInfoData(automationElement.getGuid());
                ConditionValues conditionValues = this.buildConditionValues(executeContext);
                PageInfo pageInfo = new PageInfo(50, 1);
                GridData query = this.query(zbQueryModel, true, conditionValues, pageInfo, automationElement.getTitle());
                return new GridResult(query);
            }
            catch (Exception e) {
                throw new AutomationExecuteException(e.getMessage(), (Throwable)e);
            }
        };
    }

    @ExportOperation
    public IOperationInvoker<GridResult> export() throws AutomationExecuteException {
        return this.query();
    }

    private ConditionValues buildConditionValues(ExecuteContext executeContext) {
        ConditionValues conditionValues = new ConditionValues();
        Map parameterMap = executeContext.getParameterMap();
        for (Map.Entry entry : parameterMap.entrySet()) {
            String fullName = (String)entry.getKey();
            String values = String.valueOf(entry.getValue());
            if (!"".equals(values)) {
                conditionValues.putValue(fullName, values.split(","));
                continue;
            }
            conditionValues.putValue(fullName, new String[0]);
        }
        return conditionValues;
    }

    private GridData query(ZBQueryModel zbQueryModel, boolean fetch, ConditionValues conditionValues, PageInfo pageInfo, String title) throws JQException {
        try {
            ZBQueryResult queryResult;
            ZBQueryEngine queryEngine = new ZBQueryEngine(Guid.newGuid(), zbQueryModel);
            if (fetch) {
                queryResult = queryEngine.fetch(conditionValues, pageInfo);
            } else {
                queryResult = queryEngine.query(conditionValues, pageInfo);
                ZBQueryLogHelper.info("\u67e5\u8be2\u6307\u6807\u6570\u636e", "\u6a21\u677f\u6807\u9898\uff1a" + title);
            }
            return queryResult.getData();
        }
        catch (Exception e) {
            ZBQueryLogHelper.error("\u67e5\u8be2\u6307\u6807\u6570\u636e", "\u6a21\u677f\u6807\u9898\uff1a" + title);
            throw new JQException((ErrorEnum)ZBQueryErrorEnum.ZBQUERY_EXCEPTION_100, e.getMessage(), (Throwable)e);
        }
    }

    @MetaOperation
    public IMetaInvoker meta() {
        return element -> {
            AutomationMetaInfo metaInfo = new AutomationMetaInfo();
            try {
                List<ParameterModel> pModel = ParameterBuilder.build(this.zbQueryInfoService.getQueryInfoData(element.getGuid()));
                ParameterEnv parameterEnv = new ParameterEnv(NpContextHolder.getContext().getUserId(), pModel);
                List list = pModel.stream().map(arg_0 -> this.lambda$null$1((IParameterEnv)parameterEnv, pModel, arg_0)).collect(Collectors.toList());
                metaInfo.setParameterList(list);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return metaInfo;
        };
    }

    private List<AutomationParameterOption> getAutomationParameterOptions(List<ParameterModel> pModel, ParameterModel model, IParameterValueFormat format) throws ParameterException {
        ArrayList<AutomationParameterOption> options = new ArrayList<AutomationParameterOption>();
        ParameterCalculator parameterCalculator = new ParameterCalculator(NpContextHolder.getContext().getUserId(), pModel);
        ParameterResultset candidateVal = parameterCalculator.getCandidateValue(model.getName());
        candidateVal.forEach(c -> {
            try {
                options.add(new AutomationParameterOption(format.format(c.getValue()), c.getTitle()));
            }
            catch (ParameterException e) {
                e.printStackTrace();
            }
        });
        return options;
    }

    private AutomationParameterValueModeEnum getAutomationParameterValueMode(ParameterModel model) {
        AutomationParameterValueModeEnum vm = AutomationParameterValueModeEnum.DEFAULT;
        if (model.getSelectMode() == ParameterSelectMode.MUTIPLE) {
            vm = AutomationParameterValueModeEnum.MULTI_VALUE;
        } else if (model.getSelectMode() == ParameterSelectMode.RANGE) {
            vm = AutomationParameterValueModeEnum.RANGE;
        }
        return vm;
    }

    private String buildAutoParameterValue(IParameterEnv parameterEnv, ParameterModel model, IParameterValueFormat format) throws ParameterException {
        List ll = parameterEnv.getValue(model.getName()).getValueAsString(format);
        if (!ll.isEmpty()) {
            Iterator iter = ll.iterator();
            while (iter.hasNext()) {
                String v = (String)iter.next();
                if (v != null) continue;
                iter.remove();
            }
            return StringUtils.join(ll.iterator(), (String)",");
        }
        return "";
    }

    private static AutomationParameterDataTypeEnum convertAutomationParameterType(int dataType) {
        if (dataType == 6) {
            return AutomationParameterDataTypeEnum.STRING;
        }
        if (dataType == 5) {
            return AutomationParameterDataTypeEnum.INTEGER;
        }
        if (dataType == 3 || dataType == 8 || dataType == 10) {
            return AutomationParameterDataTypeEnum.DOUBLE;
        }
        if (dataType == 2) {
            return AutomationParameterDataTypeEnum.DATETIME;
        }
        if (dataType == 1) {
            return AutomationParameterDataTypeEnum.BOOLEAN;
        }
        return null;
    }

    private /* synthetic */ AutomationParameter lambda$null$1(IParameterEnv parameterEnv, List pModel, ParameterModel model) {
        AutomationParameterDataTypeEnum automationParameterType = ZBQueryAutomationType.convertAutomationParameterType(model.getDataType());
        IParameterValueFormat format = ParameterUtils.createValueFormat((AbstractParameterDataSourceModel)model.getDatasource());
        AutomationParameter param = null;
        try {
            String defVal = this.buildAutoParameterValue(parameterEnv, model, format);
            param = new AutomationParameter(model.getName(), model.getTitle(), automationParameterType, defVal);
            param.setValueMode(this.getAutomationParameterValueMode(model));
            param.setOptions(this.getAutomationParameterOptions(pModel, model, format));
        }
        catch (ParameterException e) {
            e.printStackTrace();
        }
        return param;
    }
}

