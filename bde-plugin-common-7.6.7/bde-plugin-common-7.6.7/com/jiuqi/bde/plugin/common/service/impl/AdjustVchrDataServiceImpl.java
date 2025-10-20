/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.FetchData
 *  com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition
 *  com.jiuqi.bde.bizmodel.execute.intf.FetchDataExtractor
 *  com.jiuqi.bde.bizmodel.execute.util.TempTableUtils
 *  com.jiuqi.bde.common.constant.DcAdjustVchrSysOptionEnum
 *  com.jiuqi.bde.common.constant.PeriodTypeEnum
 *  com.jiuqi.bde.common.dto.DimensionValue
 *  com.jiuqi.bde.common.util.Variable
 *  com.jiuqi.bde.common.util.VariableParseUtil
 *  com.jiuqi.bde.log.utils.BdeLogUtil
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.OuterDataSourceUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.dc.base.client.assistdim.enums.AssistDimEffectTableEnum
 *  com.jiuqi.dc.base.common.jdbc.service.SqlRecordService
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 *  com.jiuqi.gcreport.dimension.service.DimensionService
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  org.apache.commons.collections4.MapUtils
 *  org.apache.shiro.util.ThreadContext
 *  org.springframework.jdbc.BadSqlGrammarException
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.plugin.common.service.impl;

import com.jiuqi.bde.bizmodel.execute.FetchData;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.bizmodel.execute.intf.FetchDataExtractor;
import com.jiuqi.bde.bizmodel.execute.util.TempTableUtils;
import com.jiuqi.bde.common.constant.DcAdjustVchrSysOptionEnum;
import com.jiuqi.bde.common.constant.PeriodTypeEnum;
import com.jiuqi.bde.common.dto.DimensionValue;
import com.jiuqi.bde.common.util.Variable;
import com.jiuqi.bde.common.util.VariableParseUtil;
import com.jiuqi.bde.log.utils.BdeLogUtil;
import com.jiuqi.bde.plugin.common.service.AdjustVchrDataService;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.OuterDataSourceUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.dc.base.client.assistdim.enums.AssistDimEffectTableEnum;
import com.jiuqi.dc.base.common.jdbc.service.SqlRecordService;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import com.jiuqi.gcreport.dimension.service.DimensionService;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import org.apache.commons.collections4.MapUtils;
import org.apache.shiro.util.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;

