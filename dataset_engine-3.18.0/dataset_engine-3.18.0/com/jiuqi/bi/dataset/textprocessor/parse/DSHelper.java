/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.ASTNode
 *  com.jiuqi.bi.syntax.ast.ASTNodeType
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.data.DataNode
 *  com.jiuqi.bi.syntax.format.IDataFormator
 *  com.jiuqi.bi.syntax.function.FunctionNode
 *  com.jiuqi.bi.syntax.function.IFunction
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.operator.Equal
 *  com.jiuqi.bi.syntax.operator.Or
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.sql.RangeValues
 *  com.jiuqi.bi.text.DateFormatEx
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.bi.util.time.TimeCalcException
 *  com.jiuqi.bi.util.time.TimeHelper
 *  com.jiuqi.nvwa.framework.parameter.IParameterEnv
 *  org.antlr.v4.runtime.misc.ParseCancellationException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.dataset.textprocessor.parse;

import com.jiuqi.bi.dataset.BIDataSet;
import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.BIDataSetImpl;
import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DSContext;
import com.jiuqi.bi.dataset.DataType;
import com.jiuqi.bi.dataset.FilterItem;
import com.jiuqi.bi.dataset.expression.DSExpression;
import com.jiuqi.bi.dataset.expression.DSFieldNode;
import com.jiuqi.bi.dataset.expression.DSFormulaContext;
import com.jiuqi.bi.dataset.expression.DSFormularManager;
import com.jiuqi.bi.dataset.expression.DatasetFormulaParser;
import com.jiuqi.bi.dataset.expression.RestrictTagNode;
import com.jiuqi.bi.dataset.function.Lag;
import com.jiuqi.bi.dataset.manager.TimeKeyBuilder;
import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.model.field.CalcMode;
import com.jiuqi.bi.dataset.model.field.DSField;
import com.jiuqi.bi.dataset.model.field.FieldType;
import com.jiuqi.bi.dataset.model.field.TimeGranularity;
import com.jiuqi.bi.dataset.restrict.RestrictionTag;
import com.jiuqi.bi.dataset.textprocessor.TextFormulaContext;
import com.jiuqi.bi.dataset.textprocessor.parse.func.TFunctionNode;
import com.jiuqi.bi.dataset.textprocessor.parse.node.DSNode;
import com.jiuqi.bi.dataset.textprocessor.parse.node.IAdjustable;
import com.jiuqi.bi.dataset.textprocessor.parse.node.IDSNodeDescriptor;
import com.jiuqi.bi.dataset.textprocessor.parse.node.TFieldNode;
import com.jiuqi.bi.dataset.textprocessor.parse.node.TRestrictNode;
import com.jiuqi.bi.dataset.textprocessor.parse.node.TRestrictTagNode;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.ASTNode;
import com.jiuqi.bi.syntax.ast.ASTNodeType;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.data.DataNode;
import com.jiuqi.bi.syntax.format.IDataFormator;
import com.jiuqi.bi.syntax.function.FunctionNode;
import com.jiuqi.bi.syntax.function.IFunction;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.operator.Equal;
import com.jiuqi.bi.syntax.operator.Or;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.sql.RangeValues;
import com.jiuqi.bi.text.DateFormatEx;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.bi.util.time.TimeCalcException;
import com.jiuqi.bi.util.time.TimeHelper;
import com.jiuqi.nvwa.framework.parameter.IParameterEnv;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.json.JSONObject;

public final class DSHelper {
    private TextFormulaContext tfc;

    public DSHelper(TextFormulaContext tfc) {
        this.tfc = tfc;
    }

    public BIDataSetImpl evaluate(IASTNode dsNode, List<IASTNode> filterNodes, Locale locale) throws SyntaxException {
        return this.evaluate(dsNode, filterNodes, null, locale);
    }

