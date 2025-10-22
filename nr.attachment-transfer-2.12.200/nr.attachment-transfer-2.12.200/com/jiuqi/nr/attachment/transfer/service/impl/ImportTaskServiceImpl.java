/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.core.nodekeeper.DistributionManager
 */
package com.jiuqi.nr.attachment.transfer.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.core.nodekeeper.DistributionManager;
import com.jiuqi.nr.attachment.transfer.common.Constant;
import com.jiuqi.nr.attachment.transfer.common.Utils;
import com.jiuqi.nr.attachment.transfer.dao.IImportRecordDao;
import com.jiuqi.nr.attachment.transfer.dao.IImportTaskDao;
import com.jiuqi.nr.attachment.transfer.dao.IWorkSpaceDao;
import com.jiuqi.nr.attachment.transfer.dispatch.TaskExecutor;
import com.jiuqi.nr.attachment.transfer.domain.ImportRecordDO;
import com.jiuqi.nr.attachment.transfer.domain.ImportTaskDO;
import com.jiuqi.nr.attachment.transfer.domain.WorkSpaceDO;
import com.jiuqi.nr.attachment.transfer.dto.FileInfoDTO;
import com.jiuqi.nr.attachment.transfer.dto.ImportParamDTO;
import com.jiuqi.nr.attachment.transfer.dto.ImportRecordDTO;
import com.jiuqi.nr.attachment.transfer.dto.ImportStatusDTO;
import com.jiuqi.nr.attachment.transfer.dto.RecordsQueryDTO;
import com.jiuqi.nr.attachment.transfer.service.IImportTaskService;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ImportTaskServiceImpl
implements IImportTaskService {
    private static final Logger log = LoggerFactory.getLogger(ImportTaskServiceImpl.class);
    @Autowired
    private IImportTaskDao importTaskDao;
    @Autowired
    private IImportRecordDao importRecordDao;
    @Autowired
    private TaskExecutor taskExecutor;
    @Autowired
    private IWorkSpaceDao workSpaceDao;
    private final ObjectMapper mapper = new ObjectMapper();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    @Override
    public List<ImportRecordDTO> initImportInfo(ImportParamDTO importParamDTO) {
        String newParam;
        String hex = importParamDTO.getHex();
        ImportTaskDO query = this.importTaskDao.query(hex);
        if (query != null) {
            throw new RuntimeException("\u8bf7\u91cd\u7f6e\u4efb\u52a1\u540e\u518d\u6267\u884c");
        }
        List<File> newFiles = Utils.listFiles(importParamDTO.getFilePath());
        query = new ImportTaskDO();
        query.setKey(importParamDTO.getHex());
        String serveID = DistributionManager.getInstance().self().getName();
        query.setServerId(serveID);
        query.setStatus(0);
        try {
            newParam = this.mapper.writeValueAsString((Object)importParamDTO);
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        query.setParamConfig(newParam);
        List<ImportRecordDTO> records = this.initRecords(newFiles, query.getKey());
        query.setTotal(records.size());
        this.importTaskDao.insert(query);
        return records;
    }

    @Override
    public ImportParamDTO getParam() {
        ImportParamDTO paramDTO;
        String serveID = DistributionManager.getInstance().self().getName();
        ImportTaskDO importTaskDO = this.importTaskDao.queryByServe(serveID);
        if (importTaskDO == null) {
            return null;
        }
        String paramConfig = importTaskDO.getParamConfig();
        try {
            paramDTO = (ImportParamDTO)this.mapper.readValue(paramConfig, ImportParamDTO.class);
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return paramDTO;
    }

    @Override
    public List<ImportRecordDTO> listRecords(RecordsQueryDTO recordsQueryDTO) {
        List<ImportRecordDO> list = this.importRecordDao.listByStatus(recordsQueryDTO.getStatus());
        ArrayList<ImportRecordDTO> records = new ArrayList<ImportRecordDTO>(list.size());
        list.forEach(e -> records.add(ImportRecordDTO.getInstance(e)));
        return records;
    }

    @Override
    public List<ImportRecordDTO> listNeedResume() {
        List<ImportRecordDO> list = this.importRecordDao.listByStatus(Constant.ImportStatus.NONE.getStatus(), Constant.ImportStatus.READY.getStatus(), Constant.ImportStatus.DOING.getStatus());
        ArrayList<ImportRecordDTO> records = new ArrayList<ImportRecordDTO>(list.size());
        list.forEach(e -> records.add(ImportRecordDTO.getInstance(e)));
        return records;
    }

    @Override
    public ImportStatusDTO queryRecords(RecordsQueryDTO recordsQueryDTO) {
        ImportParamDTO paramDTO;
        ImportStatusDTO sumDTO = new ImportStatusDTO();
        int start = (recordsQueryDTO.getPage() - 1) * recordsQueryDTO.getPageSize();
        int end = start + recordsQueryDTO.getPageSize();
        List<ImportRecordDO> list = this.importRecordDao.listByFilter(recordsQueryDTO.getStatus(), start, end);
        Map<String, ImportRecordDO> listMap = list.stream().collect(Collectors.toMap(e -> e.getKey(), e -> e, (e1, e2) -> e2));
        List<ImportRecordDO> allList = this.importRecordDao.list();
        String serveID = DistributionManager.getInstance().self().getName();
        ImportTaskDO importTaskDO = this.importTaskDao.queryByServe(serveID);
        if (importTaskDO == null) {
            return null;
        }
        String paramConfig = importTaskDO.getParamConfig();
        try {
            paramDTO = (ImportParamDTO)this.mapper.readValue(paramConfig, ImportParamDTO.class);
        }
        catch (JsonProcessingException e3) {
            throw new RuntimeException(e3);
        }
        ArrayList<ImportRecordDTO> records = new ArrayList<ImportRecordDTO>(list.size());
        long success = 0L;
        long failed = 0L;
        long running = 0L;
        long waitTing = 0L;
        for (ImportRecordDO recordDO : allList) {
            ImportRecordDO result;
            switch (recordDO.getStatus()) {
                case 0: 
                case 1: {
                    ++waitTing;
                    break;
                }
                case 2: {
                    ++running;
                    break;
                }
                case 3: {
                    ++success;
                    break;
                }
                case 4: {
                    ++failed;
                    break;
                }
            }
            if ((result = listMap.get(recordDO.getKey())) == null) continue;
            ImportRecordDTO recordDTO = ImportRecordDTO.getInstance(result);
            recordDTO.setFileName(paramDTO.getFilePath() + File.separator + recordDTO.getFileName());
            records.add(recordDTO);
        }
        sumDTO.setRunning(running);
        sumDTO.setFailed(failed);
        sumDTO.setSuccess(success);
        sumDTO.setTotal(importTaskDO.getTotal());
        sumDTO.setWaitTing(waitTing);
        sumDTO.setRecords(records);
        int status = importTaskDO.getStatus();
        if (status == 1) {
            status = this.taskExecutor.isAlive() ? status : 2;
        }
        sumDTO.setStatus(status);
        return sumDTO;
    }

    @Override
    public XSSFWorkbook exportResult() {
        int i;
        List<ImportRecordDO> list = this.importRecordDao.list();
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet();
        String[] headers = new String[]{"\u6587\u4ef6\u540d", "\u5927\u5c0f", "\u72b6\u6001", "\u6267\u884c\u6b21\u6570", "\u5f00\u59cb\u65f6\u95f4", "\u7ed3\u675f\u65f6\u95f4", "\u5185\u5bb9"};
        workbook.setSheetName(0, "\u5bfc\u5165\u8be6\u60c5");
        XSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        sheet.setDefaultColumnWidth(20);
        XSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        style.setWrapText(true);
        XSSFRow row = sheet.createRow(0);
        for (i = 0; i < headers.length; ++i) {
            XSSFCell cell = row.createCell(i);
            cell.setCellStyle(style);
            XSSFRichTextString text = new XSSFRichTextString(headers[i]);
            cell.setCellValue(text.toString());
        }
        for (i = 0; i < list.size(); ++i) {
            row = sheet.createRow(i + 1);
            ImportRecordDO recordDO = list.get(i);
            block11: for (int j = 0; j < headers.length; ++j) {
                XSSFCell cell = row.createCell(j);
                switch (j) {
                    case 0: {
                        cell.setCellValue(recordDO.getFileName());
                        cell.setCellType(CellType.STRING);
                        continue block11;
                    }
                    case 1: {
                        cell.setCellValue(recordDO.getSize());
                        cell.setCellType(CellType.NUMERIC);
                        continue block11;
                    }
                    case 2: {
                        cell.setCellValue(Constant.ImportStatus.getTitle(recordDO.getStatus()));
                        cell.setCellType(CellType.STRING);
                        continue block11;
                    }
                    case 3: {
                        cell.setCellValue(recordDO.getExecuteNum());
                        cell.setCellType(CellType.NUMERIC);
                        continue block11;
                    }
                    case 4: {
                        cell.setCellValue(this.sdf.format(recordDO.getStartTime()));
                        cell.setCellType(CellType.STRING);
                        continue block11;
                    }
                    case 5: {
                        cell.setCellValue(this.sdf.format(recordDO.getEndTime()));
                        cell.setCellType(CellType.STRING);
                        continue block11;
                    }
                    case 6: {
                        cell.setCellValue(recordDO.getContent());
                        cell.setCellType(CellType.STRING);
                    }
                }
            }
        }
        return workbook;
    }

    @Override
    public ImportRecordDTO queryRecord(String key) {
        ImportRecordDO importRecordDO = this.importRecordDao.get(key);
        if (importRecordDO == null) {
            return null;
        }
        return ImportRecordDTO.getInstance(importRecordDO);
    }

    @Override
    public String queryError(String key) {
        ImportRecordDO importRecordDO = this.importRecordDao.get(key);
        if (importRecordDO == null) {
            return null;
        }
        return importRecordDO.getContent();
    }

    @Override
    public void reset() {
        String serveID = DistributionManager.getInstance().self().getName();
        this.importTaskDao.deleteByServer(serveID);
        this.importRecordDao.deleteAll();
    }

    @Override
    public List<FileInfoDTO> listFile() {
        File[] files;
        ArrayList<FileInfoDTO> fileInfos = new ArrayList<FileInfoDTO>();
        WorkSpaceDO workSpaceDO = this.workSpaceDao.get(2);
        if (workSpaceDO == null) {
            throw new RuntimeException("\u8bf7\u5148\u4fdd\u5b58\u76ee\u5f55\u914d\u7f6e");
        }
        String filePath = workSpaceDO.getFilePath();
        if (!StringUtils.hasText(filePath)) {
            return Collections.emptyList();
        }
        File dir = new File(FilenameUtils.normalize(filePath));
        if (!dir.exists()) {
            return Collections.emptyList();
        }
        for (File file : files = dir.listFiles()) {
            FileInfoDTO fileInfo = new FileInfoDTO();
            fileInfo.setFileName(file.getName());
            fileInfo.setFile(file.isFile());
            fileInfos.add(fileInfo);
        }
        return fileInfos;
    }

    private List<File> updateInfo(List<File> files, ImportTaskDO query, ImportParamDTO paramDTO) {
        String newParam;
        String paramConfig = query.getParamConfig();
        boolean insert = true;
        if (StringUtils.hasText(paramConfig)) {
            insert = false;
            try {
                paramDTO = (ImportParamDTO)this.mapper.readValue(paramConfig, ImportParamDTO.class);
            }
            catch (JsonProcessingException e2) {
                throw new RuntimeException(e2);
            }
        }
        List<String> oldFiles = paramDTO.getFiles();
        List<File> newFiles = files.stream().filter(e -> !oldFiles.contains(e.getName())).collect(Collectors.toList());
        oldFiles.addAll(newFiles.stream().map(File::getName).collect(Collectors.toList()));
        try {
            newParam = this.mapper.writeValueAsString((Object)paramDTO);
        }
        catch (JsonProcessingException e3) {
            throw new RuntimeException(e3);
        }
        query.setParamConfig(newParam);
        if (insert) {
            this.importTaskDao.insert(query);
        } else {
            this.importTaskDao.update(query);
        }
        return newFiles;
    }

    private List<ImportRecordDTO> initRecords(List<File> newFiles, String ownerKey) {
        ArrayList<ImportRecordDTO> list = new ArrayList<ImportRecordDTO>();
        ArrayList<ImportRecordDO> rows = new ArrayList<ImportRecordDO>();
        for (int i = 0; i < newFiles.size(); ++i) {
            File file = newFiles.get(i);
            ImportRecordDO recordDO = new ImportRecordDO();
            recordDO.setKey(UUID.randomUUID().toString());
            recordDO.setOwner(ownerKey);
            recordDO.setFileName(file.getName());
            recordDO.setSize(file.length());
            recordDO.setStatus(Constant.ImportStatus.NONE.getStatus());
            recordDO.setExecuteNum(0);
            recordDO.setOrder(i);
            rows.add(recordDO);
            ImportRecordDTO instance = ImportRecordDTO.getInstance(recordDO);
            instance.setPath(file.getPath());
            list.add(instance);
        }
        this.importRecordDao.insert(rows);
        return list;
    }
}

