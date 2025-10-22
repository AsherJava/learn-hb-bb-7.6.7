/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.calibre2.controller;

import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.calibre2.internal.service.impl.FormDataQueryService;
import com.jiuqi.nr.calibre2.vo.CommonReportFormVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"api/v1/calibre2/"})
@Api(tags={"\u53e3\u5f84\u7ba1\u7406\uff1a\u62a5\u8868\u76f8\u5173\u6570\u636e\u67e5\u8be2"})
public class FormDataController {
    @Autowired
    private FormDataQueryService formDataQueryService;

    @ApiOperation(value="\u67e5\u8be2\u4efb\u52a1\u5217\u8868")
    @GetMapping(value={"report/task/{entity}"})
    public List<CommonReportFormVO> getTaskList(@PathVariable String entity) {
        return this.formDataQueryService.getTaskList(entity);
    }

    @ApiOperation(value="\u67e5\u8be2\u4efb\u52a1\u4e0b\u7684\u62a5\u8868\u65b9\u6848\u5217\u8868")
    @GetMapping(value={"report/formScheme/{task}/{entity}"})
    public List<CommonReportFormVO> getFormSchemeList(@PathVariable String task, @PathVariable String entity) {
        return this.formDataQueryService.getFormSchemeList(task, entity);
    }

    @ApiOperation(value="\u67e5\u8be2\u5305\u542b\u4e86\u5b9e\u4f53\u7684\u62a5\u8868")
    @GetMapping(value={"report/formScheme/{entity}"})
    public List<CommonReportFormVO> getFormSchemeList(@PathVariable String entity) {
        return this.formDataQueryService.getFormList(entity);
    }
}

