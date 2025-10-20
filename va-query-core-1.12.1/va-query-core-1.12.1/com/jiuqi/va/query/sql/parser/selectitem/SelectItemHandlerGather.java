/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.query.exception.DefinedQueryRuntimeException
 *  net.sf.jsqlparser.statement.select.SelectItem
 */
package com.jiuqi.va.query.sql.parser.selectitem;

import com.jiuqi.va.query.exception.DefinedQueryRuntimeException;
import com.jiuqi.va.query.sql.parser.common.ParserUtil;
import com.jiuqi.va.query.sql.parser.common.SqlParserHandlerCollection;
import com.jiuqi.va.query.sql.parser.selectitem.ISelectItemHandler;
import com.jiuqi.va.query.util.DCQuerySpringContextUtils;
import java.util.List;
import net.sf.jsqlparser.statement.select.SelectItem;
import org.springframework.stereotype.Component;

@Component
public class SelectItemHandlerGather {
    public SelectItem doParser(SelectItem selectItem, List<String> params) {
        if (selectItem == null) {
            return null;
        }
        if (params == null || params.isEmpty()) {
            return selectItem;
        }
        String exprSql = selectItem.toString();
        if (!ParserUtil.existParams(exprSql, params)) {
            return selectItem;
        }
        ISelectItemHandler selectItemHandler = DCQuerySpringContextUtils.getBean(SqlParserHandlerCollection.class).getSelectItemHandlerMap().get(selectItem.getClass());
        if (selectItemHandler == null) {
            throw new DefinedQueryRuntimeException(String.format("\u4e0d\u652f\u6301\u3010%1$s\u3011\u7c7b\u578b\u8868\u8fbe\u5f0f\u7684\u53c2\u6570\u89e3\u6790\uff01", selectItem.getClass()));
        }
        return selectItemHandler.doParser(selectItem, params);
    }
}

