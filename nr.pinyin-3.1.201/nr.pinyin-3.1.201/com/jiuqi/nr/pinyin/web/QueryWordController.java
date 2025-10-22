/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.log.annotation.Loggable
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.pinyin.web;

import com.jiuqi.np.log.annotation.Loggable;
import com.jiuqi.nr.pinyin.service.IPinyinCacheService;
import com.jiuqi.nr.pinyin.service.IQueryWordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Loggable(value="\u62fc\u97f3\u67e5\u6c49\u5b57\u7ec4\u4ef6")
@Api(tags={"\u62fc\u97f3\u67e5\u6c49\u5b57\u7ec4\u4ef6"})
@RestController
public class QueryWordController {
    @Autowired
    IQueryWordService queryWordService;
    @Autowired
    IPinyinCacheService iPinyinCacheService;

    @Loggable(value="\u62fc\u97f3\u6a21\u7cca\u67e5\u8be2\u6570\u636e\u5b57\u5178")
    @ApiOperation(value="\u62fc\u97f3\u6a21\u7cca\u67e5\u8be2\u6570\u636e\u5b57\u5178")
    @GetMapping(value={"/api/queryWord"})
    public List<String> queryWord(String pinyin) {
        return this.queryWordService.queryWords(pinyin);
    }

    @GetMapping(value={"/api/initCache"})
    public String initCache() {
        this.iPinyinCacheService.initCache();
        return "success";
    }
}

