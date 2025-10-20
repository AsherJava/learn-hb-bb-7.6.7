/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.file.cspro.data;

import com.jiuqi.bi.file.cspro.data.DataRow;
import com.jiuqi.bi.file.cspro.dict.Dictionary;
import com.jiuqi.bi.file.cspro.dict.Item;
import com.jiuqi.bi.file.cspro.dict.Level;
import com.jiuqi.bi.file.cspro.dict.Record;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataWriter {
    private Dictionary dictionary;
    private Map<String, List<Item>> fieldMap = new HashMap<String, List<Item>>();
    private Map<String, Integer> lengthMap = new HashMap<String, Integer>();

    public DataWriter(Dictionary dictionary) {
        this.dictionary = dictionary;
        this.initFieldMap();
    }

    private void initFieldMap() {
        int i;
        if (this.dictionary.getLevelCount() == 0) {
            return;
        }
        Level level = this.dictionary.getLevel(0);
        ArrayList<Item> idItems = new ArrayList<Item>();
        for (i = 0; i < level.getIdItemCount(); ++i) {
            Item idItem = level.getIdItem(i);
            idItems.add(idItem);
        }
        for (i = 0; i < level.getRecordCount(); ++i) {
            Record record = level.getRecord(i);
            String recordTypeValue = record.getRecordTypeValue();
            ArrayList<Item> fieldItems = new ArrayList<Item>(idItems);
            for (int j = 0; j < record.getItemCount(); ++j) {
                Item item = record.getItem(j);
                fieldItems.add(item);
            }
            this.fieldMap.put(recordTypeValue, fieldItems);
            this.lengthMap.put(recordTypeValue, record.getRecordLen());
        }
    }

    public void write(Writer writer, DataRow row) throws Exception {
        String recordType = row.getRecordType();
        List<Item> fieldItems = this.fieldMap.get(recordType);
        if (fieldItems == null) {
            throw new Exception("\u672a\u53d1\u73b0\u8868\u7684\u5b9a\u4e49");
        }
        int rowLength = this.lengthMap.get(recordType);
        StringBuffer rowBuf = new StringBuffer(rowLength);
        for (int i = 0; i < rowLength; ++i) {
            rowBuf.append(" ");
        }
        rowBuf.append("\n");
        int recordLength = this.dictionary.getRecordTypeLen();
        if (recordType.length() > recordLength) {
            throw new Exception("record\u89c4\u5b9a\u957f\u5ea6\u6bd4\u5b9e\u9645\u957f\u5ea6\u77ed");
        }
        int recordStart = this.dictionary.getRecordTypeStart();
        rowBuf.replace(recordStart - 1, recordType.length(), recordType);
        Object[] objs = row.getData();
        for (int i = 0; i < fieldItems.size(); ++i) {
            Item item = fieldItems.get(i);
            this.processValue(objs[i], item, rowBuf);
        }
        writer.write(rowBuf.toString());
    }

    private void processValue(Object obj, Item item, StringBuffer row) throws Exception {
        int length = item.getLen();
        int start = item.getStart();
        if (obj == null) {
            return;
        }
        if (item.getDataType() == null || item.getDataType().equals("Num")) {
            String decimalChar;
            String string = decimalChar = item.getDecimalChar() == null ? this.dictionary.getDecimalChar() : item.getDecimalChar();
            if (decimalChar == null) {
                decimalChar = "Yes";
            }
            if ("Yes".equals(decimalChar)) {
                String value = obj.toString();
                int difLength = length - value.length();
                if (length < value.length()) {
                    throw new Exception("\u89c4\u5b9a\u957f\u5ea6\u6bd4\u5b9e\u9645\u6570\u636e\u957f\u5ea6\u77ed,Item.Name=" + item.getName());
                }
                row.replace(start - 1 + difLength, start - 1 + length, value);
            } else {
                int difLength;
                String value = obj.toString();
                int pointIndex = value.indexOf(".");
                String integerStr = value.substring(0, pointIndex);
                String decimalStr = value.substring(pointIndex + 1, value.length());
                int deciLen = item.getDecimal();
                int deciDif = deciLen - decimalStr.length();
                if (deciDif < 0) {
                    throw new Exception("\u89c4\u5b9a\u5c0f\u6570\u957f\u5ea6\u6bd4\u5b9e\u9645\u6570\u636e\u957f\u5ea6\u77ed,Item.Name=" + item.getName());
                }
                if (deciDif > 0) {
                    row.replace(start - 1 + length - deciLen, start - 1 + length, decimalStr);
                }
                if ((difLength = length - deciLen - integerStr.length()) < 0) {
                    throw new Exception("\u89c4\u5b9a\u957f\u5ea6\u6bd4\u5b9e\u9645\u6570\u636e\u957f\u5ea6\u77ed,Item.Name=" + item.getName());
                }
                row.replace(start - 1 + difLength, start - 1 + length - deciLen, integerStr);
            }
        } else {
            String value = obj.toString();
            if (length < value.length()) {
                throw new Exception("\u89c4\u5b9a\u957f\u5ea6\u6bd4\u5b9e\u9645\u6570\u636e\u957f\u5ea6\u77ed,Item.Name=" + item.getName());
            }
            value = value.replace("\n", "\\n").replace("\r", "\\r");
            row.replace(start - 1, start - 1 + value.length(), value);
        }
    }
}

