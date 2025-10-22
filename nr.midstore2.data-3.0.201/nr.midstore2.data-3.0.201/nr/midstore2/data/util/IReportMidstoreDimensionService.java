/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nvwa.midstore.core.definition.db.MidstoreException
 */
package nr.midstore2.data.util;

import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nvwa.midstore.core.definition.db.MidstoreException;
import java.util.List;
import java.util.Map;
import nr.midstore2.data.extension.bean.ReportMidstoreContext;

public interface IReportMidstoreDimensionService {
    public void createTempTable(ReportMidstoreContext var1);

    public void closeTempTable(ReportMidstoreContext var1);

    public String getExcuteNrPeriod(ReportMidstoreContext var1);

    public Map<String, DimensionValue> getDimSetMap(ReportMidstoreContext var1) throws MidstoreException;

    public String getDePeriodFromNr(String var1, String var2);

    public String getNrPeriodFromDe(String var1, String var2);

    public String getCurPeriodByDate(String var1);

    public String getPriorPeriodByDate(String var1);

    public String getPeriodTitle(String var1, String var2);

    public List<String> getDimNamesByFormScheme(FormSchemeDefine var1);
}