    public BIDataSetImpl evaluate(IASTNode dsNode, List<IASTNode> filterNodes, List<String> calcFds, Locale locale) throws SyntaxException {
        BIDataSetImpl dataset = (BIDataSetImpl)dsNode.evaluate((IContext)this.tfc);
        try {
            if (calcFds != null) {
                dataset = this.doCalcDsField(dataset, calcFds);
            } else {
                dataset.compute(null);
            }
        }
        catch (BIDataSetException e) {
            throw new SyntaxException((Throwable)e);
        }
        List<FilterItem> globalTimeFilters = this.getGlobalTimeFilters(dsNode, dataset);
        String currTimekey = null;
        if (dsNode instanceof DSNode) {
            DSNode n = (DSNode)dsNode;
            currTimekey = this.tfc.getCurrTimekey(n.getDSName());
        }
        if (currTimekey == null) {
            currTimekey = this.getTimekeyFromFilterItems(globalTimeFilters, dataset, locale);
        }
        if (filterNodes != null && filterNodes.size() > 0) {
            List<IASTNode> filters = this.transformFilters(filterNodes, dataset, currTimekey);
            List<IASTNode> dsCxtFilters = this.convertToDSFormulaContextNode(dsNode, filters);
            DSFormulaContext dsCxt = new DSFormulaContext(dataset);
            dsCxt.setLanguage(locale.toLanguageTag());
            if (currTimekey != null) {
                int systimekeyIdx = dataset.getMetadata().indexOf("SYS_TIMEKEY");
                dsCxt.setCurrentValue(systimekeyIdx, currTimekey);
            }
            try {
                dataset = (BIDataSetImpl)dataset.doFilter(dsCxtFilters, dsCxt);
            }
            catch (BIDataSetException e) {
                throw new SyntaxException("\u6267\u884c\u6570\u636e\u96c6\u8fc7\u6ee4\u51fa\u9519\uff0c" + e.getMessage(), (Throwable)e);
            }
        }
        if (dsNode instanceof DSNode) {
            DSNode node = (DSNode)dsNode;
            List<FilterItem> formulaFilter = this.analysisFilter(node.getDSModel(), filterNodes);
            if (this.isContainTimeOffset(filterNodes) || this.isTimekeyHasRestrict(filterNodes)) {
                formulaFilter.removeAll(globalTimeFilters);
            }
            try {
                if (formulaFilter.size() > 0) {
                    dataset = (BIDataSetImpl)dataset.filter(formulaFilter);
                }
            }
            catch (BIDataSetException e) {
                throw new SyntaxException("\u6267\u884c\u6570\u636e\u96c6\u8fc7\u6ee4\u51fa\u9519\uff0c" + e.getMessage(), (Throwable)e);
            }
        }
        return dataset;
    }

    public Object evaluate(IASTNode node) throws SyntaxException {
        this.setFormulaContextForConstNode(node);
        return node.evaluate((IContext)this.tfc);
    }

    public List<IASTNode> convertToDSFormulaContextNode(IASTNode dsNode, List<IASTNode> nodes) throws SyntaxException {
        String dsName = null;
        if (dsNode instanceof DSNode) {
            dsName = ((DSNode)dsNode).getDSName();
        }
        ArrayList<IASTNode> dsCxtNodes = new ArrayList<IASTNode>();
        for (IASTNode node : nodes) {
            IASTNode adjustNode = this.adjust(dsName, node);
            this.setFormulaContextForConstNode(adjustNode);
            dsCxtNodes.add(adjustNode);
        }
        return dsCxtNodes;
    }

    private void setFormulaContextForConstNode(IASTNode node) {
        for (IASTNode child : node) {
            if (child instanceof TRestrictNode) {
                List<IASTNode> rsts = ((TRestrictNode)child).getRestricts();
                for (IASTNode rst : rsts) {
                    this.setFormulaContextForConstNode(rst);
                }
            }
            if (!(child instanceof ConstNode)) continue;
            ((ConstNode)child).setTextFormulaContext(this.tfc);
        }
    }

    private BIDataSetImpl doCalcDsField(BIDataSetImpl dataset, List<String> calcFds) throws BIDataSetException {
        ArrayList<Integer> needCalcFds = new ArrayList<Integer>();
        HashMap<String, String> calcFieldMap = new HashMap<String, String>();
        for (String expr : calcFds) {
            int colIdx = dataset.getMetadata().indexOf(expr);
            if (colIdx == -1) {
                calcFieldMap.put(expr, expr);
                continue;
            }
            needCalcFds.add(colIdx);
        }
        dataset.compute(needCalcFds);
        if (calcFieldMap.size() > 0) {
            dataset = (BIDataSetImpl)dataset.addCalcFields(calcFieldMap);
        }
        return dataset;
    }

