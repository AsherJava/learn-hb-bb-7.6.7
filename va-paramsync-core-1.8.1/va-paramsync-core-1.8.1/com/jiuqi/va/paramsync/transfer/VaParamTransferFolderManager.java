/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.transfer.engine.AbstractFolderManager
 *  com.jiuqi.bi.transfer.engine.FolderNode
 *  com.jiuqi.bi.transfer.engine.ex.TransferException
 *  com.jiuqi.va.domain.common.EnvConfig
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.user.UserLoginDTO
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  com.jiuqi.va.paramsync.config.VaParamSyncConfig
 *  com.jiuqi.va.paramsync.domain.VaParamTransferFolderNode
 *  com.jiuqi.va.paramsync.intf.VaParamTransferModuleIntf
 *  org.springframework.data.redis.core.StringRedisTemplate
 */
package com.jiuqi.va.paramsync.transfer;

import com.jiuqi.bi.transfer.engine.AbstractFolderManager;
import com.jiuqi.bi.transfer.engine.FolderNode;
import com.jiuqi.bi.transfer.engine.ex.TransferException;
import com.jiuqi.va.domain.common.EnvConfig;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.user.UserLoginDTO;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.mapper.domain.TenantDO;
import com.jiuqi.va.paramsync.config.VaParamSyncConfig;
import com.jiuqi.va.paramsync.domain.VaParamTransferFolderNode;
import com.jiuqi.va.paramsync.intf.VaParamTransferModuleIntf;
import com.jiuqi.va.paramsync.transfer.VaParamTransferLocalModuleHandle;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;

