/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.sf.jsqlparser.JSQLParserException
 *  net.sf.jsqlparser.expression.Expression
 *  net.sf.jsqlparser.expression.ExpressionVisitor
 *  net.sf.jsqlparser.expression.Function
 *  net.sf.jsqlparser.expression.UserVariable
 *  net.sf.jsqlparser.expression.operators.arithmetic.Concat
 *  net.sf.jsqlparser.expression.operators.relational.EqualsTo
 *  net.sf.jsqlparser.expression.operators.relational.GreaterThan
 *  net.sf.jsqlparser.expression.operators.relational.GreaterThanEquals
 *  net.sf.jsqlparser.expression.operators.relational.InExpression
 *  net.sf.jsqlparser.expression.operators.relational.LikeExpression
 *  net.sf.jsqlparser.expression.operators.relational.MinorThan
 *  net.sf.jsqlparser.expression.operators.relational.MinorThanEquals
 *  net.sf.jsqlparser.expression.operators.relational.NotEqualsTo
 *  net.sf.jsqlparser.parser.CCJSqlParserUtil
 *  net.sf.jsqlparser.schema.Column
 *  net.sf.jsqlparser.schema.Table
 *  net.sf.jsqlparser.statement.select.FromItem
 *  net.sf.jsqlparser.statement.select.FromItemVisitor
 *  net.sf.jsqlparser.statement.select.Join
 *  net.sf.jsqlparser.statement.select.PlainSelect
 *  net.sf.jsqlparser.statement.select.Select
 *  net.sf.jsqlparser.statement.select.SubSelect
 *  net.sf.jsqlparser.statement.select.WithItem
 *  net.sf.jsqlparser.util.TablesNamesFinder
 *  net.sf.jsqlparser.util.deparser.SelectDeParser
 *  org.apache.commons.collections4.map.CaseInsensitiveMap
 *  org.apache.shiro.util.CollectionUtils
 *  org.apache.shiro.util.StringUtils
 */
package com.jiuqi.va.query.sql.parser;

import com.jiuqi.va.query.sql.parser.common.UserVariableFinder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.ExpressionVisitor;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.expression.UserVariable;
import net.sf.jsqlparser.expression.operators.arithmetic.Concat;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.GreaterThan;
import net.sf.jsqlparser.expression.operators.relational.GreaterThanEquals;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.expression.operators.relational.LikeExpression;
import net.sf.jsqlparser.expression.operators.relational.MinorThan;
import net.sf.jsqlparser.expression.operators.relational.MinorThanEquals;
import net.sf.jsqlparser.expression.operators.relational.NotEqualsTo;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.FromItemVisitor;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SubSelect;
import net.sf.jsqlparser.statement.select.WithItem;
import net.sf.jsqlparser.util.TablesNamesFinder;
import net.sf.jsqlparser.util.deparser.SelectDeParser;
import org.apache.commons.collections4.map.CaseInsensitiveMap;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.util.StringUtils;

public class QueryVariableParser {
    public Map<String, TableColumnInfo> parse(String sql) throws JSQLParserException {
        Select select = (Select)CCJSqlParserUtil.parse((String)sql);
        return this.parseSelect(select);
    }

    private Map<String, TableColumnInfo> parseSelect(Select select) {
        CaseInsensitiveMap result = new CaseInsensitiveMap();
        HashMap<String, SubSelect> cteMap = new HashMap<String, SubSelect>();
        if (select.getWithItemsList() != null) {
            for (WithItem withItem : select.getWithItemsList()) {
                cteMap.put(withItem.getName().toLowerCase(), withItem.getSubSelect());
            }
        }
        if (select.getSelectBody() instanceof PlainSelect) {
            PlainSelect plainSelect = (PlainSelect)select.getSelectBody();
            AliasMapBuilder aliasMapBuilder = new AliasMapBuilder(cteMap);
            plainSelect.getFromItem().accept((FromItemVisitor)aliasMapBuilder);
            if (plainSelect.getJoins() != null) {
                for (Join join : plainSelect.getJoins()) {
                    join.getRightItem().accept((FromItemVisitor)aliasMapBuilder);
                }
            }
            if (plainSelect.getWhere() != null) {
                VariableFinder variableFinder = new VariableFinder(aliasMapBuilder.getAliasMap(), cteMap);
                plainSelect.getWhere().accept((ExpressionVisitor)variableFinder);
                result.putAll(variableFinder.getVariableMap());
            }
        }
        return result;
    }

