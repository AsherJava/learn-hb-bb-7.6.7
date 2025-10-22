/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 */
package com.jiuqi.nr.datacrud.impl.crud.inner;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.datacrud.api.IDwClearChangeDataService;
import com.jiuqi.nr.datacrud.spi.IDwClearChangeDataListener;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class DwClearChangeDataServiceImpl
implements IDwClearChangeDataService {
    private final Logger logger = LoggerFactory.getLogger(DwClearChangeDataServiceImpl.class);
    @Autowired
    private IEntityViewRunTimeController viewAdapter;
    @Autowired
    private IEntityDataService dataService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IDataDefinitionRuntimeController tbRtCtl;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired(required=false)
    private List<IDwClearChangeDataListener> iDwClearChangeDataListener;

    @Override
    public void doChangeData(String entityId, String entityKeyData, String startPeriod, String endPeriod) {
        if (this.logger.isDebugEnabled()) {
            this.logger.debug("\u6e05\u7406\u4efb\u52a1\u63a5\u6536\u53c2\u6570 entityId {},entityKeyData {},startPeriod {},endPeriod {}", entityId, entityKeyData, startPeriod, endPeriod);
        }
        if (!StringUtils.hasLength(entityId)) {
            return;
        }
        if (!StringUtils.hasLength(entityKeyData)) {
            return;
        }
        if (!StringUtils.hasLength(startPeriod)) {
            startPeriod = null;
        }
        if (!StringUtils.hasLength(endPeriod)) {
            endPeriod = null;
        }
        this.doChangeData(entityId, Collections.singletonList(entityKeyData), startPeriod, endPeriod);
    }

    @Override
    public void doChangeData(String entityId, String entityKeyData) {
        if (this.logger.isDebugEnabled()) {
            this.logger.debug("\u6e05\u7406\u4efb\u52a1\u63a5\u6536\u53c2\u6570 entityId {},entityKeyData {}", (Object)entityId, (Object)entityKeyData);
        }
        if (!StringUtils.hasLength(entityId)) {
            return;
        }
        if (!StringUtils.hasLength(entityKeyData)) {
            return;
        }
        this.doChangeData(entityId, Collections.singletonList(entityKeyData), null, null);
    }

    @Override
    public void doChangeData(String entityId, List<String> entityKeyData) {
        if (this.logger.isDebugEnabled()) {
            this.logger.debug("\u6e05\u7406\u4efb\u52a1\u63a5\u6536\u53c2\u6570 entityId {},entityKeyData {}", (Object)entityId, (Object)entityKeyData);
        }
        if (!StringUtils.hasLength(entityId)) {
            return;
        }
        this.doChangeData(entityId, entityKeyData, null, null);
    }

    @Override
    public void doChangeData(String entityId, List<String> entityKeyData, String startPeriod, String endPeriod) {
        if (this.logger.isDebugEnabled()) {
            this.logger.debug("\u6e05\u7406\u4efb\u52a1\u63a5\u6536\u53c2\u6570 entityId {},entityKeyData {},startPeriod {},endPeriod {}", entityId, entityKeyData, startPeriod, endPeriod);
        }
        if (!StringUtils.hasLength(entityId)) {
            return;
        }
        if (CollectionUtils.isEmpty(entityKeyData)) {
            return;
        }
        if (!StringUtils.hasLength(startPeriod)) {
            startPeriod = null;
        }
        if (!StringUtils.hasLength(endPeriod)) {
            endPeriod = null;
        }
        if (CollectionUtils.isEmpty(this.iDwClearChangeDataListener)) {
            return;
        }
        this.logger.info("\u627e\u5230{}\u4e2a\u76d1\u542c,\u5f00\u59cb\u6267\u884c\u5355\u4f4d\u5220\u9664\u540e\u6570\u636e\u6e05\u7406\u4efb\u52a1", (Object)this.iDwClearChangeDataListener.size());
        List allDataScheme = this.runtimeDataSchemeService.getAllDataScheme();
        this.logger.info("\u7cfb\u7edf\u4e2d\u4e00\u5171{}\u4e2a\u6570\u636e\u65b9\u6848", (Object)allDataScheme.size());
        for (DataScheme dataScheme : allDataScheme) {
            try {
                this.doClearDataScheme(dataScheme, entityId, entityKeyData, startPeriod, endPeriod);
            }
            catch (Exception e) {
                this.logger.warn("\u6570\u636e\u65b9\u6848{}\u6e05\u9664\u9047\u5230\u95ee\u9898,\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458", (Object)dataScheme.getTitle(), (Object)e);
            }
        }
        this.logger.info("\u6e05\u7406\u4efb\u52a1\u6267\u884c\u7ed3\u675f");
    }

    private void doClearDataScheme(DataScheme dataScheme, String entityId, List<String> entityKeyData, String startPeriod, String endPeriod) throws Exception {
        LinkedHashSet<String> allEntityIds = new LinkedHashSet<String>();
        boolean hasScope = false;
        String dataTime = null;
        String unitEntityId = null;
        List dataSchemeDimension = this.runtimeDataSchemeService.getDataSchemeDimension(dataScheme.getKey());
        for (DataDimension dataDimension : dataSchemeDimension) {
            DimensionType dimensionType = dataDimension.getDimensionType();
            if (dimensionType == DimensionType.UNIT || dimensionType == DimensionType.UNIT_SCOPE) {
                String dimKey = dataDimension.getDimKey();
                allEntityIds.add(dimKey);
                if (!hasScope) {
                    boolean bl = hasScope = dimensionType == DimensionType.UNIT_SCOPE;
                }
                if (dimensionType != DimensionType.UNIT) continue;
                unitEntityId = dimKey;
                continue;
            }
            if (dimensionType != DimensionType.PERIOD) continue;
            dataTime = dataDimension.getDimKey();
        }
        if (!allEntityIds.contains(entityId)) {
            this.logger.info("\u6570\u636e\u65b9\u6848{}\u672a\u7ed1\u5b9a\u5b9e\u4f53{}\uff0c\u8df3\u8fc7\u6e05\u9664", (Object)dataScheme.getTitle(), (Object)entityId);
            return;
        }
        allEntityIds.remove(entityId);
        if (hasScope) {
            allEntityIds.remove(unitEntityId);
        }
        this.logger.info("\u6570\u636e\u65b9\u6848{}\u7ed1\u5b9a\u5b9e\u4f53{}\uff0c\u5f00\u59cb\u6e05\u9664", (Object)dataScheme.getTitle(), (Object)entityId);
        LinkedHashSet<String> entityKeyDataSet = new LinkedHashSet<String>(entityKeyData);
        if (!allEntityIds.isEmpty()) {
            for (String entityScopeId : allEntityIds) {
                IEntityDefine iEntityDefine = this.entityMetaService.queryEntity(entityScopeId);
                String dimensionName = iEntityDefine.getDimensionName();
                DimensionValueSet dimensionValueSet = new DimensionValueSet();
                dimensionValueSet.setValue(dimensionName, entityKeyData);
                IEntityTable iEntityTable = this.getEntityTable(entityScopeId, dataTime, dimensionValueSet);
                for (String entityKey : entityKeyData) {
                    IEntityRow iEntityRow = iEntityTable.quickFindByEntityKey(entityKey);
                    if (iEntityRow == null) continue;
                    entityKeyDataSet.remove(entityKey);
                    this.logger.info("\u5355\u4f4d{}\u5b58\u5728\u4e8e{}\uff0c\u8df3\u8fc7\u6e05\u9664\u8be5\u5355\u4f4d\u6570\u636e", (Object)iEntityRow.getTitle(), (Object)entityScopeId);
                }
            }
        }
        if (entityKeyDataSet.isEmpty()) {
            this.logger.info("\u6ca1\u6709\u5355\u4f4d\u9700\u8981\u6e05\u9664\u6570\u636e\uff0c\u6e05\u9664\u4efb\u52a1\u6b63\u5e38\u7ed3\u675f");
            return;
        }
        for (IDwClearChangeDataListener dwClearChangeDataListener : this.iDwClearChangeDataListener) {
            this.logger.info("{}\u76d1\u542c\u5f00\u59cb\u6267\u884c", (Object)dwClearChangeDataListener.name());
            ArrayList<String> entityKeys = new ArrayList<String>(entityKeyDataSet);
            try {
                if (entityKeys.size() == 1) {
                    dwClearChangeDataListener.dataClear(dataScheme.getKey(), (String)entityKeys.get(0), startPeriod, endPeriod);
                } else {
                    dwClearChangeDataListener.dataClear(dataScheme.getKey(), entityKeys, startPeriod, endPeriod);
                }
                this.logger.info("{}\u76d1\u542c\u6267\u884c\u7ed3\u675f", (Object)dwClearChangeDataListener.name());
            }
            catch (Exception e) {
                this.logger.warn("{}\u76d1\u542c\u6e05\u9664\u6570\u636e\u5931\u8d25\uff0c\u8df3\u8fc7\u5f53\u524d\u76d1\u542c\uff0c\u7ee7\u7eed\u4e0b\u4e00\u4e2a\u76d1\u542c\u6267\u884c\u6e05\u9664\u4efb\u52a1", (Object)dwClearChangeDataListener.name(), (Object)e);
            }
        }
        this.logger.info("\u6570\u636e\u65b9\u6848{}\u6e05\u9664\u7ed3\u675f", (Object)dataScheme.getTitle());
    }

    private IEntityTable getEntityTable(String entityId, String dataTime, DimensionValueSet dimensionValueSet) throws Exception {
        EntityViewDefine entityView = this.viewAdapter.buildEntityView(entityId);
        IEntityQuery query = this.dataService.newEntityQuery();
        query.setEntityView(entityView);
        query.setMasterKeys(dimensionValueSet);
        ExecutorContext executorContext = new ExecutorContext(this.tbRtCtl);
        executorContext.setPeriodView(dataTime);
        return query.executeReader((IContext)executorContext);
    }
}

