/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.common.QueryField
 *  com.jiuqi.np.dataengine.common.QueryFields
 *  com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache
 *  com.jiuqi.np.dataengine.definitions.DefinitionsCache
 *  com.jiuqi.np.dataengine.definitions.TableModelRunInfo
 *  com.jiuqi.np.dataengine.exception.ExpressionException
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.parse.AdvanceFunction
 *  com.jiuqi.np.dataengine.parse.IReportFunction
 *  com.jiuqi.np.dataengine.parse.ReadWriteInfo
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.function.func;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.common.QueryFields;
import com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache;
import com.jiuqi.np.dataengine.definitions.DefinitionsCache;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.exception.ExpressionException;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.parse.AdvanceFunction;
import com.jiuqi.np.dataengine.parse.IReportFunction;
import com.jiuqi.np.dataengine.parse.ReadWriteInfo;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.function.func.floatcopy.FloatCopyDestCopyCondition;
import com.jiuqi.nr.function.func.floatcopy.FloatCopyExecutor;
import com.jiuqi.nr.function.func.floatcopy.FloatCopyParaParser;
import com.jiuqi.nr.function.func.floatcopy.FloatCopyQueryInfo;
import com.jiuqi.nr.function.func.floatcopy.FloatCopySrcQueryCondition;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.util.List;

