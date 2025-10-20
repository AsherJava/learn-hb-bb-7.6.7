/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.util.concurrent.AtomicDouble
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 */
package com.jiuqi.common.expimp.progress.common;

import com.google.common.util.concurrent.AtomicDouble;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.common.expimp.progress.common.ProgressData;
import com.jiuqi.common.expimp.progress.service.ProgressService;
import java.util.Objects;

public class ProgressDataImpl<E>
implements ProgressData<E> {
    public static String DEFAULT_GROUPNAME = "default";
    protected static final long PROGRESS_EXPIRE_TIME_SECONDS = 3600L;
    protected String sn;
    protected AtomicDouble progress;
    protected boolean successFlag;
    protected long expireTime;
    protected E result;
    protected String groupName = DEFAULT_GROUPNAME;

    public ProgressDataImpl() {
        this(UUIDUtils.newUUIDStr());
    }

    public ProgressDataImpl(String sn) {
        this(sn, null, 3600L);
    }

    public ProgressDataImpl(String sn, E result) {
        this(sn, result, 3600L);
    }

    public ProgressDataImpl(String sn, E result, long expireTime) {
        this(sn, result, expireTime, DEFAULT_GROUPNAME);
    }

    public ProgressDataImpl(String sn, E result, String groupName) {
        this(sn, result, 3600L, groupName);
    }

    public ProgressDataImpl(String sn, E result, long expireTime, String groupName) {
        Objects.requireNonNull(sn, "\u4efb\u52a1\u6279\u6b21\u53f7\u4e0d\u5141\u8bb8\u4e3a\u7a7a");
        this.sn = sn;
        this.result = result;
        this.progress = new AtomicDouble(0.0);
        this.expireTime = expireTime;
        this.groupName = groupName;
    }

    @Override
    public final String getSn() {
        return this.sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    @Override
    public final boolean isSuccessFlag() {
        return this.successFlag;
    }

    @Override
    public final void setSuccessFlag(boolean successFlag) {
        this.successFlag = successFlag;
    }

    @Override
    public final long getExpireTime() {
        return this.expireTime;
    }

    @Override
    public final double addProgressValue(double addProgressValue) {
        double progressValue = this.progress.addAndGet(addProgressValue);
        return progressValue;
    }

    @Override
    public final void setProgressValue(double progressValue) {
        this.progress.set(progressValue);
    }

    @Override
    public final double getProgressValue() {
        return this.progress.get();
    }

    @Override
    public E getResult() {
        return this.result;
    }

    @Override
    public void setResult(E result) {
        this.result = result;
    }

    @Override
    public final void setProgressValueAndRefresh(double progressValue) {
        this.progress.set(progressValue);
        this.refresh();
    }

    @Override
    public final double addProgressValueAndRefresh(double addProgressValue) {
        double progressValue = this.progress.addAndGet(addProgressValue);
        this.refresh();
        return progressValue;
    }

    @Override
    public final void setSuccessFlagAndRefresh(boolean successFlag) {
        this.successFlag = successFlag;
        this.refresh();
    }

    @Override
    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    @Override
    public String getGroupName() {
        return this.groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    private void refresh() {
        ProgressService progressService = (ProgressService)SpringContextUtils.getBean(ProgressService.class);
        progressService.refreshProgressData(this);
    }

    public void setProgress(AtomicDouble progress) {
        this.progress = progress;
    }

    public AtomicDouble getProgress() {
        return this.progress;
    }
}

