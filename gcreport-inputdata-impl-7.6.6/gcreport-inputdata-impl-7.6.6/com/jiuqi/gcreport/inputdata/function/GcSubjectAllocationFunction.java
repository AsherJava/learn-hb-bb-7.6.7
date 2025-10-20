/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.common.nr.function.IGcFunction
 *  com.jiuqi.np.dataengine.query.QueryContext
 */
package com.jiuqi.gcreport.inputdata.function;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.common.nr.function.IGcFunction;
import com.jiuqi.gcreport.inputdata.function.util.GcSubjectAllocationUtils;
import com.jiuqi.gcreport.inputdata.inputdata.entity.InputDataEO;
import com.jiuqi.np.dataengine.query.QueryContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class GcSubjectAllocationFunction
extends Function
implements IGcFunction {
    private static final Logger LOGGER = LoggerFactory.getLogger(GcSubjectAllocationFunction.class);
    private final String FUNCTION_NAME = "SubjectAllocation";

    public GcSubjectAllocationFunction() {
        this.parameters().add(new Parameter("tableCode", 6, "\u57fa\u7840\u6570\u636e\u8868\u540d", false));
        this.parameters().add(new Parameter("filter", 6, "\u6761\u4ef6(\u683c\u5f0f: \u5185\u90e8\u8868\u5b57\u6bb51=\u57fa\u7840\u6570\u636e\u5b57\u6bb51,\u5185\u90e8\u8868\u5b57\u6bb52=\u57fa\u7840\u6570\u636e\u5b57\u6bb52...)", false));
        this.parameters().add(new Parameter("subjectAndRateColumn", 6, "\u57fa\u7840\u6570\u636e\u4e2d\u79d1\u76ee\u548c\u6bd4\u4f8b\u5206\u914d\u5217\u3002\u683c\u5f0f\u662f\u201c\u79d1\u76ee\u5b57\u6bb5\u4ee3\u7801,\u6bd4\u4f8b\u5206\u914d\u4ee3\u7801\u201d", false));
    }

    public String name() {
        return "SubjectAllocation";
    }

    public String title() {
        return "\u62b5\u9500\u91d1\u989d\u6309\u6bd4\u4f8b\u5206\u914d\u5230\u6307\u5b9a\u7684\u79d1\u76ee";
    }

    public int getResultType(IContext iContext, List<IASTNode> list) throws SyntaxException {
        return 6;
    }

    public String category() {
        return "\u5408\u5e76\u62a5\u8868\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        QueryContext queryContext = (QueryContext)context;
        String tableCode = (String)parameters.get(0).evaluate((IContext)queryContext);
        String filter = (String)parameters.get(1).evaluate((IContext)queryContext);
        String subjectAndRateColumn = (String)parameters.get(2).evaluate((IContext)queryContext);
        HashMap<String, String> mergeUnitCodeGroupByFieldCode = new HashMap<String, String>();
        ArrayList<String> parents = new ArrayList<String>();
        try {
            InputDataEO inputData = GcSubjectAllocationUtils.getInputData(queryContext, parents);
            Map<String, Object> fieldValues = GcSubjectAllocationUtils.getBaseDataFieldValues(filter, inputData, mergeUnitCodeGroupByFieldCode);
            List<GcBaseData> baseDatas = GcSubjectAllocationUtils.listBaseDataByFilter(tableCode, fieldValues);
            return this.getSubjectAndRate(baseDatas, subjectAndRateColumn, mergeUnitCodeGroupByFieldCode, parents);
        }
        catch (Exception e) {
            LOGGER.error("\u62b5\u9500\u91d1\u989d\u6309\u6bd4\u4f8b\u5206\u914d\u5230\u6307\u5b9a\u7684\u79d1\u76ee\u5f02\u5e38", e);
            return null;
        }
    }

    public String toDescription() {
        String supperDescription = super.toDescription();
        StringBuilder buffer = new StringBuilder(supperDescription);
        buffer.append("\u8fd4\u56de\u503c\uff1a").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append(DataType.toExpression((int)6)).append("\uff1a").append(DataType.toString((int)6)).append("\uff1b").append("\u62b5\u9500\u91d1\u989d\u6bd4\u4f8b\u5206\u914d\u79d1\u76ee").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("\u793a\u4f8b\uff1a").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u573a\u666f\uff1a").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u62b5\u9500\u91d1\u989d\u6309\u6bd4\u4f8b\u5206\u914d\u5230\u6307\u5b9a\u7684\u79d1\u76ee ").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f\uff1a").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("SubjectAllocation('\u57fa\u7840\u6570\u636e\u8868\u540d','\u6761\u4ef6(\u683c\u5f0f: \u5185\u90e8\u8868\u5b57\u6bb51=\u57fa\u7840\u6570\u636e\u5b57\u6bb51,\u5185\u90e8\u8868\u5b57\u6bb52=\u57fa\u7840\u6570\u636e\u5b57\u6bb52...)','\u57fa\u7840\u6570\u636e\u4e2d\u79d1\u76ee\u548c\u6bd4\u4f8b\u5206\u914d\u5217\u3002\u683c\u5f0f\u662f\u201c\u79d1\u76ee\u5b57\u6bb5\u4ee3\u7801,\u6bd4\u4f8b\u5206\u914d\u4ee3\u7801\u201d')").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        return buffer.toString();
    }

    private String getSubjectAndRate(List<GcBaseData> baseDatas, String subjectAndRateColumn, Map<String, String> mergeUnitCodeGroupByFieldCode, List<String> parents) {
        if (CollectionUtils.isEmpty(baseDatas)) {
            return null;
        }
        if (StringUtils.isEmpty((String)subjectAndRateColumn)) {
            throw new RuntimeException("\u7f3a\u5c11\u57fa\u7840\u6570\u636e\u4e2d\u79d1\u76ee\u548c\u6bd4\u4f8b\u5206\u914d\u5217\u6761\u4ef6");
        }
        String[] columns = subjectAndRateColumn.split(",");
        if (columns.length < 2) {
            throw new RuntimeException("\u7f3a\u5c11\u57fa\u7840\u6570\u636e\u4e2d\u79d1\u76ee\u6216\u8005\u6bd4\u4f8b\u5206\u914d\u5217\u6761\u4ef6");
        }
        List<Object> baseDataFilter = new ArrayList<GcBaseData>(baseDatas);
        if (!mergeUnitCodeGroupByFieldCode.isEmpty()) {
            String baseDateHBDWFieldCode = (String)mergeUnitCodeGroupByFieldCode.keySet().stream().findFirst().get();
            String baseDateHBDWValue = mergeUnitCodeGroupByFieldCode.values().stream().findFirst().get();
            baseDataFilter = baseDatas.stream().filter(item -> baseDateHBDWValue.equals(String.valueOf(item.getFieldVal(baseDateHBDWFieldCode)))).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(baseDataFilter) && Objects.nonNull(parents)) {
                String parentCode;
                Map<Object, List<GcBaseData>> baseDataGroupByMergeUnitCode = baseDatas.stream().collect(Collectors.groupingBy(item -> item.getFieldVal(baseDateHBDWFieldCode)));
                for (int i = parents.size() - 1; i >= 0 && CollectionUtils.isEmpty(baseDataFilter = baseDataGroupByMergeUnitCode.get(parentCode = parents.get(i))); --i) {
                }
            }
        }
        if (CollectionUtils.isEmpty(baseDataFilter)) {
            return null;
        }
        String subjectColumn = columns[0];
        String rateColumn = columns[1];
        HashMap<String, Double> subjectAndRate = new HashMap<String, Double>();
        ArrayList<GcBaseData> subjectAllocationBaseDatas = new ArrayList<GcBaseData>();
        String tableName = null;
        for (GcBaseData baseData : baseDataFilter) {
            String subjectCode = String.valueOf(baseData.getFieldVal(subjectColumn));
            double rate = NumberUtils.parseDouble((Object)baseData.getFieldVal(rateColumn));
            if (Double.compare(rate, 0.0) == 0) continue;
            subjectAndRate.put(subjectCode, rate);
            subjectAllocationBaseDatas.add(baseData);
            tableName = baseData.getTableName();
        }
        if (subjectAndRate.isEmpty() || CollectionUtils.isEmpty(subjectAllocationBaseDatas)) {
            return null;
        }
        HashMap<String, Object> formulaResult = new HashMap<String, Object>();
        formulaResult.put("rateValue", subjectAndRate);
        formulaResult.put("rateInfo", subjectAllocationBaseDatas);
        formulaResult.put("tableName", tableName);
        String formulaResultStr = JsonUtils.writeValueAsString(formulaResult);
        LOGGER.debug("SubjectAllocation\u516c\u5f0f\u8fd4\u56de\u503c\uff1a" + formulaResultStr);
        return formulaResultStr;
    }
}

