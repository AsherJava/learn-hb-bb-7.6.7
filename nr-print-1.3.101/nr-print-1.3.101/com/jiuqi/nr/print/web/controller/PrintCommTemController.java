/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.definition.api.IDesignTimePrintController
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignPrintComTemDefine
 *  com.jiuqi.nr.definition.facade.DesignPrintTemplateDefine
 *  com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine
 *  com.jiuqi.nr.task.api.aop.TaskLog
 *  com.jiuqi.nvwa.definition.common.UUIDUtils
 *  com.jiuqi.util.OrderGenerator
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
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.definition.api.IDesignTimePrintController;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignPrintComTemDefine;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateDefine;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine;
import com.jiuqi.nr.print.dto.DesignerInfoDTO;
import com.jiuqi.nr.print.exception.PrintDesignException;
import com.jiuqi.nr.print.service.IPrintCommonTemService;
import com.jiuqi.nr.print.service.IPrintDesignExtendService;
import com.jiuqi.nr.print.web.param.CommonTemplatePM;
import com.jiuqi.nr.print.web.vo.ComTemVO;
import com.jiuqi.nr.task.api.aop.TaskLog;
import com.jiuqi.nvwa.definition.common.UUIDUtils;
import com.jiuqi.util.OrderGenerator;
import com.jiuqi.xg.process.ITemplateDocument;
import com.jiuqi.xg.process.ITemplateObject;
import com.jiuqi.xg.process.util.SerializeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@JQRestController
@Api(tags={"\u6253\u5370\u8bbe\u7f6e"})
@RequestMapping(value={"api/v2/print/"})
public class PrintCommTemController {
    private static Logger logger = LoggerFactory.getLogger(PrintCommTemController.class);
    @Autowired
    private IPrintCommonTemService printCommonTemService;
    @Autowired
    private IDesignTimePrintController designTimePrintController;
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private IPrintDesignExtendService designExtendService;

    @ApiOperation(value="\u6bcd\u7248\u8986\u76d6")
    @RequestMapping(value={"setting/coverTemplate"}, method={RequestMethod.POST})
    @TaskLog(operation="\u6bcd\u7248\u8986\u76d6")
    public boolean coverPrintTemplate(@RequestBody CommonTemplatePM templatePM) {
        boolean isCovered = true;
        try {
            this.printCommonTemService.coverTemplate(templatePM);
        }
        catch (Exception e) {
            isCovered = false;
            logger.error("\u6bcd\u7248\u8986\u76d6\u5931\u8d25", (Object)e.getMessage(), (Object)e);
        }
        return isCovered;
    }

    @ApiOperation(value="\u6bcd\u7248\u540c\u6b65")
    @RequestMapping(value={"setting/syncTemplate"}, method={RequestMethod.POST})
    @TaskLog(operation="\u6bcd\u7248\u540c\u6b65")
    public boolean syncPrintTemplate(@RequestBody CommonTemplatePM templatePM) {
        boolean isSynced = true;
        try {
            this.printCommonTemService.syncTemplate(templatePM);
        }
        catch (Exception e) {
            logger.error("\u6bcd\u7248\u540c\u6b65\u5931\u8d25", (Object)e.getMessage(), (Object)e);
            isSynced = false;
        }
        return isSynced;
    }

    @ApiOperation(value="\u65b0\u589e\u6bcd\u7248")
    @RequestMapping(value={"setting/create"}, method={RequestMethod.POST})
    @TaskLog(operation="\u65b0\u589e\u6bcd\u7248")
    public String createPrintComTem(@RequestBody ComTemVO vo) throws JQException {
        DesignPrintComTemDefine define = this.designTimePrintController.initPrintComTem();
        if (StringUtils.hasText(vo.getKey())) {
            define.setKey(vo.getKey());
        } else {
            vo.setKey(define.getKey());
        }
        if (StringUtils.hasText(vo.getCode())) {
            define.setCode(vo.getCode());
        } else {
            vo.setCode(define.getCode());
        }
        define.setTitle(vo.getTitle());
        define.setPrintSchemeKey(vo.getPrintSchemeKey());
        ITemplateDocument document = this.designTimePrintController.initCommonTemplateDocument(define.getPrintSchemeKey());
        define.setTemplateData(SerializeUtil.serialize((ITemplateObject)document).getBytes());
        return this.printCommonTemService.insertPrintComTem(define);
    }