@Service
public class AdjustVchrDataServiceImpl
implements AdjustVchrDataService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private DataSourceService dataSourceService;
    @Autowired
    private DimensionService dimensionService;
    @Autowired
    private INvwaSystemOptionService sysOptionService;
    @Autowired
    private SqlRecordService sqlRecordService;

    @Override
    public FetchData getAdjustVchrFetchData(BalanceCondition condi) {
        List dimensionVOS = this.dimensionService.findDimFieldsVOByTableName(AssistDimEffectTableEnum.DC_ADJUSTVCHRITEM.name());
        List dimensionCodeList = dimensionVOS.stream().filter(vo -> vo.getPublishedFlag() != null && vo.getPublishedFlag().equals(1)).map(DimensionVO::getCode).collect(Collectors.toList());
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT A.SUBJECTCODE                                                      AS SUBJECTCODE, \n");
        sql.append("       A.CURRENCYCODE                                                     AS CURRENCYCODE, \n");
        sql.append("       1 AS ORIENT,  \n");
        sql.append("       0                                                                  AS NC, \n");
        sql.append("       0                                                                  AS C, \n");
        sql.append("       SUM(A.DEBIT)                                                       AS JF, \n");
        sql.append("       SUM(A.CREDIT)                                                      AS DF, \n");
        sql.append("       SUM(A.DEBIT)                                                       AS JL, \n");
        sql.append("       SUM(A.CREDIT)                                                      AS DL, \n");
        sql.append("       SUM(A.DEBIT - A.CREDIT)                                            AS YE, \n");
        sql.append("       0                                                                  AS WNC, \n");
        sql.append("       0                                                                  AS WC, \n");
        sql.append("       SUM(A.ORGND)                                                       AS WJF, \n");
        sql.append("       SUM(A.ORGNC)                                                       AS WDF, \n");
        sql.append("       SUM(A.ORGND)                                                       AS WJL, \n");
        sql.append("       SUM(A.ORGNC)                                                       AS WDL, \n");
        sql.append("       SUM(A.ORGND - A.ORGNC)                                             AS WYE \n");
        sql.append("       ${ASSIST_FIELD_SQL}");
        sql.append("  FROM DC_ADJUSTVCHRITEM A \n");
        sql.append(" WHERE 1 = 1 \n");
        sql.append(String.format(" AND A.UNITCODE = '%s' \n", condi.getUnitCode()));
        sql.append(String.format(" AND A.ACCTYEAR = %s \n", condi.getAcctYear()));
        sql.append("   AND A.ACCTPERIOD >= '${STARTPERIOD}' \n");
        sql.append("   AND A.ACCTPERIOD <= '${ENDPERIOD}' \n");
        sql.append("   ${ADJUST_VCHR_PERIOD_TYPE} \n");
        sql.append(TempTableUtils.buildSubjectCondiSql((BalanceCondition)condi, (String)"A.SUBJECTCODE"));
        sql.append(" GROUP BY A.SUBJECTCODE, A.CURRENCYCODE ${ASSIST_GROUP_SQL} \n");
        StringBuilder assistFieldSql = new StringBuilder();
        StringBuilder assistGroupSql = new StringBuilder();
        Variable variable = new Variable();
        if (!CollectionUtils.isEmpty((Collection)condi.getAssTypeList())) {
            for (String dimensionCode : dimensionCodeList) {
                assistFieldSql.append(String.format(",A.%1$s AS %1$s \n", dimensionCode));
                assistGroupSql.append(String.format(", A.%1$s", dimensionCode));
            }
        }
        String periodScheme = "";
        if (MapUtils.isNotEmpty((Map)condi.getDimensionValueMap()) && condi.getDimensionValueMap().containsKey("DATATIME")) {
            periodScheme = ((DimensionValue)condi.getDimensionValueMap().get("DATATIME")).getValue();
        }
        variable.put("ADJUST_VCHR_PERIOD_TYPE", this.buildAdjustVchrPeriodTypeCondi(periodScheme));
        variable.put("ASSIST_FIELD_SQL", assistFieldSql.toString());
        variable.put("ASSIST_GROUP_SQL", assistGroupSql.toString());
        variable.put("STARTPERIOD", String.format("%02d", condi.getStartPeriod()));
        variable.put("ENDPERIOD", String.format("%02d", condi.getEndPeriod()));
        String lastSql = VariableParseUtil.parse((String)sql.toString(), (Map)variable.getVariableMap());
        BdeLogUtil.recordLog((String)condi.getRequestTaskId(), (String)"\u8c03\u6574\u51ed\u8bc1\u53d6\u6570", (Object)condi, (String)lastSql);
        return this.query(lastSql, new Object[0]);
    }

    @Override
    public FetchData getAdjustVchrXjllFetchData(BalanceCondition condi) {
        List dimensionVOS = this.dimensionService.findDimFieldsVOByTableName(AssistDimEffectTableEnum.DC_ADJUSTVCHRITEM.name());
        List dimensionCodeList = dimensionVOS.stream().filter(vo -> vo.getPublishedFlag() != null && vo.getPublishedFlag().equals(1)).map(DimensionVO::getCode).collect(Collectors.toList());
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT A.SUBJECTCODE                                                      AS SUBJECTCODE, \n");
        sql.append("       A.CFITEMCODE                                                       AS CFITEMCODE, \n");
        sql.append("       A.CURRENCYCODE                                                     AS CURRENCYCODE, \n");
        sql.append("       1 AS ORIENT,  \n");
        sql.append("       SUM(A.DEBIT - A.CREDIT)                                            AS BQNUM,\n");
        sql.append("       SUM(A.ORGND - A.ORGNC)                                             AS WBQNUM,\n");
        sql.append("       SUM(A.DEBIT - A.CREDIT)                                            AS LJNUM,\n");
        sql.append("       SUM(A.ORGND - A.ORGNC)                                             AS WLJNUM\n");
        sql.append("       ${ASSIST_FIELD_SQL}");
        sql.append("  FROM DC_ADJUSTVCHRITEM A \n");
        sql.append(" WHERE 1 = 1 \n");
        sql.append(String.format(" AND A.UNITCODE = '%s' \n", condi.getUnitCode()));
        sql.append(String.format(" AND A.ACCTYEAR = %s \n", condi.getAcctYear()));
        sql.append("   AND A.ACCTPERIOD >= '${STARTPERIOD}' \n");
        sql.append("   AND A.ACCTPERIOD <= '${ENDPERIOD}' \n");
        sql.append("   ${ADJUST_VCHR_PERIOD_TYPE} \n");
        sql.append(" GROUP BY A.SUBJECTCODE, A.CFITEMCODE, A.CURRENCYCODE ${ASSIST_GROUP_SQL} \n");
        StringBuilder assistFieldSql = new StringBuilder();
        StringBuilder assistGroupSql = new StringBuilder();
        Variable variable = new Variable();
        if (!CollectionUtils.isEmpty((Collection)condi.getAssTypeList())) {
            for (String dimensionCode : dimensionCodeList) {
                assistFieldSql.append(String.format(",A.%1$s AS %1$s \n", dimensionCode));
                assistGroupSql.append(String.format(", A.%1$s", dimensionCode));
            }
        }
        String periodScheme = "";
        if (MapUtils.isNotEmpty((Map)condi.getDimensionValueMap()) && condi.getDimensionValueMap().containsKey("DATATIME")) {
            periodScheme = ((DimensionValue)condi.getDimensionValueMap().get("DATATIME")).getValue();
        }
        variable.put("ADJUST_VCHR_PERIOD_TYPE", this.buildAdjustVchrPeriodTypeCondi(periodScheme));
        variable.put("ASSIST_FIELD_SQL", assistFieldSql.toString());
        variable.put("ASSIST_GROUP_SQL", assistGroupSql.toString());
        variable.put("STARTPERIOD", String.format("%02d", condi.getStartPeriod()));
        variable.put("ENDPERIOD", String.format("%02d", condi.getEndPeriod()));
        String lastSql = VariableParseUtil.parse((String)sql.toString(), (Map)variable.getVariableMap());
        BdeLogUtil.recordLog((String)condi.getRequestTaskId(), (String)"\u8c03\u6574\u51ed\u8bc1\u73b0\u6d41\u53d6\u6570", (Object)condi, (String)lastSql);
        return this.query(lastSql, new Object[0]);
    }

    @Override
    public List<Object[]> reorderAdjustVchrList(Map<String, Integer> columns, FetchData originalData) {
        List originalList = originalData.getRowDatas();
        Map originalColumns = originalData.getColumns();
        ArrayList<Object[]> reorderedList = new ArrayList<Object[]>();
        for (Object[] array : originalList) {
            ArrayList<Object> newList = new ArrayList<Object>();
            for (String key : columns.keySet()) {
                if (originalColumns.get(key) != null) {
                    newList.add(array[(Integer)originalColumns.get(key)]);
                    continue;
                }
                newList.add(null);
            }
            Object[] newArray = newList.toArray(new Object[columns.keySet().size()]);
            reorderedList.add(newArray);
        }
        return reorderedList;
    }

    private String buildAdjustVchrPeriodTypeCondi(String periodSchemeStr) {
        if (StringUtils.isEmpty((String)periodSchemeStr)) {
            return "";
        }
        PeriodWrapper wrapper = new PeriodWrapper(periodSchemeStr);
        String periodType = PeriodUtil.convertType2Str((int)wrapper.getType());
        DcAdjustVchrSysOptionEnum optionEnum = DcAdjustVchrSysOptionEnum.getOptionIdByPeriodType((String)periodType);
        String optionValue = this.sysOptionService.findValueById(optionEnum.name());
        if (!StringUtils.isEmpty((String)optionValue)) {
            optionValue = optionValue.substring(1, optionValue.length() - 1);
            optionValue = optionValue.replace("\"", "");
        }
        Assert.isNotEmpty((String)optionValue, (String)String.format("\u671f\u95f4\u7c7b\u578b\u3010%1$s\u3011\u5bf9\u5e94\u7684\u8c03\u6574\u51ed\u8bc1\u7cfb\u7edf\u9009\u9879\u672a\u914d\u7f6e", periodType), (Object[])new Object[0]);
        List<String> optionPeriodTypes = Arrays.asList(optionValue.split(","));
        int periodEndMonth = AdjustVchrDataServiceImpl.getPeriodMonthScope(wrapper.getPeriod(), periodType)[1];
        StringJoiner sql = new StringJoiner(" OR ");
        for (String optionPeriodType : optionPeriodTypes) {
            PeriodTypeEnum periodTypeEnum = PeriodTypeEnum.getByCode((String)optionPeriodType);
            sql.add(String.format(" ( A.PERIODTYPE = '%1$s' AND A.ACCTPERIOD = %2$s) \n", periodTypeEnum.getCode(), periodTypeEnum.monthToPeriod(periodEndMonth)));
        }
        return String.format(" AND (%s)", sql);
    }

    private static int[] getPeriodMonthScope(int period, String periodType) {
        return AdjustVchrDataServiceImpl.getPeriodMonthScope(period, PeriodTypeEnum.getByCode((String)periodType));
    }

    private static int[] getPeriodMonthScope(int period, PeriodTypeEnum periodTypeEnum) {
        switch (periodTypeEnum) {
            case J: {
                return new int[]{(period - 1) * 3 + 1, period * 3};
            }
            case H: {
                return new int[]{(period - 1) * 6 + 1, period * 6};
            }
            case N: {
                return new int[]{1, 12};
            }
        }
        return new int[]{period, period};
    }

    private FetchData query(String sql, Object[] params) {
        Assert.isNotEmpty((String)sql);
        return this.recordSql(sql, JsonUtils.writeValueAsString((Object)params), () -> {
            try {
                return (FetchData)OuterDataSourceUtils.getJdbcTemplate().query(sql, params, (ResultSetExtractor)new FetchDataExtractor());
            }
            catch (BadSqlGrammarException e) {
                throw new RuntimeException(e.getCause().getMessage(), e);
            }
        });
    }

    private <T> T recordSql(String sql, String argsJson, Supplier<T> func) {
        String sqlLogId = UUIDUtils.newHalfGUIDStr();
        Date startTime = new Date();
        try {
            if (!Objects.isNull(ThreadContext.get((Object)"SQLLOGID_KEY"))) {
                String taskLogId = String.valueOf(ThreadContext.get((Object)"SQLLOGID_KEY"));
                this.sqlRecordService.recordSql(sqlLogId, taskLogId, sql, argsJson, startTime);
            }
        }
        catch (Exception e) {
            this.logger.error("\u8bb0\u5f55\u6267\u884cSQL\u65e5\u5fd7\u51fa\u73b0\u5f02\u5e38", e);
        }
        T rs = func.get();
        Date endTime = new Date();
        try {
            if (!Objects.isNull(sqlLogId)) {
                this.sqlRecordService.recordEndTime(sqlLogId, endTime);
            }
        }
        catch (Exception e) {
            this.logger.error("\u8bb0\u5f55\u6267\u884cSQL\u7ed3\u675f\u65f6\u95f4\u51fa\u73b0\u5f02\u5e38", e);
        }
        return rs;
    }
}

