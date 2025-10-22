/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.attachment.transfer.service;

import com.jiuqi.nr.attachment.transfer.dto.FileInfoDTO;
import com.jiuqi.nr.attachment.transfer.dto.ImportParamDTO;
import com.jiuqi.nr.attachment.transfer.dto.ImportRecordDTO;
import com.jiuqi.nr.attachment.transfer.dto.ImportStatusDTO;
import com.jiuqi.nr.attachment.transfer.dto.RecordsQueryDTO;
import java.util.List;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public interface IImportTaskService {
    public List<ImportRecordDTO> initImportInfo(ImportParamDTO var1);

    public ImportParamDTO getParam();

    public List<ImportRecordDTO> listRecords(RecordsQueryDTO var1);

    public List<ImportRecordDTO> listNeedResume();

    public ImportStatusDTO queryRecords(RecordsQueryDTO var1);

    public XSSFWorkbook exportResult();

    public ImportRecordDTO queryRecord(String var1);

    public String queryError(String var1);

    public void reset();

    public List<FileInfoDTO> listFile();
}