public class VaParamTransferFolderManager
extends AbstractFolderManager {
    private static final Logger logger = LoggerFactory.getLogger(VaParamTransferFolderManager.class);
    private String rootParentId;
    private VaParamTransferModuleIntf module;

    public String getRootParentId() {
        return this.rootParentId;
    }

    public void setRootParentId(String rootParentId) {
        this.rootParentId = rootParentId;
    }

    public VaParamTransferModuleIntf getModule() {
        return this.module;
    }

    public void setModule(VaParamTransferModuleIntf module) {
        this.module = module;
    }

    public String addFolder(FolderNode arg0) throws TransferException {
        if (this.module == null) {
            return null;
        }
        VaParamTransferFolderNode node = new VaParamTransferFolderNode();
        node.setId(arg0.getGuid());
        node.setParentId(arg0.getParentGuid());
        node.setName(arg0.getName());
        node.setTitle(arg0.getTitle());
        VaParamTransferLocalModuleHandle localModuleHandle = (VaParamTransferLocalModuleHandle)ApplicationContextRegister.getBean(VaParamTransferLocalModuleHandle.class);
        VaParamTransferModuleIntf currModule = localModuleHandle.getModule(this.module.getName());
        if (currModule != null) {
            currModule.addFolderNode(this.rootParentId, node);
        } else if (VaParamSyncConfig.isRedisEnable()) {
            TenantDO tenant = new TenantDO();
            tenant.addExtInfo("id", (Object)UUID.randomUUID().toString());
            tenant.addExtInfo("module", (Object)this.module.getName());
            tenant.addExtInfo("category", (Object)this.rootParentId);
            UserLoginDTO user = ShiroUtil.getUser();
            if (user != null) {
                if (user.getExtInfo("JTOKENID") == null) {
                    user.addExtInfo("JTOKENID", (Object)ShiroUtil.getToken());
                }
                tenant.addExtInfo("user", (Object)JSONUtil.toJSONString((Object)user));
            }
            tenant.addExtInfo("node", (Object)JSONUtil.toJSONString((Object)node));
            EnvConfig.sendRedisMsg((String)VaParamSyncConfig.getParamTransferFolderNodeAddMsg(), (String)JSONUtil.toJSONString((Object)tenant));
        }
        return arg0.getGuid();
    }

    public FolderNode getFolderByTitle(String arg0, String arg1, String arg2) throws TransferException {
        return null;
    }

    public FolderNode getFolderNode(String arg0) throws TransferException {
        if (this.module == null) {
            return null;
        }
        VaParamTransferFolderNode node = null;
        VaParamTransferLocalModuleHandle localModuleHandle = (VaParamTransferLocalModuleHandle)ApplicationContextRegister.getBean(VaParamTransferLocalModuleHandle.class);
        VaParamTransferModuleIntf currModule = localModuleHandle.getModule(this.module.getName());
        if (currModule != null) {
            node = currModule.getFolderNode(this.rootParentId, arg0);
        } else if (VaParamSyncConfig.isRedisEnable()) {
            StringRedisTemplate stringRedisTemplate = (StringRedisTemplate)ApplicationContextRegister.getBean(StringRedisTemplate.class);
            TenantDO tenant = new TenantDO();
            tenant.addExtInfo("id", (Object)UUID.randomUUID().toString());
            tenant.addExtInfo("module", (Object)this.module.getName());
            tenant.addExtInfo("category", (Object)this.rootParentId);
            tenant.addExtInfo("nodeId", (Object)arg0);
            UserLoginDTO user = ShiroUtil.getUser();
            if (user != null) {
                Object extInfo = user.getExtInfo("JTOKENID");
                user.addExtInfo("JTOKENID", extInfo == null ? ShiroUtil.getToken() : extInfo);
                tenant.addExtInfo("user", (Object)JSONUtil.toJSONString((Object)user));
            }
            EnvConfig.sendRedisMsg((String)VaParamSyncConfig.getParamTransferFolderNodeMsg(), (String)JSONUtil.toJSONString((Object)tenant));
            for (int i = 0; i < 50; ++i) {
                Boolean flag = stringRedisTemplate.hasKey((Object)tenant.getExtInfo("id").toString());
                if (Boolean.TRUE.equals(flag)) {
                    String data = (String)stringRedisTemplate.opsForValue().get((Object)tenant.getExtInfo("id").toString());
                    if (!StringUtils.hasText(data)) break;
                    node = (VaParamTransferFolderNode)JSONUtil.parseObject((String)data, VaParamTransferFolderNode.class);
                    break;
                }
                try {
                    Thread.sleep(100L);
                    continue;
                }
                catch (InterruptedException e) {
                    logger.error("\u53c2\u6570\u5bfc\u5165\u5bfc\u51fa\u7b49\u5f85\u83b7\u53d6\u76ee\u5f55\u8282\u70b9\u5f02\u5e38", e);
                }
            }
        }
        if (node != null) {
            FolderNode folderNode = new FolderNode();
            folderNode.setGuid(node.getId());
            folderNode.setParentGuid(node.getParentId());
            folderNode.setName(node.getName());
            folderNode.setTitle(node.getTitle());
            folderNode.setType("group");
            return folderNode;
        }
        return null;
    }

    public List<FolderNode> getFolderNodes(String arg0) throws TransferException {
        ArrayList<FolderNode> folderNodes = new ArrayList<FolderNode>();
        if (this.module == null) {
            return folderNodes;
        }
        List nodes = null;
        VaParamTransferLocalModuleHandle localModuleHandle = (VaParamTransferLocalModuleHandle)ApplicationContextRegister.getBean(VaParamTransferLocalModuleHandle.class);
        VaParamTransferModuleIntf currModule = localModuleHandle.getModule(this.module.getName());
        if (currModule != null) {
            nodes = currModule.getFolderNodes(this.rootParentId, arg0);
        } else if (VaParamSyncConfig.isRedisEnable()) {
            StringRedisTemplate stringRedisTemplate = (StringRedisTemplate)ApplicationContextRegister.getBean(StringRedisTemplate.class);
            TenantDO tenant = new TenantDO();
            tenant.addExtInfo("id", (Object)UUID.randomUUID().toString());
            tenant.addExtInfo("module", (Object)this.module.getName());
            tenant.addExtInfo("category", (Object)this.rootParentId);
            tenant.addExtInfo("parent", (Object)arg0);
            UserLoginDTO user = ShiroUtil.getUser();
            if (user != null) {
                if (user.getExtInfo("JTOKENID") == null) {
                    user.addExtInfo("JTOKENID", (Object)ShiroUtil.getToken());
                }
                tenant.addExtInfo("user", (Object)JSONUtil.toJSONString((Object)user));
            }
            EnvConfig.sendRedisMsg((String)VaParamSyncConfig.getParamTransferFolderNodesMsg(), (String)JSONUtil.toJSONString((Object)tenant));
            for (int i = 0; i < 50; ++i) {
                Boolean flag = stringRedisTemplate.hasKey((Object)tenant.getExtInfo("id").toString());
                if (flag == null || !flag.booleanValue()) {
                    try {
                        Thread.sleep(100L);
                    }
                    catch (InterruptedException e) {
                        logger.error("\u53c2\u6570\u5bfc\u5165\u5bfc\u51fa\u7b49\u5f85\u83b7\u53d6\u76ee\u5f55\u8282\u70b9\u5217\u8868\u5f02\u5e38", e);
                    }
                    continue;
                }
                String data = (String)stringRedisTemplate.opsForValue().get((Object)tenant.getExtInfo("id").toString());
                if (!StringUtils.hasText(data)) break;
                nodes = JSONUtil.parseArray((String)data, VaParamTransferFolderNode.class);
                break;
            }
        }
        if (nodes != null && !nodes.isEmpty()) {
            for (VaParamTransferFolderNode node : nodes) {
                FolderNode folderNode = new FolderNode();
                folderNode.setGuid(node.getId());
                folderNode.setParentGuid(node.getParentId());
                folderNode.setName(node.getName());
                folderNode.setTitle(node.getTitle());
                folderNode.setType("group");
                folderNodes.add(folderNode);
            }
        }
        return folderNodes;
    }

    public List<FolderNode> getPathFolders(String arg0) throws TransferException {
        return new ArrayList<FolderNode>();
    }

    public void modifyFolder(FolderNode arg0) throws TransferException {
    }
}

