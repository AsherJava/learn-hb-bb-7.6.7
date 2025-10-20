/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.dynamic.DynamicNode
 *  com.jiuqi.bi.syntax.interpret.ISQLInfo
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.va.formula.intf.TableFieldNode
 */
package com.jiuqi.va.biz.ruler;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.dynamic.DynamicNode;
import com.jiuqi.bi.syntax.interpret.ISQLInfo;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.va.biz.impl.data.DataRowImpl;
import com.jiuqi.va.biz.impl.model.ModelContextImpl;
import com.jiuqi.va.biz.impl.model.ModelDataContext;
import com.jiuqi.va.biz.intf.data.DataRow;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.ruler.ModelFormulaHandle;
import com.jiuqi.va.biz.ruler.impl.ComputedPropDefineImpl;
import com.jiuqi.va.biz.ruler.intf.Formula;
import com.jiuqi.va.formula.intf.TableFieldNode;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ComputePropNode
extends DynamicNode
implements TableFieldNode {
    private static final Logger log = LoggerFactory.getLogger(ComputePropNode.class);
    private String tableName;
    private String fieldName;
    private UUID objectId;

    public ComputePropNode(Token token, UUID objectId) {
        super(token);
        this.objectId = objectId;
    }

    public ComputePropNode(Token token, UUID objectId, String tableName, String fieldName) {
        super(token);
        this.objectId = objectId;
        this.tableName = tableName;
        this.fieldName = fieldName;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public UUID getObjectId() {
        return this.objectId;
    }

    public int getType(IContext context) throws SyntaxException {
        ModelDataContext modelDataContext = (ModelDataContext)context;
        ComputedPropDefineImpl computedProp = (ComputedPropDefineImpl)modelDataContext.modelDefine.getPlugins().get("computedProp");
        Optional<Formula> first = computedProp.getFormulas().stream().filter(o -> this.objectId.equals(o.getId())).findFirst();
        if (!first.isPresent()) {
            Integer i = computedProp.getResultType().get(this.objectId);
            return this.getDateTimeType(modelDataContext, i);
        }
        Formula formula = first.get();
        if (computedProp.getResultType().containsKey(formula.getId())) {
            Integer i = computedProp.getResultType().get(formula.getId());
            return this.getDateTimeType(modelDataContext, i);
        }
        try {
            String expression = formula.getExpression();
            IExpression iExpression = ModelFormulaHandle.getInstance().parse(modelDataContext, expression, formula.getFormulaType());
            int i = iExpression.getType((IContext)modelDataContext);
            return this.getDateTimeType(modelDataContext, i);
        }
        catch (ParseException parseException) {
            log.error("\u89e3\u6790\u8ba1\u7b97\u5c5e\u6027\u516c\u5f0f\u5f02\u5e38" + parseException.getMessage(), parseException);
            throw new ParseException("\u89e3\u6790\u8ba1\u7b97\u5c5e\u6027\u516c\u5f0f\u5f02\u5e38:" + this.objectId);
        }
    }

    private int getDateTimeType(ModelDataContext modelDataContext, int i) {
        if (i != 0) {
            return i;
        }
        Model model = modelDataContext.model;
        if (model == null) {
            return 0;
        }
        ModelContextImpl modelContext = (ModelContextImpl)model.getContext();
        Object contextValue = modelContext.getContextValue("X--computeDateTimeFields");
        if (contextValue != null) {
            List fields = (List)contextValue;
            return fields.contains(this.fieldName) ? 2 : 0;
        }
        return 0;
    }

    public Object evaluate(IContext context) throws SyntaxException {
        ModelDataContext dataContext = (ModelDataContext)context;
        Object rowObject = dataContext.get(this.tableName);
        if (rowObject instanceof DataRow) {
            DataRowImpl dataRow = (DataRowImpl)rowObject;
            return dataContext.valueOf(dataRow.getValue(this.fieldName), this.getType(context));
        }
        if (rowObject instanceof Map) {
            Object value = ((Map)rowObject).get(this.fieldName);
            if (value == null) {
                return null;
            }
            return dataContext.valueOf(value, this.getType(context));
        }
        return null;
    }

    public void toString(StringBuilder buffer) {
        buffer.append(String.format("PROP_[%s]", this.fieldName));
    }

    protected void toSQL(IContext context, StringBuilder buffer, ISQLInfo info) {
    }
}

