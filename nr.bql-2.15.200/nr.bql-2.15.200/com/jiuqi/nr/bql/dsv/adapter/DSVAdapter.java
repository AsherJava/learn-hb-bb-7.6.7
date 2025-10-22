/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.adhoc.ext.model.DSV
 *  com.jiuqi.bi.adhoc.ext.model.UpdateInfo
 *  com.jiuqi.bi.adhoc.model.TableInfo
 *  com.jiuqi.bi.logging.ILogger
 *  com.jiuqi.bi.logging.LogManager
 *  com.jiuqi.np.dataengine.common.DataEngineConsts
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.core.DeployStatusEnum
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 */
package com.jiuqi.nr.bql.dsv.adapter;

import com.jiuqi.bi.adhoc.ext.model.DSV;
import com.jiuqi.bi.adhoc.ext.model.UpdateInfo;
import com.jiuqi.bi.adhoc.model.TableInfo;
import com.jiuqi.bi.logging.ILogger;
import com.jiuqi.bi.logging.LogManager;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.nr.bql.dsv.adapter.DSVContext;
import com.jiuqi.nr.bql.dsv.adapter.TableInfoAdapter;
import com.jiuqi.nr.bql.intf.ICalibreDimTableProvider;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.core.DeployStatusEnum;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class DSVAdapter {
    private static final ILogger logger = LogManager.getLogger((String)"com.jiuqi.nvwa.bql.dsv");
    @Autowired
    @Qualifier(value="RuntimeDataSchemeNoCacheServiceImpl-NO_CACHE")
    private IRuntimeDataSchemeService dataSchemeService;
    @Autowired
    private IDesignDataSchemeService designDataSchemeService;
    @Autowired
    private TableInfoAdapter tableInfoAdapter;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired(required=false)
    private ICalibreDimTableProvider calibreDimTableProvider;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;

    public DSV getDSV(String dsvName) throws Exception {
        try {
            String dataSchemeCode = DSVAdapter.getDataSchemeCodebyDSVName(dsvName);
            DataScheme dataScheme = this.dataSchemeService.getDataSchemeByCode(dataSchemeCode);
            if (dataScheme == null) {
                throw new Exception("\u672a\u627e\u5230\u6807\u8bc6\u4e3a   " + dataSchemeCode + " \u7684\u6570\u636e\u65b9\u6848");
            }
            DSV dsv = new DSV();
            dsv.setName(dsvName);
            dsv.setTitle(dataScheme.getTitle());
            dsv.setGuid(dataScheme.getKey());
            dsv.setConnName("@NR");
            DSVContext context = this.createDSVContext(dataScheme, dsv);
            List<TableInfo> tableInfos = this.tableInfoAdapter.getAllTableInfos(context, dataScheme.getKey(), dsv);
            dsv.getTables().addAll(tableInfos);
            return dsv;
        }
        catch (Exception e) {
            logger.error("\u6570\u636e\u65b9\u6848 \u3010 " + dsvName + "\u3011 \u9002\u914d dsv\u6a21\u578b\u51fa\u9519\uff1a" + e.getMessage());
            throw e;
        }
    }

    public UpdateInfo queryUpdateInfo(String dsvName) {
        DataScheme dataScheme = this.dataSchemeService.getDataSchemeByCode(DSVAdapter.getDataSchemeCodebyDSVName(dsvName));
        UpdateInfo updateInfo = this.getUpdateInfo(dataScheme);
        return updateInfo;
    }

    public List<UpdateInfo> queryUpdateInfos(String[] dsvNames) {
        ArrayList<UpdateInfo> updateInfos = new ArrayList<UpdateInfo>(dsvNames.length);
        for (String dsvName : dsvNames) {
            UpdateInfo updateInfo = this.queryUpdateInfo(dsvName);
            updateInfos.add(updateInfo);
        }
        return updateInfos;
    }

    public List<UpdateInfo> queryAllUpdateInfos() {
        List allDataSchemes = this.dataSchemeService.getAllDataScheme();
        if (allDataSchemes == null || allDataSchemes.isEmpty()) {
            List allDesignDataSchemes = this.designDataSchemeService.getAllDataScheme();
            if (allDesignDataSchemes != null && allDesignDataSchemes.size() > 0) {
                logger.info("\u67e5\u8be2\u7f13\u5b58\u5237\u65b0\uff1a\u672a\u53d1\u73b0\u53ef\u7528\u6570\u636e\u65b9\u6848\uff01");
            }
            return new ArrayList<UpdateInfo>(0);
        }
        ArrayList<UpdateInfo> allUpdateInfos = new ArrayList<UpdateInfo>(allDataSchemes.size());
        for (DataScheme dataScheme : allDataSchemes) {
            UpdateInfo updateInfo = this.getUpdateInfo(dataScheme);
            if (DataEngineConsts.DATA_ENGINE_DEBUG) {
                // empty if block
            }
            allUpdateInfos.add(updateInfo);
        }
        return allUpdateInfos;
    }

    public static String getDSVName(DataScheme dataScheme) {
        return "NR_" + dataScheme.getCode();
    }

    public static String getDataSchemeCodebyDSVName(String dsvName) {
        return dsvName.substring("NR_".length());
    }

    private DSVContext createDSVContext(DataScheme dataScheme, DSV dsv) throws Exception {
        DSVContext context = new DSVContext(dsv, dataScheme, this.dataDefinitionRuntimeController);
        context.doInit(this.dataSchemeService, this.entityMetaService, this.periodEngineService, this.dataDefinitionRuntimeController);
        return context;
    }

    private UpdateInfo getUpdateInfo(DataScheme dataScheme) {
        UpdateInfo updateInfo = new UpdateInfo();
        updateInfo.setDsvName(DSVAdapter.getDSVName(dataScheme));
        updateInfo.setDsvType("nr.datascheme");
        try {
            DeployStatusEnum deployStatus = this.dataSchemeService.getDataSchemeDeployStatus(dataScheme.getKey());
            if (deployStatus == DeployStatusEnum.DEPLOY || deployStatus == DeployStatusEnum.FAIL) {
                logger.debug("\u6570\u636e\u65b9\u6848" + dataScheme.getCode() + "\u6b63\u5728\u53d1\u5e03\uff0c\u66f4\u65b0\u65f6\u95f4\u88ab\u91cd\u7f6e\uff0c\u7b49\u5f85\u4e0b\u6b21\u5237\u65b0");
                updateInfo.setUpdateTime(new Date(0L));
            } else {
                Date calibreDimUpdateTime;
                DataTable table = this.dataSchemeService.getLatestDataTableByScheme(dataScheme.getKey());
                Date updateTime = null;
                updateTime = table != null ? (dataScheme.getUpdateTime().compareTo(table.getUpdateTime()) > 0 ? new Date(dataScheme.getUpdateTime().toEpochMilli()) : new Date(table.getUpdateTime().toEpochMilli())) : new Date(dataScheme.getUpdateTime().toEpochMilli());
                if (this.calibreDimTableProvider != null && (calibreDimUpdateTime = this.calibreDimTableProvider.getLastUpdateTime(dataScheme)).compareTo(updateTime) > 0) {
                    updateTime = calibreDimUpdateTime;
                }
                updateInfo.setUpdateTime(updateTime);
            }
        }
        catch (Exception e) {
            updateInfo.setUpdateTime(new Date(0L));
            logger.error("\u83b7\u53d6\u6570\u636e\u65b9\u6848 \u3010 " + dataScheme.getCode() + "\u3011\u7684\u66f4\u65b0\u65f6\u95f4\u51fa\u9519\uff01", (Throwable)e);
        }
        return updateInfo;
    }

    public static ILogger getLogger() {
        return logger;
    }
}

