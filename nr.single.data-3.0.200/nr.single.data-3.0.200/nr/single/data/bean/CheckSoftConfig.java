/*
 * Decompiled with CFR 0.152.
 */
package nr.single.data.bean;

import java.io.Serializable;

public class CheckSoftConfig
implements Serializable {
    private static final long serialVersionUID = 4691995048231559114L;
    private int keyOff;
    private String machineFeignKey;
    private String paraExpression;
    private byte[] paraData;
    private byte[] configData;
    private byte[] regData;

    public byte[] getConfigData() {
        return this.configData;
    }

    public void setConfigData(byte[] configData) {
        this.configData = configData;
    }

    public int getKeyOff() {
        return this.keyOff;
    }

    public void setKeyOff(int keyOff) {
        this.keyOff = keyOff;
    }

    public byte[] getRegData() {
        return this.regData;
    }

    public void setRegData(byte[] regData) {
        this.regData = regData;
    }

    public byte[] getParaData() {
        return this.paraData;
    }

    public void setParaData(byte[] paraData) {
        this.paraData = paraData;
    }

    public String getParaExpression() {
        return this.paraExpression;
    }

    public void setParaExpression(String paraExpression) {
        this.paraExpression = paraExpression;
    }

    public String getMachineFeignKey() {
        return this.machineFeignKey;
    }

    public void setMachineFeignKey(String machineFeignKey) {
        this.machineFeignKey = machineFeignKey;
    }
}

