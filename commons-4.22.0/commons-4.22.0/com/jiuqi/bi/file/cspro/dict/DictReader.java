/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.file.cspro.dict;

import com.jiuqi.bi.file.cspro.dict.Dictionary;
import com.jiuqi.bi.file.cspro.dict.Item;
import com.jiuqi.bi.file.cspro.dict.Level;
import com.jiuqi.bi.file.cspro.dict.Record;
import java.io.BufferedReader;
import java.io.UnsupportedEncodingException;

public class DictReader {
    private static final String SECTION_DICTIONARY = "Dictionary";
    private static final String SECTION_LEVEL = "Level";
    private static final String SECTION_ITEM = "Item";
    private static final String SECTION_RECORD = "Record";
    private static final String SECTION_VALUESET = "ValueSet";
    private BufferedReader reader;
    private Dictionary dictionary;
    private Level currentLevel;
    private Record currentRecord;
    private Item currentItem;
    private Object currentValueSet;

    public static Dictionary readDictionary(BufferedReader reader) throws Exception {
        DictReader dr = new DictReader(reader);
        dr.read();
        return dr.getDictionary();
    }

    public DictReader(BufferedReader reader) {
        this.reader = reader;
    }

    public void read() throws Exception {
        String line = this.reader.readLine();
        if (line == null) {
            return;
        }
        line = DictReader.removeBom(line);
        do {
            if ((line = line.trim()).length() == 0 || line.startsWith("#")) continue;
            if (line.startsWith("[") && line.endsWith("]")) {
                String section = line.substring(1, line.length() - 1);
                this.parseSection(section);
                continue;
            }
            int index = line.indexOf("=");
            if (index == -1) {
                this.parseProperty(line, null);
                continue;
            }
            this.parseProperty(line.substring(0, index), line.substring(index + 1));
        } while ((line = this.reader.readLine()) != null);
        this.parseFinished();
    }

    public Dictionary getDictionary() throws Exception {
        if (this.dictionary == null) {
            this.read();
        }
        return this.dictionary;
    }

    private void parseSection(String sectionName) {
        if (SECTION_DICTIONARY.equals(sectionName)) {
            this.dictionary = new Dictionary();
        } else if (SECTION_LEVEL.equals(sectionName)) {
            if (this.currentValueSet != null) {
                this.currentValueSet = null;
            }
            if (this.currentItem != null) {
                if (this.currentItem.isIdItem()) {
                    this.currentLevel.addIdItem(this.currentItem);
                } else {
                    this.currentRecord.addItem(this.currentItem);
                }
                this.currentItem = null;
            }
            if (this.currentRecord != null) {
                this.currentLevel.addRecord(this.currentRecord);
                this.currentRecord = null;
            }
            if (this.currentLevel != null) {
                this.dictionary.addLevel(this.currentLevel);
            }
            this.currentLevel = new Level();
        } else if (SECTION_ITEM.equals(sectionName)) {
            boolean isIdItem;
            boolean bl = isIdItem = this.currentRecord == null;
            if (this.currentValueSet != null) {
                this.currentValueSet = null;
            }
            if (this.currentItem != null) {
                if (isIdItem) {
                    this.currentLevel.addIdItem(this.currentItem);
                } else {
                    this.currentRecord.addItem(this.currentItem);
                }
            }
            this.currentItem = new Item(isIdItem);
        } else if (SECTION_RECORD.equalsIgnoreCase(sectionName)) {
            if (this.currentValueSet != null) {
                this.currentValueSet = null;
            }
            if (this.currentItem != null) {
                if (this.currentItem.isIdItem()) {
                    this.currentLevel.addIdItem(this.currentItem);
                } else {
                    this.currentRecord.addItem(this.currentItem);
                }
                this.currentItem = null;
            }
            if (this.currentRecord != null) {
                this.currentLevel.addRecord(this.currentRecord);
            }
            this.currentRecord = new Record();
        } else if (SECTION_VALUESET.equals(sectionName)) {
            this.currentValueSet = new Object();
        }
    }

    private void parseProperty(String propertyName, String propertyValue) {
        if (this.currentValueSet == null) {
            if (this.currentItem != null) {
                this.setItemProperty(this.currentItem, propertyName, propertyValue);
            } else if (this.currentRecord != null) {
                this.setRecordProperty(this.currentRecord, propertyName, propertyValue);
            } else if (this.currentLevel != null) {
                this.setLevelProperty(this.currentLevel, propertyName, propertyValue);
            } else {
                this.setDictionaryProperty(this.dictionary, propertyName, propertyValue);
            }
        }
    }

    private void parseFinished() {
        if (this.currentValueSet != null) {
            this.currentValueSet = null;
        }
        if (this.currentItem != null) {
            if (this.currentItem.isIdItem()) {
                this.currentLevel.addIdItem(this.currentItem);
            } else {
                this.currentRecord.addItem(this.currentItem);
            }
            this.currentItem = null;
        }
        if (this.currentRecord != null) {
            this.currentLevel.addRecord(this.currentRecord);
            this.currentRecord = null;
        }
        if (this.currentLevel != null) {
            this.dictionary.addLevel(this.currentLevel);
        }
    }

