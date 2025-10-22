/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.ASTNode
 *  com.jiuqi.bi.syntax.ast.ASTNodeType
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IAssignable
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.cell.Position
 *  com.jiuqi.bi.syntax.data.CalendarCache
 *  com.jiuqi.bi.syntax.interpret.ISQLInfo
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.interpret.SQLInfoDescr
 *  com.jiuqi.bi.syntax.interpret.ScriptInfo
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.definition.provider.DimensionRow
 *  com.jiuqi.np.period.IPeriodAdapter
 *  com.jiuqi.np.period.PeriodModifier
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nvwa.dataengine.common.Convert
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.np.dataengine.node;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.ASTNode;
import com.jiuqi.bi.syntax.ast.ASTNodeType;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IAssignable;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.cell.Position;
import com.jiuqi.bi.syntax.data.CalendarCache;
import com.jiuqi.bi.syntax.interpret.ISQLInfo;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.interpret.SQLInfoDescr;
import com.jiuqi.bi.syntax.interpret.ScriptInfo;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.QueryParam;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DataEngineUtil;
import com.jiuqi.np.dataengine.common.DataLinkColumn;
import com.jiuqi.np.dataengine.common.DataModelLinkColumn;
import com.jiuqi.np.dataengine.common.DataTypesConvert;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.common.QueryFields;
import com.jiuqi.np.dataengine.common.QueryTable;
import com.jiuqi.np.dataengine.common.ValidateResult;
import com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.executors.StatItem;
import com.jiuqi.np.dataengine.intf.IFieldValueProcessor;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.intf.IValueValidateHandler;
import com.jiuqi.np.dataengine.intf.ZBAuthJudger;
import com.jiuqi.np.dataengine.node.FormulaDataLinkPosition;
import com.jiuqi.np.dataengine.node.FormulaShowInfo;
import com.jiuqi.np.dataengine.node.NodeShowInfo;
import com.jiuqi.np.dataengine.node.PeriodConditionNode;
import com.jiuqi.np.dataengine.node.StatisticInfo;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.query.UpdateDatas;
import com.jiuqi.np.dataengine.reader.QueryFieldInfo;
import com.jiuqi.np.definition.provider.DimensionRow;
import com.jiuqi.np.period.IPeriodAdapter;
import com.jiuqi.np.period.PeriodModifier;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nvwa.dataengine.common.Convert;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DynamicDataNode
extends ASTNode
implements IAssignable {
    private static final Logger logger = LoggerFactory.getLogger(DynamicDataNode.class);
    private static final long serialVersionUID = -20860266924858710L;
    protected QueryField queryField;
    protected StatisticInfo statisticNode;
    protected ASTNode relatedNode;
    protected boolean isAssgin;
    protected boolean innerSelect;
    protected String nodeId;
    protected DataModelLinkColumn dataModelLink;
    protected boolean showDictTitle;
    protected String relateTaskItem;
    protected QueryTable mainTable;
    protected NodeShowInfo showInfo;
    protected String tableAlias;
    protected int dataType = -1;
    protected String refEntityId = null;
    protected boolean readValueByIndex = false;
    protected QueryFieldInfo queryFieldInfo = null;
    protected String sqlColumn = null;
    protected boolean supportLocate = true;

    public DynamicDataNode(Token token, QueryField queryField) {
        super(token);
        this.queryField = queryField;
        if (queryField != null) {
            this.sqlColumn = queryField.getFieldName();
        }
    }

    public DynamicDataNode(QueryField queryField) {
        this(null, queryField);
    }

    public DynamicDataNode() {
        this(null, null);
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public void applySpecification(IContext context, List<IASTNode> specifications) throws SyntaxException {
    }

    public void setStatistic(IASTNode conditionNode, int statKind) {
        this.statisticNode = new StatisticInfo(statKind);
        this.statisticNode.condNode = conditionNode;
        this.statisticNode.valueNode = new DynamicDataNode(this.queryField);
    }

    public StatisticInfo getStatisticInfo() {
        return this.statisticNode;
    }

    public void getQueryFields(QueryFields queryFields) {
        this.queryField = queryFields.addOrGetExist(this.queryField);
    }

    public QueryFields getQueryFields() {
        QueryFields queryFields = new QueryFields();
        this.getQueryFields(queryFields);
        return queryFields;
    }

    public int setValue(IContext context, Object value) throws SyntaxException {
        QueryContext qContext = (QueryContext)context;
        ZBAuthJudger aulthJuger = qContext.getAuthJudger();
        if (aulthJuger != null && !aulthJuger.canModify(this.queryField.getUID())) {
            return 0;
        }
        if (!this.checkValueValidate(qContext, value)) {
            return 0;
        }
        try {
            Object resultValue = DataEngineConsts.formatData(this.queryField, value);
            UpdateDatas updateDatas = qContext.getUpdateDatas();
            if (updateDatas != null) {
                Object oldValue = DataEngineConsts.formatData(this.queryField, this.evaluate(context));
                updateDatas.addValue(qContext, this.queryField, resultValue, oldValue);
            }
            if (this.readValueByIndex) {
                qContext.writeData(this.queryFieldInfo, resultValue);
            } else {
                qContext.writeData(this.queryField, resultValue);
            }
        }
        catch (Exception e) {
            throw new SyntaxException((Throwable)e);
        }
        return 1;
    }

    private boolean checkValueValidate(QueryContext qContext, Object value) {
        QueryParam queryParam = qContext.getQueryParam();
        if (queryParam.getValueValidateHandlers() == null || queryParam.getValueValidateHandlers().size() <= 0) {
            return true;
        }
        boolean checkResult = true;
        for (IValueValidateHandler validateHandler : queryParam.getValueValidateHandlers()) {
            ValidateResult result = validateHandler.checkValue(this.queryField.getFieldName(), this.queryField.getDataType(), value);
            if (result == null || result.isResultValue()) continue;
            checkResult = false;
        }
        return checkResult;
    }

    public ASTNodeType getNodeType() {
        return ASTNodeType.DYNAMICDATA;
    }

    public int getType(IContext context) throws SyntaxException {
        if (this.dataType < 0) {
            this.dataType = this.queryField.getDataType();
            if (this.showDictTitle) {
                this.dataType = 6;
                return this.dataType;
            }
            if (this.relatedNode != null) {
                this.dataType = this.relatedNode.getType(context);
                return this.dataType;
            }
            if (this.statisticNode != null) {
                this.dataType = this.statisticNode.getType(context);
            } else if (this.dataType == 5) {
                this.dataType = 2;
            } else if (this.dataType == 4 || this.dataType == 3) {
                this.dataType = 3;
            } else if (this.dataType == 34 || this.dataType == 36) {
                this.dataType = 6;
            }
        }
        return this.dataType;
    }

    public Object evaluate(IContext context) throws SyntaxException {
        QueryContext qContext = (QueryContext)context;
        try {
            IFmlExecEnvironment env;
            Set<String> unKnownUnits;
            ExecutorContext exeContext = qContext.getExeContext();
            if (StringUtils.isNotEmpty((String)this.relateTaskItem) && qContext.getLinkQueryInfoMap().size() > 0 && (unKnownUnits = qContext.getUnKnownLinkUnitSet(this.relateTaskItem)) != null) {
                String unitDim = exeContext.getEnv().getUnitDimesion(exeContext);
                Object unitId = qContext.getCurrentMasterKey().getValue(unitDim);
                if (unKnownUnits.contains(unitId.toString())) {
                    if (qContext.isEvalRelatedUnitsNotFound()) {
                        return null;
                    }
                    throw new SyntaxException("\u6ca1\u6709\u627e\u5230\u4e3b\u4f53\u5bf9\u5e94\u7684\u5173\u8054\u4e3b\u4f53");
                }
            }
            if (this.relatedNode != null) {
                return this.relatedNode.evaluate(context);
            }
            if (this.statisticNode != null) {
                return this.statisticNode.evaluate(context);
            }
            Object value = null;
            value = this.readValueByIndex ? qContext.readData(this.queryFieldInfo) : qContext.readData(this.queryField);
            if (this.getType(context) == 10) {
                if (value instanceof Double) {
                    value = new BigDecimal((Double)value);
                } else if (value instanceof Integer) {
                    value = new BigDecimal((Integer)value);
                } else if (value instanceof Long) {
                    value = new BigDecimal((Long)value);
                }
            } else if (this.getType(context) == 5 || this.getType(context) == 2) {
                if (value instanceof Calendar) {
                    return value;
                }
                Calendar c = null;
                if (value instanceof Timestamp) {
                    c = Calendar.getInstance();
                    c.setTime((Timestamp)value);
                } else if (value instanceof Date) {
                    c = Calendar.getInstance();
                    c.setTime((Date)value);
                } else if (value instanceof Long) {
                    c = CalendarCache.get((long)((Long)value));
                }
                return c;
            }
            if (this.showDictTitle && this.queryField.getReferFieldKey() != null) {
                if (this.refEntityId == null) {
                    DataModelDefinitionsCache dataDefinitionsCache = exeContext.getCache().getDataModelDefinitionsCache();
                    ColumnModelDefine refColumn = dataDefinitionsCache.findField(this.queryField.getReferFieldKey());
                    String refTableName = dataDefinitionsCache.getTableName(refColumn);
                    this.refEntityId = dataDefinitionsCache.getDimensionProvider().getEntityIdByEntityTableCode(exeContext, refTableName);
                }
                if (this.queryField.isMultival() && value != null) {
                    String[] array = value.toString().split(";");
                    StringBuilder buff = new StringBuilder();
                    for (String o : array) {
                        String code = o.toString();
                        DimensionRow row = qContext.getFieldRefDimensionRow(this.refEntityId, (Object)code);
                        buff.append(row == null ? "" : row.getTitle()).append(";");
                    }
                    if (array.length > 0) {
                        buff.setLength(buff.length() - 1);
                    }
                    value = buff.toString();
                } else {
                    DimensionRow row = qContext.getFieldRefDimensionRow(this.refEntityId, value);
                    if (row != null) {
                        value = row.getTitle();
                    }
                }
            }
            if ((env = exeContext.getEnv()) != null && env.getFieldValueProcessor() != null) {
                IFieldValueProcessor fieldValueProcessor = env.getFieldValueProcessor();
                value = fieldValueProcessor.processValue(this.queryField, value);
            }
            return value;
        }
        catch (Exception e) {
            throw new SyntaxException((Throwable)e);
        }
    }

    public boolean support(Language lang) {
        return lang != Language.JQMDX && lang != Language.EXCEL;
    }

    protected void toSQL(IContext context, StringBuilder buffer, ISQLInfo info) throws InterpretException {
        QueryContext qContext = (QueryContext)context;
        ExecutorContext exeContext = qContext.getExeContext();
        boolean useDNASql = exeContext.isUseDnaSql();
        String fieldTableAlias = this.getTableAlias(qContext, this.queryField.getTable());
        if (this.sqlColumn == null && this.queryField != null) {
            this.sqlColumn = this.queryField.getFieldName();
        }
        try {
            if (this.statisticNode != null) {
                buffer.append("(select ");
                String sumType = StatItem.getSqlFunction(this.statisticNode.statKind);
                buffer.append(sumType).append("(").append(this.sqlColumn).append(")");
                buffer.append(" from ").append(this.queryField.getTableName()).append(" ");
                if (useDNASql) {
                    buffer.append("as ");
                }
                String innerAlias = fieldTableAlias;
                buffer.append(innerAlias);
                buffer.append(" where ");
                TableModelRunInfo tableInfo = exeContext.getCache().getDataModelDefinitionsCache().getTableInfo(this.sqlColumn);
                DimensionValueSet masterkeys = qContext.getMasterKeys();
                boolean whereNeedAnd = false;
                for (int i = 0; i < masterkeys.size(); ++i) {
                    String keyName = masterkeys.getName(i);
                    Object keyValue = masterkeys.getValue(i);
                    String dimensionFieldName = keyName;
                    ColumnModelDefine dimensionField = tableInfo.getDimensionField(keyName);
                    if (dimensionField == null) continue;
                    dimensionFieldName = dimensionField.getName();
                    int dimDataType = DataTypesConvert.fieldTypeToDataType(dimensionField.getColumnType());
                    if (StringUtils.isEmpty((String)dimensionFieldName)) continue;
                    if (whereNeedAnd) {
                        buffer.append(" and ");
                    }
                    this.appendToCondition(buffer, innerAlias, dimensionFieldName, keyValue, dimDataType, info, qContext);
                    whereNeedAnd = true;
                }
                if (this.statisticNode.condNode != null) {
                    buffer.append(" and (").append(this.statisticNode.condNode.interpret((IContext)qContext, Language.SQL, (Object)info)).append(")");
                }
                buffer.append(")");
            } else if (this.relatedNode != null) {
                buffer.append(this.relatedNode.evaluate((IContext)qContext));
            } else {
                QueryTable table = this.queryField.getTable();
                if (this.isInnerSelect()) {
                    TableModelRunInfo tableInfo = exeContext.getCache().getDataModelDefinitionsCache().getTableInfo(table.getTableName());
                    TableModelRunInfo mainTableInfo = exeContext.getCache().getDataModelDefinitionsCache().getTableInfo(this.mainTable.getTableName());
                    buffer.append("(");
                    buffer.append("select ").append(fieldTableAlias).append(".").append(this.sqlColumn);
                    buffer.append(" from ").append(table.getTableName());
                    if (exeContext.isUseDnaSql()) {
                        buffer.append(" as ");
                    } else {
                        buffer.append(" ");
                    }
                    buffer.append(fieldTableAlias);
                    buffer.append(" where ");
                    boolean needAnd = false;
                    PeriodModifier periodModifier = this.queryField.getTable().getPeriodModifier();
                    if (periodModifier != null) {
                        ColumnModelDefine dimensionField = tableInfo.getDimensionField("DATATIME");
                        PeriodWrapper oldPeriodWrapper = qContext.getPeriodWrapper();
                        PeriodWrapper currentPeriodWrapper = new PeriodWrapper(oldPeriodWrapper);
                        IPeriodAdapter periodAdapter = exeContext.getPeriodAdapter();
                        periodAdapter.modify(currentPeriodWrapper, periodModifier);
                        String period = currentPeriodWrapper.toString();
                        this.appendToCondition(buffer, this.getTableAlias(qContext, this.mainTable), dimensionField.getName(), period, 6, info, qContext);
                        needAnd = true;
                    }
                    for (int i = 0; i < table.getTableDimensions().size(); ++i) {
                        Object dimValue;
                        String dimension = table.getTableDimensions().get(i);
                        ColumnModelDefine dimensionField = tableInfo.getDimensionField(dimension);
                        if (dimensionField == null) continue;
                        if (table.getDimensionRestriction() != null && table.getDimensionRestriction().hasValue(dimension)) {
                            if (needAnd) {
                                buffer.append(" and ");
                            }
                            dimValue = table.getDimensionRestriction().getValue(dimension);
                            int dimDataType = DataTypesConvert.fieldTypeToDataType(dimensionField.getColumnType());
                            this.appendToCondition(buffer, fieldTableAlias, dimensionField.getName(), dimValue, dimDataType, info, qContext);
                            needAnd = true;
                            continue;
                        }
                        if (this.mainTable.getTableDimensions().contains(dimension)) {
                            if (needAnd) {
                                buffer.append(" and ");
                            }
                            buffer.append(this.getTableAlias(qContext, this.mainTable)).append(".").append(DataEngineUtil.getDimensionFieldName(mainTableInfo, dimension));
                            buffer.append("=");
                            buffer.append(fieldTableAlias).append(".").append(DataEngineUtil.getDimensionFieldName(tableInfo, dimension));
                            needAnd = true;
                            continue;
                        }
                        if (!qContext.getMasterKeys().hasValue(dimension)) continue;
                        if (needAnd) {
                            buffer.append(" and ");
                        }
                        dimValue = qContext.getMasterKeys().getValue(dimension);
                        int dimDataType = DataTypesConvert.fieldTypeToDataType(dimensionField.getColumnType());
                        this.appendToCondition(buffer, fieldTableAlias, dimensionField.getName(), dimValue, dimDataType, info, qContext);
                        needAnd = true;
                    }
                    buffer.append(")");
                } else if (info != null && info instanceof SQLInfoDescr && ((SQLInfoDescr)info).getIgnoreAlias()) {
                    buffer.append(this.sqlColumn);
                } else {
                    String alias = this.tableAlias == null ? fieldTableAlias : this.tableAlias;
                    buffer.append(alias).append(".").append(this.sqlColumn);
                }
            }
        }
        catch (Exception e) {
            throw new InterpretException((Throwable)e);
        }
    }

    protected String getTableAlias(QueryContext qContext, QueryTable table) {
        String tableAlias = qContext.getQueryTableAliaMap().get(table);
        if (tableAlias == null) {
            tableAlias = table.getAlias();
        }
        return tableAlias;
    }

    protected void appendValue(StringBuilder sql, Object value, int dataType, ISQLInfo info, QueryContext queryContext) {
        if (dataType == 6) {
            sql.append("'").append(value).append("'");
        } else if (dataType == 33) {
            UUID uuid = Convert.toUUID((Object)value);
            sql.append(DataEngineUtil.buildGUIDValueSql(info.getDatabase(), uuid));
        } else if (dataType == 5 || dataType == 2) {
            sql.append(DataEngineUtil.buildDateValueSql(info.getDatabase(), value, queryContext.getQueryParam().getConnection()));
        } else {
            sql.append(value);
        }
    }

    protected void toJavaScript(IContext context, StringBuilder buffer, ScriptInfo info) throws InterpretException {
        String statisticInfo = null;
        if (this.statisticNode != null) {
            info.setHasStatistic(true);
            String condName = null;
            String valueName = null;
            if (this.statisticNode.condNode != null) {
                this.statisticNode.condNode.interpret(context, buffer, Language.JavaScript, (Object)info);
                condName = info.getCurrentName();
            }
            if (this.statisticNode.valueNode != null) {
                valueName = String.format("new _FN('%s',%s,'%s')", this.statisticNode.valueNode.getQueryField().getFieldName(), this.statisticNode.valueNode.getQueryField().getDataType(), this.statisticNode.valueNode.getQueryField().getTableName());
            }
            statisticInfo = String.format("new %s(%s,%s,%s)", ((Object)((Object)this.statisticNode)).getClass().getSimpleName(), this.statisticNode.statKind, condName, valueName);
        }
        String periodInfo = null == this.queryField.getPeriodModifier() ? "" : this.queryField.getPeriodModifier().toString();
        String dimensionRestriction = null == this.queryField.getDimensionRestriction() ? "" : this.queryField.dimensionValueSetToString(this.queryField.getDimensionRestriction());
        String linkCode = null == this.dataModelLink ? "" : this.dataModelLink.getDataLinkCode();
        StringBuilder sbr = new StringBuilder();
        if (info.isExcelSyntax() && this.dataModelLink != null) {
            Position position = this.dataModelLink.getGridPosition();
            sbr.append("new _FN('").append(this.queryField.getFieldName()).append("',").append(this.queryField.getDataType()).append(",'").append(this.queryField.getTableName()).append("',").append(statisticInfo).append(",'").append(periodInfo).append("','").append(this.dataModelLink.getShowInfo().getSheetName()).append("',").append(String.format("new _Pos(%s,%s,%s)", position.row(), position.col(), position.options())).append(",'").append(linkCode).append("')");
        } else {
            sbr.append("new _FN('").append(this.queryField.getFieldName()).append("',").append(this.queryField.getDataType()).append(",'").append(this.queryField.getTableName()).append("',").append(statisticInfo).append(",'").append(periodInfo).append("','").append(dimensionRestriction).append("','").append(linkCode).append("')");
        }
        info.setCurrentName(sbr.toString());
    }

    protected void appendToCondition(StringBuilder sql, String tableAlias, String name, Object value, int dataType, ISQLInfo info, QueryContext queryContext) {
        sql.append(tableAlias).append(".").append(name);
        sql.append("=");
        this.appendValue(sql, value, dataType, info, queryContext);
    }

    protected void toFormula(IContext context, StringBuilder buffer, Object info) throws InterpretException {
        FormulaShowInfo formulaShowInfo;
        FormulaShowInfo formulaShowInfo2;
        if (context instanceof QueryContext && info instanceof FormulaShowInfo && !(formulaShowInfo2 = (FormulaShowInfo)info).isReportMode()) {
            buffer.append(this.queryField.getFieldName());
            return;
        }
        int beginIndex = buffer.length();
        NodeShowInfo showInfo = this.getShowInfo();
        if (showInfo.getTableName() != null) {
            buffer.append(showInfo.getTableName());
        } else if (showInfo.getSheetName() != null) {
            buffer.append(showInfo.getSheetName());
        }
        if (showInfo.isHasBracket()) {
            buffer.append("[");
        }
        buffer.append(this.queryField.getFieldCode());
        if (showInfo.getInnerAppend() != null) {
            buffer.append(",").append(showInfo.getInnerAppend());
        }
        if (showInfo.isHasBracket()) {
            buffer.append("]");
        }
        if (showInfo.getEndAppend() != null) {
            buffer.append(showInfo.getEndAppend());
        }
        if (info != null && (formulaShowInfo = (FormulaShowInfo)info).isCollectPositions() && this.dataModelLink != null) {
            formulaShowInfo.getDataLinkPositions().add(new FormulaDataLinkPosition(beginIndex, buffer.length(), this.dataModelLink));
        }
    }

    public boolean isStatic(IContext context) {
        return false;
    }

    public void toString(StringBuilder buffer) {
        try {
            this.toFormula(null, buffer, null);
        }
        catch (InterpretException e) {
            buffer.append(this.queryField);
        }
    }

    protected void toExplain(IContext context, StringBuilder buffer, Object info) throws InterpretException {
        QueryContext qContext = (QueryContext)context;
        try {
            ColumnModelDefine fieldDefine = qContext.getExeContext().getCache().getDataModelDefinitionsCache().findField(this.queryField.getUID());
            String title = fieldDefine.getTitle();
            if (StringUtils.isEmpty((String)title)) {
                title = fieldDefine.getCode();
            }
            buffer.append(title);
            if (this.queryField.getPeriodModifier() != null) {
                buffer.append(this.queryField.getPeriodModifier().toTitle());
            }
            if (this.queryField.getIsLj()) {
                buffer.append("\u672c\u5e74\u7d2f\u8ba1");
            }
            if (this.statisticNode != null) {
                buffer.append(StatItem.STAT_KIND_MODE_TITLES[this.statisticNode.statKind]);
            }
            if (this.queryField.getDimensionRestriction() != null) {
                Object value = this.queryField.getDimensionRestriction().getValue("DATATIME");
                if (value instanceof String) {
                    String period = (String)value;
                    IPeriodAdapter periodProvider = qContext.getExeContext().getPeriodAdapter();
                    PeriodWrapper pw = new PeriodWrapper(period);
                    buffer.append(periodProvider.getPeriodTitle(pw));
                } else if (value instanceof PeriodConditionNode) {
                    PeriodConditionNode condition = (PeriodConditionNode)value;
                    condition.toExplain((IContext)qContext, buffer, info);
                }
            }
            if (StringUtils.isNotEmpty((String)this.relateTaskItem)) {
                buffer.append("@").append(this.relateTaskItem);
            }
        }
        catch (ParseException e) {
            logger.error(e.getMessage(), e);
            throw new InterpretException((Throwable)e);
        }
    }

    public QueryField getQueryField() {
        return this.queryField;
    }

    public void setQueryField(QueryField queryField) {
        this.queryField = queryField;
    }

    public boolean isAssgin() {
        return this.isAssgin;
    }

    public void setAssgin(boolean isAssgin) {
        this.isAssgin = isAssgin;
    }

    public boolean isInnerSelect() {
        return this.innerSelect;
    }

    public void setInnerSelect(QueryTable mainTable) {
        this.mainTable = mainTable;
        this.innerSelect = !mainTable.equals(this.queryField.getTable());
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();
        this.toString(buffer);
        return buffer.toString();
    }

    public String getNodeId() {
        if (this.nodeId == null) {
            this.nodeId = this.toString();
        }
        return this.nodeId;
    }

    public int hashCode() {
        if (this.dataModelLink != null) {
            return this.dataModelLink.hashCode();
        }
        int hashCode = this.queryField.hashCode();
        return hashCode;
    }

    public boolean equals(Object obj) {
        if (obj instanceof DynamicDataNode) {
            DynamicDataNode other = (DynamicDataNode)((Object)obj);
            return this.queryField.equals(other.queryField);
        }
        return super.equals(obj);
    }

    public boolean isShowDictTitle() {
        return this.showDictTitle;
    }

    public void setShowDictTitle(boolean showDictTitle) {
        this.showDictTitle = showDictTitle;
    }

    public boolean isReportCell() {
        return this.dataModelLink != null;
    }

    public void setRelateItem(IContext context, String relateItem) {
        this.relateTaskItem = relateItem;
    }

    public ASTNode getRelatedNode() {
        return this.relatedNode;
    }

    public void setRelatedNode(ASTNode relatedNode) {
        this.relatedNode = relatedNode;
    }

    public DataLinkColumn getDataLink() {
        if (this.dataModelLink != null) {
            return new DataLinkColumn(this.dataModelLink);
        }
        return null;
    }

    public DataModelLinkColumn getDataModelLink() {
        return this.dataModelLink;
    }

    public void setDataModelLink(DataModelLinkColumn dataModelLink) {
        this.dataModelLink = dataModelLink;
    }

    public String getRelateTaskItem() {
        return this.relateTaskItem;
    }

    public void setRelateTaskItem(String relateTaskItem) {
        this.relateTaskItem = relateTaskItem;
    }

    public NodeShowInfo getShowInfo() {
        if (this.dataModelLink != null) {
            return this.dataModelLink.getShowInfo();
        }
        if (this.showInfo == null) {
            this.showInfo = new NodeShowInfo();
        }
        return this.showInfo;
    }

    protected int initNumSacle(IContext context) {
        return this.queryField.getFractionDigits();
    }

    public void clearStatisticInfo() {
        this.statisticNode = null;
    }

    public String getTableAlias() {
        return this.tableAlias;
    }

    public void setTableAlias(String tableAlias) {
        this.tableAlias = tableAlias;
    }

    public boolean isReadValueByIndex() {
        return this.readValueByIndex;
    }

    public void setReadValueByIndex(boolean readValueByIndex) {
        this.readValueByIndex = readValueByIndex;
    }

    public QueryFieldInfo getQueryFieldInfo() {
        return this.queryFieldInfo;
    }

    public void setQueryFieldInfo(QueryFieldInfo queryFieldInfo) {
        this.queryFieldInfo = queryFieldInfo;
    }

    public void setSqlColumn(String sqlColumn) {
        this.sqlColumn = sqlColumn;
    }

    public boolean isSupportLocate() {
        return this.supportLocate;
    }

    public void setSupportLocate(boolean supportLocate) {
        this.supportLocate = supportLocate;
    }
}