public class FloatCopy
extends AdvanceFunction
implements IReportFunction {
    private static final long serialVersionUID = 3565622721625053981L;

    public FloatCopy() {
        this.parameters().add(new Parameter("srcRelaExp", 6, "\u6e90\u6d6e\u52a8\u884c\u5173\u8054\u7684\u8868\u8fbe\u5f0f"));
        this.parameters().add(new Parameter("destRelaExp", 6, "\u76ee\u6807\u6d6e\u52a8\u884c\u5173\u8054\u7684\u8868\u8fbe\u5f0f"));
        this.parameters().add(new Parameter("srcFilter", 6, "\u6e90\u6d6e\u52a8\u884c\u8fc7\u6ee4\u6761\u4ef6"));
        this.parameters().add(new Parameter("assignExp", 6, "\u8d4b\u503c\u8868\u8fbe\u5f0f"));
        this.parameters().add(new Parameter("clearDestBeforeCopy", 1, "\u590d\u5236\u524d\u662f\u5426\u6e05\u9664\u76ee\u6807\u6d6e\u52a8\u884c\u7684\u6570\u636e"));
        this.parameters().add(new Parameter("copyMode", 6, "\u62f7\u8d1d\u65b9\u5f0f"));
        this.parameters().add(new Parameter("periodOffset", 3, "\u65f6\u671f\u504f\u79fb\u91cf"));
        this.parameters().add(new Parameter("srcPeriodType", 6, "\u6e90\u6d6e\u52a8\u884c\u65f6\u671f\u7c7b\u578b"));
        this.parameters().add(new Parameter("periodCount", 3, "\u6307\u5b9a\u53d6\u6e90\u65f6\u671f\u540e\u7684\u51e0\u671f\u6570\u636e"));
    }

    public String name() {
        return "FloatCopy";
    }

    public String title() {
        return "\u6d6e\u52a8\u884c\u4e4b\u95f4\u6570\u636e\u62f7\u8d1d";
    }

    protected void toExplain(IContext context, List<IASTNode> parameters, StringBuilder buffer, Object info) throws InterpretException {
        QueryContext qContext = (QueryContext)context;
        try {
            FloatCopyParaParser parser = new FloatCopyParaParser(qContext, parameters);
            buffer.append("\u4ece");
            buffer.append(parser.getQueryCondition().getRow().getReportTitle());
            buffer.append("\u590d\u5236\u5230");
            buffer.append(parser.getCopyCondition().getRow().getReportTitle());
        }
        catch (SyntaxException e) {
            throw new InterpretException(e.getMessage(), (Throwable)e);
        }
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 0;
    }

    public String category() {
        return "\u5176\u5b83\u51fd\u6570";
    }

    public int validate(IContext context, List<IASTNode> parameters) throws SyntaxException {
        QueryContext qContext = (QueryContext)context;
        try {
            FloatCopyParaParser parser = new FloatCopyParaParser(qContext, parameters);
            if (!parser.isValid()) {
                throw new SyntaxException("\u5b57\u6bb5\u5173\u8054\u8868\u8fbe\u5f0f\u89e3\u6790\u5931\u8d25");
            }
            return this.getResultType((IContext)qContext, parameters);
        }
        catch (Exception e) {
            throw new SyntaxException("\u51fd\u6570" + this.name() + "\u53c2\u6570\u6821\u9a8c\u51fa\u9519\uff1a" + e.getMessage(), (Throwable)e);
        }
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        QueryContext qContext = (QueryContext)context;
        try {
            FloatCopyParaParser parser = new FloatCopyParaParser(qContext, parameters);
            if (!parser.isValid()) {
                return false;
            }
            return new FloatCopyExecutor(parser, this).execute(qContext);
        }
        catch (Exception e) {
            throw new SyntaxException((Throwable)e);
        }
    }

    public ReadWriteInfo getReadWriteInfo(QueryContext qContext, List<IASTNode> params) {
        ReadWriteInfo info = null;
        if (qContext == null) {
            return null;
        }
        try {
            FloatCopyParaParser parser = new FloatCopyParaParser(qContext, params);
            FloatCopySrcQueryCondition queryCondition = parser.getQueryCondition();
            if (parser.isValid() && queryCondition.getLinkedAlias() == null && !queryCondition.hasPeriodOffset()) {
                info = new ReadWriteInfo();
                FloatCopyDestCopyCondition copyCondition = parser.getCopyCondition();
                info.setClearTable(copyCondition.isClearBeforeCopy());
                ExecutorContext exeContext = qContext.getExeContext();
                DefinitionsCache cache = exeContext.getCache();
                DataModelDefinitionsCache dataModelDefinitionsCache = cache.getDataModelDefinitionsCache();
                QueryFields writeQueryFields = this.getQueryFields(qContext, exeContext, cache, dataModelDefinitionsCache, copyCondition.getRow());
                info.setWriteQueryFields(writeQueryFields);
                if (writeQueryFields.getCount() > 0) {
                    info.setWriteTable(writeQueryFields.getItem(0).getTable());
                }
                QueryFields readQueryFields = this.getQueryFields(qContext, exeContext, cache, dataModelDefinitionsCache, queryCondition.getRow());
                info.setReadQueryFields(readQueryFields);
            }
        }
        catch (Exception e) {
            qContext.getMonitor().exception(e);
        }
        return info;
    }

    private QueryFields getQueryFields(QueryContext qContext, ExecutorContext exeContext, DefinitionsCache cache, DataModelDefinitionsCache dataModelDefinitionsCache, FloatCopyQueryInfo destQueryInfo) throws ExpressionException {
        QueryFields writeQueryFields = new QueryFields();
        FieldDefine floatOrderField = destQueryInfo.getFloatOrderField();
        TableModelRunInfo tableInfo = dataModelDefinitionsCache.getTableInfo(dataModelDefinitionsCache.getTableName(floatOrderField));
        for (ColumnModelDefine fieldDefine : tableInfo.getAllFields()) {
            IFmlExecEnvironment env;
            QueryField queryField = cache.extractQueryField(exeContext, fieldDefine, null, null);
            if (qContext.isNeedTableRegion() && exeContext.getEnv() != null && (env = exeContext.getEnv()).getDataModelLinkFinder().hasRegionCondition(exeContext, destQueryInfo.getRegionKey())) {
                queryField.setRegion(destQueryInfo.getRegionKey());
            }
            writeQueryFields.add(queryField);
        }
        return writeQueryFields;
    }
}

