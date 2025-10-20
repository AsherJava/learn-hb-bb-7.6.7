/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.meta.MetaInfoDTO
 *  com.jiuqi.va.domain.meta.batchupdate.MetaDataBatchUpdateProgress
 *  org.springframework.data.redis.core.StringRedisTemplate
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.bizmeta.controller;

import com.jiuqi.va.bizmeta.service.MetaDataUpdateService;
import com.jiuqi.va.bizmeta.service.impl.MetaDataUpdateServiceImpl;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.meta.MetaInfoDTO;
import com.jiuqi.va.domain.meta.batchupdate.MetaDataBatchUpdateProgress;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/biz/meta/data"})
public class MetaDataUpdateController {
    private static final Logger logger = LoggerFactory.getLogger(MetaDataUpdateServiceImpl.class);
    @Autowired
    private MetaDataUpdateService metaDataUpdateService;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @PostMapping(value={"/batch/update"})
    R batchUpdate() {
        try {
            this.redisTemplate.opsForValue().set((Object)"META_DATA_UPDATE_CONTROLLER_CACHE_KEY", (Object)"1", 30L, TimeUnit.SECONDS);
            MetaDataBatchUpdateProgress metaDataBatchUpdateProgress = this.metaDataUpdateService.batchUpdate();
            R r = R.ok().put("data", (Object)metaDataBatchUpdateProgress);
            return r;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            R r = R.error((String)e.getMessage());
            return r;
        }
        finally {
            this.redisTemplate.delete((Object)"META_DATA_UPDATE_CONTROLLER_CACHE_KEY");
        }
    }

    @PostMapping(value={"/batch/update/progress"})
    R batchUpdateProgress() {
        try {
            MetaDataBatchUpdateProgress metaDataBatchUpdateProgress = this.metaDataUpdateService.getProgress();
            if (metaDataBatchUpdateProgress.getCurrent() == metaDataBatchUpdateProgress.getTotal()) {
                return R.ok().put("data", (Object)metaDataBatchUpdateProgress);
            }
            MetaDataBatchUpdateProgress progress = new MetaDataBatchUpdateProgress();
            progress.setCurrent(metaDataBatchUpdateProgress.getCurrent());
            progress.setTotal(metaDataBatchUpdateProgress.getTotal());
            progress.setUpdating(metaDataBatchUpdateProgress.isUpdating());
            progress.setErrorMsg(metaDataBatchUpdateProgress.getErrorMsg());
            return R.ok().put("data", (Object)progress);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return R.error((String)e.getMessage());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @PostMapping(value={"/single/update"})
    MetaDataBatchUpdateProgress singleUpdate(@RequestBody MetaInfoDTO metaInfoDTO) {
        try {
            this.redisTemplate.opsForValue().set((Object)"META_DATA_UPDATE_CONTROLLER_CACHE_KEY", (Object)"1", 30L, TimeUnit.SECONDS);
            MetaDataBatchUpdateProgress metaDataBatchUpdateProgress = this.metaDataUpdateService.singleUpdate(metaInfoDTO);
            return metaDataBatchUpdateProgress;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            MetaDataBatchUpdateProgress metaDataBatchUpdateProgress = new MetaDataBatchUpdateProgress(e.getMessage());
            return metaDataBatchUpdateProgress;
        }
        finally {
            this.redisTemplate.delete((Object)"META_DATA_UPDATE_CONTROLLER_CACHE_KEY");
        }
    }
}

