/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.dimension.vo;

import com.jiuqi.gcreport.dimension.vo.EffectTableVO;
import java.util.List;

public class EffectScopeVO {
    private String code;
    private String title;
    private String sysCode;
    private String systitle;
    private String des;
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
}

