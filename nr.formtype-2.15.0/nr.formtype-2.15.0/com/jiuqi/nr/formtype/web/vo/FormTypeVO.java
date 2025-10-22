/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.np.definition.common.UUIDUtils
 */
package com.jiuqi.nr.formtype.web.vo;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.nr.formtype.facade.FormTypeDefine;
import org.springframework.util.StringUtils;

public class FormTypeVO {
    private String key;
    private String code;
    private String title;
    private String groupKey;
    private String desc;
    private String order;
    private boolean createDefaultDatas;

    public FormTypeVO() {
    }

    public FormTypeVO(FormTypeDefine define) {
        this.key = define.getId();
        this.code = define.getCode();
        this.title = define.getTitle();
        this.groupKey = define.getGroupId();
        this.desc = define.getDesc();
        this.order = define.getOrder();
    }

    public void toDefine(FormTypeDefine define) {
        define.setId(StringUtils.hasLength(this.key) ? this.key : UUIDUtils.getKey());
        define.setCode(this.code);
        define.setTitle(this.title);
        if (StringUtils.hasLength(this.groupKey)) {
            define.setGroupId(this.groupKey);
        } else {
            define.setGroupId("--");
        }
        define.setDesc(this.desc);
        if (StringUtils.hasLength(this.order)) {
            define.setOrder(this.order);
        } else {
            define.setOrder(OrderGenerator.newOrder());
        }
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

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

    public String getGroupKey() {
        return this.groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public boolean isCreateDefaultDatas() {
        return this.createDefaultDatas;
    }

    public void setCreateDefaultDatas(boolean createDefaultDatas) {
        this.createDefaultDatas = createDefaultDatas;
    }
}

