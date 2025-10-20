/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.transfer.engine.ex.TransferException
 *  com.jiuqi.bi.transfer.engine.intf.IDataTransfer
 *  com.jiuqi.bi.transfer.engine.intf.IExportContext
 *  com.jiuqi.bi.transfer.engine.intf.IImportContext
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

import com.jiuqi.bi.transfer.engine.ex.TransferException;
import com.jiuqi.bi.transfer.engine.intf.IDataTransfer;
import com.jiuqi.bi.transfer.engine.intf.IExportContext;
import com.jiuqi.bi.transfer.engine.intf.IImportContext;
import com.jiuqi.va.domain.common.EnvConfig;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.user.UserLoginDTO;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.mapper.domain.TenantDO;
import com.jiuqi.va.paramsync.config.VaParamSyncConfig;
import com.jiuqi.va.paramsync.intf.VaParamTransferModuleIntf;
import com.jiuqi.va.paramsync.transfer.VaParamTransferLocalModuleHandle;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;

public class VaParamTransferData
implements IDataTransfer {
    private static final Logger logger = LoggerFactory.getLogger(VaParamTransferData.class);
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

    public InputStream exportData(IExportContext arg0, String arg1) throws TransferException {
        String dataInfoStr = null;
        VaParamTransferLocalModuleHandle localModuleHandle = (VaParamTransferLocalModuleHandle)ApplicationContextRegister.getBean(VaParamTransferLocalModuleHandle.class);
        VaParamTransferModuleIntf currModule = localModuleHandle.getModule(this.module.getName());
        if (currModule != null) {
            dataInfoStr = currModule.getExportDataInfo(this.categoryId, arg1);
        } else if (VaParamSyncConfig.isRedisEnable()) {
            StringRedisTemplate stringRedisTemplate = (StringRedisTemplate)ApplicationContextRegister.getBean(StringRedisTemplate.class);
            TenantDO tenant = new TenantDO();
            tenant.addExtInfo("id", (Object)UUID.randomUUID().toString());
            tenant.addExtInfo("module", (Object)this.module.getName());
            tenant.addExtInfo("category", (Object)this.categoryId);
            tenant.addExtInfo("nodeId", (Object)arg1);
            UserLoginDTO user = ShiroUtil.getUser();
            if (user != null) {
                Object extInfo = user.getExtInfo("JTOKENID");
                user.addExtInfo("JTOKENID", extInfo == null ? ShiroUtil.getToken() : extInfo);
                tenant.addExtInfo("user", (Object)JSONUtil.toJSONString((Object)user));
            }
            EnvConfig.sendRedisMsg((String)VaParamSyncConfig.getParamTransferDataExportMsg(), (String)JSONUtil.toJSONString((Object)tenant));
            for (int i = 0; i < 50; ++i) {
                Boolean flag = stringRedisTemplate.hasKey((Object)tenant.getExtInfo("id").toString());
                if (Boolean.TRUE.equals(flag)) {
                    dataInfoStr = (String)stringRedisTemplate.opsForValue().get((Object)tenant.getExtInfo("id").toString());
                    break;
                }
                try {
                    Thread.sleep(100L);
                    continue;
                }
                catch (InterruptedException e) {
                    logger.error("\u53c2\u6570\u5bfc\u51fa\u7b49\u5f85\u83b7\u53d6\u8d44\u6e90\u6570\u636e\u5f02\u5e38", e);
                }
            }
        }
        if (StringUtils.hasText(dataInfoStr)) {
            return new ByteArrayInputStream(dataInfoStr.getBytes(StandardCharsets.UTF_8));
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void importData(IImportContext arg0, InputStream arg1) throws TransferException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();){
            byte[] buffer = new byte[1024];
            int len = -1;
            while ((len = arg1.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }
            byte[] data = bos.toByteArray();
            VaParamTransferLocalModuleHandle localModuleHandle = (VaParamTransferLocalModuleHandle)ApplicationContextRegister.getBean(VaParamTransferLocalModuleHandle.class);
            VaParamTransferModuleIntf currModule = localModuleHandle.getModule(this.module.getName());
            boolean isImportMultiLanguage = arg0.isImportMultiLanguage();
            if (currModule != null) {
                currModule.importDataInfo(this.categoryId, arg0.getTargetGuid(), new String(data, StandardCharsets.UTF_8), isImportMultiLanguage);
                return;
            }
            if (!VaParamSyncConfig.isRedisEnable()) {
                return;
            }
            StringRedisTemplate stringRedisTemplate = (StringRedisTemplate)ApplicationContextRegister.getBean(StringRedisTemplate.class);
            TenantDO tenant = new TenantDO();
            tenant.addExtInfo("id", (Object)UUID.randomUUID().toString());
            tenant.addExtInfo("module", (Object)this.module.getName());
            tenant.addExtInfo("category", (Object)this.categoryId);
            tenant.addExtInfo("isImportMultiLanguage", (Object)isImportMultiLanguage);
            tenant.addExtInfo("targetId", (Object)arg0.getTargetGuid());
            tenant.addExtInfo("data", (Object)new String(data, StandardCharsets.UTF_8));
            UserLoginDTO user = ShiroUtil.getUser();
            if (user != null) {
                if (user.getExtInfo("JTOKENID") == null) {
                    user.addExtInfo("JTOKENID", (Object)ShiroUtil.getToken());
                }
                tenant.addExtInfo("user", (Object)JSONUtil.toJSONString((Object)user));
            }
            EnvConfig.sendRedisMsg((String)VaParamSyncConfig.getParamTransferDataImportMsg(), (String)JSONUtil.toJSONString((Object)tenant));
            int i = 0;
            while (i < 20) {
                Boolean flag = stringRedisTemplate.hasKey((Object)tenant.getExtInfo("id").toString());
                if (Boolean.TRUE.equals(flag)) {
                    return;
                }
                try {
                    Thread.sleep(500L);
                }
                catch (InterruptedException e) {
                    logger.error("\u53c2\u6570\u5bfc\u5165\u7b49\u5f85\u8d44\u6e90\u6570\u636e\u5bfc\u5165\u5b8c\u6210\u5f02\u5e38", e);
                }
                ++i;
            }
            return;
        }
        catch (IOException e) {
            logger.error("\u53c2\u6570\u5bfc\u5165\u8d44\u6e90\u6570\u636e\u5f02\u5e38", e);
            return;
        }
        finally {
            try {
                arg1.close();
            }
            catch (IOException e) {
                logger.error("\u53c2\u6570\u5bfc\u5165\u5173\u95ed\u6d41\u5f02\u5e38", e);
            }
        }
    }
}

