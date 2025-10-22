/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.bufdb.BufDBException
 *  com.jiuqi.bi.bufdb.config.InstanceConfig
 *  com.jiuqi.bi.bufdb.config.SchemaConfig
 *  com.jiuqi.bi.bufdb.db.DatabaseFactory
 *  com.jiuqi.bi.bufdb.db.IInstance
 *  com.jiuqi.bi.bufdb.db.ISchema
 */
package com.jiuqi.nr.io.bufdb.impl;

import com.jiuqi.bi.bufdb.BufDBException;
import com.jiuqi.bi.bufdb.config.InstanceConfig;
import com.jiuqi.bi.bufdb.config.SchemaConfig;
import com.jiuqi.bi.bufdb.db.DatabaseFactory;
import com.jiuqi.bi.bufdb.db.IInstance;
import com.jiuqi.bi.bufdb.db.ISchema;
import com.jiuqi.nr.io.bufdb.IIOBufDBProvider;
import com.jiuqi.nr.io.bufdb.IOBufDBConfig;
import com.jiuqi.nr.io.bufdb.impl.SingletonSchema;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SingletonIOBufDBProvider
implements IIOBufDBProvider {
    private static final String TZ_IMPORT_SN = "TZIMPORT";
    @Autowired
    private IOBufDBConfig config;
    private volatile IInstance instance;
    private final AtomicInteger counter = new AtomicInteger();
    private volatile long lastTime = System.currentTimeMillis();

    public SingletonIOBufDBProvider() {
        SingletonIOBufDBDaemon daemon = new SingletonIOBufDBDaemon(this);
        Thread t = new Thread((Runnable)daemon, TZ_IMPORT_SN);
        t.setDaemon(true);
        t.start();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public ISchema getSchema() throws BufDBException {
        SingletonIOBufDBProvider schemaConfig2;
        if (this.instance != null) {
            SchemaConfig schemaConfig2 = new SchemaConfig(false, true);
            schemaConfig2.setMinLiveTime(this.config.getMinLiveTime());
            schemaConfig2.setMaxTimeOut(this.config.getMaxTimeOut());
            ISchema schema = this.instance.openSchema(TZ_IMPORT_SN, schemaConfig2);
            this.counter.incrementAndGet();
            this.lastTime = Long.MAX_VALUE;
            return new SingletonSchema(schema, this);
        }
        if (this.instance == null) {
            schemaConfig2 = this;
            synchronized (schemaConfig2) {
                if (this.instance == null) {
                    try {
                        DatabaseFactory.getConfig().setMaxCachePercent(this.config.getMaxCachePercent());
                        if (this.config.getMaxCacheBytes() > 0L) {
                            DatabaseFactory.getConfig().setMaxCacheBytes(this.config.getMaxCacheBytes());
                        }
                        InstanceConfig instConfig = new InstanceConfig(this.config.getDbPath());
                        instConfig.setMinLiveTime(this.config.getMinLiveTime());
                        instConfig.setMaxTimeOut(this.config.getMaxTimeOut());
                        this.instance = DatabaseFactory.createInstance((InstanceConfig)instConfig);
                    }
                    catch (Exception e) {
                        this.destroy();
                        throw e;
                    }
                }
            }
        }
        schemaConfig2 = new SchemaConfig(false, true);
        schemaConfig2.setMinLiveTime(this.config.getMinLiveTime());
        schemaConfig2.setMaxTimeOut(this.config.getMaxTimeOut());
        ISchema schema = this.instance.openSchema(TZ_IMPORT_SN, (SchemaConfig)schemaConfig2);
        this.counter.incrementAndGet();
        this.lastTime = Long.MAX_VALUE;
        return new SingletonSchema(schema, this);
    }

    public void close() throws BufDBException {
        int count = this.counter.decrementAndGet();
        if (count == 0) {
            this.lastTime = System.currentTimeMillis();
        }
    }

    public synchronized void destroy() throws BufDBException {
        if (this.instance != null) {
            if (this.instance.hasSchema(TZ_IMPORT_SN)) {
                this.instance.dropSchema(TZ_IMPORT_SN);
            }
            this.instance.close();
        }
        this.instance = null;
    }

    public int getCounter() {
        return this.counter.get();
    }

    private static class SingletonIOBufDBDaemon
    implements Runnable {
        private final Logger logger = LoggerFactory.getLogger(SingletonIOBufDBDaemon.class);
        private final SingletonIOBufDBProvider provider;
        private final long expiredTime = 600000L;
        private final long waitTime = 180000000000L;

        private SingletonIOBufDBDaemon(SingletonIOBufDBProvider provider) {
            this.provider = provider;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void run() {
            while (true) {
                LockSupport.parkNanos(180000000000L);
                this.logger.debug("bufDb \u5355\u5b9e\u4f8b\u68c0\u67e5");
                long curr = System.currentTimeMillis();
                SingletonIOBufDBProvider singletonIOBufDBProvider = this.provider;
                synchronized (singletonIOBufDBProvider) {
                    if (this.provider.instance != null && curr - this.provider.lastTime > this.expiredTime && this.provider.getCounter() == 0) {
                        try {
                            this.provider.destroy();
                            this.logger.info("bufDb \u5355\u5b9e\u4f8b\u91ca\u653e\u5b8c\u6210");
                        }
                        catch (BufDBException e) {
                            this.logger.error("\u5220\u9664 bufDb \u5355\u5b9e\u4f8b\u5931\u8d25\uff0c\u9000\u51fa\u5b88\u62a4\u7ebf\u7a0b", e);
                            return;
                        }
                    }
                }
            }
        }
    }
}

