/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.midstore.core.definition.db.MidstoreException
 */
package nr.midstore2.data.util;

import com.jiuqi.nvwa.midstore.core.definition.db.MidstoreException;
import java.util.List;
import java.util.Set;
import nr.midstore2.data.extension.bean.ReportMidstoreContext;

public interface IReportMidstoreReadWriteService {
    public List<String> getUnitcodeByUnitState(ReportMidstoreContext var1, List<String> var2) throws MidstoreException;

    public Set<String> getUnitcodeByOnlyUnitState(ReportMidstoreContext var1, List<String> var2) throws MidstoreException;

    public boolean isAdmin();

    public Set<String> getCanReadUnitList(ReportMidstoreContext var1, String var2) throws MidstoreException;

    public Set<String> getCanWriteUnitList(ReportMidstoreContext var1, String var2) throws MidstoreException;
}

