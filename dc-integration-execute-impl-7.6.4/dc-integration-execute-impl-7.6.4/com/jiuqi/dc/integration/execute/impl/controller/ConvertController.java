/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.dc.integration.execute.client.ConvertClient
 *  com.jiuqi.dc.integration.execute.client.dto.ConvertExecuteDTO
 *  com.jiuqi.dc.integration.execute.client.dto.ConvertLogDTO
 *  com.jiuqi.dc.integration.execute.client.vo.ConvertLogVO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.dc.integration.execute.impl.controller;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.dc.integration.execute.client.ConvertClient;
import com.jiuqi.dc.integration.execute.client.dto.ConvertExecuteDTO;
import com.jiuqi.dc.integration.execute.client.dto.ConvertLogDTO;
import com.jiuqi.dc.integration.execute.client.vo.ConvertLogVO;
import com.jiuqi.dc.integration.execute.impl.service.ConvertLogService;
import com.jiuqi.dc.integration.execute.impl.service.ConvertService;
import com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConvertController
implements ConvertClient {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ConvertService executeService;
    @Autowired
    private ConvertLogService logService;

    public BusinessResponseEntity<String> executeDataConvert(@RequestBody ConvertExecuteDTO executeParam) {
        try {
            this.executeService.convert(executeParam, false);
        }
        catch (Exception e) {
            this.logger.error("\u6267\u884c\u6570\u636e\u8f6c\u6362", e);
            return BusinessResponseEntity.error((Throwable)e);
        }
        return BusinessResponseEntity.ok();
    }

    public BusinessResponseEntity<ConvertLogDTO> queryConvertLog(@PathVariable(value="dataSchemeCode") String dataSchemeCode, @PathVariable(value="page") Integer page, @PathVariable(value="pageSize") Integer pageSize) {
        try {
            ConvertLogDTO result = this.logService.queryDataConvertLog(dataSchemeCode, page, pageSize);
            return BusinessResponseEntity.ok((Object)result);
        }
        catch (Exception e) {
            this.logger.error("\u67e5\u8be2\u8f6c\u6362\u65e5\u5fd7", e);
            return BusinessResponseEntity.error((Throwable)e);
        }
    }

    public BusinessResponseEntity<ConvertLogVO> getExecuteById(@RequestBody ConvertLogVO convertLogVO) {
        return BusinessResponseEntity.ok((Object)this.logService.getExecuteById(convertLogVO));
    }

    public BusinessResponseEntity<String> getSettingTemplate(@RequestBody DataMappingDefineDTO dataMappingDefineDTO) {
        return BusinessResponseEntity.ok((Object)this.executeService.getSettingTemplate(dataMappingDefineDTO));
    }

    public BusinessResponseEntity<Boolean> batchDeleteById(List<String> deleteIdList) {
        return BusinessResponseEntity.ok((Object)this.logService.batchDeleteById(deleteIdList));
    }
}

