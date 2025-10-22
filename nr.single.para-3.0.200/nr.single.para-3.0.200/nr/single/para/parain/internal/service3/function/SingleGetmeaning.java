/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.data.DataNode
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.node.CellDataNode
 *  com.jiuqi.np.dataengine.node.DynamicDataNode
 *  com.jiuqi.np.dataengine.node.VariableDataNode
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.definition.common.SpringBeanProvider
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.logging.LogFactory
 *  com.jiuqi.np.logging.Logger
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.data.engine.util.EntityQueryHelper
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.function.func.getMeaning
 */
package nr.single.para.parain.internal.service3.function;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.data.DataNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.node.CellDataNode;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.node.VariableDataNode;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.definition.common.SpringBeanProvider;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.logging.LogFactory;
import com.jiuqi.np.logging.Logger;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.data.engine.util.EntityQueryHelper;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.function.func.getMeaning;
import java.util.List;

public class SingleGetmeaning
extends Function {
    private static final Logger logger = LogFactory.getLogger(getMeaning.class);
    private static final long serialVersionUID = 4977981972332780857L;

    public SingleGetmeaning() {
        this.parameters().add(new Parameter("code", 6, "\u679a\u4e3e\u9879\u4ee3\u7801"));
        this.parameters().add(new Parameter("basedata", 0, "\u679a\u4e3e\u6807\u8bc6"));
    }

    public String category() {
        return "\u57fa\u7840\u6570\u636e\u6269\u5c55\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        Object p0 = parameters.get(0).evaluate(context);
        if (p0 == null) {
            return null;
        }
        if (p0 instanceof String) {
            QueryContext qContext = (QueryContext)context;
            String code = (String)p0;
            IASTNode p1 = parameters.get(1);
            String entityKey = (String)p1.evaluate((IContext)qContext);
            if (p1 instanceof VariableDataNode) {
                VariableDataNode varNode = (VariableDataNode)p1;
                entityKey = varNode.toString();
            } else if (p1 instanceof DynamicDataNode) {
                DynamicDataNode dataNode = (DynamicDataNode)p1;
                entityKey = dataNode.getQueryField().getFieldCode();
            } else if (p1 instanceof CellDataNode) {
                DynamicDataNode dataNode = (DynamicDataNode)p1.getChild(0);
                entityKey = dataNode.getQueryField().getFieldCode();
            }
            return this.getEntityItemTitle(qContext, code, entityKey);
        }
        throw new SyntaxException("GETMEANING()\u51fd\u6570\u7684\u7b2c\u4e00\u4e2a\u53c2\u6570\u5fc5\u987b\u4e3a\u5b57\u7b26\u4e32\u6216\u6570\u503c\u7c7b\u578b");
    }

    public String getEntityItemTitle(QueryContext qContext, String code, String item) throws SyntaxException {
        try {
            IEntityRow row;
            EntityQueryHelper entityQueryHelper;
            IEntityTable entityTable;
            ExecutorContext exeContext = qContext.getExeContext();
            IFmlExecEnvironment env = exeContext.getEnv();
            String tableCode = item.startsWith("MD_") ? item.toUpperCase() : env.findEntityTableCode(item);
            String period = null;
            PeriodWrapper periodWrapper = qContext.getPeriodWrapper();
            if (periodWrapper != null) {
                period = periodWrapper.toString();
            }
            if ((entityTable = (entityQueryHelper = (EntityQueryHelper)SpringBeanProvider.getBean(EntityQueryHelper.class)).queryEntityTreeByTableCode(qContext, tableCode, null, period)) == null) {
                entityTable = entityQueryHelper.queryEntityTreeByTableCode(qContext, item.toUpperCase(), null, period);
            }
            if (entityTable != null && (row = entityTable.findByCode(code)) != null) {
                return row.getTitle();
            }
        }
        catch (Exception e) {
            throw new SyntaxException(e.getMessage(), (Throwable)e);
        }
        return null;
    }

    public int getResultType(IContext context, List<IASTNode> parameters) {
        return 6;
    }

    public String name() {
        return "GETMEANING";
    }

    public String title() {
        return "\u6839\u636e\u679a\u4e3e\u4ee3\u7801\u8fd4\u56de\u679a\u4e3e\u542b\u4e49";
    }

    public boolean isInfiniteParameter() {
        return true;
    }

    public boolean support(Language lang) {
        return lang != Language.EXCEL && lang != Language.SQL && lang != Language.JavaScript;
    }

    protected void toFormula(IContext context, List<IASTNode> parameters, StringBuilder buffer, Object info) throws InterpretException {
        QueryContext qContext;
        DynamicDataNode dataNode;
        IASTNode p1 = parameters.get(0);
        IASTNode p2 = parameters.get(1);
        if (p2 instanceof DataNode) {
            super.toFormula(context, parameters, buffer, info);
            return;
        }
        buffer.append(this.nameOf(Language.FORMULA, info)).append('(');
        p1.interpret(context, buffer, Language.FORMULA, info);
        buffer.append(',');
        String tableCode = "";
        if (p2 instanceof DynamicDataNode) {
            dataNode = (DynamicDataNode)p2;
            tableCode = this.getFieldCode(context, dataNode);
        } else if (p2 instanceof VariableDataNode) {
            VariableDataNode varNode = (VariableDataNode)p2;
            tableCode = varNode.toString();
        } else if (p2 instanceof CellDataNode) {
            dataNode = (DynamicDataNode)p2.getChild(0);
            tableCode = this.getFieldCode(context, dataNode);
        } else {
            qContext = (QueryContext)context;
            try {
                tableCode = (String)p2.evaluate((IContext)qContext);
            }
            catch (SyntaxException e) {
                p2.interpret(context, buffer, Language.FORMULA, info);
            }
        }
        qContext = (QueryContext)context;
        ExecutorContext exeContext = qContext.getExeContext();
        IFmlExecEnvironment env = exeContext.getEnv();
        tableCode = env.findEntityTableCode(tableCode);
        buffer.append(tableCode).append(')');
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

