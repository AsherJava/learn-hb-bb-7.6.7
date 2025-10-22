/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.context.infc.INRContext
 *  com.jiuqi.nr.jtable.annotation.JtableLog
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.base.LogParam
 */
package com.jiuqi.nr.dataentry.bean;

import com.jiuqi.nr.context.infc.INRContext;
import com.jiuqi.nr.jtable.annotation.JtableLog;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.base.LogParam;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BatchDownLoadEnclosureInfo
extends JtableLog
implements Serializable,
INRContext {
    private static final long serialVersionUID = 1L;
    private JtableContext context;
    private Map<String, List<String>> fileFieldMap = new HashMap<String, List<String>>();
    private String location;
    private String fileName;
    private String downLoadKey;
    private List<String> formKeys;
    private String contextTaskKey;
    private String contextEntityId;
    private String contextFormSchemeKey;
    private String contextFilterExpression;

    public JtableContext getContext() {
        return this.context;
    }

    public void setContext(JtableContext context) {
        this.context = context;
    }

    public Map<String, List<String>> getFileFieldMap() {
        return this.fileFieldMap;
    }

    public void setFileFieldMap(Map<String, List<String>> fileFieldMap) {
        this.fileFieldMap = fileFieldMap;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDownLoadKey() {
        return this.downLoadKey;
    }

    public void setDownLoadKey(String downLoadKey) {
        this.downLoadKey = downLoadKey;
    }

    public List<String> getFormKeys() {
        return this.formKeys;
    }

    public void setFormKeys(List<String> formKeys) {
        this.formKeys = formKeys;
    }

    public LogParam getLogParam() {
        LogParam logParam = new LogParam();
        HashMap<String, String> other = new HashMap<String, String>();
        logParam.setModule("\u6570\u636e\u5f55\u5165");
        logParam.setTitle("\u6279\u91cf\u4e0b\u8f7d\u9644\u4ef6");
        if (null != this.context.getFormKey() && !this.context.getFormKey().isEmpty()) {
            other.put("formKeys", this.context.getFormKey());
        }
        return logParam;
    }

    public String getContextEntityId() {
        return this.contextEntityId;
    }

    public String getContextFilterExpression() {
        return this.contextFilterExpression;
    }
}

