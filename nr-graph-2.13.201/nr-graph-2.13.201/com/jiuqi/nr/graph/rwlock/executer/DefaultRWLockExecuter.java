/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.graph.rwlock.executer;

import com.jiuqi.nr.graph.IRWLockExecuter;
import com.jiuqi.nr.graph.exception.RWLockExecuterException;
import com.jiuqi.nr.graph.function.IRunnable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultRWLockExecuter
implements IRWLockExecuter {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultRWLockExecuter.class);
    private final ReadWriteLock rwlock;

    public DefaultRWLockExecuter(ReadWriteLock rwlock) {
        this.rwlock = rwlock;
    }

    @Override
    public <R> R doRread(IRunnable<R> fun) {
        R result = null;
        Lock readLock = this.rwlock.readLock();
        readLock.lock();
        LOGGER.debug("\u83b7\u53d6\u8bfb\u9501\u6210\u529f");
        try {
            result = fun.run();
        }
        catch (Exception e) {
            throw new RWLockExecuterException(e);
        }
        finally {
            readLock.unlock();
            LOGGER.debug("\u91ca\u653e\u8bfb\u9501");
        }
        return result;
    }

    @Override
    public <R> R doWrite(IRunnable<R> fun) {
        R result = null;
        Lock writeLock = this.rwlock.writeLock();
        writeLock.lock();
        LOGGER.debug("\u83b7\u53d6\u5199\u9501\u6210\u529f");
        try {
            result = fun.run();
        }
        catch (Exception e) {
            throw new RWLockExecuterException(e);
        }
        finally {
            writeLock.unlock();
            LOGGER.debug("\u91ca\u653e\u5199\u9501");
        }
        return result;
    }

    @Override
    public <R> R tryRead(IRunnable<R> fun) {
        R result = null;
        Lock readLock = this.rwlock.readLock();
        boolean tryLock = false;
        try {
            tryLock = readLock.tryLock(60L, TimeUnit.SECONDS);
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LOGGER.error("\u6267\u884c\u5931\u8d25", e);
            throw new RWLockExecuterException("\u83b7\u53d6\u9501\u5f02\u5e38");
        }
        if (tryLock) {
            LOGGER.debug("\u83b7\u53d6\u8bfb\u9501\u6210\u529f");
            try {
                result = fun.run();
            }
            catch (Exception e) {
                throw new RWLockExecuterException(e);
            }
            finally {
                readLock.unlock();
                LOGGER.debug("\u91ca\u653e\u8bfb\u9501");
            }
        } else {
            throw new RWLockExecuterException("\u6b63\u5728\u53d1\u5e03\u53c2\u6570\uff0c\u8bf7\u7a0d\u540e\u518d\u8bd5");
        }
        return result;
    }

    @Override
    public <R> R tryWrite(IRunnable<R> fun) {
        R result = null;
        Lock writeLock = this.rwlock.writeLock();
        boolean tryLock = false;
        try {
            tryLock = writeLock.tryLock(30L, TimeUnit.SECONDS);
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LOGGER.error("\u6267\u884c\u5931\u8d25", e);
            throw new RWLockExecuterException("\u83b7\u53d6\u9501\u5f02\u5e38");
        }
        if (tryLock) {
            LOGGER.debug("\u83b7\u53d6\u5199\u9501\u6210\u529f");
            try {
                result = fun.run();
            }
            catch (Exception e) {
                throw new RWLockExecuterException(e);
            }
            finally {
                writeLock.unlock();
                LOGGER.debug("\u91ca\u653e\u5199\u9501");
            }
        } else {
            throw new RWLockExecuterException("\u6b63\u5728\u5237\u65b0\u7f13\u5b58\uff0c\u8bf7\u7a0d\u540e\u518d\u8bd5");
        }
        return result;
    }
}

