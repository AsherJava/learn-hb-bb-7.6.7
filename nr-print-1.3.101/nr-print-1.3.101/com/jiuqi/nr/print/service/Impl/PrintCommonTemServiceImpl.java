/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.grid.GridData
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.definition.api.IDesignTimePrintController
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.exception.NrDefinitionRuntimeException
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignPrintComTemDefine
 *  com.jiuqi.nr.definition.facade.DesignPrintTemplateDefine
 *  com.jiuqi.nr.definition.facade.print.common.other.PrintUtil
 *  com.jiuqi.nr.definition.facade.print.core.ReportTemplateObject
 *  com.jiuqi.nr.definition.print.common.PrintElementUtils
 *  com.jiuqi.nr.definition.print.common.PrintErrorEnum
 *  com.jiuqi.nvwa.definition.common.UUIDUtils
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.xg.process.IGraphicalElement
 *  com.jiuqi.xg.process.ITemplateDocument
 *  com.jiuqi.xg.process.ITemplateElement
 *  com.jiuqi.xg.process.ITemplateObject
 *  com.jiuqi.xg.process.ITemplateObjectFactory
 *  com.jiuqi.xg.process.ITemplatePage
 *  com.jiuqi.xg.process.obj.ImageTemplateObject
 *  com.jiuqi.xg.process.obj.PageTemplateObject
 *  com.jiuqi.xg.process.util.SerializeUtil
 *  com.jiuqi.xlib.resource.IResource
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.print.service.Impl;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.grid.GridData;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.definition.api.IDesignTimePrintController;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.exception.NrDefinitionRuntimeException;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignPrintComTemDefine;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateDefine;
import com.jiuqi.nr.definition.facade.print.common.other.PrintUtil;
import com.jiuqi.nr.definition.facade.print.core.ReportTemplateObject;
import com.jiuqi.nr.definition.print.common.PrintElementUtils;
import com.jiuqi.nr.definition.print.common.PrintErrorEnum;
import com.jiuqi.nr.print.dto.CommTemCheckDTO;
import com.jiuqi.nr.print.exception.PrintAttributeException;
import com.jiuqi.nr.print.exception.PrintDesignException;
import com.jiuqi.nr.print.service.IPrintCommonTemService;
import com.jiuqi.nr.print.service.IPrintDesignExtendService;
import com.jiuqi.nr.print.web.param.CommonTemplatePM;
import com.jiuqi.nvwa.definition.common.UUIDUtils;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.xg.process.IGraphicalElement;
import com.jiuqi.xg.process.ITemplateDocument;
import com.jiuqi.xg.process.ITemplateElement;
import com.jiuqi.xg.process.ITemplateObject;
import com.jiuqi.xg.process.ITemplateObjectFactory;
import com.jiuqi.xg.process.ITemplatePage;
import com.jiuqi.xg.process.obj.ImageTemplateObject;
import com.jiuqi.xg.process.obj.PageTemplateObject;
import com.jiuqi.xg.process.util.SerializeUtil;
import com.jiuqi.xlib.resource.IResource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@Service
public class PrintCommonTemServiceImpl
implements IPrintCommonTemService {
    private static final Logger logger = LoggerFactory.getLogger(PrintCommonTemServiceImpl.class);
    @Autowired
    private IDesignTimePrintController printController;
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private IPrintDesignExtendService printExtendService;

    @Override
    public void coverTemplate(CommonTemplatePM templatePM) {
        Assert.notNull((Object)templatePM.getDesignerId(), "'designerId' must not be null.");
        for (String formKey : templatePM.getFormKeys()) {
            try {
                this.doCover(formKey, templatePM.getPrintSchemeKey(), templatePM.getDesignerId());
            }
            catch (JQException e) {
                logger.error(PrintAttributeException.ATTRIBUTE_COVER_FAIL.getMessage(), e);
                throw new NrDefinitionRuntimeException((ErrorEnum)PrintAttributeException.ATTRIBUTE_COVER_FAIL, (Throwable)e);
            }
        }
    }

    @Override
    public void syncTemplate(CommonTemplatePM templatePM) {
        Assert.notNull((Object)templatePM.getDesignerId(), "'designerId' must not be null.");
        for (String formKey : templatePM.getFormKeys()) {
            try {
                this.doSync(formKey, templatePM.getPrintSchemeKey(), templatePM.getDesignerId());
            }
            catch (JQException e) {
                logger.error(PrintAttributeException.ATTRIBUTE_SYNC_FAIL.getMessage(), e);
                throw new NrDefinitionRuntimeException((ErrorEnum)PrintAttributeException.ATTRIBUTE_SYNC_FAIL, (Throwable)e);
            }
        }
    }

    private void doCover(String formKey, String printSchemekey, String designerId) throws JQException {
        DesignFormDefine report = this.designTimeViewController.getForm(formKey);
        DesignPrintTemplateDefine printTemplate = this.printController.getPrintTemplateBySchemeAndForm(printSchemekey, formKey);
        if (printTemplate != null) {
            this.printController.deletePrintTemplate(new String[]{printTemplate.getKey()});
        }
        String serializeTemplate = this.printExtendService.getCurrTemplateDocument(designerId);
        ITemplateDocument template = (ITemplateDocument)SerializeUtil.deserialize((String)serializeTemplate, (ITemplateObjectFactory)PrintElementUtils.FACTORY);
        ITemplateDocument updatedTemplate = this.replaceForm(template, report);
        ITemplateDocument updatedTemplate2 = this.setSpecialId(updatedTemplate);
        this.intsertTemplate(printSchemekey, report, designerId, updatedTemplate2);
    }

    public void doSync(String formKey, String printSchemekey, String designerId) throws JQException {
        DesignFormDefine report = this.designTimeViewController.getForm(formKey);
        String serializeTemplate = this.printExtendService.getCurrTemplateDocument(designerId);
        ITemplateDocument template = (ITemplateDocument)SerializeUtil.deserialize((String)serializeTemplate, (ITemplateObjectFactory)PrintElementUtils.FACTORY);
        DesignPrintTemplateDefine printTemplate = this.printController.getPrintTemplateBySchemeAndForm(printSchemekey, formKey);
        if (printTemplate != null) {
            ITemplateDocument reportTemplate = (ITemplateDocument)SerializeUtil.deserialize((String)new String(printTemplate.getTemplateData()), (ITemplateObjectFactory)PrintElementUtils.FACTORY);
            CommTemCheckDTO checkRes = this.syncCheck(reportTemplate, template);
            if (checkRes.getAddElement() != null && checkRes.getAddElement().length > 0) {
                this.addWorldLabel(checkRes.getAddElement(), reportTemplate, template);
            }
            if (checkRes.getDeleteElement() != null && checkRes.getDeleteElement().length > 0) {
                this.deleteWorldLabel(checkRes.getDeleteElement(), reportTemplate);
            }
            if (checkRes.getUpdateElement() != null && checkRes.getUpdateElement().length > 0) {
                this.updateWorldLabel(checkRes, reportTemplate);
            }
            this.syncPageSetting(reportTemplate, template);
            ITemplateDocument updatedTemplate = this.replaceForm(template, reportTemplate, report);
            this.intsertTemplate(printSchemekey, report, designerId, updatedTemplate);
        }
    }

    private CommTemCheckDTO syncCheck(ITemplateDocument printTemplate, ITemplateDocument commonTemplate) {
        CommTemCheckDTO checkRes = new CommTemCheckDTO();
        Map<String, IGraphicalElement> printElementMap = this.getPrintElementId(printTemplate);
        Map<String, IGraphicalElement> commonElementMap = this.getCommonElementId(commonTemplate);
        Set<String> printElementIds = printElementMap.keySet();
        Set<String> commonElementIds = commonElementMap.keySet();
        Set<String> deleteElementSet = this.judgeDelete(printElementIds, commonElementIds);
        Set<String> addElementSet = this.judgeAdd(commonElementIds, printElementIds);
        Set<String> updateElementSet = this.judgeUpdate(printElementIds, commonElementIds);
        Set<String> specialUpdateEleSet = this.toSpecialId(updateElementSet);
        ArrayList<IGraphicalElement> addElements = new ArrayList<IGraphicalElement>();
        ArrayList<IGraphicalElement> deleteElements = new ArrayList<IGraphicalElement>();
        ArrayList<IGraphicalElement> updateElements = new ArrayList<IGraphicalElement>();
        ArrayList<IGraphicalElement> updateCommonElements = new ArrayList<IGraphicalElement>();
        ArrayList<IGraphicalElement> updatePrintElements = new ArrayList<IGraphicalElement>();
        for (String addElementId : addElementSet) {
            addElements.add(commonElementMap.get(addElementId));
        }
        for (String deleteElementId : deleteElementSet) {
            deleteElements.add(printElementMap.get(deleteElementId));
        }
        for (String updateElementId : updateElementSet) {
            updateElements.add(commonElementMap.get(updateElementId));
            updateCommonElements.add(commonElementMap.get(updateElementId));
        }
        for (String specialEleId : specialUpdateEleSet) {
            updatePrintElements.add(printElementMap.get(specialEleId));
        }
        checkRes.setAddElement(addElements.toArray(new IGraphicalElement[addElementSet.size()]));
        checkRes.setDeleteElement(deleteElements.toArray(new IGraphicalElement[deleteElementSet.size()]));
        checkRes.setUpdateElement(updateElements.toArray(new IGraphicalElement[updateElementSet.size()]));
        checkRes.setUpdateCommonElements(updateCommonElements.toArray(new IGraphicalElement[updateElementSet.size()]));
        checkRes.setUpdatePrintElements(updatePrintElements.toArray(new IGraphicalElement[updateElementSet.size()]));
        return checkRes;
    }

    private void syncPageSetting(ITemplateDocument reportTemplate, ITemplateDocument commonTemplate) {
        PageTemplateObject reportPage = (PageTemplateObject)reportTemplate.getPage(0);
        PageTemplateObject commonTemPage = (PageTemplateObject)commonTemplate.getPage(0);
        reportPage.setPaper(commonTemPage.getPaper());
        reportPage.setOrientation(commonTemPage.getOrientation());
    }

    private void addWorldLabel(IGraphicalElement[] addElement, ITemplateDocument reportTemplate, ITemplateDocument commonTemplate) {
        PageTemplateObject page = (PageTemplateObject)reportTemplate.getPage(0);
        for (int i = 0; i < addElement.length; ++i) {
            if (addElement[i] instanceof ImageTemplateObject) {
                IResource resource = commonTemplate.getResourceManager().getResource(((ImageTemplateObject)addElement[i]).getImageId());
                reportTemplate.getResourceManager().createResource(resource.getKind(), resource.getName(), resource.getLocal(), resource.getBytes());
            }
            addElement[i].setID(this.setSpecialId(addElement[i].getID()));
            page.add((ITemplateElement)addElement[i]);
        }
    }

    private void deleteWorldLabel(IGraphicalElement[] deleteElement, ITemplateDocument reportTemplate) {
        PageTemplateObject page = (PageTemplateObject)reportTemplate.getPage(0);
        for (int i = 0; i < deleteElement.length; ++i) {
            page.remove((ITemplateElement)deleteElement[i]);
        }
    }

    private void updateWorldLabel(CommTemCheckDTO checkRes, ITemplateDocument reportTemplate) {
        int i;
        PageTemplateObject page = (PageTemplateObject)reportTemplate.getPage(0);
        for (i = 0; i < checkRes.getUpdateElement().length; ++i) {
            page.remove((ITemplateElement)checkRes.getUpdatePrintElements()[i]);
        }
        for (i = 0; i < checkRes.getUpdateElement().length; ++i) {
            checkRes.getUpdateCommonElements()[i].setID(checkRes.getUpdateCommonElements()[i].getID());
            page.add((ITemplateElement)checkRes.getUpdateCommonElements()[i]);
        }
    }

    private ITemplateDocument replaceForm(ITemplateDocument commonTemTemplate, ITemplateDocument targetTemplate, DesignFormDefine report) {
        PageTemplateObject commonPage = (PageTemplateObject)commonTemTemplate.getPage(0);
        ReportTemplateObject commonReportTemplateObject = PrintElementUtils.getReportTemplate((PageTemplateObject)commonPage);
        PageTemplateObject targetPage = (PageTemplateObject)targetTemplate.getPage(0);
        ReportTemplateObject targetReportTemplateObject = PrintElementUtils.getReportTemplate((PageTemplateObject)targetPage);
        if (targetReportTemplateObject != null && commonReportTemplateObject != null) {
            targetPage.remove((ITemplateElement)targetReportTemplateObject);
            targetPage.add((ITemplateElement)commonReportTemplateObject);
        }
        return this.replaceForm(targetTemplate, report);
    }

    private ITemplateDocument replaceForm(ITemplateDocument template, DesignFormDefine report) {
        PageTemplateObject page = (PageTemplateObject)template.getPage(0);
        ReportTemplateObject reportTemplate = PrintElementUtils.getReportTemplate((PageTemplateObject)page);
        if (reportTemplate != null) {
            page.remove((ITemplateElement)reportTemplate);
            byte[] reportData = report.getBinaryData();
            GridData gridData = new GridData();
            if (null != reportData) {
                PrintUtil.grid2DataToGridData((Grid2Data)Grid2Data.bytesToGrid((byte[])reportData), (GridData)gridData);
            }
            reportTemplate.setGridData(gridData);
        }
        page.add((ITemplateElement)reportTemplate);
        return template;
    }

    private void intsertTemplate(String printSchemeKey, DesignFormDefine report, String designerId, ITemplateDocument updatedTemplate) throws JQException {
        boolean isUpdate;
        if (report == null) {
            throw new JQException((ErrorEnum)PrintErrorEnum.PRINTERROR_002);
        }
        DesignPrintTemplateDefine printTemplate = this.printController.getPrintTemplateBySchemeAndForm(printSchemeKey, report.getKey());
        String templateData = SerializeUtil.serialize((ITemplateObject)updatedTemplate);
        boolean bl = isUpdate = printTemplate != null;
        if (!isUpdate) {
            printTemplate = this.printController.initPrintTemplate();
            printTemplate.setTitle(report.getTitle() + "\u6253\u5370\u6a21\u677f");
            printTemplate.setFormKey(report.getKey());
            printTemplate.setPrintSchemeKey(printSchemeKey);
            printTemplate.setOrder(OrderGenerator.newOrder());
            printTemplate.setUpdateTime(new Date());
        }
        byte[] printTemplateBytes = null;
        if (com.jiuqi.bi.util.StringUtils.isNotEmpty((String)templateData)) {
            printTemplateBytes = templateData.getBytes();
        }
        printTemplate.setTemplateData(printTemplateBytes);
        if (isUpdate) {
            this.printController.updatePrintTemplate(new DesignPrintTemplateDefine[]{printTemplate});
        } else {
            this.printController.insertPrintTemplate(new DesignPrintTemplateDefine[]{printTemplate});
        }
    }

    public Set<String> judgeUpdate(Set<String> printElementIds, Set<String> commonElementIds) {
        HashSet<String> result = new HashSet<String>();
        HashSet<String> usedCommonElementIds = new HashSet<String>();
        for (String commonElementId : commonElementIds) {
            usedCommonElementIds.add("commonTem_" + commonElementId);
        }
        result.addAll(printElementIds);
        result.retainAll(usedCommonElementIds);
        Set<String> updateResult = this.toOriginIds(result);
        return updateResult;
    }

    public Set<String> judgeAdd(Set<String> commonElementIds, Set<String> printElementIds) {
        HashSet<String> result = new HashSet<String>();
        HashSet<String> usedCommonElementIds = new HashSet<String>();
        for (String commonElementId : commonElementIds) {
            usedCommonElementIds.add("commonTem_" + commonElementId);
        }
        result.addAll(usedCommonElementIds);
        result.removeAll(printElementIds);
        Set<String> addResult = this.toOriginIds(result);
        return addResult;
    }

    public Set<String> judgeDelete(Set<String> printElementIds, Set<String> commonElementIds) {
        HashSet<String> result = new HashSet<String>();
        HashSet<String> usedCommonElementIds = new HashSet<String>();
        HashSet<String> usedPrintElementIds = new HashSet<String>();
        String commonPrefix = "commonTem";
        for (String commonElementId : commonElementIds) {
            usedCommonElementIds.add("commonTem_" + commonElementId);
        }
        for (String printElementId : printElementIds) {
            if (!printElementId.substring(0, 9).equals(commonPrefix)) continue;
            usedPrintElementIds.add(printElementId);
        }
        result.addAll(usedPrintElementIds);
        result.removeAll(usedCommonElementIds);
        return result;
    }

    private Map<String, IGraphicalElement> getPrintElementId(ITemplateDocument printTemplate) {
        HashMap<String, IGraphicalElement> elementMap = new HashMap<String, IGraphicalElement>();
        for (ITemplatePage page : printTemplate.getPages()) {
            for (IGraphicalElement element : page.getGraphicalElements()) {
                if (element instanceof ReportTemplateObject) continue;
                elementMap.put(element.getID(), element);
            }
        }
        return elementMap;
    }

    private Map<String, IGraphicalElement> getCommonElementId(ITemplateDocument commonTemplate) {
        HashMap<String, IGraphicalElement> elementMap = new HashMap<String, IGraphicalElement>();
        for (ITemplatePage page : commonTemplate.getPages()) {
            for (IGraphicalElement element : page.getGraphicalElements()) {
                if (element instanceof ReportTemplateObject) continue;
                elementMap.put(element.getID(), element);
            }
        }
        return elementMap;
    }

    private ITemplateDocument setSpecialId(ITemplateDocument template) {
        for (ITemplatePage page : template.getPages()) {
            for (IGraphicalElement element : page.getGraphicalElements()) {
                element.setID("commonTem_" + element.getID());
            }
        }
        return template;
    }

    private String setSpecialId(String elementId) {
        return "commonTem_" + elementId;
    }

    private Set<String> toSpecialId(Set<String> elementIds) {
        HashSet<String> result = new HashSet<String>();
        for (String elementId : elementIds) {
            result.add("commonTem_" + elementId);
        }
        return result;
    }

    private Set<String> toOriginIds(Set<String> result) {
        HashSet<String> usedResult = new HashSet<String>();
        String commonPrefix = "commonTem_";
        for (String commonIds : result) {
            usedResult.add(commonIds.replace(commonPrefix, ""));
        }
        return usedResult;
    }

    private void checkComTem(DesignPrintComTemDefine define) throws JQException {
        if (!StringUtils.hasText(define.getPrintSchemeKey())) {
            throw new JQException((ErrorEnum)PrintDesignException.COMTEM_SAVE_FAIL, "\u6bcd\u7248\u6240\u5c5e\u6253\u5370\u65b9\u6848\u4e3a\u7a7a");
        }
        if (!StringUtils.hasText(define.getTitle())) {
            throw new JQException((ErrorEnum)PrintDesignException.COMTEM_SAVE_FAIL, "\u6bcd\u7248\u6807\u9898\u4e3a\u7a7a");
        }
        if (!StringUtils.hasText(define.getTitle())) {
            define.setKey(UUIDUtils.getKey());
        }
        List comTemDefines = this.printController.listPrintComTemBySchemeWithoutBigData(define.getPrintSchemeKey());
        boolean codeMatch = false;
        boolean titleMatch = false;
        for (DesignPrintComTemDefine comTemDefine : comTemDefines) {
            if (comTemDefine.getKey().equals(define.getKey())) continue;
            codeMatch |= comTemDefine.getCode().equals(define.getCode());
            titleMatch |= comTemDefine.getTitle().equals(define.getTitle());
        }
        if (codeMatch) {
            throw new JQException((ErrorEnum)PrintDesignException.COMTEM_SAVE_FAIL, "\u6bcd\u7248\u6807\u8bc6\u91cd\u590d");
        }
        if (titleMatch) {
            throw new JQException((ErrorEnum)PrintDesignException.COMTEM_SAVE_FAIL, "\u6bcd\u7248\u6807\u9898\u91cd\u590d");
        }
    }

    private boolean checkDefaultComTem(String printSchemeKey) {
        DesignPrintComTemDefine define = this.printController.getPrintComTem(printSchemeKey);
        if (null != define) {
            return false;
        }
        define = this.printController.initPrintComTem();
        define.setKey(printSchemeKey);
        define.setCode("DEFAULT");
        define.setTitle("\u9ed8\u8ba4\u6bcd\u7248");
        define.setPrintSchemeKey(printSchemeKey);
        define.setOrder(OrderGenerator.newOrder());
        this.printController.insertPrintComTem(define);
        return true;
    }

    @Override
    @Transactional
    public String insertPrintComTem(DesignPrintComTemDefine define) throws JQException {
        this.checkComTem(define);
        if (this.checkDefaultComTem(define.getPrintSchemeKey())) {
            define.setOrder(OrderGenerator.newOrder());
        }
        this.printController.insertPrintComTem(define);
        return define.getKey();
    }

    @Override
    @Transactional
    public void updatePrintComTem(DesignPrintComTemDefine define) throws JQException {
        if (define.isDefault()) {
            throw new JQException((ErrorEnum)PrintDesignException.COMTEM_SAVE_FAIL, "\u9ed8\u8ba4\u6bcd\u7248\u4e0d\u5141\u8bb8\u7f16\u8f91");
        }
        this.checkComTem(define);
        this.printController.updatePrintComTem(define);
    }

    @Override
    @Transactional
    public void deletePrintComTem(DesignPrintComTemDefine define) throws JQException {
        if (define.isDefault()) {
            throw new JQException((ErrorEnum)PrintDesignException.COMTEM_DELETE_FAIL, "\u9ed8\u8ba4\u6bcd\u7248\u4e0d\u5141\u8bb8\u5220\u9664");
        }
        List<DesignPrintTemplateDefine> templates = this.printController.listPrintTemplateByScheme(define.getPrintSchemeKey()).stream().filter(t -> define.getCode().equals(t.getComTemCode())).peek(t -> t.setComTemCode(null)).collect(Collectors.toList());
        if (!templates.isEmpty()) {
            this.printController.updatePrintTemplate(templates.toArray(templates.toArray(new DesignPrintTemplateDefine[0])));
        }
        this.printController.deletePrintComTem(define.getKey());
    }
}

