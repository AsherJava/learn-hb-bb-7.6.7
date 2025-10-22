/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.JsonDeserializer
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.Module
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.module.SimpleModule
 *  com.jiuqi.grid.GridData
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.definition.facade.print.common.other.PrintUtil
 *  com.jiuqi.nr.definition.facade.print.core.ReportTemplateObject
 *  com.jiuqi.nr.definition.print.common.PrintElementUtils
 *  com.jiuqi.nr.definition.print.common.PrintGrid2DataDeserialize
 *  com.jiuqi.nr.print.dto.DesignerInfoDTO
 *  com.jiuqi.nr.print.service.IPrintCommonTemService
 *  com.jiuqi.nr.print.service.IPrintDesignExtendService
 *  com.jiuqi.nr.print.service.IPrintTemplateIOService
 *  com.jiuqi.nr.print.service.IReportPrintDesignService
 *  com.jiuqi.nr.print.web.param.CommonTemplatePM
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.GridCellData
 *  com.jiuqi.nvwa.grid2.json.Grid2DataSerialize
 *  com.jiuqi.nvwa.grid2.json.GridCellDataDeserialize
 *  com.jiuqi.nvwa.grid2.json.GridCellDataSerialize
 *  com.jiuqi.xg.process.ITemplateDocument
 *  com.jiuqi.xg.process.ITemplateElement
 *  com.jiuqi.xg.process.ITemplateObject
 *  com.jiuqi.xg.process.ITemplateObjectFactory
 *  com.jiuqi.xg.process.table.TablePaginateConfig
 *  com.jiuqi.xg.process.table.obj.TableTemplateObject
 *  com.jiuqi.xg.process.util.SerializeUtil
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.http.HttpServletResponse
 *  org.apache.shiro.authz.annotation.RequiresPermissions
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.designer.web.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.jiuqi.grid.GridData;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.definition.facade.print.common.other.PrintUtil;
import com.jiuqi.nr.definition.facade.print.core.ReportTemplateObject;
import com.jiuqi.nr.definition.print.common.PrintElementUtils;
import com.jiuqi.nr.definition.print.common.PrintGrid2DataDeserialize;
import com.jiuqi.nr.designer.common.NrDesingerErrorEnum;
import com.jiuqi.nr.print.dto.DesignerInfoDTO;
import com.jiuqi.nr.print.service.IPrintCommonTemService;
import com.jiuqi.nr.print.service.IPrintDesignExtendService;
import com.jiuqi.nr.print.service.IPrintTemplateIOService;
import com.jiuqi.nr.print.service.IReportPrintDesignService;
import com.jiuqi.nr.print.web.param.CommonTemplatePM;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.GridCellData;
import com.jiuqi.nvwa.grid2.json.Grid2DataSerialize;
import com.jiuqi.nvwa.grid2.json.GridCellDataDeserialize;
import com.jiuqi.nvwa.grid2.json.GridCellDataSerialize;
import com.jiuqi.xg.process.ITemplateDocument;
import com.jiuqi.xg.process.ITemplateElement;
import com.jiuqi.xg.process.ITemplateObject;
import com.jiuqi.xg.process.ITemplateObjectFactory;
import com.jiuqi.xg.process.table.TablePaginateConfig;
import com.jiuqi.xg.process.table.obj.TableTemplateObject;
import com.jiuqi.xg.process.util.SerializeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.Closeable;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.function.Consumer;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@JQRestController
@RequestMapping(value={"designer"})
@Api(tags={"\u6253\u5370\u8bbe\u8ba1\u5668"})
public class PrintDesignerController {
    private static final Logger logger = LoggerFactory.getLogger(PrintDesignerController.class);
    @Autowired
    private IReportPrintDesignService reportPrintDesignService;
    @Autowired
    private IPrintDesignExtendService printExtendService;
    @Autowired
    private IPrintCommonTemService commonTemplateService;
    @Autowired
    private IPrintTemplateIOService printTemplateIOService;

    @ApiOperation(value="\u53d1\u5e03\u6253\u5370\u6a21\u677f")
    @RequestMapping(value={"deploy/{designerId}/{schemeId}/{formId}"}, method={RequestMethod.GET})
    public void deployTemplate(@PathVariable String designerId, @PathVariable String schemeId, @PathVariable(required=false) String formId) throws Exception {
        this.reportPrintDesignService.deployTemplate(designerId, schemeId, formId);
    }

