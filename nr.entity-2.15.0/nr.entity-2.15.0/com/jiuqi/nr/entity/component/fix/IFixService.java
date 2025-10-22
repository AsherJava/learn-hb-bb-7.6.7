/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.entity.component.fix;

import com.jiuqi.nr.entity.component.fix.dto.OrgDataFixDTO;
import com.jiuqi.nr.entity.component.fix.dto.SimpleDefineDTO;
import java.util.List;

public interface IFixService {
    public List<SimpleDefineDTO> listOrg();

    public List<SimpleDefineDTO> listOrgVersion(String var1);

    public List<OrgDataFixDTO> fixParents(String var1, String var2);

    public void fixOrdinal(String var1, String var2);
}

