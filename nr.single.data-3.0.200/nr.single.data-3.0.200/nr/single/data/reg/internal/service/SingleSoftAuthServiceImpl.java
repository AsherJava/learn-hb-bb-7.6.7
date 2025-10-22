/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.sql.type.Convert
 *  com.jiuqi.nr.configuration.common.ConfigContentType
 *  com.jiuqi.nr.configuration.config.ConfigurationConfig
 *  com.jiuqi.nr.configuration.facade.BusinessConfigurationDefine
 *  com.jiuqi.nr.single.core.para.ini.Ini
 *  com.jiuqi.nr.single.lib.reg.internal.SingleElaesImpl
 *  com.jiuqi.nr.single.lib.reg.internal.SingleRegisterImpl
 *  com.jiuqi.nr.single.lib.util.SingleRegCheckUtil
 */
package nr.single.data.reg.internal.service;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.sql.type.Convert;
import com.jiuqi.nr.configuration.common.ConfigContentType;
import com.jiuqi.nr.configuration.config.ConfigurationConfig;
import com.jiuqi.nr.configuration.facade.BusinessConfigurationDefine;
import com.jiuqi.nr.single.core.para.ini.Ini;
import com.jiuqi.nr.single.lib.reg.internal.SingleElaesImpl;
import com.jiuqi.nr.single.lib.reg.internal.SingleRegisterImpl;
import com.jiuqi.nr.single.lib.util.SingleRegCheckUtil;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import nr.single.data.bean.CheckSoftConfig;
import nr.single.data.bean.CheckSoftNode;
import nr.single.data.bean.CheckSoftParam;
import nr.single.data.reg.service.SingleSoftAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class SingleSoftAuthServiceImpl
implements SingleSoftAuthService {
    private static final Logger logger = LoggerFactory.getLogger(SingleSoftAuthServiceImpl.class);
    @Autowired
    private ConfigurationConfig configService;

    @Override
    public CheckSoftNode getMachCode(CheckSoftParam checkParam) {
        CheckSoftNode node = new CheckSoftNode();
        CheckSoftConfig config = null;
        if (checkParam != null) {
            config = checkParam.getAuthConfig();
        }
        node.setMachineCode(this.getMachineCode(config));
        node.setCheckState(1);
        return node;
    }

    @Override
    public CheckSoftNode getCheckParam(CheckSoftParam checkParam) {
        CheckSoftNode node = new CheckSoftNode();
        node.setCheckState(2);
        if (StringUtils.isNotEmpty((String)checkParam.getMachineCode()) && checkParam.getMachineCode().equalsIgnoreCase(this.getMachineCode(checkParam.getAuthConfig()))) {
            node.setCheckCode(this.getCheckCode(checkParam.getAuthConfig()));
            node.setCheckState(1);
        } else {
            node.setMessage("\u673a\u5668\u7801\u9519\u8bef");
        }
        return node;
    }

    @Override
    public CheckSoftNode getSoftRegInfo(CheckSoftParam checkParam) {
        CheckSoftNode node = new CheckSoftNode();
        node.setCheckState(2);
        if (StringUtils.isNotEmpty((String)checkParam.getCheckCode()) && checkParam.getCheckCode().equalsIgnoreCase(this.getCheckCode(checkParam.getAuthConfig()))) {
            node.setAuthConfig(checkParam.getAuthConfig());
            this.readRegFromConfig(node, this.getMachEncode(checkParam.getAuthConfig()));
            node.setAuthConfig(null);
            node.setCheckState(1);
        }
        return node;
    }

    @Override
    public CheckSoftNode checkSoftReg(CheckSoftParam checkParam) {
        CheckSoftNode node = new CheckSoftNode();
        node.setCheckState(2);
        if (StringUtils.isNotEmpty((String)checkParam.getCheckCode()) && checkParam.getCheckCode().equalsIgnoreCase(this.getCheckCode(checkParam.getAuthConfig()))) {
            node.setAuthConfig(checkParam.getAuthConfig());
            node.getVariableMap().put("CompanyName", checkParam.getCompanyName());
            node.getVariableMap().put("SerialNumber", checkParam.getSerialNumber());
            node.getVariableMap().put("Username", checkParam.getUserName());
            node.getVariableMap().put("RegisterCode", checkParam.getRegisterCode());
            node.getVariableMap().put("RegTime", checkParam.getRegTime());
            if (!node.getVariableMap().containsKey("SoftType") && checkParam.getSoftType() > 0) {
                node.getVariableMap().put("SoftType", String.valueOf(checkParam.getSoftType()));
            }
            this.checkRegisterCode(node);
            node.setAuthConfig(null);
            if (node.getCheckState() == 2) {
                node.setMessage("\u6ce8\u518c\u7801\u9519\u8bef");
            }
        } else {
            node.setMessage("\u6821\u9a8c\u7801\u9519\u8bef");
        }
        return node;
    }

    @Override
    public CheckSoftNode doSoftRegister(CheckSoftParam checkParam) {
        CheckSoftNode node = new CheckSoftNode();
        node.setCheckState(2);
        node.getVariableMap().putAll(checkParam.getVariableMap());
        if (StringUtils.isNotEmpty((String)checkParam.getCheckCode()) && checkParam.getCheckCode().equalsIgnoreCase(this.getCheckCode(checkParam.getAuthConfig()))) {
            node.setAuthConfig(checkParam.getAuthConfig());
            node.getVariableMap().put("CompanyName", checkParam.getCompanyName());
            node.getVariableMap().put("SerialNumber", checkParam.getSerialNumber());
            node.getVariableMap().put("Username", checkParam.getUserName());
            node.getVariableMap().put("RegisterCode", checkParam.getRegisterCode());
            node.getVariableMap().put("RegTime", checkParam.getRegTime());
            if (!node.getVariableMap().containsKey("SoftType") && checkParam.getSoftType() > 0) {
                node.getVariableMap().put("SoftType", String.valueOf(checkParam.getSoftType()));
            }
            this.checkRegisterCode(node);
            if (node.getCheckState() == 1) {
                this.writeRegFromConfig(node, this.getMachEncode(checkParam.getAuthConfig()));
            }
            node.setAuthConfig(null);
        } else {
            node.setMessage("\u6821\u9a8c\u7801\u9519\u8bef");
        }
        return node;
    }

    private void readRegFromConfig(CheckSoftNode node, String machEncode) {
        BusinessConfigurationDefine config;
        byte[] inidatas = null;
        if (node.getAuthConfig() != null && node.getAuthConfig().getRegData() != null) {
            inidatas = node.getAuthConfig().getRegData();
        }
        if (inidatas == null && (config = this.configService.getBusinessConfigurationController().getConfiguration("63350655-0000-48f5-b332-000000000000", "04bac4fb-0000-4d93-9058-000000000000", "SOFTREG.INI_" + machEncode)) != null) {
            byte[] datas = Convert.base64ToBytes((CharSequence)config.getConfigurationContent());
            inidatas = datas;
        }
        if (inidatas != null) {
            Ini ini = new Ini();
            try {
                ini.loadIniFromBytes(inidatas, "SOFTAUTH.INI");
                String CompanyName = ini.ReadString("General", "CompanyName", "");
                String SerialNumber = ini.ReadString("General", "SerialNumber", "");
                String Username = ini.ReadString("General", "Username", "");
                String RegisterCode = ini.ReadString("General", "RegisterCode", "");
                String RegTime = ini.ReadString("General", "RegTime", "");
                node.getVariableMap().put("CompanyName", CompanyName);
                node.getVariableMap().put("SerialNumber", SerialNumber);
                node.getVariableMap().put("Username", Username);
                node.getVariableMap().put("RegisterCode", RegisterCode);
                node.getVariableMap().put("RegTime", RegTime);
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    private void writeRegFromConfig(CheckSoftNode node, String machEncode) {
        boolean writeToDb = node.getAuthConfig() == null || node.getAuthConfig().getRegData() == null;
        byte[] regDatas = null;
        boolean isNew = false;
        BusinessConfigurationDefine config = null;
        if (writeToDb) {
            config = this.configService.getBusinessConfigurationController().getConfiguration("63350655-0000-48f5-b332-000000000000", "04bac4fb-0000-4d93-9058-000000000000", "SOFTREG.INI_" + machEncode);
            boolean bl = isNew = config == null;
            if (config != null) {
                byte[] datas;
                regDatas = datas = Convert.base64ToBytes((CharSequence)config.getConfigurationContent());
            } else {
                config = this.configService.getBusinessConfigurationController().createConfiguration();
                config.setKey(UUID.randomUUID().toString());
                config.setTaskKey("63350655-0000-48f5-b332-000000000000");
                config.setFormSchemeKey("04bac4fb-0000-4d93-9058-000000000000");
                config.setCode("SOFTREG.INI_" + machEncode);
                config.setCategory("SINGLEAUTH");
                config.setContentType(ConfigContentType.CCT_BINARY);
                config.setDescription("SOFTREG.INI");
                config.setFileName("SOFTREG.INI");
                config.setTitle("\u6ce8\u518c\u4fe1\u606f");
            }
        }
        Ini ini = new Ini();
        if (regDatas != null) {
            try {
                ini.loadIniFromBytes(regDatas, "SOFTREG.INI");
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        String CompanyName = node.getVariableMap().get("CompanyName");
        String SerialNumber = node.getVariableMap().get("SerialNumber");
        String Username = node.getVariableMap().get("Username");
        String RegisterCode = node.getVariableMap().get("RegisterCode");
        String RegTime = node.getVariableMap().get("RegTime");
        ini.WriteString("General", "CompanyName", CompanyName);
        ini.WriteString("General", "SerialNumber", SerialNumber);
        ini.WriteString("General", "Username", Username);
        ini.WriteString("General", "RegisterCode", RegisterCode);
        ini.WriteString("General", "RegTime", RegTime);
        byte[] datas2 = ini.saveIniToBytes("");
        node.setRegData(datas2);
        if (writeToDb) {
            config.setConfigurationContent(Convert.bytesToBase64((byte[])datas2));
            if (isNew) {
                this.configService.getBusinessConfigurationController().addConfiguration(config);
            } else {
                this.configService.getBusinessConfigurationController().updateConfiguration(config);
            }
        }
    }

    @Override
    public Map<String, Object> readAuthFromConfig(CheckSoftConfig authCongfig) {
        BusinessConfigurationDefine config;
        byte[] iniDatas = null;
        if (authCongfig != null && authCongfig.getConfigData() != null) {
            iniDatas = authCongfig.getConfigData();
        }
        HashMap<String, Object> authMap = new HashMap<String, Object>();
        if (iniDatas == null && (config = this.configService.getBusinessConfigurationController().getConfiguration("63350655-0000-48f5-b332-000000000000", "04bac4fb-0000-4d93-9058-000000000000", "SOFTAUTH.INI")) != null) {
            byte[] datas = Convert.base64ToBytes((CharSequence)config.getConfigurationContent());
            iniDatas = datas;
        }
        if (iniDatas != null) {
            Ini ini = new Ini();
            try {
                ini.loadIniFromBytes(iniDatas, "softAuth.ini");
                String AppName = ini.ReadString("General", "AppName", "");
                String AppExeName = ini.ReadString("General", "AppExeName", "");
                String KeyOff = ini.ReadString("General", "KeyOff", "");
                String SoftType = ini.ReadString("General", "SoftType", "");
                String ShowReg = ini.ReadString("General", "ShowReg", "");
                String ShowOnlineReg = ini.ReadString("General", "ShowOnlineReg", "");
                String OnlineRegUrl = ini.ReadString("General", "OnlineRegUrl", "");
                String FileFlag = ini.ReadString("Para", "FileFlag", "");
                String TaskFlag = ini.ReadString("Para", "TaskFlag", "");
                String TaskFlagPrefix = ini.ReadString("Para", "TaskFlagPrefix", "");
                String TaskYearFrom = ini.ReadString("Para", "TaskYearFrom", "");
                String InvidKeyOff = ini.ReadString("Para", "InvidKeyOff", "");
                String paraExpression = ini.ReadString("Para", "ParaExpression", "");
                String paraLimitYear = ini.ReadString("Para", "LimitYear", "");
                String paraLimitMonth = ini.ReadString("Para", "LimitMonth", "");
                authMap.put("AppName", AppName);
                authMap.put("AppExeName", AppExeName);
                authMap.put("KeyOff", KeyOff);
                authMap.put("SoftType", SoftType);
                authMap.put("ShowReg", ShowReg);
                authMap.put("ShowOnlineReg", ShowOnlineReg);
                authMap.put("FileFlag", FileFlag);
                authMap.put("TaskFlag", TaskFlag);
                authMap.put("TaskFlagPrefix", TaskFlagPrefix);
                authMap.put("TaskYearFrom", TaskYearFrom);
                authMap.put("InvidKeyOff", InvidKeyOff);
                authMap.put("OnlineRegUrl", OnlineRegUrl);
                authMap.put("ParaExpression", paraExpression);
                if (StringUtils.isNotEmpty((String)paraLimitYear)) {
                    authMap.put("LimitYear", paraLimitYear);
                    authMap.put("LimitMonth", paraLimitMonth);
                }
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return authMap;
    }

    private int getConfigMachKeyOff(CheckSoftConfig authCongfig) {
        int num = 0;
        if (authCongfig != null) {
            String keyOff;
            Map<String, Object> authMap;
            num = authCongfig.getKeyOff();
            if (num != 0) {
                return num;
            }
            if (authCongfig.getConfigData() != null && (authMap = this.readAuthFromConfig(authCongfig)).containsKey("KeyOff") && StringUtils.isNotEmpty((String)(keyOff = (String)authMap.get("KeyOff")))) {
                try {
                    num = Integer.parseInt(keyOff);
                }
                catch (Exception ex) {
                    logger.error("\u8bfb\u53d6\u673a\u5668\u504f\u79fb\u91cf\u5931\u8d25\uff1a" + ex.getMessage(), ex);
                }
            }
        }
        return num;
    }

    private int getSoftType(CheckSoftConfig authCongfig) {
        String softType;
        Map<String, Object> authMap;
        int num = 2;
        if (authCongfig != null && (authMap = this.readAuthFromConfig(authCongfig)).containsKey("SoftType") && StringUtils.isNotEmpty((String)(softType = (String)authMap.get("SoftType")))) {
            try {
                num = Integer.parseInt(softType);
            }
            catch (Exception ex) {
                logger.error("\u8bfb\u53d6\u8f6f\u4ef6\u7c7b\u578b\u5931\u8d25\uff1a" + ex.getMessage(), ex);
            }
        }
        return num;
    }

    private String getMachineCode(CheckSoftConfig authCongfig) {
        SingleRegisterImpl reg = new SingleRegisterImpl();
        int num = this.readRandCode(authCongfig);
        num = num + 8929146 + this.getConfigMachKeyOff(authCongfig);
        String code = reg.getLongToHex(num);
        return code;
    }

    private String getCheckCode(CheckSoftConfig authCongfig) {
        SingleRegisterImpl reg = new SingleRegisterImpl();
        int num = reg.getOriginMachine();
        num = num + 19392949 + this.getConfigMachKeyOff(authCongfig);
        String code = reg.getLongToHex(num);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = Calendar.getInstance().getTime();
        code = formatter.format(date) + code;
        code = reg.getStringToMD5(code);
        return code;
    }

    private void checkRegisterCode(CheckSoftNode node) {
        HashMap<String, String> checkResult;
        String softTypes;
        String curMachCode = this.getMachineCode(node.getAuthConfig());
        String companyName = node.getVariableMap().get("CompanyName");
        String serialNumber = node.getVariableMap().get("SerialNumber");
        String username = node.getVariableMap().get("Username");
        String registerCode = node.getVariableMap().get("RegisterCode");
        String regTime = node.getVariableMap().get("RegTime");
        int checkState = 2;
        int softType = this.getSoftType(node.getAuthConfig());
        if (node.getVariableMap().containsKey("SoftType") && StringUtils.isNotEmpty((String)(softTypes = node.getVariableMap().get("SoftType")))) {
            try {
                softType = Integer.parseInt(softTypes);
            }
            catch (Exception ex) {
                logger.error("\u8bfb\u53d6\u8f6f\u4ef6\u7c7b\u578b\u5931\u8d25\uff1a" + ex.getMessage(), ex);
            }
        }
        if (softType == 3) {
            checkResult = new HashMap<String, String>();
            boolean checkDate = this.checkEndDate3(registerCode, curMachCode, node, checkResult);
            if (!checkDate) {
                node.setCheckState(2);
                return;
            }
            registerCode = (String)checkResult.get("RegisterCode");
        } else if (softType == 32) {
            checkResult = new HashMap();
            boolean checkDate = this.checkEndDate2(registerCode, curMachCode, node, checkResult);
            if (!checkDate) {
                node.setCheckState(2);
                return;
            }
            registerCode = (String)checkResult.get("RegisterCode");
        } else if (softType == 31) {
            checkResult = new HashMap();
            boolean checkDate = this.checkEndDate1(registerCode, curMachCode, node, checkResult);
            if (!checkDate) {
                node.setCheckState(2);
                return;
            }
            registerCode = (String)checkResult.get("RegisterCode");
        }
        if (StringUtils.isEmpty((String)username) || StringUtils.isEmpty((String)companyName)) {
            node.setMessage("\u7528\u6237\u540d\u79f0\u548c\u5355\u4f4d\u540d\u79f0\u4e0d\u80fd\u90fd\u4e3a\u7a7a\uff01");
        } else if (StringUtils.isEmpty((String)registerCode)) {
            node.setMessage("\u8f6f\u4ef6\u6ce8\u518c\u7801\u8f93\u5165\u9519\u8bef\uff01");
        } else if (registerCode.length() != 9) {
            node.setMessage("\u8f6f\u4ef6\u6ce8\u518c\u7801\u8f93\u5165\u9519\u8bef\uff01\u8f6f\u4ef6\u6ce8\u518c\u7801\u5e94\u4e3a\u5982\u4e0b\u5f62\u5f0f\uff1aXXXX-XXXX\uff01");
        } else if (registerCode.charAt(4) != '-') {
            node.setMessage("\u8f6f\u4ef6\u6ce8\u518c\u7801\u8f93\u5165\u9519\u8bef\uff01\u8f6f\u4ef6\u6ce8\u518c\u7801\u5e94\u4e3a\u5982\u4e0b\u5f62\u5f0f\uff1aXXXX-XXXX\uff01");
        } else {
            checkState = 1;
        }
        if (checkState == 2) {
            node.setCheckState(checkState);
            return;
        }
        SingleRegisterImpl reg = new SingleRegisterImpl();
        String registerCodeMd5 = reg.getStringToMD5(registerCode);
        checkState = reg.checkRegisterCode(username, companyName, curMachCode, registerCodeMd5, this.getCheckCode2());
        if (checkState == 2) {
            node.setMessage("\u8f6f\u4ef6\u6ce8\u518c\u7801\u8f93\u5165\u9519\u8bef\uff01");
        }
        node.setCheckState(checkState);
    }

    private boolean checkEndDate1(String registerCode, String curMachCode, CheckSoftNode node, Map<String, String> checkResult) {
        boolean checkDate;
        block10: {
            checkDate = false;
            String regErrorMsg = "\u8f6f\u4ef6\u6ce8\u518c\u7801\u8f93\u5165\u9519\u8bef\uff01";
            try {
                SingleElaesImpl elase = new SingleElaesImpl();
                String code2 = elase.decryptAESString(registerCode, registerCode.length(), "date_" + curMachCode, true);
                if (StringUtils.isEmpty((String)code2)) {
                    node.setMessage(regErrorMsg);
                    break block10;
                }
                if (code2.length() != 18) {
                    node.setMessage(regErrorMsg);
                    break block10;
                }
                String[] code3 = code2.split(";");
                if (code3.length != 2) {
                    node.setMessage(regErrorMsg);
                    break block10;
                }
                String registerCode2 = code3[0];
                String checkDateCode = code3[1];
                checkResult.put("RegisterCode", registerCode2);
                if (StringUtils.isEmpty((String)registerCode2) || registerCode2.length() != 9) {
                    node.setMessage(regErrorMsg);
                    break block10;
                }
                if (StringUtils.isEmpty((String)checkDateCode) || checkDateCode.length() != 8) {
                    node.setMessage(regErrorMsg);
                    break block10;
                }
                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
                try {
                    Date currentDate = new Date();
                    Date date = formatter.parse(checkDateCode + " 23:59:59");
                    if (date != null && date.after(currentDate)) {
                        node.getVariableMap().put("RegEndDate", checkDateCode);
                        node.getVariableMap().put("CheckDateState", "1");
                        checkDate = true;
                        break block10;
                    }
                    node.getVariableMap().put("CheckDateState", "2");
                }
                catch (Exception e) {
                    logger.info(e.getMessage());
                    node.setMessage(regErrorMsg);
                }
            }
            catch (Exception e) {
                logger.info(e.getMessage());
                node.setMessage(regErrorMsg);
            }
        }
        return checkDate;
    }

    private boolean checkEndDate2(String registerCode, String curMachCode, CheckSoftNode node, Map<String, String> checkResult) {
        boolean checkDate;
        block11: {
            checkDate = false;
            String regErrorMsg = "\u8f6f\u4ef6\u6ce8\u518c\u7801\u8f93\u5165\u9519\u8bef\uff01";
            try {
                String code2 = registerCode;
                if (StringUtils.isEmpty((String)code2)) {
                    node.setMessage(regErrorMsg);
                    break block11;
                }
                if (code2.length() != 19) {
                    node.setMessage(regErrorMsg);
                    break block11;
                }
                String registerCode2 = code2.substring(0, 9);
                String checkDateCode = code2.substring(10, 19);
                checkResult.put("RegisterCode", registerCode2);
                if (StringUtils.isEmpty((String)registerCode2) || registerCode2.length() != 9) {
                    node.setMessage(regErrorMsg);
                    break block11;
                }
                if (StringUtils.isEmpty((String)checkDateCode) || checkDateCode.length() != 9) {
                    node.setMessage(regErrorMsg);
                    break block11;
                }
                if (registerCode.charAt(4) != '-' || registerCode.charAt(9) != '-' || registerCode.charAt(14) != '-') {
                    node.setMessage("\u8f6f\u4ef6\u6ce8\u518c\u7801\u8f93\u5165\u9519\u8bef\uff01\u8f6f\u4ef6\u6ce8\u518c\u7801\u5e94\u4e3a\u5982\u4e0b\u5f62\u5f0f\uff1aXXXX-XXXX-XXXX-XXXX\uff01");
                    break block11;
                }
                SingleRegisterImpl reg = new SingleRegisterImpl();
                long machCode = reg.getHexToLong(curMachCode);
                String checkDateCode3 = "" + checkDateCode.charAt(7) + checkDateCode.charAt(2) + checkDateCode.charAt(1) + checkDateCode.charAt(5) + checkDateCode.charAt(4) + checkDateCode.charAt(3) + checkDateCode.charAt(8) + checkDateCode.charAt(0) + checkDateCode.charAt(6);
                long checkDateValue = reg.getHexToLong(checkDateCode3);
                if ((checkDateValue = checkDateValue - machCode - 13246587L) < 20240101L || checkDateValue > 20440101L) {
                    node.setMessage(regErrorMsg);
                    break block11;
                }
                String checkDateCode2 = String.valueOf(checkDateValue);
                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
                try {
                    Date currentDate = new Date();
                    Date date = formatter.parse(checkDateCode2 + " 23:59:59");
                    if (date != null && date.after(currentDate)) {
                        node.getVariableMap().put("RegEndDate", checkDateCode2);
                        node.getVariableMap().put("CheckDateState", "1");
                        checkDate = true;
                        break block11;
                    }
                    node.getVariableMap().put("CheckDateState", "2");
                }
                catch (Exception e) {
                    logger.info(e.getMessage());
                    node.setMessage(regErrorMsg);
                }
            }
            catch (Exception e) {
                logger.info(e.getMessage());
                node.setMessage(regErrorMsg);
            }
        }
        return checkDate;
    }

    private boolean checkEndDate3(String registerCode, String curMachCode, CheckSoftNode node, Map<String, String> checkResult) {
        boolean checkDate;
        block12: {
            checkDate = false;
            String regErrorMsg = "\u8f6f\u4ef6\u6ce8\u518c\u7801\u8f93\u5165\u9519\u8bef\uff01";
            try {
                String code2 = registerCode;
                if (StringUtils.isEmpty((String)code2)) {
                    node.setMessage(regErrorMsg);
                    break block12;
                }
                if (code2.length() != 20) {
                    node.setMessage(regErrorMsg);
                    break block12;
                }
                String registerCode2 = code2.substring(0, 9);
                String checkDateCode = code2.substring(10, 20);
                checkResult.put("RegisterCode", registerCode2);
                String checkSrcCode = code2.replace("-", "");
                String checkSrcChar = code2.substring(19, 20);
                String checkDestChar = SingleRegCheckUtil.getRegCheckCode((String)checkSrcCode);
                if (StringUtils.isEmpty((String)registerCode2) || registerCode2.length() != 9) {
                    node.setMessage(regErrorMsg);
                    break block12;
                }
                if (StringUtils.isEmpty((String)checkDateCode) || checkDateCode.length() != 10) {
                    node.setMessage(regErrorMsg);
                    break block12;
                }
                if (!checkSrcChar.equalsIgnoreCase(checkDestChar)) {
                    node.setMessage(regErrorMsg);
                    break block12;
                }
                if (registerCode.charAt(4) != '-' || registerCode.charAt(9) != '-' || registerCode.charAt(14) != '-') {
                    node.setMessage("\u8f6f\u4ef6\u6ce8\u518c\u7801\u8f93\u5165\u9519\u8bef\uff01\u8f6f\u4ef6\u6ce8\u518c\u7801\u5e94\u4e3a\u5982\u4e0b\u5f62\u5f0f\uff1aXXXX-XXXX-XXXX-XXXX\uff01");
                    break block12;
                }
                SingleRegisterImpl reg = new SingleRegisterImpl();
                long machCode = reg.getHexToLong(curMachCode);
                String checkDateCode3 = "" + checkDateCode.charAt(7) + checkDateCode.charAt(2) + checkDateCode.charAt(1) + checkDateCode.charAt(5) + checkDateCode.charAt(4) + checkDateCode.charAt(3) + checkDateCode.charAt(8) + checkDateCode.charAt(0) + checkDateCode.charAt(6);
                long checkDateValue = reg.getHexToLong(checkDateCode3);
                if ((checkDateValue = checkDateValue - machCode - 13246587L) < 20240101L || checkDateValue > 20440101L) {
                    node.setMessage(regErrorMsg);
                    break block12;
                }
                String checkDateCode2 = String.valueOf(checkDateValue);
                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
                try {
                    Date currentDate = new Date();
                    Date date = formatter.parse(checkDateCode2 + " 23:59:59");
                    if (date != null && date.after(currentDate)) {
                        node.getVariableMap().put("RegEndDate", checkDateCode2);
                        node.getVariableMap().put("CheckDateState", "1");
                        checkDate = true;
                        break block12;
                    }
                    node.getVariableMap().put("CheckDateState", "2");
                }
                catch (Exception e) {
                    logger.info(e.getMessage());
                    node.setMessage(regErrorMsg);
                }
            }
            catch (Exception e) {
                logger.info(e.getMessage());
                node.setMessage(regErrorMsg);
            }
        }
        return checkDate;
    }

    private String getCheckCode2() {
        SingleRegisterImpl reg = new SingleRegisterImpl();
        int machCode = reg.getOriginMachine() + 1010101997;
        String newCheckCode = reg.getLongToHex(machCode);
        newCheckCode = reg.getStringToMD5(newCheckCode);
        return newCheckCode;
    }

    private String getMachEncode() {
        SingleRegisterImpl reg = new SingleRegisterImpl();
        int machCode = reg.getOriginMachine() + 1010102020;
        String enCode = reg.getLongToHex(machCode);
        enCode = reg.getStringToMD5(enCode);
        return enCode;
    }

    private String getMachEncode(CheckSoftConfig authCongfig) {
        String machCode = this.getMachEncode();
        if (authCongfig != null && StringUtils.isNotEmpty((String)authCongfig.getMachineFeignKey())) {
            SingleRegisterImpl reg = new SingleRegisterImpl();
            machCode = reg.getStringToMD5(machCode + "_" + authCongfig.getMachineFeignKey());
        }
        return machCode;
    }

    private int readRandCode() {
        return this.readRandCode(this.getMachEncode());
    }

    private int readRandCode(CheckSoftConfig authCongfig) {
        return this.readRandCode(this.getMachEncode(authCongfig));
    }

    private int readRandCode(String machEncode) {
        String randCode = "";
        BusinessConfigurationDefine config = this.configService.getBusinessConfigurationController().getConfiguration("63350655-0000-48f5-b332-000000000000", "04bac4fb-0000-4d93-9058-000000000000", "RANDMACH_" + machEncode);
        boolean isNew = config == null;
        boolean needWrite = false;
        SingleElaesImpl elase = new SingleElaesImpl();
        if (config != null) {
            String code = config.getConfigurationContent();
            if (StringUtils.isNotEmpty((String)code)) {
                String code1 = elase.decryptFromBase64(code, "auth_" + machEncode);
                if (StringUtils.isEmpty((String)code1)) {
                    needWrite = true;
                } else {
                    randCode = code1;
                }
            } else {
                needWrite = true;
            }
        } else {
            config = this.configService.getBusinessConfigurationController().createConfiguration();
            config.setKey(UUID.randomUUID().toString());
            config.setTaskKey("63350655-0000-48f5-b332-000000000000");
            config.setFormSchemeKey("04bac4fb-0000-4d93-9058-000000000000");
            config.setCode("RANDMACH_" + machEncode);
            config.setCategory("SINGLEAUTH");
            config.setContentType(ConfigContentType.CCT_TEXT);
            config.setDescription("RANDMACH");
            config.setFileName("RANDMACH");
            config.setTitle("\u673a\u5668\u4fe1\u606f");
            needWrite = true;
        }
        if (needWrite) {
            randCode = UUID.randomUUID().toString();
            String code1 = elase.encryptToBase64(randCode, "auth_" + machEncode);
            config.setConfigurationContent(code1);
            if (isNew) {
                this.configService.getBusinessConfigurationController().addConfiguration(config);
            } else {
                this.configService.getBusinessConfigurationController().updateConfiguration(config);
            }
        }
        SingleRegisterImpl reg = new SingleRegisterImpl();
        int value = reg.getMachineValue(randCode);
        return value;
    }

    @Override
    public CheckSoftNode getRegEndDate(CheckSoftParam checkParam) {
        CheckSoftNode node = new CheckSoftNode();
        node.setCheckState(2);
        if (StringUtils.isNotEmpty((String)checkParam.getCheckCode()) && checkParam.getCheckCode().equalsIgnoreCase(this.getCheckCode(checkParam.getAuthConfig()))) {
            String curMachCode = checkParam.getMachineCode();
            String registerCode = null;
            node.setAuthConfig(checkParam.getAuthConfig());
            if (StringUtils.isEmpty((String)checkParam.getRegisterCode())) {
                this.readRegFromConfig(node, this.getMachEncode(checkParam.getAuthConfig()));
                registerCode = node.getVariableMap().get("RegisterCode");
            } else {
                node.getVariableMap().put("CompanyName", checkParam.getCompanyName());
                node.getVariableMap().put("SerialNumber", checkParam.getSerialNumber());
                node.getVariableMap().put("Username", checkParam.getUserName());
                node.getVariableMap().put("RegisterCode", checkParam.getRegisterCode());
                node.getVariableMap().put("RegTime", checkParam.getRegTime());
                registerCode = node.getVariableMap().get("RegisterCode");
            }
            if (!node.getVariableMap().containsKey("SoftType") && checkParam.getSoftType() > 0) {
                node.getVariableMap().put("SoftType", String.valueOf(checkParam.getSoftType()));
            }
            if (checkParam.getSoftType() > 0) {
                if (checkParam.getSoftType() == 3) {
                    HashMap<String, String> checkResult = new HashMap<String, String>();
                    boolean checkDate = this.checkEndDate3(registerCode, curMachCode, node, checkResult);
                    if (!checkDate) {
                        node.setCheckState(2);
                        return node;
                    }
                    node.setCheckState(1);
                } else if (checkParam.getSoftType() == 32) {
                    HashMap<String, String> checkResult = new HashMap<String, String>();
                    boolean checkDate = this.checkEndDate2(registerCode, curMachCode, node, checkResult);
                    if (!checkDate) {
                        node.setCheckState(2);
                        return node;
                    }
                    node.setCheckState(1);
                } else if (checkParam.getSoftType() == 31) {
                    HashMap<String, String> checkResult = new HashMap<String, String>();
                    boolean checkDate = this.checkEndDate1(registerCode, curMachCode, node, checkResult);
                    if (!checkDate) {
                        node.setCheckState(2);
                        return node;
                    }
                    node.setCheckState(1);
                } else {
                    node.setMessage("\u8f6f\u4ef6\u7c7b\u578b\u9519\u8bef");
                }
            } else {
                node.setMessage("\u8f6f\u4ef6\u7c7b\u578b\u9519\u8bef");
            }
            if (node.getCheckState() == 2) {
                node.setMessage("\u6ce8\u518c\u7801\u9519\u8bef");
            }
        } else {
            node.setMessage("\u6821\u9a8c\u7801\u9519\u8bef");
        }
        return node;
    }
}

