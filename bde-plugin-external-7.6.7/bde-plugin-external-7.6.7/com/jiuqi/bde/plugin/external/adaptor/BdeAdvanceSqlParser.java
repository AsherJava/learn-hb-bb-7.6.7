/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.dc.mappingscheme.impl.common.Column
 *  com.jiuqi.dc.mappingscheme.impl.util.AdvanceSqlParser
 *  net.sf.jsqlparser.JSQLParserException
 *  net.sf.jsqlparser.expression.Alias
 *  net.sf.jsqlparser.parser.CCJSqlParserUtil
 *  net.sf.jsqlparser.schema.Column
 *  net.sf.jsqlparser.statement.Statement
 *  net.sf.jsqlparser.statement.select.PlainSelect
 *  net.sf.jsqlparser.statement.select.Select
 *  net.sf.jsqlparser.statement.select.SelectExpressionItem
 */
package com.jiuqi.bde.plugin.external.adaptor;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.dc.mappingscheme.impl.common.Column;
import com.jiuqi.dc.mappingscheme.impl.util.AdvanceSqlParser;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class BdeAdvanceSqlParser
extends AdvanceSqlParser {
    public List<Column> parseSql(String dataSourceCode, String sql) {
        try {
            return super.parseSql(dataSourceCode, sql);
        }
        catch (Exception e) {
            return this.parseColumnBySql(sql);
        }
    }

    private List<Column> parseColumnBySql(String sql) {
        try {
            Statement parse = CCJSqlParserUtil.parse((String)sql);
            Select select = (Select)parse;
            PlainSelect plainSelect = (PlainSelect)select.getSelectBody();
            List selectItems = plainSelect.getSelectItems();
            if (CollectionUtils.isEmpty((Collection)selectItems)) {
                return CollectionUtils.newArrayList();
            }
            return selectItems.stream().map(item -> {
                if (!(item instanceof SelectExpressionItem)) {
                    throw new BusinessRuntimeException("\u89e3\u6790\u7ed3\u679c\u5217\u5931\u8d25\uff1a\u672a\u80fd\u89e3\u6790\u5b57\u6bb5\u3010" + item + "\u3011");
                }
                SelectExpressionItem expressionItem = (SelectExpressionItem)item;
                String columnName = this.parseColumnName(expressionItem);
                Column column = new Column();
                column.setName(columnName);
                column.setTitle(columnName);
                return column;
            }).collect(Collectors.toList());
        }
        catch (JSQLParserException e) {
            if (e.getMessage().contains("Was")) {
                throw new BusinessRuntimeException(String.format("SQL\u3010%1$s\u3011\u683c\u5f0f\u4e0d\u6b63\u786e\uff0c\u8bf7\u68c0\u67e5:", sql) + e.getMessage().substring(0, e.getMessage().indexOf("Was")), (Throwable)e);
            }
            throw new BusinessRuntimeException(String.format("SQL\u3010%1$s\u3011\u683c\u5f0f\u4e0d\u6b63\u786e\uff0c\u8bf7\u68c0\u67e5:", sql) + e.getMessage(), (Throwable)e);
        }
    }

    private String parseColumnName(SelectExpressionItem expressionItem) {
        Alias alias = expressionItem.getAlias();
        if (alias != null) {
            return alias.getName().contains("\"") ? alias.getName().replace("\"", "") : alias.getName();
        }
        if (expressionItem.getExpression() instanceof Column) {
            net.sf.jsqlparser.schema.Column expression = (net.sf.jsqlparser.schema.Column)expressionItem.getExpression();
            return expression.getColumnName();
        }
        return expressionItem.getExpression().toString();
    }
}

