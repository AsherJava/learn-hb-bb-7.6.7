/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.common.ParamDeployEnum$DeployStatus
 *  com.jiuqi.nr.definition.common.ParamDeployEnum$ParamStatus
 *  org.springframework.jdbc.core.BatchPreparedStatementSetter
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.nr.definition.deploy.dao;

import com.jiuqi.nr.definition.common.ParamDeployEnum;
import com.jiuqi.nr.definition.deploy.entity.ParamDeployStatusDO;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class ParamDeployStatusDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static final String TABLE_NAME = "NR_PARAM_DEPLOY_STATUS";
    private static final String[] FIELD_NAMES = new String[]{"SCHEME_KEY", "PARAM_STATUS", "DEPLOY_STATUS", "DEPLOY_DETAIL", "DEPLOY_TIME", "LAST_DEPLOY_TIME", "USER_KEY", "USER_NAME", "DDL_STATUS"};
    private static final String SQL_QUERY = String.format("SELECT %s FROM %s WHERE %s = ?", String.join((CharSequence)",", FIELD_NAMES), "NR_PARAM_DEPLOY_STATUS", "SCHEME_KEY");
    private static final String SQL_QUERY_ALL = String.format("SELECT %s FROM %s", String.join((CharSequence)",", FIELD_NAMES), "NR_PARAM_DEPLOY_STATUS");
    private static final String SQL_QUERY_BY_STATUS = String.format("SELECT %s FROM %s WHERE %s = ?", String.join((CharSequence)",", FIELD_NAMES), "NR_PARAM_DEPLOY_STATUS", "DEPLOY_STATUS");
    private static final String SQL_QUERY_BY_TASK = String.format("SELECT %s FROM %s S INNER JOIN NR_PARAM_FORMSCHEME F ON S.SCHEME_KEY = F.FC_KEY WHERE F.FC_TASK_KEY = ?", Arrays.stream(FIELD_NAMES).map(v -> "S." + v).collect(Collectors.joining(",")), "NR_PARAM_DEPLOY_STATUS");
    private static final String SQL_INSERT = String.format("INSERT INTO %s (%s) VALUES (%s)", "NR_PARAM_DEPLOY_STATUS", String.join((CharSequence)",", FIELD_NAMES), Arrays.stream(FIELD_NAMES).map(v -> "?").collect(Collectors.joining(",")));
    private static final String SQL_UPDATE = String.format("UPDATE %s SET %s = ? WHERE %s = ? ", "NR_PARAM_DEPLOY_STATUS", String.join((CharSequence)" = ?, ", FIELD_NAMES), "SCHEME_KEY");
    private static final String SQL_DELETE = String.format("DELETE FROM %s WHERE %s = ? ", "NR_PARAM_DEPLOY_STATUS", "SCHEME_KEY");
    private static final RowMapper<ParamDeployStatusDO> ROWMAPPER = (rs, rowNum) -> {
        ParamDeployStatusDO obj = new ParamDeployStatusDO();
        obj.setSchemeKey(rs.getString(1));
        obj.setParamStatus(ParamDeployEnum.ParamStatus.valueOf((int)rs.getInt(2)));
        obj.setDeployStatus(ParamDeployEnum.DeployStatus.valueOf((int)rs.getInt(3)));
        obj.setDeployDetail(rs.getString(4));
        Timestamp timestamp = rs.getTimestamp(5);
        obj.setDeployTime(null == timestamp ? null : Date.from(timestamp.toInstant()));
        timestamp = rs.getTimestamp(6);
        obj.setLastDeployTime(null == timestamp ? null : Date.from(timestamp.toInstant()));
        obj.setUserKey(rs.getString(7));
        obj.setUserName(rs.getString(8));
        obj.setDdlStatus(rs.getInt(9));
        return obj;
    };

    private static void insertStatement(PreparedStatement ps, ParamDeployStatusDO status) throws SQLException {
        ps.setString(1, status.getSchemeKey());
        ps.setInt(2, status.getParamStatus().getValue());
        ps.setInt(3, status.getDeployStatus().getValue());
        ps.setString(4, status.getDeployDetail());
        Date time = status.getDeployTime();
        ps.setTimestamp(5, null == time ? null : Timestamp.from(time.toInstant()));
        time = status.getLastDeployTime();
        ps.setTimestamp(6, null == time ? null : Timestamp.from(time.toInstant()));
        ps.setString(7, status.getUserKey());
        ps.setString(8, status.getUserName());
        ps.setInt(9, status.getDdlStatus());
    }

    private static void updateStatement(PreparedStatement ps, ParamDeployStatusDO status) throws SQLException {
        ps.setString(1, status.getSchemeKey());
        ps.setInt(2, status.getParamStatus().getValue());
        ps.setInt(3, status.getDeployStatus().getValue());
        ps.setString(4, status.getDeployDetail());
        Date time = status.getDeployTime();
        ps.setTimestamp(5, null == time ? null : Timestamp.from(time.toInstant()));
        time = status.getLastDeployTime();
        ps.setTimestamp(6, null == time ? null : Timestamp.from(time.toInstant()));
        ps.setString(7, status.getUserKey());
        ps.setString(8, status.getUserName());
        ps.setInt(9, status.getDdlStatus());
        ps.setString(10, status.getSchemeKey());
    }

    public ParamDeployStatusDO getDeployStatus(String schemeKey) {
        return this.jdbcTemplate.query(SQL_QUERY, pss -> pss.setString(1, schemeKey), ROWMAPPER).stream().findFirst().orElse(null);
    }

    public List<ParamDeployStatusDO> listDeployStatus() {
        return this.jdbcTemplate.query(SQL_QUERY_ALL, ROWMAPPER);
    }

    public List<ParamDeployStatusDO> listDeployStatusByStatus(ParamDeployEnum.DeployStatus deployStatus) {
        return this.jdbcTemplate.query(SQL_QUERY_BY_STATUS, pss -> pss.setInt(1, deployStatus.getValue()), ROWMAPPER);
    }

    public List<ParamDeployStatusDO> listDeployStatusByTask(String taskKey) {
        return this.jdbcTemplate.query(SQL_QUERY_BY_TASK, pss -> pss.setString(1, taskKey), ROWMAPPER);
    }

    public int insertDeployStatus(ParamDeployStatusDO status) {
        return this.jdbcTemplate.update(SQL_INSERT, pss -> ParamDeployStatusDao.insertStatement(pss, status));
    }

    public void insertDeployStatus(final List<ParamDeployStatusDO> status) {
        if (CollectionUtils.isEmpty(status)) {
            return;
        }
        this.jdbcTemplate.batchUpdate(SQL_INSERT, new BatchPreparedStatementSetter(){

            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ParamDeployStatusDao.insertStatement(ps, (ParamDeployStatusDO)status.get(i));
            }

            public int getBatchSize() {
                return status.size();
            }
        });
    }

    public int updateDeployStatus(ParamDeployStatusDO status) {
        return this.jdbcTemplate.update(SQL_UPDATE, pss -> ParamDeployStatusDao.updateStatement(pss, status));
    }

    public void updateDeployStatus(final List<ParamDeployStatusDO> status) {
        if (CollectionUtils.isEmpty(status)) {
            return;
        }
        this.jdbcTemplate.batchUpdate(SQL_UPDATE, new BatchPreparedStatementSetter(){

            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ParamDeployStatusDao.updateStatement(ps, (ParamDeployStatusDO)status.get(i));
            }

            public int getBatchSize() {
                return status.size();
            }
        });
    }

    public int deleteDeployStatus(String schemeKey) {
        return this.jdbcTemplate.update(SQL_DELETE, pss -> pss.setString(1, schemeKey));
    }

    public String getUpdateStatusSqlByDDLBit(String formScheme, int value) {
        return String.format("UPDATE %s SET %s = %d WHERE %s = '%s';", TABLE_NAME, "DDL_STATUS", value, "SCHEME_KEY", formScheme);
    }
}

