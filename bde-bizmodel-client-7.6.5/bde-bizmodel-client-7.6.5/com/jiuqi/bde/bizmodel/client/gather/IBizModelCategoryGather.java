/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.bizmodel.client.gather;

import com.jiuqi.bde.bizmodel.client.intf.IBizModelCategory;
import java.util.List;

public interface IBizModelCategoryGather {
    public List<IBizModelCategory> list();

    public IBizModelCategory get(String var1);
}

