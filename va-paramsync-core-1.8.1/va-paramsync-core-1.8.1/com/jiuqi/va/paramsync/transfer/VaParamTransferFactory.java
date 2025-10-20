/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.transfer.engine.AbstractBusinessManager
 *  com.jiuqi.bi.transfer.engine.AbstractFolderManager
 *  com.jiuqi.bi.transfer.engine.BusinessNode
 *  com.jiuqi.bi.transfer.engine.ResItem
 *  com.jiuqi.bi.transfer.engine.TransferFactory
 *  com.jiuqi.bi.transfer.engine.ex.TransferException
 *  com.jiuqi.bi.transfer.engine.intf.IConfigTransfer
 *  com.jiuqi.bi.transfer.engine.intf.IDataTransfer
 *  com.jiuqi.bi.transfer.engine.intf.IMetaFinder
 *  com.jiuqi.bi.transfer.engine.intf.IModelTransfer
 *  com.jiuqi.bi.transfer.engine.intf.IPublisher
 *  com.jiuqi.bi.transfer.engine.model.GuidMapperBean
 *  com.jiuqi.bi.transfer.engine.model.NameMapperBean
 *  com.jiuqi.va.domain.common.EnvConfig
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.user.UserLoginDTO
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  com.jiuqi.va.paramsync.config.VaParamSyncConfig
 *  com.jiuqi.va.paramsync.domain.VaParamTransferBusinessNode
 *  com.jiuqi.va.paramsync.intf.VaParamTransferModuleIntf
 *  org.springframework.data.redis.core.StringRedisTemplate
 */
package com.jiuqi.va.paramsync.transfer;

import com.jiuqi.bi.transfer.engine.AbstractBusinessManager;
import com.jiuqi.bi.transfer.engine.AbstractFolderManager;
import com.jiuqi.bi.transfer.engine.BusinessNode;
import com.jiuqi.bi.transfer.engine.ResItem;
import com.jiuqi.bi.transfer.engine.TransferFactory;
import com.jiuqi.bi.transfer.engine.ex.TransferException;
import com.jiuqi.bi.transfer.engine.intf.IConfigTransfer;
import com.jiuqi.bi.transfer.engine.intf.IDataTransfer;
import com.jiuqi.bi.transfer.engine.intf.IMetaFinder;
import com.jiuqi.bi.transfer.engine.intf.IModelTransfer;
import com.jiuqi.bi.transfer.engine.intf.IPublisher;
import com.jiuqi.bi.transfer.engine.model.GuidMapperBean;
import com.jiuqi.bi.transfer.engine.model.NameMapperBean;
import com.jiuqi.va.domain.common.EnvConfig;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.user.UserLoginDTO;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.mapper.domain.TenantDO;
import com.jiuqi.va.paramsync.config.VaParamSyncConfig;
import com.jiuqi.va.paramsync.domain.VaParamTransferBusinessNode;
import com.jiuqi.va.paramsync.intf.VaParamTransferModuleIntf;
import com.jiuqi.va.paramsync.transfer.VaParamTransferBusinessManager;
import com.jiuqi.va.paramsync.transfer.VaParamTransferData;
import com.jiuqi.va.paramsync.transfer.VaParamTransferFolderManager;
import com.jiuqi.va.paramsync.transfer.VaParamTransferLocalModuleHandle;
import com.jiuqi.va.paramsync.transfer.VaParamTransferModel;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;

