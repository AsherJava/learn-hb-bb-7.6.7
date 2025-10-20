/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.transfer.engine.AbstractBusinessManager
 *  com.jiuqi.bi.transfer.engine.BusinessNode
 *  com.jiuqi.bi.transfer.engine.FolderNode
 *  com.jiuqi.bi.transfer.engine.ex.TransferException
 *  com.jiuqi.va.domain.common.EnvConfig
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.user.UserLoginDTO
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  com.jiuqi.va.paramsync.config.VaParamSyncConfig
 *  com.jiuqi.va.paramsync.domain.VaParamTransferBusinessNode
 *  com.jiuqi.va.paramsync.domain.VaParamTransferFolderNode
 *  com.jiuqi.va.paramsync.intf.VaParamTransferModuleIntf
 *  org.springframework.data.redis.core.StringRedisTemplate
 */
package com.jiuqi.va.paramsync.transfer;

import com.jiuqi.bi.transfer.engine.AbstractBusinessManager;
import com.jiuqi.bi.transfer.engine.BusinessNode;
import com.jiuqi.bi.transfer.engine.FolderNode;
import com.jiuqi.bi.transfer.engine.ex.TransferException;
import com.jiuqi.va.domain.common.EnvConfig;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.user.UserLoginDTO;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.mapper.domain.TenantDO;
import com.jiuqi.va.paramsync.config.VaParamSyncConfig;
import com.jiuqi.va.paramsync.domain.VaParamTransferBusinessNode;
import com.jiuqi.va.paramsync.domain.VaParamTransferFolderNode;
import com.jiuqi.va.paramsync.intf.VaParamTransferModuleIntf;
import com.jiuqi.va.paramsync.transfer.VaParamTransferLocalModuleHandle;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;

