/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.nr.single.core.para.parser.eoums;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.single.core.dbf.DbfException;
import com.jiuqi.nr.single.core.dbf.DbfTableUtil;
import com.jiuqi.nr.single.core.dbf.IDbfTable;
import com.jiuqi.nr.single.core.para.ParaInfo;
import com.jiuqi.nr.single.core.para.parser.eoums.DataInfo;
import com.jiuqi.nr.single.core.para.parser.eoums.EnumsItemModel;
import com.jiuqi.nr.single.core.util.datatable.DataRow;
import com.jiuqi.nr.single.core.util.datatable.DataRowCollection;
import java.io.File;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnumParser {
    private static final Logger logger = LoggerFactory.getLogger(EnumParser.class);
    private String dirName;
    private Map<String, EnumsItemModel> bdList;

    public void InitDirName(String dirName_0) {
        this.dirName = dirName_0;
    }

    public final void parse(ParaInfo paraInfo) throws Exception {
        this.InitBDList(paraInfo);
        if (this.bdList == null || this.bdList.isEmpty()) {
            return;
        }
        this.ParseBDs();
    }

    private void ConvertFixStructBD() {
        for (EnumsItemModel baseDataModel : this.bdList.values()) {
            if (baseDataModel.getCodeStruct() == null || baseDataModel.getCodeStruct().equals("")) continue;
            Set<String> keySet = baseDataModel.getEnumItemList().keySet();
            for (String code : keySet) {
                String parentCode = this.GetParentCode(baseDataModel, code);
                baseDataModel.getEnumItemList().get(code).setParent(parentCode);
            }
            baseDataModel.setCodeStruct(baseDataModel.getCodeStruct().replace(";", ""));
            baseDataModel.setFix(false);
        }
    }

    private boolean IsAllZero(String str) {
        if (StringUtils.isEmpty((String)str)) {
            return false;
        }
        for (int i = 0; i < str.length(); ++i) {
            if (str.charAt(i) == '0') continue;
            return false;
        }
        return true;
    }

    private String GetParentCode(String[] CodeStruct, String Code) {
        if (CodeStruct.length == 0) {
            return null;
        }
        if (CodeStruct.length == 1) {
            int codeLen = Integer.parseInt(CodeStruct[0]);
            String resultStr = "";
            for (int i = 0; i < codeLen; ++i) {
                resultStr = resultStr + "0";
            }
            return resultStr;
        }
        if (CodeStruct.length == 2) {
            int allLen = 0;
            String[] resultStr = CodeStruct;
            int i = resultStr.length;
            for (int j = 0; j < i; ++j) {
                String s = resultStr[j];
                allLen += Integer.parseInt(s);
            }
            String allZeroStr = "";
            for (i = 0; i < allLen; ++i) {
                allZeroStr = allZeroStr + "0";
            }
            int len1 = Integer.parseInt(CodeStruct[0]);
            int len2 = Integer.parseInt(CodeStruct[1]);
            String str1 = Code.substring(0, len1);
            String str2 = Code.substring(len1, len1 + len2);
            if (this.IsAllZero(str2)) {
                return allZeroStr.substring(0, allLen);
            }
            return str1 + allZeroStr.substring(0, len2);
        }
        int len1 = Integer.parseInt(CodeStruct[0]);
        int allLen = 0;
        for (String s : CodeStruct) {
            allLen += Integer.parseInt(s);
        }
        String[] newCodeStruct = new String[CodeStruct.length - 1];
        for (int i = 0; i < newCodeStruct.length; ++i) {
            newCodeStruct[i] = CodeStruct[i + 1];
        }
        String newCode = Code.substring(len1, allLen);
        String str = this.GetParentCode(newCodeStruct, newCode);
        return Code.substring(0, len1) + str;
    }

    private String GetParentCode(EnumsItemModel baseDataModel, String code) {
        String parentEnumCode = this.GetParentCode(baseDataModel.getCodeStruct().split("[;]", -1), code);
        return parentEnumCode;
    }

    private void AddEmptyParent(EnumsItemModel baseDataModel, String parentEnumCode) {
        DataInfo dataInfo = new DataInfo();
        dataInfo.setCode(parentEnumCode);
        dataInfo.setName("\u8f85\u52a9\u589e\u52a0\u8282\u70b9");
        dataInfo.setParent("");
        dataInfo.setStartFlag(false);
        dataInfo.setRowNum(baseDataModel.getEnumItemList().size() + 1);
        baseDataModel.getEnumItemList().put(dataInfo.getCode(), dataInfo);
        baseDataModel.getItemDataList().add(dataInfo);
        this.GetParentCode(baseDataModel, parentEnumCode);
    }

    private void InitBDList(ParaInfo paraInfo) throws Exception {
        String afile = this.dirName + "PARA" + File.separatorChar + "EnumList.DBF";
        String aEnumCtrlfile = this.dirName + "PARA" + File.separatorChar + "EnumControl.dat";
        try {
            File ctrlFile = new File(FilenameUtils.normalize(aEnumCtrlfile));
            paraInfo.setValueEmptyByEnumCtrl(ctrlFile.exists());
            File file = new File(FilenameUtils.normalize(afile));
            if (file.exists()) {
                IDbfTable reader = DbfTableUtil.getDbfTable(afile);
                this.bdList = paraInfo.getEnunList();
                DataRowCollection dataRows = reader.getTable().getRows();
                for (int i = 0; i < dataRows.size(); ++i) {
                    DataRow rowObjects = (DataRow)dataRows.get(i);
                    EnumsItemModel baseDataModel = new EnumsItemModel();
                    baseDataModel.setCode(((String)rowObjects.getValue(0)).trim());
                    if (paraInfo.getPrefix() != null) {
                        baseDataModel.setJioCode(paraInfo.getPrefix() + baseDataModel.getCode());
                    } else {
                        baseDataModel.setJioCode(baseDataModel.getCode());
                    }
                    baseDataModel.setTitle(((String)rowObjects.getValue(1)).trim());
                    baseDataModel.setCodeLen(new BigDecimal(rowObjects.getValue(2).toString()).intValue());
                    if (!baseDataModel.getCode().toUpperCase().equals("SYS_TASKPERIODENUM")) {
                        baseDataModel.setCodeStruct(((String)rowObjects.getValue(3)).trim().replace(",", ";"));
                    }
                    baseDataModel.setLeafOnly(Boolean.parseBoolean(rowObjects.getValue(5).toString()));
                    baseDataModel.setForceInList(Boolean.parseBoolean(rowObjects.getValue(6).toString()));
                    baseDataModel.setMultiSelect(Boolean.parseBoolean(rowObjects.getValue(8).toString()));
                    if (rowObjects.getColumns().size() >= 12) {
                        baseDataModel.setTreeTyep(new BigDecimal(rowObjects.getValue(10).toString()).intValue());
                        baseDataModel.setFix(Boolean.parseBoolean(rowObjects.getValue(11).toString()));
                    }
                    if (rowObjects.getColumns().size() >= 13) {
                        baseDataModel.setValueEmpty(Boolean.parseBoolean(rowObjects.getValue(12).toString()));
                    }
                    this.bdList.put(baseDataModel.getCode(), baseDataModel);
                }
            }
        }
        catch (DbfException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void ParseBDs() throws Exception {
        for (EnumsItemModel baseDataModel : this.bdList.values()) {
            StringBuilder fileName = new StringBuilder(this.dirName).append("PARA").append(File.separator).append("MJ").append(baseDataModel.getCode().trim()).append(".DBF");
            File f = new File(FilenameUtils.normalize(fileName.toString()));
            if (!f.exists()) {
                fileName = new StringBuilder(this.dirName).append("PARA").append(File.separator).append("MJ").append(baseDataModel.getCode().trim().toUpperCase()).append(".DBF");
            }
            String file = fileName.toString();
            try {
                IDbfTable reader = DbfTableUtil.getDbfTable(file);
                DataRowCollection dataRows = reader.getTable().getRows();
                for (int i = 0; i < dataRows.size(); ++i) {
                    DataRow rowObjects = (DataRow)dataRows.get(i);
                    if ((((String)rowObjects.getValue(0)).trim() + ((String)rowObjects.getValue(1)).trim()).indexOf("peD") == 0) continue;
                    DataInfo dataInfo = new DataInfo();
                    dataInfo.setCode(((String)rowObjects.getValue(0)).trim());
                    dataInfo.setName(((String)rowObjects.getValue(1)).trim());
                    if (rowObjects.getColumns().size() > 2) {
                        dataInfo.setParent(((String)rowObjects.getValue(2)).trim());
                    } else {
                        dataInfo.setParent("");
                    }
                    dataInfo.setRowNum(i + 1);
                    baseDataModel.getEnumItemList().put(dataInfo.getCode(), dataInfo);
                    baseDataModel.getItemDataList().add(dataInfo);
                }
            }
            catch (DbfException e) {
                logger.error(e.getMessage(), e);
            }
            catch (RuntimeException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    public final void initInfos(ParaInfo paraInfo) throws Exception {
        this.InitBDList(paraInfo);
        if (this.bdList == null || this.bdList.isEmpty()) {
            return;
        }
        this.ParseBDs();
    }
}

