/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.Expression
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.format.IDataFormator
 *  com.jiuqi.bi.syntax.format.IFormatableContext
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.dataset.textprocessor.parse.func;

import com.jiuqi.bi.dataset.BIDataRow;
import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.BIDataSetImpl;
import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.FilterItem;
import com.jiuqi.bi.dataset.expression.DSFieldNode;
import com.jiuqi.bi.dataset.expression.DSFormulaContext;
import com.jiuqi.bi.dataset.model.field.FieldType;
import com.jiuqi.bi.dataset.textprocessor.TextFormulaContext;
import com.jiuqi.bi.dataset.textprocessor.parse.DSHelper;
import com.jiuqi.bi.dataset.textprocessor.parse.func.TFunction;
import com.jiuqi.bi.dataset.textprocessor.parse.node.DSNode;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.Expression;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.format.IDataFormator;
import com.jiuqi.bi.syntax.format.IFormatableContext;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class DSPrintRows
extends TFunction {
    private static final long serialVersionUID = -9099724727013928321L;
    private static final String PUNCTUATION = ",.;:?!\uff0c\u3002\uff1b\uff1a\uff1f\uff01\u3001";

    public DSPrintRows() {
        this.parameters().add(new Parameter("ds", 5100, "\u6570\u636e\u96c6"));
        this.parameters().add(new Parameter("showText", 0, "\u6587\u672c\u683c\u5f0f\u5316\u5b57\u7b26\u4e32"));
        this.parameters().add(new Parameter("filter", 1, "\u8fc7\u6ee4\u8868\u8fbe\u5f0f", true));
    }

    public String name() {
        return "DS_PRINTROWS";
    }

    public String title() {
        return "\u6570\u636e\u96c6\u6587\u672c\u8f93\u51fa\u51fd\u6570";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 6;
    }

    public IDataFormator getDataFormator(IContext context) throws SyntaxException {
        return null;
    }

    @Override
    public boolean isDatasetNodeParam(int paramIndex) {
        return paramIndex == 0;
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        TextFormulaContext tfc = (TextFormulaContext)context;
        DSHelper helper = new DSHelper(tfc);
        IASTNode p0 = parameters.get(0);
        String dsName = this.getDSName(parameters);
        ArrayList<IASTNode> filters = new ArrayList<IASTNode>(parameters.subList(2, parameters.size()));
        Locale locale = StringUtils.isEmpty((String)tfc.getI18nLang()) ? Locale.getDefault() : Locale.forLanguageTag(tfc.getI18nLang());
        BIDataSetImpl dataset = helper.evaluate(p0, filters, locale);
        IASTNode p1 = parameters.get(1);
        if (dataset.getRecordCount() > 1) {
            HashSet<String> appearDims = new HashSet<String>();
            for (IASTNode filterNd : filters) {
                this.findoutFieldNode(appearDims, filterNd);
            }
            this.findoutFieldNode(appearDims, p1);
            try {
                dataset = helper.doAggregate(dataset, appearDims);
            }
            catch (BIDataSetException e) {
                throw new SyntaxException(e.getMessage(), (Throwable)e);
            }
        }
        Expression expr = new Expression(null, p1);
        ArrayList<String> strs = new ArrayList<String>();
        int count = dataset.getRecordCount();
        List<FilterItem> restricts = tfc.getFormulaFilter(dsName);
        List columns = dataset.getMetadata().getColumns();
        for (int i = 0; i < count; ++i) {
            String result;
            ArrayList<FilterItem> newRest = new ArrayList<FilterItem>();
            if (restricts != null) {
                newRest.addAll(restricts);
            }
            for (Column column : columns) {
                if (((BIDataSetFieldInfo)column.getInfo()).getFieldType() == FieldType.MEASURE) continue;
                ArrayList<Object> keyList = new ArrayList<Object>();
                keyList.add(dataset.get(i).getValue(column.getIndex()));
                FilterItem fi = new FilterItem(column.getName(), keyList);
                newRest.add(fi);
            }
            if (dsName != null) {
                tfc.setDSFilters(dsName, newRest);
            }
            DSFormatableContext dsCxt = new DSFormatableContext(dataset, dataset.get(i));
            if (context instanceof IFormatableContext) {
                dsCxt.setCxt((IFormatableContext)context);
            }
            if ((result = expr.evalAsString((IContext)dsCxt)) == null) continue;
            strs.add(result);
        }
        if (dsName != null) {
            tfc.setDSFilters(dsName, restricts);
        }
        return this.joinStringsWithPunctuation(strs);
    }

    public int validate(IContext context, List<IASTNode> parameters) throws SyntaxException {
        TextFormulaContext tfc = (TextFormulaContext)context;
        String dsName = this.getDSName(parameters);
        DSHelper helper = new DSHelper(tfc);
        for (int i = 1; i < parameters.size(); ++i) {
            IASTNode adjustNode = helper.adjust(dsName, parameters.get(i));
            parameters.set(i, adjustNode);
        }
        return super.validate(context, parameters);
    }

    private void findoutFieldNode(Set<String> sets, IASTNode root) {
        for (IASTNode nd : root) {
            if (!(nd instanceof DSFieldNode)) continue;
            BIDataSetFieldInfo info = ((DSFieldNode)nd).getFieldInfo();
            sets.add(info.getName().toUpperCase());
        }
    }

    private String joinStringsWithPunctuation(List<String> strs) {
        StringBuilder sb = new StringBuilder();
        if (strs != null) {
            int size = strs.size();
            for (int i = 0; i < size; ++i) {
                String s = strs.get(i);
                if (StringUtils.isEmpty((String)s)) continue;
                if (PUNCTUATION.indexOf(String.valueOf(s.charAt(0))) != -1 && sb.length() > 0 && PUNCTUATION.indexOf(String.valueOf(sb.charAt(sb.length() - 1))) != -1) {
                    sb.deleteCharAt(sb.length() - 1);
                }
                sb.append(s);
            }
        }
        if (sb.length() > 0 && PUNCTUATION.indexOf(String.valueOf(sb.charAt(sb.length() - 1))) != -1) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    private String getDSName(List<IASTNode> parameters) {
        for (int i = 0; i < parameters.size(); ++i) {
            if (!this.isDatasetNodeParam(i)) continue;
            IASTNode param = parameters.get(i);
            for (IASTNode nd : param) {
                if (!(nd instanceof DSNode)) continue;
                return ((DSNode)nd).getDSName();
            }
        }
        return null;
    }

    private class DSFormatableContext
    extends DSFormulaContext
    implements IFormatableContext {
        private IFormatableContext cxt;

        public DSFormatableContext(BIDataSetImpl dataset, BIDataRow row) {
            super(dataset, row);
        }

        public void setCxt(IFormatableContext cxt) {
            this.cxt = cxt;
        }

        public IDataFormator getDefaultFormator(IASTNode node) throws SyntaxException {
            return this.cxt == null ? null : this.cxt.getDefaultFormator(node);
        }
    }
}

