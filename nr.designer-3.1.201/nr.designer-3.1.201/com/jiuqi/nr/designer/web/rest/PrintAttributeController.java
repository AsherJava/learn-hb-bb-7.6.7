/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.definition.controller.IPrintDesignTimeController
 *  com.jiuqi.xg.process.Paper
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 */
package com.jiuqi.nr.designer.web.rest;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.definition.controller.IPrintDesignTimeController;
import com.jiuqi.nr.designer.common.NrDesignLogHelper;
import com.jiuqi.nr.designer.common.NrDesingerErrorEnum;
import com.jiuqi.nr.designer.planpublish.service.TaskPlanPublishExternalService;
import com.jiuqi.nr.designer.service.IPrintAttributeService;
import com.jiuqi.nr.designer.web.rest.vo.PrintAttributeVo;
import com.jiuqi.xg.process.Paper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@JQRestController
@RequestMapping(value={"api/v1/designer/"})
@Api(tags={"\u6253\u5370\u8bbe\u7f6e"})
public class PrintAttributeController {
    @Autowired
    private TaskPlanPublishExternalService taskPlanPublishExternalService;
    @Autowired
    private IPrintAttributeService attributeService;
    @Autowired
    private IPrintDesignTimeController iPrintDesignTimeController;

    @ApiOperation(value="\u67e5\u8be2\u6253\u5370\u8bbe\u7f6e")
    @RequestMapping(value={"print/setting/query"}, method={RequestMethod.GET})
    public PrintAttributeVo queryPrintAttribute(String printSchemeKey) throws Exception {
        return this.attributeService.queryPrintAttribute(printSchemeKey);
    }

    @ApiOperation(value="\u66f4\u65b0\u6253\u5370\u8bbe\u7f6e")
    @RequestMapping(value={"print/setting/update"}, method={RequestMethod.POST})
    public boolean updatePrintAttribute(@RequestBody PrintAttributeVo vo) throws Exception {
        String logTitle = "\u66f4\u65b0\u6253\u5370\u8bbe\u7f6e";
        String printSchemeTitle = "\u672a\u77e5";
        try {
            printSchemeTitle = this.iPrintDesignTimeController.queryPrintTemplateSchemeDefine(vo.getPrintSchemeKey()).getTitle();
            boolean taskCanEdit = this.taskPlanPublishExternalService.printSchemeCanEdit(vo.getPrintSchemeKey());
            if (!taskCanEdit) {
                throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_030);
            }
            boolean isAttributeChange = this.attributeService.printAttributeISChange(vo);
            this.attributeService.updatePrintAttribute(vo);
            NrDesignLogHelper.log(logTitle, printSchemeTitle, NrDesignLogHelper.LOGLEVEL_INFO);
            return isAttributeChange;
        }
        catch (JQException e) {
            NrDesignLogHelper.log(logTitle, printSchemeTitle, NrDesignLogHelper.LOGLEVEL_ERROR);
            throw e;
        }
        catch (Exception e) {
            NrDesignLogHelper.log(logTitle, printSchemeTitle, NrDesignLogHelper.LOGLEVEL_ERROR);
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_127);
        }
    }

    @ApiOperation(value="\u52a0\u8f7d\u6253\u5370\u7eb8\u578b")
    @RequestMapping(value={"print/setting/loadpapers"}, method={RequestMethod.GET})
    public List<Paper> loadPapers() throws IllegalArgumentException, IllegalAccessException {
        return this.attributeService.loadPapers();
    }

    @ApiOperation(value="\u52a0\u8f7d\u6253\u5370\u5b57\u4f53")
    @RequestMapping(value={"print/setting/loadfonts"}, method={RequestMethod.GET})
    public String[] loadFonts() {
        return this.attributeService.loadFonts();
    }

    @ApiOperation(value="\u52a0\u8f7d\u6253\u5370\u5b57\u4f53\u5927\u5c0f")
    @RequestMapping(value={"print/setting/loadfontsize"}, method={RequestMethod.GET})
    public String[] loadFontSize() {
        return null;
    }
}

