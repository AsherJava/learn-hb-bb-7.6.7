/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.http.CacheControl
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
package com.jiuqi.nr.formtype.web.rest;

import com.jiuqi.nr.formtype.facade.FormTypeDataDefine;
import com.jiuqi.nr.formtype.service.IFormTypeApplyService;
import com.jiuqi.nr.formtype.service.IFormTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@RestController
@RequestMapping(value={"api/v1/formtype/"})
@Api(tags={"\u62a5\u8868\u7c7b\u578b\u56fe\u6807\u83b7\u53d6\u670d\u52a1"})
public class FormTypeIconController {
    private static final Logger logger = LoggerFactory.getLogger(FormTypeIconController.class);
    @Autowired
    private IFormTypeService iFormTypeService;
    @Autowired
    private IFormTypeApplyService iFormTypeApplyService;

    @ApiOperation(value="\u83b7\u53d6\u56fe\u6807")
    @GetMapping(value={"icon/{formTypeCode}/{dataCode}"})
    public ResponseEntity<String> getFormTypeIcon(@PathVariable String formTypeCode, @PathVariable String dataCode, WebRequest request) {
        long time;
        String iconBase64;
        if (this.iFormTypeApplyService.isFormType(formTypeCode)) {
            FormTypeDataDefine formTypeData = this.iFormTypeService.queryFormTypeData(formTypeCode, dataCode);
            Date updateTime = null;
            if (null == formTypeData) {
                updateTime = new Date();
                iconBase64 = "";
            } else {
                updateTime = formTypeData.getUpdateTime();
                iconBase64 = this.iFormTypeApplyService.getIcon(formTypeData);
            }
            time = updateTime.getTime();
            if (request.checkNotModified(time)) {
                logger.info("\u5355\u4f4d\u6027\u8d28\u56fe\u6807\u670d\u52a1\uff1a{}[{}]\u56fe\u6807\u6ca1\u6709\u53d8\u5316\uff0c\u8fd4\u56de\u72b6\u6001\u7801304.", (Object)formTypeCode, (Object)dataCode);
                return ResponseEntity.status((HttpStatus)HttpStatus.NOT_MODIFIED).build();
            }
        } else {
            String lastModified = request.getHeader("Last-Modified");
            if (StringUtils.hasLength(lastModified)) {
                logger.info("\u5355\u4f4d\u6027\u8d28\u56fe\u6807\u670d\u52a1\uff1a{}[{}]\u56fe\u6807\u6ca1\u6709\u53d8\u5316\uff0c\u8fd4\u56de\u72b6\u6001\u7801304.", (Object)formTypeCode, (Object)dataCode);
                return ResponseEntity.status((HttpStatus)HttpStatus.NOT_MODIFIED).build();
            }
            iconBase64 = this.iFormTypeApplyService.getSysIcon(dataCode);
            time = System.currentTimeMillis();
        }
        logger.info("\u5355\u4f4d\u6027\u8d28\u56fe\u6807\u670d\u52a1\uff1a{}[{}]\u56fe\u6807\u8bf7\u6c42\u6210\u529f.", (Object)formTypeCode, (Object)dataCode);
        CacheControl cacheControl = CacheControl.maxAge((long)86400L, (TimeUnit)TimeUnit.SECONDS).cachePublic();
        return ((ResponseEntity.BodyBuilder)((ResponseEntity.BodyBuilder)((ResponseEntity.BodyBuilder)ResponseEntity.ok().allow(new HttpMethod[]{HttpMethod.GET})).cacheControl(cacheControl)).lastModified(time)).body((Object)iconBase64);
    }

    @ApiOperation(value="\u83b7\u53d6\u56fe\u6807")
    @GetMapping(value={"icon/{formTypeCode}"})
    public ResponseEntity<Map<String, String>> getFormTypeIcon(@PathVariable String formTypeCode, WebRequest request) {
        if (this.iFormTypeApplyService.isFormType(formTypeCode)) {
            List<FormTypeDataDefine> formTypeDatas = this.iFormTypeService.queryFormTypeDatas(formTypeCode);
            for (FormTypeDataDefine data : formTypeDatas) {
                if (request.checkNotModified(data.getUpdateTime().getTime())) continue;
                Map<String, String> iconMap = formTypeDatas.stream().collect(Collectors.toMap(FormTypeDataDefine::getCode, this.iFormTypeApplyService::getIcon));
                logger.info("\u5355\u4f4d\u6027\u8d28\u56fe\u6807\u670d\u52a1\uff1a{}\u56fe\u6807\u8bf7\u6c42\u6210\u529f.", (Object)formTypeCode);
                return ((ResponseEntity.BodyBuilder)ResponseEntity.ok().allow(new HttpMethod[]{HttpMethod.GET})).body(iconMap);
            }
        } else {
            String lastModified = request.getHeader("Last-Modified");
            if (!StringUtils.hasLength(lastModified)) {
                logger.info("\u5355\u4f4d\u6027\u8d28\u56fe\u6807\u670d\u52a1\uff1a{}\u56fe\u6807\u8bf7\u6c42\u6210\u529f.", (Object)formTypeCode);
                return ((ResponseEntity.BodyBuilder)ResponseEntity.ok().allow(new HttpMethod[]{HttpMethod.GET})).body(this.iFormTypeApplyService.getAllSysIcon());
            }
        }
        logger.info("\u5355\u4f4d\u6027\u8d28\u56fe\u6807\u670d\u52a1\uff1a{}\u56fe\u6807\u6ca1\u6709\u53d8\u5316\uff0c\u8fd4\u56de\u72b6\u6001\u7801304.", (Object)formTypeCode);
        return ResponseEntity.status((HttpStatus)HttpStatus.NOT_MODIFIED).build();
    }

    @ApiOperation(value="\u83b7\u53d6\u56fe\u6807")
    @GetMapping(value={"icon/scheme/{schemeKey}"})
    public ResponseEntity<Map<String, String>> getIconByScheme(@PathVariable String schemeKey, WebRequest request) {
        String lastModified = request.getHeader("Last-Modified");
        if (!StringUtils.hasLength(lastModified)) {
            logger.info("\u5355\u4f4d\u6027\u8d28\u56fe\u6807\u670d\u52a1\uff1a{}\u56fe\u6807\u8bf7\u6c42\u6210\u529f.", (Object)schemeKey);
            return ((ResponseEntity.BodyBuilder)ResponseEntity.ok().allow(new HttpMethod[]{HttpMethod.GET})).body(this.iFormTypeApplyService.getAllSysIcon(schemeKey));
        }
        logger.info("\u5355\u4f4d\u6027\u8d28\u56fe\u6807\u670d\u52a1\uff1a{}\u56fe\u6807\u6ca1\u6709\u53d8\u5316\uff0c\u8fd4\u56de\u72b6\u6001\u7801304.", (Object)schemeKey);
        return ResponseEntity.status((HttpStatus)HttpStatus.NOT_MODIFIED).build();
    }
}

