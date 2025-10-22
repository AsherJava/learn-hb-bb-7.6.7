/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache
 *  com.jiuqi.np.dataengine.parse.IReportFunction
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.definition.common.SpringBeanProvider
 *  com.jiuqi.nr.entity.engine.data.AbstractData
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.bql.datasource.parse.func;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache;
import com.jiuqi.np.dataengine.parse.IReportFunction;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.definition.common.SpringBeanProvider;
import com.jiuqi.nr.bql.util.EntityQueryService;
import com.jiuqi.nr.entity.engine.data.AbstractData;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.util.List;

public class GetEntityField
extends Function
implements IReportFunction {
    private static final long serialVersionUID = -4659012036014561487L;

    public GetEntityField() {
        this.parameters().add(new Parameter("tableCode", 6, "\u4e3b\u4f53\u8868\u540d"));
        this.parameters().add(new Parameter("entityKey", 0, "\u4e3b\u4f53\u6570\u636e\u9879\u6807\u8bc6\uff0c\u53ef\u4ee5\u662fcode\u6216\u8005id"));
        this.parameters().add(new Parameter("fieldCode", 6, "\u5b57\u6bb5\u540d"));
    }

    public String name() {
        return "GetEntityField";
    }

    public String title() {
        return "\u83b7\u53d6\u4e3b\u6570\u636e\u5176\u4ed6\u5217\u503c";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        if (context instanceof QueryContext) {
            QueryContext qContext = (QueryContext)context;
            String tableCode = (String)parameters.get(0).evaluate(context);
            String fieldCode = (String)parameters.get(2).evaluate(context);
            DataModelDefinitionsCache dataModelDefinitionsCache = qContext.getExeContext().getCache().getDataModelDefinitionsCache();
            ColumnModelDefine columnModel = dataModelDefinitionsCache.parseSearchField(tableCode.toUpperCase(), fieldCode.toUpperCase(), tableCode);
            if (columnModel != null) {
                ColumnModelType columnType = columnModel.getColumnType();
                if (columnType == ColumnModelType.INTEGER) {
                    return 3;
                }
                return columnType.getValue();
            }
        }
        return 0;
    }

    public String category() {
        return "\u5176\u5b83\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        if (context instanceof QueryContext) {
            QueryContext qContext = (QueryContext)context;
            try {
                String tableCode = (String)parameters.get(0).evaluate(context);
                Object p1 = parameters.get(1).evaluate(context);
                if (p1 == null) {
                    return null;
                }
                String entityKey = p1.toString();
                String fieldCode = (String)parameters.get(2).evaluate(context);
                if (StringUtils.isEmpty((String)fieldCode)) {
                    return null;
                }
                String veriosnPeriod = qContext.getVersionPeriod();
                EntityQueryService entityQueryHelper = (EntityQueryService)SpringBeanProvider.getBean(EntityQueryService.class);
                IEntityTable entityTable = entityQueryHelper.queryEntityTreeByTableCode(qContext, tableCode, null, veriosnPeriod);
                IEntityRow entityRow = entityTable.findByEntityKey(entityKey);
                if (entityRow == null) {
                    entityRow = entityTable.findByCode(entityKey);
                }
                if (entityRow != null) {
                    AbstractData value = entityRow.getValue(fieldCode.toUpperCase());
                    Object returnValue = value.getAsObject();
                    return returnValue;
                }
            }
            catch (Exception e) {
                throw new SyntaxException(e.getMessage(), (Throwable)e);
            }
        }
        return null;
    }
}

