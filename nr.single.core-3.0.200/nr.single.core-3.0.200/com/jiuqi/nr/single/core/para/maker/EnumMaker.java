/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.nr.single.core.para.maker;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.single.core.dbf.DBFCreator;
import com.jiuqi.nr.single.core.dbf.DbfException;
import com.jiuqi.nr.single.core.dbf.DbfTableUtil;
import com.jiuqi.nr.single.core.dbf.IDbfTable;
import com.jiuqi.nr.single.core.para.ParaInfo;
import com.jiuqi.nr.single.core.para.parser.eoums.DataInfo;
import com.jiuqi.nr.single.core.para.parser.eoums.EnumsItemModel;
import com.jiuqi.nr.single.core.util.datatable.DataRow;
import java.io.File;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnumMaker {
    private static final Logger logger = LoggerFactory.getLogger(EnumMaker.class);
    private String dirName;
    private Map<String, EnumsItemModel> bdList;

    public void InitDirName(String dirName_0) {
        this.dirName = dirName_0;
    }

    public final void make(ParaInfo paraInfo) throws Exception {
        this.saveBDList(paraInfo);
        if (this.bdList == null || this.bdList.isEmpty()) {
            return;
        }
        this.saveBDs();
    }

    private boolean isAllZero(String str) {
        if (StringUtils.isEmpty((String)str)) {
            return false;
        }
        for (int i = 0; i < str.length(); ++i) {
            if (str.charAt(i) == '0') continue;
            return false;
        }
        return true;
    }

    private String getParentCode(String[] CodeStruct, String Code) {
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
            if (this.isAllZero(str2)) {
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
        String str = this.getParentCode(newCodeStruct, newCode);
        return Code.substring(0, len1) + str;
    }

    public void addEmptyParent(EnumsItemModel baseDataModel, String parentEnumCode) {
        DataInfo dataInfo = new DataInfo();
        dataInfo.setCode(parentEnumCode);
        dataInfo.setName("\u8f85\u52a9\u589e\u52a0\u8282\u70b9");
        dataInfo.setParent("");
        dataInfo.setStartFlag(false);
        dataInfo.setRowNum(baseDataModel.getEnumItemList().size() + 1);
        baseDataModel.getEnumItemList().put(dataInfo.getCode(), dataInfo);
        baseDataModel.getItemDataList().add(dataInfo);
        this.getParentCode(baseDataModel.getCodeStruct().split("[;]", -1), parentEnumCode);
    }

    private void saveBDList(ParaInfo paraInfo) throws Exception {
        String afile = this.dirName + "PARA" + File.separatorChar + "EnumList.DBF";
        try {
            DBFCreator creator = new DBFCreator();
            creator.addField("Identy", 'C', 32);
            creator.addField("Caption", 'C', 150);
            creator.addField("EnumWidth", 'N', 6);
            creator.addField("MakeupStr", 'C', 255);
            creator.addField("RefCount", 'N', 6);
            creator.addField("LeafOnly", 'L', 0);
            creator.addField("FullEnum", 'L', 0);
            creator.addField("Group", 'C', 40);
            creator.addField("MultiSelec", 'L', 0);
            creator.addField("MergeMeani", 'L', 0);
            creator.addField("TreeType", 'N', 6);
            creator.addField("FixedCodeL", 'L', 0);
            creator.addField("ValueEmpty", 'L', 0);
            creator.createTable(afile);
            IDbfTable reader = DbfTableUtil.getDbfTable(afile);
            this.bdList = paraInfo.getEnunList();
            for (EnumsItemModel baseDataModel : this.bdList.values()) {
                DataRow rowObjects = new DataRow();
                rowObjects.setColumns(reader.getTable().getColumns());
                rowObjects.setValue(0, (Object)baseDataModel.getCode());
                rowObjects.setValue(1, (Object)baseDataModel.getTitle());
                rowObjects.setValue(2, (Object)baseDataModel.getCodeLen());
                rowObjects.setValue(3, (Object)baseDataModel.getCodeStruct());
                rowObjects.setValue(5, (Object)baseDataModel.getLeafOnly());
                rowObjects.setValue(6, (Object)baseDataModel.getForceInList());
                rowObjects.setValue(8, (Object)baseDataModel.getMultiSelect());
                if (rowObjects.getColumns().size() >= 12) {
                    rowObjects.setValue(10, (Object)baseDataModel.getTreeTyep());
                    rowObjects.setValue(11, (Object)baseDataModel.getFix());
                }
                reader.getTable().getRows().add(rowObjects);
            }
            reader.saveData();
        }
        catch (DbfException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void saveBDs() throws Exception {
        for (EnumsItemModel baseDataModel : this.bdList.values()) {
            StringBuilder fileName = new StringBuilder(this.dirName).append("PARA").append(File.separator).append("MJ").append(baseDataModel.getCode().trim()).append(".DBF");
            String file = fileName.toString();
            try {
                DBFCreator creator = new DBFCreator();
                creator.addField("MHBM", 'C', baseDataModel.getCodeLen());
                creator.addField("MJHY", 'C', 255);
                creator.addField("FBM", 'C', baseDataModel.getCodeLen());
                creator.createTable(file);
                IDbfTable reader = DbfTableUtil.getDbfTable(file);
                for (int i = 0; i < baseDataModel.getItemDataList().size(); ++i) {
                    DataRow rowObjects = new DataRow();
                    DataInfo dataInfo = baseDataModel.getItemDataList().get(i);
                    rowObjects.setValue(0, (Object)dataInfo.getCode());
                    rowObjects.setValue(1, (Object)dataInfo.getName());
                    rowObjects.setValue(2, (Object)dataInfo.getParent());
                    reader.getTable().getRows().add(rowObjects);
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

    public final void saveInfos(ParaInfo paraInfo) throws Exception {
        this.saveBDList(paraInfo);
        if (this.bdList == null || this.bdList.isEmpty()) {
            return;
        }
        this.saveBDs();
    }
}

