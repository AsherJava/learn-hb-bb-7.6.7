/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.estimation.common.StringLogger
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 *  com.jiuqi.nvwa.definition.interval.dao.DesignIndexModelDao
 *  com.jiuqi.nvwa.definition.service.DataModelDeployService
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.data.estimation.sub.database.intf.impl;

import com.jiuqi.nr.data.estimation.common.StringLogger;
import com.jiuqi.nr.data.estimation.sub.database.common.SubDatabaseLogger;
import com.jiuqi.nr.data.estimation.sub.database.dao.IDataSchemeSubDatabaseDao;
import com.jiuqi.nr.data.estimation.sub.database.entity.DataSchemeSubDatabase;
import com.jiuqi.nr.data.estimation.sub.database.entity.DataSchemeSubDatabaseDefine;
import com.jiuqi.nr.data.estimation.sub.database.entity.IDataSchemeSubDatabase;
import com.jiuqi.nr.data.estimation.sub.database.intf.ICopiedTableGenerator;
import com.jiuqi.nr.data.estimation.sub.database.intf.IDataSchemeSubDatabaseHelper;
import com.jiuqi.nr.data.estimation.sub.database.intf.IMakeSubDatabaseParam;
import com.jiuqi.nr.data.estimation.sub.database.intf.IOriginalDesTableModelDefine;
import com.jiuqi.nr.data.estimation.sub.database.intf.impl.CreateSubDatabaseExecutor;
import com.jiuqi.nr.data.estimation.sub.database.intf.impl.OriginalDesTableModelDefine;
import com.jiuqi.nr.data.estimation.sub.database.intf.impl.RemoveSubDatabaseExecutor;
import com.jiuqi.nr.data.estimation.sub.database.intf.impl.UpdateSubDatabaseExecutor;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import com.jiuqi.nvwa.definition.interval.dao.DesignIndexModelDao;
import com.jiuqi.nvwa.definition.service.DataModelDeployService;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.nvwa.definition.service.DesignDataModelService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataSchemeSubDatabaseHelper
implements IDataSchemeSubDatabaseHelper {
    @Resource
    public DesignIndexModelDao designIndexModelDao;
    @Resource
    public IDataSchemeSubDatabaseDao subDatabaseDao;
    @Resource
    public DataModelDeployService dataModelDeployService;
    @Resource
    public DesignDataModelService designDataModelService;
    @Resource
    public IRuntimeDataSchemeService runtimeDataSchemeService;
    @Resource
    public DataModelService dataModelService;
    private Map<String, IMakeSubDatabaseParam> subDatabaseParamMap;

    @Autowired
    public DataSchemeSubDatabaseHelper(List<IMakeSubDatabaseParam> list) {
        if (null != list && !list.isEmpty()) {
            this.subDatabaseParamMap = new HashMap<String, IMakeSubDatabaseParam>();
            list.forEach(e -> this.subDatabaseParamMap.put(e.getSubDatabaseCode(), (IMakeSubDatabaseParam)e));
        }
    }

    private IMakeSubDatabaseParam getMakeSubDatabaseParam(String databaseCode) {
        return this.subDatabaseParamMap != null && this.subDatabaseParamMap.containsKey(databaseCode) ? this.subDatabaseParamMap.get(databaseCode) : null;
    }

    @Override
    public StringLogger createSubDatabase(String dataSchemeKey, String databaseCode) {
        SubDatabaseLogger logger = new SubDatabaseLogger(LoggerFactory.getLogger(CreateSubDatabaseExecutor.class));
        IMakeSubDatabaseParam makePara = this.getMakeSubDatabaseParam(databaseCode);
        if (makePara != null) {
            CreateSubDatabaseExecutor executor = new CreateSubDatabaseExecutor(this, dataSchemeKey);
            executor.execute(makePara, logger);
        } else {
            logger.logError("\u65e0\u6548\u7684\u5206\u5e93\u53c2\u6570\uff1a\u83b7\u53d6\u4e0d\u5230[" + databaseCode + "]\u7684\u5206\u5e93\u53c2\u6570\u5b9e\u4f8b");
        }
        return logger;
    }

    @Override
    public StringLogger updateSubDatabase(String dataSchemeKey, String databaseCode) {
        SubDatabaseLogger logger = new SubDatabaseLogger(LoggerFactory.getLogger(UpdateSubDatabaseExecutor.class));
        IMakeSubDatabaseParam makePara = this.getMakeSubDatabaseParam(databaseCode);
        if (makePara != null) {
            UpdateSubDatabaseExecutor executor = new UpdateSubDatabaseExecutor();
            executor.execute(makePara, logger);
        } else {
            logger.logError("\u65e0\u6548\u7684\u5206\u5e93\u53c2\u6570\uff1a\u83b7\u53d6\u4e0d\u5230[" + databaseCode + "]\u7684\u5206\u5e93\u53c2\u6570\u5b9e\u4f8b");
        }
        return logger;
    }

    @Override
    public StringLogger deleteSubDatabase(String dataSchemeKey, String databaseCode, boolean forceDelete) {
        SubDatabaseLogger logger = new SubDatabaseLogger(LoggerFactory.getLogger(RemoveSubDatabaseExecutor.class));
        IMakeSubDatabaseParam makePara = this.getMakeSubDatabaseParam(databaseCode);
        if (makePara != null) {
            RemoveSubDatabaseExecutor executor = new RemoveSubDatabaseExecutor(this, dataSchemeKey, forceDelete);
            executor.execute(makePara, logger);
        } else {
            logger.logError("\u65e0\u6548\u7684\u5206\u5e93\u53c2\u6570\uff1a\u83b7\u53d6\u4e0d\u5230[" + databaseCode + "]\u7684\u5206\u5e93\u53c2\u6570\u5b9e\u4f8b");
        }
        return logger;
    }

    @Override
    public IDataSchemeSubDatabase querySubDatabaseDefine(String dataSchemeKey, String databaseCode) {
        DataSchemeSubDatabaseDefine record;
        IMakeSubDatabaseParam makePara = this.getMakeSubDatabaseParam(databaseCode);
        if (makePara != null && (record = this.subDatabaseDao.findRecord(dataSchemeKey, databaseCode)) != null) {
            return new DataSchemeSubDatabase(record, makePara);
        }
        return null;
    }

    @Override
    public boolean existSubDatabase(String dataSchemeKey, String databaseCode) {
        return this.querySubDatabaseDefine(dataSchemeKey, databaseCode) != null;
    }

    public List<IOriginalDesTableModelDefine> getDesignTableModelsByDataScheme(String dataSchemeKey) {
        List dataFields = this.runtimeDataSchemeService.getDeployInfoBySchemeKey(dataSchemeKey);
        ArrayList<IOriginalDesTableModelDefine> tableModelInfos = new ArrayList<IOriginalDesTableModelDefine>();
        HashSet<String> distinctSet = new HashSet<String>();
        for (DataFieldDeployInfo fdi : dataFields) {
            if (distinctSet.contains(fdi.getTableModelKey())) continue;
            DesignTableModelDefine tableModelDefine = this.designDataModelService.getTableModelDefine(fdi.getTableModelKey());
            DataTable dataTable = this.runtimeDataSchemeService.getDataTable(fdi.getDataTableKey());
            DataTableType dataTableType = dataTable.getDataTableType();
            tableModelInfos.add(new OriginalDesTableModelDefine(tableModelDefine, dataTableType));
            distinctSet.add(fdi.getTableModelKey());
        }
        return tableModelInfos;
    }

    public List<DesignTableModelDefine> getDesignTableModelsByCopied(String dataSchemeKey, ICopiedTableGenerator tableGenerator) {
        ArrayList<DesignTableModelDefine> copiedTableModels = new ArrayList<DesignTableModelDefine>();
        List<IOriginalDesTableModelDefine> tableInfos = this.getDesignTableModelsByDataScheme(dataSchemeKey);
        for (IOriginalDesTableModelDefine tableInfo : tableInfos) {
            DesignTableModelDefine copiedTableModel = this.designDataModelService.getTableModelDefineByCode(tableGenerator.madeCopiedTableCode(tableInfo.getDesTableModelDefine().getCode()));
            if (copiedTableModel == null) continue;
            copiedTableModels.add(copiedTableModel);
        }
        return copiedTableModels;
    }
}

