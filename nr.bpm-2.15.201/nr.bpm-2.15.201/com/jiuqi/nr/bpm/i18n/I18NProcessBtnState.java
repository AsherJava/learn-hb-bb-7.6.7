/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.i18n;

import com.jiuqi.nr.bpm.upload.UploadState;

public enum I18NProcessBtnState {
    I18N_ORIGINAL_SUBMIT(UploadState.ORIGINAL_SUBMIT.name(), "\u672a\u9001\u5ba1"),
    I18N_SUBMITTED(UploadState.SUBMITED.name(), "\u5df2\u9001\u5ba1"),
    I18N_RETURNED(UploadState.RETURNED.name(), "\u5df2\u9000\u5ba1"),
    I18N_UPLOADED(UploadState.UPLOADED.name(), "\u5df2\u4e0a\u62a5"),
    I18N_ORIGINAL_UPLOAD(UploadState.ORIGINAL_UPLOAD.name(), "\u672a\u4e0a\u62a5"),
    I18N_REJECTED(UploadState.REJECTED.name(), "\u5df2\u9000\u56de"),
    I18N_PART_START(UploadState.PART_START.name(), "\u90e8\u5206\u5f00\u59cb"),
    I18N_PART_SUBMITED(UploadState.PART_SUBMITED.name(), "\u90e8\u5206\u9001\u5ba1"),
    I18N_PART_UPLOADED(UploadState.PART_UPLOADED.name(), "\u90e8\u5206\u4e0a\u62a5"),
    I18N_PART_CONFIRMED(UploadState.PART_CONFIRMED.name(), "\u90e8\u5206\u786e\u8ba4"),
    I18N_CONFIRMED(UploadState.CONFIRMED.name(), "\u5df2\u786e\u8ba4"),
    I18N_VIRTUALNODE("all-unit", "\u5168\u90e8\u5355\u4f4d");

    private String code;
    private String title;

    private I18NProcessBtnState(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }
}

