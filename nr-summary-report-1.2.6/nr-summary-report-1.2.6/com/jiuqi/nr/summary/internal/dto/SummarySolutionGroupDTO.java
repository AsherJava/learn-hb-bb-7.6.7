/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 *  javax.validation.constraints.NotNull
 *  javax.validation.constraints.Size
 */
package com.jiuqi.nr.summary.internal.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiuqi.nr.summary.api.Ordered;
import com.jiuqi.nr.summary.api.SummarySolutionGroup;
import com.jiuqi.nr.summary.common.jackson.InstantJsonDeserializer;
import com.jiuqi.nr.summary.common.jackson.InstantJsonSerializer;
import java.time.Instant;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@JsonIgnoreProperties(ignoreUnknown=true)
public class SummarySolutionGroupDTO
implements SummarySolutionGroup {
    @Size(max=40, message="key\u6700\u5927\u4e0d\u80fd\u8d85\u8fc740\u4e2a\u5b57\u7b26")
    private @Size(max=40, message="key\u6700\u5927\u4e0d\u80fd\u8d85\u8fc740\u4e2a\u5b57\u7b26") String key;
    @Size(max=200, message="title\u6700\u5927\u4e0d\u80fd\u8d85\u8fc7200\u4e2a\u5b57\u7b26")
    @NotNull(message="title\u4e0d\u80fd\u4e3a\u7a7a")
    private @Size(max=200, message="title\u6700\u5927\u4e0d\u80fd\u8d85\u8fc7200\u4e2a\u5b57\u7b26") @NotNull(message="title\u4e0d\u80fd\u4e3a\u7a7a") String title;
    @Size(max=40, message="parent\u6700\u5927\u4e0d\u80fd\u8d85\u8fc740\u4e2a\u5b57\u7b26")
    private @Size(max=40, message="parent\u6700\u5927\u4e0d\u80fd\u8d85\u8fc740\u4e2a\u5b57\u7b26") String parent;
    @Size(max=1000, message="desc\u6700\u5927\u4e0d\u80fd\u8d85\u8fc71000\u4e2a\u5b57\u7b26")
    private @Size(max=1000, message="desc\u6700\u5927\u4e0d\u80fd\u8d85\u8fc71000\u4e2a\u5b57\u7b26") String desc;
    @JsonDeserialize(using=InstantJsonDeserializer.class)
    @JsonSerialize(using=InstantJsonSerializer.class)
    private Instant modifyTime;
    @Size(max=10, message="order\u6700\u5927\u4e0d\u80fd\u8d85\u8fc710\u4e2a\u5b57\u7b26")
    private @Size(max=10, message="order\u6700\u5927\u4e0d\u80fd\u8d85\u8fc710\u4e2a\u5b57\u7b26") String order;

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }

    @Override
    public Instant getModifyTime() {
        return this.modifyTime;
    }

    @Override
    public String getOrder() {
        return this.order;
    }

    @Override
    public String getParent() {
        return this.parent;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public void setName(String name) {
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    @Override
    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public void setModifyTime(Instant modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Override
    public void setOrder(String order) {
        this.order = order;
    }

    @Override
    public int compareTo(Ordered o) {
        if (o == null || o.getOrder() == null) {
            return 1;
        }
        if (this.order == null) {
            return -1;
        }
        return this.order.compareTo(o.getOrder());
    }
}

