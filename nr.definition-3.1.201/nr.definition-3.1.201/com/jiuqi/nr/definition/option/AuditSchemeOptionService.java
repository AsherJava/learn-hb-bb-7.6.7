/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.option;

import com.jiuqi.nr.definition.option.dto.AuditSchemeDTO;
import java.util.List;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public interface AuditSchemeOptionService {
    public List<AuditSchemeDTO> getAllItemsInAuditScheme(String var1);

    public List<AuditSchemeDTO> getAllAuditTypesInScheme(String var1);

    @Nullable
    public AuditSchemeDTO getAuditTypeInScheme(String var1, @NonNull String var2);

    @Nullable
    public String getAuditTypeValueInScheme(String var1, @NonNull String var2);

    @Nullable
    public AuditSchemeDTO getConditionInScheme(String var1);

    @Nullable
    public String getConditionValueInScheme(String var1);
}

