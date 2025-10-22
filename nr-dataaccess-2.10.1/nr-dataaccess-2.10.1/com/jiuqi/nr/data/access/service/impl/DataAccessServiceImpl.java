/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  org.apache.shiro.util.Assert
 */
package com.jiuqi.nr.data.access.service.impl;

import com.jiuqi.nr.data.access.common.AccessType;
import com.jiuqi.nr.data.access.param.AccessParam;
import com.jiuqi.nr.data.access.param.AccessResult;
import com.jiuqi.nr.data.access.param.BatchAccessResult;
import com.jiuqi.nr.data.access.param.BatchZBAccessRes;
import com.jiuqi.nr.data.access.param.IAccessResult;
import com.jiuqi.nr.data.access.param.IBatchAccessResult;
import com.jiuqi.nr.data.access.param.IBatchZBAccessResult;
import com.jiuqi.nr.data.access.param.ZbAccessRes;
import com.jiuqi.nr.data.access.service.IDataAccessService;
import com.jiuqi.nr.data.access.service.impl.AccessCacheManager;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import java.util.List;
import java.util.Map;
import org.apache.shiro.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataAccessServiceImpl
implements IDataAccessService {
    private static final Logger logger = LoggerFactory.getLogger(DataAccessServiceImpl.class);
    private final String taskKey;
    private final String formSchemeKey;
    private final AccessCacheManager accessCacheManager;

    public DataAccessServiceImpl(String taskKey, String formSchemeKey, AccessCacheManager accessCacheManager) {
        this.taskKey = taskKey;
        this.formSchemeKey = formSchemeKey;
        this.accessCacheManager = accessCacheManager;
    }

    @Override
    public IAccessResult visible(DimensionCombination masterKey, String formKey) {
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("\u72b6\u6001\u5f15\u64ce-visible-\u62a5\u8868\u53ef\u89c1\u6743\u9650\u63a5\u53e3\u83b7\u53d6 \u53c2\u6570\uff1ataskKey:[%s];formSchemeKey:[%s];masterKey:[%s];formKey:[%s]", this.taskKey, this.formSchemeKey, masterKey, formKey));
        }
        return new AccessResult(this.taskKey, this.formSchemeKey, masterKey, formKey, AccessType.VISIT, this.accessCacheManager);
    }

    @Override
    public IAccessResult zbVisible(DimensionCombination masterKey, String zbKey) {
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("\u72b6\u6001\u5f15\u64ce-zbVisible-\u6307\u6807\u53ef\u89c1\u6743\u9650\u63a5\u53e3\u83b7\u53d6 \u53c2\u6570\uff1ataskKey:[%s];formSchemeKey:[%s];masterKey:[%s];zbKey:[%s]", this.taskKey, this.formSchemeKey, masterKey, zbKey));
        }
        return new ZbAccessRes(AccessType.VISIT, masterKey, zbKey, this.accessCacheManager);
    }

    @Override
    public IAccessResult readable(DimensionCombination masterKey, String formKey) {
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("\u72b6\u6001\u5f15\u64ce-readable-\u62a5\u8868\u53ef\u8bfb\u6743\u9650\u63a5\u53e3\u83b7\u53d6 \u53c2\u6570\uff1ataskKey:[%s];formSchemeKey:[%s];masterKey:[%s];formKey:[%s]", this.taskKey, this.formSchemeKey, masterKey, formKey));
        }
        return new AccessResult(this.taskKey, this.formSchemeKey, masterKey, formKey, AccessType.READ, this.accessCacheManager);
    }

    @Override
    public IAccessResult zbReadable(DimensionCombination masterKey, String zbKey) {
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("\u72b6\u6001\u5f15\u64ce-zbReadable-\u6307\u6807\u53ef\u8bfb\u6743\u9650\u63a5\u53e3\u83b7\u53d6 \u53c2\u6570\uff1ataskKey:[%s];formSchemeKey:[%s];masterKey:[%s];zbKey:[%s]", this.taskKey, this.formSchemeKey, masterKey, zbKey));
        }
        return new ZbAccessRes(AccessType.READ, masterKey, zbKey, this.accessCacheManager);
    }

    @Override
    public IAccessResult writeable(DimensionCombination masterKey, String formKey) {
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("\u72b6\u6001\u5f15\u64ce-writeable-\u62a5\u8868\u53ef\u5199\u6743\u9650\u63a5\u53e3\u83b7\u53d6 \u53c2\u6570\uff1ataskKey:[%s];formSchemeKey:[%s];masterKey:[%s];formKey:[%s]", this.taskKey, this.formSchemeKey, masterKey, formKey));
        }
        return new AccessResult(this.taskKey, this.formSchemeKey, masterKey, formKey, AccessType.WRITE, this.accessCacheManager);
    }

    @Override
    public IAccessResult zbWriteable(DimensionCombination masterKey, String zbKey) {
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("\u72b6\u6001\u5f15\u64ce-zbWriteable-\u6307\u6807\u53ef\u5199\u6743\u9650\u63a5\u53e3\u83b7\u53d6 \u53c2\u6570\uff1ataskKey:[%s];formSchemeKey:[%s];masterKey:[%s];zbKey:[%s]", this.taskKey, this.formSchemeKey, masterKey, zbKey));
        }
        return new ZbAccessRes(AccessType.WRITE, masterKey, zbKey, this.accessCacheManager);
    }

    @Override
    public IAccessResult zbSysWriteable(DimensionCombination masterKey, String zbKey) {
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("\u72b6\u6001\u5f15\u64ce-zbWriteable-\u6307\u6807\u7cfb\u7edf\u53ef\u5199\u6743\u9650\u63a5\u53e3\u83b7\u53d6 \u53c2\u6570\uff1ataskKey:[%s];formSchemeKey:[%s];masterKey:[%s];zbKey:[%s]", this.taskKey, this.formSchemeKey, masterKey, zbKey));
        }
        return new ZbAccessRes(AccessType.SYS_WRITE, masterKey, zbKey, this.accessCacheManager);
    }

    @Override
    public IAccessResult sysWriteable(DimensionCombination masterKey, String formKey) {
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("\u72b6\u6001\u5f15\u64ce-sysWriteable-\u62a5\u8868\u7cfb\u7edf\u53ef\u5199\u6743\u9650\u63a5\u53e3\u83b7\u53d6 \u53c2\u6570\uff1ataskKey:[%s];formSchemeKey:[%s];masterKey:[%s];formKey:[%s]", this.taskKey, this.formSchemeKey, masterKey, formKey));
        }
        return new AccessResult(this.taskKey, this.formSchemeKey, masterKey, formKey, AccessType.SYS_WRITE, this.accessCacheManager);
    }

    @Override
    public IBatchAccessResult getVisitAccess(DimensionCollection collectionMasterKeys, List<String> formKeys) {
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("\u72b6\u6001\u5f15\u64ce-getVisitAccess-\u62a5\u8868\u6279\u91cf\u53ef\u89c1\u6743\u9650\u63a5\u53e3\u83b7\u53d6 \u53c2\u6570\uff1ataskKey:[%s];formSchemeKey:[%s];masterKey:[%s];formKeys:[%s]", this.taskKey, this.formSchemeKey, collectionMasterKeys, formKeys));
        }
        return new BatchAccessResult(this.taskKey, this.formSchemeKey, collectionMasterKeys, formKeys, AccessType.VISIT, this.accessCacheManager);
    }

    @Override
    public IBatchZBAccessResult getZBVisitAccess(DimensionCollection collectionMasterKeys, List<String> zbKeys) {
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("\u72b6\u6001\u5f15\u64ce-getZBVisitAccess-\u6307\u6807\u6279\u91cf\u53ef\u89c1\u6743\u9650\u63a5\u53e3\u83b7\u53d6 \u53c2\u6570 masterKey:[%s];zbKeys:[%s]", collectionMasterKeys, zbKeys));
        }
        return new BatchZBAccessRes(collectionMasterKeys, zbKeys, AccessType.VISIT, this.accessCacheManager);
    }

    @Override
    public IBatchAccessResult getReadAccess(DimensionCollection collectionMasterKeys, List<String> formKeys) {
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("\u72b6\u6001\u5f15\u64ce-getReadAccess-\u62a5\u8868\u6279\u91cf\u53ef\u8bfb\u6743\u9650\u63a5\u53e3\u83b7\u53d6 \u53c2\u6570\uff1ataskKey:[%s];formSchemeKey:[%s];masterKey:[%s];formKeys:[%s]", this.taskKey, this.formSchemeKey, collectionMasterKeys, formKeys));
        }
        return new BatchAccessResult(this.taskKey, this.formSchemeKey, collectionMasterKeys, formKeys, AccessType.READ, this.accessCacheManager);
    }

    @Override
    public IBatchZBAccessResult getZBReadAccess(DimensionCollection collectionMasterKeys, List<String> zbKeys) {
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("\u72b6\u6001\u5f15\u64ce-getZBReadAccess-\u6307\u6807\u6279\u91cf\u53ef\u8bfb\u6743\u9650\u63a5\u53e3\u83b7\u53d6 \u53c2\u6570\uff1amasterKey:[%s];zbKeys:[%s]", collectionMasterKeys, zbKeys));
        }
        return new BatchZBAccessRes(collectionMasterKeys, zbKeys, AccessType.READ, this.accessCacheManager);
    }

    @Override
    public IBatchAccessResult getWriteAccess(DimensionCollection collectionMasterKeys, List<String> formKeys) {
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("\u72b6\u6001\u5f15\u64ce-getWriteAccess-\u62a5\u8868\u6279\u91cf\u53ef\u5199\u6743\u9650\u63a5\u53e3\u83b7\u53d6 \u53c2\u6570\uff1ataskKey:[%s];formSchemeKey:[%s];masterKey:[%s];formKeys:[%s]", this.taskKey, this.formSchemeKey, collectionMasterKeys, formKeys));
        }
        return new BatchAccessResult(this.taskKey, this.formSchemeKey, collectionMasterKeys, formKeys, AccessType.WRITE, this.accessCacheManager);
    }

    @Override
    public IBatchZBAccessResult getZBWriteAccess(DimensionCollection collectionMasterKeys, List<String> zbKeys) {
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("\u72b6\u6001\u5f15\u64ce-getZBWriteAccess-\u6307\u6807\u6279\u91cf\u53ef\u5199\u6743\u9650\u63a5\u53e3\u83b7\u53d6 \u53c2\u6570\uff1amasterKey:[%s];zbKeys:[%s]", collectionMasterKeys, zbKeys));
        }
        return new BatchZBAccessRes(collectionMasterKeys, zbKeys, AccessType.WRITE, this.accessCacheManager);
    }

    @Override
    public IBatchZBAccessResult getZBSysWriteAccess(DimensionCollection masterKey, List<String> zbKeys) {
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("\u72b6\u6001\u5f15\u64ce-getZBWriteAccess-\u6307\u6807\u6279\u91cf\u7cfb\u7edf\u53ef\u5199\u6743\u9650\u63a5\u53e3\u83b7\u53d6 \u53c2\u6570\uff1amasterKey:[%s];zbKeys:[%s]", masterKey, zbKeys));
        }
        return new BatchZBAccessRes(masterKey, zbKeys, AccessType.SYS_WRITE, this.accessCacheManager);
    }

    @Override
    public IBatchAccessResult getSysWriteAccess(DimensionCollection collectionMasterKeys, List<String> formKeys) {
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("\u72b6\u6001\u5f15\u64ce-getSysWriteAccess-\u62a5\u8868\u6279\u91cf\u7cfb\u7edf\u53ef\u5199\u6743\u9650\u63a5\u53e3\u83b7\u53d6 \u53c2\u6570\uff1ataskKey:[%s];formSchemeKey:[%s];masterKey:[%s];formKeys:[%s]", this.taskKey, this.formSchemeKey, collectionMasterKeys, formKeys));
        }
        return new BatchAccessResult(this.taskKey, this.formSchemeKey, collectionMasterKeys, formKeys, AccessType.SYS_WRITE, this.accessCacheManager);
    }

    @Override
    public IAccessResult visible(AccessParam param, DimensionCombination masterKey, String formKey) {
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("\u72b6\u6001\u5f15\u64ce-visible-\u62a5\u8868\u53ef\u89c1\u6743\u9650\u63a5\u53e3\u83b7\u53d6 \u53c2\u6570\uff1ataskKey:[%s];formSchemeKey:[%s];masterKey:[%s];formKey:[%s]", this.taskKey, this.formSchemeKey, masterKey, formKey));
        }
        return new AccessResult(param, this.taskKey, this.formSchemeKey, masterKey, formKey, AccessType.VISIT, this.accessCacheManager);
    }

    @Override
    public IAccessResult zbVisible(AccessParam param, DimensionCombination masterKey, String zbKey) {
        return new ZbAccessRes(AccessType.VISIT, masterKey, zbKey, this.accessCacheManager);
    }

    @Override
    public IAccessResult readable(AccessParam param, DimensionCombination masterKey, String formKey) {
        return new AccessResult(param, this.taskKey, this.formSchemeKey, masterKey, formKey, AccessType.READ, this.accessCacheManager);
    }

    @Override
    public IAccessResult zbReadable(AccessParam param, DimensionCombination masterKey, String zbKey) {
        return new ZbAccessRes(AccessType.READ, masterKey, zbKey, this.accessCacheManager);
    }

    @Override
    public IAccessResult writeable(AccessParam param, DimensionCombination masterKey, String formKey) {
        return new AccessResult(param, this.taskKey, this.formSchemeKey, masterKey, formKey, AccessType.WRITE, this.accessCacheManager);
    }

    @Override
    public IAccessResult sysWriteable(AccessParam param, DimensionCombination masterKey, String formKey) {
        return new AccessResult(param, this.taskKey, this.formSchemeKey, masterKey, formKey, AccessType.SYS_WRITE, this.accessCacheManager);
    }

    @Override
    public IAccessResult zbWriteable(AccessParam param, DimensionCombination masterKey, String zbKey) {
        return new ZbAccessRes(AccessType.WRITE, masterKey, zbKey, this.accessCacheManager);
    }

    @Override
    public Map<String, Object> getExtendResult(AccessParam param, DimensionCombination masterKey, String formKey) {
        Assert.notNull((Object)param, (String)"param is must not be null!");
        return this.accessCacheManager.getExtendAccessResult(param, this.formSchemeKey, masterKey, formKey);
    }
}

