/*
 * Decompiled with CFR 0.152.
 */
package nr.single.map.data.facade;

import nr.single.map.data.facade.SingleMapFormSchemeDefine;

public interface ISingleMapNrController {
    public SingleMapFormSchemeDefine CreateSingleMapDefine();

    public SingleMapFormSchemeDefine QuerySingleMapDefine(String var1, String var2);

    public SingleMapFormSchemeDefine QueryAndCreateSingleMapDefine(String var1, String var2);

    public void UpdateSingleMapDefine(String var1, String var2, SingleMapFormSchemeDefine var3);
}

