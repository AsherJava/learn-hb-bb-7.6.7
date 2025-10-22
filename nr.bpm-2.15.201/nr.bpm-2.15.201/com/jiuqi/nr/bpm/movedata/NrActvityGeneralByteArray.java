/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.movedata;

import java.math.BigDecimal;

public class NrActvityGeneralByteArray {
    private String id;
    private BigDecimal rev;
    private String name;
    private String deploymentId;
    private byte[] bytes;
    private BigDecimal generated;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getRev() {
        return this.rev;
    }

    public void setRev(BigDecimal rev) {
        this.rev = rev;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeploymentId() {
        return this.deploymentId;
    }

    public void setDeploymentId(String deploymentId) {
        this.deploymentId = deploymentId;
    }

    public byte[] getBytes() {
        return this.bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public BigDecimal getGenerated() {
        return this.generated;
    }

    public void setGenerated(BigDecimal generated) {
        this.generated = generated;
    }
}

