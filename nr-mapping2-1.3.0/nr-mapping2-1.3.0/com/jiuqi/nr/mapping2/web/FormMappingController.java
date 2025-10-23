/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.mapping2.web;

import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.mapping2.dto.FormMappingDTO;
import com.jiuqi.nr.mapping2.service.IFormMappingService;
import com.jiuqi.nr.mapping2.web.vo.CommonTreeNode;
import com.jiuqi.nr.mapping2.web.vo.FormMappingSaveVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"/api/mapping2/form"})
@Api(tags={"\u8868\u5355\u6620\u5c04"})
public class FormMappingController {
    private final Logger logger = LoggerFactory.getLogger(FormMappingController.class);
    @Autowired
    private IFormMappingService formMappingService;

    @GetMapping(value={"/tree-init/{schemeKey}"})
    @ApiOperation(value="\u6784\u9020\u62a5\u8868\u6811\u5f62")
    public List<CommonTreeNode> buildFormTree(@PathVariable String schemeKey) {
        return this.formMappingService.initTree(schemeKey);
    }

    @GetMapping(value={"/list/{schemeKey}/{groupKey}"})
    @ApiOperation(value="\u67e5\u8be2\u8868\u5355\u6620\u5c04")
    public List<FormMappingDTO> list(@PathVariable String schemeKey, @PathVariable String groupKey) {
        return this.formMappingService.list(schemeKey, groupKey);
    }

    @PostMapping(value={"/save"})
    @ApiOperation(value="\u4fdd\u5b58\u6620\u5c04")
    public void save(@RequestBody FormMappingSaveVO saveVO) {
        this.formMappingService.update(saveVO.getSchemeKey(), saveVO.getItems());
    }

    @GetMapping(value={"/clean/{schemeKey}/{groupKey}"})
    @ApiOperation(value="\u6e05\u7406\u6620\u5c04")
    public void clean(@PathVariable String schemeKey, @PathVariable String groupKey) {
        this.formMappingService.clean(schemeKey, groupKey);
    }
}

