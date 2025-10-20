/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.dynamic.DynamicNode
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.va.formula.intf.FunRefDataDefine
 *  com.jiuqi.va.formula.intf.TableFieldNode
 */
package com.jiuqi.va.biz.ruler;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.dynamic.DynamicNode;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.va.biz.impl.model.ModelDataContext;
import com.jiuqi.va.biz.intf.data.DataRow;
import com.jiuqi.va.biz.utils.BizBindingI18nUtil;
import com.jiuqi.va.formula.intf.FunRefDataDefine;
import com.jiuqi.va.formula.intf.TableFieldNode;
import java.util.HashMap;

public class RefDataNode
extends DynamicNode
implements TableFieldNode {
    private static final long serialVersionUID = -5665493872756078755L;
    public final FunRefDataDefine refDataDefine;

    public RefDataNode(Token token, FunRefDataDefine refDataDefine) {
        super(token);
        this.refDataDefine = refDataDefine;
    }

    public int getType(IContext context) throws SyntaxException {
        return this.refDataDefine.getDataType();
    }

    public Object evaluate(IContext context) throws SyntaxException {
        ModelDataContext dataContext = (ModelDataContext)context;
        Object row = this.getRow(dataContext);
        if (row instanceof DataRow) {
            return ((DataRow)row).getString(this.refDataDefine.getFieldName().toUpperCase());
        }
        return ((HashMap)row).get(this.refDataDefine.getFieldName().toLowerCase());
    }

    public void toString(StringBuilder buffer) {
        buffer.append(String.format("%s[%s]", this.refDataDefine.getTableName(), this.refDataDefine.getFieldName()));
    }

    private Object getRow(ModelDataContext dataContext) {
        String tableName = this.refDataDefine.getTableName();
        if (dataContext.get(tableName) == null) {
            throw new RuntimeException(BizBindingI18nUtil.getMessage("va.bizbinding.refdatanode.currrowempty", new Object[]{this.toString()}));
        }
        return dataContext.get(tableName);
    }

    public String getTableName() {
        return this.refDataDefine.getTableName();
    }

    public String getFieldName() {
        return this.refDataDefine.getFieldName();
    }
}

