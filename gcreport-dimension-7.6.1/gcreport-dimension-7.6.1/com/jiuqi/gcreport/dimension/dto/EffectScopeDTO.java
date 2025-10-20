/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.dimension.dto;

import com.jiuqi.gcreport.dimension.vo.EffectTableVO;
import java.util.List;

public class EffectScopeDTO {
    private String code;
    private String title;
    private String sysCode;
    private String systitle;
    private String serverCode;
    private String des;
    private List<String> containedScopes;
    private List<EffectTableVO> effectTableVOS;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<EffectTableVO> getEffectTableVOS() {
        return this.effectTableVOS;
    }

    public void setEffectTableVOS(List<EffectTableVO> effectTableVOS) {
        this.effectTableVOS = effectTableVOS;
    }

    public String getSysCode() {
        return this.sysCode;
    }

    public void setSysCode(String sysCode) {
        this.sysCode = sysCode;
    }

    public String getSystitle() {
        return this.systitle;
    }

    public void setSystitle(String systitle) {
        this.systitle = systitle;
    }

    public String getDes() {
        return this.des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public List<String> getContainedScopes() {
        return this.containedScopes;
    }

    public void setContainedScopes(List<String> containedScopes) {
        this.containedScopes = containedScopes;
    }

    public String getServerCode() {
        return this.serverCode;
    }

    public void setServerCode(String serverCode) {
        this.serverCode = serverCode;
    }
}

