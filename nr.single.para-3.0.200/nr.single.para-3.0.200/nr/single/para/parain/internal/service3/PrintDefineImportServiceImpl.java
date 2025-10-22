/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.grid.GridData
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.np.grid.Font
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.controller.IPrintDesignTimeController
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignPrintTemplateDefine
 *  com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine
 *  com.jiuqi.nr.definition.facade.print.PrintPaperDefine
 *  com.jiuqi.nr.definition.facade.print.PrintSchemeAttributeDefine
 *  com.jiuqi.nr.definition.facade.print.PrintTemplateAttributeDefine
 *  com.jiuqi.nr.definition.facade.print.WordLabelDefine
 *  com.jiuqi.nr.definition.facade.print.common.other.PrintUtil
 *  com.jiuqi.nr.definition.facade.print.core.DocumentBuildUtil
 *  com.jiuqi.nr.definition.facade.print.core.ReportLabelTemplateObject
 *  com.jiuqi.nr.definition.facade.print.core.ReportTemplateObject
 *  com.jiuqi.nr.definition.facade.print.core.WordLabelTemplateObject
 *  com.jiuqi.nr.single.core.grid.SinglePrintUtil
 *  com.jiuqi.nr.single.core.para.ParaInfo
 *  com.jiuqi.nr.single.core.para.parser.print.FontDataClass
 *  com.jiuqi.nr.single.core.para.parser.print.GLineItem
 *  com.jiuqi.nr.single.core.para.parser.print.GPoints
 *  com.jiuqi.nr.single.core.para.parser.print.GRectangleItem
 *  com.jiuqi.nr.single.core.para.parser.print.GText
 *  com.jiuqi.nr.single.core.para.parser.print.GraphGroup
 *  com.jiuqi.nr.single.core.para.parser.print.GraphItem
 *  com.jiuqi.nr.single.core.para.parser.print.GridPrintMan
 *  com.jiuqi.nr.single.core.para.parser.print.GridPrintPage
 *  com.jiuqi.nr.single.core.para.parser.print.GridPrintTextData
 *  com.jiuqi.nr.single.core.para.parser.print.PointRec
 *  com.jiuqi.nr.single.core.para.parser.print.PrintSchemeConsts$PaperMapping
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.xg.draw2d.Font
 *  com.jiuqi.xg.draw2d.FontMetrics
 *  com.jiuqi.xg.process.GraphicalFactoryManager
 *  com.jiuqi.xg.process.ITemplateDocument
 *  com.jiuqi.xg.process.ITemplateElement
 *  com.jiuqi.xg.process.ITemplateObject
 *  com.jiuqi.xg.process.ITemplateObjectFactory
 *  com.jiuqi.xg.process.ITemplatePage
 *  com.jiuqi.xg.process.Paper
 *  com.jiuqi.xg.process.obj.DocumentTemplateObject
 *  com.jiuqi.xg.process.obj.LineTemplateObject
 *  com.jiuqi.xg.process.obj.PageTemplateObject
 *  com.jiuqi.xg.process.obj.TextTemplateObject
 *  com.jiuqi.xg.process.table.ITablePaginateLocationProvider
 *  com.jiuqi.xg.process.table.TableLineConfig
 *  com.jiuqi.xg.process.table.TablePaginateConfig
 *  com.jiuqi.xg.process.table.TableResizeConfig
 *  com.jiuqi.xg.process.table.impl.BasicTablePaginateLocationProvider
 *  com.jiuqi.xg.process.table.obj.TableTemplateObject
 *  com.jiuqi.xg.process.util.SerializeUtil
 *  com.jiuqi.xlib.measure.ILengthUnit
 *  com.jiuqi.xlib.measure.LengthUnits
 */
