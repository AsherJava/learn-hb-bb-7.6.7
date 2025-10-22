/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.function.func.floatcopy;

import com.jiuqi.nr.function.func.floatcopy.FloatCopyQueryInfo;

public class FloatCopyDestCopyCondition {
    private FloatCopyQueryInfo row;
    private boolean clearBeforeCopy;
    private String copyMode;

    public FloatCopyQueryInfo getRow() {
        return this.row;
    }

    public void setRow(FloatCopyQueryInfo row) {
        this.row = row;
    }

    public boolean isClearBeforeCopy() {
        return this.clearBeforeCopy;
    }

    public void setClearBeforeCopy(boolean clearBeforeCopy) {
        this.clearBeforeCopy = clearBeforeCopy;
    }

    public String getCopyMode() {
        return this.copyMode;
    }

    public void setCopyMode(String copyMode) {
        this.copyMode = copyMode;
    }

    public String getCopyModeExplain() {
        if (this.copyMode.equals("0") || this.copyMode.equalsIgnoreCase("false")) {
            return this.copyMode + "\uff1a\u6807\u8bc6\u5217\u76f8\u540c\u7684\u884c\u4e0d\u8fdb\u884c\u5408\u8ba1\uff0c\u4ec5\u7f57\u5217";
        }
        if (this.copyMode.equals("1") || this.copyMode.equalsIgnoreCase("true")) {
            return this.copyMode + "\uff1a\u6e90\u6d6e\u52a8\u884c\u6570\u636e\u63d0\u5230\u76ee\u6807\u6d6e\u52a8\u884c\u540e\uff0c\u6807\u8bc6\u5217\u76f8\u540c\u7684\u884c\u8fdb\u884c\u5408\u8ba1";
        }
        if (this.copyMode.equals("2")) {
            return this.copyMode + "\uff1a\u5982\u679c\u76ee\u6807\u6d6e\u52a8\u884c\u4e0e\u6e90\u6d6e\u52a8\u884c\u6307\u6807\u76f8\u540c\uff0c\u5219\u5c06\u6e90\u6d6e\u52a8\u884c\u7684\u6570\u636e\u66f4\u65b0\u5230\u76ee\u6807\u6d6e\u52a8\u884c\u53bb";
        }
        if (this.copyMode.equals("3")) {
            return this.copyMode + "\uff1a\u5982\u679c\u76ee\u6807\u6d6e\u52a8\u884c\u4e0e\u6e90\u6d6e\u52a8\u884c\u6307\u6807\u76f8\u540c\uff0c\u5219\u5c06\u6e90\u6d6e\u52a8\u884c\u7684\u6570\u636e\u66f4\u65b0\u5230\u76ee\u6807\u6d6e\u52a8\u884c\u53bb\uff0c\u4e14\u6e05\u9664\u6e90\u6d6e\u52a8\u884c\u4e2d\u4e0d\u5b58\u5728\u800c\u5728\u76ee\u6807\u6d6e\u52a8\u884c\u4e2d\u5b58\u5728\u7684\u884c\u6570\u636e";
        }
        if (this.copyMode.equals("4")) {
            return this.copyMode + "\uff1a\u5c06\u6e90\u6d6e\u52a8\u884c\u7684\u6570\u636e\u7d2f\u52a0\u5230\u5230\u76ee\u6807\u6d6e\u52a8\u884c\u76f8\u540c\u7f16\u7801\u7684\u884c\u4e2d\uff0c\u4e14\u6e05\u9664\u6e90\u6d6e\u52a8\u884c\u4e2d\u4e0d\u5b58\u5728\u800c\u5728\u76ee\u6807\u6d6e\u52a8\u884c\u4e2d\u5b58\u5728\u7684\u884c\u6570\u636e";
        }
        if (this.copyMode.equals("5")) {
            return this.copyMode + "\uff1a\u5982\u679c\u76ee\u6807\u6d6e\u52a8\u884c\u4e0e\u6e90\u6d6e\u52a8\u884c\u6307\u6807\u503c\u76f8\u540c\uff0c\u5219\u5c06\u6e90\u6d6e\u52a8\u884c\u7684\u6570\u636e\u66f4\u65b0\u5230\u5230\u76ee\u6807\u6d6e\u52a8\u884c\u76f8\u540c\u7f16\u7801\u7684\"\u6240\u6709\u884c\"\u4e2d\uff0c\u4e14\u76ee\u6807\u6d6e\u52a8\u884c\u4e2d\u4e0d\u5b58\u65f6\u4e5f\u4e0d\u589e\u52a0";
        }
        if (this.copyMode.equals("6")) {
            return this.copyMode + "\uff1a\u5c06\u6e90\u6d6e\u52a8\u884c\u7684\u6570\u636e\u6309\u5206\u7c7b\u6307\u6807\u7d2f\u52a0\u540e\u590d\u5236\u5230\u76ee\u6807\u6d6e\u52a8\u884c\u4e2d";
        }
        if (this.copyMode.equals("7")) {
            return this.copyMode + "\uff1a\u5c06\u6e90\u6d6e\u52a8\u884c\u7684\u6570\u636e\u6309\u5206\u7c7b\u6307\u6807\u7d2f\u52a0\u540e\u590d\u5236\u5230\u76ee\u6807\u6d6e\u52a8\u884c\u4e2d\uff0c\u4e14\u6e05\u9664\u6e90\u6d6e\u52a8\u884c\u4e2d\u4e0d\u5b58\u5728\u800c\u5728\u76ee\u6807\u6d6e\u52a8\u884c\u4e2d\u5b58\u5728\u7684\u884c\u6570\u636e";
        }
        if (this.copyMode.equals("8")) {
            return this.copyMode + "\uff1a\u5c06\u6e90\u6d6e\u52a8\u884c\u7684\u6570\u636e\u7d2f\u52a0\u5230\u5230\u76ee\u6807\u6d6e\u52a8\u884c\u76f8\u540c\u7f16\u7801\u7684\u884c\u4e2d,\u4e14\u76ee\u6807\u6d6e\u52a8\u884c\u4e2d\u4e0d\u5b58\u65f6\u4e5f\u4e0d\u589e\u52a0";
        }
        if (this.copyMode.equals("9")) {
            return this.copyMode + "\uff1a\u5c06\u6e90\u6d6e\u52a8\u884c\u7684\u6570\u636e\u7d2f\u52a0\u5230\u76ee\u6807\u6d6e\u52a8\u884c\u76f8\u540c\u7f16\u7801\u7684\u884c\u4e2d\uff0c\u4f46\u4e0d\u6e05\u9664\u6e90\u6d6e\u52a8\u884c\u4e2d\u4e0d\u5b58\u5728\u800c\u5728\u76ee\u6807\u6d6e\u52a8\u884c\u4e2d\u5b58\u5728\u7684\u884c\u6570\u636e";
        }
        return "\u9ed8\u8ba40\uff1a\u6807\u8bc6\u5217\u76f8\u540c\u7684\u884c\u4e0d\u8fdb\u884c\u5408\u8ba1\uff0c\u4ec5\u7f57\u5217";
    }
}

