/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.single.core.dbf.DBFCreator
 *  com.jiuqi.nr.single.core.dbf.DbfTableUtil
 *  com.jiuqi.nr.single.core.dbf.IDbfTable
 *  com.jiuqi.nr.single.core.exception.SingleFileException
 *  com.jiuqi.nr.single.core.util.SinglePathUtil
 *  com.jiuqi.nr.single.core.util.datatable.DataRow
 */
package nr.single.map.data;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.single.core.dbf.DBFCreator;
import com.jiuqi.nr.single.core.dbf.DbfTableUtil;
import com.jiuqi.nr.single.core.dbf.IDbfTable;
import com.jiuqi.nr.single.core.exception.SingleFileException;
import com.jiuqi.nr.single.core.util.SinglePathUtil;
import com.jiuqi.nr.single.core.util.datatable.DataRow;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nr.single.map.data.DataChkInfo;
import nr.single.map.data.PathUtil;
import nr.single.map.data.TaskDataContext;

public class CheckDataUtil {
    public static final String DATACHK_SLIPT = "_";
    public static final String CDATA_CHK_FORMULA_SPLIPT = "\uff0e";
    public static final String CDATA_CHK_FILE = "Sys_CCSHSM.DBF";
    public static final String CDATA_CHK_INDEX = ".JIO";
    public static final boolean CDATA_CHK_ADD_FIRST_LINE = true;
    public static final String CDATA_CHK_TABLE_MIDDLE = "Sys_TableMiddle";
    public static final int CFI_ERROR_ZDM = 0;
    public static final int CFI_TABLE_FLAG = 1;
    public static final int CFI_FOMULA_EXP = 2;
    public static final int CFI_FLOAT_FLAG = 3;
    public static final int CFI_EORROR_HINT = 4;
    public static final int CFI_FOMULA_FLAG = 5;
    public static final int CFI_ROW_NUM = 6;
    public static final int CFI_COL_NUM = 7;
    public static final int CFI_FLOAT_ORDER = 8;
    public static final int CFI_FOMULA_AREA = 9;
    public static final int CFI_SIZE_FOMULA_AREA = 20;
    public static final int CFI_SIZE_FOMULA_FLAG = 15;
    public static final int CFI_SIZE_FLOAT_FLAG = 100;
    public static final int CFI_SIZE_FLOAT_ORDER = 6;
    public static final int MAX_IDENTIFIER_LEN = 50;
    public static final int CFI_SIZE_FML_ECHEME = 30;
    public static final int RTC_STRING_TYPE_MAX_LENGTH = 254;
    public static final String CFI_NAME_ERROR_ZDM = "ErrorZDM";
    public static final String CFI_NAME_TABLE_FLAG = "TableFlag";
    public static final String CFI_NAME_FOMULA_EXP = "FormulaExp";
    public static final String CFI_NAME_FLOAT_FLAG = "FloatFlag";
    public static final String CFI_NAME_ERROR_HINT = "ErrorHint";
    public static final String CFI_NAME_FOMULA_FLAG = "FomulaFlag";
    public static final String CFI_NAME_ROW_NUM = "RowNum";
    public static final String CFI_NAME_COL_NUM = "ColNum";
    public static final String CFI_NAME_FOMULA_AREA = "FomulaArea";
    public static final String CFI_NAME_FLOAT_ORDER = "FloatOrder";
    public static final String CFI_NAME_FLOATING_ID = "FloatingId";
    public static final String CFI_NAME_FML_SCHEME = "FmlScheme";
    public static final String CFORMULA_FLAG_SPLICT_CHAR = "\uff0e";
    public static final String CFORMULA_LEFT_CHAR = "[";
    public static final String CFORMULA_RIGHT_CHAR = "]";
    public static final String CFORMULA_NUM_SPLICT_CHAR = ",";
    public static final String CFORMULA_DEFAULT_SCHEME = "\u9ed8\u8ba4\u516c\u5f0f\u65b9\u6848";
    public static final String CBJ_FORMULA_FORM_KEY = "00000000-0000-0000-0000-000000000000";

    private CheckDataUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static void CreateCheckDBF(TaskDataContext context, String path, int zmdLenth, boolean hasOrderField) throws Exception {
        CheckDataUtil.CreateCheckDBFFile(context.getNeedCreateDBF(), path, zmdLenth, hasOrderField);
    }

