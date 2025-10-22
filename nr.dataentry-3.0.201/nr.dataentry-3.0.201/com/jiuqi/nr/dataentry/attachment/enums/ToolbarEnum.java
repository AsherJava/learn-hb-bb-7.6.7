/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.attachment.enums;

public enum ToolbarEnum {
    DELETE("delete", "\u5220\u9664", "#icon-_Tshanchu"),
    DOWNLOAD("download", "\u4e0b\u8f7d", "#icon-16_GJ_A_NR_wenjianxiazai"),
    BATCHATTACHMENTDOWNLOAD("batchAttachmentDownload", "\u6279\u91cf\u4e0b\u8f7d", "#icon-_GJWpiliangxiazai"),
    BATCHDELETE("batchDelete", "\u6279\u91cf\u5220\u9664", "#icon-_GJWpiliangshanchu"),
    CHOOSEFROMFILEPOOL("chooseFromFilepool", "\u4ece\u9644\u4ef6\u6c60\u9009\u62e9", "#icon-16_FJGL_A_NR_congfujianchixuanze"),
    DELETEHISTORICL("deleteHistorical", "\u5386\u53f2\u9644\u4ef6\u6e05\u9664", "#icon-16_FJGL_A_NR_lishifujianqingchu"),
    DELETEALL("deleteAll", "\u6279\u91cf\u5220\u9664", "#icon-16_FJGL_A_NR_qingchuquanbu"),
    DELETE_EN("delete", "Delete", "#icon-_Tshanchu"),
    DOWNLOAD_EN("download", "Download", "#icon-16_GJ_A_NR_wenjianxiazai"),
    BATCHATTACHMENTDOWNLOAD_EN("batchAttachmentDownload", "Batch download", "#icon-_GJWpiliangxiazai"),
    BATCHDELETE_EN("batchDelete", "Batch deletion", "#icon-_GJWpiliangshanchu"),
    CHOOSEFROMFILEPOOL_EN("chooseFromFilepool", "Select from filepool", "#icon-16_FJGL_A_NR_congfujianchixuanze"),
    DELETEHISTORICL_EN("deleteHistorical", "Delete historical", "#icon-16_FJGL_A_NR_lishifujianqingchu"),
    DELETEALL_EN("deleteAll", "Batch deletion", "#icon-16_FJGL_A_NR_qingchuquanbu");

    private String code;
    private String title;
    private String icon;

    private ToolbarEnum(String code, String title, String icon) {
        this.code = code;
        this.title = title;
        this.icon = icon;
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

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}

