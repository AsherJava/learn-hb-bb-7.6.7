/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.subject.impl.subject.expimp.intf.impl;

import com.jiuqi.common.subject.impl.subject.data.DataType;
import com.jiuqi.common.subject.impl.subject.expimp.intf.ISubjectExpImpFieldDefine;

public enum SubjectImpExpDefaultColumnEnum implements ISubjectExpImpFieldDefine
{
    CODE("code", "\u4ee3\u7801", DataType.STRING, true, 18, "\u5fc5\u586b", "1001"),
    NAME("name", "\u540d\u79f0", DataType.STRING, true, 32, "\u5fc5\u586b", "\u5e93\u5b58\u73b0\u91d1"),
    SHORTNAME("shortname", "\u7b80\u79f0", DataType.STRING, false, 18, "\u975e\u5fc5\u586b", ""),
    PARENTCODE("parentcode", "\u4e0a\u7ea7\u4ee3\u7801", DataType.STRING, false, 18, "1\u3001\u975e\u5fc5\u586b\r\n2\u3001\u79d1\u76ee\u7684\u76f4\u63a5\u4e0a\u7ea7\u4ee3\u7801\r\n3\u3001\u4e0a\u7ea7\u4ee3\u7801\u4e3a\u7a7a\u65f6\uff0c\u9ed8\u8ba4\u4e3a\u4e00\u7ea7\u79d1\u76ee", ""),
    ORIENT("orient", "\u501f\u8d37\u65b9\u5411", DataType.INT, true, 16, "1\u3001\u5fc5\u586b\r\n2\u3001\u53ea\u80fd\u5bfc\u5165\u201c\u501f\u201d\u6216\u8005\u201c\u8d37\u201d", "\u501f"),
    GENERALTYPE("generaltype", "\u9879\u76ee\u5927\u7c7b", DataType.STRING, true, 16, "1\u3001\u5fc5\u586b\r\n2\u3001\u586b\u5165\u79d1\u76ee\u5927\u7c7b\u540d\u79f0\r\n", "\u8d44\u4ea7\u7c7b"),
    REMARK("remark", "\u79d1\u76ee\u8bf4\u660e", DataType.STRING, false, 20, "\u975e\u5fc5\u586b", "\u5b58\u653e\u5728\u8d22\u4f1a\u90e8\u95e8\uff0c\u968f\u65f6\u53ef\u4ee5\u52a8\u7528\u7684\u90a3\u90e8\u5206\u5e93\u5b58\u73b0\u91d1\uff0c\u5305\u62ec\u4eba\u6c11\u5e01\u548c\u5916\u5e01"),
    REQUIRED_ASSIST("requiredassist", "\u5fc5\u586b\u8f85\u52a9\u9879", DataType.STRING, false, 18, "1\u3001\u975e\u5fc5\u586b\r\n2\u3001\u586b\u5165\u5fc5\u586b\u7684\u8f85\u52a9\u7ef4\u5ea6\u540d\u79f0\uff0c\u591a\u4e2a\u7ef4\u5ea6\u95f4\u9017\u53f7\u9694\u5f00", "\u9879\u76ee,\u90e8\u95e8"),
    NON_REQUIRED_ASSIST("nonrequiredassist", "\u975e\u5fc5\u586b\u8f85\u52a9\u9879", DataType.STRING, false, 18, "1\u3001\u975e\u5fc5\u586b\r\n2\u3001\u586b\u5165\u975e\u5fc5\u586b\u7684\u8f85\u52a9\u7ef4\u5ea6\u540d\u79f0\uff0c\u591a\u4e2a\u7ef4\u5ea6\u95f4\u9017\u53f7\u9694\u5f00", "\u5ba2\u6237");

    private final String code;
    private final String name;
    private final DataType dataType;
    private final boolean required;
    private final Integer width;
    private final String remark;
    private final String example;

    private SubjectImpExpDefaultColumnEnum(String code, String name, DataType dataType, boolean required, Integer width, String remark, String example) {
        this.code = code;
        this.name = name;
        this.dataType = dataType;
        this.required = required;
        this.width = width;
        this.remark = remark;
        this.example = example;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public DataType getDataType() {
        return this.dataType;
    }

    @Override
    public boolean isRequired() {
        return this.required;
    }

    @Override
    public Integer getWidth() {
        return this.width;
    }

    @Override
    public String getRemark() {
        return this.remark;
    }

    @Override
    public String getExample() {
        return this.example;
    }
}

