/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.Stream
 *  com.jiuqi.bi.util.StreamException
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.nr.single.core.para.parser.table;

import com.jiuqi.bi.util.Stream;
import com.jiuqi.bi.util.StreamException;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.single.core.para.JQTFileMap;
import com.jiuqi.nr.single.core.para.ini.Ini;
import com.jiuqi.nr.single.core.para.parser.table.FieldDefs;
import com.jiuqi.nr.single.core.para.parser.table.RepInfo;
import com.jiuqi.nr.single.core.para.parser.table.TableTypeType;
import com.jiuqi.nr.single.core.para.parser.table.ZBInfo;
import com.jiuqi.nr.single.core.para.util.ReadUtil;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FMRepInfo
extends RepInfo {
    private static final Logger logger = LoggerFactory.getLogger(FMRepInfo.class);
    private int FBBLXFieldId = -1;
    private int FDWDMFieldId = -1;
    private int FDWMCFieldId = -1;
    private int FSJDMFieldId = -1;
    private int FZBDMFieldId = -1;
    private int FSNDMFieldId = -1;
    private int FXBYSFieldId = -1;
    private String FTableTypeMapStr = "";
    private String FZdmFields = "";
    private int FPeriodField = -1;
    private int FLevelFieldId = -1;
    private int FXJHSFieldId = -1;
    private int FZDMLength = 0;
    private int FStyleBMPId = -1;
    private String BBLXField;
    private String DWDMField;
    private String DWMCField;
    private String XJHSField;
    private String SJDMField;
    private String ZBDMField;
    private String SNDMField;
    private String XBYSField;
    private String PeriodField;
    private String LevelField;
    private boolean notDefine = false;
    private boolean FDoJTTreeMaintain = false;
    private boolean FDoHZTreeMaintain = false;
    private int privateColWidth;

    public final int getZDMLength() {
        return this.FZDMLength;
    }

    public final int getBBLXFieldId() {
        return this.FBBLXFieldId;
    }

    public final int getDWDMFieldId() {
        return this.FDWDMFieldId;
    }

    public final int getDWMCFieldId() {
        return this.FDWMCFieldId;
    }

    public final int getSJDMFieldId() {
        return this.FSJDMFieldId;
    }

    public final int getZBDMFieldId() {
        return this.FZBDMFieldId;
    }

    public final int getSNDMFieldId() {
        return this.FSNDMFieldId;
    }

    public final int getXBYSFieldId() {
        return this.FXBYSFieldId;
    }

    public final String getTableTypeMapStr() {
        return this.FTableTypeMapStr;
    }

    public final List<String> getZdmFields() {
        String[] zdmFieldArr = this.FZdmFields.split(";");
        ArrayList<String> resultList = new ArrayList<String>();
        for (String field : zdmFieldArr) {
            resultList.add(field);
        }
        return resultList;
    }

    public final int getSQFieldId() {
        return this.FPeriodField;
    }

    public final String getSQField() {
        return this.PeriodField;
    }

    public final int getLevelFieldId() {
        return this.FLevelFieldId;
    }

    public final int getXJHSFieldId() {
        return this.FXJHSFieldId;
    }

    public final int getStyleBMPId() {
        return this.FStyleBMPId;
    }

    public final boolean getDoJTTreeMaintain() {
        return this.FDoJTTreeMaintain;
    }

    public final boolean getDoHZTreeMaintain() {
        return this.FDoHZTreeMaintain;
    }

    public final void IniStruct(Stream mask0, JQTFileMap jqtFileMap) throws IOException, StreamException {
        try {
            if (jqtFileMap.getAttrMatrixBlock() == 0) {
                return;
            }
            mask0.seek((long)jqtFileMap.getAttrMatrixBlock(), 0);
            this.BBLXField = ReadUtil.readStreams(mask0);
            this.DWDMField = ReadUtil.readStreams(mask0);
            this.DWMCField = ReadUtil.readStreams(mask0);
            this.SJDMField = ReadUtil.readStreams(mask0);
            this.ZBDMField = ReadUtil.readStreams(mask0);
            this.SNDMField = ReadUtil.readStreams(mask0);
            this.XBYSField = ReadUtil.readStreams(mask0);
            this.FTableTypeMapStr = ReadUtil.readStreams(mask0);
            this.FZdmFields = ReadUtil.readStreams(mask0);
            this.PeriodField = ReadUtil.readStreams(mask0);
            this.LevelField = ReadUtil.readStreams(mask0);
            this.XJHSField = ReadUtil.readStreams(mask0);
            char c0 = this.returnTableTypeChar(TableTypeType.TTT_JCFH);
            char c1 = this.returnTableTypeChar(TableTypeType.TTT_JTHZ);
            char c2 = this.returnTableTypeChar(TableTypeType.TTT_BZHZ);
            this.FDoJTTreeMaintain = c0 != '\u0000' && c1 != '\u0000' && c0 != c1 && !StringUtils.isEmpty((String)this.SJDMField) && !"".equals(this.ZBDMField);
            this.FDoHZTreeMaintain = c0 != '\u0000' && c2 != '\u0000' && c0 != c2;
        }
        catch (RuntimeException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void addDefs(FieldDefs defs) {
        super.addDefs(defs);
        this.GetStructFieldId();
    }

    public final void getStyleBMPId(String paraDir) throws IOException, StreamException {
        String iniFile = paraDir + File.separatorChar + "SYS_LRGS.DAT";
        File file = new File(FilenameUtils.normalize(iniFile));
        if (!file.exists()) {
            return;
        }
        Ini ini = new Ini();
        ini.loadIniFile(iniFile);
        String styleField = ini.ReadString("FMDM.StyleBMP", "Field", "");
        this.FStyleBMPId = this.getDefs().getZbNameList().indexOf(styleField);
    }

    public String returnTableTypeCode(TableTypeType tableType) {
        char c = this.returnTableTypeChar(tableType);
        if (c == '\u0000') {
            return "";
        }
        return String.valueOf(c);
    }

    private char returnTableTypeChar(TableTypeType tableType) {
        char RealBBLX;
        switch (tableType) {
            case TTT_JCFH: {
                RealBBLX = '0';
                break;
            }
            case TTT_JCHB: {
                RealBBLX = '*';
                break;
            }
            case TTT_JTHZ: {
                RealBBLX = '+';
                break;
            }
            case TTT_JTCE: {
                RealBBLX = '-';
                break;
            }
            case TTT_BZHZ: {
                RealBBLX = 'H';
                break;
            }
            case TTT_BZCE: {
                RealBBLX = 'C';
                break;
            }
            case TTT_YBHZ: {
                RealBBLX = 'Z';
                break;
            }
            case TTT_FJ: {
                RealBBLX = '/';
                break;
            }
            default: {
                RealBBLX = '/';
            }
        }
        char Result = '\u0000';
        if (null != this.FTableTypeMapStr) {
            int len = this.FTableTypeMapStr.length() / 2;
            for (int i = 0; i < len; ++i) {
                if (this.FTableTypeMapStr.charAt(i * 2 + 1) != RealBBLX) continue;
                Result = this.FTableTypeMapStr.charAt(i * 2);
                break;
            }
        }
        return Result;
    }

    public TableTypeType returnTableType(String signChar) {
        TableTypeType result = TableTypeType.TTT_FJ;
        if (StringUtils.isNotEmpty((String)signChar) && signChar.length() > 0) {
            result = this.returnTableType(signChar.charAt(0));
        }
        return result;
    }

    private TableTypeType returnTableType(char signChar) {
        TableTypeType result = TableTypeType.TTT_FJ;
        int RealBBLX = 47;
        if (null != this.FTableTypeMapStr) {
            int len = this.FTableTypeMapStr.length() / 2;
            for (int i = 0; i < len; ++i) {
                if (this.FTableTypeMapStr.charAt(i * 2) != signChar) continue;
                RealBBLX = this.FTableTypeMapStr.charAt(i * 2 + 1);
                break;
            }
        }
        switch (RealBBLX) {
            case 48: {
                result = TableTypeType.TTT_JCFH;
                break;
            }
            case 42: {
                result = TableTypeType.TTT_JCHB;
                break;
            }
            case 43: {
                result = TableTypeType.TTT_JTHZ;
                break;
            }
            case 45: {
                result = TableTypeType.TTT_JTCE;
                break;
            }
            case 72: {
                result = TableTypeType.TTT_BZHZ;
                break;
            }
            case 67: {
                result = TableTypeType.TTT_BZCE;
                break;
            }
            case 90: {
                result = TableTypeType.TTT_YBHZ;
                break;
            }
            case 47: {
                result = TableTypeType.TTT_FJ;
                break;
            }
            default: {
                result = TableTypeType.TTT_FJ;
            }
        }
        return result;
    }

    private void GetStructFieldId() {
        List<String> zbNames = this.getDefs().getZbNameList();
        this.FBBLXFieldId = zbNames.indexOf(this.BBLXField);
        this.FDWDMFieldId = zbNames.indexOf(this.DWDMField);
        this.FDWMCFieldId = zbNames.indexOf(this.DWMCField);
        this.FSJDMFieldId = zbNames.indexOf(this.SJDMField);
        this.FZBDMFieldId = zbNames.indexOf(this.ZBDMField);
        this.FSNDMFieldId = zbNames.indexOf(this.SNDMField);
        this.FXBYSFieldId = zbNames.indexOf(this.XBYSField);
        this.FPeriodField = zbNames.indexOf(this.PeriodField);
        this.FLevelFieldId = zbNames.indexOf(this.LevelField);
        this.FXJHSFieldId = zbNames.indexOf(this.XJHSField);
        Map<String, ZBInfo> zbPairs = this.getDefs().getAllZbInfosPair();
        String[] zdmFields = this.FZdmFields.split("[;]", -1);
        this.FZDMLength = 0;
        for (String field : zdmFields) {
            if (!zbPairs.containsKey(field)) continue;
            ZBInfo zbInfo = zbPairs.get(field);
            this.FZDMLength += zbInfo.getLength();
        }
        for (ZBInfo zbInfo : zbPairs.values()) {
            if (!"SYS_ZDM".equals(zbInfo.getFieldName())) continue;
            zbInfo.setLength(this.FZDMLength);
            break;
        }
    }

    public final int getColWidth() {
        return this.privateColWidth;
    }

    public final void setColWidth(int value) {
        this.privateColWidth = value;
    }

    public final String getDWDMFieldName() {
        return this.DWDMField;
    }

    public final String getDWMCFieldName() {
        return this.DWMCField;
    }

    public String getBBLXField() {
        return this.BBLXField;
    }

    public String getXJHSField() {
        return this.XJHSField;
    }

    public String getSJDMField() {
        return this.SJDMField;
    }

    public String getZBDMField() {
        return this.ZBDMField;
    }

    public String getSNDMField() {
        return this.SNDMField;
    }

    public String getXBYSField() {
        return this.XBYSField;
    }

    public String getPeriodField() {
        return this.PeriodField;
    }

    public String getLevelField() {
        return this.LevelField;
    }

    public boolean isNotDefine() {
        return this.notDefine;
    }

    public void setNotDefine(boolean notDefine) {
        this.notDefine = notDefine;
    }
}

