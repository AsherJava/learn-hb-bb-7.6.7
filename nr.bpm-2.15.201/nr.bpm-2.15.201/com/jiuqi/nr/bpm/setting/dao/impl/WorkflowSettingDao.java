/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.ContextExtension
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.logging.LogFactory
 *  com.jiuqi.np.logging.Logger
 *  com.jiuqi.np.util.NpRollbackException
 *  com.jiuqi.nr.context.cxt.DsContext
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.util.OrderGenerator
 *  com.jiuqi.util.StringUtils
 *  javax.annotation.Resource
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.JdbcOperations
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.PreparedStatementCallback
 *  org.springframework.jdbc.core.namedparam.MapSqlParameterSource
 *  org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
 *  org.springframework.jdbc.core.namedparam.SqlParameterSource
 *  org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback
 *  org.springframework.jdbc.support.lob.DefaultLobHandler
 *  org.springframework.jdbc.support.lob.LobCreator
 *  org.springframework.jdbc.support.lob.LobHandler
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.bpm.setting.dao.impl;

import com.jiuqi.np.core.context.ContextExtension;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.logging.LogFactory;
import com.jiuqi.np.logging.Logger;
import com.jiuqi.np.util.NpRollbackException;
import com.jiuqi.nr.bpm.setting.bean.WorkflowSettingDefine;
import com.jiuqi.nr.bpm.setting.pojo.UnBindResult;
import com.jiuqi.nr.bpm.setting.pojo.WorkflowSettingPojo;
import com.jiuqi.nr.bpm.setting.utils.SettingUtil;
import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.util.OrderGenerator;
import com.jiuqi.util.StringUtils;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class WorkflowSettingDao {
    private static final Logger logger = LogFactory.getLogger(WorkflowSettingDao.class);
    private static final String TABLE_NAME_SETTING = "SYS_WORKFLOW_SETTING";
    private static final String TABLE_NAME_RELATION = "SYS_WORKFLOW_RELATION";
    private static final String S_ID = "S_ID";
    private static final String S_TITLE = "S_TITLE";
    private static final String S_DATATYPE = "S_DATATYPE";
    private static final String S_DATAID = "S_DATAID";
    private static final String S_WORKFLOW_ID = "S_WORKFLOWID";
    private static final String S_DESC = "S_DESC";
    private static final String S_CREATEUSER = "S_CREATEUSER";
    private static final String S_UPDATETIME = "S_UPDATETIME";
    private static final String S_VERSION = "S_VERSION";
    private static final String S_CHOOSEALL = "S_CHOOSEALL";
    private static final String S_ORDER = "S_ORDER";
    private static final String S_TYPE = "S_TYPE";
    private static final String S_UNIT_ID = "S_UNITID";
    private static final String S_SETTING_ID = "S_SETTINGID";
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Resource
    private IRuntimeDataSchemeService dataSchemeService;

    @Transactional
    public String insertData(WorkflowSettingPojo workflowSetting) {
        try {
            if (workflowSetting != null) {
                String workflowId = this.creatWorkFlowSetting(workflowSetting);
                return workflowId;
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
            throw new RuntimeException(e.getMessage());
        }
        return null;
    }

    public void updateData(WorkflowSettingPojo workflowSetting) {
        try {
            if (null != workflowSetting) {
                this.updateWorkflowSetting(workflowSetting);
                if (null != workflowSetting.getAddObj() && !workflowSetting.getAddObj().isEmpty()) {
                    Set<String> addDatas = workflowSetting.getAddObj();
                    for (String dataObj : addDatas) {
                        this.createWorkflowRelation(dataObj, workflowSetting.getKey());
                    }
                }
                if (null != workflowSetting.getDeleteObj() && !workflowSetting.getDeleteObj().isEmpty()) {
                    this.deleteWorkflowRelation(workflowSetting.getKey(), workflowSetting.getDeleteObj());
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
            throw new RuntimeException();
        }
    }

    public void updateWorkflowState() {
    }

    private String creatWorkFlowSetting(final WorkflowSettingPojo workflowSetting) {
        final String ID = UUID.randomUUID().toString();
        try {
            StringBuffer sbSql = new StringBuffer();
            sbSql.append("INSERT INTO ").append(TABLE_NAME_SETTING).append("(");
            sbSql.append(S_ID).append(",");
            sbSql.append(S_TITLE).append(",");
            sbSql.append(S_DATATYPE).append(",");
            sbSql.append(S_DATAID).append(",");
            sbSql.append(S_WORKFLOW_ID).append(",");
            sbSql.append(S_DESC).append(",");
            sbSql.append(S_CREATEUSER).append(",");
            sbSql.append(S_UPDATETIME).append(",");
            sbSql.append(S_VERSION).append(",");
            sbSql.append(S_CHOOSEALL).append(",");
            sbSql.append(S_ORDER).append(",");
            sbSql.append(S_TYPE).append(")");
            sbSql.append(" VALUES (?,?,?,?,?,?,?,?,?,?,?,?) ");
            int execute = (Integer)this.jdbcTemplate.execute(sbSql.toString(), (PreparedStatementCallback)new AbstractLobCreatingPreparedStatementCallback((LobHandler)new DefaultLobHandler()){

                protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException, DataAccessException {
                    ps.setString(1, ID);
                    ps.setString(2, workflowSetting.getTitle());
                    ps.setString(3, workflowSetting.getDataType());
                    ps.setString(4, workflowSetting.getDataId());
                    ps.setString(5, workflowSetting.getWorkflowId());
                    ps.setString(6, workflowSetting.getDesc());
                    ps.setString(7, WorkflowSettingDao.getCurrentUserName());
                    ps.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
                    ps.setString(9, "1.0.0");
                    ps.setInt(10, workflowSetting.isChooseAll() ? 1 : 0);
                    String order = workflowSetting.getOrder();
                    if (null == order || order.isEmpty()) {
                        order = OrderGenerator.newOrder();
                    }
                    ps.setString(11, order);
                    ps.setInt(12, workflowSetting.getType());
                }
            });
            if (execute > 0) {
                return ID;
            }
            return "\u65b0\u589e\u6d41\u7a0b\u7ba1\u7406\u5b9a\u4e49,\u6570\u636e\u5e93\u6267\u884c\u63d2\u5165\u8bed\u53e5\u5931\u8d25\uff01";
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
            return null;
        }
    }

    private String createWorkflowRelation(final String dataObj, final String settingId) {
        try {
            StringBuffer sbSql = new StringBuffer();
            sbSql.append("INSERT INTO ").append(TABLE_NAME_RELATION).append(" (");
            sbSql.append(S_SETTING_ID).append(",");
            sbSql.append(S_UNIT_ID).append(") ");
            sbSql.append(" VALUES (?,?)");
            int execute = (Integer)this.jdbcTemplate.execute(sbSql.toString(), (PreparedStatementCallback)new AbstractLobCreatingPreparedStatementCallback((LobHandler)new DefaultLobHandler()){

                protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException, DataAccessException {
                    ps.setString(1, settingId);
                    ps.setString(2, dataObj);
                }
            });
            if (execute < 0) {
                return "\u65b0\u589e\u6d41\u7a0b\u914d\u7f6e\u5173\u8054\u8868,\u6570\u636e\u5e93\u6267\u884c\u63d2\u5165\u8bed\u53e5\u5931\u8d25\uff01";
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
            throw new RuntimeException();
        }
        return "";
    }

    private String updateWorkflowSetting(final WorkflowSettingPojo workflowSetting) {
        try {
            StringBuffer sbSql = new StringBuffer();
            sbSql.append("UPDATE ").append(TABLE_NAME_SETTING).append(" set ");
            sbSql.append(S_TITLE).append(" = ?, ");
            sbSql.append(S_DATATYPE).append(" = ?, ");
            sbSql.append(S_DATAID).append(" = ?, ");
            sbSql.append(S_WORKFLOW_ID).append(" = ?, ");
            sbSql.append(S_DESC).append(" = ?, ");
            sbSql.append(S_CREATEUSER).append(" = ?, ");
            sbSql.append(S_UPDATETIME).append(" = ?, ");
            sbSql.append(S_VERSION).append(" = ?, ");
            sbSql.append(S_CHOOSEALL).append(" = ?, ");
            sbSql.append(S_ORDER).append(" = ?, ");
            sbSql.append(S_TYPE).append(" = ?, ");
            sbSql.append(" WHERE ").append(S_ID).append(" = ? ");
            int execute = (Integer)this.jdbcTemplate.execute(sbSql.toString(), (PreparedStatementCallback)new AbstractLobCreatingPreparedStatementCallback((LobHandler)new DefaultLobHandler()){

                protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException, DataAccessException {
                    ps.setString(1, workflowSetting.getTitle());
                    ps.setString(2, workflowSetting.getDataType());
                    ps.setString(3, workflowSetting.getDataId());
                    ps.setString(4, workflowSetting.getWorkflowId());
                    ps.setString(5, workflowSetting.getDesc());
                    ps.setString(6, SettingUtil.getCurrentUserId());
                    ps.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
                    ps.setString(8, "1.0.0");
                    ps.setInt(9, workflowSetting.isChooseAll() ? 1 : 0);
                    String order = workflowSetting.getOrder();
                    if (null == order || order.isEmpty()) {
                        order = OrderGenerator.newOrder();
                    }
                    ps.setString(10, order);
                    ps.setInt(11, workflowSetting.getType());
                    ps.setString(12, workflowSetting.getKey());
                }
            });
            if (execute > 0) {
                return "";
            }
            return "\u6570\u636e\u5e93\u6267\u884c\u66f4\u65b0\u8bed\u53e5\u5931\u8d25\uff01";
        }
        catch (Exception e) {
            return e.getMessage();
        }
    }

    public List<WorkflowSettingDefine> getWorkflowSettingList() {
        List<WorkflowSettingDefine> workflowSettingDefineList = this.getWorkflowSettingDefineList();
        if (workflowSettingDefineList != null) {
            for (WorkflowSettingDefine workflowSettingDefine : workflowSettingDefineList) {
                List<String> unitList = this.getUnitListBySetingId(workflowSettingDefine.getId());
                String[] strings = new String[unitList.size()];
                unitList.toArray(strings);
                workflowSettingDefine.setDataObj(strings);
            }
        }
        return workflowSettingDefineList;
    }

    public List<WorkflowSettingDefine> getWorkflowSettingDefineList() {
        StringBuffer sbSql = new StringBuffer();
        sbSql.append("SELECT * FROM ");
        sbSql.append(TABLE_NAME_SETTING);
        sbSql.append(" order by ").append(S_ORDER);
        ArrayList<WorkflowSettingDefine> dlist = new ArrayList<WorkflowSettingDefine>();
        this.jdbcTemplate.query(sbSql.toString(), rs -> {
            WorkflowSettingDefine define = this.getDefineByResultSet(rs);
            dlist.add(define);
        });
        return dlist;
    }

    public WorkflowSettingDefine getWorkflowSettingDefineById(String settingId) {
        StringBuffer sbSql = new StringBuffer();
        sbSql.append("SELECT * FROM ");
        sbSql.append(TABLE_NAME_SETTING);
        sbSql.append(" t WHERE ").append(S_ID).append(" = ?").append(" order by ").append(S_ORDER);
        Object[] params = new Object[]{settingId};
        WorkflowSettingDefine define = new WorkflowSettingDefine();
        this.jdbcTemplate.query(sbSql.toString(), rs -> {
            define.setId(rs.getString(S_ID));
            define.setTitle(rs.getString(S_TITLE));
            define.setDataType(rs.getString(S_DATATYPE));
            define.setDataId(rs.getString(S_DATAID));
            define.setWorkflowId(rs.getString(S_WORKFLOW_ID));
            define.setDesc(rs.getString(S_DESC));
            define.setChooseAll("1".equals(String.valueOf(rs.getInt(S_CHOOSEALL))));
            define.setCreateUser(rs.getString(S_CHOOSEALL));
            Timestamp time = rs.getTimestamp(S_UPDATETIME);
            if (time != null) {
                define.setUpdateTime(new Date(time.getTime()));
            }
            define.setOrder(rs.getString(S_ORDER));
            define.setType(rs.getInt(S_TYPE));
        }, params);
        return define;
    }

    public List<String> getUnitListBySetingId(String settingId) {
        StringBuffer sbSql = new StringBuffer();
        sbSql.append("SELECT * FROM ");
        sbSql.append(TABLE_NAME_RELATION);
        sbSql.append(" t WHERE ").append(S_SETTING_ID).append(" = ?");
        Object[] params = new Object[]{settingId};
        ArrayList<String> unitlist = new ArrayList<String>();
        this.jdbcTemplate.query(sbSql.toString(), rs -> {
            String unitId = rs.getString("s_unitId");
            unitlist.add(unitId);
        }, params);
        return unitlist;
    }

    private WorkflowSettingDefine getDefineByResultSet(ResultSet rs) throws SQLException {
        WorkflowSettingDefine define = new WorkflowSettingDefine();
        define.setId(rs.getString(S_ID));
        define.setTitle(rs.getString(S_TITLE));
        define.setDataType(rs.getString(S_DATATYPE));
        define.setDataId(rs.getString(S_DATAID));
        define.setWorkflowId(rs.getString(S_WORKFLOW_ID));
        define.setDesc(rs.getString(S_DESC));
        define.setChooseAll("1".equals(String.valueOf(rs.getInt(S_CHOOSEALL))));
        define.setCreateUser(rs.getString(S_CREATEUSER));
        Timestamp time = rs.getTimestamp(S_UPDATETIME);
        if (time != null) {
            define.setUpdateTime(new Date(time.getTime()));
        }
        define.setOrder(rs.getString(S_ORDER));
        define.setType(rs.getInt(S_TYPE));
        return define;
    }

    public String delWorkFlowSettingByID(String settingId) {
        try {
            String sql = "DELETE FROM SYS_WORKFLOW_SETTING WHERE S_ID = ?";
            Object[] params = new Object[]{settingId};
            int count = this.jdbcTemplate.update(sql, params);
            logger.info("\u5220\u9664\u6307\u5b9a\u7684\u6d41\u7a0b\u914d\u7f6e" + count);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
        }
        this.deleteWorkflowRelation(settingId);
        return "";
    }

    public String delWorkFlowSettingBySchemeKey(String formSchemeKey) {
        try {
            String sql = "DELETE FROM SYS_WORKFLOW_SETTING  WHERE S_DATAID = ?";
            Object[] params = new Object[]{formSchemeKey};
            int count = this.jdbcTemplate.update(sql, params);
            logger.info("\u5220\u9664\u6307\u5b9a\u7684\u6d41\u7a0b\u914d\u7f6e" + count);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
        }
        this.deleteWorkflowRelationByFormSchemeKey(formSchemeKey);
        return "";
    }

    private void deleteWorkflowRelation(String settingId) {
        try {
            String sql = "DELETE FROM  SYS_WORKFLOW_RELATION   WHERE S_SETTINGID = ?";
            Object[] params = new Object[]{settingId};
            int count = this.jdbcTemplate.update(sql, params);
            logger.info("\u5220\u9664\u7684\u5355\u4f4d\u5173\u8054\u8868\u4fe1\u606f" + count);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
        }
    }

    private void deleteWorkflowRelationByFormSchemeKey(String formSchemeKey) {
        try {
            String sql = "DELETE FROM  SYS_WORKFLOW_RELATION   WHERE S_FORMSCHMEKEY = ?";
            Object[] params = new Object[]{formSchemeKey};
            int count = this.jdbcTemplate.update(sql, params);
            logger.info("\u5220\u9664\u7684\u5355\u4f4d\u5173\u8054\u8868\u4fe1\u606f" + count);
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public void deleteWorkflowRelation(String settingId, Set<String> unitIds) {
        try {
            int i = 0;
            if (unitIds.size() > 0) {
                for (String unitid : unitIds) {
                    String sql = "DELETE FROM SYS_WORKFLOW_RELATION WHERE S_SETTINGID = ? and S_UNITID = ?";
                    Object[] params = new Object[]{settingId, unitid};
                    int count = this.jdbcTemplate.update(sql, params);
                    i += count;
                }
            }
            logger.info("\u5220\u9664\u7684\u5355\u4f4d\u5173\u8054\u8868\u4fe1\u606f" + i);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
        }
    }

    public void delWorkFlowStateBysettingId(String settingId, String tableName) {
        try {
            String sql = "DELETE FROM " + tableName + " WHERE " + S_SETTING_ID + " = ?";
            Object[] params = new Object[]{settingId};
            int count = this.jdbcTemplate.update(sql, params);
            logger.info("\u5220\u9664\u7684\u72b6\u6001\u8868\u4fe1\u606f" + count);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
        }
    }

    public static ContextUser getCurrentUser() {
        NpContext context = NpContextHolder.getContext();
        ContextUser user = context.getUser();
        return user;
    }

    public static String getCurrentUserName() {
        ContextUser user = WorkflowSettingDao.getCurrentUser();
        if (user != null) {
            return user.getName();
        }
        return null;
    }

    public WorkflowSettingDefine getWorkflowSettingByFormSchemeKey(String formSchemeKey) {
        String sql = "SELECT * FROM SYS_WORKFLOW_SETTING  WHERE S_DATAID = ?";
        Object[] params = new Object[]{formSchemeKey};
        WorkflowSettingDefine define = new WorkflowSettingDefine();
        this.jdbcTemplate.query(sql, params, rs -> {
            define.setId(rs.getString(S_ID));
            define.setTitle(rs.getString(S_TITLE));
            define.setDataType(rs.getString(S_DATATYPE));
            define.setDataId(rs.getString(S_DATAID));
            define.setWorkflowId(rs.getString(S_WORKFLOW_ID));
            define.setDesc(rs.getString(S_DESC));
            define.setChooseAll("1".equals(String.valueOf(rs.getInt(S_CHOOSEALL))));
            define.setCreateUser(rs.getString(S_CREATEUSER));
            Timestamp time = rs.getTimestamp(S_UPDATETIME);
            if (time != null) {
                define.setUpdateTime(new Date(time.getTime()));
            }
            define.setOrder(rs.getString(S_ORDER));
            define.setType(rs.getInt(S_TYPE));
        });
        return define;
    }

    public WorkflowSettingDefine getWorkflowIdByFormSchemeKey(String formSchemeKey) {
        String sql = "SELECT * FROM SYS_WORKFLOW_SETTING  WHERE S_DATAID = ?";
        Object[] params = new Object[]{formSchemeKey};
        WorkflowSettingDefine define = new WorkflowSettingDefine();
        this.jdbcTemplate.query(sql, params, rs -> {
            define.setId(rs.getString(S_ID));
            define.setTitle(rs.getString(S_TITLE));
            define.setDataType(rs.getString(S_DATATYPE));
            define.setDataId(rs.getString(S_DATAID));
            define.setWorkflowId(rs.getString(S_WORKFLOW_ID));
            define.setDesc(rs.getString(S_DESC));
            define.setChooseAll("1".equals(String.valueOf(rs.getInt(S_CHOOSEALL))));
            define.setCreateUser(rs.getString(S_CREATEUSER));
            Timestamp time = rs.getTimestamp(S_UPDATETIME);
            if (time != null) {
                define.setUpdateTime(new Date(time.getTime()));
            }
            define.setOrder(rs.getString(S_ORDER));
            define.setType(rs.getInt(S_TYPE));
        });
        return define;
    }

    public List<WorkflowSettingDefine> searchDefineByinput(String input) {
        String sql = "SELECT * FROM SYS_WORKFLOW_SETTING  WHERE S_TITLE like ? ";
        Object[] params = new Object[]{"%" + input + "%"};
        ArrayList<WorkflowSettingDefine> dlist = new ArrayList<WorkflowSettingDefine>();
        this.jdbcTemplate.query(sql, params, rs -> {
            WorkflowSettingDefine define = this.getDefineByResultSet(rs);
            dlist.add(define);
        });
        return dlist;
    }

    public List<WorkflowSettingDefine> getFormSchemeByWorkflow(String workflowId) {
        String sql = "SELECT * FROM SYS_WORKFLOW_SETTING  WHERE S_WORKFLOWID = ? ";
        Object[] params = new Object[]{workflowId};
        ArrayList<WorkflowSettingDefine> dlist = new ArrayList<WorkflowSettingDefine>();
        this.jdbcTemplate.query(sql, params, rs -> {
            WorkflowSettingDefine define = this.getDefineByResultSet(rs);
            dlist.add(define);
        });
        return dlist;
    }

    public void insertReleate(String formSchemeKey, String period, Set<String> unitIds, Set<String> reportIds, int type, String settingId) {
        try {
            String dimEntityKey = this.getDimEntityKey(formSchemeKey);
            String sql = "INSERT INTO SYS_WORKFLOW_RELATION (S_FORMSCHMEKEY,S_PERIOD,S_UNITID,S_REPORTID,s_type,S_SETTINGID,S_DIMENTITYKEY) VALUES ( ?, ?, ?, ? ,?,?,?)";
            ArrayList<Object[]> args = new ArrayList<Object[]>();
            if (reportIds.size() > 0) {
                for (String unitId : unitIds) {
                    for (String reportId : reportIds) {
                        Object[] param = new Object[]{formSchemeKey, period, unitId, this.converReportId(reportId), type, settingId, dimEntityKey};
                        args.add(param);
                    }
                }
            }
            this.jdbcTemplate.batchUpdate(sql, args);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
        }
    }

    public List<UnBindResult> queryDataByFormSchemeKey(String formSchemeKey, String period) {
        String sql = "SELECT * FROM SYS_WORKFLOW_RELATION t WHERE t.S_FORMSCHMEKEY = ? and t.S_PERIOD = ?";
        Object[] params = new Object[]{formSchemeKey, period};
        ArrayList<UnBindResult> dlist = new ArrayList<UnBindResult>();
        this.jdbcTemplate.query(sql, params, rs -> {
            UnBindResult define = this.getUnBindResultByResultSet(rs);
            dlist.add(define);
        });
        return dlist;
    }

    public List<UnBindResult> queryDataByType(String formSchemeKey, String period, int type) {
        String sql = "SELECT * FROM SYS_WORKFLOW_RELATION t WHERE t.S_FORMSCHMEKEY = ? and t.S_PERIOD = ? and t.S_TYPE = ? ";
        Object[] params = new Object[]{formSchemeKey, period, type};
        ArrayList<UnBindResult> dlist = new ArrayList<UnBindResult>();
        this.jdbcTemplate.query(sql, params, rs -> {
            UnBindResult define = this.getUnBindResultByResultSet(rs);
            dlist.add(define);
        });
        return dlist;
    }

    public List<UnBindResult> queryDataByType(String formSchemeKey, String period, List<String> unitIds, List<String> reportIds, int type) {
        String unitStr = "";
        String dimEntityKey = this.getDimEntityKey(formSchemeKey);
        if (unitIds != null && unitIds.size() > 0) {
            for (String string : unitIds) {
                unitStr = unitStr + "'" + string + "',";
            }
            unitStr = unitStr.substring(0, unitStr.length() - 1);
        }
        String reportStr = "";
        if (reportIds != null && reportIds.size() > 0) {
            for (String id : reportIds) {
                reportStr = reportStr + "'" + this.converReportId(id) + "',";
            }
            reportStr = reportStr.substring(0, reportStr.length() - 1);
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("SELECT * FROM ");
        stringBuffer.append(TABLE_NAME_RELATION).append(" t");
        stringBuffer.append(" where t.S_FORMSCHMEKEY = '").append(formSchemeKey).append("'").append(" and ").append(" t.S_PERIOD = '").append(period).append("'").append(" and ").append(" t.s_type = '").append(type).append("'");
        stringBuffer.append(" and ").append(" t.S_DIMENTITYKEY = '").append(dimEntityKey).append("'");
        if (StringUtils.isNotEmpty((String)unitStr)) {
            String colName = " t.S_UNITID ";
            stringBuffer.append(" and ");
            String inSql = this.getInSql(colName, unitIds, true);
            stringBuffer.append(inSql);
        }
        if (StringUtils.isNotEmpty((String)reportStr)) {
            stringBuffer.append(" and ").append(" t.S_REPORTID in (").append(reportStr).append(")");
        }
        ArrayList<UnBindResult> dlist = new ArrayList<UnBindResult>();
        this.jdbcTemplate.query(stringBuffer.toString(), rs -> {
            UnBindResult define = this.getUnBindResultByResultSet(rs);
            dlist.add(define);
        });
        return dlist;
    }

    protected String getInSql(String colName, List<String> unitIds, boolean inOrNo) {
        String in = " in ";
        String ine = " or ";
        if (!inOrNo) {
            in = " not in ";
            ine = " and ";
        }
        int count = this.getCount(unitIds.size());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; ++i) {
            String unitStr = "";
            List<String> subList = this.groupList(unitIds, i);
            if (subList != null && subList.size() > 0) {
                for (String id : subList) {
                    unitStr = unitStr + "'" + id + "',";
                }
                unitStr = unitStr.substring(0, unitStr.length() - 1);
            }
            sb.append(colName).append(in).append(" ( ").append(unitStr).append(" )");
            if (i == count - 1) continue;
            sb.append(ine);
        }
        return sb.toString();
    }

    protected List<String> groupList(List<String> list, int i) {
        if (list == null || list.size() == 0) {
            return list;
        }
        return list.subList(i * 1000, Math.min((i + 1) * 1000, list.size()));
    }

    protected int getCount(int size) {
        int count = size % 1000 == 0 ? size / 1000 : size / 1000 + 1;
        return count;
    }

    public List<UnBindResult> queryDataByType(String formSchemeKey, String period, String unitId, int type) {
        String dimEntityKey = this.getDimEntityKey(formSchemeKey);
        String sql = "SELECT * FROM SYS_WORKFLOW_RELATION t WHERE t.S_FORMSCHMEKEY = ? and t.S_UNITID and t.S_PERIOD = ? and t.S_TYPE = ? and S_DIMENTITYKEY = ?";
        Object[] params = new Object[]{formSchemeKey, unitId, period, type, dimEntityKey};
        ArrayList<UnBindResult> dlist = new ArrayList<UnBindResult>();
        this.jdbcTemplate.query(sql, params, rs -> {
            UnBindResult define = this.getUnBindResultByResultSet(rs);
            dlist.add(define);
        });
        return dlist;
    }

    public UnBindResult queryDataByType(String formSchemeKey, String period, String unitId, String reportId, int type) {
        UnBindResult define = new UnBindResult();
        String dimEntityKey = this.getDimEntityKey(formSchemeKey);
        String sql = "SELECT * FROM SYS_WORKFLOW_RELATION t WHERE t.S_FORMSCHMEKEY = ? and t.S_UNITID = ? and t.S_REPORTID = ? and t.S_PERIOD = ? and  t.S_TYPE = ? and S_DIMENTITYKEY = ? ";
        Object[] params = new Object[]{formSchemeKey, unitId, this.converReportId(reportId), period, type, dimEntityKey};
        this.jdbcTemplate.query(sql, params, rs -> {
            define.setFormSchemeKey(rs.getString("S_FORMSCHMEKEY"));
            define.setPeriod(rs.getString("S_PERIOD"));
            define.setUnitId(rs.getString(S_UNIT_ID));
            define.setReportId(rs.getString("S_REPORTID"));
            define.setsType(rs.getInt("s_type"));
            define.setDimEntityKey(rs.getString("S_DIMENTITYKEY"));
        });
        return define;
    }

    private UnBindResult getUnBindResultByResultSet(ResultSet rs) throws SQLException {
        UnBindResult define = new UnBindResult();
        define.setFormSchemeKey(rs.getString("S_FORMSCHMEKEY"));
        define.setPeriod(rs.getString("S_PERIOD"));
        define.setUnitId(rs.getString(S_UNIT_ID));
        define.setReportId(rs.getString("S_REPORTID"));
        define.setsType(rs.getInt("s_type"));
        define.setDimEntityKey(rs.getString("S_DIMENTITYKEY"));
        return define;
    }

    public void delBindData(String formSchemeKey, String period, String unitId, String reportId, int type) {
        try {
            String dimEntityKey = this.getDimEntityKey(formSchemeKey);
            String sql = "DELETE FROM SYS_WORKFLOW_RELATION  WHERE S_FORMSCHMEKEY = ? and S_UNITID = ?  AND  S_REPORTID = ? AND S_PERIOD = ?  AND S_TYPE = ? AND S_DIMENTITYKEY = ?";
            Object[] params = new Object[]{formSchemeKey, unitId, this.converReportId(reportId), period, type, dimEntityKey};
            this.jdbcTemplate.update(sql, params);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
        }
    }

    private String converReportId(String reportId) {
        if (reportId == null) {
            return "-";
        }
        return reportId;
    }

    @Transactional(rollbackFor={NpRollbackException.class})
    public int updateUnBindResultByFormScheme(String formSchemeKey, String entityId) {
        String sql = "UPDATE SYS_WORKFLOW_RELATION set S_DIMENTITYKEY=:entityId WHERE S_FORMSCHMEKEY =:formSchemeKey ";
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("entityId", (Object)entityId);
        source.addValue("formSchemeKey", (Object)formSchemeKey);
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return template.update(sql, (SqlParameterSource)source);
    }

    @Transactional(rollbackFor={NpRollbackException.class})
    public int updateWorkflowSettingDefineById(String settingId, String workflowId) {
        String sql = "UPDATE SYS_WORKFLOW_SETTING set S_WORKFLOWID=:workflowId WHERE S_ID =:settingId";
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("settingId", (Object)settingId);
        source.addValue("workflowId", (Object)workflowId);
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return template.update(sql, (SqlParameterSource)source);
    }

    private String getDimEntityKey(String formSchemeKey) {
        ContextExtension extension = NpContextHolder.getContext().getExtension("WORKFLOW_ST_ENTITYKEY");
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
        return this.getContextMainDimId(taskDefine.getDw());
    }

    public String getContextMainDimId(String dw) {
        DsContext dsContext = DsContextHolder.getDsContext();
        String entityId = dsContext.getContextEntityId();
        return StringUtils.isEmpty((String)entityId) ? dw : entityId;
    }
}

