/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.user.SystemUser
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.service.UserService
 *  com.jiuqi.nr.task.api.common.ComponentDefine
 *  com.jiuqi.nr.task.api.dto.ITransferContext
 *  com.jiuqi.nr.task.api.face.AbstractFormSchemeResourceFactory
 *  com.jiuqi.nr.task.api.face.IResourceDataProvider
 *  com.jiuqi.nr.task.api.face.IResourceDeploy
 *  com.jiuqi.nr.task.api.face.IResourceIOProvider
 */
package com.jiuqi.nr.task.form.ext;

import com.jiuqi.np.user.SystemUser;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.service.UserService;
import com.jiuqi.nr.task.api.common.ComponentDefine;
import com.jiuqi.nr.task.api.dto.ITransferContext;
import com.jiuqi.nr.task.api.face.AbstractFormSchemeResourceFactory;
import com.jiuqi.nr.task.api.face.IResourceDataProvider;
import com.jiuqi.nr.task.api.face.IResourceDeploy;
import com.jiuqi.nr.task.api.face.IResourceIOProvider;
import com.jiuqi.nr.task.form.ext.FormResourceDataProvider;
import com.jiuqi.nr.task.form.form.service.IFormDefineService;
import com.jiuqi.nr.task.form.form.service.IFormGroupService;
import com.jiuqi.nr.task.form.service.IFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FormResourceExt
extends AbstractFormSchemeResourceFactory {
    public static final String CODE = "FORM_EXT";
    private static final String TITLE = "\u62a5\u8868";
    @Autowired
    private IFormService formService;
    @Autowired
    private IFormDefineService formDefineService;
    @Autowired
    private IFormGroupService formGroupService;
    @Autowired
    private UserService<User> userUserService;
    @Autowired
    private UserService<SystemUser> systemUserUserService;

    public String code() {
        return CODE;
    }

    public String title() {
        return TITLE;
    }

    public double order() {
        return 0.5;
    }

    public boolean enable(String formSchemeKey) {
        return true;
    }

    public IResourceDataProvider dataProvider() {
        return new FormResourceDataProvider(this.formService, this.formDefineService, this.formGroupService, this.userUserService, this.systemUserUserService);
    }

    public IResourceIOProvider transferProvider(ITransferContext context) {
        return null;
    }

    public IResourceDeploy deployProvider() {
        return null;
    }

    public ComponentDefine component() {
        return new ComponentDefine("formManage", "@nr", "formEntry");
    }
}

