/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.data.DataNode
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.util.ArrayKey
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.node.CellDataNode
 *  com.jiuqi.np.dataengine.node.DynamicDataNode
 *  com.jiuqi.np.dataengine.node.VariableDataNode
 *  com.jiuqi.np.dataengine.parse.IReportFunction
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.definition.common.SpringBeanProvider
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.logging.LogFactory
 *  com.jiuqi.np.logging.Logger
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.data.engine.util.EntityQueryHelper
 *  com.jiuqi.nr.definition.internal.env.ItemNode
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 */
package com.jiuqi.nr.function.func;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.data.DataNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.util.ArrayKey;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.node.CellDataNode;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.node.VariableDataNode;
import com.jiuqi.np.dataengine.parse.IReportFunction;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.definition.common.SpringBeanProvider;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.logging.LogFactory;
import com.jiuqi.np.logging.Logger;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.data.engine.util.EntityQueryHelper;
import com.jiuqi.nr.definition.internal.env.ItemNode;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import java.util.HashSet;
import java.util.List;

public final class InList
extends Function
implements IReportFunction {
    private static final Logger logger = LogFactory.getLogger(InList.class);
    private static final long serialVersionUID = 2696107757880730327L;

    public InList() {
        this.parameters().add(new Parameter("text", 0, "\u5f85\u67e5\u627e\u7684\u5185\u5bb9"));
        this.parameters().add(new Parameter("items", 0, "\u67e5\u627e\u5217\u8868"));
    }

    public String category() {
        return "\u903b\u8f91\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        Object p0 = parameters.get(0).evaluate(context);
        if (p0 == null) {
            return null;
        }
        if (p0 instanceof String) {
            String text = (String)p0;
            if (parameters.size() == 2) {
                QueryContext qContext = (QueryContext)context;
                IASTNode p1 = parameters.get(1);
                String entityKey = (String)p1.evaluate((IContext)qContext);
                if (p1 instanceof ItemNode) {
                    entityKey = (String)parameters.get(1).evaluate((IContext)qContext);
                } else if (p1 instanceof VariableDataNode) {
                    VariableDataNode varNode = (VariableDataNode)p1;
                    entityKey = varNode.toString();
                } else if (p1 instanceof DynamicDataNode) {
                    DynamicDataNode dataNode = (DynamicDataNode)p1;
                    entityKey = dataNode.getQueryField().getFieldCode();
                } else if (p1 instanceof CellDataNode) {
                    DynamicDataNode dataNode = (DynamicDataNode)p1.getChild(0);
                    entityKey = dataNode.getQueryField().getFieldCode();
                } else {
                    Object str = p1.evaluate(context);
                    return text.startsWith((String)str);
                }
                return this.judgeEntity(qContext, text, entityKey);
            }
            for (int i = 1; i < parameters.size(); ++i) {
                Object p1 = parameters.get(i).evaluate(context);
                if (p1 == null || !text.startsWith((String)p1)) continue;
                return Boolean.TRUE;
            }
        } else if (p0 instanceof Number) {
            for (int i = 1; i < parameters.size(); ++i) {
                Object p1 = parameters.get(i).evaluate(context);
                if (p1 == null || DataType.compare((Number)((Number)p0), (Number)((Number)p1), (int)Integer.MIN_VALUE) != 0) continue;
                return Boolean.TRUE;
            }
        } else {
            throw new SyntaxException("InList()\u51fd\u6570\u7684\u7b2c\u4e00\u4e2a\u53c2\u6570\u5fc5\u987b\u4e3a\u5b57\u7b26\u4e32\u6216\u6570\u503c\u7c7b\u578b");
        }
        return Boolean.FALSE;
    }

    public boolean judgeEntity(QueryContext qContext, String text, String item) throws SyntaxException {
        try {
            IFmlExecEnvironment env = qContext.getExeContext().getEnv();
            String itemName = item.toUpperCase();
            ArrayKey cacheKey = new ArrayKey(new Object[]{this.name(), itemName});
            HashSet<String> codeCache = (HashSet<String>)qContext.getCache().get(cacheKey);
            if (codeCache == null) {
                EntityQueryHelper entityQueryHelper;
                IEntityTable entityTable;
                codeCache = new HashSet<String>();
                String tableCode = itemName.startsWith("MD_") ? itemName.toUpperCase() : env.findEntityTableCode(itemName);
                String period = null;
                PeriodWrapper periodWrapper = qContext.getPeriodWrapper();
                if (periodWrapper != null) {
                    period = periodWrapper.toString();
                }
                if ((entityTable = (entityQueryHelper = (EntityQueryHelper)SpringBeanProvider.getBean(EntityQueryHelper.class)).queryEntityTreeByTableCode(qContext, tableCode, null, period)) == null) {
                    entityTable = entityQueryHelper.queryEntityTreeByTableCode(qContext, item.toUpperCase(), null, period);
                }
                if (entityTable != null) {
                    List rows = entityTable.getAllRows();
                    for (IEntityRow row : rows) {
                        codeCache.add(row.getCode());
                    }
                }
                qContext.getCache().put(cacheKey, codeCache);
            }
            return codeCache.contains(text);
        }
        catch (Exception e) {
            throw new SyntaxException(e.getMessage(), (Throwable)e);
        }
    }

    public int getResultType(IContext context, List<IASTNode> parameters) {
        return 1;
    }

    public String name() {
        return "InList";
    }

    public String title() {
        return "\u5224\u65ad\u6307\u5b9a\u6587\u672c\u5b57\u7b26\u4e32\u662f\u5426\u4ee5\u5217\u8868\u4e2d\u7684\u67d0\u4e2a\u5b57\u7b26\u4e32\u4e3a\u5f00\u5934(\u5982\u679c\u5224\u65ad\u7684\u5217\u8868\u662f\u4e2a\u679a\u4e3e\u5219\u9700\u8981\u5b8c\u5168\u5339\u914d\u679a\u4e3e\u4ee3\u7801)";
    }

    protected void toExplain(IContext context, List<IASTNode> parameters, StringBuilder buffer, Object info) throws InterpretException {
        parameters.get(0).interpret(context, buffer, Language.EXPLAIN, info);
        buffer.append("\u5728");
        for (int i = 1; i < parameters.size(); ++i) {
            parameters.get(i).interpret(context, buffer, Language.EXPLAIN, info);
            buffer.append(",");
        }
        buffer.setLength(buffer.length() - 1);
        buffer.append("\u5217\u8868\u4e2d");
    }

    public boolean isInfiniteParameter() {
        return true;
    }

    public boolean support(Language lang) {
        return lang != Language.EXCEL && lang != Language.SQL && lang != Language.JavaScript;
    }

    protected void toFormula(IContext context, List<IASTNode> parameters, StringBuilder buffer, Object info) throws InterpretException {
        IASTNode p1 = parameters.get(0);
        IASTNode p2 = parameters.get(1);
        if (parameters.size() > 2 || p2 instanceof DataNode) {
            super.toFormula(context, parameters, buffer, info);
            return;
        }
        buffer.append(this.nameOf(Language.FORMULA, info)).append('(');
        p1.interpret(context, buffer, Language.FORMULA, info);
        buffer.append(',');
        if (p2 instanceof DynamicDataNode) {
            DynamicDataNode dataNode = (DynamicDataNode)p2;
            String fieldCode = this.getFieldCode(context, dataNode);
            buffer.append(fieldCode);
        } else if (p2 instanceof VariableDataNode) {
            VariableDataNode varNode = (VariableDataNode)p2;
            buffer.append(varNode.toString());
        } else if (p2 instanceof CellDataNode) {
            DynamicDataNode dataNode = (DynamicDataNode)p2.getChild(0);
            String fieldCode = this.getFieldCode(context, dataNode);
            buffer.append(fieldCode);
        } else {
            p2.interpret(context, buffer, Language.FORMULA, info);
        }
        buffer.append(')');
    }

    protected String getFieldCode(IContext context, DynamicDataNode dataNode) {
        QueryContext qContext = (QueryContext)context;
        String fieldCode = dataNode.getQueryField().getFieldCode();
        FieldDefine field = null;
        if (dataNode.getDataLink() != null) {
            field = dataNode.getDataLink().getField();
        } else {
            try {
                field = qContext.getExeContext().getCache().getDataModelDefinitionsCache().getColumnModelFinder().findFieldDefineByColumnId(dataNode.getQueryField().getUID());
            }
            catch (ParseException e) {
                logger.error(e.getMessage(), (Throwable)e);
            }
        }
        if (field != null && StringUtils.isNotEmpty((String)field.getAlias())) {
            fieldCode = field.getAlias();
        }
        return fieldCode;
    }
}

