/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.np.definition.exception.DBParaException
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.designer.paramlanguage.controller;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.nr.designer.common.NrDesingerErrorEnum;
import com.jiuqi.nr.designer.paramlanguage.service.SaveLanguageService;
import com.jiuqi.nr.designer.paramlanguage.vo.BigDataSaveObject;
import com.jiuqi.nr.designer.paramlanguage.vo.ParamLanguageObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"api/v1/designer/"})
@Api(tags={"\u53c2\u6570\u56fd\u9645\u5316"})
public class SaveLanguageController {
    @Autowired
    private SaveLanguageService saveLanguageService;

    @PostMapping(value={"saveDesignerParamLanguage"})
    @ApiOperation(value="\u4fdd\u5b58\u53c2\u6570\u56fd\u9645\u5316\u8bed\u8a00\u4fe1\u606f")
    public void saveDesignerParamLanguage(@RequestBody ParamLanguageObject[] paramLanguages) throws JQException {
        try {
            this.saveLanguageService.batchSaveParamLanguage(paramLanguages);
        }
        catch (DBParaException e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_169, (Throwable)e);
        }
    }

    @PostMapping(value={"saveBigDataParamLanguage"})
    @ApiOperation(value="\u4fdd\u5b58bigdata\u6570\u636e--\u8868\u6837\u3001\u6d6e\u52a8\u533a\u57df\u9875\u7b7e")
    public void saveBigDataParamLanguage(@RequestBody BigDataSaveObject bigDataSaveObject) throws JQException {
        this.saveLanguageService.saveBigDataParamLanguage(bigDataSaveObject);
    }
}

