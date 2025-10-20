/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.oss.Bucket
 *  com.jiuqi.bi.oss.ObjectStorageEngine
 *  com.jiuqi.bi.oss.ObjectStorageException
 *  com.jiuqi.np.log.log4j2.job.LogsArchiveJob
 *  com.jiuqi.nvwa.jobmanager.sysjob.AbstractSysJob
 *  com.jiuqi.nvwa.jobmanager.sysjob.SysJobManager
 *  org.json.JSONObject
 */
package com.jiuqi.np.log.launch;

import com.jiuqi.bi.oss.Bucket;
import com.jiuqi.bi.oss.ObjectStorageEngine;
import com.jiuqi.bi.oss.ObjectStorageException;
import com.jiuqi.np.log.log4j2.job.LogsArchiveJob;
import com.jiuqi.nvwa.jobmanager.sysjob.AbstractSysJob;
import com.jiuqi.nvwa.jobmanager.sysjob.SysJobManager;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class NvWaLogStartUp {
    private Logger logger = LoggerFactory.getLogger(NvWaLogStartUp.class);

    public void init() throws Exception {
        this.initRegisterSysJob();
    }

    public void initWhenStarted() throws Exception {
        this.initOssAchieveBucket();
    }

    private void initOssAchieveBucket() throws ObjectStorageException {
        ObjectStorageEngine engine = ObjectStorageEngine.newInstance();
        if (engine.getBucket("LOG_ARCHIVE") == null) {
            Bucket bucket = new Bucket("LOG_ARCHIVE");
            bucket.setDesc("\u7528\u4e8e\u65e5\u5fd7\u5f52\u6863");
            bucket.setOpen(true);
            engine.createBucket(bucket);
        }
    }

    private void initRegisterSysJob() {
        try {
            LogsArchiveJob logsArchiveJob = new LogsArchiveJob();
            SysJobManager sysJobManager = SysJobManager.getInstance();
            JSONObject config = new JSONObject();
            config.put("archiveDays", 1);
            config.put("archivePath", (Object)"");
            config.put("zipPwdSwitch", false);
            config.put("zipPassWord", (Object)"");
            sysJobManager.register((AbstractSysJob)logsArchiveJob, config.toString());
        }
        catch (Exception e) {
            this.logger.error("\u521d\u59cb\u5316\u6ce8\u518c\u7cfb\u7edf\u4efb\u52a1\u5931\u8d25", (Object)e.getMessage(), (Object)e);
        }
    }
}

