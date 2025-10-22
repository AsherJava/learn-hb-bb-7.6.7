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
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDimensionProvider
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.node.CellDataNode
 *  com.jiuqi.np.dataengine.node.DynamicDataNode
 *  com.jiuqi.np.dataengine.node.VariableDataNode
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.definition.common.SpringBeanProvider
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.provider.DimensionRow
 *  com.jiuqi.np.definition.provider.DimensionTable
 *  com.jiuqi.np.logging.LogFactory
 *  com.jiuqi.np.logging.Logger
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.data.engine.util.EntityQueryHelper
 *  com.jiuqi.nr.definition.internal.env.ItemNode
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.function.func.InList
 */
package nr.single.para.parain.internal.service3.function;

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
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDimensionProvider;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.node.CellDataNode;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.node.VariableDataNode;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.definition.common.SpringBeanProvider;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.provider.DimensionRow;
import com.jiuqi.np.definition.provider.DimensionTable;
import com.jiuqi.np.logging.LogFactory;
import com.jiuqi.np.logging.Logger;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.data.engine.util.EntityQueryHelper;
import com.jiuqi.nr.definition.internal.env.ItemNode;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.function.func.InList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SingleInlist
extends Function {
    private static final Logger logger = LogFactory.getLogger(InList.class);
    private static final long serialVersionUID = 2696107757880730327L;

    public SingleInlist() {
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
            EntityCodeMatcher codeMatcher = (EntityCodeMatcher)qContext.getCache().get(cacheKey);
            if (codeMatcher == null) {
                EntityQueryHelper entityQueryHelper;
                IEntityTable entityTable;
                codeMatcher = new EntityCodeMatcher(itemName);
                String tableCode = itemName.startsWith("MD_") ? itemName.toUpperCase() : env.findEntityTableCode(itemName);
                String period = null;
                PeriodWrapper periodWrapper = qContext.getPeriodWrapper();
                if (periodWrapper != null) {
                    period = periodWrapper.toString();
                }
                if ((entityTable = (entityQueryHelper = (EntityQueryHelper)SpringBeanProvider.getBean(EntityQueryHelper.class)).queryEntityTreeByTableCode(qContext, tableCode, null, period)) == null) {
                    entityTable = entityQueryHelper.queryEntityTreeByTableCode(qContext, item.toUpperCase(), null, period);
                }
                List rows = entityTable.getAllRows();
                for (IEntityRow row : rows) {
                    codeMatcher.addCode(row.getCode());
                }
                qContext.getCache().put(cacheKey, codeMatcher);
            }
            return codeMatcher.match(text);
        }
        catch (Exception e) {
            throw new SyntaxException(e.getMessage(), (Throwable)e);
        }
    }

    @Deprecated
    private boolean judgeEntityOld(QueryContext qContext, String text, String item) throws SyntaxException {
        try {
            ExecutorContext exeContext = qContext.getExeContext();
            IFmlExecEnvironment env = exeContext.getEnv();
            String entityKey = env.findEntityTableCode(item.toUpperCase());
            IDimensionProvider dimensionProvider = exeContext.getCache().getDataDefinitionsCache().getDimensionProvider();
            String dimensionName = dimensionProvider.getDimensionNameByEntityTableCode(exeContext, entityKey);
            DimensionTable table = qContext.getDimTable(dimensionName);
            if (table == null) {
                table = qContext.getDimTable(dimensionProvider.getDimensionNameByEntityTableCode(exeContext, item.toUpperCase()));
            }
            if (table == null || table.rowCount() <= 0) {
                return text.startsWith(entityKey);
            }
            for (int i = 0; i < table.rowCount(); ++i) {
                DimensionRow row = table.getRow(i);
                if (!text.startsWith(row.getCode())) continue;
                return true;
            }
        }
        catch (Exception e) {
            throw new SyntaxException(e.getMessage(), (Throwable)e);
        }
        return false;
    }

    public int getResultType(IContext context, List<IASTNode> parameters) {
        return 1;
    }

    public String name() {
        return "InList";
    }

    public String title() {
        return "\u8fd4\u56de\u6587\u672c\u6216\u6570\u503c\u5728\u5217\u8868\u4e2d\u662f\u5426\u5b58\u5728";
    }

    public boolean isInfiniteParameter() {
        return true;
    }

    public boolean support(Language lang) {
        return lang != Language.EXCEL && lang != Language.SQL && lang != Language.JavaScript;
    }

    protected void toFormula(IContext context, List<IASTNode> parameters, StringBuilder buffer, Object info) throws InterpretException {
        DynamicDataNode dataNode;
        IASTNode p1 = parameters.get(0);
        IASTNode p2 = parameters.get(1);
        if (parameters.size() > 2 || p2 instanceof DataNode) {
            super.toFormula(context, parameters, buffer, info);
            return;
        }
        buffer.append(this.nameOf(Language.FORMULA, info)).append('(');
        p1.interpret(context, buffer, Language.FORMULA, info);
        buffer.append(',');
        String realP2 = "";
        QueryContext qContext = (QueryContext)context;
        if (p2 instanceof DynamicDataNode) {
            dataNode = (DynamicDataNode)p2;
            realP2 = this.getFieldCode(context, dataNode);
        } else if (p2 instanceof VariableDataNode) {
            VariableDataNode varNode = (VariableDataNode)p2;
            realP2 = varNode.toString();
        } else if (p2 instanceof CellDataNode) {
            dataNode = (DynamicDataNode)p2.getChild(0);
            realP2 = this.getFieldCode(context, dataNode);
        } else {
            try {
                realP2 = (String)p2.evaluate((IContext)qContext);
            }
            catch (SyntaxException e) {
                p2.interpret(context, buffer, Language.FORMULA, info);
                realP2 = "";
            }
        }
        ExecutorContext exeContext = qContext.getExeContext();
        IFmlExecEnvironment env = exeContext.getEnv();
        if (StringUtils.isNotEmpty((String)realP2)) {
            buffer.append(env.findEntityTableCode(realP2));
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

    private class EntityCodeMatcher {
        private final Set<String> codeCache = new HashSet<String>();
        private final List<Integer> codelengthList = new ArrayList<Integer>();
        private String entityName = null;
        private boolean sorted = false;

        public EntityCodeMatcher(String entityName) {
            this.entityName = entityName;
        }

        public void addCode(String code) {
            this.codeCache.add(code);
            int length = code.length();
            if (!this.codelengthList.contains(length)) {
                this.codelengthList.add(length);
            }
        }

        public boolean match(String text) {
            if (this.codeCache.isEmpty()) {
                return text.startsWith(this.entityName);
            }
            boolean result = this.codeCache.contains(text);
            if (result) {
                return result;
            }
            if (!this.sorted) {
                Collections.sort(this.codelengthList);
                this.sorted = true;
            }
            int textLength = text.length();
            for (int i = this.codelengthList.size() - 1; i >= 0; --i) {
                String subText;
                int length = this.codelengthList.get(i);
                if (length >= textLength || !(result = this.codeCache.contains(subText = text.substring(0, length)))) continue;
                return result;
            }
            return result;
        }
    }
}

