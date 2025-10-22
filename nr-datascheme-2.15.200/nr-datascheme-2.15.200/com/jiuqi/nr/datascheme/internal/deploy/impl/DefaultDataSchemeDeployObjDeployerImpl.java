/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.facade.design.DesignCatalogModelDefine
 *  com.jiuqi.nvwa.definition.service.CatalogModelService
 */
package com.jiuqi.nr.datascheme.internal.deploy.impl;

import com.jiuqi.nr.datascheme.internal.dao.impl.DataSchemeDeployStatusDaoImpl;
import com.jiuqi.nr.datascheme.internal.deploy.IDataSchemeDeployObjDeployer;
import com.jiuqi.nr.datascheme.internal.deploy.RuntimeDataSchemeManagerService;
import com.jiuqi.nr.datascheme.internal.deploy.common.DataSchemeDeployObj;
import com.jiuqi.nr.datascheme.internal.deploy.common.DeployContext;
import com.jiuqi.nr.datascheme.internal.deploy.common.DeployType;
import com.jiuqi.nr.datascheme.internal.deploy.impl.DataSchemeDeployServiceImpl;
import com.jiuqi.nvwa.definition.facade.design.DesignCatalogModelDefine;
import com.jiuqi.nvwa.definition.service.CatalogModelService;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DefaultDataSchemeDeployObjDeployerImpl
implements IDataSchemeDeployObjDeployer {
    @Autowired
    private DataSchemeDeployStatusDaoImpl dataSchemeDeployStatusDao;
    @Autowired
    private RuntimeDataSchemeManagerService runtimeDataSchemeManager;
    @Autowired
    private CatalogModelService catalogModelService;
    private static final Logger LOGGER = LoggerFactory.getLogger(DataSchemeDeployServiceImpl.class);

    @Override
    public void doDeploy(DeployContext context, DataSchemeDeployObj info) {
        if (context.isPreDeploy()) {
            return;
        }
        String dataSchemeKey = info.getDataScheme().getKey();
        this.deployCatalog(dataSchemeKey, info);
        this.deployI18n(dataSchemeKey);
        this.deployAdjust(dataSchemeKey);
    }

    private void deleteDeployStatus(String dataSchemeKey) {
        this.dataSchemeDeployStatusDao.delete(dataSchemeKey);
    }

    private void deployCatalog(String dataSchemeKey, DataSchemeDeployObj dataCatalogDeployInfo) {
        if (null == dataCatalogDeployInfo) {
            LOGGER.info("\u6570\u636e\u65b9\u6848\u53d1\u5e03\uff1a\u6ca1\u6709\u68c0\u6d4b\u5230\u65b9\u6848\u3001\u516c\u5171\u7ef4\u5ea6\u3001\u76ee\u5f55\u53d8\u5316\u3002");
            return;
        }
        if (DeployType.NONE == dataCatalogDeployInfo.getState() && DeployType.NONE == dataCatalogDeployInfo.getDimState() && !dataCatalogDeployInfo.needUpdateDataGroup()) {
            LOGGER.info("\u6570\u636e\u65b9\u6848\u53d1\u5e03\uff1a\u6ca1\u6709\u68c0\u6d4b\u5230\u65b9\u6848\u3001\u516c\u5171\u7ef4\u5ea6\u3001\u76ee\u5f55\u53d8\u5316\u3002");
            return;
        }
        if (DeployType.ADD.equals((Object)dataCatalogDeployInfo.getState())) {
            List<DesignCatalogModelDefine> addCatalogModels = dataCatalogDeployInfo.getAddCatalogModels();
            this.catalogModelService.insertCatalogModelDefines(addCatalogModels.toArray(new DesignCatalogModelDefine[0]));
            this.runtimeDataSchemeManager.updateRuntimeDataScheme(dataSchemeKey);
            this.runtimeDataSchemeManager.updateRuntimeDataSchemeDim(dataSchemeKey);
            this.runtimeDataSchemeManager.updateRuntimeDataGroup(dataSchemeKey);
        } else if (DeployType.DELETE.equals((Object)dataCatalogDeployInfo.getState())) {
            List<String> deleteCalalogModelKeys = dataCatalogDeployInfo.getDeleteCalalogModelKeys();
            this.catalogModelService.deleteCatalogModelDefines(deleteCalalogModelKeys.toArray(new String[0]));
            this.runtimeDataSchemeManager.deleteRuntimeDataScheme(dataSchemeKey);
            this.runtimeDataSchemeManager.deleteRuntimeDataSchemeDim(dataSchemeKey);
            this.runtimeDataSchemeManager.deleteRuntimeDataGroup(dataSchemeKey);
            this.deleteDeployStatus(dataSchemeKey);
        } else {
            List<String> deleteCalalogModelKeys = dataCatalogDeployInfo.getDeleteCalalogModelKeys();
            ArrayList<DesignCatalogModelDefine> allCatalogModels = new ArrayList<DesignCatalogModelDefine>(dataCatalogDeployInfo.getAddCatalogModels());
            allCatalogModels.addAll(dataCatalogDeployInfo.getUpdateCatalogModels());
            for (DesignCatalogModelDefine designCatalogModelDefine : allCatalogModels) {
                deleteCalalogModelKeys.add(designCatalogModelDefine.getID());
            }
            this.catalogModelService.deleteCatalogModelDefines(deleteCalalogModelKeys.toArray(new String[0]));
            this.catalogModelService.insertCatalogModelDefines(allCatalogModels.toArray(new DesignCatalogModelDefine[0]));
            if (DeployType.NONE != dataCatalogDeployInfo.getState()) {
                this.runtimeDataSchemeManager.updateRuntimeDataScheme(dataSchemeKey);
            }
            if (DeployType.NONE != dataCatalogDeployInfo.getDimState()) {
                this.runtimeDataSchemeManager.updateRuntimeDataSchemeDim(dataSchemeKey);
            }
            if (dataCatalogDeployInfo.needUpdateDataGroup()) {
                this.runtimeDataSchemeManager.updateRuntimeDataGroup(dataSchemeKey);
            }
        }
    }

    private void deployI18n(String dataSchemeKey) {
        LOGGER.info("\u6570\u636e\u65b9\u6848\u53d1\u5e03\uff1a \u53d1\u5e03\u591a\u8bed\u8a00\u53c2\u6570\u3002");
        this.runtimeDataSchemeManager.updateRuntimeI18n(dataSchemeKey);
    }

    private void deployAdjust(String dataSchemeKey) {
        LOGGER.info("\u6570\u636e\u65b9\u6848\u53d1\u5e03\uff1a \u53d1\u5e03\u8c03\u6574\u671f\u6570\u636e\u3002");
        this.runtimeDataSchemeManager.updateRuntimeAdjust(dataSchemeKey);
    }
}

