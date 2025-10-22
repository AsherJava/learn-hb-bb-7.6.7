/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.ContextExtension
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 */
package com.jiuqi.nr.bpm.impl.ReportState;

import com.jiuqi.np.core.context.ContextExtension;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.common.UploadRecordNew;
import com.jiuqi.nr.bpm.common.UploadStateNew;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean;
import com.jiuqi.nr.bpm.impl.ReportState.BatchUploadStateServiceImpl;
import com.jiuqi.nr.bpm.impl.ReportState.CacheValue;
import com.jiuqi.nr.bpm.impl.ReportState.StateCacheKey;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import java.io.Serializable;
import java.util.List;

public class BatchUploadStateCacheServiceImpl
extends BatchUploadStateServiceImpl {
    private static final String CACHENAME = "NR";
    private static final String QUERY_LATEST_UPLOAD_ACTION = "queryLatestUploadAction";
    private static final String QUERY_UPLOAD_STATE = "queryUploadState";
    private static final String QUERY_UPLOAD_STATE_NEW = "queryUploadStateNew";

    @Override
    public UploadRecordNew queryLatestUploadAction(DimensionValueSet dimensionValueSet, String formKey, String groupKey, FormSchemeDefine formScheme) {
        return this.queryLatestUploadAction(dimensionValueSet, formKey, groupKey, formScheme, true);
    }

    @Override
    public UploadRecordNew queryLatestUploadAction(DimensionValueSet dimensionValueSet, String formKey, String groupKey, FormSchemeDefine formScheme, boolean getUser) {
        boolean canCache = this.checkCanCache(dimensionValueSet);
        if (canCache) {
            StateCacheKey cacheKey = this.getCacheKey(QUERY_LATEST_UPLOAD_ACTION, dimensionValueSet, formScheme.getKey(), formKey, null, getUser);
            String keyString = cacheKey.toString();
            CacheValue cacheValue = this.getCache(keyString);
            if (cacheValue == null) {
                UploadRecordNew recordNew = super.queryLatestUploadAction(dimensionValueSet, formKey, groupKey, formScheme, getUser);
                if (recordNew != null && recordNew.getTaskId() != null) {
                    cacheValue = new CacheValue();
                    cacheValue.setCacheItem(recordNew);
                    cacheValue.setNull(recordNew == null);
                    this.setCache(keyString, cacheValue);
                }
                return recordNew;
            }
            return (UploadRecordNew)cacheValue.getCacheItem();
        }
        return super.queryLatestUploadAction(dimensionValueSet, formKey, groupKey, formScheme, getUser);
    }

    @Override
    public ActionStateBean queryUploadState(DimensionValueSet dimensionValueSet, String formKey, String groupKey, FormSchemeDefine formScheme) {
        boolean canCache = this.checkCanCache(dimensionValueSet);
        if (canCache) {
            StateCacheKey cacheKey = this.getCacheKey(QUERY_UPLOAD_STATE, dimensionValueSet, formScheme.getKey(), formKey, groupKey, false);
            String keyString = cacheKey.toString();
            CacheValue cacheValue = this.getCache(keyString);
            if (cacheValue == null) {
                ActionStateBean actionStateBean = super.queryUploadState(dimensionValueSet, formKey, groupKey, formScheme);
                if (actionStateBean != null && actionStateBean.getTaskKey() != null) {
                    cacheValue = new CacheValue();
                    cacheValue.setCacheItem(actionStateBean);
                    cacheValue.setNull(actionStateBean == null);
                    this.setCache(keyString, cacheValue);
                }
                return actionStateBean;
            }
            return (ActionStateBean)cacheValue.getCacheItem();
        }
        return super.queryUploadState(dimensionValueSet, formKey, groupKey, formScheme);
    }

    @Override
    public UploadStateNew queryUploadStateNew(DimensionValueSet dimensionValueSet, String formKey, FormSchemeDefine formScheme) {
        boolean canCache = this.checkCanCache(dimensionValueSet);
        if (canCache) {
            StateCacheKey cacheKey = this.getCacheKey(QUERY_UPLOAD_STATE_NEW, dimensionValueSet, formScheme.getKey(), formKey, null, false);
            String keyString = cacheKey.toString();
            CacheValue cacheValue = this.getCache(keyString);
            if (cacheValue == null) {
                UploadStateNew uploadStateNew = super.queryUploadStateNew(dimensionValueSet, formKey, formScheme);
                if (uploadStateNew != null && uploadStateNew.getTaskId() != null) {
                    cacheValue = new CacheValue();
                    cacheValue.setCacheItem(uploadStateNew);
                    cacheValue.setNull(uploadStateNew == null);
                    this.setCache(keyString, cacheValue);
                }
                return uploadStateNew;
            }
            return (UploadStateNew)cacheValue.getCacheItem();
        }
        return super.queryUploadStateNew(dimensionValueSet, formKey, formScheme);
    }

    private void setCache(String cacheKey, CacheValue cacheValue) {
        NpContext npContext = NpContextHolder.getContext();
        ContextExtension contextExtension = npContext.getExtension(CACHENAME);
        contextExtension.put(cacheKey, (Serializable)cacheValue);
    }

    private CacheValue getCache(String cacheKey) {
        NpContext npContext = NpContextHolder.getContext();
        ContextExtension contextExtension = npContext.getExtension(CACHENAME);
        return (CacheValue)contextExtension.get(cacheKey);
    }

    private StateCacheKey getCacheKey(String cacheCode, DimensionValueSet dimensionValueSet, String formSchemeKey, String formKey, String groupKey, boolean getUser) {
        StateCacheKey cacheKey = new StateCacheKey();
        cacheKey.setCacheKey(cacheCode);
        cacheKey.setDimensionValueSet(dimensionValueSet);
        cacheKey.setFormKey(formKey);
        cacheKey.setFormSchemeKey(formSchemeKey);
        cacheKey.setGroupKey(groupKey);
        cacheKey.setGetUser(getUser);
        return cacheKey;
    }

    private boolean checkCanCache(DimensionValueSet dimensionValueSet) {
        int count = dimensionValueSet.size();
        for (int index = 0; index < count; ++index) {
            Object value = dimensionValueSet.getValue(index);
            if (!(value instanceof List)) continue;
            return false;
        }
        return true;
    }
}

