/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.distributeds.IDistributeDsValueProvider
 *  com.jiuqi.bi.sql.ConnectionWapper
 *  com.jiuqi.bi.sql.IConnectionProvider
 *  com.jiuqi.bi.syntax.dynamic.IDynamicNodeProvider
 *  com.jiuqi.bi.syntax.function.IFunction
 *  com.jiuqi.bi.syntax.function.IFunctionProvider
 *  com.jiuqi.bi.syntax.sql.ISQLQueryListener
 *  com.jiuqi.bi.syntax.sql.RangeValues
 *  com.jiuqi.bi.syntax.sql.SQLQueryExecutor
 *  com.jiuqi.bi.text.DateFormatEx
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nvwa.framework.parameter.IParameterEnv
 *  com.jiuqi.nvwa.framework.parameter.ParameterException
 *  com.jiuqi.nvwa.framework.parameter.syntax.ParamNodeProvider
 *  com.jiuqi.nvwa.framework.parameter.syntax.ParamTitle
 *  com.jiuqi.nvwa.framework.parameter.syntax.ParamValue
 *  com.jiuqi.nvwa.framework.parameter.syntax.SQLFilterByParam
 */
package com.jiuqi.bi.dataset.sql;

import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.DataType;
import com.jiuqi.bi.dataset.FilterItem;
import com.jiuqi.bi.dataset.IDSContext;
import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.model.field.DSField;
import com.jiuqi.bi.dataset.model.field.TimeGranularity;
import com.jiuqi.bi.dataset.sql.DistributeDsValueProviderFactoryManager;
import com.jiuqi.bi.dataset.sql.SQLConnectionProviderManager;
import com.jiuqi.bi.dataset.sql.SQLModel;
import com.jiuqi.bi.dataset.sql.SQLParamQueryListener;
import com.jiuqi.bi.dataset.sql.SQLQueryExecutorFactoryManager;
import com.jiuqi.bi.distributeds.IDistributeDsValueProvider;
import com.jiuqi.bi.sql.ConnectionWapper;
import com.jiuqi.bi.sql.IConnectionProvider;
import com.jiuqi.bi.syntax.dynamic.IDynamicNodeProvider;
import com.jiuqi.bi.syntax.function.IFunction;
import com.jiuqi.bi.syntax.function.IFunctionProvider;
import com.jiuqi.bi.syntax.sql.ISQLQueryListener;
import com.jiuqi.bi.syntax.sql.RangeValues;
import com.jiuqi.bi.syntax.sql.SQLQueryExecutor;
import com.jiuqi.bi.text.DateFormatEx;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nvwa.framework.parameter.IParameterEnv;
import com.jiuqi.nvwa.framework.parameter.ParameterException;
import com.jiuqi.nvwa.framework.parameter.syntax.ParamNodeProvider;
import com.jiuqi.nvwa.framework.parameter.syntax.ParamTitle;
import com.jiuqi.nvwa.framework.parameter.syntax.ParamValue;
import com.jiuqi.nvwa.framework.parameter.syntax.SQLFilterByParam;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class SQLDataSetUtils {
    public static SQLQueryExecutor createSQLQueryExecutor(Connection conn, IDSContext context) throws BIDataSetException {
        String userGuid = context.getUserGuid();
        String dsGuid = context.getDataSrcGuid();
        SQLQueryExecutor executor = SQLQueryExecutorFactoryManager.getInstance().createQueryExecutor(conn, userGuid, dsGuid);
        executor.registerListener((ISQLQueryListener)new SQLParamQueryListener(context));
        IFunctionProvider functionProvider = SQLConnectionProviderManager.getInstance().getFunctionProvider();
        if (functionProvider != null) {
            Iterator iter = functionProvider.iterator();
            while (iter.hasNext()) {
                executor.registerFunction((IFunction)iter.next());
            }
        }
        executor.registerFunction((IFunction)new SQLFilterByParam(conn));
        executor.registerFunction((IFunction)new ParamValue());
        executor.registerFunction((IFunction)new ParamTitle());
        try {
            executor.registerDynamicProvider((IDynamicNodeProvider)new ParamNodeProvider(context.getEnhancedParameterEnv()));
        }
        catch (ParameterException e) {
            throw new BIDataSetException(e.getMessage(), e);
        }
        return executor;
    }

    public static Connection getConnection1(SQLModel model) throws BIDataSetException, SQLException {
        IConnectionProvider provider = SQLConnectionProviderManager.getInstance().getConnectionProvider();
        if (provider == null) {
            throw new BIDataSetException("\u672a\u6ce8\u518c\u6570\u636e\u8fde\u63a5\u63d0\u4f9b\u5668\uff0c\u65e0\u6cd5\u83b7\u53d6\u6570\u636e\u5e93\u8fde\u63a5");
        }
        return provider.open(model.getDataSourceId());
    }

    public static ConnectionWapper[] getConnections(SQLModel model, IParameterEnv env) throws BIDataSetException, SQLException {
        IConnectionProvider provider = SQLConnectionProviderManager.getInstance().getConnectionProvider();
        if (provider == null) {
            throw new BIDataSetException("\u672a\u6ce8\u518c\u6570\u636e\u8fde\u63a5\u63d0\u4f9b\u5668\uff0c\u65e0\u6cd5\u83b7\u53d6\u6570\u636e\u5e93\u8fde\u63a5");
        }
        IDistributeDsValueProvider distributeDsValueProvider = DistributeDsValueProviderFactoryManager.getInstance().createProvider(env);
        return provider.openDistributeConnecion(model.getDataSourceId(), distributeDsValueProvider);
    }

    public static boolean isMultipleConnection(SQLModel model, IParameterEnv env) throws BIDataSetException, SQLException {
        IConnectionProvider provider = SQLConnectionProviderManager.getInstance().getConnectionProvider();
        if (provider == null) {
            throw new BIDataSetException("\u672a\u6ce8\u518c\u6570\u636e\u8fde\u63a5\u63d0\u4f9b\u5668\uff0c\u65e0\u6cd5\u83b7\u53d6\u6570\u636e\u5e93\u8fde\u63a5");
        }
        IDistributeDsValueProvider distributeDsValueProvider = DistributeDsValueProviderFactoryManager.getInstance().createProvider(env);
        return provider.isMultipleConnection(model.getDataSourceId(), distributeDsValueProvider);
    }

    public static void makeSQLExecutorFilter(DSModel model, RangeValues timekeyRange, FilterItem[] filters, Map<String, Object> keyValueFilters, String[] exprFilter) throws Exception {
        SQLDataSetUtils.makeSQLExecutorFilter(model, timekeyRange, filters, keyValueFilters, exprFilter, null);
    }

    public static void makeSQLExecutorFilter(DSModel model, RangeValues timekeyRange, FilterItem[] filters, Map<String, Object> keyValueFilters, String[] exprFilter, String lang) throws Exception {
        if (timekeyRange == null && (filters == null || filters.length == 0)) {
            return;
        }
        ArrayList<String> exprFilters = new ArrayList<String>();
        for (FilterItem filter : filters) {
            if (filter.getRange() != null) {
                keyValueFilters.put(filter.getFieldName(), filter.getRange());
                continue;
            }
            if (filter.getKeyList() != null && !filter.getKeyList().isEmpty()) {
                keyValueFilters.put(filter.getFieldName(), filter.getKeyList());
                continue;
            }
            if (filter.getExpr() == null || filter.getExpr().length() <= 0) continue;
            exprFilters.add(filter.getExpr());
        }
        StringBuffer exprBuf = new StringBuffer();
        if (timekeyRange != null) {
            String startTimekey = (String)timekeyRange.min;
            String endTimekey = (String)timekeyRange.max;
            Locale locale = StringUtils.isEmpty((String)lang) ? Locale.getDefault() : Locale.forLanguageTag(lang);
            String timekeyFilter = SQLDataSetUtils.translateTimekeyRangeToFilterExpr(model.getCommonFields(), startTimekey, endTimekey, locale);
            if (StringUtils.isNotEmpty((String)timekeyFilter)) {
                exprBuf.append("(").append(timekeyFilter).append(")");
            }
        }
        for (String expr : exprFilters) {
            if (exprBuf.length() > 0) {
                exprBuf.append(" and ");
            }
            exprBuf.append("(").append(expr).append(")");
        }
        exprFilter[0] = exprBuf.toString();
    }

    public static String translateTimekeyRangeToFilterExpr(List<DSField> fields, String startTimekey, String endTimekey) throws Exception {
        return SQLDataSetUtils.translateTimekeyRangeToFilterExpr(fields, startTimekey, endTimekey, Locale.getDefault());
    }

    public static String translateTimekeyRangeToFilterExpr(List<DSField> fields, String startTimekey, String endTimekey, Locale locale) throws Exception {
        List<DSField> timeFields = SQLDataSetUtils.getTimeFieldList(fields);
        if (timeFields.size() == 0) {
            return null;
        }
        if (StringUtils.isEmpty((String)startTimekey)) {
            startTimekey = "19700101";
        }
        if (StringUtils.isEmpty((String)endTimekey)) {
            endTimekey = "20991231";
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Date startDate = format.parse(startTimekey);
        Date endDate = format.parse(endTimekey);
        Calendar c1 = Calendar.getInstance();
        c1.setTime(startDate);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(endDate);
        int y1 = c1.get(1);
        int y2 = c2.get(1);
        ArrayList<Calendar> list = new ArrayList<Calendar>();
        list.add(c1);
        for (int y = y1; y < y2; ++y) {
            Calendar c = Calendar.getInstance();
            c.set(y, 11, 31);
            list.add(c);
            c = Calendar.getInstance();
            c.set(y + 1, 0, 1);
            list.add(c);
        }
        list.add(c2);
        ArrayList<String> filters = new ArrayList<String>();
        for (int i = 0; i < list.size(); i += 2) {
            String intervalFilter = SQLDataSetUtils.getIntervalFilter(timeFields, (Calendar)list.get(i), (Calendar)list.get(i + 1), locale);
            filters.add("(" + intervalFilter + ")");
        }
        return StringUtils.join(filters.iterator(), (String)" or ");
    }

    private static List<DSField> getTimeFieldList(List<DSField> fields) {
        ArrayList<DSField> timeFields = new ArrayList<DSField>();
        for (DSField field : fields) {
            if (!field.getFieldType().isTimeDimField()) continue;
            timeFields.add(field);
            if (!field.isTimekey()) continue;
            timeFields.clear();
            timeFields.add(field);
            break;
        }
        return timeFields;
    }

    private static String getIntervalFilter(List<DSField> timeFields, Calendar c1, Calendar c2, Locale locale) {
        ArrayList<String> rowFilterItems = new ArrayList<String>();
        for (DSField field : timeFields) {
            int dataType = field.getValType();
            TimeGranularity tg = field.getTimegranularity();
            StringBuffer buf = new StringBuffer();
            if (dataType == DataType.STRING.value()) {
                String pattern = field.getDataPattern();
                if (StringUtils.isEmpty((String)pattern)) {
                    pattern = "yyyyMMdd";
                }
                DateFormatEx format = new DateFormatEx(pattern, locale);
                buf.append(field.getName()).append(">=\"").append(format.format(c1.getTime())).append("\"");
                buf.append(" and ").append(field.getName()).append("<=\"").append(format.format(c2.getTime())).append("\"");
            } else if (dataType == DataType.INTEGER.value()) {
                int end;
                int start;
                if (tg == TimeGranularity.YEAR) {
                    start = c1.get(1);
                    end = c2.get(1);
                } else if (tg == TimeGranularity.MONTH) {
                    start = c1.get(2) + 1;
                    end = c2.get(2) + 1;
                } else if (tg == TimeGranularity.DAY) {
                    start = c1.get(5);
                    end = c2.get(5);
                } else {
                    if (tg == TimeGranularity.HALFYEAR) continue;
                    if (tg == TimeGranularity.QUARTER) {
                        int m1 = c1.get(2);
                        int m2 = c2.get(2);
                        start = m1 / 3 + 1;
                        end = m2 / 3 + 1;
                    } else {
                        if (tg != TimeGranularity.XUN) continue;
                        continue;
                    }
                }
                if (start == end) {
                    buf.append(field.getName()).append("=").append(start);
                } else {
                    buf.append(field.getName()).append(">=").append(start);
                    buf.append(" and ").append(field.getName()).append("<=").append(end);
                }
            } else if (dataType == DataType.DATETIME.value()) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                buf.append(field.getName()).append(">='").append(format.format(c1.getTime())).append("'");
                buf.append(" and ").append(field.getName()).append("<='").append(format.format(c2.getTime())).append("'");
            }
            if (buf.length() <= 0) continue;
            rowFilterItems.add(buf.toString());
        }
        return StringUtils.join(rowFilterItems.iterator(), (String)" and ");
    }
}

