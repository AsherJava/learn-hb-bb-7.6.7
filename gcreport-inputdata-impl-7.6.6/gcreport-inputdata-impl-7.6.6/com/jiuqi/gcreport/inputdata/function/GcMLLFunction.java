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
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
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
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
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
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class GcMLLFunction
extends Function
implements IGcFunction {
    private static final Logger LOGGER = LoggerFactory.getLogger(GcMLLFunction.class);
    public final String FUNCTION_NAME = "MLL";

    public GcMLLFunction() {
        this.parameters().add(new Parameter("tableCode", 6, "\u57fa\u7840\u6570\u636e\u8868\u540d", false));
        this.parameters().add(new Parameter("filter", 6, "\u6761\u4ef6(\u683c\u5f0f: \u5185\u90e8\u8868\u5b57\u6bb51=\u57fa\u7840\u6570\u636e\u5b57\u6bb51,\u5185\u90e8\u8868\u5b57\u6bb52=\u57fa\u7840\u6570\u636e\u5b57\u6bb52...)", false));
        this.parameters().add(new Parameter("fieldCode", 6, "\u57fa\u7840\u6570\u636e\u6bdb\u5229\u7387\u5217", false));
    }

    public String name() {
        return "MLL";
    }

    public String title() {
        return "\u83b7\u53d6\u6bdb\u5229\u7387";
    }

    public int getResultType(IContext iContext, List<IASTNode> list) throws SyntaxException {
        return 3;
    }

    public String category() {
        return "\u5408\u5e76\u62a5\u8868\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        QueryContext queryContext = (QueryContext)context;
        String tableCode = (String)parameters.get(0).evaluate((IContext)queryContext);
        String filter = (String)parameters.get(1).evaluate((IContext)queryContext);
        String fieldCode = (String)parameters.get(2).evaluate((IContext)queryContext);
        HashMap<String, String> mergeUnitCodeGroupByFieldCode = new HashMap<String, String>();
        ArrayList<String> parents = new ArrayList<String>();
        try {
            InputDataEO inputData = GcSubjectAllocationUtils.getInputData(queryContext, parents);
            Map<String, Object> fieldValues = GcSubjectAllocationUtils.getBaseDataFieldValues(filter, inputData, mergeUnitCodeGroupByFieldCode);
            List<GcBaseData> baseDatas = GcSubjectAllocationUtils.listBaseDataByFilter(tableCode, fieldValues);
            return this.getMllValue(baseDatas, fieldCode, mergeUnitCodeGroupByFieldCode, parents);
        }
        catch (Exception e) {
            LOGGER.error("\u83b7\u53d6\u6bdb\u5229\u7387\u516c\u5f0f\u5f02\u5e38", e);
            return 0.0;
        }
    }

    public String toDescription() {
        String supperDescription = super.toDescription();
        StringBuilder buffer = new StringBuilder(supperDescription);
        buffer.append("\u8fd4\u56de\u503c\uff1a").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append(DataType.toExpression((int)3)).append("\uff1a").append(DataType.toString((int)3)).append("\uff1b").append("\u6bdb\u5229\u7387").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("\u793a\u4f8b\uff1a").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u573a\u666f\uff1a").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u83b7\u53d6\u6bdb\u5229\u7387 ").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f\uff1a").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("SubjectAllocation('\u57fa\u7840\u6570\u636e\u8868\u540d','\u6761\u4ef6(\u683c\u5f0f: \u5185\u90e8\u8868\u5b57\u6bb51=\u57fa\u7840\u6570\u636e\u5b57\u6bb51,\u5185\u90e8\u8868\u5b57\u6bb52=\u57fa\u7840\u6570\u636e\u5b57\u6bb52...)','\u57fa\u7840\u6570\u636e\u6bdb\u5229\u7387\u5217')").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        return buffer.toString();
    }

    private double getMllValue(List<GcBaseData> baseDatas, String fieldCode, Map<String, String> mergeUnitCodeGroupByFieldCode, List<String> parents) {
        if (StringUtils.isEmpty((String)fieldCode)) {
            throw new BusinessRuntimeException("\u83b7\u53d6\u57fa\u7840\u6570\u636e\u6bdb\u5229\u7387\u5217\u4e3a\u7a7a");
        }
        if (CollectionUtils.isEmpty(baseDatas)) {
            return 0.0;
        }
        if (mergeUnitCodeGroupByFieldCode.isEmpty()) {
            return NumberUtils.parseDouble((Object)((GcBaseData)baseDatas.stream().findFirst().get()).getFieldVal(fieldCode));
        }
        String baseDateHBDWFieldCode = (String)mergeUnitCodeGroupByFieldCode.keySet().stream().findFirst().get();
        String baseDateHBDWValue = mergeUnitCodeGroupByFieldCode.values().stream().findFirst().get();
        Optional<GcBaseData> baseDataOpt = baseDatas.stream().filter(item -> baseDateHBDWValue.equals(String.valueOf(item.getFieldVal(baseDateHBDWFieldCode)))).findFirst();
        if (baseDataOpt.isPresent()) {
            return NumberUtils.parseDouble((Object)baseDataOpt.get().getFieldVal(fieldCode));
        }
        if (Objects.isNull(parents)) {
            return 0.0;
        }
        Map<Object, List<GcBaseData>> baseDataGroupByMergeUnitCode = baseDatas.stream().collect(Collectors.groupingBy(item -> item.getFieldVal(baseDateHBDWFieldCode)));
        for (int i = parents.size() - 1; i >= 0; --i) {
            String parentCode = parents.get(i);
            List<GcBaseData> baseData = baseDataGroupByMergeUnitCode.get(parentCode);
            if (CollectionUtils.isEmpty(baseData)) continue;
            return NumberUtils.parseDouble((Object)((GcBaseData)baseData.stream().findFirst().get()).getFieldVal(fieldCode));
        }
        return 0.0;
    }
}

