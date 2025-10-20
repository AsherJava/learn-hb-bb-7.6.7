/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.dc.integration.execute.client;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.dc.integration.execute.client.dto.ConvertExecuteDTO;
import com.jiuqi.dc.integration.execute.client.dto.ConvertLogDTO;
import com.jiuqi.dc.integration.execute.client.vo.ConvertLogVO;
import com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface ConvertClient {
    public static final String DW_CONVERT_API_BASE_PATH = "/api/datacenter/v1/ref/convert";

    @PostMapping(value={"/api/datacenter/v1/ref/convert/execute"})
    public BusinessResponseEntity<String> executeDataConvert(@RequestBody ConvertExecuteDTO var1);

    @PostMapping(value={"/api/datacenter/v1/ref/convert/log/{dataSchemeCode}/{page}/{pageSize}"})
    public BusinessResponseEntity<ConvertLogDTO> queryConvertLog(@PathVariable(value="dataSchemeCode") String var1, @PathVariable(value="page") Integer var2, @PathVariable(value="pageSize") Integer var3);

    @PostMapping(value={"/api/datacenter/v1/ref/convert/log/execute"})
    public BusinessResponseEntity<ConvertLogVO> getExecuteById(@RequestBody ConvertLogVO var1);

    @PostMapping(value={"/api/datacenter/v1/ref/define/bizdata/dataSchemeCode"})
    public BusinessResponseEntity<String> getSettingTemplate(@RequestBody DataMappingDefineDTO var1);

    @PostMapping(value={"/api/datacenter/v1/ref/convert/log/batchDeleteById"})
    public BusinessResponseEntity<Boolean> batchDeleteById(@RequestBody List<String> var1);
}

