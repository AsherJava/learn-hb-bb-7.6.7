/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.query.template.vo.TemplateFieldSettingVO
 *  net.sf.jsqlparser.JSQLParserException
 *  net.sf.jsqlparser.expression.Alias
 *  net.sf.jsqlparser.expression.Expression
 *  net.sf.jsqlparser.parser.CCJSqlParserUtil
 *  net.sf.jsqlparser.schema.Column
 *  net.sf.jsqlparser.schema.Table
 *  net.sf.jsqlparser.statement.select.AllColumns
 *  net.sf.jsqlparser.statement.select.AllTableColumns
 *  net.sf.jsqlparser.statement.select.FromItem
 *  net.sf.jsqlparser.statement.select.Join
 *  net.sf.jsqlparser.statement.select.PlainSelect
 *  net.sf.jsqlparser.statement.select.Select
 *  net.sf.jsqlparser.statement.select.SelectExpressionItem
 *  net.sf.jsqlparser.statement.select.SelectItem
 *  net.sf.jsqlparser.statement.select.SelectItemVisitor
 *  net.sf.jsqlparser.statement.select.SelectItemVisitorAdapter
 *  net.sf.jsqlparser.statement.select.SelectVisitor
 *  net.sf.jsqlparser.statement.select.SelectVisitorAdapter
 *  net.sf.jsqlparser.statement.select.SetOperationList
 *  net.sf.jsqlparser.statement.select.SubSelect
 *  net.sf.jsqlparser.statement.select.WithItem
 *  org.apache.commons.collections4.map.CaseInsensitiveMap
 */
package com.jiuqi.va.query.sql.parser;

