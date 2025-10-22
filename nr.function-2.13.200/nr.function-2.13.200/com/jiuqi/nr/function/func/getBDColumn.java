/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.database.sql.parser.SQLInterpretException
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.interpret.ISQLInfo
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.QueryParam
 *  com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache
 *  com.jiuqi.np.dataengine.definitions.TableModelRunInfo
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.parse.IReportFunction
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.definition.common.Consts
 *  com.jiuqi.np.definition.common.SpringBeanProvider
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.data.engine.util.EntityQueryHelper
 *  com.jiuqi.nr.entity.engine.data.AbstractData
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.common.TableDictType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.nr.function.func;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.sql.parser.SQLInterpretException;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.interpret.ISQLInfo;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.QueryParam;
import com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.parse.IReportFunction;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.definition.common.Consts;
import com.jiuqi.np.definition.common.SpringBeanProvider;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.data.engine.util.EntityQueryHelper;
import com.jiuqi.nr.entity.engine.data.AbstractData;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.common.TableDictType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class getBDColumn
extends Function
implements IReportFunction {
    private static final long serialVersionUID = -4562771098865625223L;

    public getBDColumn() {
        this.parameters().add(new Parameter("tableCode", 6, "\u5b9e\u4f53\u8868\u540d"));
        this.parameters().add(new Parameter("entityKey", 0, "\u5b9e\u4f53\u4e3b\u952e\u5217"));
        this.parameters().add(new Parameter("fieldCode", 6, "\u5b57\u6bb5\u540d"));
    }

    public String name() {
        return "getBDColumn";
    }

    public String title() {
        return "\u6839\u636e\u5b9e\u4f53\u6807\u8bc6\u83b7\u53d6\u5b9e\u4f53\u4e2d\u7684\u67d0\u4e2a\u5c5e\u6027\u5217";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        if (parameters == null || parameters.size() < 3) {
            return 0;
        }
        try {
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
        catch (Exception e) {
            throw new SyntaxException("\u51fd\u6570" + this.name() + "\u53c2\u6570\u6821\u9a8c\u51fa\u9519\uff1a" + e.getMessage(), (Throwable)e);
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
                EntityQueryHelper entityQueryHelper;
                IEntityTable entityTable;
                IEntityRow entityRow;
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
                String period = null;
                PeriodWrapper periodWrapper = qContext.getPeriodWrapper();
                if (periodWrapper != null) {
                    period = periodWrapper.toString();
                }
                if ((entityRow = (entityTable = (entityQueryHelper = (EntityQueryHelper)SpringBeanProvider.getBean(EntityQueryHelper.class)).queryEntityTreeByTableCode(qContext, tableCode, null, period)).findByEntityKey(entityKey)) != null) {
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

    public boolean support(Language lang) {
        return lang != Language.EXCEL && lang != Language.JQMDX && lang != Language.JavaScript;
    }

    protected void toSQL(IContext context, List<IASTNode> parameters, StringBuilder buffer, ISQLInfo info) throws InterpretException {
        if (context instanceof QueryContext) {
            QueryContext qContext = (QueryContext)context;
            try {
                String tableCode = (String)parameters.get(0).evaluate(context);
                IASTNode entityKeyNode = parameters.get(1);
                String fieldCode = (String)parameters.get(2).evaluate(context);
                if (StringUtils.isEmpty((String)fieldCode)) {
                    return;
                }
                EntityQueryHelper entityQueryHelper = (EntityQueryHelper)SpringBeanProvider.getBean(EntityQueryHelper.class);
                ExecutorContext exeContext = qContext.getExeContext();
                String entityId = entityQueryHelper.getDimensionProvider().getEntityIdByEntityTableCode(exeContext, tableCode);
                TableModelDefine tableModel = entityQueryHelper.getEntityMetaService().getTableModel(entityId);
                IEntityModel entityModel = entityQueryHelper.getEntityMetaService().getEntityModel(entityId);
                IEntityAttribute columnModel = entityModel.getAttribute(fieldCode);
                buffer.append("(select ").append(columnModel.getName()).append(" from ").append(tableModel.getName()).append(" ").append(tableModel.getName()).append(" where ");
                buffer.append(tableModel.getName()).append(".").append(entityModel.getBizKeyField().getName()).append("=");
                entityKeyNode.interpret(context, buffer, Language.SQL, (Object)info);
                this.appendVersionFilter(qContext, tableModel, buffer);
                buffer.append(")");
            }
            catch (Exception e) {
                throw new InterpretException(e.getMessage(), (Throwable)e);
            }
        }
    }

    private void appendVersionFilter(QueryContext qContext, TableModelDefine tableModel, StringBuilder condition) throws Exception {
        Date[] dateRegion;
        if (tableModel.getDictType() != TableDictType.ZIPPER) {
            return;
        }
        PeriodWrapper periodWrapper = qContext.getPeriodWrapper();
        Date queryVersionDate = Consts.DATE_VERSION_FOR_ALL;
        if (periodWrapper != null && (dateRegion = qContext.getExeContext().getPeriodAdapter().getPeriodDateRegion(periodWrapper)) != null) {
            queryVersionDate = dateRegion[1];
        }
        String tableAlias = tableModel.getName();
        condition.append(" and ");
        TableModelRunInfo tableRunInfo = qContext.getExeContext().getCache().getDataModelDefinitionsCache().getTableInfo(tableModel);
        condition.append(this.getDateCompareSql(qContext, tableAlias, "VALIDTIME", "<=", queryVersionDate));
        condition.append(" and ");
        condition.append(this.getDateCompareSql(qContext, tableAlias, "INVALIDTIME", ">", queryVersionDate));
        ColumnModelDefine stopFlagField = tableRunInfo.getFieldByName("STOPFLAG");
        if (stopFlagField != null) {
            condition.append(" and ").append(tableAlias).append(".").append(stopFlagField.getName()).append("<>1");
        }
        ColumnModelDefine recoveryFlagField = tableRunInfo.getFieldByName("RECOVERYFLAG");
        if (stopFlagField != null) {
            condition.append(" and ").append(tableAlias).append(".").append(recoveryFlagField.getName()).append("<>1");
        }
    }

    private String getDateCompareSql(QueryContext qContext, String alias, String fieldName, String compareOper, Date date) throws SQLInterpretException {
        QueryParam queryParam = qContext.getQueryParam();
        IDatabase database = queryParam.getDatabase();
        StringBuilder sql = new StringBuilder();
        if (StringUtils.isNotEmpty((String)alias)) {
            sql.append(alias).append(".");
        }
        sql.append(fieldName);
        sql.append(compareOper);
        if (database.isDatabase("DERBY")) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String dateSql = "TIMESTAMP('" + format.format(date) + "','00:00:00')";
            sql.append(dateSql);
        } else {
            sql.append(database.createSQLInterpretor(queryParam.getConnection()).formatSQLDate(date));
        }
        return sql.toString();
    }
}

