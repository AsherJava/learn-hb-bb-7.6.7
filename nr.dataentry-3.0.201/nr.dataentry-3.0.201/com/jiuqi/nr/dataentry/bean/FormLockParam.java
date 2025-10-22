/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.context.infc.INRContext
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.jtable.annotation.JtableLog
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.uniformity.service.JUniformityService
 */
package com.jiuqi.nr.dataentry.bean;

import com.jiuqi.nr.context.infc.INRContext;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessProvider;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.jtable.annotation.JtableLog;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.uniformity.service.JUniformityService;
import java.util.Arrays;
import java.util.List;

public class FormLockParam
extends JtableLog
implements JUniformityService,
INRContext {
    private static final long serialVersionUID = 1L;
    private JtableContext context;
    private boolean lock;
    private String contextEntityId;
    private String contextFilterExpression;
    private boolean forceUnLock = false;

    public void setContextEntityId(String contextEntityId) {
        this.contextEntityId = contextEntityId;
    }

    public void setContextFilterExpression(String contextFilterExpression) {
        this.contextFilterExpression = contextFilterExpression;
    }

    public boolean isForceUnLock() {
        return this.forceUnLock;
    }

    public void setForceUnLock(boolean forceUnLock) {
        this.forceUnLock = forceUnLock;
    }

    public JtableContext getContext() {
        return this.context;
    }

    public void setContext(JtableContext context) {
        this.context = context;
    }

    public List<String> getFormKeys() {
        String formKey = this.context.getFormKey();
        if (null == formKey || "".equals(formKey)) {
            ReadWriteAccessProvider readWriteAccessProvider = (ReadWriteAccessProvider)BeanUtil.getBean(ReadWriteAccessProvider.class);
            return readWriteAccessProvider.getAllFormKeys(this.context.getFormSchemeKey());
        }
        String[] split = formKey.split(";");
        return Arrays.asList(split);
    }

    public boolean isLock() {
        return this.lock;
    }

    public void setLock(boolean lock) {
        this.lock = lock;
    }

    public String getContextEntityId() {
        return this.contextEntityId;
    }

    public String getContextFilterExpression() {
        return this.contextFilterExpression;
    }
}

