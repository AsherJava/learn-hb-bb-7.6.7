/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.nodekeeper.GlobalConnectionProviderManager
 */
package com.jiuqi.bi.core.jobs.defaultlog;

import com.jiuqi.bi.core.jobs.defaultlog.DefaultLogDAO;
import com.jiuqi.bi.core.jobs.defaultlog.LogItem;
import com.jiuqi.bi.core.nodekeeper.GlobalConnectionProviderManager;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerConsumer {
    private Logger slf4jLogger = LoggerFactory.getLogger(this.getClass());
    private static final LogItem EOF = new LogItem();
    private BlockingQueue<LogItem> queue;
    private Thread consumerThread;
    private static final LoggerConsumer instance = new LoggerConsumer();

    private LoggerConsumer() {
    }

    public static LoggerConsumer getInstance() {
        return instance;
    }

    public synchronized void start(int capacity) {
        this.queue = new ArrayBlockingQueue<LogItem>(capacity);
        this.consumerThread = new Thread(() -> {
            try {
                this.consume();
            }
            catch (InterruptedException e) {
                this.slf4jLogger.info("\u67d0\u4e9b\u539f\u56e0\u4e2d\u65ad\u4e86\u65e5\u5fd7\u7ebf\u7a0b", e);
                Thread.currentThread().interrupt();
            }
            catch (Exception e) {
                this.slf4jLogger.error("\u4efb\u52a1\u5f02\u6b65\u65e5\u5fd7\u7ebf\u7a0b\u672a\u542f\u52a8", e);
            }
            finally {
                this.queue = null;
            }
        }, "\u4efb\u52a1\u5f02\u6b65\u65e5\u5fd7\u7ebf\u7a0b");
        this.consumerThread.start();
    }

    public synchronized void stop() {
        if (this.consumerThread == null) {
            this.slf4jLogger.warn("\u65e5\u5fd7\u6d88\u8d39\u8005\u672a\u521d\u59cb\u5316");
        }
        this.addItem(EOF);
    }

    void addItem(LogItem item) {
        if (this.queue == null) {
            String levelStr = "\u65e0\u6cd5\u5b58\u50a8\u5230\u6570\u636e\u5e93\u4e2d\u7684\u65e5\u5fd7:";
            switch (item.getLevel()) {
                case 0: {
                    this.slf4jLogger.trace("{}{}", (Object)levelStr, (Object)item.getMessage());
                    break;
                }
                case 10: {
                    this.slf4jLogger.debug("{}{}", (Object)levelStr, (Object)item.getMessage());
                    break;
                }
                case 30: {
                    this.slf4jLogger.warn("{}{}", (Object)levelStr, (Object)item.getMessage());
                    break;
                }
                case 40: {
                    this.slf4jLogger.error("{}{}{}{}", levelStr, item.getMessage(), ";\u8be6\u7ec6\u4fe1\u606f:", item.getDetail());
                    break;
                }
                default: {
                    this.slf4jLogger.info("{}{}", (Object)levelStr, (Object)item.getMessage());
                }
            }
            return;
        }
        try {
            this.queue.put(item);
        }
        catch (InterruptedException e) {
            this.slf4jLogger.info("\u67d0\u4e9b\u539f\u56e0\u4e2d\u65ad\u4e86\u7ebf\u7a0b", e);
            Thread.currentThread().interrupt();
        }
    }

    private void consume() throws InterruptedException {
        ArrayList<LogItem> list = new ArrayList<LogItem>();
        while (true) {
            LogItem item;
            if ((item = this.queue.poll(2L, TimeUnit.SECONDS)) == null) {
                this.insertLogToDatabase(list);
                continue;
            }
            if (item == EOF) break;
            list.add(item);
            if (list.size() <= 200) continue;
            this.insertLogToDatabase(list);
        }
        this.insertLogToDatabase(list);
    }

    private void insertLogToDatabase(List<LogItem> list) {
        if (list.isEmpty()) {
            return;
        }
        try (Connection conn = GlobalConnectionProviderManager.getConnection();){
            DefaultLogDAO.insertLogs(conn, list);
        }
        catch (Exception e) {
            this.slf4jLogger.error("\u5199\u5165\u4efb\u52a1\u65e5\u5fd7\u51fa\u73b0\u9519\u8bef", e);
            for (LogItem i : list) {
                this.slf4jLogger.debug(this.printLogItem(i));
            }
        }
        list.clear();
    }

    private String printLogItem(LogItem item) {
        return String.format("\u65e0\u6cd5\u5b58\u50a8\u5230\u6570\u636e\u5e93\u4e2d\u7684\u65e5\u5fd7\uff1a[%1$tF %1$tT] %2$s", new Date(item.getTimestamp()), item.getMessage());
    }
}