    public BIDataSetImpl doAggregate(BIDataSet dataset, Set<String> reservedFieldNames) throws BIDataSetException {
        List columns = dataset.getMetadata().getColumns();
        boolean reservedTimekey = false;
        if (reservedFieldNames.contains("SYS_TIMEKEY")) {
            reservedTimekey = true;
        }
        ArrayList<String> dimList = new ArrayList<String>();
        HashSet<String> showKeyset = new HashSet<String>();
        int dsKeySize = 0;
        for (int i = 0; i < columns.size(); ++i) {
            BIDataSetFieldInfo info = (BIDataSetFieldInfo)((Column)columns.get(i)).getInfo();
            String name = info.getName().toUpperCase();
            FieldType ftype = info.getFieldType();
            if (ftype == null || !ftype.isDimField()) continue;
            boolean isKey = name.equals(info.getKeyField().toUpperCase());
            if (isKey) {
                ++dsKeySize;
            }
            if (reservedFieldNames.contains(name)) {
                dimList.add(name);
                showKeyset.add(info.getKeyField());
                continue;
            }
            if (!ftype.isTimeDimField() || !reservedTimekey) continue;
            dimList.add(name);
            showKeyset.add(info.getKeyField());
        }
        if (dsKeySize > showKeyset.size()) {
            dataset = dataset.aggregate(dimList);
        }
        columns = dataset.getMetadata().getColumns();
        ArrayList<Integer> reservedCalcFieldIdxes = new ArrayList<Integer>();
        for (int i = 0; i < columns.size(); ++i) {
            BIDataSetFieldInfo info = (BIDataSetFieldInfo)((Column)columns.get(i)).getInfo();
            String name = info.getName().toUpperCase();
            if (!reservedFieldNames.contains(name) || !info.isCalcField() || info.getCalcMode() != CalcMode.AGGR_THEN_CALC) continue;
            reservedCalcFieldIdxes.add(((Column)columns.get(i)).getIndex());
        }
        if (reservedCalcFieldIdxes.size() > 0) {
            dataset.compute(reservedCalcFieldIdxes);
        }
        return (BIDataSetImpl)dataset;
    }

    public BIDataSet openDataset(DSModel dsModel) throws BIDataSetException {
        DSContext dsCxt = this.createDSContext(dsModel);
        if (this.tfc == null || this.tfc.getDsProvider() == null) {
            throw new BIDataSetException("\u672a\u8bbe\u7f6e\u53d6\u6570\u63d0\u4f9b\u5668\uff0c\u65e0\u6cd5\u6253\u5f00\u6570\u636e\u96c6\u8282\u70b9");
        }
        BIDataSet dataset = this.tfc.getDsProvider().open(dsCxt, dsModel);
        return dataset;
    }

    public List<FilterItem> analysisFilter(DSModel dsModel, List<IASTNode> restricts) {
        List<FilterItem> formulaFilters = this.tfc.getFormulaFilter(dsModel.getName());
        if (formulaFilters.size() == 0) {
            return formulaFilters;
        }
        HashMap<String, FilterItem> map = new HashMap<String, FilterItem>();
        for (FilterItem fi : formulaFilters) {
            if (!StringUtils.isNotEmpty((String)fi.getFieldName())) continue;
            map.put(fi.getFieldName().toUpperCase(), fi);
        }
        if (restricts != null) {
            for (IASTNode root : restricts) {
                this.removeDuplicateFromMap(dsModel, map, root.iterator());
                if (map.size() != 0) continue;
                break;
            }
        }
        return new ArrayList<FilterItem>(map.values());
    }

