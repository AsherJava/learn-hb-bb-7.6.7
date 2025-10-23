/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.restapi.NvwaRestApiPermissionsProvider
 *  com.jiuqi.np.core.restapi.PermissionsResourceItemDTO
 */
package com.jiuqi.nr.dafafill.auth;

import com.jiuqi.np.core.restapi.NvwaRestApiPermissionsProvider;
import com.jiuqi.np.core.restapi.PermissionsResourceItemDTO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class DatafillRestApiPermissionsProvider
implements NvwaRestApiPermissionsProvider {
    public String getProdLine() {
        return "\u62a5\u8868";
    }

    public String getCode() {
        return "nr_datafill";
    }

    public String getTitle() {
        return "\u81ea\u5b9a\u4e49\u5f55\u5165";
    }

    public int getSeq() {
        return 0;
    }

    public List<PermissionsResourceItemDTO> getChildResources() {
        ArrayList<PermissionsResourceItemDTO> items = new ArrayList<PermissionsResourceItemDTO>();
        PermissionsResourceItemDTO item = new PermissionsResourceItemDTO();
        item.setCode("nr:datafill:manage");
        item.setTitle("\u6a21\u677f\u7ba1\u7406");
        items.add(item);
        PermissionsResourceItemDTO item1 = new PermissionsResourceItemDTO();
        item1.setCode("nr:datafill:app");
        item1.setTitle("\u6a21\u578b\u5b9a\u4e49");
        items.add(item1);
        return items;
    }
}

