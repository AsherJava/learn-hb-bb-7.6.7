/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  nr.single.map.data.exception.SingleDataException
 */
package nr.single.client.service;

import java.util.Set;
import nr.single.map.data.exception.SingleDataException;

public interface ISingleDataCheckService {
    public Set<String> analFormulaExpToTables(String var1, String var2, String var3, String var4, boolean var5) throws SingleDataException;
}

