/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.restapi.NvwaRestApiPermissionsProvider
 *  com.jiuqi.np.core.restapi.PermissionsResourceItemDTO
 */
package com.jiuqi.nr.etl.web;

import com.jiuqi.np.core.restapi.NvwaRestApiPermissionsProvider;
import com.jiuqi.np.core.restapi.PermissionsResourceItemDTO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ETLApiPermissionsProvider
implements NvwaRestApiPermissionsProvider {
    public String getProdLine() {
        return "\u62a5\u8868";
    }

    public String getCode() {
        return "ETL";
    }

    public String getTitle() {
        return "ETL";
    }

    public int getSeq() {
        return 0;
    }

    public List<PermissionsResourceItemDTO> getChildResources() {
        ArrayList<PermissionsResourceItemDTO> list = new ArrayList<PermissionsResourceItemDTO>();
        PermissionsResourceItemDTO item1 = new PermissionsResourceItemDTO();
        item1.setCode("\u62a5\u8868:ETL:\u670d\u52a1\u914d\u7f6e");
        item1.setTitle("\u670d\u52a1\u914d\u7f6e");
        list.add(item1);
        return list;
    }
}

