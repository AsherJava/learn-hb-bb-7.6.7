/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package nr.single.client.service.export;

import com.jiuqi.nr.jtable.params.base.JtableContext;
import nr.single.client.bean.JIODeleteResultObject;

public interface IBatchDeleteRegionDataService {
    public JIODeleteResultObject clearRegiondata(JtableContext var1, String var2, String var3);

    public JIODeleteResultObject clearFormdata(JtableContext var1, String var2);
}

