/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.user.UserLoginDTO
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  org.springframework.data.redis.connection.Message
 *  org.springframework.data.redis.connection.MessageListener
 *  org.springframework.data.redis.core.StringRedisTemplate
 */
package com.jiuqi.va.paramsync.common;

import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.user.UserLoginDTO;
import com.jiuqi.va.mapper.domain.TenantDO;
import com.jiuqi.va.paramsync.config.VaParamSyncConfig;
import com.jiuqi.va.paramsync.domain.VaParamTransferFolderNode;
import com.jiuqi.va.paramsync.intf.VaParamTransferModuleIntf;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component(value="vaParamTransferFolderNodeMsgReciver")
@ConditionalOnExpression(value="${spring.redis.enabled:true}")
public class VaParamTransferFolderNodeMsgReciver
implements MessageListener {
    private static Logger logger = LoggerFactory.getLogger(VaParamTransferFolderNodeMsgReciver.class);
    @Autowired(required=false)
    private List<VaParamTransferModuleIntf> modules;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public void onMessage(Message message, byte[] pattern) {
        if (message == null || this.modules == null || this.modules.isEmpty()) {
            return;
        }
        String msg = new String(message.getBody());
        String channel = new String(message.getChannel());
        if (VaParamSyncConfig.getParamTransferFolderNodesMsg().equals(channel)) {
            this.sendFolder(msg, 0);
        } else if (VaParamSyncConfig.getParamTransferFolderNodeMsg().equals(channel)) {
            this.sendFolder(msg, 2);
        } else if (VaParamSyncConfig.getParamTransferFolderNodeAddMsg().equals(channel)) {
            this.addFolderNode(msg);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void sendFolder(String message, int type) {
        TenantDO tenant = (TenantDO)JSONUtil.parseObject((String)message, TenantDO.class);
        String id = tenant.getExtInfo("id").toString();
        String moduleName = (String)tenant.getExtInfo("module");
        String category = (String)tenant.getExtInfo("category");
        String parent = (String)tenant.getExtInfo("parent");
        String nodeId = (String)tenant.getExtInfo("nodeId");
        String user = (String)tenant.getExtInfo("user");
        for (VaParamTransferModuleIntf module : this.modules) {
            if (!moduleName.equals(module.getName())) continue;
            try {
                VaParamTransferFolderNode folderNode;
                ShiroUtil.bindUser((UserLoginDTO)((UserLoginDTO)JSONUtil.parseObject((String)user, UserLoginDTO.class)));
                ShiroUtil.bindTenantName((String)tenant.getTenantName());
                String jsonStr = "";
                if (type == 0) {
                    List<VaParamTransferFolderNode> folderNodes = module.getFolderNodes(category, parent);
                    if (folderNodes != null && !folderNodes.isEmpty()) {
                        jsonStr = JSONUtil.toJSONString(folderNodes);
                    }
                } else if (type == 1 && (folderNode = module.getFolderNode(category, nodeId)) != null) {
                    jsonStr = JSONUtil.toJSONString((Object)folderNode);
                }
                this.stringRedisTemplate.opsForValue().set((Object)id, (Object)jsonStr, 5L, TimeUnit.SECONDS);
                break;
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
                break;
            }
            finally {
                ShiroUtil.unbindUser();
                ShiroUtil.unbindTenantName();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void addFolderNode(String message) {
        TenantDO tenant = (TenantDO)JSONUtil.parseObject((String)message, TenantDO.class);
        String id = tenant.getExtInfo("id").toString();
        String moduleName = (String)tenant.getExtInfo("module");
        String category = (String)tenant.getExtInfo("category");
        String nodeStr = (String)tenant.getExtInfo("node");
        String user = (String)tenant.getExtInfo("user");
        for (VaParamTransferModuleIntf module : this.modules) {
            if (!moduleName.equals(module.getName())) continue;
            Boolean flag = this.stringRedisTemplate.opsForValue().setIfAbsent((Object)("import_" + id), (Object)"\u5bfc\u5165\u6267\u884c\u4e2d", 5L, TimeUnit.SECONDS);
            if (flag == null || !flag.booleanValue()) break;
            try {
                ShiroUtil.bindUser((UserLoginDTO)((UserLoginDTO)JSONUtil.parseObject((String)user, UserLoginDTO.class)));
                ShiroUtil.bindTenantName((String)tenant.getTenantName());
                VaParamTransferFolderNode node = (VaParamTransferFolderNode)JSONUtil.parseObject((String)nodeStr, VaParamTransferFolderNode.class);
                module.addFolderNode(category, node);
                break;
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
                break;
            }
            finally {
                ShiroUtil.unbindUser();
                ShiroUtil.unbindTenantName();
            }
        }
    }
}

