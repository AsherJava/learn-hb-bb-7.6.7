/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.dc.base.common.intf.impl.TreeDTO
 *  com.jiuqi.dc.base.common.vo.SelectOptionVO
 *  com.jiuqi.dc.mappingscheme.client.BizDataRefDefineClient
 *  com.jiuqi.dc.mappingscheme.client.common.FieldDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataRefDefineListDTO
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.dc.mappingscheme.impl.controller;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.dc.base.common.intf.impl.TreeDTO;
import com.jiuqi.dc.base.common.vo.SelectOptionVO;
import com.jiuqi.dc.mappingscheme.client.BizDataRefDefineClient;
import com.jiuqi.dc.mappingscheme.client.common.FieldDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataRefDefineListDTO;
import com.jiuqi.dc.mappingscheme.impl.service.BizDataRefDefineService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BizDataRefDefineController
implements BizDataRefDefineClient {
    @Autowired
    private BizDataRefDefineService service;

    public BusinessResponseEntity<List<TreeDTO>> tree(@RequestBody DataRefDefineListDTO dto) {
        return BusinessResponseEntity.ok(this.service.tree(dto));
    }

    public BusinessResponseEntity<List<DataMappingDefineDTO>> list(@RequestBody DataRefDefineListDTO dto) {
        return BusinessResponseEntity.ok(this.service.list(dto));
    }

    public BusinessResponseEntity<DataMappingDefineDTO> get(@PathVariable String id) {
        return BusinessResponseEntity.ok((Object)this.service.findById(id));
    }

    public BusinessResponseEntity<List<SelectOptionVO>> listMappingTable(String pluginType) {
        return BusinessResponseEntity.ok(this.service.listMappingTable(pluginType));
    }

    public BusinessResponseEntity<List<FieldDTO>> listOdsFields(@RequestBody DataMappingDefineDTO dto) {
        return BusinessResponseEntity.ok(this.service.listOdsFields(dto));
    }

    public BusinessResponseEntity<Boolean> create(@RequestBody DataMappingDefineDTO dto) {
        return BusinessResponseEntity.ok((Object)this.service.create(dto));
    }

    public BusinessResponseEntity<Boolean> modify(@RequestBody DataMappingDefineDTO dto) {
        return BusinessResponseEntity.ok((Object)this.service.modify(dto));
    }

    public BusinessResponseEntity<Boolean> delete(@RequestBody DataMappingDefineDTO dto) {
        return BusinessResponseEntity.ok((Object)this.service.delete(dto));
    }

    public BusinessResponseEntity<String> preview(@RequestBody DataMappingDefineDTO dto) {
        return BusinessResponseEntity.ok((Object)this.service.preview(dto));
    }
}

