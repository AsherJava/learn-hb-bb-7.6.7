/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.FunctionNode
 *  com.jiuqi.bi.syntax.function.IFunction
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.common.nr.function.INrFunction
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.parse.PreProcessingFunction
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.jtable.dataset.impl.FloatRegionRelationEvn
 *  com.jiuqi.nr.jtable.filter.RegionTabFilter
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.base.RegionData
 *  com.jiuqi.nr.jtable.params.base.RegionTab
 *  com.jiuqi.nr.jtable.params.input.RegionFilterInfo
 *  com.jiuqi.nr.jtable.params.input.RegionQueryInfo
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 */
package com.jiuqi.gcreport.inputdata.function;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.FunctionNode;
import com.jiuqi.bi.syntax.function.IFunction;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.common.nr.function.INrFunction;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.inputdata.function.sumhb.dto.SumhbParam;
import com.jiuqi.gcreport.inputdata.function.sumhb.dto.SumhbParamBuilder;
import com.jiuqi.gcreport.inputdata.function.sumhb.service.SumHbService;
import com.jiuqi.gcreport.inputdata.util.InputDataNameProvider;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.parse.PreProcessingFunction;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.jtable.dataset.impl.FloatRegionRelationEvn;
import com.jiuqi.nr.jtable.filter.RegionTabFilter;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.base.RegionData;
import com.jiuqi.nr.jtable.params.base.RegionTab;
import com.jiuqi.nr.jtable.params.input.RegionFilterInfo;
import com.jiuqi.nr.jtable.params.input.RegionQueryInfo;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class SumHbZbFunction
extends PreProcessingFunction
implements INrFunction {
    private static final long serialVersionUID = 899333003095435298L;
    private final Logger logger = LoggerFactory.getLogger(SumHbZbFunction.class);
    public static final String FUNC_NAME = "SUMHBZB";

    public SumHbZbFunction() {
        this.parameters().add(new Parameter("ZB", 0, "\u8981\u6c42\u548c\u7684\u6307\u6807\uff0c\u683c\u5f0f\u4e3a\u8868\u5355code[\u6307\u6807code,SUM]\u6216\u8868\u5355code[\u6307\u6807\u5750\u6807,SUM]\uff0c\u4f8b\u5982SUMHBZB(WL04[AMT,SUM])\u6216\u8005SUMHBZB(WL04[2,1,SUM])"));
        this.parameters().add(new Parameter("FILTER", 6, "\u8fc7\u6ee4\u6761\u4ef6\uff0c\u53ef\u4ee5\u4e3a\u7a7a\uff0c\u5982\u679c\u9700\u8981\u67e5\u8be2\u533a\u57df\u5185\u90e8\u5206\u6570\u636e\uff0c\u586b\u5199\u5bf9\u5e94\u8fc7\u6ee4\u6761\u4ef6\u3002", true));
    }

    public String name() {
        return FUNC_NAME;
    }

    public String title() {
        return "\u53d6\u5185\u90e8\u62b5\u6d88\u8868\u6c47\u603b";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) {
        if (CollectionUtils.isEmpty(parameters)) {
            return 3;
        }
        this.getSumhbParam((QueryContext)context, parameters);
        return 3;
    }

    private SumhbParam getSumhbParam(QueryContext queryContext, List<IASTNode> parameters) {
        SumhbParamBuilder sumhbParamBuilder;
        FormSchemeDefine formSchemeDefine;
        ReportFmlExecEnvironment env = (ReportFmlExecEnvironment)queryContext.getExeContext().getEnv();
        try {
            formSchemeDefine = env.getFormSchemeDefine();
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.function.sumhbparamsparseformdefine"));
        }
        InputDataNameProvider inputDataNameProvider = (InputDataNameProvider)SpringContextUtils.getBean(InputDataNameProvider.class);
        String inputTableName = inputDataNameProvider.getTableNameByTaskId(formSchemeDefine.getTaskKey());
        try {
            sumhbParamBuilder = new SumhbParamBuilder(queryContext, FUNC_NAME, inputTableName);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException((Throwable)e);
        }
        return sumhbParamBuilder.build(parameters);
    }

    public Object evalute(IContext context, List<IASTNode> parameters) {
        QueryContext queryContext = (QueryContext)context;
        ReportFmlExecEnvironment env = (ReportFmlExecEnvironment)queryContext.getExeContext().getEnv();
        try {
            FormSchemeDefine formSchemeDefine = env.getFormSchemeDefine();
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.function.sumhbparamsparseformdefine"));
        }
        SumhbParam sumhbParam = this.getSumhbParam((QueryContext)context, parameters);
        DimensionValueSet ds = queryContext.getCurrentMasterKey();
        String orgId = (String)ds.getValue("MD_ORG");
        String currencyId = (String)ds.getValue("MD_CURRENCY");
        String cacheKey = SumHbService.getCacheKey(orgId, currencyId, sumhbParam);
        if (queryContext.getCache().containsKey(cacheKey)) {
            return queryContext.getCache().get(SumHbService.getCacheKey(orgId, currencyId, sumhbParam));
        }
        try {
            FunctionNode functionNode = new FunctionNode(null, (IFunction)this, parameters);
            ArrayList<FunctionNode> functionNodes = new ArrayList<FunctionNode>();
            functionNodes.add(functionNode);
            ((SumHbService)SpringContextUtils.getBean(SumHbService.class)).sumHbZbFunctionCalc(queryContext, FUNC_NAME, functionNodes);
            if (queryContext.getCache().containsKey(cacheKey)) {
                return queryContext.getCache().get(cacheKey);
            }
        }
        catch (Exception e) {
            this.logger.error("SUMHBZB\u8fd0\u7b97\u62a5\u9519", e);
        }
        return 0;
    }

    private RegionQueryInfo initRegionQueryInfo(IJtableParamService entityControlService, JtableContext jtableContext, DimensionValueSet dimensionValueSet, FloatRegionRelationEvn regionRelationEvn) {
        RegionQueryInfo regionQueryInfo = new RegionQueryInfo();
        regionQueryInfo.setContext(jtableContext);
        RegionData region = regionRelationEvn.getRegionData();
        regionQueryInfo.setRegionKey(region.getKey());
        if (region == null) {
            return regionQueryInfo;
        }
        List regionTabs = entityControlService.getRegionTabs(region.getKey());
        if (CollectionUtils.isEmpty(regionTabs)) {
            return regionQueryInfo;
        }
        if (null != regionTabs) {
            RegionTabFilter regionTabSettingFilter = new RegionTabFilter(jtableContext, dimensionValueSet);
            for (RegionTab regionTab : regionTabs) {
                if (!regionTabSettingFilter.accept(regionTab)) continue;
                RegionFilterInfo regionFilterInfo = regionQueryInfo.getFilterInfo() == null ? new RegionFilterInfo() : regionQueryInfo.getFilterInfo();
                regionQueryInfo.setFilterInfo(regionFilterInfo);
                regionFilterInfo.setFilterFormula(Arrays.asList(regionTab.getFilter()));
                regionFilterInfo.setCellQuerys(region.getCells());
                break;
            }
        }
        return regionQueryInfo;
    }

    public String category() {
        return "\u5408\u5e76\u62a5\u8868\u51fd\u6570";
    }

    public String toDescription() {
        String supperDescription = super.toDescription();
        StringBuilder buffer = new StringBuilder(supperDescription);
        buffer.append("\u8fd4\u56de\u503c\uff1a").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append(DataType.toExpression((int)3)).append("\uff1a").append(DataType.toString((int)3)).append("\uff1b").append("\u5185\u90e8\u8868\u91d1\u989d\u5b57\u6bb5\u5408\u8ba1\u503c").append(StringUtils.LINE_SEPARATOR);
        buffer.append("\u793a\u4f8b\uff1a").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u573a\u666f1\uff1a").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u4ee3\u7801\u4e3aKV95UAK7\u7684\u5185\u90e8\u8868\u91d1\u989d\u5b57\u6bb5\u5408\u8ba1\u503c\u3002 ").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f\uff1a").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("SUMHBZB(KV95UAK7[4,4,SUM]) \u6216  SUMHBZB(KV95UAK7[AMT,SUM])").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u573a\u666f2\uff1a").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u4ee3\u7801\u4e3aKV95UAK7\u7684\u5185\u90e8\u8868\u4e2d\u79d1\u76ee\u662f100101\u7684\u91d1\u989d\u5b57\u6bb5\u5408\u8ba1\u503c\u3002 ").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f\uff1a").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("SUMHBZB(KV95UAK7[4,4,SUM],\"SUBJECTCODE='100101'\") \u6216  SUMHBZB(KV95UAK7[AMT,SUM],\"SUBJECTCODE='100101'\")").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u6ce8\u610f\uff1a\u5f53\u4e00\u4e2a\u8868\u5355\u4e2d\u5b58\u5728\u591a\u4e2a\u5185\u90e8\u8868\u533a\u57df\u65f6\uff0c\u91d1\u989d\u5b57\u6bb5\u901a\u8fc7\u5355\u5143\u683c\u5750\u6807\u6307\u5b9a\uff0c\u4e0d\u8981\u5199\u5b57\u6bb5\u540d\u79f0\u3002").append(StringUtils.LINE_SEPARATOR);
        return buffer.toString();
    }
}

