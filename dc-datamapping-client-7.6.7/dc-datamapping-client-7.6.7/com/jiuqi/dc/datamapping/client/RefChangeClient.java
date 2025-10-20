/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.web.bind.annotation.PostMapping
 */
package com.jiuqi.dc.datamapping.client;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.dc.datamapping.client.dto.RefChangeDTO;
import com.jiuqi.dc.datamapping.client.vo.DataRefSaveVO;
import org.springframework.web.bind.annotation.PostMapping;

public interface RefChangeClient {
    public static final String API_BASE_PATH = "/api/datacenter/v1/ref/change";

    @PostMapping(value={"/api/datacenter/v1/ref/change/handle"})
    public BusinessResponseEntity<DataRefSaveVO> dataRefChange(RefChangeDTO var1);

    @PostMapping(value={"/api/datacenter/v1/ref/change/batchHandle"})
    public BusinessResponseEntity<DataRefSaveVO> dataRefChange(RefChangeDTO[] var1);
}

