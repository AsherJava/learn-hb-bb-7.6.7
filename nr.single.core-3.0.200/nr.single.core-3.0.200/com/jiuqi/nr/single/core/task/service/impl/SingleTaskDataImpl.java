/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.MemoryDataRow
 *  com.jiuqi.bi.dataset.Metadata
 *  com.jiuqi.bi.util.MemStream
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.bi.util.StreamException
 *  org.apache.commons.lang3.StringUtils
 */
package com.jiuqi.nr.single.core.task.service.impl;

import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.MemoryDataRow;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.util.MemStream;
import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.bi.util.StreamException;
import com.jiuqi.nr.single.core.common.SingleConsts;
import com.jiuqi.nr.single.core.dbf.DBFCreator;
import com.jiuqi.nr.single.core.dbf.DbfTableUtil;
import com.jiuqi.nr.single.core.dbf.IDbfTable;
import com.jiuqi.nr.single.core.exception.SingleFileException;
import com.jiuqi.nr.single.core.internal.file.SingleFileImpl;
import com.jiuqi.nr.single.core.para.ParaInfo;
import com.jiuqi.nr.single.core.para.consts.PeriodType;
import com.jiuqi.nr.single.core.para.parser.SolutionParser;
import com.jiuqi.nr.single.core.task.ISingleTaskEngine;
import com.jiuqi.nr.single.core.task.SingleTaskException;
import com.jiuqi.nr.single.core.task.model.SingleAttachFileInfo;
import com.jiuqi.nr.single.core.task.model.SingleAttachInfo;
import com.jiuqi.nr.single.core.task.model.SingleDataType;
import com.jiuqi.nr.single.core.task.model.SingleEntityDataItem;
import com.jiuqi.nr.single.core.task.model.SingleFieldInfo;
import com.jiuqi.nr.single.core.task.model.SinglePeriodDataItem;
import com.jiuqi.nr.single.core.task.model.SingleTaskInfo;
import com.jiuqi.nr.single.core.task.service.ISingleTableDataHandler;
import com.jiuqi.nr.single.core.task.service.ISingleTableDataWriter;
import com.jiuqi.nr.single.core.task.service.ISingleTaskData;
import com.jiuqi.nr.single.core.task.service.ISingleTaskParamReader;
import com.jiuqi.nr.single.core.task.service.impl.SingleTableDataWriterImpl;
import com.jiuqi.nr.single.core.task.service.impl.SingleTaskParamReaderImpl;
import com.jiuqi.nr.single.core.task.util.SingleTaskUtils;
import com.jiuqi.nr.single.core.util.Ini;
import com.jiuqi.nr.single.core.util.SinglePathUtil;
import com.jiuqi.nr.single.core.util.ZipUtil;
import com.jiuqi.nr.single.core.util.datatable.DataRow;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SingleTaskDataImpl
implements ISingleTaskData {
    private static final Logger logger = LoggerFactory.getLogger(SingleTaskDataImpl.class);
    private ISingleTaskEngine taskEngine;
    private SingleTaskInfo taskInfo;

    public SingleTaskDataImpl(ISingleTaskEngine taskEngine) {
        this.taskEngine = taskEngine;
    }

    public void init() throws SingleTaskException {
        this.taskInfo = new SingleTaskInfo();
        ParaInfo paraInfo = new ParaInfo(this.taskEngine.getTaskDir());
        SolutionParser solutionParser = new SolutionParser();
        solutionParser.InitDirName(this.taskEngine.getTaskDir());
        try {
            solutionParser.parse(paraInfo);
        }
        catch (StreamException e) {
            logger.error(e.getMessage(), e);
            throw new SingleTaskException(e.getMessage(), e);
        }
        this.taskInfo.setTaskFlag(paraInfo.getPrefix());
        this.taskInfo.setFileFlag(paraInfo.getFileFlag());
        this.taskInfo.setTaskTitle(paraInfo.getTaskName());
        this.taskInfo.setTaskYear(paraInfo.getTaskYear());
        this.taskInfo.setTaskType(paraInfo.getTaskType());
    }

    @Override
    public void zipToSingleFile(String destFile) throws SingleTaskException {
        SingleFileImpl singleFile = new SingleFileImpl();
        ParaInfo paraInfo = new ParaInfo(this.taskEngine.getTaskDir());
        SolutionParser solutionParser = new SolutionParser();
        solutionParser.InitDirName(this.taskEngine.getTaskDir());
        try {
            solutionParser.parse(paraInfo);
        }
        catch (StreamException e) {
            logger.error(e.getMessage(), e);
            throw new SingleTaskException(e.getMessage(), e);
        }
        try (FileOutputStream outStream = new FileOutputStream(SinglePathUtil.normalize(destFile + "temp.zip"));){
            ZipUtil.zipDirectory(this.taskEngine.getTaskDir(), outStream, null, "GBK");
        }
        catch (SingleFileException | IOException e) {
            logger.error(e.getMessage(), e);
            throw new SingleTaskException(e.getMessage(), e);
        }
        singleFile.getInfo().writeString("General", "Name", paraInfo.getTaskName());
        singleFile.getInfo().writeString("General", "Flag", paraInfo.getPrefix());
        singleFile.getInfo().writeString("General", "FileFlag", paraInfo.getFileFlag());
        singleFile.getInfo().writeString("General", "Year", paraInfo.getTaskYear());
        singleFile.getInfo().writeString("General", "Period", paraInfo.getTaskTime());
        singleFile.getInfo().writeString("General", "Time", paraInfo.getTaskTime());
        singleFile.getInfo().writeString("General", "Version", paraInfo.getTaskVerion());
        singleFile.getInfo().writeString("General", "Group", paraInfo.getTaskVerion());
        singleFile.getInfo().writeString("General", "InputClien", "0");
        singleFile.getInfo().writeString("General", "NetPeriodT", "");
        String taskDataDir = SinglePathUtil.getNewPath(this.taskEngine.getTaskDir(), "DATA");
        String taskParaDir = SinglePathUtil.getNewPath(this.taskEngine.getTaskDir(), "PARA");
        try {
            if (SinglePathUtil.getFileExists(taskDataDir + paraInfo.getFileFlag() + "FMDM.DBF")) {
                singleFile.getInfo().writeString("Data", "QYSJ", "1");
            }
            if (SinglePathUtil.getFileExists(taskDataDir + "Sys_CCSHSM.DBF")) {
                singleFile.getInfo().writeString("Data", "SHSM", "1");
            }
            if (SinglePathUtil.getFileExists(taskDataDir + "SYS_JSHDB.DBF")) {
                singleFile.getInfo().writeString("Data", "HSHD", "1");
            }
            if (SinglePathUtil.getFileExists(taskParaDir + "BBBT.DBF")) {
                singleFile.getInfo().writeString("Data", "PARA", "1");
            }
        }
        catch (SingleFileException e1) {
            logger.error(e1.getMessage(), e1);
            throw new SingleTaskException(e1.getMessage(), e1);
        }
        try {
            singleFile.makeJio(destFile + "temp.zip", destFile);
            SinglePathUtil.deleteFile(destFile + "temp.zip");
        }
        catch (SingleFileException e) {
            logger.error(e.getMessage(), e);
            throw new SingleTaskException(e.getMessage(), e);
        }
    }

    @Override
    public void createDBf(String dbfFile, List<SingleFieldInfo> fields) throws SingleTaskException {
        try {
            if (!SinglePathUtil.getFileExists(dbfFile)) {
                DBFCreator creartor = new DBFCreator();
                for (SingleFieldInfo singleField : fields) {
                    int dbFieldType = 67;
                    if (singleField.getDataType() == SingleDataType.BOOLEAN) {
                        dbFieldType = 76;
                    } else if (singleField.getDataType() == SingleDataType.DATE) {
                        dbFieldType = 84;
                    } else if (singleField.getDataType() == SingleDataType.FLOAT) {
                        dbFieldType = 66;
                    } else if (singleField.getDataType() == SingleDataType.INTEGER) {
                        dbFieldType = 73;
                    } else if (singleField.getDataType() == SingleDataType.STRING) {
                        dbFieldType = 67;
                    } else if (singleField.getDataType() == SingleDataType.CLOB) {
                        dbFieldType = 82;
                    } else if (singleField.getDataType() == SingleDataType.FILE) {
                        dbFieldType = 79;
                    }
                    creartor.addField(singleField.getFieldCode(), (char)dbFieldType, singleField.getPrecision(), singleField.getDecimal());
                }
                creartor.createTable(dbfFile);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SingleTaskException(e.getMessage(), e);
        }
    }

    @Override
    public ISingleTableDataWriter createTableWriter(String tableCode, List<String> fieldNames) throws SingleTaskException {
        String taskDataDir = SinglePathUtil.getNewPath(this.taskEngine.getTaskDir(), "DATA");
        String dbfFile = taskDataDir + this.taskInfo.getFileFlag() + tableCode + ".DBF";
        return this.getTableDataWriterByDbf(dbfFile, fieldNames);
    }

    @Override
    public ISingleTableDataWriter createTableWriter(String tableCode, int floatingId, List<String> fieldNames) throws SingleTaskException {
        String taskDataDir = SinglePathUtil.getNewPath(this.taskEngine.getTaskDir(), "DATA");
        String dbfFile = taskDataDir + this.taskInfo.getFileFlag() + tableCode + ".DBF";
        return this.getTableDataWriterByDbf(dbfFile, fieldNames);
    }

    @Override
    public ISingleTableDataWriter createDbfWriter(String dbfFile, List<String> fieldNames) throws SingleTaskException {
        return this.getTableDataWriterByDbf(dbfFile, fieldNames);
    }

    @Override
    public boolean readTableData(String tableCode, ISingleTableDataHandler handler) throws SingleTaskException {
        String taskDataDir = SinglePathUtil.getNewPath(this.taskEngine.getTaskDir(), "DATA");
        String dbfFile = SinglePathUtil.getNewFilePath(taskDataDir, this.taskInfo.getFileFlag() + tableCode + ".DBF");
        return this.readTableDataByDbf(dbfFile, handler);
    }

    @Override
    public boolean readTableData(String tableCode, int floatingId, ISingleTableDataHandler handler) throws SingleTaskException {
        String taskDataDir = SinglePathUtil.getNewPath(this.taskEngine.getTaskDir(), "DATA");
        String dbfFile = SinglePathUtil.getNewFilePath(taskDataDir, this.taskInfo.getFileFlag() + tableCode + "_F" + floatingId + ".DBF");
        return this.readTableDataByDbf(dbfFile, handler);
    }

    @Override
    public boolean readDbfData(String dbfFile, ISingleTableDataHandler handler) throws SingleTaskException {
        return this.readTableDataByDbf(dbfFile, handler);
    }

    @Override
    public void close() {
    }

    private ISingleTableDataWriter getTableDataWriterByDbf(String dbfFile, List<String> fieldNames) throws SingleTaskException {
        SingleTableDataWriterImpl writer = new SingleTableDataWriterImpl(dbfFile, fieldNames, this);
        return writer;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean readTableDataByDbf(String dbfFile, ISingleTableDataHandler handler) throws SingleTaskException {
        boolean result = false;
        try {
            if (!SinglePathUtil.getFileExists(dbfFile)) {
                logger.info("\u8bfb\u53d6JIO\u4efb\u52a1\u6570\u636e:\u6587\u4ef6\u4e0d\u5b58\u5728\uff1a" + dbfFile);
                return result;
            }
        }
        catch (SingleFileException e1) {
            logger.error(e1.getMessage(), e1);
            throw new SingleTaskException(e1.getMessage(), e1);
        }
        logger.info("\u8bfb\u53d6JIO\u4efb\u52a1\u6570\u636e");
        result = true;
        try (IDbfTable dataDbf = DbfTableUtil.getDbfTable(dbfFile);){
            Metadata<Column> metadata = SingleTaskUtils.getDbfMetadata(dataDbf);
            handler.start(metadata);
            for (int i = 0; i < dataDbf.getDataRowCount(); ++i) {
                DataRow dbfRow = (DataRow)dataDbf.getTable().getRows().get(i);
                if (!dataDbf.isHasLoadAllRec()) {
                    dataDbf.loadDataRow(dbfRow);
                }
                try {
                    MemoryDataRow dataRow = new MemoryDataRow(dataDbf.getTable().getColumns().size());
                    for (int j = 0; j < dataDbf.getTable().getColumns().size(); ++j) {
                        String fieldValue = dbfRow.getValueString(j);
                        dataRow.setString(j, fieldValue);
                    }
                    handler.handle((com.jiuqi.bi.dataset.DataRow)dataRow);
                    continue;
                }
                finally {
                    if (!dataDbf.isHasLoadAllRec()) {
                        dataDbf.clearDataRow(dbfRow);
                    }
                }
            }
            handler.finish();
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SingleTaskException(e.getMessage(), e);
        }
        return result;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<SinglePeriodDataItem> readEntityList(String singlePeriod) throws SingleTaskException {
        ArrayList<SinglePeriodDataItem> result = new ArrayList<SinglePeriodDataItem>();
        HashMap periodMap = new HashMap();
        String taskDataDir = SinglePathUtil.getNewPath(this.taskEngine.getTaskDir(), "DATA");
        String dbfFile = SinglePathUtil.getNewFilePath(taskDataDir, this.taskInfo.getFileFlag() + "FMDM.DBF");
        try {
            if (!SinglePathUtil.getFileExists(dbfFile)) {
                logger.info("\u8bfb\u53d6JIO\u4efb\u52a1\u6570\u636e:\u6587\u4ef6\u4e0d\u5b58\u5728\uff1a" + dbfFile);
                return result;
            }
        }
        catch (SingleFileException e1) {
            logger.error(e1.getMessage(), e1);
            throw new SingleTaskException(e1.getMessage(), e1);
        }
        boolean isOnePeriod = false;
        PeriodType periodType = SingleConsts.ChangepeiodType(this.taskInfo.getTaskType());
        if (periodType == PeriodType.YEAR) {
            isOnePeriod = true;
        }
        SinglePeriodDataItem periodItem = new SinglePeriodDataItem();
        result.add(periodItem);
        logger.info("\u8bfb\u53d6JIO\u4efb\u52a1\u6570\u636e");
        try (IDbfTable dataDbf = DbfTableUtil.getDbfTable(dbfFile);){
            for (int i = 0; i < dataDbf.getDataRowCount(); ++i) {
                DataRow dbfRow = (DataRow)dataDbf.getTable().getRows().get(i);
                if (!dataDbf.isHasLoadAllRec()) {
                    dataDbf.loadDataRow(dbfRow);
                }
                try {
                    SingleEntityDataItem entityItem = new SingleEntityDataItem();
                    entityItem.setZdm(dbfRow.getValueString("SYS_ZDM"));
                    entityItem.setParentZdm(dbfRow.getValueString("SYS_FJD"));
                    periodItem.getEntitys().add(entityItem);
                    continue;
                }
                finally {
                    if (!dataDbf.isHasLoadAllRec()) {
                        dataDbf.clearDataRow(dbfRow);
                    }
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SingleTaskException(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public String getTaskDir() {
        return this.taskEngine.getTaskDir();
    }

    @Override
    public String getTaskParaDir() {
        return SinglePathUtil.getNewPath(this.getTaskDir(), "PARA");
    }

    @Override
    public String getTaskDataDir() {
        return SinglePathUtil.getNewPath(this.getTaskDir(), "DATA");
    }

    @Override
    public String getTaskDataDocDir() {
        return SinglePathUtil.getNewPath(this.getTaskDataDir(), "SYS_DOC");
    }

    @Override
    public String getTaskDataTextDir() {
        return SinglePathUtil.getNewPath(this.getTaskDataDir(), "SYS_TXT");
    }

    @Override
    public String getTaskTempDir() {
        return SinglePathUtil.getNewPath(this.getTaskDir(), "TEMP");
    }

    @Override
    public String getTextFieldValue(com.jiuqi.bi.dataset.DataRow dataRow, int fieldInex) throws SingleTaskException {
        String result;
        block7: {
            result = null;
            String singleFileName = dataRow.getString(fieldInex);
            if (StringUtils.isEmpty((CharSequence)singleFileName)) {
                singleFileName = UUID.randomUUID().toString().replace("-", "") + ".Ini";
            }
            String textFilePath = this.getTaskDataTextDir() + singleFileName;
            try {
                if (!SinglePathUtil.getFileExists(textFilePath)) break block7;
                String textFilePath1 = SinglePathUtil.normalize(textFilePath);
                try {
                    MemStream stream = new MemStream();
                    stream.loadFromFile(textFilePath1);
                    stream.seek(0L, 0);
                    long aSize = stream.getSize();
                    if (stream.getSize() == 0L) {
                        return result;
                    }
                    int count = stream.readInt();
                    if ((long)count > aSize - 4L) {
                        count = (int)aSize - 4;
                    }
                    byte[] data = new byte[count];
                    stream.readBuffer(data, 0, count);
                    result = new String(data, "GB2312");
                }
                catch (Exception ex) {
                    logger.error(ex.getMessage(), ex);
                    throw ex;
                }
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
                throw new SingleTaskException(e.getMessage(), e);
            }
        }
        return result;
    }

    @Override
    public SingleAttachInfo getDocFieldValue(com.jiuqi.bi.dataset.DataRow dataRow, int fieldInex, boolean needUnzip) throws SingleTaskException {
        String[] values;
        String fieldValue;
        SingleAttachInfo result = null;
        String zdm = dataRow.getString(0);
        String fileName = fieldValue = dataRow.getString(fieldInex);
        if (StringUtils.isNotEmpty((CharSequence)fieldValue) && (values = fieldValue.split(";")).length > 0) {
            fileName = values[0];
        }
        result = this.getDocTableValue2(zdm, fileName, needUnzip);
        return result;
    }

    @Override
    public SingleAttachInfo getDocTableValue(String zdm, String tableCode, boolean needUnzip) throws SingleTaskException {
        return this.getDocTableValue2(zdm, tableCode, needUnzip);
    }

    private SingleAttachInfo getDocTableValue2(String zdm, String fileName, boolean needUnzip) throws SingleTaskException {
        SingleAttachInfo result = null;
        String zdmDir = SinglePathUtil.getNewPath(this.getTaskDataDocDir(), zdm);
        if (StringUtils.isNotEmpty((CharSequence)fileName)) {
            try {
                String iniFile;
                result = new SingleAttachInfo();
                String zipFile = SinglePathUtil.getNewFilePath(zdmDir, fileName + ".ZIP");
                HashMap<String, String> fileMap = new HashMap<String, String>();
                if (SinglePathUtil.getFileExists(zipFile)) {
                    result.setZipFilePath(zipFile);
                    if (needUnzip) {
                        String zdmZipTempDir = SinglePathUtil.createNewPath(this.getTaskTempDir(), zdm + OrderGenerator.newOrder());
                        try {
                            ZipUtil.unzipFile(zdmZipTempDir, zipFile, "GBK");
                        }
                        catch (Exception e) {
                            logger.error(e.getMessage(), e);
                            throw e;
                        }
                        result.setUnzipTempPath(zdmZipTempDir);
                        List<String> subFiles = SinglePathUtil.getFileList(zdmZipTempDir, false, null);
                        for (String subFile : subFiles) {
                            String subFielName = SinglePathUtil.getExtractFileName(subFile);
                            fileMap.put(subFielName, subFile);
                        }
                    }
                }
                if (SinglePathUtil.getFileExists(iniFile = SinglePathUtil.getNewFilePath(zdmDir, fileName + ".Ini"))) {
                    result.setIniFilePath(iniFile);
                    Ini ini = new Ini();
                    ini.loadIniFile(iniFile);
                    String countCode = ini.readString(fileName, "Count", "0");
                    int count = Integer.parseInt(countCode);
                    for (int i = 0; i < count; ++i) {
                        String fileNoExt = ini.readString(fileName, i + "_Name", "0");
                        String fileExt = ini.readString(fileName, i + "_Type", "");
                        String fileKey = ini.readString(fileName, i + "_FileKey", "");
                        String category = ini.readString(fileName, i + "_Category", "");
                        String fileSecret = ini.readString(fileName, i + "_SecretLevel", "");
                        String fjFileName = fileNoExt;
                        if (StringUtils.isNotEmpty((CharSequence)fileExt)) {
                            fjFileName = fileExt.startsWith(".") ? fileNoExt + fileExt : fileNoExt + "." + fileExt;
                        }
                        String subFile = null;
                        long fileSize = 0L;
                        if (fileMap.containsKey(fjFileName)) {
                            subFile = (String)fileMap.get(fjFileName);
                            fileSize = SinglePathUtil.getFileSize(subFile);
                        }
                        SingleAttachFileInfo fjInfo = new SingleAttachFileInfo();
                        fjInfo.setName(fileNoExt);
                        fjInfo.setType(fileExt);
                        if (StringUtils.isNotEmpty((CharSequence)fileKey)) {
                            fjInfo.setFileKey(fileKey);
                        }
                        if (StringUtils.isNotEmpty((CharSequence)category)) {
                            fjInfo.setCategory(category);
                        }
                        if (StringUtils.isNotEmpty((CharSequence)fileSecret)) {
                            fjInfo.setFileSecret(fileSecret);
                        }
                        fjInfo.setFilePath(subFile);
                        fjInfo.setSize(fileSize);
                        result.getFiles().add(fjInfo);
                    }
                }
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
                throw new SingleTaskException(e.getMessage(), e);
            }
        }
        return result;
    }

    @Override
    public ISingleTaskParamReader getParamReader() throws SingleTaskException {
        SingleTaskParamReaderImpl paramReader = new SingleTaskParamReaderImpl(this);
        return paramReader;
    }

    @Override
    public SingleTaskInfo getTaskInfo() {
        return this.taskInfo;
    }
}

