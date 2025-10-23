/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.graph.rwlock.executer;

import com.jiuqi.nr.graph.IRWLockExecuter;
import com.jiuqi.nr.graph.exception.RWLockExecuterException;
import com.jiuqi.nr.graph.function.IRunnable;
import com.jiuqi.nr.graph.rwlock.executer.DatabaseLock;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseRWLockExecuter
implements IRWLockExecuter {
    private final String id;
    private final String title;
    private final DatabaseLock databaseLock;
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseRWLockExecuter.class);

    public DatabaseRWLockExecuter(String id, DatabaseLock databaseLock) {
        this.id = id;
        this.title = "\u56fe\u7f13\u5b58\u8bfb\u5199\u9501";
        this.databaseLock = databaseLock;
        this.databaseLock.init(this.id, this.title);
    }

    @Override
    public <R> R doRread(IRunnable<R> fun) {
        R result = null;
        this.databaseLock.shareLock(this.id, this.title);
        LOGGER.debug("\u83b7\u53d6\u8bfb\u9501\u6210\u529f");
        try {
            result = fun.run();
        }
        catch (Exception e) {
            throw new RWLockExecuterException(e);
        }
        finally {
            this.databaseLock.shareUnlock(this.id, this.title);
            LOGGER.debug("\u91ca\u653e\u8bfb\u9501");
        }
        return result;
    }

    @Override
    public <R> R doWrite(IRunnable<R> fun) {
        R result = null;
        this.databaseLock.exclusiveLock(this.id, this.title);
        LOGGER.debug("\u83b7\u53d6\u5199\u9501\u6210\u529f");
        try {
            result = fun.run();
        }
        catch (Exception e) {
            throw new RWLockExecuterException(e);
        }
        finally {
            this.databaseLock.exclusiveUnlock(this.id, this.title);
            LOGGER.debug("\u91ca\u653e\u5199\u9501");
        }
        return result;
    }

    @Override
    public <R> R tryRead(IRunnable<R> fun) {
        R result = null;
        boolean tryLock = this.databaseLock.tryShareLock(this.id, this.title, 60L, TimeUnit.SECONDS);
        if (tryLock) {
            LOGGER.debug("\u83b7\u53d6\u8bfb\u9501\u6210\u529f");
            try {
                result = fun.run();
            }
            catch (Exception e) {
                throw new RWLockExecuterException(e);
            }
            finally {
                this.databaseLock.shareUnlock(this.id, this.title);
                LOGGER.debug("\u91ca\u653e\u8bfb\u9501");
            }
        } else {
            throw new RWLockExecuterException("\u6b63\u5728\u53d1\u5e03\u53c2\u6570\uff0c\u8bf7\u7a0d\u540e\u518d\u8bd5\uff08\u9501ID\uff1a" + this.id + "\uff09");
        }
        return result;
    }

    @Override
    public <R> R tryWrite(IRunnable<R> fun) {
        R result = null;
        boolean tryLock = this.databaseLock.tryExclusiveLock(this.id, this.title, 30L, TimeUnit.SECONDS);
        if (tryLock) {
            LOGGER.debug("\u83b7\u53d6\u5199\u9501\u6210\u529f");
            try {
                result = fun.run();
            }
            catch (Exception e) {
                throw new RWLockExecuterException(e);
            }
            finally {
                this.databaseLock.exclusiveUnlock(this.id, this.title);
                LOGGER.debug("\u91ca\u653e\u5199\u9501");
            }
        } else {
            throw new RWLockExecuterException("\u6b63\u5728\u5237\u65b0\u7f13\u5b58\uff0c\u8bf7\u7a0d\u540e\u518d\u8bd5\uff08\u9501ID\uff1a" + this.id + "\uff09");
        }
        return result;
    }
}

