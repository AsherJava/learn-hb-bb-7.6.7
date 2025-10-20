/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.expimp.progress.executor.impl;

import com.jiuqi.common.expimp.progress.common.ProgressDataImpl;
import com.jiuqi.common.expimp.progress.executor.AbstractProgressExecutor;
import java.lang.ref.SoftReference;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class ProgressMapExecutorImpl<E>
extends AbstractProgressExecutor<ProgressDataImpl<E>, E> {
    private ConcurrentHashMap<String, SoftReference<ProgressDataImpl<E>>> progressDataMap = new ConcurrentHashMap();

    @Override
    public void create(ProgressDataImpl<E> progressData) {
        SoftReference<ProgressDataImpl<E>> softReferenceProgressData = new SoftReference<ProgressDataImpl<E>>(progressData);
        this.progressDataMap.put(progressData.getSn(), softReferenceProgressData);
    }

    @Override
    public void refresh(ProgressDataImpl<E> progressData) {
        SoftReference<ProgressDataImpl<E>> softReferenceProgressData = new SoftReference<ProgressDataImpl<E>>(progressData);
        this.progressDataMap.put(progressData.getSn(), softReferenceProgressData);
    }

    @Override
    public ProgressDataImpl<E> query(String sn, boolean isAutoClearProgressDataWhenFinished) {
        if (sn == null) {
            return null;
        }
        SoftReference<ProgressDataImpl<E>> softReference = this.progressDataMap.get(sn);
        if (softReference == null) {
            return null;
        }
        ProgressDataImpl<E> progressData = softReference.get();
        if (progressData == null) {
            return null;
        }
        if (isAutoClearProgressDataWhenFinished && progressData.getProgressValue() >= 1.0) {
            this.removeProgressData(sn);
        }
        return progressData;
    }

    @Override
    public void remove(String sn) {
        this.progressDataMap.remove(sn);
    }

    @Override
    public String getExecutorName() {
        return "map";
    }
}

