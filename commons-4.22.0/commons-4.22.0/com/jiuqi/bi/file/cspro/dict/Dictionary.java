/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.file.cspro.dict;

import com.jiuqi.bi.file.cspro.dict.Level;
import java.util.ArrayList;
import java.util.List;

public class Dictionary {
    private String version;
    private String label;
    private String name;
    private String note;
    private int recordTypeStart;
    private int recordTypeLen;
    private String positions;
    private String zeroFill;
    private String DecimalChar;
    private List<Level> levels = new ArrayList<Level>();

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return this.note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getRecordTypeStart() {
        return this.recordTypeStart;
    }

    public void setRecordTypeStart(int recordTypeStart) {
        this.recordTypeStart = recordTypeStart;
    }

    public int getRecordTypeLen() {
        return this.recordTypeLen;
    }

    public void setRecordTypeLen(int recordTypeLen) {
        this.recordTypeLen = recordTypeLen;
    }

    public String getPositions() {
        return this.positions;
    }

    public void setPositions(String positions) {
        this.positions = positions;
    }

    public String getZeroFill() {
        return this.zeroFill;
    }

    public void setZeroFill(String zeroFill) {
        this.zeroFill = zeroFill;
    }

    public String getDecimalChar() {
        return this.DecimalChar;
    }

    public void setDecimalChar(String decimalChar) {
        this.DecimalChar = decimalChar;
    }

    public void addLevel(Level level) {
        this.levels.add(level);
    }

    public Level getLevel(int index) {
        return this.levels.get(index);
    }

    public void clearLevel() {
        this.levels.clear();
    }

    public int getLevelCount() {
        return this.levels.size();
    }
}

