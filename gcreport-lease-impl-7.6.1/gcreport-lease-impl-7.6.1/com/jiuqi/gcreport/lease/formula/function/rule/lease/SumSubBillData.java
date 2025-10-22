/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.DataSet
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.function.IFunctionProvider
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.billcore.util.InvestBillTool
 *  com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportDataRow
 *  com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportDataSet
 *  com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportExceutorContext
 *  com.jiuqi.gcreport.common.cache.formula.IFunctionCache
 *  com.jiuqi.gcreport.common.nr.function.IGcFunction
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.nr.impl.function.GcReportFunctionProvider
 *  com.jiuqi.gcreport.org.api.period.MonthAdapter
 *  com.jiuqi.gcreport.org.api.period.YearPeriodDO
 *  com.jiuqi.gcreport.org.api.period.YearPeriodUtil
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataSetExprEvaluator
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.util.StringUtils
 *  com.jiuqi.va.formula.common.utils.FunctionUtils
 *  org.assertj.core.util.Lists
 */
package com.jiuqi.gcreport.lease.formula.function.rule.lease;

import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.DataSet;
import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.IFunctionProvider;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.gcreport.billcore.util.InvestBillTool;
import com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportDataRow;
import com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportDataSet;
import com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportExceutorContext;
import com.jiuqi.gcreport.common.cache.formula.IFunctionCache;
import com.jiuqi.gcreport.common.nr.function.IGcFunction;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.nr.impl.function.GcReportFunctionProvider;
import com.jiuqi.gcreport.org.api.period.MonthAdapter;
import com.jiuqi.gcreport.org.api.period.YearPeriodDO;
import com.jiuqi.gcreport.org.api.period.YearPeriodUtil;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataSetExprEvaluator;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.util.StringUtils;
import com.jiuqi.va.formula.common.utils.FunctionUtils;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SumSubBillData
extends Function
implements IGcFunction,
IFunctionCache {
    private static final long serialVersionUID = 1L;
    public static final String FUNCTION_NAME = "SumSubBillData";
    private final String CURR = "CURR";
    private final String PRIOR = "PRIOR";
    private final String ALL = "ALL";
    private final String TOTAL = "TOTAL";
    @Autowired
    private IDataAccessProvider provider;
    @Autowired
    private IDataDefinitionRuntimeController runtimeController;
    private static transient ThreadLocal<Map<String, String>> subTable2ChangeMonthFieldMap = new ThreadLocal();

    public SumSubBillData() {
        this.parameters().add(new Parameter("subBillZb", 6, "\u5b50\u8868\u5355\u636e\u5b57\u6bb5"));
        this.parameters().add(new Parameter("periodRange", 6, "\u65f6\u671f\u8303\u56f4"));
        this.parameters().add(new Parameter("filter", 6, "\u5b50\u8868\u8fc7\u6ee4\u6761\u4ef6(\u53ef\u7701\u7565)", true));
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        String subTableName;
        QueryContext queryContext = (QueryContext)context;
        GcReportExceutorContext executorContext = (GcReportExceutorContext)queryContext.getExeContext();
        DefaultTableEntity master = executorContext.getData();
        String masterTableName = executorContext.getDefaultGroupName();
        String subBillZb = (String)parameters.get(0).evaluate(null);
        String periodRange = (String)parameters.get(1).evaluate(null);
        String filter = null;
        if (parameters.size() >= 3) {
            filter = (String)parameters.get(2).evaluate(null);
        }
        this.initSubTable2ChangeMonthFeildMap();
        if (subBillZb.contains("[")) {
            subTableName = subBillZb.substring(0, subBillZb.indexOf("["));
            subBillZb = subBillZb.substring(subBillZb.indexOf("[") + 1, subBillZb.indexOf("]"));
        } else {
            subTableName = (String)queryContext.getMasterKeys().getValue("subTableName");
            if (com.jiuqi.common.base.util.StringUtils.isEmpty((String)subTableName)) {
                throw new BusinessRuntimeException("SumSubBillData\u51fd\u6570\u8fd0\u7b97\u8fc7\u7a0b\u4e2d\u51fa\u73b0\uff1a" + masterTableName + "\u4e3b\u8868\u4e0b\u65e0\u5b50\u8868");
            }
        }
        List billItemData = InvestBillTool.getBillItemByMasterId((String)master.getId(), (String)subTableName);
        if ("CURR".equals(periodRange)) {
            return this.getCurrSumValue(subBillZb, master, billItemData, filter, subTableName, queryContext);
        }
        if ("PRIOR".equals(periodRange)) {
            return this.getPriorSumValue(subBillZb, master, billItemData, filter, subTableName, queryContext);
        }
        if ("ALL".equals(periodRange)) {
            return this.getAllSumValue(subBillZb, master, billItemData, filter, subTableName, queryContext);
        }
        if ("TOTAL".equals(periodRange)) {
            return this.getTotalSumValue(subBillZb, master, billItemData, filter, subTableName, queryContext);
        }
        return null;
    }

    private void initSubTable2ChangeMonthFeildMap() {
        Map<String, String> table2FieldMap = subTable2ChangeMonthFieldMap.get();
        if (null == table2FieldMap || table2FieldMap.isEmpty()) {
            table2FieldMap = new HashMap<String, String>();
            table2FieldMap.put("GC_COMMONASSETBILLITEM", "DPCAMONTH");
            table2FieldMap.put("GC_INVESTBILLITEM", "CHANGEDATE");
            subTable2ChangeMonthFieldMap.set(table2FieldMap);
        }
    }

    private double getCurrSumValue(String subBillZb, DefaultTableEntity master, List<Map<String, Object>> billItemDatas, String filter, String tableName, QueryContext context) {
        Double sumValue = 0.0;
        YearPeriodDO yearPeriodUtil = YearPeriodUtil.transform(null, (String)context.getMasterKeys().getValue("DATATIME").toString());
        int currYear = yearPeriodUtil.getYear();
        MonthAdapter monthAdapter = MonthAdapter.getInstance((YearPeriodDO)yearPeriodUtil);
        int endMonth = monthAdapter.getEndMonth();
        for (Map<String, Object> item : billItemDatas) {
            int[] yearAndMonthArr = this.getYearAndMonth(item, tableName);
            int subBillYear = yearAndMonthArr[0];
            int subBillMonth = yearAndMonthArr[1];
            if (currYear != subBillYear || subBillMonth > endMonth || !this.filterData(context.getMasterKeys(), filter, master, item, tableName)) continue;
            Double monthlyAmt = (Double)item.get(subBillZb);
            sumValue = NumberUtils.sum((Double)sumValue, (Double)monthlyAmt);
        }
        return sumValue;
    }

    private double getAllSumValue(String subBillZb, DefaultTableEntity master, List<Map<String, Object>> billItemDatas, String filter, String tableName, QueryContext context) {
        Double sumValue = 0.0;
        YearPeriodDO yearPeriodUtil = YearPeriodUtil.transform(null, (String)context.getMasterKeys().getValue("DATATIME").toString());
        int currYear = yearPeriodUtil.getYear();
        MonthAdapter monthAdapter = MonthAdapter.getInstance((YearPeriodDO)yearPeriodUtil);
        int endMonth = monthAdapter.getEndMonth();
        for (Map<String, Object> item : billItemDatas) {
            int[] yearAndMonthArr = this.getYearAndMonth(item, tableName);
            int subBillYear = yearAndMonthArr[0];
            int subBillMonth = yearAndMonthArr[1];
            if (subBillYear > currYear || subBillMonth > endMonth + (currYear - subBillYear) * 12 || !this.filterData(context.getMasterKeys(), filter, master, item, tableName)) continue;
            Double monthlyAmt = (Double)item.get(subBillZb);
            sumValue = NumberUtils.sum((Double)sumValue, (Double)monthlyAmt);
        }
        return sumValue;
    }

    private double getTotalSumValue(String subBillZb, DefaultTableEntity master, List<Map<String, Object>> billItemDatas, String filter, String tableName, QueryContext context) {
        Double sumValue = 0.0;
        for (Map<String, Object> item : billItemDatas) {
            if (!this.filterData(context.getMasterKeys(), filter, master, item, tableName)) continue;
            Double monthlyAmt = (Double)item.get(subBillZb);
            sumValue = NumberUtils.sum((Double)sumValue, (Double)monthlyAmt);
        }
        return sumValue;
    }

    private int[] getYearAndMonth(Map<String, Object> item, String subTableName) {
        String changeMonth = (String)item.get("CHANGEMONTH");
        Map<String, String> sub2ChangeMonthFeildMap = subTable2ChangeMonthFieldMap.get();
        String fieldCode = sub2ChangeMonthFeildMap.get(subTableName);
        if (!com.jiuqi.common.base.util.StringUtils.isEmpty((String)fieldCode)) {
            Object fieldVal = item.get(fieldCode);
            changeMonth = fieldVal instanceof Date ? DateUtils.format((Date)((Date)fieldVal)) : (String)item.get(fieldCode);
        }
        int[] arr = new int[2];
        if (!com.jiuqi.common.base.util.StringUtils.isEmpty((String)changeMonth)) {
            String[] changeMonthArr = changeMonth.split("-");
            arr[0] = Integer.valueOf(changeMonthArr[0]);
            arr[1] = Integer.valueOf(changeMonthArr[1]);
        }
        return arr;
    }

    private double getPriorSumValue(String subBillZb, DefaultTableEntity master, List<Map<String, Object>> billItemDatas, String filter, String tableName, QueryContext context) {
        Double sumValue = 0.0;
        YearPeriodDO yearPeriodUtil = YearPeriodUtil.transform(null, (String)context.getMasterKeys().getValue("DATATIME").toString());
        int currYear = yearPeriodUtil.getYear();
        for (Map<String, Object> item : billItemDatas) {
            int[] yearAndMonthArr = this.getYearAndMonth(item, tableName);
            int subBillYear = yearAndMonthArr[0];
            if (subBillYear >= currYear || !this.filterData(context.getMasterKeys(), filter, master, item, tableName)) continue;
            Double monthlyAmt = (Double)item.get(subBillZb);
            sumValue = NumberUtils.sum((Double)sumValue, (Double)monthlyAmt);
        }
        return sumValue;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean filterData(DimensionValueSet ds, String formula, DefaultTableEntity master, Map<String, Object> item, String tableName) {
        if (com.jiuqi.common.base.util.StringUtils.isEmpty((String)formula)) {
            return true;
        }
        YearPeriodDO yearPeriod = YearPeriodUtil.transform(null, (String)ds.getValue("DATATIME").toString());
        formula = StringUtils.replace((String)formula, (String)"[CURR_YEAR]", (String)String.valueOf(yearPeriod.getYear()));
        formula = StringUtils.replace((String)formula, (String)"[CURR_TIME]", (String)String.valueOf(yearPeriod.getPeriod()));
        GcReportDataSet dataSet = new GcReportDataSet(new String[]{tableName});
        try (IDataSetExprEvaluator evaluator = this.provider.newDataSetExprEvaluator((DataSet)dataSet);){
            GcReportExceutorContext context = new GcReportExceutorContext(this.runtimeController);
            context.registerFunctionProvider((IFunctionProvider)new GcReportFunctionProvider());
            context.setDefaultGroupName(tableName);
            context.setDataSet(dataSet);
            context.setData(master);
            evaluator.prepare((ExecutorContext)context, ds, formula);
            GcReportDataRow row = new GcReportDataRow(dataSet.getMetadata());
            row.setData(master);
            DefaultTableEntity entity = new DefaultTableEntity();
            entity.resetFields(item);
            row.setDatas(Lists.list((Object[])new DefaultTableEntity[]{entity}));
            boolean bl = evaluator.judge((DataRow)row);
            return bl;
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u516c\u5f0f\u6267\u884c\u9519\u8bef\uff0c\u9519\u8bef\u516c\u5f0f\uff1a" + formula, (Throwable)e);
        }
    }

    public String toDescription() {
        StringBuilder buffer = new StringBuilder(64);
        buffer.append("\u51fd\u6570\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ");
        this.toDeclaration(buffer);
        buffer.append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u8bf4\u660e\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append(this.title()).append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u53c2\u6570\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("subBillZb").append("\uff1a").append(DataType.toString((int)6)).append("\uff1bsubBillZb(\u5355\u636e\u5b50\u8868\u5b57\u6bb5)").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("timeType").append("\uff1a").append(DataType.toString((int)6)).append("\uff1bCURR\uff08\u5f53\u524d\u5e74\u5e74\u521d\u5230\u5f53\u671f\uff09,PRIOR\uff08\u6700\u65e9\u4e00\u671f\u5230\u4e0a\u5e74\u5e74\u672b\uff09, ALL(\u6700\u65e9\u4e00\u671f\u5230\u5f53\u671f), TOTAL(\u6240\u6709\u671f)").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("filter").append("\uff1a").append(DataType.toString((int)6)).append("\uff1b\u8fc7\u6ee4\u6761\u4ef6").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u8fd4\u56de\u503c\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append(DataType.toExpression((int)3)).append("\uff1a").append(DataType.toString((int)3)).append("\uff1b").append("\u5b50\u8868\u5b57\u6bb5\u65f6\u671f\u8303\u56f4\u5185\u7684\u6c47\u603b\u91d1\u989d").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u793a\u4f8b\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u573a\u666f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u83b7\u53d6 \u79df\u8d41\u4ed8\u6b3e\u989d\u5f53\u524d\u5e74\u5e74\u521d\u5230\u5f53\u671f\u7684\u6c47\u603b\u91d1\u989d ").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("SumSubBillData('LEASEPAYAMT','CURR')").append(FunctionUtils.LINE_SEPARATOR);
        return buffer.toString();
    }

    public String name() {
        return FUNCTION_NAME;
    }

    public String title() {
        return "\u83b7\u53d6\u5b50\u8868\u5b57\u6bb5\u65f6\u671f\u8303\u56f4\u5185\u7684\u6c47\u603b\u91d1\u989d";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) {
        return 3;
    }

    public String category() {
        return "\u5408\u5e76\u62a5\u8868\u51fd\u6570";
    }

    public void enableCache() {
        subTable2ChangeMonthFieldMap.set(new HashMap());
    }

    public void releaseCache() {
        subTable2ChangeMonthFieldMap.remove();
    }
}

