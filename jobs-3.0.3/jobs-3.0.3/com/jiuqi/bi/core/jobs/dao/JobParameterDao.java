/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONArray
 *  org.json.JSONObject
 */
package com.jiuqi.bi.core.jobs.dao;

import com.jiuqi.bi.core.jobs.model.JobParameter;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class JobParameterDao {
    private static final String T_NAME = "BI_JOBS_PARAMETER";
    private static final String F_GUID = "JOB_GUID";
    private static final String F_DATA = "PARAMS_DATA";
    private static final String SELECT_BY_GUID = "SELECT PARAMS_DATA FROM BI_JOBS_PARAMETER WHERE JOB_GUID = ? ";
    private static final String INSERT_PARAMS = "INSERT INTO BI_JOBS_PARAMETER ( JOB_GUID , PARAMS_DATA ) VALUES (?, ?) ";
    private static final String DELETE_PARAMS = "DELETE  FROM BI_JOBS_PARAMETER WHERE JOB_GUID = ? ";

    private JobParameterDao() {
    }

    public static List<JobParameter> selectByJobGuid(Connection conn, String jobGuid) throws SQLException {
        ArrayList<JobParameter> parameters = new ArrayList<JobParameter>();
        try (PreparedStatement ps = conn.prepareStatement(SELECT_BY_GUID);){
            ps.setString(1, jobGuid);
            try (ResultSet rs = ps.executeQuery();){
                if (rs.next()) {
                    byte[] bytes = rs.getBytes(1);
                    long bytesLength = bytes.length;
                    if (bytesLength == 0L) {
                        ArrayList<JobParameter> arrayList = parameters;
                        return arrayList;
                    }
                    JSONArray array = new JSONArray(new String(bytes, StandardCharsets.UTF_8));
                    for (int j = 0; j < array.length(); ++j) {
                        JSONObject jsonObject = array.getJSONObject(j);
                        JobParameter parameter = new JobParameter();
                        parameter.fromJson(jsonObject);
                        parameters.add(parameter);
                    }
                }
            }
        }
        return parameters;
    }

    public static void insertParameters(Connection conn, String jobGuid, List<JobParameter> parameters) throws SQLException {
        if (parameters == null || parameters.isEmpty()) {
            return;
        }
        JSONArray array = new JSONArray();
        for (JobParameter parameter : parameters) {
            array.put((Object)parameter.toJson());
        }
        try (PreparedStatement ps = conn.prepareStatement(INSERT_PARAMS);){
            ps.setString(1, jobGuid);
            ps.setBytes(2, array.toString().getBytes(StandardCharsets.UTF_8));
            ps.execute();
        }
    }

    public static void deleteParameters(Connection conn, String jobGuid) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(DELETE_PARAMS);){
            ps.setString(1, jobGuid);
            ps.execute();
        }
    }
}

