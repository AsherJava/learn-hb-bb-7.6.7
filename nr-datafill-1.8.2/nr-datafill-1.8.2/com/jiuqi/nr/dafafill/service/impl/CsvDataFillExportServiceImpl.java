/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.file.FileInfo
 *  com.jiuqi.nr.file.FileService
 *  com.jiuqi.nr.table.df.DataFrame
 *  com.jiuqi.nr.table.io.csv.CsvWriteOptions
 */
package com.jiuqi.nr.dafafill.service.impl;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.dafafill.model.DataFillDataQueryInfo;
import com.jiuqi.nr.dafafill.model.DataFillDataResult;
import com.jiuqi.nr.dafafill.model.enums.ExportType;
import com.jiuqi.nr.dafafill.model.enums.TableSample;
import com.jiuqi.nr.dafafill.model.table.DataFillBaseCell;
import com.jiuqi.nr.dafafill.service.IDataFillDataEnvService;
import com.jiuqi.nr.dafafill.service.IDataFillExportService;
import com.jiuqi.nr.file.FileInfo;
import com.jiuqi.nr.file.FileService;
import com.jiuqi.nr.table.df.DataFrame;
import com.jiuqi.nr.table.io.csv.CsvWriteOptions;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CsvDataFillExportServiceImpl
implements IDataFillExportService {
    private static Logger logger = LoggerFactory.getLogger(CsvDataFillExportServiceImpl.class);
    @Autowired
    private IDataFillDataEnvService dataFillDataEnvService;
    @Autowired
    private FileService fileService;

    @Override
    public boolean accept(ExportType exportType) {
        return ExportType.CSV == exportType;
    }

    @Override
    public void export(DataFillDataQueryInfo queryInfo, AsyncTaskMonitor monitor) {
        queryInfo.setPagerInfo(null);
        queryInfo.getContext().getModel().setTableSample(TableSample.NOTSUPPORTED);
        monitor.progressAndMessage(0.1, "\u5f00\u59cb\u67e5\u8be2");
        DataFillDataResult dataResult = this.dataFillDataEnvService.query(queryInfo, monitor);
        monitor.progressAndMessage(0.8, "\u6784\u5efa\u5bfc\u51fa\u6587\u4ef6");
        if (dataResult.isSuccess()) {
            DataFrame<DataFillBaseCell> table = dataResult.getTable();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            CsvWriteOptions options = CsvWriteOptions.builder((OutputStream)out).header(true).colHeader(true).build();
            try {
                table.write().csv(options);
            }
            catch (IOException e) {
                logger.error("\u81ea\u5b9a\u4e49\u5f55\u5165\u5bfc\u51fa\u5f02\u5e38\uff01", e);
            }
            byte[] bytes = out.toByteArray();
            ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
            LocalDateTime currentTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-ddHH:mm:ss.SSS");
            String formattedTime = currentTime.format(formatter).replace(" ", "");
            FileInfo fileInfo = this.fileService.area("DATA_FILL_EXPORT").uploadTemp(formattedTime + "\u81ea\u5b9a\u4e49\u5f55\u5165\u5bfc\u51fa\u8868\u683c.csv", (InputStream)inputStream);
            monitor.finish("\u5bfc\u51fa\u5b8c\u6210", (Object)fileInfo);
        }
    }
}

