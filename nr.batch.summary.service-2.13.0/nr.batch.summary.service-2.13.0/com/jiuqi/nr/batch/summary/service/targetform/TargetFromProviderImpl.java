/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.batch.summary.storage.entity.SchemeRangeForm
 *  com.jiuqi.nr.batch.summary.storage.entity.SingleDim
 *  com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.type.DataTableGatherType
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.batch.summary.service.targetform;

import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.batch.summary.service.targetform.IDataBaseTableProvider;
import com.jiuqi.nr.batch.summary.service.targetform.OriTableModelImpl;
import com.jiuqi.nr.batch.summary.service.targetform.OriTableModelInfo;
import com.jiuqi.nr.batch.summary.service.targetform.SumTableModelImpl;
import com.jiuqi.nr.batch.summary.service.targetform.SumTableModelInfo;
import com.jiuqi.nr.batch.summary.service.targetform.TargetFromProvider;
import com.jiuqi.nr.batch.summary.service.targetform.TargetFromProviderFactoryImpl;
import com.jiuqi.nr.batch.summary.storage.entity.SchemeRangeForm;
import com.jiuqi.nr.batch.summary.storage.entity.SingleDim;
import com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.type.DataTableGatherType;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;

public class TargetFromProviderImpl
implements TargetFromProvider {
    private TaskDefine taskDefine;
    private DataScheme dataScheme;
    private SummaryScheme summaryScheme;
    private IDataBaseTableProvider iDataBaseTableProvider;
    protected TargetFromProviderFactoryImpl wrapper;

    public TargetFromProviderImpl(TargetFromProviderFactoryImpl wrapper, SummaryScheme summaryScheme, IDataBaseTableProvider iDataBaseTableProvider) {
        this.wrapper = wrapper;
        this.summaryScheme = summaryScheme;
        this.iDataBaseTableProvider = iDataBaseTableProvider;
        this.taskDefine = wrapper.runTimeViewController.queryTaskDefine(summaryScheme.getTask());
        this.dataScheme = wrapper.runtimeDataSchemeService.getDataScheme(this.taskDefine.getDataScheme());
    }

    @Override
    public List<FormDefine> getRangeForms(String period) {
        List defRangeForms = null;
        SchemeRangeForm rangeFormConf = this.summaryScheme.getRangeForm();
        switch (rangeFormConf.getRangeFormType()) {
            case ALL: {
                defRangeForms = this.wrapper.runTimeViewController.queryAllFormDefinesByTask(this.taskDefine.getKey());
                break;
            }
            case CUSTOM: {
                defRangeForms = this.wrapper.runTimeViewController.queryFormsById(rangeFormConf.getFormList());
            }
        }
        FormSchemeDefine formSchemeDefine = this.wrapper.queryFormSchemeDefine(this.taskDefine.getKey(), period);
        List formDefines = this.wrapper.runTimeViewController.queryAllFormDefinesByFormScheme(formSchemeDefine.getKey());
        List formKeys = formDefines.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
        return defRangeForms.stream().filter(formDefine -> formKeys.contains(formDefine.getKey())).collect(Collectors.toList());
    }

    @Override
    public List<OriTableModelInfo> getRangeFormTables(List<FormDefine> rangeForms, String period) {
        Map<String, Object> qjDimJustValueMap = this.whenQJDimensionOnlyOneValue(period);
        ArrayList<OriTableModelInfo> oriTableModels = new ArrayList<OriTableModelInfo>();
        HashSet<String> checkList = new HashSet<String>();
        for (FormDefine formDefine : rangeForms) {
            List allRegionsInForm = this.wrapper.runTimeViewController.getAllRegionsInForm(formDefine.getKey());
            for (DataRegionDefine dataRegionDefine : allRegionsInForm) {
                List allLinksInRegion = this.wrapper.runTimeViewController.getAllLinksInRegion(dataRegionDefine.getKey());
                if ((allLinksInRegion = allLinksInRegion.stream().filter(l -> StringUtils.isNotEmpty((String)l.getLinkExpression())).collect(Collectors.toList())).isEmpty()) continue;
                List deployInfo = this.wrapper.runtimeDataSchemeService.getDeployInfoByDataFieldKeys((String[])allLinksInRegion.stream().map(DataLinkDefine::getLinkExpression).toArray(String[]::new));
                for (DataFieldDeployInfo dataFieldDeployInfo : deployInfo) {
                    TableModelDefine originTableModelDefine = this.wrapper.dataModelService.getTableModelDefineById(dataFieldDeployInfo.getTableModelKey());
                    TableModelDefine tableModelDefine = this.iDataBaseTableProvider.getOriginTableModelDefine(dataFieldDeployInfo.getTableModelKey(), this.summaryScheme.getTask(), period);
                    if (checkList.contains(tableModelDefine.getName()) || !this.isSummaryTableModel(dataRegionDefine, originTableModelDefine)) continue;
                    OriTableModelImpl oriTableModel = new OriTableModelImpl(originTableModelDefine, tableModelDefine, qjDimJustValueMap, this.dataScheme, this.wrapper.dataModelService, this.wrapper.runtimeDataSchemeService, this.wrapper.subDatabaseTableNamesProvider);
                    oriTableModel.setTableName(tableModelDefine.getName());
                    oriTableModel.setSimpleTable(dataRegionDefine.getRegionKind() == DataRegionKind.DATA_REGION_SIMPLE);
                    oriTableModels.add(oriTableModel);
                    checkList.add(tableModelDefine.getName());
                }
            }
        }
        return oriTableModels;
    }

    @Override
    public SumTableModelInfo getSumTableModel(OriTableModelInfo oriTableModel, String period) {
        TableModelDefine sumTableModel = this.iDataBaseTableProvider.getSumTableModelDefine(oriTableModel, this.summaryScheme, period);
        return new SumTableModelImpl(sumTableModel, this.wrapper.dataModelService, oriTableModel);
    }

    private boolean isSummaryTableModel(DataRegionDefine dataRegionDefine, TableModelDefine tableModelDefine) {
        String dataTableKey;
        if (dataRegionDefine.getRegionKind() != DataRegionKind.DATA_REGION_SIMPLE && StringUtils.isNotEmpty((String)(dataTableKey = this.wrapper.dataFieldDeployInfoService.getDataTableByTableModel(tableModelDefine.getID())))) {
            DataTable dataTable = this.wrapper.runtimeDataSchemeService.getDataTable(dataTableKey);
            return dataTable != null && !dataTable.getDataTableGatherType().equals((Object)DataTableGatherType.LIST);
        }
        return true;
    }

    private Map<String, Object> whenQJDimensionOnlyOneValue(String period) {
        TableModelDefine tableModel;
        FormSchemeDefine formSchemeDefine = this.wrapper.queryFormSchemeDefine(this.summaryScheme.getTask(), period);
        String contextEntityId = DsContextHolder.getDsContext().getContextEntityId();
        if (StringUtils.isEmpty((String)contextEntityId)) {
            contextEntityId = formSchemeDefine.getDw();
        }
        IEntityModel dwEntityModel = this.wrapper.entityMetaService.getEntityModel(contextEntityId);
        TaskDefine taskDefine = this.wrapper.runTimeViewController.queryTaskDefine(this.summaryScheme.getTask());
        List dimensions = this.wrapper.runtimeDataSchemeService.getDataSchemeDimension(taskDefine.getDataScheme());
        HashSet<String> singleDimKeys = new HashSet<String>();
        for (DataDimension dimension : dimensions) {
            String dimAttribute;
            IEntityAttribute attribute;
            if (DimensionType.DIMENSION != dimension.getDimensionType() || (attribute = dwEntityModel.getAttribute(dimAttribute = dimension.getDimAttribute())) == null || attribute.isMultival()) continue;
            singleDimKeys.add(dimension.getDimKey());
        }
        List singleDims = this.summaryScheme.getSingleDims();
        HashMap<String, Object> qjDimJustValueMap = new HashMap<String, Object>();
        if (!CollectionUtils.isEmpty(singleDims)) {
            for (SingleDim singleDim : singleDims) {
                tableModel = this.wrapper.entityMetaService.getTableModel(singleDim.getEntityId());
                qjDimJustValueMap.put(tableModel.getName(), singleDim.getValue());
                singleDimKeys.remove(singleDim.getEntityId());
            }
        }
        for (String singleDimKey : singleDimKeys) {
            tableModel = this.wrapper.entityMetaService.getTableModel(singleDimKey);
            qjDimJustValueMap.put(tableModel.getName(), tableModel.getName());
        }
        return qjDimJustValueMap;
    }
}

