/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.quickreport.engine.IReportEngine
 *  com.jiuqi.bi.quickreport.engine.ReportEngineFactory
 *  com.jiuqi.bi.quickreport.model.QuickReport
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.data.ArrayData
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FuncReadWriteType
 *  com.jiuqi.np.dataengine.parse.AdvanceFunction
 *  com.jiuqi.np.dataengine.parse.IReportFunction
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.definition.common.SpringBeanProvider
 *  com.jiuqi.nr.common.util.TimeDimUtils
 *  com.jiuqi.nvwa.framework.parameter.IParameterEnv
 *  com.jiuqi.nvwa.framework.parameter.ParameterEnv
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterModel
 *  com.jiuqi.nvwa.quickreport.service.QuickReportModelService
 */
package com.jiuqi.nr.function.func;

import com.jiuqi.bi.quickreport.engine.IReportEngine;
import com.jiuqi.bi.quickreport.engine.ReportEngineFactory;
import com.jiuqi.bi.quickreport.model.QuickReport;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.data.ArrayData;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.parse.AdvanceFunction;
import com.jiuqi.np.dataengine.parse.IReportFunction;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.definition.common.SpringBeanProvider;
import com.jiuqi.nr.common.util.TimeDimUtils;
import com.jiuqi.nvwa.framework.parameter.IParameterEnv;
import com.jiuqi.nvwa.framework.parameter.ParameterEnv;
import com.jiuqi.nvwa.framework.parameter.model.ParameterModel;
import com.jiuqi.nvwa.quickreport.service.QuickReportModelService;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class QuickReportWriteBack
extends AdvanceFunction
implements IReportFunction {
    private static final long serialVersionUID = 6914179566017710691L;

    public QuickReportWriteBack() {
        this.parameters().add(new Parameter("quickReportName", 6, "\u5feb\u901f\u5206\u6790\u8868\u8868\u540d"));
        this.parameters().add(new Parameter("paraValue", 6, "\u53c2\u6570\u503c\u4e0e\u5feb\u901f\u5206\u6790\u8868\u4e2d\u7684\u53c2\u6570\u6309\u987a\u5e8f\u4e00\u4e00\u5bf9\u5e94"));
    }

    public boolean isInfiniteParameter() {
        return true;
    }

    public String category() {
        return "\u5176\u5b83\u51fd\u6570";
    }

    public int validate(IContext context, List<IASTNode> parameters) throws SyntaxException {
        QueryContext qContext = (QueryContext)context;
        try {
            QuickReportModelService quickReportModelService = (QuickReportModelService)SpringBeanProvider.getBean(QuickReportModelService.class);
            String reportId = (String)parameters.get(0).evaluate(context);
            QuickReport report = quickReportModelService.getQuickReportByGuidOrId(reportId);
            if (report == null) {
                throw new SyntaxException("\u6ca1\u6709\u627e\u5230\u8868\u540d\u4e3a" + reportId + "\u7684\u5feb\u901f\u5206\u6790\u8868\uff01");
            }
            List reportParas = report.getParamModels();
            if (parameters.size() > 1 && parameters.size() - 1 != reportParas.size()) {
                throw new SyntaxException("\u53c2\u6570\u503c\u4e2a\u6570\u4e0e\u5feb\u901f\u5206\u6790\u8868" + reportId + "\u7684\u53c2\u6570\u4e2a\u6570\u4e0d\u5339\u914d\uff01");
            }
        }
        catch (Exception e) {
            throw new SyntaxException("\u51fd\u6570" + this.name() + "\u53c2\u6570\u6821\u9a8c\u51fa\u9519\uff1a" + e.getMessage(), (Throwable)e);
        }
        return this.getResultType((IContext)qContext, parameters);
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        try {
            QuickReportModelService quickReportModelService = (QuickReportModelService)SpringBeanProvider.getBean(QuickReportModelService.class);
            String reportId = (String)parameters.get(0).evaluate(context);
            QuickReport report = quickReportModelService.getQuickReportByGuidOrId(reportId);
            if (report == null) {
                throw new SyntaxException("\u6ca1\u6709\u627e\u5230\u8868\u540d\u4e3a" + reportId + "\u7684\u5feb\u901f\u5206\u6790\u8868\uff01");
            }
            String userId = NpContextHolder.getContext().getUserId();
            List reportParas = report.getParamModels();
            ParameterEnv paramEnv = new ParameterEnv(userId, reportParas);
            if (parameters.size() > 1) {
                if (parameters.size() - 1 != reportParas.size()) {
                    throw new SyntaxException("\u53c2\u6570\u503c\u4e2a\u6570\u4e0e\u5feb\u901f\u5206\u6790\u8868" + reportId + "\u7684\u53c2\u6570\u4e2a\u6570\u4e0d\u5339\u914d\uff01");
                }
                HashMap<String, List<Object>> paramValueList = new HashMap<String, List<Object>>();
                for (int i = 1; i < parameters.size(); ++i) {
                    List<Object> valueList;
                    IASTNode p = parameters.get(i);
                    ParameterModel paraModel = (ParameterModel)reportParas.get(i - 1);
                    Object value = p.evaluate(context);
                    if (value == null || value instanceof String && StringUtils.isEmpty((String)value.toString())) continue;
                    if (value instanceof ArrayData) {
                        ArrayData array = (ArrayData)value;
                        valueList = array.toList();
                    } else {
                        if (paraModel.getDatasource() != null && paraModel.getDatasource().getTimegranularity() >= 0) {
                            value = TimeDimUtils.periodToTimeKey((String)value.toString());
                        }
                        valueList = Collections.singletonList(value);
                    }
                    paramValueList.put(paraModel.getName(), valueList);
                }
                paramEnv.initValue(paramValueList);
            }
            IReportEngine engine = ReportEngineFactory.createEngine((String)userId, (QuickReport)report, (IParameterEnv)paramEnv);
            engine.initParamEnv();
            engine.open(16);
            return true;
        }
        catch (Exception e) {
            throw new SyntaxException(e.getMessage(), (Throwable)e);
        }
    }

    public int getResultType(IContext arg0, List<IASTNode> arg1) throws SyntaxException {
        return 1;
    }

    public String name() {
        return "QuickReportWriteBack";
    }

    public String title() {
        return "\u5feb\u901f\u5206\u6790\u8868\u56de\u5199";
    }

    public DataEngineConsts.FuncReadWriteType getReadWriteType() {
        return DataEngineConsts.FuncReadWriteType.UNKNOWN;
    }
}

