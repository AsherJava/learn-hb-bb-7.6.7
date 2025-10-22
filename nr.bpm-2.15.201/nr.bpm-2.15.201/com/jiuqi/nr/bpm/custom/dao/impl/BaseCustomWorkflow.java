/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.util.OrderGenerator
 *  com.jiuqi.util.StringUtils
 *  org.json.JSONArray
 *  org.json.JSONObject
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.bpm.custom.dao.impl;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowAction;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowDefine;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowGroup;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowLine;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowNodeSet;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowParticipant;
import com.jiuqi.nr.bpm.custom.exception.WorkflowException;
import com.jiuqi.util.OrderGenerator;
import com.jiuqi.util.StringUtils;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public abstract class BaseCustomWorkflow {
    protected static final Logger logger = LoggerFactory.getLogger(BaseCustomWorkflow.class);
    private static final String G_ID = "G_ID";
    private static final String G_TITLE = "G_TITLE";
    private static final String G_ORDER = "G_ORDER";
    private static final String G_DESC = "G_DESC";
    private static final String G_UPDATETIME = "G_UPDATETIME";
    protected static final String F_ID = "F_ID";
    protected static final String F_STATE = "F_STATE";
    private static final String F_TITLE = "F_TITLE";
    private static final String F_CODE = "F_CODE";
    private static final String F_ORDER = "F_ORDER";
    private static final String F_GROUPID = "F_GROUPID";
    private static final String F_UPDATETIME = "F_UPDATETIME";
    private static final String F_DESC = "F_DESC";
    private static final String F_DATAID = "F_DATAID";
    private static final String F_AUTOSTART = "F_AUTOSTART";
    private static final String F_SUBFLOW = "F_SUBFLOW";
    private static final String F_FLOWOBJID = "F_FLOWOBJID";
    private static final String F_LINKID = "F_LINKID";
    private static final String F_XML = "F_XML";
    private static final String F_CUSTOM = "F_CUSTOM";
    private static final String F_SENDMAIL = "F_SENDMAIL";
    protected static final String F_TASK = "F_TASK";
    protected static final String F_OLD_DEFINE_ID = "F_OLD_DEFINE_ID";
    private static final String N_ID = "N_ID";
    private static final String N_LINKID = "N_LINKID";
    private static final String N_CODE = "N_CODE";
    private static final String N_TITLE = "N_TITLE";
    private static final String N_DESC = "N_DESC";
    private static final String N_UPDATETIME = "N_UPDATETIME";
    private static final String N_FRONTARRIVE = "N_FRONTARRIVE";
    private static final String N_BACKARRIVE = "N_BACKARRIVE";
    private static final String N_WRITABLE = "N_WRITABLE";
    private static final String N_GETBACK = "N_GETBACK";
    private static final String N_APPOINTABLE = "N_APPOINTABLE";
    private static final String N_STARTAPPOINT = "N_STARTAPPOINT";
    private static final String N_APPOINTRESOURCE = "N_APPOINTRESOURCE";
    private static final String N_APPOINTRENAME = "N_APPOINTRENAME";
    private static final String N_APPOINTUSERRANGE = "N_APPOINTUSERRANGE";
    private static final String N_ACTIONRENAME_PASS = "N_ACTIONRENAME_PASS";
    private static final String N_ACTIONRENAME_REJECT = "N_ACTIONRENAME_REJECT";
    private static final String N_PARTIS = "N_PARTIS";
    private static final String N_ACTIONS = "N_ACTIONS";
    private static final String N_DEADLINE = "N_DEADLINE";
    private static final String N_SIGNNODE = "N_SIGNNODE";
    private static final String N_SPECIALTAG = "N_SPECIALTAG";
    private static final String N_COUNTERSIGN_COUNT = "N_COUNTERSIGN_COUNT";
    private static final String N_SIGN_USER = "N_SIGN_USER";
    private static final String N_USERORROLE = "N_USERORROLE";
    private static final String N_TRANSFER = "N_TRANSFER";
    private static final String N_SIGN_ROLE = "N_SIGN_ROLE";
    private static final String N_NODE_JUMP = "N_NODE_JUMP";
    private static final String N_SIGN_START_MODE = "N_SIGN_START_MODE";
    private static final String N_STATISTICAL_NODE = "N_STATISTICAL_NODE";
    private static final String L_ID = "L_ID";
    private static final String L_LINKID = "L_LINKID";
    private static final String L_CODE = "L_CODE";
    private static final String L_TITLE = "L_TITLE";
    private static final String L_DESC = "L_DESC";
    private static final String L_ATCIONID = "L_ATCIONID";
    private static final String L_FORMULA = "L_FORMULA";
    private static final String L_ALLMDIM = "L_ALLMDIM";
    private static final String L_ALLREPORT = "L_ALLREPORT";
    private static final String L_UPDATETIME = "L_UPDATETIME";
    private static final String L_MDIM = "L_MDIM";
    private static final String L_REPORT = "L_REPORT";
    private static final String L_CREATDATAVERSION = "L_CREATDATAVERSION";
    private static final String L_MSGCONTENT = "L_MSGCONTENT";
    private static final String L_SENDBY_PHONE = "L_SENDBY_PHONE";
    private static final String L_SENDBY_MAIL = "L_SENDBY_MAIL";
    private static final String L_SENDBY_PROTAL = "L_SENDBY_PROTAL";
    private static final String L_MSGUSER = "L_MSGUSER";
    private static final String L_BNID = "L_BNID";
    private static final String L_ANID = "L_ANID";
    private static final String L_CONDITIONEXECUTE = "L_CONDITIONEXECUTE";
    private static final String P_ID = "P_ID";
    private static final String P_LINKID = "P_LINKID";
    private static final String P_NODEID = "P_NODEID";
    private static final String P_STRATEGYID = "P_STRATEGYID";
    private static final String P_UPDATETIME = "P_UPDATETIME";
    private static final String P_ROLEIDS = "P_ROLEIDS";
    private static final String P_USERIDS = "P_USERIDS";
    private static final String P_PARAM = "P_PARAM";
    private static final String P_DESC = "P_DESC";
    private static final String A_ID = "A_ID";
    private static final String A_LINKID = "A_LINKID";
    private static final String A_NODEID = "A_NODEID";
    private static final String A_ACTIONID = "A_ACTIONID";
    private static final String A_UPDATETIME = "A_UPDATETIME";
    private static final String A_EXSET = "A_EXSET";
    private static final String A_STATE_NAME = "A_STATE_NAME";
    private static final String A_DESC = "A_DESC";
    private static final String A_TITLE = "A_TITLE";
    private static final String A_IMAGE = "A_IMAGE";
    private static final String A_STATE_CODE = "A_STATE_CODE";
    private static final String A_CODE = "A_CODE";
    private static final Function<ResultSet, WorkFlowGroup> ENTITY_WORKFLOWGROUP = rs -> {
        WorkFlowGroup entity = new WorkFlowGroup();
        int col = 1;
        try {
            entity.setId(rs.getString(col++));
            entity.setTitle(rs.getString(col++));
            entity.setOrder(rs.getString(col++));
            entity.setDesc(rs.getString(col++));
            Timestamp time = rs.getTimestamp(col++);
            if (time != null) {
                entity.setUpdatetime(new Date(time.getTime()));
            }
        }
        catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("read workflowgroup error.", e);
        }
        return entity;
    };
    private static final Function<ResultSet, WorkFlowDefine> ENTITY_WORKFLOWDEFINE = rs -> {
        WorkFlowDefine entity = new WorkFlowDefine();
        int col = 1;
        try {
            entity.setId(rs.getString(col++));
            entity.setState(rs.getInt(col++));
            entity.setTitle(rs.getString(col++));
            entity.setCode(rs.getString(col++));
            entity.setOrder(rs.getString(col++));
            entity.setParentID(rs.getString(col++));
            Timestamp time = rs.getTimestamp(col++);
            if (time != null) {
                entity.setUpdatetime(new Date(time.getTime()));
            }
            entity.setDesc(rs.getString(col++));
            entity.setDataid(rs.getString(col++));
            entity.setAutostart("1".equals(String.valueOf(rs.getString(col++))));
            entity.setSubflow("1".equals(String.valueOf(rs.getString(col++))));
            entity.setFlowobjid(rs.getString(col++));
            entity.setLinkid(rs.getString(col++));
            Object object = rs.getObject(col);
            if (object != null) {
                Object xml;
                if (object instanceof byte[]) {
                    xml = BaseCustomWorkflow.toByteArray(object);
                    if (null != xml) {
                        try {
                            String str = new String((byte[])xml, "UTF-8");
                            entity.setXml(str);
                        }
                        catch (UnsupportedEncodingException e) {
                            logger.error(e.getMessage(), e);
                        }
                    }
                } else {
                    xml = rs.getString(col);
                    entity.setXml((String)xml);
                }
            }
            int n = ++col;
            entity.setCustom("1".equals(String.valueOf(rs.getString(n))));
            int n2 = ++col;
            entity.setSendEmail("1".equals(String.valueOf(rs.getString(n2))));
            int n3 = ++col;
            ++col;
            entity.setTaskId(rs.getString(n3));
        }
        catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("read workflowdefine error.", e);
        }
        return entity;
    };
    private static final Function<ResultSet, WorkFlowAction> ENTITY_WORKFLOWACTION = rs -> {
        WorkFlowAction entity = new WorkFlowAction();
        int col = 1;
        try {
            Object object;
            entity.setId(rs.getString(col++));
            entity.setLinkid(rs.getString(col++));
            entity.setNodeid(rs.getString(col++));
            entity.setActionid(rs.getString(col++));
            Timestamp time = rs.getTimestamp(col++);
            if (time != null) {
                entity.setUpdateTime(new Date(time.getTime()));
            }
            if ((object = rs.getObject(col)) != null) {
                Object exset;
                if (object instanceof byte[]) {
                    exset = BaseCustomWorkflow.toByteArray(object);
                    if (null != exset) {
                        try {
                            String str = new String((byte[])exset, "UTF-8");
                            entity.setExset(str);
                        }
                        catch (UnsupportedEncodingException e) {
                            logger.error(e.getMessage(), e);
                        }
                    }
                } else {
                    exset = rs.getString(col);
                    entity.setExset((String)exset);
                }
            }
            int n = ++col;
            entity.setStateName(rs.getString(n));
            int n2 = ++col;
            entity.setActionDesc(rs.getString(n2));
            int n3 = ++col;
            entity.setActionTitle(rs.getString(n3));
            int n4 = ++col;
            entity.setImagePath(rs.getString(n4));
            int n5 = ++col;
            entity.setStateCode(rs.getString(n5));
            int n6 = ++col;
            ++col;
            entity.setActionCode(rs.getString(n6));
        }
        catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("read workflowaction error.", e);
        }
        return entity;
    };
    private static final Function<ResultSet, WorkFlowLine> ENTITY_WORKFLOWLINE = rs -> {
        WorkFlowLine entity = new WorkFlowLine();
        int col = 1;
        try {
            String str;
            Object object2;
            Object object;
            entity.setId(rs.getString(col++));
            entity.setLinkid(rs.getString(col++));
            entity.setCode(rs.getString(col++));
            entity.setTitle(rs.getString(col++));
            entity.setDesc(rs.getString(col++));
            entity.setActionid(rs.getString(col++));
            entity.setFormula(rs.getString(col++));
            entity.setAllmdim("1".equals(rs.getString(col++)));
            entity.setAllreport("1".equals(rs.getString(col++)));
            Timestamp time = rs.getTimestamp(col++);
            if (time != null) {
                entity.setUpdatetime(new Date(time.getTime()));
            }
            if ((object = rs.getObject(col)) != null) {
                Object mdim;
                if (object instanceof byte[]) {
                    mdim = BaseCustomWorkflow.toByteArray(object);
                    if (null != mdim) {
                        try {
                            String str2 = new String((byte[])mdim, "UTF-8");
                            entity.setMdim(str2);
                        }
                        catch (UnsupportedEncodingException e) {
                            logger.error(e.getMessage(), e);
                        }
                    }
                } else {
                    mdim = rs.getString(col);
                    entity.setMdim((String)mdim);
                }
            }
            if ((object2 = rs.getObject(++col)) != null) {
                Object report;
                if (object2 instanceof byte[]) {
                    report = BaseCustomWorkflow.toByteArray(object2);
                    if (null != report) {
                        try {
                            String str3 = new String((byte[])report, "UTF-8");
                            entity.setReport(str3);
                        }
                        catch (UnsupportedEncodingException e) {
                            logger.error(e.getMessage(), e);
                        }
                    }
                } else {
                    report = rs.getString(col);
                    entity.setReport((String)report);
                }
            }
            int n = ++col;
            boolean creatdataversion = rs.getInt(n) == 1;
            entity.setCreatDataVersion(creatdataversion);
            Object object3 = rs.getObject(++col);
            if (object3 != null) {
                if (object3 instanceof byte[]) {
                    byte[] msgcontent = BaseCustomWorkflow.toByteArray(object3);
                    if (null != msgcontent) {
                        try {
                            str = new String(msgcontent, "UTF-8");
                            entity.setMsgcontent(str);
                        }
                        catch (UnsupportedEncodingException e) {
                            logger.error(e.getMessage(), e);
                        }
                    }
                } else {
                    String msgContent = rs.getString(col);
                    entity.setMsgcontent(msgContent);
                }
            }
            int n2 = ++col;
            entity.setSendby_phone("1".equals(rs.getString(n2)));
            int n3 = ++col;
            entity.setSendby_mail("1".equals(rs.getString(n3)));
            int n4 = ++col;
            entity.setSendby_protal("1".equals(rs.getString(n4)));
            Object object4 = rs.getObject(++col);
            if (object4 != null) {
                if (object4 instanceof byte[]) {
                    byte[] msguser = BaseCustomWorkflow.toByteArray(object4);
                    if (null != msguser) {
                        try {
                            String str4 = new String(msguser, "UTF-8");
                            if (str4 != null && !str4.isEmpty()) {
                                Map<String, Object> userMap = BaseCustomWorkflow.getMsgUser(str4);
                                entity.setMsguser(userMap);
                            }
                        }
                        catch (UnsupportedEncodingException e) {
                            logger.error(e.getMessage(), e);
                        }
                    }
                } else {
                    str = rs.getString(col);
                    if (str != null && !str.isEmpty()) {
                        Map<String, Object> userMap = BaseCustomWorkflow.getMsgUser(str);
                        entity.setMsguser(userMap);
                    }
                }
            }
            int n5 = ++col;
            entity.setBeforeNodeID(rs.getString(n5));
            int n6 = ++col;
            entity.setAfterNodeID(rs.getString(n6));
            int n7 = ++col;
            ++col;
            entity.setConditionExecute(rs.getString(n7));
        }
        catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("read workflowline error.", e);
        }
        return entity;
    };
    private static final Function<ResultSet, WorkFlowNodeSet> ENTITY_WORKFLOWNODESET = rs -> {
        WorkFlowNodeSet entity = new WorkFlowNodeSet();
        int col = 1;
        try {
            String signRole;
            Object object2;
            String str;
            entity.setId(rs.getString(col++));
            entity.setLinkid(rs.getString(col++));
            entity.setCode(rs.getString(col++));
            entity.setTitle(rs.getString(col++));
            entity.setDesc(rs.getString(col++));
            Timestamp time = rs.getTimestamp(col++);
            if (time != null) {
                entity.setUpdateTime(new Date(time.getTime()));
            }
            entity.setFrontarrive(rs.getInt(col++));
            entity.setBackarrive(rs.getInt(col++));
            boolean writable = "1".equals(rs.getString(col++));
            entity.setWritable(writable);
            boolean getback = "1".equals(rs.getString(col++));
            entity.setGetback(getback);
            boolean appointable = "1".equals(rs.getString(col++));
            entity.setAppointable(appointable);
            boolean startappoint = "1".equals(rs.getString(col++));
            entity.setStartappoint(startappoint);
            entity.setAppointResource(rs.getString(col++));
            entity.setAppointReName(rs.getString(col++));
            entity.setAppointUserRange(rs.getString(col++));
            entity.setActionReName_pass(rs.getString(col++));
            entity.setActionReName_reject(rs.getString(col++));
            Object object = rs.getObject(col);
            if (object != null) {
                if (object instanceof byte[]) {
                    byte[] partis = BaseCustomWorkflow.toByteArray(object);
                    if (null != partis) {
                        try {
                            str = new String(partis, "UTF-8");
                            if (str != null && !str.isEmpty()) {
                                String[] parray = str.split(";");
                                entity.setPartis(parray);
                            }
                        }
                        catch (UnsupportedEncodingException e) {
                            logger.error(e.getMessage(), e);
                        }
                    }
                } else {
                    String str2 = rs.getString(col);
                    if (str2 != null && !str2.isEmpty()) {
                        String[] parray = str2.split(";");
                        entity.setPartis(parray);
                    }
                }
            }
            if ((object2 = rs.getObject(++col)) != null) {
                if (object2 instanceof byte[]) {
                    byte[] actions = BaseCustomWorkflow.toByteArray(object2);
                    if (null != actions) {
                        try {
                            String str3 = new String(actions, "UTF-8");
                            if (str3 != null && !str3.isEmpty()) {
                                String[] aarray = str3.split(";");
                                entity.setActions(aarray);
                            }
                        }
                        catch (UnsupportedEncodingException e) {
                            logger.error(e.getMessage(), e);
                        }
                    }
                } else {
                    str = rs.getString(col);
                    if (str != null && !str.isEmpty()) {
                        String[] aarray = str.split(";");
                        entity.setActions(aarray);
                    }
                }
            }
            int n = ++col;
            entity.setDeadline(rs.getString(n));
            int n2 = ++col;
            boolean signNode = "1".equals(rs.getString(n2));
            entity.setSignNode(signNode);
            int n3 = ++col;
            boolean specialTag = "1".equals(rs.getString(n3));
            entity.setSpecialTag(specialTag);
            int n4 = ++col;
            entity.setCountersign_count(rs.getInt(n4));
            int n5 = ++col;
            ++col;
            String signUser = rs.getString(n5);
            if (signUser != null && !signUser.isEmpty()) {
                String[] aarray = signUser.split(";");
                entity.setSign_user(aarray);
            }
            if ((signRole = rs.getString(col++)) != null && !signRole.isEmpty()) {
                String[] aarray = signRole.split(";");
                entity.setSign_role(aarray);
            }
            entity.setUserOrRole(rs.getString(col++));
            entity.setTransfer(rs.getString(col++));
            boolean nodeJump = "1".equals(rs.getString(col++));
            entity.setNodeJump(nodeJump);
            entity.setSignStartMode("1".equals(rs.getString(col++)));
            entity.setStatisticalNode("1".equals(rs.getString(col++)));
        }
        catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("read workflownodeset error.", e);
        }
        return entity;
    };
    private static final Function<ResultSet, WorkFlowParticipant> ENTITY_WORKFLOWPARTICIPANT = rs -> {
        WorkFlowParticipant entity = new WorkFlowParticipant();
        int col = 1;
        try {
            Object object2;
            Object object;
            entity.setId(rs.getString(col++));
            entity.setLinkid(rs.getString(col++));
            entity.setNodeid(rs.getString(col++));
            entity.setStrategyid(rs.getString(col++));
            Timestamp time = rs.getTimestamp(col++);
            if (time != null) {
                entity.setUpdatetime(new Date(time.getTime()));
            }
            if ((object = rs.getObject(col)) != null) {
                String[] array;
                if (object instanceof byte[]) {
                    byte[] rolesValue = BaseCustomWorkflow.toByteArray(object);
                    if (null != rolesValue) {
                        array = BaseCustomWorkflow.byteArrayToStringArray(rolesValue);
                        entity.setRoleIds(array);
                    }
                } else {
                    String str = rs.getString(col);
                    if (str != null && str.length() > 0) {
                        array = str.split(";");
                        entity.setRoleIds(array);
                    }
                }
            }
            if ((object2 = rs.getObject(++col)) != null) {
                String[] array;
                if (object2 instanceof byte[]) {
                    byte[] usersValue = BaseCustomWorkflow.toByteArray(object2);
                    if (null != usersValue) {
                        array = BaseCustomWorkflow.byteArrayToStringArray(usersValue);
                        entity.setUserIds(array);
                    }
                } else {
                    String str = rs.getString(col);
                    if (str != null && str.length() > 0) {
                        array = str.split(";");
                        entity.setUserIds(array);
                    }
                }
            }
            int n = ++col;
            entity.setParam(rs.getString(n));
            int n2 = ++col;
            ++col;
            entity.setDescription(rs.getString(n2));
        }
        catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("read workflowparticipant error.", e);
        }
        return entity;
    };
    @Autowired
    protected JdbcTemplate jdbcTemplate;

    public void insertWorkflowGroup(WorkFlowGroup workFlowGroup, String tableName) throws JQException {
        String sqls = String.format("INSERT INTO %s (%s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?)", tableName, G_ID, G_TITLE, G_ORDER, G_DESC, G_UPDATETIME);
        String groupID = workFlowGroup.getId();
        if (groupID == null) {
            String order;
            if (null == groupID || groupID.isEmpty()) {
                groupID = UUID.randomUUID().toString();
            }
            if (null == (order = workFlowGroup.getOrder()) || order.isEmpty()) {
                order = OrderGenerator.newOrder();
            }
            Object[] args = new Object[]{groupID, workFlowGroup.getTitle(), order, workFlowGroup.getDesc(), new Timestamp(System.currentTimeMillis())};
            try {
                this.jdbcTemplate.update(sqls, args);
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
                throw new JQException((ErrorEnum)WorkflowException.ERROR_INSERT);
            }
        }
    }

    public String insertWorkflowDefine(WorkFlowDefine define, String tableName) throws JQException {
        String linkid;
        String order;
        String sqls = String.format("INSERT INTO %s (%s, %s, %s, %s, %s,%s, %s, %s, %s, %s,%s, %s, %s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?,?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?)", tableName, F_ID, F_STATE, F_TITLE, F_CODE, F_ORDER, F_GROUPID, F_UPDATETIME, F_DESC, F_DATAID, F_AUTOSTART, F_SUBFLOW, F_FLOWOBJID, F_LINKID, F_XML, F_CUSTOM, F_SENDMAIL, F_TASK);
        String ID = define.getId();
        if (null == ID || ID.isEmpty()) {
            ID = UUID.randomUUID().toString();
        }
        if (null == (order = define.getOrder()) || order.isEmpty()) {
            order = OrderGenerator.newOrder();
        }
        if (null == (linkid = define.getLinkid()) || linkid.isEmpty()) {
            linkid = UUID.randomUUID().toString();
        }
        String xml = define.getXml();
        Object[] args = new Object[]{ID, define.getState(), define.getTitle(), define.getCode(), order, define.getParentID(), new Timestamp(System.currentTimeMillis()), define.getDesc(), define.getDataid(), define.isAutostart() ? 1 : 0, define.isSubflow() ? 1 : 0, define.getFlowobjid(), linkid, xml, define.isCustom() ? 1 : 0, define.isSendEmail() ? 1 : 0, define.getTaskId()};
        try {
            this.jdbcTemplate.update(sqls, args);
            return ID;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)WorkflowException.ERROR_INSERT);
        }
    }

    public void insertWorkflowNode(WorkFlowNodeSet workflowNode, String tableName) throws JQException {
        String sqls = String.format("INSERT INTO %s (%s, %s, %s, %s, %s,%s, %s, %s, %s, %s,%s, %s, %s, %s, %s,%s, %s, %s, %s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s) VALUES (?, ?, ?, ?, ?,?, ?, ?, ?, ?,?, ?, ?, ?, ?,?, ?, ?, ?,?,?,?,?,?,?,?,?,?,?,?)", tableName, N_ID, N_LINKID, N_CODE, N_TITLE, N_DESC, N_UPDATETIME, N_FRONTARRIVE, N_BACKARRIVE, N_WRITABLE, N_GETBACK, N_APPOINTABLE, N_STARTAPPOINT, N_APPOINTRESOURCE, N_APPOINTRENAME, N_APPOINTUSERRANGE, N_ACTIONRENAME_PASS, N_ACTIONRENAME_REJECT, N_PARTIS, N_ACTIONS, N_DEADLINE, N_SIGNNODE, N_SPECIALTAG, N_COUNTERSIGN_COUNT, N_SIGN_USER, N_SIGN_ROLE, N_USERORROLE, N_TRANSFER, N_NODE_JUMP, N_SIGN_START_MODE, N_STATISTICAL_NODE);
        Object[] args = new Object[30];
        String ID = workflowNode.getId();
        if (null == ID || ID.isEmpty()) {
            ID = UUID.randomUUID().toString();
        }
        args[0] = ID;
        args[1] = workflowNode.getLinkid();
        args[2] = workflowNode.getCode();
        args[3] = workflowNode.getTitle();
        args[4] = workflowNode.getDesc();
        args[5] = new Timestamp(System.currentTimeMillis());
        args[6] = workflowNode.getFrontarrive();
        args[7] = workflowNode.getBackarrive();
        args[8] = workflowNode.isWritable() ? 1 : 0;
        args[9] = workflowNode.isGetback() ? 1 : 0;
        args[10] = workflowNode.isAppointable() ? 1 : 0;
        args[11] = workflowNode.isStartappoint() ? 1 : 0;
        args[12] = workflowNode.getAppointResource();
        args[13] = workflowNode.getAppointReName();
        args[14] = workflowNode.getAppointUserRange();
        args[15] = workflowNode.getActionReName_pass();
        args[16] = workflowNode.getActionReName_reject();
        String[] partis = workflowNode.getPartis();
        String partisStr = "";
        if (partis != null) {
            for (String id : partis) {
                partisStr = partisStr + id + ";";
            }
            partisStr = partisStr.substring(0, partisStr.length() - 1);
        }
        args[17] = partisStr;
        String[] actions = workflowNode.getActions();
        String actionIdStr = "";
        if (actions != null) {
            for (String id : actions) {
                actionIdStr = actionIdStr + id + ";";
            }
            actionIdStr.substring(0, actionIdStr.length() - 1);
        }
        args[18] = actionIdStr;
        args[19] = workflowNode.getDeadline();
        args[20] = workflowNode.isSignNode() ? 1 : 0;
        args[21] = workflowNode.isSpecialTag() ? 1 : 0;
        args[22] = workflowNode.getCountersign_count();
        if (null != workflowNode.getSign_user() && workflowNode.getSign_user().length > 0) {
            String[] array = workflowNode.getSign_user();
            String idStr = "";
            String[] stringArray = array;
            int n = stringArray.length;
            for (int i = 0; i < n; ++i) {
                String id = stringArray[i];
                idStr = idStr + id + ";";
            }
            idStr = idStr.substring(0, idStr.length() - 1);
            args[23] = idStr;
        }
        if (null != workflowNode.getSign_role() && workflowNode.getSign_role().length > 0) {
            String[] array = workflowNode.getSign_role();
            String idStr = "";
            for (String id : array) {
                idStr = idStr + id + ";";
            }
            idStr = idStr.substring(0, idStr.length() - 1);
            args[24] = idStr;
        }
        args[25] = workflowNode.getUserOrRole();
        args[26] = workflowNode.getTransfer();
        args[27] = workflowNode.isNodeJump() ? 1 : 0;
        args[28] = workflowNode.isSignStartMode() ? 1 : 0;
        args[29] = workflowNode.isStatisticalNode() ? 1 : 0;
        try {
            this.jdbcTemplate.update(sqls, args);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)WorkflowException.ERROR_INSERT);
        }
    }

    public void insertWorkflowLine(WorkFlowLine workflowLine, String tableName) throws JQException {
        String sqls = String.format("INSERT INTO %s (%s, %s, %s, %s, %s,%s, %s, %s, %s, %s,%s, %s, %s, %s, %s,%s, %s, %s, %s, %s,%s) VALUES (?, ?, ?, ?, ?,?, ?, ?, ?, ?,?, ?, ?, ?, ?,?, ?, ?, ?, ?,?)", tableName, L_ID, L_LINKID, L_CODE, L_TITLE, L_DESC, L_ATCIONID, L_FORMULA, L_ALLMDIM, L_ALLREPORT, L_UPDATETIME, L_MDIM, L_REPORT, L_CREATDATAVERSION, L_MSGCONTENT, L_SENDBY_PHONE, L_SENDBY_MAIL, L_SENDBY_PROTAL, L_MSGUSER, L_BNID, L_ANID, L_CONDITIONEXECUTE);
        Object[] args = new Object[21];
        String ID = workflowLine.getId();
        if (null == ID || ID.isEmpty()) {
            ID = UUID.randomUUID().toString();
        }
        args[0] = ID;
        args[1] = workflowLine.getLinkid();
        args[2] = workflowLine.getCode();
        args[3] = workflowLine.getTitle();
        args[4] = workflowLine.getDesc();
        args[5] = workflowLine.getActionid();
        args[6] = workflowLine.getFormula();
        args[7] = workflowLine.isAllmdim() ? 1 : 0;
        args[8] = workflowLine.isAllreport() ? 1 : 0;
        args[9] = new Timestamp(System.currentTimeMillis());
        args[10] = workflowLine.getMdim();
        args[11] = workflowLine.getReport();
        args[12] = workflowLine.isCreatDataVersion() ? 1 : 0;
        args[13] = workflowLine.getMsgcontent();
        args[14] = workflowLine.isSendby_phone() ? 1 : 0;
        args[15] = workflowLine.isSendby_mail() ? 1 : 0;
        args[16] = workflowLine.isSendby_protal() ? 1 : 0;
        String msguser = this.getMsgUser(workflowLine.getMsguser());
        args[17] = msguser;
        args[18] = workflowLine.getBeforeNodeID();
        args[19] = workflowLine.getAfterNodeID();
        String conditionExecute = workflowLine.getConditionExecute();
        if (null == conditionExecute) {
            conditionExecute = "DefaultConditionalExecute";
        }
        args[20] = conditionExecute;
        try {
            this.jdbcTemplate.update(sqls, args);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)WorkflowException.ERROR_INSERT);
        }
    }

    public void insertWorkflowParticipant(WorkFlowParticipant workflowParticipant, String tableName) throws JQException {
        String sqls = String.format("INSERT INTO %s (%s, %s, %s, %s, %s,%s, %s, %s,  %s) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)", tableName, P_ID, P_LINKID, P_NODEID, P_STRATEGYID, P_UPDATETIME, P_ROLEIDS, P_USERIDS, P_PARAM, P_DESC);
        Object[] args = new Object[9];
        String ID = workflowParticipant.getId();
        if (null == ID || ID.isEmpty()) {
            ID = UUID.randomUUID().toString();
        }
        args[0] = ID;
        args[1] = workflowParticipant.getLinkid();
        args[2] = workflowParticipant.getNodeid();
        args[3] = workflowParticipant.getStrategyid();
        args[4] = new Timestamp(System.currentTimeMillis());
        String rolesValue = this.arrayToString(workflowParticipant.getRoleIds());
        String usersValue = this.arrayToString(workflowParticipant.getUserIds());
        args[5] = rolesValue;
        args[6] = usersValue;
        args[7] = workflowParticipant.getParam();
        args[8] = workflowParticipant.getDescription();
        try {
            this.jdbcTemplate.update(sqls, args);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)WorkflowException.ERROR_INSERT);
        }
    }

    public void insertWorkflowAction(WorkFlowAction workflowAction, String tableName) throws JQException {
        String sqls = String.format("INSERT INTO %s (%s, %s, %s, %s, %s,%s, %s, %s, %s, %s,%s,%s) VALUES (?, ?, ?, ?, ?,?, ?, ?, ?, ?,?,?)", tableName, A_ID, A_LINKID, A_NODEID, A_ACTIONID, A_UPDATETIME, A_EXSET, A_STATE_NAME, A_DESC, A_TITLE, A_IMAGE, A_STATE_CODE, A_CODE);
        Object[] args = new Object[12];
        String ID = workflowAction.getId();
        if (null == ID || ID.isEmpty()) {
            ID = UUID.randomUUID().toString();
        }
        args[0] = ID;
        args[1] = workflowAction.getLinkid();
        args[2] = workflowAction.getNodeid();
        args[3] = workflowAction.getActionid();
        args[4] = new Timestamp(System.currentTimeMillis());
        args[5] = workflowAction.getExset();
        args[6] = workflowAction.getStateName();
        args[7] = workflowAction.getActionDesc();
        args[8] = workflowAction.getActionTitle();
        args[9] = workflowAction.getImagePath();
        args[10] = workflowAction.getStateCode();
        args[11] = workflowAction.getActionCode();
        try {
            this.jdbcTemplate.update(sqls, args);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)WorkflowException.ERROR_INSERT);
        }
    }

    public void updateWorkflowDefine(WorkFlowDefine define, String tableName) throws JQException {
        String sql = String.format("UPDATE %s SET  %s = ?, %s = ?,  %s = ?, %s = ?,  %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ? , %s = ?  WHERE %s = ?", tableName, F_STATE, F_TITLE, F_CODE, F_ORDER, F_GROUPID, F_UPDATETIME, F_DESC, F_DATAID, F_AUTOSTART, F_SUBFLOW, F_FLOWOBJID, F_LINKID, F_XML, F_CUSTOM, F_SENDMAIL, F_ID);
        Object[] args = new Object[]{define.getState(), define.getTitle(), define.getCode(), define.getOrder(), define.getParentID(), new Timestamp(System.currentTimeMillis()), define.getDesc(), define.getDataid(), define.isAutostart() ? 1 : 0, define.isSubflow() ? 1 : 0, define.getFlowobjid(), define.getLinkid(), define.getXml(), define.isCustom() ? 1 : 0, define.isSendEmail() ? 1 : 0, define.getId()};
        try {
            this.jdbcTemplate.update(sql, args);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)WorkflowException.ERROR_UPDATE);
        }
    }

    public void updateWorkflowNode(WorkFlowNodeSet workflowNode, String tableName) {
        String sql = String.format("UPDATE %s SET  %s = ?, %s = ?,  %s = ?, %s = ?,  %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s=? , %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?,%s=?,%s=? WHERE %s = ? and %s = ?", tableName, N_LINKID, N_CODE, N_TITLE, N_DESC, N_UPDATETIME, N_FRONTARRIVE, N_BACKARRIVE, N_WRITABLE, N_GETBACK, N_APPOINTABLE, N_STARTAPPOINT, N_APPOINTRESOURCE, N_APPOINTRENAME, N_APPOINTUSERRANGE, N_ACTIONRENAME_PASS, N_ACTIONRENAME_REJECT, N_PARTIS, N_ACTIONS, N_DEADLINE, N_SIGNNODE, N_SPECIALTAG, N_COUNTERSIGN_COUNT, N_SIGN_USER, N_SIGN_ROLE, N_USERORROLE, N_TRANSFER, N_NODE_JUMP, N_SIGN_START_MODE, N_STATISTICAL_NODE, N_ID, N_LINKID);
        Object[] args = new Object[31];
        args[0] = workflowNode.getLinkid();
        args[1] = workflowNode.getCode();
        args[2] = workflowNode.getTitle();
        args[3] = workflowNode.getDesc();
        args[4] = new Timestamp(System.currentTimeMillis());
        args[5] = workflowNode.getFrontarrive();
        args[6] = workflowNode.getBackarrive();
        args[7] = workflowNode.isWritable() ? 1 : 0;
        args[8] = workflowNode.isGetback() ? 1 : 0;
        args[9] = workflowNode.isAppointable() ? 1 : 0;
        args[10] = workflowNode.isStartappoint() ? 1 : 0;
        args[11] = workflowNode.getAppointResource();
        args[12] = workflowNode.getAppointReName();
        args[13] = workflowNode.getAppointUserRange();
        args[14] = workflowNode.getActionReName_pass();
        args[15] = workflowNode.getActionReName_reject();
        String[] partisArray = workflowNode.getPartis();
        String partisIdStr = "";
        if (partisArray != null) {
            for (String id : partisArray) {
                partisIdStr = partisIdStr + id + ";";
            }
            partisIdStr = partisIdStr.substring(0, partisIdStr.length() - 1);
        }
        args[16] = partisIdStr;
        String[] actionArray = workflowNode.getActions();
        String actionIdStr = "";
        if (actionArray != null) {
            for (String id : actionArray) {
                actionIdStr = actionIdStr + id + ";";
            }
            actionIdStr.substring(0, actionIdStr.length() - 1);
        }
        args[17] = actionIdStr;
        args[18] = workflowNode.getDeadline();
        args[19] = workflowNode.isSignNode() ? 1 : 0;
        args[20] = workflowNode.isSpecialTag() ? 1 : 0;
        args[21] = workflowNode.getCountersign_count();
        if (null != workflowNode.getSign_user() && workflowNode.getSign_user().length > 0) {
            String[] array = workflowNode.getSign_user();
            String idStr = "";
            String[] stringArray = array;
            int n = stringArray.length;
            for (int i = 0; i < n; ++i) {
                String id = stringArray[i];
                idStr = idStr + id + ";";
            }
            idStr = idStr.substring(0, idStr.length() - 1);
            args[22] = idStr;
        }
        if (null != workflowNode.getSign_role() && workflowNode.getSign_role().length > 0) {
            String[] array = workflowNode.getSign_role();
            String idStr = "";
            for (String id : array) {
                idStr = idStr + id + ";";
            }
            idStr = idStr.substring(0, idStr.length() - 1);
            args[23] = idStr;
        }
        args[24] = workflowNode.getUserOrRole();
        args[25] = workflowNode.getTransfer();
        args[26] = workflowNode.isNodeJump() ? 1 : 0;
        args[27] = workflowNode.isSignStartMode() ? 1 : 0;
        args[28] = workflowNode.isStatisticalNode() ? 1 : 0;
        args[29] = workflowNode.getId();
        args[30] = workflowNode.getLinkid();
        this.jdbcTemplate.update(sql, args);
    }

    public void updateWorkflowLine(WorkFlowLine workflowLine, String tableName) {
        String sql = String.format("UPDATE %s SET  %s = ?, %s = ?,  %s = ?, %s = ?,  %s = ?, %s = ?,  %s = ?, %s = ?,  %s = ?, %s = ?,  %s = ?, %s = ?,  %s = ?, %s = ?,  %s = ?, %s = ?,  %s = ?, %s = ?,  %s = ?, %s = ? WHERE %s = ? and %s = ?", tableName, L_LINKID, L_CODE, L_TITLE, L_DESC, L_ATCIONID, L_FORMULA, L_ALLMDIM, L_ALLREPORT, L_UPDATETIME, L_MDIM, L_REPORT, L_CREATDATAVERSION, L_MSGCONTENT, L_SENDBY_PHONE, L_SENDBY_MAIL, L_SENDBY_PROTAL, L_MSGUSER, L_BNID, L_ANID, L_CONDITIONEXECUTE, L_ID, L_LINKID);
        Object[] args = new Object[22];
        args[0] = workflowLine.getLinkid();
        args[1] = workflowLine.getCode();
        args[2] = workflowLine.getTitle();
        args[3] = workflowLine.getDesc();
        args[4] = workflowLine.getActionid();
        args[5] = workflowLine.getFormula();
        args[6] = workflowLine.isAllmdim() ? 1 : 0;
        args[7] = workflowLine.isAllreport() ? 1 : 0;
        args[8] = new Timestamp(System.currentTimeMillis());
        args[9] = workflowLine.getMdim();
        args[10] = workflowLine.getReport();
        args[11] = workflowLine.isCreatDataVersion() ? 1 : 0;
        args[12] = workflowLine.getMsgcontent();
        args[13] = workflowLine.isSendby_phone() ? 1 : 0;
        args[14] = workflowLine.isSendby_mail() ? 1 : 0;
        args[15] = workflowLine.isSendby_protal() ? 1 : 0;
        String msguser = this.getMsgUser(workflowLine.getMsguser());
        args[16] = msguser;
        args[17] = workflowLine.getBeforeNodeID();
        args[18] = workflowLine.getAfterNodeID();
        String conditionExecute = workflowLine.getConditionExecute();
        if (null == conditionExecute) {
            conditionExecute = "DefaultConditionalExecute";
        }
        args[19] = conditionExecute;
        args[20] = workflowLine.getId();
        args[21] = workflowLine.getLinkid();
        this.jdbcTemplate.update(sql, args);
    }

    public void updateWorkflowParticipant(WorkFlowParticipant workflowParticipant, String tableName) {
        String sql = String.format("UPDATE %s SET  %s = ?, %s = ?,  %s = ?, %s = ?,  %s = ?, %s = ?, %s = ?,  %s = ?  WHERE %s = ? and %s = ?", tableName, P_LINKID, P_NODEID, P_STRATEGYID, P_UPDATETIME, P_ROLEIDS, P_USERIDS, P_PARAM, P_DESC, P_ID, P_LINKID);
        Object[] args = new Object[10];
        args[0] = workflowParticipant.getLinkid();
        args[1] = workflowParticipant.getNodeid();
        args[2] = workflowParticipant.getStrategyid();
        args[3] = new Timestamp(System.currentTimeMillis());
        String rolesValue = this.arrayToString(workflowParticipant.getRoleIds());
        String usersValue = this.arrayToString(workflowParticipant.getUserIds());
        args[4] = rolesValue;
        args[5] = usersValue;
        args[6] = workflowParticipant.getParam();
        args[7] = workflowParticipant.getDescription();
        args[8] = workflowParticipant.getId();
        args[9] = workflowParticipant.getLinkid();
        this.jdbcTemplate.update(sql, args);
    }

    public void updateWorkflowAction(WorkFlowAction workflowAction, String tableName) {
        String sql = String.format("UPDATE %s SET  %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?,%s = ?  WHERE %s = ? and %s = ?", tableName, A_LINKID, A_NODEID, A_ACTIONID, A_UPDATETIME, A_EXSET, A_STATE_NAME, A_DESC, A_TITLE, A_IMAGE, A_STATE_CODE, A_CODE, A_ID, A_LINKID);
        Object[] args = new Object[]{workflowAction.getLinkid(), workflowAction.getNodeid(), workflowAction.getActionid(), new Timestamp(System.currentTimeMillis()), workflowAction.getExset(), workflowAction.getStateName(), workflowAction.getActionDesc(), workflowAction.getActionTitle(), workflowAction.getImagePath(), workflowAction.getStateCode(), workflowAction.getActionCode(), workflowAction.getId(), workflowAction.getLinkid()};
        this.jdbcTemplate.update(sql, args);
    }

    public void deleteWorkflowNode(List<String> nodeIds, String linkId, String tableName) throws JQException {
        String sql = String.format("delete from %s where %s = ? and %s = ?", tableName, N_ID, N_LINKID);
        List<Object[]> batchArgs = this.getBatchArgs(nodeIds, linkId);
        try {
            this.jdbcTemplate.batchUpdate(sql.toString(), batchArgs);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)WorkflowException.ERROR_DELETE);
        }
    }

    public void deleteWorkflowLine(List<String> lineIds, String linkId, String tableName) throws JQException {
        String sql = String.format("delete from %s where %s = ? and %s = ?", tableName, L_ID, L_LINKID);
        List<Object[]> batchArgs = this.getBatchArgs(lineIds, linkId);
        try {
            this.jdbcTemplate.batchUpdate(sql.toString(), batchArgs);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)WorkflowException.ERROR_DELETE);
        }
    }

    public void deleteWorkflowLine(String lineId, String linkId, String tableName) throws JQException {
        String sql = String.format("delete from %s where %s = ? and %s = ?", tableName, L_ID, L_LINKID);
        try {
            this.jdbcTemplate.batchUpdate(new String[]{sql.toString(), lineId, linkId});
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)WorkflowException.ERROR_DELETE);
        }
    }

    public void deleteWorkflowParticipant(List<String> partiIds, String linkId, String tableName) throws JQException {
        String sql = String.format("delete from %s where %s = ? and %s = ?", tableName, P_ID, P_LINKID);
        List<Object[]> batchArgs = this.getBatchArgs(partiIds, linkId);
        try {
            this.jdbcTemplate.batchUpdate(sql.toString(), batchArgs);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)WorkflowException.ERROR_DELETE);
        }
    }

    public void deleteWorkflowAction(List<String> actionIds, String linkId, String tableName) throws JQException {
        String sql = String.format("delete from %s where %s = ? and %s = ?", tableName, A_ID, A_LINKID);
        List<Object[]> batchArgs = this.getBatchArgs(actionIds, linkId);
        try {
            this.jdbcTemplate.batchUpdate(sql.toString(), batchArgs);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)WorkflowException.ERROR_DELETE);
        }
    }

    private List<Object[]> getBatchArgs(List<String> formKeys, String linkId) {
        ArrayList<Object[]> batchArgs = new ArrayList<Object[]>();
        for (String formKey : formKeys) {
            String[] params = new String[]{formKey, linkId};
            batchArgs.add(params);
        }
        return batchArgs;
    }

    public void deleteWorkflowById(String id, int state, String tableName) throws JQException {
        String sql = String.format("delete from %s where %s = ? and %s = ?", tableName, F_ID, F_STATE);
        Object[] args = new Object[]{id, state};
        try {
            this.jdbcTemplate.update(sql.toString(), args);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)WorkflowException.ERROR_DELETE);
        }
    }

    public WorkFlowGroup getWorkFlowGroupByID(String groupID, String tableName) {
        String sql = String.format("SELECT %S,%S,%S,%S,%S FROM %S WHERE %S = ? ", G_ID, G_TITLE, G_ORDER, G_DESC, G_UPDATETIME, tableName, G_ID);
        Optional first = this.jdbcTemplate.query(sql, (rs, row) -> ENTITY_WORKFLOWGROUP.apply(rs), new Object[]{groupID}).stream().findFirst();
        return first.orElse(null);
    }

    public List<WorkFlowGroup> getAllWorkFlowGroup(String tableName) {
        String sql = String.format("SELECT %s,%s,%s,%s,%s FROM %S ", G_ID, G_TITLE, G_ORDER, G_DESC, G_UPDATETIME, tableName);
        Object[] objects = new String[]{};
        return this.jdbcTemplate.query(sql, objects, (rs, row) -> ENTITY_WORKFLOWGROUP.apply(rs));
    }

    public WorkFlowGroup getWorkFlowGroupByTitle(String title, String tableName) {
        String sql = String.format("SELECT %S,%S,%S,%S,%S FROM %S WHERE %S = ? ", G_ID, G_TITLE, G_ORDER, G_DESC, G_UPDATETIME, tableName, G_TITLE);
        Optional first = this.jdbcTemplate.query(sql, (rs, row) -> ENTITY_WORKFLOWGROUP.apply(rs), new Object[]{title}).stream().findFirst();
        return first.orElse(null);
    }

    public List<WorkFlowDefine> getWorkFlowDefineByGroupID(String groupID, String tableName) {
        String sql = String.format("SELECT %s, %s, %s, %s, %s,%s, %s, %s, %s, %s,%s, %s, %s, %s, %s, %s, %s FROM %S where %s = ?", F_ID, F_STATE, F_TITLE, F_CODE, F_ORDER, F_GROUPID, F_UPDATETIME, F_DESC, F_DATAID, F_AUTOSTART, F_SUBFLOW, F_FLOWOBJID, F_LINKID, F_XML, F_CUSTOM, F_SENDMAIL, F_TASK, tableName, F_GROUPID);
        Object[] objects = new String[]{groupID};
        return this.jdbcTemplate.query(sql, objects, (rs, row) -> ENTITY_WORKFLOWDEFINE.apply(rs));
    }

    public List<WorkFlowDefine> getAllWorkFlowDefineNoParentid(String tableName) {
        String sql = String.format("SELECT %s, %s, %s, %s, %s,%s, %s, %s, %s, %s,%s, %s, %s, %s, %s,%s,%s FROM %S ", F_ID, F_STATE, F_TITLE, F_CODE, F_ORDER, F_GROUPID, F_UPDATETIME, F_DESC, F_DATAID, F_AUTOSTART, F_SUBFLOW, F_FLOWOBJID, F_LINKID, F_XML, F_CUSTOM, F_SENDMAIL, F_TASK, tableName);
        Object[] objects = new String[]{};
        return this.jdbcTemplate.query(sql, objects, (rs, row) -> ENTITY_WORKFLOWDEFINE.apply(rs));
    }

    public List<WorkFlowDefine> searchDefineByinput(String input, String tableName) {
        String sql = String.format("SELECT %s, %s, %s, %s, %s,%s, %s, %s, %s, %s,%s, %s, %s, %s, %s, %s, %s FROM %S where %s like ? or %s like ?", F_ID, F_STATE, F_TITLE, F_CODE, F_ORDER, F_GROUPID, F_UPDATETIME, F_DESC, F_DATAID, F_AUTOSTART, F_SUBFLOW, F_FLOWOBJID, F_LINKID, F_XML, F_CUSTOM, F_SENDMAIL, F_TASK, tableName, F_TITLE, F_CODE);
        Object[] objects = new String[]{"%" + input + "%", "%" + input + "%"};
        return this.jdbcTemplate.query(sql, objects, (rs, row) -> ENTITY_WORKFLOWDEFINE.apply(rs));
    }

    public boolean groupHasChildern(String groupID, String tableName) {
        String sql = String.format("SELECT count(*) FROM %s  WHERE G_ID = ?", tableName, F_GROUPID);
        Object[] params = new Object[]{groupID};
        int count = (Integer)this.jdbcTemplate.queryForObject(sql, params, Integer.class);
        return count > 0;
    }

    public WorkFlowDefine getWorkFlowDefineByID(String defineID, int state, String tableName) {
        String sql = String.format("SELECT %s, %s, %s, %s, %s,%s, %s, %s, %s, %s,%s, %s, %s, %s, %s, %s, %s FROM %S WHERE %S = ? and %s = ?", F_ID, F_STATE, F_TITLE, F_CODE, F_ORDER, F_GROUPID, F_UPDATETIME, F_DESC, F_DATAID, F_AUTOSTART, F_SUBFLOW, F_FLOWOBJID, F_LINKID, F_XML, F_CUSTOM, F_SENDMAIL, F_TASK, tableName, F_ID, F_STATE);
        Optional first = this.jdbcTemplate.query(sql, (rs, row) -> ENTITY_WORKFLOWDEFINE.apply(rs), new Object[]{defineID, state}).stream().findFirst();
        return first.orElse(null);
    }

    public WorkFlowDefine getWorkFlowDefineByLinkID(String linkid, String tableName) {
        String sql = String.format("SELECT %s, %s, %s, %s, %s,%s, %s, %s, %s, %s,%s, %s, %s, %s, %s, %s, %s FROM %S WHERE %S = ? ", F_ID, F_STATE, F_TITLE, F_CODE, F_ORDER, F_GROUPID, F_UPDATETIME, F_DESC, F_DATAID, F_AUTOSTART, F_SUBFLOW, F_FLOWOBJID, F_LINKID, F_XML, F_CUSTOM, F_SENDMAIL, F_TASK, tableName, F_LINKID);
        Optional first = this.jdbcTemplate.query(sql, (rs, row) -> ENTITY_WORKFLOWDEFINE.apply(rs), new Object[]{linkid}).stream().findFirst();
        return first.orElse(null);
    }

    public WorkFlowNodeSet getWorkFlowNodeSetByID(String nodesetid, String linkId, String tableName) {
        String sql = String.format("SELECT %s, %s, %s, %s, %s,%s, %s, %s, %s, %s,%s, %s, %s, %s, %s,%s, %s, %s, %s ,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s FROM %S WHERE %S = ? and %S = ? ", N_ID, N_LINKID, N_CODE, N_TITLE, N_DESC, N_UPDATETIME, N_FRONTARRIVE, N_BACKARRIVE, N_WRITABLE, N_GETBACK, N_APPOINTABLE, N_STARTAPPOINT, N_APPOINTRESOURCE, N_APPOINTRENAME, N_APPOINTUSERRANGE, N_ACTIONRENAME_PASS, N_ACTIONRENAME_REJECT, N_PARTIS, N_ACTIONS, N_DEADLINE, N_SIGNNODE, N_SPECIALTAG, N_COUNTERSIGN_COUNT, N_SIGN_USER, N_SIGN_ROLE, N_USERORROLE, N_TRANSFER, N_NODE_JUMP, N_SIGN_START_MODE, N_STATISTICAL_NODE, tableName, N_ID, N_LINKID);
        Optional first = this.jdbcTemplate.query(sql, (rs, row) -> ENTITY_WORKFLOWNODESET.apply(rs), new Object[]{nodesetid, linkId}).stream().findFirst();
        return first.orElse(null);
    }

    public List<WorkFlowNodeSet> getWorkFlowNodeSetsByLinkID(String linkid, String tableName) {
        String sql = String.format("SELECT %s, %s, %s, %s, %s,%s, %s, %s, %s, %s,%s, %s, %s, %s, %s,%s, %s, %s, %s, %s, %s, %s,%s,%s,%s,%s,%s,%s,%s,%s FROM %S where %s = ? ", N_ID, N_LINKID, N_CODE, N_TITLE, N_DESC, N_UPDATETIME, N_FRONTARRIVE, N_BACKARRIVE, N_WRITABLE, N_GETBACK, N_APPOINTABLE, N_STARTAPPOINT, N_APPOINTRESOURCE, N_APPOINTRENAME, N_APPOINTUSERRANGE, N_ACTIONRENAME_PASS, N_ACTIONRENAME_REJECT, N_PARTIS, N_ACTIONS, N_DEADLINE, N_SIGNNODE, N_SPECIALTAG, N_COUNTERSIGN_COUNT, N_SIGN_USER, N_SIGN_ROLE, N_USERORROLE, N_TRANSFER, N_NODE_JUMP, N_SIGN_START_MODE, N_STATISTICAL_NODE, tableName, N_LINKID);
        return this.jdbcTemplate.query(sql, (rs, row) -> ENTITY_WORKFLOWNODESET.apply(rs), new Object[]{linkid});
    }

    public List<WorkFlowLine> getWorkFlowLinesByLinkID(String linkid, String tableName) {
        String sql = String.format("SELECT %s, %s, %s, %s, %s,%s, %s, %s, %s, %s,%s, %s, %s, %s, %s,%s, %s, %s, %s, %s,%s FROM %S where %s = ? ", L_ID, L_LINKID, L_CODE, L_TITLE, L_DESC, L_ATCIONID, L_FORMULA, L_ALLMDIM, L_ALLREPORT, L_UPDATETIME, L_MDIM, L_REPORT, L_CREATDATAVERSION, L_MSGCONTENT, L_SENDBY_PHONE, L_SENDBY_MAIL, L_SENDBY_PROTAL, L_MSGUSER, L_BNID, L_ANID, L_CONDITIONEXECUTE, tableName, L_LINKID);
        Object[] objects = new String[]{linkid};
        return this.jdbcTemplate.query(sql, objects, (rs, row) -> ENTITY_WORKFLOWLINE.apply(rs));
    }

    public WorkFlowLine getWorkFlowLineByID(String id, String linkId, String tableName) {
        String sql = String.format("SELECT %s, %s, %s, %s, %s,%s, %s, %s, %s, %s,%s, %s, %s, %s, %s,%s, %s, %s, %s, %s,%s FROM %S where %s = ? and %s = ?", L_ID, L_LINKID, L_CODE, L_TITLE, L_DESC, L_ATCIONID, L_FORMULA, L_ALLMDIM, L_ALLREPORT, L_UPDATETIME, L_MDIM, L_REPORT, L_CREATDATAVERSION, L_MSGCONTENT, L_SENDBY_PHONE, L_SENDBY_MAIL, L_SENDBY_PROTAL, L_MSGUSER, L_BNID, L_ANID, L_CONDITIONEXECUTE, tableName, L_ID, L_LINKID);
        Optional first = this.jdbcTemplate.query(sql, (rs, row) -> ENTITY_WORKFLOWLINE.apply(rs), new Object[]{id, linkId}).stream().findFirst();
        return first.orElse(null);
    }

    public List<WorkFlowAction> getWorkFlowActionsByLinkID(String linkid, String tableName) {
        String sql = String.format("SELECT %s, %s, %s, %s, %s,%s, %s, %s, %s, %s,%s,%s FROM %S where %s = ? ", A_ID, A_LINKID, A_NODEID, A_ACTIONID, A_UPDATETIME, A_EXSET, A_STATE_NAME, A_DESC, A_TITLE, A_IMAGE, A_STATE_CODE, A_CODE, tableName, A_LINKID);
        Object[] objects = new String[]{linkid};
        return this.jdbcTemplate.query(sql, objects, (rs, row) -> ENTITY_WORKFLOWACTION.apply(rs));
    }

    public List<WorkFlowParticipant> getWorkFlowParticipantsByLinkID(String linkid, String tableName) {
        String sql = String.format("SELECT %s, %s, %s, %s, %s,%s, %s, %s,  %s FROM %S where %s = ? ", P_ID, P_LINKID, P_NODEID, P_STRATEGYID, P_UPDATETIME, P_ROLEIDS, P_USERIDS, P_PARAM, P_DESC, tableName, P_LINKID);
        Object[] objects = new String[]{linkid};
        return this.jdbcTemplate.query(sql, objects, (rs, row) -> ENTITY_WORKFLOWPARTICIPANT.apply(rs));
    }

    public String getWorkFlowDefineXmlByID(String defineID, int state, String tableName) {
        StringBuffer xmlStr = new StringBuffer();
        String sql = String.format("SELECT %s, %s, %s, %s, %s,%s, %s, %s, %s, %s,%s, %s, %s, %s, %s, %s, %s FROM %S where %s = ? and %s = ?", F_ID, F_STATE, F_TITLE, F_CODE, F_ORDER, F_GROUPID, F_UPDATETIME, F_DESC, F_DATAID, F_AUTOSTART, F_SUBFLOW, F_FLOWOBJID, F_LINKID, F_XML, F_CUSTOM, F_SENDMAIL, F_TASK, tableName, F_ID, F_STATE);
        Optional first = this.jdbcTemplate.query(sql, (rs, row) -> ENTITY_WORKFLOWDEFINE.apply(rs), new Object[]{defineID, state}).stream().findFirst();
        if (first.isPresent()) {
            try {
                String xml = ((WorkFlowDefine)first.get()).getXml();
                xmlStr.append(xml);
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return xmlStr.toString();
    }

    public void delWorkFlowGroupByID(String groupid, String tableName) throws JQException {
        String sql = String.format("delete from %s where %s = ?", tableName, G_ID);
        Object[] params = new Object[]{groupid};
        try {
            this.jdbcTemplate.update(sql.toString(), params);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)WorkflowException.ERROR_DELETE);
        }
    }

    public void delWorkFlowNodeSetByID(String nodeid, String linkId, String tableName) throws JQException {
        String sql = String.format("delete from %s where %s = ? and %s = ?", tableName, N_ID, N_LINKID);
        Object[] params = new Object[]{nodeid, linkId};
        try {
            this.jdbcTemplate.update(sql.toString(), params);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)WorkflowException.ERROR_DELETE);
        }
        this.delWorkFlowActionByNodeid(nodeid, tableName);
        this.delWorkFlowParticpantByNodeid(nodeid, tableName);
    }

    public String delWorkFlowNodeSetByIDList(List<String> nodeids, String linkId, String tableName) throws JQException {
        this.deleteWorkflowNode(nodeids, linkId, tableName);
        this.delWorkFlowActionByNodeidList(nodeids, linkId, tableName);
        this.delWorkFlowParticpantByNodeidList(nodeids, linkId, tableName);
        return "";
    }

    public void delWorkFlowActionByNodeid(String nodeid, String tableName) throws JQException {
        String sql = String.format("delete from %s where %s = ?", tableName, A_NODEID);
        Object[] params = new Object[]{nodeid};
        try {
            this.jdbcTemplate.update(sql.toString(), params);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)WorkflowException.ERROR_DELETE);
        }
    }

    public void delWorkFlowActionByNodeidList(List<String> nodeids, String linkId, String tableName) throws JQException {
        String sql = String.format("delete from %s where %s = ?", tableName, A_NODEID);
        List<Object[]> batchArgs = this.getBatchArgs(nodeids, linkId);
        try {
            this.jdbcTemplate.batchUpdate(sql.toString(), batchArgs);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)WorkflowException.ERROR_DELETE);
        }
    }

    public void delWorkFlowParticpantByNodeid(String nodeid, String tableName) throws JQException {
        String sql = String.format("delete from %s where %s = ?", tableName, P_NODEID);
        Object[] params = new Object[]{nodeid};
        try {
            this.jdbcTemplate.update(sql.toString(), params);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)WorkflowException.ERROR_DELETE);
        }
    }

    public void delWorkFlowParticpantByNodeidList(List<String> nodeid, String linkId, String tableName) throws JQException {
        String sql = String.format("delete from %s where %s = ?", tableName, P_NODEID);
        List<Object[]> batchArgs = this.getBatchArgs(nodeid, linkId);
        try {
            this.jdbcTemplate.batchUpdate(sql.toString(), batchArgs);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)WorkflowException.ERROR_DELETE);
        }
    }

    public String delWorkFlowDefineByID(String defineid, int state, String tableName) throws JQException {
        List<String> linkidList = this.getLinkidByDefineID(defineid, state, tableName);
        String linkid = linkidList.get(0);
        String sql = String.format("delete from %s where %s = ? and %s = ?", tableName, F_ID, F_STATE);
        Object[] params = new Object[]{defineid, state};
        try {
            this.jdbcTemplate.update(sql.toString(), params);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)WorkflowException.ERROR_DELETE);
        }
        this.delWorkFlowNodeSetByLinkid(linkid, tableName);
        this.delWorkFlowLinkByLinkid(linkid, tableName);
        this.delWorkFlowActionByLinkid(linkid, tableName);
        this.delWorkFlowParticpantByLinkid(linkid, tableName);
        return "";
    }

    public void delWorkFlowNodeSetByLinkid(String linkid, String tableName) throws JQException {
        String sql = String.format("delete from %s where %s = ?", tableName, N_LINKID);
        Object[] params = new Object[]{linkid};
        try {
            this.jdbcTemplate.update(sql.toString(), params);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)WorkflowException.ERROR_DELETE);
        }
    }

    public void delWorkFlowLinkByLinkid(String linkid, String tableName) throws JQException {
        String sql = String.format("delete from %s where %s = ?", tableName, L_LINKID);
        Object[] params = new Object[]{linkid};
        try {
            this.jdbcTemplate.update(sql.toString(), params);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)WorkflowException.ERROR_DELETE);
        }
    }

    public void delWorkFlowActionByLinkid(String linkid, String tableName) throws JQException {
        String sql = String.format("delete from %s where %s = ? ", tableName, A_LINKID);
        Object[] params = new Object[]{linkid};
        try {
            this.jdbcTemplate.update(sql.toString(), params);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)WorkflowException.ERROR_DELETE);
        }
    }

    public void delWorkFlowParticpantByLinkid(String linkid, String tableName) throws JQException {
        String sql = String.format("delete from %s where %s = ?", tableName, P_LINKID);
        Object[] params = new Object[]{linkid};
        try {
            this.jdbcTemplate.update(sql.toString(), params);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)WorkflowException.ERROR_DELETE);
        }
    }

    public List<String> getLinkidByDefineID(String defineid, int state, String tableName) {
        String sql = String.format("SELECT %s FROM %S where %s = ? and %s = ?", F_LINKID, tableName, F_ID, F_STATE);
        Object[] objects = new Object[]{defineid, state};
        return this.jdbcTemplate.query(sql, objects, (rs, row) -> rs.getString(1));
    }

    public WorkFlowDefine releaseWorkFlowDefine(WorkFlowDefine define, String tableName) {
        String sql = String.format("UPDATE %s SET %s = ? WHERE %s = ? and %s = ?", tableName, F_STATE, F_ID, F_STATE);
        Object[] args = new Object[]{1, define.getId(), 0};
        this.jdbcTemplate.update(sql, args);
        return this.getWorkFlowDefineByID(define.getId(), 1, tableName);
    }

    public Object updateWorkFlowGroup(WorkFlowGroup group, String tableName) {
        String sql = String.format("UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ? ", tableName, G_TITLE, G_ORDER, G_DESC, G_UPDATETIME, G_ID);
        Object[] args = new Object[]{group.getTitle(), group.getOrder(), group.getDesc(), new Timestamp(System.currentTimeMillis()), group.getId()};
        this.jdbcTemplate.update(sql, args);
        return this.getWorkFlowGroupByID(group.getId(), tableName);
    }

    public void updateWorkFlowDefineTitle(String id, int state, String title, String tableName) {
        String sql = String.format("UPDATE %s SET %s = ?, %s = ? WHERE %s = ? AND %s = ?", tableName, F_TITLE, F_UPDATETIME, F_ID, F_STATE);
        Object[] args = new Object[]{title, new Timestamp(System.currentTimeMillis()), id, state};
        this.jdbcTemplate.update(sql, args);
    }

    public List<String> getAllDefineTitle(String tableName) {
        String sql = String.format("SELECT %s FROM %S order by F_TITLE", F_TITLE, tableName);
        Object[] objects = new Object[]{};
        return this.jdbcTemplate.query(sql, objects, (rs, row) -> rs.getString(1));
    }

    public List<WorkFlowDefine> getWorkFlowDefineByTitle(String title, String tableName) {
        String sql = String.format("SELECT %s, %s, %s, %s, %s,%s, %s, %s, %s, %s,%s, %s, %s, %s, %s,%s, %s FROM %S where %s = ? ", F_ID, F_STATE, F_TITLE, F_CODE, F_ORDER, F_GROUPID, F_UPDATETIME, F_DESC, F_DATAID, F_AUTOSTART, F_SUBFLOW, F_FLOWOBJID, F_LINKID, F_XML, F_CUSTOM, F_SENDMAIL, F_TASK, tableName, F_TITLE);
        Object[] objects = new Object[]{title};
        return this.jdbcTemplate.query(sql, objects, (rs, row) -> ENTITY_WORKFLOWDEFINE.apply(rs));
    }

    public List<WorkFlowDefine> getWorkFlowDefineByTitleAndTask(String title, String taskKey, String tableName) {
        String sql = String.format("SELECT %s, %s, %s, %s, %s,%s, %s, %s, %s, %s,%s, %s, %s, %s, %s,%s, %s FROM %S where %s = ? and %s = ? ", F_ID, F_STATE, F_TITLE, F_CODE, F_ORDER, F_GROUPID, F_UPDATETIME, F_DESC, F_DATAID, F_AUTOSTART, F_SUBFLOW, F_FLOWOBJID, F_LINKID, F_XML, F_CUSTOM, F_SENDMAIL, F_TASK, tableName, F_TITLE, F_TASK);
        Object[] objects = new Object[]{title, taskKey};
        return this.jdbcTemplate.query(sql, objects, (rs, row) -> ENTITY_WORKFLOWDEFINE.apply(rs));
    }

    public List<WorkFlowDefine> getWorkFlowDefineByCode(String code, String tableName) {
        String sql = String.format("SELECT %s, %s, %s, %s, %s,%s, %s, %s, %s, %s,%s, %s, %s, %s, %s,%s, %s FROM %S where %s = ? ", F_ID, F_STATE, F_TITLE, F_CODE, F_ORDER, F_GROUPID, F_UPDATETIME, F_DESC, F_DATAID, F_AUTOSTART, F_SUBFLOW, F_FLOWOBJID, F_LINKID, F_XML, F_CUSTOM, F_SENDMAIL, F_TASK, tableName, F_CODE);
        Object[] objects = new Object[]{code};
        return this.jdbcTemplate.query(sql, objects, (rs, row) -> ENTITY_WORKFLOWDEFINE.apply(rs));
    }

    public List<WorkFlowDefine> getAllWorkFlowDefineByState(int state, String tableName) {
        String sql = String.format("SELECT %s, %s, %s, %s, %s,%s, %s, %s, %s, %s,%s, %s, %s, %s, %s,%s, %s FROM %S where %s = ? order by F_ORDER", F_ID, F_STATE, F_TITLE, F_CODE, F_ORDER, F_GROUPID, F_UPDATETIME, F_DESC, F_DATAID, F_AUTOSTART, F_SUBFLOW, F_FLOWOBJID, F_LINKID, F_XML, F_CUSTOM, F_SENDMAIL, F_TASK, tableName, F_STATE);
        Object[] objects = new Object[]{state};
        return this.jdbcTemplate.query(sql, objects, (rs, row) -> ENTITY_WORKFLOWDEFINE.apply(rs));
    }

    public List<WorkFlowParticipant> getWorkFlowParticipantsByID(String[] idarray, String linkId, String tableName) {
        String sub = "";
        for (String id : idarray) {
            sub = sub + "'" + id + "',";
        }
        sub = sub.substring(0, sub.length() - 1);
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT P_ID, P_LINKID,P_NODEID, P_STRATEGYID, P_UPDATETIME, P_ROLEIDS, P_USERIDS, P_PARAM, P_DESC ").append(" FROM ").append(tableName);
        if (StringUtils.isNotEmpty((String)sub)) {
            sql.append(" where ").append(" P_ID in (").append(sub).append(")");
            sql.append(" and ").append(" P_LINKID ").append("=").append("'").append(linkId).append("'");
        }
        return this.jdbcTemplate.query(sql.toString(), (rs, row) -> ENTITY_WORKFLOWPARTICIPANT.apply(rs));
    }

    public List<WorkFlowParticipant> getWorkFlowParticipantByNodeID(String nodeid, String linkId, String tableName) {
        String sql = String.format("SELECT %s, %s, %s, %s, %s,%s, %s, %s,  %s FROM %S where %s = ? and  %s = ? ", P_ID, P_LINKID, P_NODEID, P_STRATEGYID, P_UPDATETIME, P_ROLEIDS, P_USERIDS, P_PARAM, P_DESC, tableName, P_NODEID, P_LINKID);
        Object[] objects = new String[]{nodeid, linkId};
        return this.jdbcTemplate.query(sql, objects, (rs, row) -> ENTITY_WORKFLOWPARTICIPANT.apply(rs));
    }

    public List<WorkFlowLine> getWorkFlowLinesByPreTask(String preTaskCode, String linkId, String tableName) {
        String sql = String.format("SELECT %s, %s, %s, %s, %s,%s, %s, %s, %s, %s,%s, %s, %s, %s, %s,%s, %s, %s, %s, %s,%s FROM %S where %s = ? and %s = ? ", L_ID, L_LINKID, L_CODE, L_TITLE, L_DESC, L_ATCIONID, L_FORMULA, L_ALLMDIM, L_ALLREPORT, L_UPDATETIME, L_MDIM, L_REPORT, L_CREATDATAVERSION, L_MSGCONTENT, L_SENDBY_PHONE, L_SENDBY_MAIL, L_SENDBY_PROTAL, L_MSGUSER, L_BNID, L_ANID, L_CONDITIONEXECUTE, tableName, L_BNID, L_LINKID);
        Object[] objects = new String[]{preTaskCode, linkId};
        return this.jdbcTemplate.query(sql, objects, (rs, row) -> ENTITY_WORKFLOWLINE.apply(rs));
    }

    public List<WorkFlowLine> getWorkFlowLinesByPreTask(String preTaskCode, String actionId, String linkId, String tableName) {
        String sql = String.format("SELECT %s, %s, %s, %s, %s,%s, %s, %s, %s, %s,%s, %s, %s, %s, %s,%s, %s, %s, %s, %s,%s FROM %S where %s = ? and %s = ? and %s = ?", L_ID, L_LINKID, L_CODE, L_TITLE, L_DESC, L_ATCIONID, L_FORMULA, L_ALLMDIM, L_ALLREPORT, L_UPDATETIME, L_MDIM, L_REPORT, L_CREATDATAVERSION, L_MSGCONTENT, L_SENDBY_PHONE, L_SENDBY_MAIL, L_SENDBY_PROTAL, L_MSGUSER, L_BNID, L_ANID, L_CONDITIONEXECUTE, tableName, L_BNID, L_ATCIONID, L_LINKID);
        Object[] objects = new String[]{preTaskCode, actionId, linkId};
        return this.jdbcTemplate.query(sql, objects, (rs, row) -> ENTITY_WORKFLOWLINE.apply(rs));
    }

    public List<WorkFlowLine> getWorkFlowLineByEndNode(String endNode, String linkId, String tableName) {
        Object[] objects = new String[2];
        String sql = null;
        if (endNode.contains("end")) {
            sql = String.format("SELECT %s, %s, %s, %s, %s,%s, %s, %s, %s, %s,%s, %s, %s, %s, %s,%s, %s, %s, %s, %s,%s FROM %S where %s like ? and %s = ?", L_ID, L_LINKID, L_CODE, L_TITLE, L_DESC, L_ATCIONID, L_FORMULA, L_ALLMDIM, L_ALLREPORT, L_UPDATETIME, L_MDIM, L_REPORT, L_CREATDATAVERSION, L_MSGCONTENT, L_SENDBY_PHONE, L_SENDBY_MAIL, L_SENDBY_PROTAL, L_MSGUSER, L_BNID, L_ANID, L_CONDITIONEXECUTE, tableName, L_ANID, L_LINKID);
            objects[0] = "EndEvent%";
        } else {
            sql = String.format("SELECT %s, %s, %s, %s, %s,%s, %s, %s, %s, %s,%s, %s, %s, %s, %s,%s, %s, %s, %s, %s,%s FROM %S where %s = ? and %s = ? ", L_ID, L_LINKID, L_CODE, L_TITLE, L_DESC, L_ATCIONID, L_FORMULA, L_ALLMDIM, L_ALLREPORT, L_UPDATETIME, L_MDIM, L_REPORT, L_CREATDATAVERSION, L_MSGCONTENT, L_SENDBY_PHONE, L_SENDBY_MAIL, L_SENDBY_PROTAL, L_MSGUSER, L_BNID, L_ANID, L_CONDITIONEXECUTE, tableName, L_ANID, L_LINKID);
            objects[0] = endNode;
        }
        objects[1] = linkId;
        return this.jdbcTemplate.query(sql, objects, (rs, row) -> ENTITY_WORKFLOWLINE.apply(rs));
    }

    public List<WorkFlowLine> getWorkFlowLineByEndNode(List<String> bNode, String endNode, String linkId, String tableName) {
        String sub = "";
        if (bNode != null && bNode.size() > 0) {
            for (String id : bNode) {
                sub = sub + "'" + id + "',";
            }
            sub = sub.substring(0, sub.length() - 1);
        }
        StringBuffer sb = new StringBuffer();
        sb.append("select L_ID, L_LINKID, L_CODE, L_TITLE, L_DESC, L_ATCIONID, L_FORMULA, L_ALLMDIM, L_ALLREPORT, L_UPDATETIME,\r\n\t\t\t\tL_MDIM, L_REPORT, L_CREATDATAVERSION, L_MSGCONTENT, L_SENDBY_PHONE, L_SENDBY_MAIL, L_SENDBY_PROTAL,\r\n\t\t\t\tL_MSGUSER, L_BNID, L_ANID, L_CONDITIONEXECUTE ").append(" from ").append(tableName).append(" where ").append(" L_ANID='").append(endNode).append("'");
        if (StringUtils.isNotEmpty((String)sub)) {
            sb.append(" and ").append(" L_BNID in (").append(sub).append(")");
        } else {
            sb.append(" and ").append(" L_BNID like 'StartEvent%'");
        }
        sb.append(" and ").append(L_LINKID).append(" = ").append("'").append(linkId).append("'");
        return this.jdbcTemplate.query(sb.toString(), (rs, row) -> ENTITY_WORKFLOWLINE.apply(rs));
    }

    public List<WorkFlowAction> getWorkFlowActionByNodeID(String nodeid, String linkId, String tableName) {
        String sql = String.format("SELECT %s, %s, %s, %s, %s,%s, %s, %s, %s, %s,%s,%s FROM %S WHERE %S = ? and %S = ? ", A_ID, A_LINKID, A_NODEID, A_ACTIONID, A_UPDATETIME, A_EXSET, A_STATE_NAME, A_DESC, A_TITLE, A_IMAGE, A_STATE_CODE, A_CODE, tableName, A_NODEID, A_LINKID);
        Object[] objects = new String[]{nodeid, linkId};
        return this.jdbcTemplate.query(sql, objects, (rs, row) -> ENTITY_WORKFLOWACTION.apply(rs));
    }

    public WorkFlowAction getWorkflowActionByNodeId(String nodeId, String tableName) {
        String sql = String.format("SELECT %s, %s, %s, %s, %s,%s, %s, %s, %s, %s,%s,%s FROM %S WHERE %S = ? ", A_ID, A_LINKID, A_NODEID, A_ACTIONID, A_UPDATETIME, A_EXSET, A_STATE_NAME, A_DESC, A_TITLE, A_IMAGE, A_STATE_CODE, A_CODE, tableName, A_NODEID);
        Optional first = this.jdbcTemplate.query(sql, (rs, row) -> ENTITY_WORKFLOWACTION.apply(rs), new Object[]{nodeId}).stream().findFirst();
        return first.orElse(null);
    }

    public WorkFlowAction getWorkflowActionByCodeAndActionId(String nodeId, String actionId, String linkId, String tableName) {
        String sql = String.format("SELECT %s, %s, %s, %s, %s,%s, %s, %s, %s, %s,%s,%s FROM %S WHERE %S = ? and %s = ? and %s = ? ", A_ID, A_LINKID, A_NODEID, A_ACTIONID, A_UPDATETIME, A_EXSET, A_STATE_NAME, A_DESC, A_TITLE, A_IMAGE, A_STATE_CODE, A_CODE, tableName, A_NODEID, A_CODE, A_LINKID);
        Optional first = this.jdbcTemplate.query(sql, (rs, row) -> ENTITY_WORKFLOWACTION.apply(rs), new Object[]{nodeId, actionId, linkId}).stream().findFirst();
        return first.orElse(null);
    }

    public WorkFlowAction getWorkflowActionById(String id, String linkId, String tableName) {
        String sql = String.format("SELECT %s, %s, %s, %s, %s,%s, %s, %s, %s, %s,%s,%s FROM %S WHERE %S = ? and %S = ?  ", A_ID, A_LINKID, A_NODEID, A_ACTIONID, A_UPDATETIME, A_EXSET, A_STATE_NAME, A_DESC, A_TITLE, A_IMAGE, A_STATE_CODE, A_CODE, tableName, A_ID, A_LINKID);
        Optional first = this.jdbcTemplate.query(sql, (rs, row) -> ENTITY_WORKFLOWACTION.apply(rs), new Object[]{id, linkId}).stream().findFirst();
        return first.orElse(null);
    }

    public WorkFlowAction getWorkflowActionByCode(String code, String linkId, String tableName) {
        String sql = String.format("SELECT %s, %s, %s, %s, %s,%s, %s, %s, %s, %s,%s,%s FROM %S WHERE %S = ? and %S = ?  ", A_ID, A_LINKID, A_NODEID, A_ACTIONID, A_UPDATETIME, A_EXSET, A_STATE_NAME, A_DESC, A_TITLE, A_IMAGE, A_STATE_CODE, A_CODE, tableName, A_CODE, A_LINKID);
        Optional first = this.jdbcTemplate.query(sql, (rs, row) -> ENTITY_WORKFLOWACTION.apply(rs), new Object[]{code, linkId}).stream().findFirst();
        return first.orElse(null);
    }

    public WorkFlowAction getWorkflowActionByCodeandLinkId(String actionCode, String linkId, String tableName) {
        String sql = String.format("SELECT %s, %s, %s, %s, %s,%s, %s, %s, %s, %s,%s,%s FROM %S WHERE %S = ? and %s = ? ", A_ID, A_LINKID, A_NODEID, A_ACTIONID, A_UPDATETIME, A_EXSET, A_STATE_NAME, A_DESC, A_TITLE, A_IMAGE, A_STATE_CODE, A_CODE, tableName, A_CODE, A_LINKID);
        Optional first = this.jdbcTemplate.query(sql, (rs, row) -> ENTITY_WORKFLOWACTION.apply(rs), new Object[]{actionCode, linkId}).stream().findFirst();
        return first.orElse(null);
    }

    private static Map<String, Object> getMsgUser(String str) {
        HashMap<String, Object> userMap = new HashMap<String, Object>();
        JSONArray strategise = new JSONArray(str);
        if (!strategise.isEmpty()) {
            for (int i = 0; i < strategise.length(); ++i) {
                JSONObject obj = strategise.getJSONObject(i);
                String strategyid = obj.getString("strategy");
                if (!obj.has("value")) continue;
                Object valueObj = obj.get("value");
                userMap.put(strategyid, valueObj.toString());
            }
        }
        return userMap;
    }

    private String getMsgUser(Map<String, Object> map) {
        JSONArray strategise = new JSONArray();
        if (map != null) {
            for (String key : map.keySet()) {
                JSONObject obj = new JSONObject();
                obj.put("strategy", (Object)key);
                Object value = map.get(key);
                obj.put("value", value);
                strategise.put((Object)obj);
            }
        }
        return strategise.toString();
    }

    private static String[] byteArrayToStringArray(byte[] byteArray) {
        if (null != byteArray) {
            try {
                String str = new String(byteArray, "UTF-8");
                if (str.length() > 0) {
                    String[] array = str.split(";");
                    return array;
                }
            }
            catch (UnsupportedEncodingException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return null;
    }

    private String arrayToString(String[] array) {
        String value = "";
        if (array != null && array.length > 0) {
            for (String a : array) {
                value = value + a + ";";
            }
            if (value.length() > 0) {
                value = value.substring(0, value.length() - 1);
            }
        }
        return value;
    }

    public List<WorkFlowAction> getAllWorkFlowActions(String tableName) {
        String sql = String.format("SELECT %s, %s, %s, %s, %s,%s, %s, %s, %s, %s,%s,%s FROM %S ", A_ID, A_LINKID, A_NODEID, A_ACTIONID, A_UPDATETIME, A_EXSET, A_STATE_NAME, A_DESC, A_TITLE, A_IMAGE, A_STATE_CODE, A_CODE, tableName);
        return this.jdbcTemplate.query(sql, (rs, row) -> ENTITY_WORKFLOWACTION.apply(rs));
    }

    public WorkFlowLine getWorkFlowLines(String beforeNodeId, String afterNodeId, String actionId, String linkId, String tableName) {
        String sql = String.format("SELECT %s, %s, %s, %s, %s,%s, %s, %s, %s, %s,%s, %s, %s, %s, %s,%s, %s, %s, %s, %s,%s FROM %S where %s = ? and %s = ? and %s = ? and %s = ?", L_ID, L_LINKID, L_CODE, L_TITLE, L_DESC, L_ATCIONID, L_FORMULA, L_ALLMDIM, L_ALLREPORT, L_UPDATETIME, L_MDIM, L_REPORT, L_CREATDATAVERSION, L_MSGCONTENT, L_SENDBY_PHONE, L_SENDBY_MAIL, L_SENDBY_PROTAL, L_MSGUSER, L_BNID, L_ANID, L_CONDITIONEXECUTE, tableName, L_BNID, L_ANID, L_ATCIONID, L_LINKID);
        Optional first = this.jdbcTemplate.query(sql, (rs, row) -> ENTITY_WORKFLOWLINE.apply(rs), new Object[]{beforeNodeId, afterNodeId, actionId, linkId}).stream().findFirst();
        return first.orElse(null);
    }

    public List<WorkFlowLine> getWorkFlowLinesByPreTask(Set<String> preTaskCode, String linkId, String tableName) {
        String sub = "";
        if (preTaskCode != null && preTaskCode.size() > 0) {
            for (String nodeid : preTaskCode) {
                sub = sub + "'" + nodeid + "',";
            }
            sub = sub.substring(0, sub.length() - 1);
        }
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT L_ID, L_LINKID, L_CODE, L_TITLE, L_DESC, L_ATCIONID, L_FORMULA, L_ALLMDIM, L_ALLREPORT, L_UPDATETIME,\n\t\t\t\tL_MDIM, L_REPORT, L_CREATDATAVERSION, L_MSGCONTENT, L_SENDBY_PHONE, L_SENDBY_MAIL, L_SENDBY_PROTAL,\n\t\t\t\tL_MSGUSER, L_BNID, L_ANID, L_CONDITIONEXECUTE ").append(" from ").append(tableName);
        if (StringUtils.isNotEmpty((String)sub)) {
            sb.append(" where ").append(" L_BNID in(").append(sub).append(")");
            sb.append(" and ").append(" L_LINKID = ").append("'").append(linkId).append("'");
        }
        return this.jdbcTemplate.query(sb.toString(), (rs, row) -> ENTITY_WORKFLOWLINE.apply(rs));
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

    public List<WorkFlowDefine> getWorkFlowDefineByTaskKey(String taskKey, String tableName) {
        String sql = String.format("SELECT %s, %s, %s, %s, %s,%s, %s, %s, %s, %s,%s, %s, %s, %s, %s, %s, %s FROM %S where %s = ?", F_ID, F_STATE, F_TITLE, F_CODE, F_ORDER, F_GROUPID, F_UPDATETIME, F_DESC, F_DATAID, F_AUTOSTART, F_SUBFLOW, F_FLOWOBJID, F_LINKID, F_XML, F_CUSTOM, F_SENDMAIL, F_TASK, tableName, F_TASK);
        Object[] objects = new String[]{taskKey};
        return this.jdbcTemplate.query(sql, objects, (rs, row) -> ENTITY_WORKFLOWDEFINE.apply(rs));
    }
}

