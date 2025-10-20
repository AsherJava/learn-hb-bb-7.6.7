/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.definition.controller.IPrintDesignTimeController
 *  com.jiuqi.nr.definition.controller.IViewDeployController
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 */
package com.jiuqi.nr.designer.web.rest;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.definition.controller.IPrintDesignTimeController;
import com.jiuqi.nr.definition.controller.IViewDeployController;
import com.jiuqi.nr.designer.common.NrDesignLogHelper;
import com.jiuqi.nr.designer.common.NrDesingerErrorEnum;
import com.jiuqi.nr.designer.planpublish.service.TaskPlanPublishExternalService;
import com.jiuqi.nr.designer.service.IPrintSchemeService;
import com.jiuqi.nr.designer.web.rest.vo.PrintSchemeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@JQRestController
@RequestMapping(value={"api/v1/designer/"})
@Api(tags={"\u6253\u5370\u65b9\u6848"})
public class PrintSchemeController {
    @Autowired
    private TaskPlanPublishExternalService taskPlanPublishExternalService;
    @Autowired
    private IPrintSchemeService printSchemeService;
    @Autowired
    private IPrintDesignTimeController printDesignTimeService;
    @Autowired
    private IViewDeployController deployController;

    @ApiOperation(value="\u6839\u636e\u4efb\u52a1\u548c\u62a5\u8868\u65b9\u6848\u67e5\u8be2\u9ed8\u8ba4\u4f7f\u7528\u7684\u6253\u5370\u65b9\u6848")
    @RequestMapping(value={"print/scheme/querydefault/{taskKey}/{formSchemeKey}"}, method={RequestMethod.POST})
    public PrintSchemeVo queryDefaultPrintScheme(@PathVariable String taskKey, @PathVariable String formSchemeKey) throws Exception {
        return this.printSchemeService.queryDefaultPrintScheme(taskKey, formSchemeKey);
    }

    @ApiOperation(value="\u6839\u636e\u62a5\u8868\u65b9\u6848\u67e5\u8be2\u6240\u6709\u6253\u5370\u65b9\u6848")
    @RequestMapping(value={"print/scheme/query"}, method={RequestMethod.GET})
    public List<PrintSchemeVo> queryPrintSchemes(String formSchemeKey) throws Exception {
        return this.printSchemeService.queryPrintSchemes(formSchemeKey);
    }

