/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.nr.single.core.dbf;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.single.core.dbf.DbfCrcCode;
import com.jiuqi.nr.single.core.dbf.DbfCrcCodeManager;
import com.jiuqi.nr.single.core.dbf.DbfException;
import com.jiuqi.nr.single.core.dbf.DbfField;
import com.jiuqi.nr.single.core.dbf.DbfHeader;
import com.jiuqi.nr.single.core.dbf.DbfUtil;
import com.jiuqi.nr.single.core.dbf.IDbfTable;
import com.jiuqi.nr.single.core.util.BitConverter;
import com.jiuqi.nr.single.core.util.SinglePathUtil;
import com.jiuqi.nr.single.core.util.ZipUtil;
import com.jiuqi.nr.single.core.util.datatable.DataColumn;
import com.jiuqi.nr.single.core.util.datatable.DataRow;
import com.jiuqi.nr.single.core.util.datatable.DataTable;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbfTableImpl
implements IDbfTable {
    private static final Logger logger = LoggerFactory.getLogger(DbfTableImpl.class);
    private static final byte DELETEDFLAG = 42;
    private static final byte NODELETEDFLAG = 32;
    private String dbfFileName = null;
    private String encoding = "GBK";
    private RandomAccessFile accessFile = null;
    private long fileSize = 0L;
    private boolean isFileOpened;
    private byte[] recordBuffer;
    private int fieldCount = 0;
    private DbfHeader dbfHeader = null;
    private DbfField[] dbfFields;
    private DbfCrcCode dbCrcCode = null;
    private DataTable dbfDataTable = null;
    private List<Integer> tableRowsFlag;
    private boolean needCheckCRC = true;
    private int recordsDataOffset;
    private int recordLength;
    private boolean hasLoadAllRec = true;
    private boolean needZipData = false;
    private boolean needLoadFile = false;
    private boolean bufferReadWrite = true;
    private boolean batchDataMode = false;
    private SimpleDateFormat dateFormatter;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public DbfTableImpl(String fileName, String enCoding, boolean create, boolean batchMode) throws DbfException {
        try {
            this.dbfFileName = fileName;
            this.encoding = enCoding;
            this.batchDataMode = batchMode;
            this.openDbfFile();
        }
        finally {
            if (!this.needLoadFile) {
                this.closeFileStream();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public DbfTableImpl(String fileName, String enCoding, boolean create) throws DbfException {
        try {
            this.dbfFileName = fileName;
            this.encoding = enCoding;
            this.openDbfFile();
        }
        finally {
            if (!this.needLoadFile) {
                this.closeFileStream();
            }
        }
    }

    public DbfTableImpl(String fileName, String encodingName) throws DbfException {
        this.dbfFileName = fileName.trim();
        this.encoding = encodingName;
        try {
            this.openDbfFile();
        }
        finally {
            if (!this.needLoadFile) {
                this.closeFileStream();
            }
        }
    }

    public DbfTableImpl(String fileName) throws DbfException {
        this.dbfFileName = fileName.trim();
        try {
            this.openDbfFile();
        }
        finally {
            if (!this.needLoadFile) {
                this.closeFileStream();
            }
        }
    }

    @Override
    public long getFileSize() {
        return this.fileSize;
    }

    @Override
    public int getDataRowCount() {
        int count = 0;
        if (null != this.dbfDataTable) {
            count = this.dbfDataTable.getRowCount();
        }
        return count;
    }

    @Override
    public int getDataRealRowCount() {
        int count = 0;
        if (null != this.dbfDataTable) {
            count = this.dbfDataTable.getRowCount();
            if (this.tableRowsFlag != null) {
                int deleteCount = 0;
                for (int a : this.tableRowsFlag) {
                    if (a != 0) continue;
                    ++deleteCount;
                }
                count -= deleteCount;
            }
        }
        return count;
    }

    @Override
    public void saveData() throws DbfException {
        this.writeDbfFile();
    }

    @Override
    public void dispose() throws DbfException {
        this.dispose(true);
    }

    protected void dispose(boolean disposing) throws DbfException {
        if (disposing) {
            this.close();
        }
    }

    @Override
    public void close() throws DbfException {
        this.closeFileStream();
        this.recordBuffer = null;
        this.dbfHeader = null;
        this.dbfFields = null;
        this.dbCrcCode = null;
        this.isFileOpened = false;
        this.fieldCount = 0;
        this.clearAllDataRows();
    }

    @Override
    public void closeFileStream() throws DbfException {
        try {
            if (this.accessFile != null) {
                this.accessFile.close();
                this.accessFile = null;
            }
        }
        catch (Exception e) {
            throw new DbfException(e.getMessage(), e);
        }
    }

    private void openDbfFile() throws DbfException {
        this.close();
        if (StringUtils.isEmpty((String)this.dbfFileName)) {
            throw new DbfException("filename is empty or null.");
        }
        if (!DbfUtil.fileIsExists(this.dbfFileName)) {
            throw new DbfException(this.dbfFileName + " does not exist.");
        }
        try {
            this.getFileStream();
            this.readHeader();
            this.readFields();
            this.getRecordBufferBytes();
            this.createDbfTable();
            this.getDbfRecords();
            this.readCrcCode();
        }
        catch (DbfException e) {
            this.close();
            throw e;
        }
    }

    private void writeDbfFile() throws DbfException {
        if (StringUtils.isEmpty((String)this.dbfFileName)) {
            throw new DbfException("filename is empty or null.");
        }
        try {
            this.getWirteFileStream();
            this.setRecordCount(this.dbfDataTable.getRowCount());
            this.writeHeader();
            this.writeFields();
            this.writeOthers();
            this.getRecordBufferBytes();
            this.setDbfReRecords();
            this.generateCrcCode();
            this.writeCrcCode();
            this.close();
        }
        catch (Exception e) {
            this.close();
            throw e;
        }
    }

    @Override
    public void getFileStream() throws DbfException {
        try {
            if (this.accessFile != null) {
                this.accessFile.close();
                this.accessFile = null;
            }
            File dbfFile = new File(SinglePathUtil.normalize(this.dbfFileName));
            this.accessFile = new RandomAccessFile(dbfFile, "r");
            this.isFileOpened = true;
            this.fileSize = dbfFile.length();
        }
        catch (Exception e) {
            throw new DbfException("fail to read  " + this.dbfFileName + ".");
        }
    }

    @Override
    public void getWirteFileStream() throws DbfException {
        try {
            if (this.accessFile != null) {
                this.accessFile.close();
                this.accessFile = null;
            }
            File dbfFile = new File(SinglePathUtil.normalize(this.dbfFileName));
            this.accessFile = new RandomAccessFile(dbfFile, "rw");
            this.isFileOpened = true;
        }
        catch (Exception e) {
            throw new DbfException("fail to read  " + this.dbfFileName + ".");
        }
    }

    private void readHeader() throws DbfException {
        this.dbfHeader = new DbfHeader();
        try {
            if (this.bufferReadWrite) {
                byte[] headerBuffer = this.readBytes(32);
                this.dbfHeader.setVersion(headerBuffer[0]);
                this.dbfHeader.setLastModifyYear(headerBuffer[1]);
                this.dbfHeader.setLastModifyMonth(headerBuffer[2]);
                this.dbfHeader.setLastModifyDay(headerBuffer[3]);
                byte[] tmp = new byte[4];
                tmp = DbfUtil.copySubBytes(headerBuffer, 4, 4L, tmp);
                this.dbfHeader.setRecordCount(BitConverter.toInt32(tmp, 0));
                tmp = new byte[2];
                tmp = DbfUtil.copySubBytes(headerBuffer, 8, 2L, tmp);
                this.dbfHeader.setHeaderLength(BitConverter.toInt16(tmp, 0));
                tmp = new byte[2];
                tmp = DbfUtil.copySubBytes(headerBuffer, 10, 2L, tmp);
                this.dbfHeader.setRecordLength(BitConverter.toInt16(tmp, 0));
                tmp = new byte[16];
                tmp = DbfUtil.copySubBytes(headerBuffer, 12, 16L, tmp);
                this.dbfHeader.setReserved(tmp);
                this.dbfHeader.setTableFlag(headerBuffer[28]);
                this.dbfHeader.setCodePageFlag(headerBuffer[29]);
                tmp = new byte[2];
                tmp = DbfUtil.copySubBytes(headerBuffer, 30, 2L, tmp);
                this.dbfHeader.setReserved2(tmp);
            } else {
                this.dbfHeader.setVersion(this.readByte());
                this.dbfHeader.setLastModifyYear(this.readByte());
                this.dbfHeader.setLastModifyMonth(this.readByte());
                this.dbfHeader.setLastModifyDay(this.readByte());
                this.dbfHeader.setRecordCount(this.readInt32());
                this.dbfHeader.setHeaderLength(this.readInt16());
                this.dbfHeader.setRecordLength(this.readInt16());
                this.dbfHeader.setReserved(this.readBytes(16));
                this.dbfHeader.setTableFlag(this.readByte());
                this.dbfHeader.setCodePageFlag(this.readByte());
                this.dbfHeader.setReserved2(this.readBytes(2));
            }
            this.fieldCount = this.getFieldCount();
        }
        catch (Exception e) {
            throw new DbfException("fail to write file header.");
        }
    }

    private void writeHeader() throws DbfException {
        try {
            this.accessFile.seek(0L);
            if (this.fieldCount < 2000) {
                this.dbfHeader.setHeaderLength((short)(32 + this.fieldCount * 32 + 1 + 263));
            } else {
                this.dbfHeader.setHeaderLength((short)296);
                this.dbfHeader.setRecordLength((short)this.fieldCount);
            }
            if (this.bufferReadWrite) {
                byte[] headBuffer = new byte[32];
                headBuffer[0] = this.dbfHeader.getVersion();
                headBuffer[1] = this.dbfHeader.getLastModifyYear();
                headBuffer[2] = this.dbfHeader.getLastModifyMonth();
                headBuffer[3] = this.dbfHeader.getLastModifyDay();
                byte[] b = BitConverter.getBytes(this.dbfHeader.getRecordCount());
                DbfUtil.wirteSubBytes(headBuffer, 4, 4L, b);
                b = BitConverter.getBytes(this.dbfHeader.getHeaderLength());
                DbfUtil.wirteSubBytes(headBuffer, 8, 2L, b);
                b = BitConverter.getBytes(this.dbfHeader.getRecordLength());
                DbfUtil.wirteSubBytes(headBuffer, 10, 2L, b);
                b = this.dbfHeader.getReserved();
                DbfUtil.wirteSubBytes(headBuffer, 12, 16L, b);
                headBuffer[28] = this.dbfHeader.getTableFlag();
                headBuffer[29] = this.dbfHeader.getCodePageFlag();
                b = this.dbfHeader.getReserved2();
                DbfUtil.wirteSubBytes(headBuffer, 30, 2L, b);
                this.writeBytes(headBuffer);
            } else {
                this.accessFile.write(this.dbfHeader.getVersion());
                this.accessFile.write(this.dbfHeader.getLastModifyYear());
                this.accessFile.write(this.dbfHeader.getLastModifyMonth());
                this.accessFile.write(this.dbfHeader.getLastModifyDay());
                this.writeInt32(this.dbfHeader.getRecordCount());
                this.writeUInt16(this.dbfHeader.getHeaderLength());
                this.writeUInt16(this.dbfHeader.getRecordLength());
                this.accessFile.write(this.dbfHeader.getReserved());
                this.accessFile.write(this.dbfHeader.getTableFlag());
                this.accessFile.write(this.dbfHeader.getCodePageFlag());
                this.accessFile.write(this.dbfHeader.getReserved2());
            }
        }
        catch (Exception e) {
            throw new DbfException("fail to read file header.");
        }
    }

    private void writeOthers() throws DbfException {
        char[] reservedHead = new char[264];
        Arrays.fill(reservedHead, 0, reservedHead.length, '\u0000');
        int a = 13;
        reservedHead[0] = (char)a;
        String aa = new String(reservedHead);
        try {
            byte[] strByte = aa.getBytes(this.encoding);
            this.writeBytes(strByte);
        }
        catch (Exception e) {
            throw new DbfException(e.getMessage(), e);
        }
    }

    private void readCrcCode() throws DbfException {
        this.dbCrcCode = new DbfCrcCode();
        try {
            this.accessFile.seek(this.accessFile.length() - 16L);
            boolean isFile = true;
            this.dbCrcCode.setVersion(this.readInt32());
            this.dbCrcCode.setPosition(this.readInt32());
            this.dbCrcCode.setCrcCode(this.readInt32());
            this.dbCrcCode.setReserved(this.readInt32());
            this.dbCrcCode.setHasCrcCode(true);
            this.dbCrcCode.setLength(16);
            this.needCheckCRC = this.dbCrcCode.getVersion() == 1147498740;
        }
        catch (Exception e) {
            throw new DbfException("fail to read file Crc.");
        }
    }

    private void generateCrcCode() throws DbfException {
        try {
            Byte[] buffer;
            int dataSize = this.dbfHeader.getHeaderLength2() + this.dbfHeader.getRecordCount() * this.recordLength & 0x3FFFFFFF;
            if (this.fieldCount >= 2000) {
                dataSize = this.recordsDataOffset + this.dbfHeader.getRecordCount() * this.recordLength & 0x3FFFFFFF;
            }
            int offset = (int)Math.round((double)dataSize * (Math.cos(this.dbfHeader.getRecordCount()) + Math.sin(this.recordLength)) / 2.0);
            if (this.needCheckCRC) {
                offset = (int)Math.round((double)dataSize * (Math.sin(this.dbfHeader.getRecordCount()) + Math.cos(this.recordLength)) / 2.0);
            }
            if (offset > dataSize - (buffer = new Byte[4096]).length && (offset = dataSize - buffer.length) < 0) {
                offset = 0;
            }
            if (offset < this.dbfHeader.getHeaderLength2()) {
                // empty if block
            }
            int count = buffer.length;
            if (offset < 0) {
                offset = 0;
                count = 0;
            }
            this.fileSize = dataSize;
            if ((long)count > this.fileSize - (long)offset) {
                count = (int)(this.fileSize - (long)offset);
            }
            if (this.accessFile != null) {
                this.accessFile.seek(offset);
            }
            byte[] buffer2 = this.readBytes(count);
            if (this.needCheckCRC) {
                this.dbCrcCode.setVersion(1147498740);
            } else {
                this.dbCrcCode.setVersion(829530394);
            }
            SecureRandom ran = new SecureRandom();
            ran.setSeed(dataSize);
            this.dbCrcCode.setPosition(ran.nextInt(dataSize));
            this.dbCrcCode.setCrcCode(DbfCrcCodeManager.getCRC32(this.dbfHeader.getHeaderLength2(), buffer2));
            if (this.fieldCount >= 2000) {
                this.dbCrcCode.setCrcCode(DbfCrcCodeManager.getCRC32(this.recordsDataOffset, buffer2));
            }
            this.dbCrcCode.setReserved(0);
            if (this.needCheckCRC) {
                this.dbCrcCode.setHasCrcCode(true);
                this.dbCrcCode.setLength(16);
            }
            this.fileSize = dataSize + 16;
        }
        catch (Exception e) {
            throw new DbfException("fail to read file Crc.");
        }
    }

    private void writeCrcCode() throws DbfException {
        try {
            if (this.dbCrcCode.getLength() == 0 || !this.dbCrcCode.getHasCrcCode()) {
                return;
            }
            long filesize = this.dbfHeader.getHeaderLength2() + this.recordLength * this.dbfHeader.getRecordCount() + this.dbCrcCode.getLength();
            this.accessFile.seek(filesize - (long)this.dbCrcCode.getLength());
            this.accessFile.write(BitConverter.getBytes(this.dbCrcCode.getVersion()));
            this.accessFile.write(BitConverter.getBytes(this.dbCrcCode.getPosition()));
            this.accessFile.write(BitConverter.getBytes(this.dbCrcCode.getCrcCode()));
            this.accessFile.write(BitConverter.getBytes(this.dbCrcCode.getReserved()));
        }
        catch (Exception e) {
            throw new DbfException("fail to read file header.", e);
        }
    }

    private int getFieldCount() throws DbfException {
        int fCount = 0;
        if (this.dbfHeader.getHeaderLength2() == 296) {
            fCount = this.dbfHeader.getRecordLength2();
            this.recordsDataOffset = 33 + fCount * 32 + 263;
            this.recordLength = 0;
        } else {
            fCount = (this.dbfHeader.getHeaderLength2() - 32 - 1 - 263) / 32;
            this.recordsDataOffset = this.dbfHeader.getHeaderLength2();
            this.recordLength = this.dbfHeader.getRecordLength2();
        }
        return fCount;
    }

    private void readFields() throws DbfException {
        this.dbfFields = new DbfField[this.fieldCount];
        try {
            int len = 0;
            for (int k = 0; k < this.fieldCount; ++k) {
                this.dbfFields[k] = new DbfField();
                this.dbfFields[k].setNameBytes(this.readBytes(11));
                byte flag = this.dbfFields[k].getNameBytes()[0];
                if (flag == 13) {
                    this.fieldCount = k;
                    DbfField[] tempdbfFields = this.dbfFields;
                    this.dbfFields = new DbfField[this.fieldCount];
                    System.arraycopy(tempdbfFields, 0, this.dbfFields, 0, this.fieldCount);
                    break;
                }
                if (this.bufferReadWrite) {
                    byte[] fieldBuffer = this.readBytes(21);
                    this.dbfFields[k].setTypeChar(fieldBuffer[0]);
                    byte[] tmp = new byte[4];
                    tmp = DbfUtil.copySubBytes(fieldBuffer, 1, 4L, tmp);
                    this.dbfFields[k].setOffset(BitConverter.toInt32(tmp, 0));
                    this.dbfFields[k].setLength(BitConverter.byteToInt(fieldBuffer[5]));
                    this.dbfFields[k].setPrecision(BitConverter.byteToInt(fieldBuffer[6]));
                    this.dbfFields[k].setFieldSign(fieldBuffer[7]);
                    tmp = new byte[13];
                    tmp = DbfUtil.copySubBytes(fieldBuffer, 8, 13L, tmp);
                    this.dbfFields[k].setReserved(tmp);
                } else {
                    this.dbfFields[k].setTypeChar(this.readByte());
                    this.dbfFields[k].setOffset(this.readInt32());
                    this.dbfFields[k].setLength(BitConverter.byteToInt(this.readByte()));
                    this.dbfFields[k].setPrecision(BitConverter.byteToInt(this.readByte()));
                    this.dbfFields[k].setFieldSign(this.readByte());
                    this.dbfFields[k].setReserved(this.readBytes(13));
                }
                len += this.dbfFields[k].getLength();
            }
            this.recordLength = len + 1;
        }
        catch (Exception e) {
            throw new DbfException("fail to read field information.");
        }
    }

    private void writeFields() throws DbfException {
        try {
            for (int k = 0; k < this.fieldCount; ++k) {
                DbfField field = this.dbfFields[k];
                if (this.bufferReadWrite) {
                    byte[] fieldBuffer = new byte[32];
                    DbfUtil.wirteSubBytes(fieldBuffer, 0, 11L, field.getNameBytes());
                    fieldBuffer[11] = field.getTypeChar();
                    byte[] b = BitConverter.getBytes(field.getOffset());
                    DbfUtil.wirteSubBytes(fieldBuffer, 12, 4L, b);
                    fieldBuffer[16] = BitConverter.intToByte(field.getLength());
                    fieldBuffer[17] = BitConverter.intToByte(field.getPrecision());
                    fieldBuffer[18] = field.getFieldSign();
                    DbfUtil.wirteSubBytes(fieldBuffer, 19, 13L, field.getNameBytes());
                    this.accessFile.write(fieldBuffer, 0, 32);
                    continue;
                }
                this.writeBytes(field.getNameBytes(), 0, 11);
                this.writeByte(field.getTypeChar());
                this.writeBytes(BitConverter.getBytes(field.getOffset()));
                this.writeByte(BitConverter.intToByte(field.getLength()));
                this.writeByte(BitConverter.intToByte(field.getPrecision()));
                this.writeByte(field.getFieldSign());
                this.writeBytes(field.getReserved(), 0, 13);
            }
        }
        catch (Exception e) {
            throw new DbfException("fail to wite field information.");
        }
    }

    private void getRecordBufferBytes() throws DbfException {
        this.recordBuffer = this.fieldCount < 2000 ? new byte[this.dbfHeader.getRecordLength2()] : new byte[this.recordLength];
    }

    private void createDbfTable() throws DbfException {
        if (this.dbfDataTable != null) {
            this.dbfDataTable.Clear();
            this.dbfDataTable = null;
        }
        this.dbfDataTable = new DataTable();
        this.dbfDataTable.setTableName(this.getTableName());
        this.dbfDataTable.getColumns().setHasMapColumName(false);
        try {
            HashMap<String, String> fieldNameMap = new HashMap<String, String>();
            File file = new File(SinglePathUtil.normalize(this.dbfFileName));
            String name = file.getName();
            name = name.replaceAll(".DBF", ".JQM");
            String jqmFileName = file.getParent() + File.separatorChar + name;
            File file2 = new File(jqmFileName);
            if (file2.exists()) {
                try (FileInputStream in = new FileInputStream(jqmFileName);
                     InputStreamReader reader2 = new InputStreamReader(in);
                     BufferedReader bufferedReader = new BufferedReader(reader2);){
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        String[] columArray;
                        if (!StringUtils.isNotEmpty((String)line) || (columArray = line.split("=")).length <= 1 || !StringUtils.isNotEmpty((String)columArray[0]) || !StringUtils.isNotEmpty((String)columArray[1])) continue;
                        fieldNameMap.put(columArray[0], columArray[1]);
                    }
                }
                catch (Exception e) {
                    logger.info(e.getMessage());
                }
                if (fieldNameMap.size() > 0) {
                    this.dbfDataTable.getColumns().setHasMapColumName(true);
                }
            }
            for (int k = 0; k < this.fieldCount; ++k) {
                DataColumn col = new DataColumn();
                String colText = this.dbfFields[k].getFieldName(this.encoding);
                if (StringUtils.isEmpty((String)colText)) {
                    throw new DbfException("the " + (k + 1) + "th column name is null.");
                }
                col.setColumnName(colText);
                col.setCaptionName(colText);
                col.setDataType(this.dbfFields[k].getDataTypeInt());
                col.setDataTypeChar((char)this.dbfFields[k].getTypeChar());
                if (fieldNameMap.containsKey(colText)) {
                    col.setMapColumnName((String)fieldNameMap.get(colText));
                } else {
                    col.setMapColumnName(colText);
                }
                col.setColumnIndex(k);
                col.setPrecision(this.dbfFields[k].getLength());
                col.setDecimal(this.dbfFields[k].getPrecision());
                this.dbfDataTable.getColumns().addColumn(col);
            }
        }
        catch (Exception e) {
            throw new DbfException(e.getMessage(), e);
        }
    }

    @Override
    public void getDbfRecords() throws DbfException {
        try {
            long startPos = 0L;
            startPos = this.fieldCount < 2000 ? (long)this.dbfHeader.getHeaderLength2() : (long)(32 + this.fieldCount * 32 + 1 + 263);
            if (this.accessFile != null) {
                this.accessFile.seek(startPos);
            }
            this.tableRowsFlag = new ArrayList<Integer>();
            this.hasLoadAllRec = this.getRecordCount() * this.fieldCount < 1000000 && !this.batchDataMode;
            this.needZipData = false;
            this.needLoadFile = !this.hasLoadAllRec && this.accessFile != null;
            for (int k = 0; k < this.getRecordCount(); ++k) {
                DataRow row;
                if (this.accessFile != null) {
                    long pos = startPos + (long)k * (long)this.getRecordLength2();
                    this.accessFile.seek(pos);
                }
                if (this.readRecordBuffer(k) != 42) {
                    row = this.dbfDataTable.newRow();
                    if (this.hasLoadAllRec) {
                        for (int i = 0; i < this.fieldCount; ++i) {
                            row.setValue(i, (Object)this.getFieldValue(i));
                        }
                        row.setHasLoaded(true);
                        row.setRecordNo(k);
                    } else {
                        if (this.fieldCount > 0) {
                            row.setValue(0, (Object)this.getFieldValue(0));
                        }
                        row.setHasLoaded(false);
                        row.setRecordNo(k);
                        row.setData(DbfUtil.copySubBytes(this.recordBuffer, 0, this.recordBuffer.length));
                        if (this.needLoadFile) {
                            row.setZipData(null);
                        } else if (this.needZipData) {
                            byte[] zipData = ZipUtil.getZipData(row.getData(), "GB2312", 0);
                            row.setZipData(zipData);
                            row.setData(null);
                        }
                    }
                    this.dbfDataTable.getRows().add(row);
                    this.tableRowsFlag.add(1);
                    continue;
                }
                row = this.dbfDataTable.newRow();
                row.setBufferValid(false);
                this.dbfDataTable.getRows().add(row);
                this.tableRowsFlag.add(0);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DbfException("fail to get dbf table.");
        }
    }

    @Override
    public void loadDataRow(DataRow row) {
        this.loadDataRowByIndexs(row, null);
    }

    @Override
    public void loadDataRowByNames(DataRow row, List<String> fieldNames) {
        if (this.dbfDataTable == null) {
            return;
        }
        ArrayList<Integer> fieldIndexs = new ArrayList<Integer>();
        fieldIndexs.add(0);
        for (String fieldName : fieldNames) {
            DataColumn column = this.dbfDataTable.getColumns().get(fieldName);
            if (column == null) continue;
            fieldIndexs.add(column.getColumnIndex());
        }
        this.loadDataRowByIndexs(row, fieldIndexs);
    }

    @Override
    public void loadDataRowByIndexs(DataRow row, List<Integer> fieldIndexs) {
        if (this.hasLoadAllRec) {
            return;
        }
        if (row.isHasLoaded()) {
            return;
        }
        if (row.getRecordNo() < 0) {
            return;
        }
        try {
            long pos = 0L;
            if (row.getData() == null && row.getZipData() == null) {
                if (this.fieldCount < 2000) {
                    pos = (long)this.dbfHeader.getHeaderLength2() + (long)this.dbfHeader.getRecordLength2() * (long)row.getRecordNo();
                } else {
                    pos = 32 + this.fieldCount * 32 + 1 + 263;
                    pos += (long)this.recordLength * (long)row.getRecordNo();
                }
                if (this.accessFile != null) {
                    try {
                        if (pos + 1L < this.fileSize) {
                            this.accessFile.seek(pos);
                        } else {
                            logger.info("\u8d85\u51fa\u5927\u5c0f\uff1a" + String.valueOf(pos));
                        }
                    }
                    catch (Exception ex) {
                        logger.error(ex.getMessage(), ex);
                    }
                }
                if (this.readRecordBuffer(row.getRecordNo()) != 42) {
                    row.setData(this.recordBuffer);
                }
            } else if (row.getData() == null) {
                if (this.needZipData && row.getZipData() != null) {
                    try {
                        byte[] unZipData = ZipUtil.getUnZipData(row.getZipData(), "GB2312");
                        row.setData(unZipData);
                        this.recordBuffer = DbfUtil.copySubBytes(row.getData(), 0, this.recordBuffer.length, this.recordBuffer);
                    }
                    catch (Exception ex) {
                        logger.error(ex.getMessage(), ex);
                    }
                }
            } else {
                this.recordBuffer = DbfUtil.copySubBytes(row.getData(), 0, this.recordBuffer.length, this.recordBuffer);
            }
            if (row.getItemMap().size() > 0) {
                Object zdm = row.getValue(0);
                row.clearItemValues();
                row.setValue(0, zdm);
            }
            if (fieldIndexs != null && !fieldIndexs.isEmpty()) {
                for (int i = 0; i < fieldIndexs.size(); ++i) {
                    int fieldIndex = fieldIndexs.get(i);
                    row.setValue(fieldIndex, (Object)this.getFieldValue(fieldIndex));
                }
            } else {
                for (int i = 0; i < this.fieldCount; ++i) {
                    row.setValue(i, (Object)this.getFieldValue(i));
                }
            }
            row.setHasLoaded(true);
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    @Override
    public void clearDataRow(DataRow row) {
        this.clearDataRow(row, false);
    }

    @Override
    public void clearDataRow(DataRow row, boolean onlyClumn) {
        if (this.hasLoadAllRec) {
            return;
        }
        if (!row.isHasLoaded()) {
            return;
        }
        if (row.getRecordNo() < 0) {
            return;
        }
        if (row.getItemMap().size() > 1) {
            Object zdm = row.getValue(0);
            row.clearItemValues();
            row.setValue(0, zdm);
        }
        if (this.needLoadFile) {
            if (!onlyClumn) {
                row.setData(null);
            }
            row.setZipData(null);
        } else if (this.needZipData) {
            try {
                byte[] zipData = ZipUtil.getZipData(row.getData(), "GB2312", 9);
                row.setZipData(zipData);
                row.setData(null);
            }
            catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
            }
        }
        row.setHasLoaded(false);
    }

    @Override
    public void clearAllDataRows() {
        for (int k = 0; k < this.getRecordCount(); ++k) {
            this.clearRecordBuffer();
            DataRow row = (DataRow)this.dbfDataTable.getRows().get(k);
            if (this.isHasLoadAllRec()) {
                this.clearDataRow(row);
                row.setData(null);
                continue;
            }
            if (row.getData() == null) continue;
            row.setData(null);
        }
    }

    @Override
    public void saveDataRow(DataRow row) {
        if (this.hasLoadAllRec) {
            return;
        }
        if (row.getRecordNo() < 0) {
            return;
        }
        try {
            this.clearRecordBuffer();
            this.writeDataToRow(row);
            row.setData(DbfUtil.copySubBytes(this.recordBuffer, 0, this.recordBuffer.length, row.getData()));
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    @Override
    public DataRow getRecordByIndex(int index) {
        DataRow result = null;
        if (index < this.getRecordCount() && this.tableRowsFlag.get(index) != 42) {
            result = (DataRow)this.dbfDataTable.getRows().get(index);
        }
        return result;
    }

    @Override
    public void deleteRecordByIndex(int index) {
        if (index < this.getRecordCount()) {
            this.dbfDataTable.getRows().remove(index);
            this.tableRowsFlag.remove(index);
        }
    }

    @Override
    public void setDbfReRecords() throws DbfException {
        try {
            for (int k = 0; k < this.getRecordCount(); ++k) {
                this.clearRecordBuffer();
                DataRow row = (DataRow)this.dbfDataTable.getRows().get(k);
                if (this.isHasLoadAllRec()) {
                    this.writeDataToRow(row);
                } else if (row.getData() != null) {
                    this.recordBuffer = DbfUtil.copySubBytes(row.getData(), 0, this.recordBuffer.length, this.recordBuffer);
                } else if (row.getZipData() != null) {
                    byte[] unZipData = ZipUtil.getUnZipData(row.getZipData(), "GB2312");
                    row.setData(unZipData);
                    this.recordBuffer = DbfUtil.copySubBytes(row.getData(), 0, this.recordBuffer.length, this.recordBuffer);
                    row.setData(null);
                } else if (row.getItemMap() != null && !row.getItemMap().isEmpty()) {
                    this.writeDataToRow(row);
                }
                this.writeRecordBuffer(k);
            }
        }
        catch (Exception e) {
            throw new DbfException(e.getMessage(), e);
        }
    }

    private void writeDataToRow(DataRow row) throws DbfException {
        for (int i = 0; i < this.fieldCount; ++i) {
            if (this.dbfFields[i].getIsLogic()) {
                if (null != row.getValue(i)) {
                    String aValue = row.getValue(i).toString();
                    if (StringUtils.isEmpty((String)aValue)) {
                        this.setFieldValue(i, "N");
                        continue;
                    }
                    if (aValue.equalsIgnoreCase("true")) {
                        this.setFieldValue(i, "Y");
                        continue;
                    }
                    if (aValue.equalsIgnoreCase("false")) {
                        this.setFieldValue(i, "N");
                        continue;
                    }
                    if (aValue.equalsIgnoreCase("\u662f") || aValue.equalsIgnoreCase("T") || aValue.equalsIgnoreCase("1") || aValue.equalsIgnoreCase("Y")) {
                        this.setFieldValue(i, "Y");
                        continue;
                    }
                    if (aValue.equalsIgnoreCase("\u5426") || aValue.equalsIgnoreCase("F") || aValue.equalsIgnoreCase("0") || aValue.equalsIgnoreCase("N")) {
                        this.setFieldValue(i, "N");
                        continue;
                    }
                    if (Boolean.parseBoolean(aValue)) {
                        this.setFieldValue(i, "Y");
                        continue;
                    }
                    this.setFieldValue(i, "N");
                    continue;
                }
                this.setFieldValue(i, "N");
                continue;
            }
            try {
                String value = "";
                if (row.getValue(i) != null) {
                    value = row.getValue(i).toString();
                }
                this.setFieldValue(i, value);
                continue;
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
                throw e;
            }
        }
    }

    private byte readRecordBuffer(int recordIndex) throws DbfException {
        this.recordBuffer = this.fieldCount < 2000 ? this.readBytes(this.recordBuffer, this.dbfHeader.getRecordLength2(), true) : this.readBytes(this.recordBuffer, this.recordLength, true);
        byte deleteFlag = this.recordBuffer[0];
        return deleteFlag;
    }

    private int getRecordLength2() {
        if (this.fieldCount < 2000) {
            return this.dbfHeader.getRecordLength2();
        }
        return this.recordLength;
    }

    private byte readByte() throws DbfException {
        try {
            byte[] b = new byte[1];
            this.accessFile.read(b, 0, 1);
            return b[0];
        }
        catch (Exception e) {
            throw new DbfException(e.getMessage(), e);
        }
    }

    private void writeByte(byte value) throws DbfException {
        try {
            byte[] b = new byte[]{value};
            this.accessFile.write(b, 0, 1);
        }
        catch (Exception e) {
            throw new DbfException(e.getMessage(), e);
        }
    }

    private byte[] readBytes(int len) throws DbfException {
        byte[] b = new byte[len];
        return this.readBytes(b, len);
    }

    private byte[] readBytes(byte[] b, int len) throws DbfException {
        return this.readBytes(b, len, false);
    }

    private byte[] readBytes(byte[] b, int len, boolean accessFileFirst) throws DbfException {
        block11: {
            if (b == null || b.length != len) {
                b = new byte[len];
            }
            try {
                if (len > 2048000000) {
                    int pos1 = 0;
                    int len1 = 1024;
                    int len2 = len;
                    if (len1 > len2) {
                        len1 = len2;
                    }
                    while (len1 > 0) {
                        byte[] tempdata = new byte[len1];
                        this.accessFile.read(tempdata, 0, len1);
                        DbfUtil.wirteSubBytes(b, pos1, len1, tempdata);
                        pos1 += len1;
                        if ((len2 -= len1) != 0) {
                            if (len2 > 1024) {
                                len1 = 1024;
                                continue;
                            }
                            len1 = len2;
                            continue;
                        }
                        break block11;
                    }
                    break block11;
                }
                if (this.accessFile != null && (accessFileFirst || len > 100)) {
                    this.accessFile.read(b, 0, len);
                } else if (this.accessFile != null) {
                    this.accessFile.read(b, 0, len);
                }
            }
            catch (Exception e) {
                throw new DbfException(e.getMessage(), e);
            }
        }
        return b;
    }

    private void writeBytes(byte[] value) throws DbfException {
        try {
            this.accessFile.write(value);
        }
        catch (Exception e) {
            throw new DbfException(e.getMessage(), e);
        }
    }

    private void writeBytes(byte[] value, int len) throws DbfException {
        try {
            this.accessFile.write(value, 0, len);
        }
        catch (Exception e) {
            throw new DbfException(e.getMessage(), e);
        }
    }

    private void writeBytes(byte[] value, int offset, int len) throws DbfException {
        try {
            this.accessFile.write(value, offset, len);
        }
        catch (Exception e) {
            throw new DbfException(e.getMessage(), e);
        }
    }

    private int readInt32() throws DbfException {
        try {
            byte[] b = new byte[4];
            this.accessFile.read(b, 0, 4);
            return BitConverter.toInt32(b, 0);
        }
        catch (Exception e) {
            throw new DbfException(e.getMessage(), e);
        }
    }

    private void writeInt32(int value) throws DbfException {
        try {
            byte[] b = BitConverter.getBytes(value);
            this.accessFile.write(b, 0, 4);
        }
        catch (Exception e) {
            throw new DbfException(e.getMessage(), e);
        }
    }

    private short readUInt16() throws DbfException {
        try {
            byte[] b = new byte[2];
            this.accessFile.read(b, 0, 2);
            return (short)BitConverter.toUInt16(b, 0);
        }
        catch (Exception e) {
            throw new DbfException(e.getMessage(), e);
        }
    }

    private void writeUInt16(short value) throws DbfException {
        try {
            byte[] b = BitConverter.getBytes(value);
            this.accessFile.write(b, 0, 2);
        }
        catch (Exception e) {
            throw new DbfException(e.getMessage(), e);
        }
    }

    private short readInt16() throws DbfException {
        try {
            byte[] b = new byte[2];
            this.accessFile.read(b, 0, 2);
            return BitConverter.toInt16(b, 0);
        }
        catch (Exception e) {
            throw new DbfException(e.getMessage(), e);
        }
    }

    private void writeInt16(short value) throws DbfException {
        try {
            byte[] b = BitConverter.getBytes(value);
            this.accessFile.write(b, 0, 2);
        }
        catch (Exception e) {
            throw new DbfException(e.getMessage(), e);
        }
    }

    private void writeRecordBuffer(int recordIndex) throws IOException {
        this.recordBuffer[0] = 32;
        if (this.fieldCount < 2000) {
            this.accessFile.write(this.recordBuffer, 0, this.dbfHeader.getRecordLength2());
        } else {
            this.accessFile.write(this.recordBuffer, 0, this.recordLength);
        }
    }

    private void clearRecordBuffer() {
        for (int i = 1; i < this.recordBuffer.length; ++i) {
            this.recordBuffer[i] = 0;
        }
    }

    private String getFieldValue(int fieldIndex) throws Exception {
        String code;
        byte[] tmp;
        String fieldValue = null;
        DbfField field = this.dbfFields[fieldIndex];
        int offset = field.getDataOffset();
        if (offset < 0) {
            offset = 1;
            for (int i = 0; i < fieldIndex; ++i) {
                offset += this.dbfFields[i].getLength2();
            }
            field.setDataOffset(offset);
        }
        if ((tmp = field.getDataBytes()) == null) {
            tmp = new byte[field.getLength2()];
            field.setDataBytes(tmp);
        }
        Arrays.fill(tmp, (byte)0);
        tmp = DbfUtil.copySubBytes(this.recordBuffer, offset, field.getLength2(), tmp);
        if (this.dbfFields[fieldIndex].getIsInt()) {
            int val = BitConverter.toInt32(tmp, 0);
            BigDecimal bg = new BigDecimal(val);
            BigDecimal bg1 = bg.setScale(0, 4);
            fieldValue = bg1.toPlainString();
        } else if (this.dbfFields[fieldIndex].getIsDouble()) {
            Double val = BitConverter.toDouble(tmp, 0);
            BigDecimal bg = new BigDecimal(val);
            BigDecimal bg1 = bg.setScale(this.dbfFields[fieldIndex].getPrecision(), 4);
            fieldValue = bg1.toPlainString();
        } else if (this.dbfFields[fieldIndex].getIsMoney()) {
            long val = BitConverter.toInt64(tmp, 0);
            fieldValue = String.valueOf((float)val / 10000.0f);
        } else if (this.dbfFields[fieldIndex].getIsDate()) {
            Date date = DbfUtil.toDate(tmp, this.encoding);
            fieldValue = this.getDateFormatter().format(date);
        } else if (this.dbfFields[fieldIndex].getIsTime()) {
            Date date;
            Date time = DbfUtil.ToTime(tmp);
            fieldValue = time.equals(date = DbfUtil.INITLASTDATE) ? "" : this.getDateFormatter().format(time);
        } else {
            fieldValue = new String(tmp, this.encoding);
            if ("GB2312".equals(this.encoding)) {
                fieldValue = new String(tmp, "GBK");
            }
        }
        fieldValue = fieldValue.startsWith(" ") ? ((code = fieldValue.trim()).length() > 0 ? fieldValue.substring(0, fieldValue.indexOf(code)) + code : code) : fieldValue.trim();
        if (this.dbfFields[fieldIndex].getIsNumber() || this.dbfFields[fieldIndex].getIsFloat()) {
            if (fieldValue.length() == 0) {
                fieldValue = "0";
            } else if (fieldValue == ".") {
                fieldValue = "0";
            } else {
                float val = 0.0f;
                val = Float.parseFloat(fieldValue);
                fieldValue = String.valueOf(val);
            }
        } else if (this.dbfFields[fieldIndex].getIsLogic()) {
            fieldValue = "T".equalsIgnoreCase(fieldValue) || "Y".equalsIgnoreCase(fieldValue) || "1".equalsIgnoreCase(fieldValue) || "true".equalsIgnoreCase(fieldValue) || "\u662f".equalsIgnoreCase(fieldValue) ? "true" : "false";
        } else if (this.dbfFields[fieldIndex].getIsDate() || this.dbfFields[fieldIndex].getIsTime()) {
            // empty if block
        }
        return fieldValue;
    }

    private void setFieldValue(int fieldIndex, String fieldValue) throws DbfException {
        int offset = 1;
        for (int i = 0; i < fieldIndex; ++i) {
            offset += this.dbfFields[i].getLength2();
        }
        byte[] tmp = new byte[this.dbfFields[fieldIndex].getLength2()];
        if (this.dbfFields[fieldIndex].getIsInt()) {
            if (StringUtils.isEmpty((String)fieldValue) || "0.0".equalsIgnoreCase(fieldValue)) {
                fieldValue = "0";
            }
            int val = 0;
            try {
                val = Integer.parseInt(fieldValue);
            }
            catch (Exception ex) {
                try {
                    double val2 = Double.parseDouble(fieldValue);
                    val = (int)val2;
                }
                catch (Exception ex2) {
                    val = 0;
                    fieldValue = "0";
                    logger.error(ex2.getMessage(), ex2);
                }
            }
            tmp = BitConverter.getBytes(val);
        } else if (this.dbfFields[fieldIndex].getIsDouble()) {
            if (StringUtils.isEmpty((String)fieldValue)) {
                fieldValue = "0";
            } else if (fieldValue.contains(",")) {
                fieldValue = fieldValue.replace(",", "");
            }
            double val = 0.0;
            try {
                val = Double.parseDouble(fieldValue);
            }
            catch (Exception ex) {
                val = 0.0;
                fieldValue = "0";
                logger.error(ex.getMessage(), ex);
            }
            tmp = BitConverter.getBytes2(val);
        } else if (this.dbfFields[fieldIndex].getIsMoney()) {
            if (StringUtils.isEmpty((String)fieldValue)) {
                fieldValue = "0";
            } else if (fieldValue.contains(",")) {
                fieldValue = fieldValue.replace(",", "");
            }
            float aa = 0.0f;
            try {
                aa = Float.parseFloat(fieldValue);
            }
            catch (Exception ex) {
                aa = 0.0f;
                fieldValue = "0";
                logger.error(ex.getMessage(), ex);
            }
            long val = (long)aa * 10000L;
            tmp = BitConverter.getBytes(val);
        } else if (this.dbfFields[fieldIndex].getIsDate()) {
            if (StringUtils.isEmpty((String)fieldValue)) {
                for (int i = 0; i < this.dbfFields[fieldIndex].getLength2(); ++i) {
                    fieldValue = fieldValue + " ";
                }
                tmp = this.getStringBytes(fieldValue, this.encoding);
            } else {
                Date date = DbfUtil.INITLASTDATE;
                try {
                    date = this.getDateFormatter().parse(fieldValue);
                }
                catch (Exception ex) {
                    date = DbfUtil.INITLASTDATE;
                    logger.error(ex.getMessage(), ex);
                }
                tmp = DbfUtil.formDate(date, tmp.length, this.encoding);
            }
        } else if (this.dbfFields[fieldIndex].getIsTime()) {
            if (StringUtils.isEmpty((String)fieldValue)) {
                Date date = DbfUtil.INITLASTDATE;
                tmp = DbfUtil.formTime(date, tmp.length);
            } else {
                try {
                    Date date = this.getDateFormatter().parse(fieldValue);
                    tmp = DbfUtil.formTime(date, tmp.length);
                }
                catch (Exception ex) {
                    Date date = DbfUtil.INITLASTDATE;
                    tmp = DbfUtil.formTime(date, tmp.length);
                }
            }
        } else if (this.dbfFields[fieldIndex].getIsString()) {
            byte[] temp2 = this.getStringBytes(fieldValue, this.encoding);
            if ("GB2312".equals(this.encoding)) {
                temp2 = this.getStringBytes(fieldValue, "GBK");
            }
            Arrays.fill(tmp, (byte)32);
            int len = temp2.length;
            if (len > tmp.length) {
                len = tmp.length;
            }
            if (temp2.length > 0) {
                System.arraycopy(temp2, 0, tmp, 0, len);
            }
        } else if (this.dbfFields[fieldIndex].getIsFile() || this.dbfFields[fieldIndex].getIsBlob()) {
            byte[] temp2 = this.getStringBytes(fieldValue, this.encoding);
            if ("GB2312".equals(this.encoding)) {
                temp2 = this.getStringBytes(fieldValue, "GBK");
            }
            Arrays.fill(tmp, (byte)32);
            int len = temp2.length;
            if (len > tmp.length) {
                len = tmp.length;
            }
            if (temp2.length > 0) {
                System.arraycopy(temp2, 0, tmp, 0, len);
            }
        } else {
            tmp = this.getStringBytes(fieldValue, this.encoding);
        }
        if (!this.dbfFields[fieldIndex].getIsNumber() && !this.dbfFields[fieldIndex].getIsFloat()) {
            if (this.dbfFields[fieldIndex].getIsLogic()) {
                fieldValue = StringUtils.isEmpty((String)fieldValue) ? "N" : (fieldValue.equalsIgnoreCase("false") || fieldValue.equalsIgnoreCase("N") || fieldValue == "0" || fieldValue.equalsIgnoreCase("F") || fieldValue == "\u5426" ? "N" : (fieldValue.equalsIgnoreCase("true") || fieldValue.equalsIgnoreCase("Y") || fieldValue == "1" || fieldValue.equalsIgnoreCase("T") || fieldValue == "\u662f" ? "Y" : "N"));
                tmp = this.getStringBytes(fieldValue, this.encoding);
            } else if (this.dbfFields[fieldIndex].getIsDate() || this.dbfFields[fieldIndex].getIsTime()) {
                // empty if block
            }
        }
        DbfUtil.wirteSubBytes(this.recordBuffer, offset, this.dbfFields[fieldIndex].getLength2(), tmp);
    }

    private byte[] getStringBytes(String fieldValue, String ecoding) throws DbfException {
        try {
            return fieldValue.getBytes(ecoding);
        }
        catch (Exception e) {
            throw new DbfException(e.getMessage(), e);
        }
    }

    @Override
    public String getTableName() {
        return "";
    }

    @Override
    public void setFileName(String fileName) {
        this.dbfFileName = fileName;
    }

    @Override
    public String getEncoding() {
        return this.encoding;
    }

    @Override
    public int getRecordLength() {
        if (!this.getIsFileOpened()) {
            return 0;
        }
        return this.dbfHeader.getRecordLength2();
    }

    @Override
    public int getFieldCount2() {
        if (!this.getIsFileOpened()) {
            return 0;
        }
        return this.dbfFields.length;
    }

    @Override
    public int getRecordCount() {
        if (!this.getIsFileOpened() || this.dbfHeader == null) {
            return 0;
        }
        return this.dbfHeader.getRecordCount();
    }

    @Override
    public void setRecordCount(int value) {
        this.dbfHeader.setRecordCount(value);
    }

    @Override
    public boolean getIsFileOpened() {
        return this.isFileOpened;
    }

    @Override
    public DataTable getTable() {
        if (!this.isFileOpened) {
            return null;
        }
        return this.dbfDataTable;
    }

    @Override
    public DbfField[] geDbfFields() {
        if (!this.isFileOpened) {
            return null;
        }
        return this.dbfFields;
    }

    @Override
    public boolean getNeedCheckCRC() {
        return this.needCheckCRC;
    }

    @Override
    public void setNeedCheckCRC(boolean value) {
        this.needCheckCRC = value;
    }

    @Override
    public void moveFirst() {
    }

    @Override
    public boolean isHasLoadAllRec() {
        return this.hasLoadAllRec;
    }

    @Override
    public void setHasLoadAllRec(boolean hasLoadAllRec) {
        this.hasLoadAllRec = hasLoadAllRec;
    }

    public SimpleDateFormat getDateFormatter() {
        if (this.dateFormatter == null) {
            this.dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        }
        return this.dateFormatter;
    }

    @Override
    public int getFieldIndex(String fieldName) {
        int fieldIndex = -1;
        DataColumn column = this.dbfDataTable.getColumns().get(fieldName);
        if (column != null) {
            fieldIndex = column.getColumnIndex();
        }
        return fieldIndex;
    }

    @Override
    public List<Integer> getFieldIndexs(List<String> fieldNames) {
        ArrayList<Integer> fieldIndexs = new ArrayList<Integer>();
        for (String fieldName : fieldNames) {
            int fieldIndex = this.getFieldIndex(fieldName);
            if (fieldIndex < 0) continue;
            fieldIndexs.add(fieldIndex);
        }
        return fieldIndexs;
    }
}

