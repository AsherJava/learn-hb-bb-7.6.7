/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.dc.mappingscheme.client;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.dc.mappingscheme.client.common.SchemeDefaultDataVO;
import com.jiuqi.dc.mappingscheme.client.common.SchemeDefaultVO;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import com.jiuqi.dc.mappingscheme.client.vo.SchemeDimVo;
import java.util.Set;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface DataSchemeInitClient {
    public static final String API_BASE_PATH = "/api/datacenter/v1/ref/init/scheme";

    @PostMapping(value={"/api/datacenter/v1/ref/init/scheme/default/data"})
    public BusinessResponseEntity<SchemeDefaultDataVO> getDefaultSchemeData(@RequestBody DataSchemeDTO var1);

    @PostMapping(value={"/api/datacenter/v1/ref/init/scheme/defaultScheme/data"})
    public BusinessResponseEntity<SchemeDefaultVO> getDefaultData(@RequestBody DataSchemeDTO var1);

    @GetMapping(value={"/api/datacenter/v1/ref/init/scheme/plugin/dim/list/{pluginType}"})
    public BusinessResponseEntity<Set<SchemeDimVo>> getDimList(@PathVariable String var1);
}

