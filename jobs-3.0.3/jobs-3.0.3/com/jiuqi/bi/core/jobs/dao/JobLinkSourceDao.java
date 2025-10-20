/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.jobs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JobLinkSourceDao {
    private static final String T_NAME = "BI_JOBS_INSTANCE_RELATESOURCE";
    private static final String F_JOB_GUID = "BJI_GUID";
    private static final String F_SOURCE_GUID = "BJIR_SOURCE";
    private static final String JOBI_NAME = "BI_JOBS_INSTANCES";
    private static final String JOBI_GUID = "BJI_JOBGUID";
    private static final String JOBI_STATE = "BJI_STATE";
    private static final String SELECT_SOURCES_BY_JOB_GUID = "SELECT BJIR_SOURCE FROM BI_JOBS_INSTANCE_RELATESOURCE WHERE BJI_GUID = ? ";
    private static final String INSERT_JOB_SOUCE = "INSERT INTO BI_JOBS_INSTANCE_RELATESOURCE ( BJI_GUID , BJIR_SOURCE )  VALUES (?, ?) ";
    private static final String DELETE_JOB_SOUECE = "DELETE FROM BI_JOBS_INSTANCE_RELATESOURCE WHERE BJI_GUID = ? ";

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static List<String> selectJobSource(Connection connection, String jobGuid) throws SQLException {
        ArrayList<String> sources = new ArrayList<String>();
        try (PreparedStatement ps = connection.prepareStatement(SELECT_SOURCES_BY_JOB_GUID);){
            ps.setString(1, jobGuid);
            try (ResultSet rs = ps.executeQuery();){
                while (rs.next()) {
                    sources.add(rs.getString(1));
                }
            }
        }
        return sources;
    }

    public static void addJobSource(Connection connection, String jobGuid, List<String> sourceGuid) throws SQLException {
        if (sourceGuid == null || sourceGuid.isEmpty()) {
            return;
        }
        try (PreparedStatement ps = connection.prepareStatement(INSERT_JOB_SOUCE);){
            ps.setString(1, jobGuid);
            for (String s : sourceGuid) {
                ps.setString(2, s);
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    public static void deleteJobSource(Connection connection, List<String> jobGuids) throws SQLException {
        if (jobGuids == null || jobGuids.isEmpty()) {
            return;
        }
        try (PreparedStatement ps = connection.prepareStatement(DELETE_JOB_SOUECE);){
            for (String jobGuid : jobGuids) {
                ps.setString(1, jobGuid);
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    public static boolean isLinkSourcesRunning(Connection conn, List<String> linkSource) throws SQLException {
        if (linkSource == null || linkSource.isEmpty()) {
            return false;
        }
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM (SELECT * FROM BI_JOBS_INSTANCE_RELATESOURCE WHERE BJIR_SOURCE");
        sql.append(" IN (");
        for (int i = 0; i < linkSource.size(); ++i) {
            sql.append("'");
            sql.append(linkSource.get(i));
            sql.append("'");
            if (linkSource.size() - 1 == i) continue;
            sql.append(",");
        }
        sql.append(")) BJIR LEFT JOIN BI_JOBS_INSTANCES BJI on BJI.BJI_JOBGUID = BJIR.BJI_GUID");
        sql.append(" WHERE BJI.BJI_STATE = 0 OR BJI.BJI_STATE = -1");
        try (PreparedStatement ps = conn.prepareStatement(sql.toString());
             ResultSet resultSet = ps.executeQuery();){
            if (resultSet.next()) {
                boolean bl = true;
                return bl;
            }
        }
        return false;
    }
}

