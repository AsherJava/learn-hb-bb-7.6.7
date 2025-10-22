/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.designer.paramlanguage.controller;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.designer.common.NrDesingerErrorEnum;
import com.jiuqi.nr.designer.paramlanguage.service.LanguageTypeService;
import com.jiuqi.nr.designer.paramlanguage.vo.LanguageTypeObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"api/v1/designer/"})
@Api(tags={"\u8bed\u8a00\u7c7b\u578b"})
public class LanguageTypeController {
    @Autowired
    private LanguageTypeService languageTypeService;

    @PostMapping(value={"queryDefaultLanguage"})
    @ApiOperation(value="\u67e5\u8be2\u9ed8\u8ba4\u8bed\u8a00")
    public String queryDefaultLanguage() throws JQException {
        try {
            return this.languageTypeService.queryDefaultLanguage();
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_170, (Throwable)e);
        }
    }

    @GetMapping(value={"queryAllLanguageList"})
    @ApiOperation(value="\u67e5\u8be2\u6240\u6709\u8bed\u8a00\u5217\u8868")
    public List<LanguageTypeObject> queryAllType() throws JQException {
        try {
            return this.languageTypeService.queryAll();
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_172, (Throwable)e);
        }
    }
}