    public List<IASTNode> convertFilterItemToFilterNode(DSModel dsModel, List<FilterItem> filterItems) throws SyntaxException {
        ArrayList<IASTNode> nodes = new ArrayList<IASTNode>();
        DatasetFormulaParser parser = DSFormularManager.getInstance().createParser(dsModel);
        for (FilterItem fi : filterItems) {
            BIDataSetFieldInfo fieldInfo = null;
            if (fi.getFieldName().equals("SYS_TIMEKEY")) {
                fieldInfo = new BIDataSetFieldInfo("SYS_TIMEKEY", DataType.STRING.value(), null);
                fieldInfo.setKeyField("SYS_TIMEKEY");
            } else {
                fieldInfo = this.findInfo(dsModel, fi.getFieldName());
            }
            DSFieldNode fieldNode = new DSFieldNode(null, fieldInfo);
            DataType type = DataType.valueOf(fieldInfo.getValType());
            int baseType = type == DataType.UNKNOWN ? 0 : DataType.translateToSyntaxType(type);
            List<Object> keyList = fi.getKeyList();
            if (keyList != null && keyList.size() > 0) {
                ArrayList<Equal> equals = new ArrayList<Equal>();
                for (Object key : keyList) {
                    Equal equal = new Equal(null, (IASTNode)fieldNode, (IASTNode)new DataNode(null, baseType, key));
                    equals.add(equal);
                }
                if (equals.size() == 1) {
                    nodes.add((IASTNode)equals.get(0));
                } else {
                    Or or = new Or(null, (IASTNode)equals.get(0), (IASTNode)equals.get(1));
                    for (int i = 2; i < equals.size(); ++i) {
                        or = new Or(null, (IASTNode)or, (IASTNode)equals.get(i));
                    }
                    nodes.add((IASTNode)or);
                }
            }
            if (!StringUtils.isNotEmpty((String)fi.getExpr())) continue;
            DSExpression expr = parser.parseEval(fi.getExpr(), new DSFormulaContext(dsModel));
            nodes.add(expr.getChild(0));
        }
        return nodes;
    }

    public IASTNode adjust(String dsName, IASTNode node) throws SyntaxException {
        DSModel dsModel;
        String childDsName = dsName;
        if (node instanceof IDSNodeDescriptor && (dsModel = ((IDSNodeDescriptor)node).getDSModel()) != null) {
            childDsName = dsModel.getName();
        }
        for (int i = 0; i < node.childrenSize(); ++i) {
            IASTNode child = node.getChild(i);
            node.setChild(i, this.adjust(childDsName, child));
        }
        if (node instanceof IAdjustable) {
            IAdjustable adj = (IAdjustable)node;
            if (adj.isAdjustable(dsName)) {
                return adj.adjust();
            }
            return new ConstNode(node.getToken(), node);
        }
        return node;
    }

