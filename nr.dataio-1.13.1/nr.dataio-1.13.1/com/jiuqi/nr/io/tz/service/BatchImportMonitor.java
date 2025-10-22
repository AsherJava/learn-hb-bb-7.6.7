/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 */
package com.jiuqi.nr.io.tz.service;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BatchImportMonitor {
    public static AsyncTaskMonitor loggerMonitor = new AsyncTaskMonitor(){
        private final Logger logger = LoggerFactory.getLogger(BatchImportMonitor.class);

        public String getTaskId() {
            return null;
        }

        public String getTaskPoolTask() {
            return null;
        }

        public void progressAndMessage(double progress, String message) {
            this.logger.info(message);
        }

        public boolean isCancel() {
            return false;
        }

        public void finish(String result, Object detail) {
            this.logger.info(result);
        }

        public void canceling(String result, Object detail) {
        }

        public void canceled(String result, Object detail) {
        }

        public void error(String result, Throwable t) {
            this.logger.error(result, t);
        }

        public boolean isFinish() {
            return false;
        }
    };
}

