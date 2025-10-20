/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.node.ObjectNode
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.meta.MetaInfoDTO
 *  com.jiuqi.va.domain.meta.OperateType
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.bizmeta.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jiuqi.va.bizmeta.domain.metagroup.MetaGroupDTO;
import com.jiuqi.va.bizmeta.domain.metainfo.MetaInfoPageDTO;
import com.jiuqi.va.bizmeta.service.IMetaInfoService;
import com.jiuqi.va.bizmeta.service.impl.MetaGroupService;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.meta.MetaInfoDTO;
import com.jiuqi.va.domain.meta.OperateType;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/bill/vaos"})
public class BillRouteConfigController {
    @Autowired
    private IMetaInfoService metaInfoService;
    @Autowired
    private MetaGroupService metaGroupService;

    @GetMapping(value={"/metaType/get"})
    public Object getMeta() {
        String modes = "[{\"title\": \"\u5355\u636e\u7ba1\u7406\", \"code\": \"bill\"},{\"title\": \"\u5355\u636e\u5217\u8868\u7ba1\u7406\", \"code\": \"billlist\"},{\"title\": \"\u5de5\u4f5c\u6d41\u7ba1\u7406\", \"code\": \"workflow\"}]";
        return JSONUtil.parseArray((String)modes);
    }

    @GetMapping(value={"/bill/get"})
    public Object getBillDefines() {
        List<ObjectNode> objects = this.getJsonObjects("bill");
        return JSONUtil.parseArray((String)JSONUtil.toJSONString(objects));
    }

    @GetMapping(value={"/billlist/get"})
    public Object getBillListDefines() {
        List<ObjectNode> objects = this.getJsonObjects("billlist");
        return JSONUtil.parseArray((String)JSONUtil.toJSONString(objects));
    }

    private List<ObjectNode> getJsonObjects(String name) {
        MetaInfoPageDTO infoPageDTO = new MetaInfoPageDTO();
        infoPageDTO.setMetaType(name);
        List<MetaGroupDTO> metGroups = this.metaGroupService.getGroupList(null, null, OperateType.DESIGN);
        List<String> strings = metGroups.stream().map(MetaGroupDTO::getModuleName).distinct().collect(Collectors.toList());
        ArrayList metaList = new ArrayList();
        strings.forEach(s -> {
            infoPageDTO.setModule((String)s);
            List<MetaInfoDTO> list = this.metaInfoService.getMetaList(infoPageDTO);
            metaList.addAll(list);
        });
        return metaList.stream().map(metaInfoDTO -> {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode object = mapper.createObjectNode();
            object.put("title", metaInfoDTO.getTitle());
            object.put("code", metaInfoDTO.getUniqueCode());
            return object;
        }).collect(Collectors.toList());
    }
}

