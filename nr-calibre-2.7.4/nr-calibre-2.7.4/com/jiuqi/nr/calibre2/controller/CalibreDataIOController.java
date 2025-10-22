/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.calibre2.controller;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.calibre2.ICalibreDataIOService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@JQRestController
@RequestMapping(value={"api/v1/calibre2/"})
@Api(tags={"\u53e3\u5f84\u7ba1\u7406\uff1a\u53e3\u5f84\u6570\u636e\u5bfc\u5165\u5bfc\u51fa"})
public class CalibreDataIOController {
    @Autowired
    private ICalibreDataIOService dataManageService;

    @ApiOperation(value="\u5bfc\u5165\u53e3\u5f84\u6570\u636e")
    @PostMapping(value={"import_calibre"})
    public void importCalibre(@RequestParam(value="file") MultipartFile file, String key) throws JQException {
        this.dataManageService.importCalibreData(file, key);
    }

    @ApiOperation(value="\u5bfc\u51fa\u53e3\u5f84\u6570\u636e")
    @GetMapping(value={"export_calibre/{key}"})
    public void exportCalibre(HttpServletResponse res, @PathVariable(value="key") String key) throws JQException {
        this.dataManageService.exportCalibreData(key, res);
    }
}

