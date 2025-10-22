/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.logging.LogFactory
 *  com.jiuqi.np.logging.Logger
 *  com.jiuqi.util.OrderGenerator
 *  org.json.JSONArray
 *  org.json.JSONObject
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.PreparedStatementCallback
 *  org.springframework.jdbc.core.RowCallbackHandler
 *  org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback
 *  org.springframework.jdbc.support.lob.DefaultLobHandler
 *  org.springframework.jdbc.support.lob.LobCreator
 *  org.springframework.jdbc.support.lob.LobHandler
 */
package com.jiuqi.nr.bpm.custom.dao.impl;

import com.jiuqi.np.logging.LogFactory;
import com.jiuqi.np.logging.Logger;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowAction;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowDefine;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowDefineSaveEntity;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowGroup;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowLine;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowNodeSet;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowParticipant;
import com.jiuqi.util.OrderGenerator;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.stereotype.Repository;

@Deprecated
@Repository
public class CustomWorkFolwDao {
    private static final Logger logger = LogFactory.getLogger(CustomWorkFolwDao.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public WorkFlowGroup getWorkFlowGroupByID(String groupID) {
        String sql = "SELECT t.g_id,t.g_title,t.g_order,t.g_desc,t.g_updatetime FROM sys_workflow_group t WHERE t.g_id = ?";
        Object[] params = new Object[]{groupID};
        final WorkFlowGroup group = new WorkFlowGroup();
        this.jdbcTemplate.query(sql, new RowCallbackHandler(){

            public void processRow(ResultSet rs) throws SQLException {
                group.setId(rs.getString(1));
                group.setTitle(rs.getString(2));
                group.setOrder(rs.getString(3));
                group.setDesc(rs.getString(4));
                Timestamp time = rs.getTimestamp(5);
                if (time != null) {
                    group.setUpdatetime(new Date(time.getTime()));
                }
            }
        }, params);
        if (groupID.equals(group.getId())) {
            return group;
        }
        return null;
    }

    public WorkFlowGroup getWorkFlowGroupByTitle(String title) {
        String sql = "SELECT t.g_id,t.g_title,t.g_order,t.g_desc,t.g_updatetime FROM sys_workflow_group t WHERE t.g_title = ?";
        Object[] params = new Object[]{title};
        final WorkFlowGroup group = new WorkFlowGroup();
        this.jdbcTemplate.query(sql, new RowCallbackHandler(){

            public void processRow(ResultSet rs) throws SQLException {
                group.setId(rs.getString(1));
                group.setTitle(rs.getString(2));
                group.setOrder(rs.getString(3));
                group.setDesc(rs.getString(4));
                Timestamp time = rs.getTimestamp(5);
                if (time != null) {
                    group.setUpdatetime(new Date(time.getTime()));
                }
            }
        }, params);
        if (title.equals(group.getId())) {
            return group;
        }
        return null;
    }

    public List<WorkFlowGroup> getAllWorkFlowGroup() {
        ArrayList<WorkFlowGroup> groupList = new ArrayList<WorkFlowGroup>();
        String sql = "SELECT * FROM sys_workflow_group t order by t.g_order";
        List queryForList = this.jdbcTemplate.queryForList(sql);
        for (Map map : queryForList) {
            WorkFlowGroup group = new WorkFlowGroup();
            group.setId(map.get("g_id") == null ? "" : map.get("g_id").toString());
            group.setTitle(map.get("g_title") == null ? "" : map.get("g_title").toString());
            group.setOrder(map.get("g_order") == null ? "" : map.get("g_order").toString());
            group.setDesc(map.get("g_desc") == null ? "" : map.get("g_desc").toString());
            Timestamp time = map.get("g_updatetime") == null ? null : (Timestamp)map.get("g_updatetime");
            Date updatetime = null;
            if (time != null) {
                updatetime = new Date(time.getTime());
            }
            group.setUpdatetime(updatetime);
            groupList.add(group);
        }
        return groupList;
    }

    public List<WorkFlowDefine> getWorkFlowDefineByGroupID(String groupID) {
        String sql = "SELECT * FROM sys_workflow_define t WHERE t.f_groupid = ? order by t.f_order";
        Object[] params = new Object[]{groupID};
        final ArrayList<WorkFlowDefine> dlist = new ArrayList<WorkFlowDefine>();
        this.jdbcTemplate.query(sql, new RowCallbackHandler(){

            public void processRow(ResultSet rs) throws SQLException {
                WorkFlowDefine define = CustomWorkFolwDao.this.getDefineByResultSet(rs);
                dlist.add(define);
            }
        }, params);
        return dlist;
    }

    public List<WorkFlowDefine> getAllWorkFlowDefineNoParentid() {
        String sql = "SELECT * FROM sys_workflow_define t WHERE t.f_groupid is null order by t.f_order";
        final ArrayList<WorkFlowDefine> dlist = new ArrayList<WorkFlowDefine>();
        this.jdbcTemplate.query(sql, new RowCallbackHandler(){

            public void processRow(ResultSet rs) throws SQLException {
                WorkFlowDefine define = CustomWorkFolwDao.this.getDefineByResultSet(rs);
                dlist.add(define);
            }
        });
        return dlist;
    }

    public List<WorkFlowDefine> searchDefineByinput(String input) {
        String sql = "SELECT * FROM sys_workflow_define t WHERE t.f_title like ? or t.f_code like ?";
        Object[] params = new Object[]{"%" + input + "%", "%" + input + "%"};
        final ArrayList<WorkFlowDefine> dlist = new ArrayList<WorkFlowDefine>();
        this.jdbcTemplate.query(sql, new RowCallbackHandler(){

            public void processRow(ResultSet rs) throws SQLException {
                WorkFlowDefine define = CustomWorkFolwDao.this.getDefineByResultSet(rs);
                dlist.add(define);
            }
        }, params);
        return dlist;
    }

    public boolean groupHasChildern(String groupID) {
        String sql = "SELECT count(*) countd FROM sys_workflow_define t WHERE t.f_groupid = ?";
        Object[] params = new Object[]{groupID};
        int count = (Integer)this.jdbcTemplate.queryForObject(sql, Integer.class, params);
        return count > 0;
    }

    public WorkFlowDefine getWorkFlowDefineByID(String defineID, int state) {
        String sql = "SELECT * FROM sys_workflow_define t WHERE t.f_id = ? and t.f_state = ?";
        Object[] params = new Object[]{defineID, state};
        final WorkFlowDefine define = new WorkFlowDefine();
        this.jdbcTemplate.query(sql, new RowCallbackHandler(){

            public void processRow(ResultSet rs) throws SQLException {
                define.setId(rs.getString("f_id"));
                define.setTitle(rs.getString("f_title"));
                define.setCode(rs.getString("f_code"));
                define.setOrder(rs.getString("f_order"));
                define.setParentID(rs.getString("f_groupid"));
                define.setDataid(rs.getString("f_dataid"));
                define.setFlowobjid(rs.getString("f_flowobjid"));
                define.setDesc(rs.getString("f_desc"));
                Object object = rs.getObject("f_xml");
                if (object != null) {
                    if (object instanceof byte[]) {
                        byte[] xml = CustomWorkFolwDao.toByteArray(object);
                        if (null != xml) {
                            try {
                                String str = new String(xml, "UTF-8");
                                define.setXml(str);
                            }
                            catch (UnsupportedEncodingException e) {
                                logger.error(e.getMessage(), (Throwable)e);
                            }
                        }
                    } else {
                        String str = rs.getString("f_xml");
                        define.setXml(str);
                    }
                }
                define.setAutostart("1".equals(String.valueOf(rs.getInt("f_autostart"))));
                define.setSubflow("1".equals(String.valueOf(rs.getInt("f_subflow"))));
                Timestamp time = rs.getTimestamp("f_updatetime");
                if (time != null) {
                    define.setUpdatetime(new Date(time.getTime()));
                }
                define.setState(rs.getInt("f_state"));
                define.setLinkid(rs.getString("f_linkid"));
                define.setCustom("1".equals(String.valueOf(rs.getString("isCoutom"))));
            }
        }, params);
        if (defineID.equals(define.getId())) {
            return define;
        }
        return null;
    }

    public WorkFlowDefine getWorkFlowDefineByLinkID(String linkid) {
        String sql = "SELECT * FROM sys_workflow_define t WHERE t.f_linkid = ?";
        Object[] params = new Object[]{linkid};
        final WorkFlowDefine define = new WorkFlowDefine();
        this.jdbcTemplate.query(sql, new RowCallbackHandler(){

            public void processRow(ResultSet rs) throws SQLException {
                define.setId(rs.getString("f_id"));
                define.setTitle(rs.getString("f_title"));
                define.setCode(rs.getString("f_code"));
                define.setOrder(rs.getString("f_order"));
                define.setParentID(rs.getString("f_groupid"));
                define.setDataid(rs.getString("f_dataid"));
                define.setFlowobjid(rs.getString("f_flowobjid"));
                define.setDesc(rs.getString("f_desc"));
                Object object = rs.getObject("f_xml");
                if (object != null) {
                    if (object instanceof byte[]) {
                        byte[] xml = CustomWorkFolwDao.toByteArray(object);
                        if (null != xml) {
                            try {
                                String str = new String(xml, "UTF-8");
                                define.setXml(str);
                            }
                            catch (UnsupportedEncodingException e) {
                                logger.error(e.getMessage(), (Throwable)e);
                            }
                        }
                    } else {
                        String str = rs.getString("f_xml");
                        define.setXml(str);
                    }
                }
                define.setAutostart("1".equals(String.valueOf(rs.getInt("f_autostart"))));
                define.setSubflow("1".equals(String.valueOf(rs.getInt("f_subflow"))));
                Timestamp time = rs.getTimestamp("f_updatetime");
                if (time != null) {
                    define.setUpdatetime(new Date(time.getTime()));
                }
                define.setState(rs.getInt("f_state"));
                define.setLinkid(rs.getString("f_linkid"));
                define.setCustom("1".equals(String.valueOf(rs.getString("isCoutom"))));
            }
        }, params);
        if (linkid.equals(define.getLinkid())) {
            return define;
        }
        return null;
    }

    public WorkFlowNodeSet getWorkFlowNodeSetByID(String nodesetid) {
        String sql = "SELECT * FROM sys_workflow_nodeset t WHERE t.n_id = ?";
        Object[] params = new Object[]{nodesetid};
        final WorkFlowNodeSet nodeset = new WorkFlowNodeSet();
        this.jdbcTemplate.query(sql, new RowCallbackHandler(){

            public void processRow(ResultSet rs) throws SQLException {
                Timestamp time;
                Object object2;
                String str;
                nodeset.setId(rs.getString("n_id"));
                nodeset.setLinkid(rs.getString("n_linkid"));
                nodeset.setCode(rs.getString("n_code"));
                nodeset.setTitle(rs.getString("n_title"));
                nodeset.setDesc(rs.getString("n_desc"));
                Object object = rs.getObject("n_partis");
                if (object != null) {
                    if (object instanceof byte[]) {
                        byte[] partis = CustomWorkFolwDao.toByteArray(object);
                        if (null != partis) {
                            try {
                                str = new String(partis, "UTF-8");
                                if (str != null && !str.isEmpty()) {
                                    String[] parray = str.split(";");
                                    nodeset.setPartis(parray);
                                }
                            }
                            catch (UnsupportedEncodingException e) {
                                logger.error(e.getMessage(), (Throwable)e);
                            }
                        }
                    } else {
                        String str2 = rs.getString("n_partis");
                        if (str2 != null && str2.length() > 0) {
                            String[] parray = str2.split(";");
                            nodeset.setPartis(parray);
                        }
                    }
                }
                if ((object2 = rs.getObject("n_actions")) != null) {
                    if (object2 instanceof byte[]) {
                        byte[] actions = CustomWorkFolwDao.toByteArray(object2);
                        if (null != actions) {
                            try {
                                String str3 = new String(actions, "UTF-8");
                                if (str3 != null && !str3.isEmpty()) {
                                    String[] aarray = str3.split(";");
                                    nodeset.setActions(aarray);
                                }
                            }
                            catch (UnsupportedEncodingException e) {
                                logger.error(e.getMessage(), (Throwable)e);
                            }
                        }
                    } else {
                        str = rs.getString("n_actions");
                        if (str != null && str.length() > 0) {
                            String[] aarray = str.split(";");
                            nodeset.setActions(aarray);
                        }
                    }
                }
                if ((time = rs.getTimestamp("n_updatetime")) != null) {
                    nodeset.setUpdateTime(new Date(time.getTime()));
                }
                nodeset.setFrontarrive(rs.getInt("n_frontarrive"));
                nodeset.setBackarrive(rs.getInt("n_backarrive"));
                boolean writable = "1".equals(rs.getString("n_writable"));
                nodeset.setWritable(writable);
                boolean getback = "1".equals(rs.getString("n_getback"));
                nodeset.setGetback(getback);
                boolean appointable = "1".equals(rs.getString("n_appointable"));
                nodeset.setAppointable(appointable);
                boolean startappoint = "1".equals(rs.getString("n_startappoint"));
                nodeset.setStartappoint(startappoint);
                nodeset.setActionReName_pass(rs.getString("n_actionReName_pass"));
                nodeset.setActionReName_reject(rs.getString("n_actionReName_reject"));
                nodeset.setAppointReName(rs.getString("n_appointReName"));
                nodeset.setAppointResource(rs.getString("n_appointResource"));
                nodeset.setAppointUserRange(rs.getString("n_appointUserRange"));
            }
        }, params);
        if (nodesetid.equals(nodeset.getId())) {
            return nodeset;
        }
        return null;
    }

    public List<WorkFlowNodeSet> getWorkFlowNodeSetsByLinkID(String linkid) {
        final ArrayList<WorkFlowNodeSet> nodesets = new ArrayList<WorkFlowNodeSet>();
        if (null == linkid || linkid.isEmpty()) {
            return nodesets;
        }
        String sql = "SELECT * FROM sys_workflow_nodeset t WHERE t.n_linkid = ?";
        Object[] params = new Object[]{linkid};
        this.jdbcTemplate.query(sql, new RowCallbackHandler(){

            public void processRow(ResultSet rs) throws SQLException {
                WorkFlowNodeSet nodeset = CustomWorkFolwDao.this.getQueryNodeSet(rs);
                nodesets.add(nodeset);
            }
        }, params);
        return nodesets;
    }

    public List<WorkFlowLine> getWorkFlowLinesByLinkID(String linkid) {
        final ArrayList<WorkFlowLine> lines = new ArrayList<WorkFlowLine>();
        if (null == linkid || linkid.isEmpty()) {
            return lines;
        }
        String sql = "SELECT * FROM sys_workflow_line t WHERE t.l_linkid = ?";
        Object[] params = new Object[]{linkid};
        this.jdbcTemplate.query(sql, new RowCallbackHandler(){

            public void processRow(ResultSet rs) throws SQLException {
                WorkFlowLine line = CustomWorkFolwDao.this.getQueryLine(rs);
                lines.add(line);
            }
        }, params);
        return lines;
    }

    public List<WorkFlowAction> getWorkFlowActionsByLinkID(String linkid) {
        final ArrayList<WorkFlowAction> actions = new ArrayList<WorkFlowAction>();
        if (null == linkid || linkid.isEmpty()) {
            return actions;
        }
        String sql = "SELECT * FROM sys_workflow_action t WHERE t.a_linkid = ?";
        Object[] params = new Object[]{linkid};
        this.jdbcTemplate.query(sql, new RowCallbackHandler(){

            public void processRow(ResultSet rs) throws SQLException {
                WorkFlowAction line = CustomWorkFolwDao.this.getQueryAction(rs);
                actions.add(line);
            }
        }, params);
        return actions;
    }

    public List<WorkFlowParticipant> getWorkFlowParticipantsByLinkID(String linkid) {
        final ArrayList<WorkFlowParticipant> participants = new ArrayList<WorkFlowParticipant>();
        if (null == linkid || linkid.isEmpty()) {
            return participants;
        }
        String sql = "SELECT * FROM sys_workflow_parti t WHERE t.p_linkid = ?";
        Object[] params = new Object[]{linkid};
        this.jdbcTemplate.query(sql, new RowCallbackHandler(){

            public void processRow(ResultSet rs) throws SQLException {
                WorkFlowParticipant p = CustomWorkFolwDao.this.getQueryParticipant(rs);
                participants.add(p);
            }
        }, params);
        return participants;
    }

    private WorkFlowAction getQueryAction(ResultSet rs) {
        WorkFlowAction action = new WorkFlowAction();
        try {
            action.setId(rs.getString("a_id"));
            action.setLinkid(rs.getString("a_linkid"));
            action.setNodeid(rs.getString("a_nodeid"));
            action.setActionid(rs.getString("a_actionid"));
            Timestamp time = rs.getTimestamp("a_updatetime");
            if (time != null) {
                action.setUpdateTime(new Date(time.getTime()));
            }
            action.setActionDesc(rs.getString("a_desc"));
            action.setActionTitle(rs.getString("a_title"));
            action.setImagePath(rs.getString("a_image"));
            action.setStateName(rs.getString("a_state_name"));
            action.setStateCode(rs.getString("a_state_code"));
            action.setActionCode(rs.getString("a_code"));
            Object object = rs.getObject("a_exset");
            if (object != null) {
                if (object instanceof byte[]) {
                    byte[] exset = CustomWorkFolwDao.toByteArray(object);
                    if (null != exset) {
                        try {
                            String str = new String(exset, "UTF-8");
                            action.setExset(str);
                        }
                        catch (UnsupportedEncodingException e) {
                            logger.error(e.getMessage(), (Throwable)e);
                        }
                    }
                } else {
                    String str = rs.getString("a_exset");
                    action.setExset(str);
                }
            }
        }
        catch (SQLException e) {
            logger.error(e.getMessage(), (Throwable)e);
        }
        return action;
    }

    private WorkFlowParticipant getQueryParticipant(ResultSet rs) {
        WorkFlowParticipant partici = new WorkFlowParticipant();
        try {
            Object object2;
            Object object;
            partici.setId(rs.getString("p_id"));
            partici.setLinkid(rs.getString("p_linkid"));
            partici.setNodeid(rs.getString("p_nodeid"));
            partici.setStrategyid(rs.getString("p_strategyid"));
            Timestamp time = rs.getTimestamp("p_updatetime");
            if (time != null) {
                partici.setUpdatetime(new Date(time.getTime()));
            }
            if ((object = rs.getObject("p_roleids")) != null) {
                String[] array;
                if (object instanceof byte[]) {
                    byte[] rolesValue = CustomWorkFolwDao.toByteArray(object);
                    if (null != rolesValue) {
                        array = this.byteArrayToStringArray(rolesValue);
                        partici.setRoleIds(array);
                    }
                } else {
                    String str = rs.getString("p_roleids");
                    if (str != null && str.length() > 0) {
                        array = str.split(";");
                        partici.setRoleIds(array);
                    }
                }
            }
            if ((object2 = rs.getObject("p_userids")) != null) {
                if (object2 instanceof byte[]) {
                    byte[] usersValue = CustomWorkFolwDao.toByteArray(object2);
                    if (null != usersValue) {
                        String[] array = this.byteArrayToStringArray(usersValue);
                        partici.setUserIds(array);
                    }
                } else {
                    String str = rs.getString("p_userids");
                    if (str != null && str.length() > 0) {
                        String[] array = str.split(";");
                        partici.setUserIds(array);
                    }
                }
            }
            partici.setParam(rs.getString("p_param"));
            partici.setDescription(rs.getString("p_desc"));
        }
        catch (SQLException e) {
            logger.error(e.getMessage(), (Throwable)e);
        }
        return partici;
    }

    private String[] byteArrayToStringArray(byte[] byteArray) {
        if (null != byteArray) {
            try {
                String str = new String(byteArray, "UTF-8");
                if (str.length() > 0) {
                    String[] array = str.split(";");
                    return array;
                }
            }
            catch (UnsupportedEncodingException e) {
                logger.error(e.getMessage(), (Throwable)e);
            }
        }
        return null;
    }

    private WorkFlowNodeSet getQueryNodeSet(ResultSet rs) {
        WorkFlowNodeSet nodeset = new WorkFlowNodeSet();
        try {
            Timestamp time;
            Object object2;
            String str;
            nodeset.setId(rs.getString("n_id"));
            nodeset.setLinkid(rs.getString("n_linkid"));
            nodeset.setCode(rs.getString("n_code"));
            nodeset.setTitle(rs.getString("n_title"));
            nodeset.setDesc(rs.getString("n_desc"));
            Object object = rs.getObject("n_partis");
            if (object != null) {
                if (object instanceof byte[]) {
                    byte[] partis = CustomWorkFolwDao.toByteArray(object);
                    if (null != partis) {
                        try {
                            str = new String(partis, "UTF-8");
                            if (str != null && !str.isEmpty()) {
                                String[] parray = str.split(";");
                                nodeset.setPartis(parray);
                            }
                        }
                        catch (UnsupportedEncodingException e) {
                            logger.error(e.getMessage(), (Throwable)e);
                        }
                    }
                } else {
                    String str2 = rs.getString("n_partis");
                    if (str2 != null && str2.length() > 0) {
                        String[] parray = str2.split(";");
                        nodeset.setPartis(parray);
                    }
                }
            }
            if ((object2 = rs.getObject("n_actions")) != null) {
                if (object2 instanceof byte[]) {
                    byte[] actions = rs.getBytes("n_actions");
                    if (null != actions) {
                        try {
                            String str3 = new String(actions, "UTF-8");
                            if (str3 != null && !str3.isEmpty()) {
                                String[] aarray = str3.split(";");
                                nodeset.setActions(aarray);
                            }
                        }
                        catch (UnsupportedEncodingException e) {
                            logger.error(e.getMessage(), (Throwable)e);
                        }
                    }
                } else {
                    str = rs.getString("n_actions");
                    if (str != null && str.length() > 0) {
                        String[] aarray = str.split(";");
                        nodeset.setActions(aarray);
                    }
                }
            }
            if ((time = rs.getTimestamp("n_updatetime")) != null) {
                nodeset.setUpdateTime(new Date(time.getTime()));
            }
            nodeset.setFrontarrive(rs.getInt("n_frontarrive"));
            nodeset.setBackarrive(rs.getInt("n_backarrive"));
            boolean writable = "1".equals(rs.getString("n_writable"));
            nodeset.setWritable(writable);
            boolean getback = "1".equals(rs.getString("n_getback"));
            nodeset.setGetback(getback);
            boolean appointable = "1".equals(rs.getString("n_appointable"));
            nodeset.setAppointable(appointable);
            boolean startappoint = "1".equals(rs.getString("n_startappoint"));
            nodeset.setStartappoint(startappoint);
            nodeset.setActionReName_pass(rs.getString("n_actionReName_pass"));
            nodeset.setActionReName_reject(rs.getString("n_actionReName_reject"));
            nodeset.setAppointReName(rs.getString("n_appointReName"));
            nodeset.setAppointResource(rs.getString("n_appointResource"));
            nodeset.setAppointUserRange(rs.getString("n_appointUserRange"));
        }
        catch (SQLException e) {
            logger.error(e.getMessage(), (Throwable)e);
        }
        return nodeset;
    }

    public WorkFlowLine getQueryLine(ResultSet rs) {
        WorkFlowLine line;
        block26: {
            line = new WorkFlowLine();
            try {
                String str;
                Object object3;
                String str2;
                Object object2;
                String str3;
                line.setId(rs.getString("l_id"));
                line.setLinkid(rs.getString("l_linkid"));
                line.setBeforeNodeID(rs.getString("l_bnid"));
                line.setAfterNodeID(rs.getString("l_anid"));
                line.setCode(rs.getString("l_code"));
                line.setTitle(rs.getString("l_title"));
                line.setDesc(rs.getString("l_desc"));
                line.setActionid(rs.getString("l_atcionid"));
                line.setAllmdim("1".equals(rs.getString("l_allmdim")));
                line.setAllreport("1".equals(rs.getString("l_allreport")));
                line.setFormula(rs.getString("l_formula"));
                Timestamp time = rs.getTimestamp("l_updatetime");
                if (time != null) {
                    line.setUpdatetime(new Date(time.getTime()));
                }
                boolean creatdataversion = rs.getInt("l_creatdataversion") == 1;
                line.setCreatDataVersion(creatdataversion);
                Object object = rs.getObject("l_mdim");
                if (object != null) {
                    if (object instanceof byte[]) {
                        byte[] mdim = CustomWorkFolwDao.toByteArray(object);
                        if (null != mdim) {
                            try {
                                str3 = new String(mdim, "UTF-8");
                                line.setMdim(str3);
                            }
                            catch (UnsupportedEncodingException e) {
                                logger.error(e.getMessage(), (Throwable)e);
                            }
                        }
                    } else {
                        String str4 = rs.getString("l_mdim");
                        line.setMdim(str4);
                    }
                }
                if ((object2 = rs.getObject("l_report")) != null) {
                    if (object2 instanceof byte[]) {
                        byte[] report = CustomWorkFolwDao.toByteArray(object2);
                        if (null != report) {
                            try {
                                str2 = new String(report, "UTF-8");
                                line.setReport(str2);
                            }
                            catch (UnsupportedEncodingException e) {
                                logger.error(e.getMessage(), (Throwable)e);
                            }
                        }
                    } else {
                        str3 = rs.getString("l_report");
                        line.setReport(str3);
                    }
                }
                if ((object3 = rs.getObject("l_msgcontent")) != null) {
                    if (object3 instanceof byte[]) {
                        byte[] msgcontent = CustomWorkFolwDao.toByteArray(object3);
                        if (null != msgcontent) {
                            try {
                                str = new String(msgcontent, "UTF-8");
                                line.setMsgcontent(str);
                            }
                            catch (UnsupportedEncodingException e) {
                                logger.error(e.getMessage(), (Throwable)e);
                            }
                        }
                    } else {
                        str2 = rs.getString("l_msgcontent");
                        line.setMsgcontent(str2);
                    }
                }
                line.setSendby_mail("1".equals(rs.getString("l_sendby_mail")));
                line.setSendby_phone("1".equals(rs.getString("l_sendby_phone")));
                line.setSendby_protal("1".equals(rs.getString("l_sendby_protal")));
                Object object4 = rs.getObject("l_msguser");
                if (object4 == null) break block26;
                if (object4 instanceof byte[]) {
                    byte[] msguser = CustomWorkFolwDao.toByteArray(object4);
                    if (null == msguser) break block26;
                    try {
                        String str5 = new String(msguser, "UTF-8");
                        if (str5 != null && !str5.isEmpty()) {
                            Map<String, Object> userMap = this.getMsgUser(str5);
                            line.setMsguser(userMap);
                        }
                        break block26;
                    }
                    catch (UnsupportedEncodingException e) {
                        logger.error(e.getMessage(), (Throwable)e);
                    }
                    break block26;
                }
                str = rs.getString("l_msguser");
                if (str != null && str.length() > 0) {
                    Map<String, Object> userMap = this.getMsgUser(str);
                    line.setMsguser(userMap);
                }
            }
            catch (SQLException e) {
                logger.error(e.getMessage(), (Throwable)e);
            }
        }
        return line;
    }

    public List<WorkFlowNodeSet> getWorkFlowNodeSetsByDefineID(String defineid, int state) {
        String linkid = this.getLinkidByDefineID(defineid, state);
        return this.getWorkFlowNodeSetsByLinkID(linkid);
    }

    public WorkFlowLine getWorkFlowLineByID(String lineid) {
        String sql = "SELECT * FROM sys_workflow_line t WHERE t.l_id = ?";
        Object[] params = new Object[]{lineid};
        final WorkFlowLine line = new WorkFlowLine();
        this.jdbcTemplate.query(sql, new RowCallbackHandler(){

            public void processRow(ResultSet rs) throws SQLException {
                String str;
                Object object3;
                String str2;
                Object object2;
                String str3;
                line.setId(rs.getString("l_id"));
                line.setLinkid(rs.getString("l_linkid"));
                line.setBeforeNodeID(rs.getString("l_bnid"));
                line.setAfterNodeID(rs.getString("l_anid"));
                line.setCode(rs.getString("l_code"));
                line.setTitle(rs.getString("l_title"));
                line.setDesc(rs.getString("l_desc"));
                line.setActionid(rs.getString("l_atcionid"));
                line.setAllmdim("1".equals(rs.getString("l_allmdim")));
                line.setAllreport("1".equals(rs.getString("l_allreport")));
                line.setFormula(rs.getString("l_formula"));
                Timestamp time = rs.getTimestamp("l_updatetime");
                if (time != null) {
                    line.setUpdatetime(new Date(time.getTime()));
                }
                boolean creatdataversion = rs.getInt("l_creatdataversion") == 1;
                line.setCreatDataVersion(creatdataversion);
                Object object = rs.getObject("l_mdim");
                if (object != null) {
                    if (object instanceof byte[]) {
                        byte[] mdim = CustomWorkFolwDao.toByteArray(object);
                        if (null != mdim) {
                            try {
                                str3 = new String(mdim, "UTF-8");
                                line.setMdim(str3);
                            }
                            catch (UnsupportedEncodingException e) {
                                logger.error(e.getMessage(), (Throwable)e);
                            }
                        }
                    } else {
                        String str4 = rs.getString("l_mdim");
                        line.setMdim(str4);
                    }
                }
                if ((object2 = rs.getObject("l_report")) != null) {
                    if (object2 instanceof byte[]) {
                        byte[] report = CustomWorkFolwDao.toByteArray(object2);
                        if (null != report) {
                            try {
                                str2 = new String(report, "UTF-8");
                                line.setReport(str2);
                            }
                            catch (UnsupportedEncodingException e) {
                                logger.error(e.getMessage(), (Throwable)e);
                            }
                        }
                    } else {
                        str3 = rs.getString("l_report");
                        line.setReport(str3);
                    }
                }
                if ((object3 = rs.getObject("l_msgcontent")) != null) {
                    if (object3 instanceof byte[]) {
                        byte[] msgcontent = CustomWorkFolwDao.toByteArray(object3);
                        if (null != msgcontent) {
                            try {
                                str = new String(msgcontent, "UTF-8");
                                line.setMsgcontent(str);
                            }
                            catch (UnsupportedEncodingException e) {
                                logger.error(e.getMessage(), (Throwable)e);
                            }
                        }
                    } else {
                        str2 = rs.getString("l_msgcontent");
                        line.setMsgcontent(str2);
                    }
                }
                line.setSendby_mail("1".equals(rs.getString("l_sendby_mail")));
                line.setSendby_phone("1".equals(rs.getString("l_sendby_phone")));
                line.setSendby_protal("1".equals(rs.getString("l_sendby_protal")));
                Object object4 = rs.getObject("l_msguser");
                if (object4 != null) {
                    if (object4 instanceof byte[]) {
                        byte[] msguser = CustomWorkFolwDao.toByteArray(object4);
                        if (null != msguser) {
                            try {
                                String str5 = new String(msguser, "UTF-8");
                                if (str5 != null && !str5.isEmpty()) {
                                    Map userMap = CustomWorkFolwDao.this.getMsgUser(str5);
                                    line.setMsguser(userMap);
                                }
                            }
                            catch (UnsupportedEncodingException e) {
                                logger.error(e.getMessage(), (Throwable)e);
                            }
                        }
                    } else {
                        str = rs.getString("l_msguser");
                        if (str != null && !str.isEmpty()) {
                            Map userMap = CustomWorkFolwDao.this.getMsgUser(str);
                            line.setMsguser(userMap);
                        }
                    }
                }
            }
        }, params);
        if (lineid.equals(line.getId())) {
            return line;
        }
        return null;
    }

    public String getWorkFlowDefineXmlByID(String defineID, int state) {
        String sql = "SELECT t.f_xml FROM sys_workflow_define t WHERE t.f_id = ? and t.f_state = ?";
        Object[] params = new Object[]{defineID, state};
        final StringBuffer xmlStr = new StringBuffer();
        this.jdbcTemplate.query(sql, new RowCallbackHandler(){

            public void processRow(ResultSet rs) throws SQLException {
                Object object = rs.getObject("f_xml");
                if (object != null) {
                    if (object instanceof byte[]) {
                        byte[] xml = CustomWorkFolwDao.toByteArray(object);
                        if (null != xml) {
                            try {
                                String res = new String(xml, "UTF-8");
                                xmlStr.append(res);
                            }
                            catch (UnsupportedEncodingException e) {
                                logger.error(e.getMessage(), (Throwable)e);
                            }
                        }
                    } else {
                        String res = rs.getString("f_xml");
                        xmlStr.append(res);
                    }
                }
            }
        }, params);
        return xmlStr.toString();
    }

    public Object creatWorkFlowGroup(final WorkFlowGroup group) {
        try {
            String sql = "INSERT INTO sys_workflow_group(g_id, g_title, g_order, g_desc, g_updatetime) VALUES (?,?,?,?,?)";
            final StringBuffer sb = new StringBuffer();
            int execute = (Integer)this.jdbcTemplate.execute(sql, (PreparedStatementCallback)new AbstractLobCreatingPreparedStatementCallback((LobHandler)new DefaultLobHandler()){

                protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException, DataAccessException {
                    String groupID = group.getId();
                    if (null == groupID || groupID.isEmpty()) {
                        groupID = UUID.randomUUID().toString();
                    }
                    sb.append(groupID);
                    int i = 1;
                    ps.setObject(i++, groupID);
                    ps.setString(i++, group.getTitle());
                    String order = group.getOrder();
                    if (null == order || order.isEmpty()) {
                        order = OrderGenerator.newOrder();
                    }
                    ps.setString(i++, order);
                    ps.setString(i++, group.getDesc());
                    ps.setTimestamp(i++, new Timestamp(System.currentTimeMillis()));
                }
            });
            if (execute > 0) {
                return this.getWorkFlowGroupByID(sb.toString());
            }
            return "\u6570\u636e\u5e93\u6267\u884c\u63d2\u5165\u8bed\u53e5\u5931\u8d25\uff01";
        }
        catch (Exception e) {
            return e.getMessage();
        }
    }

    public Object creatWorkFlowDefine(final WorkFlowDefine define) {
        try {
            final StringBuffer sb = new StringBuffer();
            String sql = "INSERT INTO sys_workflow_define (f_id, f_title, f_code, f_order, f_groupid, f_updatetime, f_desc, f_dataid, f_autostart, f_subflow, f_flowobjid, f_xml, f_state, f_linkid,isCoutom)  VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            int execute = (Integer)this.jdbcTemplate.execute(sql, (PreparedStatementCallback)new AbstractLobCreatingPreparedStatementCallback((LobHandler)new DefaultLobHandler()){

                protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException, DataAccessException {
                    String ID = define.getId();
                    if (null == ID || ID.isEmpty()) {
                        ID = UUID.randomUUID().toString();
                    }
                    sb.append(ID);
                    ps.setObject(1, ID);
                    ps.setString(2, define.getTitle());
                    ps.setString(3, define.getCode());
                    String order = define.getOrder();
                    if (null == order || order.isEmpty()) {
                        order = OrderGenerator.newOrder();
                    }
                    ps.setString(4, order);
                    ps.setString(5, define.getParentID());
                    ps.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
                    ps.setString(7, define.getDesc());
                    ps.setString(8, define.getDataid());
                    ps.setInt(9, define.isAutostart() ? 1 : 0);
                    ps.setInt(10, define.isSubflow() ? 1 : 0);
                    ps.setString(11, define.getFlowobjid());
                    ps.setString(12, define.getXml());
                    ps.setInt(13, define.getState());
                    String linkid = define.getLinkid();
                    if (null == linkid || linkid.isEmpty()) {
                        linkid = UUID.randomUUID().toString();
                    }
                    ps.setString(14, linkid);
                    ps.setInt(15, define.isCustom() ? 1 : 0);
                }
            });
            if (execute > 0) {
                return this.getWorkFlowDefineByID(sb.toString(), define.getState());
            }
            return "\u6570\u636e\u5e93\u6267\u884c\u63d2\u5165\u8bed\u53e5\u5931\u8d25\uff01";
        }
        catch (Exception e) {
            return e.getMessage();
        }
    }

    public String creatWorkFlowNodeSet(final WorkFlowNodeSet nodeset) {
        try {
            String sql = "INSERT INTO sys_workflow_nodeset (n_id, n_linkid, n_code, n_title, n_desc, n_partis, n_updatetime, n_actions, n_frontarrive, n_backarrive, n_writable,n_getback, n_appointable, n_startappoint, n_appointResource, n_appointReName, n_appointUserRange, n_actionReName_pass, n_actionReName_reject)  VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            int execute = (Integer)this.jdbcTemplate.execute(sql, (PreparedStatementCallback)new AbstractLobCreatingPreparedStatementCallback((LobHandler)new DefaultLobHandler()){

                protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException, DataAccessException {
                    String ID = nodeset.getId();
                    if (null == ID || ID.isEmpty()) {
                        ID = UUID.randomUUID().toString();
                    }
                    ps.setString(1, ID);
                    ps.setString(2, nodeset.getLinkid());
                    ps.setString(3, nodeset.getCode());
                    ps.setString(4, nodeset.getTitle());
                    ps.setString(5, nodeset.getDesc());
                    String[] partisArray = nodeset.getPartis();
                    String partisIdStr = "";
                    for (String id : partisArray) {
                        partisIdStr = partisIdStr + id + ";";
                    }
                    partisIdStr = partisIdStr.substring(0, partisIdStr.length() - 1);
                    ps.setString(6, "");
                    ps.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
                    String[] array = nodeset.getActions();
                    String idStr = "";
                    for (String id : array) {
                        idStr = idStr + id + ";";
                    }
                    idStr = idStr.substring(0, idStr.length() - 1);
                    ps.setString(8, idStr);
                    ps.setInt(9, nodeset.getFrontarrive());
                    ps.setInt(10, nodeset.getBackarrive());
                    ps.setInt(11, nodeset.isWritable() ? 1 : 0);
                    ps.setInt(12, nodeset.isGetback() ? 1 : 0);
                    ps.setInt(13, nodeset.isAppointable() ? 1 : 0);
                    ps.setInt(14, nodeset.isStartappoint() ? 1 : 0);
                    ps.setString(15, nodeset.getAppointResource());
                    ps.setString(16, nodeset.getAppointReName());
                    ps.setString(17, nodeset.getAppointUserRange());
                    ps.setString(18, nodeset.getActionReName_pass());
                    ps.setString(19, nodeset.getActionReName_reject());
                }
            });
            if (execute > 0) {
                return "";
            }
            return "\u65b0\u5efa\u6d41\u7a0b\u8282\u70b9,\u6570\u636e\u5e93\u6267\u884c\u63d2\u5165\u8bed\u53e5\u5931\u8d25\uff01";
        }
        catch (Exception e) {
            return e.getMessage();
        }
    }

    public String updateWorkFlowNodeSet(final WorkFlowNodeSet nodeset) {
        try {
            String sql = "UPDATE sys_workflow_nodeset set n_linkid = ?, n_code = ?, n_title = ?, n_desc = ?, n_partis = ?, n_updatetime = ?, n_actions = ?, n_frontarrive = ?, n_backarrive = ?,n_writable = ?, n_getback = ?, n_appointable = ?, n_startappoint = ?, n_appointResource = ?, n_appointReName = ?, n_appointUserRange = ?, n_actionReName_pass = ?, n_actionReName_reject = ?  WHERE n_id = ? ";
            int execute = (Integer)this.jdbcTemplate.execute(sql, (PreparedStatementCallback)new AbstractLobCreatingPreparedStatementCallback((LobHandler)new DefaultLobHandler()){

                protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException, DataAccessException {
                    ps.setString(1, nodeset.getLinkid());
                    ps.setString(2, nodeset.getCode());
                    ps.setString(3, nodeset.getTitle());
                    ps.setString(4, nodeset.getDesc());
                    String[] array = nodeset.getPartis();
                    String idStr = "";
                    for (String id : array) {
                        idStr = idStr + id + ";";
                    }
                    idStr = idStr.substring(0, idStr.length() - 1);
                    ps.setString(5, "");
                    ps.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
                    String[] actionArray = nodeset.getActions();
                    String actionIdStr = "";
                    for (String id : actionArray) {
                        actionIdStr = actionIdStr + id + ";";
                    }
                    actionIdStr = actionIdStr.substring(0, actionIdStr.length() - 1);
                    ps.setString(7, actionIdStr);
                    ps.setInt(8, nodeset.getFrontarrive());
                    ps.setInt(9, nodeset.getBackarrive());
                    ps.setInt(10, nodeset.isWritable() ? 1 : 0);
                    ps.setInt(11, nodeset.isGetback() ? 1 : 0);
                    ps.setInt(12, nodeset.isAppointable() ? 1 : 0);
                    ps.setInt(13, nodeset.isStartappoint() ? 1 : 0);
                    ps.setString(14, nodeset.getAppointResource());
                    ps.setString(15, nodeset.getAppointReName());
                    ps.setString(16, nodeset.getAppointUserRange());
                    ps.setString(17, nodeset.getActionReName_pass());
                    ps.setString(18, nodeset.getActionReName_reject());
                    ps.setString(19, nodeset.getId());
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

    public Object updateWorkFlowDefine(final WorkFlowDefine define) {
        try {
            String sql = "UPDATE sys_workflow_define T SET  f_title = ? ,f_code = ?, f_order = ?, f_groupid = ? ,f_updatetime = ?, f_desc = ?, f_dataid = ?, f_autostart = ?,f_subflow = ?, f_flowobjid = ?,f_xml = ? ,isCoutom = ?,f_state = ?  WHERE T.f_id = ?";
            int execute = (Integer)this.jdbcTemplate.execute(sql, (PreparedStatementCallback)new AbstractLobCreatingPreparedStatementCallback((LobHandler)new DefaultLobHandler()){

                protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException, DataAccessException {
                    int i = 1;
                    ps.setString(i++, define.getTitle());
                    ps.setString(i++, define.getCode());
                    String order = define.getOrder();
                    if (null == order || order.isEmpty()) {
                        order = OrderGenerator.newOrder();
                    }
                    ps.setString(i++, order);
                    ps.setString(i++, define.getParentID());
                    ps.setTimestamp(i++, new Timestamp(System.currentTimeMillis()));
                    ps.setString(i++, define.getDesc());
                    ps.setString(i++, define.getDataid());
                    ps.setInt(i++, define.isAutostart() ? 1 : 0);
                    ps.setInt(i++, define.isSubflow() ? 1 : 0);
                    ps.setString(i++, define.getFlowobjid());
                    ps.setString(i++, define.getXml());
                    ps.setInt(i++, define.isCustom() ? 1 : 0);
                    ps.setInt(i++, define.getState());
                    ps.setString(i++, define.getId());
                }
            });
            if (execute > 0) {
                return this.getWorkFlowDefineByID(define.getId(), define.getState());
            }
            return "\u4fee\u6539\u6d41\u7a0b\u5b9a\u4e49,\u6570\u636e\u5e93\u6267\u884c\u66f4\u65b0\u8bed\u53e5\u5931\u8d25\uff01";
        }
        catch (Exception e) {
            return e.getMessage();
        }
    }

    public String delWorkFlowGroupByID(String groupid) {
        try {
            String sql = "DELETE FROM sys_workflow_group  WHERE g_id = ?";
            Object[] params = new Object[]{groupid};
            int count = this.jdbcTemplate.update(sql, params);
            if (count > 0) {
                return "";
            }
            return "\u6570\u636e\u5e93\u6267\u884c\u5220\u9664\u8bed\u53e5\u5931\u8d25\uff01";
        }
        catch (Exception e) {
            return e.getMessage();
        }
    }

    public String delWorkFlowNodeSetByID(String nodeid) {
        try {
            String sql = "DELETE FROM sys_workflow_nodeset  WHERE n_id = ?";
            Object[] params = new Object[]{nodeid};
            int count = this.jdbcTemplate.update(sql, params);
            logger.info("\u5220\u9664\u6307\u5b9a\u7684\u6d41\u7a0b\u8282\u70b9" + count);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
            logger.error(e.getMessage(), (Throwable)e);
        }
        this.delWorkFlowActionByNodeid(nodeid);
        this.delWorkFlowParticpantByNodeid(nodeid);
        return "";
    }

    public String delWorkFlowNodeSetByIDList(List<String> nodeids) {
        try {
            String sql = "DELETE FROM sys_workflow_nodeset  WHERE n_id =? ";
            List<Object[]> batchArgs = this.getBatchArgs(nodeids);
            this.jdbcTemplate.batchUpdate(sql.toString(), batchArgs);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
            logger.error(e.getMessage(), (Throwable)e);
        }
        this.delWorkFlowActionByNodeidList(nodeids);
        this.delWorkFlowParticpantByNodeidList(nodeids);
        return "";
    }

    public String delWorkFlowLineByIDList(List<String> lines) {
        try {
            String sql = "DELETE FROM sys_workflow_line  WHERE l_id =? ";
            List<Object[]> batchArgs = this.getBatchArgs(lines);
            this.jdbcTemplate.batchUpdate(sql.toString(), batchArgs);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
            logger.error(e.getMessage(), (Throwable)e);
        }
        return "";
    }

    public String delWorkFlowParticisidsByIDList(List<String> particiids) {
        try {
            String sql = "DELETE FROM sys_workflow_parti  WHERE p_id =? ";
            List<Object[]> batchArgs = this.getBatchArgs(particiids);
            this.jdbcTemplate.batchUpdate(sql.toString(), batchArgs);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
            logger.error(e.getMessage(), (Throwable)e);
        }
        return "";
    }

    public String delWorkFlowActionsByIDList(List<String> actionids) {
        try {
            String sql = "DELETE FROM sys_workflow_action  WHERE a_id =? ";
            List<Object[]> batchArgs = this.getBatchArgs(actionids);
            this.jdbcTemplate.batchUpdate(sql.toString(), batchArgs);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
            logger.error(e.getMessage(), (Throwable)e);
        }
        return "";
    }

    private List<Object[]> getBatchArgs(List<String> formKeys) {
        ArrayList<Object[]> batchArgs = new ArrayList<Object[]>();
        for (String formKey : formKeys) {
            String[] params = new String[]{formKey};
            batchArgs.add(params);
        }
        return batchArgs;
    }

    public void delWorkFlowActionByNodeid(String nodeid) {
        try {
            String sql = "DELETE FROM sys_workflow_action  WHERE a_nodeid = ?";
            Object[] params = new Object[]{nodeid};
            int count = this.jdbcTemplate.update(sql, params);
            logger.info("\u5220\u9664\u8282\u70b9\u7684\u6309\u94ae" + count);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
            logger.error(e.getMessage(), (Throwable)e);
        }
    }

    public void delWorkFlowActionByNodeidList(List<String> nodeids) {
        try {
            String sql = "DELETE FROM sys_workflow_action  WHERE a_nodeid = ?";
            List<Object[]> batchArgs = this.getBatchArgs(nodeids);
            this.jdbcTemplate.batchUpdate(sql.toString(), batchArgs);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
            logger.error(e.getMessage(), (Throwable)e);
        }
    }

    public void delWorkFlowParticpantByNodeid(String nodeid) {
        try {
            String sql = "DELETE FROM sys_workflow_parti  WHERE p_nodeid = ?";
            Object[] params = new Object[]{nodeid};
            int count = this.jdbcTemplate.update(sql, params);
            logger.info("\u5220\u9664\u8282\u70b9\u7684\u53c2\u4e0e\u8005" + count);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
            logger.error(e.getMessage(), (Throwable)e);
        }
    }

    public void delWorkFlowParticpantByNodeidList(List<String> nodeid) {
        try {
            String sql = "DELETE FROM sys_workflow_parti  WHERE p_nodeid = ?";
            List<Object[]> batchArgs = this.getBatchArgs(nodeid);
            this.jdbcTemplate.batchUpdate(sql.toString(), batchArgs);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
            logger.error(e.getMessage(), (Throwable)e);
        }
    }

    public String delWorkFlowDefineByID(String defineid, int state) {
        String linkid = this.getLinkidByDefineID(defineid, state);
        try {
            String sql = "DELETE FROM sys_workflow_define  WHERE f_id = ? and f_state = ?";
            Object[] params = new Object[]{defineid, state};
            int count = this.jdbcTemplate.update(sql, params);
            if (count < 1) {
                return "\u6570\u636e\u5e93\u6267\u884c\u5220\u9664\u8bed\u53e5\u5931\u8d25\uff01";
            }
        }
        catch (Exception e) {
            return e.getMessage();
        }
        this.delWorkFlowNodeSetByLinkid(linkid);
        this.delWorkFlowLinkByLinkid(linkid);
        this.delWorkFlowActionByLinkid(linkid);
        this.delWorkFlowParticpantByLinkid(linkid);
        return "";
    }

    public void delWorkFlowNodeSetByLinkid(String linkid) {
        try {
            String sql = "DELETE FROM sys_workflow_nodeset  WHERE n_linkid = ?";
            Object[] params = new Object[]{linkid};
            int count = this.jdbcTemplate.update(sql, params);
            logger.info("\u5220\u9664\u5173\u8054\u7684\u6d41\u7a0b\u8282\u70b9" + count);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
            logger.error(e.getMessage(), (Throwable)e);
        }
    }

    public void delWorkFlowLinkByLinkid(String linkid) {
        try {
            String sql = "DELETE FROM sys_workflow_line  WHERE l_linkid = ?";
            Object[] params = new Object[]{linkid};
            int count = this.jdbcTemplate.update(sql, params);
            logger.info("\u5220\u9664\u5173\u8054\u7684\u8f6c\u79fb\u7ebf" + count);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
            logger.error(e.getMessage(), (Throwable)e);
        }
    }

    public void delWorkFlowActionByLinkid(String linkid) {
        try {
            String sql = "DELETE FROM sys_workflow_action  WHERE a_linkid = ?";
            Object[] params = new Object[]{linkid};
            int count = this.jdbcTemplate.update(sql, params);
            logger.info("\u5220\u9664\u5173\u8054\u7684\u6309\u94ae" + count);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
            logger.error(e.getMessage(), (Throwable)e);
        }
    }

    public void delWorkFlowParticpantByLinkid(String linkid) {
        try {
            String sql = "DELETE FROM sys_workflow_parti  WHERE p_linkid = ?";
            Object[] params = new Object[]{linkid};
            int count = this.jdbcTemplate.update(sql, params);
            logger.info("\u5220\u9664\u5173\u8054\u7684\u53c2\u4e0e\u8005" + count);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
            logger.error(e.getMessage(), (Throwable)e);
        }
    }

    public String creatWorkFlowNParticipant(final WorkFlowParticipant participant) {
        try {
            String sql = "INSERT INTO sys_workflow_parti(p_id, p_linkid, p_nodeid, p_strategyid, p_updatetime, p_roleids,p_userids,p_param,p_desc) VALUES (?,?,?,?,?,?,?,?,?)";
            int execute = (Integer)this.jdbcTemplate.execute(sql, (PreparedStatementCallback)new AbstractLobCreatingPreparedStatementCallback((LobHandler)new DefaultLobHandler()){

                protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException, DataAccessException {
                    String ID = participant.getId();
                    if (null == ID || ID.isEmpty()) {
                        ID = UUID.randomUUID().toString();
                    }
                    int i = 1;
                    ps.setObject(i++, ID);
                    ps.setString(i++, participant.getLinkid());
                    ps.setString(i++, participant.getNodeid());
                    ps.setString(i++, participant.getStrategyid());
                    ps.setTimestamp(i++, new Timestamp(System.currentTimeMillis()));
                    String rolesValue = CustomWorkFolwDao.this.arrayToString(participant.getRoleIds());
                    String usersValue = CustomWorkFolwDao.this.arrayToString(participant.getUserIds());
                    ps.setString(i++, rolesValue);
                    ps.setString(i++, usersValue);
                    ps.setString(i++, participant.getParam());
                    ps.setString(i++, participant.getDescription());
                }
            });
            if (execute < 1) {
                return "\u65b0\u5efa\u6d41\u7a0b\u8282\u70b9\u53c2\u4e0e\u8005,\u6570\u636e\u5e93\u6267\u884c\u63d2\u5165\u8bed\u53e5\u5931\u8d25\uff01";
            }
        }
        catch (Exception e) {
            return e.getMessage();
        }
        return "";
    }

    private String arrayToString(String[] array) {
        String value = "";
        for (String a : array) {
            value = value + a + ";";
        }
        if (value.length() > 0) {
            value = value.substring(0, value.length() - 1);
        }
        return value;
    }

    public String creatWorkFlowNAction(final WorkFlowAction action) {
        try {
            String sql = "INSERT INTO sys_workflow_action(a_id, a_linkid, a_nodeid, a_actionid, a_updatetime, a_exset,a_state_name, a_state_code, a_desc, a_title, a_image, a_code) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
            int execute = (Integer)this.jdbcTemplate.execute(sql, (PreparedStatementCallback)new AbstractLobCreatingPreparedStatementCallback((LobHandler)new DefaultLobHandler()){

                protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException, DataAccessException {
                    String ID = action.getId();
                    if (null == ID || ID.isEmpty()) {
                        ID = UUID.randomUUID().toString();
                    }
                    int i = 1;
                    ps.setObject(i++, ID);
                    ps.setString(i++, action.getLinkid());
                    ps.setString(i++, action.getNodeid());
                    ps.setString(i++, action.getActionid());
                    ps.setTimestamp(i++, new Timestamp(System.currentTimeMillis()));
                    ps.setString(i++, action.getExset());
                    ps.setString(i++, action.getStateName());
                    ps.setString(i++, action.getStateCode());
                    ps.setString(i++, action.getActionDesc());
                    ps.setString(i++, action.getActionTitle());
                    ps.setString(i++, action.getImagePath());
                    ps.setString(i++, action.getActionCode());
                }
            });
            if (execute < 1) {
                return "\u65b0\u5efa\u6d41\u7a0b\u8282\u70b9\u6309\u94ae,\u6570\u636e\u5e93\u6267\u884c\u63d2\u5165\u8bed\u53e5\u5931\u8d25\uff01";
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
            return e.getMessage();
        }
        return "";
    }

    public String updateWorkFlowNAction(final WorkFlowAction action) {
        try {
            String sql = "update sys_workflow_action set  a_linkid = ?, a_nodeid = ?, a_actionid = ?, a_updatetime = ?, a_exset = ?, a_state_name = ?, a_state_code = ?, a_desc = ?, a_title = ?, a_image = ? ,a_code = ?  where a_id = ?";
            int execute = (Integer)this.jdbcTemplate.execute(sql, (PreparedStatementCallback)new AbstractLobCreatingPreparedStatementCallback((LobHandler)new DefaultLobHandler()){

                protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException, DataAccessException {
                    int i = 1;
                    ps.setString(i++, action.getLinkid());
                    ps.setString(i++, action.getNodeid());
                    ps.setString(i++, action.getActionid());
                    ps.setTimestamp(i++, new Timestamp(System.currentTimeMillis()));
                    ps.setString(i++, action.getExset());
                    ps.setString(i++, action.getStateName());
                    ps.setString(i++, action.getStateCode());
                    ps.setString(i++, action.getActionDesc());
                    ps.setString(i++, action.getActionTitle());
                    ps.setString(i++, action.getImagePath());
                    ps.setString(i++, action.getActionCode());
                    ps.setObject(i++, action.getId());
                }
            });
            if (execute < 1) {
                return "\u66f4\u65b0\u6d41\u7a0b\u8282\u70b9\u6309\u94ae,\u6570\u636e\u5e93\u6267\u884c\u66f4\u65b0\u8bed\u53e5\u5931\u8d25\uff01";
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
            return e.getMessage();
        }
        return "";
    }

    public String updateWorkFlowNParticipant(final WorkFlowParticipant participant) {
        try {
            String sql = "update sys_workflow_parti set p_linkid = ?, p_nodeid = ?, p_strategyid = ?, p_updatetime = ?, p_roleids = ?, p_userids = ?, p_param = ?, p_desc = ?where p_id = ?";
            int execute = (Integer)this.jdbcTemplate.execute(sql, (PreparedStatementCallback)new AbstractLobCreatingPreparedStatementCallback((LobHandler)new DefaultLobHandler()){

                protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException, DataAccessException {
                    int i = 1;
                    ps.setString(i++, participant.getLinkid());
                    ps.setString(i++, participant.getNodeid());
                    ps.setString(i++, participant.getStrategyid());
                    ps.setTimestamp(i++, new Timestamp(System.currentTimeMillis()));
                    String[] roleIds = participant.getRoleIds();
                    String[] userIds = participant.getUserIds();
                    String rolesValue = CustomWorkFolwDao.this.arrayToString(roleIds);
                    String usersValue = CustomWorkFolwDao.this.arrayToString(userIds);
                    ps.setString(i++, rolesValue);
                    ps.setString(i++, usersValue);
                    ps.setString(i++, participant.getParam());
                    ps.setString(i++, participant.getDescription());
                    ps.setObject(i++, participant.getId());
                }
            });
            if (execute < 1) {
                return "\u4fee\u6539\u6d41\u7a0b\u8282\u70b9\u53c2\u4e0e\u8005,\u6570\u636e\u5e93\u6267\u884c\u66f4\u65b0\u8bed\u53e5\u5931\u8d25\uff01";
            }
        }
        catch (Exception e) {
            return e.getMessage();
        }
        return "";
    }

    public WorkFlowDefine cloneMaintenanceWorkFlowDefine(WorkFlowDefine define) {
        WorkFlowDefine copyDefine = this.copyWorkflowdefine(define.getId(), define.getState());
        if (null == copyDefine) {
            return null;
        }
        String linkid = copyDefine.getLinkid();
        String oldLinkID = define.getLinkid();
        Map<String, String> nodeSetIDMap = this.copyWorkflowNodeSet(oldLinkID, linkid);
        this.copyWorkflowNodeAction(linkid, nodeSetIDMap);
        this.copyWorkflowNodeLink(oldLinkID, linkid, nodeSetIDMap);
        this.copyWorkflowNodeParticpant(linkid, nodeSetIDMap);
        return copyDefine;
    }

    private WorkFlowDefine copyWorkflowdefine(final String defineid, final int state) {
        final String linkid = UUID.randomUUID().toString();
        try {
            String sql = "INSERT INTO sys_workflow_define(f_id, f_title, f_code, f_order, f_groupid,f_updatetime, f_desc, f_dataid, f_autostart,f_subflow, f_flowobjid, f_xml, f_state, f_linkid,isCoutom) select f_id, f_title, f_code, f_order, f_groupid,?, f_desc, f_dataid, f_autostart,f_subflow, f_flowobjid, f_xml,isCoutom, ?, ?  from sys_workflow_define  where f_id = ? and f_state = ?";
            int execute = (Integer)this.jdbcTemplate.execute(sql, (PreparedStatementCallback)new AbstractLobCreatingPreparedStatementCallback((LobHandler)new DefaultLobHandler()){

                protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException, DataAccessException {
                    ps.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
                    ps.setInt(2, 0);
                    ps.setString(3, linkid);
                    ps.setString(4, defineid);
                    ps.setInt(5, state);
                }
            });
            if (execute < 1) {
                return this.getWorkFlowDefineByID(defineid, 0);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
            return null;
        }
        return this.getWorkFlowDefineByID(defineid, 0);
    }

    private List<String> getAllNodeSetIDs(String linkID) {
        final ArrayList<String> ids = new ArrayList<String>();
        String sql = "SELECT t.n_id FROM sys_workflow_nodeset t WHERE t.n_linkid = ?";
        Object[] params = new Object[]{linkID};
        this.jdbcTemplate.query(sql, new RowCallbackHandler(){

            public void processRow(ResultSet rs) throws SQLException {
                String id = rs.getString("n_id");
                ids.add(id);
            }
        }, params);
        return ids;
    }

    private Map<String, String> copyWorkflowNodeSet(String oldLinkID, String linkid) {
        List<String> nodeSetID = this.getAllNodeSetIDs(oldLinkID);
        if (null == nodeSetID || nodeSetID.isEmpty()) {
            return null;
        }
        HashMap<String, String> idmap = new HashMap<String, String>();
        for (String oldid : nodeSetID) {
            String newID = this.copyOneWorkflowNodeSet(oldid, linkid);
            idmap.put(oldid, newID);
        }
        return idmap;
    }

    private String copyOneWorkflowNodeSet(final String nodeid, final String linkid) {
        final String ID = UUID.randomUUID().toString();
        try {
            String sql = "INSERT INTO sys_workflow_nodeset  (n_id, n_linkid, n_code, n_title, n_desc, n_partis, n_updatetime, n_actions, n_frontarrive,  n_backarrive, n_writable, n_getback, n_appointable, n_startappoint, n_appointResource, n_appointReName, n_appointUserRange, n_actionReName_pass, n_actionReName_reject)  select ?, ?, n_code, n_title, n_desc, n_partis, ?, n_actions, n_frontarrive, n_backarrive, n_writable, n_getback, n_appointable, n_startappoint, n_appointResource, n_appointReName, n_appointUserRange, n_actionReName_pass, n_actionReName_reject  from sys_workflow_nodeset  where n_id = ?";
            int execute = (Integer)this.jdbcTemplate.execute(sql, (PreparedStatementCallback)new AbstractLobCreatingPreparedStatementCallback((LobHandler)new DefaultLobHandler()){

                protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException, DataAccessException {
                    ps.setString(1, ID);
                    ps.setString(2, linkid);
                    ps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
                    ps.setString(4, nodeid);
                }
            });
            if (execute < 1) {
                return ID;
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
            return null;
        }
        return ID;
    }

    private boolean copyWorkflowNodeLink(String oldLinkID, String linkid, Map<String, String> nodeIDMap) {
        List<WorkFlowLine> lines = this.getWorkFlowLinesByLinkID(oldLinkID);
        for (WorkFlowLine line : lines) {
            line.setBeforeNodeID(nodeIDMap.get(line.getBeforeNodeID()));
            line.setAfterNodeID(nodeIDMap.get(line.getAfterNodeID()));
            line.setLinkid(linkid);
            this.creatWorkFlowLine(line);
        }
        return true;
    }

    private boolean copyWorkflowNodeParticpant(String linkid, Map<String, String> nodeSetIDMap) {
        if (nodeSetIDMap != null && !nodeSetIDMap.isEmpty()) {
            for (String key : nodeSetIDMap.keySet()) {
                this.copyOneWorkflowNodeParticpant(linkid, key, nodeSetIDMap.get(key));
            }
        }
        return true;
    }

    private boolean copyOneWorkflowNodeParticpant(final String linkid, final String oldnodeid, final String newnodeid) {
        try {
            String sql = "INSERT INTO sys_workflow_parti(p_id, p_linkid, p_nodeid, p_strategyid, p_updatetime, p_roleids,p_userids, p_param, p_desc)select ?, ?, ?, p_strategyid, ?, p_roleids,p_userids, p_param, p_desc from sys_workflow_parti where p_nodeid = ?";
            int execute = (Integer)this.jdbcTemplate.execute(sql, (PreparedStatementCallback)new AbstractLobCreatingPreparedStatementCallback((LobHandler)new DefaultLobHandler()){

                protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException, DataAccessException {
                    String ID = UUID.randomUUID().toString();
                    ps.setString(1, ID);
                    ps.setString(2, linkid);
                    ps.setString(3, newnodeid);
                    ps.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
                    ps.setString(5, oldnodeid);
                }
            });
            if (execute < 1) {
                return false;
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
            return false;
        }
        return true;
    }

    private boolean copyWorkflowNodeAction(String linkid, Map<String, String> nodeSetIDMap) {
        if (nodeSetIDMap != null && !nodeSetIDMap.isEmpty()) {
            for (String key : nodeSetIDMap.keySet()) {
                this.copyOneWorkflowNodeAction(linkid, key, nodeSetIDMap.get(key));
            }
        }
        return true;
    }

    private boolean copyOneWorkflowNodeAction(final String linkid, final String oldnodeid, final String newnodeid) {
        try {
            String sql = "INSERT INTO sys_workflow_action(a_id, a_linkid, a_nodeid, a_actionid,a_code a_updatetime, a_exset)select ?, ?, ?, a_actionid,a_code, ?, a_exset from sys_workflow_action where a_nodeid = ?";
            int execute = (Integer)this.jdbcTemplate.execute(sql, (PreparedStatementCallback)new AbstractLobCreatingPreparedStatementCallback((LobHandler)new DefaultLobHandler()){

                protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException, DataAccessException {
                    String ID = UUID.randomUUID().toString();
                    ps.setString(1, ID);
                    ps.setString(2, linkid);
                    ps.setString(3, newnodeid);
                    ps.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
                    ps.setString(5, oldnodeid);
                }
            });
            if (execute < 1) {
                return false;
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
            return false;
        }
        return true;
    }

    public String getLinkidByDefineID(String defineid, int state) {
        String sql = "SELECT t.f_linkid FROM sys_workflow_define t WHERE t.f_id = ? and t.f_state = ?";
        Object[] params = new Object[]{defineid, state};
        final StringBuffer linkidStr = new StringBuffer();
        this.jdbcTemplate.query(sql, new RowCallbackHandler(){

            public void processRow(ResultSet rs) throws SQLException {
                String linkid = rs.getString("f_linkid");
                linkidStr.append(linkid);
            }
        }, params);
        return linkidStr.toString();
    }

    public WorkFlowDefine releaseWorkFlowDefine(final WorkFlowDefine define) {
        try {
            String sql = "UPDATE sys_workflow_define T SET  f_state = ? WHERE T.f_id = ? and T.f_state = ? ";
            int execute = (Integer)this.jdbcTemplate.execute(sql, (PreparedStatementCallback)new AbstractLobCreatingPreparedStatementCallback((LobHandler)new DefaultLobHandler()){

                protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException, DataAccessException {
                    ps.setInt(1, 1);
                    ps.setString(2, define.getId());
                    ps.setInt(3, 0);
                }
            });
            if (execute > 0) {
                return this.getWorkFlowDefineByID(define.getId(), 1);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
            logger.error(e.getMessage(), (Throwable)e);
        }
        return null;
    }

    public Object updateWorkFlowGroup(final WorkFlowGroup group) {
        try {
            String sql = "UPDATE sys_workflow_group T SET  g_title = ?, g_order = ?, g_desc = ?, g_updatetime = ?  WHERE T.g_id = ?";
            int execute = (Integer)this.jdbcTemplate.execute(sql, (PreparedStatementCallback)new AbstractLobCreatingPreparedStatementCallback((LobHandler)new DefaultLobHandler()){

                protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException, DataAccessException {
                    int i = 1;
                    ps.setString(i++, group.getTitle());
                    ps.setString(i++, group.getOrder());
                    ps.setString(i++, group.getDesc());
                    ps.setTimestamp(i++, new Timestamp(System.currentTimeMillis()));
                    ps.setString(i++, group.getId());
                }
            });
            if (execute > 0) {
                return this.getWorkFlowGroupByID(group.getId());
            }
            return "\u6570\u636e\u5e93\u6267\u884c\u66f4\u65b0\u8bed\u53e5\u5931\u8d25\uff01";
        }
        catch (Exception e) {
            return e.getMessage();
        }
    }

    public Object updateWorkFlowDefineTitle(final String id, final int state, final String title) {
        try {
            String sql = "UPDATE sys_workflow_define SET  f_title = ?, f_updatetime = ? WHERE f_id = ? and f_state = ? ";
            int execute = (Integer)this.jdbcTemplate.execute(sql, (PreparedStatementCallback)new AbstractLobCreatingPreparedStatementCallback((LobHandler)new DefaultLobHandler()){

                protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException, DataAccessException {
                    int i = 1;
                    ps.setString(i++, title);
                    ps.setTimestamp(i++, new Timestamp(System.currentTimeMillis()));
                    ps.setString(i++, id);
                    ps.setInt(i++, state);
                }
            });
            if (execute > 0) {
                return this.getWorkFlowDefineByID(id, state);
            }
            return "\u6570\u636e\u5e93\u6267\u884c\u66f4\u65b0\u8bed\u53e5\u5931\u8d25\uff01";
        }
        catch (Exception e) {
            return e.getMessage();
        }
    }

    public String updateWorkFlowLine(final WorkFlowLine line) {
        try {
            String sql = "UPDATE sys_workflow_line SET l_linkid = ?, l_code = ?, l_title = ?, l_desc = ?, l_atcionid = ?, l_formula = ? , l_allmdim = ?, l_allreport = ?, l_updatetime = ?, l_mdim = ? , l_report = ?, l_creatdataversion = ?, l_msgcontent = ?, l_sendby_phone = ?,l_sendby_mail = ?, l_sendby_protal = ?, l_msguser = ?,l_bnid = ?,  l_anid = ? , l_conditionExecute = ?where l_id = ? ";
            int execute = (Integer)this.jdbcTemplate.execute(sql, (PreparedStatementCallback)new AbstractLobCreatingPreparedStatementCallback((LobHandler)new DefaultLobHandler()){

                protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException, DataAccessException {
                    int i = 1;
                    ps.setString(i++, line.getLinkid());
                    ps.setString(i++, line.getCode());
                    ps.setString(i++, line.getTitle());
                    ps.setString(i++, line.getDesc());
                    ps.setString(i++, line.getActionid());
                    ps.setString(i++, line.getFormula());
                    ps.setInt(i++, line.isAllmdim() ? 1 : 0);
                    ps.setInt(i++, line.isAllreport() ? 1 : 0);
                    ps.setTimestamp(i++, new Timestamp(System.currentTimeMillis()));
                    ps.setString(i++, line.getMdim());
                    ps.setString(i++, line.getReport());
                    ps.setInt(i++, line.isCreatDataVersion() ? 1 : 0);
                    ps.setString(i++, line.getMsgcontent());
                    ps.setInt(i++, line.isSendby_phone() ? 1 : 0);
                    ps.setInt(i++, line.isSendby_mail() ? 1 : 0);
                    ps.setInt(i++, line.isSendby_protal() ? 1 : 0);
                    String msgUser = CustomWorkFolwDao.this.getMsgUser(line.getMsguser());
                    ps.setString(i++, msgUser);
                    ps.setString(i++, line.getBeforeNodeID());
                    ps.setString(i++, line.getAfterNodeID());
                    ps.setString(i++, line.getConditionExecute());
                    ps.setString(i++, line.getId());
                }
            });
            if (execute < 1) {
                return "\u4fee\u6539\u6d41\u7a0b\u8f6c\u79fb\u7ebf,\u6570\u636e\u5e93\u6267\u884c\u66f4\u65b0\u8bed\u53e5\u5931\u8d25\uff01";
            }
        }
        catch (Exception e) {
            return e.getMessage();
        }
        return "";
    }

    public String creatWorkFlowLine(final WorkFlowLine line) {
        try {
            String sql = "insert into sys_workflow_line (l_id, l_linkid, l_code, l_title, l_desc, l_atcionid, l_formula, l_allmdim, l_allreport, l_updatetime, l_mdim, l_report, l_creatdataversion, l_msgcontent, l_sendby_phone, l_sendby_mail, l_sendby_protal, l_msguser, l_bnid, l_anid, l_conditionExecute)VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            int execute = (Integer)this.jdbcTemplate.execute(sql, (PreparedStatementCallback)new AbstractLobCreatingPreparedStatementCallback((LobHandler)new DefaultLobHandler()){

                protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException, DataAccessException {
                    int i = 1;
                    String id = line.getId();
                    if (null == id || id.isEmpty()) {
                        id = UUID.randomUUID().toString();
                    }
                    ps.setString(i++, id);
                    ps.setString(i++, line.getLinkid());
                    ps.setString(i++, line.getCode());
                    ps.setString(i++, line.getTitle());
                    ps.setString(i++, line.getDesc());
                    ps.setString(i++, line.getActionid());
                    ps.setString(i++, line.getFormula());
                    ps.setInt(i++, line.isAllmdim() ? 1 : 0);
                    ps.setInt(i++, line.isAllreport() ? 1 : 0);
                    ps.setTimestamp(i++, new Timestamp(System.currentTimeMillis()));
                    ps.setString(i++, line.getMdim());
                    ps.setString(i++, line.getReport());
                    ps.setInt(i++, line.isCreatDataVersion() ? 1 : 0);
                    ps.setString(i++, line.getMsgcontent());
                    ps.setInt(i++, line.isSendby_phone() ? 1 : 0);
                    ps.setInt(i++, line.isSendby_mail() ? 1 : 0);
                    ps.setInt(i++, line.isSendby_protal() ? 1 : 0);
                    String msguser = CustomWorkFolwDao.this.getMsgUser(line.getMsguser());
                    ps.setString(i++, msguser);
                    ps.setString(i++, line.getBeforeNodeID());
                    ps.setString(i++, line.getAfterNodeID());
                    String conditionExecute = line.getConditionExecute();
                    if (conditionExecute != null && !conditionExecute.isEmpty()) {
                        ps.setString(i++, conditionExecute);
                    } else {
                        ps.setString(i++, "DefaultConditionalExecute");
                    }
                }
            });
            if (execute < 1) {
                return "\u65b0\u5efa\u6d41\u7a0b\u8f6c\u79fb\u7ebf,\u6570\u636e\u5e93\u6267\u884c\u63d2\u5165\u8bed\u53e5\u5931\u8d25\uff01";
            }
        }
        catch (Exception e) {
            return e.getMessage();
        }
        return "";
    }

    private String getMsgUser(Map<String, Object> map) {
        JSONArray strategise = new JSONArray();
        for (String key : map.keySet()) {
            JSONObject obj = new JSONObject();
            obj.put("strategy", (Object)key);
            Object value = map.get(key);
            obj.put("value", value);
            strategise.put((Object)obj);
        }
        return strategise.toString();
    }

    private Map<String, Object> getMsgUser(String str) {
        HashMap<String, Object> userMap = new HashMap<String, Object>();
        JSONArray strategise = new JSONArray(str);
        for (int i = 0; i < strategise.length(); ++i) {
            JSONObject obj = strategise.getJSONObject(i);
            String strategyid = obj.getString("strategy");
            ArrayList value = new ArrayList();
            if (!obj.has("value")) continue;
            Object valueObj = obj.get("value");
            userMap.put(strategyid, valueObj);
        }
        return userMap;
    }

    public String[] saveWrokflowdDfine(WorkFlowDefineSaveEntity saveObj) {
        List<String> list;
        List<WorkFlowAction> actions;
        List<WorkFlowAction> creat_actions;
        List<String> list2;
        List<WorkFlowParticipant> updateParticis;
        List<WorkFlowParticipant> creatParticis;
        List<String> list3;
        List<WorkFlowLine> updateLines;
        List<WorkFlowLine> creatLines;
        List<String> list4;
        List<WorkFlowNodeSet> updateNodes;
        List<WorkFlowNodeSet> creatNodes;
        WorkFlowDefine define = saveObj.getDefine();
        StringBuffer sb = new StringBuffer();
        if (define != null) {
            Object obj = null;
            String id = define.getId();
            obj = id == null || id.isEmpty() ? this.creatWorkFlowDefine(define) : this.updateWorkFlowDefine(define);
            if (obj instanceof String) {
                sb.append((String)obj);
            } else if (obj instanceof WorkFlowDefine) {
                define = (WorkFlowDefine)obj;
            }
        }
        if ((creatNodes = saveObj.getCreat_nodes()) != null && !creatNodes.isEmpty()) {
            for (WorkFlowNodeSet workFlowNodeSet : creatNodes) {
                String error = this.creatWorkFlowNodeSet(workFlowNodeSet);
                if (error == null || error.isEmpty()) continue;
                sb.append(error);
            }
        }
        if ((updateNodes = saveObj.getUpdate_nodes()) != null && !updateNodes.isEmpty()) {
            for (WorkFlowNodeSet node : updateNodes) {
                String error = this.updateWorkFlowNodeSet(node);
                if (error == null || error.isEmpty()) continue;
                sb.append(error);
            }
        }
        if ((list4 = saveObj.getDel_nodes()) != null && !list4.isEmpty()) {
            this.delWorkFlowNodeSetByIDList(list4);
        }
        if ((creatLines = saveObj.getCreat_lines()) != null && !creatLines.isEmpty()) {
            for (WorkFlowLine workFlowLine : creatLines) {
                String error = this.creatWorkFlowLine(workFlowLine);
                if (error == null || error.isEmpty()) continue;
                sb.append(error);
            }
        }
        if ((updateLines = saveObj.getUpdate_lines()) != null && !updateLines.isEmpty()) {
            for (WorkFlowLine l : updateLines) {
                String error = this.updateWorkFlowLine(l);
                if (error == null || error.isEmpty()) continue;
                sb.append(error);
            }
        }
        if ((list3 = saveObj.getDel_lines()) != null && !list3.isEmpty()) {
            this.delWorkFlowLineByIDList(list3);
        }
        if ((creatParticis = saveObj.getCreat_participants()) != null && !creatParticis.isEmpty()) {
            for (WorkFlowParticipant workFlowParticipant : creatParticis) {
                String error = this.creatWorkFlowNParticipant(workFlowParticipant);
                if (error == null || error.isEmpty()) continue;
                sb.append(error);
            }
        }
        if ((updateParticis = saveObj.getUpdate_participants()) != null && !updateParticis.isEmpty()) {
            for (WorkFlowParticipant p : updateParticis) {
                String error = this.updateWorkFlowNParticipant(p);
                if (error == null || error.isEmpty()) continue;
                sb.append(error);
            }
        }
        if ((list2 = saveObj.getDel_participants()) != null && !list2.isEmpty()) {
            this.delWorkFlowParticisidsByIDList(list2);
        }
        if ((creat_actions = saveObj.getCreat_actions()) != null && !creat_actions.isEmpty()) {
            for (WorkFlowAction workFlowAction : creat_actions) {
                String error = this.creatWorkFlowNAction(workFlowAction);
                if (error == null || error.isEmpty()) continue;
                sb.append(error);
            }
        }
        if ((actions = saveObj.getUpdate_actions()) != null && !actions.isEmpty()) {
            for (WorkFlowAction a : actions) {
                String error = this.updateWorkFlowNAction(a);
                if (error == null || error.isEmpty()) continue;
                sb.append(error);
            }
        }
        if ((list = saveObj.getDel_actions()) != null && !list.isEmpty()) {
            this.delWorkFlowActionsByIDList(list);
        }
        return new String[]{sb.toString(), define.getId()};
    }

    public List<String> getAllDefineTitle() {
        String sql = "SELECT f_title FROM sys_workflow_define t order by t.f_title";
        Object[] params = new Object[]{};
        final ArrayList<String> titles = new ArrayList<String>();
        this.jdbcTemplate.query(sql, new RowCallbackHandler(){

            public void processRow(ResultSet rs) throws SQLException {
                titles.add(rs.getString("f_title"));
            }
        }, params);
        return titles;
    }

    public List<WorkFlowDefine> getWorkFlowDefineByTitle(String title) {
        String sql = "SELECT * FROM sys_workflow_define t WHERE t.f_title = ?";
        Object[] params = new Object[]{title};
        final ArrayList<WorkFlowDefine> defineList = new ArrayList<WorkFlowDefine>();
        this.jdbcTemplate.query(sql, new RowCallbackHandler(){

            public void processRow(ResultSet rs) throws SQLException {
                WorkFlowDefine define = CustomWorkFolwDao.this.getDefineByResultSet(rs);
                defineList.add(define);
            }
        }, params);
        return defineList;
    }

    public List<WorkFlowDefine> getWorkFlowDefineByCode(String code) {
        String sql = "SELECT * FROM sys_workflow_define t WHERE t.f_code = ?";
        Object[] params = new Object[]{code};
        final ArrayList<WorkFlowDefine> defineList = new ArrayList<WorkFlowDefine>();
        this.jdbcTemplate.query(sql, new RowCallbackHandler(){

            public void processRow(ResultSet rs) throws SQLException {
                WorkFlowDefine define = CustomWorkFolwDao.this.getDefineByResultSet(rs);
                defineList.add(define);
            }
        }, params);
        return defineList;
    }

    private WorkFlowDefine getDefineByResultSet(ResultSet rs) throws SQLException {
        WorkFlowDefine define = new WorkFlowDefine();
        define.setId(rs.getString("f_id"));
        define.setTitle(rs.getString("f_title"));
        define.setCode(rs.getString("f_code"));
        define.setOrder(rs.getString("f_order"));
        define.setParentID(rs.getString("f_groupid"));
        define.setDataid(rs.getString("f_dataid"));
        define.setFlowobjid(rs.getString("f_flowobjid"));
        define.setDesc(rs.getString("f_desc"));
        Object object = rs.getObject("f_xml");
        if (object != null) {
            if (object instanceof byte[]) {
                byte[] xml = CustomWorkFolwDao.toByteArray(object);
                if (null != xml) {
                    try {
                        String str = new String(xml, "UTF-8");
                        define.setXml(str);
                    }
                    catch (UnsupportedEncodingException e) {
                        logger.error(e.getMessage(), (Throwable)e);
                    }
                }
            } else {
                String str = rs.getString("f_xml");
                define.setXml(str);
            }
        }
        define.setAutostart("1".equals(String.valueOf(rs.getInt("f_autostart"))));
        define.setSubflow("1".equals(String.valueOf(rs.getInt("f_subflow"))));
        Timestamp time = rs.getTimestamp("f_updatetime");
        if (time != null) {
            define.setUpdatetime(new Date(time.getTime()));
        }
        define.setState(rs.getInt("f_state"));
        define.setLinkid(rs.getString("f_linkid"));
        define.setCustom("1".equals(String.valueOf(rs.getString("isCoutom"))));
        return define;
    }

    public List<WorkFlowParticipant> getWorkFlowParticipantsByID(String[] idarray) {
        final ArrayList<WorkFlowParticipant> participants = new ArrayList<WorkFlowParticipant>();
        if (null == idarray || idarray.length < 1) {
            return participants;
        }
        String sql = "SELECT * FROM sys_workflow_parti t WHERE t.p_id in (";
        String sub = "";
        for (String id : idarray) {
            sub = sub + "'" + id + "',";
        }
        sub = sub.substring(0, sub.length() - 1);
        sql = sql + sub + ")";
        this.jdbcTemplate.query(sql, new RowCallbackHandler(){

            public void processRow(ResultSet rs) throws SQLException {
                WorkFlowParticipant p = CustomWorkFolwDao.this.getQueryParticipant(rs);
                participants.add(p);
            }
        });
        return participants;
    }

    public List<WorkFlowAction> getWorkFlowActionByNodeID(String nodeid) {
        final ArrayList<WorkFlowAction> actions = new ArrayList<WorkFlowAction>();
        if (null == nodeid || nodeid.isEmpty()) {
            return actions;
        }
        String sql = "SELECT * FROM sys_workflow_action t WHERE t.a_nodeid = ?";
        Object[] params = new Object[]{nodeid};
        this.jdbcTemplate.query(sql, new RowCallbackHandler(){

            public void processRow(ResultSet rs) throws SQLException {
                WorkFlowAction line = CustomWorkFolwDao.this.getQueryAction(rs);
                actions.add(line);
            }
        }, params);
        return actions;
    }

    public List<WorkFlowParticipant> getWorkFlowParticipantByNodeID(String nodeid) {
        final ArrayList<WorkFlowParticipant> participants = new ArrayList<WorkFlowParticipant>();
        if (null == nodeid || nodeid.isEmpty()) {
            return participants;
        }
        String sql = "SELECT * FROM sys_workflow_parti t WHERE t.p_nodeid = ?";
        Object[] params = new Object[]{nodeid};
        this.jdbcTemplate.query(sql, new RowCallbackHandler(){

            public void processRow(ResultSet rs) throws SQLException {
                WorkFlowParticipant p = CustomWorkFolwDao.this.getQueryParticipant(rs);
                participants.add(p);
            }
        }, params);
        return participants;
    }

    public List<WorkFlowDefine> getAllWorkFlowDefineByState() {
        String sql = "SELECT * FROM sys_workflow_define t WHERE t.f_state = 1 order by t.f_order";
        final ArrayList<WorkFlowDefine> dlist = new ArrayList<WorkFlowDefine>();
        this.jdbcTemplate.query(sql, new RowCallbackHandler(){

            public void processRow(ResultSet rs) throws SQLException {
                WorkFlowDefine define = CustomWorkFolwDao.this.getDefineByResultSet(rs);
                dlist.add(define);
            }
        });
        return dlist;
    }

    public WorkFlowAction getWorkflowActionByCode(String nodeId) {
        try {
            String sql = "SELECT * FROM sys_workflow_action t WHERE t.a_nodeid = ?";
            Object[] params = new Object[]{nodeId};
            final WorkFlowAction action = new WorkFlowAction();
            this.jdbcTemplate.query(sql, new RowCallbackHandler(){

                public void processRow(ResultSet rs) throws SQLException {
                    action.setId(rs.getString("a_id"));
                    action.setLinkid(rs.getString("a_linkid"));
                    action.setNodeid(rs.getString("a_nodeid"));
                    action.setActionid(rs.getString("a_actionid"));
                    Timestamp time = rs.getTimestamp("a_updatetime");
                    if (time != null) {
                        action.setUpdateTime(new Date(time.getTime()));
                    }
                    action.setActionDesc(rs.getString("a_desc"));
                    action.setActionTitle(rs.getString("a_title"));
                    action.setImagePath(rs.getString("a_image"));
                    action.setStateName(rs.getString("a_state_name"));
                    action.setStateCode(rs.getString("a_state_code"));
                    action.setActionCode(rs.getString("a_code"));
                    Object object = rs.getObject("a_exset");
                    if (object != null) {
                        if (object instanceof byte[]) {
                            byte[] exset = CustomWorkFolwDao.toByteArray(object);
                            if (null != exset) {
                                try {
                                    String str = new String(exset, "UTF-8");
                                    action.setExset(str);
                                }
                                catch (UnsupportedEncodingException e) {
                                    logger.error(e.getMessage(), (Throwable)e);
                                }
                            }
                        } else {
                            String str = rs.getString("a_exset");
                            action.setExset(str);
                        }
                    }
                }
            }, params);
            return action;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
            throw new RuntimeException();
        }
    }

    public List<WorkFlowLine> getWorkFlowLinesByPreTaskAndActionId(String preTaskCode) {
        final ArrayList<WorkFlowLine> lines = new ArrayList<WorkFlowLine>();
        if (null == preTaskCode || preTaskCode.isEmpty()) {
            return lines;
        }
        String sql = "SELECT * FROM sys_workflow_line t WHERE t.l_bnid = ? ";
        Object[] params = new Object[]{preTaskCode};
        this.jdbcTemplate.query(sql, new RowCallbackHandler(){

            public void processRow(ResultSet rs) throws SQLException {
                WorkFlowLine line = CustomWorkFolwDao.this.getQueryLine(rs);
                lines.add(line);
            }
        }, params);
        return lines;
    }

    public WorkFlowAction getWorkflowActionByCode(String nodeId, String actionId) {
        try {
            String sql = "SELECT * FROM sys_workflow_action t WHERE t.a_nodeid = ? and t.a_code = ?";
            Object[] params = new Object[]{nodeId, actionId};
            final WorkFlowAction action = new WorkFlowAction();
            this.jdbcTemplate.query(sql, new RowCallbackHandler(){

                public void processRow(ResultSet rs) throws SQLException {
                    action.setId(rs.getString("a_id"));
                    action.setLinkid(rs.getString("a_linkid"));
                    action.setNodeid(rs.getString("a_nodeid"));
                    action.setActionid(rs.getString("a_actionid"));
                    Timestamp time = rs.getTimestamp("a_updatetime");
                    if (time != null) {
                        action.setUpdateTime(new Date(time.getTime()));
                    }
                    action.setActionDesc(rs.getString("a_desc"));
                    action.setActionTitle(rs.getString("a_title"));
                    action.setImagePath(rs.getString("a_image"));
                    action.setStateName(rs.getString("a_state_name"));
                    action.setStateCode(rs.getString("a_state_code"));
                    action.setActionCode(rs.getString("a_code"));
                    Object object = rs.getObject("a_exset");
                    if (object != null) {
                        if (object instanceof byte[]) {
                            byte[] exset = CustomWorkFolwDao.toByteArray(object);
                            if (null != exset) {
                                try {
                                    String str = new String(exset, "UTF-8");
                                    action.setExset(str);
                                }
                                catch (UnsupportedEncodingException e) {
                                    logger.error(e.getMessage(), (Throwable)e);
                                }
                            }
                        } else {
                            String str = rs.getString("a_exset");
                            action.setExset(str);
                        }
                    }
                }
            }, params);
            return action;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
            throw new RuntimeException();
        }
    }

    public WorkFlowAction getWorkflowActionById(String nodeId) {
        try {
            String sql = "SELECT * FROM sys_workflow_action t WHERE t.a_id = ?";
            Object[] params = new Object[]{nodeId};
            final WorkFlowAction action = new WorkFlowAction();
            this.jdbcTemplate.query(sql, new RowCallbackHandler(){

                public void processRow(ResultSet rs) throws SQLException {
                    action.setId(rs.getString("a_id"));
                    action.setLinkid(rs.getString("a_linkid"));
                    action.setNodeid(rs.getString("a_nodeid"));
                    action.setActionid(rs.getString("a_actionid"));
                    Timestamp time = rs.getTimestamp("a_updatetime");
                    if (time != null) {
                        action.setUpdateTime(new Date(time.getTime()));
                    }
                    action.setActionDesc(rs.getString("a_desc"));
                    action.setActionTitle(rs.getString("a_title"));
                    action.setImagePath(rs.getString("a_image"));
                    action.setStateName(rs.getString("a_state_name"));
                    action.setStateCode(rs.getString("a_state_code"));
                    action.setActionCode(rs.getString("a_code"));
                    Object object = rs.getObject("a_exset");
                    if (object != null) {
                        if (object instanceof byte[]) {
                            byte[] exset = CustomWorkFolwDao.toByteArray(object);
                            if (null != exset) {
                                try {
                                    String str = new String(exset, "UTF-8");
                                    action.setExset(str);
                                }
                                catch (UnsupportedEncodingException e) {
                                    logger.error(e.getMessage(), (Throwable)e);
                                }
                            }
                        } else {
                            String str = rs.getString("a_exset");
                            action.setExset(str);
                        }
                    }
                }
            }, params);
            return action;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
            throw new RuntimeException();
        }
    }

    public List<WorkFlowLine> getWorkFlowLineByEndNode(String endNode) {
        final ArrayList<WorkFlowLine> lines = new ArrayList<WorkFlowLine>();
        if (null == endNode || endNode.isEmpty()) {
            return lines;
        }
        String sql = "SELECT * FROM sys_workflow_line t WHERE t.l_anid = ? ";
        Object[] params = new Object[]{endNode};
        this.jdbcTemplate.query(sql, new RowCallbackHandler(){

            public void processRow(ResultSet rs) throws SQLException {
                WorkFlowLine line = CustomWorkFolwDao.this.getQueryLine(rs);
                lines.add(line);
            }
        }, params);
        return lines;
    }

    public WorkFlowAction getWorkflowActionByCodeandLinkId(String actionCode, String linkId) {
        try {
            String sql = "SELECT * FROM sys_workflow_action t WHERE t.a_code = ? and t.a_linkid = ?";
            Object[] params = new Object[]{actionCode, linkId};
            final WorkFlowAction action = new WorkFlowAction();
            this.jdbcTemplate.query(sql, new RowCallbackHandler(){

                public void processRow(ResultSet rs) throws SQLException {
                    action.setId(rs.getString("a_id"));
                    action.setLinkid(rs.getString("a_linkid"));
                    action.setNodeid(rs.getString("a_nodeid"));
                    action.setActionid(rs.getString("a_actionid"));
                    Timestamp time = rs.getTimestamp("a_updatetime");
                    if (time != null) {
                        action.setUpdateTime(new Date(time.getTime()));
                    }
                    action.setActionDesc(rs.getString("a_desc"));
                    action.setActionTitle(rs.getString("a_title"));
                    action.setImagePath(rs.getString("a_image"));
                    action.setStateName(rs.getString("a_state_name"));
                    action.setStateCode(rs.getString("a_state_code"));
                    action.setActionCode(rs.getString("a_code"));
                    Object object = rs.getObject("a_exset");
                    if (object != null) {
                        if (object instanceof byte[]) {
                            byte[] exset = CustomWorkFolwDao.toByteArray(object);
                            if (null != exset) {
                                try {
                                    String str = new String(exset, "UTF-8");
                                    action.setExset(str);
                                }
                                catch (UnsupportedEncodingException e) {
                                    logger.error(e.getMessage(), (Throwable)e);
                                }
                            }
                        } else {
                            String str = rs.getString("a_exset");
                            action.setExset(str);
                        }
                    }
                }
            }, params);
            return action;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
            throw new RuntimeException();
        }
    }

    public static byte[] toByteArray(Object obj) {
        byte[] bytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            bytes = bos.toByteArray();
            oos.close();
            bos.close();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        return bytes;
    }
}

