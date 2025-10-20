/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.dc.base.common.vo.SelectOptionVO
 *  com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.dc.datamapping.client;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.dc.base.common.vo.SelectOptionVO;
import com.jiuqi.dc.datamapping.client.dto.DataRefAutoMatchDTO;
import com.jiuqi.dc.datamapping.client.dto.DataRefListDTO;
import com.jiuqi.dc.datamapping.client.dto.DataRefSaveDTO;
import com.jiuqi.dc.datamapping.client.vo.DataRefAutoMatchVO;
import com.jiuqi.dc.datamapping.client.vo.DataRefListVO;
import com.jiuqi.dc.datamapping.client.vo.DataRefSaveVO;
import com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface DataRefConfigureClient {
    public static final String DW_REF_API_BASE_PATH = "/api/datacenter/v1/ref/configure";

    @GetMapping(value={"/api/datacenter/v1/ref/configure/define/{dataSchemeCode}"})
    public BusinessResponseEntity<List<DataMappingDefineDTO>> listDefine(@PathVariable String var1);

    @GetMapping(value={"/api/datacenter/v1/ref/configure/define/list"})
    public BusinessResponseEntity<List<DataMappingDefineDTO>> listAllDefine();

    @PostMapping(value={"/api/datacenter/v1/ref/configure/list"})
    public BusinessResponseEntity<DataRefListVO> list(@RequestBody DataRefListDTO var1);

    @PostMapping(value={"/api/datacenter/v1/ref/configure/save"})
    public BusinessResponseEntity<DataRefSaveVO> save(@RequestBody DataRefSaveDTO var1);

    @PostMapping(value={"/api/datacenter/v1/ref/configure/clean"})
    public BusinessResponseEntity<Integer> clean(@RequestBody DataRefSaveDTO var1);

    @PostMapping(value={"/api/datacenter/v1/ref/configure/auto_match"})
    public BusinessResponseEntity<DataRefAutoMatchVO> autoMatch(@RequestBody DataRefAutoMatchDTO var1);

    @GetMapping(value={"/api/datacenter/v1/ref/configure/matchRuleList"})
    public BusinessResponseEntity<List<SelectOptionVO>> matchRuleList();

    @PostMapping(value={"/api/datacenter/v1/ref/configure/define/listBySchemeCode"})
    public BusinessResponseEntity<List<BaseDataMappingDefineDTO>> listDefineBySchemeCode(@RequestBody List<String> var1);
}