    public static void CreateCheckDBFFile(boolean createNew, String path, int zmdLenth, boolean hasOrderField) throws Exception {
        String fileName = path + CDATA_CHK_FILE;
        if (createNew || !PathUtil.getFileExists(fileName)) {
            DBFCreator creartor = new DBFCreator();
            creartor.addField(CFI_NAME_ERROR_ZDM, 'C', zmdLenth, 0);
            creartor.addField(CFI_NAME_TABLE_FLAG, 'C', 50, 0);
            creartor.addField(CFI_NAME_FOMULA_EXP, 'C', 254, 0);
            creartor.addField(CFI_NAME_FLOAT_FLAG, 'C', 100, 0);
            creartor.addField(CFI_NAME_ERROR_HINT, 'C', 254, 0);
            creartor.addField(CFI_NAME_FOMULA_FLAG, 'C', 15, 0);
            creartor.addField(CFI_NAME_ROW_NUM, 'I', 0, 0);
            creartor.addField(CFI_NAME_COL_NUM, 'I', 0, 0);
            creartor.addField(CFI_NAME_FLOAT_ORDER, 'C', 6, 0);
            creartor.addField(CFI_NAME_FLOATING_ID, 'I', 0, 0);
            creartor.addField(CFI_NAME_FML_SCHEME, 'C', 30, 0);
            creartor.createTable(fileName);
        }
    }

    public static String getCheckDBFFileName(String path) {
        return path + CDATA_CHK_FILE;
    }

