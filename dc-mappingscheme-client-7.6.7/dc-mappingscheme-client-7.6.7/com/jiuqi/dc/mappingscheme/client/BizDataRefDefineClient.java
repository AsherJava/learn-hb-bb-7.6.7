/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.dc.base.common.intf.impl.TreeDTO
 *  com.jiuqi.dc.base.common.vo.SelectOptionVO
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.dc.mappingscheme.client;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.dc.base.common.intf.impl.TreeDTO;
import com.jiuqi.dc.base.common.vo.SelectOptionVO;
import com.jiuqi.dc.mappingscheme.client.common.FieldDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataRefDefineListDTO;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface BizDataRefDefineClient {
    public static final String API_BASE_PATH = "/api/datacenter/v1/ref/define/bizdata";

    @PostMapping(value={"/api/datacenter/v1/ref/define/bizdata/tree"})
    public BusinessResponseEntity<List<TreeDTO>> tree(@RequestBody DataRefDefineListDTO var1);

    @PostMapping(value={"/api/datacenter/v1/ref/define/bizdata/list"})
    public BusinessResponseEntity<List<DataMappingDefineDTO>> list(@RequestBody DataRefDefineListDTO var1);

    @GetMapping(value={"/api/datacenter/v1/ref/define/bizdata/get/{id}"})
    public BusinessResponseEntity<DataMappingDefineDTO> get(@PathVariable String var1);

    @GetMapping(value={"/api/datacenter/v1/ref/define/bizdata/table/{pluginType}"})
    public BusinessResponseEntity<List<SelectOptionVO>> listMappingTable(@PathVariable String var1);

    @PostMapping(value={"/api/datacenter/v1/ref/define/bizdata/ods_field"})
    public BusinessResponseEntity<List<FieldDTO>> listOdsFields(@RequestBody DataMappingDefineDTO var1);

    @PostMapping(value={"/api/datacenter/v1/ref/define/bizdata/create"})
    public BusinessResponseEntity<Boolean> create(@RequestBody DataMappingDefineDTO var1);

    @PostMapping(value={"/api/datacenter/v1/ref/define/bizdata/modify"})
    public BusinessResponseEntity<Boolean> modify(@RequestBody DataMappingDefineDTO var1);

    @PostMapping(value={"/api/datacenter/v1/ref/define/bizdata/delete"})
    public BusinessResponseEntity<Boolean> delete(@RequestBody DataMappingDefineDTO var1);

    @PostMapping(value={"/api/datacenter/v1/ref/define/bizdata/preview"})
    public BusinessResponseEntity<String> preview(@RequestBody DataMappingDefineDTO var1);
}

