/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.Metadata
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.function.FunctionNode
 *  com.jiuqi.bi.syntax.function.IFunction
 *  com.jiuqi.bi.syntax.function.text.Concatenate
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.operator.Plus
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 */
package com.jiuqi.bi.dataset.executor;

import com.jiuqi.bi.dataset.BIDataRow;
import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.BIDataSetImpl;
import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.dataset.expression.DSExpression;
import com.jiuqi.bi.dataset.expression.DSFieldNode;
import com.jiuqi.bi.dataset.expression.DSFormulaContext;
import com.jiuqi.bi.dataset.expression.DSFormularManager;
import com.jiuqi.bi.dataset.expression.DSFunctionNode;
import com.jiuqi.bi.dataset.expression.DSRestrictNode;
import com.jiuqi.bi.dataset.expression.DatasetFormulaParser;
import com.jiuqi.bi.dataset.function.Lag;
import com.jiuqi.bi.dataset.model.field.CalcMode;
import com.jiuqi.bi.dataset.model.field.FieldType;
import com.jiuqi.bi.dataset.restrict.FilterOptimizer;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.function.FunctionNode;
import com.jiuqi.bi.syntax.function.IFunction;
import com.jiuqi.bi.syntax.function.text.Concatenate;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.operator.Plus;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public final class FieldEvalExecutor {
    private BIDataSetImpl dataset;
    private String language;

    public FieldEvalExecutor(BIDataSetImpl dataset) {
        this.dataset = dataset;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void calc(Set<Integer> colSets) throws BIDataSetException {
        if (colSets == null || colSets.size() == 0) {
            return;
        }
        Map<Integer, CalcFieldNode> calcMap = this.parseCalcExpr(colSets);
        Collection<CalcFieldNode> calcNodes = calcMap.values();
        BIDataSetImpl orginds = this.dataset;
        Map<String, String> nestFormulas = this.getAllNestFormula(calcNodes);
        if (nestFormulas.size() > 0) {
            this.dataset = (BIDataSetImpl)this.dataset.addCalcFields(nestFormulas);
        }
        this.setOptimizerForExpression(calcNodes);
        List<CalcFieldNode> nodependNodes = this.findNotDependNode(calcMap);
        if (nodependNodes.size() > 0) {
            this.calcNotDependFieldNodes(nodependNodes);
        }
        if (orginds != this.dataset) {
            int count = orginds.getRecordCount();
            for (int row = 0; row < count; ++row) {
                Object[] rowdata = orginds.getRowData(row);
                Object[] rsdata = this.dataset.getRowData(row);
                for (Integer col : colSets) {
                    rowdata[col.intValue()] = rsdata[col];
                }
            }
            this.dataset = orginds;
        }
    }

    private List<FilterOptimizer> setOptimizerForExpression(Collection<CalcFieldNode> calcNodes) throws BIDataSetException {
        DSFormulaContext dsCxt = new DSFormulaContext(this.dataset);
        dsCxt.setLanguage(this.language);
        ArrayList<FilterOptimizer> optimizers = new ArrayList<FilterOptimizer>();
        for (CalcFieldNode cfn : calcNodes) {
            BIDataSetFieldInfo info = (BIDataSetFieldInfo)this.dataset.getMetadata().getColumn(cfn.colIdx).getInfo();
            FilterOptimizer optimizer = new FilterOptimizer();
            boolean ignoreCalcFieldDefaultRst = info.isCalcField() && info.isDimention();
            optimizer.analysis((IASTNode)cfn.calcExpr, dsCxt, ignoreCalcFieldDefaultRst);
            ((DSExpression)cfn.calcExpr).setOptimizer(optimizer);
            optimizers.add(optimizer);
        }
        return optimizers;
    }

    private List<CalcFieldNode> findNotDependNode(Map<Integer, CalcFieldNode> calcMap) throws BIDataSetException {
        ArrayList<CalcFieldNode> nodependNodes = new ArrayList<CalcFieldNode>();
        HashSet<Integer> hasCalcColSet = new HashSet<Integer>();
        for (CalcFieldNode node : calcMap.values()) {
            if (node.dependCalcCols.isEmpty()) {
                nodependNodes.add(node);
                continue;
            }
            this.testCycle(node, calcMap);
            this.compute(node, hasCalcColSet, calcMap);
        }
        ArrayList<CalcFieldNode> toBeRemoved = new ArrayList<CalcFieldNode>();
        Iterator iterator = hasCalcColSet.iterator();
        while (iterator.hasNext()) {
            int i = (Integer)iterator.next();
            toBeRemoved.add(calcMap.get(i));
        }
        nodependNodes.removeAll(toBeRemoved);
        return nodependNodes;
    }

    private void calcNotDependFieldNodes(List<CalcFieldNode> nodependNodes) throws BIDataSetException {
        ArrayList<CalcFieldNode> dimFdNodes = new ArrayList<CalcFieldNode>();
        ArrayList<CalcFieldNode> msFdNodes = new ArrayList<CalcFieldNode>();
        for (CalcFieldNode cfn : nodependNodes) {
            Column column = this.dataset.getMetadata().getColumn(cfn.colIdx);
            if (((BIDataSetFieldInfo)column.getInfo()).isDimention()) {
                dimFdNodes.add(cfn);
                continue;
            }
            msFdNodes.add(cfn);
        }
        if (dimFdNodes.size() > 0) {
            this._calcNodes(dimFdNodes);
        }
        if (msFdNodes.size() > 0) {
            this._calcNodes(msFdNodes);
        }
    }

    private void _calcNodes(List<CalcFieldNode> nodes) throws BIDataSetException {
        boolean usingSingleThread;
        boolean bl = usingSingleThread = nodes.size() < 10 || this.dataset.getRecordCount() < 100000;
        if (usingSingleThread) {
            for (BIDataRow curRow : this.dataset) {
                this.doCalc(nodes, curRow);
            }
        } else {
            ExecutorService ex = Executors.newCachedThreadPool();
            ArrayList<Future<String>> rsList = new ArrayList<Future<String>>();
            for (final CalcFieldNode calcFieldNode : nodes) {
                final BIDataSetFieldInfo info = (BIDataSetFieldInfo)this.dataset.getMetadata().getColumn(calcFieldNode.colIdx).getInfo();
                Future<String> f = ex.submit(new Callable<String>(){

                    @Override
                    public String call() throws Exception {
                        Iterator<BIDataRow> itor = FieldEvalExecutor.this.dataset.iterator();
                        try {
                            while (itor.hasNext()) {
                                BIDataRow curRow = itor.next();
                                FieldEvalExecutor.this.doCalc(calcFieldNode, curRow, info);
                            }
                            return null;
                        }
                        catch (BIDataSetException e) {
                            return e.getMessage();
                        }
                    }
                });
                rsList.add(f);
            }
            ex.shutdown();
            for (Future future : rsList) {
                String err;
                try {
                    err = (String)future.get();
                }
                catch (Exception e) {
                    throw new BIDataSetException(e.getMessage(), e);
                }
                if (err == null) continue;
                throw new BIDataSetException(err);
            }
        }
    }

    private void compute(CalcFieldNode node, Set<Integer> hasCalcColSet, Map<Integer, CalcFieldNode> calcMap) throws BIDataSetException {
        if (hasCalcColSet.contains(node.colIdx)) {
            return;
        }
        Set<Integer> depends = node.dependCalcCols;
        for (Integer depend : depends) {
            CalcFieldNode dependNode;
            if (hasCalcColSet.contains(depend) || (dependNode = calcMap.get(depend)) == null) continue;
            this.compute(dependNode, hasCalcColSet, calcMap);
        }
        Iterator<BIDataRow> itor = this.dataset.iterator();
        BIDataSetFieldInfo info = (BIDataSetFieldInfo)this.dataset.getMetadata().getColumn(node.colIdx).getInfo();
        while (itor.hasNext()) {
            BIDataRow curRow = itor.next();
            this.doCalc(node, curRow, info);
        }
        hasCalcColSet.add(node.colIdx);
    }

    private void testCycle(CalcFieldNode node, Map<Integer, CalcFieldNode> calcMap) throws BIDataSetException {
        HashSet<Integer> needComputeCol = new HashSet<Integer>();
        needComputeCol.add(node.colIdx);
        this.testPrevNode(node, calcMap, needComputeCol);
    }

    private void testPrevNode(CalcFieldNode node, Map<Integer, CalcFieldNode> calcMap, Set<Integer> needComputeCol) throws BIDataSetException {
        Set<Integer> depends = node.dependCalcCols;
        for (Integer depend : depends) {
            if (!needComputeCol.add(depend)) {
                String calcFieldName = this.dataset.getMetadata().getColumn(depend.intValue()).getName();
                throw new BIDataSetException("\u6570\u636e\u96c6\u7684\u8ba1\u7b97\u5b57\u6bb5\u3010" + calcFieldName + "\u3011\u5b58\u5728\u5faa\u73af\u4f9d\u8d56\uff0c\u65e0\u6cd5\u6267\u884c\u8ba1\u7b97");
            }
            CalcFieldNode prevNode = calcMap.get(depend);
            if (prevNode == null) continue;
            this.testPrevNode(prevNode, calcMap, needComputeCol);
        }
    }

    private Map<Integer, CalcFieldNode> parseCalcExpr(Set<Integer> colSet) throws BIDataSetException {
        DatasetFormulaParser parser = DSFormularManager.getInstance().createParser(this.dataset);
        DSFormulaContext dsCxt = new DSFormulaContext(this.dataset, null);
        dsCxt.setLanguage(this.language);
        HashMap<Integer, CalcFieldNode> map = new HashMap<Integer, CalcFieldNode>();
        Metadata<BIDataSetFieldInfo> metadata = this.dataset.getMetadata();
        for (Integer col : colSet) {
            IExpression expr;
            Column column = metadata.getColumn(col.intValue());
            BIDataSetFieldInfo info = (BIDataSetFieldInfo)column.getInfo();
            String formula = info.getFormula();
            try {
                expr = parser._parseEvalWithoutOptimize(formula, dsCxt);
            }
            catch (ParseException e) {
                throw new BIDataSetException("\u8ba1\u7b97\u5b57\u6bb5\u3010" + info.getName() + "\u3011\u6c42\u503c\u51fa\u9519\uff0c" + e.getMessage(), e);
            }
            DSExpression dsExpr = new DSExpression(formula, expr.getChild(0));
            try {
                this._setFieldNodeNameMode(dsExpr, dsCxt);
            }
            catch (SyntaxException e) {
                throw new BIDataSetException(e.getMessage(), e);
            }
            CalcFieldNode node = new CalcFieldNode(column.getIndex(), dsExpr);
            node.dependCalcCols = this.getDependCalcMsCols(column.getIndex(), dsExpr);
            map.put(column.getIndex(), node);
        }
        return map;
    }

    private void _setFieldNodeNameMode(DSExpression dsExpr, IContext context) throws SyntaxException {
        Iterator itor = dsExpr.iterator();
        while (itor.hasNext()) {
            IASTNode node = (IASTNode)itor.next();
            if (!this.isConcatenateString(node, context)) continue;
            int childSize = node.childrenSize();
            for (int i = 0; i < childSize; ++i) {
                IASTNode child = node.getChild(i);
                if (!(child instanceof DSFieldNode)) continue;
                ((DSFieldNode)child).setValueMode(2);
            }
        }
    }

    private boolean isConcatenateString(IASTNode node, IContext context) throws SyntaxException {
        if (node instanceof Plus) {
            int childSize = node.childrenSize();
            for (int i = 0; i < childSize; ++i) {
                IASTNode child = node.getChild(i);
                if (child.getType(context) != 6) continue;
                return true;
            }
        }
        return node instanceof FunctionNode && ((FunctionNode)node).isFunction(new Class[]{Concatenate.class});
    }

    private Map<String, String> getAllNestFormula(Collection<CalcFieldNode> calcFdNodes) throws BIDataSetException {
        HashMap<String, String> needAppendCols = new HashMap<String, String>();
        for (CalcFieldNode cfn : calcFdNodes) {
            Column column = this.dataset.getMetadata().getColumn(cfn.colIdx);
            List<String> list = this.replaceNestFormula(cfn.calcExpr.getChild(0), ((BIDataSetFieldInfo)column.getInfo()).getCalcMode());
            if (list == null) continue;
            for (String formula : list) {
                needAppendCols.put(formula, formula);
            }
        }
        return needAppendCols;
    }

    private List<String> replaceNestFormula(IASTNode root, CalcMode calcMode) throws BIDataSetException {
        if (root instanceof DSFunctionNode) {
            DSFunctionNode funcNode = (DSFunctionNode)root;
            ArrayList<String> calcFieldList = new ArrayList<String>();
            DSFormulaContext context = new DSFormulaContext(this.dataset);
            context.setLanguage(this.language);
            for (int i = 0; i < funcNode.childrenSize(); ++i) {
                String formula;
                Object child = funcNode.getChild(i);
                if (!this.isParamNeedConvertToCalcField((IASTNode)child)) continue;
                try {
                    formula = child.interpret((IContext)context, Language.FORMULA, null);
                }
                catch (InterpretException e) {
                    throw new BIDataSetException(e.getMessage(), e);
                }
                calcFieldList.add(formula);
                BIDataSetFieldInfo info = new BIDataSetFieldInfo();
                info.setCalcField(true);
                info.setCalcMode(calcMode);
                info.setFieldType(FieldType.MEASURE);
                info.setFormula(formula);
                info.setName(formula);
                child = new DSFieldNode(null, info);
                funcNode.setChild(i, (IASTNode)child);
            }
            return calcFieldList;
        }
        return null;
    }

    private boolean isParamNeedConvertToCalcField(IASTNode node) {
        if (node instanceof DSRestrictNode) {
            return true;
        }
        if (node instanceof FunctionNode) {
            FunctionNode funcNode = (FunctionNode)node;
            IFunction func = funcNode.getDefine();
            return !(func instanceof Lag);
        }
        for (IASTNode nd : node) {
            if (!(nd instanceof DSRestrictNode)) continue;
            return true;
        }
        return false;
    }

    private Set<Integer> getDependCalcMsCols(int colIdx, IExpression expr) throws BIDataSetException {
        HashSet<Integer> depends = new HashSet<Integer>();
        Iterator itor = expr.iterator();
        Metadata<BIDataSetFieldInfo> metadata = this.dataset.getMetadata();
        while (itor.hasNext()) {
            DSFieldNode fieldNode;
            BIDataSetFieldInfo info;
            IASTNode node = (IASTNode)itor.next();
            if (!(node instanceof DSFieldNode) || !(info = (fieldNode = (DSFieldNode)node).getFieldInfo()).isCalcField() || info.getFieldType() != FieldType.MEASURE) continue;
            int col = metadata.indexOf(info.getName());
            if (col == -1) {
                String calcFieldName = metadata.getColumn(colIdx).getName();
                throw new BIDataSetException("\u8ba1\u7b97\u5b57\u6bb5\u3010" + calcFieldName + "\u3011\u6c42\u503c\u51fa\u9519\uff0c\u8868\u8fbe\u5f0f\u4e2d\u4f9d\u8d56\u7684\u3010" + info.getName() + "\u3011\u4e0d\u5b58\u5728");
            }
            depends.add(col);
        }
        return depends;
    }

    private void doCalc(List<CalcFieldNode> calcFields, BIDataRow dataRow) throws BIDataSetException {
        DSFormulaContext dsCxt = new DSFormulaContext(this.dataset, dataRow);
        dsCxt.setLanguage(this.language);
        int size = calcFields.size();
        for (int i = 0; i < size; ++i) {
            CalcFieldNode node = calcFields.get(i);
            try {
                BIDataSetFieldInfo info = (BIDataSetFieldInfo)this.dataset.getMetadata().getColumn(node.colIdx).getInfo();
                if (info != null) {
                    dsCxt.setCalcMode(info.getCalcMode());
                }
                Object value = node.calcExpr.evaluate((IContext)dsCxt);
                int rownum = dataRow.getRowNum();
                this.dataset.getRowData((int)rownum)[node.colIdx] = value;
                continue;
            }
            catch (SyntaxException e) {
                String colName = this.dataset.getMetadata().getColumn(node.colIdx).getName();
                throw new BIDataSetException("\u8ba1\u7b97\u5b57\u6bb5\u3010" + colName + "\u3011\u6c42\u503c\u51fa\u9519\uff0c" + e.getMessage(), e);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void doCalc(CalcFieldNode node, BIDataRow dataRow, BIDataSetFieldInfo info) throws BIDataSetException {
        DSFormulaContext dsCxt = new DSFormulaContext(this.dataset, dataRow);
        dsCxt.setLanguage(this.language);
        try {
            Object[] rowData;
            if (info != null) {
                dsCxt.setCalcMode(info.getCalcMode());
            }
            Object value = node.calcExpr.evaluate((IContext)dsCxt);
            int rownum = dataRow.getRowNum();
            Object[] objectArray = rowData = this.dataset.getRowData(rownum);
            synchronized (rowData) {
                rowData[node.colIdx] = value;
                // ** MonitorExit[var8_10] (shouldn't be in output)
            }
        }
        catch (SyntaxException e) {
            String colName = this.dataset.getMetadata().getColumn(node.colIdx).getName();
            throw new BIDataSetException("\u8ba1\u7b97\u5b57\u6bb5\u3010" + colName + "\u3011\u6c42\u503c\u51fa\u9519\uff0c" + e.getMessage(), e);
        }
        {
            return;
        }
    }

    private final class CalcFieldNode {
        public int colIdx;
        public IExpression calcExpr;
        public Set<Integer> dependCalcCols;

        public CalcFieldNode(int colIdx, IExpression calcExpr) {
            this.colIdx = colIdx;
            this.calcExpr = calcExpr;
        }
    }
}

