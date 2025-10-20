/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.reportparser.IReportDynamicNodeProvider
 *  com.jiuqi.bi.syntax.reportparser.ReportFormulaParser
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 */
package com.jiuqi.bde.base.formula;

import com.jiuqi.bde.base.formula.FetchFormulaContext;
import com.jiuqi.bde.base.formula.FetchFormulaUtil;
import com.jiuqi.bde.base.formula.node.FetchFormulaNodeProvider;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.reportparser.IReportDynamicNodeProvider;
import com.jiuqi.bi.syntax.reportparser.ReportFormulaParser;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FetchFormulaPaser {
    private ReportFormulaParser parser;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public FetchFormulaPaser() {
        this.parser = ReportFormulaParser.getInstance();
        this.parser.registerDynamicNodeProvider((IReportDynamicNodeProvider)new FetchFormulaNodeProvider());
    }

    public IExpression parseEval(String formula, FetchFormulaContext context) {
        Assert.isNotEmpty((String)formula, (String)"\u903b\u8f91\u8868\u8fbe\u5f0f\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        try {
            return this.parser.parseEval(formula, (IContext)context);
        }
        catch (Exception e) {
            this.logger.error(String.format("\u903b\u8f91\u8868\u8fbe\u5f0f[%1$s]\u89e3\u6790\u51fa\u73b0\u9519\u8bef\uff1a%2$s", formula, e.getMessage()), e);
            throw new BusinessRuntimeException(String.format("\u903b\u8f91\u8868\u8fbe\u5f0f[%1$s]\u89e3\u6790\u51fa\u73b0\u9519\u8bef\uff1a%2$s", FetchFormulaUtil.formatFormulaInfo(formula), FetchFormulaUtil.formatMessageInfo(e.getMessage())), (Throwable)e);
        }
    }

    public Object evaluate(String formula, FetchFormulaContext context) {
        Assert.isNotEmpty((String)formula, (String)"\u903b\u8f91\u8868\u8fbe\u5f0f\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        HashSet<String> fetchSettingIdSet = new HashSet<String>();
        Pattern BILL_LOGIC_PATTERN = Pattern.compile("BIZMODEL\\[(.*?)\\]");
        Matcher matcher = BILL_LOGIC_PATTERN.matcher(formula);
        while (matcher.find()) {
            fetchSettingIdSet.add(matcher.group(1));
        }
        if (!CollectionUtils.isEmpty(fetchSettingIdSet) && context.getFetchResultMap() != null && !context.getFetchResultMap().isEmpty()) {
            HashMap<String, String> settingIdReplaceMap = new HashMap<String, String>();
            HashMap<String, Object> fetchResultMapWrapper = new HashMap<String, Object>();
            for (Map.Entry<String, Object> entry : context.getFetchResultMap().entrySet()) {
                if (!fetchSettingIdSet.contains(entry.getKey())) {
                    fetchResultMapWrapper.put(entry.getKey(), entry.getValue());
                    continue;
                }
                String newId = "ID" + entry.getKey();
                settingIdReplaceMap.put(entry.getKey(), newId);
                fetchResultMapWrapper.put(newId, entry.getValue());
            }
            context.setFetchResultMap(fetchResultMapWrapper);
            for (Map.Entry<String, Object> entry : settingIdReplaceMap.entrySet()) {
                formula = formula.replace(entry.getKey(), (CharSequence)entry.getValue());
            }
        }
        try {
            Object val = this.parser.parseEval(formula, (IContext)context).evaluate((IContext)context);
            return val == null ? Integer.valueOf(0) : val;
        }
        catch (Exception e) {
            this.logger.error(String.format("\u903b\u8f91\u8868\u8fbe\u5f0f[%1$s]\u8fd0\u7b97\u51fa\u73b0\u9519\u8bef\uff1a%2$s", formula, e.getMessage()), e);
            throw new BusinessRuntimeException(String.format("\u903b\u8f91\u8868\u8fbe\u5f0f[%1$s]\u8fd0\u7b97\u51fa\u73b0\u9519\u8bef\uff1a%2$s", FetchFormulaUtil.formatFormulaInfo(formula), FetchFormulaUtil.formatMessageInfo(e.getMessage())), (Throwable)e);
        }
    }
}

