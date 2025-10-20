/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.file.cspro.dict;

import com.jiuqi.bi.file.cspro.dict.Dictionary;
import com.jiuqi.bi.file.cspro.dict.Item;
import com.jiuqi.bi.file.cspro.dict.Level;
import com.jiuqi.bi.file.cspro.dict.Record;
import com.jiuqi.bi.util.StringUtils;
import java.io.IOException;
import java.io.Writer;

public class DictWriter {
    private Dictionary dic;
    private static final String SECTION_DICTIONARY = "Dictionary";
    private static final String SECTION_LEVEL = "Level";
    private static final String SECTION_IDITEMS = "IdItems";
    private static final String SECTION_ITEM = "Item";
    private static final String SECTION_RECORD = "Record";

    public DictWriter(Dictionary dic) {
        this.dic = dic;
    }

    public void write(Writer writer) throws IOException {
        StringBuffer buf = new StringBuffer();
        this.getDicPro(buf);
        writer.write(buf.toString());
        buf.delete(0, buf.length());
        int levelCount = this.dic.getLevelCount();
        for (int i = 0; i < levelCount; ++i) {
            Level level = this.dic.getLevel(i);
            this.getLevelPro(buf, level);
            writer.write(buf.toString());
            buf.delete(0, buf.length());
            writer.write("[IdItems]\n\n");
            int idItemCount = level.getIdItemCount();
            for (int j = 0; j < idItemCount; ++j) {
                Item item = level.getIdItem(j);
                this.getItemPro(buf, item);
                writer.write(buf.toString());
                buf.delete(0, buf.length());
            }
            int recordCount = level.getRecordCount();
            for (int j = 0; j < recordCount; ++j) {
                Record record = level.getRecord(j);
                this.getRecordPro(buf, record);
                writer.write(buf.toString());
                buf.delete(0, buf.length());
                int recordItemCount = record.getItemCount();
                for (int n = 0; n < recordItemCount; ++n) {
                    Item item = record.getItem(n);
                    this.getItemPro(buf, item);
                    writer.write(buf.toString());
                    buf.delete(0, buf.length());
                }
            }
        }
    }

    private void getDicPro(StringBuffer dicBuf) throws IOException {
        String decimalChar;
        String zeroFill;
        String note;
        String name;
        String label;
        dicBuf.append("[").append(SECTION_DICTIONARY).append("]\n");
        String version = this.dic.getVersion();
        if (StringUtils.isNotEmpty((String)version)) {
            dicBuf.append("Version=").append(version).append("\n");
        }
        if (StringUtils.isNotEmpty((String)(label = this.dic.getLabel()))) {
            dicBuf.append("Label=").append(label).append("\n");
        }
        if (StringUtils.isNotEmpty((String)(name = this.dic.getName()))) {
            dicBuf.append("Name=").append(name).append("\n");
        }
        if (StringUtils.isNotEmpty((String)(note = this.dic.getNote()))) {
            dicBuf.append("Note=").append(name).append("\n");
        }
        dicBuf.append("RecordTypeStart=").append(this.dic.getRecordTypeStart()).append("\n");
        dicBuf.append("RecordTypeLen=").append(this.dic.getRecordTypeLen()).append("\n");
        String positions = this.dic.getPositions();
        if (StringUtils.isNotEmpty((String)positions)) {
            dicBuf.append("Positions=").append(positions).append("\n");
        }
        if (StringUtils.isNotEmpty((String)(zeroFill = this.dic.getZeroFill()))) {
            dicBuf.append("ZeroFill=").append(zeroFill).append("\n");
        }
        if (StringUtils.isNotEmpty((String)(decimalChar = this.dic.getDecimalChar()))) {
            dicBuf.append("DecimalChar=").append(decimalChar).append("\n");
        }
        dicBuf.append("\n");
    }

    private void getLevelPro(StringBuffer levelBuf, Level level) {
        String name;
        levelBuf.append("[").append(SECTION_LEVEL).append("]\n");
        String label = level.getLabel();
        if (StringUtils.isNotEmpty((String)label)) {
            levelBuf.append("Label=").append(label).append("\n");
        }
        if (StringUtils.isNotEmpty((String)(name = level.getName()))) {
            levelBuf.append("Name=").append(name).append("\n");
        }
        levelBuf.append("\n");
    }

    private void getItemPro(StringBuffer itemBuf, Item item) {
        String name;
        itemBuf.append("[").append(SECTION_ITEM).append("]\n");
        String label = item.getLabel();
        if (StringUtils.isNotEmpty((String)label)) {
            itemBuf.append("Label=").append(label).append("\n");
        }
        if (StringUtils.isNotEmpty((String)(name = item.getName()))) {
            itemBuf.append("Name=").append(name).append("\n");
        }
        itemBuf.append("Start=").append(item.getStart()).append("\n");
        itemBuf.append("Len=").append(item.getLen()).append("\n");
        String dataType = item.getDataType();
        if (StringUtils.isNotEmpty((String)dataType)) {
            itemBuf.append("DataType=").append(dataType).append("\n");
        }
        itemBuf.append("Decimal=").append(item.getDecimal()).append("\n");
        String decChar = item.getDecimalChar();
        if (StringUtils.isNotEmpty((String)decChar)) {
            itemBuf.append("DecChar=").append(decChar).append("\n");
        }
        itemBuf.append("\n");
    }

    private void getRecordPro(StringBuffer recordBuf, Record record) {
        String required;
        String recordTypeValue;
        String name;
        recordBuf.append("[").append(SECTION_RECORD).append("]\n");
        String label = record.getLabel();
        if (StringUtils.isNotEmpty((String)label)) {
            recordBuf.append("Label=").append(label).append("\n");
        }
        if (StringUtils.isNotEmpty((String)(name = record.getName()))) {
            recordBuf.append("Name=").append(name).append("\n");
        }
        if (StringUtils.isNotEmpty((String)(recordTypeValue = record.getRecordTypeValue()))) {
            recordBuf.append("RecordTypeValue='").append(recordTypeValue).append("'\n");
        }
        if (StringUtils.isNotEmpty((String)(required = record.getRequired()))) {
            recordBuf.append("Required=").append(required).append("\n");
        }
        recordBuf.append("RecordLen=").append(record.getRecordLen()).append("\n");
        if (record.getMaxRecords() != 0) {
            recordBuf.append("MaxRecords=").append(record.getMaxRecords()).append("\n");
        }
        recordBuf.append("\n");
    }
}

