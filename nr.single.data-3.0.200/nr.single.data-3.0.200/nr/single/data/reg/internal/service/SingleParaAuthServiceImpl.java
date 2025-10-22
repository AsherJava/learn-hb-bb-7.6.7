/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.MemStream
 *  com.jiuqi.bi.util.Stream
 *  com.jiuqi.bi.util.StreamException
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.sql.type.Convert
 *  com.jiuqi.nr.configuration.config.ConfigurationConfig
 *  com.jiuqi.nr.configuration.facade.BusinessConfigurationDefine
 *  com.jiuqi.nr.single.core.dbf.DbfTableUtil
 *  com.jiuqi.nr.single.core.dbf.IDbfTable
 *  com.jiuqi.nr.single.core.exception.SingleFileException
 *  com.jiuqi.nr.single.core.para.JQTHeader
 *  com.jiuqi.nr.single.core.para.ini.Iini
 *  com.jiuqi.nr.single.core.para.ini.Ini
 *  com.jiuqi.nr.single.core.syntax.SyntaxProvider
 *  com.jiuqi.nr.single.core.syntax.TSyntax
 *  com.jiuqi.nr.single.core.syntax.bean.CommonDataType
 *  com.jiuqi.nr.single.core.syntax.common.CommonDataTypeType
 *  com.jiuqi.nr.single.core.syntax.common.SyntaxCode
 *  com.jiuqi.nr.single.core.syntax.common.TCheckType
 *  com.jiuqi.nr.single.core.util.datatable.DataRow
 *  com.jiuqi.nr.single.lib.reg.internal.SingleElaesImpl
 *  com.jiuqi.util.OrderGenerator
 *  nr.single.map.data.PathUtil
 */
package nr.single.data.reg.internal.service;

