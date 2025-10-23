/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.nr.reminder.plan.dao.impl;

import com.jiuqi.nr.reminder.plan.common.CbUtils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.springframework.jdbc.core.RowMapper;

public class CbPlanContentDO
implements RowMapper<CbPlanContentDO> {
    private String id;
    private String title;
    private String content;
    private String showContent;
    private String createUser;
    private String updateUser;
    private Timestamp updateTime;
    private Timestamp createTime;
    public static final String NR_CB_CONTENT = "NR_CB_PLAN_CONTENT";
    public static final String ID_COL = "ID";
    public static final String TITLE_COL = "TITLE";
    public static final String CONTENT_COL = "P_CONTENT";
    public static final String CREATE_USER_COL = "P_CREATE_USER";
    public static final String UPDATE_USER_COL = "P_UPDATE_USER";
    public static final String CREATE_TIME_COL = "P_CREATE_TIME";
    public static final String UPDATE_TIME_COL = "P_UPDATE_TIME";
    public static final List<String> SELECT_COLUMNS = new ArrayList<String>(){
        {
            this.add(CbPlanContentDO.ID_COL);
            this.add(CbPlanContentDO.TITLE_COL);
            this.add(CbPlanContentDO.CONTENT_COL);
            this.add(CbPlanContentDO.CREATE_USER_COL);
            this.add(CbPlanContentDO.UPDATE_USER_COL);
            this.add(CbPlanContentDO.CREATE_TIME_COL);
            this.add(CbPlanContentDO.UPDATE_TIME_COL);
        }
    };

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
        this.showContent = CbUtils.removeTag(content);
    }

    public String getShowContent() {
        return this.showContent;
    }

    public void setShowContent(String showContent) {
        this.showContent = showContent;
    }

    public String getCreateUser() {
        return this.createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUpdateUser() {
        return this.updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Timestamp getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public Timestamp getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public CbPlanContentDO mapRow(ResultSet rs, int rowNum) throws SQLException {
        CbPlanContentDO contentDO = new CbPlanContentDO();
        contentDO.setId(rs.getString(ID_COL));
        contentDO.setTitle(rs.getString(TITLE_COL));
        contentDO.setContent(rs.getString(CONTENT_COL));
        contentDO.setCreateUser(rs.getString(CREATE_USER_COL));
        contentDO.setUpdateUser(rs.getString(UPDATE_USER_COL));
        contentDO.setCreateTime(rs.getTimestamp(CREATE_TIME_COL));
        contentDO.setUpdateTime(rs.getTimestamp(UPDATE_TIME_COL));
        return contentDO;
    }
}

