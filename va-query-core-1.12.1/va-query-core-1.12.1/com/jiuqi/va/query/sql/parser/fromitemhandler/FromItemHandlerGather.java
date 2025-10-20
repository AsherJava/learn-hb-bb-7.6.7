/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.query.exception.DefinedQuerySqlException
 *  net.sf.jsqlparser.Model
 *  net.sf.jsqlparser.statement.select.FromItem
 */
package com.jiuqi.va.query.sql.parser.fromitemhandler;

import com.jiuqi.va.query.exception.DefinedQuerySqlException;
import com.jiuqi.va.query.sql.parser.common.ParserUtil;
import com.jiuqi.va.query.sql.parser.common.SqlParserHandlerCollection;
import com.jiuqi.va.query.sql.parser.fromitemhandler.IFromItemHandler;
import com.jiuqi.va.query.util.DCQuerySpringContextUtils;
import java.util.List;
import net.sf.jsqlparser.Model;
import net.sf.jsqlparser.statement.select.FromItem;
import org.springframework.stereotype.Component;

@Component
public class FromItemHandlerGather {
    public Model doParser(FromItem fromItem, List<String> params) {
        if (fromItem == null) {
            return null;
        }
        if (params == null || params.isEmpty()) {
            return fromItem;
        }
        String exprSql = fromItem.toString();
        if (!ParserUtil.existParams(exprSql, params)) {
            return fromItem;
        }
        IFromItemHandler fromItemHandler = DCQuerySpringContextUtils.getBean(SqlParserHandlerCollection.class).getFromItemHandlerMap().get(fromItem.getClass());
        if (fromItemHandler == null) {
            throw new DefinedQuerySqlException(String.format("\u4e0d\u652f\u6301\u3010%1$s\u3011\u7c7b\u578b\u8868\u8fbe\u5f0f\u7684\u53c2\u6570\u89e3\u6790\uff01", fromItem.getClass()));
        }
        return fromItemHandler.doParser((Model)fromItem, params);
    }
}

