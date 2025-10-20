/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.dynamic.DynamicNode
 *  com.jiuqi.bi.syntax.interpret.ISQLInfo
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.va.formula.intf.FieldNode
 */
package com.jiuqi.va.biz.ruler;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.dynamic.DynamicNode;
import com.jiuqi.bi.syntax.interpret.ISQLInfo;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.va.biz.impl.model.ModelDataContext;
import com.jiuqi.va.biz.intf.data.DataDefine;
import com.jiuqi.va.biz.intf.data.DataFieldDefine;
import com.jiuqi.va.biz.intf.data.DataTableDefine;
import com.jiuqi.va.biz.intf.value.Convert;
import com.jiuqi.va.biz.intf.value.ValueType;
import com.jiuqi.va.formula.intf.FieldNode;
import org.springframework.util.ObjectUtils;

public class ModelParamNode
extends DynamicNode
implements FieldNode {
    private static final long serialVersionUID = -7447628024145329366L;
    private int datatype;
    private String paramKey = "";

    public ModelParamNode(Token token, String paramKey) {
        super(token);
        this.paramKey = paramKey.toUpperCase();
    }

    public int getType(IContext context) throws SyntaxException {
        ModelDataContext ctx = (ModelDataContext)context;
        ValueType fieldValueType = ctx.getFieldValueType(this.paramKey);
        if (fieldValueType != null) {
            return ctx.getType(fieldValueType);
        }
        Object modelParamNodeType = ctx.get("model_param_node_type");
        if (modelParamNodeType != null && modelParamNodeType instanceof ValueType) {
            return ctx.getType((ValueType)((Object)modelParamNodeType));
        }
        Object tableName = ctx.get("model_param_node_tablename");
        if (ObjectUtils.isEmpty(tableName)) {
            return 0;
        }
        DataDefine data = (DataDefine)((Object)ctx.modelDefine.getPlugins().get("data"));
        DataTableDefine dataTableDefine = data.getTables().find((String)tableName);
        if (dataTableDefine == null) {
            return 0;
        }
        DataFieldDefine fieldDefine = dataTableDefine.getFields().find(this.paramKey);
        if (fieldDefine == null) {
            return 0;
        }
        return ctx.getType(fieldDefine.getValueType());
    }

    public Object evaluate(IContext context) throws SyntaxException {
        ModelDataContext cxt = Convert.cast(context, ModelDataContext.class);
        Object value = cxt.get(this.paramKey);
        return cxt.valueOf(value, this.getType(context));
    }

    public void toString(StringBuilder buffer) {
        buffer.append(String.format("[%s]", this.paramKey));
    }

    public void interpret(IContext context, StringBuilder buffer, Language lang, Object info) throws InterpretException {
        if (Language.FORMULA.equals((Object)lang)) {
            this.toString(buffer);
        } else {
            super.interpret(context, buffer, lang, info);
        }
    }

    public String getName() {
        return this.paramKey;
    }

    public void setDataType(int datatype) {
        this.datatype = datatype;
    }

    public int getDataType() {
        return this.datatype;
    }

    protected void toSQL(IContext context, StringBuilder buffer, ISQLInfo info) throws InterpretException {
        buffer.append('\"').append(this.paramKey).append('\"');
    }
}