    @ApiOperation(value="\u83b7\u53d6\u6253\u5370\u8868\u683c\u5bf9\u8c61\u53ca\u624b\u52a8\u5206\u9875\u4fe1\u606f")
    @RequestMapping(value={"print/get/tableconfig/{designerId}/{elementId}"}, method={RequestMethod.GET})
    public PrintTableConfig getTableConfig(@PathVariable String designerId, @PathVariable String elementId) throws JsonProcessingException {
        PrintTableConfig customTableConfig = new PrintTableConfig();
        customTableConfig.setDesignerId(designerId);
        customTableConfig.setElementId(elementId);
        ITemplateElement<?> iTemplateElement = this.getTemplateElement(designerId, elementId);
        if (iTemplateElement instanceof TableTemplateObject) {
            TableTemplateObject tableTemplate = (TableTemplateObject)iTemplateElement;
            int[] rowPaginteLocations = tableTemplate.getPaginateConfig().getRowPaginteLocations();
            int[] colPaginateLocations = tableTemplate.getPaginateConfig().getColPaginateLocations();
            customTableConfig.setRowPaginate(rowPaginteLocations);
            customTableConfig.setColPaginate(colPaginateLocations);
            GridData gridData = tableTemplate.getGridData();
            Grid2Data grid2Data = PrintUtil.gridDataToGrid2Data((GridData)gridData, null);
            this.initGrid(grid2Data);
            ObjectMapper objectMapper = new ObjectMapper();
            SimpleModule module = new SimpleModule();
            module.addSerializer(Grid2Data.class, (JsonSerializer)new Grid2DataSerialize());
            module.addSerializer(GridCellData.class, (JsonSerializer)new GridCellDataSerialize());
            objectMapper.registerModule((Module)module);
            customTableConfig.setGridData(objectMapper.writeValueAsString((Object)grid2Data));
        }
        return customTableConfig;
    }

    private ITemplateElement<?> getTemplateElement(String designerId, String elementId) {
        String serializeTemplate = this.printExtendService.getCurrTemplateDocument(designerId);
        ITemplateDocument document = (ITemplateDocument)SerializeUtil.deserialize((String)serializeTemplate, (ITemplateObjectFactory)PrintElementUtils.FACTORY);
        ITemplateElement iTemplateElement = null;
        for (ITemplateElement templateElement : document.getPage(0).getTemplateElements()) {
            if (!templateElement.getID().equals(elementId)) continue;
            iTemplateElement = templateElement;
            break;
        }
        return iTemplateElement;
    }

    private void updateTemplateElement(String designerId, String elementId, Consumer<ITemplateElement<?>> consumer) {
        String serializeTemplate = this.printExtendService.getCurrTemplateDocument(designerId);
        ITemplateDocument document = (ITemplateDocument)SerializeUtil.deserialize((String)serializeTemplate, (ITemplateObjectFactory)PrintElementUtils.FACTORY);
        ITemplateElement iTemplateElement = null;
        for (ITemplateElement templateElement : document.getPage(0).getTemplateElements()) {
            if (!templateElement.getID().equals(elementId)) continue;
            iTemplateElement = templateElement;
            break;
        }
        if (null == iTemplateElement) {
            return;
        }
        consumer.accept(iTemplateElement);
        serializeTemplate = SerializeUtil.serialize((ITemplateObject)document);
        this.printExtendService.updateTemplate(designerId, serializeTemplate, true);
    }

    private void initGrid(Grid2Data gridData) {
        for (int c = 0; c < gridData.getColumnCount(); ++c) {
            for (int r = 0; r < gridData.getRowCount(); ++r) {
                GridCellData gridCellData = gridData.getGridCellData(c, r);
                gridCellData.setEditable(false);
                if (r == 0) {
                    gridCellData.setBackGroundColor(0xEBEBEB);
                }
                if (c != 0) continue;
                gridCellData.setBackGroundColor(0xEBEBEB);
            }
        }
    }