public class VaParamTransferFactory
extends TransferFactory {
    private static final Logger logger = LoggerFactory.getLogger(VaParamTransferFactory.class);
    private String id;
    private String title;
    private VaParamTransferModuleIntf module;
    private boolean supportExport;
    private boolean supportExportData;

    public IConfigTransfer createConfigTransfer() throws TransferException {
        return null;
    }

    public IDataTransfer createDataTransfer(String arg0) throws TransferException {
        VaParamTransferData dataTransfer = new VaParamTransferData();
        dataTransfer.setCategoryId(this.id);
        dataTransfer.setModule(this.module);
        return dataTransfer;
    }

    public IMetaFinder createMetaFinder(String arg0) {
        return null;
    }

    public IModelTransfer createModelTransfer(String arg0) throws TransferException {
        VaParamTransferModel modelTransfer = new VaParamTransferModel();
        modelTransfer.setCategoryId(this.id);
        modelTransfer.setModule(this.module);
        return modelTransfer;
    }

    public IPublisher createPublisher() throws TransferException {
        return null;
    }

    public AbstractBusinessManager getBusinessManager() {
        VaParamTransferBusinessManager businessManager = new VaParamTransferBusinessManager();
        businessManager.setModule(this.module);
        businessManager.setCategoryId(this.id);
        return businessManager;
    }

    public List<String> getDependenceFactoryIds() {
        return this.module.getDependenceFactoryIds();
    }

    public AbstractFolderManager getFolderManager() {
        VaParamTransferFolderManager folderManager = new VaParamTransferFolderManager();
        folderManager.setRootParentId(this.id);
        folderManager.setModule(this.module);
        return folderManager;
    }

    public String getIcon() throws TransferException {
        return null;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setModule(VaParamTransferModuleIntf module) {
        this.module = module;
    }

    public void setSupportExport(boolean supportExport) {
        this.supportExport = supportExport;
    }

    public void setSupportExportData(boolean supportExportData) {
        this.supportExportData = supportExportData;
    }

    public String getModifiedTime(String arg0) throws TransferException {
        BusinessNode businessNode = this.getBusinessManager().getBusinessNode(arg0);
        return businessNode != null ? businessNode.getModifyTime() : null;
    }

    public String getModuleId() {
        return this.module.getModuleId() != null ? this.module.getModuleId() : this.module.getName();
    }

    public int getOrder() {
        return 0;
    }

    public List<ResItem> getRelatedBusiness(String arg0) throws TransferException {
        if (!StringUtils.hasText(arg0)) {
            return null;
        }
        List nodes = null;
        VaParamTransferLocalModuleHandle localModuleHandle = (VaParamTransferLocalModuleHandle)ApplicationContextRegister.getBean(VaParamTransferLocalModuleHandle.class);
        VaParamTransferModuleIntf currModule = localModuleHandle.getModule(this.module.getName());
        if (currModule != null) {
            nodes = currModule.getRelatedBusiness(this.id, arg0);
        } else if (VaParamSyncConfig.isRedisEnable()) {
            StringRedisTemplate stringRedisTemplate = (StringRedisTemplate)ApplicationContextRegister.getBean(StringRedisTemplate.class);
            TenantDO tenant = new TenantDO();
            tenant.addExtInfo("id", (Object)UUID.randomUUID().toString());
            tenant.addExtInfo("module", (Object)this.module.getName());
            tenant.addExtInfo("category", (Object)this.id);
            tenant.addExtInfo("nodeId", (Object)arg0);
            UserLoginDTO user = ShiroUtil.getUser();
            if (user != null) {
                if (user.getExtInfo("JTOKENID") == null) {
                    user.addExtInfo("JTOKENID", (Object)ShiroUtil.getToken());
                }
                tenant.addExtInfo("user", (Object)JSONUtil.toJSONString((Object)user));
            }
            EnvConfig.sendRedisMsg((String)VaParamSyncConfig.getParamTransferRelatedBusinessMsg(), (String)JSONUtil.toJSONString((Object)tenant));
            for (int i = 0; i < 20; ++i) {
                Boolean flag = stringRedisTemplate.hasKey((Object)tenant.getExtInfo("id").toString());
                if (flag == null || !flag.booleanValue()) {
                    try {
                        Thread.sleep(250L);
                    }
                    catch (InterruptedException e) {
                        logger.error("\u53c2\u6570\u5bfc\u5165\u5bfc\u51fa\u7b49\u5f85\u83b7\u53d6\u8d44\u6e90\u5173\u8054\u8282\u70b9\u5f02\u5e38", e);
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
            ArrayList<ResItem> resList = new ArrayList<ResItem>();
            for (VaParamTransferBusinessNode businessNode : nodes) {
                resList.add(new ResItem(businessNode.getId(), businessNode.getModuleId(), businessNode.getCategoryId()));
            }
            return resList;
        }
        return null;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public List<NameMapperBean> handleMapper() throws TransferException {
        return null;
    }

    public List<GuidMapperBean> handleMapper(List<NameMapperBean> arg0) throws TransferException {
        return null;
    }

    public boolean supportExport(String arg0) throws TransferException {
        return this.supportExport;
    }

    public boolean supportExportData(String arg0) throws TransferException {
        return this.supportExportData;
    }
}

