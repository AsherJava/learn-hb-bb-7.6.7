/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.nr.single.lib.reg.internal.service;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.single.lib.reg.internal.SingleElaesImpl;
import com.jiuqi.nr.single.lib.reg.internal.SingleRegisterImpl;
import com.jiuqi.nr.single.lib.reg.service.RoRegisterService;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.stereotype.Service;

@Service
public class RoRegisterServiceImpl
implements RoRegisterService {
    private static int roVersion = 0;
    private static final String CHECKTODATE = "20240421";
    private static final String CHCECKFROMDATE = "20230421";
    private static final String ERRORRESULT = "ERROR";
    private static final String SUCCESSRESULT = "CORRECT";
    private static final String REG_ERROR_INFO = "\u8f6f\u4ef6\u6ce8\u518c\u7801\u8f93\u5165\u9519\u8bef\uff01";

    @Override
    public String getRegisterCode(String username, String companyName, String machineName, String checkMachine, String checkCode) {
        String result = ERRORRESULT;
        if (roVersion != 0) {
            return result;
        }
        int checkState = 2;
        if (StringUtils.isEmpty((String)username) || StringUtils.isEmpty((String)companyName) || StringUtils.isEmpty((String)machineName)) {
            result = "\u7528\u6237\u540d\u79f0\u548c\u5355\u4f4d\u540d\u79f0\u4e0d\u80fd\u90fd\u4e3a\u7a7a\uff01";
        } else {
            checkState = 1;
        }
        if (checkState == 2) {
            return result;
        }
        SingleRegisterImpl sr = new SingleRegisterImpl();
        String aStr2 = checkCode;
        String aStr3 = this.getMachineCodeValue();
        int aCode2 = sr.getRegisterCode("97GG2023", "JIUQI", aStr3, this.getCheckCode2());
        String aStr = sr.getLongToHex(aCode2);
        if (!(aStr = sr.getStringToMD5(aStr)).equalsIgnoreCase(aStr2)) {
            return result;
        }
        int aCode = sr.getRegisterCode(username, companyName, machineName, this.getCheckCode2());
        String aStr4 = sr.getLongToHex(aCode);
        SingleElaesImpl elase = new SingleElaesImpl();
        aStr4 = elase.encryptToBase64(aStr4, this.getMachineCodeValue());
        return aStr4;
    }

    @Override
    public String checkRegisterCode(String username, String companyName, String machineName, String regCode) {
        String result = ERRORRESULT;
        SingleRegisterImpl reg = new SingleRegisterImpl();
        String registerCodeMd5 = reg.getStringToMD5(regCode);
        int checkState = 2;
        if (StringUtils.isEmpty((String)username) || StringUtils.isEmpty((String)companyName)) {
            result = "\u7528\u6237\u540d\u79f0\u548c\u5355\u4f4d\u540d\u79f0\u4e0d\u80fd\u90fd\u4e3a\u7a7a\uff01";
        } else if (StringUtils.isEmpty((String)regCode)) {
            result = REG_ERROR_INFO;
        } else if (regCode.length() != 9) {
            result = "\u8f6f\u4ef6\u6ce8\u518c\u7801\u8f93\u5165\u9519\u8bef\uff01\u8f6f\u4ef6\u6ce8\u518c\u7801\u5e94\u4e3a\u5982\u4e0b\u5f62\u5f0f\uff1aXXXX-XXXX\uff01";
        } else if (regCode.charAt(4) != '-') {
            result = "\u8f6f\u4ef6\u6ce8\u518c\u7801\u8f93\u5165\u9519\u8bef\uff01\u8f6f\u4ef6\u6ce8\u518c\u7801\u5e94\u4e3a\u5982\u4e0b\u5f62\u5f0f\uff1aXXXX-XXXX\uff01";
        } else {
            checkState = 1;
        }
        if (checkState == 2) {
            return result;
        }
        checkState = reg.checkRegisterCode(username, companyName, machineName, registerCodeMd5, this.getCheckCode2());
        result = checkState == 2 ? REG_ERROR_INFO : SUCCESSRESULT;
        return result;
    }

    private String getCheckCode2() {
        SingleRegisterImpl reg = new SingleRegisterImpl();
        int machCode = reg.getOriginMachine() + 1010101997;
        String newCheckCode = reg.getLongToHex(machCode);
        newCheckCode = reg.getStringToMD5(newCheckCode);
        return newCheckCode;
    }

    @Override
    public String getMachineCodeValue() {
        return "67bdb993-cb81-4897-b729-449ed7c385c4";
    }

    @Override
    public String getVersion() {
        return String.valueOf(roVersion);
    }

    @Override
    public String checkDateValid() {
        String result = ERRORRESULT;
        if (roVersion != 2) {
            return result;
        }
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd");
        String aStr = dateFormatter.format(new Date());
        if (CHCECKFROMDATE.compareTo(aStr) <= 0 && CHECKTODATE.compareTo(aStr) >= 0) {
            result = SUCCESSRESULT;
        }
        return result;
    }

    @Override
    public String decryptCode(String aCode) {
        SingleElaesImpl elase = new SingleElaesImpl();
        String aCode2 = elase.decryptFromBase64(aCode, this.getMachineCodeValue());
        SingleRegisterImpl sr = new SingleRegisterImpl();
        int newValue = sr.getHexToLong(aCode2);
        return sr.getLongToHex(newValue - 547592);
    }
}

