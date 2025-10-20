/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 *  com.jiuqi.bde.common.dto.OrgMappingDTO
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.FieldMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.impl.service.BizDataRefDefineService
 */
package com.jiuqi.bde.bizmodel.impl.orgmapping.service.impl;

import com.jiuqi.bde.bizmodel.impl.orgmapping.service.GCQueryBeforeMappingAssistMapService;
import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.bde.common.dto.OrgMappingDTO;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.client.dto.FieldMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.impl.service.BizDataRefDefineService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GCQueryBeforeMappingAssistMapServiceImpl
implements GCQueryBeforeMappingAssistMapService {
    @Autowired
    BizDataRefDefineService bizDataDefineService;

    @Override
    public Map<String, String> queryBdeAssistMappingMap(OrgMappingDTO orgMapping) {
        HashMap<String, String> assistMap = new HashMap<String, String>();
        String dataSchemeCode = orgMapping.getDataSchemeCode();
        if (StringUtils.isEmpty((String)dataSchemeCode)) {
            return assistMap;
        }
        DataMappingDefineDTO bizDataDefine = this.bizDataDefineService.findByCode(dataSchemeCode, ComputationModelEnum.CEDXBALANCE.getCode());
        List fieldMappingDefineDTOList = bizDataDefine == null ? CollectionUtils.newArrayList() : bizDataDefine.getItems();
        for (FieldMappingDefineDTO fieldMappingDefine : fieldMappingDefineDTOList) {
            assistMap.put(fieldMappingDefine.getFieldName(), fieldMappingDefine.getOdsFieldName());
        }
        return assistMap;
    }
}

