/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.common.intf.impl.Entity
 *  com.jiuqi.dc.base.common.utils.Pair
 */
package com.jiuqi.dc.base.impl.etl;

import com.jiuqi.dc.base.common.intf.impl.Entity;
import com.jiuqi.dc.base.common.utils.Pair;
import java.util.List;
import java.util.Map;

public interface EtlService {
    public Pair<Boolean, String> excuteEtlProcess(String var1, Map<String, String> var2);

    public Pair<Boolean, String> excuteEtlProcess(String var1, String var2);

    public List<Entity> getEtlTaskList(String var1, String var2);

    public Pair<Boolean, String> excuteEtlProcess(String var1, String var2, Map<String, String> var3, Boolean var4);
}