    public static boolean getCheckDBFFileExist(String path) throws SingleFileException {
        File file = new File(SinglePathUtil.normalize((String)CheckDataUtil.getCheckDBFFileName(path)));
        return file.exists();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static Map<String, Map<String, List<DataChkInfo>>> loadDataCheckInfos(String dataPath) throws Exception {
        HashMap<String, Map<String, List<DataChkInfo>>> result = new HashMap<String, Map<String, List<DataChkInfo>>>();
        if (!CheckDataUtil.getCheckDBFFileExist(dataPath)) {
            return result;
        }
        try (IDbfTable checkDbf = DbfTableUtil.getDbfTable((String)CheckDataUtil.getCheckDBFFileName(dataPath));){
            for (int i = 0; i < checkDbf.getDataRowCount(); ++i) {
                DataRow dbfRow = (DataRow)checkDbf.getTable().getRows().get(i);
                if (!checkDbf.isHasLoadAllRec()) {
                    checkDbf.loadDataRow(dbfRow);
                }
                try {
                    String zdm = dbfRow.getValueString(CFI_NAME_ERROR_ZDM.toUpperCase());
                    String formCode = dbfRow.getValueString(CFI_NAME_TABLE_FLAG.toUpperCase());
                    String formulaFlag = dbfRow.getValueString(CFI_NAME_FOMULA_FLAG.toUpperCase());
                    String fmlScheme = dbfRow.getValueString(CFI_NAME_FML_SCHEME.toUpperCase());
                    List<DataChkInfo> checkInfoList = null;
                    Map schemeChecks = null;
                    if (StringUtils.isEmpty((String)fmlScheme)) {
                        fmlScheme = CFORMULA_DEFAULT_SCHEME;
                    }
                    if (!result.containsKey(fmlScheme)) {
                        schemeChecks = new HashMap();
                        result.put(fmlScheme, schemeChecks);
                    } else {
                        schemeChecks = (Map)result.get(fmlScheme);
                    }
                    String newFormCode = formCode;
                    if (StringUtils.isEmpty((String)newFormCode)) {
                        newFormCode = CDATA_CHK_TABLE_MIDDLE;
                    }
                    if (!schemeChecks.containsKey(newFormCode)) {
                        checkInfoList = new ArrayList();
                        schemeChecks.put(newFormCode, checkInfoList);
                    } else {
                        checkInfoList = (List)schemeChecks.get(newFormCode);
                    }
                    DataChkInfo checkInfo = new DataChkInfo();
                    checkInfo.setErrorZDM(zdm);
                    checkInfo.setTableFlag(formCode);
                    String floatCode = dbfRow.getValueString(CFI_NAME_FLOAT_FLAG.toUpperCase());
                    checkInfo.setFloatFlag(floatCode);
                    checkInfo.setEorrorHint(dbfRow.getValueString(CFI_NAME_ERROR_HINT.toUpperCase()));
                    checkInfo.setFomulaExp(dbfRow.getValueString(CFI_NAME_FOMULA_EXP.toUpperCase()));
                    checkInfo.setFormulaFlag(formulaFlag);
                    String rowCode = dbfRow.getValueString(CFI_NAME_ROW_NUM.toUpperCase());
                    String colCode = dbfRow.getValueString(CFI_NAME_COL_NUM.toUpperCase());
                    int rowNum = -1;
                    if (StringUtils.isNotEmpty((String)rowCode) && (rowNum = Integer.parseInt(rowCode)) == 0) {
                        rowNum = -1;
                    }
                    checkInfo.setRowNum(rowNum);
                    int colNum = -1;
                    if (StringUtils.isNotEmpty((String)colCode) && (colNum = Integer.parseInt(colCode)) == 0) {
                        colNum = -1;
                    }
                    checkInfo.setColNum(colNum);
                    String floatOrder = dbfRow.getValueString(CFI_NAME_FLOAT_ORDER.toUpperCase());
                    if (StringUtils.isNotEmpty((String)floatOrder)) {
                        checkInfo.setFloatOrder(floatOrder);
                    }
                    String floatingCode = dbfRow.getValueString(CFI_NAME_FLOATING_ID.toUpperCase());
                    int floatingId = 0;
                    if (StringUtils.isNotEmpty((String)floatingCode) && (floatingId = Integer.parseInt(floatingCode)) < 0) {
                        floatingId = 0;
                    }
                    checkInfo.setFloatingId(floatingId);
                    if (StringUtils.isNotEmpty((String)fmlScheme)) {
                        checkInfo.setFmlScheme(fmlScheme);
                    }
                    checkInfoList.add(checkInfo);
                    continue;
                }
                finally {
                    if (!checkDbf.isHasLoadAllRec()) {
                        checkDbf.clearDataRow(dbfRow);
                    }
                }
            }
        }
        return result;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void saveDataCheckInfos(int zdmLength, String dataPath, Map<String, List<DataChkInfo>> checkInfos, boolean hasOrderField) throws Exception {
        if (!CheckDataUtil.getCheckDBFFileExist(dataPath)) {
            CheckDataUtil.CreateCheckDBFFile(false, dataPath, zdmLength, hasOrderField);
        }
        try (IDbfTable checkDbf = DbfTableUtil.getDbfTable((String)CheckDataUtil.getCheckDBFFileName(dataPath));){
            for (List<DataChkInfo> checkInfoList : checkInfos.values()) {
                for (DataChkInfo checkInfo : checkInfoList) {
                    String zdm = checkInfo.getErrorZDM();
                    String formCode = checkInfo.getTableFlag();
                    DataRow dbfRow = checkDbf.getTable().newRow();
                    dbfRow.setValue(CFI_NAME_ERROR_ZDM.toUpperCase(), (Object)zdm);
                    dbfRow.setValue(CFI_NAME_TABLE_FLAG.toUpperCase(), (Object)formCode);
                    dbfRow.setValue(CFI_NAME_FOMULA_EXP.toUpperCase(), (Object)"");
                    dbfRow.setValue(CFI_NAME_FLOAT_FLAG.toUpperCase(), (Object)checkInfo.getFloatFlag());
                    dbfRow.setValue(CFI_NAME_ERROR_HINT.toUpperCase(), (Object)checkInfo.getEorrorHint());
                    dbfRow.setValue(CFI_NAME_FOMULA_FLAG.toUpperCase(), (Object)checkInfo.getFormulaFlag());
                    dbfRow.setValue(CFI_NAME_ROW_NUM.toUpperCase(), (Object)(checkInfo.getRowNum() < 0 ? 0 : checkInfo.getRowNum()));
                    dbfRow.setValue(CFI_NAME_COL_NUM.toUpperCase(), (Object)(checkInfo.getColNum() < 0 ? 0 : checkInfo.getColNum()));
                    dbfRow.setValue(CFI_NAME_FLOAT_ORDER.toUpperCase(), (Object)checkInfo.getFloatOrder());
                    dbfRow.setValue(CFI_NAME_FLOATING_ID.toUpperCase(), (Object)(checkInfo.getFloatingId() < 0 ? 0 : checkInfo.getFloatingId()));
                    dbfRow.setValue(CFI_NAME_FML_SCHEME.toUpperCase(), (Object)checkInfo.getFmlScheme());
                    checkDbf.getTable().getRows().add((Object)dbfRow);
                }
            }
            checkDbf.saveData();
        }
    }
}

