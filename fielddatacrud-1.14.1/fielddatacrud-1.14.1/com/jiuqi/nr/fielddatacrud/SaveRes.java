/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datacrud.ReturnRes
 */
package com.jiuqi.nr.fielddatacrud;

import com.jiuqi.nr.datacrud.ReturnRes;
import java.util.Collection;
import java.util.Map;

public interface SaveRes {
    public Collection<String> getNoPermissionDw();

    public Collection<String> getSaveDw();

    public Collection<String> getFailDw();

    public ReturnRes getFailMessage(String var1);

    public Map<String, ReturnRes> getFailMessages();

    default public int getCount() {
        return 0;
    }
}

