/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.restapi.NvwaRestApiPermissionsProvider
 *  com.jiuqi.np.core.restapi.PermissionsResourceItemDTO
 */
package com.jiuqi.nr.dataentry.provider;

import com.jiuqi.np.core.restapi.NvwaRestApiPermissionsProvider;
import com.jiuqi.np.core.restapi.PermissionsResourceItemDTO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class OverviewNvwaRestApiPermissionsProvider
implements NvwaRestApiPermissionsProvider {
    public String getProdLine() {
        return "\u62a5\u8868";
    }

    public String getCode() {
        return "nr_overview";
    }

    public String getTitle() {
        return "\u4e0a\u62a5\u7eb5\u89c8";
    }

    public int getSeq() {
        return 0;
    }

    public List<PermissionsResourceItemDTO> getChildResources() {
        ArrayList<PermissionsResourceItemDTO> items = new ArrayList<PermissionsResourceItemDTO>();
        PermissionsResourceItemDTO item = new PermissionsResourceItemDTO();
        item.setCode("nr:overview:query");
        item.setTitle("\u67e5\u8be2");
        items.add(item);
        PermissionsResourceItemDTO item1 = new PermissionsResourceItemDTO();
        item1.setCode("nr:overview:export");
        item1.setTitle("\u5bfc\u51fa");
        items.add(item1);
        return items;
    }
}