package nr.single.para.parain.internal.service3;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.grid.GridData;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.controller.IPrintDesignTimeController;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateDefine;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine;
import com.jiuqi.nr.definition.facade.print.PrintPaperDefine;
import com.jiuqi.nr.definition.facade.print.PrintSchemeAttributeDefine;
import com.jiuqi.nr.definition.facade.print.PrintTemplateAttributeDefine;
import com.jiuqi.nr.definition.facade.print.WordLabelDefine;
import com.jiuqi.nr.definition.facade.print.common.other.PrintUtil;
import com.jiuqi.nr.definition.facade.print.core.DocumentBuildUtil;
import com.jiuqi.nr.definition.facade.print.core.ReportLabelTemplateObject;
import com.jiuqi.nr.definition.facade.print.core.ReportTemplateObject;
import com.jiuqi.nr.definition.facade.print.core.WordLabelTemplateObject;
import com.jiuqi.nr.single.core.grid.SinglePrintUtil;
import com.jiuqi.nr.single.core.para.ParaInfo;
import com.jiuqi.nr.single.core.para.parser.print.FontDataClass;
import com.jiuqi.nr.single.core.para.parser.print.GLineItem;
import com.jiuqi.nr.single.core.para.parser.print.GPoints;
import com.jiuqi.nr.single.core.para.parser.print.GRectangleItem;
import com.jiuqi.nr.single.core.para.parser.print.GText;
import com.jiuqi.nr.single.core.para.parser.print.GraphGroup;
import com.jiuqi.nr.single.core.para.parser.print.GraphItem;
import com.jiuqi.nr.single.core.para.parser.print.GridPrintMan;
import com.jiuqi.nr.single.core.para.parser.print.GridPrintPage;
import com.jiuqi.nr.single.core.para.parser.print.GridPrintTextData;
import com.jiuqi.nr.single.core.para.parser.print.PointRec;
import com.jiuqi.nr.single.core.para.parser.print.PrintSchemeConsts;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.xg.draw2d.Font;
import com.jiuqi.xg.draw2d.FontMetrics;
import com.jiuqi.xg.process.GraphicalFactoryManager;
import com.jiuqi.xg.process.ITemplateDocument;
import com.jiuqi.xg.process.ITemplateElement;
import com.jiuqi.xg.process.ITemplateObject;
import com.jiuqi.xg.process.ITemplateObjectFactory;
import com.jiuqi.xg.process.ITemplatePage;
import com.jiuqi.xg.process.Paper;
import com.jiuqi.xg.process.obj.DocumentTemplateObject;
import com.jiuqi.xg.process.obj.LineTemplateObject;
import com.jiuqi.xg.process.obj.PageTemplateObject;
import com.jiuqi.xg.process.obj.TextTemplateObject;
import com.jiuqi.xg.process.table.ITablePaginateLocationProvider;
import com.jiuqi.xg.process.table.TableLineConfig;
import com.jiuqi.xg.process.table.TablePaginateConfig;
import com.jiuqi.xg.process.table.TableResizeConfig;
import com.jiuqi.xg.process.table.impl.BasicTablePaginateLocationProvider;
import com.jiuqi.xg.process.table.obj.TableTemplateObject;
import com.jiuqi.xg.process.util.SerializeUtil;
import com.jiuqi.xlib.measure.ILengthUnit;
import com.jiuqi.xlib.measure.LengthUnits;
import java.awt.Color;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import nr.single.para.compare.bean.ParaImportInfoResult;
import nr.single.para.compare.definition.CompareDataPrintItemDTO;
import nr.single.para.compare.definition.CompareDataPrintSchemeDTO;
import nr.single.para.compare.definition.ISingleCompareDataPrintItemService;
import nr.single.para.compare.definition.ISingleCompareDataPrintScemeService;
import nr.single.para.compare.definition.common.CompareDataType;
import nr.single.para.compare.definition.common.CompareUpdateType;
import nr.single.para.compare.definition.exception.SingleParamImportException;
import nr.single.para.parain.internal.cache.TaskImportContext;
import nr.single.para.parain.service.IPrintDefineImportService;
import nr.single.para.print.ElementsDrawHandlerManager;
import nr.single.para.print.ElementsDrawParam;
import nr.single.para.print.PrintTemplateDrawParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrintDefineImportServiceImpl
implements IPrintDefineImportService {
    private static final Logger log = LoggerFactory.getLogger(PrintDefineImportServiceImpl.class);
    @Autowired
    private IDesignTimeViewController viewController;
    @Autowired
    private IPrintDesignTimeController printController;
    @Autowired
    private ISingleCompareDataPrintItemService printCompareService;
    @Autowired
    private ISingleCompareDataPrintScemeService printSchemeService;
    private static final ILengthUnit unit = LengthUnits.getDefault().getUnit(4);

    public static void draw(ITemplateDocument doc, DesignFormDefine report, List<WordLabelDefine> wordLabels) {
        PageTemplateObject page = DocumentBuildUtil.buildTemplatePage((String)report.getTitle());
        doc.add((ITemplatePage)page);
        WordLabelDefine[] labels = wordLabels.toArray(new WordLabelDefine[wordLabels.size()]);
        PrintDefineImportServiceImpl.drawElements(page, report, labels, 0);
    }

    private static void drawElements(PageTemplateObject page, DesignFormDefine currReport, WordLabelDefine[] labels, int printSolutionType) {
        ElementsDrawHandlerManager.getHandler().handle(new PrintTemplateDrawParam(page, currReport, labels, printSolutionType));
    }

    @Override
    public void importPrintDefines(TaskImportContext importContext, String formSchemeKey) throws Exception {
        ParaInfo para = importContext.getParaInfo();
        List printSchemes = this.printController.getAllPrintSchemeByFormScheme(formSchemeKey);
        HashMap<String, DesignPrintTemplateSchemeDefine> printSchemeDic = new HashMap<String, DesignPrintTemplateSchemeDefine>();
        for (DesignPrintTemplateSchemeDefine scheme : printSchemes) {
            printSchemeDic.put(scheme.getTitle(), scheme);
        }
        HashMap<String, DesignFormDefine> formCache = new HashMap<String, DesignFormDefine>();
        List oldFormList = this.viewController.queryAllFormDefinesByFormScheme(formSchemeKey);
        for (DesignFormDefine form : oldFormList) {
            formCache.put(form.getFormCode(), form);
        }
        ParaImportInfoResult printsLog = null;
        Map<Object, Object> oldPrintItemDic = new HashMap();
        Map<Object, Object> oldPrintSchemeDtoDic = new HashMap();
        if (importContext.getCompareInfo() != null) {
            CompareDataPrintSchemeDTO schemeQueryParam = new CompareDataPrintSchemeDTO();
            schemeQueryParam.setInfoKey(importContext.getCompareInfo().getKey());
            schemeQueryParam.setDataType(CompareDataType.DATA_PRINTTSCHEME);
            List<CompareDataPrintSchemeDTO> oldPrintSchemeDtos = this.printSchemeService.list(schemeQueryParam);
            oldPrintSchemeDtoDic = oldPrintSchemeDtos.stream().collect(Collectors.toMap(e -> e.getSingleTitle(), e -> e));
            CompareDataPrintItemDTO printQueryParam = new CompareDataPrintItemDTO();
            printQueryParam.setInfoKey(importContext.getCompareInfo().getKey());
            printQueryParam.setDataType(CompareDataType.DATA_PRINTTITEM);
            List<CompareDataPrintItemDTO> oldPrintItemList = this.printCompareService.list(printQueryParam);
            oldPrintItemDic = oldPrintItemList.stream().collect(Collectors.toMap(e -> e.getSingleCode(), e -> e));
            if (importContext.getImportResult() != null) {
                printsLog = importContext.getImportResult().getLogInfo(CompareDataType.DATA_PRINTTITEM, "prints", "\u6253\u5370\u6a21\u677f");
            }
        }
        double startPos = importContext.getCurProgress();
        Map printMgr = para.getPrtMgr();
        for (Map.Entry entry : printMgr.entrySet()) {
            String printSchemeName = (String)entry.getKey();
            Map printFormMap = (Map)entry.getValue();
            boolean isNeedUpdateScheme = true;
            CompareDataPrintSchemeDTO oldSchemeCompare = null;
            if (oldPrintSchemeDtoDic.containsKey(printSchemeName) && (oldSchemeCompare = (CompareDataPrintSchemeDTO)oldPrintSchemeDtoDic.get(printSchemeName)) != null && (oldSchemeCompare.getUpdateType() == CompareUpdateType.UPDATE_IGNORE || oldSchemeCompare.getUpdateType() == CompareUpdateType.UPDATE_UNOVER)) {
                isNeedUpdateScheme = false;
            }
            DesignPrintTemplateSchemeDefine scheme = null;
            boolean isNewScheme = true;
            if (printSchemeDic.containsKey(printSchemeName)) {
                scheme = (DesignPrintTemplateSchemeDefine)printSchemeDic.get(printSchemeName);
                scheme.setOrder(OrderGenerator.newOrder());
                scheme.setUpdateTime(new Date());
                scheme.setGatherCoverData(null);
                scheme.setCommonAttribute(null);
                if (isNeedUpdateScheme) {
                    this.printController.updatePrintTemplateSchemeDefine(scheme);
                }
                isNewScheme = false;
            } else {
                if (!isNeedUpdateScheme) continue;
                scheme = this.printController.createPrintTemplateSchemeDefine();
                scheme.setTitle(printSchemeName);
                scheme.setFormSchemeKey(formSchemeKey);
                scheme.setTaskKey(importContext.getTaskKey());
                scheme.setUpdateTime(new Date());
                scheme.setOrder(OrderGenerator.newOrder());
                scheme.setGatherCoverData(null);
                scheme.setCommonAttribute(null);
                scheme.setOwnerLevelAndId(importContext.getCurServerCode());
                this.printController.insertPrintTemplateSchemeDefine(scheme);
            }
            this.updateScheme(scheme);
            for (Map.Entry entry2 : printFormMap.entrySet()) {
                boolean isPrintNew;
                String formCode = (String)entry2.getKey();
                importContext.onProgress(startPos += 0.1 / (double)(printMgr.size() * printFormMap.size()), "\u5bfc\u5165\u8868\u5355" + formCode + "\u7684\u6253\u5370\u6a21\u677f");
                GridPrintMan singlePrintMan = (GridPrintMan)entry2.getValue();
                if (!formCache.containsKey(formCode)) continue;
                if (oldPrintItemDic.containsKey(printSchemeName + "_" + formCode)) {
                    CompareDataPrintItemDTO printCompareItem = null;
                    printCompareItem = (CompareDataPrintItemDTO)oldPrintItemDic.get(printSchemeName + "_" + formCode);
                    if (printCompareItem != null) {
                        if (printsLog != null) {
                            ParaImportInfoResult printLog = new ParaImportInfoResult();
                            printLog.copyForm(printCompareItem);
                            printLog.setSuccess(true);
                            printsLog.addItem(printLog);
                        }
                        if (printCompareItem.getUpdateType() == CompareUpdateType.UPDATE_IGNORE || printCompareItem.getUpdateType() == CompareUpdateType.UPDATE_UNOVER) continue;
                    }
                }
                DesignFormDefine form = (DesignFormDefine)formCache.get(formCode);
                DesignPrintTemplateDefine printTemplate = this.printController.queryPrintTemplateDefineBySchemeAndForm(scheme.getKey(), form.getKey());
                boolean bl = isPrintNew = null == printTemplate;
                if (isPrintNew) {
                    printTemplate = this.printController.createPrintTemplateDefine();
                    printTemplate.setTitle(form.getTitle() + "\u6253\u5370\u6a21\u677f");
                    printTemplate.setFormKey(form.getKey());
                    printTemplate.setPrintSchemeKey(scheme.getKey());
                    printTemplate.setOrder(OrderGenerator.newOrder());
                    printTemplate.setUpdateTime(new Date());
                    printTemplate.setOwnerLevelAndId(importContext.getCurServerCode());
                }
                boolean havePrint = false;
                havePrint = formCode.equals("FMDM") ? this.updatePrintTemplateFMDM(printTemplate, singlePrintMan, form, scheme) : this.updatePrintTemplate(printTemplate, singlePrintMan, form, scheme);
                if (isPrintNew) {
                    if (havePrint) {
                        printTemplate.setFormUpdateTime(new Date());
                        this.printController.insertPrintTemplateDefine(printTemplate);
                    }
                } else if (havePrint) {
                    printTemplate.setFormUpdateTime(new Date());
                    this.printController.updatePrintTemplateDefine(printTemplate);
                } else {
                    this.printController.deletePrintTemplateDefine(printTemplate.getKey());
                }
                log.info("\u5bfc\u5165\u6253\u5370\u65b9\u6848:{}", (Object)(scheme.getTitle() + "," + scheme.getKey().toString() + ",\u8868\u5355\uff1a" + formCode + "," + printTemplate.getKey().toString() + ",\u65f6\u95f4:" + new Date().toString()));
            }
            if (!printFormMap.containsKey("SYS_HZFMDY") || !isNeedUpdateScheme) continue;
            GridPrintMan singlePrintMan = (GridPrintMan)printFormMap.get("SYS_HZFMDY");
            DesignPrintTemplateDefine printTemplate = this.printController.createPrintTemplateDefine();
            this.updatePrintTemplateFMDM(printTemplate, singlePrintMan, null, scheme);
            scheme.setGatherCoverData(printTemplate.getTemplateData());
            this.printController.updatePrintTemplateSchemeDefine(scheme);
        }
        if (importContext.getImportOption().isOverWriteAll()) {
            for (DesignPrintTemplateSchemeDefine scheme : printSchemes) {
                if (printMgr.containsKey(scheme.getTitle())) continue;
                this.printController.deletePrintTemplateDefineByScheme(scheme.getKey());
                this.printController.deletePrintTemplateSchemeDefine(scheme.getKey());
            }
        }
    }

    private boolean updatePrintTemplate(DesignPrintTemplateDefine printTemplate, GridPrintMan singlePrintMan, DesignFormDefine form, DesignPrintTemplateSchemeDefine scheme) throws SingleParamImportException {
        try {
            TableTemplateObject reportElement;
            GridData newGridData;
            SecureRandom secureRandom = new SecureRandom();
            ITemplateObjectFactory templateObjectFactory = GraphicalFactoryManager.getTemplateObjectFactory((String)"REPORT_PRINT_NATURE");
            if (null == templateObjectFactory) {
                return false;
            }
            DocumentTemplateObject documentObject = (DocumentTemplateObject)templateObjectFactory.create("document");
            documentObject.setNature("REPORT_PRINT_NATURE");
            PrintTemplateAttributeDefine attribute = this.printController.getPrintTemplateAttribute(printTemplate);
            HashMap<String, String> textTrans = new HashMap<String, String>();
            this.initLabels(singlePrintMan, attribute.getWordLabels(), scheme, textTrans);
            PageTemplateObject pageLi = this.initPage(singlePrintMan);
            pageLi.setID(printTemplate.getTitle() + secureRandom.nextDouble());
            TablePaginateConfig paginateConfig = new TablePaginateConfig();
            TableResizeConfig resizeConfig = new TableResizeConfig();
            int horzPages = singlePrintMan.getHorzPages();
            int vertPages = singlePrintMan.getVertPages();
            int[] rowPaginateLocations = null;
            if (vertPages > 1) {
                rowPaginateLocations = new int[vertPages - 1];
            }
            int[] colPaginateLocations = null;
            if (horzPages > 1) {
                colPaginateLocations = new int[horzPages - 1];
            }
            if (horzPages > 0 && vertPages > 0) {
                int rows;
                int cols;
                ArrayList printHeadRows = new ArrayList();
                ArrayList printHeadCols = new ArrayList();
                GridPrintPage gridItemZero = singlePrintMan.getPages()[0][0];
                List prtColsZero = gridItemZero.getPrtCols();
                List prtRowsZero = gridItemZero.getPrtRows();
                printHeadRows.addAll(prtRowsZero);
                printHeadCols.addAll(prtColsZero);
                if (horzPages != 1 || vertPages != 1) {
                    for (int i = 0; i < singlePrintMan.getVertPages(); ++i) {
                        for (int j = 0; j < singlePrintMan.getHorzPages(); ++j) {
                            int printRows;
                            int k;
                            if (singlePrintMan.getPages()[j][i] == null || j == 0 && i == 0) continue;
                            GridPrintPage gridItemNow = singlePrintMan.getPages()[j][i];
                            List prtColsNow = gridItemNow.getPrtCols();
                            List prtRowsNow = gridItemNow.getPrtRows();
                            if (!printHeadRows.isEmpty()) {
                                for (k = printHeadRows.size() - 1; k >= 0; --k) {
                                    if (prtRowsNow.indexOf(printHeadRows.get(k)) >= 0) continue;
                                    printHeadRows.remove(k);
                                }
                            }
                            if (!printHeadCols.isEmpty()) {
                                for (k = printHeadCols.size() - 1; k >= 0; --k) {
                                    if (prtColsNow.indexOf(printHeadCols.get(k)) >= 0) continue;
                                    printHeadCols.remove(k);
                                }
                            }
                            if (null == prtColsZero || null == prtRowsZero || null == prtColsNow || null == prtRowsNow) continue;
                            if (i == 0 && j == 1 && prtColsZero.get(0) != prtColsNow.get(0)) {
                                paginateConfig.setShowColHeaderInAllPages(false);
                            }
                            if (i == 0 && j == 1 && prtColsZero.get(prtColsZero.size() - 1) != prtColsNow.get(prtColsNow.size() - 1)) {
                                paginateConfig.setShowColFooterInAllPages(false);
                            }
                            if (horzPages == 1) {
                                paginateConfig.setColPaginateType(-1);
                            } else if (j != 0 && (paginateConfig.getColPaginateType() == 1 || paginateConfig.getColPaginateType() == -1)) {
                                paginateConfig.setColPaginateType(-1);
                                if (colPaginateLocations[j - 1] == 0) {
                                    colPaginateLocations[j - 1] = (Integer)singlePrintMan.getPages()[j - 1][i].getPrtCols().get(singlePrintMan.getPages()[j - 1][i].getPrtCols().size() - 2);
                                }
                            }
                            if (j == 0 && i == 1 && prtRowsZero.get(0) != prtRowsNow.get(0)) {
                                paginateConfig.setShowRowHeaderInAllPages(false);
                            }
                            if (j == 0 && i == 1 && prtRowsZero.get(prtRowsZero.size() - 1) != prtRowsNow.get(prtRowsNow.size() - 1)) {
                                paginateConfig.setShowRowFooterInAllPages(false);
                            }
                            if (vertPages == 1) {
                                paginateConfig.setRowPaginateType(-1);
                                continue;
                            }
                            if (i == 0 || paginateConfig.getRowPaginateType() != 0 && paginateConfig.getRowPaginateType() != -1) continue;
                            paginateConfig.setRowPaginateType(-1);
                            if (rowPaginateLocations[i - 1] != 0) continue;
                            if (i != 1) {
                                if (paginateConfig.isShowRowHeaderInAllPages()) {
                                    int printRows2;
                                    int top = 0;
                                    if (gridItemNow.getGridData() != null) {
                                        top = gridItemNow.getGridData().getHeaderRowCount();
                                    }
                                    if ((printRows2 = singlePrintMan.getPages()[j][i - 1].getPrtRows().size()) > 2) {
                                        rowPaginateLocations[i - 1] = (Integer)singlePrintMan.getPages()[j][i - 1].getPrtRows().get(printRows2 - 2) + (top - 1) * (i - 1);
                                        continue;
                                    }
                                    if (i >= 2) {
                                        rowPaginateLocations[i - 1] = rowPaginateLocations[i - 2];
                                    }
                                    log.info("\u6253\u5370\u6a21\u677f\u5b58\u5728\u5f02\u5e38\uff0c\u8bf7\u68c0\u67e5:{}", (Object)(scheme.getTitle() + "," + scheme.getKey().toString() + ",\u8868\u5355\uff1a" + form.getFormCode() + "," + printTemplate.getKey().toString() + ",\u65f6\u95f4:" + new Date().toString()));
                                    continue;
                                }
                                printRows = singlePrintMan.getPages()[j][i - 1].getPrtRows().size();
                                if (printRows >= 2) {
                                    rowPaginateLocations[i - 1] = (Integer)singlePrintMan.getPages()[j][i - 1].getPrtRows().get(printRows - 2);
                                    continue;
                                }
                                if (i >= 2) {
                                    rowPaginateLocations[i - 1] = rowPaginateLocations[i - 2];
                                }
                                log.info("\u6253\u5370\u6a21\u677f\u5b58\u5728\u5f02\u5e38\uff0c\u8bf7\u68c0\u67e5:{}", (Object)(scheme.getTitle() + "," + scheme.getKey().toString() + ",\u8868\u5355\uff1a" + form.getFormCode() + "," + printTemplate.getKey().toString() + ",\u65f6\u95f4:" + new Date().toString()));
                                continue;
                            }
                            printRows = singlePrintMan.getPages()[j][i - 1].getPrtRows().size();
                            if (printRows >= 2) {
                                rowPaginateLocations[i - 1] = (Integer)singlePrintMan.getPages()[j][i - 1].getPrtRows().get(printRows - 2);
                                continue;
                            }
                            if (i >= 2) {
                                rowPaginateLocations[i - 1] = rowPaginateLocations[i - 2];
                            }
                            log.info("\u6253\u5370\u6a21\u677f\u5b58\u5728\u5f02\u5e38\uff0c\u8bf7\u68c0\u67e5:{}", (Object)(scheme.getTitle() + "," + scheme.getKey().toString() + ",\u8868\u5355\uff1a" + form.getFormCode() + "," + printTemplate.getKey().toString() + ",\u65f6\u95f4:" + new Date().toString()));
                        }
                    }
                    if (rowPaginateLocations != null && rowPaginateLocations[0] != 0) {
                        paginateConfig.setRowPaginateLocations(rowPaginateLocations);
                    }
                    if (colPaginateLocations != null && colPaginateLocations[0] != 0) {
                        paginateConfig.setColPaginateLocations(colPaginateLocations);
                    }
                    if (rowPaginateLocations != null && rowPaginateLocations[0] != 0 || colPaginateLocations != null && colPaginateLocations[0] != 0) {
                        BasicTablePaginateLocationProvider locationProvider = new BasicTablePaginateLocationProvider(paginateConfig);
                        paginateConfig.setPaginateLocationProvider((ITablePaginateLocationProvider)locationProvider);
                    }
                }
                byte[] reportData = null;
                newGridData = null;
                if (null != form) {
                    reportData = form.getBinaryData();
                }
                if (null != reportData) {
                    Grid2Data bytesToGrid = Grid2Data.bytesToGrid((byte[])reportData);
                    newGridData = new GridData();
                    PrintUtil.grid2DataToGridData((Grid2Data)bytesToGrid, (GridData)newGridData);
                }
                if (null == newGridData) {
                    newGridData = new GridData();
                }
                int gridDataCols = 0;
                int gridDataRows = 0;
                if (gridItemZero.getGridData() != null) {
                    gridDataCols = gridItemZero.getGridData().getColumnCount();
                    gridDataRows = gridItemZero.getGridData().getRowCount();
                }
                if ((cols = singlePrintMan.getColAxis().size()) == 0) {
                    log.info("\u6253\u5370\u6a21\u677f\u672a\u5b9a\u4e49\u6253\u5370\u5217\uff0c\u8bf7\u68c0\u67e5:{}", (Object)(scheme.getTitle() + "," + scheme.getKey().toString() + ",\u8868\u5355\uff1a" + form.getFormCode() + "," + printTemplate.getKey().toString() + ",\u65f6\u95f4:" + new Date().toString()));
                }
                if (cols + 1 != gridDataCols) {
                    List closList = singlePrintMan.getColAxis();
                    for (int index = 0; index < newGridData.getColCount(); ++index) {
                        if (closList.contains(index)) {
                            newGridData.setColVisible(index, true);
                            continue;
                        }
                        newGridData.setColVisible(index, false);
                    }
                }
                if ((rows = singlePrintMan.getRowAxis().size()) + 1 != gridDataRows) {
                    List rowsList = singlePrintMan.getRowAxis();
                    for (int index = 0; index < newGridData.getRowCount(); ++index) {
                        if (rowsList.contains(index)) {
                            newGridData.setRowVisible(index, true);
                            continue;
                        }
                        newGridData.setRowVisible(index, false);
                    }
                }
                if (!printHeadRows.isEmpty()) {
                    newGridData.setScrollTopRow((Integer)printHeadRows.get(printHeadRows.size() - 1) + 1);
                }
                if (!printHeadCols.isEmpty()) {
                    newGridData.setScrollTopCol((Integer)printHeadCols.get(printHeadCols.size() - 1) + 1);
                }
                GraphItem graphItem = singlePrintMan.getGraphs()[0][0];
                this.convertGraph(pageLi, graphItem, textTrans);
                resizeConfig.setVerticalResizeType(5);
                ElementsDrawParam param = new ElementsDrawParam(pageLi, form, null, 0);
                reportElement = this.drawReportElementLi(param, gridItemZero, newGridData);
                if (singlePrintMan.getPageData().getGlobalProp() > 0) {
                    // empty if block
                }
                double minBottomLableTop = this.ResizeElement(reportElement, pageLi);
                if (horzPages == 1 || vertPages == 1) {
                    FormType formType = form.getFormType();
                    paginateConfig.setColPaginateType(-1);
                    if (FormType.FORM_TYPE_FLOAT.getValue() == formType.getValue()) {
                        paginateConfig.setRowPaginateType(0);
                        resizeConfig.setVerticalResizeType(4);
                        double height = pageLi.getHeight();
                        double y = reportElement.getY();
                        height -= 2.0 * y;
                        if (minBottomLableTop > 0.0 && reportElement.getY() + height < minBottomLableTop - 0.1) {
                            height = minBottomLableTop - reportElement.getY() - 0.5;
                        }
                        reportElement.setHeight(height);
                    } else if (horzPages == 1 && vertPages == 1) {
                        paginateConfig.setRowPaginateType(-1);
                    }
                }
            } else {
                return false;
            }
            int OutLineStype = this.getOutLineStype(newGridData);
            TableLineConfig config = new TableLineConfig();
            config.setOutsideLineStyle(OutLineStype);
            config.setInsideThickness(0.1);
            config.setOutsideThickness(0.1);
            reportElement.setLineConfig(config);
            reportElement.setPaginateConfig(paginateConfig);
            reportElement.setResizeConfig(resizeConfig);
            pageLi.add((ITemplateElement)reportElement);
            PrintDefineImportServiceImpl.printElementResetLocation(pageLi, attribute.getWordLabels());
            PrintDefineImportServiceImpl.printElementCorrect(pageLi);
            documentObject.add((ITemplatePage)pageLi);
            this.printController.setPrintTemplateAttribute(printTemplate, attribute);
            String printData = SerializeUtil.serialize((ITemplateObject)documentObject);
            byte[] printdDataBytes = null;
            if (StringUtils.isNotEmpty((String)printData)) {
                printdDataBytes = printData.getBytes();
            }
            printTemplate.setTemplateData(printdDataBytes);
            return true;
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new SingleParamImportException(e.getMessage(), e);
        }
    }

    private boolean updatePrintTemplateFMDM(DesignPrintTemplateDefine printTemplate, GridPrintMan singlePrintMan, DesignFormDefine form, DesignPrintTemplateSchemeDefine scheme) throws SingleParamImportException {
        try {
            ITemplateObjectFactory templateObjectFactory = GraphicalFactoryManager.getTemplateObjectFactory((String)"REPORT_PRINT_NATURE");
            if (null == templateObjectFactory) {
                return false;
            }
            DocumentTemplateObject documentObject = (DocumentTemplateObject)templateObjectFactory.create("document");
            documentObject.setNature("REPORT_PRINT_NATURE");
            PrintTemplateAttributeDefine attribute = this.printController.getPrintTemplateAttribute(printTemplate);
            HashMap<String, String> textTrans = new HashMap<String, String>();
            this.initLabels(singlePrintMan, attribute.getWordLabels(), scheme, textTrans);
            SecureRandom secureRandom = new SecureRandom();
            PageTemplateObject pageLi = this.initPage(singlePrintMan);
            pageLi.setID(printTemplate.getTitle() + secureRandom.nextDouble());
            TablePaginateConfig paginateConfig = new TablePaginateConfig();
            TableResizeConfig resizeConfig = new TableResizeConfig();
            GraphItem graphItem = singlePrintMan.getGraphs()[0][0];
            this.convertGraph(pageLi, graphItem, null);
            resizeConfig.setVerticalResizeType(5);
            ElementsDrawParam param = new ElementsDrawParam(pageLi, form, null, 0);
            TableTemplateObject reportElement = this.drawReportElementLi(param, null, null);
            TableLineConfig config = new TableLineConfig();
            config.setInsideThickness(0.1);
            config.setOutsideThickness(0.1);
            reportElement.setLineConfig(config);
            reportElement.setPaginateConfig(paginateConfig);
            reportElement.setResizeConfig(resizeConfig);
            pageLi.add((ITemplateElement)reportElement);
            PrintDefineImportServiceImpl.printElementResetLocation(pageLi, attribute.getWordLabels());
            PrintDefineImportServiceImpl.printElementCorrect(pageLi);
            documentObject.add((ITemplatePage)pageLi);
            this.printController.setPrintTemplateAttribute(printTemplate, attribute);
            String printData = SerializeUtil.serialize((ITemplateObject)documentObject);
            byte[] printdDataBytes = null;
            if (StringUtils.isNotEmpty((String)printData)) {
                printdDataBytes = printData.getBytes();
            }
            printTemplate.setTemplateData(printdDataBytes);
            return true;
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new SingleParamImportException(e.getMessage(), e);
        }
    }

    private TableTemplateObject drawReportElementLi(ElementsDrawParam param, GridPrintPage girdItem, GridData newGridData) {
        DesignFormDefine currReport = param.getReport();
        ReportTemplateObject reportElement = new ReportTemplateObject();
        if (null != girdItem) {
            reportElement.setWidth((double)(girdItem.getWidth() / 10));
            reportElement.setHeight((double)(girdItem.getHeight() / 10));
            reportElement.setX((double)(girdItem.getLeft() / 10));
            reportElement.setY((double)(girdItem.getTop() / 10));
        }
        String title = currReport == null ? "\u6253\u5370\u6a21\u677f" : currReport.getTitle();
        String reportGuid = currReport == null ? UUIDUtils.getKey() : currReport.getKey();
        reportElement.setID(UUID.randomUUID().toString());
        reportElement.setReportGuid(reportGuid);
        reportElement.setReportTitle(title);
        if (null != newGridData) {
            reportElement.setGridData(newGridData);
        }
        return reportElement;
    }

    protected TableTemplateObject drawReportElement(ElementsDrawParam param, GridPrintPage girdItem) {
        DesignFormDefine currReport = param.getReport();
        ReportTemplateObject reportElement = new ReportTemplateObject();
        reportElement.setWidth((double)(girdItem.getWidth() / 10));
        reportElement.setHeight((double)(girdItem.getHeight() / 10));
        reportElement.setX((double)(girdItem.getLeft() / 10));
        reportElement.setY((double)(girdItem.getTop() / 10));
        String title = currReport == null ? "\u6253\u5370\u6a21\u677f" : currReport.getTitle();
        String reportGuid = currReport == null ? UUIDUtils.getKey() : currReport.getKey();
        reportElement.setID(UUID.randomUUID().toString());
        reportElement.setReportGuid(reportGuid);
        reportElement.setReportTitle(title);
        this.setData(currReport, reportElement);
        reportElement.getPaginateConfig().setRowPaginateType(0);
        reportElement.getPaginateConfig().setColPaginateType(1);
        reportElement.getResizeConfig().setHorizonResizeType(5);
        reportElement.getResizeConfig().setVerticalResizeType(5);
        reportElement.getResizeConfig().setHorizonScaleLocked(false);
        return reportElement;
    }

    private int getOutLineStype(GridData newGridData) {
        int ir;
        int ic;
        int OutLineStype = 1;
        block0: for (ic = newGridData.getColCount() - 1; ic > 0; --ic) {
            if (!newGridData.getColVisible(ic)) continue;
            for (ir = 1; ir < newGridData.getRowCount(); ++ir) {
                if (!newGridData.getRowVisible(ir) || newGridData.getCell(ic, ir).getHasConjection() || newGridData.getCell(ic, ir).getREdgeStyle() == 1 || newGridData.getCell(ic, ir).getLEdgeStyle() == 0) continue;
                OutLineStype = 0;
                break block0;
            }
            break;
        }
        if (OutLineStype == 1) {
            block2: for (ic = 1; ic < newGridData.getColCount(); ++ic) {
                if (!newGridData.getColVisible(ic)) continue;
                for (ir = 1; ir < newGridData.getRowCount(); ++ir) {
                    if (!newGridData.getRowVisible(ir) || newGridData.getCell(ic, ir).getHasConjection() || newGridData.getCell(ic, ir).getLEdgeStyle() == 1 || newGridData.getCell(ic, ir).getLEdgeStyle() == 0) continue;
                    OutLineStype = 0;
                    break block2;
                }
                break;
            }
        }
        return OutLineStype;
    }

    private double ResizeElement(TableTemplateObject reportElement, PageTemplateObject page) {
        double bottomLableAdd;
        double topLableAdd;
        double maxTopLableTop = 0.0;
        double minBottomLableTop = 0.0;
        ITemplateElement[] templateElements = page.getTemplateElements();
        ArrayList<Object> topLables = new ArrayList<Object>();
        ArrayList<Object> BottomLables = new ArrayList<Object>();
        for (ITemplateElement iTemplateElement : templateElements) {
            if (iTemplateElement instanceof WordLabelTemplateObject) {
                WordLabelTemplateObject label = (WordLabelTemplateObject)iTemplateElement;
                if (label.getY() <= reportElement.getY() + 1.0) {
                    topLables.add(label);
                    if (!(maxTopLableTop < label.getY() + label.getHeight())) continue;
                    maxTopLableTop = label.getY() + label.getHeight();
                    continue;
                }
                if (!(label.getY() >= reportElement.getY() + reportElement.getHeight() - 1.0)) continue;
                BottomLables.add(label);
                if (minBottomLableTop != 0.0 && !(minBottomLableTop > label.getY())) continue;
                minBottomLableTop = label.getY();
                continue;
            }
            if (!(iTemplateElement instanceof ReportLabelTemplateObject)) continue;
            ReportLabelTemplateObject label3 = (ReportLabelTemplateObject)iTemplateElement;
            if (label3.getY() <= reportElement.getY() + 1.0) {
                topLables.add(label3);
                if (!(maxTopLableTop < label3.getY() + label3.getHeight())) continue;
                maxTopLableTop = label3.getY() + label3.getHeight();
                continue;
            }
            if (!(label3.getY() >= reportElement.getY() + reportElement.getHeight() - 1.0)) continue;
            BottomLables.add(label3);
            if (minBottomLableTop != 0.0 && !(minBottomLableTop > label3.getY())) continue;
            minBottomLableTop = label3.getY();
        }
        if (maxTopLableTop > 0.0 && (topLableAdd = reportElement.getY() - maxTopLableTop - 0.1) < 0.0) {
            for (TextTemplateObject textTemplateObject : topLables) {
                if (!(textTemplateObject.getY() + topLableAdd > 0.0)) continue;
                textTemplateObject.setY(textTemplateObject.getY() + topLableAdd);
            }
        }
        if (minBottomLableTop > 0.0 && (bottomLableAdd = minBottomLableTop - reportElement.getY() - reportElement.getHeight() - 0.1) < 0.0) {
            for (TextTemplateObject textTemplateObject : BottomLables) {
                textTemplateObject.setY(textTemplateObject.getY() - bottomLableAdd);
            }
        }
        return minBottomLableTop;
    }

    private void setData(DesignFormDefine report, ReportTemplateObject reportElement) {
        byte[] reportData = null;
        if (null != report) {
            reportData = report.getBinaryData();
        }
        if (null != reportData) {
            Grid2Data bytesToGrid = Grid2Data.bytesToGrid((byte[])reportData);
            GridData newGridData = new GridData();
            PrintUtil.grid2DataToGridData((Grid2Data)bytesToGrid, (GridData)newGridData);
            reportElement.setGridData(newGridData);
        }
    }

    private void convertGraph(PageTemplateObject page, GraphItem graphItem, Map<String, String> textTrans) {
        SecureRandom secureRandom = new SecureRandom();
        if (graphItem instanceof GraphGroup) {
            GraphGroup graphGroup = (GraphGroup)graphItem;
            for (int i = 0; i < graphGroup.getSize(); ++i) {
                this.convertGraph(page, (GraphItem)graphGroup.getItems().get(i), textTrans);
            }
        } else if (graphItem instanceof GText) {
            this.convertGraphByGText(page, graphItem, textTrans, secureRandom);
        } else if (graphItem instanceof GLineItem) {
            GLineItem lineItem = (GLineItem)graphItem;
            LineTemplateObject element = new LineTemplateObject();
            element.setID("line" + secureRandom.nextDouble());
            element.setLine((double)(lineItem.getX1() / 10), (double)(lineItem.getY1() / 10), (double)(lineItem.getX2() / 10), (double)(lineItem.getY2() / 10));
            element.setThickness((double)lineItem.getWidth() / 9.0);
            element.setColor(lineItem.getLineColor());
            element.setLineStyle(lineItem.getStyle());
            page.add((ITemplateElement)element);
        } else if (graphItem instanceof GPoints) {
            GPoints pointItem = (GPoints)graphItem;
            int len = pointItem.getPoints().size();
            for (int i = 0; i < len - 1; ++i) {
                PointRec XPoint = (PointRec)pointItem.getPoints().get(i);
                PointRec YPoint = (PointRec)pointItem.getPoints().get(i + 1);
                LineTemplateObject element = new LineTemplateObject();
                element.setID("line" + secureRandom.nextDouble());
                element.setLine((double)(XPoint.getX() / 10L), (double)(XPoint.getY() / 10L), (double)(YPoint.getX() / 10L), (double)(YPoint.getY() / 10L));
                element.setThickness(0.1111111111111111);
                element.setColor(0);
                element.setLineStyle(4);
                page.add((ITemplateElement)element);
            }
        } else if (graphItem instanceof GRectangleItem) {
            GRectangleItem lineItem = (GRectangleItem)graphItem;
            if (lineItem.getX1() == lineItem.getX2() || lineItem.getY1() == lineItem.getY2()) {
                return;
            }
            this.convertGraphByGRectangleItem(page, graphItem, textTrans, secureRandom);
        } else {
            log.info("XXXXXXXXXXXXXXXXXXXXXXXX\u6709\u5176\u4ed6\u6807\u7b7e\u7c7b\u578b\u672a\u5904\u7406:{}", (Object)"\u8bf7\u68c0\u67e5");
        }
    }

    private void convertGraphByGText(PageTemplateObject page, GraphItem graphItem, Map<String, String> textTrans, SecureRandom secureRandom) {
        GText text = (GText)graphItem;
        ReportLabelTemplateObject element = new ReportLabelTemplateObject();
        element.setID("text" + secureRandom.nextDouble());
        String newText = this.transformationText(text.getText());
        if (StringUtils.isNotEmpty((String)newText) && textTrans != null && textTrans.containsKey(newText)) {
            newText = textTrans.get(newText);
        }
        int textLength = newText == null ? 0 : newText.length();
        double textWidth = 0.0;
        if (text.getFont().getSize() > 0.0) {
            element.setHeight(text.getFont().getSize() / 2.0);
            textWidth = this.getTextLableWidth(text.getFont(), newText) + 1.0;
        } else {
            element.setHeight(SinglePrintUtil.getFontSizeByCE((double)(text.getHeight() * 72.0 / 254.0)) / 2.0);
            textWidth = (double)textLength * Math.ceil(element.getHeight() + 1.0);
        }
        element.setWidth(textWidth);
        element.setX(text.getLeft() / 10.0);
        element.setY(text.getTop() / 10.0);
        element.setContent(newText);
        Font newFont = new Font(text.getFont());
        newFont.setColor(this.convertColorRGB(newFont.getColor()));
        element.setFont(newFont);
        element.setAutoWrap(false);
        page.add((ITemplateElement)element);
    }

    private int convertColorRGB(int oldColor) {
        int bc = oldColor & 0xFF;
        int gc = oldColor >> 8 & 0xFF;
        int rc = oldColor >> 16 & 0xFF;
        Color c = new Color(bc, gc, rc);
        return c.getRGB();
    }

    private void convertGraphByGRectangleItem(PageTemplateObject page, GraphItem graphItem, Map<String, String> textTrans, SecureRandom secureRandom) {
        GRectangleItem lineItem = (GRectangleItem)graphItem;
        for (int i = 0; i < 4; ++i) {
            int x1 = 0;
            int y1 = 0;
            int x2 = 0;
            int Y2 = 0;
            if (i == 0) {
                x1 = lineItem.getX1();
                y1 = lineItem.getY1();
                x2 = lineItem.getX2();
                Y2 = lineItem.getY1();
            } else if (i == 1) {
                x1 = lineItem.getX2();
                y1 = lineItem.getY1();
                x2 = lineItem.getX2();
                Y2 = lineItem.getY2();
            } else if (i == 2) {
                x1 = lineItem.getX2();
                y1 = lineItem.getY2();
                x2 = lineItem.getX1();
                Y2 = lineItem.getY2();
            } else {
                x1 = lineItem.getX1();
                y1 = lineItem.getY2();
                x2 = lineItem.getX1();
                Y2 = lineItem.getY1();
            }
            LineTemplateObject element = new LineTemplateObject();
            element.setID("line" + secureRandom.nextDouble());
            element.setLine((double)(x1 / 10), (double)(y1 / 10), (double)(x2 / 10), (double)(Y2 / 10));
            element.setThickness((double)lineItem.getWidth() / 9.0);
            element.setColor(lineItem.getLineColor());
            element.setLineStyle(lineItem.getStyle());
            page.add((ITemplateElement)element);
        }
    }

    private String transformationText(String textStr) {
        if (StringUtils.isEmpty((String)textStr)) {
            return "";
        }
        textStr = textStr.replace("%d", "{#PageNumber}");
        String ret = new String(textStr);
        int leftIdx = ret.indexOf("<D>");
        int rightIdx = ret.indexOf("</D>");
        String fieldStr = "";
        if (leftIdx >= 0 && leftIdx + 3 < rightIdx) {
            fieldStr = textStr.substring(leftIdx + 3, rightIdx);
            ret = textStr.substring(0, leftIdx) + "{" + fieldStr + "}" + textStr.substring(rightIdx + 4, textStr.length());
        }
        return ret;
    }

    private PageTemplateObject initPage(GridPrintMan singlePrintMan) {
        PageTemplateObject page = new PageTemplateObject();
        Paper paper = PrintSchemeConsts.PaperMapping.PAPER_SIZE_DEFAULT.getPaper();
        Paper paperA3 = PrintSchemeConsts.PaperMapping.PAPER_SIZE_A3.getPaper();
        short paperType = singlePrintMan.getPageData().getPaperData().getPaperType();
        short orientation = singlePrintMan.getPageData().getPaperData().getOrientation();
        short paperWidth = singlePrintMan.getPageData().getPaperData().getPaperWidth();
        short paperHeight = singlePrintMan.getPageData().getPaperData().getPaperHeight();
        if (paperType > 0 && paperType < PrintSchemeConsts.PaperMapping.values().length) {
            paper = PrintSchemeConsts.PaperMapping.values()[paperType].getPaper();
        } else if (paperA3.getWidth() == (double)paperWidth && paperA3.getHeight() == (double)paperHeight) {
            paper = paperA3;
        } else if (paperA3.getHeight() == (double)paperWidth && paperA3.getWidth() == (double)paperHeight) {
            paper = paperA3;
        } else {
            for (PrintSchemeConsts.PaperMapping paperMapping : PrintSchemeConsts.PaperMapping.values()) {
                Paper paper1 = paperMapping.getPaper();
                if ((paper1.getWidth() != (double)paperWidth || paper1.getHeight() != (double)paperHeight) && (paper1.getHeight() != (double)paperWidth || paper1.getWidth() != (double)paperHeight)) continue;
                paper = paper1;
                break;
            }
        }
        if (orientation << 8 == 512) {
            page.setOrientation(256);
        } else {
            page.setOrientation(512);
        }
        page.setPaper(paper);
        short leftMargin = singlePrintMan.getPageData().getPaperMargin().getLeftMargin();
        short topMargin = singlePrintMan.getPageData().getPaperMargin().getTopMargin();
        short rightMargin = singlePrintMan.getPageData().getPaperMargin().getRightMargin();
        short bottomMargin = singlePrintMan.getPageData().getPaperMargin().getBottomMargin();
        page.setMargins(new double[]{topMargin, bottomMargin, leftMargin, rightMargin});
        if (singlePrintMan.getPageData().getGlobalProp() > 0) {
            // empty if block
        }
        short globalProp = singlePrintMan.getPageData().getGlobalProp();
        byte vertCenter = (byte)(singlePrintMan.getPageData().getVertCenter() ? 1 : 0);
        byte horzCenter = (byte)(singlePrintMan.getPageData().getHorzCenter() ? 1 : 0);
        byte fitInPage = (byte)(singlePrintMan.getPageData().getFitInPage() ? 1 : 0);
        byte iReserved1 = singlePrintMan.getPageData().getiReserved1();
        short iReserved2 = singlePrintMan.getPageData().getiReserved2();
        short wReserved1 = singlePrintMan.getPageData().getwReserved1();
        short wReserved2 = singlePrintMan.getPageData().getwReserved2();
        return page;
    }

    private void updateScheme(DesignPrintTemplateSchemeDefine scheme) throws Exception {
        PrintSchemeAttributeDefine attribute = this.printController.getPrintSchemeAttribute(scheme);
        List wordLabels = attribute.getWordLabels();
        wordLabels.removeAll(wordLabels);
        wordLabels.addAll(Arrays.asList(PrintUtil.getDefaultWordLabels()));
        PrintPaperDefine paper = attribute.getPaper();
        paper.setDirection(1);
        paper.setMarginTop(15.0);
        paper.setMarginBottom(10.0);
        paper.setMarginLeft(10.0);
        paper.setMarginRight(10.0);
        this.printController.setPrintSchemeAttribute(scheme, attribute);
    }

    private List<WordLabelDefine> initLabels(GridPrintMan singlePrintMan, List<WordLabelDefine> wordLabels, DesignPrintTemplateSchemeDefine scheme, Map<String, String> textTrans) {
        if (null == wordLabels) {
            wordLabels = new ArrayList<WordLabelDefine>();
        } else {
            wordLabels.clear();
        }
        int count = singlePrintMan.getTextsDef().getCount();
        for (int i = 0; i < count; ++i) {
            int idx;
            GridPrintTextData textData = singlePrintMan.getTextsDef().getTextData(i);
            WordLabelDefine define = this.printController.createWordLabelDefine();
            define.setElement(0);
            String text = this.transformationText(textData.getText());
            String comment = textData.getComments();
            if ("\u91d1\u989d\u5355\u4f4d".equalsIgnoreCase(comment) && StringUtils.isNotEmpty((String)text) && (idx = text.indexOf("\uff1a")) >= 0) {
                String newText = text.substring(0, idx) + "\uff1a{#RPTMONEYUNIT}";
                if (textTrans != null) {
                    textTrans.put(text, newText);
                }
                text = newText;
            }
            short option = textData.getPosition();
            switch (option) {
                case 0: {
                    define.setHorizontalPos(0);
                    define.setVerticalPos(0);
                    define.setElement(1);
                    break;
                }
                case 1: {
                    define.setHorizontalPos(2);
                    define.setVerticalPos(0);
                    define.setElement(1);
                    break;
                }
                case 2: {
                    define.setHorizontalPos(0);
                    define.setVerticalPos(1);
                    define.setElement(1);
                    break;
                }
                case 3: {
                    define.setHorizontalPos(2);
                    define.setVerticalPos(1);
                    define.setElement(1);
                    break;
                }
                case 4: {
                    define.setHorizontalPos(1);
                    define.setVerticalPos(0);
                    define.setElement(1);
                    break;
                }
                case 5: {
                    define.setHorizontalPos(1);
                    define.setVerticalPos(1);
                    define.setElement(1);
                    break;
                }
                case 6: {
                    define.setHorizontalPos(1);
                    define.setVerticalPos(0);
                    define.setElement(0);
                    break;
                }
                case 7: {
                    define.setHorizontalPos(0);
                    define.setVerticalPos(0);
                    define.setElement(0);
                    break;
                }
                case 8: {
                    define.setHorizontalPos(2);
                    define.setVerticalPos(0);
                    define.setElement(0);
                    break;
                }
                case 9: {
                    define.setHorizontalPos(1);
                    define.setVerticalPos(1);
                    define.setElement(0);
                    break;
                }
                case 10: {
                    define.setHorizontalPos(0);
                    define.setVerticalPos(1);
                    define.setElement(0);
                    break;
                }
                case 11: {
                    define.setHorizontalPos(2);
                    define.setVerticalPos(1);
                    define.setElement(0);
                    break;
                }
            }
            FontDataClass font = textData.getFontData();
            define.setText(text);
            define.setFont(this.convertFont(font));
            wordLabels.add(define);
        }
        if (null == wordLabels || wordLabels.size() == 0) {
            try {
                PrintSchemeAttributeDefine attributeScheme = this.printController.getPrintSchemeAttribute(scheme);
                wordLabels = attributeScheme.getWordLabels();
            }
            catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        return wordLabels;
    }

    private String convertTextStr(String textStr) {
        if (StringUtils.isEmpty((String)textStr)) {
            return textStr;
        }
        String ret = new String(textStr);
        int leftIdx = ret.indexOf("<D>");
        int rightIdx = ret.indexOf("</D>");
        String fieldStr = "";
        if (leftIdx >= 0 && leftIdx + 3 < rightIdx) {
            fieldStr = textStr.substring(leftIdx + 3, rightIdx);
            ret = "{" + fieldStr + "}";
        }
        return ret;
    }

    private com.jiuqi.np.grid.Font convertFont(FontDataClass font) {
        com.jiuqi.np.grid.Font retFont = new com.jiuqi.np.grid.Font();
        retFont.setStylevalue((int)font.getStyles());
        retFont.setSize(new Double(font.getHeight()).intValue());
        retFont.setName(font.getName());
        retFont.setColor(this.convertColorRGB(font.getColor()));
        return retFont;
    }

    public static void printElementResetLocation(PageTemplateObject page, List<WordLabelDefine> wordLabels) {
        if (wordLabels == null) {
            return;
        }
        HashMap<String, WordLabelDefine> lableMap = new HashMap<String, WordLabelDefine>();
        for (WordLabelDefine lableDefine : wordLabels) {
            lableMap.put(lableDefine.getText(), lableDefine);
        }
        ITemplateElement[] templateElements = page.getTemplateElements();
        for (int i = 0; i < templateElements.length; ++i) {
            ReportLabelTemplateObject wordLabel;
            String content;
            ITemplateElement element = templateElements[i];
            if (!(element instanceof ReportLabelTemplateObject) || !StringUtils.isNotEmpty((String)(content = (wordLabel = (ReportLabelTemplateObject)element).getContent())) || !lableMap.containsKey(content)) continue;
            WordLabelDefine lableDefine = (WordLabelDefine)lableMap.get(content);
            String code = "0";
            if (lableDefine.getElement() == 0) {
                code = "0";
            } else if (lableDefine.getElement() == 1) {
                code = "1";
            }
            if (lableDefine.getVerticalPos() == 0) {
                code = code + "0";
            } else if (lableDefine.getVerticalPos() == 1) {
                code = code + "1";
            }
            if (lableDefine.getHorizontalPos() == 0) {
                code = code + "0";
            } else if (lableDefine.getHorizontalPos() == 1) {
                code = code + "1";
            } else if (lableDefine.getHorizontalPos() == 2) {
                code = code + "2";
            }
            wordLabel.setLocation(code);
        }
    }

    public static void printElementCorrect(PageTemplateObject page) {
        ITemplateElement[] templateElements = page.getTemplateElements();
        for (int i = 0; i < templateElements.length; ++i) {
            WordLabelTemplateObject wordLabel;
            String content;
            ITemplateElement element = templateElements[i];
            if (!(element instanceof LineTemplateObject || element.getWidth() != 0.0 && element.getHeight() != 0.0)) {
                page.remove(element);
                continue;
            }
            if (element.getX() + element.getWidth() < 0.0 || page.getWidth() < element.getX() || element.getY() + element.getHeight() < 0.0 || page.getHeight() < element.getHeight()) {
                page.remove(element);
                continue;
            }
            if (!(element instanceof WordLabelTemplateObject) || !StringUtils.isEmpty((String)(content = (wordLabel = (WordLabelTemplateObject)element).getContent())) && !(wordLabel.getX() < 0.0)) continue;
            page.remove(element);
        }
    }

    private double getTextLableWidth(Font font, String text) {
        FontMetrics metrics = FontMetrics.getMetrics((Font)font, (ILengthUnit)unit);
        return unit.toMilliMeter(metrics.getStringWidth(text));
    }
}