import com.jiuqi.bi.util.MemStream;
import com.jiuqi.bi.util.Stream;
import com.jiuqi.bi.util.StreamException;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.sql.type.Convert;
import com.jiuqi.nr.configuration.config.ConfigurationConfig;
import com.jiuqi.nr.configuration.facade.BusinessConfigurationDefine;
import com.jiuqi.nr.single.core.dbf.DbfTableUtil;
import com.jiuqi.nr.single.core.dbf.IDbfTable;
import com.jiuqi.nr.single.core.exception.SingleFileException;
import com.jiuqi.nr.single.core.para.JQTHeader;
import com.jiuqi.nr.single.core.para.ini.Iini;
import com.jiuqi.nr.single.core.para.ini.Ini;
import com.jiuqi.nr.single.core.syntax.SyntaxProvider;
import com.jiuqi.nr.single.core.syntax.TSyntax;
import com.jiuqi.nr.single.core.syntax.bean.CommonDataType;
import com.jiuqi.nr.single.core.syntax.common.CommonDataTypeType;
import com.jiuqi.nr.single.core.syntax.common.SyntaxCode;
import com.jiuqi.nr.single.core.syntax.common.TCheckType;
import com.jiuqi.nr.single.core.util.datatable.DataRow;
import com.jiuqi.nr.single.lib.reg.internal.SingleElaesImpl;
import com.jiuqi.util.OrderGenerator;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nr.single.data.bean.CheckSoftNode;
import nr.single.data.bean.CheckSoftParam;
import nr.single.data.reg.internal.service.SyntaxProviderParaAuthImpl;
import nr.single.data.reg.service.SingleParaAuthService;
import nr.single.data.reg.service.SingleSoftAuthService;
import nr.single.map.data.PathUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class SingleParaAuthServiceImpl
implements SingleParaAuthService {
    private static final Logger logger = LoggerFactory.getLogger(SingleParaAuthServiceImpl.class);
    @Autowired
    private ConfigurationConfig configService;
    @Autowired
    private SingleSoftAuthService softAuthService;

    @Override
    public CheckSoftNode checktaskparam(String taskKey, String formSchemeKey, CheckSoftParam param) {
        BusinessConfigurationDefine config;
        CheckSoftNode node = new CheckSoftNode();
        node.setCheckState(2);
        byte[] iniDatas = null;
        String paraExpression = "";
        if (param.getAuthConfig() != null) {
            if (param.getAuthConfig().getParaData() != null) {
                iniDatas = param.getAuthConfig().getParaData();
            }
            paraExpression = param.getAuthConfig().getParaExpression();
        }
        if (iniDatas == null && StringUtils.isNotEmpty((String)taskKey) && StringUtils.isNotEmpty((String)formSchemeKey) && (config = this.configService.getBusinessConfigurationController().getConfiguration(taskKey, formSchemeKey, "TASKSIGN.TSK")) != null) {
            iniDatas = Convert.base64ToBytes((CharSequence)config.getConfigurationContent());
        }
        if (iniDatas != null) {
            Ini ini = new Ini();
            try {
                ini.loadIniFromBytes(iniDatas, "TASKSIGN.TSK");
                Map<String, Object> configMap = this.softAuthService.readAuthFromConfig(param.getAuthConfig());
                this.checkTaskIni(taskKey, formSchemeKey, node, paraExpression, (Iini)ini, configMap);
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        } else {
            node.setMessage("\u672a\u914d\u7f6e\u4efb\u52a1\u76f8\u5173\u4fe1\u606f");
        }
        return node;
    }

    @Override
    public void checkTaskIni(String taskKey, String formSchemeKey, CheckSoftNode node, String paraExpression, Iini ini, Map<String, Object> configMap) {
        List<String> flagList;
        String taskName = ini.ReadString("General", "Name", "");
        String taskFlag = ini.ReadString("General", "Flag", "");
        String fileFlag = ini.ReadString("General", "FileFlag", "");
        String taskYear = ini.ReadString("General", "Year", "");
        String taskTime = ini.ReadString("General", "Time", "");
        String taskGroup = ini.ReadString("General", "Group", "");
        String taskVersion = ini.ReadString("General", "Version", "");
        String AppName = (String)configMap.get("AppName");
        String authFileFlag = (String)configMap.get("FileFlag");
        String authTaskFlag = (String)configMap.get("TaskFlag");
        String authTaskFlagPrefix = (String)configMap.get("TaskFlagPrefix");
        String authTaskYearFrom = (String)configMap.get("TaskYearFrom");
        String authInvidKeyOff = (String)configMap.get("InvidKeyOff");
        if (StringUtils.isEmpty((String)paraExpression)) {
            paraExpression = (String)configMap.get("ParaExpression");
        }
        HashMap<String, Object> varConfigs = new HashMap<String, Object>();
        for (String code : configMap.keySet()) {
            varConfigs.put("auth" + code, configMap.get(code));
        }
        varConfigs.put("taskName", taskName);
        varConfigs.put("taskFlag", taskFlag);
        varConfigs.put("fileFlag", fileFlag);
        varConfigs.put("taskYear", taskYear);
        varConfigs.put("authTaskYearFrom", authTaskYearFrom);
        varConfigs.put("taskTime", taskTime);
        varConfigs.put("taskGroup", taskGroup);
        varConfigs.put("taskVersion", taskVersion);
        int checkState = 1;
        if (StringUtils.isNotEmpty((String)authInvidKeyOff)) {
            boolean authInvidKeyOffB = false;
            String authInvidTaskFlag = this.readInvidTaskFlag(taskKey, formSchemeKey, taskFlag, authInvidKeyOff);
            if (StringUtils.isNotEmpty((String)taskFlag) && taskFlag.equalsIgnoreCase(authInvidTaskFlag)) {
                authInvidKeyOffB = true;
            }
            varConfigs.put("authInvidKeyOffB", authInvidKeyOffB);
            varConfigs.put("authInvidTaskFlag", authInvidTaskFlag);
        }
        if (StringUtils.isNotEmpty((String)paraExpression) && !this.judgeExp(paraExpression, varConfigs)) {
            checkState = 2;
        }
        if (checkState == 1 && StringUtils.isNotEmpty((String)authFileFlag)) {
            if (authFileFlag.equalsIgnoreCase(fileFlag)) {
                checkState = 1;
            } else {
                String[] fileFlags = authFileFlag.split(",");
                if (fileFlags.length > 1) {
                    flagList = Arrays.asList(fileFlags);
                    if (flagList.indexOf(fileFlag) < 0) {
                        checkState = 2;
                    }
                } else {
                    checkState = 2;
                }
            }
        }
        if (checkState == 1 && StringUtils.isNotEmpty((String)authTaskFlag)) {
            if (authTaskFlag.equalsIgnoreCase(taskFlag)) {
                checkState = 1;
            } else {
                String[] taskFlags = authTaskFlag.split(",");
                if (taskFlags.length > 1) {
                    flagList = Arrays.asList(taskFlags);
                    if (flagList.indexOf(taskFlag) < 0) {
                        checkState = 2;
                    }
                } else {
                    checkState = 2;
                }
            }
        }
        if (checkState == 1 && StringUtils.isNotEmpty((String)authTaskFlagPrefix) && !taskFlag.startsWith(authTaskFlagPrefix)) {
            checkState = 2;
        }
        if (checkState == 1 && StringUtils.isNotEmpty((String)authTaskYearFrom) && authTaskYearFrom.compareToIgnoreCase(taskYear) > 0) {
            checkState = 2;
        }
        node.setCheckState(checkState);
        if (checkState == 2) {
            node.setMessage("\u672c\u7cfb\u7edf\u53ea\u80fd\u7528\u4e8e" + AppName + "\u7279\u5b9a\u53c2\u6570\uff0c\u5982\u6709\u95ee\u9898\u8bf7\u4e0e\u4e45\u5176\u516c\u53f8\u8054\u7cfb");
        }
        if (checkState == 1) {
            String authLimitYear = (String)configMap.get("LimitYear");
            String authLimitMonth = (String)configMap.get("LimitMonth");
            if (StringUtils.isNotEmpty((String)authLimitYear) && StringUtils.isNotEmpty((String)authLimitMonth) && StringUtils.isNotEmpty((String)taskKey) && StringUtils.isNotEmpty((String)formSchemeKey)) {
                int authLimitYearNum = 0;
                int authLimitMonthNum = 0;
                try {
                    authLimitYearNum = Integer.parseInt(authLimitYear);
                    authLimitMonthNum = Integer.parseInt(authLimitMonth);
                }
                catch (Exception e) {
                    logger.error(e.getMessage(), e);
                    logger.info("\u9519\u8bef\u7684\u53c2\u6570\u9650\u5236\u5e74\u6708\uff1a" + authLimitYear + "," + authLimitMonth);
                }
                if (!this.judgeLimitYearByTask(taskKey, formSchemeKey, authLimitYearNum, authLimitMonthNum)) {
                    checkState = 2;
                    node.setCheckState(checkState);
                    node.setMessage("\u4efb\u52a1\u53c2\u6570\u7248\u672c\u5df2\u66f4\u65b0\uff0c\u60a8\u53ef\u80fd\u9700\u8981\u66f4\u9ad8\u7248\u672c\u7684\u8f6f\u4ef6\u624d\u80fd\u4f7f\u7528\u8be5\u53c2\u6570\uff01");
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean judgeLimitYearByTask(String taskKey, String formSchemeKey, int authLimitYear, int authLimitMonth) {
        BusinessConfigurationDefine config;
        boolean result = true;
        if (StringUtils.isNotEmpty((String)taskKey) && StringUtils.isNotEmpty((String)formSchemeKey) && (config = this.configService.getBusinessConfigurationController().getConfiguration(taskKey, formSchemeKey, "BBBT.DBF")) != null) {
            byte[] fileDatas = Convert.base64ToBytes((CharSequence)config.getConfigurationContent());
            try {
                String path = PathUtil.getExportTempFilePath((String)"jioJudgeTask");
                String workPath = PathUtil.createNewPath((String)path, (String)OrderGenerator.newOrder());
                String dbfFileName = workPath + "BBBT.DBF";
                try {
                    MemStream stream = new MemStream();
                    stream.write(fileDatas, 0, fileDatas.length);
                    stream.seek(0L, 0);
                    stream.saveToFile(dbfFileName);
                }
                catch (StreamException e) {
                    logger.info(e.getMessage(), e);
                }
                catch (IOException e) {
                    logger.info(e.getMessage(), e);
                }
                try (IDbfTable checkDbf = DbfTableUtil.getDbfTable((String)dbfFileName);){
                    for (int i = 0; i < checkDbf.getDataRowCount(); ++i) {
                        DataRow dbfRow = (DataRow)checkDbf.getTable().getRows().get(i);
                        if (!checkDbf.isHasLoadAllRec()) {
                            checkDbf.loadDataRow(dbfRow);
                        }
                        try {
                            String bbbs = dbfRow.getValueString("BBBS");
                            String bblx = dbfRow.getValueString("BBLX");
                            if (!"R".equalsIgnoreCase(bblx) && !"X".equalsIgnoreCase(bblx) || this.judgeLimitYearByRep(taskKey, formSchemeKey, bbbs, authLimitYear, authLimitMonth)) continue;
                            result = false;
                            break;
                        }
                        finally {
                            if (!checkDbf.isHasLoadAllRec()) {
                                checkDbf.clearDataRow(dbfRow);
                            }
                        }
                    }
                }
                catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
                PathUtil.deleteFile((String)dbfFileName);
                PathUtil.deleteDir((String)workPath);
            }
            catch (SingleFileException e1) {
                logger.info(e1.getMessage(), e1);
            }
        }
        return result;
    }

    private boolean judgeLimitYearByRep(String taskKey, String formSchemeKey, String tableFlag, int authLimitYearNum, int authLimitMonthNum) {
        BusinessConfigurationDefine config;
        boolean result = true;
        if (StringUtils.isNotEmpty((String)taskKey) && StringUtils.isNotEmpty((String)formSchemeKey) && (config = this.configService.getBusinessConfigurationController().getConfiguration(taskKey, formSchemeKey, tableFlag + ".JQT")) != null) {
            byte[] fileDatas = Convert.base64ToBytes((CharSequence)config.getConfigurationContent());
            try {
                MemStream stream = new MemStream();
                stream.write(fileDatas, 0, fileDatas.length);
                stream.seek(0L, 0);
                JQTHeader header = new JQTHeader();
                header.init((Stream)stream);
                if (header.getCreationYear() <= 0 || header.getCreationMonth() <= 0) {
                    return result;
                }
                int fileYear = header.getCreationYear();
                int fileMonth = header.getCreationMonth();
                if (authLimitYearNum < fileYear || authLimitYearNum == fileYear && authLimitMonthNum < fileMonth) {
                    result = false;
                    logger.info("\u4efb\u52a1\u53c2\u6570\u7248\u672c\u5df2\u66f4\u65b0" + tableFlag + ",\u521b\u5efa\u5e74\u6708\uff1a" + fileYear + "," + fileMonth);
                }
            }
            catch (StreamException e) {
                logger.info(e.getMessage(), e);
            }
        }
        return result;
    }

    private boolean InvidTaskFlag(String taskKey, String formSchemeKey, String taskFlag, String authInvidKeyOff) {
        boolean result = false;
        String newTaskFlag = this.readInvidTaskFlag(taskKey, formSchemeKey, taskFlag, authInvidKeyOff);
        if (StringUtils.isNotEmpty((String)taskFlag) && taskFlag.equalsIgnoreCase(newTaskFlag)) {
            result = true;
        }
        return result;
    }

    private String readInvidTaskFlag(String taskKey, String formSchemeKey, String taskFlag, String authInvidKeyOff) {
        byte[] iniDatas;
        BusinessConfigurationDefine config;
        String result = "";
        if (StringUtils.isNotEmpty((String)taskFlag) && StringUtils.isNotEmpty((String)formSchemeKey) && (config = this.configService.getBusinessConfigurationController().getConfiguration(taskFlag, formSchemeKey, "MJTKFG.JQX")) != null && (iniDatas = Convert.base64ToBytes((CharSequence)config.getConfigurationContent())) != null) {
            result = this.readInvidTaskFlagByData(iniDatas, taskFlag, authInvidKeyOff);
        }
        return result;
    }

    @Override
    public String readInvidTaskFlagByData(byte[] iniDatas, String taskFlag, String authInvidKeyOff) {
        String result = "";
        if (iniDatas != null) {
            SingleElaesImpl aes = new SingleElaesImpl();
            try {
                byte[] iniDatas2 = new byte[iniDatas.length - 4];
                System.arraycopy(iniDatas, 4, iniDatas2, 0, iniDatas2.length);
                String encodeCode = taskFlag + String.valueOf(Integer.parseInt(authInvidKeyOff, 16));
                byte[] infos = aes.decryptAESStringToBytes(iniDatas2, encodeCode, false);
                Ini ini = new Ini();
                try {
                    ini.loadIniFromBytes(infos, "");
                    result = ini.ReadString("config", "TaskFlag", "");
                }
                catch (Exception e1) {
                    logger.info(e1.getMessage(), e1);
                }
            }
            catch (Exception e) {
                logger.info(e.getMessage(), e);
            }
        }
        return result;
    }

    private boolean judgeExp(String paraExpression, Map<String, Object> configMap) {
        boolean result = false;
        TSyntax checkSyntax = new TSyntax();
        SyntaxProviderParaAuthImpl provider = new SyntaxProviderParaAuthImpl();
        provider.setVariableMap(configMap);
        checkSyntax.setProvider((SyntaxProvider)provider);
        checkSyntax.getExpressions().clear();
        checkSyntax.getExpressions().add(paraExpression);
        SyntaxCode buildCode = checkSyntax.buildNiporlan(TCheckType.CHECK_JUDGE);
        String msg = checkSyntax.syntaxContent(buildCode);
        if (buildCode == SyntaxCode.SY_OK) {
            if (checkSyntax.judge((String)checkSyntax.getNiporlans().get(0))) {
                result = true;
            } else {
                logger.info("\u53c2\u6570\u6761\u4ef6\u9519\u8bef\uff1a" + paraExpression + "," + msg);
            }
        }
        return result;
    }

    private void testExp() {
        try {
            TSyntax checkSyntax = new TSyntax();
            SyntaxProviderParaAuthImpl provider = new SyntaxProviderParaAuthImpl();
            HashMap<String, Object> configMap = new HashMap<String, Object>();
            configMap.put("AAA", "12");
            configMap.put("BBB", "34");
            configMap.put("CCC", 313);
            configMap.put("DDD", 600);
            configMap.put("TASKYEAR", "2021");
            configMap.put("TASKFLAG", "GZQY2021");
            provider.setVariableMap(configMap);
            checkSyntax.setProvider((SyntaxProvider)provider);
            checkSyntax.getExpressions().clear();
            checkSyntax.getExpressions().add("[CCC] = 313");
            checkSyntax.getExpressions().add("[CCC] > 313");
            checkSyntax.getExpressions().add("[CCC] < 313");
            checkSyntax.getExpressions().add("[CCC] < 31");
            checkSyntax.getExpressions().add("[CCC] < 413");
            checkSyntax.getExpressions().add("TASKYEAR  = \"2021\"");
            checkSyntax.getExpressions().add("TASKYEAR  < \"2021\"");
            checkSyntax.getExpressions().add("TASKYEAR  > \"2021\"");
            checkSyntax.getExpressions().add("TASKYEAR  <= \"2021\"");
            checkSyntax.getExpressions().add("L$(TASKFLAG,4) = \"GZQY\"");
            checkSyntax.getExpressions().add("L$(TASKFLAG,2) = \"GZQY\"");
            checkSyntax.getExpressions().add("R$(TASKFLAG,4) = \"GZQY\"");
            checkSyntax.getExpressions().add("M$(TASKFLAG,1,2) = \"GZQY\"");
            SyntaxCode aa = checkSyntax.buildNiporlan(TCheckType.CHECK_JUDGE);
            if (aa == SyntaxCode.SY_OK) {
                for (int i = 0; i < checkSyntax.getExpressions().size(); ++i) {
                    logger.info((String)checkSyntax.getExpressions().get(i));
                    logger.info((String)checkSyntax.getNiporlans().get(i));
                    if (checkSyntax.judge((String)checkSyntax.getNiporlans().get(i))) {
                        logger.info("true");
                        continue;
                    }
                    logger.info("false");
                }
            }
            checkSyntax.getExpressions().clear();
            checkSyntax.getExpressions().add("AAA +  BBB + \"56768\"");
            checkSyntax.getExpressions().add("[CCC] +  [DDD] + 1000");
            SyntaxCode bb = checkSyntax.buildNiporlan(TCheckType.CHECK_ATTRACT);
            if (bb == SyntaxCode.SY_OK) {
                for (int i = 0; i < checkSyntax.getExpressions().size(); ++i) {
                    logger.info((String)checkSyntax.getExpressions().get(i));
                    logger.info((String)checkSyntax.getNiporlans().get(i));
                    CommonDataType retData = new CommonDataType();
                    checkSyntax.attract((String)checkSyntax.getNiporlans().get(i), retData);
                    if (retData.getCdType() == CommonDataTypeType.CD_STRING_TYPE) {
                        logger.info(retData.getCdString());
                        continue;
                    }
                    if (retData.getCdType() == CommonDataTypeType.CD_INT_TYPE) {
                        logger.info(String.valueOf(retData.getCdInt()));
                        continue;
                    }
                    if (retData.getCdType() == CommonDataTypeType.CD_REAL_TYPE) {
                        logger.info(String.valueOf(retData.getCdReal()));
                        continue;
                    }
                    logger.info("\u53d6\u503c\u5931\u8d25");
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}

