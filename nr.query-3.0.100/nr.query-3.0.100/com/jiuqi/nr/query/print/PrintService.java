/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.definition.controller.IPrintDesignTimeController
 *  com.jiuqi.nr.definition.print.vo.PrintAttributeVo
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.xg.draw2d.IPrintDevice
 *  com.jiuqi.xg.print.PrinterDevice
 *  com.jiuqi.xg.print.PrinterInfo
 *  com.jiuqi.xg.print.SimplePaintInteractor
 *  com.jiuqi.xg.print.util.AsyncWorkContainnerUtil
 *  com.jiuqi.xg.print.util.PrintProcessUtil
 *  com.jiuqi.xg.process.GraphicalFactoryManager
 *  com.jiuqi.xg.process.IContentStream
 *  com.jiuqi.xg.process.IPaginateContext
 *  com.jiuqi.xg.process.IPaintInteractor
 *  com.jiuqi.xg.process.IProcessMonitor
 *  com.jiuqi.xg.process.ITemplateDocument
 *  com.jiuqi.xg.process.ITemplatePage
 *  com.jiuqi.xg.process.Paper
 *  com.jiuqi.xg.process.SimpleProcessMonitor
 *  com.jiuqi.xg.process.impl.PaginateContext
 *  com.jiuqi.xg.process.table.TablePaginateConfig
 *  org.json.JSONObject
 */
