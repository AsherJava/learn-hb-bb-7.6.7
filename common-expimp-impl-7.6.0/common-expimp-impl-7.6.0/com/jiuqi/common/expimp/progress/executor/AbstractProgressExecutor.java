/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.expimp.progress.executor;

import com.jiuqi.common.expimp.progress.common.ProgressDataImpl;
import com.jiuqi.common.expimp.progress.executor.ProgressExecutor;

public abstract class AbstractProgressExecutor<T extends ProgressDataImpl<E>, E>
implements ProgressExecutor<T, E> {
    @Override
    public final void createProgressData(T progressData) {
        this.create(this.filter(progressData));
    }

    @Override
    public final void refreshProgressData(T progressData) {
        this.refresh(this.filter(progressData));
    }

    @Override
    public final T queryProgressData(String sn, boolean isAutoClearProgressDataWhenFinished) {
        return this.query(sn, isAutoClearProgressDataWhenFinished);
    }

    @Override
    public final void removeProgressData(String sn) {
        this.remove(sn);
    }

    private ProgressDataImpl<E> filter(T progressData) {
        ProgressDataImpl newProgressData = new ProgressDataImpl<E>(((ProgressDataImpl)progressData).getSn()){};
        newProgressData.setResult(((ProgressDataImpl)progressData).getResult());
        newProgressData.setGroupName(((ProgressDataImpl)progressData).getGroupName());
        newProgressData.setSuccessFlag(((ProgressDataImpl)progressData).isSuccessFlag());
        newProgressData.setProgressValue(((ProgressDataImpl)progressData).getProgressValue());
        newProgressData.setExpireTime(((ProgressDataImpl)progressData).getExpireTime());
        return newProgressData;
    }

    @Override
    public abstract String getExecutorName();

    protected abstract void create(ProgressDataImpl<E> var1);

    protected abstract void refresh(ProgressDataImpl<E> var1);

    protected abstract T query(String var1, boolean var2);

    protected abstract void remove(String var1);
}

