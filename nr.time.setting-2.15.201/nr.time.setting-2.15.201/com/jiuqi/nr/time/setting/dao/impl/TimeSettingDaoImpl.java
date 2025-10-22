/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DataEngineUtil
 *  com.jiuqi.util.StringUtils
 *  org.apache.ibatis.jdbc.SQL
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.time.setting.dao.impl;

import com.jiuqi.np.dataengine.common.DataEngineUtil;
import com.jiuqi.nr.time.setting.bean.TimeSettingInfo;
import com.jiuqi.util.StringUtils;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class TimeSettingDaoImpl {
    private static final Logger logger = LoggerFactory.getLogger(TimeSettingDaoImpl.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static final String TABLE_UNIT_TIME = "nr_unit_set_time";
    private static final String COL_FORMSCHEME_ID = "formscheme_id";
    private static final String COL_PERIOD = "period";
    private static final String COL_UNIT_ID = "unit_id";
    private static final String COL_OPERATOR = "operator";
    private static final String COL_SUBMIT_STARTTIME = "submit_starttime";
    private static final String COL_DEADLINETIME = "submit_deadlinetime";
    private static final String COL_REPAY_DEADLINE = "repay_deadline";
    private static final String COL_OPERATOR_UNITID = "operator_unitid";
    private static final String COL_CREATETIME = "createtime";
    private static final String COL_UPDATETIME = "updatetime";
    private static final String COL_UNIT_LEVEL = "unit_level";

    public void saveDeadlineInfo(TimeSettingInfo deadlineInfo) {
        try {
            this.batchInsert(deadlineInfo);
        }
        catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public List<TimeSettingInfo> queryTableData(TimeSettingInfo deadlineInfo) {
        LinkedList<TimeSettingInfo> deadTimeResultList = new LinkedList<TimeSettingInfo>();
        String sql = ((SQL)((SQL)((SQL)new SQL().SELECT(new String[]{COL_UNIT_ID, COL_DEADLINETIME, COL_SUBMIT_STARTTIME, COL_REPAY_DEADLINE, COL_OPERATOR, COL_OPERATOR_UNITID, COL_FORMSCHEME_ID, COL_PERIOD})).FROM(TABLE_UNIT_TIME)).WHERE("formscheme_id=:formscheme_id and submit_deadlinetime=:submit_deadlinetime")).toString();
        try {
            if (StringUtils.isNotEmpty((String)deadlineInfo.getDeadLineTime())) {
                this.jdbcTemplate.query(sql, rs -> {
                    TimeSettingInfo setTimeResult = new TimeSettingInfo();
                    setTimeResult.setFormSchemeKey(rs.getString(COL_FORMSCHEME_ID));
                    setTimeResult.setPeriod(rs.getString(COL_PERIOD));
                    Timestamp time = rs.getTimestamp(COL_DEADLINETIME);
                    Date date2 = new Date(time.getTime());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    setTimeResult.setDeadLineTime(sdf.format(date2));
                    Timestamp submitStarttime = rs.getTimestamp(COL_SUBMIT_STARTTIME);
                    Date submitDate = new Date(submitStarttime.getTime());
                    setTimeResult.setSubmitStartTime(sdf.format(submitDate));
                    Timestamp repayDeadline = rs.getTimestamp(COL_REPAY_DEADLINE);
                    Date repayDate = new Date(repayDeadline.getTime());
                    setTimeResult.setRepayDeadline(sdf.format(repayDate));
                    deadTimeResultList.add(setTimeResult);
                }, new Object[]{deadlineInfo.getFormSchemeKey(), Timestamp.valueOf(deadlineInfo.getDeadLineTime())});
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return deadTimeResultList;
    }

    public List<TimeSettingInfo> queryTableData(String formSchemeKey, String period) {
        LinkedList<TimeSettingInfo> deadTimeResultList = new LinkedList<TimeSettingInfo>();
        String sql = ((SQL)((SQL)((SQL)new SQL().SELECT(new String[]{COL_UNIT_ID, COL_DEADLINETIME, COL_SUBMIT_STARTTIME, COL_REPAY_DEADLINE, COL_OPERATOR, COL_OPERATOR_UNITID, COL_FORMSCHEME_ID, COL_PERIOD})).FROM(TABLE_UNIT_TIME)).WHERE("formscheme_id = ?  and period = ? ")).toString();
        try {
            if (StringUtils.isNotEmpty((String)period)) {
                this.jdbcTemplate.query(sql, rs -> {
                    TimeSettingInfo setTimeResult = new TimeSettingInfo();
                    setTimeResult.setFormSchemeKey(rs.getString(COL_FORMSCHEME_ID));
                    setTimeResult.setPeriod(rs.getString(COL_PERIOD));
                    Timestamp time = rs.getTimestamp(COL_DEADLINETIME);
                    Date date2 = new Date(time.getTime());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    setTimeResult.setDeadLineTime(sdf.format(date2));
                    Timestamp submitStarttime = rs.getTimestamp(COL_SUBMIT_STARTTIME);
                    Date submitDate = new Date(submitStarttime.getTime());
                    setTimeResult.setSubmitStartTime(sdf.format(submitDate));
                    Timestamp repayDeadline = rs.getTimestamp(COL_REPAY_DEADLINE);
                    Date repayDate = new Date(repayDeadline.getTime());
                    setTimeResult.setRepayDeadline(sdf.format(repayDate));
                    deadTimeResultList.add(setTimeResult);
                }, new Object[]{formSchemeKey, period});
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return deadTimeResultList;
    }

    public TimeSettingInfo queryDeadTime(String formSchemeKey, String period, String unitId) {
        TimeSettingInfo setlineInfo = new TimeSettingInfo();
        String sql = ((SQL)((SQL)((SQL)new SQL().SELECT(new String[]{COL_UNIT_ID, COL_DEADLINETIME, COL_SUBMIT_STARTTIME, COL_REPAY_DEADLINE, COL_OPERATOR, COL_OPERATOR_UNITID, COL_FORMSCHEME_ID, COL_PERIOD})).FROM(TABLE_UNIT_TIME)).WHERE("formscheme_id = ?  and period = ?  and unit_id = ? ")).toString();
        try {
            if (StringUtils.isNotEmpty((String)period)) {
                this.jdbcTemplate.query(sql, rs -> {
                    setlineInfo.setFormSchemeKey(rs.getString(COL_FORMSCHEME_ID));
                    setlineInfo.setPeriod(rs.getString(COL_PERIOD));
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Timestamp time = rs.getTimestamp(COL_DEADLINETIME);
                    Date date2 = new Date(time.getTime());
                    setlineInfo.setDeadLineTime(sdf.format(date2));
                    Timestamp submitStarttime = rs.getTimestamp(COL_SUBMIT_STARTTIME);
                    Date submitDate = new Date(submitStarttime.getTime());
                    setlineInfo.setSubmitStartTime(sdf.format(submitDate));
                    Timestamp repayDeadline = rs.getTimestamp(COL_REPAY_DEADLINE);
                    Date repayDate = new Date(repayDeadline.getTime());
                    setlineInfo.setRepayDeadline(sdf.format(repayDate));
                    setlineInfo.setOperator(rs.getString(COL_OPERATOR));
                    setlineInfo.setOperatorOfUnitId(rs.getString(COL_OPERATOR_UNITID));
                }, new Object[]{formSchemeKey, period, unitId});
                return setlineInfo;
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public List<TimeSettingInfo> queryTableData(String formSchemeKey, String period, String unitId) {
        LinkedList<TimeSettingInfo> setTimeResultList = new LinkedList<TimeSettingInfo>();
        String sql = ((SQL)((SQL)((SQL)new SQL().SELECT(new String[]{COL_UNIT_ID, COL_DEADLINETIME, COL_SUBMIT_STARTTIME, COL_REPAY_DEADLINE, COL_OPERATOR, COL_OPERATOR_UNITID, COL_FORMSCHEME_ID, COL_PERIOD, COL_UNIT_LEVEL})).FROM(TABLE_UNIT_TIME)).WHERE("formscheme_id = ?  and period = ?  and unit_id = ? ")).toString();
        try {
            if (StringUtils.isNotEmpty((String)period)) {
                this.jdbcTemplate.query(sql, rs -> {
                    Timestamp repayDeadline;
                    Timestamp submitStarttime;
                    TimeSettingInfo setTimeResult = new TimeSettingInfo();
                    setTimeResult.setFormSchemeKey(rs.getString(COL_FORMSCHEME_ID));
                    setTimeResult.setPeriod(rs.getString(COL_PERIOD));
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Timestamp deadtime = rs.getTimestamp(COL_DEADLINETIME);
                    if (deadtime != null) {
                        Date date2 = new Date(deadtime.getTime());
                        setTimeResult.setDeadLineTime(sdf.format(date2));
                    }
                    if ((submitStarttime = rs.getTimestamp(COL_SUBMIT_STARTTIME)) != null) {
                        Date submitDate = new Date(submitStarttime.getTime());
                        setTimeResult.setSubmitStartTime(sdf.format(submitDate));
                    }
                    if ((repayDeadline = rs.getTimestamp(COL_REPAY_DEADLINE)) != null) {
                        Date repayDate = new Date(repayDeadline.getTime());
                        setTimeResult.setRepayDeadline(sdf.format(repayDate));
                    }
                    setTimeResult.setOperator(rs.getString(COL_OPERATOR));
                    setTimeResult.setOperatorOfUnitId(rs.getString(COL_OPERATOR_UNITID));
                    setTimeResult.setUnitLevel(rs.getInt(COL_UNIT_LEVEL));
                    setTimeResultList.add(setTimeResult);
                }, new Object[]{formSchemeKey, period, unitId});
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return setTimeResultList;
    }

    public TimeSettingInfo queryDeadTime(String formSchemeKey, String period, String unitId, String operator) {
        TimeSettingInfo setlineInfo = new TimeSettingInfo();
        String sql = ((SQL)((SQL)((SQL)new SQL().SELECT(new String[]{COL_UNIT_ID, COL_DEADLINETIME, COL_SUBMIT_STARTTIME, COL_REPAY_DEADLINE, COL_OPERATOR, COL_OPERATOR_UNITID, COL_FORMSCHEME_ID, COL_PERIOD, COL_UNIT_LEVEL})).FROM(TABLE_UNIT_TIME)).WHERE("formscheme_id = ?  and period = ?  and unit_id = ?  and operator = ? ")).toString();
        try {
            if (StringUtils.isNotEmpty((String)period)) {
                this.jdbcTemplate.query(sql, rs -> {
                    Timestamp repayDeadline;
                    Timestamp submitStarttime;
                    setlineInfo.setFormSchemeKey(rs.getString(COL_FORMSCHEME_ID));
                    setlineInfo.setPeriod(rs.getString(COL_PERIOD));
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Timestamp deadtime = rs.getTimestamp(COL_DEADLINETIME);
                    if (deadtime != null) {
                        Date date2 = new Date(deadtime.getTime());
                        setlineInfo.setDeadLineTime(sdf.format(date2));
                    }
                    if ((submitStarttime = rs.getTimestamp(COL_SUBMIT_STARTTIME)) != null) {
                        Date submitDate = new Date(submitStarttime.getTime());
                        setlineInfo.setSubmitStartTime(sdf.format(submitDate));
                    }
                    if ((repayDeadline = rs.getTimestamp(COL_REPAY_DEADLINE)) != null) {
                        Date repayDate = new Date(repayDeadline.getTime());
                        setlineInfo.setRepayDeadline(sdf.format(repayDate));
                    }
                    setlineInfo.setOperator(rs.getString(COL_OPERATOR));
                    setlineInfo.setOperatorOfUnitId(rs.getString(COL_OPERATOR_UNITID));
                    setlineInfo.setUnitLevel(rs.getInt(COL_UNIT_LEVEL));
                }, new Object[]{formSchemeKey, period, unitId, operator});
                return setlineInfo;
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public void deleteTime(String formSchemeKey, String period, List<String> unitids, String operator) {
        for (String id : unitids) {
            String conditionSql = String.format("%s = ? and %s = ? and %s = ? and %s =?", COL_FORMSCHEME_ID, COL_PERIOD, COL_OPERATOR, COL_UNIT_ID);
            String sql0 = ((SQL)((SQL)new SQL().DELETE_FROM(TABLE_UNIT_TIME)).WHERE(conditionSql)).toString();
            this.jdbcTemplate.update(sql0, new Object[]{formSchemeKey, period, operator, id});
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void batchInsert(TimeSettingInfo setTimeInfo) throws SQLException {
        try (Connection connection = null;){
            connection = this.getConnection();
            List<Object[]> batchValues = this.getBatchInsertValues(setTimeInfo);
            if (batchValues.size() > 0) {
                this.batchInsertOptions(connection, batchValues);
            }
        }
    }

    private void batchInsertOptions(Connection connection, List<Object[]> batchValues) throws SQLException {
        StringBuilder insertSql = new StringBuilder();
        insertSql.append("INSERT INTO ").append(TABLE_UNIT_TIME);
        insertSql.append(" ").append(String.format("(%s,%s,%s,%s,%s,%s,%s,%s,%s)", COL_FORMSCHEME_ID, COL_UNIT_ID, COL_PERIOD, COL_DEADLINETIME, COL_SUBMIT_STARTTIME, COL_REPAY_DEADLINE, COL_OPERATOR, COL_OPERATOR_UNITID, COL_UNIT_LEVEL));
        insertSql.append(" VALUES ").append("(?,?,?,?,?,?,?,?,?)");
        DataEngineUtil.batchUpdate((Connection)connection, (String)insertSql.toString(), batchValues);
    }

    private List<Object[]> getBatchInsertValues(TimeSettingInfo setTimeInfo) {
        ArrayList<Object[]> batchValues = new ArrayList<Object[]>();
        String deadTime = setTimeInfo.getDeadLineTime();
        String formSchemeKey = setTimeInfo.getFormSchemeKey();
        String period = setTimeInfo.getPeriod();
        String submitStartTime = setTimeInfo.getSubmitStartTime();
        String repayDeadline = setTimeInfo.getRepayDeadline();
        return batchValues;
    }

    private Connection getConnection() throws SQLException {
        return this.jdbcTemplate.getDataSource().getConnection();
    }

    public void updateDeadlineInfo(TimeSettingInfo deadlineInfo) {
        try {
            this.batchUpdate(deadlineInfo);
        }
        catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void batchUpdate(TimeSettingInfo setTimeInfo) throws SQLException {
        try (Connection connection = null;){
            connection = this.getConnection();
            List<Object[]> batchValues = this.getBatchUpdateValues(setTimeInfo);
            if (batchValues.size() > 0) {
                this.batchupdateOptions(connection, batchValues);
            }
        }
    }

    private void batchupdateOptions(Connection connection, List<Object[]> batchValues) throws SQLException {
        String sql = String.format("UPDATE %s SET %s = ?, %s = ?, %s = ? where %s = ? and %s = ? and %s = ? and %s = ?", TABLE_UNIT_TIME, COL_DEADLINETIME, COL_SUBMIT_STARTTIME, COL_REPAY_DEADLINE, COL_FORMSCHEME_ID, COL_PERIOD, COL_OPERATOR, COL_UNIT_ID);
        this.jdbcTemplate.batchUpdate(sql, batchValues);
    }

    private List<Object[]> getBatchUpdateValues(TimeSettingInfo setTimeInfo) {
        ArrayList<Object[]> batchValues = new ArrayList<Object[]>();
        String deadTime = setTimeInfo.getDeadLineTime();
        String formSchemeKey = setTimeInfo.getFormSchemeKey();
        String period = setTimeInfo.getPeriod();
        String submitStartTime = setTimeInfo.getSubmitStartTime();
        String repayDeadline = setTimeInfo.getRepayDeadline();
        return batchValues;
    }

    public void saveDeadlineInfo(List<TimeSettingInfo> deadlineInfo) {
        try {
            this.batchInsert(deadlineInfo);
        }
        catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void batchInsert(List<TimeSettingInfo> setTimeInfo) throws SQLException {
        try (Connection connection = null;){
            connection = this.getConnection();
            List<Object[]> batchValues = this.getBatchInsertValues(setTimeInfo);
            if (batchValues.size() > 0) {
                this.batchInsertOptions(connection, batchValues);
            }
        }
    }

    private List<Object[]> getBatchInsertValues(List<TimeSettingInfo> setTimeInfoList) {
        ArrayList<Object[]> batchValues = new ArrayList<Object[]>();
        for (TimeSettingInfo setTimeInfo : setTimeInfoList) {
            Object[] paramValues = new Object[9];
            paramValues[0] = setTimeInfo.getFormSchemeKey();
            paramValues[2] = setTimeInfo.getPeriod();
            String deadTime = setTimeInfo.getDeadLineTime();
            String submitStartTime = setTimeInfo.getSubmitStartTime();
            String repayDeadline = setTimeInfo.getRepayDeadline();
            if (deadTime != null) {
                paramValues[3] = Timestamp.valueOf(deadTime);
            }
            if (submitStartTime != null) {
                paramValues[4] = Timestamp.valueOf(submitStartTime);
            }
            if (repayDeadline != null) {
                paramValues[5] = Timestamp.valueOf(repayDeadline);
            }
            paramValues[6] = setTimeInfo.getOperator();
            paramValues[7] = setTimeInfo.getOperatorOfUnitId();
            paramValues[8] = setTimeInfo.getUnitLevel();
            batchValues.add(paramValues);
        }
        return batchValues;
    }

    public void updateDeadlineInfo(List<TimeSettingInfo> deadlineInfo) {
        try {
            this.batchUpdate(deadlineInfo);
        }
        catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void batchUpdate(List<TimeSettingInfo> setTimeInfo) throws SQLException {
        try (Connection connection = null;){
            connection = this.getConnection();
            List<Object[]> batchValues = this.getBatchUpdateValues(setTimeInfo);
            if (batchValues.size() > 0) {
                this.batchupdateOptions(connection, batchValues);
            }
        }
    }

    private List<Object[]> getBatchUpdateValues(List<TimeSettingInfo> setTimeInfoList) {
        ArrayList<Object[]> batchValues = new ArrayList<Object[]>();
        for (TimeSettingInfo setTimeInfo : setTimeInfoList) {
            Object[] paramValues = new Object[7];
            String deadTime = setTimeInfo.getDeadLineTime();
            String submitStartTime = setTimeInfo.getSubmitStartTime();
            String repayDeadline = setTimeInfo.getRepayDeadline();
            if (deadTime != null) {
                paramValues[0] = Timestamp.valueOf(deadTime);
            }
            if (submitStartTime != null) {
                paramValues[1] = Timestamp.valueOf(submitStartTime);
            }
            if (repayDeadline != null) {
                paramValues[2] = Timestamp.valueOf(repayDeadline);
            }
            paramValues[3] = setTimeInfo.getFormSchemeKey();
            paramValues[4] = setTimeInfo.getPeriod();
            paramValues[5] = setTimeInfo.getOperator();
            batchValues.add(paramValues);
        }
        return batchValues;
    }
}

