/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.definition.api.IDesignTimePrintController
 *  com.jiuqi.nr.definition.api.IParamDeployController
 *  com.jiuqi.nr.definition.common.ParamResourceType
 *  com.jiuqi.nr.definition.facade.DesignPrintTemplateDefine
 *  com.jiuqi.nr.task.api.aop.TaskLog
 *  com.jiuqi.xg.process.ITemplateDocument
 *  com.jiuqi.xg.process.ITemplateObject
 *  com.jiuqi.xg.process.util.SerializeUtil
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 */
package com.jiuqi.nr.print.web.controller;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.definition.api.IDesignTimePrintController;
import com.jiuqi.nr.definition.api.IParamDeployController;
import com.jiuqi.nr.definition.common.ParamResourceType;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateDefine;
import com.jiuqi.nr.print.common.PrintSchemeMoveType;
import com.jiuqi.nr.print.exception.PrintSchemeException;
import com.jiuqi.nr.print.service.IPrintSchemeService;
import com.jiuqi.nr.print.web.vo.PrintSchemeVo;
import com.jiuqi.nr.task.api.aop.TaskLog;
import com.jiuqi.xg.process.ITemplateDocument;
import com.jiuqi.xg.process.ITemplateObject;
import com.jiuqi.xg.process.util.SerializeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@JQRestController
@Api(tags={"\u6253\u5370\u65b9\u6848"})
@RequestMapping(value={"api/v2/print/"})
public class PrintSchemeController {
    @Autowired
    private IPrintSchemeService printSchemeService;
    @Autowired
    private IDesignTimePrintController designTimePrintController;
    @Autowired
    private IParamDeployController paramDeployController;

    @ApiOperation(value="\u6839\u636e\u4efb\u52a1\u548c\u62a5\u8868\u65b9\u6848\u67e5\u8be2\u9ed8\u8ba4\u4f7f\u7528\u7684\u6253\u5370\u65b9\u6848")
    @RequestMapping(value={"scheme/querydefault/{taskKey}/{formSchemeKey}"}, method={RequestMethod.POST})
    public PrintSchemeVo getDefaultPrintScheme(@PathVariable String taskKey, @PathVariable String formSchemeKey) {
        return this.printSchemeService.getDefaultPrintScheme(taskKey, formSchemeKey);
    }

    @ApiOperation(value="\u6839\u636e\u62a5\u8868\u65b9\u6848\u67e5\u8be2\u6240\u6709\u6253\u5370\u65b9\u6848")
    @RequestMapping(value={"scheme/query"}, method={RequestMethod.GET})
    public List<PrintSchemeVo> listPrintSchemes(String formSchemeKey) {
        return this.printSchemeService.listPrintSchemeByFormScheme(formSchemeKey);
    }