    @ApiOperation(value="\u65b0\u589e\u6253\u5370\u65b9\u6848")
    @RequestMapping(value={"print/scheme/add"}, method={RequestMethod.POST})
    public String addPrintScheme(@RequestBody PrintSchemeVo printSchemeVo) throws Exception {
        String logTitle = "\u65b0\u589e\u6253\u5370\u65b9\u6848";
        String printSchemeTitle = "\u672a\u77e5";
        try {
            printSchemeTitle = printSchemeVo.getTitle();
            boolean taskCanEdit = this.taskPlanPublishExternalService.schemeCanEdit(printSchemeVo.getFormSchemeKey());
            if (!taskCanEdit) {
                throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_030);
            }
            String printSchemeKey = this.printSchemeService.addPrintScheme(printSchemeVo);
            NrDesignLogHelper.log(logTitle, printSchemeTitle, NrDesignLogHelper.LOGLEVEL_INFO);
            return printSchemeKey;
        }
        catch (JQException e) {
            NrDesignLogHelper.log(logTitle, printSchemeTitle, NrDesignLogHelper.LOGLEVEL_ERROR);
            throw e;
        }
        catch (Exception e) {
            NrDesignLogHelper.log(logTitle, printSchemeTitle, NrDesignLogHelper.LOGLEVEL_ERROR);
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_124);
        }
    }

    @ApiOperation(value="\u66f4\u65b0\u6253\u5370\u65b9\u6848")
    @RequestMapping(value={"print/scheme/update"}, method={RequestMethod.POST})
    public void updatePrintScheme(@RequestBody PrintSchemeVo printSchemeVo) throws Exception {
        String logTitle = "\u66f4\u65b0\u6253\u5370\u65b9\u6848";
        String printSchemeTitle = "\u672a\u77e5";
        try {
            printSchemeTitle = this.printDesignTimeService.queryPrintTemplateSchemeDefine(printSchemeVo.getKey()).getTitle();
            boolean taskCanEdit = this.taskPlanPublishExternalService.schemeCanEdit(printSchemeVo.getFormSchemeKey());
            if (!taskCanEdit) {
                throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_030);
            }
            this.printSchemeService.updatePrintScheme(printSchemeVo);
            NrDesignLogHelper.log(logTitle, printSchemeTitle, NrDesignLogHelper.LOGLEVEL_INFO);
        }
        catch (JQException e) {
            NrDesignLogHelper.log(logTitle, printSchemeTitle, NrDesignLogHelper.LOGLEVEL_ERROR);
            throw e;
        }
        catch (Exception e) {
            NrDesignLogHelper.log(logTitle, printSchemeTitle, NrDesignLogHelper.LOGLEVEL_ERROR);
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_125);
        }
    }

    @ApiOperation(value="\u590d\u5236\u6253\u5370\u65b9\u6848")
    @RequestMapping(value={"print/scheme/copy"}, method={RequestMethod.POST})
    public String copyPrintScheme(@RequestBody PrintSchemeVo printSchemeVo) throws Exception {
        return this.printSchemeService.copyPrintScheme(printSchemeVo);
    }

    @ApiOperation(value="\u5220\u9664\u6253\u5370\u65b9\u6848")
    @RequestMapping(value={"print/scheme/delete/{printSchemeKey}"}, method={RequestMethod.POST})
    public void deletePrintScheme(@PathVariable String printSchemeKey) throws Exception {
        String logTitle = "\u5220\u9664\u6253\u5370\u65b9\u6848";
        String printSchemeTitle = "\u672a\u77e5";
        try {
            printSchemeTitle = this.printDesignTimeService.queryPrintTemplateSchemeDefine(printSchemeKey).getTitle();
            boolean taskCanEdit = this.taskPlanPublishExternalService.printSchemeCanEdit(printSchemeKey);
            if (!taskCanEdit) {
                throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_030);
            }
            this.printSchemeService.deletePrintScheme(printSchemeKey);
            NrDesignLogHelper.log(logTitle, printSchemeTitle, NrDesignLogHelper.LOGLEVEL_INFO);
        }
        catch (JQException e) {
            NrDesignLogHelper.log(logTitle, printSchemeTitle, NrDesignLogHelper.LOGLEVEL_ERROR);
            throw e;
        }
        catch (Exception e) {
            NrDesignLogHelper.log(logTitle, printSchemeTitle, NrDesignLogHelper.LOGLEVEL_ERROR);
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_126);
        }
    }

    @ApiOperation(value="\u79fb\u52a8\u6253\u5370\u65b9\u6848")
    @RequestMapping(value={"print/scheme/exchange/{sourceKey}/{targetKey}"}, method={RequestMethod.POST})
    public void exchangeOrder(@PathVariable String sourceKey, @PathVariable String targetKey) throws Exception {
        this.printSchemeService.exchangeOrder(sourceKey, targetKey);
    }

    @ApiOperation(value="\u53d1\u5e03\u6253\u5370\u65b9\u6848")
    @RequestMapping(value={"deploy/{schemeId}"}, method={RequestMethod.GET})
    public void deployScheme(@PathVariable String schemeId) throws Exception {
        this.deployController.deployPrintScheme(schemeId, true);
    }

    @ApiOperation(value="\u5220\u9664\u6253\u5370\u65b9\u6848\u4e0b\u7684\u6240\u6709\u6a21\u677f")
    @RequestMapping(value={"print/scheme/deleteTemplete/{printSchemeKey}"}, method={RequestMethod.GET})
    public void deleteTempleteByPrintScheme(@PathVariable String printSchemeKey) throws JQException {
        try {
            boolean taskCanEdit = this.taskPlanPublishExternalService.printSchemeCanEdit(printSchemeKey);
            if (!taskCanEdit) {
                throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_030);
            }
            this.printSchemeService.deleteTempleteByPrintScheme(printSchemeKey);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_035, (Throwable)e);
        }
    }
}

