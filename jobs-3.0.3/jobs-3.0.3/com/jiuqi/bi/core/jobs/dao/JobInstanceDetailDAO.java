/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.jobs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JobInstanceDetailDAO {
    private static final Logger logger = LoggerFactory.getLogger(JobInstanceDetailDAO.class);
    private static final String TABLE_NAME = "BI_JOBS_INSTANCE_DETAIL";
    private static final String FIELD_INSTANCEID = "BJI_INSTANCEID";
    private static final String FIELD_BJID_DETAIL = "BJID_DETAIL";
    private static final String FIELD_BJID_PARAMS = "BJID_PARAMS";
    private static final String FIELD_BJID_UPDATE_TIME = "BJID_UPDATE_TIME";
    private static final String SELECT_DETAIL_BY_INSTANCEID = "SELECT BJID_DETAIL FROM BI_JOBS_INSTANCE_DETAIL WHERE BJI_INSTANCEID = ? ";
    private static final String SELECT_PARAMS_BY_INSTANCEID = "SELECT BJID_PARAMS FROM BI_JOBS_INSTANCE_DETAIL WHERE BJI_INSTANCEID = ? ";
    private static final String INSERT_INSTANCE_DETAIL = "INSERT INTO BI_JOBS_INSTANCE_DETAIL ( BJI_INSTANCEID , BJID_PARAMS )  VALUES (?, ?) ";
    private static final String UPDATE_INSTANCE_DETAIL = "UPDATE BI_JOBS_INSTANCE_DETAIL SET BJID_DETAIL = ?  WHERE BJI_INSTANCEID = ? ";
    private static final String DELETE_INSTANCE_DETAIL = "DELETE FROM BI_JOBS_INSTANCE_DETAIL WHERE BJI_INSTANCEID = ? ";
    private static final String INSERT_INSTANCE_DETAIL_WITH_TIME = "INSERT INTO BI_JOBS_INSTANCE_DETAIL ( BJI_INSTANCEID , BJID_PARAMS , BJID_UPDATE_TIME )  VALUES (?, ?, ?) ";
    private static final String UPDATE_INSTANCE_DETAIL_WITH_TIME = "UPDATE BI_JOBS_INSTANCE_DETAIL SET BJID_DETAIL = ? , BJID_UPDATE_TIME = ?  WHERE BJI_INSTANCEID = ? ";
    private static final String SQL_DELETE_NO_EXIST_IN_INSTANCE_TABLE = " DELETE FROM BI_JOBS_INSTANCE_DETAIL WHERE BJI_INSTANCEID IN ( SELECT BJI_INSTANCEID FROM ( SELECT R.BJI_INSTANCEID,I.BJI_INSTANCEID SOURCE_ID FROM BI_JOBS_INSTANCE_DETAIL R LEFT JOIN BI_JOBS_INSTANCES I ON R.BJI_INSTANCEID = I.BJI_INSTANCEID WHERE I.BJI_INSTANCEID IS NULL ) T  ) ";

    public static String selectInstanceDetail(Connection connection, String instanceId) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(SELECT_DETAIL_BY_INSTANCEID);){
            ps.setString(1, instanceId);
            try (ResultSet rs = ps.executeQuery();){
                if (rs.next()) {
                    String string = rs.getString(1);
                    return string;
                }
            }
        }
        return null;
    }

    public static String selectInstanceParams(Connection connection, String instanceId) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(SELECT_PARAMS_BY_INSTANCEID);){
            ps.setString(1, instanceId);
            try (ResultSet rs = ps.executeQuery();){
                if (rs.next()) {
                    String string = rs.getString(1);
                    return string;
                }
            }
        }
        return null;
    }

    public static void addInstanceDetailWithParams(Connection connection, String instanceId, String params) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(INSERT_INSTANCE_DETAIL);){
            ps.setString(1, instanceId);
            ps.setString(2, params);
            ps.executeUpdate();
        }
    }

    public static void deleteInstanceDetail(Connection connection, List<String> instanceIds) throws SQLException {
        if (instanceIds == null || instanceIds.isEmpty()) {
            return;
        }
        try (PreparedStatement ps = connection.prepareStatement(DELETE_INSTANCE_DETAIL);){
            for (String instanceId : instanceIds) {
                ps.setString(1, instanceId);
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    public static void deleteNoSourceDetail(Connection connection) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(SQL_DELETE_NO_EXIST_IN_INSTANCE_TABLE);){
            ps.executeBatch();
        }
    }

    public static void updateInstanceDetail(Connection connection, String instanceId, String detail) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(UPDATE_INSTANCE_DETAIL);){
            ps.setString(1, detail);
            ps.setString(2, instanceId);
            ps.executeUpdate();
        }
    }

    public static void addImmediatelyInstanceDetailWithParams(Connection connection, String instanceId, String params) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(INSERT_INSTANCE_DETAIL_WITH_TIME);){
            ps.setString(1, instanceId);
            ps.setString(2, params);
            ps.setLong(3, System.currentTimeMillis());
            ps.executeUpdate();
        }
    }

    public static void updateImmediatelyInstanceDetail(Connection connection, String instanceId, String detail) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(UPDATE_INSTANCE_DETAIL_WITH_TIME);){
            ps.setString(1, detail);
            ps.setLong(2, System.currentTimeMillis());
            ps.setString(3, instanceId);
            ps.executeUpdate();
        }
    }
}

