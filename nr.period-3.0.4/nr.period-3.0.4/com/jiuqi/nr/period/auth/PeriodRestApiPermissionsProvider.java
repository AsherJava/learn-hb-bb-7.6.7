/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.restapi.NvwaRestApiPermissionsProvider
 *  com.jiuqi.np.core.restapi.PermissionsResourceItemDTO
 */
package com.jiuqi.nr.period.auth;

import com.jiuqi.np.core.restapi.NvwaRestApiPermissionsProvider;
import com.jiuqi.np.core.restapi.PermissionsResourceItemDTO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class PeriodRestApiPermissionsProvider
implements NvwaRestApiPermissionsProvider {
    public String getProdLine() {
        return "\u62a5\u8868";
    }

    public String getCode() {
        return "nr_period";
    }

    public String getTitle() {
        return "\u65f6\u671f\u7ba1\u7406";
    }

    public int getSeq() {
        return 0;
    }

    public List<PermissionsResourceItemDTO> getChildResources() {
        ArrayList<PermissionsResourceItemDTO> items = new ArrayList<PermissionsResourceItemDTO>();
        PermissionsResourceItemDTO item = new PermissionsResourceItemDTO();
        item.setCode("nr:period_tree:query");
        item.setTitle("\u5b9a\u4e49\u67e5\u8be2");
        items.add(item);
        PermissionsResourceItemDTO item1 = new PermissionsResourceItemDTO();
        item1.setCode("nr:period_data:query");
        item1.setTitle("\u6570\u636e\u67e5\u8be2");
        items.add(item1);
        return items;
    }
}

