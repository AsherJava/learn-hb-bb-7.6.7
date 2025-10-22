/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.formtype.internal.icon;

import java.io.IOException;
import java.util.Properties;

public interface IconSourceProvider {
    public String getSchemeKey();

    public String getBase64Icon(String var1) throws IOException;

    public Properties getProperties() throws IOException;
}

