/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.logging.ILogger
 *  com.jiuqi.bi.logging.SLFLogger
 *  com.jiuqi.bi.syntax.data.ArrayData
 *  com.jiuqi.bi.syntax.sql.ISQLQueryListener
 *  com.jiuqi.bi.syntax.sql.ISQLQueryListener$ParamInfo
 *  com.jiuqi.bi.syntax.sql.SQLQueryException
 *  com.jiuqi.bi.syntax.sql.SQLQueryExecutor
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.bi.util.tree.ObjectVistor
 *  com.jiuqi.bi.util.tree.TreeBuilder
 *  com.jiuqi.bi.util.tree.TreeBuilderFactory
 *  com.jiuqi.bi.util.tree.TreeNode
 */
package com.jiuqi.nvwa.framework.parameter.datasource.extend;

import com.jiuqi.bi.logging.ILogger;
import com.jiuqi.bi.logging.SLFLogger;
import com.jiuqi.bi.syntax.data.ArrayData;
import com.jiuqi.bi.syntax.sql.ISQLQueryListener;
import com.jiuqi.bi.syntax.sql.SQLQueryException;
import com.jiuqi.bi.syntax.sql.SQLQueryExecutor;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.bi.util.tree.ObjectVistor;
import com.jiuqi.bi.util.tree.TreeBuilder;
import com.jiuqi.bi.util.tree.TreeBuilderFactory;
import com.jiuqi.bi.util.tree.TreeNode;
import com.jiuqi.nvwa.framework.parameter.ParameterCalculator;
import com.jiuqi.nvwa.framework.parameter.ParameterException;
import com.jiuqi.nvwa.framework.parameter.ParameterResultItem;
import com.jiuqi.nvwa.framework.parameter.ParameterResultset;
import com.jiuqi.nvwa.framework.parameter.datasource.DataSourceCandidateFieldInfo;
import com.jiuqi.nvwa.framework.parameter.datasource.ParameterDataSourceContext;
import com.jiuqi.nvwa.framework.parameter.datasource.ParameterDataSourceHierarchyType;
import com.jiuqi.nvwa.framework.parameter.datasource.extend.ISQLQueryProvider;
import com.jiuqi.nvwa.framework.parameter.datasource.extend.SQLDataSourceModel;
import com.jiuqi.nvwa.framework.parameter.model.ParameterDependMember;
import com.jiuqi.nvwa.framework.parameter.model.ParameterHierarchyFilterItem;
import com.jiuqi.nvwa.framework.parameter.model.ParameterModel;
import com.jiuqi.nvwa.framework.parameter.model.ParameterSelectMode;
import com.jiuqi.nvwa.framework.parameter.model.config.AbstractParameterValueConfig;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class SQLQueryHelper {
    private ParameterModel parameterModel;
    private SQLDataSourceModel datasourceModel;
    private String[] colNames;
    private static ISQLQueryProvider sqlProvider;

    public SQLQueryHelper(ParameterModel parameterModel) {
        this.parameterModel = parameterModel;
        this.datasourceModel = (SQLDataSourceModel)parameterModel.getDatasource();
    }

    public static void setSQLQueryProvider(ISQLQueryProvider provider) {
        sqlProvider = provider;
    }

    public Connection getConnection() throws SQLException {
        if (sqlProvider != null) {
            return sqlProvider.getConnection(this.datasourceModel.getDatasourceId());
        }
        throw new SQLException("\u672a\u8bbe\u7f6e\u6570\u636e\u8fde\u63a5\u63d0\u4f9b\u5668");
    }

    /*
     * Exception decompiling
     */
    public List<DataSourceCandidateFieldInfo> getSQLFields() throws ParameterException {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 3 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJar(BatchJarDecompiler.java:77)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJars(BatchJarDecompiler.java:47)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.main(BatchJarDecompiler.java:116)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive exception aggregation
     */
    public ParameterResultset filterByAppointValue(ParameterCalculator calculator, List<String> appointKeyValues, String filter, ParameterDataSourceContext.PageInfo pageInfo) throws ParameterException {
        String sql = this.datasourceModel.getSql();
        if (StringUtils.isEmpty((String)sql)) {
            throw new ParameterException("SQL\u6765\u6e90\u7684\u53c2\u6570\u4e2d\uff0c\u672a\u5b9a\u4e49SQL\u8bed\u53e5");
        }
        try (Connection conn = this.getConnection();){
            ParameterResultset parameterResultset;
            SQLQueryExecutor executor = this.buildQueryExecutor(conn, calculator);
            try {
                parameterResultset = this.doQuerySQL(calculator, executor, appointKeyValues, filter, pageInfo);
            }
            catch (Throwable throwable) {
                executor.close();
                throw throwable;
            }
            executor.close();
            return parameterResultset;
        }
        catch (Exception e) {
            throw new ParameterException(e.getMessage(), e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive exception aggregation
     */
    public ParameterResultset getFirstValue(ParameterCalculator calculator, List<String> appointKeyValues) throws ParameterException {
        String sql = this.datasourceModel.getSql();
        if (StringUtils.isEmpty((String)sql)) {
            throw new ParameterException("SQL\u6765\u6e90\u7684\u53c2\u6570\u4e2d\uff0c\u672a\u5b9a\u4e49SQL\u8bed\u53e5");
        }
        try (Connection conn = this.getConnection();){
            SQLQueryExecutor executor;
            block18: {
                block16: {
                    ParameterResultset rs;
                    block17: {
                        ParameterResultset parameterResultset;
                        executor = this.buildQueryExecutor(conn, calculator);
                        try {
                            ParameterDataSourceHierarchyType hierType = this.datasourceModel.getHierarchyType();
                            if (hierType != ParameterDataSourceHierarchyType.PARENTMODE && hierType != ParameterDataSourceHierarchyType.STRUCTURECODE) break block16;
                            rs = this.doQuerySQL(calculator, executor, appointKeyValues, null, null);
                            if (!rs.isEmpty()) break block17;
                            parameterResultset = ParameterResultset.EMPTY_RESULTSET;
                        }
                        catch (Throwable throwable) {
                            executor.close();
                            throw throwable;
                        }
                        executor.close();
                        return parameterResultset;
                    }
                    if (appointKeyValues != null && !appointKeyValues.isEmpty()) {
                        rs.sortByKeysOrder(appointKeyValues);
                    }
                    TreeNode root = this.buildTree(rs);
                    ParameterResultItem item = (ParameterResultItem)root.getChildren()[0].getItem();
                    ParameterResultset parameterResultset = new ParameterResultset(item);
                    executor.close();
                    return parameterResultset;
                }
                if (appointKeyValues != null && !appointKeyValues.isEmpty()) break block18;
                ParameterResultset rs = this.doQuerySQL(calculator, executor, appointKeyValues, null, new ParameterDataSourceContext.PageInfo(1, 1));
                executor.close();
                return rs;
            }
            ParameterResultset result = this.doQuerySQL(calculator, executor, appointKeyValues, null, null);
            result.sortByKeysOrder(appointKeyValues);
            ParameterResultset parameterResultset = result.size() > 0 ? new ParameterResultset(result.get(0)) : ParameterResultset.EMPTY_RESULTSET;
            executor.close();
            return parameterResultset;
        }
        catch (Exception e) {
            throw new ParameterException(e.getMessage(), e);
        }
    }

    public ParameterResultset getChildrenValue(ParameterCalculator calculator, List<String> appointKeyValues, ParameterHierarchyFilterItem hierItem) throws ParameterException {
        TreeNode root;
        String sql = this.datasourceModel.getSql();
        if (StringUtils.isEmpty((String)sql)) {
            throw new ParameterException("SQL\u6765\u6e90\u7684\u53c2\u6570\u4e2d\uff0c\u672a\u5b9a\u4e49SQL\u8bed\u53e5");
        }
        String parent = hierItem.getParent();
        ParameterResultset result = this.filterByAppointValue(calculator, appointKeyValues, null, null);
        try {
            root = this.buildTree(result);
        }
        catch (Exception e) {
            throw new ParameterException("\u6784\u9020\u6811\u5f62\u7ed3\u6784\u51fa\u9519", e);
        }
        if (parent != null && !parent.trim().isEmpty() && (root = this.findTreeNodeById(root, parent)) == null) {
            return ParameterResultset.EMPTY_RESULTSET;
        }
        ArrayList<ParameterResultItem> list = new ArrayList<ParameterResultItem>();
        this.buildParameterResultItemFromTreeNode(root, list, hierItem.isAllSub());
        return new ParameterResultset(list);
    }

    private void buildParameterResultItemFromTreeNode(TreeNode root, List<ParameterResultItem> list, boolean allSub) {
        TreeNode[] children;
        ParameterResultItem rootItem = (ParameterResultItem)root.getItem();
        for (TreeNode child : children = root.getChildren()) {
            ParameterResultItem item = (ParameterResultItem)child.getItem();
            if (rootItem != null && rootItem.getValue() != null) {
                item.setParent(rootItem.getValue().toString());
            }
            if (child.getChildren().length > 0) {
                item.setLeaf(false);
            }
            item.setLevel(child.level());
            ArrayList<String> path = new ArrayList<String>();
            for (TreeNode parent = child.getParent(); parent != null && parent.getItem() != null; parent = parent.getParent()) {
                String pv = ((ParameterResultItem)parent.getItem()).getValue().toString();
                path.add(pv);
            }
            Collections.reverse(path);
            item.setPath(StringUtils.join(path.iterator(), (String)"/"));
            list.add(item);
            if (!allSub) continue;
            this.buildParameterResultItemFromTreeNode(child, list, allSub);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive exception aggregation
     */
    public ParameterResultset search(ParameterCalculator calculator, List<String> candidateValue, List<String> searchValues, ParameterDataSourceContext.PageInfo pageInfo, boolean showPath) throws ParameterException {
        String[] cols = this.getSQLColumnNames(calculator);
        String keycolName = cols[0];
        String namecolName = null;
        if (cols.length > 1) {
            namecolName = cols[1];
        }
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < searchValues.size(); ++i) {
            if (i > 0) {
                buf.append(" and ");
            }
            buf.append("(");
            List<String> conds = Arrays.asList(searchValues.get(i).split(" "));
            for (int j = 0; j < conds.size(); ++j) {
                String cond = conds.get(j);
                if (cond.isEmpty()) continue;
                if (j > 0) {
                    buf.append(" or ");
                }
                this.appendKeySearchCond(buf, keycolName, cond);
                if (!StringUtils.isNotEmpty((String)namecolName)) continue;
                buf.append(" or ");
                this.appendKeySearchCond(buf, namecolName, cond);
            }
            buf.append(")");
        }
        String filterExpr = buf.toString();
        try (Connection conn = this.getConnection();){
            ParameterResultset parameterResultset;
            SQLQueryExecutor executor = this.buildQueryExecutor(conn, calculator);
            try {
                parameterResultset = this.doQuerySQL(calculator, executor, candidateValue, filterExpr, pageInfo);
            }
            catch (Throwable throwable) {
                executor.close();
                throw throwable;
            }
            executor.close();
            return parameterResultset;
        }
        catch (SQLQueryException e) {
            throw new ParameterException(e.getMessage(), e);
        }
        catch (SQLException e) {
            throw new ParameterException(e.getMessage(), e);
        }
    }

    private void appendKeySearchCond(StringBuilder buf, String fieldName, String value) {
        char[] chars;
        boolean containLow = false;
        for (char c : chars = value.toCharArray()) {
            if (c < 'a' || c > 'z') continue;
            containLow = true;
            break;
        }
        if (containLow) {
            buf.append("UPPER(").append(fieldName).append(")").append(" like '%").append(value.toUpperCase()).append("%' ");
        } else {
            buf.append(fieldName).append(" like '%").append(value).append("%' ");
        }
    }

    private TreeNode buildTree(ParameterResultset result) throws Exception {
        TreeBuilder treeBuilder;
        switch (this.datasourceModel.getHierarchyType()) {
            case PARENTMODE: {
                treeBuilder = TreeBuilderFactory.createParentTreeBuilder((ObjectVistor)new ParentTreeNodeValueVistor());
                break;
            }
            case STRUCTURECODE: {
                String structCode = this.datasourceModel.getStructureCode();
                treeBuilder = TreeBuilderFactory.createStructTreeBuilder((ObjectVistor)new ParentTreeNodeValueVistor(), (String)structCode, (boolean)true);
                break;
            }
            default: {
                throw new Exception("\u975e\u6811\u5f62\u7ed3\u6784\uff0c\u4e0d\u80fd\u8c03\u7528\u8be5\u65b9\u6cd5");
            }
        }
        treeBuilder.setSortMode(1);
        return treeBuilder.build(result.iterator());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private ParameterResultset doQuerySQL(ParameterCalculator calculator, SQLQueryExecutor executor, List<String> appointKeyValues, String filter, ParameterDataSourceContext.PageInfo pageInfo) throws SQLQueryException, SQLException, ParameterException {
        boolean filterInMemory = false;
        HashSet<String> keys = new HashSet<String>();
        HashMap<String, List<Object>> map = new HashMap<String, List<Object>>();
        if (this.canFilterByDB(appointKeyValues)) {
            map.put(this.getKeyColName(calculator), appointKeyValues);
        } else if (appointKeyValues != null && !appointKeyValues.isEmpty()) {
            filterInMemory = true;
            for (String s : appointKeyValues) {
                keys.add(s);
            }
        }
        AbstractParameterValueConfig cfg = this.parameterModel.getValueConfig();
        List<ParameterDependMember> depends = cfg.getDepends();
        if (!depends.isEmpty()) {
            StringBuilder filterBuf = new StringBuilder();
            if (filter != null) {
                filterBuf.append(filter);
            }
            for (ParameterDependMember dm : depends) {
                String colName = dm.getDatasourceFieldName();
                if (StringUtils.isEmpty((String)colName)) continue;
                ParameterModel pmodel = calculator.findParameterModelByName(dm.getParameterName(), true);
                ParameterResultset dependParamVal = calculator.getValue(dm.getParameterName());
                if (dependParamVal.isEmpty()) continue;
                if (pmodel.isRangeParameter()) {
                    int dataType = pmodel.getDataType();
                    Object min = dependParamVal.get(0).getValue();
                    Object max = dependParamVal.get(1).getValue();
                    if (min != null) {
                        if (filterBuf.length() > 0) {
                            filterBuf.append(" and ");
                        }
                        if (dataType == 6 || dataType == 2) {
                            filterBuf.append(colName).append(">='").append(min).append("'");
                        } else {
                            filterBuf.append(colName).append(">=").append(min);
                        }
                    }
                    if (max == null) continue;
                    if (filterBuf.length() > 0) {
                        filterBuf.append(" and ");
                    }
                    if (dataType == 6 || dataType == 2) {
                        filterBuf.append(colName).append("<='").append(max).append("'");
                        continue;
                    }
                    filterBuf.append(colName).append("<=").append(max);
                    continue;
                }
                List v = (List)map.get(colName);
                if (v == null) {
                    map.put(colName, dependParamVal.getValueAsList());
                    continue;
                }
                List<Object> dvs = dependParamVal.getValueAsList();
                HashSet set = new HashSet();
                v.forEach(c -> set.add(c));
                dvs = dvs.stream().filter(c -> set.contains(c)).collect(Collectors.toList());
                map.put(colName, dvs);
            }
            if (filterBuf.length() > 0) {
                filter = filterBuf.toString();
            }
        }
        try (ResultSet rs = pageInfo != null && pageInfo.startRow > 0 && pageInfo.recordSize > 0 ? executor.executeQuery(pageInfo.startRow - 1, pageInfo.recordSize + pageInfo.startRow - 1, map, filter) : executor.executeQuery(map, filter);){
            int columnCount = rs.getMetaData().getColumnCount();
            ArrayList<ParameterResultItem> result = new ArrayList<ParameterResultItem>();
            HashSet<String> set = new HashSet<String>();
            while (rs.next()) {
                Object object = rs.getObject(1);
                if (object == null) continue;
                ParameterResultItem item = new ParameterResultItem(object.toString());
                String key = rs.getString(1);
                if (!set.add(key)) {
                    throw new ParameterException("\u4ee3\u7801\u5217\u91cd\u590d");
                }
                if (filterInMemory && !keys.contains(key)) continue;
                item.setTitle(columnCount > 1 ? rs.getString(2) : rs.getString(1));
                if (columnCount > 2 && this.datasourceModel.getHierarchyType() == ParameterDataSourceHierarchyType.PARENTMODE) {
                    item.setParent(rs.getString(3));
                }
                result.add(item);
            }
            ParameterResultset parameterResultset = new ParameterResultset(result);
            return parameterResultset;
        }
    }

    private boolean canFilterByDB(List<String> appointKeyValues) {
        if (appointKeyValues == null || appointKeyValues.isEmpty()) {
            return false;
        }
        return appointKeyValues.size() <= 100;
    }

    String getKeyColName(ParameterCalculator calculator) throws SQLQueryException, SQLException, ParameterException {
        String[] names = this.getSQLColumnNames(calculator);
        if (names != null) {
            return names[0];
        }
        throw new ParameterException("\u83b7\u53d6SQL\u5143\u6570\u636e\u5931\u8d25");
    }

    private SQLQueryExecutor buildQueryExecutor(Connection conn, final ParameterCalculator calculator) throws SQLQueryException {
        SQLQueryExecutor executor = sqlProvider.createQueryExecutor(conn, calculator.getUserId(), this.datasourceModel.getDatasourceId());
        if (executor == null) {
            executor = new SQLQueryExecutor(conn);
        }
        if (executor.getLogger() == null) {
            executor.setLogger((ILogger)new SLFLogger("com.jiuqi.nvwa.parameter"));
        }
        executor.registerListener(new ISQLQueryListener(){

            public ISQLQueryListener.ParamInfo findParam(String paramName) {
                try {
                    return SQLQueryHelper.this.getParamInfo(calculator, paramName);
                }
                catch (ParameterException e) {
                    return null;
                }
            }
        });
        executor.open(this.datasourceModel.getSql());
        return executor;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public String[] getSQLColumnNames(ParameterCalculator calculator) throws ParameterException {
        if (this.colNames == null) {
            try (Connection conn = this.getConnection();
                 SQLQueryExecutor executor = this.buildQueryExecutor(conn, calculator);
                 ResultSet rs = executor.executeQuery(0, 1);){
                ResultSetMetaData metadata = rs.getMetaData();
                this.colNames = new String[metadata.getColumnCount()];
                for (int i = 0; i < this.colNames.length; ++i) {
                    this.colNames[i] = metadata.getColumnLabel(i + 1);
                    int pos = this.colNames[i].indexOf(46);
                    if (pos <= 0) continue;
                    this.colNames[i] = this.colNames[i].substring(pos + 1);
                }
            }
            catch (SQLQueryException e) {
                throw new ParameterException(e.getMessage(), e);
            }
            catch (SQLException e) {
                throw new ParameterException(e.getMessage(), e);
            }
        }
        return this.colNames;
    }

    private ISQLQueryListener.ParamInfo getParamInfo(ParameterCalculator calculator, String name) throws ParameterException {
        ParameterModel model = null;
        ParameterResultset value = null;
        if (calculator != null && (model = calculator.findParameterModelByName(name, true)) != null) {
            value = calculator.getValue(name);
        }
        if (model.getSelectMode() == ParameterSelectMode.MUTIPLE) {
            ArrayList<Object> values = new ArrayList<Object>();
            for (ParameterResultItem item : value) {
                values.add(item.getValue());
            }
            ArrayData arrays = new ArrayData(model.getDataType(), values);
            return new ISQLQueryListener.ParamInfo(name, 11, (Object)arrays);
        }
        return new ISQLQueryListener.ParamInfo(name, model.getDataType(), value.isEmpty() ? null : value.get(0).getValue());
    }

    private TreeNode findTreeNodeById(TreeNode root, String id) {
        TreeNode[] children;
        ParameterResultItem item;
        if (root.getItem() != null && (item = (ParameterResultItem)root.getItem()).getValue().toString().equals(id)) {
            return root;
        }
        for (TreeNode tn : children = root.getChildren()) {
            TreeNode node = this.findTreeNodeById(tn, id);
            if (node == null) continue;
            return node;
        }
        return null;
    }

    private static class ParentTreeNodeValueVistor
    implements ObjectVistor {
        private ParentTreeNodeValueVistor() {
        }

        public String getCode(Object arg0) {
            ParameterResultItem row = (ParameterResultItem)arg0;
            return row.getValue().toString();
        }

        public String getParentCode(Object arg0) {
            ParameterResultItem row = (ParameterResultItem)arg0;
            return row.getParent();
        }
    }
}

