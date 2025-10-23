/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.DeserializationFeature
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.Module
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.module.SimpleModule
 *  com.jiuqi.grid.GridData
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.np.cache.internal.springcache.CacheProvider
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.definition.api.IDesignTimePrintController
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.exception.NrDefinitionRuntimeException
 *  com.jiuqi.nr.definition.facade.DesignPrintComTemDefine
 *  com.jiuqi.nr.definition.facade.DesignPrintTemplateDefine
 *  com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine
 *  com.jiuqi.nr.definition.facade.PrintTemplateDefine
 *  com.jiuqi.nr.definition.facade.print.common.other.PrintUtil
 *  com.jiuqi.nr.definition.facade.print.common.runtime.factory.ReportElementConfigerFactory
 *  com.jiuqi.nr.definition.facade.print.core.ReportTemplateObject
 *  com.jiuqi.nr.definition.print.common.PrintElementUtils
 *  com.jiuqi.nr.task.api.cellbook.CellBookInit
 *  com.jiuqi.nvwa.cellbook.converter.CellBookGrid2dataConverter
 *  com.jiuqi.nvwa.cellbook.json.CellBookSerialize
 *  com.jiuqi.nvwa.cellbook.model.CellBook
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.GridCellData
 *  com.jiuqi.print.designer.PrintDesigner
 *  com.jiuqi.print.designer.command.ModifyPropertyCommand
 *  com.jiuqi.print.designer.entiry.ElementItem
 *  com.jiuqi.print.designer.exception.DesignerNotInitException
 *  com.jiuqi.print.designer.exception.ModelNotFoundException
 *  com.jiuqi.print.designer.factory.ElementConfigerFactory
 *  com.jiuqi.print.designer.util.ResourceUtils
 *  com.jiuqi.print.viewer.PrintPreviewer
 *  com.jiuqi.print.viewer.command.ModifyPropertyCommand
 *  com.jiuqi.print.viewer.exception.ModelNotFoundException
 *  com.jiuqi.print.viewer.exception.ViewerNotInitException
 *  com.jiuqi.print.viewer.exception.ViewerNotLoadException
 *  com.jiuqi.print.viewer.interactor.DnaPrintPaginateInteractor
 *  com.jiuqi.print.viewer.util.DnaPrintProcessUtil
 *  com.jiuqi.xg.gef.Incident
 *  com.jiuqi.xg.gef.internal.JSONHelper
 *  com.jiuqi.xg.print.internal.obj.PrintPageSetting
 *  com.jiuqi.xg.print.util.AsyncResult
 *  com.jiuqi.xg.print.util.AsyncWork
 *  com.jiuqi.xg.print.util.AsyncWorkContainnerUtil
 *  com.jiuqi.xg.print.viewer.IElementConfiger
 *  com.jiuqi.xg.print.viewer.IPrintViewer
 *  com.jiuqi.xg.print.viewer.internal.PrintViewer
 *  com.jiuqi.xg.process.GraphicalFactoryManager
 *  com.jiuqi.xg.process.IDrawDocument
 *  com.jiuqi.xg.process.IGraphicalDocument
 *  com.jiuqi.xg.process.IGraphicalElement
 *  com.jiuqi.xg.process.IGraphicalObject
 *  com.jiuqi.xg.process.IGraphicalPage
 *  com.jiuqi.xg.process.IPaginateContext
 *  com.jiuqi.xg.process.IPaginateFactory
 *  com.jiuqi.xg.process.IPaginateInteractor
 *  com.jiuqi.xg.process.IProcessMonitor
 *  com.jiuqi.xg.process.ITemplateDocument
 *  com.jiuqi.xg.process.ITemplateElement
 *  com.jiuqi.xg.process.ITemplateObject
 *  com.jiuqi.xg.process.ITemplateObjectFactory
 *  com.jiuqi.xg.process.ITemplatePage
 *  com.jiuqi.xg.process.NullProcessMonitor
 *  com.jiuqi.xg.process.Paper
 *  com.jiuqi.xg.process.obj.AbstractGraphicalObject
 *  com.jiuqi.xg.process.obj.PageTemplateObject
 *  com.jiuqi.xg.process.table.obj.TableTemplateObject
 *  com.jiuqi.xg.process.util.SerializeUtil
 *  com.jiuqi.xg.process.watermark.WatermarkConfig
 *  com.jiuqi.xg.process.watermark.WatermarkTemplateObject
 *  com.jiuqi.xlib.IAction
 *  com.jiuqi.xlib.ICommand
 *  com.jiuqi.xlib.spring.SpringChannel
 *  com.jiuqi.xlib.spring.SpringChannel$Invoke
 *  javax.el.PropertyNotFoundException
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.nr.print.service.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.jiuqi.grid.GridData;
import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.cache.internal.springcache.CacheProvider;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.definition.api.IDesignTimePrintController;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.exception.NrDefinitionRuntimeException;
import com.jiuqi.nr.definition.facade.DesignPrintComTemDefine;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateDefine;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine;
import com.jiuqi.nr.definition.facade.PrintTemplateDefine;
import com.jiuqi.nr.definition.facade.print.common.other.PrintUtil;
import com.jiuqi.nr.definition.facade.print.common.runtime.factory.ReportElementConfigerFactory;
import com.jiuqi.nr.definition.facade.print.core.ReportTemplateObject;
import com.jiuqi.nr.definition.print.common.PrintElementUtils;
import com.jiuqi.nr.print.action.ClearAction;
import com.jiuqi.nr.print.action.PageAdaptiveAction;
import com.jiuqi.nr.print.action.ReloadAction;
import com.jiuqi.nr.print.action.SaveAction;
import com.jiuqi.nr.print.dto.DesignerInfoDTO;
import com.jiuqi.nr.print.dto.ReportLabelDTO;
import com.jiuqi.nr.print.exception.PrintDesignException;
import com.jiuqi.nr.print.exception.PrintDesignerException;
import com.jiuqi.nr.print.service.IPrintDesignExtendService;
import com.jiuqi.nr.task.api.cellbook.CellBookInit;
import com.jiuqi.nvwa.cellbook.converter.CellBookGrid2dataConverter;
import com.jiuqi.nvwa.cellbook.json.CellBookSerialize;
import com.jiuqi.nvwa.cellbook.model.CellBook;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.GridCellData;
import com.jiuqi.print.designer.PrintDesigner;
import com.jiuqi.print.designer.entiry.ElementItem;
import com.jiuqi.print.designer.exception.DesignerNotInitException;
import com.jiuqi.print.designer.factory.ElementConfigerFactory;
import com.jiuqi.print.designer.util.ResourceUtils;
import com.jiuqi.print.viewer.PrintPreviewer;
import com.jiuqi.print.viewer.command.ModifyPropertyCommand;
import com.jiuqi.print.viewer.exception.ModelNotFoundException;
import com.jiuqi.print.viewer.exception.ViewerNotInitException;
import com.jiuqi.print.viewer.exception.ViewerNotLoadException;
import com.jiuqi.print.viewer.interactor.DnaPrintPaginateInteractor;
import com.jiuqi.print.viewer.util.DnaPrintProcessUtil;
import com.jiuqi.xg.gef.Incident;
import com.jiuqi.xg.gef.internal.JSONHelper;
import com.jiuqi.xg.print.internal.obj.PrintPageSetting;
import com.jiuqi.xg.print.util.AsyncResult;
import com.jiuqi.xg.print.util.AsyncWork;
import com.jiuqi.xg.print.util.AsyncWorkContainnerUtil;
import com.jiuqi.xg.print.viewer.IElementConfiger;
import com.jiuqi.xg.print.viewer.IPrintViewer;
import com.jiuqi.xg.print.viewer.internal.PrintViewer;
import com.jiuqi.xg.process.GraphicalFactoryManager;
import com.jiuqi.xg.process.IDrawDocument;
import com.jiuqi.xg.process.IGraphicalDocument;
import com.jiuqi.xg.process.IGraphicalElement;
import com.jiuqi.xg.process.IGraphicalObject;
import com.jiuqi.xg.process.IGraphicalPage;
import com.jiuqi.xg.process.IPaginateContext;
import com.jiuqi.xg.process.IPaginateFactory;
import com.jiuqi.xg.process.IPaginateInteractor;
import com.jiuqi.xg.process.IProcessMonitor;
import com.jiuqi.xg.process.ITemplateDocument;
import com.jiuqi.xg.process.ITemplateElement;
import com.jiuqi.xg.process.ITemplateObject;
import com.jiuqi.xg.process.ITemplateObjectFactory;
import com.jiuqi.xg.process.ITemplatePage;
import com.jiuqi.xg.process.NullProcessMonitor;
import com.jiuqi.xg.process.Paper;
import com.jiuqi.xg.process.obj.AbstractGraphicalObject;
import com.jiuqi.xg.process.obj.PageTemplateObject;
import com.jiuqi.xg.process.table.obj.TableTemplateObject;
import com.jiuqi.xg.process.util.SerializeUtil;
import com.jiuqi.xg.process.watermark.WatermarkConfig;
import com.jiuqi.xg.process.watermark.WatermarkTemplateObject;
import com.jiuqi.xlib.IAction;
import com.jiuqi.xlib.ICommand;
import com.jiuqi.xlib.spring.SpringChannel;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.el.PropertyNotFoundException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@Service(value="PrintBaseImpl")
public class PrintDesignExtendServiceImpl
implements IPrintDesignExtendService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PrintDesignExtendServiceImpl.class);
    private NedisCacheManager cacheManager;
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private IDesignTimePrintController designTimePrintController;
    public static final String PROPERTIES_DISABLED = "disabled";
    public static final String PROPERTIES_DISABLED_VALUE = Boolean.TRUE.toString();
    public static final String PROPERTIES_NOT_DISABLED = "notDisabledProperties";
    public static final Set<String> PROPERTIES_NOT_DISABLED_VALUE_SET = new HashSet<String>(Arrays.asList("paginateConfigEdit", "resizeConfigEdit", "isRowPaginateFirst", "paginateConfig", "resizeConfig", "gridData"));
    public static final String PROPERTIES_NOT_DISABLED_VALUE = new JSONArray(PROPERTIES_NOT_DISABLED_VALUE_SET).toString();

    @Autowired
    public void setCacheManager(CacheProvider cacheProvider) {
        this.cacheManager = cacheProvider.getCacheManager("nr-print-designer");
    }

    private NedisCache getCache(String designerId) {
        return this.cacheManager.getCache(designerId);
    }

    @Override
    public DesignerInfoDTO getPrintDesignerInfo(String designerId) {
        return (DesignerInfoDTO)this.cacheManager.getCache(designerId).get("PRINT_DESIGNER_INFO", DesignerInfoDTO.class);
    }

    @Override
    public void updatePrintDesignerInfo(String designerId, DesignerInfoDTO dto) {
        this.cacheManager.getCache(designerId).put("PRINT_DESIGNER_INFO", (Object)dto);
    }

    @Override
    public void updateLinkedComTem(String designerId, String commonCode) {
        DesignerInfoDTO info = this.getPrintDesignerInfo(designerId);
        if (null == info) {
            return;
        }
        info.setLinkedChange(true);
        info.setLinkedCommonCode(commonCode);
        ITemplateDocument document = null;
        if (StringUtils.hasText(commonCode)) {
            info.setCustomGrid(false);
            info.setCustomGuidDate(null);
            document = this.designTimePrintController.initTemplateDocument(info.getPrintSchemeId(), commonCode, info.getFormId());
        } else {
            document = this.getTemplateDocument(designerId);
        }
        this.updateTemplate(designerId, document);
    }

    public String init(String nature, String designerId) {
        NedisCache cache;
        PrintDesigner designer;
        if (!StringUtils.hasLength(designerId)) {
            designerId = UUID.randomUUID().toString();
        }
        if (null == (designer = (PrintDesigner)(cache = this.getCache(designerId)).get(designerId, PrintDesigner.class))) {
            designer = new PrintDesigner((Object)designerId, "REPORT_PRINT_NATURE");
            designer.addElementItem(new ElementItem("page", new String[0]));
            IElementConfiger reportTextConf = ReportElementConfigerFactory.getElementConfiger((String)nature, (String)"element_wordLabel");
            designer.addElementItem(new ElementItem("element_wordLabel", new String[0], reportTextConf));
            IElementConfiger reportTextConfLabel = ReportElementConfigerFactory.getElementConfiger((String)nature, (String)"element_reportLabel");
            designer.addElementItem(new ElementItem("element_reportLabel", new String[0], reportTextConfLabel));
            IElementConfiger lineConf = ElementConfigerFactory.getElementConfiger((String)nature, (String)"line");
            designer.addElementItem(new ElementItem("line", new String[]{"basicProperty", "lineProperty"}, lineConf));
            IElementConfiger imgConf = ElementConfigerFactory.getElementConfiger((String)nature, (String)"image");
            designer.addElementItem(new ElementItem("image", new String[]{"basicProperty", "imageProperty"}, imgConf));
            IElementConfiger reportTableConf = ReportElementConfigerFactory.getElementConfiger((String)nature, (String)"element_report");
            designer.addElementItem(new ElementItem("element_report", new String[0], reportTableConf));
            cache.put(designerId, (Object)designer);
        }
        this.reloadPrintActions(designerId);
        LOGGER.debug("\u62a5\u8868\u6253\u5370\u8bbe\u8ba1\u670d\u52a1: \u521d\u59cb\u5316\u6253\u5370\u8bbe\u8ba1\u5668, \u8bbe\u8ba1\u5668ID: {}", (Object)designerId);
        return designerId;
    }

    public void reloadPrintActions(String designerId) {
        NedisCache cache = this.getCache(designerId);
        PrintDesigner designer = (PrintDesigner)cache.get(designerId, PrintDesigner.class);
        if (null == designer) {
            throw new DesignerNotInitException("Print designer isn't init!");
        }
        IPrintViewer viewer = designer.getViewer();
        viewer.getActionManager().addAction((IAction)new SaveAction("report_save", designerId, this, this.designTimePrintController));
        viewer.getActionManager().addAction((IAction)new ReloadAction("report_reload", designerId, this, this.designTimePrintController, this.designTimeViewController));
        viewer.getActionManager().addAction((IAction)new ClearAction("clearDesigner", designerId, this, this.designTimePrintController));
        viewer.getActionManager().addAction((IAction)new PageAdaptiveAction("printPageAdaptive", designer));
    }

    public String load(String designerId, String content) {
        ITemplateDocument template;
        NedisCache cache = this.getCache(designerId);
        DesignerInfoDTO info = DesignerInfoDTO.valueOf(content);
        PrintDesigner designer = (PrintDesigner)cache.get(designerId, PrintDesigner.class);
        try {
            if (info.isCoverTemplate()) {
                template = this.designTimePrintController.getCoverTemplateDocument(info.getPrintSchemeId());
            } else if (info.isCommonTemplate()) {
                String key = StringUtils.hasText(info.getPrintTemplateId()) ? info.getPrintTemplateId() : info.getPrintSchemeId();
                template = this.designTimePrintController.getCommonTemplateDocument(key);
                info.setLinkedForms(this.getLinkedForms(info.getPrintSchemeId(), key));
            } else {
                DesignPrintTemplateDefine define = this.designTimePrintController.getPrintTemplateBySchemeAndForm(info.getPrintSchemeId(), info.getFormId());
                if (null == define) {
                    template = this.designTimePrintController.initTemplateDocument(info.getPrintSchemeId(), "DEFAULT", info.getFormId());
                    info.setLinkedCommonCode("DEFAULT");
                } else {
                    template = this.designTimePrintController.getTemplateDocument(define);
                    info.setLinkedCommonCode(define.getComTemCode());
                    info.setCustomGrid(!define.isAutoRefreshForm());
                    info.setCustomGuidDate(define.getFormUpdateTime());
                }
            }
        }
        catch (Exception e) {
            LOGGER.error(PrintDesignException.TEMPLATE_LOAD_FAIL.getMessage(), e);
            throw new NrDefinitionRuntimeException((ErrorEnum)PrintDesignException.TEMPLATE_LOAD_FAIL, (Throwable)e);
        }
        if (null == designer) {
            this.init("REPORT_PRINT_NATURE", designerId);
            designer = (PrintDesigner)cache.get(designerId, PrintDesigner.class);
        }
        info.setDesignerId(designerId);
        this.updatePrintDesignerInfo(designerId, info);
        this.updateTemplate(designerId, designer, template);
        info.setOriginTemplate(SerializeUtil.serialize((ITemplateObject)designer.getContent()));
        this.updatePrintDesignerInfo(designerId, info);
        LOGGER.debug("\u62a5\u8868\u6253\u5370\u8bbe\u8ba1\u670d\u52a1: \u52a0\u8f7d\u6253\u5370\u8bbe\u8ba1\u6a21\u677f, \u6a21\u677f\u4fe1\u606f: {}", (Object)info);
        return designerId;
    }

    public Collection<String> getLinkedForms(String printSchemeKey, String commonKey) {
        String commonCode;
        DesignPrintComTemDefine define = this.designTimePrintController.getPrintComTem(commonKey);
        String string = commonCode = null != define ? define.getCode() : "DEFAULT";
        if (!"DEFAULT".equals(commonCode)) {
            return this.designTimePrintController.listPrintTemplateByScheme(printSchemeKey).stream().filter(t -> commonCode.equals(t.getComTemCode())).map(PrintTemplateDefine::getFormKey).collect(Collectors.toSet());
        }
        DesignPrintTemplateSchemeDefine scheme = this.designTimePrintController.getPrintTemplateScheme(printSchemeKey);
        List templates = this.designTimePrintController.listPrintTemplateByScheme(printSchemeKey);
        Set<String> forms = this.designTimeViewController.queryAllSoftFormDefinesByFormScheme(scheme.getFormSchemeKey()).stream().map(IBaseMetaItem::getKey).collect(Collectors.toSet());
        for (DesignPrintTemplateDefine template : templates) {
            if (commonCode.equals(template.getComTemCode())) continue;
            forms.remove(template.getFormKey());
        }
        return forms;
    }

    private void handleTemplate(ITemplateDocument templateDocument) {
        ITemplatePage page = templateDocument.getPages()[0];
        for (IGraphicalElement element : page.getGraphicalElements()) {
            if (!(element instanceof ReportTemplateObject)) continue;
            element.setProperty("seqPaginateHeight", (Object)String.valueOf(element.getHeight()));
            element.setProperty("seqPaginateWidth", (Object)String.valueOf(element.getWidth()));
        }
    }

    private void updateTemplate(String designerId, PrintDesigner designer, ITemplateDocument templateDocument) {
        NedisCache cache = this.getCache(designerId);
        if (null == templateDocument) {
            return;
        }
        HashMap<String, Object> commonModels = null;
        DesignerInfoDTO info = this.getPrintDesignerInfo(designerId);
        if (null != info.getDesignerVersion() && DesignerInfoDTO.DesignerVersion.V1 != info.getDesignerVersion() && info.isFormTemplate() && StringUtils.hasText(info.getLinkedCommonCode())) {
            commonModels = new HashMap<String, Object>();
            ITemplateDocument common = this.designTimePrintController.getCommonTemplateDocument(info.getPrintSchemeId(), info.getLinkedCommonCode());
            if (null == common) {
                info.setLinkedCommonCode(null);
            } else {
                for (ITemplatePage page : common.getPages()) {
                    for (ITemplateElement element : page.getTemplateElements()) {
                        commonModels.put(PrintElementUtils.linkCommon((String)element.getID()), element);
                    }
                    commonModels.put(PrintElementUtils.linkCommon((String)page.getID()), page);
                }
                commonModels.put(info.getLinkedCommonCode(), common);
                cache.hMSet("PRINT_COMMON", commonModels);
            }
        }
        this.handleTemplate(templateDocument);
        Pattern compile = Pattern.compile("^[a-zA-Z0-9-_]+$");
        for (ITemplatePage page : templateDocument.getPages()) {
            ITemplateElement[] elements;
            Matcher pageIdMatcher = compile.matcher(page.getID());
            if (!pageIdMatcher.matches()) {
                page.setID(UUIDUtils.getKey());
            }
            cache.put(page.getID(), (Object)page);
            for (ITemplateElement element : elements = page.getTemplateElements()) {
                Matcher elementMatcher = compile.matcher(element.getID());
                if (!elementMatcher.matches()) {
                    element.setID(UUIDUtils.getKey());
                }
                if (null != commonModels && commonModels.containsKey(element.getID())) {
                    element.setProperty(PROPERTIES_DISABLED, (Object)PROPERTIES_DISABLED_VALUE);
                    element.setProperty(PROPERTIES_NOT_DISABLED, (Object)PROPERTIES_NOT_DISABLED_VALUE);
                    if (element instanceof ReportTemplateObject) {
                        element.addPropertyChangeListener(PrintDesignExtendServiceImpl.getPropertyChangeListener(cache));
                    }
                } else {
                    element.setProperty(PROPERTIES_DISABLED, null);
                    element.setProperty(PROPERTIES_NOT_DISABLED, null);
                }
                cache.put(element.getID(), (Object)element);
            }
        }
        designer.setContent(templateDocument);
    }

    private static PropertyChangeListener getPropertyChangeListener(NedisCache cache) {
        return evt -> {
            Object source = evt.getSource();
            String propertyName = evt.getPropertyName();
            Object newValue = evt.getNewValue();
            if (Boolean.FALSE == newValue && source instanceof ReportTemplateObject) {
                if (propertyName.equals("resizeConfigEdit")) {
                    Object object;
                    ReportTemplateObject obj = (ReportTemplateObject)source;
                    Cache.ValueWrapper valueWrapper = cache.hGet("PRINT_COMMON", (Object)obj.getID());
                    Object object2 = object = null == valueWrapper ? null : valueWrapper.get();
                    if (object instanceof ReportTemplateObject) {
                        obj.setResizeConfig(((ReportTemplateObject)object).getResizeConfig());
                        obj.setWidth(((ReportTemplateObject)object).getWidth());
                        obj.setHeight(((ReportTemplateObject)object).getHeight());
                    }
                } else if (propertyName.equals("paginateConfigEdit")) {
                    Object object;
                    ReportTemplateObject obj = (ReportTemplateObject)source;
                    Cache.ValueWrapper valueWrapper = cache.hGet("PRINT_COMMON", (Object)obj.getID());
                    Object object3 = object = null == valueWrapper ? null : valueWrapper.get();
                    if (object instanceof ReportTemplateObject) {
                        obj.setPaginateConfig(((ReportTemplateObject)object).getPaginateConfig());
                        obj.setRowPaginateFirst(((ReportTemplateObject)object).isRowPaginateFirst());
                    }
                }
            }
        };
    }

    public void destoryDesigner(String designerId) {
        NedisCache cache = this.getCache(designerId);
        cache.evict(designerId);
        LOGGER.debug("\u62a5\u8868\u6253\u5370\u8bbe\u8ba1\u670d\u52a1: \u9500\u6bc1\u6253\u5370\u8bbe\u8ba1\u5668, \u8bbe\u8ba1\u5668ID: {}", (Object)designerId);
    }

    private ITemplateObject getTemplateObject(String designerId, String elementId) {
        NedisCache cache = this.getCache(designerId);
        PrintDesigner printDesigner = (PrintDesigner)cache.get(designerId, PrintDesigner.class);
        if (null == printDesigner) {
            return null;
        }
        if (designerId.equals(elementId)) {
            return printDesigner.getContent();
        }
        Object element = (ITemplateObject)cache.get(elementId, ITemplateObject.class);
        if (null == element) {
            block0: for (ITemplatePage page : printDesigner.getDocument().getPages()) {
                if (page.getID().equals(elementId)) {
                    element = page;
                    cache.put(page.getID(), (Object)page);
                    for (IGraphicalElement item : page) {
                        cache.put(item.getID(), (Object)item);
                    }
                    break;
                }
                for (ITemplateElement item : page.getTemplateElements()) {
                    if (!item.getID().equals(elementId)) continue;
                    element = item;
                    cache.put(item.getID(), (Object)item);
                    continue block0;
                }
            }
            if (null == element) {
                return null;
            }
        }
        return element;
    }

    public String getAllProterty(String designerId, String modelId) {
        LOGGER.debug("\u62a5\u8868\u6253\u5370\u8bbe\u8ba1\u670d\u52a1: \u83b7\u53d6\u63a7\u4ef6\u5c5e\u6027, \u8bbe\u8ba1\u5668ID: {}, \u63a7\u4ef6ID: {}", (Object)designerId, (Object)modelId);
        ITemplateObject element = this.getTemplateObject(designerId, modelId);
        if (null == element || element instanceof ITemplatePage) {
            return "";
        }
        AbstractGraphicalObject graphicalObject = (AbstractGraphicalObject)element;
        JSONObject jo = graphicalObject.serialize();
        return null == jo ? "" : jo.toString();
    }

    public String getProterty(String designerId, String modelId, String propertyName) {
        ITemplateObject element = this.getTemplateObject(designerId, modelId);
        if (null == element) {
            return "";
        }
        AbstractGraphicalObject graphicalObject = (AbstractGraphicalObject)element;
        JSONObject jo = graphicalObject.serialize();
        return null == jo ? "" : jo.getString(propertyName);
    }

    public String updateProterty(String designerId, String modelId, String content) {
        LOGGER.debug("\u62a5\u8868\u6253\u5370\u8bbe\u8ba1\u670d\u52a1: \u66f4\u65b0\u63a7\u4ef6\u5c5e\u6027, \u8bbe\u8ba1\u5668ID: {}, \u63a7\u4ef6ID: {}, \u5c5e\u6027: {}", designerId, modelId, content);
        NedisCache cache = this.getCache(designerId);
        JSONArray arrays = new JSONArray(content);
        PrintDesigner printDesigner = (PrintDesigner)cache.get(designerId, PrintDesigner.class);
        if (modelId.equals("templetePageSetting")) {
            this.updatePrintSettings(arrays, printDesigner);
        } else {
            ITemplateElement<?> element = IPrintDesignExtendService.getITemplateElement(printDesigner.getContent(), modelId);
            if (null == element) {
                throw new com.jiuqi.print.designer.exception.ModelNotFoundException(designerId + " ");
            }
            int length = arrays.length();
            for (int i = 0; i < length; ++i) {
                JSONObject jo = arrays.getJSONObject(i);
                Object oldValue = null;
                if (jo.has("oldValue")) {
                    oldValue = jo.get("oldValue");
                }
                String propertyName = jo.getString("propertyName");
                if (PROPERTIES_DISABLED_VALUE.equals(element.getProperty(PROPERTIES_DISABLED)) && !PROPERTIES_NOT_DISABLED_VALUE_SET.contains(propertyName)) continue;
                com.jiuqi.print.designer.command.ModifyPropertyCommand command = new com.jiuqi.print.designer.command.ModifyPropertyCommand(element, propertyName, oldValue, (Object)jo.getString("value"));
                printDesigner.getViewer().getCommandStack().execute((ICommand)command);
            }
        }
        return "";
    }

    private void updatePrintSettings(JSONArray arrays, PrintDesigner printDesigner) {
        IGraphicalPage page;
        IGraphicalDocument document = printDesigner.getViewer().getContent();
        IGraphicalPage oldPage = page = document.getPage(0);
        int length = arrays.length();
        for (int i = 0; i < length; ++i) {
            JSONObject jo = arrays.getJSONObject(i);
            ObjectMapper mapper = new ObjectMapper();
            try {
                if ("markConfig".equals(jo.getString("propertyName"))) {
                    this.updateWatermark(mapper, page, jo);
                } else if ("page".equals(jo.getString("propertyName"))) {
                    this.updatePageSettings(mapper, page, jo);
                } else {
                    return;
                }
                com.jiuqi.print.designer.command.ModifyPropertyCommand command = new com.jiuqi.print.designer.command.ModifyPropertyCommand((Object)document, jo.getString("propertyName"), (Object)oldPage, (Object)page);
                printDesigner.getViewer().getCommandStack().execute((ICommand)command);
                continue;
            }
            catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
    }

    private void updateWatermark(ObjectMapper mapper, IGraphicalPage page, JSONObject jo) throws IOException {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        PageTemplateObject pageObj = (PageTemplateObject)page;
        if (!jo.getBoolean("enableMark")) {
            WatermarkTemplateObject.removeWatermark((PageTemplateObject)pageObj);
        } else {
            WatermarkConfig oldConfig;
            WatermarkConfig config = (WatermarkConfig)mapper.readValue(jo.getString("value"), WatermarkConfig.class);
            if (!config.equals((Object)(oldConfig = (WatermarkConfig)mapper.readValue(jo.getString("oldValue"), WatermarkConfig.class)))) {
                WatermarkTemplateObject.removeWatermark((PageTemplateObject)pageObj);
                WatermarkTemplateObject.createWatermark((PageTemplateObject)pageObj, (WatermarkConfig)config);
                for (ITemplateElement element : pageObj.getTemplateElements()) {
                    if (!(element instanceof TableTemplateObject)) continue;
                    pageObj.remove(element);
                    pageObj.add(0, element);
                    break;
                }
            }
        }
    }

    private void updatePageSettings(ObjectMapper mapper, IGraphicalPage page, JSONObject jo) throws IOException {
        PrintPageSetting printPageSetting = (PrintPageSetting)mapper.readValue(jo.getString("value"), PrintPageSetting.class);
        double[] margins = new double[]{printPageSetting.getMarginTop(), printPageSetting.getMarginBottom(), printPageSetting.getMarginLeft(), printPageSetting.getMarginRight()};
        page.setMargins(margins);
        Paper paper = new Paper();
        paper.setName(printPageSetting.getPaperName());
        paper.setHeight((double)printPageSetting.getPaperHeight());
        paper.setWidth((double)printPageSetting.getPaperWidth());
        paper.setSize(printPageSetting.getPaperSize());
        page.setPaper(paper);
        page.setOrientation(Integer.parseInt(printPageSetting.getDirection()));
    }

    public String doAction(String designerId, String actionName) {
        LOGGER.debug("\u62a5\u8868\u6253\u5370\u8bbe\u8ba1\u670d\u52a1: \u6267\u884c\u64cd\u4f5c, \u8bbe\u8ba1\u5668ID: {}, \u64cd\u4f5c: {}", (Object)designerId, (Object)actionName);
        NedisCache cache = this.getCache(designerId);
        if (null == cache) {
            throw new DesignerNotInitException("Print designer isn't init!");
        }
        PrintDesigner designer = (PrintDesigner)cache.get(designerId, PrintDesigner.class);
        if (null == designer) {
            throw new DesignerNotInitException("Print designer isn't init!");
        }
        IAction action = designer.getAction(actionName);
        if (null == action) {
            return "";
        }
        action.refreshStatus();
        if (action.isEnable()) {
            action.run();
        }
        return "";
    }

    private boolean messageDisabled(String designerId, String body) {
        PrintDesigner designer = this.getPrintDesigner(designerId);
        if (null == designer) {
            return true;
        }
        IGraphicalObject[] selection = designer.getSelection();
        if (null == selection || 0 == selection.length) {
            return false;
        }
        boolean hasDisabled = false;
        for (IGraphicalObject obj : selection) {
            if (!PROPERTIES_DISABLED_VALUE.equals(obj.getProperty(PROPERTIES_DISABLED))) continue;
            hasDisabled = true;
            break;
        }
        if (!hasDisabled) {
            return false;
        }
        if (PROPERTIES_NOT_DISABLED_VALUE_SET.contains("x") && PROPERTIES_NOT_DISABLED_VALUE_SET.contains("y") && PROPERTIES_NOT_DISABLED_VALUE_SET.contains("width") && PROPERTIES_NOT_DISABLED_VALUE_SET.contains("height")) {
            return false;
        }
        JSONArray array = new JSONArray(body);
        if (array.isEmpty()) {
            return false;
        }
        for (int i = 0; i < array.length(); ++i) {
            try {
                List incs = JSONHelper.decodeIncidentData((String)array.getString(i));
                for (Incident inc : incs) {
                    if (!"childResize".equals(inc.data)) continue;
                    return true;
                }
                continue;
            }
            catch (JSONException jSONException) {
                // empty catch block
            }
        }
        return false;
    }

    public String message(String designerId, String body) {
        LOGGER.debug("\u62a5\u8868\u6253\u5370\u8bbe\u8ba1\u670d\u52a1: \u753b\u5e03\u64cd\u4f5c, \u8bbe\u8ba1\u5668ID: {}, \u64cd\u4f5c: {}", (Object)designerId, (Object)body);
        if (this.messageDisabled(designerId, body)) {
            return Collections.emptyList().toString();
        }
        SpringChannel channel = this.getChannel(designerId);
        JSONArray array = new JSONArray();
        try {
            channel.acceptMessage(body);
            channel.fireRequestResponding();
            List invokes = SpringChannel.takeInvokes((String)designerId);
            System.out.println(invokes);
            for (int i = 0; i < invokes.size(); ++i) {
                SpringChannel.Invoke invoke = (SpringChannel.Invoke)invokes.get(i);
                JSONObject object = invoke.toJSON();
                array.put((Object)object);
            }
        }
        catch (Throwable e) {
            LOGGER.error(e.getMessage(), e);
        }
        return array.toString();
    }

    private SpringChannel getChannel(String id) {
        return (SpringChannel)SpringChannel.channels.get(id);
    }

    public PrintDesigner getPrintDesigner(String designerId) {
        NedisCache cache = this.getCache(designerId);
        return (PrintDesigner)cache.get(designerId, PrintDesigner.class);
    }

    @Override
    public String getCurrTemplateDocument(String designerId) {
        NedisCache cache = this.getCache(designerId);
        PrintDesigner printDesigner = (PrintDesigner)cache.get(designerId, PrintDesigner.class);
        if (null == printDesigner) {
            return "";
        }
        ITemplateDocument templateDocument = printDesigner.getContent();
        return SerializeUtil.serialize((ITemplateObject)templateDocument);
    }

    public ITemplateDocument getTemplateDocument(String designerId) {
        NedisCache cache = this.getCache(designerId);
        Cache.ValueWrapper valueWrapper = cache.get(designerId);
        if (null == valueWrapper) {
            return null;
        }
        Object object = valueWrapper.get();
        if (null == object) {
            return null;
        }
        if (object instanceof PrintDesigner) {
            return ((PrintDesigner)object).getContent();
        }
        return null;
    }

    @Override
    public boolean templateIsSave(String designerId) {
        LOGGER.debug("\u62a5\u8868\u6253\u5370\u8bbe\u8ba1\u670d\u52a1: \u5224\u65ad\u662f\u5426\u4fdd\u5b58, \u8bbe\u8ba1\u5668ID: {}", (Object)designerId);
        Assert.notNull((Object)designerId, "'designerId' must not be null.");
        DesignerInfoDTO info = this.getPrintDesignerInfo(designerId);
        if (null == info) {
            return true;
        }
        return info.isChanged(this.getTemplateDocument(designerId));
    }

    private IDrawDocument getIDrawDocument(String designerId) {
        ITemplateDocument templateDocument = this.getTemplateDocument(designerId);
        if (templateDocument == null) {
            throw new PrintDesignerException("\u67e5\u8be2\u4e0d\u5230\u6587\u6863");
        }
        templateDocument = PrintElementUtils.toTemplateDocument((String)PrintElementUtils.toString((ITemplateDocument)templateDocument));
        templateDocument.setNature("REPORT_PRINT_NATURE");
        DnaPrintPaginateInteractor paginateIntractor = new DnaPrintPaginateInteractor();
        IPaginateFactory paginateFactory = GraphicalFactoryManager.getPaginateFactory((String)"REPORT_PRINT_NATURE");
        IPaginateContext paginateContext = paginateFactory.getPaginateContext();
        return DnaPrintProcessUtil.paginate((ITemplateDocument)templateDocument, (IPaginateInteractor)paginateIntractor, (IPaginateContext)paginateContext, (IProcessMonitor)new NullProcessMonitor());
    }

    public String initPrintViewer(String nature, String viewerId) {
        NedisCache cache;
        PrintPreviewer printViewer;
        if (!StringUtils.hasLength(viewerId)) {
            viewerId = UUID.randomUUID().toString();
        }
        if (null == (printViewer = (PrintPreviewer)(cache = this.getCache(viewerId)).get(viewerId, PrintPreviewer.class))) {
            PrintPreviewer viewer = new PrintPreviewer((Object)viewerId, "REPORT_PRINT_NATURE");
            cache.put(viewerId, (Object)viewer);
        }
        LOGGER.debug("\u62a5\u8868\u6253\u5370\u8bbe\u8ba1\u670d\u52a1: \u521d\u59cb\u5316\u6253\u5370\u9884\u89c8\u5668, \u9884\u89c8\u5668ID: {}", (Object)viewerId);
        return viewerId;
    }

    public String loadViewDocument(String id, String content) {
        String designerId = this.getDesignerId(id);
        IDrawDocument iDrawDocument = this.getIDrawDocument(designerId);
        NedisCache cache = this.getCache(id);
        PrintPreviewer printPreviewer = (PrintPreviewer)cache.get(id, PrintPreviewer.class);
        if (null == printPreviewer) {
            printPreviewer = new PrintPreviewer((Object)id, iDrawDocument.getNature());
            cache.put(id, (Object)printPreviewer);
        }
        printPreviewer.setContent(iDrawDocument);
        printPreviewer.getViewer().setEditable(false);
        LOGGER.debug("\u62a5\u8868\u6253\u5370\u8bbe\u8ba1\u670d\u52a1: \u52a0\u8f7d\u6253\u5370\u9884\u89c8\u6a21\u677f, \u9884\u89c8\u5668ID: {}, \u6a21\u677f\u4fe1\u606f: {}", (Object)id, (Object)content);
        return id;
    }

    public void destoryPrintViewer(String viewerId) {
        NedisCache cache = this.getCache(viewerId);
        cache.evict(viewerId);
        LOGGER.debug("\u62a5\u8868\u6253\u5370\u8bbe\u8ba1\u670d\u52a1: \u6ce8\u9500\u6253\u5370\u9884\u89c8\u5668, \u9884\u89c8\u5668ID: {}", (Object)viewerId);
    }

    public String getAllProtertyByViewer(String viewerId, String modelId) {
        NedisCache cache = this.getCache(viewerId);
        if (null == cache) {
            throw new ViewerNotInitException("Print designer isn't init!");
        }
        ITemplateObject element = (ITemplateObject)cache.get(modelId, ITemplateObject.class);
        if (null == element) {
            return null;
        }
        if (element instanceof ITemplatePage) {
            return "";
        }
        AbstractGraphicalObject graphicalObject = (AbstractGraphicalObject)element;
        JSONObject jo = graphicalObject.serialize();
        return jo.toString();
    }

    public String getProtertyByViewer(String viewerId, String modelId, String propertyName) {
        NedisCache cache = this.getCache(viewerId);
        if (null == cache) {
            throw new ViewerNotLoadException("Print designer isn't load!");
        }
        ITemplateObject element = (ITemplateObject)cache.get(modelId, ITemplateObject.class);
        if (null == element) {
            throw new ModelNotFoundException(viewerId + " ");
        }
        if (element instanceof ITemplatePage) {
            return "";
        }
        AbstractGraphicalObject graphicalObject = (AbstractGraphicalObject)element;
        JSONObject result = graphicalObject.serialize();
        if (result.has(propertyName)) {
            try {
                return result.getString(propertyName);
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }
        throw new PropertyNotFoundException(propertyName + " not found in model  :" + modelId);
    }

    public String updateProtertyByViewer(String viewerId, String modelId, String content) {
        NedisCache cache = this.getCache(viewerId);
        try {
            JSONArray arrays = new JSONArray(content);
            if (null == cache) {
                throw new ViewerNotInitException("Print designer isn't init!");
            }
            PrintViewer viewer = (PrintViewer)cache.get(viewerId, PrintViewer.class);
            ITemplateElement element = (ITemplateElement)cache.get(modelId, ITemplateElement.class);
            if (null == element) {
                throw new ModelNotFoundException(viewerId + " ");
            }
            int length = arrays.length();
            for (int i = 0; i < length; ++i) {
                JSONObject jo = arrays.getJSONObject(i);
                Object oldValue = null;
                if (jo.has("oldValue")) {
                    oldValue = jo.get("oldValue");
                }
                ModifyPropertyCommand command = new ModifyPropertyCommand((Object)element, jo.getString("propertyName"), oldValue, (Object)jo.getString("value"));
                viewer.getCommandStack().execute((ICommand)command);
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getProtertiesByViewer(String content) {
        String channelId = UUID.randomUUID().toString();
        return channelId;
    }

    public String updateProtertiesByViewer(String content) {
        String channelId = UUID.randomUUID().toString();
        return channelId;
    }

    private String getDesignerId(String viewerId) {
        return viewerId.substring("viewer".length());
    }

    public String viewerMessage(String viewerId, String body) {
        SpringChannel channel = this.getChannel(viewerId);
        JSONArray array = new JSONArray();
        try {
            channel.acceptMessage(body);
            channel.fireRequestResponding();
            List invokes = SpringChannel.takeInvokes((String)viewerId);
            for (int i = 0; i < invokes.size(); ++i) {
                SpringChannel.Invoke invoke = (SpringChannel.Invoke)invokes.get(i);
                JSONObject object = invoke.toJSON();
                array.put((Object)object);
            }
        }
        catch (Throwable e) {
            LOGGER.error(e.getMessage(), e);
        }
        return array.toString();
    }

    public String doActionByViewer(String viewerId, String actionName) {
        NedisCache cache = this.getCache(viewerId);
        if (null == cache) {
            throw new ViewerNotInitException("Print designer isn't init!");
        }
        PrintPreviewer viewer = (PrintPreviewer)cache.get(viewerId, PrintPreviewer.class);
        return "";
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public String getDefaultPrinter(String viewerId) {
        NedisCache cache = this.getCache(viewerId);
        if (null == cache) {
            throw new ViewerNotInitException("Print designer isn't init!");
        }
        PrintPreviewer viewer = (PrintPreviewer)cache.get(viewerId, PrintPreviewer.class);
        viewer.getPrinterDevice().getDefaultPrinter(null);
        try {
            AsyncResult result = AsyncWorkContainnerUtil.getAsyncResult((String)viewerId, (long)5000L);
            String string = result.getResult();
            return string;
        }
        catch (InterruptedException e) {
            LOGGER.error(e.getMessage(), e);
            Thread.currentThread().interrupt();
        }
        return "";
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public String getAvailablePrinterNames(String viewerId) {
        NedisCache cache = this.getCache(viewerId);
        if (null == cache) {
            throw new ViewerNotInitException("Print designer isn't init!");
        }
        PrintPreviewer viewer = (PrintPreviewer)cache.get(viewerId, PrintPreviewer.class);
        viewer.getPrinterDevice().getAvailablePrinterNames(null);
        try {
            AsyncResult result = AsyncWorkContainnerUtil.getAsyncResult((String)viewerId, (long)5000L);
            String string = result.getResult();
            return string;
        }
        catch (InterruptedException e) {
            LOGGER.error(e.getMessage(), e);
            Thread.currentThread().interrupt();
        }
        return "";
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public String getPrinterInfo(String viewerId) {
        NedisCache cache = this.getCache(viewerId);
        if (null == cache) {
            throw new ViewerNotInitException("Print designer isn't init!");
        }
        PrintPreviewer viewer = (PrintPreviewer)cache.get(viewerId, PrintPreviewer.class);
        viewer.getPrinterDevice().getPrinterInfo();
        try {
            AsyncResult result = AsyncWorkContainnerUtil.getAsyncResult((String)viewerId, (long)5000L);
            String string = result.getResult();
            return string;
        }
        catch (InterruptedException e) {
            LOGGER.error(e.getMessage(), e);
            Thread.currentThread().interrupt();
        }
        return "";
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public String showPrinterPreference(String viewerId) {
        NedisCache cache = this.getCache(viewerId);
        if (null == cache) {
            throw new ViewerNotInitException("Print designer isn't init!");
        }
        PrintPreviewer viewer = (PrintPreviewer)cache.get(viewerId, PrintPreviewer.class);
        try {
            Thread.sleep(2000L);
            AsyncResult result = AsyncWorkContainnerUtil.getAsyncResult((String)viewerId, (long)5000L);
            String string = result.getResult();
            return string;
        }
        catch (InterruptedException e) {
            LOGGER.error(e.getMessage(), e);
            Thread.currentThread().interrupt();
        }
        return "";
    }

    public String printAsyncWork(String printerID, String workName) {
        if (!AsyncWorkContainnerUtil.isRegisted((String)printerID)) {
            return new JSONObject().toString();
        }
        AsyncWork asyncWork = AsyncWorkContainnerUtil.getAsyncWork((String)printerID);
        if (null == asyncWork) {
            return new JSONObject().toString();
        }
        return asyncWork.toString();
    }

    public String printAsyncWorkResult(String printerID, String workName, String result) {
        if (!AsyncWorkContainnerUtil.isRegisted((String)printerID)) {
            return new JSONObject().toString();
        }
        AsyncResult asyncResult = new AsyncResult(workName, result);
        AsyncWorkContainnerUtil.addAsyncResult((String)printerID, (AsyncResult)asyncResult);
        return new JSONObject().toString();
    }

    public String getImageUrl(String designerId, String resourceId) {
        ITemplateDocument doc = this.getTemplateDocument(designerId);
        return ResourceUtils.getImageUrl((ITemplateDocument)doc, (String)resourceId);
    }

    @Override
    public void upload(String designerId, String elementId, String fileName, byte[] fileData) {
        ITemplateDocument doc = this.getTemplateDocument(designerId);
        try {
            ResourceUtils.updateImage((ITemplateDocument)doc, (String)elementId, (String)fileName, (byte[])fileData);
        }
        catch (Exception e) {
            LOGGER.debug("\u62a5\u8868\u6253\u5370\u8bbe\u8ba1\u670d\u52a1: \u66f4\u65b0\u56fe\u7247\u51fa\u9519, \u8bbe\u8ba1\u5668ID: {}, \u63a7\u4ef6ID: {}", designerId, elementId, e);
        }
        LOGGER.debug("\u62a5\u8868\u6253\u5370\u8bbe\u8ba1\u670d\u52a1: \u66f4\u65b0\u56fe\u7247, \u8bbe\u8ba1\u5668ID: {}, \u63a7\u4ef6ID: {}", (Object)designerId, (Object)elementId);
    }

    @Override
    public String getCurrPrintSchemeKey(String designerId) {
        DesignerInfoDTO info = this.getPrintDesignerInfo(designerId);
        return info.getPrintSchemeId();
    }

    @Override
    public String getCurrFormKey(String designerId) {
        DesignerInfoDTO info = this.getPrintDesignerInfo(designerId);
        return info.getFormId();
    }

    public void updateTemplate(String designerId, ITemplateDocument targetTemplate) {
        this.updateTemplate(designerId, targetTemplate, true);
    }

    private void updateTemplate(String designerId, ITemplateDocument targetTemplate, boolean updateGrid) {
        PrintDesigner printDesigner = this.getPrintDesigner(designerId);
        if (null == printDesigner) {
            return;
        }
        ITemplateDocument originTemplate = printDesigner.getContent();
        if (!updateGrid) {
            this.replaceForm(originTemplate, targetTemplate);
        }
        this.updateTemplate(designerId, printDesigner, targetTemplate);
    }

    @Override
    public void updateTemplate(String designerId, String templateStr, boolean updateGrid) {
        ITemplateDocument targetTemplate = (ITemplateDocument)SerializeUtil.deserialize((String)templateStr, (ITemplateObjectFactory)PrintElementUtils.FACTORY);
        this.updateTemplate(designerId, targetTemplate, updateGrid);
        LOGGER.debug("\u62a5\u8868\u6253\u5370\u8bbe\u8ba1\u670d\u52a1: \u66f4\u65b0\u6253\u5370\u6bcd\u7248, \u8bbe\u8ba1\u5668ID: {}, \u662f\u5426\u66f4\u65b0\u8868\u6837: {}", (Object)designerId, (Object)updateGrid);
    }

    private void replaceForm(ITemplateDocument originTemplate, ITemplateDocument targetTemplate) {
        PageTemplateObject originPage = (PageTemplateObject)originTemplate.getPage(0);
        ReportTemplateObject originReportTemplateObject = PrintElementUtils.getReportTemplate((PageTemplateObject)originPage);
        PageTemplateObject targetPage = (PageTemplateObject)targetTemplate.getPage(0);
        ReportTemplateObject targetReportTemplateObject = PrintElementUtils.getReportTemplate((PageTemplateObject)targetPage);
        if (targetReportTemplateObject != null) {
            targetPage.remove((ITemplateElement)targetReportTemplateObject);
            if (originReportTemplateObject != null) {
                GridData gridData = originReportTemplateObject.getGridData();
                targetReportTemplateObject.setGridData(gridData);
                targetPage.add((ITemplateElement)targetReportTemplateObject);
            }
        }
    }

    @Override
    public List<ReportLabelDTO> updateReportLabel(String designerId, ReportLabelDTO oldLabel, ReportLabelDTO newLabel) {
        PrintDesigner printDesigner = this.getPrintDesigner(designerId);
        if (null == printDesigner) {
            return Collections.emptyList();
        }
        this.updateReportLabel(printDesigner, oldLabel, newLabel);
        ITemplateElement[] templateElements = printDesigner.getContent().getPage(0).getTemplateElements();
        return IPrintDesignExtendService.getLabels(templateElements);
    }

    private void updateReportLabel(PrintDesigner designer, ReportLabelDTO oldLabel, ReportLabelDTO newLabel) {
        if (oldLabel != null && newLabel != null) {
            ITemplateElement[] elements;
            ITemplateElement element = null;
            ITemplatePage page = designer.getContent().getPage(0);
            for (ITemplateElement e : elements = page.getTemplateElements()) {
                if (!e.getID().equals(oldLabel.getId())) continue;
                element = e;
                break;
            }
            if (null == element) {
                return;
            }
            com.jiuqi.print.designer.command.ModifyPropertyCommand command = new com.jiuqi.print.designer.command.ModifyPropertyCommand((Object)element, "location", (Object)oldLabel.getLocation(), (Object)newLabel.getLocation());
            designer.getViewer().getCommandStack().execute((ICommand)command);
        }
    }

    @Override
    public String getTableGrid(String designerId, String elementId) {
        PrintDesigner printDesigner = this.getPrintDesigner(designerId);
        if (null == printDesigner) {
            return null;
        }
        ITemplateElement<?> iTemplateElement = IPrintDesignExtendService.getITemplateElement(printDesigner.getContent(), elementId);
        if (iTemplateElement instanceof TableTemplateObject) {
            TableTemplateObject tableTemplate = (TableTemplateObject)iTemplateElement;
            CellBook cellBook = this.gridData2Cell(tableTemplate.getGridData(), elementId);
            ObjectMapper objectMapper = new ObjectMapper();
            SimpleModule module = new SimpleModule();
            module.addSerializer(CellBook.class, (JsonSerializer)new CellBookSerialize());
            objectMapper.registerModule((Module)module);
            try {
                return objectMapper.writeValueAsString((Object)cellBook);
            }
            catch (JsonProcessingException e) {
                LOGGER.error("\u8868\u6837\u5e8f\u5217\u5316\u5f02\u5e38", e);
            }
        }
        return null;
    }

    private CellBook gridData2Cell(GridData gridData, String elementId) {
        Grid2Data grid2Data = PrintUtil.gridDataToGrid2Data((GridData)gridData, null);
        this.initGrid(grid2Data);
        CellBook cellBook = CellBookInit.init();
        CellBookGrid2dataConverter.grid2DataToCellBook((Grid2Data)grid2Data, (CellBook)cellBook, (String)elementId);
        return cellBook;
    }

    private void initGrid(Grid2Data gridData) {
        for (int c = 0; c < gridData.getColumnCount(); ++c) {
            for (int r = 0; r < gridData.getRowCount(); ++r) {
                GridCellData gridCellData = gridData.getGridCellData(c, r);
                gridCellData.setEditable(false);
            }
        }
        if (!gridData.isRowHidden(0) || !gridData.isColumnHidden(0)) {
            gridData.setRowHidden(0, true);
            gridData.setColumnHidden(0, true);
        }
    }

    @Override
    public void updateTableGrid(String designerId, String elementId, byte[] grid) {
        PrintDesigner printDesigner = this.getPrintDesigner(designerId);
        if (null == printDesigner) {
            return;
        }
        ITemplateElement<?> iTemplateElement = IPrintDesignExtendService.getITemplateElement(printDesigner.getContent(), elementId);
        if (iTemplateElement instanceof ReportTemplateObject) {
            ReportTemplateObject tableTemplate = (ReportTemplateObject)iTemplateElement;
            tableTemplate.setGridData(GridData.bytesToGrid((byte[])grid));
            DesignerInfoDTO info = this.getPrintDesignerInfo(designerId);
            info.setCustomGrid(true);
            info.setCustomGuidDate(new Date());
            this.updatePrintDesignerInfo(designerId, info);
        } else if (iTemplateElement instanceof TableTemplateObject) {
            TableTemplateObject tableTemplate = (TableTemplateObject)iTemplateElement;
            tableTemplate.setGridData(GridData.bytesToGrid((byte[])grid));
        }
        LOGGER.debug("\u62a5\u8868\u6253\u5370\u8bbe\u8ba1\u670d\u52a1: \u66f4\u65b0\u8868\u6837, \u8bbe\u8ba1\u5668ID: {}", (Object)designerId);
    }

    @Override
    public Map<String, Object> getAttribute(String designerId) {
        HashMap<String, Object> paperInfoMap = new HashMap<String, Object>();
        if (StringUtils.hasText(designerId)) {
            DesignerInfoDTO info;
            ITemplateDocument template = this.getTemplateDocument(designerId);
            if (null == template) {
                return paperInfoMap;
            }
            PageTemplateObject iTemplatePage = (PageTemplateObject)template.getPages()[0];
            paperInfoMap.put("labels", IPrintDesignExtendService.getLabels(iTemplatePage.getTemplateElements()));
            paperInfoMap.put("paper", iTemplatePage.getPaper());
            paperInfoMap.put("orientation", iTemplatePage.getOrientation());
            paperInfoMap.put("margins", iTemplatePage.getMargins());
            WatermarkConfig watermarkConfig = WatermarkTemplateObject.getWatermarkConfig((PageTemplateObject)iTemplatePage);
            if (null != watermarkConfig) {
                HashMap<String, String> config = new HashMap<String, String>();
                config.put("content", watermarkConfig.getContent());
                config.put("fontname", watermarkConfig.getFontname());
                config.put("fontsizeStr", watermarkConfig.getFontsizeStr());
                config.put("fontcolorStr", watermarkConfig.getFontcolorStr());
                paperInfoMap.put("markConfig", config);
            }
            paperInfoMap.put(PROPERTIES_DISABLED, StringUtils.hasText((info = this.getPrintDesignerInfo(designerId)).getLinkedCommonCode()) ? "true" : "false");
        }
        return paperInfoMap;
    }
}

