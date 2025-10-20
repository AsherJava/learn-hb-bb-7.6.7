/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.dc.base.common.vo.SelectOptionVO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.ModelAttribute
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestParam
 */
package com.jiuqi.dc.base.client.assistdim;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.dc.base.client.assistdim.dto.AssistDimDTO;
import com.jiuqi.dc.base.common.vo.SelectOptionVO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface AssistDimClient {
    public static final String API_BASE_PATH = "/api/datacenter/v1/base/assistdim";

    @PostMapping(value={"/api/datacenter/v1/base/assistdim/sync_cache"})
    public BusinessResponseEntity<Boolean> syncCache();

    @GetMapping(value={"/api/datacenter/v1/base/assistdim/list"})
    public BusinessResponseEntity<List<AssistDimDTO>> list(@ModelAttribute BaseDataDTO var1);

    @PostMapping(value={"/api/datacenter/v1/base/assistdim/list_by_table/{effectable}"})
    public BusinessResponseEntity<List<AssistDimDTO>> list(@PathVariable(name="effectable") String var1);

    @GetMapping(value={"/api/datacenter/v1/base/assistdim/published"})
    public BusinessResponseEntity<List<AssistDimDTO>> listPublished();

    @GetMapping(value={"/api/datacenter/v1/base/assistdim/valuetype"})
    public BusinessResponseEntity<List<SelectOptionVO>> listValueType();

    @GetMapping(value={"/api/datacenter/v1/base/assistdim/effectable"})
    public BusinessResponseEntity<List<SelectOptionVO>> listEffectTable(@RequestParam(required=false, name="type") String var1);

    @GetMapping(value={"/api/datacenter/v1/base/assistdim/get/{id}"})
    public BusinessResponseEntity<AssistDimDTO> get(@PathVariable UUID var1);

    @PostMapping(value={"/api/datacenter/v1/base/assistdim/create"})
    public BusinessResponseEntity<Boolean> create(@RequestBody AssistDimDTO var1);

    @PostMapping(value={"/api/datacenter/v1/base/assistdim/modify"})
    public BusinessResponseEntity<Boolean> modify(@RequestBody AssistDimDTO var1);

    @PostMapping(value={"/api/datacenter/v1/base/assistdim/delete"})
    public BusinessResponseEntity<Boolean> delete(@RequestBody AssistDimDTO var1);

    @PostMapping(value={"/api/datacenter/v1/base/assistdim/publish"})
    public BusinessResponseEntity<Boolean> publish(@RequestBody AssistDimDTO var1);

    @PostMapping(value={"/api/datacenter/v1/base/assistdim/save_publish"})
    public BusinessResponseEntity<Boolean> savePublish(@RequestBody AssistDimDTO var1);
}

