/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.meta.MetaInfoDO
 *  com.jiuqi.va.domain.meta.MetaInfoDTO
 *  com.jiuqi.va.domain.meta.MetaInfoEditionDO
 *  com.jiuqi.va.domain.meta.batchupdate.MetaDataBatchUpdateProgress
 *  org.apache.shiro.util.StringUtils
 *  org.springframework.data.redis.core.StringRedisTemplate
 */
package com.jiuqi.va.bizmeta.service.impl;

import com.jiuqi.va.bizmeta.cache.MetaCache;
import com.jiuqi.va.bizmeta.cache.MetaDataUpdateCache;
import com.jiuqi.va.bizmeta.dao.IMetaDataInfoDao;
import com.jiuqi.va.bizmeta.dao.IMetaDataInfoUserDao;
import com.jiuqi.va.bizmeta.service.MetaDataUpdateAsyncService;
import com.jiuqi.va.bizmeta.service.MetaDataUpdateService;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.meta.MetaInfoDO;
import com.jiuqi.va.domain.meta.MetaInfoDTO;
import com.jiuqi.va.domain.meta.MetaInfoEditionDO;
import com.jiuqi.va.domain.meta.batchupdate.MetaDataBatchUpdateProgress;
import java.util.List;
import java.util.Map;
import org.apache.shiro.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class MetaDataUpdateServiceImpl
implements MetaDataUpdateService {
    private static final Logger logger = LoggerFactory.getLogger(MetaDataUpdateServiceImpl.class);
    @Autowired
    private IMetaDataInfoDao iMetaDataInfoDao;
    @Autowired
    private IMetaDataInfoUserDao iMetaDataInfoUserDao;
    @Autowired
    private MetaDataUpdateAsyncService metaDataUpdateAsyncService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public MetaDataBatchUpdateProgress batchUpdate() {
        List<MetaInfoDO> allMeteInfo = this.getAllMeteInfo();
        List<MetaInfoEditionDO> allAppendMeteInfoUser = this.getAllAppendMeteInfoUser();
        MetaDataBatchUpdateProgress progress = new MetaDataBatchUpdateProgress();
        progress.setTotal(allMeteInfo.size() + allAppendMeteInfoUser.size());
        progress.setCurrent(0);
        if (this.isUpdating()) {
            progress.setUpdating(true);
            progress.setErrorMsg("\u5b58\u5728\u6b63\u5728\u6279\u91cf\u5347\u7ea7\u64cd\u4f5c");
            return progress;
        }
        if (this.isDeploying()) {
            progress.setUpdating(true);
            progress.setErrorMsg("\u5b58\u5728\u6b63\u5728\u53d1\u5e03\u7684\u5355\u636e\uff0c\u8bf7\u7a0d\u540e\u91cd\u8bd5\u3002");
            return progress;
        }
        if (this.syncBill()) {
            progress.setUpdating(true);
            progress.setErrorMsg("\u5b58\u5728\u6b63\u5728\u53d1\u5e03\u7684\u5355\u636e\uff0c\u8bf7\u7a0d\u540e\u91cd\u8bd5");
            return progress;
        }
        MetaDataUpdateCache.setMetaDataBatchUpdateProgress(progress);
        this.metaDataUpdateAsyncService.asyncHandleUpdate(allMeteInfo, allAppendMeteInfoUser, progress);
        return progress;
    }

    private boolean syncBill() {
        Map<String, Map<Long, Integer>> stringMapMap = MetaCache.tenantSyncStatusCache.get(ShiroUtil.getTenantName());
        if (stringMapMap == null) {
            return false;
        }
        for (String s : stringMapMap.keySet()) {
            String[] s1 = s.split("_");
            if (s1.length < 3 || !"B".equals(s1[1]) || stringMapMap.get(s).keySet().size() <= 0) continue;
            return true;
        }
        return false;
    }

    @Override
    public MetaDataBatchUpdateProgress getProgress() {
        return MetaDataUpdateCache.getMetaDataBatchUpdateProgress();
    }

    @Override
    public boolean isUpdating() {
        return Boolean.TRUE.equals(this.stringRedisTemplate.hasKey((Object)"META_DATA_UPDATE_CACHE_KEY"));
    }

    @Override
    public boolean isDeploying() {
        return Boolean.TRUE.equals(this.stringRedisTemplate.hasKey((Object)"META_DATA_DEPLOY_CACHE_KEY"));
    }

    @Override
    public boolean isControllerUpdating() {
        return Boolean.TRUE.equals(this.stringRedisTemplate.hasKey((Object)"META_DATA_UPDATE_CONTROLLER_CACHE_KEY"));
    }

    private List<MetaInfoDO> getAllMeteInfo() {
        MetaInfoDO select = new MetaInfoDO();
        select.setMetaType("bill");
        return this.iMetaDataInfoDao.select(select);
    }

    private List<MetaInfoEditionDO> getAllAppendMeteInfoUser() {
        MetaInfoEditionDO select = new MetaInfoEditionDO();
        select.setMetaType("bill");
        select.setMetaState(Integer.valueOf(1));
        return this.iMetaDataInfoUserDao.select(select);
    }

    private List<MetaInfoDO> getAllMeteInfoByCondition(MetaInfoDTO metaInfoDTO) {
        MetaInfoDO select = new MetaInfoDO();
        select.setMetaType("bill");
        select.setUniqueCode(metaInfoDTO.getUniqueCode());
        return this.iMetaDataInfoDao.select(select);
    }

    private List<MetaInfoEditionDO> getAllAppendMeteInfoUserByCondition(MetaInfoDTO metaInfoDTO) {
        MetaInfoEditionDO select = new MetaInfoEditionDO();
        select.setMetaType("bill");
        select.setMetaState(Integer.valueOf(1));
        select.setUniqueCode(metaInfoDTO.getUniqueCode());
        return this.iMetaDataInfoUserDao.select(select);
    }

    @Override
    public MetaDataBatchUpdateProgress singleUpdate(MetaInfoDTO metaInfoDTO) {
        if (!StringUtils.hasText((String)metaInfoDTO.getUniqueCode())) {
            return new MetaDataBatchUpdateProgress("uniquecode\u4e0d\u80fd\u4e3a\u7a7a");
        }
        List<MetaInfoDO> allMeteInfo = this.getAllMeteInfoByCondition(metaInfoDTO);
        List<MetaInfoEditionDO> allAppendMeteInfoUser = this.getAllAppendMeteInfoUserByCondition(metaInfoDTO);
        MetaDataBatchUpdateProgress progress = new MetaDataBatchUpdateProgress();
        progress.setTotal(allMeteInfo.size() + allAppendMeteInfoUser.size());
        progress.setCurrent(0);
        if (this.isUpdating()) {
            progress.setUpdating(true);
            progress.setErrorMsg("\u5b58\u5728\u6b63\u5728\u6279\u91cf\u5347\u7ea7\u64cd\u4f5c");
            return progress;
        }
        if (this.isDeploying()) {
            progress.setUpdating(true);
            progress.setErrorMsg("\u5b58\u5728\u6b63\u5728\u53d1\u5e03\u7684\u5355\u636e\uff0c\u8bf7\u7a0d\u540e\u91cd\u8bd5\u3002");
            return progress;
        }
        if (this.syncBill()) {
            progress.setUpdating(true);
            progress.setErrorMsg("\u5b58\u5728\u6b63\u5728\u53d1\u5e03\u7684\u5355\u636e\uff0c\u8bf7\u7a0d\u540e\u91cd\u8bd5");
            return progress;
        }
        MetaDataUpdateCache.setMetaDataBatchUpdateProgress(progress);
        this.metaDataUpdateAsyncService.syncHandleUpdate(allMeteInfo, allAppendMeteInfoUser, progress);
        return progress;
    }
}

