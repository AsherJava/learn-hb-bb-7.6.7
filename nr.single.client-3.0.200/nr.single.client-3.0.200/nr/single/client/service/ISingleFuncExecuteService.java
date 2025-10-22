/*
 * Decompiled with CFR 0.152.
 */
package nr.single.client.service;

import java.io.IOException;
import nr.single.client.bean.SingleExportData;
import nr.single.client.bean.SingleExportParam;

public interface ISingleFuncExecuteService {
    public SingleExportData export(SingleExportParam var1) throws IOException;
}

