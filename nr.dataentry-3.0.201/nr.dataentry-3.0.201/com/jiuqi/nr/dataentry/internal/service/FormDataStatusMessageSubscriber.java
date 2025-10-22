/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.cache.message.MessageSubscriber
 *  com.jiuqi.np.cache.message.Subscriber
 *  com.jiuqi.np.core.context.ContextIdentity
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.context.impl.NpContextIdentity
 *  com.jiuqi.np.core.context.impl.NpContextImpl
 *  com.jiuqi.np.core.context.impl.NpContextUser
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.service.SystemUserService
 *  com.jiuqi.np.user.service.UserService
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.output.FormDataStatusMessage
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 */
package com.jiuqi.nr.dataentry.internal.service;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.cache.message.MessageSubscriber;
import com.jiuqi.np.cache.message.Subscriber;
import com.jiuqi.np.core.context.ContextIdentity;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.context.impl.NpContextIdentity;
import com.jiuqi.np.core.context.impl.NpContextImpl;
import com.jiuqi.np.core.context.impl.NpContextUser;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.service.SystemUserService;
import com.jiuqi.np.user.service.UserService;
import com.jiuqi.nr.dataentry.internal.service.FormDataStatusServiceImpl;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.output.FormDataStatusMessage;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Subscriber(channels={"MESSAGE_CHANNE_FORM_DATA_STATUS"})
public class FormDataStatusMessageSubscriber
implements MessageSubscriber {
    @Autowired
    IJtableParamService jtableParamService;
    @Autowired
    private UserService<User> userService;
    @Autowired
    private SystemUserService systemUserService;
    private final Logger logger = LoggerFactory.getLogger(FormDataStatusServiceImpl.class);

    public void onMessage(String channel, Object message, boolean fromThisInstance) {
        if (!(message instanceof FormDataStatusMessage)) {
            return;
        }
        FormDataStatusMessage formDataStatusMessage = (FormDataStatusMessage)message;
        this.loadContext(formDataStatusMessage);
        this.saveOrUpdateData(formDataStatusMessage);
    }

    public void loadContext(FormDataStatusMessage formDataStatusMessage) {
        String username;
        NpContextImpl context = (NpContextImpl)NpContextHolder.createEmptyContext();
        context.setTenant(formDataStatusMessage.getTenant());
        if (context.getUser() == null && StringUtils.isNotEmpty((String)(username = formDataStatusMessage.getUserName()))) {
            User userFound = null;
            Optional sysuser = this.systemUserService.findByUsername(username);
            if (sysuser.isPresent()) {
                userFound = (User)sysuser.get();
            } else {
                Optional user = this.userService.findByUsername(username);
                if (user.isPresent()) {
                    userFound = (User)user.get();
                }
            }
            if (userFound != null) {
                NpContextUser contextUser = new NpContextUser();
                contextUser.setId(userFound.getId());
                contextUser.setName(userFound.getName());
                contextUser.setNickname(userFound.getNickname());
                contextUser.setDescription(userFound.getDescription());
                context.setUser((ContextUser)contextUser);
            }
        }
        if (context.getOrganization() == null) {
            // empty if block
        }
        if (context.getIdentity() == null && formDataStatusMessage.getIdentityId() != null) {
            String identityId = formDataStatusMessage.getIdentityId();
            NpContextIdentity contextIdentity = new NpContextIdentity();
            contextIdentity.setId(identityId);
            context.setIdentity((ContextIdentity)contextIdentity);
        }
        NpContextHolder.setContext((NpContext)context);
    }

    private void saveOrUpdateData(FormDataStatusMessage formDataStatusMessage) {
        JtableContext jtableContext = formDataStatusMessage.getContext();
        JtableContext excutorJtableContext = new JtableContext(jtableContext);
        excutorJtableContext.setFormKey("");
    }
}