    private IASTNode transformTimekeyOffset(BIDataSetImpl dataset, List<IASTNode> timefilters, String currTimekey) throws SyntaxException {
        Column sys_timekeyCol = dataset.getMetadata().find("SYS_TIMEKEY");
        if (sys_timekeyCol == null) {
            throw new SyntaxException("\u65e0\u6cd5\u4ece\u6570\u636e\u96c6\u4e2d\u83b7\u53d6\u65f6\u671f\u5b57\u6bb5\u4fe1\u606f\uff0c\u4e0d\u80fd\u8fdb\u884c\u65f6\u671f\u504f\u79fb\u8fc7\u6ee4");
        }
        if (currTimekey == null) {
            throw new SyntaxException("\u4e3a\u8bbe\u7f6e\u5f53\u524d\u671f\u6570\u636e\uff0c\u65e0\u6cd5\u8fdb\u884c\u65f6\u671f\u8fc7\u6ee4");
        }
        int offsetDays = 0;
        TimeGranularity granularity = ((BIDataSetFieldInfo)sys_timekeyCol.getInfo()).getTimegranularity();
        for (IASTNode filter : timefilters) {
            if (filter instanceof Equal) {
                IASTNode left = filter.getChild(0);
                IASTNode right = filter.getChild(1);
                if (left instanceof DSFieldNode) {
                    DSFieldNode fieldNode = (DSFieldNode)left;
                    BIDataSetFieldInfo info = fieldNode.getFieldInfo();
                    if (right instanceof RestrictTagNode || right instanceof TRestrictTagNode) {
                        String tag;
                        ASTNode tagNode;
                        if (right instanceof RestrictTagNode) {
                            tagNode = (RestrictTagNode)right;
                            tag = tagNode.getTag();
                        } else {
                            tagNode = (TRestrictTagNode)right;
                            tag = tagNode.getTag();
                        }
                        if (info.getFieldType() == FieldType.TIME_DIM) {
                            int val = info.getTimegranularity().days() / granularity.days();
                            if (RestrictionTag.isPREV(tag)) {
                                offsetDays -= val;
                                continue;
                            }
                            if (RestrictionTag.isNEXT(tag)) {
                                offsetDays += val;
                                continue;
                            }
                            throw new SyntaxException("\u975e\u6cd5\u7684\u516c\u5f0f\u8868\u8fbe\u5f0f\uff0c\u76ee\u524d\u53ea\u652f\u6301NEXT\u548cPREV\u4e24\u79cd\u504f\u79fb\u65b9\u5f0f");
                        }
                        throw new SyntaxException("\u975e\u6cd5\u7684\u516c\u5f0f\u8868\u8fbe\u5f0f\uff0c\u4e0d\u5141\u8bb8\u5bf9\u975e\u65f6\u671f\u5b57\u6bb5\u8fdb\u884c\u504f\u79fb");
                    }
                    int curVal = TimeKeyBuilder.parse(currTimekey, info.getTimegranularity());
                    Object restrictVal = right.evaluate((IContext)this.tfc);
                    if (restrictVal instanceof Number) {
                        int rstV = ((Number)restrictVal).intValue();
                        offsetDays += rstV - curVal;
                        continue;
                    }
                    throw new SyntaxException("\u975e\u6cd5\u7684\u516c\u5f0f\u8868\u8fbe\u5f0f\uff0c\u5bf9\u65f6\u671f\u7684\u9650\u5b9a\uff0c\u65f6\u671f\u7ef4\u5ea6\u5fc5\u987b\u662f\u6570\u503c\u7c7b\u578b");
                }
                throw new SyntaxException("\u975e\u6cd5\u7684\u516c\u5f0f\u8868\u8fbe\u5f0f\uff0c\u53ea\u80fd\u5bf9\u65f6\u671f\u5b57\u6bb5\u8fdb\u884c\u8bbe\u7f6e");
            }
            if (filter instanceof FunctionNode) {
                FunctionNode funcNode = (FunctionNode)filter;
                IFunction define = funcNode.getDefine();
                if (define instanceof Lag) {
                    DSFieldNode dsFieldNode = (DSFieldNode)funcNode.getChild(0);
                    BIDataSetFieldInfo fieldInfo = dsFieldNode.getFieldInfo();
                    Number len = (Number)funcNode.getChild(1).evaluate((IContext)this.tfc);
                    int val = fieldInfo.getTimegranularity().days() / granularity.days();
                    offsetDays -= val * Math.abs(len.intValue());
                    continue;
                }
                throw new SyntaxException("\u975e\u6cd5\u7684\u516c\u5f0f\u8868\u8fbe\u5f0f\uff0c\u65f6\u671f\u504f\u79fb\u51fd\u6570\u4e2d\uff0c\u76ee\u524d\u53ea\u652f\u6301LAG\u51fd\u6570");
            }
            throw new SyntaxException("\u975e\u6cd5\u7684\u516c\u5f0f\u8868\u8fbe\u5f0f\uff0c\u76ee\u524d\u5bf9\u65f6\u671f\u9650\u5b9a\u53ea\u80fd\u662f\u4f7f\u7528\u504f\u79fb\u51fd\u6570\u3001NEXT\u3001PREV");
        }
        int minMonth = -1;
        int maxMonth = -1;
        String fmstr = dataset.getMetadata().getProperties().getOrDefault("FiscalMonth", null);
        if (fmstr != null) {
            JSONObject json = new JSONObject(fmstr);
            minMonth = Integer.parseInt(json.optString("min", "-1"));
            maxMonth = Integer.parseInt(json.optString("max", "-1"));
        }
        String prvTimekey = TimeKeyBuilder.prev(currTimekey, granularity, -offsetDays, minMonth, maxMonth);
        Equal equal = new Equal(null);
        equal.setChild(0, (IASTNode)new DSFieldNode(null, (BIDataSetFieldInfo)sys_timekeyCol.getInfo()));
        equal.setChild(1, (IASTNode)new DataNode(null, prvTimekey));
        return equal;
    }

