/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.restapi.NvwaRestApiPermissionsProvider
 *  com.jiuqi.np.core.restapi.PermissionsResourceItemDTO
 */
package com.jiuqi.gcreport.invest.restapi;

import com.jiuqi.gcreport.invest.restapi.InvestRestApiEnum;
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
        return "gc_invest";
    }

    public String getTitle() {
        return "\u6295\u8d44\u53f0\u8d26";
    }

    public int getSeq() {
        return 100;
    }

    public List<PermissionsResourceItemDTO> getChildResources() {
        if (null == this.items) {
            InvestRestApiEnum[] values;
            ArrayList<PermissionsResourceItemDTO> titems = new ArrayList<PermissionsResourceItemDTO>();
            for (InvestRestApiEnum restApiEnum : values = InvestRestApiEnum.values()) {
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

