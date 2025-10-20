/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.DataSetException
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.dataset.Metadata
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.dataset.executor;

import com.jiuqi.bi.dataset.AggrMeasureItem;
import com.jiuqi.bi.dataset.BIDataSet;
import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.BIDataSetImpl;
import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DataSetException;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.dataset.expression.DSExpression;
import com.jiuqi.bi.dataset.expression.DSFieldNode;
import com.jiuqi.bi.dataset.expression.DSFormulaContext;
import com.jiuqi.bi.dataset.expression.DSFormularManager;
import com.jiuqi.bi.dataset.expression.DatasetFormulaParser;
import com.jiuqi.bi.dataset.model.field.AggregationType;
import com.jiuqi.bi.dataset.model.field.ApplyType;
import com.jiuqi.bi.dataset.model.field.CalcMode;
import com.jiuqi.bi.dataset.model.field.FieldType;
import com.jiuqi.bi.dataset.model.hierarchy.DSHierarchy;
import com.jiuqi.bi.dataset.model.hierarchy.DSHierarchyType;
import com.jiuqi.bi.dataset.stat.StatConfig;
import com.jiuqi.bi.dataset.stat.StatProcessor;
import com.jiuqi.bi.dataset.stat.info.StatInfo;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.util.StringUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class AggrExecutor {
    private BIDataSetImpl dataset;

    public AggrExecutor(BIDataSet dataset) {
        this.dataset = (BIDataSetImpl)dataset;
    }

    public BIDataSetImpl aggregateByItems(List<Integer> dimColList, List<AggrMeasureItem> measureList, boolean isAutoFillParentColumn) throws BIDataSetException {
        StatConfig config = new StatConfig(true, isAutoFillParentColumn);
        StatProcessor processor = new StatProcessor(this.dataset, config);
        measureList = new ArrayList<AggrMeasureItem>(measureList);
        ArrayList<Integer> showDimColList = new ArrayList<Integer>();
        this.preprocess(dimColList, showDimColList, isAutoFillParentColumn);
        if (this.dataset.getRecordCount() >= 1) {
            this.checkField(showDimColList, measureList);
        }
        StatInfo statInfo = processor.createStatInfo(showDimColList, measureList);
        return this._aggregate(statInfo, config);
    }

    public BIDataSetImpl aggregate(List<String> dimList, List<String> measureList, boolean isAutoFillParentColumn) throws BIDataSetException {
        ArrayList<AggrMeasureItem> items = new ArrayList<AggrMeasureItem>();
        for (int i = 0; i < measureList.size(); ++i) {
            Column column = this.dataset.getMetadata().find(measureList.get(i));
            if (column == null) {
                throw new BIDataSetException("\u65e0\u6cd5\u627e\u5230\u5b57\u6bb5[" + measureList.get(i) + "]");
            }
            items.add(new AggrMeasureItem(measureList.get(i), null, column.getTitle(), ((BIDataSetFieldInfo)column.getInfo()).getAggregation()));
        }
        return this.aggregateByItems(this.getColIdxList(dimList), items, isAutoFillParentColumn);
    }

    public BIDataSetImpl aggregate(List<String> dimList, boolean isAutoFillParentColumn) throws BIDataSetException {
        ArrayList<AggrMeasureItem> items = new ArrayList<AggrMeasureItem>();
        List columns = this.dataset.getMetadata().getColumns();
        for (Column column : columns) {
            if (((BIDataSetFieldInfo)column.getInfo()).getFieldType() != FieldType.MEASURE) continue;
            items.add(new AggrMeasureItem(column.getName(), null, column.getTitle(), ((BIDataSetFieldInfo)column.getInfo()).getAggregation()));
        }
        return this.aggregateByItems(this.getColIdxList(dimList), items, isAutoFillParentColumn);
    }

    public BIDataSetImpl _aggregate(StatInfo statInfo, StatConfig statConfig) throws BIDataSetException {
        boolean hasAggTimeDim;
        int count = this.dataset.getRecordCount();
        if (count == 0) {
            return this.createEmptyDataset(statInfo, statConfig);
        }
        if (count == 1) {
            return this._aggregateOnlyOneRowDs(statInfo, statConfig);
        }
        boolean isMsContainsNonPeriod = false;
        for (int col : statInfo.destMsColIdx) {
            Column column = statInfo.metadata.getColumn(col);
            ApplyType applyType = ((BIDataSetFieldInfo)column.getInfo()).getApplyType();
            if (applyType == null || applyType == ApplyType.PERIOD) continue;
            isMsContainsNonPeriod = true;
            break;
        }
        boolean bl = hasAggTimeDim = isMsContainsNonPeriod && statInfo.isTimeWasAggr;
        if (hasAggTimeDim) {
            Object info;
            ArrayList<Integer> srcDimList = new ArrayList<Integer>();
            for (Integer i : statInfo.destDimColIdx) {
                srcDimList.add(statInfo.dest2srcColMap.get(i));
            }
            List orgcolumns = this.dataset.getMetadata().getColumns();
            HashSet<String> timeDimColList = new HashSet<String>();
            for (int i = 0; i < orgcolumns.size(); ++i) {
                info = (BIDataSetFieldInfo)((Column)orgcolumns.get(i)).getInfo();
                if (((BIDataSetFieldInfo)info).getFieldType() != FieldType.TIME_DIM || srcDimList.contains(i)) continue;
                srcDimList.add(i);
                timeDimColList.add(((BIDataSetFieldInfo)info).getName());
            }
            ArrayList<AggrMeasureItem> reserveMsList = new ArrayList<AggrMeasureItem>();
            info = statInfo.destMsColIdx.iterator();
            while (info.hasNext()) {
                int col = (Integer)info.next();
                Column column = statInfo.metadata.getColumn(col);
                AggregationType aggrType = ((BIDataSetFieldInfo)column.getInfo()).getAggregation();
                AggrMeasureItem item = new AggrMeasureItem(column.getName(), null, column.getTitle(), aggrType);
                reserveMsList.add(item);
            }
            StatProcessor statProcessor = new StatProcessor(this.dataset, statConfig);
            BIDataSetImpl ds = statProcessor.stat(srcDimList, reserveMsList);
            srcDimList = new ArrayList();
            List tmpColumns = ds.getMetadata().getColumns();
            for (int i = 0; i < tmpColumns.size(); ++i) {
                BIDataSetFieldInfo info2 = (BIDataSetFieldInfo)((Column)tmpColumns.get(i)).getInfo();
                if (!info2.isDimention() || timeDimColList.contains(info2.getName()) || info2.getName().equals("SYS_TIMEKEY")) continue;
                srcDimList.add(i);
            }
            statProcessor = new StatProcessor(ds, statConfig);
            ds = statProcessor.stat(srcDimList, reserveMsList);
            return ds;
        }
        StatProcessor statProcessor = new StatProcessor(this.dataset, statConfig);
        return statProcessor.stat(statInfo);
    }

    private BIDataSetImpl _aggregateOnlyOneRowDs(StatInfo statInfo, StatConfig statConfig) throws BIDataSetException {
        MemoryDataSet newMemoryDataset = new MemoryDataSet(BIDataSetFieldInfo.class, statInfo.metadata);
        Object[] data = new Object[statInfo.metadata.size()];
        this.fillRowDataForOneRowDs(statInfo.metadata, data);
        try {
            newMemoryDataset.add(data);
        }
        catch (DataSetException e) {
            throw new BIDataSetException(e.getMessage(), e);
        }
        BIDataSetImpl newdataset = new BIDataSetImpl((MemoryDataSet<BIDataSetFieldInfo>)newMemoryDataset);
        newdataset.setParameterEnv(this.dataset.getEnhancedParameterEnv());
        newdataset.setLogger(this.dataset.getLogger());
        return newdataset;
    }

    private BIDataSetImpl createEmptyDataset(StatInfo statInfo, StatConfig statConfig) throws BIDataSetException {
        MemoryDataSet newMemoryDataset = new MemoryDataSet(BIDataSetFieldInfo.class, statInfo.metadata);
        BIDataSetImpl newdataset = new BIDataSetImpl((MemoryDataSet<BIDataSetFieldInfo>)newMemoryDataset);
        newdataset.setParameterEnv(this.dataset.getEnhancedParameterEnv());
        newdataset.setLogger(this.dataset.getLogger());
        return newdataset;
    }

    private void fillRowDataForOneRowDs(Metadata<BIDataSetFieldInfo> metadata, Object[] data) throws BIDataSetException {
        for (int i = 0; i < data.length; ++i) {
            Column column = metadata.getColumn(i);
            BIDataSetFieldInfo info = (BIDataSetFieldInfo)column.getInfo();
            if (info.isCalcField() && info.getCalcMode() == CalcMode.AGGR_THEN_CALC) {
                DatasetFormulaParser parser = DSFormularManager.getInstance().createParser(this.dataset);
                DSFormulaContext dsCxt = new DSFormulaContext(this.dataset, this.dataset.get(0));
                try {
                    DSExpression expr = parser.parseEval(info.getFormula(), dsCxt);
                    data[i] = expr.evaluate(dsCxt);
                    continue;
                }
                catch (SyntaxException e) {
                    throw new BIDataSetException("\u8ba1\u7b97\u5b57\u6bb5\u3010" + info.getName() + "\u3011\u6c42\u503c\u51fa\u9519\uff0c" + e.getMessage(), e);
                }
            }
            int index = this.dataset.getMetadata().indexOf(info.getName());
            data[i] = this.dataset.get(0).getValue(index);
        }
    }

    private List<Integer> getColIdxList(List<String> colNameList) throws BIDataSetException {
        ArrayList<Integer> colIdxList = new ArrayList<Integer>();
        for (String colName : colNameList) {
            int idx = this.dataset.getMetadata().indexOf(colName);
            if (idx == -1) {
                throw new BIDataSetException("\u6570\u636e\u96c6\u4e2d\u4e0d\u5b58\u5728\u5b57\u6bb5\u3010" + colName + "\u3011");
            }
            colIdxList.add(idx);
        }
        return colIdxList;
    }

    private void checkField(List<Integer> dimColList, List<AggrMeasureItem> measureList) throws BIDataSetException {
        Map<Integer, Set<Integer>> dependMap;
        HashSet<Integer> orgset = new HashSet<Integer>();
        HashSet<Integer> dependset = new HashSet<Integer>();
        try {
            dependMap = this.loadFieldDependance();
        }
        catch (ParseException e) {
            throw new BIDataSetException(e.getMessage(), e);
        }
        for (AggrMeasureItem msItem : measureList) {
            int col = this.dataset.getMetadata().indexOf(msItem.getFieldName());
            orgset.add(col);
            Set<Integer> depends = dependMap.get(col);
            if (depends == null) continue;
            dependset.addAll(depends);
        }
        dependset.removeAll(orgset);
        for (Integer i : dependset) {
            this.checkCalcDependField(i, dimColList, measureList, dependMap);
        }
    }

    private void preprocess(List<Integer> reserveDimColList, List<Integer> showDimColList, boolean isAutoFillParentColumn) {
        Metadata<BIDataSetFieldInfo> metadata = this.dataset.getMetadata();
        HashSet<Integer> dimColSet = new HashSet<Integer>();
        for (Integer col : reserveDimColList) {
            int keyCol;
            BIDataSetFieldInfo info = (BIDataSetFieldInfo)metadata.getColumn(col.intValue()).getInfo();
            if (dimColSet.add(col)) {
                showDimColList.add(col);
            }
            if (!StringUtils.isNotEmpty((String)info.getKeyField()) || (keyCol = metadata.indexOf(info.getKeyField())) == -1 || keyCol == col || !dimColSet.add(keyCol)) continue;
            showDimColList.add(keyCol);
        }
        List<DSHierarchy> hiers = this.getHierarchy();
        if (isAutoFillParentColumn && hiers != null) {
            for (DSHierarchy hier : hiers) {
                if (hier.getType() != DSHierarchyType.PARENT_HIERARCHY) continue;
                Integer v1 = metadata.indexOf(hier.getLevels().get(0));
                Integer v2 = metadata.indexOf(hier.getParentFieldName());
                if (!dimColSet.contains(v1) || dimColSet.contains(v2) || v2 == -1) continue;
                dimColSet.add(v2);
                showDimColList.add(v2);
            }
        }
    }

    private List<DSHierarchy> getHierarchy() {
        Metadata<BIDataSetFieldInfo> metadata = this.dataset.getMetadata();
        return (List)metadata.getProperties().get("HIERARCHY");
    }

    private void checkCalcDependField(Integer checkFieldCol, List<Integer> dimColList, List<AggrMeasureItem> measureList, Map<Integer, Set<Integer>> dependMap) throws BIDataSetException {
        Metadata<BIDataSetFieldInfo> metadata = this.dataset.getMetadata();
        BIDataSetFieldInfo info = (BIDataSetFieldInfo)metadata.getColumn(checkFieldCol.intValue()).getInfo();
        if (info.isDimention()) {
            this.checkDependDimField(checkFieldCol, dimColList, measureList, dependMap);
        } else {
            AggrMeasureItem item = new AggrMeasureItem(info.getName(), null, info.getTitle(), info.getAggregation());
            measureList.add(item);
        }
    }

    private void checkDependDimField(Integer checkFieldCol, List<Integer> dimColList, List<AggrMeasureItem> measureList, Map<Integer, Set<Integer>> dependMap) throws BIDataSetException {
        Metadata<BIDataSetFieldInfo> metadata = this.dataset.getMetadata();
        BIDataSetFieldInfo info = (BIDataSetFieldInfo)metadata.getColumn(checkFieldCol.intValue()).getInfo();
        if (dimColList.contains(checkFieldCol)) {
            return;
        }
        boolean isValid = true;
        if (info.getFieldType() == FieldType.TIME_DIM) {
            boolean isContainTimekey;
            boolean bl = isContainTimekey = metadata.indexOf("SYS_TIMEKEY") != -1;
            if (isContainTimekey) {
                dimColList.add(checkFieldCol);
            } else {
                isValid = false;
            }
        } else {
            boolean isNameField = info.getName().equalsIgnoreCase(info.getNameField());
            int keyCol = this.dataset.getMetadata().indexOf(info.getKeyField());
            if (isNameField && dimColList.contains(keyCol)) {
                dimColList.add(checkFieldCol);
            } else {
                isValid = false;
            }
        }
        if (!isValid) {
            Set<Map.Entry<Integer, Set<Integer>>> entrySet = dependMap.entrySet();
            for (Map.Entry<Integer, Set<Integer>> entry : entrySet) {
                if (!entry.getValue().contains(checkFieldCol)) continue;
                String msColName = this.dataset.getMetadata().getColumn(entry.getKey().intValue()).getName();
                throw new BIDataSetException("\u5ea6\u91cf\u5b57\u6bb5" + msColName + "\u4f9d\u8d56\u4e86\u7ef4\u5ea6\u5217" + info.getName() + "\uff0c\u4f46\u8be5\u7ef4\u5ea6\u5217\u88ab\u6d88\u7ef4");
            }
        }
    }

    private Map<Integer, Set<Integer>> loadFieldDependance() throws ParseException {
        Metadata<BIDataSetFieldInfo> metadata = this.dataset.getMetadata();
        Map props = metadata.getProperties();
        Object obj = props.get("calcFieldDependence");
        if (obj == null) {
            HashMap<Integer, Set<Integer>> depMap = new HashMap<Integer, Set<Integer>>();
            DatasetFormulaParser parser = DSFormularManager.getInstance().createParser(this.dataset);
            DSFormulaContext dsCxt = new DSFormulaContext(this.dataset);
            List columns = metadata.getColumns();
            for (Column column : columns) {
                BIDataSetFieldInfo info = (BIDataSetFieldInfo)column.getInfo();
                if (!info.isCalcField() || info.getCalcMode() != CalcMode.AGGR_THEN_CALC) continue;
                int col = this.dataset.getMetadata().indexOf(info.getName());
                IExpression expr = parser._parseEvalWithoutOptimize(info.getFormula(), dsCxt);
                depMap.put(col, this.getAppearFieldList(expr));
            }
            props.put("calcFieldDependence", depMap);
            return depMap;
        }
        return (Map)obj;
    }

    private Set<Integer> getAppearFieldList(IExpression expr) {
        HashSet<Integer> sets = new HashSet<Integer>();
        for (IASTNode node : expr) {
            if (!(node instanceof DSFieldNode)) continue;
            DSFieldNode fd = (DSFieldNode)node;
            int col = this.dataset.getMetadata().indexOf(fd.getName());
            sets.add(col);
        }
        return sets;
    }
}

