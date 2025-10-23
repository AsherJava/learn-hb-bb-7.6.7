/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.transfer.engine.service.TransferResourceDataBean
 */
package com.jiuqi.nr.mapping2.service;

import com.jiuqi.bi.transfer.engine.service.TransferResourceDataBean;
import com.jiuqi.nr.mapping2.dto.NVWADataUploadParam;
import com.jiuqi.nr.mapping2.util.NvdataMappingContext;
import java.util.List;
import java.util.Map;

public interface NvdataMappingService {
    public Map<String, List<String>> getMappingParams(NVWADataUploadParam var1);

    public void saveNvdataMapping(Map<String, List<TransferResourceDataBean>> var1, NvdataMappingContext var2) throws Exception;
}