package com.jiuqi.nr.query.print;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.definition.controller.IPrintDesignTimeController;
import com.jiuqi.nr.definition.print.vo.PrintAttributeVo;
import com.jiuqi.nr.query.block.QueryBlockDefine;
import com.jiuqi.nr.query.print.QueryPrintTemplateService;
import com.jiuqi.nr.query.print.service.impl.QueryPrintIPaginateInteractor;
import com.jiuqi.nr.query.print.service.impl.QueryPrintParam;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.xg.draw2d.IPrintDevice;
import com.jiuqi.xg.print.PrinterDevice;
import com.jiuqi.xg.print.PrinterInfo;
import com.jiuqi.xg.print.SimplePaintInteractor;
import com.jiuqi.xg.print.util.AsyncWorkContainnerUtil;
import com.jiuqi.xg.print.util.PrintProcessUtil;
import com.jiuqi.xg.process.GraphicalFactoryManager;
import com.jiuqi.xg.process.IContentStream;
import com.jiuqi.xg.process.IPaginateContext;
import com.jiuqi.xg.process.IPaintInteractor;
import com.jiuqi.xg.process.IProcessMonitor;
import com.jiuqi.xg.process.ITemplateDocument;
import com.jiuqi.xg.process.ITemplatePage;
import com.jiuqi.xg.process.Paper;
import com.jiuqi.xg.process.SimpleProcessMonitor;
import com.jiuqi.xg.process.impl.PaginateContext;
import com.jiuqi.xg.process.table.TablePaginateConfig;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrintService {
    private static final Logger log = LoggerFactory.getLogger(PrintService.class);
    @Autowired
    private IPrintDesignTimeController printController;
    @Autowired
    private QueryPrintTemplateService queryPrintTemplateService;

    public void print(PrinterDevice printerDevice, AsyncTaskMonitor asyncTaskMonitor, QueryBlockDefine queryBlockDefine, Grid2Data grid2Data, PrintAttributeVo attrVoConfig, TablePaginateConfig paginateConfig) {
        JSONObject returnInfo = new JSONObject();
        try {
            ArrayList contentList;
            block11: {
                double firstLevel = 0.05;
                asyncTaskMonitor.progressAndMessage(firstLevel, "");
                PaginateContext paginateContext = new PaginateContext("REPORT_PRINT_NATURE", GraphicalFactoryManager.getPaginateFactory((String)"REPORT_PRINT_NATURE"));
                SimplePaintInteractor simplePaintInteractor = new SimplePaintInteractor();
                contentList = new ArrayList();
                String paperName = "A4";
                int orientation = 512;
                try {
                    QueryPrintParam param = new QueryPrintParam();
                    param.setFormKey(queryBlockDefine.getBlockInfo().getFormSchemeKey());
                    QueryPrintIPaginateInteractor interactor = new QueryPrintIPaginateInteractor(param);
                    Object printTemPlate = null;
                    ITemplateDocument documentTemplateObject = null;
                    if (null == printTemPlate) {
                        try {
                            documentTemplateObject = this.queryPrintTemplateService.loadQueryTemplateDocument(queryBlockDefine, grid2Data, attrVoConfig, paginateConfig);
                        }
                        catch (Exception e) {
                            log.error(e.getMessage(), e);
                        }
                    }
                    ITemplatePage[] pages = documentTemplateObject.getPages();
                    ITemplatePage page = pages[0];
                    Paper paper = page.getPaper();
                    String name = paper.getName();
                    int paperOrientation = page.getOrientation();
                    documentTemplateObject.setNature("REPORT_PRINT_NATURE");
                    if (!paperName.equals(name) || orientation != paperOrientation) {
                        paperName = name;
                        orientation = paperOrientation;
                    }
                    double percent = 0.8;
                    asyncTaskMonitor.progressAndMessage(percent, "");
                }
                catch (Exception e) {
                    log.error(e.getMessage(), e);
                    String message = e.getMessage();
                    if (null == message || !message.startsWith("failPrinting")) break block11;
                    throw new RuntimeException("failPrinting \u591a\u6b21\u5c1d\u8bd5\u5f00\u542f\u4e0b\u6b21\u6253\u5370\u5931\u8d25");
                }
            }
            IContentStream[] streams = new IContentStream[contentList.size()];
            if (contentList.size() > 0) {
                printerDevice.setBatchPrint(false);
            }
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            asyncTaskMonitor.error("\u6253\u5370\u5931\u8d25\uff01\uff01", (Throwable)e);
            return;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        String objectToJson = null;
        try {
            objectToJson = objectMapper.writeValueAsString((Object)returnInfo);
        }
        catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }
        asyncTaskMonitor.finish("\u6253\u5370\u5b8c\u6210\u3002", (Object)objectToJson);
    }

    private void printPaper(PrinterDevice printerDevice, List<IContentStream> contentList, String paperName, int orientation, SimplePaintInteractor simplePaintInteractor, PaginateContext paginateContext, IContentStream[] streams, SimpleProcessMonitor monitor) {
        Paper[] supportedPapers;
        IContentStream[] tempStreams = new IContentStream[contentList.size()];
        for (int index = 0; index < contentList.size(); ++index) {
            tempStreams[index] = contentList.get(index);
        }
        PrinterInfo printerInfo = printerDevice.getPrinterInfo();
        for (Paper parper : supportedPapers = printerInfo.getSupportedPapers()) {
            if (!paperName.equals(parper.getName())) continue;
            printerDevice.setPaper(parper);
            break;
        }
        printerDevice.setOrientation(orientation);
        PrintProcessUtil.print((IContentStream[])tempStreams, (IPaintInteractor)simplePaintInteractor, (IPrintDevice)printerDevice, (IPaginateContext)paginateContext, (IProcessMonitor)monitor);
        contentList.clear();
        contentList.addAll(Arrays.asList(streams));
        boolean printIng = true;
        int maxCount = 1800;
        int count = 0;
        while (printIng) {
            try {
                Thread.sleep(1000L);
                if (++count >= maxCount) {
                    throw new RuntimeException("failPrinting \u591a\u6b21\u5c1d\u8bd5\u5f00\u542f\u4e0b\u6b21\u6253\u5370\u5931\u8d25");
                }
            }
            catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
            PrinterDevice printerDeviceTemp = AsyncWorkContainnerUtil.getPrinterDevice((String)printerDevice.getId());
            if (null != printerDeviceTemp) {
                printIng = printerDeviceTemp.isPrintIng();
                continue;
            }
            printIng = false;
        }
    }
}

