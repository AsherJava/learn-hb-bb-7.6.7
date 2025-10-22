/*
 * Decompiled with CFR 0.152.
 */
package nr.midstore.core.param.service;

import java.util.List;
import nr.midstore.core.definition.dto.MidstoreSchemeDTO;

public interface IMidstoreSchemeQueryService {
    public List<MidstoreSchemeDTO> getSchemesByOrgCode(String var1, String var2);

    public List<MidstoreSchemeDTO> getSchemesByCodes(String var1, List<String> var2);

    public List<MidstoreSchemeDTO> getSchemesByTask(String var1);
}

