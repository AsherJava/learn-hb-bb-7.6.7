/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.AssistExtendDimClient
 *  com.jiuqi.bde.bizmodel.client.vo.AssistExtendDimVO
 *  com.jiuqi.common.base.BusinessRuntimeException
 */
package com.jiuqi.gc.financialcubes.query.utils;

import com.jiuqi.bde.bizmodel.client.AssistExtendDimClient;
import com.jiuqi.bde.bizmodel.client.vo.AssistExtendDimVO;
import com.jiuqi.common.base.BusinessRuntimeException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DimensionSearchService {
    @Autowired
    private AssistExtendDimClient assistExtendDimClient;

    public Map<String, String> getCodeRefFieldMap() {
        HashMap<String, String> codeRefFieldMap = new HashMap<String, String>();
        if (this.assistExtendDimClient.getAllStartAssistExtendDim() != null) {
            List dataList = (List)this.assistExtendDimClient.getAllStartAssistExtendDim().getData();
            for (AssistExtendDimVO data : dataList) {
                codeRefFieldMap.put(data.getCode(), data.getRefField());
            }
        } else {
            throw new BusinessRuntimeException("\u83b7\u53d6\u7ef4\u5ea6\u5c5e\u6027\u4fe1\u606f\u51fa\u9519");
        }
        return codeRefFieldMap;
    }
}

