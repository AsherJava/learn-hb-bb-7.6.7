/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.nr.io.service.impl;

import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.io.dataset.IRegionDataSet;
import com.jiuqi.nr.io.params.base.RegionData;
import com.jiuqi.nr.io.params.base.TableContext;
import com.jiuqi.nr.io.params.output.EntityDatas;
import com.jiuqi.nr.io.params.output.ExportEntity;
import com.jiuqi.nr.io.service.ExportService;
import com.jiuqi.nr.io.service.IoEntityService;
import com.jiuqi.nr.io.service.impl.RegionsDataSetProvider;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExportServiceImpl
implements ExportService {
    private static final Logger log = LoggerFactory.getLogger(ExportServiceImpl.class);
    @Autowired
    RunTimeAuthViewController runTimeAuthViewController;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    private IEntityQuery entityQuery;
    @Autowired
    private IDataAccessProvider dataAccessProvider;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IoEntityService ioEntityService;
    @Autowired
    private IEntityMetaService metaService;
    @Autowired
    private RegionsDataSetProvider provider;

    @Override
    public List<IRegionDataSet> getRegionsDatas(TableContext context) {
        ArrayList<RegionData> regions = new ArrayList<RegionData>();
        if (context.getFormKey().contains(";")) {
            String[] forms = context.getFormKey().split(";");
            for (int i = 0; i < forms.length; ++i) {
                List<RegionData> formRegions = this.getRegions(forms[i]);
                regions.addAll(formRegions);
            }
        } else {
            List<RegionData> formRegions = this.getRegions(context.getFormKey());
            regions.addAll(formRegions);
        }
        ArrayList<IRegionDataSet> res = new ArrayList<IRegionDataSet>();
        for (RegionData regionData : regions) {
            IRegionDataSet region = this.provider.getRegionDataSet(context, regionData);
            if (!Objects.nonNull(region)) continue;
            res.add(region);
        }
        return res;
    }

    private ExportEntity getEntityInfo(IEntityTable item) {
        List allRows = item.getAllRows();
        ExportEntity expEntity = new ExportEntity();
        ArrayList<EntityDatas> entity = new ArrayList<EntityDatas>();
        for (IEntityRow row : allRows) {
            IEntityRow parent;
            EntityDatas en = new EntityDatas();
            en.setTitle(row.getTitle());
            en.setCode(row.getCode());
            String parentEntityKey = row.getParentEntityKey();
            String[] parentsEntityKeyDataPath = row.getParentsEntityKeyDataPath();
            if (null != parentEntityKey && null != (parent = item.findByEntityKey(parentEntityKey))) {
                en.setParentCode(parent.getCode());
            }
            if (null != parentsEntityKeyDataPath && parentsEntityKeyDataPath.length > 0) {
                String parentPath = "";
                for (int i = 0; i < parentsEntityKeyDataPath.length; ++i) {
                    parentPath = parentPath + "/" + item.findByEntityKey(parentsEntityKeyDataPath[i]).getCode();
                }
                en.setParentPath(parentPath);
            }
            entity.add(en);
        }
        expEntity.setDatas(entity);
        return expEntity;
    }

    @Override
    public List<ExportEntity> getEntitys(TableContext context) {
        ArrayList<ExportEntity> entitys;
        block6: {
            entitys = new ArrayList<ExportEntity>();
            try {
                String[] keys;
                ArrayList<IEntityTable> entityTable = new ArrayList<IEntityTable>();
                String masterKeys = this.runTimeViewController.getFormSchemeEntity(context.getFormSchemeKey());
                if (masterKeys == null) break block6;
                for (String key : keys = masterKeys.split(";")) {
                    EntityViewDefine entityView = null;
                    IEntityTable entityTables = null;
                    try {
                        entityView = this.entityViewRunTimeController.buildEntityView(key);
                        if (this.ioEntityService.isPeriod(key)) continue;
                        entityTables = this.ioEntityService.getEntityTable(context, key, this.getExecutorContext(context));
                    }
                    catch (Exception e) {
                        log.info("\u67e5\u8be2\u4e3b\u4f53\u5b9e\u4f53\u7ed3\u679c\u51fa\u9519{}", (Object)e.getMessage());
                    }
                    if (null == entityView || null == entityTables || null == entityTables.getAllRows() || entityTables.getAllRows().size() <= 0) continue;
                    entityTable.add(entityTables);
                    ExportEntity entityInfo = this.getEntityInfo(entityTables);
                    IEntityDefine entityDefine = this.metaService.queryEntity(entityView.getEntityId());
                    if (entityDefine != null) {
                        entityInfo.setTitle(entityDefine.getTitle());
                    }
                    TableModelDefine tableModel = this.metaService.getTableModel(entityView.getEntityId());
                    entityInfo.setTableName(tableModel.getName());
                    entityInfo.setDefaultImport(context.isImportEntitys());
                    entitys.add(entityInfo);
                }
            }
            catch (Exception e) {
                log.info("\u67e5\u8be2\u4e3b\u4f53\u5b9e\u4f53\u7ed3\u679c\u51fa\u9519{}", (Object)e.getMessage());
            }
        }
        return entitys;
    }

    private ExecutorContext getExecutorContext(TableContext context) {
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runTimeViewController, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, context.getFormSchemeKey());
        executorContext.setEnv((IFmlExecEnvironment)environment);
        executorContext.setJQReportModel(true);
        return executorContext;
    }

    private List<RegionData> getRegions(String formKey) {
        ArrayList<RegionData> regions = new ArrayList<RegionData>();
        List allRegionDefines = this.runTimeAuthViewController.getAllRegionsInForm(formKey);
        for (DataRegionDefine dataRegionDefine : allRegionDefines) {
            RegionData regionData = new RegionData();
            regionData.initialize(dataRegionDefine);
            regions.add(regionData);
        }
        return regions;
    }
}

