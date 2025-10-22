/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.definition.common.Consts$EntityField
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.np.definition.impl.internal.FieldDefineImpl
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.fmdm.FMDMAttributeDTO
 *  com.jiuqi.nr.fmdm.FMDMDataDTO
 *  com.jiuqi.nr.fmdm.IFMDMAttribute
 *  com.jiuqi.nr.fmdm.IFMDMAttributeService
 *  com.jiuqi.nr.fmdm.IFMDMData
 *  com.jiuqi.nr.fmdm.IFMDMDataService
 *  com.jiuqi.nr.fmdm.exception.FMDMQueryException
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.nr.io.fmdm.dataset.impl;

import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.definition.common.Consts;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.np.definition.impl.internal.FieldDefineImpl;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.fmdm.FMDMAttributeDTO;
import com.jiuqi.nr.fmdm.FMDMDataDTO;
import com.jiuqi.nr.fmdm.IFMDMAttribute;
import com.jiuqi.nr.fmdm.IFMDMAttributeService;
import com.jiuqi.nr.fmdm.IFMDMData;
import com.jiuqi.nr.fmdm.IFMDMDataService;
import com.jiuqi.nr.fmdm.exception.FMDMQueryException;
import com.jiuqi.nr.io.dataset.IRegionDataSetReader;
import com.jiuqi.nr.io.params.base.RegionData;
import com.jiuqi.nr.io.params.base.TableContext;
import com.jiuqi.nr.io.params.output.ExportFieldDefine;
import com.jiuqi.nr.io.service.IoEntityService;
import com.jiuqi.nr.io.service.impl.ParameterService;
import com.jiuqi.nr.io.tz.dataset.AbstractRegionDataSet;
import com.jiuqi.nr.io.util.ParamUtil;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FMDMRegionDataSet
extends AbstractRegionDataSet {
    private static final Logger logger = LoggerFactory.getLogger(FMDMRegionDataSet.class);
    private IFMDMDataService fMDMDataService;
    private IFMDMAttributeService fMDMAttributeService;
    private TableContext tableContext;
    private RegionData regionData;
    private IRunTimeViewController runTimeViewController;
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    private DataModelService dataModelService;
    private List<FieldDefine> bizKeyFieldDef = new ArrayList<FieldDefine>();
    private List<IFMDMAttribute> columnModelDefines = new ArrayList<IFMDMAttribute>();
    private List<IFMDMData> fmdmDatas = null;
    private Map<String, IEntityTable> enumDataValues = new HashMap<String, IEntityTable>();
    private IoEntityService ioEntityService;
    private IEntityViewRunTimeController entityViewRunTimeController;
    private ExecutorContext executorContext;

    public FMDMRegionDataSet(TableContext context, RegionData regionData) {
        this.tableContext = context.clone();
        this.regionData = regionData;
        ParameterService parameterService = ParamUtil.getParameterService();
        this.runTimeViewController = parameterService.getRunTimeViewController();
        this.dataDefinitionRuntimeController = parameterService.getDataDefinitionRuntimeController();
        this.dataModelService = (DataModelService)BeanUtil.getBean(DataModelService.class);
        this.fMDMAttributeService = (IFMDMAttributeService)BeanUtil.getBean(IFMDMAttributeService.class);
        this.fMDMDataService = (IFMDMDataService)BeanUtil.getBean(IFMDMDataService.class);
        this.ioEntityService = (IoEntityService)BeanUtil.getBean(IoEntityService.class);
        this.entityViewRunTimeController = (IEntityViewRunTimeController)BeanUtil.getBean(IEntityViewRunTimeController.class);
        FMDMAttributeDTO fmdmAttributeDTO = new FMDMAttributeDTO();
        fmdmAttributeDTO.setFormSchemeKey(context.getFormSchemeKey());
        this.columnModelDefines = this.fMDMAttributeService.listShowAttribute(fmdmAttributeDTO);
        IFMDMAttribute fmdmBizField = this.fMDMAttributeService.getFMDMBizField(fmdmAttributeDTO);
        ColumnModelDefine columnModelDefine = this.dataModelService.getColumnModelDefineByCode(fmdmBizField.getTableID(), fmdmBizField.getCode());
        if (columnModelDefine != null) {
            TableModelDefine tableModelDefine = this.dataModelService.getTableModelDefineById(columnModelDefine.getTableID());
            FieldDefineImpl fieldDefine = new FieldDefineImpl(columnModelDefine, tableModelDefine.getName());
            this.bizKeyFieldDef.add((FieldDefine)fieldDefine);
        }
    }

    @Override
    public RegionData getRegionData() {
        return this.regionData;
    }

    @Override
    public boolean isFloatRegion() {
        return false;
    }

    @Override
    public List<ExportFieldDefine> getFieldDataList() {
        TableDefine td = null;
        String tableName = "";
        ArrayList<ExportFieldDefine> fieldDefines = new ArrayList<ExportFieldDefine>();
        if (null != this.columnModelDefines && !this.columnModelDefines.isEmpty()) {
            try {
                td = this.dataDefinitionRuntimeController.queryTableDefine(this.columnModelDefines.get(0).getTableID());
            }
            catch (Exception e) {
                logger.info("\u67e5\u5b58\u50a8\u8868\u51fa\u9519{}", (Object)e.getMessage(), (Object)e);
            }
            for (ColumnModelDefine columnModelDefine : this.columnModelDefines) {
                ExportFieldDefine fd = new ExportFieldDefine(columnModelDefine.getTitle(), tableName + "." + columnModelDefine.getCode(), columnModelDefine.getPrecision(), columnModelDefine.getColumnType().getValue());
                fd.setValueType(columnModelDefine.getColumnType().getValue());
                fd.setTableCode(td != null ? td.getCode() : null);
                fieldDefines.add(fd);
            }
        }
        return fieldDefines;
    }

    private void queryFMDMDatas() {
        FMDMDataDTO fmdmDataDTO = new FMDMDataDTO();
        fmdmDataDTO.setDimensionValueSet(this.tableContext.getDimensionSet());
        fmdmDataDTO.setFormSchemeKey(this.tableContext.getFormSchemeKey());
        fmdmDataDTO.setDimensionValueSet(this.tableContext.getDimensionSet());
        fmdmDataDTO.setDataMasking(this.tableContext.isDataMasking());
        try {
            this.fmdmDatas = this.fMDMDataService.list(fmdmDataDTO);
        }
        catch (FMDMQueryException e) {
            logger.error("\u5c01\u9762\u4ee3\u7801\u6570\u636e\u67e5\u8be2\u51fa\u9519{}", (Object)e.getMessage(), (Object)e);
        }
    }

    @Override
    public List<FieldDefine> getBizFieldDefList() {
        return this.bizKeyFieldDef;
    }

    @Override
    public FieldDefine getUnitFieldDefine() throws Exception {
        for (FieldDefine fieldDefine : this.bizKeyFieldDef) {
            if (!fieldDefine.getCode().equals("MDCODE")) continue;
            return fieldDefine;
        }
        return null;
    }

    @Override
    public boolean hasNext() {
        if (this.fmdmDatas == null) {
            this.queryFMDMDatas();
        }
        return !this.fmdmDatas.isEmpty();
    }

    @Override
    public ArrayList<Object> next() {
        ArrayList<Object> row = new ArrayList<Object>();
        for (IFMDMAttribute columnModelDefine : this.columnModelDefines) {
            row.add(this.dataTransferOri(columnModelDefine, this.fmdmDatas.get(0).getAsObject(columnModelDefine.getCode())));
        }
        this.fmdmDatas.remove(0);
        return row;
    }

    @Override
    public void queryToDataRowReader(IRegionDataSetReader regionDataSetReader) {
        regionDataSetReader.start(new ArrayList<FieldDefine>());
        while (this.hasNext()) {
            Object rowData = this.next();
            regionDataSetReader.readRowData((List<Object>)rowData);
        }
        regionDataSetReader.finish();
    }

    private Object dataTransferOri(IFMDMAttribute columnModelDefine, Object data) {
        if (null == data) {
            return "";
        }
        if (columnModelDefine.getReferColumnID() != null) {
            IEntityTable iEntityTable;
            EntityViewDefine queryEntityView;
            if (!this.enumDataValues.containsKey(columnModelDefine.getReferColumnID()) && null != (queryEntityView = this.entityViewRunTimeController.buildEntityView(columnModelDefine.getReferEntityId()))) {
                try {
                    ExecutorContext context = this.getExecutorContext(this.tableContext);
                    IEntityTable entityTables = this.ioEntityService.getIEntityTable(queryEntityView, this.tableContext, context);
                    this.enumDataValues.put(columnModelDefine.getReferColumnID(), entityTables);
                }
                catch (Exception e) {
                    logger.debug("\u67e5\u8be2\u6307\u6807\u6570\u636e\u8fde\u63a5\u5b9e\u4f53\u7ed3\u679c\u51fa\u9519{}", (Object)e.getMessage());
                }
            }
            if (null != (iEntityTable = this.enumDataValues.get(columnModelDefine.getReferColumnID()))) {
                String referCode = "";
                try {
                    ColumnModelDefine colum = this.dataModelService.getColumnModelDefineByID(columnModelDefine.getReferColumnID());
                    referCode = colum.getCode();
                }
                catch (Exception e) {
                    logger.debug("\u6ca1\u6709\u627e\u5173\u8054\u7684\u6307\u6807{}", (Object)e.getMessage());
                }
                String string = data.toString();
                if (string.contains("|")) {
                    return string.split("\\|")[0];
                }
                if (string.contains(";")) {
                    List allRows = iEntityTable.getAllRows();
                    StringBuilder multiTitle = new StringBuilder();
                    for (IEntityRow row : allRows) {
                        if (row.getTitle().equals(string)) {
                            if (Consts.EntityField.ENTITY_FIELD_KEY.equals((Object)referCode)) {
                                return null != row.getEntityKeyData() ? row.getEntityKeyData() : row.getCode();
                            }
                            return row.getCode();
                        }
                        if (!string.contains(";") || !string.contains(row.getTitle())) continue;
                        if (multiTitle.length() > 0) {
                            multiTitle.append(";").append(row.getEntityKeyData());
                            continue;
                        }
                        multiTitle.append(row.getEntityKeyData());
                    }
                    if (!multiTitle.toString().isEmpty()) {
                        return multiTitle.toString();
                    }
                } else {
                    List findAllByCode = iEntityTable.findAllByCode(string);
                    if (findAllByCode != null && !findAllByCode.isEmpty()) {
                        return ((IEntityRow)findAllByCode.get(0)).getTitle();
                    }
                }
            }
            return data;
        }
        return data;
    }

    private ExecutorContext getExecutorContext(TableContext context) {
        if (this.executorContext == null) {
            ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
            ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runTimeViewController, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, context.getFormSchemeKey());
            executorContext.setEnv((IFmlExecEnvironment)environment);
            executorContext.setJQReportModel(true);
            this.executorContext = executorContext;
        }
        return this.executorContext;
    }
}

