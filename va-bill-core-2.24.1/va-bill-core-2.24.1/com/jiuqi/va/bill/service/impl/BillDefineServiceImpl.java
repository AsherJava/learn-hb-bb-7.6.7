/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.impl.model.I18nPluginManager
 *  com.jiuqi.va.biz.intf.model.I18nPlugin
 *  com.jiuqi.va.biz.intf.model.ModelContext
 *  com.jiuqi.va.biz.intf.model.ModelDefine
 *  com.jiuqi.va.biz.intf.model.ModelDefineService
 *  com.jiuqi.va.biz.intf.model.ModelException
 *  com.jiuqi.va.biz.intf.model.ModelManager
 *  com.jiuqi.va.biz.intf.model.ModelType
 *  com.jiuqi.va.biz.intf.model.PluginDefine
 *  com.jiuqi.va.i18n.domain.VaI18nResourceDTO
 *  com.jiuqi.va.i18n.feign.VaI18nClient
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  com.jiuqi.va.utils.VaI18nParamUtil
 */
package com.jiuqi.va.bill.service.impl;

import com.jiuqi.va.bill.intf.BillContext;
import com.jiuqi.va.bill.intf.BillDefine;
import com.jiuqi.va.bill.intf.BillDefineService;
import com.jiuqi.va.bill.intf.BillException;
import com.jiuqi.va.bill.intf.BillModel;
import com.jiuqi.va.bill.utils.BillCoreI18nUtil;
import com.jiuqi.va.biz.impl.model.I18nPluginManager;
import com.jiuqi.va.biz.intf.model.I18nPlugin;
import com.jiuqi.va.biz.intf.model.ModelContext;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.intf.model.ModelDefineService;
import com.jiuqi.va.biz.intf.model.ModelException;
import com.jiuqi.va.biz.intf.model.ModelManager;
import com.jiuqi.va.biz.intf.model.ModelType;
import com.jiuqi.va.biz.intf.model.PluginDefine;
import com.jiuqi.va.i18n.domain.VaI18nResourceDTO;
import com.jiuqi.va.i18n.feign.VaI18nClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.mapper.domain.TenantDO;
import com.jiuqi.va.utils.VaI18nParamUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class BillDefineServiceImpl
implements BillDefineService {
    private static final Logger log = LoggerFactory.getLogger(BillDefineServiceImpl.class);
    private ModelManager modelManager;
    @Autowired
    private ModelDefineService modelDefineService;
    @Autowired
    private I18nPluginManager i18nPluginManager;
    @Autowired
    private VaI18nClient vaDataResourceClient;

    private ModelManager getModelManager() {
        if (this.modelManager == null) {
            this.modelManager = (ModelManager)ApplicationContextRegister.getBean(ModelManager.class);
        }
        return this.modelManager;
    }

    @Override
    public BillDefine getDefine(String billType) {
        BillDefine billDefine;
        Exception exception = null;
        try {
            billDefine = (BillDefine)this.modelDefineService.getDefine(billType);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            exception = e;
            if (StringUtils.hasText(billType) && billType.contains("_")) {
                int indexOf = billType.indexOf("_", billType.indexOf("_") + 1) + 1;
                billType = billType.substring(indexOf);
            }
            throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.billdefineservice.billdefine") + billType + e.getMessage(), exception);
        }
        if (billDefine != null) {
            if (VaI18nParamUtil.getTranslationEnabled().booleanValue()) {
                this.defineI18nTrans(billDefine);
            }
            return billDefine;
        }
        ModelType modelType = (ModelType)this.getModelManager().find(billType);
        if (modelType != null) {
            try {
                billDefine = (BillDefine)modelType.getModelDefineClass().newInstance();
            }
            catch (IllegalAccessException | InstantiationException e) {
                throw new ModelException((Throwable)e);
            }
            modelType.initModelDefine((ModelDefine)billDefine, billType);
            if (VaI18nParamUtil.getTranslationEnabled().booleanValue()) {
                this.defineI18nTrans(billDefine);
            }
            return billDefine;
        }
        throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.billdefineservice.nofindbilldefine") + billType, exception);
    }

    @Override
    public BillDefine getDefine(String billType, boolean i18nFlag) {
        BillDefine billDefine;
        Exception exception = null;
        try {
            billDefine = (BillDefine)this.modelDefineService.getDefine(billType);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            exception = e;
            if (StringUtils.hasText(billType) && billType.contains("_")) {
                int indexOf = billType.indexOf("_", billType.indexOf("_") + 1) + 1;
                billType = billType.substring(indexOf);
            }
            throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.billdefineservice.billdefine") + billType + e.getMessage(), exception);
        }
        if (billDefine != null) {
            if (i18nFlag && VaI18nParamUtil.getTranslationEnabled().booleanValue()) {
                this.defineI18nTrans(billDefine);
            }
            return billDefine;
        }
        ModelType modelType = (ModelType)this.getModelManager().find(billType);
        if (modelType != null) {
            try {
                billDefine = (BillDefine)modelType.getModelDefineClass().newInstance();
            }
            catch (IllegalAccessException | InstantiationException e) {
                throw new ModelException((Throwable)e);
            }
            modelType.initModelDefine((ModelDefine)billDefine, billType);
            if (i18nFlag && VaI18nParamUtil.getTranslationEnabled().booleanValue()) {
                this.defineI18nTrans(billDefine);
            }
            return billDefine;
        }
        throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.billdefineservice.nofindbilldefine") + billType, exception);
    }

    private void defineI18nTrans(BillDefine billDefine) {
        try {
            List i18nPluginList = this.i18nPluginManager.getI18nPluginList();
            if (i18nPluginList == null || i18nPluginList.size() <= 0) {
                return;
            }
            ArrayList keys = new ArrayList();
            for (I18nPlugin i18nPlugin : i18nPluginList) {
                List pluginKeys;
                PluginDefine plugin;
                if (!i18nPlugin.isBackEndTrans() || (plugin = (PluginDefine)billDefine.getPlugins().find(i18nPlugin.getName())) == null || (pluginKeys = i18nPlugin.getAllI18nKeys(plugin, (ModelDefine)billDefine)) == null || pluginKeys.isEmpty()) continue;
                keys.addAll(pluginKeys);
            }
            VaI18nResourceDTO dataResourceDTO = new VaI18nResourceDTO();
            dataResourceDTO.setKey(keys);
            List results = this.vaDataResourceClient.queryList(dataResourceDTO);
            if (results == null || results.size() <= 0) {
                return;
            }
            HashMap i18nValueMap = new HashMap();
            int i = 0;
            for (String result : results) {
                i18nValueMap.put(keys.get(i), result);
                ++i;
            }
            for (I18nPlugin i18nPlugin : i18nPluginList) {
                PluginDefine plugin;
                if (!i18nPlugin.isBackEndTrans() || (plugin = (PluginDefine)billDefine.getPlugins().find(i18nPlugin.getName())) == null) continue;
                i18nPlugin.processForI18n(plugin, (ModelDefine)billDefine, i18nValueMap);
            }
        }
        catch (Exception e) {
            log.error("\u5355\u636e\u5b9a\u4e49\u56fd\u9645\u5316\u8f6c\u5316\u5f02\u5e38", e);
        }
    }

    @Override
    public BillModel createModel(BillContext context, BillDefine define) {
        return (BillModel)this.modelDefineService.createModel((ModelContext)context, (ModelDefine)define);
    }

    @Override
    public BillModel createModel(BillContext context, String billType) {
        BillDefine define = context.isDisable18n() ? this.getDefine(billType, false) : this.getDefine(billType);
        return this.createModel(context, define);
    }

    @Override
    public BillModel createModel(BillContext context, String billType, long defver) {
        BillDefine define = this.getDefine(billType, defver);
        return this.createModel(context, define);
    }

    public BillDefine getDefine(String billType, long ver) {
        BillDefine billDefine;
        Exception exception = null;
        try {
            billDefine = (BillDefine)this.modelDefineService.getDefine(billType, Long.valueOf(ver));
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            exception = e;
            if (StringUtils.hasText(billType) && billType.contains("_")) {
                int indexOf = billType.indexOf("_", billType.indexOf("_") + 1) + 1;
                billType = billType.substring(indexOf);
            }
            throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.billdefineservice.billdefine") + billType + e.getMessage(), exception);
        }
        if (billDefine != null) {
            if (VaI18nParamUtil.getTranslationEnabled().booleanValue()) {
                this.defineI18nTrans(billDefine);
            }
            return billDefine;
        }
        ModelType modelType = (ModelType)this.getModelManager().find(billType);
        if (modelType != null) {
            try {
                billDefine = (BillDefine)modelType.getModelDefineClass().newInstance();
            }
            catch (IllegalAccessException | InstantiationException e) {
                throw new ModelException((Throwable)e);
            }
            modelType.initModelDefine((ModelDefine)billDefine, billType);
            if (VaI18nParamUtil.getTranslationEnabled().booleanValue()) {
                this.defineI18nTrans(billDefine);
            }
            return billDefine;
        }
        throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.billdefineservice.nofindbilldefine") + billType, exception);
    }

    @Override
    public BillDefine getDefine(TenantDO tenantDO) {
        String definecode = tenantDO.getExtInfo().get("DEFINECODE") == null ? "" : tenantDO.getExtInfo().get("DEFINECODE").toString();
        ModelType modelType = (ModelType)this.getModelManager().find(definecode);
        if (modelType != null) {
            BillDefine billDefine;
            try {
                billDefine = (BillDefine)modelType.getModelDefineClass().newInstance();
            }
            catch (IllegalAccessException | InstantiationException e) {
                throw new ModelException((Throwable)e);
            }
            modelType.initModelDefine((ModelDefine)billDefine, definecode);
            return billDefine;
        }
        return (BillDefine)this.modelDefineService.getDefine(tenantDO);
    }

    @Override
    public BillDefine getDefine(String billType, String externalViewName) {
        BillDefine billDefine;
        Exception exception = null;
        try {
            billDefine = (BillDefine)this.modelDefineService.getDefine(billType, externalViewName);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            exception = e;
            throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.billdefineservice.billdefine") + billType + e.getMessage(), exception);
        }
        if (billDefine != null) {
            if (VaI18nParamUtil.getTranslationEnabled().booleanValue()) {
                this.defineI18nTrans(billDefine);
            }
            return billDefine;
        }
        throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.billdefineservice.nofindbilldefine") + billType, exception);
    }

    @Override
    public BillModel createModel(BillContext context, String billType, String externalViewName) {
        BillDefine define = this.getDefine(billType, externalViewName);
        return this.createModel(context, define);
    }
}

