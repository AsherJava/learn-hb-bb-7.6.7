/*
 * Decompiled with CFR 0.152.
 */
package nr.midstore.core.work.service.data;

import java.util.List;
import nr.midstore.core.definition.bean.MidstoreContext;

public interface IMidstoreConditonService {
    public String getCondtionSQl(String var1);

    public String getCondtionSQl(MidstoreContext var1, String var2);

    public List<String> getDeEntityCodes(MidstoreContext var1);

    public List<List<String>> getDeEntityCodes(MidstoreContext var1, int var2);

    public String getCondtionSQl(List<String> var1, String var2);
}

