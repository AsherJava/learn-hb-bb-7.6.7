/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zbquery.extend;

import com.jiuqi.nr.zbquery.extend.IZBQueryExtendProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ZBQueryExtendProviderManager {
    @Autowired(required=false)
    private IZBQueryExtendProvider extendProvider;

    public IZBQueryExtendProvider getExtendProvider() {
        return this.extendProvider;
    }
}

