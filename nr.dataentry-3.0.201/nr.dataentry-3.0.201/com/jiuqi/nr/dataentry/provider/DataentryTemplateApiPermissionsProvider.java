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
public class DataentryTemplateApiPermissionsProvider
implements NvwaRestApiPermissionsProvider {
    public String getProdLine() {
        return "\u62a5\u8868";
    }

    public String getCode() {
        return "nr_dataentry_template";
    }

    public String getTitle() {
        return "\u6570\u636e\u5f55\u5165\u6a21\u677f";
    }

    public int getSeq() {
        return 0;
    }

    public List<PermissionsResourceItemDTO> getChildResources() {
        ArrayList<PermissionsResourceItemDTO> list = new ArrayList<PermissionsResourceItemDTO>();
        PermissionsResourceItemDTO item1 = new PermissionsResourceItemDTO();
        item1.setCode("nr:dataentryTemplate:manage");
        item1.setTitle("\u6a21\u677f\u7ba1\u7406");
        list.add(item1);
        return list;
    }
}