    private List<FilterItem> getGlobalTimeFilters(IASTNode dsNode, BIDataSetImpl dataset) throws SyntaxException {
        ArrayList<FilterItem> globalTimeFilters = new ArrayList<FilterItem>();
        if (dsNode instanceof IDSNodeDescriptor) {
            IDSNodeDescriptor node = (IDSNodeDescriptor)dsNode;
            List<FilterItem> globalFilters = this.tfc.getFormulaFilter(node.getDSModel() != null ? node.getDSModel().getName() : null);
            for (FilterItem fi : globalFilters) {
                FieldType fType;
                Column column;
                if (fi.getFieldName() == null || (column = dataset.getMetadata().find(fi.getFieldName())) == null || (fType = ((BIDataSetFieldInfo)column.getInfo()).getFieldType()) != null && !fType.isTimeDimField()) continue;
                globalTimeFilters.add(fi);
            }
        }
        return globalTimeFilters;
    }

    private String getTimekeyFromFilterItems(List<FilterItem> filters, BIDataSetImpl dataset, Locale locale) throws SyntaxException {
        if (filters.size() == 0) {
            return null;
        }
        Column sys_timekeyCol = dataset.getMetadata().find("SYS_TIMEKEY");
        if (sys_timekeyCol == null) {
            return null;
        }
        int year = -1;
        int month = -1;
        int day = -1;
        for (FilterItem fi : filters) {
            if (fi.getKeyList() == null || fi.getKeyList().size() != 1) {
                return null;
            }
            Object value = fi.getKeyList().get(0);
            Column column = dataset.getMetadata().find(fi.getFieldName());
            BIDataSetFieldInfo info = (BIDataSetFieldInfo)column.getInfo();
            if (info.getFieldType() != null && !info.getFieldType().isTimeDimField()) continue;
            if (info.isTimekey() || info.getName().equals("SYS_TIMEKEY")) {
                return (String)value;
            }
            int dataType = info.getValType();
            int v = -1;
            if (dataType == DataType.INTEGER.value()) {
                v = ((Number)value).intValue();
            } else if (dataType == DataType.DATETIME.value()) {
                Calendar c = (Calendar)value;
                v = this.get(c, info.getTimegranularity());
            } else if (dataType == DataType.STRING.value()) {
                String str = (String)value;
                if (info.getDataPattern() == null) {
                    try {
                        v = Integer.parseInt(str);
                    }
                    catch (Exception e) {
                        v = -1;
                    }
                } else {
                    DateFormatEx sdf = new DateFormatEx(info.getDataPattern(), locale);
                    Calendar c = Calendar.getInstance();
                    try {
                        Date date = sdf.parse(str);
                        c.setTime(date);
                        v = this.get(c, info.getTimegranularity());
                    }
                    catch (ParseException e) {
                        v = -1;
                    }
                }
            }
            switch (info.getTimegranularity()) {
                case YEAR: {
                    year = v;
                    break;
                }
                case MONTH: {
                    month = v;
                    break;
                }
                case DAY: {
                    day = v;
                    break;
                }
            }
        }
        TimeGranularity sys_tg = ((BIDataSetFieldInfo)sys_timekeyCol.getInfo()).getTimegranularity();
        switch (sys_tg) {
            case YEAR: {
                month = month == -1 ? 1 : month;
                day = day == -1 ? 1 : day;
                break;
            }
            case MONTH: 
            case HALFYEAR: 
            case QUARTER: {
                day = day == -1 ? 1 : day;
                break;
            }
        }
        if (year != -1 && month != -1 && day != -1) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month - 1, day);
            DateFormatEx format = new DateFormatEx("yyyyMMdd");
            return format.format(calendar.getTime());
        }
        return null;
    }

    private int get(Calendar cal, TimeGranularity granularity) {
        if (granularity == TimeGranularity.YEAR) {
            return cal.get(1);
        }
        if (granularity == TimeGranularity.QUARTER) {
            int month = cal.get(2);
            return month / 3 + 1;
        }
        if (granularity == TimeGranularity.MONTH) {
            return cal.get(2) + 1;
        }
        if (granularity == TimeGranularity.DAY) {
            return cal.get(5);
        }
        if (granularity == TimeGranularity.WEEK) {
            try {
                return TimeHelper.getTimeValue((Calendar)cal, (int)granularity.value());
            }
            catch (TimeCalcException e) {
                throw new ParseCancellationException(e.getMessage(), (Throwable)e);
            }
        }
        return -1;
    }

    private List<IASTNode> transformFilters(List<IASTNode> allFilters, BIDataSetImpl dataset, String currTimekey) throws SyntaxException {
        ArrayList<IASTNode> commonfilters = new ArrayList<IASTNode>();
        ArrayList<IASTNode> timefilters = new ArrayList<IASTNode>();
        for (IASTNode node : allFilters) {
            if (node instanceof FunctionNode) {
                FunctionNode tfn = (FunctionNode)node;
                if (tfn.getDefine() instanceof Lag) {
                    timefilters.add(node);
                    continue;
                }
                commonfilters.add(node);
                continue;
            }
            if (node instanceof Equal) {
                IASTNode child = node.getChild(1);
                if (child instanceof TRestrictTagNode) {
                    TRestrictTagNode tagNode = (TRestrictTagNode)child;
                    String tag = tagNode.getTag();
                    if (RestrictionTag.isMB(tag) || RestrictionTag.isALL(tag) || RestrictionTag.isCURRENT(tag)) continue;
                    timefilters.add(node);
                    continue;
                }
                child = node.getChild(0);
                if (child instanceof DSFieldNode) {
                    BIDataSetFieldInfo info = ((DSFieldNode)child).getFieldInfo();
                    if (info.getFieldType() == FieldType.TIME_DIM) {
                        timefilters.add(node);
                        continue;
                    }
                    commonfilters.add(node);
                    continue;
                }
                commonfilters.add(node);
                continue;
            }
            commonfilters.add(node);
        }
        ArrayList<IASTNode> filters = new ArrayList<IASTNode>();
        filters.addAll(commonfilters);
        if (timefilters.size() > 0) {
            if (this.isContainTimeOffset(allFilters)) {
                if (currTimekey == null) {
                    throw new SyntaxException("\u6570\u636e\u96c6\u4e2d\u672a\u80fd\u83b7\u53d6\u5230\u5f53\u524d\u671f\uff0c\u65e0\u6cd5\u8fdb\u884c\u65f6\u671f\u504f\u79fb\u9650\u5b9a");
                }
                IASTNode filter = this.transformTimekeyOffset(dataset, timefilters, currTimekey);
                filters.add(filter);
            } else {
                filters.addAll(timefilters);
            }
        }
        return filters;
    }

    private boolean isContainTimeOffset(List<IASTNode> allFilters) {
        if (allFilters == null) {
            return false;
        }
        for (IASTNode node : allFilters) {
            ASTNode tagNode;
            if (node instanceof FunctionNode) {
                FunctionNode tfn = (FunctionNode)node;
                if (!(tfn.getDefine() instanceof Lag)) continue;
                return true;
            }
            if (!(node instanceof Equal)) continue;
            IASTNode child = node.getChild(1);
            String tag = null;
            if (child instanceof RestrictTagNode) {
                tagNode = (RestrictTagNode)child;
                tag = tagNode.getTag();
            } else if (child instanceof TRestrictTagNode) {
                tagNode = (TRestrictTagNode)child;
                tag = tagNode.getTag();
            }
            if (tag == null || RestrictionTag.isMB(tag) || RestrictionTag.isALL(tag) || RestrictionTag.isCURRENT(tag)) continue;
            return true;
        }
        return false;
    }

    private boolean isTimekeyHasRestrict(List<IASTNode> allFilters) {
        if (allFilters == null) {
            return false;
        }
        for (IASTNode node : allFilters) {
            for (IASTNode nd : node) {
                DSFieldNode fieldNode;
                BIDataSetFieldInfo info;
                if (!(nd instanceof DSFieldNode) || !(info = (fieldNode = (DSFieldNode)nd).getFieldInfo()).isTimekey()) continue;
                return true;
            }
        }
        return false;
    }

    private void removeDuplicateFromMap(DSModel dsModel, Map<String, FilterItem> map, Iterator<IASTNode> itor) {
        TimeGranularity minTg = this.getMinTimeGranularity(dsModel);
        while (itor.hasNext()) {
            TFieldNode tfdNode;
            IASTNode node = itor.next();
            if (node instanceof TFieldNode && (tfdNode = (TFieldNode)node).isAdjustable(dsModel.getName())) {
                node = tfdNode.adjust();
            }
            if (!(node instanceof DSFieldNode)) continue;
            DSFieldNode fieldNode = (DSFieldNode)node;
            BIDataSetFieldInfo info = fieldNode.getFieldInfo();
            if (info.isDimention()) {
                String key = info.getKeyField().toUpperCase();
                String name = info.getNameField().toUpperCase();
                map.remove(key);
                map.remove(name);
                if (info.getFieldType() == FieldType.TIME_DIM && info.getTimegranularity() == minTg) {
                    map.remove("SYS_TIMEKEY");
                }
            } else {
                String name = info.getName().toUpperCase();
                map.remove(name);
            }
            if (map.size() != 0) continue;
            break;
        }
    }

    private TimeGranularity getMinTimeGranularity(DSModel dsModel) {
        TimeGranularity minTg = null;
        List<DSField> commFields = dsModel.getCommonFields();
        for (DSField field : commFields) {
            TimeGranularity tg = field.getTimegranularity();
            if (tg == null || minTg != null && minTg.compareTo(tg) <= 0) continue;
            minTg = tg;
        }
        return minTg;
    }

    private DSContext createDSContext(DSModel dsModel) {
        IParameterEnv paramEnv = this.tfc.getEnhancedParameterEnv(dsModel.getName());
        DSContext dsCxt = new DSContext(dsModel, this.tfc.getUserGuid(), paramEnv);
        if (this.tfc.isFilterOnDsOpen()) {
            RangeValues timekeyRange;
            List<FilterItem> filters = this.tfc.getFormulaFilter(dsModel.getName());
            if (filters != null) {
                for (FilterItem fi : filters) {
                    dsCxt.addFilterItem(fi);
                }
            }
            if ((timekeyRange = this.tfc.getTimekeyRange(dsModel.getName())) != null) {
                dsCxt.setTimekeyRange((String)timekeyRange.min, (String)timekeyRange.max);
            }
        }
        return dsCxt;
    }

    private BIDataSetFieldInfo findInfo(DSModel dsModel, String fieldName) {
        List<DSField> fields = dsModel.getFields();
        for (DSField field : fields) {
            if (!field.getName().equalsIgnoreCase(fieldName)) continue;
            BIDataSetFieldInfo info = new BIDataSetFieldInfo();
            info.loadFromDSField(field);
            return info;
        }
        return null;
    }

    private class ConstNode
    extends ASTNode {
        private static final long serialVersionUID = 1L;
        private IASTNode node;
        private TextFormulaContext tfc;

        public ConstNode(Token token, IASTNode node) {
            super(token);
            this.node = node;
        }

        public void setTextFormulaContext(TextFormulaContext tfc) {
            this.tfc = tfc;
        }

        public ASTNodeType getNodeType() {
            return ASTNodeType.DYNAMICDATA;
        }

        public int getType(IContext context) throws SyntaxException {
            return this.node.getType((IContext)this.tfc);
        }

        public Object evaluate(IContext context) throws SyntaxException {
            return this.node.evaluate((IContext)this.tfc);
        }

        public boolean isStatic(IContext context) {
            return false;
        }

        public void toString(StringBuilder buffer) {
            this.node.toString(buffer);
        }

        protected void toFormula(IContext context, StringBuilder buffer, Object info) throws InterpretException {
            IASTNode nd = null;
            if (this.node instanceof TFunctionNode) {
                TFunctionNode tfn = (TFunctionNode)this.node;
                nd = tfn.transformToDsNode();
            }
            if (nd == null) {
                nd = this.node;
            }
            nd.interpret(context, buffer, Language.FORMULA, info);
        }

        public IDataFormator getDataFormator(IContext context) throws SyntaxException {
            return this.node.getDataFormator((IContext)this.tfc);
        }
    }
}

