/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.MemStream
 *  com.jiuqi.bi.util.Stream
 *  com.jiuqi.bi.util.StreamException
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.nr.single.core.para.parser;

import com.jiuqi.bi.util.MemStream;
import com.jiuqi.bi.util.Stream;
import com.jiuqi.bi.util.StreamException;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.single.core.para.FormulaInfo;
import com.jiuqi.nr.single.core.para.FormulaVariableInfo;
import com.jiuqi.nr.single.core.para.LinkTaskBean;
import com.jiuqi.nr.single.core.para.ParaInfo;
import com.jiuqi.nr.single.core.para.consts.JIOZbType;
import com.jiuqi.nr.single.core.para.consts.ZBDataType;
import com.jiuqi.nr.single.core.para.ini.Ini;
import com.jiuqi.nr.single.core.para.util.ReadUtil;
import com.jiuqi.nr.single.core.util.SinglePathUtil;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FormulaParse {
    private static final Logger logger = LoggerFactory.getLogger(FormulaParse.class);
    private String dirName;
    private int idx = 0;
    private Map<String, LinkTaskBean> linkTaskMap;

    public void InitDirName(String dirName) {
        this.dirName = dirName;
    }

    public final void parse(ParaInfo paraInfo) throws IOException, StreamException {
        List<String> formulaGroupList = this.InitFormulaGroup();
        for (String formulaGroup : formulaGroupList) {
            String file = "";
            file = "\u9ed8\u8ba4\u516c\u5f0f\u65b9\u6848".equals(formulaGroup) ? this.dirName + "PARA" + File.separatorChar + "SYS_GS" + ".DAT" : this.dirName + "PARA" + File.separatorChar + formulaGroup + ".DAT";
            File fileInfo = new File(FilenameUtils.normalize(file));
            if (!fileInfo.exists()) continue;
            Map<String, List<FormulaInfo>> formulaMap = this.LoadFormulasFromFile(file);
            paraInfo.AddFmlGroup(formulaGroup, formulaMap);
        }
        String varFile = this.dirName + "PARA" + File.separatorChar + "SYS_GSBL.Ini";
        List<FormulaVariableInfo> formualVarlist = this.LoadFormulaVarialbesFromFile(varFile);
        paraInfo.setFormulaVariables(formualVarlist);
    }

    public Map<String, List<FormulaInfo>> LoadFormulasFromFile(String file) {
        HashMap<String, List<FormulaInfo>> formulaMap = new HashMap<String, List<FormulaInfo>>();
        File fileInfo = new File(FilenameUtils.normalize(file));
        if (!fileInfo.exists()) {
            return formulaMap;
        }
        long SavePoint = 0L;
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        MemStream stream = null;
        try {
            stream = new MemStream();
            stream.loadFromFile(file);
            stream.seek(0L, 0);
            SavePoint = stream.getPosition();
            ReadUtil.skipStream((Stream)stream, 68);
            int[] ret = ReadUtil.decodeLoad((Stream)stream, 2, 45890);
            stream.seek(SavePoint, 0);
            ReadUtil.skipStream((Stream)stream, ret[0]);
            int s1 = ReadUtil.readIntValue((Stream)stream);
            for (int i = 0; i < s1; ++i) {
                int parseInt = ReadUtil.readIntValue((Stream)stream);
                String str = ReadUtil.readStringValue((Stream)stream, parseInt);
                int position = ReadUtil.readIntValue((Stream)stream);
                int position1 = ReadUtil.readIntValue((Stream)stream);
                map.put(str, position);
            }
            stream.seek(SavePoint, 0);
            for (String key : map.keySet()) {
                ArrayList<FormulaInfo> formulaList = new ArrayList<FormulaInfo>();
                if (key.equals("SYS_FORMULAKEY")) continue;
                int pos = (Integer)map.get(key);
                ReadUtil.skipStream((Stream)stream, pos);
                int x = ReadUtil.readIntValue((Stream)stream);
                int y = ReadUtil.readIntValue((Stream)stream);
                for (int i = 0; i < x; ++i) {
                    block15: for (int j = 0; j < y; ++j) {
                        FormulaInfo formula = null;
                        if (i == 0) {
                            formula = new FormulaInfo();
                            formulaList.add(formula);
                        }
                        formula = (FormulaInfo)formulaList.get(j);
                        int size = ReadUtil.readIntValue((Stream)stream);
                        String value = "";
                        if (size > 0) {
                            value = ReadUtil.readStringValue((Stream)stream, size);
                        }
                        switch (i) {
                            case 0: {
                                formula.setExpression(value);
                                continue block15;
                            }
                            case 1: {
                                formula.setDescription(value);
                                continue block15;
                            }
                            case 2: {
                                formula.setErrorData(value);
                                continue block15;
                            }
                            case 3: {
                                formula.setType(value);
                                continue block15;
                            }
                            case 4: {
                                formula.setCalcType(value);
                                continue block15;
                            }
                            case 7: {
                                formula.setCode(value);
                                continue block15;
                            }
                            case 9: {
                                formula.setAdjustCells(value);
                                continue block15;
                            }
                            case 10: {
                                formula.setUserLevel(value);
                                continue block15;
                            }
                        }
                    }
                }
                stream.seek(SavePoint, 0);
                formulaMap.put(key, formulaList);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return formulaMap;
    }

    private List<String> InitFormulaGroup() throws IOException, StreamException {
        ArrayList<String> ret = new ArrayList<String>();
        ret.add("\u9ed8\u8ba4\u516c\u5f0f\u65b9\u6848");
        String fmlfile = this.dirName + "PARA" + File.separatorChar + "ParamSet.Lst";
        File fml = new File(FilenameUtils.normalize(fmlfile));
        if (!fml.exists()) {
            return ret;
        }
        Ini ini = new Ini();
        ini.loadIniFile(fmlfile);
        int fmlmgrCount = Integer.parseInt(ini.ReadString("FmlGroups", "Count", "0"));
        for (int i = 0; i < fmlmgrCount; ++i) {
            String key = "Item_" + i;
            String value = ini.ReadString("FmlGroups", key, "");
            if (StringUtils.isEmpty((String)value)) continue;
            ret.add(value);
        }
        return ret;
    }

    public List<FormulaVariableInfo> LoadFormulaVarialbesFromFile(String file) {
        ArrayList<FormulaVariableInfo> list = new ArrayList<FormulaVariableInfo>();
        Ini ini = null;
        try {
            if (!SinglePathUtil.getFileExists(file)) {
                return list;
            }
            ini = new Ini();
            ini.loadIniFile(file);
            List<String> varCodes = ini.iniLoadStrs("SYS_VARS");
            for (String varCode : varCodes) {
                if (!StringUtils.isNotEmpty((String)varCode)) continue;
                String varTitle = ini.ReadString(varCode, "VarTitle", "");
                String dataType = ini.ReadString(varCode, "DataType", "");
                String dataSize = ini.ReadString(varCode, "DataSize", "");
                String decimal = ini.ReadString(varCode, "Decimal", "");
                String dftType = ini.ReadString(varCode, "DftType", "");
                String defaultValue = ini.ReadString(varCode, "Default", "");
                String enumDict = ini.ReadString(varCode, "EnumDict", "");
                FormulaVariableInfo varInfo = new FormulaVariableInfo();
                varInfo.setVarCode(varCode);
                varInfo.setVarTitle(varTitle);
                varInfo.setDataType(JIOZbType.ChangeJIOType(dataType));
                if (StringUtils.isEmpty((String)dataSize)) {
                    if (varInfo.getDataType() == ZBDataType.INTEGER || varInfo.getDataType() == ZBDataType.NUMERIC) {
                        varInfo.setDataSize(20);
                    } else {
                        varInfo.setDataSize(0);
                    }
                } else {
                    varInfo.setDataSize(Integer.parseInt(dataSize));
                }
                if (StringUtils.isEmpty((String)decimal)) {
                    if (varInfo.getDataType() == ZBDataType.INTEGER || varInfo.getDataType() == ZBDataType.NUMERIC) {
                        varInfo.setDecimal(0);
                    } else {
                        varInfo.setDecimal(0);
                    }
                } else {
                    varInfo.setDecimal(Integer.parseInt(decimal));
                }
                if (StringUtils.isEmpty((String)dftType)) {
                    varInfo.setDftType(1);
                } else {
                    varInfo.setDftType(Integer.parseInt(dftType));
                }
                varInfo.setDefaultValue(defaultValue);
                varInfo.setEnumDict(enumDict);
                list.add(varInfo);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return list;
    }
}

