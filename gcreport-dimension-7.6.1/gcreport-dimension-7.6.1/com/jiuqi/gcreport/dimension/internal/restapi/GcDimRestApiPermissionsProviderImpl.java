/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.restapi.NvwaRestApiPermissionsProvider
 *  com.jiuqi.np.core.restapi.PermissionsResourceItemDTO
 */
package com.jiuqi.gcreport.dimension.internal.restapi;

import com.jiuqi.gcreport.dimension.internal.restapi.DimRestApiEnum;
import com.jiuqi.np.core.restapi.NvwaRestApiPermissionsProvider;
import com.jiuqi.np.core.restapi.PermissionsResourceItemDTO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class GcDimRestApiPermissionsProviderImpl
implements NvwaRestApiPermissionsProvider {
    private List<PermissionsResourceItemDTO> items;

    public String getProdLine() {
        return "\u5408\u5e76\u62a5\u8868";
    }

    public String getCode() {
        return "gc_dimension";
    }

    public String getTitle() {
        return "\u7ef4\u5ea6\u7ba1\u7406";
    }

    public int getSeq() {
        return 100;
    }

    public List<PermissionsResourceItemDTO> getChildResources() {
        if (null == this.items) {
            DimRestApiEnum[] values;
            ArrayList<PermissionsResourceItemDTO> titems = new ArrayList<PermissionsResourceItemDTO>();
            for (DimRestApiEnum restApiEnum : values = DimRestApiEnum.values()) {
                PermissionsResourceItemDTO item = new PermissionsResourceItemDTO();
                item.setCode(restApiEnum.getCode());
                item.setTitle(restApiEnum.getTitle());
                titems.add(item);
            }
            this.items = titems;
        }
        return this.items;
    }
}