    private static class VariableFinder
    extends UserVariableFinder {
        private final Map<String, FromItem> aliasMap;
        private final Map<String, SubSelect> cteMap;
        private final Map<String, TableColumnInfo> variableMap = new HashMap<String, TableColumnInfo>();

        public VariableFinder(Map<String, FromItem> aliasMap, Map<String, SubSelect> cteMap) {
            this.aliasMap = aliasMap;
            this.cteMap = cteMap;
        }

        public Map<String, TableColumnInfo> getVariableMap() {
            return this.variableMap;
        }

        @Override
        public void visit(EqualsTo expr) {
            this.processComparison(expr.getLeftExpression(), expr.getRightExpression(), false);
        }

        @Override
        public void visit(NotEqualsTo expr) {
            this.processComparison(expr.getLeftExpression(), expr.getRightExpression(), false);
        }

        @Override
        public void visit(GreaterThan expr) {
            this.processComparison(expr.getLeftExpression(), expr.getRightExpression(), false);
        }

        @Override
        public void visit(GreaterThanEquals expr) {
            this.processComparison(expr.getLeftExpression(), expr.getRightExpression(), false);
        }

        @Override
        public void visit(MinorThan expr) {
            this.processComparison(expr.getLeftExpression(), expr.getRightExpression(), false);
        }

        @Override
        public void visit(MinorThanEquals expr) {
            this.processComparison(expr.getLeftExpression(), expr.getRightExpression(), false);
        }

        @Override
        public void visit(LikeExpression expr) {
            this.processComparison(expr.getLeftExpression(), expr.getRightExpression(), false);
        }

        @Override
        public void visit(InExpression expr) {
            this.processComparison(expr.getLeftExpression(), expr.getRightExpression(), true);
        }

        private void processComparison(Expression left, Expression right, boolean isIn) {
            List<String> userVarInExpression;
            if (right instanceof UserVariable) {
                if (left instanceof Column) {
                    Column column = (Column)left;
                    UserVariable variable = (UserVariable)right;
                    String tableAlias = column.getTable() != null ? column.getTable().getName().toLowerCase() : this.inferTableAlias();
                    String physicalTable = this.resolvePhysicalTable(tableAlias);
                    this.variableMap.put(variable.getName(), new TableColumnInfo(physicalTable, column.getColumnName(), isIn));
                }
            } else if (right instanceof SubSelect) {
                right.accept((ExpressionVisitor)this);
            } else if ((right instanceof Function || right instanceof Concat) && !CollectionUtils.isEmpty(userVarInExpression = this.getUserVariableList(right))) {
                this.processComparison(left, (Expression)new UserVariable(userVarInExpression.get(0)), false);
            }
        }

        private String inferTableAlias() {
            return this.aliasMap.size() == 1 ? this.aliasMap.keySet().iterator().next() : null;
        }

        private String resolvePhysicalTable(String alias) {
            if (this.aliasMap.containsKey(alias)) {
                FromItem fromItem = this.aliasMap.get(alias);
                if (fromItem instanceof Table) {
                    return ((Table)fromItem).getFullyQualifiedName();
                }
                if (fromItem instanceof SubSelect) {
                    List tables = new TablesNamesFinder().getTableList((Expression)((SubSelect)fromItem));
                    if (!tables.isEmpty()) {
                        return (String)tables.get(0);
                    }
                    return "unknown";
                }
            }
            if (this.cteMap.containsKey(alias)) {
                SubSelect cteSelect = this.cteMap.get(alias);
                List tables = new TablesNamesFinder().getTableList((Expression)cteSelect);
                return tables.isEmpty() ? "cte" : (String)tables.get(0);
            }
            return "unknown";
        }
    }

    private class AliasMapBuilder
    extends SelectDeParser {
        private Map<String, FromItem> aliasMap = new HashMap<String, FromItem>();
        private Map<String, SubSelect> cteMap;

        public AliasMapBuilder(Map<String, SubSelect> cteMap) {
            this.cteMap = cteMap;
        }

        public Map<String, FromItem> getAliasMap() {
            return this.aliasMap;
        }

        public void setAliasMap(Map<String, FromItem> aliasMap) {
            this.aliasMap = aliasMap;
        }

        public void visit(Table table) {
            String alias = table.getAlias() != null ? table.getAlias().getName().toLowerCase() : table.getName().toLowerCase();
            this.aliasMap.put(alias, (FromItem)table);
        }

        public void visit(SubSelect subSelect) {
            String alias = subSelect.getAlias().getName().toLowerCase();
            if (StringUtils.hasText((String)alias)) {
                this.aliasMap.put(alias, (FromItem)subSelect);
            }
            super.visit(subSelect);
        }

        public Map<String, SubSelect> getCteMap() {
            return this.cteMap;
        }

        public void setCteMap(Map<String, SubSelect> cteMap) {
            this.cteMap = cteMap;
        }
    }

    public static class TableColumnInfo {
        private final String tableName;
        private final String columnName;
        private final boolean isIn;

        public TableColumnInfo(String tableName, String columnName, boolean isIn) {
            this.tableName = tableName;
            this.columnName = columnName;
            this.isIn = isIn;
        }

        public String getTableName() {
            return this.tableName;
        }

        public String getColumnName() {
            return this.columnName;
        }

        public boolean isIn() {
            return this.isIn;
        }

        public String toString() {
            return this.tableName + "." + this.columnName;
        }
    }
}

