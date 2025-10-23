/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.application.ApplicationInitialization
 */
package com.jiuqi.nr.transmission.data.config;

import com.jiuqi.np.core.application.ApplicationInitialization;
import com.jiuqi.nr.transmission.data.dto.SyncHistoryDTO;
import com.jiuqi.nr.transmission.data.service.ISyncHistoryService;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class CheckUncompleteHistory
implements ApplicationInitialization {
    private static final Logger logger = LoggerFactory.getLogger(CheckUncompleteHistory.class);
    @Autowired
    private ISyncHistoryService syncHistoryService;

    public void init(boolean isSysTenant) {
        int[] arr = new int[]{0, 1, 2};
        List<SyncHistoryDTO> uncomplete = this.syncHistoryService.getUnComplete();
        for (SyncHistoryDTO syncHistoryDTO : uncomplete) {
            if (StringUtils.hasText(syncHistoryDTO.getInstanceId())) continue;
            syncHistoryDTO.setStatus(4);
            syncHistoryDTO.setDetail(syncHistoryDTO.getDetail() + "\r\n\u670d\u52a1\u5f02\u5e38\u7ed3\u675f\uff0c\u7ec8\u6b62\u540c\u6b65");
            syncHistoryDTO.setEndTime(new Date());
            this.syncHistoryService.update(syncHistoryDTO);
            logger.info("\u542f\u52a8\u65f6\u5019\u591a\u7ea7\u90e8\u7f72\u5386\u53f2\u8bb0\u5f55 " + syncHistoryDTO.getKey() + " \u72b6\u6001\u5f02\u5e38\uff0c\u5df2\u6539\u4e3a\u5931\u8d25\uff01");
        }
    }
}

