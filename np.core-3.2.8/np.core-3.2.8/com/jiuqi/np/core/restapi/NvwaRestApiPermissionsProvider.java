/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.core.restapi;

import com.jiuqi.np.core.restapi.PermissionsResourceItemDTO;
import java.util.List;

public interface NvwaRestApiPermissionsProvider {
    public String getProdLine();

    public String getCode();

    public String getTitle();

    public int getSeq();

    public List<PermissionsResourceItemDTO> getChildResources();

    default public List<PermissionsResourceItemDTO> getDefaultAllowResources(String roleId) {
        return null;
    }
}

