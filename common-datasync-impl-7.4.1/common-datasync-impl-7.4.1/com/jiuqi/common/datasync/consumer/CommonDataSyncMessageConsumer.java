/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
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
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.common.datasync.consumer;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.datasync.discovery.CommonDataSyncDiscovery;
import com.jiuqi.common.datasync.dto.CommonDataSyncMqTopicMessageDTO;
import com.jiuqi.common.datasync.dto.CommonDataSyncSettingItemDTO;
import com.jiuqi.common.datasync.enums.CommonDataSyncMqTopicMessageType;
import com.jiuqi.common.datasync.executor.CommonDataSyncExecutor;
import com.jiuqi.common.datasync.executor.CommonDataSyncExecutorContext;
import com.jiuqi.common.datasync.message.CommonDataSyncMessage;
import com.jiuqi.common.datasync.producer.CommonDataSyncMessageProducer;
import com.jiuqi.common.datasync.properties.CommonDataSyncProperties;
import com.jiuqi.common.datasync.service.CommonDataSyncService;
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
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Component
public class CommonDataSyncMessageConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommonDataSyncMessageConsumer.class);
    @Autowired
    private UserService<User> userService;
    @Autowired
    private SystemUserService systemUserService;
    @Autowired
    private CommonDataSyncProperties commonDataSyncProperties;
    @Autowired
    private CommonDataSyncService commonDataSyncService;
    @Autowired
    private CommonDataSyncMessageProducer dataSyncMessageProducer;
    @Autowired
    private CommonDataSyncDiscovery commonDataSyncDiscovery;

    public void receiveDataSyncTopicMessageHandler(String msg) {
        try {
            if (ObjectUtils.isEmpty(msg)) {
                return;
            }
            CommonDataSyncMqTopicMessageDTO topicMessageDTO = (CommonDataSyncMqTopicMessageDTO)JsonUtils.readValue((String)msg, CommonDataSyncMqTopicMessageDTO.class);
            if (CommonDataSyncMqTopicMessageType.NOTICE.equals((Object)topicMessageDTO.getMsgType())) {
                this.dataSyncMessageProducer.publishCommonDataSyncMqTopicReportMessageDTO();
            } else if (CommonDataSyncMqTopicMessageType.REPORT.equals((Object)topicMessageDTO.getMsgType())) {
                this.commonDataSyncDiscovery.discovery(topicMessageDTO);
            }
        }
        catch (Exception e) {
            LOGGER.error("\u8ba2\u9605\u5404\u670d\u52a1\u7684\u540c\u6b65\u6570\u636e\u4efb\u52a1\u9879\u7684\u4e0a\u62a5\u6ce8\u518c\u5217\u8868\u4fe1\u606f\u53d1\u751f\u5f02\u5e38\u3002", e);
        }
    }

    public void receiveDataSyncQueueMessageHandler(String msg) throws BusinessRuntimeException {
        try {
            CommonDataSyncMessage dataSyncMessage = (CommonDataSyncMessage)JsonUtils.readValue((String)msg, CommonDataSyncMessage.class);
            this.refreshContext(dataSyncMessage.getUsername());
            CommonDataSyncMessageConsumer dataSyncMessageConsumer = (CommonDataSyncMessageConsumer)SpringContextUtils.getBean(CommonDataSyncMessageConsumer.class);
            dataSyncMessageConsumer.execute(dataSyncMessage);
        }
        catch (Exception e) {
            LOGGER.error("\u6570\u636e\u540c\u6b65\u53d1\u751f\u5f02\u5e38\u3002", e);
            throw new BusinessRuntimeException((Throwable)e);
        }
    }

    private void refreshContext(String username) {
        if (ObjectUtils.isEmpty(username)) {
            throw new BusinessRuntimeException("\u6570\u636e\u540c\u6b65\u6267\u884c\u7528\u6237\u540d\u4e0d\u80fd\u4e3a\u7a7a\u3002");
        }
        User user = this.getUserByUserName(username);
        if (user == null || user.getId() == null) {
            throw new BusinessRuntimeException("\u6570\u636e\u540c\u6b65\u6267\u884c\u7528\u6237\u540d[" + username + "]\u4e0d\u5b58\u5728\u3002");
        }
        if (!ObjectUtils.isEmpty(username)) {
            NpContextImpl npContext = this.buildContext(username);
            NpContextHolder.setContext((NpContext)npContext);
        }
    }

    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.REQUIRES_NEW)
    public void execute(CommonDataSyncMessage dataSyncMessage) throws Exception {
        CommonDataSyncSettingItemDTO itemDTO = dataSyncMessage.getItemDTO();
        if (ObjectUtils.isEmpty(itemDTO.getType())) {
            LOGGER.error("\u6570\u636e\u540c\u6b65\u7c7b\u578b\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
            return;
        }
        if (!this.commonDataSyncProperties.getServiceName().equals(itemDTO.getServiceName())) {
            LOGGER.error("\u5ffd\u7565{}\u7c7b\u578b\u7684\u6570\u636e\u540c\u6b65\uff0c\u5f53\u524d\u670d\u52a1\u540d\u4e3a{}\uff0c\u63a5\u53e3\u8c03\u7528\u65b9\u670d\u52a1\u540d\u4e3a{}\u3002", JsonUtils.writeValueAsString((Object)dataSyncMessage), this.commonDataSyncProperties.getServiceName(), itemDTO.getServiceName());
            return;
        }
        CommonDataSyncExecutor dataSyncExecutor = this.commonDataSyncService.findDataSyncExecutorByType(itemDTO.getType());
        if (dataSyncExecutor == null) {
            LOGGER.error("\u6570\u636e\u540c\u6b65\u7c7b\u578b[{}]\u5728\u7cfb\u7edf\u670d\u52a1[{}]\u4e2d\u4e0d\u5b58\u5728\uff0c\u53c2\u6570\u8be6\u60c5[{}]\u3002", itemDTO.getType(), itemDTO.getServiceName(), JsonUtils.writeValueAsString((Object)dataSyncMessage));
            return;
        }
        CommonDataSyncExecutorContext context = new CommonDataSyncExecutorContext();
        context.setDataSyncSettingItemDTO(itemDTO);
        dataSyncExecutor.execute(context);
    }

    public User getUserByUserName(String userName) {
        if (ObjectUtils.isEmpty(userName)) {
            return null;
        }
        Optional user = this.userService.findByUsername(userName);
        if (user.isPresent()) {
            return (User)user.get();
        }
        Optional sysUser = this.systemUserService.findByUsername(userName);
        return sysUser.orElse(null);
    }

    public NpContextImpl buildContext(String userName) {
        NpContextImpl npContext = (NpContextImpl)NpContextHolder.createEmptyContext();
        npContext.setTenant("__default_tenant__");
        NpContextUser contextUser = this.buildUserContext(userName);
        npContext.setUser((ContextUser)contextUser);
        NpContextIdentity identity = this.buildIdentityContext(contextUser);
        npContext.setIdentity((ContextIdentity)identity);
        return npContext;
    }

    private NpContextIdentity buildIdentityContext(NpContextUser contextUser) {
        NpContextIdentity identity = new NpContextIdentity();
        identity.setId(contextUser.getId());
        identity.setTitle(contextUser.getFullname());
        return identity;
    }

    protected NpContextUser buildUserContext(String userName) {
        NpContextUser userContext = new NpContextUser();
        User user = this.getUserByUserName(userName);
        if (user == null) {
            throw new BusinessRuntimeException("\u67e5\u8be2\u4e0d\u5230\u7528\u6237\u4fe1\u606f[" + userName + "]\u3002");
        }
        userContext.setId(user.getId());
        userContext.setName(user.getName());
        userContext.setNickname(user.getNickname());
        userContext.setDescription(user.getDescription());
        return userContext;
    }
}

