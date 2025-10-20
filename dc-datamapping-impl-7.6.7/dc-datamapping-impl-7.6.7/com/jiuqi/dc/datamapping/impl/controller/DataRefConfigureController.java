/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.dc.base.common.vo.SelectOptionVO
 *  com.jiuqi.dc.datamapping.client.DataRefConfigureClient
 *  com.jiuqi.dc.datamapping.client.dto.DataRefAutoMatchDTO
 *  com.jiuqi.dc.datamapping.client.dto.DataRefListDTO
 *  com.jiuqi.dc.datamapping.client.dto.DataRefSaveDTO
 *  com.jiuqi.dc.datamapping.client.vo.DataRefAutoMatchVO
 *  com.jiuqi.dc.datamapping.client.vo.DataRefListVO
 *  com.jiuqi.dc.datamapping.client.vo.DataRefSaveVO
 *  com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSchemeService
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.dc.datamapping.impl.controller;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.dc.base.common.vo.SelectOptionVO;
import com.jiuqi.dc.datamapping.client.DataRefConfigureClient;
import com.jiuqi.dc.datamapping.client.dto.DataRefAutoMatchDTO;
import com.jiuqi.dc.datamapping.client.dto.DataRefListDTO;
import com.jiuqi.dc.datamapping.client.dto.DataRefSaveDTO;
import com.jiuqi.dc.datamapping.client.vo.DataRefAutoMatchVO;
import com.jiuqi.dc.datamapping.client.vo.DataRefListVO;
import com.jiuqi.dc.datamapping.client.vo.DataRefSaveVO;
import com.jiuqi.dc.datamapping.impl.gather.impl.DataRefConfigureServiceGather;
import com.jiuqi.dc.datamapping.impl.service.DataRefConfigureService;
import com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import com.jiuqi.dc.mappingscheme.impl.service.DataSchemeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataRefConfigureController
implements DataRefConfigureClient {
    @Autowired
    private DataRefConfigureService service;
    @Autowired
    private DataRefConfigureServiceGather dataRefConfigureServiceGather;
    @Autowired
    private DataSchemeService dataSchemeService;

    public BusinessResponseEntity<List<DataMappingDefineDTO>> listDefine(@PathVariable String dataSchemeCode) {
        return BusinessResponseEntity.ok(this.service.listDefine(dataSchemeCode));
    }

    public BusinessResponseEntity<List<DataMappingDefineDTO>> listAllDefine() {
        return BusinessResponseEntity.ok(this.service.listAllDefine());
    }

    public BusinessResponseEntity<DataRefListVO> list(@RequestBody DataRefListDTO dto) {
        DataSchemeDTO dataSchemeDTO = new DataSchemeDTO();
        if (dto.getDataSchemeCode() != null) {
            dataSchemeDTO = this.dataSchemeService.getByCode(dto.getDataSchemeCode());
        }
        return BusinessResponseEntity.ok((Object)this.dataRefConfigureServiceGather.getDataRefConfigureServiceBySourceDataType(dataSchemeDTO.getSourceDataType()).list(dto));
    }

    public BusinessResponseEntity<DataRefSaveVO> save(@RequestBody DataRefSaveDTO dto) {
        DataRefSaveVO save = this.service.save(dto);
        if (save.getErrorMessage() == null || save.getErrorMessage().isEmpty()) {
            return BusinessResponseEntity.ok((Object)save);
        }
        return BusinessResponseEntity.error((String)save.getErrorMessageStr());
    }

    public BusinessResponseEntity<Integer> clean(@RequestBody DataRefSaveDTO dto) {
        return BusinessResponseEntity.ok((Object)this.service.clean(dto));
    }

    public BusinessResponseEntity<DataRefAutoMatchVO> autoMatch(@RequestBody DataRefAutoMatchDTO dto) {
        return BusinessResponseEntity.ok((Object)this.service.autoMatch(dto));
    }

    public BusinessResponseEntity<List<SelectOptionVO>> matchRuleList() {
        return BusinessResponseEntity.ok(this.service.matchRuleList());
    }

    public BusinessResponseEntity<List<BaseDataMappingDefineDTO>> listDefineBySchemeCode(List<String> codes) {
        return BusinessResponseEntity.ok(this.service.listDefineBySchemeCode(codes));
    }
}

