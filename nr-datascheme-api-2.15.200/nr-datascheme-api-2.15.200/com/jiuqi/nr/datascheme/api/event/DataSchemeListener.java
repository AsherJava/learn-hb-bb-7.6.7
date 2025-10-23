/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.api.event;

import com.jiuqi.nr.datascheme.api.DesignDataDimension;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import java.util.List;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public interface DataSchemeListener {
    public void savePostProcess(@NonNull DesignDataScheme var1, @Nullable List<DesignDataDimension> var2);

    public void updatePostProcess(@NonNull DesignDataScheme var1, @NonNull DesignDataScheme var2, @Nullable List<DesignDataDimension> var3, @Nullable List<DesignDataDimension> var4);

    public void deletePostProcess(@NonNull DesignDataScheme var1);
}

