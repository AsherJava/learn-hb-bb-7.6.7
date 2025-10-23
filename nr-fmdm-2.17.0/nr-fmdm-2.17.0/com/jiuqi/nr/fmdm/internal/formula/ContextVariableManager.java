/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.dynamic.DynamicNodeException
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.syntax.reportparser.IReportDynamicNodeProvider
 *  com.jiuqi.np.dataengine.common.DataTypesConvert
 *  com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache
 *  com.jiuqi.np.dataengine.definitions.TableModelRunInfo
 *  com.jiuqi.np.dataengine.node.VariableDataNode
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.np.dataengine.var.VariableManagerBase
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.data.engine.fml.var.VarEntity
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.fmdm.internal.formula;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.dynamic.DynamicNodeException;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.syntax.reportparser.IReportDynamicNodeProvider;
import com.jiuqi.np.dataengine.common.DataTypesConvert;
import com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.node.VariableDataNode;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.np.dataengine.var.VariableManagerBase;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.data.engine.fml.var.VarEntity;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ContextVariableManager
extends VariableManagerBase
implements IReportDynamicNodeProvider {
    private static final Logger logger = LoggerFactory.getLogger(ContextVariableManager.class);

    public IASTNode find(IContext context, Token token, String refName) throws DynamicNodeException {
        Variable var = this.find(refName);
        if (var != null) {
            return new VariableDataNode(token, var);
        }
        QueryContext qContext = (QueryContext)context;
        int index = refName.lastIndexOf("_");
        if (index >= 0) {
            try {
                String entityTableName = refName.substring(0, index);
                String fieldName = refName.substring(index + 1);
                DataModelDefinitionsCache tableCache = qContext.getExeContext().getCache().getDataModelDefinitionsCache();
                TableModelRunInfo tableRunInfo = tableCache.getTableInfo(entityTableName);
                if (tableRunInfo != null) {
                    String dimensionName = tableRunInfo.getDimensions().get(0);
                    ColumnModelDefine fieldDefine = tableRunInfo.getFieldByName(fieldName);
                    if (fieldDefine != null) {
                        FieldDefine field = tableCache.getFieldDefine(fieldDefine);
                        var = new VarEntity(dimensionName, refName, field, DataTypesConvert.fieldTypeToDataType((FieldType)field.getType()));
                        return new VariableDataNode(token, var);
                    }
                }
            }
            catch (ParseException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return null;
    }

    public IASTNode find(IContext context, Token token, List<String> objPath) throws DynamicNodeException {
        return null;
    }

    public IASTNode findRestrict(IContext context, Token token, List<String> objPath, List<IASTNode> restrictItems) throws DynamicNodeException {
        return null;
    }

    public IASTNode findSpecial(IContext context, Token token, String refName) throws DynamicNodeException {
        return this.find(context, token, refName);
    }

    public IASTNode findSpec(IContext arg0, Token arg1, String arg2, String arg3) throws DynamicNodeException {
        return null;
    }
}

