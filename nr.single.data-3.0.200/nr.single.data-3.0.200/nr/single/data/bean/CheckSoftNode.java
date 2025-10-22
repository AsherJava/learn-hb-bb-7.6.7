/*
 * Decompiled with CFR 0.152.
 */
package nr.single.data.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import nr.single.data.bean.CheckSoftConfig;

public class CheckSoftNode
implements Serializable {
    private static final long serialVersionUID = 8638027092101714661L;
    private String machineCode;
    private String checkCode;
    private int checkState;
    private String message;
    private Map<String, String> variableMap;
    private byte[] regData;
    private CheckSoftConfig authConfig;

    public String getMachineCode() {
        return this.machineCode;
    }

    public void setMachineCode(String machineCode) {
        this.machineCode = machineCode;
    }

    public String getCheckCode() {
        return this.checkCode;
    }

    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode;
    }

    public int getCheckState() {
        return this.checkState;
    }

    public void setCheckState(int checkState) {
        this.checkState = checkState;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public byte[] getRegData() {
        return this.regData;
    }

    public void setRegData(byte[] regData) {
        this.regData = regData;
    }

    public CheckSoftConfig getAuthConfig() {
        return this.authConfig;
    }

    public void setAuthConfig(CheckSoftConfig authConfig) {
        this.authConfig = authConfig;
    }
}

