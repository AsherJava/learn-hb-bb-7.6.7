/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  org.springframework.dao.DataAccessException
 *  org.springframework.data.util.Pair
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.ResultSetExtractor
 *  org.springframework.transaction.annotation.Transactional
 *  reactor.util.function.Tuple3
 *  reactor.util.function.Tuples
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.callback;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.np.sql.CustomClassExecutor;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.util.Pair;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.transaction.annotation.Transactional;
import reactor.util.function.Tuple3;
import reactor.util.function.Tuples;

public class FetchContextVariableUpdate
implements CustomClassExecutor {
    private static final Logger logger = LoggerFactory.getLogger(FetchContextVariableUpdate.class);

    @Transactional(rollbackFor={Exception.class})
    public void execute(DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        logger.info("\u5f00\u59cb\u4fee\u590dBDE\u6d6e\u52a8\u533a\u57df\u8bbe\u7f6e[\u53d6\u6570\u4e0a\u4e0b\u6587\u53c2\u6570]\u8bbe\u8ba1\u671f\u6570\u636e");
        this.updateFloatSettingConfigInfo(jdbcTemplate, "BDE_FETCHFLOATSETTING_DES");
        logger.info("BDE\u6d6e\u52a8\u533a\u57df\u8bbe\u7f6e\u8bbe\u8ba1\u671f\u6570\u636e\u4fee\u590d\u5b8c\u6210");
        logger.info("\u5f00\u59cb\u4fee\u590dBDE\u6d6e\u52a8\u533a\u57df\u8bbe\u7f6e[\u53d6\u6570\u4e0a\u4e0b\u6587\u53c2\u6570]\u8fd0\u884c\u671f\u6570\u636e");
        this.updateFloatSettingConfigInfo(jdbcTemplate, "BDE_FETCHFLOATSETTING");
        logger.info("BDE\u6d6e\u52a8\u533a\u57df\u8bbe\u7f6e\u8fd0\u884c\u671f\u6570\u636e\u4fee\u590d\u5b8c\u6210");
        logger.info("\u5f00\u59cb\u4fee\u590dBDE\u56fa\u5b9a\u533a\u57df\u8bbe\u7f6e[\u53d6\u6570\u4e0a\u4e0b\u6587\u53c2\u6570]\u8bbe\u8ba1\u671f\u6570\u636e");
        this.updateFetchSettingConfigInfo(jdbcTemplate, "BDE_FETCHSETTING_DES");
        logger.info("BDE\u56fa\u5b9a\u533a\u57df\u8bbe\u7f6e\u8bbe\u8ba1\u671f\u6570\u636e\u4fee\u590d\u5b8c\u6210");
        logger.info("\u5f00\u59cb\u4fee\u590dBDE\u56fa\u5b9a\u533a\u57df\u8bbe\u7f6e[\u53d6\u6570\u4e0a\u4e0b\u6587\u53c2\u6570]\u8fd0\u884c\u671f\u6570\u636e");
        this.updateFetchSettingConfigInfo(jdbcTemplate, "BDE_FETCHSETTING");
        logger.info("BDE\u56fa\u5b9a\u533a\u57df\u8bbe\u7f6e\u8fd0\u884c\u671f\u6570\u636e\u4fee\u590d\u5b8c\u6210");
    }

    private void updateFloatSettingConfigInfo(JdbcTemplate jdbcTemplate, String tableName) {
        String queryFloatSettingSql = "SELECT ID, QUERYCONFIGINFO FROM %1$s ";
        List floatSettingList = (List)jdbcTemplate.query(String.format(queryFloatSettingSql, tableName), (ResultSetExtractor)new ResultSetExtractor<List<Pair<String, String>>>(){

            public List<Pair<String, String>> extractData(ResultSet rs) throws SQLException, DataAccessException {
                ArrayList<Pair<String, String>> result = new ArrayList<Pair<String, String>>(256);
                while (rs.next()) {
                    try {
                        result.add((Pair<String, String>)Pair.of((Object)rs.getString(1), (Object)rs.getString(2)));
                    }
                    catch (Exception exception) {}
                }
                return result;
            }
        });
        if (CollectionUtils.isEmpty((Collection)floatSettingList)) {
            return;
        }
        ArrayList<Object[]> batchArgs = new ArrayList<Object[]>(floatSettingList.size());
        for (Pair pair : floatSettingList) {
            String configInfo = this.replaceByContextVariable((String)pair.getSecond());
            batchArgs.add(new Object[]{configInfo, pair.getFirst()});
        }
        String updateFieldDefineTypeSql = "UPDATE %1$s SET QUERYCONFIGINFO = ? WHERE 1 = 1  AND ID = ? ";
        jdbcTemplate.batchUpdate(String.format(updateFieldDefineTypeSql, tableName), batchArgs);
    }

    private void updateFetchSettingConfigInfo(JdbcTemplate jdbcTemplate, String tableName) {
        String queryFetchSettingSql = "SELECT ID, FORMULA, MEMO FROM %1$s WHERE 1 = 1 AND BIZMODELCODE IN  ('TFV','FORMULA') ";
        List fetchSettingList = (List)jdbcTemplate.query(String.format(queryFetchSettingSql, tableName), (ResultSetExtractor)new ResultSetExtractor<List<Tuple3<String, String, String>>>(){

            public List<Tuple3<String, String, String>> extractData(ResultSet rs) throws SQLException, DataAccessException {
                ArrayList<Tuple3<String, String, String>> result = new ArrayList<Tuple3<String, String, String>>(256);
                while (rs.next()) {
                    try {
                        result.add((Tuple3<String, String, String>)Tuples.of((Object)rs.getString(1), (Object)(rs.getString(2) == null ? "" : rs.getString(2)), (Object)(rs.getString(3) == null ? "" : rs.getString(3))));
                    }
                    catch (Exception exception) {}
                }
                return result;
            }
        });
        if (CollectionUtils.isEmpty((Collection)fetchSettingList)) {
            return;
        }
        ArrayList<Object[]> batchArgs = new ArrayList<Object[]>(fetchSettingList.size());
        for (Tuple3 tuple : fetchSettingList) {
            String formula = this.replaceByContextVariable((String)tuple.getT2());
            String memo = this.replaceByContextVariable((String)tuple.getT3());
            batchArgs.add(new Object[]{formula, memo, tuple.getT1()});
        }
        String updateSql = "UPDATE %1$s SET FORMULA = ?, MEMO = ?  WHERE 1 = 1 AND ID = ? ";
        jdbcTemplate.batchUpdate(String.format(updateSql, tableName), batchArgs);
    }

    private String replaceByContextVariable(String srcConfigInfo) {
        if (srcConfigInfo == null) {
            return null;
        }
        String configInfo = srcConfigInfo.replaceAll("(?i)#orgid", "\\$unitCode\\$").replaceAll("(?i)#unitCode", "\\$unitCode\\$").replaceAll("(?i)#\\*", "\\$unitCode\\$").replaceAll("(?i)#yearperiod", "\\$yearPeriod\\$").replaceAll("(?i)#periodscheme", "\\$periodScheme\\$").replaceAll("(?i)#fullperiod", "\\$fullPeriod\\$").replaceAll("(?i)#year", "\\$year\\$").replaceAll("(?i)#period", "\\$period\\$").replaceAll("(?i)#bookid", "\\$bookid\\$").replaceAll("(?i)#startdate", "\\$startDate\\$").replaceAll("(?i)#enddate", "\\$endDate\\$").replaceAll("(?i)#taskid", "\\$taskId\\$").replaceAll("(?i)#includeuncharged", "\\$includeUnCharged\\$").replaceAll("(?i)#MD_CURRENCY", "\\$MD_CURRENCY\\$").replaceAll("(?i)#MD_GCADJTYPE", "\\$MD_GCADJTYPE\\$").replaceAll("(?i)#MD_GCORGTYPE", "\\$MD_GCORGTYPE\\$");
        configInfo = configInfo.replaceAll("(?i)\\$unitCode\\$", "#unitCode#").replaceAll("(?i)\\$year\\$", "#year#").replaceAll("(?i)\\$period\\$", "#period#").replaceAll("(?i)\\$yearperiod\\$", "#yearPeriod#").replaceAll("(?i)\\$fullperiod\\$", "#fullPeriod#").replaceAll("(?i)\\$bookid\\$", "#bookCode#").replaceAll("(?i)\\$startdate\\$", "#startDate#").replaceAll("(?i)\\$enddate\\$", "#endDate#").replaceAll("(?i)\\$periodscheme\\$", "#periodScheme#").replaceAll("(?i)\\$taskid\\$", "#taskId#").replaceAll("(?i)\\$includeuncharged\\$", "#includeUnCharged#").replaceAll("(?i)\\$MD_CURRENCY\\$", "#MD_CURRENCY#").replaceAll("(?i)\\$MD_GCADJTYPE\\$", "#MD_GCADJTYPE#").replaceAll("(?i)\\$MD_GCORGTYPE\\$", "#MD_GCORGTYPE#");
        return configInfo;
    }
}

