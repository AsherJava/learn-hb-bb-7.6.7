/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.sf.jsqlparser.Model
 *  net.sf.jsqlparser.expression.Expression
 *  net.sf.jsqlparser.expression.operators.relational.ItemsList
 *  net.sf.jsqlparser.statement.select.SelectItem
 */
package com.jiuqi.va.query.sql.parser.common;

import com.jiuqi.va.query.sql.parser.exprhandler.IExpressionHandler;
import com.jiuqi.va.query.sql.parser.fromitemhandler.IFromItemHandler;
import com.jiuqi.va.query.sql.parser.itemlisthandler.IItemListHandler;
import com.jiuqi.va.query.sql.parser.model.IModelHandler;
import com.jiuqi.va.query.sql.parser.selectitem.ISelectItemHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.jsqlparser.Model;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.relational.ItemsList;
import net.sf.jsqlparser.statement.select.SelectItem;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SqlParserHandlerCollection
implements InitializingBean {
    @Autowired(required=false)
    private List<IModelHandler> modelHandlers;
    @Autowired(required=false)
    private List<IExpressionHandler> expressionHandlers;
    @Autowired(required=false)
    private List<IFromItemHandler> fromItemHandlers;
    @Autowired(required=false)
    private List<IItemListHandler> itemListHandlers;
    @Autowired(required=false)
    private List<ISelectItemHandler> selectItemHandlers;
    private static final Map<Class<? extends Model>, IModelHandler> MODEL_HANDLER_MAP = new HashMap<Class<? extends Model>, IModelHandler>();
    private static final Map<Class<? extends Expression>, IExpressionHandler> EXPRESSION_HANDLER_MAP = new HashMap<Class<? extends Expression>, IExpressionHandler>();
    private static final Map<Class<? extends Model>, IFromItemHandler> FROM_ITEM_HANDLER_MAP = new HashMap<Class<? extends Model>, IFromItemHandler>();
    private static final Map<Class<? extends ItemsList>, IItemListHandler> ITEM_LIST_HANDLER_HASH_MAP = new HashMap<Class<? extends ItemsList>, IItemListHandler>();
    private static final Map<Class<? extends SelectItem>, ISelectItemHandler> SELECT_ITEM_HANDLER_MAP = new HashMap<Class<? extends SelectItem>, ISelectItemHandler>();

    @Override
    public void afterPropertiesSet() {
        this.init();
    }

    public Map<Class<? extends Model>, IModelHandler> getModelHandlerMap() {
        return MODEL_HANDLER_MAP;
    }

    public Map<Class<? extends Expression>, IExpressionHandler> getExpressionHandlerMap() {
        return EXPRESSION_HANDLER_MAP;
    }

    public Map<Class<? extends Model>, IFromItemHandler> getFromItemHandlerMap() {
        return FROM_ITEM_HANDLER_MAP;
    }

    public Map<Class<? extends ItemsList>, IItemListHandler> getItemListHandlerMap() {
        return ITEM_LIST_HANDLER_HASH_MAP;
    }

    public Map<Class<? extends SelectItem>, ISelectItemHandler> getSelectItemHandlerMap() {
        return SELECT_ITEM_HANDLER_MAP;
    }

    private void init() {
        this.modelHandlers.forEach(modelHandler -> {
            Class<? extends Model> clazzType = modelHandler.getClazzType();
            if (clazzType != null) {
                MODEL_HANDLER_MAP.put(clazzType, (IModelHandler)modelHandler);
            }
        });
        this.expressionHandlers.forEach(expressionHandler -> {
            Class<? extends Expression> clazzType = expressionHandler.getClazzType();
            if (clazzType != null) {
                EXPRESSION_HANDLER_MAP.put(clazzType, (IExpressionHandler)expressionHandler);
            }
        });
        this.fromItemHandlers.forEach(fromItemHandler -> {
            Class<? extends Model> clazzType = fromItemHandler.getClazzType();
            if (clazzType != null) {
                FROM_ITEM_HANDLER_MAP.put(clazzType, (IFromItemHandler)fromItemHandler);
            }
        });
        this.itemListHandlers.forEach(itemListHandler -> {
            Class<? extends ItemsList> clazzType = itemListHandler.getClazzType();
            if (clazzType != null) {
                ITEM_LIST_HANDLER_HASH_MAP.put(clazzType, (IItemListHandler)itemListHandler);
            }
        });
        this.selectItemHandlers.forEach(selectItemHandler -> {
            Class<? extends SelectItem> clazzType = selectItemHandler.getClazzType();
            if (clazzType != null) {
                SELECT_ITEM_HANDLER_MAP.put(clazzType, (ISelectItemHandler)selectItemHandler);
            }
        });
    }
}

