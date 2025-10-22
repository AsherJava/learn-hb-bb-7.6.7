/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.midstore.dataexchange.services.IDataExchangeTask
 */
package nr.midstore.core.work.service.extend;

import com.jiuqi.bi.core.midstore.dataexchange.services.IDataExchangeTask;
import java.util.List;
import java.util.Map;
import nr.midstore.core.definition.bean.MidstoreContext;

public interface IMidstoreDataGetPreprocessService {
    public void preprocessDataToMidstore(MidstoreContext var1, IDataExchangeTask var2);

    public Map<String, List<String>> preprocessOrgToMidstoreScheme(String var1, String var2, List<String> var3, List<String> var4);
}

