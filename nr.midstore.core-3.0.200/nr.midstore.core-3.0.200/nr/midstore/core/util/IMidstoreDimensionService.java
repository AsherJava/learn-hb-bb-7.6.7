/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 */
package nr.midstore.core.util;

import com.jiuqi.nr.common.params.DimensionValue;
import java.util.Map;
import nr.midstore.core.definition.bean.MidstoreContext;
import nr.midstore.core.definition.db.MidstoreException;

public interface IMidstoreDimensionService {
    public void createTempTable(MidstoreContext var1);

    public void closeTempTable(MidstoreContext var1);

    public String getExcutePeriod(MidstoreContext var1);

    public Map<String, DimensionValue> getDimSetMap(MidstoreContext var1) throws MidstoreException;

    public String getDePeriodFromNr(String var1, String var2);

    public String getNrPeriodFromDe(String var1, String var2);

    public String getCurPeriodByDate(String var1);

    public String getPriorPeriodByDate(String var1);

    public String getPeriodTitle(String var1, String var2);
}

