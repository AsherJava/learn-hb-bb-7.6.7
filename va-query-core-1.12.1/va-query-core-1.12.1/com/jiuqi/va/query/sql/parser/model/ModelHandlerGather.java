/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.query.exception.DefinedQuerySqlException
 *  net.sf.jsqlparser.Model
 */
package com.jiuqi.va.query.sql.parser.model;

import com.jiuqi.va.query.exception.DefinedQuerySqlException;
import com.jiuqi.va.query.sql.parser.common.ParserUtil;
import com.jiuqi.va.query.sql.parser.common.SqlParserHandlerCollection;
import com.jiuqi.va.query.sql.parser.model.IModelHandler;
import com.jiuqi.va.query.util.DCQuerySpringContextUtils;
import java.util.List;
import java.util.Map;
import net.sf.jsqlparser.Model;
import org.springframework.stereotype.Component;

@Component
public class ModelHandlerGather {
    private IModelHandler getModelHandler(Class<? extends Model> clazz) {
        Map<Class<? extends Model>, IModelHandler> modelHandlerMap = DCQuerySpringContextUtils.getBean(SqlParserHandlerCollection.class).getModelHandlerMap();
        for (Map.Entry<Class<? extends Model>, IModelHandler> clazzEntry : modelHandlerMap.entrySet()) {
            if (modelHandlerMap.containsKey(clazz)) {
                return modelHandlerMap.get(clazz);
            }
            if (!clazzEntry.getKey().isAssignableFrom(clazz)) continue;
            return clazzEntry.getValue();
        }
        return null;
    }

    public Model doParser(Model srcModel, List<String> params) {
        if (srcModel == null) {
            return null;
        }
        if (params == null || params.isEmpty()) {
            return srcModel;
        }
        String exprSql = srcModel.toString();
        if (!ParserUtil.existParams(exprSql, params)) {
            return srcModel;
        }
        IModelHandler modelHandler = this.getModelHandler(srcModel.getClass());
        if (modelHandler == null) {
            throw new DefinedQuerySqlException(String.format("\u4e0d\u652f\u6301\u3010%1$s\u3011\u6a21\u578b\u7684\u53c2\u6570\u89e3\u6790\uff01", srcModel.getClass()));
        }
        return modelHandler.doParser(srcModel, params);
    }
}

