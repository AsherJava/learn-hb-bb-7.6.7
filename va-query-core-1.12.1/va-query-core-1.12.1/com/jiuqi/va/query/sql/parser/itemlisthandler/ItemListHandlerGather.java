/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.query.exception.DefinedQuerySqlException
 *  net.sf.jsqlparser.expression.operators.relational.ItemsList
 */
package com.jiuqi.va.query.sql.parser.itemlisthandler;

import com.jiuqi.va.query.exception.DefinedQuerySqlException;
import com.jiuqi.va.query.sql.parser.common.ParserUtil;
import com.jiuqi.va.query.sql.parser.common.SqlParserHandlerCollection;
import com.jiuqi.va.query.sql.parser.itemlisthandler.IItemListHandler;
import com.jiuqi.va.query.util.DCQuerySpringContextUtils;
import java.util.List;
import net.sf.jsqlparser.expression.operators.relational.ItemsList;
import org.springframework.stereotype.Component;

@Component
public class ItemListHandlerGather {
    public ItemsList doParser(ItemsList itemsList, List<String> params) {
        if (itemsList == null) {
            return null;
        }
        if (params == null || params.isEmpty()) {
            return itemsList;
        }
        String exprSql = itemsList.toString();
        if (!ParserUtil.existParams(exprSql, params)) {
            return itemsList;
        }
        IItemListHandler itemListHandler = DCQuerySpringContextUtils.getBean(SqlParserHandlerCollection.class).getItemListHandlerMap().get(itemsList.getClass());
        if (itemListHandler == null) {
            throw new DefinedQuerySqlException(String.format("\u4e0d\u652f\u6301\u3010%1$s\u3011\u7c7b\u578b\u8868\u8fbe\u5f0f\u7684\u53c2\u6570\u89e3\u6790\uff01", itemsList.getClass()));
        }
        return itemListHandler.doParser(itemsList, params);
    }
}

