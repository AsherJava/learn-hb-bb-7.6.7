/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.database.metadata.LogicField
 *  com.jiuqi.np.dataengine.common.DataEngineUtil
 *  com.jiuqi.nr.common.db.DatabaseInstance
 *  com.jiuqi.nr.common.temptable.ITempTable
 *  com.jiuqi.nr.common.temptable.ITempTableManager
 *  com.jiuqi.nr.workflow2.engine.core.exception.ProcessRuntimeException
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
 *  org.springframework.jdbc.datasource.DataSourceUtils
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.runtime.repository;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.np.dataengine.common.DataEngineUtil;
import com.jiuqi.nr.common.db.DatabaseInstance;
import com.jiuqi.nr.common.temptable.ITempTable;
import com.jiuqi.nr.common.temptable.ITempTableManager;
import com.jiuqi.nr.workflow2.engine.core.exception.ProcessRuntimeException;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.model.QueryModel;
import com.jiuqi.nr.workflow2.engine.dflt.utils.LogUtils;
import java.io.Closeable;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ProcessInstanceLockManager {
    private static final String UNLOCKVALUE = "'-'";
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ITempTableManager tempTableManager;
    private ConcurrentHashMap<String, ProcessInstanceLock> locks = new ConcurrentHashMap();
    private Timer heartbeatTimer;
    private static final long HEARTBEATPERIOD = 30000L;
    private static final long HEARTBEATDELAY = 30000L;
    private static final short CLEANINTERVAL = 10;
    private static final int LOCKDEADLINE = 60000;
    private volatile short currentCleanInterval = (short)10;
    private static final String TABLENAME = "NR_WF_ISTLOCK";
    private static final String ID = "ID";
    private static final String LOCKTIME = "LOCKTIME";
    private static final String ACTIVETIME = "ACTIVETIME";
    private static final String INSTANCETABLENAME = "ISTTABLENAME";

    public void startup() {
        this.heartbeatTimer = new Timer("NR.WORKFLOW.INSTANCE.LOCK.HEARTBEATTIMER");
        this.heartbeatTimer.schedule(new TimerTask(){

            @Override
            public void run() {
                ProcessInstanceLockManager.this.heartbeats();
                ProcessInstanceLockManager.access$110(ProcessInstanceLockManager.this);
                if (ProcessInstanceLockManager.this.currentCleanInterval <= 0) {
                    ProcessInstanceLockManager.this.cleanLock();
                    ProcessInstanceLockManager.this.currentCleanInterval = (short)10;
                }
            }
        }, 30000L, 30000L);
    }

    public ProcessInstanceLock createLock(QueryModel.ProcessInstanceQueryModel istQueryModel, Collection<String> needLockIds) {
        ProcessInstanceLock lockObj = new ProcessInstanceLock(istQueryModel, needLockIds);
        return lockObj;
    }

    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public void lock(ProcessInstanceLock lock) {
        if (lock.locked) {
            throw new IllegalAccessError("\u9519\u8bef\u8c03\u7528\uff0c\u4e0d\u80fd\u91cd\u590d\u9501\u5b9a\u3002");
        }
        lock.lockTime = Calendar.getInstance();
        this.saveLock(lock);
        this.doLock(lock);
        this.locks.put(lock.getLockId(), lock);
        lock.locked = true;
    }

    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public void lockAll(ProcessInstanceLock lock, long timeoutMilis) throws TimeoutException {
        block4: {
            if (lock.locked) {
                throw new IllegalAccessError("\u9519\u8bef\u8c03\u7528\uff0c\u4e0d\u80fd\u91cd\u590d\u9501\u5b9a\u3002");
            }
            lock.lockTime = Calendar.getInstance();
            this.saveLock(lock);
            long sleepMilis = 1000L;
            long delayMilis = 0L;
            do {
                this.doLock(lock);
                if (lock.lockedCount >= lock.needLockIds.size()) break block4;
                try {
                    Thread.sleep(1000L);
                }
                catch (InterruptedException e) {
                    LogUtils.LOGGER.error("lock process instance error.", e);
                }
            } while ((delayMilis += 1000L) < timeoutMilis);
            throw new TimeoutException("lock process instance timeout: " + timeoutMilis);
        }
        this.locks.put(lock.getLockId(), lock);
        lock.locked = true;
    }

    public void unlock(ProcessInstanceLock lock) {
        if (lock.locked) {
            this.unlockInstance(lock);
            this.deleteLock(lock.lockId);
            lock.locked = false;
            lock.lockedCount = 0;
            lock.lockedInstanceIds = null;
            this.locks.remove(lock.getLockId());
        }
    }

    private void saveLock(ProcessInstanceLock lock) {
        String sql = "INSERT INTO NR_WF_ISTLOCK(ID, LOCKTIME, ACTIVETIME, ISTTABLENAME) VALUES(?,?,?,?)";
        this.jdbcTemplate.update("INSERT INTO NR_WF_ISTLOCK(ID, LOCKTIME, ACTIVETIME, ISTTABLENAME) VALUES(?,?,?,?)", new Object[]{lock.getLockId(), lock.lockTime, lock.lockTime, lock.istQueryModel.getTableName()});
    }

    private void doLock(ProcessInstanceLock lock) {
        int affected;
        HashSet needLockIds;
        block19: {
            needLockIds = new HashSet(lock.needLockIds);
            if (lock.getLockedInstanceIds() != null) {
                needLockIds.removeAll(lock.getLockedInstanceIds());
            }
            if (needLockIds.size() > DataEngineUtil.getMaxInSize((IDatabase)DatabaseInstance.getDatabase())) {
                try (ITempTable tempTable = this.tempTableManager.getOneKeyTempTable();){
                    ArrayList<Object[]> records = new ArrayList<Object[]>(needLockIds.size());
                    for (String id : needLockIds) {
                        records.add(new Object[]{id});
                    }
                    tempTable.insertRecords(records);
                    Object[] args = new Object[]{lock.lockId};
                    affected = this.jdbcTemplate.update(this.makeSQLForLockUsingJoin(lock.istQueryModel, tempTable), args);
                    break block19;
                }
                catch (Exception e) {
                    throw new ProcessRuntimeException("jiuqi.nr.default", "\u5f00\u542f\u6216\u5173\u95ed\u4e34\u65f6\u8868\u5931\u8d25\u3002", (Throwable)e);
                }
            }
            HashMap<String, Object> args = new HashMap<String, Object>(2);
            args.put(lock.istQueryModel.getLockColumn().getName(), lock.lockId);
            args.put(lock.istQueryModel.getIdColumn().getName(), needLockIds);
            NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(this.jdbcTemplate.getDataSource());
            affected = namedParameterJdbcTemplate.update(this.makeSQLForLockUsingIn(lock.istQueryModel), args);
        }
        Set lockedIds = affected >= needLockIds.size() ? needLockIds : (Set)this.jdbcTemplate.query(this.makeSQLForQueryLock(lock.istQueryModel), rs -> {
            HashSet<String> ids = new HashSet<String>();
            while (rs.next()) {
                ids.add(rs.getString(1));
            }
            return ids;
        }, new Object[]{lock.lockId});
        if (lock.lockedInstanceIds == null) {
            lock.lockedInstanceIds = new HashSet<String>(lockedIds);
        } else {
            lock.lockedInstanceIds.addAll(lockedIds);
        }
        lock.lockedCount += affected;
    }

    private void deleteLock(String lockId) {
        String sql = "DELETE FROM NR_WF_ISTLOCK WHERE ID=?";
        this.jdbcTemplate.update("DELETE FROM NR_WF_ISTLOCK WHERE ID=?", new Object[]{lockId});
    }

    private void unlockInstance(ProcessInstanceLock lock) {
        Object[] args = new Object[]{lock.lockId};
        this.jdbcTemplate.update(this.makeSQLForUnlock(lock.istQueryModel), args);
    }

    private void deleteLocks(Collection<String> lockIds) {
        String sql = "DELETE FROM NR_WF_ISTLOCK WHERE ID IN(:ID)";
        HashMap<String, Collection<String>> args = new HashMap<String, Collection<String>>(1);
        args.put(ID, lockIds);
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(this.jdbcTemplate.getDataSource());
        namedParameterJdbcTemplate.update("DELETE FROM NR_WF_ISTLOCK WHERE ID IN(:ID)", args);
    }

    private void heartbeats() {
        String sql = "UPDATE NR_WF_ISTLOCK SET ACTIVETIME=:ACTIVETIME WHERE ID IN(:ID)";
        Collection activeLockIds = this.locks.values().stream().filter(o -> o.locked).map(o -> o.getLockId()).collect(Collectors.toList());
        if (activeLockIds.isEmpty()) {
            return;
        }
        HashMap<String, Object> args = new HashMap<String, Object>(2);
        args.put(ACTIVETIME, Calendar.getInstance());
        args.put(ID, activeLockIds);
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(this.jdbcTemplate.getDataSource());
        namedParameterJdbcTemplate.update("UPDATE NR_WF_ISTLOCK SET ACTIVETIME=:ACTIVETIME WHERE ID IN(:ID)", args);
    }

    private void cleanLock() {
        String sql = "SELECT ID, ISTTABLENAME FROM NR_WF_ISTLOCK WHERE ACTIVETIME < ?";
        Calendar deadline = Calendar.getInstance();
        deadline.add(13, -60000);
        HashMap deadLocks = new HashMap();
        this.jdbcTemplate.query("SELECT ID, ISTTABLENAME FROM NR_WF_ISTLOCK WHERE ACTIVETIME < ?", rs -> {
            while (rs.next()) {
                String instanceTable = rs.getString(2);
                ArrayList<String> lockIdsInTable = (ArrayList<String>)deadLocks.get(instanceTable);
                if (lockIdsInTable == null) {
                    lockIdsInTable = new ArrayList<String>();
                    deadLocks.put(instanceTable, lockIdsInTable);
                }
                lockIdsInTable.add(rs.getString(1));
            }
            return null;
        }, new Object[]{deadline});
        for (Map.Entry entry : deadLocks.entrySet()) {
            if (this.tableIsExists((String)entry.getKey())) {
                this.unlockInstacne((String)entry.getKey(), (List)entry.getValue());
            }
            this.deleteLocks((Collection)entry.getValue());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean tableIsExists(String tableName) {
        Connection conn = null;
        try {
            conn = DataSourceUtils.getConnection((DataSource)this.jdbcTemplate.getDataSource());
            IDatabase db = DatabaseManager.getInstance().findDatabaseByConnection(conn);
            boolean bl = db.createMetadata(conn).getTableByName(tableName) != null;
            return bl;
        }
        catch (Exception e) {
            LogUtils.LOGGER.error("\u6e05\u7406\u6d41\u7a0b\u5b9e\u4f8b\u9501\u5931\u8d25\uff0c\u65e0\u6cd5\u5224\u5b9a\u6d41\u7a0b\u5b9e\u4f8b\u8868\u662f\u5426\u5b58\u5728\u3002", e);
            boolean bl = false;
            return bl;
        }
        finally {
            if (conn != null) {
                DataSourceUtils.releaseConnection((Connection)conn, (DataSource)this.jdbcTemplate.getDataSource());
            }
        }
    }

    private void unlockInstacne(String tableName, List<String> lockIds) {
        String sql = "UPDATE " + tableName + " SET " + "IST_LOCK" + " = " + UNLOCKVALUE + " WHERE " + "IST_LOCK" + " IN(:" + "IST_LOCK" + ")";
        HashMap<String, List<String>> args = new HashMap<String, List<String>>(1);
        args.put("IST_LOCK", lockIds);
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(this.jdbcTemplate.getDataSource());
        namedParameterJdbcTemplate.update(sql, args);
    }

    private String makeSQLForLockUsingIn(QueryModel.ProcessInstanceQueryModel istQueryModel) {
        StringBuilder sqlBuilder = new StringBuilder("UPDATE ").append(istQueryModel.getTableName()).append(" SET ").append(istQueryModel.getLockColumn().getName()).append("=:").append(istQueryModel.getLockColumn().getName()).append(" WHERE ").append(istQueryModel.getIdColumn().getName()).append(" IN(:").append(istQueryModel.getIdColumn().getName()).append(")").append(" AND ").append(istQueryModel.getLockColumn().getName()).append("=").append(UNLOCKVALUE);
        return sqlBuilder.toString();
    }

    private String makeSQLForLockUsingJoin(QueryModel.ProcessInstanceQueryModel istQueryModel, ITempTable tempTable) {
        String tempTableIdField = ((LogicField)tempTable.getMeta().getLogicFields().get(0)).getFieldName();
        StringBuilder sqlBuilder = new StringBuilder("UPDATE ").append(istQueryModel.getTableName()).append(" SET ").append(istQueryModel.getLockColumn().getName()).append("=? ").append(" WHERE ").append(istQueryModel.getIdColumn().getName()).append(" IN(SELECT ").append(tempTableIdField).append(" FROM ").append(tempTable.getTableName()).append(")").append(" AND ").append(istQueryModel.getLockColumn().getName()).append("=").append(UNLOCKVALUE);
        return sqlBuilder.toString();
    }

    private String makeSQLForQueryLock(QueryModel.ProcessInstanceQueryModel istQueryModel) {
        StringBuilder sqlBuilder = new StringBuilder("SELECT ").append(istQueryModel.getIdColumn().getName()).append(" FROM ").append(istQueryModel.getTableName()).append(" WHERE ").append(istQueryModel.getLockColumn().getName()).append("=?");
        return sqlBuilder.toString();
    }

    private String makeSQLForUnlock(QueryModel.ProcessInstanceQueryModel istQueryModel) {
        StringBuilder sqlBuilder = new StringBuilder("UPDATE ").append(istQueryModel.getTableName()).append(" SET ").append(istQueryModel.getLockColumn().getName()).append("=").append(UNLOCKVALUE).append(" WHERE ").append(istQueryModel.getLockColumn().getName()).append("=?");
        return sqlBuilder.toString();
    }

    static /* synthetic */ short access$110(ProcessInstanceLockManager x0) {
        short s = x0.currentCleanInterval;
        x0.currentCleanInterval = (short)(s - 1);
        return s;
    }

    public class ProcessInstanceLock
    implements Closeable {
        public static final String UNLOCK = "-";
        private final QueryModel.ProcessInstanceQueryModel istQueryModel;
        private final Set<String> needLockIds;
        private final String lockId = UUID.randomUUID().toString();
        private Calendar lockTime;
        boolean locked = false;
        int lockedCount;
        Set<String> lockedInstanceIds;

        ProcessInstanceLock(QueryModel.ProcessInstanceQueryModel istQueryModel, Collection<String> needLockIds) {
            if (needLockIds == null || needLockIds.isEmpty()) {
                throw new IllegalArgumentException("'needLockIds' must not be empty.");
            }
            this.istQueryModel = istQueryModel;
            this.needLockIds = new HashSet<String>(needLockIds);
        }

        public String getLockId() {
            return this.lockId;
        }

        public Collection<String> getLockedInstanceIds() {
            return this.lockedInstanceIds;
        }

        @Override
        public void close() {
            ProcessInstanceLockManager.this.unlock(this);
        }
    }
}