    private void setItemProperty(Item item, String propertyName, String propertyValue) {
        if ("Label".equals(propertyName)) {
            item.setLabel(propertyValue);
        } else if ("Name".equals(propertyName)) {
            item.setName(propertyValue);
        } else if ("Start".equals(propertyName)) {
            try {
                int start = Integer.parseInt(propertyValue);
                item.setStart(start);
            }
            catch (NumberFormatException e) {
                throw new IllegalArgumentException("\u89e3\u6790\u5c5e\u6027[Item]Start\u51fa\u9519\uff0c\u9700\u8981\u6570\u503c\u7c7b\u578b\u3002Item.Name=" + item.getName());
            }
        } else if ("Len".equals(propertyName)) {
            try {
                int len = Integer.parseInt(propertyValue);
                item.setLen(len);
            }
            catch (NumberFormatException e) {
                throw new IllegalArgumentException("\u89e3\u6790\u5c5e\u6027[Item]Len\u51fa\u9519\uff0c\u9700\u8981\u6570\u503c\u7c7b\u578b\u3002Item.Name=" + item.getName());
            }
        } else if ("DataType".equals(propertyName)) {
            item.setDataType(propertyValue);
        } else if ("Decimal".equals(propertyName)) {
            try {
                int dec = Integer.parseInt(propertyValue);
                item.setDecimal(dec);
            }
            catch (NumberFormatException e) {
                throw new IllegalArgumentException("\u89e3\u6790\u5c5e\u6027[Item]Decimal\u51fa\u9519\uff0c\u9700\u8981\u6570\u503c\u7c7b\u578b\u3002Item.Name=" + item.getName());
            }
        } else if ("DecChar".equals(propertyName)) {
            item.setDecimalChar(propertyValue);
        }
    }

    private void setRecordProperty(Record record, String propertyName, String propertyValue) {
        if ("Label".equals(propertyName)) {
            record.setLabel(propertyValue);
        } else if ("Name".equals(propertyName)) {
            record.setName(propertyValue);
        } else if ("RecordTypeValue".equals(propertyName)) {
            if (propertyValue.startsWith("'") && propertyValue.endsWith("'")) {
                propertyValue = propertyValue.substring(1, propertyValue.length() - 1);
            }
            record.setRecordTypeValue(propertyValue);
        } else if ("Required".equals(propertyName)) {
            record.setRequired(propertyValue);
        } else {
            if ("RecordLen".equals(propertyName)) {
                try {
                    int len = Integer.parseInt(propertyValue);
                    record.setRecordLen(len);
                }
                catch (NumberFormatException e) {
                    throw new IllegalArgumentException("\u89e3\u6790\u5c5e\u6027[Record]RecordLen\u51fa\u9519\uff0c\u9700\u8981\u6570\u503c\u7c7b\u578b\u3002Record.Name=" + record.getName());
                }
            }
            if ("MaxRecords".equals(propertyName)) {
                try {
                    int max = Integer.parseInt(propertyValue);
                    record.setMaxRecords(max);
                }
                catch (NumberFormatException e) {
                    throw new IllegalArgumentException("\u89e3\u6790\u5c5e\u6027[Record]MaxRecords\u51fa\u9519\uff0c\u9700\u8981\u6570\u503c\u7c7b\u578b\u3002Record.Name=" + record.getName());
                }
            }
        }
    }

    private void setLevelProperty(Level level, String propertyName, String propertyValue) {
        if ("Label".equals(propertyName)) {
            level.setLabel(propertyValue);
        } else if ("Name".equals(propertyName)) {
            level.setName(propertyValue);
        }
    }

    private void setDictionaryProperty(Dictionary dictionary, String propertyName, String propertyValue) {
        if ("Version".equals(propertyName)) {
            dictionary.setVersion(propertyValue);
        } else if ("Label".equals(propertyName)) {
            dictionary.setLabel(propertyValue);
        } else if ("Name".equals(propertyName)) {
            dictionary.setName(propertyValue);
        } else if ("Note".equals(propertyName)) {
            dictionary.setNote(propertyValue);
        } else if ("RecordTypeStart".equals(propertyName)) {
            try {
                int start = Integer.parseInt(propertyValue);
                dictionary.setRecordTypeStart(start);
            }
            catch (NumberFormatException e) {
                throw new IllegalArgumentException("\u89e3\u6790\u5c5e\u6027[Dictionary]RecordTypeStart\u51fa\u9519\uff0c\u9700\u8981\u6570\u503c\u7c7b\u578b");
            }
        } else if ("RecordTypeLen".equals(propertyName)) {
            try {
                int len = Integer.parseInt(propertyValue);
                dictionary.setRecordTypeLen(len);
            }
            catch (NumberFormatException e) {
                throw new IllegalArgumentException("\u89e3\u6790\u5c5e\u6027[Dictionary]RecordTypeLen\u51fa\u9519\uff0c\u9700\u8981\u6570\u503c\u7c7b\u578b");
            }
        } else if ("Positions".equals(propertyName)) {
            dictionary.setPositions(propertyValue);
        } else if ("ZeroFill".equals(propertyName)) {
            dictionary.setZeroFill(propertyValue);
        } else if ("DecimalChar".equals(propertyName)) {
            dictionary.setDecimalChar(propertyValue);
        }
    }

    private static String removeBom(String str) {
        int i = DictReader.checkUTF8Bom(str);
        if (i != -1) {
            return str.substring(i);
        }
        return str;
    }

    private static int checkUTF8Bom(String str) {
        byte[] bom = new byte[]{-17, -69, -65};
        try {
            String bomStr = new String(bom, "UTF-8");
            if (bomStr.startsWith(bomStr)) {
                return bomStr.length();
            }
            return -1;
        }
        catch (UnsupportedEncodingException e) {
            return -1;
        }
    }
}

