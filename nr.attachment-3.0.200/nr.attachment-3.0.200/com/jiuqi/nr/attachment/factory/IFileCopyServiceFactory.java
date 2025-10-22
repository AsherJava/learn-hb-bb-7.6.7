/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.attachment.factory;

import com.jiuqi.nr.attachment.provider.IFileCopyParamProvider;
import com.jiuqi.nr.attachment.service.IFileCopyService;

public interface IFileCopyServiceFactory {
    public IFileCopyService getFileCopyService(IFileCopyParamProvider var1);
}

