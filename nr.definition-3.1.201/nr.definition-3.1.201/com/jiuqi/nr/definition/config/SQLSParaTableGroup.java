/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.TableKind
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.np.definition.facade.TableGroupDefine
 *  com.jiuqi.np.definition.internal.impl.DesignTableGroupDefineImpl
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.util.OrderGenerator
 */
package com.jiuqi.nr.definition.config;

import com.jiuqi.np.definition.common.TableKind;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.np.definition.facade.TableGroupDefine;
import com.jiuqi.np.definition.internal.impl.DesignTableGroupDefineImpl;
import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.nr.definition.common.NrTableGroupEnum;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.impl.DesignFormSchemeDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignTaskDefineImpl;
import com.jiuqi.util.OrderGenerator;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SQLSParaTableGroup
implements CustomClassExecutor {
    private static final Logger logger = LoggerFactory.getLogger(SQLSParaTableGroup.class);
    private static final String SQL_QUERY_TASK = "SELECT TK_KEY,TK_TITLE FROM NR_PARAM_TASK_DES";
    private static final String SQL_QUERY_TABLEGROUP = "SELECT TG_KEY,TG_PARENT_KEY FROM DES_SYS_TABLEGROUP";
    private static final String SQL_QUERY_CHILDRENTABLEGROUP = "SELECT TG_KEY,TG_CODE FROM DES_SYS_TABLEGROUP WHERE TG_PARENT_KEY = ?";
    private static final String SQL_QUERY_SCHEME = "SELECT FC_KEY,FC_TITLE,FC_CODE,FC_TASK_KEY FROM NR_PARAM_FORMSCHEME_DES WHERE FC_TASK_KEY = ?";
    private static final String SQL_INSERT_TABLEGROUP = "INSERT INTO DES_SYS_TABLEGROUP (TG_KEY,TG_TITLE,TG_CODE,TG_ORDER) VALUES(?,?,?,?)";
    private static final String SQL_INSERT_TABLEGROUPWITHPARENT = "INSERT INTO DES_SYS_TABLEGROUP (TG_KEY,TG_TITLE,TG_CODE,TG_PARENT_KEY,TG_ORDER) VALUES(?,?,?,?,?)";
    private static final String SQL_UPDATE_TABLEGROUPPARENTNULL = "UPDATE DES_SYS_TABLEGROUP SET TG_PARENT_KEY = null WHERE TG_KEY = ?";
    private static final String SQL_UPDATE_TABLEGROUPPARENTL = "UPDATE DES_SYS_TABLEGROUP SET TG_PARENT_KEY = ? WHERE TG_KEY = ?";
    private static final String SQL_UPDATE_TABLE = "UPDATE DES_SYS_TABLEDEFINE SET TD_GROUP = ? WHERE TD_GROUP=? AND TD_KIND = ?";
    private static final String SQL_QUERY_TABLEGROUPBYTYPE = "SELECT TG_KEY,TG_PARENT_KEY FROM DES_SYS_TABLEGROUP WHERE TG_PARENT_KEY = ? AND TG_CODE=?";
    private Connection connection = null;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void execute(DataSource dataSource) throws Exception {
        try {
            this.connection = dataSource.getConnection();
            List<TaskDefine> tasks = this.getAllTask();
            ArrayList oldTask = new ArrayList();
            tasks.forEach(task -> {
                try {
                    TableGroupDefine group = this.getTableGroup(task.getKey());
                    if (null == group) {
                        this.insertTableGroup((TaskDefine)task);
                    } else {
                        oldTask.add(task.getKey());
                        this.updateTableGroupParent(task.getKey());
                    }
                    List<TableGroupDefine> groups = this.getChildrenGroup(task.getKey());
                    HashSet<String> groupCodeSet = new HashSet<String>();
                    groups.forEach(childGroup -> groupCodeSet.add(childGroup.getCode()));
                    String systemGroup = null;
                    String enumGroup = null;
                    systemGroup = groupCodeSet.add(NrTableGroupEnum.SYSTEM.getCode()) ? this.insertTableGroup(task.getKey(), NrTableGroupEnum.SYSTEM) : this.queryTableGroupByType(task.getKey(), NrTableGroupEnum.SYSTEM);
                    this.moveOldTable(task.getKey(), systemGroup, NrTableGroupEnum.SYSTEM);
                    enumGroup = groupCodeSet.add(NrTableGroupEnum.ENUM.getCode()) ? this.insertTableGroup(task.getKey(), NrTableGroupEnum.ENUM) : this.queryTableGroupByType(task.getKey(), NrTableGroupEnum.ENUM);
                    this.moveOldTable(task.getKey(), enumGroup, NrTableGroupEnum.ENUM);
                    List<DesignFormSchemeDefine> schemes = this.getSchemeGroupByTask(task.getKey());
                    schemes.forEach(scheme -> {
                        try {
                            String schemeSystemGroup = null;
                            String schemeEnumGroup = null;
                            String schemeDataGroup = null;
                            TableGroupDefine schemeGroup = this.getTableGroup(scheme.getKey());
                            if (null == schemeGroup) {
                                this.insertTableGroup((FormSchemeDefine)scheme);
                            } else {
                                oldTask.add(scheme.getKey());
                                this.updateTableGroupParent(scheme.getKey(), task.getKey());
                            }
                            List<TableGroupDefine> schemeChildreGroups = this.getChildrenGroup(scheme.getKey());
                            HashSet<String> schemeChildreGroupCodeSet = new HashSet<String>();
                            schemeChildreGroups.forEach(childGroup -> schemeChildreGroupCodeSet.add(childGroup.getCode()));
                            schemeSystemGroup = schemeChildreGroupCodeSet.add(NrTableGroupEnum.SYSTEM.getCode()) ? this.insertTableGroup(scheme.getKey(), NrTableGroupEnum.SYSTEM) : this.queryTableGroupByType(task.getKey(), NrTableGroupEnum.SYSTEM);
                            this.moveOldTable(scheme.getKey(), schemeSystemGroup, NrTableGroupEnum.SYSTEM);
                            schemeEnumGroup = schemeChildreGroupCodeSet.add(NrTableGroupEnum.ENUM.getCode()) ? this.insertTableGroup(scheme.getKey(), NrTableGroupEnum.ENUM) : this.queryTableGroupByType(task.getKey(), NrTableGroupEnum.ENUM);
                            this.moveOldTable(scheme.getKey(), schemeEnumGroup, NrTableGroupEnum.ENUM);
                            schemeDataGroup = schemeChildreGroupCodeSet.add(NrTableGroupEnum.DATA.getCode()) ? this.insertTableGroup(scheme.getKey(), NrTableGroupEnum.DATA) : this.queryTableGroupByType(task.getKey(), NrTableGroupEnum.DATA);
                            this.moveOldTable(scheme.getKey(), schemeDataGroup, NrTableGroupEnum.DATA);
                        }
                        catch (SQLException e) {
                            logger.error(e.getMessage(), e);
                        }
                    });
                }
                catch (SQLException e) {
                    logger.error(e.getMessage(), e);
                }
            });
        }
        finally {
            this.closeConnection(this.connection);
        }
    }

    private String queryTableGroupByType(String key, NrTableGroupEnum groupType) throws SQLException {
        String result = null;
        try (PreparedStatement statement = this.connection.prepareStatement(SQL_QUERY_TABLEGROUPBYTYPE);){
            statement.setString(1, key);
            statement.setString(2, groupType.getCode());
            try (ResultSet rs = statement.executeQuery();){
                if (rs.next()) {
                    result = rs.getString(1);
                }
            }
        }
        return result;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void moveOldTable(String oldParent, String newparent, NrTableGroupEnum system) throws SQLException {
        if (newparent == null) {
            return;
        }
        PreparedStatement statement = null;
        try {
            statement = this.connection.prepareStatement(SQL_UPDATE_TABLE);
            statement.setString(1, newparent);
            statement.setString(2, oldParent);
            switch (system) {
                case ENUM: {
                    statement.setInt(3, TableKind.TABLE_KIND_DICTIONARY.getValue());
                    break;
                }
                case DATA: {
                    statement.setInt(3, TableKind.TABLE_KIND_BIZDATA.getValue());
                    break;
                }
                case SYSTEM: {
                    statement.setInt(3, TableKind.TABLE_KIND_ENTITY.getValue());
                    break;
                }
            }
            statement.execute();
        }
        finally {
            this.closeStatment(statement);
        }
    }

    private List<DesignFormSchemeDefine> getSchemeGroupByTask(String key) throws SQLException {
        ArrayList<DesignFormSchemeDefine> schemes = new ArrayList<DesignFormSchemeDefine>();
        try (PreparedStatement statement = this.connection.prepareStatement(SQL_QUERY_SCHEME);){
            statement.setString(1, key);
            try (ResultSet rs = statement.executeQuery();){
                while (rs.next()) {
                    int i = 1;
                    DesignFormSchemeDefineImpl scheme = new DesignFormSchemeDefineImpl();
                    scheme.setKey(rs.getString(i++));
                    scheme.setTitle(rs.getString(i++));
                    scheme.setFormSchemeCode(rs.getString(i++));
                    scheme.setTaskKey(rs.getString(i++));
                    schemes.add(scheme);
                }
            }
        }
        return schemes;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private String insertTableGroup(String key, NrTableGroupEnum groupType) throws SQLException {
        PreparedStatement statement = null;
        String result = UUIDUtils.getKey();
        try {
            statement = this.connection.prepareStatement(SQL_INSERT_TABLEGROUPWITHPARENT);
            statement.setString(1, result);
            statement.setString(2, groupType.getTitle());
            statement.setString(3, groupType.getCode());
            statement.setString(4, key);
            statement.setString(5, OrderGenerator.newOrder());
            statement.execute();
        }
        finally {
            this.closeStatment(statement);
        }
        return result;
    }

    private List<TableGroupDefine> getChildrenGroup(String key) throws SQLException {
        ArrayList<TableGroupDefine> groups = new ArrayList<TableGroupDefine>();
        try (PreparedStatement statement = this.connection.prepareStatement(SQL_QUERY_CHILDRENTABLEGROUP);){
            statement.setString(1, key);
            try (ResultSet rs = statement.executeQuery();){
                while (rs.next()) {
                    int i = 1;
                    DesignTableGroupDefineImpl group = new DesignTableGroupDefineImpl();
                    group.setKey(rs.getString(i++));
                    group.setCode(rs.getString(i++));
                    groups.add((TableGroupDefine)group);
                }
            }
        }
        return groups;
    }

    private void updateTableGroupParent(String key) throws SQLException {
        PreparedStatement statement = null;
        try {
            statement = this.connection.prepareStatement(SQL_UPDATE_TABLEGROUPPARENTNULL);
            statement.setString(1, key);
            statement.execute();
        }
        finally {
            this.closeStatment(statement);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void updateTableGroupParent(String key, String parentKey) throws SQLException {
        PreparedStatement statement = null;
        try {
            statement = this.connection.prepareStatement(SQL_UPDATE_TABLEGROUPPARENTL);
            statement.setString(1, parentKey);
            statement.setString(2, key);
            statement.execute();
        }
        finally {
            this.closeStatment(statement);
        }
    }

    private void insertTableGroup(TaskDefine task) throws SQLException {
        PreparedStatement statement = null;
        try {
            statement = this.connection.prepareStatement(SQL_INSERT_TABLEGROUP);
            statement.setString(1, task.getKey());
            statement.setString(2, task.getTitle());
            statement.setString(3, task.getTaskCode());
            statement.setString(4, OrderGenerator.newOrder());
            statement.execute();
        }
        finally {
            this.closeStatment(statement);
        }
    }

    private void insertTableGroup(FormSchemeDefine scheme) throws SQLException {
        PreparedStatement statement = null;
        try {
            statement = this.connection.prepareStatement(SQL_INSERT_TABLEGROUPWITHPARENT);
            statement.setString(1, scheme.getKey());
            statement.setString(2, scheme.getTitle());
            statement.setString(3, scheme.getFormSchemeCode());
            statement.setString(4, scheme.getTaskKey());
            statement.setString(5, OrderGenerator.newOrder());
            statement.execute();
        }
        finally {
            this.closeStatment(statement);
        }
    }

    private List<TaskDefine> getAllTask() throws SQLException {
        ArrayList<TaskDefine> tasks = new ArrayList<TaskDefine>();
        try (PreparedStatement statement = this.connection.prepareStatement(SQL_QUERY_TASK);
             ResultSet rs = statement.executeQuery();){
            while (rs.next()) {
                int i = 1;
                DesignTaskDefineImpl task = new DesignTaskDefineImpl();
                task.setKey(rs.getString(i++));
                task.setTitle(rs.getString(i++));
                tasks.add(task);
            }
        }
        return tasks;
    }

    private TableGroupDefine getTableGroup(String key) throws SQLException {
        DesignTableGroupDefineImpl group = null;
        try (PreparedStatement statement = this.connection.prepareStatement("SELECT TG_KEY,TG_PARENT_KEY FROM DES_SYS_TABLEGROUP where tg_key = ?");){
            statement.setString(1, key);
            try (ResultSet rs = statement.executeQuery();){
                if (rs.next()) {
                    int i = 1;
                    group = new DesignTableGroupDefineImpl();
                    group.setKey(rs.getString(i++));
                    group.setTitle(rs.getString(i++));
                }
            }
        }
        return group;
    }

    private void closeConnection(Connection connection) throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    private void closeStatment(PreparedStatement statement) throws SQLException {
        if (statement != null) {
            statement.close();
        }
    }

    private void closeResultSet(ResultSet rs) throws SQLException {
        if (rs != null) {
            rs.close();
        }
    }
}

