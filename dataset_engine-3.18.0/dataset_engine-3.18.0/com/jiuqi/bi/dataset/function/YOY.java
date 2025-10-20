/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.Metadata
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.parser.IContext
 *  org.json.JSONObject
 */
package com.jiuqi.bi.dataset.function;

import com.jiuqi.bi.dataset.BIDataRow;
import com.jiuqi.bi.dataset.BIDataSet;
import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.BIDataSetImpl;
import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.FilterItem;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.dataset.expression.DSFieldNode;
import com.jiuqi.bi.dataset.expression.DSFormulaContext;
import com.jiuqi.bi.dataset.function.DSFunction;
import com.jiuqi.bi.dataset.manager.TimeKeyBuilder;
import com.jiuqi.bi.dataset.model.field.FieldType;
import com.jiuqi.bi.dataset.model.field.TimeGranularity;
import com.jiuqi.bi.dataset.model.hierarchy.DSHierarchy;
import com.jiuqi.bi.dataset.model.hierarchy.DSHierarchyType;
import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.parser.IContext;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.json.JSONObject;

public class YOY
extends DSFunction {
    private static final long serialVersionUID = 5884910166692699684L;

    public YOY() {
        this.parameters().add(new Parameter("field", 0, "\u5ea6\u91cf\u5b57\u6bb5"));
        this.parameters().add(new Parameter("restrict", 1, "\u9650\u5b9a\u8868\u8fbe\u5f0f", true));
    }

    @Override
    public Object evalute(IContext context, List<IASTNode> paramNodes, BIDataSet filterDs) throws SyntaxException {
        Column sys_timekey;
        DSFormulaContext dsCxt = (DSFormulaContext)context;
        BIDataSetImpl dataset = dsCxt.getDataSet();
        BIDataRow curRow = dsCxt.getCurRow();
        if (curRow == null) {
            curRow = this.getCurrRowFromDS(filterDs);
        }
        if ((sys_timekey = dataset.getMetadata().find("SYS_TIMEKEY")) == null) {
            throw new SyntaxException("\u5f53\u524d\u6570\u636e\u96c6\u6ca1\u6709\u8bbe\u7f6e\u65f6\u95f4\u7ef4\u5ea6\u6216\u8005\u65f6\u95f4\u7ef4\u5ea6\u5df2\u7ecf\u88ab\u6d88\u7ef4\u5904\u7406\u6389\uff0c\u65e0\u6cd5\u8fdb\u884c\u540c\u6bd4\u8ba1\u7b97\u3002");
        }
        BIDataRow lastRow = this.getLastTimeDataRow(dataset, curRow, (BIDataSetFieldInfo)sys_timekey.getInfo());
        if (lastRow == null) {
            return null;
        }
        IASTNode msNode = paramNodes.get(0);
        DSFieldNode fieldNode = (DSFieldNode)msNode;
        BIDataSetFieldInfo msField = fieldNode.getFieldInfo();
        int msFdIdx = dataset.getMetadata().indexOf(msField.getName());
        if (curRow.wasNull(msFdIdx)) {
            return null;
        }
        double curValue = curRow.getDouble(msFdIdx);
        double lastValue = lastRow.getDouble(msFdIdx);
        if (lastRow.wasNull(msFdIdx) || DataType.compare((double)lastValue, (double)0.0) == 0) {
            return null;
        }
        return (curValue - lastValue) / Math.abs(lastValue);
    }

    public int validate(IContext context, List<IASTNode> parameters) throws SyntaxException {
        IASTNode node = parameters.get(0);
        if (!(node instanceof DSFieldNode)) {
            throw new SyntaxException("\u51fd\u6570\u53c2\u6570\u9519\u8bef\uff0c\u8282\u70b9\u3010" + node.interpret(context, Language.FORMULA, null) + "\u3011\u4e0d\u662f\u4e00\u4e2a\u6570\u636e\u96c6\u5b57\u6bb5\u8282\u70b9");
        }
        DSFieldNode dsNode = (DSFieldNode)node;
        BIDataSetFieldInfo info = dsNode.getFieldInfo();
        if (info.getFieldType() != FieldType.MEASURE) {
            throw new SyntaxException("\u5b57\u6bb5" + info.getName() + "\u4e0d\u662f\u4e00\u4e2a\u5ea6\u91cf\u5b57\u6bb5");
        }
        return super.validate(context, parameters);
    }

    private BIDataRow getCurrRowFromDS(BIDataSet dataset) throws SyntaxException {
        int rowCount = dataset.getRecordCount();
        if (rowCount == 1) {
            return dataset.get(0);
        }
        if (rowCount < 1) {
            throw new SyntaxException("\u9650\u5b9a\u8868\u8fbe\u5f0f\u9650\u5b9a\u7684\u7ed3\u679c\u6ca1\u6709\u8bb0\u5f55\uff0c\u65e0\u6cd5\u8fdb\u884c\u540c\u6bd4\u8ba1\u7b97");
        }
        throw new SyntaxException("\u9650\u5b9a\u6761\u4ef6\u672a\u80fd\u786e\u5b9a\u5f53\u524d\u884c\uff0c\u65e0\u6cd5\u8fdb\u884c\u540c\u6bd4\u8ba1\u7b97");
    }

    private FilterItem getLastTimeFilterItem(BIDataSetFieldInfo sys_timekeyFieldInfo, BIDataSet dataset, BIDataRow curRow) throws SyntaxException {
        String newDateStr;
        int timeFieldColIndex = dataset.getMetadata().indexOf(sys_timekeyFieldInfo.getName());
        FilterItem timeFilter = new FilterItem();
        timeFilter.setFieldName(sys_timekeyFieldInfo.getName());
        String dateStr = curRow.getString(timeFieldColIndex);
        if (dateStr == null) {
            throw new SyntaxException("\u65e5\u671f\u952e\u5217\u4e3a\u7a7a\uff0c\u65e0\u6cd5\u8fdb\u884c\u504f\u79fb\u8ba1\u7b97\u3002\u53ef\u80fd\u662f\u6570\u636e\u96c6\u4e2d\u4e0d\u5305\u542b\u65e5\u671f\u5b57\u6bb5\uff0c\u6216\u8005\u6570\u636e\u96c6\u7684\u65e5\u671f\u683c\u5f0f\u8bbe\u7f6e\u4e0d\u51c6\u786e");
        }
        if (TimeGranularity.WEEK == sys_timekeyFieldInfo.getTimegranularity()) {
            newDateStr = TimeKeyBuilder.prevWeekByYear(dateStr, 1);
        } else {
            int minMonth = -1;
            int maxMonth = -1;
            String fmstr = dataset.getMetadata().getProperties().getOrDefault("FiscalMonth", null);
            if (fmstr != null) {
                JSONObject json = new JSONObject(fmstr);
                minMonth = Integer.parseInt(json.optString("min", "-1"));
                maxMonth = Integer.parseInt(json.optString("max", "-1"));
            }
            newDateStr = TimeKeyBuilder.prev(dateStr, TimeGranularity.YEAR, 1, minMonth, maxMonth);
        }
        ArrayList<Object> keyList = new ArrayList<Object>();
        keyList.add(newDateStr);
        timeFilter.setKeyList(keyList);
        return timeFilter;
    }

    private BIDataRow getLastTimeDataRow(BIDataSet dataset, BIDataRow curRow, BIDataSetFieldInfo timeFieldInfo) throws SyntaxException {
        FilterItem timeFilter = this.getLastTimeFilterItem(timeFieldInfo, dataset, curRow);
        ArrayList<FilterItem> filterItems = new ArrayList<FilterItem>();
        List columns = dataset.getMetadata().getColumns();
        Set<Integer> parentColset = this.getParentColSet(dataset);
        for (int i = 0; i < columns.size(); ++i) {
            BIDataSetFieldInfo info = (BIDataSetFieldInfo)((Column)columns.get(i)).getInfo();
            FieldType ftype = info.getFieldType();
            if (ftype != FieldType.GENERAL_DIM || !info.getName().equalsIgnoreCase(info.getKeyField()) || parentColset.contains(((Column)columns.get(i)).getIndex())) continue;
            ArrayList<Object> keyList = new ArrayList<Object>();
            keyList.add(curRow.getValue(i));
            FilterItem fi = new FilterItem(((Column)columns.get(i)).getName(), keyList);
            filterItems.add(fi);
        }
        filterItems.add(timeFilter);
        BIDataSetImpl filteredDataSet = null;
        try {
            filteredDataSet = (BIDataSetImpl)dataset.filter(filterItems);
        }
        catch (BIDataSetException e) {
            throw new SyntaxException("YOY\u51fd\u6570\u4e2d\u6267\u884c\u8fc7\u6ee4\u6570\u636e\u96c6\u51fa\u9519", (Throwable)e);
        }
        int rowCount = filteredDataSet.getRecordCount();
        if (rowCount == 1) {
            return filteredDataSet.get(0);
        }
        if (rowCount < 1) {
            return null;
        }
        throw new SyntaxException("\u9650\u5b9a\u4fe1\u606f\u4e0d\u5b8c\u6574\uff0c\u4e0a\u671f\u6570\u51fa\u73b0\u591a\u4e2a\u8bb0\u5f55");
    }

    private Set<Integer> getParentColSet(BIDataSet dataset) {
        List<DSHierarchy> hiers = this.getHierarchy(dataset);
        HashSet<Integer> set = new HashSet<Integer>();
        Metadata<BIDataSetFieldInfo> metadata = dataset.getMetadata();
        if (hiers != null) {
            for (DSHierarchy hier : hiers) {
                if (hier.getType() != DSHierarchyType.PARENT_HIERARCHY) continue;
                set.add(metadata.indexOf(hier.getParentFieldName()));
            }
        }
        return set;
    }

    private List<DSHierarchy> getHierarchy(BIDataSet dataset) {
        Metadata<BIDataSetFieldInfo> metadata = dataset.getMetadata();
        return (List)metadata.getProperties().get("HIERARCHY");
    }

    public int getResultType(IContext context, List<IASTNode> paramNodes) throws SyntaxException {
        return 3;
    }

    public String name() {
        return "DS_YOY";
    }

    public String title() {
        return "\u8ba1\u7b97\u6307\u5b9a\u5ea6\u91cf\u7684\u540c\u6bd4\u589e\u957f\u7387";
    }
}

