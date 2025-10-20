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
import com.jiuqi.va.paramsync.domain.VaParamTransferBusinessNode;
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

@Component(value="vaParamTransferBusinessNodeMsgReciver")
@ConditionalOnExpression(value="${spring.redis.enabled:true}")
public class VaParamTransferBusinessNodeMsgReciver
implements MessageListener {
    private static Logger logger = LoggerFactory.getLogger(VaParamTransferBusinessNodeMsgReciver.class);
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
        if (VaParamSyncConfig.getParamTransferBusinessNodesMsg().equals(channel)) {
            this.getBusiness(msg, 0);
        } else if (VaParamSyncConfig.getParamTransferBusinessNodeMsg().equals(channel)) {
            this.getBusiness(msg, 1);
        } else if (VaParamSyncConfig.getParamTransferPathFoldersMsg().equals(channel)) {
            this.getBusiness(msg, 2);
        } else if (VaParamSyncConfig.getParamTransferRelatedBusinessMsg().equals(channel)) {
            this.getBusiness(msg, 3);
        } else if (VaParamSyncConfig.getParamTransferModelExportMsg().equals(channel)) {
            this.getBusiness(msg, 4);
        } else if (VaParamSyncConfig.getParamTransferDataExportMsg().equals(channel)) {
            this.getBusiness(msg, 5);
        } else if (VaParamSyncConfig.getParamTransferModelImportMsg().equals(channel)) {
            this.importBusiness(msg, 0);
        } else if (VaParamSyncConfig.getParamTransferDataImportMsg().equals(channel)) {
            this.importBusiness(msg, 1);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void getBusiness(String message, int type) {
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
                String info;
                ShiroUtil.bindUser((UserLoginDTO)((UserLoginDTO)JSONUtil.parseObject((String)user, UserLoginDTO.class)));
                ShiroUtil.bindTenantName((String)tenant.getTenantName());
                String jsonStr = "";
                if (type == 0) {
                    List<VaParamTransferBusinessNode> businessNodes = module.getBusinessNodes(category, parent);
                    if (businessNodes != null && !businessNodes.isEmpty()) {
                        jsonStr = JSONUtil.toJSONString(businessNodes);
                    }
                } else if (type == 1) {
                    VaParamTransferBusinessNode businessNode = module.getBusinessNode(category, nodeId);
                    if (businessNode != null) {
                        jsonStr = JSONUtil.toJSONString((Object)businessNode);
                    }
                } else if (type == 2) {
                    List<VaParamTransferFolderNode> folders = module.getPathFolders(category, nodeId);
                    if (folders != null && !folders.isEmpty()) {
                        jsonStr = JSONUtil.toJSONString(folders);
                    }
                } else if (type == 3) {
                    List<VaParamTransferBusinessNode> businessNodes = module.getRelatedBusiness(category, nodeId);
                    if (businessNodes != null && !businessNodes.isEmpty()) {
                        jsonStr = JSONUtil.toJSONString(businessNodes);
                    }
                } else if (type == 4) {
                    String info2 = module.getExportModelInfo(category, nodeId);
                    if (info2 == null) {
                        jsonStr = info2;
                    }
                } else if (type == 5 && (info = module.getExportDataInfo(category, nodeId)) == null) {
                    jsonStr = info;
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
    private void importBusiness(String message, int type) {
        TenantDO tenant = (TenantDO)JSONUtil.parseObject((String)message, TenantDO.class);
        String id = tenant.getExtInfo("id").toString();
        String moduleName = (String)tenant.getExtInfo("module");
        String category = (String)tenant.getExtInfo("category");
        String targetId = (String)tenant.getExtInfo("targetId");
        String data = (String)tenant.getExtInfo("data");
        String user = (String)tenant.getExtInfo("user");
        boolean isImportMultiLanguage = (Boolean)tenant.getExtInfo("isImportMultiLanguage");
        boolean importDataFlag = (Boolean)tenant.getExtInfo("importDataFlag");
        for (VaParamTransferModuleIntf module : this.modules) {
            if (!moduleName.equals(module.getName())) continue;
            Boolean flag = this.stringRedisTemplate.opsForValue().setIfAbsent((Object)("import_" + id), (Object)"\u5bfc\u5165\u6267\u884c\u4e2d", 5L, TimeUnit.SECONDS);
            if (flag == null || !flag.booleanValue()) break;
            try {
                ShiroUtil.bindUser((UserLoginDTO)((UserLoginDTO)JSONUtil.parseObject((String)user, UserLoginDTO.class)));
                ShiroUtil.bindTenantName((String)tenant.getTenantName());
                if (type == 0) {
                    module.importModelInfo(category, data, isImportMultiLanguage, importDataFlag);
                } else if (type == 1) {
                    module.importDataInfo(category, targetId, data, isImportMultiLanguage);
                }
                this.stringRedisTemplate.opsForValue().set((Object)id, (Object)"\u5bfc\u5165\u5b8c\u6210", 5L, TimeUnit.SECONDS);
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

