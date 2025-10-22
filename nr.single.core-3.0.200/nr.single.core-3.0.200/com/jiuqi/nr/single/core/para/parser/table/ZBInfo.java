/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.Stream
 *  com.jiuqi.bi.util.StreamException
 */
package com.jiuqi.nr.single.core.para.parser.table;

import com.jiuqi.bi.util.Stream;
import com.jiuqi.bi.util.StreamException;
import com.jiuqi.nr.single.core.para.consts.JIOZbType;
import com.jiuqi.nr.single.core.para.consts.ZBDataType;
import com.jiuqi.nr.single.core.para.parser.eoums.EnumInfo;
import com.jiuqi.nr.single.core.para.util.ReadUtil;

public class ZBInfo {
    private ZBDataType dataType = ZBDataType.forValue(0);
    private byte decimald;
    private int length;
    private int extSign;
    private String wReserved;
    private int[] gridPos;
    private int[] numPos;
    private int[] setNum;
    private byte bReserved1;
    private byte bReserved2;
    private String wReserved2;
    private int lReserved;
    private String dwReserved;
    private String enumIdent;
    private String zbTitle;
    private String FieldName;
    private int sumType;
    private int enumShowWay;
    private EnumInfo enumInfo;
    private String defaultValue;
    private String uUIDCode;
    private String otherJedw;
    private String mappingCode;
    private String mappingTalbe;

    public String getuUIDCode() {
        return this.uUIDCode;
    }

    public void setuUIDCode(String uUIDCode) {
        this.uUIDCode = uUIDCode;
    }

    public final String getFieldName() {
        return this.FieldName;
    }

    public final void setFieldName(String fieldName) {
        this.FieldName = fieldName;
    }

    public final ZBDataType getDataType() {
        return this.dataType;
    }

    public final void setDataType(ZBDataType dataType) {
        this.dataType = dataType;
    }

    public final byte getDecimal() {
        return this.decimald;
    }

    public final void setDecimal(byte i) {
        this.decimald = i;
    }

    public final int getLength() {
        return this.length;
    }

    public final void setLength(int length) {
        this.length = length;
    }

    public final int getExtSign() {
        return this.extSign;
    }

    public final void setExtSign(int extSign) {
        this.extSign = extSign;
    }

    public final String getwReserved() {
        return this.wReserved;
    }

    public final boolean getHLNumEmpty() {
        return (this.extSign & 0x4000) != 0;
    }

    public final void setwReserved(String wReserved) {
        this.wReserved = wReserved;
    }

    public final int[] getGridPos() {
        return this.gridPos;
    }

    public final void setGridPos(int[] gridPos) {
        this.gridPos = gridPos;
    }

    public final int[] getNumPos() {
        return this.numPos;
    }

    public final void setNumPos(int[] numPos) {
        this.numPos = numPos;
    }

    public final int[] getSetNum() {
        return this.setNum;
    }

    public final void setSetNum(int[] setNum) {
        this.setNum = setNum;
    }

    public final byte getbReserved1() {
        return this.bReserved1;
    }

    public final void setbReserved1(byte bReserved1) {
        this.bReserved1 = bReserved1;
    }

    public final byte getbReserved2() {
        return this.bReserved2;
    }

    public final void setbReserved2(byte bReserved2) {
        this.bReserved2 = bReserved2;
    }

    public final String getwReserved2() {
        return this.wReserved2;
    }

    public final void setwReserved2(String wReserved2) {
        this.wReserved2 = wReserved2;
    }

    public final int getlReserved() {
        return this.lReserved;
    }

    public final void setlReserved(int lReserved) {
        this.lReserved = lReserved;
    }

    public final String getDwReserved() {
        return this.dwReserved;
    }

    public final void setDwReserved(String dwReserved) {
        this.dwReserved = dwReserved;
    }

    public final String getZbTitle() {
        return this.zbTitle;
    }

    public final void setZbTitle(String zbTitle) {
        this.zbTitle = zbTitle;
    }

    public int Size() {
        return 0;
    }

    public final boolean getZbSumMode() {
        return (this.extSign & 1) != 0;
    }

    public final boolean getJEDW() {
        return (this.extSign & 2) != 0;
    }

    public void Init(Stream mask0) throws StreamException {
        this.setFieldName(ReadUtil.readStringValue(mask0, 32).trim());
        this.setDataType(JIOZbType.ChangeJIOType(ReadUtil.readStringValue(mask0, 1).trim()));
        this.setDecimal(ReadUtil.readByteValue(mask0));
        int length = ReadUtil.readSmallIntValue(mask0);
        if (this.dataType == ZBDataType.NUMERIC) {
            length = 20;
        }
        this.setLength(length == 0 ? 20 : length);
        this.setExtSign(ReadUtil.readSmallIntValue(mask0));
        this.setwReserved(ReadUtil.readStringValue(mask0, 2).trim());
        int[] gridPos = new int[]{ReadUtil.readSmallIntValue(mask0), ReadUtil.readSmallIntValue(mask0)};
        this.setGridPos(gridPos);
        int[] numPos = new int[]{ReadUtil.readSmallIntValue(mask0), ReadUtil.readSmallIntValue(mask0)};
        this.setNumPos(numPos);
        int[] setNum = new int[]{ReadUtil.readSmallIntValue(mask0), ReadUtil.readSmallIntValue(mask0)};
        this.setSetNum(setNum);
        this.setbReserved1(ReadUtil.readByteValue(mask0));
        this.setbReserved2(ReadUtil.readByteValue(mask0));
        this.setwReserved2(ReadUtil.readStringValue(mask0, 2).trim());
        this.setlReserved(ReadUtil.readIntValue(mask0));
        this.setDwReserved(ReadUtil.readStringValue(mask0, 4).trim());
    }

    public final String getEnumId() {
        return this.enumIdent;
    }

    public final void setEnumId(String enumId) {
        this.enumIdent = enumId;
    }

    public final int getSumType() {
        return this.sumType;
    }

    public final void setSumType(int sumType) {
        this.sumType = sumType;
    }

    public final int getEnumShowWay() {
        return this.enumShowWay;
    }

    public final void setEnumShowWay(int enumShowWay) {
        this.enumShowWay = enumShowWay;
    }

    public final EnumInfo getEnumInfo() {
        return this.enumInfo;
    }

    public final void setEnumInfo(EnumInfo value) {
        this.enumInfo = value;
    }

    public final String getDefaultValue() {
        return this.defaultValue;
    }

    public final void setDefaultValue(String value) {
        this.defaultValue = value;
    }

    public String getOtherJedw() {
        return this.otherJedw;
    }

    public void setOtherJedw(String otherJedw) {
        this.otherJedw = otherJedw;
    }

    public String getMappingCode() {
        return this.mappingCode;
    }

    public void setMappingCode(String mappingCode) {
        this.mappingCode = mappingCode;
    }

    public String getMappingTalbe() {
        return this.mappingTalbe;
    }

    public void setMappingTalbe(String mappingTalbe) {
        this.mappingTalbe = mappingTalbe;
    }
}

