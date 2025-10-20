/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.jobs.dao;

import com.jiuqi.bi.core.jobs.bean.JobSchedulerStateBean;
import com.jiuqi.bi.core.jobs.bean.SchedulerState;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SchedulerStateDao {
    private static final String T_NAME = "BI_JOBS_SCHEDULER_STATE";
    private static final String F_NODE = "BJSS_NODE";
    private static final String F_SCHEDULER = "BJSS_SCHEDULER";
    private static final String F_STATE = "BJSS_STATE";
    private static final String F_TIME = "BJSS_TIME";
    private static final String SELECT_STATES = "SELECT BJSS_NODE, BJSS_SCHEDULER, BJSS_STATE, BJSS_TIME FROM BI_JOBS_SCHEDULER_STATE";
    private static final String SELECT_BY_NODE_AND_SCHEDULER = "SELECT BJSS_NODE, BJSS_SCHEDULER, BJSS_STATE, BJSS_TIME FROM BI_JOBS_SCHEDULER_STATE WHERE BJSS_NODE = ?  AND BJSS_SCHEDULER = ? ";
    private static final String INSERT_STATE = "INSERT INTO BI_JOBS_SCHEDULER_STATE ( BJSS_NODE , BJSS_SCHEDULER, BJSS_STATE, BJSS_TIME )  VALUES (?, ?, ?, ?) ";
    private static final String UPDATE_STATE = "UPDATE BI_JOBS_SCHEDULER_STATE SET BJSS_STATE = ? , BJSS_TIME = ?  WHERE BJSS_NODE = ?  AND BJSS_SCHEDULER = ? ";
    private static final String UPDATE_STATE_WITHALL_SCHEDULER = "UPDATE BI_JOBS_SCHEDULER_STATE SET BJSS_STATE = ? , BJSS_TIME = ?  WHERE BJSS_NODE = ? ";

    public static List<JobSchedulerStateBean> selectAll(Connection conn) throws SQLException {
        ArrayList<JobSchedulerStateBean> beans = new ArrayList<JobSchedulerStateBean>();
        try (PreparedStatement ps = conn.prepareStatement(SELECT_STATES);
             ResultSet rs = ps.executeQuery();){
            while (rs.next()) {
                JobSchedulerStateBean bean = SchedulerStateDao.fillBean(rs);
                beans.add(bean);
            }
        }
        return beans;
    }

    public static JobSchedulerStateBean selectByName(Connection conn, String node, String schedulerName) throws SQLException {
        JobSchedulerStateBean bean = new JobSchedulerStateBean();
        try (PreparedStatement ps = conn.prepareStatement(SELECT_BY_NODE_AND_SCHEDULER);){
            ps.setString(1, node);
            ps.setString(2, schedulerName);
            try (ResultSet rs = ps.executeQuery();){
                while (rs.next()) {
                    bean = SchedulerStateDao.fillBean(rs);
                }
            }
        }
        return bean;
    }

    public static void insert(Connection conn, String node, String schedulerName, SchedulerState state) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(INSERT_STATE);){
            ps.setString(1, node);
            ps.setString(2, schedulerName);
            ps.setString(3, state.toString());
            ps.setLong(4, System.currentTimeMillis());
            ps.execute();
        }
    }

    public static int update(Connection conn, String node, String schedulerName, SchedulerState state) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(UPDATE_STATE);){
            ps.setString(1, state.toString());
            ps.setLong(2, System.currentTimeMillis());
            ps.setString(3, node);
            ps.setString(4, schedulerName);
            int n = ps.executeUpdate();
            return n;
        }
    }

    public static int updateAllScheduler(Connection conn, String node, SchedulerState state) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(UPDATE_STATE_WITHALL_SCHEDULER);){
            ps.setString(1, state.toString());
            ps.setLong(2, System.currentTimeMillis());
            ps.setString(3, node);
            int n = ps.executeUpdate();
            return n;
        }
    }

    private static JobSchedulerStateBean fillBean(ResultSet rs) throws SQLException {
        JobSchedulerStateBean bean = new JobSchedulerStateBean();
        bean.setNode(rs.getString(F_NODE));
        bean.setScheduler(rs.getString(F_SCHEDULER));
        bean.setState(SchedulerState.valueOf(rs.getString(F_STATE)));
        bean.setTime(rs.getLong(F_TIME));
        return bean;
    }
}