    private String toChars(int num) {
        int baseLength = 26;
        int baseCharCode = 65;
        if (num >= 26) {
            int index = num % 26;
            num /= 26;
            if (index == 0) {
                --num;
            }
            return this.toChars(num) + (char)(65 + index);
        }
        return "" + (char)(65 + num);
    }

    @ApiOperation(value="\u8bbe\u7f6e\u624b\u52a8\u5206\u9875")
    @RequestMapping(value={"print/update/tableconfig"}, method={RequestMethod.POST})
    public void updateTableConfig(@RequestBody PrintTableConfig paginateConfig) {
        String designerId = paginateConfig.getDesignerId();
        String elementId = paginateConfig.getElementId();
        this.updateTemplateElement(designerId, elementId, element -> {
            int[] colPaginate;
            TableTemplateObject tableTemplate = (TableTemplateObject)element;
            TablePaginateConfig tablePaginateConfig = tableTemplate.getPaginateConfig();
            int[] rowPaginate = paginateConfig.getRowPaginate();
            if (null != rowPaginate && rowPaginate.length > 0) {
                tablePaginateConfig.setRowPaginateLocations(rowPaginate);
                tablePaginateConfig.setRowPaginateType(-1);
            }
            if (null != (colPaginate = paginateConfig.getColPaginate()) && colPaginate.length > 0) {
                tablePaginateConfig.setColPaginateLocations(colPaginate);
                tablePaginateConfig.setColPaginateType(-1);
            }
        });
    }

    @ApiOperation(value="\u83b7\u53d6\u6253\u5370\u8868\u683c\u5bf9\u8c61")
    @RequestMapping(value={"print/get/grid/{designerId}/{elementId}"}, method={RequestMethod.GET})
    public String getTableGrid(@PathVariable String designerId, @PathVariable String elementId) throws JsonProcessingException {
        ITemplateElement<?> iTemplateElement = this.getTemplateElement(designerId, elementId);
        if (iTemplateElement instanceof TableTemplateObject) {
            TableTemplateObject tableTemplate = (TableTemplateObject)iTemplateElement;
            GridData gridData = tableTemplate.getGridData();
            Grid2Data grid2Data = PrintUtil.gridDataToGrid2Data((GridData)gridData, null);
            this.initGrid(grid2Data);
            ObjectMapper objectMapper = new ObjectMapper();
            SimpleModule module = new SimpleModule();
            module.addSerializer(Grid2Data.class, (JsonSerializer)new Grid2DataSerialize());
            module.addSerializer(GridCellData.class, (JsonSerializer)new GridCellDataSerialize());
            objectMapper.registerModule((Module)module);
            return objectMapper.writeValueAsString((Object)grid2Data);
        }
        return null;
    }

