/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.parse.IReportFunction
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.definition.common.SpringBeanProvider
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.data.engine.util.EntityQueryHelper
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.model.IEntityDefine
 */
package com.jiuqi.nr.function.func;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.parse.IReportFunction;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.definition.common.SpringBeanProvider;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.data.engine.util.EntityQueryHelper;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityDefine;
import java.util.List;

public class GetBDParent
extends Function
implements IReportFunction {
    private static final long serialVersionUID = -1025399880453412142L;

    public GetBDParent() {
        this.parameters().add(new Parameter("tableCode", 6, "\u4e3b\u4f53\u8868\u540d"));
        this.parameters().add(new Parameter("entityKey", 0, "\u4e3b\u4f53\u6570\u636e\u9879\u6807\u8bc6\uff0c\u53ef\u4ee5\u662fcode\u6216\u8005id"));
    }

    public String name() {
        return "GetBDParent";
    }

    public String title() {
        return "\u83b7\u53d6\u57fa\u7840\u6570\u636e\u7684\u4e0a\u7ea7\u4ee3\u7801";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 6;
    }

    public String category() {
        return "\u5176\u5b83\u51fd\u6570";
    }

    public int validate(IContext context, List<IASTNode> parameters) throws SyntaxException {
        try {
            String tableCode = (String)parameters.get(0).evaluate(context);
            EntityQueryHelper entityQueryHelper = (EntityQueryHelper)SpringBeanProvider.getBean(EntityQueryHelper.class);
            IEntityDefine entityDefine = entityQueryHelper.getEntityMetaService().queryEntityByCode(tableCode);
            if (entityDefine == null) {
                throw new SyntaxException("\u672a\u627e\u5230\u6807\u8bc6\u4e3a" + tableCode + "\u7684\u5b9e\u4f53\u5b9a\u4e49");
            }
        }
        catch (Exception e) {
            throw new SyntaxException("\u51fd\u6570" + this.name() + "\u53c2\u6570\u6821\u9a8c\u51fa\u9519\uff1a" + e.getMessage(), (Throwable)e);
        }
        return this.getResultType(context, parameters);
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        if (context instanceof QueryContext) {
            QueryContext qContext = (QueryContext)context;
            try {
                EntityQueryHelper entityQueryHelper;
                IEntityTable entityTable;
                IEntityRow entityRow;
                String tableCode = (String)parameters.get(0).evaluate(context);
                Object p1 = parameters.get(1).evaluate(context);
                if (p1 == null) {
                    return null;
                }
                String entityKey = p1.toString();
                String period = null;
                PeriodWrapper periodWrapper = qContext.getPeriodWrapper();
                if (periodWrapper != null) {
                    period = periodWrapper.toString();
                }
                if ((entityRow = (entityTable = (entityQueryHelper = (EntityQueryHelper)SpringBeanProvider.getBean(EntityQueryHelper.class)).queryEntityTreeByTableCode(qContext, tableCode, null, period)).findByEntityKey(entityKey)) == null) {
                    entityRow = entityTable.findByCode(entityKey);
                }
                if (entityRow != null) {
                    return entityRow.getParentEntityKey();
                }
            }
            catch (Exception e) {
                throw new SyntaxException(e.getMessage(), (Throwable)e);
            }
        }
        return null;
    }
}

