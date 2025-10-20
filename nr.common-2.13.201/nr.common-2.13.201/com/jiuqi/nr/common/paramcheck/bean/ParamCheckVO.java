/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.common.paramcheck.bean;

import com.jiuqi.nr.common.paramcheck.common.ParamType;
import com.jiuqi.nr.common.paramcheck.service.CheckParam;

public class ParamCheckVO {
    private String bean;
    private String title;
    private ParamType type;

    public ParamCheckVO() {
    }

    public ParamCheckVO(String bean, String title, ParamType type) {
        this.bean = bean;
        this.title = title;
        this.type = type;
    }

    public String getBean() {
        return this.bean;
    }

    public void setBean(String bean) {
        this.bean = bean;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ParamType getType() {
        return this.type;
    }

    public void setType(ParamType type) {
        this.type = type;
    }

    public static ParamCheckVO buildItem(CheckParam checkParam) {
        ParamCheckVO vo = new ParamCheckVO();
        vo.setBean(checkParam.name());
        vo.setTitle(checkParam.title());
        vo.setType(checkParam.type());
        return vo;
    }
}

