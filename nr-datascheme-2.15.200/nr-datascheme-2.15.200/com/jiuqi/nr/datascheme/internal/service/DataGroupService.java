/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.internal.service;

import com.jiuqi.nr.datascheme.internal.dto.DataGroupDTO;
import java.util.List;

public interface DataGroupService {
    public List<DataGroupDTO> searchBy(String var1, String var2, int var3);

    public List<DataGroupDTO> searchBy(List<String> var1, String var2, int var3);

    public List<DataGroupDTO> searchBy(String var1);

    public List<DataGroupDTO> searchBy(String var1, String var2);
}

