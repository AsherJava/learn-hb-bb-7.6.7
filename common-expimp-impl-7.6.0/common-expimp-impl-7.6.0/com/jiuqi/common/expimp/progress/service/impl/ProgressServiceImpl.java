/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.expimp.progress.service.impl;

import com.jiuqi.common.expimp.progress.common.ProgressData;
import com.jiuqi.common.expimp.progress.proxy.ProgressProxy;
import com.jiuqi.common.expimp.progress.service.ProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value="gcProgressServiceImpl")
public class ProgressServiceImpl<T extends ProgressData<E>, E>
implements ProgressService<T, E> {
    @Autowired
    private ProgressProxy<T, E> progressServiceProxy;

    @Override
    public void createProgressData(T progressData) {
        this.progressServiceProxy.createProgressData(progressData);
    }

    @Override
    public void refreshProgressData(T progressData) {
        this.progressServiceProxy.refreshProgressData(progressData);
    }

    @Override
    public ProgressData<E> queryProgressData(String sn) {
        return this.queryProgressData(sn, true);
    }

    @Override
    public ProgressData<E> queryProgressData(String sn, boolean isAutoClearProgressDataWhenFinished) {
        return this.progressServiceProxy.queryProgressData(sn, isAutoClearProgressDataWhenFinished);
    }

    @Override
    public void removeProgressData(String sn) {
        this.progressServiceProxy.removeProgressData(sn);
    }
}

