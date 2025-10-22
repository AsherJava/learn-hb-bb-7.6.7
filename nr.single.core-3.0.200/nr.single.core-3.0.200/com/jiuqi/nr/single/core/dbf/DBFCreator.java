/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.MemStream
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.nr.single.core.dbf;

import com.jiuqi.bi.util.MemStream;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.single.core.dbf.DbfException;
import com.jiuqi.nr.single.core.dbf.DbfField;
import com.jiuqi.nr.single.core.dbf.DbfHeader;
import com.jiuqi.nr.single.core.dbf.DbfUtil;
import com.jiuqi.nr.single.core.exception.SingleFileException;
import com.jiuqi.nr.single.core.util.BitConverter;
import com.jiuqi.nr.single.core.util.SinglePathUtil;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DBFCreator {
    private List<DbfField> dbfFields = new ArrayList<DbfField>();
    private short recordLength = 0;
    private List<String> fieldNames2 = new ArrayList<String>();
    private Map<String, DbfField> fieldCach = new LinkedHashMap<String, DbfField>();
    private boolean needSave = false;
    private String encoding = "GB2312";
    private DbfHeader dbfHeader = null;
    private boolean bufferReadWrite = true;

    public DBFCreator() {
        this.dbfFields = new ArrayList<DbfField>();
        this.fieldNames2 = new ArrayList<String>();
    }

    public int addField(String fieldName, char dataType, int size) throws DbfException {
        return this.addField(fieldName, dataType, size, 0);
    }

    public int addField(String fieldName, char dataType, int size, int dec) throws DbfException {
        int fieldnum;
        if (StringUtils.isEmpty((String)fieldName)) {
            return -1;
        }
        if (this.dbfFields == null) {
            this.dbfFields = new ArrayList<DbfField>();
        }
        if ((fieldnum = this.dbfFields.size()) > 50000) {
            throw new DbfException("dbfCreate:\u5b57\u6bb5\u6570\u76ee\u592a\u591a\uff0c\u65e0\u6cd5\u521b\u5efa\u6570\u636e\u5e93");
        }
        DbfField field = new DbfField();
        String str = "";
        if (fieldName.length() > 10) {
            str = "F_" + String.valueOf(this.dbfFields.size());
            field.setFieldName(str);
            str = str + '=' + fieldName.toUpperCase();
            this.needSave = true;
        } else {
            field.setFieldName(fieldName.toUpperCase());
            str = fieldName.toUpperCase() + '=' + fieldName.toUpperCase();
        }
        this.fieldNames2.add(str);
        this.fieldCach.put(fieldName.toUpperCase(), field);
        field.setDataType(dataType);
        switch (dataType) {
            case 'C': {
                if (size == 0) {
                    size = 1;
                }
                dec = 0;
                break;
            }
            case 'D': 
            case 'T': {
                size = 8;
                dec = 0;
                break;
            }
            case 'F': 
            case 'N': {
                if (size == 0) {
                    size = 10;
                }
                if (size > 20) {
                    size = 20;
                }
                if (dec < size) break;
                size = 1;
                dec = 1;
                break;
            }
            case 'B': {
                size = 8;
                if (dec <= 16) break;
                dec = 0;
                break;
            }
            case 'Y': {
                size = 8;
                if (dec <= 4) break;
                dec = 0;
                break;
            }
            case 'I': {
                size = 4;
                dec = 0;
                break;
            }
            case 'L': {
                size = 1;
                dec = 0;
                break;
            }
            case 'G': 
            case 'O': 
            case 'R': {
                size = 40;
                dec = 0;
                break;
            }
            default: {
                throw new DbfException("dbfCreate:\u5b57\u6bb5\u7c7b\u578b\u65e0\u6548\uff0c\u65e0\u6cd5\u521b\u5efa\u6570\u636e\u5e93");
            }
        }
        field.setLength(size);
        field.setPrecision(dec);
        field.setOffset(this.recordLength + 1);
        this.recordLength = (short)(this.recordLength + size);
        this.dbfFields.add(field);
        return fieldnum;
    }

    public void createTable(String fileName) throws DbfException {
        try (FileOutputStream destfileStream = new FileOutputStream(SinglePathUtil.normalize(fileName));
             BufferedOutputStream binaryWriter = new BufferedOutputStream(destfileStream);){
            Calendar calendar = Calendar.getInstance();
            this.dbfHeader = new DbfHeader();
            this.dbfHeader.setVersion((byte)48);
            this.dbfHeader.setLastModifyYear((byte)(calendar.get(1) - 1900));
            this.dbfHeader.setLastModifyMonth((byte)(calendar.get(2) + 1));
            this.dbfHeader.setLastModifyDay((byte)calendar.get(5));
            if (this.dbfFields.size() < 2000) {
                this.dbfHeader.setHeaderLength((short)(32 + this.dbfFields.size() * 32 + 1 + 263));
                this.dbfHeader.setRecordLength((short)(this.recordLength + 1));
            } else {
                this.dbfHeader.setHeaderLength((short)296);
                this.dbfHeader.setRecordLength((short)this.dbfFields.size());
            }
            this.writeHeader(binaryWriter);
            this.writeFields(binaryWriter);
            char[] reservedHead = new char[264];
            Arrays.fill(reservedHead, 0, reservedHead.length, '0');
            int a = 13;
            reservedHead[0] = (char)a;
            String aa = new String(reservedHead);
            byte[] strByte = aa.getBytes(this.encoding);
            binaryWriter.write(strByte);
            binaryWriter.flush();
        }
        catch (SingleFileException | IOException e1) {
            throw new DbfException(e1.getMessage(), e1);
        }
        if (this.needSave) {
            try {
                File file = new File(SinglePathUtil.normalize(fileName));
                String name = file.getName();
                name = name.replaceAll(".DBF", ".JQM");
                String jqmFileName = file.getParent() + File.separatorChar + name;
                StringBuilder sp = new StringBuilder();
                for (String line : this.fieldNames2) {
                    sp.append(line).append('\r').append('\n');
                }
                String content = sp.toString();
                MemStream stream = new MemStream();
                stream.seek(0L, 0);
                stream.setUseEncode(true);
                stream.writeString(content);
                stream.saveToFile(jqmFileName);
            }
            catch (Exception e) {
                throw new DbfException(e.getMessage(), e);
            }
        }
    }

    private void writeHeader(BufferedOutputStream binaryWriter) throws DbfException {
        try {
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
                binaryWriter.write(headBuffer);
            } else {
                binaryWriter.write(this.dbfHeader.getVersion());
                binaryWriter.write(this.dbfHeader.getLastModifyYear());
                binaryWriter.write(this.dbfHeader.getLastModifyMonth());
                binaryWriter.write(this.dbfHeader.getLastModifyDay());
                binaryWriter.write(BitConverter.getBytes(this.dbfHeader.getRecordCount()));
                binaryWriter.write(BitConverter.getBytes2(this.dbfHeader.getHeaderLength2()));
                binaryWriter.write(BitConverter.getBytes2(this.dbfHeader.getRecordLength2()));
                binaryWriter.write(this.dbfHeader.getReserved());
                binaryWriter.write(this.dbfHeader.getTableFlag());
                binaryWriter.write(this.dbfHeader.getCodePageFlag());
                binaryWriter.write(this.dbfHeader.getReserved2());
            }
        }
        catch (Exception e) {
            throw new DbfException("fail to read file header.");
        }
    }

    private void writeFields(BufferedOutputStream binaryWriter) throws DbfException {
        try {
            for (DbfField dbfField : this.dbfFields) {
                if (this.bufferReadWrite) {
                    byte[] fieldBuffer = new byte[32];
                    DbfUtil.wirteSubBytes(fieldBuffer, 0, 11L, dbfField.getNameBytes());
                    fieldBuffer[11] = dbfField.getTypeChar();
                    byte[] b = BitConverter.getBytes(dbfField.getOffset());
                    DbfUtil.wirteSubBytes(fieldBuffer, 12, 4L, b);
                    fieldBuffer[16] = BitConverter.intToByte(dbfField.getLength());
                    fieldBuffer[17] = BitConverter.intToByte(dbfField.getPrecision());
                    fieldBuffer[18] = dbfField.getFieldSign();
                    DbfUtil.wirteSubBytes(fieldBuffer, 19, 13L, dbfField.getNameBytes());
                    binaryWriter.write(fieldBuffer, 0, 32);
                    continue;
                }
                binaryWriter.write(dbfField.getNameBytes());
                binaryWriter.write(dbfField.getTypeChar());
                binaryWriter.write(BitConverter.getBytes(dbfField.getOffset()));
                binaryWriter.write(BitConverter.intToByte(dbfField.getLength()));
                binaryWriter.write(BitConverter.intToByte(dbfField.getPrecision()));
                binaryWriter.write(dbfField.getFieldSign());
                binaryWriter.write(dbfField.getReserved());
            }
        }
        catch (Exception e) {
            throw new DbfException(e.getMessage(), e);
        }
    }

    public boolean hasField(String fieldName) {
        return this.fieldCach.containsKey(fieldName);
    }
}

