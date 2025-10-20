/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.meta.MetaTreeInfoDTO
 *  com.jiuqi.va.domain.meta.MetaType
 *  com.jiuqi.va.domain.meta.ModuleDTO
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  org.springframework.http.HttpHeaders
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestHeader
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.bizmeta.controller;

import com.jiuqi.va.bizmeta.common.utils.ModulesServerProvider;
import com.jiuqi.va.bizmeta.domain.multimodule.ModuleServer;
import com.jiuqi.va.bizmeta.domain.multimodule.Modules;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.meta.MetaTreeInfoDTO;
import com.jiuqi.va.domain.meta.MetaType;
import com.jiuqi.va.domain.meta.ModuleDTO;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/biz/meta/module"})
public class MetaDataModulesController {
    @PostMapping(value={"/get"})
    public R getModuleByName(@RequestHeader HttpHeaders headers, @RequestBody ModuleDTO moduleDTO) {
        ModuleServer moduleServer = ModulesServerProvider.getModuleServer(moduleDTO.getModuleName(), moduleDTO.getFunctionType());
        if (moduleServer == null) {
            return R.error((String)String.format("\u672a\u627e\u5230\u6a21\u5757\u6807\u8bc6\u4e3a%s\u5bf9\u5e94\u7684\u6a21\u5757", moduleDTO.getModuleName()));
        }
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("name", moduleServer.getName());
        map.put("title", moduleServer.getTitle());
        if (this.isFeignClient(headers)) {
            map.put("server", moduleServer.getServer());
        }
        map.put("path", "/".equals(moduleServer.getPath()) ? "" : moduleServer.getPath());
        return R.ok(map);
    }

    private boolean isFeignClient(HttpHeaders headers) {
        List list = headers.get((Object)"feignclient");
        return list != null && list.size() > 0 && Boolean.valueOf((String)list.get(0)) != false;
    }

    @PostMapping(value={"/list"})
    public List<MetaTreeInfoDTO> getAllModule(@RequestBody TenantDO param) {
        ArrayList<MetaTreeInfoDTO> modules = new ArrayList<MetaTreeInfoDTO>();
        for (ModuleServer moduleServer : Modules.getModules()) {
            MetaTreeInfoDTO module = new MetaTreeInfoDTO();
            module.setName(moduleServer.getName());
            module.setTitle(moduleServer.getTitle());
            module.setType(MetaType.MODULE);
            modules.add(module);
        }
        return modules;
    }
}

