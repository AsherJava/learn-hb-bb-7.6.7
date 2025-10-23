/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.subdatabase.provider;

import com.jiuqi.nr.subdatabase.facade.SubDataBase;
import java.util.Set;

public interface SubDataBaseCustomTableProvider {
    public void createCustomTable(String var1, SubDataBase var2);

    public void deleteCustomTable(String var1, SubDataBase var2);

    public void syncCustomTable(String var1, SubDataBase var2);

    public Set<String> getCustomTableNames(String var1);
}

