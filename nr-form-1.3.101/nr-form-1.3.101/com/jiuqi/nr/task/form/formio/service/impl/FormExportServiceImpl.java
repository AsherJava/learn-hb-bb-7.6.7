/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.np.definition.facade.DesignFieldDefine
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignDataLinkDefine
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.internal.controller.NRDesignTimeController
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.task.api.cellbook.CellBookInit
 *  com.jiuqi.nr.task.api.file.IFileAreaService
 *  com.jiuqi.nr.task.api.file.dto.FileAreaDTO
 *  com.jiuqi.nr.task.api.task.AsyncTaskInfo
 *  com.jiuqi.nr.task.api.task.DownloadInfo
 *  com.jiuqi.nvwa.cellbook.converter.CellBookGrid2dataConverter
 *  com.jiuqi.nvwa.cellbook.datatype.CommonCellDataType
 *  com.jiuqi.nvwa.cellbook.excel.CellSheetToExcel
 *  com.jiuqi.nvwa.cellbook.model.Cell
 *  com.jiuqi.nvwa.cellbook.model.CellBook
 *  com.jiuqi.nvwa.cellbook.model.CellSheet
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.nr.task.form.formio.service.impl;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.np.definition.facade.DesignFieldDefine;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignDataLinkDefine;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.internal.controller.NRDesignTimeController;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.task.api.cellbook.CellBookInit;
import com.jiuqi.nr.task.api.file.IFileAreaService;
import com.jiuqi.nr.task.api.file.dto.FileAreaDTO;
import com.jiuqi.nr.task.api.task.AsyncTaskInfo;
import com.jiuqi.nr.task.api.task.DownloadInfo;
import com.jiuqi.nr.task.form.common.FormExportVtEnum;
import com.jiuqi.nr.task.form.dto.FormExportDTO;
import com.jiuqi.nr.task.form.exception.FormExportRunTimeException;
import com.jiuqi.nr.task.form.exception.FormRuntimeException;
import com.jiuqi.nr.task.form.formio.IFormExportService;
import com.jiuqi.nr.task.form.formio.service.IFormExportCellExtractor;
import com.jiuqi.nr.task.form.formio.service.impl.formExportExtractors.DataLinkPosCellExtractor;
import com.jiuqi.nr.task.form.formio.service.impl.formExportExtractors.FieldTitleCellExtractor;
import com.jiuqi.nr.task.form.formio.service.impl.formExportExtractors.FieldTypeCellExtractor;
import com.jiuqi.nr.task.form.formio.service.impl.formExportExtractors.GatherTypeCellExtractor;
import com.jiuqi.nr.task.form.formio.service.impl.formExportExtractors.TableCellExtractor;
import com.jiuqi.nvwa.cellbook.converter.CellBookGrid2dataConverter;
import com.jiuqi.nvwa.cellbook.datatype.CommonCellDataType;
import com.jiuqi.nvwa.cellbook.excel.CellSheetToExcel;
import com.jiuqi.nvwa.cellbook.model.Cell;
import com.jiuqi.nvwa.cellbook.model.CellBook;
import com.jiuqi.nvwa.cellbook.model.CellSheet;
import com.jiuqi.nvwa.grid2.Grid2Data;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FormExportServiceImpl
implements IFormExportService {
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private NRDesignTimeController nrDesignTimeController;
    @Autowired
    private IFileAreaService fileAreaService;
    @Autowired
    private IEntityMetaService entityMetaService;
    private final Map<FormExportVtEnum, IFormExportCellExtractor> cellTextExtractorMap = new HashMap<FormExportVtEnum, IFormExportCellExtractor>();
    public static final String ROOT_LOCATION = System.getProperty("java.io.tmpdir");
    public static final String EXPORTDIR = ROOT_LOCATION + File.separator + ".nr" + File.separator + "AppData" + File.separator + "export";

    protected FormExportServiceImpl() {
    }

    @Override
    public void exportFormAsync(FormExportDTO exportObj, AsyncTaskMonitor monitor) {
        monitor.progressAndMessage(0.05, "\u5f00\u59cb\u5bfc\u51fa");
        String resultLocation = this.getTempLocation();
        try {
            this.exportAsync(resultLocation, exportObj);
        }
        catch (Exception e) {
            throw new FormExportRunTimeException("\u8868\u6837\u5bfc\u51fa\u5931\u8d25:" + e.getMessage(), e);
        }
        monitor.progressAndMessage(0.95, "");
        AsyncTaskInfo taskInfo = new AsyncTaskInfo(monitor.getTaskId(), "FORM_EXT", "\u8868\u6837\u5bfc\u51fa");
        taskInfo.setDownloadInfo(new DownloadInfo(exportObj.getDownLoadKey(), exportObj.getFileName()));
        monitor.finish("excel\u5bfc\u51fa\u5b8c\u6210", (Object)taskInfo);
    }

    @Override
    public void download(FormExportDTO exportObj, HttpServletResponse response) {
        String downLoadKey = exportObj.getDownLoadKey();
        try {
            this.fileAreaService.download(downLoadKey, (OutputStream)response.getOutputStream(), new FileAreaDTO(true));
        }
        catch (IOException e) {
            throw new FormRuntimeException("\u6587\u4ef6\u4e0b\u8f7d\u5931\u8d25:" + e.getMessage(), e);
        }
    }

    private void exportAsync(String resultLocation, FormExportDTO exportObj) {
        List<String> formKeys = exportObj.getFormKeys();
        String fileName = exportObj.getFileName();
        HashSet<String> sheetNameSet = new HashSet<String>();
        File file = new File(resultLocation);
        if (!file.exists()) {
            file.mkdirs();
        }
        try (FileOutputStream fos = new FileOutputStream(resultLocation.concat(File.separator).concat(fileName));){
            XSSFWorkbook workbook = new XSSFWorkbook();
            for (int i = 0; i < formKeys.size(); ++i) {
                String formId = formKeys.get(i);
                DesignFormDefine formDefine = this.designTimeViewController.getForm(formId);
                Grid2Data styleData = Grid2Data.bytesToGrid((byte[])formDefine.getBinaryData());
                String sheetName = this.getSheetName(exportObj.getShowForm(), formDefine);
                int number = 1;
                while (!sheetNameSet.add(sheetName)) {
                    sheetName = sheetName.split("_")[0] + "_" + number++;
                }
                this.exportExcel(workbook, styleData, i, sheetName, formId, exportObj.getViewType());
            }
            workbook.write(fos);
            try (FileInputStream uploadInputStream = new FileInputStream(resultLocation.concat(File.separator).concat(fileName));){
                this.fileAreaService.fileUploadByKey(fileName, (InputStream)uploadInputStream, exportObj.getDownLoadKey(), new FileAreaDTO(true));
            }
            fos.flush();
        }
        catch (Exception e) {
            throw new FormRuntimeException(e);
        }
    }

    private void exportExcel(XSSFWorkbook workbook, Grid2Data styleData, int sheetCount, String sheetName, String formId, String viewType) throws JQException {
        List linkList = this.designTimeViewController.listDataLinkByForm(formId);
        HashMap<String, DesignDataLinkDefine> linkXYMap = new HashMap<String, DesignDataLinkDefine>();
        this.getLinkMap(linkList, linkXYMap);
        FormExportVtEnum vtEnum = this.getEnum(Integer.parseInt(viewType));
        IFormExportCellExtractor extractor = this.getExtractor(vtEnum);
        styleData.deleteRows(0, 1);
        styleData.deleteColumns(0, 1);
        CellBook cellBook = CellBookInit.init();
        CellBookGrid2dataConverter.grid2DataToCellBook((Grid2Data)styleData, (CellBook)cellBook, (String)sheetName);
        CellSheet sheet = (CellSheet)cellBook.getSheets().get(0);
        int colCount = sheet.getColumnCount();
        int rowCount = sheet.getRowCount();
        for (int h = 0; h < rowCount; ++h) {
            for (int l = 0; l < colCount; ++l) {
                Cell cell = sheet.getCell(h, l);
                cell.setCommonDataType(CommonCellDataType.STRING);
                if (extractor == null) continue;
                String excelPositionString = h + 1 + "_" + (l + 1);
                DesignDataLinkDefine dataLink = (DesignDataLinkDefine)linkXYMap.get(excelPositionString);
                DesignFieldDefine fieldDefine = null;
                if (dataLink != null) {
                    fieldDefine = this.nrDesignTimeController.queryFieldDefine(dataLink.getLinkExpression());
                }
                String showText = cell.getShowText();
                extractor.setCellText(showText, dataLink, fieldDefine, cell);
            }
        }
        CellSheetToExcel cellSheetToExcel = new CellSheetToExcel(workbook);
        cellBook.getSheets().forEach(cellSheet -> cellSheetToExcel.writeToExcel(cellSheet));
    }

    private IFormExportCellExtractor getExtractor(FormExportVtEnum vtEnum) {
        IFormExportCellExtractor extractor = this.cellTextExtractorMap.get((Object)vtEnum);
        if (extractor == null) {
            switch (vtEnum) {
                case DATA_LINK_POS: {
                    extractor = new DataLinkPosCellExtractor();
                    this.cellTextExtractorMap.put(DataLinkPosCellExtractor.VIEW_TYPE, extractor);
                    break;
                }
                case TITLE: {
                    extractor = new FieldTitleCellExtractor(this.designTimeViewController, this.entityMetaService);
                    this.cellTextExtractorMap.put(FieldTitleCellExtractor.VIEW_TYPE, extractor);
                    break;
                }
                case FIELD_TYPE: {
                    extractor = new FieldTypeCellExtractor(this.designTimeViewController, this.entityMetaService);
                    this.cellTextExtractorMap.put(FieldTypeCellExtractor.viewType, extractor);
                    break;
                }
                case GATHER_TYPE: {
                    extractor = new GatherTypeCellExtractor();
                    this.cellTextExtractorMap.put(GatherTypeCellExtractor.VIEW_TYPE, extractor);
                    break;
                }
                case TABLE: {
                    extractor = new TableCellExtractor();
                    this.cellTextExtractorMap.put(TableCellExtractor.VIEW_TYPE, extractor);
                }
            }
        }
        return extractor;
    }

    private String getSheetName(String showForm, DesignFormDefine formDefine) {
        String sheetName = formDefine.getTitle();
        if (showForm.indexOf("1") > -1) {
            sheetName = "";
        }
        if ('1' == showForm.charAt(0)) {
            sheetName = sheetName + formDefine.getFormCode() + "|";
        }
        if ('1' == showForm.charAt(1)) {
            sheetName = sheetName + formDefine.getTitle() + "|";
        }
        if ('1' == showForm.charAt(2)) {
            sheetName = sheetName + formDefine.getSerialNumber();
        }
        if (sheetName.endsWith("|")) {
            sheetName = sheetName.substring(0, sheetName.length() - 1);
        }
        return sheetName;
    }

    private String getTempLocation() {
        StringBuffer filePath = new StringBuffer();
        NpContext context = NpContextHolder.getContext();
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formatDate = dateFormat.format(date);
        String dirUU = UUIDUtils.getKey();
        filePath.append(EXPORTDIR).append(File.separator).append(context.getUser().getName()).append(File.separator).append(formatDate).append(File.separator).append(dirUU);
        return filePath.toString();
    }

    private FormExportVtEnum getEnum(int value) {
        Optional<FormExportVtEnum> authority = Arrays.stream(FormExportVtEnum.values()).filter(o -> o.getValue() == value).findFirst();
        return authority.get();
    }

    private void getLinkMap(List<DesignDataLinkDefine> linkList, Map<String, DesignDataLinkDefine> linkMap) {
        if (linkList != null) {
            for (DesignDataLinkDefine dataLinkDefine : linkList) {
                linkMap.put(dataLinkDefine.getPosY() + "_" + dataLinkDefine.getPosX(), dataLinkDefine);
            }
        }
    }
}

