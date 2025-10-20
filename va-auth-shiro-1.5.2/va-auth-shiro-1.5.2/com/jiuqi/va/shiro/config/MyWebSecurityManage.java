/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.shiro.mgt.SubjectFactory
 *  org.apache.shiro.web.mgt.DefaultWebSecurityManager
 */
package com.jiuqi.va.shiro.config;

import com.jiuqi.va.shiro.config.subject.MyWebSubjectFactory;
import org.apache.shiro.mgt.SubjectFactory;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;

public class MyWebSecurityManage
extends DefaultWebSecurityManager {
    public MyWebSecurityManage() {
        this.setSubjectFactory((SubjectFactory)new MyWebSubjectFactory());
    }

    public boolean isHttpSessionMode() {
        return true;
    }
}

