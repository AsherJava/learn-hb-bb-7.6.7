/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.va.domain.common.PageVO
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.common.rate.client;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.rate.client.dto.ConvertRateSchemeDTO;
import com.jiuqi.common.rate.client.vo.ConvertRateSchemeVO;
import com.jiuqi.va.domain.common.PageVO;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface ConvertRateSchemeClient {
    public static final String CONVERSION_RATE_API_BASE_PATH = "/api/gcreport/v1/common/rate/ratescheme";

    @PostMapping(value={"/api/gcreport/v1/common/rate/ratescheme/query"})
    public BusinessResponseEntity<PageVO<ConvertRateSchemeVO>> query(@RequestBody ConvertRateSchemeDTO var1);

    @PostMapping(value={"/api/gcreport/v1/common/rate/ratescheme/save"})
    public BusinessResponseEntity<PageVO<ConvertRateSchemeVO>> save(@RequestBody ConvertRateSchemeDTO var1);

    @PostMapping(value={"/api/gcreport/v1/common/rate/ratescheme/getBySubjCodes"})
    public BusinessResponseEntity<Map<String, ConvertRateSchemeVO>> getBySubjCodes(@RequestBody List<String> var1);
}

