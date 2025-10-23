/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.namedparam.MapSqlParameterSource
 *  org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
 *  org.springframework.jdbc.core.namedparam.SqlParameterSource
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.graph.rwlock.executer;

import com.jiuqi.nr.graph.exception.RWLockExecuterException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW, rollbackFor={Exception.class})
public class DatabaseLock {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseLock.class);
    public static final String DB_TABLE_NAME = "NR_PARAM_LOCK";
    public static final String DB_FIELD_LOCK_ID = "LOCK_ID";
    public static final String DB_FIELD_LOCK_TITLE = "LOCK_TITLE";
    public static final String DB_FIELD_IS_EXCLUSIVE = "IS_EXCLUSIVE";
    public static final String DB_FIELD_SHARE_LOCK_TIME = "SHARE_LOCK_TIME";
    public static final String DB_FIELD_SHARE_UNLOCK_TIME = "SHARE_UNLOCK_TIME";
    public static final String DB_FIELD_EXCLUSIVE_LOCK_TIME = "EXCLUSIVE_LOCK_TIME";
    public static final String DB_FIELD_EXCLUSIVE_UNLOCK_TIME = "EXCLUSIVE_UNLOCK_TIME";
    public static final String DB_FIELD_LOCK_COUNT = "LOCK_COUNT";
    public static final String DB_FIELD_THREAD_ID = "THREAD_ID";
    public static final String DB_FIELD_REENTRANT_COUNT = "REENTRANT_COUNT";
    public static final String DB_FIELD_HEARTBEAT_TIME = "HEARTBEAT_TIME";
    private static final String QUERY_IS_EXISTED = String.format("SELECT %s FROM %s WHERE %s=?", "LOCK_ID", "NR_PARAM_LOCK", "LOCK_ID");
    private static final String INSERT = String.format("INSERT INTO %s (%s, %s, %s, %s) VALUES (?,?,0,0)", "NR_PARAM_LOCK", "LOCK_ID", "LOCK_TITLE", "IS_EXCLUSIVE", "LOCK_COUNT");
    private static final String SHARE_LOCK = String.format("UPDATE %s SET %s=%s+1, %s=?, %s=?, %s=0, %s=? WHERE %s=? AND %s=0", "NR_PARAM_LOCK", "LOCK_COUNT", "LOCK_COUNT", "SHARE_LOCK_TIME", "HEARTBEAT_TIME", "REENTRANT_COUNT", "THREAD_ID", "LOCK_ID", "IS_EXCLUSIVE");
    private static final String SHARE_UNLOCK = String.format("UPDATE %s SET %s=%s-1, %s=? WHERE %s=? AND %s=0 AND %s>0", "NR_PARAM_LOCK", "LOCK_COUNT", "LOCK_COUNT", "SHARE_UNLOCK_TIME", "LOCK_ID", "IS_EXCLUSIVE", "LOCK_COUNT");
    private static final String EXCLUSIVE_LOCK = String.format("UPDATE %s SET %s=1, %s=1, %s=?, %s=?, %s=0, %s=? WHERE %s=? AND %s=0", "NR_PARAM_LOCK", "LOCK_COUNT", "IS_EXCLUSIVE", "EXCLUSIVE_LOCK_TIME", "HEARTBEAT_TIME", "REENTRANT_COUNT", "THREAD_ID", "LOCK_ID", "LOCK_COUNT");
    private static final String EXCLUSIVE_UNLOCK = String.format("UPDATE %s SET %s=0, %s=0, %s=? WHERE %s=? AND %s=1", "NR_PARAM_LOCK", "LOCK_COUNT", "IS_EXCLUSIVE", "EXCLUSIVE_UNLOCK_TIME", "LOCK_ID", "IS_EXCLUSIVE");
    private static final String REENTRANT_LOCK = String.format("UPDATE %s SET %s=%s+1 WHERE %s=? AND %s=? AND %s=1", "NR_PARAM_LOCK", "REENTRANT_COUNT", "REENTRANT_COUNT", "LOCK_ID", "THREAD_ID", "IS_EXCLUSIVE");
    private static final String REENTRANT_UNLOCK = String.format("UPDATE %s SET %s=%s-1 WHERE %s=? AND %s=? AND %s>0", "NR_PARAM_LOCK", "REENTRANT_COUNT", "REENTRANT_COUNT", "LOCK_ID", "THREAD_ID", "REENTRANT_COUNT");
    public static final String RESET_LOCK = String.format("UPDATE %s SET %s=0, %s=0, %s=0, %s=null WHERE %s=?", "NR_PARAM_LOCK", "LOCK_COUNT", "IS_EXCLUSIVE", "REENTRANT_COUNT", "THREAD_ID", "LOCK_ID");
    public static final String LOCK_HEARTBEAT = String.format("UPDATE %s SET %s=:time WHERE %s IN (:ids)", "NR_PARAM_LOCK", "HEARTBEAT_TIME", "LOCK_ID");
    public static final String DEADLOCK_RESET = String.format("UPDATE %s SET %s=0, %s=0, %s=0, %s=null WHERE %s<?", "NR_PARAM_LOCK", "LOCK_COUNT", "IS_EXCLUSIVE", "REENTRANT_COUNT", "THREAD_ID", "HEARTBEAT_TIME");
    public static final String LOCK_COUNT = String.format("SELECT %s FROM %s WHERE %s>0 and %s=?", "LOCK_COUNT", "NR_PARAM_LOCK", "LOCK_COUNT", "LOCK_ID");
    private static final long MAX_TIMEOUT = 10L;
    private static final TimeUnit MAX_TIMEOUT_UNIT = TimeUnit.MINUTES;
    private static final long TRY_INTERVALS_MILLIS = 500L;
    private static final Map<String, Integer> ALL_LOCK_IDS = new ConcurrentHashMap<String, Integer>();

    public void init(String id, String title) {
        block3: {
            List existedLocks = this.jdbcTemplate.queryForList(QUERY_IS_EXISTED, String.class, new Object[]{id});
            if (existedLocks.isEmpty()) {
                try {
                    LOGGER.debug("DB Lock \u521d\u59cb\u5316, LockId: {}, LockTitle: {}.", (Object)id, (Object)title);
                    this.jdbcTemplate.update(INSERT, new Object[]{id, title});
                }
                catch (Exception e) {
                    existedLocks = this.jdbcTemplate.queryForList(QUERY_IS_EXISTED, String.class, new Object[]{id});
                    if (!existedLocks.isEmpty()) break block3;
                    throw new RWLockExecuterException(e);
                }
            }
        }
    }

    private static void sleep() {
        try {
            Thread.sleep(500L);
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LOGGER.error("DB Lock \u7ebf\u7a0b\u7b49\u5f85{}\u6beb\u79d2\u5931\u8d25", (Object)500L);
        }
    }

    public void shareLock(String id, String title) {
        if (!this.tryShareLock(id, title, 10L, MAX_TIMEOUT_UNIT)) {
            throw new RWLockExecuterException("\u83b7\u53d6\u5171\u4eab\u9501\u7b49\u5f85\u8d85\u65f6");
        }
    }

    public boolean tryShareLock(String id, String title, long timeout, TimeUnit unit) {
        try {
            return this.tryLock(id, title, false, timeout, unit);
        }
        catch (Exception e) {
            DatabaseLock.sleep();
            try {
                return this.tryLock(id, title, false, timeout, unit);
            }
            catch (Exception error) {
                LOGGER.error("DB Lock \u83b7\u53d6\u5171\u4eab\u9501\u5931\u8d25, LockId: {}, LockTitle: {}", id, title, error);
                throw new RWLockExecuterException(error);
            }
        }
    }

    public void shareUnlock(String id, String title) {
        try {
            this.unlock(id, title, false);
        }
        catch (Exception e) {
            DatabaseLock.sleep();
            try {
                this.unlock(id, title, false);
            }
            catch (Exception error) {
                LOGGER.error("DB Lock \u91ca\u653e\u5171\u4eab\u9501\u5931\u8d25, LockId: {}, LockTitle: {}", id, title, error);
                throw new RWLockExecuterException(error);
            }
        }
    }

    public void exclusiveLock(String id, String title) {
        if (!this.tryExclusiveLock(id, title, 10L, MAX_TIMEOUT_UNIT)) {
            throw new RWLockExecuterException("\u83b7\u53d6\u4e92\u65a5\u9501\u7b49\u5f85\u8d85\u65f6");
        }
    }

    public boolean tryExclusiveLock(String id, String title, long timeout, TimeUnit unit) {
        try {
            return this.tryLock(id, title, true, timeout, unit);
        }
        catch (Exception e) {
            DatabaseLock.sleep();
            try {
                return this.tryLock(id, title, true, timeout, unit);
            }
            catch (Exception error) {
                LOGGER.error("DB Lock \u83b7\u53d6\u4e92\u65a5\u9501\u5931\u8d25, LockId: {}, LockTitle: {}", id, title, error);
                throw new RWLockExecuterException(error);
            }
        }
    }

    public void exclusiveUnlock(String id, String title) {
        try {
            this.unlock(id, title, true);
        }
        catch (Exception e) {
            DatabaseLock.sleep();
            try {
                this.unlock(id, title, true);
            }
            catch (Exception error) {
                LOGGER.error("DB Lock \u91ca\u653e\u4e92\u65a5\u9501\u5931\u8d25, LockId: {}, LockTitle: {}", id, title, error);
                throw new RWLockExecuterException(error);
            }
        }
    }

    private boolean tryLock(String id, String title, boolean isExclusive, long timeout, TimeUnit unit) {
        LOGGER.debug("DB Lock \u5c1d\u8bd5\u52a0\u9501, LockId: {}, LockTitle: {}, LockType: {}.", id, title, isExclusive ? "\u4e92\u65a5\u9501" : "\u5171\u4eab\u9501");
        long nanosTimeout = unit.toNanos(timeout);
        long deadline = System.nanoTime() + nanosTimeout;
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        int update = this.jdbcTemplate.update(isExclusive ? EXCLUSIVE_LOCK : SHARE_LOCK, new Object[]{currentTime, currentTime, Thread.currentThread().getId(), id});
        if (update > 0) {
            ALL_LOCK_IDS.compute(id, (key, value) -> null == value ? 1 : value + 1);
            LOGGER.debug("DB Lock \u52a0\u9501\u6210\u529f, LockId: {}, LockTitle: {}, LockType: {}.", id, title, isExclusive ? "\u4e92\u65a5\u9501" : "\u5171\u4eab\u9501");
            return true;
        }
        update = this.jdbcTemplate.update(REENTRANT_LOCK, new Object[]{id, Thread.currentThread().getId()});
        if (update > 0) {
            LOGGER.debug("DB Lock \u52a0\u9501\u6210\u529f, LockId: {}, ThreadId: {}, LockTitle: {}, LockType: {}.", id, Thread.currentThread().getId(), title, "\u91cd\u5165\u9501");
            return true;
        }
        DatabaseLock.sleep();
        while (nanosTimeout > 0L) {
            update = this.jdbcTemplate.update(isExclusive ? EXCLUSIVE_LOCK : SHARE_LOCK, new Object[]{currentTime = new Timestamp(System.currentTimeMillis()), currentTime, Thread.currentThread().getId(), id});
            if (update > 0) {
                ALL_LOCK_IDS.compute(id, (key, value) -> null == value ? 1 : value + 1);
                LOGGER.debug("DB Lock \u52a0\u9501\u6210\u529f, LockId: {}, LockTitle: {}, LockType: {}.", id, title, isExclusive ? "\u4e92\u65a5\u9501" : "\u5171\u4eab\u9501");
                return true;
            }
            DatabaseLock.sleep();
            nanosTimeout = deadline - System.nanoTime();
        }
        LOGGER.debug("DB Lock \u52a0\u9501\u8d85\u65f6, LockId: {}, LockTitle: {}, LockType: {}.", id, title, isExclusive ? "\u4e92\u65a5\u9501" : "\u5171\u4eab\u9501");
        return false;
    }

    private void unlock(String id, String title, boolean isExclusive) {
        int update = this.jdbcTemplate.update(REENTRANT_UNLOCK, new Object[]{id, Thread.currentThread().getId()});
        if (update > 0) {
            LOGGER.debug("DB Lock \u89e3\u9501, LockId: {}, ThreadId: {}, LockTitle: {}, LockType: {}.", id, Thread.currentThread().getId(), title, "\u91cd\u5165\u9501");
            return;
        }
        ALL_LOCK_IDS.compute(id, (key, value) -> null == value || 0 == value ? 0 : value - 1);
        LOGGER.debug("DB Lock \u89e3\u9501, LockId: {}, LockTitle: {}, LockType: {}.", id, title, isExclusive ? "\u4e92\u65a5\u9501" : "\u5171\u4eab\u9501");
        this.jdbcTemplate.update(isExclusive ? EXCLUSIVE_UNLOCK : SHARE_UNLOCK, new Object[]{new Timestamp(System.currentTimeMillis()), id});
    }

    public void heartbeat() {
        List ids = ALL_LOCK_IDS.entrySet().stream().filter(e -> (Integer)e.getValue() > 0).map(Map.Entry::getKey).collect(Collectors.toList());
        if (ids.isEmpty()) {
            return;
        }
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("time", (Object)new Timestamp(System.currentTimeMillis()));
        parameterSource.addValue("ids", ids);
        this.namedParameterJdbcTemplate.update(LOCK_HEARTBEAT, (SqlParameterSource)parameterSource);
    }

    public void deadlockReset(long offsetTime) {
        this.jdbcTemplate.update(DEADLOCK_RESET, new Object[]{new Timestamp(System.currentTimeMillis() - offsetTime)});
    }

    @Transactional(readOnly=true)
    public boolean isLocked(String lockId) {
        return this.jdbcTemplate.queryForList(LOCK_COUNT, Integer.class, new Object[]{lockId}).stream().findAny().orElse(0) > 0;
    }

    public void reset(String lockId) {
        this.jdbcTemplate.update(RESET_LOCK, new Object[]{lockId});
    }

    public List<Lock> listLock() {
        String sql = String.format("SELECT %s, %s, %s FROM %s WHERE %s>0", DB_FIELD_LOCK_ID, DB_FIELD_LOCK_COUNT, DB_FIELD_IS_EXCLUSIVE, DB_TABLE_NAME, DB_FIELD_LOCK_COUNT);
        ArrayList<Lock> locks = new ArrayList<Lock>();
        this.jdbcTemplate.query(sql, rm -> {
            Lock lock = new Lock();
            lock.setId(rm.getString(DB_FIELD_LOCK_ID));
            lock.setCount(rm.getInt(DB_FIELD_LOCK_COUNT));
            lock.setExclusive(rm.getBoolean(DB_FIELD_IS_EXCLUSIVE));
            locks.add(lock);
        });
        return locks;
    }

    public static class Lock {
        private String id;
        private int count;
        private boolean isExclusive;

        protected void setId(String id) {
            this.id = id;
        }

        protected void setCount(int count) {
            this.count = count;
        }

        protected void setExclusive(boolean exclusive) {
            this.isExclusive = exclusive;
        }

        public String getId() {
            return this.id;
        }

        public int getCount() {
            return this.count;
        }

        public boolean isExclusive() {
            return this.isExclusive;
        }
    }
}

