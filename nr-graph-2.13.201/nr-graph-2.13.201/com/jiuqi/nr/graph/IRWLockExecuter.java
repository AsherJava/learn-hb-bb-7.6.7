/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.graph;

import com.jiuqi.nr.graph.function.IRunnable;

public interface IRWLockExecuter {
    public static final String EXE_ERROR = "\u6267\u884c\u5931\u8d25";
    public static final String READ_LOCK = "\u83b7\u53d6\u8bfb\u9501\u6210\u529f";
    public static final String WRITE_LOCK = "\u83b7\u53d6\u5199\u9501\u6210\u529f";
    public static final String READ_UNLOCK = "\u91ca\u653e\u8bfb\u9501";
    public static final String WRITE_UNLOCK = "\u91ca\u653e\u5199\u9501";
    public static final String INTERRUPTED_EXCEPTION = "\u83b7\u53d6\u9501\u5f02\u5e38";
    public static final String TRY_RLOCK_FAILD = "\u6b63\u5728\u53d1\u5e03\u53c2\u6570\uff0c\u8bf7\u7a0d\u540e\u518d\u8bd5";
    public static final String TRY_WLOCK_FAILD = "\u6b63\u5728\u5237\u65b0\u7f13\u5b58\uff0c\u8bf7\u7a0d\u540e\u518d\u8bd5";

    public <R> R doRread(IRunnable<R> var1);

    public <R> R doWrite(IRunnable<R> var1);

    public <R> R tryRead(IRunnable<R> var1);

    public <R> R tryWrite(IRunnable<R> var1);
}

