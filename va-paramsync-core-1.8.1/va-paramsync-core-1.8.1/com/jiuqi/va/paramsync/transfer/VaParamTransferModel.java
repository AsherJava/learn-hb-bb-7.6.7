/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.transfer.engine.BusinessNode
 *  com.jiuqi.bi.transfer.engine.DataMode
 *  com.jiuqi.bi.transfer.engine.ex.TransferException
 *  com.jiuqi.bi.transfer.engine.intf.IExportContext
 *  com.jiuqi.bi.transfer.engine.intf.IImportContext
 *  com.jiuqi.bi.transfer.engine.intf.IModelTransfer
 *  com.jiuqi.bi.transfer.engine.model.MetaExportModel
 *  com.jiuqi.va.domain.common.EnvConfig
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.user.UserLoginDTO
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  com.jiuqi.va.paramsync.config.VaParamSyncConfig
 *  com.jiuqi.va.paramsync.intf.VaParamTransferModuleIntf
 *  org.springframework.data.redis.core.StringRedisTemplate
 */
package com.jiuqi.va.paramsync.transfer;

import com.jiuqi.bi.transfer.engine.BusinessNode;
import com.jiuqi.bi.transfer.engine.DataMode;
import com.jiuqi.bi.transfer.engine.ex.TransferException;
import com.jiuqi.bi.transfer.engine.intf.IExportContext;
import com.jiuqi.bi.transfer.engine.intf.IImportContext;
import com.jiuqi.bi.transfer.engine.intf.IModelTransfer;
import com.jiuqi.bi.transfer.engine.model.MetaExportModel;
import com.jiuqi.va.domain.common.EnvConfig;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.user.UserLoginDTO;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.mapper.domain.TenantDO;
import com.jiuqi.va.paramsync.config.VaParamSyncConfig;
import com.jiuqi.va.paramsync.intf.VaParamTransferModuleIntf;
import com.jiuqi.va.paramsync.transfer.VaParamTransferLocalModuleHandle;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;

