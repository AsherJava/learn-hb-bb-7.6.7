/*
 * Decompiled with CFR 0.152.
 */
package nr.single.data.datain.service;

import java.util.List;
import java.util.Map;

public interface ITaskFileMergeEntityService {
    public Map<String, List<String>> getMergeEntityList(String var1, String var2, String var3, Map<String, String> var4) throws Exception;

    public Map<String, String> getMergeEntityBenJi(String var1, String var2, String var3, Map<String, String> var4) throws Exception;

    public void beginImportFMDM(String var1, String var2, String var3, String var4);

    public void updateMergeFMDMData(String var1, String var2, String var3, Map<String, String> var4);

    public void commitImoprtFMDM();
}

