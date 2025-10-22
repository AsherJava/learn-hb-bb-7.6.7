/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.text.service;

import com.jiuqi.nr.data.text.param.TextParams;
import java.io.IOException;

public interface ExpTextService {
    public String downloadTextData(TextParams var1) throws IOException;

    public String downloadTextData(TextParams var1, String var2) throws IOException;
}

