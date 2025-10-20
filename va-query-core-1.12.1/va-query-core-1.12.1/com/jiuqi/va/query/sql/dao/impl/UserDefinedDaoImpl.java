/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.query.common.FormatNumberEnum
 *  com.jiuqi.va.query.exception.DefinedQueryRuntimeException
 *  com.jiuqi.va.query.exception.DefinedQuerySqlException
 *  com.jiuqi.va.query.sql.enumerate.DisplayFormatEnum
 *  com.jiuqi.va.query.template.enumerate.AlignEnum
 *  com.jiuqi.va.query.template.enumerate.AutoWidthTypeEnum
 *  com.jiuqi.va.query.template.enumerate.GatherTypeEnum
 *  com.jiuqi.va.query.template.enumerate.ParamTypeEnum
 *  com.jiuqi.va.query.template.vo.TemplateFieldSettingVO
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.va.query.sql.dao.impl;

import com.jiuqi.va.query.common.FormatNumberEnum;
import com.jiuqi.va.query.common.service.QuerySqlInterceptorUtil;
import com.jiuqi.va.query.datasource.service.DynamicDataSourceService;
import com.jiuqi.va.query.exception.DefinedQueryRuntimeException;
import com.jiuqi.va.query.exception.DefinedQuerySqlException;
import com.jiuqi.va.query.sql.dao.UserDefinedDao;
import com.jiuqi.va.query.sql.dto.PageSqlExecConditionDTO;
import com.jiuqi.va.query.sql.dto.SqlExecConditionDTO;
import com.jiuqi.va.query.sql.enumerate.DisplayFormatEnum;
import com.jiuqi.va.query.template.enumerate.AlignEnum;
import com.jiuqi.va.query.template.enumerate.AutoWidthTypeEnum;
import com.jiuqi.va.query.template.enumerate.GatherTypeEnum;
import com.jiuqi.va.query.template.enumerate.ParamTypeEnum;
import com.jiuqi.va.query.template.vo.TemplateFieldSettingVO;
import com.jiuqi.va.query.util.DCQueryJdbcUtils;
import com.jiuqi.va.query.util.DCQueryStringHandle;
import com.jiuqi.va.query.util.DCQueryUUIDUtil;
import com.jiuqi.va.query.util.QueryUtils;
import com.jiuqi.va.query.util.VAQueryI18nUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Repository
public class UserDefinedDaoImpl
implements UserDefinedDao {
    @Autowired
    private DynamicDataSourceService dataSourceService;
    private static final Logger logger = LoggerFactory.getLogger(UserDefinedDaoImpl.class);

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public List<TemplateFieldSettingVO> parsingSql(String dataSourceCode, SqlExecConditionDTO sqlExecConditionDTO) {
        LinkedList<TemplateFieldSettingVO> linkedList;
        ResultSetMetaData metaData;
        String sql;
        ResultSet rs;
        PreparedStatement pst;
        LinkedList<TemplateFieldSettingVO> fieldList;
        Connection connection;
        block7: {
            connection = null;
            fieldList = new LinkedList<TemplateFieldSettingVO>();
            pst = null;
            rs = null;
            sql = "";
            connection = this.dataSourceService.getConnection(dataSourceCode);
            sql = QuerySqlInterceptorUtil.getInterceptorSqlString(sqlExecConditionDTO.getSql());
            pst = connection.prepareStatement(sql);
            metaData = pst.getMetaData();
            if (metaData != null) break block7;
            DCQueryJdbcUtils.colseResource(null, pst, null);
            String pageSql = this.dataSourceService.getPageSql(dataSourceCode, sql, 1, 1);
            pst = connection.prepareStatement(pageSql);
            rs = pst.executeQuery();
            metaData = rs.getMetaData();
            if (metaData != null) break block7;
            List<TemplateFieldSettingVO> list = null;
            DCQueryJdbcUtils.colseResource(null, pst, rs);
            this.dataSourceService.closeConnection(dataSourceCode, connection);
            return list;
        }
        try {
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; ++i) {
                fieldList.add(this.initTemplateFieldSetting(metaData, i));
            }
            linkedList = fieldList;
        }
        catch (SQLException sqlException) {
            try {
                logger.error("sql \u89e3\u6790\u5931\u8d25\uff0c\u89e3\u6790\u540e\u53ef\u6267\u884cSQL\u5982\u4e0b\uff1a\n{}", (Object)sql, (Object)sqlException);
                throw new DefinedQuerySqlException((Throwable)sqlException);
                catch (Exception e) {
                    logger.error("sql \u89e3\u6790\u5931\u8d25\uff0c\u89e3\u6790\u540e\u53ef\u6267\u884cSQL\u5982\u4e0b\uff1a\n{}", (Object)sql, (Object)e);
                    throw new DefinedQueryRuntimeException("sql \u89e3\u6790\u5931\u8d25\uff0c\u8bf7\u68c0\u67e5\u81ea\u5b9a\u4e49\u67e5\u8be2\u914d\u7f6e\uff01", (Throwable)e);
                }
            }
            catch (Throwable throwable) {
                DCQueryJdbcUtils.colseResource(null, pst, rs);
                this.dataSourceService.closeConnection(dataSourceCode, connection);
                throw throwable;
            }
        }
        DCQueryJdbcUtils.colseResource(null, pst, rs);
        this.dataSourceService.closeConnection(dataSourceCode, connection);
        return linkedList;
    }

    private TemplateFieldSettingVO initTemplateFieldSetting(ResultSetMetaData metaData, int i) throws SQLException {
        TemplateFieldSettingVO field = new TemplateFieldSettingVO();
        field.setId(DCQueryUUIDUtil.getUUIDStr());
        field.setName(metaData.getColumnLabel(i));
        field.setTitle(metaData.getColumnLabel(i));
        field.setDataType(this.dataBaseTypeConvert(metaData.getColumnTypeName(i)));
        field.setAutoWidth(AutoWidthTypeEnum.ADAPT.getName());
        field.setGatherType(GatherTypeEnum.NO_AGREGATE.getTypeName());
        field.setVisibleFlag(true);
        field.setSortOrder(i);
        field.setAlign(ParamTypeEnum.NUMBER.getTypeName().equals(field.getDataType()) ? AlignEnum.RIGHT.getName() : AlignEnum.LEFT.getName());
        if (ParamTypeEnum.NUMBER.getTypeName().equals(field.getDataType())) {
            field.setDecimalLength(Integer.valueOf(2));
            field.setDisplayFormat(DisplayFormatEnum.CURRENCY.getTypeName());
        }
        if (ParamTypeEnum.DATE.getTypeName().equals(field.getDataType()) || ParamTypeEnum.DATE_TIME.getTypeName().equals(field.getDataType())) {
            field.setDisplayFormat(DisplayFormatEnum.DATE.getTypeName());
        }
        return field;
    }

    private String dataBaseTypeConvert(String columnTypeName) {
        String type;
        switch (columnTypeName) {
            case "INTEGER": {
                type = ParamTypeEnum.INTEGER.getTypeName();
                break;
            }
            case "FLOAT": 
            case "LONG": 
            case "DOUBLE": 
            case "DECIMAL": 
            case "DEC": 
            case "NUMBER": {
                type = ParamTypeEnum.NUMBER.getTypeName();
                break;
            }
            case "DATE": 
            case "TIMESTAMP": {
                type = ParamTypeEnum.DATE.getTypeName();
                break;
            }
            default: {
                type = ParamTypeEnum.STRING.getTypeName();
            }
        }
        return type;
    }

    @Override
    public List<Map<String, Object>> execSql(String dataSourceCode, PageSqlExecConditionDTO sqlExecCondition) {
        String sql = sqlExecCondition.getSql();
        Object[] args = sqlExecCondition.getArgs();
        List<TemplateFieldSettingVO> fieldList = sqlExecCondition.getFields();
        if (sqlExecCondition.isPageQuery()) {
            return this.dataSourceService.pageQuery(dataSourceCode, sql, (int)sqlExecCondition.getPageNumber(), (int)sqlExecCondition.getPageSize(), args, this.execSqlResultSetExtractor(fieldList, sqlExecCondition));
        }
        return this.dataSourceService.query(dataSourceCode, sql, args, this.execSqlResultSetExtractor(fieldList, sqlExecCondition));
    }

    private ResultSetExtractor<List<Map<String, Object>>> execSqlResultSetExtractor(List<TemplateFieldSettingVO> fieldList, PageSqlExecConditionDTO sqlExecCondition) {
        List<Object> collSpanColumns = CollectionUtils.isEmpty(sqlExecCondition.getCollSpanColumns()) ? new ArrayList() : sqlExecCondition.getCollSpanColumns();
        Map<String, TemplateFieldSettingVO> settingVOMap = collSpanColumns.stream().collect(Collectors.toMap(TemplateFieldSettingVO::getName, o -> o));
        String trueStr = VAQueryI18nUtil.getMessage("va.query.result.bool.trueValue");
        String falseStr = VAQueryI18nUtil.getMessage("va.query.result.bool.falseValue");
        String[] boolOptions = new String[]{trueStr, falseStr};
        return rs -> {
            ArrayList resultList = new ArrayList();
            while (rs.next()) {
                HashMap<String, Object> result = new HashMap<String, Object>();
                HashMap<String, String> originDataMap = new HashMap<String, String>();
                block8: for (TemplateFieldSettingVO field : fieldList) {
                    if (settingVOMap.containsKey(field.getName())) {
                        result.put(field.getName(), "");
                        continue;
                    }
                    ParamTypeEnum paramType = ParamTypeEnum.val((String)field.getDataType());
                    assert (paramType != null);
                    originDataMap.put(field.getName(), rs.getString(field.getName()));
                    switch (paramType) {
                        case STRING: {
                            result.put(field.getName(), rs.getString(field.getName()));
                            continue block8;
                        }
                        case NUMBER: {
                            result.put(field.getName(), this.formatNumber(sqlExecCondition, rs, field));
                            continue block8;
                        }
                        case INTEGER: {
                            result.put(field.getName(), rs.getInt(field.getName()));
                            continue block8;
                        }
                        case BOOL: {
                            result.put(field.getName(), this.formatBoolean(rs, field, boolOptions));
                            continue block8;
                        }
                        case DATE: {
                            String fieldValue = rs.getString(field.getName());
                            if (StringUtils.hasText(fieldValue)) {
                                Timestamp timestamp = rs.getTimestamp(field.getName());
                                if (timestamp != null) {
                                    originDataMap.put(field.getName(), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp.getTime()));
                                }
                                result.put(field.getName(), this.formatDate(rs, field));
                                continue block8;
                            }
                            result.put(field.getName(), null);
                            continue block8;
                        }
                    }
                    result.put(field.getName(), rs.getObject(field.getName()));
                }
                result.putIfAbsent("__ORIGIN_DATA_MAP__", originDataMap);
                resultList.add(result);
            }
            return resultList;
        };
    }

    private Object formatBoolean(ResultSet rs, TemplateFieldSettingVO field, String[] boolOptions) throws SQLException {
        boolean aBoolean = rs.getBoolean(field.getName());
        String displayFormat = field.getDisplayFormat();
        if (StringUtils.hasText(displayFormat)) {
            if (DisplayFormatEnum.SIMPLE_BOOLEAN.getTypeName().equals(displayFormat)) {
                if (aBoolean) {
                    return boolOptions[0];
                }
                return boolOptions[1];
            }
            return aBoolean;
        }
        return aBoolean;
    }

    @Override
    public Map<String, Object> calcTotalLine(String dataSourceCode, PageSqlExecConditionDTO sqlExecCondition) {
        String sql = sqlExecCondition.getSql();
        Object[] args = sqlExecCondition.getArgs();
        List<TemplateFieldSettingVO> fieldList = sqlExecCondition.getFields();
        return (Map)this.dataSourceService.query(dataSourceCode, sql, args, rs -> {
            HashMap<String, Object> result = new HashMap<String, Object>();
            HashMap<String, Object> originDataMap = new HashMap<String, Object>();
            while (rs.next()) {
                for (TemplateFieldSettingVO field : fieldList) {
                    result.put(field.getName(), this.formatNumber(sqlExecCondition, rs, field));
                    originDataMap.put(field.getName(), rs.getObject(field.getName()));
                }
            }
            result.putIfAbsent("__ORIGIN_DATA_MAP__", originDataMap);
            return result;
        });
    }

    private String formatDate(ResultSet rs, TemplateFieldSettingVO field) throws SQLException {
        Timestamp timestamp = rs.getTimestamp(field.getName());
        if (timestamp == null) {
            return null;
        }
        Date date = QueryUtils.convertTimeZone(timestamp);
        if (DCQueryStringHandle.isEmpty(field.getDisplayFormat())) {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        }
        if (DisplayFormatEnum.DATE.getTypeName().equals(field.getDisplayFormat())) {
            return new SimpleDateFormat(DisplayFormatEnum.DATE.getFormatString()).format(date);
        }
        if (DisplayFormatEnum.CHINESE_DATE.getTypeName().equals(field.getDisplayFormat())) {
            return new SimpleDateFormat(DisplayFormatEnum.CHINESE_DATE.getFormatString()).format(date);
        }
        if (DisplayFormatEnum.TIMESTAMP.getTypeName().equals(field.getDisplayFormat())) {
            return new SimpleDateFormat(DisplayFormatEnum.TIMESTAMP.getFormatString()).format(date);
        }
        if (DisplayFormatEnum.TIMESTAMP_SIMPLE.getTypeName().equals(field.getDisplayFormat())) {
            return new SimpleDateFormat(DisplayFormatEnum.TIMESTAMP_SIMPLE.getFormatString()).format(date);
        }
        if (DisplayFormatEnum.CHINESE_TIMESTAMP_SIMPLE.getTypeName().equals(field.getDisplayFormat())) {
            return new SimpleDateFormat(DisplayFormatEnum.CHINESE_TIMESTAMP_SIMPLE.getFormatString()).format(date);
        }
        return new SimpleDateFormat(DisplayFormatEnum.CHINESE_TIMESTAMP.getFormatString()).format(date);
    }

    private Object formatNumber(PageSqlExecConditionDTO sqlExecCondition, ResultSet rs, TemplateFieldSettingVO field) throws SQLException {
        if (rs.getObject(field.getName()) == null) {
            return null;
        }
        Double sqlNumber = rs.getDouble(field.getName());
        if (Objects.equals(field.getDataType(), "integer") && !Objects.equals(field.getGatherType(), "averge")) {
            return sqlNumber.intValue();
        }
        if (DCQueryStringHandle.isEmpty(field.getDisplayFormat()) || DisplayFormatEnum.CURRENCY.getTypeName().equals(field.getDisplayFormat())) {
            if (FormatNumberEnum.YES.equals((Object)sqlExecCondition.getFormatNumber())) {
                return this.getCurrencyString(sqlNumber, field);
            }
            if (FormatNumberEnum.NOT.equals((Object)sqlExecCondition.getFormatNumber())) {
                return sqlNumber;
            }
            if (!sqlExecCondition.isPageQuery()) {
                return sqlNumber;
            }
            return this.getCurrencyString(sqlNumber, field);
        }
        String number = sqlNumber.toString();
        String numberPlus = String.valueOf(sqlNumber * 100.0);
        if (field.getDecimalLength() != null) {
            String pattern = "0.00";
            DecimalFormat numberFormat = new DecimalFormat(pattern);
            numberFormat.setMinimumFractionDigits(field.getDecimalLength());
            numberFormat.setMaximumFractionDigits(field.getDecimalLength());
            number = numberFormat.format(sqlNumber);
            numberPlus = numberFormat.format(sqlNumber * 100.0);
        }
        if (DisplayFormatEnum.DEFAULT.getTypeName().equals(field.getDisplayFormat())) {
            return number;
        }
        if (DisplayFormatEnum.PERCENT.getTypeName().equals(field.getDisplayFormat())) {
            return number + "%";
        }
        if (DisplayFormatEnum.AUTO_PERCENT.getTypeName().equals(field.getDisplayFormat())) {
            return numberPlus + "%";
        }
        return number;
    }

    private String getCurrencyString(Double sqlNumber, TemplateFieldSettingVO field) {
        String pattern = "#,##0.00";
        DecimalFormat numberFormat = new DecimalFormat(pattern);
        if (field.getDecimalLength() != null) {
            numberFormat.setMinimumFractionDigits(field.getDecimalLength());
            numberFormat.setMaximumFractionDigits(field.getDecimalLength());
        }
        return numberFormat.format(sqlNumber);
    }

    @Override
    public int getTotalCount(String dataSourceCode, SqlExecConditionDTO sqlExecConditionDTO) {
        String countSql = " select count(1) from ( %1$s ) t where 1=1 \n";
        String sql = sqlExecConditionDTO.getSql();
        Object[] args = sqlExecConditionDTO.getArgs();
        return (Integer)this.dataSourceService.query(dataSourceCode, String.format(countSql, sql), args, rs -> {
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        });
    }

    @Override
    public List<String[]> getFetchResultRowDatas(String dataSourceCode, PageSqlExecConditionDTO sqlExecCondition) {
        String sql = sqlExecCondition.getSql();
        Object[] args = sqlExecCondition.getArgs();
        List<TemplateFieldSettingVO> fieldList = sqlExecCondition.getFields();
        return (List)this.dataSourceService.query(dataSourceCode, sql, args, rs -> {
            ArrayList<String[]> rowDatas = new ArrayList<String[]>();
            while (rs.next()) {
                String[] rowData = new String[fieldList.size()];
                block5: for (int i = 0; i < fieldList.size(); ++i) {
                    TemplateFieldSettingVO field = (TemplateFieldSettingVO)fieldList.get(i);
                    ParamTypeEnum paramType = ParamTypeEnum.val((String)field.getDataType());
                    assert (paramType != null);
                    switch (paramType) {
                        case NUMBER: {
                            rowData[i] = this.featchQueryFormatNumber(sqlExecCondition, rs, field);
                            continue block5;
                        }
                        case DATE: {
                            rowData[i] = this.formatDate(rs, field);
                            continue block5;
                        }
                        default: {
                            rowData[i] = rs.getString(field.getName());
                        }
                    }
                }
                rowDatas.add(rowData);
            }
            return rowDatas;
        });
    }

    @Override
    public void batchInsertTempTable(String dataSourceCode, String queryKey, String filedName, List<String> list) {
        String sql = " insert into DC_QUERY_TEMPTABLE (queryKey," + filedName + ") values(?,?) ";
        ArrayList<Object[]> batchArgs = new ArrayList<Object[]>();
        for (String value : list) {
            batchArgs.add(new Object[]{queryKey, value});
        }
        this.dataSourceService.batchUpdate(dataSourceCode, sql, batchArgs);
    }

    @Override
    public void clearTempTable(String dataSourceCode, String sn) {
        String sql = "delete from DC_QUERY_TEMPTABLE where queryKey like '" + sn + "%'";
        this.dataSourceService.execute(dataSourceCode, sql);
    }

    private String featchQueryFormatNumber(PageSqlExecConditionDTO sqlExecCondition, ResultSet rs, TemplateFieldSettingVO field) throws SQLException {
        if (rs.getObject(field.getName()) == null) {
            return null;
        }
        double sqlNumber = rs.getDouble(field.getName());
        if (DCQueryStringHandle.isEmpty(field.getDisplayFormat())) {
            String pattern = "0.00";
            DecimalFormat numberFormat = new DecimalFormat(pattern);
            if (field.getDecimalLength() != null) {
                numberFormat.setMinimumFractionDigits(field.getDecimalLength());
                numberFormat.setMaximumFractionDigits(field.getDecimalLength());
            }
            return numberFormat.format(sqlNumber);
        }
        Object number = this.formatNumber(sqlExecCondition, rs, field);
        if (number == null) {
            return null;
        }
        return number.toString();
    }
}

