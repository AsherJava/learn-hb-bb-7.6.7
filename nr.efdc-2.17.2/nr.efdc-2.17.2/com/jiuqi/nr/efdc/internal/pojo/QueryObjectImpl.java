/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.sql.misc.UUIDHelper
 */
package com.jiuqi.nr.efdc.internal.pojo;

import com.jiuqi.np.sql.misc.UUIDHelper;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public class QueryObjectImpl
implements Serializable {
    private static final long serialVersionUID = 7592232843257053455L;
    private String id;
    private String taskKey;
    private String formSchemeKey;
    private String mainDim;
    private List<String> mainDims;
    private String assistDim;
    private Timestamp updatetime;
    private String solutionKey;
    private String rptScheme;

    public QueryObjectImpl() {
    }

    public QueryObjectImpl(UUID taskKey, UUID formSchemeKey, UUID mainDim) {
        this.taskKey = taskKey.toString();
        this.formSchemeKey = formSchemeKey.toString();
        this.mainDim = mainDim.toString();
    }

    public QueryObjectImpl(String taskKey, String formSchemeKey, String mainDim) {
        this.taskKey = taskKey;
        this.formSchemeKey = formSchemeKey;
        this.mainDim = mainDim;
    }

    public QueryObjectImpl(String id, String taskKey, String formSchemeKey, String mainDim, String assistDim, Timestamp updatetime, String solutionKey, String rptScheme) {
        this.id = id;
        this.taskKey = taskKey;
        this.formSchemeKey = formSchemeKey;
        this.mainDim = mainDim;
        this.assistDim = assistDim;
        this.updatetime = updatetime;
        this.solutionKey = solutionKey;
        this.rptScheme = rptScheme;
    }

    @Deprecated
    public QueryObjectImpl(UUID taskKey, UUID formSchemeKey, String periodCode, String utargetKey) {
        this.taskKey = taskKey.toString();
        this.formSchemeKey = formSchemeKey.toString();
        this.mainDim = utargetKey;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getMainDim() {
        return this.mainDim;
    }

    public void setMainDim(String mainDim) {
        this.mainDim = mainDim;
    }

    public List<String> getMainDims() {
        return this.mainDims;
    }

    public void setMainDims(List<String> mainDims) {
        this.mainDims = mainDims;
    }

    public String getAssistDim() {
        return this.assistDim;
    }

    public void setAssistDim(String assistDim) {
        this.assistDim = assistDim;
    }

    public Timestamp getUpdatetime() {
        return this.updatetime;
    }

    public void setUpdatetime(Timestamp updatetime) {
        this.updatetime = updatetime;
    }

    public String getSolutionKey() {
        return this.solutionKey;
    }

    public void setSolutionKey(String solutionKey) {
        this.solutionKey = solutionKey;
    }

    public String getRptScheme() {
        return this.rptScheme;
    }

    public void setRptScheme(String rptScheme) {
        this.rptScheme = rptScheme;
    }

    public static QueryObjectImpl buildObjectByRs(ResultSet rs) throws SQLException {
        String id = UUIDHelper.valueOf((byte[])rs.getBytes("dc_key")).toString();
        String taskKey = UUIDHelper.valueOf((byte[])rs.getBytes("dc_task_key")).toString();
        String formulaScheme = UUIDHelper.valueOf((byte[])rs.getBytes("dc_formscheme_key")).toString();
        String mainDim = UUIDHelper.valueOf((byte[])rs.getBytes("dc_main_dim")).toString();
        String assistDim = rs.getString("dc_assist_dim");
        Timestamp time = rs.getTimestamp("dc_updatetime");
        UUID dcSolutionKey = UUIDHelper.valueOf((byte[])rs.getBytes("dc_solution_key"));
        String soluction = "";
        if (dcSolutionKey != null) {
            soluction = dcSolutionKey.toString();
        }
        UUID dcRptScheme = UUIDHelper.valueOf((byte[])rs.getBytes("dc_rpt_scheme"));
        String rpt_scheme = "";
        if (dcRptScheme != null) {
            rpt_scheme = dcRptScheme.toString();
        }
        return new QueryObjectImpl(id, taskKey, formulaScheme, mainDim, assistDim, time, soluction, rpt_scheme);
    }
}

