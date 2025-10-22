/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.itreebase.nodeicon;

import com.jiuqi.nr.itreebase.nodeicon.IconSource;
import com.jiuqi.nr.itreebase.nodeicon.IconSourceProvider;
import com.jiuqi.nr.itreebase.nodeicon.IconSourceScheme;

public interface IconSourceHelper {
    public IconSourceProvider getIconProvider(String var1, IconSourceScheme[] var2);

    public IconSource toIconSource(IconSourceScheme var1, String var2);

    public byte[] toByteArray(IconSourceScheme var1, String var2);

    public String toBase64String(IconSourceScheme var1, String var2);

    public String toImgSrc(IconSourceScheme var1, String var2);

    public String toImgSrc(IconSource var1);
}