    @ApiOperation(value="\u6253\u5370\u65b9\u6848\u540d\u79f0\u6821\u9a8c")
    @RequestMapping(value={"scheme/check"}, method={RequestMethod.POST})
    public void checkPrintSchemeTitle(@RequestBody PrintSchemeVo printSchemeVo) throws JQException {
        try {
            this.printSchemeService.checkTitle(printSchemeVo);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)PrintSchemeException.CHECK_PRINT_SCHEME_TITLE_FAIL, (Throwable)e);
        }
    }

    @ApiOperation(value="\u65b0\u589e\u6253\u5370\u65b9\u6848")
    @RequestMapping(value={"scheme/add"}, method={RequestMethod.POST})
    @TaskLog(operation="\u65b0\u589e\u6253\u5370\u65b9\u6848")
    public String addPrintScheme(@RequestBody PrintSchemeVo printSchemeVo) throws JQException {
        String printSchemeKey = "";
        try {
            printSchemeKey = this.printSchemeService.insertPrintScheme(printSchemeVo);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)PrintSchemeException.ADD_PRINT_SCHEME_FAIL, (Throwable)e);
        }
        return printSchemeKey;
    }

    @ApiOperation(value="\u66f4\u65b0\u6253\u5370\u65b9\u6848")
    @RequestMapping(value={"scheme/update"}, method={RequestMethod.POST})
    @TaskLog(operation="\u66f4\u65b0\u6253\u5370\u65b9\u6848")
    public void updatePrintScheme(@RequestBody PrintSchemeVo printSchemeVo) throws JQException {
        try {
            this.printSchemeService.updatePrintScheme(printSchemeVo);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)PrintSchemeException.UPDATE_PRINT_SCHEME_FAIL, (Throwable)e);
        }
    }

    @ApiOperation(value="\u590d\u5236\u6253\u5370\u65b9\u6848")
    @RequestMapping(value={"scheme/copy"}, method={RequestMethod.POST})
    @TaskLog(operation="\u590d\u5236\u6253\u5370\u65b9\u6848")
    public String copyPrintScheme(@RequestBody PrintSchemeVo printSchemeVo) throws JQException {
        String printSchemeKey = "";
        try {
            printSchemeKey = this.printSchemeService.copyPrintScheme(printSchemeVo);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)PrintSchemeException.COPY_PRINT_SCHEME_FAIL, (Throwable)e);
        }
        return printSchemeKey;
    }

    @ApiOperation(value="\u5220\u9664\u6253\u5370\u65b9\u6848")
    @RequestMapping(value={"scheme/delete/{printSchemeKey}"}, method={RequestMethod.POST})
    @TaskLog(operation="\u5220\u9664\u6253\u5370\u65b9\u6848")
    public void deletePrintScheme(@PathVariable String printSchemeKey) throws JQException {
        try {
            this.printSchemeService.deletePrintScheme(printSchemeKey);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)PrintSchemeException.DELETE_PRINT_SCHEME_FAIL, e.getMessage());
        }
    }

    @ApiOperation(value="\u79fb\u52a8\u6253\u5370\u65b9\u6848")
    @RequestMapping(value={"scheme/move/{printSchemeKey}/{formSchemeKey}/{moveType}"}, method={RequestMethod.POST})
    @TaskLog(operation="\u79fb\u52a8\u6253\u5370\u65b9\u6848")
    public void exchangeOrder(@PathVariable String printSchemeKey, @PathVariable String formSchemeKey, @PathVariable String moveType) throws JQException {
        try {
            this.printSchemeService.printSchemeMove(printSchemeKey, formSchemeKey, PrintSchemeMoveType.parseType(moveType));
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)PrintSchemeException.MOVE_PRINT_SCHEME_FAIL, (Throwable)e);
        }
    }

    @ApiOperation(value="\u53d1\u5e03\u6253\u5370\u65b9\u6848")
    @RequestMapping(value={"scheme/deploy/{schemeId}", "scheme/deploy/{schemeId}/{formId}"}, method={RequestMethod.GET})
    @TaskLog(operation="\u53d1\u5e03\u6253\u5370\u65b9\u6848")
    public void deployScheme(@PathVariable String schemeId, @PathVariable(required=false) String formId) throws JQException {
        try {
            if (StringUtils.hasText(formId)) {
                DesignPrintTemplateDefine define = this.designTimePrintController.getPrintTemplateBySchemeAndForm(schemeId, formId);
                if (null == define) {
                    ITemplateDocument document = this.designTimePrintController.initTemplateDocument(schemeId, "DEFAULT", formId);
                    define = this.designTimePrintController.initPrintTemplate();
                    define.setPrintSchemeKey(schemeId);
                    define.setFormKey(formId);
                    define.setTemplateData(SerializeUtil.serialize((ITemplateObject)document).getBytes());
                    define.setAutoRefreshForm(true);
                    define.setFormUpdateTime(null);
                    define.setComTemCode("DEFAULT");
                    this.designTimePrintController.insertPrintTemplate(define);
                }
                this.paramDeployController.deploy(ParamResourceType.PRINT_TEMPLATE, schemeId, Collections.singletonList(formId));
            } else {
                this.paramDeployController.deploy(ParamResourceType.PRINT_TEMPLATE, schemeId, Collections.emptyList());
            }
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)PrintSchemeException.DEPLOY_PRINT_SCHEME_FAIL, (Throwable)e);
        }
    }

    @ApiOperation(value="\u5220\u9664\u6253\u5370\u65b9\u6848\u4e0b\u7684\u6240\u6709\u6a21\u677f")
    @RequestMapping(value={"scheme/deleteTemplate/{printSchemeKey}"}, method={RequestMethod.GET})
    @TaskLog(operation="\u5220\u9664\u6253\u5370\u65b9\u6848\u4e0b\u7684\u6240\u6709\u6a21\u677f")
    public void deleteTemplateByPrintScheme(@PathVariable String printSchemeKey) throws JQException {
        try {
            this.printSchemeService.deleteTemplateByPrintScheme(printSchemeKey);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)PrintSchemeException.DELETE_TEMPLATE_FAIL, (Throwable)e);
        }
    }
}

