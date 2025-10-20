/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.nodekeeper.DistributionManager
 *  org.json.JSONArray
 *  org.json.JSONObject
 */
package com.jiuqi.bi.core.jobs.dao;

import com.jiuqi.bi.core.nodekeeper.DistributionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.JSONArray;
import org.json.JSONObject;

public class JobRunningDao {
    private static final String T_NAME = "BI_JOBS_RUNNING";
    private static final String F_INSTANCEID = "BJR_INSTANCEID";
    private static final String F_JOB_TYPE = "BJR_JOB_TYPE";
    private static final String F_JOB_GROUP = "BJR_JOB_GROUP";
    private static final String F_STARTTIME = "BJR_STARTTIME";
    private static final String F_NODE = "BJR_NODE";
    private static final String INSERT = "INSERT INTO BI_JOBS_RUNNING(BJR_INSTANCEID,BJR_JOB_TYPE,BJR_JOB_GROUP,BJR_STARTTIME,BJR_NODE) VALUES(?,?,?,?,?)";
    private static final String DELETE = "DELETE FROM BI_JOBS_RUNNING WHERE BJR_INSTANCEID=?";
    private static final String SELECT_COUNT = "SELECT COUNT(*) FROM BI_JOBS_RUNNING WHERE BJR_JOB_TYPE=? AND BJR_JOB_GROUP=?";
    private static final String DELETE_BY_NODE = "DELETE FROM BI_JOBS_RUNNING WHERE BJR_NODE=?";
    private static final String DELETE_ALL = "DELETE FROM BI_JOBS_RUNNING";
    private static final String SELECT_ALL = "SELECT * FROM BI_JOBS_RUNNING";

    private JobRunningDao() {
    }

    public static void insert(Connection conn, String instanceId, int jobType, String jobGroup) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(INSERT);){
            ps.setString(1, instanceId);
            ps.setInt(2, jobType);
            ps.setString(3, jobGroup);
            ps.setLong(4, System.currentTimeMillis());
            ps.setString(5, DistributionManager.getInstance().self().getName());
            ps.executeUpdate();
        }
    }

    public static void delete(Connection conn, String instanceId) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(DELETE);){
            ps.setString(1, instanceId);
            ps.executeUpdate();
        }
    }

    public static int selectCount(Connection conn, int jobType, String jobGroup) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(SELECT_COUNT);){
            ps.setInt(1, jobType);
            ps.setString(2, jobGroup);
            try (ResultSet rs = ps.executeQuery();){
                if (rs.next()) {
                    int n = rs.getInt(1);
                    return n;
                }
            }
        }
        return 0;
    }

    public static void deleteByNode(Connection conn, String node) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(DELETE_BY_NODE);){
            ps.setString(1, node);
            ps.executeUpdate();
        }
    }

    public static void deleteAll(Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(DELETE_ALL);){
            ps.executeUpdate();
        }
    }

    public static JSONArray selectAll(Connection conn) throws SQLException {
        JSONArray array = new JSONArray();
        try (PreparedStatement ps = conn.prepareStatement(SELECT_ALL);
             ResultSet rs = ps.executeQuery();){
            while (rs.next()) {
                JSONObject json = new JSONObject();
                json.put(F_INSTANCEID, (Object)rs.getString(F_INSTANCEID));
                json.put(F_JOB_TYPE, rs.getInt(F_JOB_TYPE));
                json.put(F_JOB_GROUP, (Object)rs.getString(F_JOB_GROUP));
                json.put(F_STARTTIME, rs.getLong(F_STARTTIME));
                json.put(F_NODE, (Object)rs.getString(F_NODE));
                array.put((Object)json);
            }
        }
        return array;
    }

    public static int selectFiredTriggersRunningCount(Connection conn, String sql, String schedName, String jobGroup) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(sql);){
            ps.setString(1, schedName);
            ps.setString(2, jobGroup);
            try (ResultSet rs = ps.executeQuery();){
                if (rs.next()) {
                    int n = rs.getInt(1);
                    return n;
                }
            }
        }
        return 0;
    }
}

