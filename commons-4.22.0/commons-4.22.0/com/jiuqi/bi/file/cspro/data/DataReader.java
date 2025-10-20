/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.file.cspro.data;

import com.jiuqi.bi.file.cspro.data.DataProcessor;
import com.jiuqi.bi.file.cspro.dict.Dictionary;
import com.jiuqi.bi.file.cspro.dict.Item;
import com.jiuqi.bi.file.cspro.dict.Level;
import com.jiuqi.bi.file.cspro.dict.Record;
import java.io.BufferedReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataReader {
    private Dictionary dictionary;
    private List<DataProcessor> processors = new ArrayList<DataProcessor>();
    private Map<String, List<Item>> fieldMap = new HashMap<String, List<Item>>();

    public DataReader(Dictionary dictionary) {
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
        }
    }

    public void addListener(Object listener) {
        DataProcessor processor = DataProcessor.createProcess(this.dictionary, listener);
        this.processors.add(processor);
    }

    public void readData(BufferedReader reader) throws Exception {
        String line = reader.readLine();
        if (line == null) {
            return;
        }
        line = DataReader.removeBom(line);
        int recordTypeStart = this.dictionary.getRecordTypeStart();
        int recordTypeLen = this.dictionary.getRecordTypeLen();
        do {
            if (line.length() == 0) continue;
            String recordType = line.substring(recordTypeStart - 1, recordTypeLen);
            List<Item> fields = this.fieldMap.get(recordType);
            Object[] data = new Object[fields.size()];
            for (int i = 0; i < fields.size(); ++i) {
                Item item = fields.get(i);
                int start = item.getStart();
                int len = item.getLen();
                String value = line.substring(start - 1, start - 1 + len);
                value = value.trim();
                if (item.getDataType() == null || item.getDataType().equals("Num")) {
                    boolean hasDecimalChar;
                    String decimalChar;
                    if (value.length() == 0) {
                        data[i] = null;
                        continue;
                    }
                    String string = decimalChar = item.getDecimalChar() == null ? this.dictionary.getDecimalChar() : item.getDecimalChar();
                    if (decimalChar == null) {
                        decimalChar = "Yes";
                    }
                    if (hasDecimalChar = decimalChar.equals("Yes")) {
                        double d = Double.parseDouble(value);
                        data[i] = d;
                        continue;
                    }
                    int dec = item.getDecimal();
                    String integerStr = value.substring(0, value.length() - dec);
                    String decimalStr = value.substring(value.length() - dec, value.length());
                    double d = Double.parseDouble(integerStr + "." + decimalStr);
                    data[i] = d;
                    continue;
                }
                data[i] = value;
            }
            this.processRecord(recordType, data);
        } while ((line = reader.readLine()) != null);
        for (DataProcessor processor : this.processors) {
            processor.finish();
        }
    }

    private void processRecord(String recordType, Object[] values) throws Exception {
        for (DataProcessor processor : this.processors) {
            processor.processData(recordType, values);
        }
    }

    private static String removeBom(String str) {
        int i = DataReader.checkUTF8Bom(str);
        if (i != -1) {
            return str.substring(i);
        }
        return str;
    }

    private static int checkUTF8Bom(String str) {
        byte[] bom = new byte[]{-17, -69, -65};
        try {
            String bomStr = new String(bom, "UTF-8");
            if (str.startsWith(bomStr)) {
                return bomStr.length();
            }
            return -1;
        }
        catch (UnsupportedEncodingException e) {
            return -1;
        }
    }
}

