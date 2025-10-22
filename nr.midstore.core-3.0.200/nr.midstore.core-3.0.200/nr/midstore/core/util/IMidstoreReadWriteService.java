/*
 * Decompiled with CFR 0.152.
 */
package nr.midstore.core.util;

import java.util.List;
import java.util.Set;
import nr.midstore.core.definition.bean.MidstoreContext;
import nr.midstore.core.definition.db.MidstoreException;

public interface IMidstoreReadWriteService {
    public List<String> getUnitcodeByUnitState(MidstoreContext var1, List<String> var2) throws MidstoreException;

    public Set<String> getUnitcodeByOnlyUnitState(MidstoreContext var1, List<String> var2) throws MidstoreException;

    public boolean isAdmin();

    public Set<String> getCanReadUnitList(MidstoreContext var1, String var2) throws MidstoreException;

    public Set<String> getCanWriteUnitList(MidstoreContext var1, String var2) throws MidstoreException;
}

