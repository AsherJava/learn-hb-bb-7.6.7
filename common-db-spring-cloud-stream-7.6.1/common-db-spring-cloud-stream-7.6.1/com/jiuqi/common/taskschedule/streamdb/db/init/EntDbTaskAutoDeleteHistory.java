/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.taskschedule.streamdb.db.init;

import com.jiuqi.common.taskschedule.streamdb.db.service.EntDbTaskScheduleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EntDbTaskAutoDeleteHistory {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private EntDbTaskScheduleService service;

    @Scheduled(cron="0 0 0 1/1 * *")
    public void deleteHistory() {
        this.logger.info("\u5f00\u59cb\u6267\u884c\u5220\u9664\u4efb\u52a1\u8c03\u5ea6\u5386\u53f2\u6570\u636e");
        try {
            this.service.deleteHistory();
        }
        catch (Exception e) {
            this.logger.info("\u5220\u9664\u4efb\u52a1\u8c03\u5ea6\u5386\u53f2\u6570\u636e\u9519\u8bef\uff1a" + e.getMessage());
        }
        this.logger.info("\u5220\u9664\u4efb\u52a1\u8c03\u5ea6\u5386\u53f2\u6570\u636e\u6267\u884c\u5b8c\u6bd5");
    }
}

