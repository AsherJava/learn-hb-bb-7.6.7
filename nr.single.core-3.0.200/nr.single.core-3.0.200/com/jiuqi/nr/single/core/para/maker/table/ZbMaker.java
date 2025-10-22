/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.MemStream
 *  com.jiuqi.bi.util.Stream
 *  com.jiuqi.bi.util.StreamException
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.nr.single.core.para.maker.table;

import com.jiuqi.bi.util.MemStream;
import com.jiuqi.bi.util.Stream;
import com.jiuqi.bi.util.StreamException;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.single.core.para.JQTFileMap;
import com.jiuqi.nr.single.core.para.MapCellInfo;
import com.jiuqi.nr.single.core.para.ParaInfo;
import com.jiuqi.nr.single.core.para.consts.FloatRegionType;
import com.jiuqi.nr.single.core.para.consts.FormRegionType;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZbMaker {
    private static final Logger logger = LoggerFactory.getLogger(ZbMaker.class);
    private static final int MASK_NUMEMPTY = 16384;
    private int mainBodyZbSize = 300;
    private String reportName = "";
    private Map<String, ReportFormImpl> repMap;
    private boolean isFMDM = false;
    private int privateZDMLength;

    public ZbMaker(Map<String, ReportFormImpl> repMap) {
        this.repMap = repMap;
    }

    public final int getZDMLength() {
        return this.privateZDMLength;
    }

    public final void setZDMLength(int value) {
        this.privateZDMLength = value;
    }

    public final FieldDefs savePara(FieldDefs zbMainBodyInfo, String filePaht, ParaInfo paraInfo, JQTFileMap map, String name, RepInfo report, String bblx) throws StreamException, IOException {
        this.reportName = name;
        this.isFMDM = new String("FMDM").equals(name.toUpperCase());
        this.saveZB(zbMainBodyInfo, filePaht, paraInfo, map, report, bblx);
        return zbMainBodyInfo;
    }

    private void saveZB(FieldDefs def, String filePaht, ParaInfo info, JQTFileMap jqtFileMap, RepInfo report, String bblx) throws StreamException, IOException {
        if (jqtFileMap == null) {
            return;
        }
        MemStream isStream = null;
        MemStream stream = null;
        File file = new File(filePaht);
        this.saveZBInfo((Stream)stream, true, def, report, false, bblx, info);
        if (file.exists()) {
            isStream = new MemStream();
            isStream.loadFromFile(filePaht);
            isStream.seek(0L, 0);
            ReadUtil.skipStream((Stream)isStream, jqtFileMap.getFieldDefsBlock());
            stream = isStream;
            if (jqtFileMap.gethFieldDefsBlock() != 0L) {
                // empty if block
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void saveZBInfo(Stream mask0, boolean firstfield, FieldDefs def, RepInfo report, boolean isFloat, String bblx, ParaInfo paraInfo) throws StreamException {
        block41: {
            FieldDefs zbMainBodyInfo = def;
            long savePoint = 0L;
            if (StringUtils.isEmpty((String)bblx) || bblx.toUpperCase().equals("F") || bblx.toUpperCase().equals("W")) {
                // empty if block
            }
            HashMap<Integer, EnumInfo> enumMap = new HashMap<Integer, EnumInfo>();
            try {
                int idx;
                int i;
                String[] split;
                FieldDefBlockInfo fieldDefBlockInfo = new FieldDefBlockInfo();
                savePoint = mask0.getPosition();
                if (!firstfield && !isFloat) break block41;
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
                        for (int i2 = 0; i2 < levelArr.length; ++i2) {
                            if (i2 > 0) {
                                sb.append(",");
                            }
                            sb.append(i2 + 1);
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
                }
                int enumSkip = fieldDefBlockInfo.getEnumIdentiesPosition();
                mask0.seek(savePoint, 0);
                ReadUtil.skipStream(mask0, enumSkip);
                int enumCount = ReadUtil.readIntValue(mask0);
                for (int i3 = 0; i3 < enumCount; ++i3) {
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
                for (int i4 = 0; i4 < zbCount; ++i4) {
                    ZBInfo zbInfo = new ZBInfo();
                    zbInfo.Init(mask0);
                    EnumInfo enumInfo = null;
                    if (enumMap.containsKey(i4)) {
                        enumInfo = (EnumInfo)enumMap.get(i4);
                    }
                    if (null != enumInfo) {
                        zbInfo.setEnumShowWay(enumInfo.getShowValueType());
                        zbInfo.setEnumId(enumInfo.getEnumIdenty());
                        zbInfo.setEnumInfo(enumInfo);
                    }
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
                this.saveMaiArea(mapAreaInfo, fieldDefBlockInfo, report, isFloat, bblx);
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
                for (i = 0; i < zbMainBodyInfo.getSubMbs().size(); ++i) {
                    FieldDefs subDef = zbMainBodyInfo.getSubMbs().get(i);
                    this.saveZBInfo(mask0, false, subDef, report, true, bblx, paraInfo);
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
    }

    private AreaInfo saveMaiArea(AreaInfo mapAreaInfo, FieldDefBlockInfo fieldDefBlockInfo, RepInfo report, boolean isFloat, String bblx) {
        String mbCode = mapAreaInfo.getMbCode();
        FloatRegionImpl mapArea = mapAreaInfo.getMapArea();
        String title = mapArea.getTitle();
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

