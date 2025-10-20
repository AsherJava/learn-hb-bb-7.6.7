/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.org.impl.tablepaste.service;

import com.jiuqi.gcreport.org.impl.tablepaste.vo.PasteParamVO;
import java.util.List;
import java.util.Map;

public interface TableCopyService {
    public List<Map<String, Object>> transformData(PasteParamVO var1);
}

