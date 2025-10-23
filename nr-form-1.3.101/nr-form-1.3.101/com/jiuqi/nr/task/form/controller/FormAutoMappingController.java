/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.task.form.controller;

import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.task.form.controller.dto.AutoMappingDTO;
import com.jiuqi.nr.task.form.controller.dto.AutoMappingParam;
import com.jiuqi.nr.task.form.service.IAutoMappingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"/api/v1/form-designer/"})
@Api(tags={"\u8868\u5355\u8bbe\u8ba1"})
public class FormAutoMappingController {
    @Autowired
    private IAutoMappingService autoMappingService;

    @ApiOperation(value="\u56fa\u5b9a\u533a\u57df\u81ea\u52a8\u6620\u5c04")
    @PostMapping(value={"/autoMapping"})
    public List<AutoMappingDTO> doSimpleMapping(@RequestBody AutoMappingParam autoMappingParam) {
        ArrayList<AutoMappingDTO> result = new ArrayList<AutoMappingDTO>();
        List<AutoMappingDTO> simpleMapping = autoMappingParam.getSimpleMapping();
        List<AutoMappingDTO> floatMapping = autoMappingParam.getFloatMapping();
        if (!CollectionUtils.isEmpty(simpleMapping)) {
            result.addAll(this.autoMappingService.fixAutoMapping(autoMappingParam.getFormKey(), simpleMapping));
        }
        if (!CollectionUtils.isEmpty(floatMapping)) {
            result.addAll(this.autoMappingService.floatAutoMapping(autoMappingParam.getFormKey(), floatMapping));
        }
        return result;
    }
}

