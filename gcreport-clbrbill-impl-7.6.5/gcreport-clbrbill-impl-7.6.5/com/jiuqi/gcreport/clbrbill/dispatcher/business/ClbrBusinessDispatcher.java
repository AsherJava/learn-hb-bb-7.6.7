/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 */
package com.jiuqi.gcreport.clbrbill.dispatcher.business;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.gcreport.clbrbill.dispatcher.business.ClbrBusinessHandler;
import com.jiuqi.gcreport.clbrbill.dto.ClbrMetaInfo;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClbrBusinessDispatcher {
    @Autowired
    private List<ClbrBusinessHandler> clbrBusinessHandlerList;

    public ClbrBusinessHandler dispatch(ClbrMetaInfo meta) {
        String businessCode = meta.getBusinessCode();
        String sysCode = meta.getSysCode();
        Map<String, ClbrBusinessHandler> map = this.getClbrBusinessCodeHandlerMap();
        for (Map.Entry<String, ClbrBusinessHandler> entry : map.entrySet()) {
            String key = entry.getKey();
            if (!key.equals(sysCode + "_" + businessCode)) continue;
            return entry.getValue();
        }
        if (!map.containsKey("DEFAULT_" + businessCode)) {
            throw new BusinessRuntimeException("\u4e1a\u52a1\u7f16\u7801\u4e3a" + businessCode + "\u7684\u9ed8\u8ba4\u7cfb\u7edf\u6807\u8bc6\u5904\u7406\u5668\u4e0d\u5b58\u5728");
        }
        return map.get("DEFAULT_" + businessCode);
    }

    private Map<String, ClbrBusinessHandler> getClbrBusinessCodeHandlerMap() {
        HashMap<String, ClbrBusinessHandler> map = new HashMap<String, ClbrBusinessHandler>();
        for (ClbrBusinessHandler handler : this.clbrBusinessHandlerList) {
            String[] sysCodes;
            for (String code : sysCodes = handler.getSysCode()) {
                String key = code + "_" + handler.getBusinessCode();
                if (map.containsKey(key)) {
                    throw new BusinessRuntimeException("\u534f\u540c\u4e1a\u52a1\u5904\u7406\u5668\u5b58\u5728\u91cd\u590d\u7684\u4e1a\u52a1\u7f16\u7801\u548c\u7cfb\u7edf\u6807\u8bc6:" + key);
                }
                map.put(key, handler);
            }
        }
        return map;
    }
}