    @ApiOperation(value="\u66f4\u65b0\u6253\u5370\u8868\u683c\u5bf9\u8c61")
    @RequestMapping(value={"print/update/grid"}, method={RequestMethod.POST})
    public void updateTableGrid(@RequestBody PrintTableGrid tableGrid) throws IOException {
        String designerId = tableGrid.getDesignerId();
        String elementId = tableGrid.getElementId();
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Grid2Data.class, (JsonDeserializer)new PrintGrid2DataDeserialize());
        module.addDeserializer(GridCellData.class, (JsonDeserializer)new GridCellDataDeserialize());
        objectMapper.registerModule((Module)module);
        Grid2Data grid2Data = (Grid2Data)objectMapper.readValue(tableGrid.getGridData(), Grid2Data.class);
        GridData gridData = new GridData();
        PrintUtil.grid2DataToGridData((Grid2Data)grid2Data, (GridData)gridData);
        this.updateTemplateElement(designerId, elementId, element -> {
            if (element instanceof ReportTemplateObject) {
                ReportTemplateObject reportTemplateObject = (ReportTemplateObject)element;
                reportTemplateObject.setGridData(gridData);
                DesignerInfoDTO info = this.printExtendService.getPrintDesignerInfo(designerId);
                info.setCustomGrid(true);
                info.setCustomGuidDate(new Date());
                this.printExtendService.updatePrintDesignerInfo(designerId, info);
            } else {
                TableTemplateObject tableTemplate = (TableTemplateObject)element;
                tableTemplate.setGridData(gridData);
            }
        });
    }

    @ApiOperation(value="\u6bcd\u7248\u8986\u76d6")
    @RequestMapping(value={"print/setting/coverTemplate"}, method={RequestMethod.POST})
    public boolean coverPrintTemplate(@RequestBody CommonTemplatePM templatePM) {
        try {
            this.commonTemplateService.coverTemplate(templatePM);
            return true;
        }
        catch (Exception e) {
            logger.error("\u6bcd\u7248\u8986\u76d6\u5931\u8d25", (Object)e.getMessage(), (Object)e);
            return false;
        }
    }

    @ApiOperation(value="\u6bcd\u7248\u540c\u6b65")
    @RequestMapping(value={"print/setting/syncTemplate"}, method={RequestMethod.POST})
    public boolean syncPrintTemplate(@RequestBody CommonTemplatePM templatePM) {
        try {
            this.commonTemplateService.syncTemplate(templatePM);
            return true;
        }
        catch (Exception e) {
            logger.error("\u6bcd\u7248\u540c\u6b65\u5931\u8d25", (Object)e.getMessage(), (Object)e);
            return false;
        }
    }

    @ApiOperation(value="\u6253\u5370\u6a21\u677f\u5bfc\u5165")
    @RequestMapping(value={"print/template/import"}, method={RequestMethod.POST})
    public void templateImport(@RequestParam String designerId, @RequestBody MultipartFile file) throws JQException {
        try {
            this.printTemplateIOService.printTemplateImport(file, designerId);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_220, e.getMessage());
        }
    }

    @ApiOperation(value="\u6253\u5370\u6a21\u677f\u5bfc\u51fa")
    @RequestMapping(value={"print/template/export/{designerId}"}, method={RequestMethod.GET})
    @RequiresPermissions(value={"nr:task_print:manage"})
    public void templateExport(@PathVariable String designerId, HttpServletResponse response) throws JQException {
        ServletOutputStream outputStream = null;
        try {
            String template = this.printTemplateIOService.printTemplateExport(designerId);
            this.analysisHeader(response, "printTemplateExport");
            outputStream = response.getOutputStream();
            outputStream.write(template.getBytes());
            outputStream.flush();
            this.close((Closeable)outputStream);
        }
        catch (Exception e) {
            try {
                logger.error("\u6a21\u677f\u5bfc\u51fa\u5931\u8d25" + e.getMessage());
                throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_229, e.getMessage());
            }
            catch (Throwable throwable) {
                this.close((Closeable)outputStream);
                throw throwable;
            }
        }
    }

    @ApiOperation(value="\u6253\u5370\u6a21\u677f\u5bfc\u51fa\u540d\u79f0\u83b7\u53d6")
    @RequestMapping(value={"print/template/export/name/{formId}/{printSchemeId}"}, method={RequestMethod.GET})
    public String templateExportName(@PathVariable(required=false) String formId, @PathVariable String printSchemeId) throws JQException {
        String fileName = "";
        try {
            fileName = this.printTemplateIOService.printTemplateExportName(formId, printSchemeId);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_229, e.getMessage());
        }
        return fileName;
    }

    private void analysisHeader(HttpServletResponse response, String fileName) {
        try {
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/xml");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8") + ".xml");
        }
        catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static class PrintTableConfig
    extends PrintTableGrid {
        private int[] rowPaginate;
        private int[] colPaginate;

        public int[] getRowPaginate() {
            return this.rowPaginate;
        }

        public void setRowPaginate(int[] rowPaginate) {
            this.rowPaginate = rowPaginate;
        }

        public int[] getColPaginate() {
            return this.colPaginate;
        }

        public void setColPaginate(int[] colPaginate) {
            this.colPaginate = colPaginate;
        }
    }

    public static class PrintTableGrid {
        private String designerId;
        private String elementId;
        private String gridData;

        public String getDesignerId() {
            return this.designerId;
        }

        public void setDesignerId(String designerId) {
            this.designerId = designerId;
        }

        public String getElementId() {
            return this.elementId;
        }

        public void setElementId(String elementId) {
            this.elementId = elementId;
        }

        public String getGridData() {
            return this.gridData;
        }

        public void setGridData(String gridData) {
            this.gridData = gridData;
        }
    }
}

