/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.sf.Framework
 */
package com.jiuqi.common.file.scheduler;

import com.jiuqi.common.file.service.CommonFileClearService;
import com.jiuqi.nvwa.sf.Framework;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CommonFileClearScheduler {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommonFileClearScheduler.class);
    @Autowired
    private CommonFileClearService fileClearService;

    @Scheduled(fixedRate=518400000L)
    public void doTask() {
        try {
            if (Framework.getInstance().startSuccessful()) {
                this.fileClearService.clearExpiredFiles();
            }
        }
        catch (Exception e) {
            LOGGER.error("\u6587\u4ef6\u6a21\u5757\u4e34\u65f6\u6587\u4ef6\u5b9a\u65f6\u6e05\u7406\u8868\u6e05\u7406\u5931\u8d25\uff0c\u8be6\u60c5\uff1a" + e.getMessage(), e);
        }
    }
}

