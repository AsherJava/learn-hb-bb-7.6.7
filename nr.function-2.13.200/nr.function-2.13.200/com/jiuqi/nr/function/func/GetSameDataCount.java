/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.parse.IReportFunction
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.log.BeanUtils
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.nr.function.func;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.parse.IReportFunction;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.log.BeanUtils;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.function.func.cache.SameDataCountCache;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.util.StringUtils;

public class GetSameDataCount
extends Function
implements IReportFunction {
    private static final long serialVersionUID = -1600568757014643971L;

    public GetSameDataCount() {
        this.parameters().add(new Parameter("fieldCode", 0, "\u6307\u6807\u6807\u8bc6"));
    }

    public String name() {
        return "GETSAMEDATACOUNT";
    }

    public String title() {
        return "\u67e5\u8be2\u6570\u636e\u7684\u91cd\u590d\u6b21\u6570";
    }

    public int getResultType(IContext iContext, List<IASTNode> list) throws SyntaxException {
        return 3;
    }

    public String category() {
        return "\u5176\u5b83\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        QueryContext qContext = null;
        try {
            if (context instanceof QueryContext) {
                qContext = (QueryContext)context;
                Object evaluate = parameters.get(0).evaluate(context);
                if (evaluate == null) {
                    return 0;
                }
                ExecutorContext exeContext = qContext.getExeContext();
                IASTNode p0 = parameters.get(0);
                ColumnModelDefine fieldDefine = exeContext.getCache().extractFieldDefine(p0);
                if (fieldDefine != null) {
                    String valueStr = "";
                    ColumnModelType columnType = fieldDefine.getColumnType();
                    if (columnType == ColumnModelType.STRING || columnType == ColumnModelType.DOUBLE || columnType == ColumnModelType.INTEGER) {
                        valueStr = evaluate.toString();
                    } else if (columnType == ColumnModelType.BIGDECIMAL) {
                        valueStr = ((BigDecimal)evaluate).toPlainString();
                    } else {
                        qContext.getMonitor().exception((Exception)((Object)new SyntaxException("\u4e0d\u652f\u6301\u7684\u7c7b\u578b")));
                    }
                    String cacheKey = this.getCacheKey(qContext, fieldDefine);
                    SameDataCountCache cache = (SameDataCountCache)qContext.getCache().get(cacheKey);
                    if (cache == null) {
                        cache = this.loadCache(qContext, fieldDefine);
                    }
                    Map<String, Integer> map = cache.getDataCounts();
                    return map.getOrDefault(this.getCurrentUnitCode(qContext, cache) + "_" + valueStr, 0);
                }
            }
        }
        catch (Exception e) {
            qContext.getMonitor().exception(e);
        }
        return 0;
    }

    private String getCurrentUnitCode(QueryContext queryContext, SameDataCountCache cache) {
        DimensionValueSet currentMasterKey = queryContext.getCurrentMasterKey();
        List<String> dimensionNames = cache.getDimensionNames();
        ArrayList<String> dimValues = new ArrayList<String>();
        for (String dimensionName : dimensionNames) {
            dimValues.add(currentMasterKey.getValue(dimensionName).toString());
        }
        return String.join((CharSequence)"_", dimValues);
    }

    private String getUnitDim(QueryContext queryContext) {
        return queryContext.getExeContext().getEnv().getUnitDimesion(queryContext.getExeContext());
    }

    private String getCacheKey(QueryContext qContext, ColumnModelDefine fieldDefine) throws ParseException {
        TableModelDefine tableModel = qContext.getExeContext().getCache().getDataModelDefinitionsCache().getTableModel(fieldDefine);
        return qContext.getMasterKeys().toString() + "_" + tableModel.getName() + "_" + fieldDefine.getCode();
    }

    private SameDataCountCache loadCache(QueryContext queryContext, ColumnModelDefine fieldDefine) throws Exception {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        DataModelDefinitionsCache dataModelCache = queryContext.getExeContext().getCache().getDataModelDefinitionsCache();
        TableModelDefine tableModel = dataModelCache.getTableModel(fieldDefine);
        SameDataCountCache cache = this.getDimCodes(queryContext);
        String tableName = tableModel.getName();
        String fieldName = fieldDefine.getName();
        String unitDim = this.getUnitDim(queryContext);
        Map<String, String> dimensionName2DimCode = cache.getDimensionName2DimCode();
        dimensionName2DimCode.put(unitDim, "MDCODE");
        List<String> dimensionNames = cache.getDimensionNames();
        dimensionNames.add(unitDim);
        HashMap<String, String> multiDimMap = new HashMap<String, String>();
        HashMap<String, String> singleDimMap = new HashMap<String, String>();
        DimensionValueSet masterKeys = queryContext.getMasterKeys();
        for (String dimName : dimensionNames) {
            Iterator<Object> valuesObj = masterKeys.getValue(dimName);
            if (valuesObj == null) {
                throw new SyntaxException("\u7ef4\u5ea6\u7f3a\u5931");
            }
            if (valuesObj instanceof List) {
                List list = (List)((Object)valuesObj);
                ArrayList<String> sqlValues = new ArrayList<String>();
                for (String valueObj : list) {
                    sqlValues.add("'" + valueObj + "'");
                }
                multiDimMap.put(dimName, String.join((CharSequence)",", sqlValues));
                continue;
            }
            singleDimMap.put(dimName, valuesObj.toString());
        }
        StringBuilder sb = new StringBuilder();
        sb.append("select count(").append(fieldName).append(") as c_1,").append(fieldName).append(" as c_2,");
        Collection<String> dimCodes = dimensionName2DimCode.values();
        for (String string : dimCodes) {
            sb.append(string).append(",");
        }
        sb.setLength(sb.length() - 1);
        sb.append(" from ").append(tableName).append(" where ");
        for (Map.Entry entry : multiDimMap.entrySet()) {
            sb.append(dimensionName2DimCode.get(entry.getKey())).append(" in (").append((String)entry.getValue()).append(")").append(" and ");
        }
        for (Map.Entry entry : singleDimMap.entrySet()) {
            sb.append(dimensionName2DimCode.get(entry.getKey())).append(" = '").append((String)entry.getValue()).append("' and ");
        }
        sb.append("DATATIME").append("='").append(masterKeys.getValue("DATATIME").toString()).append("' group by ").append(fieldName).append(",");
        for (String string : dimCodes) {
            sb.append(string).append(",");
        }
        sb.setLength(sb.length() - 1);
        DataSource dataSource = (DataSource)BeanUtils.getBean(DataSource.class);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sb.toString());
             ResultSet resultSet = preparedStatement.executeQuery();){
            while (resultSet.next()) {
                ColumnModelType columnType = fieldDefine.getColumnType();
                String value = "";
                if (columnType == ColumnModelType.STRING) {
                    value = resultSet.getString("c_2");
                } else if (columnType == ColumnModelType.DOUBLE || columnType == ColumnModelType.INTEGER || columnType == ColumnModelType.BIGDECIMAL) {
                    BigDecimal c2 = resultSet.getBigDecimal("c_2");
                    c2 = c2.setScale(fieldDefine.getDecimal(), RoundingMode.UNNECESSARY);
                    value = c2.toPlainString();
                }
                if (!StringUtils.hasLength(value)) continue;
                int count = resultSet.getInt("c_1");
                ArrayList<String> dimValues = new ArrayList<String>();
                for (String dimensionName : dimensionNames) {
                    dimValues.add(resultSet.getString(dimensionName2DimCode.get(dimensionName)));
                }
                map.put(String.join((CharSequence)"_", dimValues) + "_" + value, count);
            }
        }
        cache.setDataCounts(map);
        String string = this.getCacheKey(queryContext, fieldDefine);
        queryContext.getCache().put(string, cache);
        return cache;
    }

    private SameDataCountCache getDimCodes(QueryContext queryContext) {
        TaskDefine taskDefine;
        ReportFmlExecEnvironment fmlExecEnvironment;
        String formSchemeKey;
        SameDataCountCache cache = new SameDataCountCache();
        ExecutorContext exeContext = queryContext.getExeContext();
        IFmlExecEnvironment env = exeContext.getEnv();
        HashMap<String, String> map = new HashMap<String, String>();
        cache.setDimensionName2DimCode(map);
        ArrayList<String> dimensionNames = new ArrayList<String>();
        cache.setDimensionNames(dimensionNames);
        if (env instanceof ReportFmlExecEnvironment && StringUtils.hasLength(formSchemeKey = (fmlExecEnvironment = (ReportFmlExecEnvironment)env).getFormSchemeKey()) && (taskDefine = fmlExecEnvironment.getTaskDefine()) != null) {
            IRuntimeDataSchemeService service = (IRuntimeDataSchemeService)BeanUtils.getBean(IRuntimeDataSchemeService.class);
            IEntityMetaService entityMetaService = (IEntityMetaService)BeanUtils.getBean(IEntityMetaService.class);
            List dataSchemeDimension = service.getDataSchemeDimension(taskDefine.getDataScheme(), DimensionType.DIMENSION);
            dataSchemeDimension.sort(null);
            for (DataDimension dim : dataSchemeDimension) {
                if ("ADJUST".equals(dim.getDimKey())) {
                    map.put("ADJUST", "ADJUST");
                    dimensionNames.add("ADJUST");
                    continue;
                }
                IEntityDefine iEntityDefine = entityMetaService.queryEntity(dim.getDimKey());
                if (iEntityDefine == null) continue;
                map.put(iEntityDefine.getDimensionName(), iEntityDefine.getCode());
                dimensionNames.add(iEntityDefine.getDimensionName());
            }
        }
        return cache;
    }
}

