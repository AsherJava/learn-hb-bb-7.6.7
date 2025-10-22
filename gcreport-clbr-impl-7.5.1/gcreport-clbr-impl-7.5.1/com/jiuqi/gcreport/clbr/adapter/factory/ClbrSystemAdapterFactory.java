/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.gcreport.clbr.adapter.factory;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.clbr.adapter.ClbrSystemAdapter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClbrSystemAdapterFactory {
    @Autowired(required=false)
    private List<ClbrSystemAdapter> clbrSystemAdapters = Collections.emptyList();

    public ClbrSystemAdapter create(String sysCode) {
        ClbrSystemAdapter systemAdapter = this.findSystemAdapter(sysCode);
        return systemAdapter;
    }

    public List<ClbrSystemAdapter> getClbrSystemAdapters() {
        return this.clbrSystemAdapters;
    }

    public ClbrSystemAdapter findSystemAdapter(String sysCode) {
        if (StringUtils.isEmpty((String)sysCode)) {
            throw new BusinessRuntimeException("\u6765\u6e90\u7cfb\u7edf\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        }
        String finalSysCode = sysCode;
        List matchClbrSystemAdapters = this.getClbrSystemAdapters().stream().filter(clbrSystemAdapter -> clbrSystemAdapter.getSysCode().equals(finalSysCode)).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(matchClbrSystemAdapters)) {
            throw new BusinessRuntimeException("\u627e\u4e0d\u5230\u534f\u540c\u4e1a\u52a1\u5904\u7406\u9002\u914d\u5668\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458\u3002");
        }
        if (matchClbrSystemAdapters.size() > 1) {
            String matchClbrSystemAdapterClassNames = matchClbrSystemAdapters.stream().map(matchClbrSystemAdapter -> matchClbrSystemAdapter.getClass().getName()).reduce("", (s1, s2) -> s1 + "," + s2);
            StringBuilder errorMsg = new StringBuilder().append(sysCode + "\u6765\u6e90\u7cfb\u7edf\u6807\u8bc6\u5728\u5e94\u7528\u4e2d\u5339\u914d\u5230[").append(matchClbrSystemAdapters.size()).append("]\u4e2a\u534f\u540c\u4e1a\u52a1\u5904\u7406\u9002\u914d\u5668[").append(matchClbrSystemAdapterClassNames).append("]\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458\u3002");
            throw new BusinessRuntimeException(errorMsg.toString());
        }
        return (ClbrSystemAdapter)matchClbrSystemAdapters.get(0);
    }
}

