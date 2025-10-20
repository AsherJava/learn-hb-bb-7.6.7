/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.core.jobs.dao;

import com.jiuqi.bi.core.jobs.bean.JobConfigBean;
import com.jiuqi.bi.util.StringUtils;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConfigDao {
    private static final String T_NAME = "BI_JOBS_CONFIG";
    private static final String F_GUID = "BJC_GUID";
    private static final String F_NAME = "BJC_NAME";
    private static final String F_NODE = "BJC_NODE";
    private static final String F_VALUE = "BJC_VALUE";
    private static final String F_VALUE_BLOB = "BJC_VALUE_BLOB";
    private static final String SELECT_BY_NAME = "SELECT BJC_GUID, BJC_NAME, BJC_NODE, BJC_VALUE, BJC_VALUE_BLOB FROM BI_JOBS_CONFIG WHERE BJC_NAME = ? ";
    private static final String SELECT_BY_NAME_AND_NODE = "SELECT BJC_GUID, BJC_NAME, BJC_NODE, BJC_VALUE, BJC_VALUE_BLOB FROM BI_JOBS_CONFIG WHERE BJC_NAME = ?  AND BJC_NODE = ? ";
    private static final String SELECT_BY_NAME_AND_VALUE = "SELECT BJC_GUID, BJC_NAME, BJC_NODE, BJC_VALUE, BJC_VALUE_BLOB FROM BI_JOBS_CONFIG WHERE BJC_NAME = ?  AND BJC_VALUE = ? ";
    private static final String UPDATE_VALUE = "UPDATE BI_JOBS_CONFIG SET BJC_VALUE=?  WHERE BJC_GUID = ? ";
    private static final String UPDATE_VALUE_BY_NAME_AND_NODE = "UPDATE BI_JOBS_CONFIG SET BJC_VALUE=?  WHERE BJC_NAME = ?  AND BJC_NODE = ? ";
    private static final String DELETE_BY_NAME = "DELETE FROM BI_JOBS_CONFIG WHERE BJC_NAME = ? ";
    private static final String DELETE_BY_NAME_AND_VALUE = "DELETE FROM BI_JOBS_CONFIG WHERE BJC_NAME = ?  AND BJC_VALUE = ? ";
    private static final String INSERT_BATCH = "INSERT INTO BI_JOBS_CONFIG ( BJC_GUID, BJC_NAME, BJC_NODE, BJC_VALUE, BJC_VALUE_BLOB ) VALUES(?, ?, ?, ?, ?) ";

    private ConfigDao() {
    }

    public static List<JobConfigBean> selectByName(Connection conn, String name) throws SQLException {
        ArrayList<JobConfigBean> configs = new ArrayList<JobConfigBean>();
        try (PreparedStatement ps = conn.prepareStatement(SELECT_BY_NAME);){
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery();){
                while (rs.next()) {
                    JobConfigBean config = new JobConfigBean();
                    config.setGuid(rs.getString(1));
                    config.setName(rs.getString(2));
                    config.setNode(rs.getString(3));
                    config.setValue(rs.getString(4));
                    if (rs.getBytes(5) != null) {
                        String blobValue = new String(rs.getBytes(5), StandardCharsets.UTF_8);
                        config.setBlobValue(blobValue);
                    }
                    configs.add(config);
                }
            }
        }
        return configs;
    }

    public static List<JobConfigBean> selectByNameAndNode(Connection conn, String name, String node) throws SQLException {
        ArrayList<JobConfigBean> configs = new ArrayList<JobConfigBean>();
        try (PreparedStatement ps = conn.prepareStatement(SELECT_BY_NAME_AND_NODE);){
            ps.setString(1, name);
            ps.setString(2, node);
            try (ResultSet rs = ps.executeQuery();){
                while (rs.next()) {
                    JobConfigBean config = new JobConfigBean();
                    config.setGuid(rs.getString(1));
                    config.setName(rs.getString(2));
                    config.setNode(rs.getString(3));
                    config.setValue(rs.getString(4));
                    if (rs.getBytes(5) != null) {
                        String blobValue = new String(rs.getBytes(5), StandardCharsets.UTF_8);
                        config.setBlobValue(blobValue);
                    }
                    configs.add(config);
                }
            }
        }
        return configs;
    }

    public static List<JobConfigBean> selectByNameAndValue(Connection conn, String name, String value) throws SQLException {
        ArrayList<JobConfigBean> configs = new ArrayList<JobConfigBean>();
        try (PreparedStatement ps = conn.prepareStatement(SELECT_BY_NAME_AND_VALUE);){
            ps.setString(1, name);
            ps.setString(2, value);
            try (ResultSet rs = ps.executeQuery();){
                while (rs.next()) {
                    JobConfigBean config = new JobConfigBean();
                    config.setGuid(rs.getString(1));
                    config.setName(rs.getString(2));
                    config.setNode(rs.getString(3));
                    config.setValue(rs.getString(4));
                    if (rs.getBytes(5) != null) {
                        String blobValue = new String(rs.getBytes(5), StandardCharsets.UTF_8);
                        config.setBlobValue(blobValue);
                    }
                    configs.add(config);
                }
            }
        }
        return configs;
    }

    public static int updateValue(Connection conn, String guid, String value) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(UPDATE_VALUE);){
            ps.setString(1, value);
            ps.setString(2, guid);
            int n = ps.executeUpdate();
            return n;
        }
    }

    public static int updateValueByNameAndNode(Connection conn, String name, String node, String value) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(UPDATE_VALUE_BY_NAME_AND_NODE);){
            ps.setString(1, value);
            ps.setString(2, name);
            ps.setString(3, node);
            int n = ps.executeUpdate();
            return n;
        }
    }

    public static void deleteByName(Connection conn, String name) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(DELETE_BY_NAME);){
            ps.setString(1, name);
            ps.execute();
        }
    }

    public static void deleteByNameAndValue(Connection conn, String name, String value) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(DELETE_BY_NAME_AND_VALUE);){
            ps.setString(1, name);
            ps.setString(2, value);
            ps.execute();
        }
    }

    public static void insertBatch(Connection conn, List<JobConfigBean> configs) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(INSERT_BATCH);){
            for (JobConfigBean config : configs) {
                ps.setString(1, config.getGuid());
                ps.setString(2, config.getName());
                ps.setString(3, config.getNode());
                ps.setString(4, config.getValue());
                byte[] valueBytes = null;
                if (StringUtils.isNotEmpty((String)config.getBlobValue())) {
                    valueBytes = config.getBlobValue().getBytes(StandardCharsets.UTF_8);
                }
                ps.setBytes(5, valueBytes);
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }
}