    @ApiOperation(value="\u590d\u5236\u6bcd\u7248")
    @RequestMapping(value={"setting/copy"}, method={RequestMethod.POST})
    @TaskLog(operation="\u590d\u5236\u6bcd\u7248")
    public String copyPrintComTem(@RequestBody ComTemVO vo) throws JQException {
        DesignPrintComTemDefine define = this.designTimePrintController.getPrintComTem(vo.getKey());
        if (null != define) {
            define = this.designTimePrintController.copyPrintComTem(define, vo.getPrintSchemeKey());
        } else if (vo.getKey().equals(vo.getPrintSchemeKey())) {
            define = this.designTimePrintController.initPrintComTem();
            ITemplateDocument document = this.designTimePrintController.initCommonTemplateDocument(define.getPrintSchemeKey());
            define.setTemplateData(SerializeUtil.serialize((ITemplateObject)document).getBytes());
        } else {
            throw new JQException((ErrorEnum)PrintDesignException.COMTEM_SAVE_FAIL, "\u6765\u6e90\u6bcd\u7248\u4e0d\u5b58\u5728");
        }
        define.setKey(UUIDUtils.getKey());
        vo.setKey(define.getKey());
        if (StringUtils.hasText(vo.getCode())) {
            define.setCode(vo.getCode());
        } else {
            vo.setCode(define.getCode());
        }
        define.setTitle(vo.getTitle());
        define.setOrder(OrderGenerator.newOrder());
        return this.printCommonTemService.insertPrintComTem(define);
    }

    @ApiOperation(value="\u66f4\u65b0\u6bcd\u7248")
    @RequestMapping(value={"setting/update"}, method={RequestMethod.POST})
    @TaskLog(operation="\u66f4\u65b0\u6bcd\u7248")
    public void updatePrintComTem(@RequestBody ComTemVO vo) throws JQException {
        DesignPrintComTemDefine define = this.designTimePrintController.getPrintComTem(vo.getKey());
        if (StringUtils.hasText(vo.getCode()) && !vo.getCode().equals(define.getCode())) {
            throw new JQException((ErrorEnum)PrintDesignException.COMTEM_SAVE_FAIL, "\u6bcd\u7248\u6807\u8bc6\u4e0d\u5141\u8bb8\u4fee\u6539");
        }
        define.setTitle(vo.getTitle());
        this.printCommonTemService.updatePrintComTem(define);
    }

    @ApiOperation(value="\u5220\u9664\u6bcd\u7248")
    @RequestMapping(value={"setting/delete"}, method={RequestMethod.POST})
    @TaskLog(operation="\u5220\u9664\u6bcd\u7248")
    public void deletePrintComTem(@RequestBody ComTemVO vo) throws JQException {
        DesignPrintComTemDefine define = this.designTimePrintController.getPrintComTem(vo.getKey());
        this.printCommonTemService.deletePrintComTem(define);
    }

