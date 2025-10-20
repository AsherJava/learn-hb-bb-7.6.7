/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.datascheme.i18n.language.LanguageType
 *  com.jiuqi.nr.definition.facade.RegionTabSettingDefine
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.designer.paramlanguage.controller;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.datascheme.i18n.language.LanguageType;
import com.jiuqi.nr.definition.facade.RegionTabSettingDefine;
import com.jiuqi.nr.designer.common.NrDesingerErrorEnum;
import com.jiuqi.nr.designer.paramlanguage.service.ParamLanguageService;
import com.jiuqi.nr.designer.paramlanguage.vo.ParamLanguageObject;
import com.jiuqi.nr.designer.paramlanguage.vo.ResultReportObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"api/v1/designer/"})
@Api(tags={"\u53c2\u6570\u56fd\u9645\u5316"})
public class ParamLanguageController {
    @Autowired
    private ParamLanguageService paramLanguageService;

    @PostMapping(value={"checkLanguageRepeat"})
    @ApiOperation(value="\u68c0\u67e5\u8bed\u8a00\u662f\u5426\u91cd\u590d")
    public boolean checkLanguageRepeat(@RequestBody ParamLanguageObject paramLanguage) throws JQException {
        try {
            return this.paramLanguageService.checkLanguageRepeat(paramLanguage);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_171, (Throwable)e);
        }
    }

    @PostMapping(value={"queryLanguageInfoByResource"})
    @ApiOperation(value="\u6839\u636e\u8d44\u6e90\u67e5\u8be2\u5bf9\u5e94\u8bed\u8a00\u4fe1\u606f")
    public String queryLanguageInfoByResource(@RequestBody ParamLanguageObject paramLanguage) throws JQException {
        try {
            return this.paramLanguageService.queryLanguageInfoByResource(paramLanguage);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_173, (Throwable)e);
        }
    }

    @PostMapping(value={"queryReportWithParamLanguage"})
    @ApiOperation(value="\u6839\u636e\u8bed\u8a00\u7c7b\u578b\u67e5\u8be2\uff0c\u66f4\u65b0\u62a5\u8868\u9009\u62e9\u5668\u8bed\u8a00")
    public ResultReportObject queryReportWithParamLanguage(@RequestBody ResultReportObject resultReportObject) throws JQException {
        try {
            String languageType = null == resultReportObject.getLanguage() ? LanguageType.CHINESE.getLanguage() : resultReportObject.getLanguage();
            return this.paramLanguageService.queryReportWithParamLanguage(resultReportObject, languageType);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_174, (Throwable)e);
        }
    }

    @PostMapping(value={"queryRegionSettingWithParamLanguage/{formKey}/{language}"})
    @ApiOperation(value="\u6839\u636e\u8bed\u8a00\u7c7b\u578b\u67e5\u8be2\uff0c\u67e5\u8be2\u6d6e\u52a8\u533a\u57df\u9875\u7b7e\u8bed\u8a00")
    public Map<String, List<RegionTabSettingDefine>> queryRegionSettingWithParamLanguage(@PathVariable(value="formKey") String formKey, @PathVariable(value="language") String language) throws JQException {
        try {
            String languageType = null == language ? LanguageType.CHINESE.getLanguage() : language;
            return this.paramLanguageService.queryRegionSettingWithParamLanguage(formKey, languageType);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_177, (Throwable)e);
        }
    }

    @PostMapping(value={"queryFillingGuideWithParamLanguage/{formKey}/{language}"})
    @ApiOperation(value="\u6839\u636e\u8bed\u8a00\u7c7b\u578b\u67e5\u8be2\uff0c\u67e5\u8be2\u62a5\u8868\u586b\u62a5\u8bf4\u660e\u591a\u8bed\u8a00")
    public Map<String, String> queryFillingGuideWithParamLanguage(@PathVariable(value="formKey") String formKey, @PathVariable(value="language") String language) throws JQException {
        try {
            String languageType = null == language ? LanguageType.CHINESE.getLanguage() : language;
            return this.paramLanguageService.queryFillingGuideWithParamLanguage(formKey, languageType);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_177, (Throwable)e);
        }
    }
}

