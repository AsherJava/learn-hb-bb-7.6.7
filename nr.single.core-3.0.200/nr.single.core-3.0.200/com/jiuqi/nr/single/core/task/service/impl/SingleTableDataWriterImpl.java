/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.Metadata
 *  com.jiuqi.bi.util.MemStream
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.nr.single.core.task.service.impl;

import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.util.MemStream;
import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.single.core.dbf.DbfException;
import com.jiuqi.nr.single.core.dbf.DbfTableUtil;
import com.jiuqi.nr.single.core.dbf.IDbfTable;
import com.jiuqi.nr.single.core.exception.SingleFileException;
import com.jiuqi.nr.single.core.para.ini.Ini;
import com.jiuqi.nr.single.core.task.SingleTaskException;
import com.jiuqi.nr.single.core.task.service.ISingleTableDataWriter;
import com.jiuqi.nr.single.core.task.service.ISingleTaskData;
import com.jiuqi.nr.single.core.task.service.impl.SingleTaskDataImpl;
import com.jiuqi.nr.single.core.task.util.SingleTaskUtils;
import com.jiuqi.nr.single.core.util.SinglePathUtil;
import com.jiuqi.nr.single.core.util.SingleSecurityUtils;
import com.jiuqi.nr.single.core.util.ZipUtil;
import com.jiuqi.nr.single.core.util.datatable.DataColumn;
import com.jiuqi.nr.single.core.util.datatable.DataRow;
import java.io.FileOutputStream;
import java.util.List;
import java.util.UUID;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SingleTableDataWriterImpl
implements ISingleTableDataWriter {
    private static final Logger logger = LoggerFactory.getLogger(SingleTaskDataImpl.class);
    private Metadata<Column> metadata;
    private IDbfTable dataDbf;
    private String dbfFile;
    private String dataDir;
    private String tempDir;
    private String textDir;
    private String docDir;
    private ISingleTaskData taskData;

    public SingleTableDataWriterImpl(String dbfFile) throws SingleTaskException {
        this(dbfFile, null, null);
    }

    public SingleTableDataWriterImpl(String dbfFile, List<String> fieldNames, ISingleTaskData taskData) throws SingleTaskException {
        this.dbfFile = dbfFile;
        this.taskData = taskData;
        try {
            if (taskData != null) {
                this.dataDir = taskData.getTaskDataDir();
                this.textDir = taskData.getTaskDataTextDir();
                this.docDir = taskData.getTaskDataDocDir();
                this.tempDir = taskData.getTaskTempDir();
            } else {
                this.dataDir = SinglePathUtil.getExtractPath(dbfFile);
                this.textDir = SinglePathUtil.createNewPath(this.dataDir, "SYS_TXT");
                this.docDir = SinglePathUtil.createNewPath(this.dataDir, "SYS_DOC");
                this.tempDir = SinglePathUtil.createNewPath(this.dataDir, "SYS_TEMP");
            }
        }
        catch (SingleFileException e1) {
            logger.error(e1.getMessage(), e1);
            throw new SingleTaskException(e1.getMessage(), e1);
        }
        this.metadata = new Metadata();
        try {
            this.dataDbf = DbfTableUtil.getDbfTable(dbfFile);
            Metadata metadata = new Metadata();
            if (fieldNames != null) {
                for (int j = 0; j < fieldNames.size(); ++j) {
                    DataColumn column = this.dataDbf.getTable().getColumns().get(fieldNames.get(j));
                    Column newColumn = SingleTaskUtils.getDbfFieldColumn(column);
                    metadata.addColumn(newColumn);
                }
            } else {
                for (int j = 0; j < this.dataDbf.getTable().getColumns().size(); ++j) {
                    DataColumn column = (DataColumn)this.dataDbf.getTable().getColumns().get(j);
                    Column newColumn = SingleTaskUtils.getDbfFieldColumn(column);
                    metadata.addColumn(newColumn);
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SingleTaskException(e.getMessage(), e);
        }
    }

    @Override
    public Metadata getMetaData() {
        return this.metadata;
    }

    @Override
    public void insert(com.jiuqi.bi.dataset.DataRow dataRow) throws SingleTaskException {
        if (this.dataDbf == null) {
            return;
        }
        if (this.metadata == null) {
            return;
        }
        DataRow dbfRow = null;
        try {
            dbfRow = this.dataDbf.getTable().newRow();
        }
        catch (DbfException e) {
            logger.error(e.getMessage(), e);
            throw new SingleTaskException(e.getMessage(), e);
        }
        if (dbfRow == null) {
            return;
        }
        for (int i = 0; i < this.metadata.getColumnCount(); ++i) {
            Column column = this.metadata.getColumn(i);
            String fieldValuie = dataRow.getString(i);
            dbfRow.setValue(column.getName(), (Object)fieldValuie);
        }
        this.dataDbf.getTable().getRows().add(dbfRow);
    }

    @Override
    public void insert(List<com.jiuqi.bi.dataset.DataRow> dataRows) throws SingleTaskException {
        for (com.jiuqi.bi.dataset.DataRow row : dataRows) {
            this.insert(row);
        }
    }

    @Override
    public void insert(Object[] values) throws SingleTaskException {
        if (this.dataDbf == null) {
            return;
        }
        if (this.metadata == null) {
            return;
        }
        DataRow dbfRow = null;
        try {
            dbfRow = this.dataDbf.getTable().newRow();
        }
        catch (DbfException e) {
            logger.error(e.getMessage(), e);
        }
        if (dbfRow == null) {
            return;
        }
        for (int i = 0; i < this.metadata.getColumnCount(); ++i) {
            Column column = this.metadata.getColumn(i);
            String fieldValuie = (String)values[i];
            dbfRow.setValue(column.getName(), (Object)fieldValuie);
        }
        this.dataDbf.getTable().getRows().add(dbfRow);
    }

    @Override
    public void close() throws SingleTaskException {
        if (this.dataDbf != null) {
            try {
                this.dataDbf.saveData();
                this.dataDbf.close();
            }
            catch (DbfException e) {
                logger.error(e.getMessage(), e);
                throw new SingleTaskException(e.getMessage(), e);
            }
        }
        if (this.taskData == null) {
            try {
                SinglePathUtil.deleteDir(this.tempDir);
            }
            catch (SingleFileException e) {
                logger.error(e.getMessage(), e);
                throw new SingleTaskException(e.getMessage(), e);
            }
        }
    }

    @Override
    public void setTextFieldValue(com.jiuqi.bi.dataset.DataRow dataRow, int fieldInex, String fieldValue) throws SingleTaskException {
        String fileName = dataRow.getString(fieldInex);
        if (StringUtils.isEmpty((String)fileName)) {
            fileName = UUID.randomUUID().toString().replace("-", "");
            dataRow.setString(fieldInex, fileName);
        }
        String textFilePath = this.textDir + fileName;
        try {
            if (SinglePathUtil.getFileExists(textFilePath)) {
                SinglePathUtil.deleteFile(textFilePath);
            }
        }
        catch (SingleFileException e) {
            logger.info("\u5220\u9664\u6587\u4ef6\u5931\u8d25\uff1a" + textFilePath);
            throw new SingleTaskException(e.getMessage(), e);
        }
        try {
            if (StringUtils.isEmpty((String)fieldValue)) {
                return;
            }
            String textFilePath1 = FilenameUtils.normalize(textFilePath);
            SingleSecurityUtils.validatePathManipulation(textFilePath1);
            MemStream stream = new MemStream();
            stream.seek(0L, 0);
            byte[] data = fieldValue.getBytes("GB2312");
            int aSize = data.length;
            stream.writeInt(aSize);
            stream.writeBuffer(data, 0, aSize);
            stream.saveToFile(textFilePath1);
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new SingleTaskException(ex.getMessage(), ex);
        }
    }

    @Override
    public void setDocFieldValue(com.jiuqi.bi.dataset.DataRow dataRow, int fieldInex, List<String> docFiles) throws SingleTaskException {
        String[] values;
        String fieldValue = dataRow.getString(fieldInex);
        String zdm = dataRow.getString(0);
        if (StringUtils.isEmpty((String)zdm)) {
            return;
        }
        if (docFiles == null || docFiles.size() == 0) {
            return;
        }
        String fileName = fieldValue;
        fileName = StringUtils.isEmpty((String)fieldValue) ? UUID.randomUUID().toString().replace("-", "") : ((values = fieldValue.split(";")).length > 0 ? values[0] : UUID.randomUUID().toString().replace("-", ""));
        fieldValue = fileName + ";" + String.valueOf(docFiles.size());
        dataRow.setString(fieldInex, fieldValue);
        try {
            String zdmDir = SinglePathUtil.createNewPath(this.docDir, zdm);
            String docZipFile = SinglePathUtil.getNewFilePath(zdmDir, fileName + ".ZIP");
            String docIniFile = SinglePathUtil.getNewFilePath(zdmDir, fileName + ".Ini");
            String zdmZipTempDir = SinglePathUtil.createNewPath(this.tempDir, zdm + OrderGenerator.newOrder());
            Ini ini = new Ini();
            ini.WriteString(fileName, "Count", String.valueOf(docFiles.size()));
            for (int i = 0; i < docFiles.size(); ++i) {
                String docFile = docFiles.get(i);
                String subFileName = SinglePathUtil.getExtractFileName(docFile);
                String subName = SinglePathUtil.getFileNoExtensionName(subFileName);
                String subFileExt = SinglePathUtil.getFileExtensionName(subFileName);
                long subFileSize = SinglePathUtil.getFileSize(docFile);
                String newDocFile = SinglePathUtil.getNewFilePath(zdmZipTempDir, subFileName);
                SinglePathUtil.copyFile(docFile, newDocFile);
                ini.WriteString(fileName, i + "_Name", subName);
                ini.WriteString(fileName, i + "_Type", subFileExt);
                ini.WriteString(fileName, i + "_FileSize", String.valueOf(subFileSize));
            }
            ini.saveIni(docIniFile);
            docZipFile = SinglePathUtil.normalize(docZipFile);
            try (FileOutputStream outStream = new FileOutputStream(docZipFile);){
                ZipUtil.zipDirectory(zdmZipTempDir, outStream, null, "GBK");
            }
            SinglePathUtil.deleteDir(zdmZipTempDir);
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new SingleTaskException(ex.getMessage(), ex);
        }
    }
}