public class VaParamTransferModel
implements IModelTransfer {
    private static final Logger logger = LoggerFactory.getLogger(VaParamTransferModel.class);
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

    public MetaExportModel exportModel(IExportContext arg0, String arg1) throws TransferException {
        MetaExportModel exportModel = new MetaExportModel();
        if (!StringUtils.hasText(arg1)) {
            return exportModel;
        }
        String modelInfoStr = null;
        VaParamTransferLocalModuleHandle localModuleHandle = (VaParamTransferLocalModuleHandle)ApplicationContextRegister.getBean(VaParamTransferLocalModuleHandle.class);
        VaParamTransferModuleIntf currModule = localModuleHandle.getModule(this.module.getName());
        if (currModule != null) {
            modelInfoStr = currModule.getExportModelInfo(this.categoryId, arg1);
        } else if (VaParamSyncConfig.isRedisEnable()) {
            StringRedisTemplate stringRedisTemplate = (StringRedisTemplate)ApplicationContextRegister.getBean(StringRedisTemplate.class);
            TenantDO tenant = new TenantDO();
            tenant.addExtInfo("id", (Object)UUID.randomUUID().toString());
            tenant.addExtInfo("module", (Object)this.module.getName());
            tenant.addExtInfo("category", (Object)this.categoryId);
            tenant.addExtInfo("nodeId", (Object)arg1);
            UserLoginDTO user = ShiroUtil.getUser();
            if (user != null) {
                if (user.getExtInfo("JTOKENID") == null) {
                    user.addExtInfo("JTOKENID", (Object)ShiroUtil.getToken());
                }
                tenant.addExtInfo("user", (Object)JSONUtil.toJSONString((Object)user));
            }
            EnvConfig.sendRedisMsg((String)VaParamSyncConfig.getParamTransferModelExportMsg(), (String)JSONUtil.toJSONString((Object)tenant));
            for (int i = 0; i < 50; ++i) {
                Boolean flag = stringRedisTemplate.hasKey((Object)tenant.getExtInfo("id").toString());
                if (flag == null || !flag.booleanValue()) {
                    try {
                        Thread.sleep(100L);
                    }
                    catch (InterruptedException e) {
                        logger.error("\u53c2\u6570\u5bfc\u5165\u5bfc\u51fa\u7b49\u5f85\u83b7\u53d6\u8d44\u6e90\u6570\u636e\u5f02\u5e38", e);
                    }
                    continue;
                }
                modelInfoStr = (String)stringRedisTemplate.opsForValue().get((Object)tenant.getExtInfo("id").toString());
                break;
            }
        }
        if (!StringUtils.hasText(modelInfoStr)) {
            return null;
        }
        exportModel.setData(modelInfoStr.getBytes(StandardCharsets.UTF_8));
        return exportModel;
    }

    public void importModel(IImportContext arg0, byte[] arg1) throws TransferException {
        VaParamTransferLocalModuleHandle localModuleHandle = (VaParamTransferLocalModuleHandle)ApplicationContextRegister.getBean(VaParamTransferLocalModuleHandle.class);
        VaParamTransferModuleIntf currModule = localModuleHandle.getModule(this.module.getName());
        boolean importDataFlag = this.getImportDataFlag(arg0);
        boolean isImportMultiLanguage = arg0.isImportMultiLanguage();
        if (currModule != null) {
            currModule.importModelInfo(this.categoryId, new String(arg1, StandardCharsets.UTF_8), isImportMultiLanguage, importDataFlag);
        } else if (VaParamSyncConfig.isRedisEnable()) {
            Boolean flag;
            StringRedisTemplate stringRedisTemplate = (StringRedisTemplate)ApplicationContextRegister.getBean(StringRedisTemplate.class);
            TenantDO tenant = new TenantDO();
            tenant.addExtInfo("id", (Object)UUID.randomUUID().toString());
            tenant.addExtInfo("module", (Object)this.module.getName());
            tenant.addExtInfo("category", (Object)this.categoryId);
            tenant.addExtInfo("isImportMultiLanguage", (Object)isImportMultiLanguage);
            tenant.addExtInfo("data", (Object)new String(arg1, StandardCharsets.UTF_8));
            tenant.addExtInfo("importDataFlag", (Object)importDataFlag);
            UserLoginDTO user = ShiroUtil.getUser();
            if (user != null) {
                if (user.getExtInfo("JTOKENID") == null) {
                    user.addExtInfo("JTOKENID", (Object)ShiroUtil.getToken());
                }
                tenant.addExtInfo("user", (Object)JSONUtil.toJSONString((Object)user));
            }
            EnvConfig.sendRedisMsg((String)VaParamSyncConfig.getParamTransferModelImportMsg(), (String)JSONUtil.toJSONString((Object)tenant));
            for (int i = 0; !(i >= 20 || (flag = stringRedisTemplate.hasKey((Object)tenant.getExtInfo("id").toString())) != null && flag.booleanValue()); ++i) {
                try {
                    Thread.sleep(500L);
                    continue;
                }
                catch (InterruptedException e) {
                    logger.error("\u53c2\u6570\u5bfc\u5165\u5bfc\u51fa\u7b49\u5f85\u8d44\u6e90\u6570\u636e\u5bfc\u5165\u5b8c\u6210\u5f02\u5e38", e);
                }
            }
        }
    }

    private boolean getImportDataFlag(IImportContext context) {
        if (Objects.isNull(context)) {
            return false;
        }
        try {
            DataMode dataMode;
            BusinessNode srcBusinessNode = context.getSrcBusinessNode();
            return srcBusinessNode != null && Objects.nonNull(dataMode = srcBusinessNode.getDataMode()) && DataMode.NONE != dataMode;
        }
        catch (Throwable e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }
}

