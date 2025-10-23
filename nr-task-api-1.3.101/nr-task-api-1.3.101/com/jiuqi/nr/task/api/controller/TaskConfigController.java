/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.task.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/v1/task/config"})
@Api(tags={"\u4efb\u52a1\u8bbe\u8ba1\u9009\u9879\u67e5\u8be2"})
public class TaskConfigController {
    @ApiOperation(value="\u83b7\u53d6\u8868\u5355\u5b58\u50a8\u8868\u663e\u793a\u6a21\u5f0f")
    @GetMapping(value={"/tableShowModel"})
    public List<Map<String, String>> getTableShowModel() {
        ArrayList<Map<String, String>> model = new ArrayList<Map<String, String>>(2);
        HashMap<String, String> onlyCode = new HashMap<String, String>();
        onlyCode.put("key", "ONLY_CODE");
        onlyCode.put("title", "\u5c55\u793a\u6807\u8bc6");
        HashMap<String, String> allContent = new HashMap<String, String>();
        allContent.put("key", "ALL_CONTENT");
        allContent.put("title", "\u5c55\u793a\u5b58\u50a8\u8868\u548c\u6807\u8bc6");
        model.add(onlyCode);
        model.add(allContent);
        return model;
    }
}

