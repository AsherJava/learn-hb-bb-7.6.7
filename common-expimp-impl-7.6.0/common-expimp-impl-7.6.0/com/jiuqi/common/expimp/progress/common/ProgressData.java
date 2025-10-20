/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.expimp.progress.common;

public interface ProgressData<E> {
    public String getSn();

    public E getResult();

    public boolean isSuccessFlag();

    public void setSuccessFlag(boolean var1);

    public double addProgressValue(double var1);

    public void setProgressValue(double var1);

    public double getProgressValue();

    public long getExpireTime();

    public double addProgressValueAndRefresh(double var1);

    public void setProgressValueAndRefresh(double var1);

    public void setSuccessFlagAndRefresh(boolean var1);

    public String getGroupName();

    public void setResult(E var1);

    public void setExpireTime(long var1);
}

