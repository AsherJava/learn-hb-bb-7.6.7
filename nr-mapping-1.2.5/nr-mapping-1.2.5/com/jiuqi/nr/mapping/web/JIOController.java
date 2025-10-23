/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.security.HtmlUtils
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  javax.validation.Valid
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.mapping.web;

import com.jiuqi.bi.security.HtmlUtils;
import com.jiuqi.nr.mapping.dto.JIOContent;
import com.jiuqi.nr.mapping.dto.JIOUploadParam;
import com.jiuqi.nr.mapping.service.JIOConfigService;
import com.jiuqi.nr.mapping.service.JIOProvider;
import com.jiuqi.nr.mapping.web.UnitMappingController;
import com.jiuqi.nr.mapping.web.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value={"/api/mapping/jio"})
@Api(tags={"jio"})
public class JIOController {
    protected final Logger logger = LoggerFactory.getLogger(UnitMappingController.class);
    @Autowired
    JIOConfigService jioService;
    @Autowired
    JIOProvider jioProvider;

    @PostMapping(value={"/upload/{msKey}"})
    @ApiOperation(value="\u4e0a\u4f20")
    public Result uploadJIO(@PathVariable String msKey, @Valid JIOUploadParam param, @RequestParam(value="file") MultipartFile file, HttpServletRequest request) throws IOException {
        try {
            Result jioResult = this.jioProvider.execute(file.getBytes(), msKey, param);
            return jioResult;
        }
        catch (IOException e) {
            e.printStackTrace();
            JIOContent content = new JIOContent();
            content.setFileCode("code1");
            content.setFileName("name");
            Date date = new Date();
            date.setTime(3052121L);
            content.setUploadTime(date);
            content.setTaskCode("tcode");
            content.setTaskName("tname");
            content.setParaVersionNum("paravnum");
            this.jioService.saveJIO(msKey, file.getBytes(), file.getBytes(), content);
            return null;
        }
    }

    @GetMapping(value={"/content/{msKey}"})
    @ApiOperation(value="\u83b7\u53d6JIO\u4fe1\u606f")
    public JIOContent getJIOContent(@PathVariable String msKey) {
        return this.jioService.getJIOContentByMs(msKey);
    }

    @GetMapping(value={"/download/{msKey}"})
    @ApiOperation(value="\u4e0b\u8f7dJIO\u6587\u4ef6")
    public void export(@PathVariable String msKey, HttpServletResponse response) throws Exception {
        msKey = HtmlUtils.cleanUrlXSS((String)msKey);
        byte[] jioFile = this.jioService.getJIOFileByMs(msKey);
        String fileName = "jio\u6587\u4ef6.jio";
        response.setCharacterEncoding("UTF-8");
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        response.setContentType("application/octet-stream");
        try (BufferedOutputStream outputStream = new BufferedOutputStream((OutputStream)response.getOutputStream());){
            ((OutputStream)outputStream).write(jioFile, 0, jioFile.length);
            ((OutputStream)outputStream).flush();
        }
        catch (IOException e) {
            this.logger.error(e.getMessage(), e);
        }
    }
}

