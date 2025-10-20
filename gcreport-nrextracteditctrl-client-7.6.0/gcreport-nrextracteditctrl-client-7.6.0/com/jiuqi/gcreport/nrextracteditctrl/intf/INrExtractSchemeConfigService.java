/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.nrextracteditctrl.intf;

import com.jiuqi.gcreport.nrextracteditctrl.dto.NrSchemeConfigDTO;
import java.util.Map;

public interface INrExtractSchemeConfigService {
    public NrSchemeConfigDTO getSchemeByOrgId(String var1, String var2, String var3, Map<String, String> var4);
}

