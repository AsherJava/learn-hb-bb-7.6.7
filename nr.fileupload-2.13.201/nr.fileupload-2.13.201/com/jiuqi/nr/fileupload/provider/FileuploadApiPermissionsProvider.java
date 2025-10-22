/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.restapi.NvwaRestApiPermissionsProvider
 *  com.jiuqi.np.core.restapi.PermissionsResourceItemDTO
 */
package com.jiuqi.nr.fileupload.provider;

import com.jiuqi.np.core.restapi.NvwaRestApiPermissionsProvider;
import com.jiuqi.np.core.restapi.PermissionsResourceItemDTO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class FileuploadApiPermissionsProvider
implements NvwaRestApiPermissionsProvider {
    public String getProdLine() {
        return "\u62a5\u8868";
    }

    public String getCode() {
        return "nr_fileupload";
    }

    public String getTitle() {
        return "\u4e0a\u4f20\u7ec4\u4ef6";
    }

    public int getSeq() {
        return 0;
    }

    public List<PermissionsResourceItemDTO> getChildResources() {
        ArrayList<PermissionsResourceItemDTO> list = new ArrayList<PermissionsResourceItemDTO>();
        PermissionsResourceItemDTO item1 = new PermissionsResourceItemDTO();
        item1.setCode("nr:fileupload:fileupload");
        item1.setTitle("\u6587\u4ef6\u4e0a\u4f20");
        list.add(item1);
        return list;
    }
}

