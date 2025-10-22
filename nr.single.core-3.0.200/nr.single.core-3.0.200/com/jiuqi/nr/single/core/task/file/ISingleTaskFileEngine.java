/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.task.file;

import com.jiuqi.nr.single.core.task.ISingleTaskEngine;
import com.jiuqi.nr.single.core.task.SingleTaskException;
import com.jiuqi.nr.single.core.task.model.SingleTaskInfo;

public interface ISingleTaskFileEngine
extends ISingleTaskEngine {
    public String getFileName();

    public void unzipToTaskDir() throws SingleTaskException;

    public void deleteTaskDir();

    public SingleTaskInfo getTaskInfoFormFile() throws SingleTaskException;
}

