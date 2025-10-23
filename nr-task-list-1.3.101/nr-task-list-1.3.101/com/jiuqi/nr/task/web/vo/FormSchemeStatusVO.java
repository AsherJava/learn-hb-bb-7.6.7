/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.web.vo;

import com.jiuqi.nr.task.dto.FormSchemeStatusDTO;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class FormSchemeStatusVO
extends FormSchemeStatusDTO {
    private String formSchemeKey;
    private String message;
    private Long hours;
    private Long minutes;

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    public Long getHours() {
        return this.hours;
    }

    public void setHours(Long hours) {
        this.hours = hours;
    }

    public Long getMinutes() {
        return this.minutes;
    }

    public void setMinutes(Long minutes) {
        this.minutes = minutes;
    }

    public static FormSchemeStatusVO getInstance(FormSchemeStatusDTO formSchemeStatus) {
        FormSchemeStatusVO releaseVO = new FormSchemeStatusVO();
        releaseVO.setStatus(formSchemeStatus.getStatus());
        releaseVO.setPublishTime(formSchemeStatus.getPublishTime());
        releaseVO.setMessage(formSchemeStatus.getMessage());
        if (null != formSchemeStatus.getPublishTime()) {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime publishLocalDateTime = LocalDateTime.ofInstant(formSchemeStatus.getPublishTime().toInstant(), ZoneId.systemDefault());
            Duration between = Duration.between(now, publishLocalDateTime);
            long hours = between.toHours();
            long min = between.toMinutes() % 60L;
            releaseVO.setHours(hours);
            releaseVO.setMinutes(min);
        }
        return releaseVO;
    }
}

