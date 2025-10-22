/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.itreebase.nodeicon.impl.IconBrowserHttpProviderImpl
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.annotation.Resource
 *  org.springframework.http.CacheControl
 *  org.springframework.http.HttpHeaders
 *  org.springframework.http.HttpMethod
 *  org.springframework.http.HttpStatus
 *  org.springframework.http.ResponseEntity
 *  org.springframework.http.ResponseEntity$BodyBuilder
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 *  org.springframework.web.context.request.WebRequest
 */
package com.jiuiqi.nr.unit.treebase.web;

import com.jiuqi.nr.itreebase.nodeicon.impl.IconBrowserHttpProviderImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@RestController
@RequestMapping(value={"/unit-tree-icon-source/v2/anon/"})
@Api(tags={"\u5355\u4f4d\u6811\u8282\u70b9\u6570\u636e\u8bf7\u6c42-API"})
public class IUnitTreeIconController {
    private static final Logger logger = LoggerFactory.getLogger(IUnitTreeIconController.class);
    @Resource
    private IconBrowserHttpProviderImpl iconSourceHelper;

    @ApiOperation(value="\u5355\u4f4d\u6811-\u8282\u70b9\u56fe\u6807\u83b7\u53d6")
    @GetMapping(value={"/{iconSchemeKey}/{iconKey}"})
    public ResponseEntity<String> getIconImage(@PathVariable String iconSchemeKey, @PathVariable String iconKey, WebRequest request) {
        long time = System.currentTimeMillis();
        if (request.checkNotModified(time)) {
            logger.info("\u5355\u4f4d\u8282\u70b9\u56fe\u6807\uff1a{}[{}]\u56fe\u6807\u6ca1\u6709\u53d8\u5316\uff0c\u8fd4\u56de\u72b6\u6001\u7801304.", (Object)iconSchemeKey, (Object)iconKey);
            return ResponseEntity.status((HttpStatus)HttpStatus.NOT_MODIFIED).build();
        }
        String iconBase64 = this.iconSourceHelper.toBase64String(iconSchemeKey, iconKey);
        CacheControl cacheControl = CacheControl.maxAge((long)86400L, (TimeUnit)TimeUnit.SECONDS).cachePublic();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "image/svg+xml");
        return ((ResponseEntity.BodyBuilder)((ResponseEntity.BodyBuilder)((ResponseEntity.BodyBuilder)((ResponseEntity.BodyBuilder)ResponseEntity.ok().allow(new HttpMethod[]{HttpMethod.GET})).cacheControl(cacheControl)).lastModified(time)).headers(httpHeaders)).body((Object)iconBase64);
    }
}