public class VaParamTransferBusinessManager
extends AbstractBusinessManager {
    private static final Logger logger = LoggerFactory.getLogger(VaParamTransferBusinessManager.class);
    private String categoryId;
    private VaParamTransferModuleIntf module;

    public String getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public VaParamTransferModuleIntf getModule() {
        return this.module;
    }

    public void setModule(VaParamTransferModuleIntf module) {
        this.module = module;
    }

    public BusinessNode getBusinessByNameAndType(String arg0, String arg1) throws TransferException {
        return null;
    }

    public BusinessNode getBusinessNode(String arg0) throws TransferException {
        if (!StringUtils.hasText(arg0)) {
            return null;
        }
        VaParamTransferBusinessNode node = null;
        VaParamTransferLocalModuleHandle localModuleHandle = (VaParamTransferLocalModuleHandle)ApplicationContextRegister.getBean(VaParamTransferLocalModuleHandle.class);
        VaParamTransferModuleIntf currModule = localModuleHandle.getModule(this.module.getName());
        if (currModule != null) {
            node = currModule.getBusinessNode(this.categoryId, arg0);
        } else if (VaParamSyncConfig.isRedisEnable()) {
            StringRedisTemplate stringRedisTemplate = (StringRedisTemplate)ApplicationContextRegister.getBean(StringRedisTemplate.class);
            TenantDO tenant = new TenantDO();
            tenant.addExtInfo("id", (Object)UUID.randomUUID().toString());
            tenant.addExtInfo("module", (Object)this.module.getName());
            tenant.addExtInfo("category", (Object)this.categoryId);
            tenant.addExtInfo("nodeId", (Object)arg0);
            UserLoginDTO user = ShiroUtil.getUser();
            if (user != null) {
                if (user.getExtInfo("JTOKENID") == null) {
                    user.addExtInfo("JTOKENID", (Object)ShiroUtil.getToken());
                }
                tenant.addExtInfo("user", (Object)JSONUtil.toJSONString((Object)user));
            }
            EnvConfig.sendRedisMsg((String)VaParamSyncConfig.getParamTransferBusinessNodeMsg(), (String)JSONUtil.toJSONString((Object)tenant));
            for (int i = 0; i < 50; ++i) {
                Boolean flag = stringRedisTemplate.hasKey((Object)tenant.getExtInfo("id").toString());
                if (flag == null || !flag.booleanValue()) {
                    try {
                        Thread.sleep(100L);
                    }
                    catch (InterruptedException e) {
                        logger.error("\u53c2\u6570\u5bfc\u5165\u5bfc\u51fa\u7b49\u5f85\u83b7\u53d6\u8d44\u6e90\u8282\u70b9\u5f02\u5e38", e);
                    }
                    continue;
                }
                String data = (String)stringRedisTemplate.opsForValue().get((Object)tenant.getExtInfo("id").toString());
                if (!StringUtils.hasText(data)) break;
                node = (VaParamTransferBusinessNode)JSONUtil.parseObject((String)data, VaParamTransferBusinessNode.class);
                break;
            }
        }
        if (node != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            BusinessNode businessNode = new BusinessNode();
            businessNode.setGuid(node.getId());
            businessNode.setName(node.getName());
            businessNode.setTitle(node.getTitle());
            businessNode.setFolderGuid(node.getFolderId());
            businessNode.setOrder(node.getOrder());
            businessNode.setType(node.getType());
            businessNode.setTypeTitle(node.getTypeTitle());
            businessNode.setModifyTime(node.getModifyTime());
            if (!StringUtils.hasText(businessNode.getModifyTime())) {
                businessNode.setModifyTime(sdf.format(new Date()));
            }
            return businessNode;
        }
        return null;
    }

    public List<BusinessNode> getBusinessNodes(String arg0) throws TransferException {
        ArrayList<BusinessNode> businessNodes = new ArrayList<BusinessNode>();
        if (this.module == null) {
            return businessNodes;
        }
        List nodes = null;
        VaParamTransferLocalModuleHandle localModuleHandle = (VaParamTransferLocalModuleHandle)ApplicationContextRegister.getBean(VaParamTransferLocalModuleHandle.class);
        VaParamTransferModuleIntf currModule = localModuleHandle.getModule(this.module.getName());
        if (currModule != null) {
            nodes = currModule.getBusinessNodes(this.categoryId, arg0);
        } else if (VaParamSyncConfig.isRedisEnable()) {
            StringRedisTemplate stringRedisTemplate = (StringRedisTemplate)ApplicationContextRegister.getBean(StringRedisTemplate.class);
            TenantDO tenant = new TenantDO();
            tenant.addExtInfo("id", (Object)UUID.randomUUID().toString());
            tenant.addExtInfo("module", (Object)this.module.getName());
            tenant.addExtInfo("category", (Object)this.categoryId);
            tenant.addExtInfo("parent", (Object)arg0);
            UserLoginDTO user = ShiroUtil.getUser();
            if (user != null) {
                if (user.getExtInfo("JTOKENID") == null) {
                    user.addExtInfo("JTOKENID", (Object)ShiroUtil.getToken());
                }
                tenant.addExtInfo("user", (Object)JSONUtil.toJSONString((Object)user));
            }
            EnvConfig.sendRedisMsg((String)VaParamSyncConfig.getParamTransferBusinessNodesMsg(), (String)JSONUtil.toJSONString((Object)tenant));
            for (int i = 0; i < 20; ++i) {
                Boolean flag = stringRedisTemplate.hasKey((Object)tenant.getExtInfo("id").toString());
                if (flag == null || !flag.booleanValue()) {
                    try {
                        Thread.sleep(250L);
                    }
                    catch (InterruptedException e) {
                        logger.error("\u53c2\u6570\u5bfc\u5165\u5bfc\u51fa\u7b49\u5f85\u83b7\u53d6\u8d44\u6e90\u8282\u70b9\u5217\u8868\u5f02\u5e38", e);
                    }
                    continue;
                }
                String data = (String)stringRedisTemplate.opsForValue().get((Object)tenant.getExtInfo("id").toString());
                if (!StringUtils.hasText(data)) break;
                nodes = JSONUtil.parseArray((String)data, VaParamTransferBusinessNode.class);
                break;
            }
        }
        if (nodes != null && !nodes.isEmpty()) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (VaParamTransferBusinessNode node : nodes) {
                BusinessNode businessNode = new BusinessNode();
                businessNode.setGuid(node.getId());
                businessNode.setName(node.getName());
                businessNode.setTitle(node.getTitle());
                businessNode.setFolderGuid(node.getFolderId());
                businessNode.setOrder(node.getOrder());
                businessNode.setModifyTime(node.getModifyTime());
                if (!StringUtils.hasText(businessNode.getModifyTime())) {
                    businessNode.setModifyTime(sdf.format(new Date()));
                }
                businessNodes.add(businessNode);
            }
        }
        return businessNodes;
    }

    public List<FolderNode> getPathFolders(String arg0) throws TransferException {
        ArrayList<FolderNode> folders = new ArrayList<FolderNode>();
        if (this.module == null) {
            return folders;
        }
        List nodes = null;
        VaParamTransferLocalModuleHandle localModuleHandle = (VaParamTransferLocalModuleHandle)ApplicationContextRegister.getBean(VaParamTransferLocalModuleHandle.class);
        VaParamTransferModuleIntf currModule = localModuleHandle.getModule(this.module.getName());
        if (currModule != null) {
            nodes = currModule.getPathFolders(this.categoryId, arg0);
        } else if (VaParamSyncConfig.isRedisEnable()) {
            StringRedisTemplate stringRedisTemplate = (StringRedisTemplate)ApplicationContextRegister.getBean(StringRedisTemplate.class);
            TenantDO tenant = new TenantDO();
            tenant.addExtInfo("id", (Object)UUID.randomUUID().toString());
            tenant.addExtInfo("module", (Object)this.module.getName());
            tenant.addExtInfo("category", (Object)this.categoryId);
            tenant.addExtInfo("nodeId", (Object)arg0);
            UserLoginDTO user = ShiroUtil.getUser();
            if (user != null) {
                if (user.getExtInfo("JTOKENID") == null) {
                    user.addExtInfo("JTOKENID", (Object)ShiroUtil.getToken());
                }
                tenant.addExtInfo("user", (Object)JSONUtil.toJSONString((Object)user));
            }
            EnvConfig.sendRedisMsg((String)VaParamSyncConfig.getParamTransferPathFoldersMsg(), (String)JSONUtil.toJSONString((Object)tenant));
            for (int i = 0; i < 50; ++i) {
                Boolean flag = stringRedisTemplate.hasKey((Object)tenant.getExtInfo("id").toString());
                if (flag == null || !flag.booleanValue()) {
                    try {
                        Thread.sleep(100L);
                    }
                    catch (InterruptedException e) {
                        logger.error("\u53c2\u6570\u5bfc\u5165\u5bfc\u51fa\u7b49\u5f85\u83b7\u53d6\u8d44\u6e90\u8282\u70b9\u76ee\u5f55\u5f02\u5e38", e);
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
                folderNode.setName(node.getName());
                folderNode.setTitle(node.getTitle());
                folders.add(folderNode);
            }
        }
        return folders;
    }

    public void moveBusiness(BusinessNode arg0, String arg1) throws TransferException {
    }
}

