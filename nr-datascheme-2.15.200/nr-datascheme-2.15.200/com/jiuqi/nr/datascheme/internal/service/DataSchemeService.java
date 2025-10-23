/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.internal.service;

import com.jiuqi.nr.datascheme.internal.dto.DataDimDTO;
import com.jiuqi.nr.datascheme.internal.dto.DataSchemeDTO;
import java.util.List;

public interface DataSchemeService {
    public DataSchemeDTO getDataScheme(String var1);

    public DataSchemeDTO getDataSchemeByCode(String var1);

    public List<DataSchemeDTO> getDataSchemeByParent(String var1);

    public List<DataSchemeDTO> getDataSchemes(List<String> var1);

    public List<DataSchemeDTO> getAllDataScheme();

    public List<DataSchemeDTO> searchByKeyword(String var1);

    public List<DataDimDTO> getDataSchemeDimension(String var1);
}

