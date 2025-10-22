/*
 * Decompiled with CFR 0.152.
 */
package nr.single.data.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import nr.single.data.bean.CheckSoftConfig;

public class CheckSoftParam
implements Serializable {
    private static final long serialVersionUID = 4297367723615666418L;
    private String machineCode;
    private String userName;
    private String companyName;
    private String serialNumber;
    private String registerCode;
    private String regTime;
    private String checkCode;
    private CheckSoftConfig authConfig;
    private int softType;
    private Map<String, String> variableMap;

    public String getMachineCode() {
        return this.machineCode;
    }

    public void setMachineCode(String machineCode) {
        this.machineCode = machineCode;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getSerialNumber() {
        return this.serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getRegisterCode() {
        return this.registerCode;
    }

    public void setRegisterCode(String registerCode) {
        this.registerCode = registerCode;
    }

    public String getCheckCode() {
        return this.checkCode;
    }

    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode;
    }

    public int getSoftType() {
        return this.softType;
    }

    public void setSoftType(int softType) {
        this.softType = softType;
    }

    public Map<String, String> getVariableMap() {
        if (this.variableMap == null) {
            this.variableMap = new HashMap<String, String>();
        }
        return this.variableMap;
    }

    public void setVariableMap(Map<String, String> variableMap) {
        this.variableMap = variableMap;
    }

    public CheckSoftConfig getAuthConfig() {
        return this.authConfig;
    }

    public void setAuthConfig(CheckSoftConfig authConfig) {
        this.authConfig = authConfig;
    }

    public String getRegTime() {
        return this.regTime;
    }

    public void setRegTime(String regTime) {
        this.regTime = regTime;
    }
}

