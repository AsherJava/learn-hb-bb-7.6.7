/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.MemStream
 *  com.jiuqi.bi.util.Stream
 *  com.jiuqi.bi.util.StreamException
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.nr.single.core.para.parser.table;

import com.jiuqi.bi.util.MemStream;
import com.jiuqi.bi.util.Stream;
import com.jiuqi.bi.util.StreamException;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.single.core.para.JQTFileMap;
import com.jiuqi.nr.single.core.para.MapCellInfo;
import com.jiuqi.nr.single.core.para.ParaInfo;
import com.jiuqi.nr.single.core.para.consts.FloatRegionType;
import com.jiuqi.nr.single.core.para.consts.FormRegionType;
import com.jiuqi.nr.single.core.para.consts.ReportConsts;
import com.jiuqi.nr.single.core.para.consts.ZBDataType;
import com.jiuqi.nr.single.core.para.impl.FloatRegionImpl;
import com.jiuqi.nr.single.core.para.impl.ReportFormImpl;
import com.jiuqi.nr.single.core.para.parser.eoums.EnumInfo;
import com.jiuqi.nr.single.core.para.parser.table.AreaInfo;
import com.jiuqi.nr.single.core.para.parser.table.FieldDefBlockInfo;
import com.jiuqi.nr.single.core.para.parser.table.FieldDefs;
import com.jiuqi.nr.single.core.para.parser.table.RepInfo;
import com.jiuqi.nr.single.core.para.parser.table.ZBInfo;
import com.jiuqi.nr.single.core.para.util.ReadUtil;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZbParser {
    private static final Logger logger = LoggerFactory.getLogger(ZbParser.class);
    private static final int MASK_NUMEMPTY = 16384;
    private int mainBodyZbSize = 300;
    private String reportName = "";
    private Map<String, ReportFormImpl> repMap;
    private boolean isFMDM = false;
    private int privateZDMLength;

    public ZbParser(Map<String, ReportFormImpl> repMap) {
        this.repMap = repMap;
    }

    public final int getZDMLength() {
        return this.privateZDMLength;
    }

    public final void setZDMLength(int value) {
        this.privateZDMLength = value;
    }

    public final FieldDefs BuildPara(String filePaht, ParaInfo paraInfo, JQTFileMap map, String name, RepInfo report, String bblx) throws StreamException, IOException {
        this.reportName = name;
        this.isFMDM = new String("FMDM").equals(name.toUpperCase());
        return this.InitZB(filePaht, paraInfo, map, report, bblx);
    }

    private FieldDefs InitZB(String filePaht, ParaInfo info, JQTFileMap jqtFileMap, RepInfo report, String bblx) throws StreamException, IOException {
        if (jqtFileMap == null) {
            return null;
        }
        if (jqtFileMap.getFieldDefsBlock() <= 0) {
            FieldDefs zbMainBodyInfo = new FieldDefs();
            if (!StringUtils.isEmpty((String)bblx) && (bblx.toUpperCase().equals("F") || bblx.toUpperCase().equals("W"))) {
                ZBInfo zb = new ZBInfo();
                zb.setFieldName("SYS_ZDM");
                zb.setDataType(ZBDataType.STRING);
                zb.setDecimal((byte)0);
                zb.setLength(this.getZDMLength());
                zbMainBodyInfo.addZbs(zb);
            }
            AreaInfo AreaInfo2 = new AreaInfo();
            zbMainBodyInfo.setRegionInfo(AreaInfo2);
            return new FieldDefs();
        }
        MemStream isStream = null;
        MemStream stream = null;
        FieldDefs zbMainBodyInfo = null;
        File file = new File(filePaht);
        if (file.exists()) {
            isStream = new MemStream();
            isStream.loadFromFile(filePaht);
            isStream.seek(0L, 0);
            ReadUtil.skipStream((Stream)isStream, jqtFileMap.getFieldDefsBlock());
            stream = isStream;
            if (jqtFileMap.gethFieldDefsBlock() != 0L) {
                stream = ReadUtil.decompressData((Stream)stream);
            }
        }
        String mbTitle = this.repMap == null || this.repMap.get(this.reportName) == null ? this.reportName : this.repMap.get(this.reportName).getTitle();
        zbMainBodyInfo = this.InitZB((Stream)stream, true, report.getCode(), mbTitle, report, false, bblx, info);
        return zbMainBodyInfo;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private FieldDefs InitZB(Stream mask0, boolean firstfield, String code, String title, RepInfo report, boolean isFloat, String bblx, ParaInfo paraInfo) throws StreamException {
        FieldDefs zbMainBodyInfo;
        block53: {
            zbMainBodyInfo = new FieldDefs();
            String mbCode = code;
            String mbTitle = title;
            long savePoint = 0L;
            if (!StringUtils.isEmpty((String)bblx) && (bblx.toUpperCase().equals("F") || bblx.toUpperCase().equals("W"))) {
                ZBInfo zb = new ZBInfo();
                zb.setFieldName("SYS_ZDM");
                zb.setDataType(ZBDataType.STRING);
                zb.setDecimal((byte)0);
                zb.setLength(this.getZDMLength());
                zbMainBodyInfo.addZbs(zb);
                AreaInfo AreaInfo2 = new AreaInfo();
                zbMainBodyInfo.setRegionInfo(AreaInfo2);
                return zbMainBodyInfo;
            }
            HashMap<Integer, EnumInfo> enumMap = new HashMap<Integer, EnumInfo>();
            try {
                int idx;
                int i;
                String[] split;
                int i2;
                FieldDefBlockInfo fieldDefBlockInfo = new FieldDefBlockInfo();
                savePoint = mask0.getPosition();
                if (!firstfield && !isFloat) break block53;
                fieldDefBlockInfo.init(mask0);
                int numberingColNum = -1;
                String keyFields = "";
                String sumFields = "";
                String sumLevel = "";
                String[] sumFieldArr = null;
                Object srSumFieldArr = null;
                String[] keyFieldArr = null;
                Object srKeyFieldArr = null;
                String sumaryField = "";
                String summaryWidth = "";
                int minKeepRecCount = 0;
                String regionFlag = null;
                ZBInfo zb = new ZBInfo();
                zb.setFieldName("SYS_ZDM");
                zb.setDataType(ZBDataType.STRING);
                zb.setDecimal((byte)0);
                zb.setLength(this.getZDMLength());
                zbMainBodyInfo.addZbs(zb);
                StringBuilder sb = new StringBuilder();
                byte keyUnique = 1;
                if (fieldDefBlockInfo.getExtPropPosition() > 0) {
                    mask0.seek(savePoint, 0);
                    ReadUtil.skipStream(mask0, fieldDefBlockInfo.getExtPropPosition());
                    keyUnique = ReadUtil.readByteValue(mask0);
                    numberingColNum = ReadUtil.readIntValue(mask0);
                    keyFields = ReadUtil.readStreams(mask0);
                    sumFields = ReadUtil.readStreams(mask0);
                    sumLevel = ReadUtil.readStreams(mask0);
                    sumaryField = ReadUtil.readStreams(mask0);
                    summaryWidth = ReadUtil.readStreams(mask0);
                    if (sumLevel != null) {
                        String[] levelArr = sumLevel.split("[,]", -1);
                        for (int i3 = 0; i3 < levelArr.length; ++i3) {
                            if (i3 > 0) {
                                sb.append(",");
                            }
                            sb.append(i3 + 1);
                        }
                    }
                    if (keyFields != null) {
                        keyFieldArr = keyFields.split("[;]", -1);
                    }
                    if (sumFields != null) {
                        sumFieldArr = sumFields.split("[;]", -1);
                    }
                    if ((minKeepRecCount = (int)fieldDefBlockInfo.getReserved()[0]) == 0) {
                        minKeepRecCount = 1;
                    }
                    if (mask0.getPosition() != mask0.getSize()) {
                        boolean levelArr = mask0.readBool();
                    }
                    if (mask0.getPosition() + 2L < mask0.getSize()) {
                        short FiscalMask;
                        short f = mask0.readShort();
                        if (f == (FiscalMask = -32367)) {
                            String string = ReadUtil.StreamReadString(mask0);
                        } else {
                            int soFromCurrent = 1;
                            mask0.seek(-2L, soFromCurrent);
                        }
                    }
                    int FloatHasPageSumMask = 16;
                    if ((fieldDefBlockInfo.getMaskFlag() & FloatHasPageSumMask) != 0) {
                        String PageSumTitle = ReadUtil.readStreams(mask0);
                        String PageSumMergeCode = ReadUtil.readStreams(mask0);
                        if (StringUtils.isNotEmpty((String)PageSumMergeCode)) {
                            int n = Integer.parseInt(PageSumMergeCode, 0);
                        }
                    }
                    int FloatHasRegionFlagMask = 32;
                    if ((fieldDefBlockInfo.getMaskFlag() & FloatHasRegionFlagMask) != 0) {
                        regionFlag = ReadUtil.readStreams(mask0);
                    }
                }
                int enumSkip = fieldDefBlockInfo.getEnumIdentiesPosition();
                mask0.seek(savePoint, 0);
                ReadUtil.skipStream(mask0, enumSkip);
                int enumCount = ReadUtil.readIntValue(mask0);
                for (int i4 = 0; i4 < enumCount; ++i4) {
                    int key = ReadUtil.readIntValue(mask0);
                    EnumInfo enumInfo = new EnumInfo();
                    enumInfo.init(mask0);
                    if (FloatRegionType.FLOAT_DIRECTION_ROW_FLOAT.toString().equals(bblx) && enumInfo.gethYRowNum() == 0) {
                        enumInfo.sethYRowNum(fieldDefBlockInfo.getFolatingIndex());
                    }
                    if (FloatRegionType.FLOAT_DIRECTION_COL_FLOAT.toString().equals(bblx) && enumInfo.gethYColNum() == 0) {
                        enumInfo.sethYRowNum(fieldDefBlockInfo.getFolatingIndex());
                    }
                    enumMap.put(key, enumInfo);
                }
                zbMainBodyInfo.setEnumMap(enumMap);
                ArrayList<String> JECodeList = new ArrayList<String>();
                if (fieldDefBlockInfo.getReserved().length > 0 && fieldDefBlockInfo.getReserved()[1] > 0L) {
                    mask0.seek(savePoint, 0);
                    ReadUtil.skipStream(mask0, fieldDefBlockInfo.getReserved()[1]);
                    String jedws = ReadUtil.readStreams(mask0);
                    if (StringUtils.isNotEmpty((String)jedws)) {
                        String[] JECodes;
                        for (String jedw : JECodes = jedws.split("\r\n", -1)) {
                            JECodeList.add(jedw);
                        }
                    }
                }
                mask0.seek(savePoint, 0);
                int fieldSkip = fieldDefBlockInfo.getFieldDefsPosition();
                ReadUtil.skipStream(mask0, fieldSkip);
                int zbCount = fieldDefBlockInfo.getFieldCount();
                if (keyFieldArr != null) {
                    // empty if block
                }
                if (sumFieldArr != null) {
                    // empty if block
                }
                for (i2 = JECodeList.size(); i2 < zbCount; ++i2) {
                    JECodeList.add(null);
                }
                for (i2 = 0; i2 < zbCount; ++i2) {
                    ZBInfo zbInfo = new ZBInfo();
                    zbInfo.Init(mask0);
                    EnumInfo enumInfo = null;
                    if (enumMap.containsKey(i2)) {
                        enumInfo = (EnumInfo)enumMap.get(i2);
                    }
                    if (null != enumInfo) {
                        zbInfo.setEnumShowWay(enumInfo.getShowValueType());
                        zbInfo.setEnumId(enumInfo.getEnumIdenty());
                        zbInfo.setEnumInfo(enumInfo);
                    }
                    zbInfo.setOtherJedw((String)JECodeList.get(i2));
                    zbMainBodyInfo.addZbs(zbInfo);
                }
                mask0.seek(savePoint, 0);
                ReadUtil.skipStream(mask0, fieldDefBlockInfo.getCaptionsPosition());
                int size = ReadUtil.readIntValue(mask0);
                String str = ReadUtil.readStringValue(mask0, size);
                if (!StringUtils.isEmpty((String)str)) {
                    str = str.replace("\r\n", "@");
                    split = str.split("[@]", -1);
                } else {
                    split = new String[3];
                }
                mask0.seek(savePoint, 0);
                ReadUtil.skipStream(mask0, fieldDefBlockInfo.getDataLength());
                AreaInfo mapAreaInfo = null;
                mapAreaInfo = this.BuildMaiArea(fieldDefBlockInfo, report, mbCode, title, isFloat, bblx);
                zbMainBodyInfo.setRegionInfo(mapAreaInfo);
                mapAreaInfo.setNumberingColNum(numberingColNum);
                List<ZBInfo> zbList = zbMainBodyInfo.getZbs();
                int zbSize = 0;
                if (zbList != null) {
                    zbSize = zbList.size();
                }
                int tempcol = 10000;
                for (i = 1; i < zbSize; ++i) {
                    ZBInfo zbInfo = zbList.get(i);
                    String titleStr = "";
                    try {
                        if (!StringUtils.isEmpty((String)str) && i - 1 < split.length) {
                            titleStr = split[i - 1].trim();
                        }
                    }
                    catch (RuntimeException runtimeException) {
                        // empty catch block
                    }
                    String string = titleStr = titleStr.length() > 100 ? titleStr.substring(0, 99) : titleStr;
                    if (titleStr.trim().equals("")) {
                        // empty if block
                    }
                    zbInfo.setZbTitle(titleStr);
                    if (!report.isFMDM()) continue;
                    String zbName = zbInfo.getFieldName();
                    List<String> list = null;
                    Map<String, List<String>> fmZBmap = report.getFmZBMap();
                    if (report.getFmZBMap().containsKey(zbName)) {
                        list = report.getFmZBMap().get(zbName);
                    }
                    if (list == null) continue;
                    MapCellInfo mapCellInfo = new MapCellInfo();
                }
                if (keyFieldArr != null) {
                    sb = new StringBuilder();
                    for (idx = 0; idx < keyFieldArr.length; ++idx) {
                        String srKeyField = keyFieldArr[idx];
                        if (srKeyField == null || srKeyField.equals("")) continue;
                        sb.append(keyFieldArr[idx]).append(";");
                    }
                    mapAreaInfo.setKeyField(sb.toString());
                    if (keyFieldArr.length > 0) {
                        mapAreaInfo.setKeyIsUnique(keyUnique == 1);
                    } else {
                        mapAreaInfo.setKeyIsUnique(false);
                    }
                } else if (isFloat) {
                    mapAreaInfo.setKeyIsUnique(false);
                } else {
                    mapAreaInfo.setKeyIsUnique(true);
                }
                if (sumFieldArr != null) {
                    sb = new StringBuilder();
                    for (idx = 0; idx < sumFieldArr.length; ++idx) {
                        String srSumField = sumFieldArr[idx];
                        sb.append(srSumField).append(";");
                    }
                    mapAreaInfo.setSumField(sb.toString());
                    mapAreaInfo.setSumLevel(sumLevel);
                }
                mapAreaInfo.setSummaryWidh(summaryWidth);
                mapAreaInfo.setSummaryField(sumaryField);
                mapAreaInfo.setMinKeepRecCount(minKeepRecCount);
                mapAreaInfo.setRegionFlag(regionFlag);
                for (i = 0; i < fieldDefBlockInfo.getChildDefsCount(); ++i) {
                    mbCode = code + "_F" + (i < 10 ? "0" : "") + i;
                    String idx2 = i + 1 + "";
                    if (i < 10) {
                        idx2 = ReportConsts.IDX_ARR[i];
                    }
                    FieldDefs zbmainbody = this.InitZB(mask0, false, mbCode, mbTitle + "\u6d6e\u52a8\u533a\u57df" + idx2, report, true, bblx, paraInfo);
                    zbMainBodyInfo.addSubMb(zbmainbody);
                }
            }
            catch (FileNotFoundException e) {
                logger.error(e.getMessage(), e);
            }
            catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
            catch (RuntimeException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return zbMainBodyInfo;
    }

    private AreaInfo BuildMaiArea(FieldDefBlockInfo fieldDefBlockInfo, RepInfo report, String mbCode, String title, boolean isFloat, String bblx) {
        AreaInfo mapAreaInfo = new AreaInfo();
        mapAreaInfo.setMbCode(mbCode);
        FloatRegionImpl mapArea = new FloatRegionImpl();
        mapAreaInfo.setMapArea(mapArea);
        mapArea.setTitle(title);
        if (isFloat) {
            int floatCountAdd = 0;
            if (fieldDefBlockInfo.getFloatCount() > 0) {
                floatCountAdd = fieldDefBlockInfo.getFloatCount() - 1;
            }
            mapArea.setType(FormRegionType.MAP_TYPE_FLOAT);
            if (new String("r").equals(bblx.toLowerCase())) {
                mapArea.setFloatDirectory(FloatRegionType.FLOAT_DIRECTION_ROW_FLOAT);
                mapArea.setFloatRangeStartColNo(1);
                mapArea.setFloatRangeEndColNo(report.getColCount() - 1);
                mapArea.setFloatRangeStartNo(fieldDefBlockInfo.getFolatingIndex());
                mapArea.setFloatRangeEndNo(fieldDefBlockInfo.getFolatingIndex() + floatCountAdd);
            } else if (new String("c").toLowerCase().equals(bblx.toLowerCase())) {
                mapArea.setFloatDirectory(FloatRegionType.FLOAT_DIRECTION_COL_FLOAT);
                mapArea.setFloatRangeStartColNo(fieldDefBlockInfo.getFolatingIndex());
                mapArea.setFloatRangeEndColNo(fieldDefBlockInfo.getFolatingIndex() + floatCountAdd);
                mapArea.setFloatRangeStartNo(1);
                mapArea.setFloatRangeEndNo(report.getRowCount() - 1);
            }
        } else {
            mapArea.setType(FormRegionType.MAP_TYPE_FIXED);
        }
        return mapAreaInfo;
    }
}