    @ApiOperation(value="\u67e5\u8be2\u6bcd\u7248\u5173\u8054\u6a21\u677f\u4e2a\u6570")
    @RequestMapping(value={"setting/link/count/{printSchemeKey}/{commonKey}"}, method={RequestMethod.GET})
    public long getPrintComTemLinkCount(@PathVariable String printSchemeKey, @PathVariable String commonKey) {
        String commonCode;
        DesignPrintComTemDefine define = this.designTimePrintController.getPrintComTem(commonKey);
        String string = commonCode = null != define ? define.getCode() : "DEFAULT";
        if (!"DEFAULT".equals(commonCode)) {
            return this.designTimePrintController.listPrintTemplateByScheme(printSchemeKey).stream().filter(t -> commonCode.equals(t.getComTemCode())).count();
        }
        DesignPrintTemplateSchemeDefine scheme = this.designTimePrintController.getPrintTemplateScheme(printSchemeKey);
        List templates = this.designTimePrintController.listPrintTemplateByScheme(printSchemeKey);
        Set forms = this.designTimeViewController.listFormByFormScheme(scheme.getFormSchemeKey()).stream().map(IBaseMetaItem::getKey).collect(Collectors.toSet());
        long count = 0L;
        for (DesignPrintTemplateDefine template : templates) {
            forms.remove(template.getFormKey());
            if (!commonCode.equals(template.getComTemCode())) continue;
            ++count;
        }
        return count + (long)forms.size();
    }

    @ApiOperation(value="\u67e5\u8be2\u6240\u6709\u6bcd\u7248")
    @RequestMapping(value={"setting/get/all/{printSchemeKey}"}, method={RequestMethod.GET})
    public List<ComTemVO> getAllComTems(@PathVariable String printSchemeKey) {
        ArrayList<ComTemVO> result = new ArrayList<ComTemVO>();
        List comTemDefines = this.designTimePrintController.listPrintComTemBySchemeWithoutBigData(printSchemeKey);
        boolean hasDefault = false;
        for (DesignPrintComTemDefine comTemDefine : comTemDefines) {
            ComTemVO vo = new ComTemVO();
            vo.setKey(comTemDefine.getKey());
            vo.setTitle(comTemDefine.getTitle());
            vo.setCode(comTemDefine.getCode());
            vo.setPrintSchemeKey(comTemDefine.getPrintSchemeKey());
            result.add(vo);
            hasDefault |= comTemDefine.isDefault();
        }
        if (!hasDefault) {
            ComTemVO defaultVo = new ComTemVO();
            defaultVo.setKey(printSchemeKey);
            defaultVo.setCode("DEFAULT");
            defaultVo.setTitle("\u9ed8\u8ba4\u6bcd\u7248");
            defaultVo.setPrintSchemeKey(printSchemeKey);
            result.add(defaultVo);
        }
        return result;
    }

    @ApiOperation(value="\u83b7\u53d6\u5173\u8054\u7684\u8868\u5355")
    @RequestMapping(value={"setting/get/linked/forms/{designerId}"}, method={RequestMethod.GET})
    public Collection<String> getLinkedForms(@PathVariable String designerId) {
        DesignerInfoDTO info = this.designExtendService.getPrintDesignerInfo(designerId);
        return null == info ? Collections.emptyList() : info.getLinkedForms();
    }

    @ApiOperation(value="\u66f4\u65b0\u5173\u8054\u7684\u8868\u5355")
    @RequestMapping(value={"setting/update/linked/forms/{designerId}"}, method={RequestMethod.POST})
    @TaskLog(operation="\u66f4\u65b0\u5173\u8054\u7684\u8868\u5355")
    public void updateLinkedForms(@PathVariable String designerId, @RequestBody List<String> linkedForms) {
        DesignerInfoDTO info = this.designExtendService.getPrintDesignerInfo(designerId);
        if (null != info) {
            info.setLinkedChange(true);
            info.setLinkedForms(linkedForms);
            this.designExtendService.updatePrintDesignerInfo(designerId, info);
        }
    }

    @ApiOperation(value="\u66f4\u65b0\u5173\u8054\u7684\u6bcd\u7248")
    @RequestMapping(value={"setting/get/linked/comtem/{designerId}", "setting/get/linked/comtem/{designerId}/{comtem}"}, method={RequestMethod.GET})
    public void updateLinkedComTem(@PathVariable String designerId, @PathVariable(required=false) String comtem) {
        this.designExtendService.updateLinkedComTem(designerId, comtem);
    }
}