import com.jiuqi.va.query.template.vo.TemplateFieldSettingVO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.AllColumns;
import net.sf.jsqlparser.statement.select.AllTableColumns;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.statement.select.SelectItemVisitor;
import net.sf.jsqlparser.statement.select.SelectItemVisitorAdapter;
import net.sf.jsqlparser.statement.select.SelectVisitor;
import net.sf.jsqlparser.statement.select.SelectVisitorAdapter;
import net.sf.jsqlparser.statement.select.SetOperationList;
import net.sf.jsqlparser.statement.select.SubSelect;
import net.sf.jsqlparser.statement.select.WithItem;
import org.apache.commons.collections4.map.CaseInsensitiveMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SqlColumnParser {
    private static final Logger logger = LoggerFactory.getLogger(SqlColumnParser.class);
    private Map<String, SubSelect> cteMap = new HashMap<String, SubSelect>();

    public Map<String, SelectColumn> parse(String sql, List<TemplateFieldSettingVO> columns) throws JSQLParserException {
        Map<String, SelectColumn> result;
        Select select = (Select)CCJSqlParserUtil.parse((String)sql);
        final AtomicBoolean hasAllColumn = new AtomicBoolean(false);
        select.getSelectBody().accept((SelectVisitor)new SelectVisitorAdapter(){

            public void visit(PlainSelect plainSelect) {
                List selectItems = plainSelect.getSelectItems();
                for (SelectItem item : selectItems) {
                    if (item instanceof SelectExpressionItem) continue;
                    hasAllColumn.set(true);
                    break;
                }
            }

            public void visit(SetOperationList setOperationList) {
                setOperationList.getSelects().forEach(selectBody -> selectBody.accept((SelectVisitor)this));
            }
        });
        if (hasAllColumn.get()) {
            StringBuilder newSql = new StringBuilder("select ");
            columns.forEach(column -> newSql.append(" parser_column.").append(column.getName()).append(","));
            newSql.deleteCharAt(newSql.length() - 1).append(" from (").append(select).append(") parser_column");
            result = this.parse(newSql.toString());
        } else {
            result = this.parse(sql);
        }
        return result;
    }

    public Map<String, SelectColumn> parse(String sql) throws JSQLParserException {
        Select select = (Select)CCJSqlParserUtil.parse((String)sql);
        PlainSelect mainSelect = null;
        mainSelect = select.getSelectBody() instanceof SetOperationList ? (PlainSelect)((SetOperationList)select.getSelectBody()).getSelects().get(0) : (PlainSelect)select.getSelectBody();
        HashMap<String, SubSelect> withMap = new HashMap<String, SubSelect>();
        if (select.getWithItemsList() != null) {
            for (WithItem withItem : select.getWithItemsList()) {
                withMap.put(withItem.getName(), withItem.getSubSelect());
            }
        }
        this.cteMap = withMap;
        Map<String, FromItem> aliasToFromItem = this.buildAliasToFromItemMap(mainSelect);
        return this.processSelectItems(mainSelect.getSelectItems(), aliasToFromItem);
    }

    private Map<String, FromItem> buildAliasToFromItemMap(PlainSelect select) {
        List joins;
        HashMap<String, FromItem> aliasMap = new HashMap<String, FromItem>();
        FromItem fromItem = select.getFromItem();
        if (fromItem != null) {
            String alias = fromItem.getAlias() != null ? fromItem.getAlias().getName() : this.getDefaultAlias(fromItem);
            aliasMap.put(alias, fromItem);
        }
        if ((joins = select.getJoins()) != null) {
            for (Join join : joins) {
                FromItem joinFromItem = join.getRightItem();
                if (joinFromItem == null) continue;
                String joinAlias = joinFromItem.getAlias() != null ? joinFromItem.getAlias().getName() : this.getDefaultAlias(joinFromItem);
                aliasMap.put(joinAlias, joinFromItem);
            }
        }
        aliasMap.putAll(this.cteMap);
        return aliasMap;
    }

    private String getDefaultAlias(FromItem fromItem) {
        if (fromItem instanceof Table) {
            return ((Table)fromItem).getName();
        }
        return "subquery";
    }

    private Map<String, SelectColumn> processSelectItems(List<SelectItem> selectItems, final Map<String, FromItem> aliasToFromItem) {
        CaseInsensitiveMap result = new CaseInsensitiveMap();
        for (SelectItem item : selectItems) {
            item.accept((SelectItemVisitor)new SelectItemVisitorAdapter((Map)result){
                final /* synthetic */ Map val$result;
                {
                    this.val$result = map2;
                }

                public void visit(SelectExpressionItem item) {
                    Expression expr = item.getExpression();
                    if (expr instanceof Column) {
                        Column column = (Column)expr;
                        String columnName = column.getColumnName();
                        String tableAlias = column.getTable() != null ? column.getTable().getName() : SqlColumnParser.this.inferTableAlias(aliasToFromItem);
                        FromItem sourceFromItem = tableAlias != null ? (FromItem)aliasToFromItem.get(tableAlias) : null;
                        ColumnInfo columnInfo = SqlColumnParser.this.resolveColumnInfo(column, sourceFromItem);
                        if (columnInfo != null) {
                            Alias alias = item.getAlias();
                            boolean useAs = alias != null && alias.isUseAs();
                            String realName = SqlColumnParser.extractRealName(useAs, alias, columnName);
                            SelectColumn selectColumn = new SelectColumn(columnInfo.getTableName() + "." + columnInfo.getColumnName(), useAs, realName);
                            this.val$result.put(realName, selectColumn);
                        }
                    }
                }
            });
        }
        return result;
    }

    private static String extractRealName(boolean useAs, Alias alias, String columnName) {
        String aliasName;
        String realName = useAs ? ((aliasName = alias.getName()).charAt(0) == '\"' && aliasName.charAt(aliasName.length() - 1) == '\"' ? aliasName.substring(1, aliasName.length() - 1) : aliasName) : columnName;
        return realName;
    }

    private String inferTableAlias(Map<String, FromItem> aliasMap) {
        return aliasMap.size() == 1 ? aliasMap.keySet().iterator().next() : null;
    }

    private ColumnInfo resolveColumnInfo(Column column, FromItem fromItem) {
        if (fromItem instanceof Table) {
            return new ColumnInfo(((Table)fromItem).getName(), column.getColumnName());
        }
        if (fromItem instanceof SubSelect) {
            return this.resolveColumnInSubSelect(column, (SubSelect)fromItem);
        }
        return null;
    }

    private ColumnInfo resolveColumnInSubSelect(Column column, SubSelect subSelect) {
        try {
            PlainSelect subQuery = null;
            subQuery = subSelect.getSelectBody() instanceof SetOperationList ? (PlainSelect)((SetOperationList)subSelect.getSelectBody()).getSelects().get(0) : (PlainSelect)subSelect.getSelectBody();
            Map<String, FromItem> subAliasMap = this.buildAliasToFromItemMap(subQuery);
            for (SelectItem item : subQuery.getSelectItems()) {
                if (item instanceof SelectExpressionItem) {
                    String columnName;
                    SelectExpressionItem sei = (SelectExpressionItem)item;
                    String alias = sei.getAlias() != null ? sei.getAlias().getName() : null;
                    String string = columnName = sei.getExpression() instanceof Column ? ((Column)sei.getExpression()).getColumnName() : null;
                    if (!column.getColumnName().equalsIgnoreCase(alias) && !column.getColumnName().equalsIgnoreCase(columnName) || !(sei.getExpression() instanceof Column)) continue;
                    Column subColumn = (Column)sei.getExpression();
                    String subTableAlias = subColumn.getTable() != null ? subColumn.getTable().getName() : this.inferTableAlias(subAliasMap);
                    FromItem subSource = subTableAlias != null ? subAliasMap.get(subTableAlias) : null;
                    return this.resolveColumnInfo(subColumn, subSource);
                }
                if (item instanceof AllColumns) {
                    return this.resolveColumnInfo(column, subQuery.getFromItem());
                }
                if (!(item instanceof AllTableColumns)) continue;
                return this.resolveColumnInfo(column, subQuery.getFromItem());
            }
        }
        catch (Exception e) {
            logger.error("\u89e3\u6790\u5217\u4fe1\u606f\u51fa\u9519", e);
        }
        return null;
    }

    public static class SelectColumn {
        private final String field;
        private final Boolean useAs;
        private final String realName;

        public SelectColumn(String field, Boolean useAs, String realName) {
            this.field = field;
            this.useAs = useAs;
            this.realName = realName;
        }

        public String getField() {
            return this.field;
        }

        public Boolean getUseAs() {
            return this.useAs;
        }

        public String getRealName() {
            return this.realName;
        }
    }

    private static class ColumnInfo {
        private final String tableName;
        private final String columnName;

        public ColumnInfo(String tableName, String columnName) {
            this.tableName = tableName;
            this.columnName = columnName;
        }

        public String getTableName() {
            return this.tableName;
        }

        public String getColumnName() {
            return this.columnName;
        }
    }
}

