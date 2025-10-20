/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.extend.common;

public enum VaBackWriteEnum {
    AFTER_SAVE("afterSave", "\u5355\u636e\u4fdd\u5b58\u540e"),
    AFTER_COMMIT("afterCommit", "\u5de5\u4f5c\u6d41\u63d0\u4ea4\u540e"),
    BEFORE_APPROVAL("beforeApproval", "\u5de5\u4f5c\u6d41\u5ba1\u6279\u901a\u8fc7\u524d"),
    AFTER_APPROVAL("afterApproval", "\u5de5\u4f5c\u6d41\u5ba1\u6279\u901a\u8fc7\u540e"),
    AFTER_PAY_REJECT("afterPayReject", "\u4ed8\u6b3e\u9a73\u56de\u63d0\u5355\u4eba\u540e"),
    AFTER_SHARE_REJECT("afterShareReject", "\u5171\u4eab\u9a73\u56de\u63d0\u5355\u4eba\u540e"),
    AFTER_PAY_SUCCESS("afterPaySuccess", "\u4ed8\u6b3e\u5b8c\u6210\u540e"),
    AFTER_COMPLETE("afterComplete", "\u5355\u636e\u4e1a\u52a1\u6d41\u7a0b\u7ed3\u675f\u540e"),
    AFTER_REJECT("afterReject", "\u5de5\u4f5c\u6d41\u9a73\u56de\u540e"),
    AFTER_ABORT("afterAbort", "\u5355\u636e\u5e9f\u6b62\u540e"),
    AFTER_REVERSE("afterReverse", "\u5355\u636e\u7ea2\u51b2\u540e"),
    AFTER_DELETE("afterDelete", "\u5355\u636e\u5220\u9664\u540e"),
    AFTER_CREATER_BACK("afterCreaterBack", "\u5236\u5355\u8282\u70b9\u53d6\u56de\u540e");

    private String name;
    private String title;

    private VaBackWriteEnum(String name, String title) {
        this.name = name;
        this.title = title;
    }

    public String getName() {
        return this.name;
    }

    public String getTitle() {
        return this.title;
    }
}

