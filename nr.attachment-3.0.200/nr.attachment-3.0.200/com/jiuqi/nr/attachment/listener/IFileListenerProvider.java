/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.attachment.listener;

import com.jiuqi.nr.attachment.listener.IFileListener;
import com.jiuqi.nr.attachment.listener.param.FileListenerContext;

public interface IFileListenerProvider {
    public IFileListener getFileListener(FileListenerContext var1);
}

