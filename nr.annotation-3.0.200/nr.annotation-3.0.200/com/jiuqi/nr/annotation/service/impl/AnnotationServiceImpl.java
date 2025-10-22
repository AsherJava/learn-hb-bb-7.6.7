/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.service.SystemUserService
 *  com.jiuqi.np.user.service.UserService
 *  com.jiuqi.nr.common.exception.ExceptionCodeCost
 *  com.jiuqi.nr.common.exception.NotFoundTableDefineException
 *  com.jiuqi.nr.common.params.PagerInfo
 *  com.jiuqi.nr.common.util.DataEngineAdapter
 *  com.jiuqi.nr.common.util.DimensionChanger
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.log.DataServiceLoggerFactory
 *  com.jiuqi.nr.dataservice.core.log.LogDimensionCollection
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.controller.ITaskOptionController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccess
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider
 *  com.jiuqi.nvwa.dataengine.INvwaDataRow
 *  com.jiuqi.nvwa.dataengine.INvwaDataUpdator
 *  com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess
 *  com.jiuqi.nvwa.dataengine.INvwaUpdatableDataSet
 *  com.jiuqi.nvwa.dataengine.common.DataAccessContext
 *  com.jiuqi.nvwa.dataengine.intf.ISqlJoinProvider
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryModel
 *  com.jiuqi.nvwa.dataengine.model.OrderByItem
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.annotation.service.impl;

import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.service.SystemUserService;
import com.jiuqi.np.user.service.UserService;
import com.jiuqi.nr.annotation.constant.PromptConsts;
import com.jiuqi.nr.annotation.exception.ErrorParamException;
import com.jiuqi.nr.annotation.exception.NotFoundFormSchemeException;
import com.jiuqi.nr.annotation.input.CellAnnotationQueryInfo;
import com.jiuqi.nr.annotation.input.ExpAnnotationParam;
import com.jiuqi.nr.annotation.input.FormAnnotationBatchSaveInfo;
import com.jiuqi.nr.annotation.input.FormAnnotationDeleteInfo;
import com.jiuqi.nr.annotation.input.FormAnnotationQueryInfo;
import com.jiuqi.nr.annotation.input.FormCellAnnotationQueryInfo;
import com.jiuqi.nr.annotation.input.SaveFormAnnotationCommentInfo;
import com.jiuqi.nr.annotation.input.SaveFormAnnotationInfo;
import com.jiuqi.nr.annotation.input.UpdateFormAnnotationCommentInfo;
import com.jiuqi.nr.annotation.input.UpdateFormAnnotationInfo;
import com.jiuqi.nr.annotation.message.AnnotationInfo;
import com.jiuqi.nr.annotation.message.CellAnnotationInfo;
import com.jiuqi.nr.annotation.message.CellAnnotationRelation;
import com.jiuqi.nr.annotation.message.CellLocation;
import com.jiuqi.nr.annotation.message.LinkData;
import com.jiuqi.nr.annotation.message.RegionAnnotationResult;
import com.jiuqi.nr.annotation.message.RegionData;
import com.jiuqi.nr.annotation.message.RowInfo;
import com.jiuqi.nr.annotation.output.CellAnnotationComment;
import com.jiuqi.nr.annotation.output.CellAnnotationContent;
import com.jiuqi.nr.annotation.output.CellAnnotationResult;
import com.jiuqi.nr.annotation.output.ExpAnnotationComment;
import com.jiuqi.nr.annotation.output.ExpAnnotationRel;
import com.jiuqi.nr.annotation.output.ExpAnnotationResult;
import com.jiuqi.nr.annotation.output.FormAnnotationResult;
import com.jiuqi.nr.annotation.service.IAnnotationService;
import com.jiuqi.nr.annotation.service.IAnnotationTypeService;
import com.jiuqi.nr.annotation.util.AnnotationDataFieldTypeJoinProviderWithNV;
import com.jiuqi.nr.annotation.util.FormAnnotationDataFieldCommentJoinProviderWithNV;
import com.jiuqi.nr.annotation.util.FormAnnotationDataFieldJoinProviderWithNV;
import com.jiuqi.nr.annotation.util.LogHellperUtil;
import com.jiuqi.nr.common.exception.ExceptionCodeCost;
import com.jiuqi.nr.common.exception.NotFoundTableDefineException;
import com.jiuqi.nr.common.params.PagerInfo;
import com.jiuqi.nr.common.util.DataEngineAdapter;
import com.jiuqi.nr.common.util.DimensionChanger;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.log.DataServiceLoggerFactory;
import com.jiuqi.nr.dataservice.core.log.LogDimensionCollection;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nvwa.dataengine.INvwaDataAccess;
import com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider;
import com.jiuqi.nvwa.dataengine.INvwaDataRow;
import com.jiuqi.nvwa.dataengine.INvwaDataUpdator;
import com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess;
import com.jiuqi.nvwa.dataengine.INvwaUpdatableDataSet;
import com.jiuqi.nvwa.dataengine.common.DataAccessContext;
import com.jiuqi.nvwa.dataengine.intf.ISqlJoinProvider;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryModel;
import com.jiuqi.nvwa.dataengine.model.OrderByItem;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AnnotationServiceImpl
implements IAnnotationService {
    private static final Logger logger = LoggerFactory.getLogger(AnnotationServiceImpl.class);
    @Autowired
    private IRunTimeViewController runtimeView;
    @Autowired
    private ITaskOptionController taskOptionController;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private DataEngineAdapter dataEngineAdapter;
    @Autowired
    private INvwaDataAccessProvider iNvwaDataAccessProvider;
    @Autowired
    private UserService<User> userService;
    @Autowired
    private SystemUserService systemUserService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IPeriodEntityAdapter periodAdapter;
    @Autowired
    private DataServiceLoggerFactory dataServiceLoggerFactory;
    @Autowired
    private IAnnotationTypeService annotationTypeService;
    private static final String MOUDLE = "\u6279\u6ce8\u670d\u52a1";

    @Override
    @Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
    public FormAnnotationResult queryFormAnnotation(FormCellAnnotationQueryInfo formCellAnnotationQueryInfo) {
        if (StringUtils.isEmpty((String)formCellAnnotationQueryInfo.getFormSchemeKey())) {
            return null;
        }
        FormAnnotationResult result = new FormAnnotationResult();
        FormSchemeDefine formScheme = null;
        try {
            formScheme = this.runtimeView.getFormScheme(formCellAnnotationQueryInfo.getFormSchemeKey());
        }
        catch (Exception e) {
            throw new NotFoundFormSchemeException(ExceptionCodeCost.NOTFOUND_FORMSHCEME_BYKEY, new String[]{formCellAnnotationQueryInfo.getFormSchemeKey().toString()});
        }
        String isOpen = this.taskOptionController.getValue(formScheme.getTaskKey(), "DATA_ANNOCATION");
        if ("0".equals(isOpen)) {
            return result;
        }
        DimensionCombination dimensionCombination = formCellAnnotationQueryInfo.getDimensionCombination();
        DimensionValueSet dimensionValueSet = dimensionCombination.toDimensionValueSet();
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        NvwaQueryModel queryModel = new NvwaQueryModel();
        TableModelDefine formAnnotationTable = this.dataModelService.getTableModelDefineByName("SYS_FMCEAN_" + formScheme.getFormSchemeCode());
        TableModelDefine datalinkFiledTable = this.dataModelService.getTableModelDefineByName("SYS_FMCEANDF_" + formScheme.getFormSchemeCode());
        if (null == formAnnotationTable || null == datalinkFiledTable) {
            throw new NotFoundTableDefineException(new String[]{"\u542f\u7528\u6279\u6ce8\uff0c\u8bf7\u91cd\u65b0\u53d1\u5e03\u4efb\u52a1\uff01"});
        }
        List annotationFields = this.dataModelService.getColumnModelDefinesByTable(formAnnotationTable.getID());
        List datalinkFieldFields = this.dataModelService.getColumnModelDefinesByTable(datalinkFiledTable.getID());
        for (ColumnModelDefine columnModelDefine : annotationFields) {
            queryModel.getColumns().add(new NvwaQueryColumn(columnModelDefine));
            if (!columnModelDefine.getCode().equals("MDCODE") && !columnModelDefine.getCode().equals("PERIOD")) continue;
            DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger("SYS_FMCEAN_" + formScheme.getFormSchemeCode());
            String dimensionName = dimensionChanger.getDimensionName(columnModelDefine);
            queryModel.getColumnFilters().put(columnModelDefine, dimensionValueSet.getValue(dimensionName));
        }
        for (ColumnModelDefine columnModelDefine : datalinkFieldFields) {
            queryModel.getColumns().add(new NvwaQueryColumn(columnModelDefine));
        }
        StringBuffer rowFilter = new StringBuffer();
        rowFilter.append(" ( ");
        String formKey = formCellAnnotationQueryInfo.getFormKey();
        if (StringUtils.isEmpty((String)formKey)) {
            throw new RuntimeException(" formKey must is not null !!");
        }
        rowFilter.append(" ( ").append(datalinkFiledTable.getName() + "[" + "FORM_KEY" + "] in { ");
        rowFilter.append("'" + formKey + "'");
        rowFilter.append(" } ").append(" ) ");
        if (!StringUtils.isEmpty((String)formCellAnnotationQueryInfo.getRegionKey())) {
            rowFilter.append(" or ");
            rowFilter.append(" ( ").append(datalinkFiledTable.getName() + "[" + "REGION_KEY" + "] in { ");
            rowFilter.append("'" + formCellAnnotationQueryInfo.getRegionKey() + "'");
            rowFilter.append(" } ").append(" ) ");
        }
        rowFilter.append(" ) ");
        FormAnnotationDataFieldJoinProviderWithNV provider = new FormAnnotationDataFieldJoinProviderWithNV(formScheme.getFormSchemeCode());
        context.setSqlJoinProvider((ISqlJoinProvider)provider);
        queryModel.setMainTableName(formAnnotationTable.getName());
        queryModel.setFilter(rowFilter.toString());
        INvwaDataAccess readOnlyDataAccess = this.iNvwaDataAccessProvider.createReadOnlyDataAccess(queryModel);
        MemoryDataSet dataTable = null;
        try {
            dataTable = readOnlyDataAccess.executeQuery(context);
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        result.setFormKey(formCellAnnotationQueryInfo.getFormKey());
        ArrayList<String> haveAdds = new ArrayList<String>();
        Map<String, RegionAnnotationResult> regions = result.getRegions();
        int totalCount = dataTable.size();
        if (totalCount > 0) {
            this.handleSimpleAnnocations((MemoryDataSet<NvwaQueryColumn>)dataTable, haveAdds, regions, totalCount, null, queryModel);
        }
        List<Object> searchLinks = null;
        if (!StringUtils.isEmpty((String)formCellAnnotationQueryInfo.getRegionKey())) {
            searchLinks = this.getLinkDatas(formCellAnnotationQueryInfo.getRegionKey());
        } else {
            searchLinks = new ArrayList();
            List dataRegionDefineList = this.runtimeView.getAllRegionsInForm(formCellAnnotationQueryInfo.getFormKey());
            for (DataRegionDefine dataRegionDefine : dataRegionDefineList) {
                searchLinks.addAll(this.getLinkDatas(dataRegionDefine.getKey()));
            }
        }
        HashMap<String, LinkData> fieldLinkMap = new HashMap<String, LinkData>();
        if (!searchLinks.isEmpty()) {
            for (LinkData linkData : searchLinks) {
                String zbid = linkData.getFieldKey();
                if (!StringUtils.isNotEmpty((String)zbid)) continue;
                fieldLinkMap.put(zbid, linkData);
            }
        }
        if (!searchLinks.isEmpty()) {
            int totalFieldCount;
            NvwaQueryModel queryFeldModel = new NvwaQueryModel();
            DataAccessContext dataAccessContext = new DataAccessContext(this.dataModelService);
            for (ColumnModelDefine columnModelDefine : annotationFields) {
                queryFeldModel.getColumns().add(new NvwaQueryColumn(columnModelDefine));
                if (!columnModelDefine.getCode().equals("MDCODE") && !columnModelDefine.getCode().equals("PERIOD")) continue;
                DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger("SYS_FMCEAN_" + formScheme.getFormSchemeCode());
                String dimensionName = dimensionChanger.getDimensionName(columnModelDefine);
                queryFeldModel.getColumnFilters().put(columnModelDefine, dimensionValueSet.getValue(dimensionName));
            }
            StringBuffer rowFilter2 = new StringBuffer();
            rowFilter2.append(" ( ").append(" ( ");
            rowFilter2.append(datalinkFiledTable.getName() + "[" + "DATALINK_KEY" + "] in { ");
            for (int x = 0; x < searchLinks.size(); ++x) {
                if (x == 0) {
                    rowFilter2.append("'" + ((LinkData)searchLinks.get(x)).getKey() + "'");
                    continue;
                }
                rowFilter2.append(",'" + ((LinkData)searchLinks.get(x)).getKey() + "'");
            }
            rowFilter2.append(" } ").append(" ) ").append(" ) ");
            for (ColumnModelDefine columnModelDefine : datalinkFieldFields) {
                queryFeldModel.getColumns().add(new NvwaQueryColumn(columnModelDefine));
            }
            dataAccessContext.setSqlJoinProvider((ISqlJoinProvider)provider);
            queryFeldModel.setMainTableName(formAnnotationTable.getName());
            queryFeldModel.setFilter(rowFilter2.toString());
            INvwaDataAccess readOnlyDataAccess2 = this.iNvwaDataAccessProvider.createReadOnlyDataAccess(queryFeldModel);
            MemoryDataSet dataFieldTable = null;
            try {
                dataFieldTable = readOnlyDataAccess2.executeQuery(dataAccessContext);
            }
            catch (Exception e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
            if (dataFieldTable != null && (totalFieldCount = dataFieldTable.size()) > 0) {
                this.handleSimpleAnnocations((MemoryDataSet<NvwaQueryColumn>)dataFieldTable, haveAdds, regions, totalFieldCount, fieldLinkMap, queryFeldModel);
            }
        }
        return result;
    }

    private List<LinkData> getLinkDatas(String regionKey) {
        ArrayList<LinkData> linkDataList = new ArrayList<LinkData>();
        List allLinksInRegion = this.runtimeView.getAllLinksInRegion(regionKey);
        for (DataLinkDefine dataLinkDefine : allLinksInRegion) {
            String fieldKey = dataLinkDefine.getLinkExpression();
            LinkData linkData = new LinkData();
            linkData.setKey(dataLinkDefine.getKey());
            linkData.setRegionKey(regionKey);
            linkData.setFieldKey(fieldKey);
            linkDataList.add(linkData);
        }
        return linkDataList;
    }

    private void handleSimpleAnnocations(MemoryDataSet<NvwaQueryColumn> dataTable, List<String> haveAdds, Map<String, RegionAnnotationResult> regions, int totalCount, Map<String, LinkData> fieldLinkMap, NvwaQueryModel queryModel) {
        for (int index = 0; index < totalCount; ++index) {
            RegionAnnotationResult regionResult = null;
            DataRow item = dataTable.get(index);
            String regionValue = "";
            String datalinkValue = "";
            String rowIdValue = "";
            String filedValue = "";
            List columns = queryModel.getColumns();
            for (int i = 0; i < columns.size(); ++i) {
                if (((NvwaQueryColumn)columns.get(i)).getColumnModel().getCode().equals("REGION_KEY")) {
                    regionValue = item.getString(i);
                    continue;
                }
                if (((NvwaQueryColumn)columns.get(i)).getColumnModel().getCode().equals("DATALINK_KEY")) {
                    datalinkValue = item.getString(i);
                    continue;
                }
                if (((NvwaQueryColumn)columns.get(i)).getColumnModel().getCode().equals("ROW_ID")) {
                    rowIdValue = item.getString(i);
                    continue;
                }
                if (!((NvwaQueryColumn)columns.get(i)).getColumnModel().getCode().equals("FIELD_KEY")) continue;
                filedValue = item.getString(i);
            }
            if (StringUtils.isEmpty((String)regionValue) && StringUtils.isEmpty((String)datalinkValue) && !StringUtils.isEmpty((String)filedValue) && null != fieldLinkMap) {
                LinkData linkData = fieldLinkMap.get(filedValue);
                regionValue = linkData.getRegionKey();
                datalinkValue = linkData.getKey();
            }
            if (regions.containsKey(regionValue)) {
                regionResult = regions.get(regionValue);
            } else {
                regionResult = new RegionAnnotationResult();
                regionResult.setRegionKey(regionValue);
                regions.put(regionValue, regionResult);
            }
            String key = datalinkValue + rowIdValue;
            if (haveAdds.contains(key)) continue;
            haveAdds.add(key);
            List<CellLocation> cells = regionResult.getCells();
            CellLocation cellLocation = new CellLocation();
            cellLocation.setDataLinkKey(datalinkValue);
            cellLocation.setRowId(rowIdValue);
            cells.add(cellLocation);
        }
    }

    @Override
    @Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
    public List<CellAnnotationContent> queryFormAnnotationDetailed(FormAnnotationQueryInfo formAnnotationQueryInfo) {
        int n;
        FormSchemeDefine formScheme = null;
        try {
            formScheme = this.runtimeView.getFormScheme(formAnnotationQueryInfo.getFormSchemeKey());
        }
        catch (Exception e) {
            throw new NotFoundFormSchemeException(ExceptionCodeCost.NOTFOUND_FORMSHCEME_BYKEY, new String[]{formAnnotationQueryInfo.getFormSchemeKey()});
        }
        TableModelDefine formAnnotationTable = this.dataModelService.getTableModelDefineByName("SYS_FMCEAN_" + formScheme.getFormSchemeCode());
        TableModelDefine datalinkFiledTable = this.dataModelService.getTableModelDefineByName("SYS_FMCEANDF_" + formScheme.getFormSchemeCode());
        TableModelDefine commentTable = this.dataModelService.getTableModelDefineByName("SYS_FMCEANCO_" + formScheme.getFormSchemeCode());
        TableModelDefine typeTable = this.dataModelService.getTableModelDefineByName("SYS_FMCEANTYPE_" + formScheme.getFormSchemeCode());
        if (null == formAnnotationTable || null == datalinkFiledTable || null == commentTable || null == typeTable) {
            throw new NotFoundTableDefineException(new String[]{"SYS_FMCEAN_" + formScheme.getFormSchemeCode(), "SYS_FMCEANDF_" + formScheme.getFormSchemeCode(), "SYS_FMCEANCO_" + formScheme.getFormSchemeCode(), "SYS_FMCEANTYPE_" + formScheme.getFormSchemeCode()});
        }
        DimensionCombination dimensionCombination = formAnnotationQueryInfo.getDimensionCombination();
        DimensionValueSet dimensionValueSet = dimensionCombination.toDimensionValueSet();
        List annotationFields = this.dataModelService.getColumnModelDefinesByTable(formAnnotationTable.getID());
        List datalinkFieldFields = this.dataModelService.getColumnModelDefinesByTable(datalinkFiledTable.getID());
        List commentFields = this.dataModelService.getColumnModelDefinesByTable(commentTable.getID());
        NvwaQueryModel queryModel = new NvwaQueryModel();
        for (ColumnModelDefine filed : annotationFields) {
            queryModel.getColumns().add(new NvwaQueryColumn(filed));
            if (filed.getCode().equals("FMCEAN_UPDATE_DATE")) {
                queryModel.getOrderByItems().add(new OrderByItem(filed, true));
            }
            if (!filed.getCode().equals("MDCODE") && !filed.getCode().equals("PERIOD")) continue;
            DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger("SYS_FMCEAN_" + formScheme.getFormSchemeCode());
            String dimensionName = dimensionChanger.getDimensionName(filed);
            queryModel.getColumnFilters().put(filed, dimensionValueSet.getValue(dimensionName));
        }
        for (ColumnModelDefine filed : datalinkFieldFields) {
            queryModel.getColumns().add(new NvwaQueryColumn(filed));
        }
        String formKey = formAnnotationQueryInfo.getFormKey();
        if (StringUtils.isEmpty((String)formKey)) {
            throw new RuntimeException(" formKey must not be null !!");
        }
        StringBuffer rowFilter = new StringBuffer();
        rowFilter.append(" ( ").append(" ( ");
        rowFilter.append(datalinkFiledTable.getName()).append("[").append("FORM_KEY").append("] in { ");
        String[] formKeys = formKey.split(";");
        for (int i = 0; i < formKeys.length; ++i) {
            if (0 == i) {
                rowFilter.append("'").append(formKeys[i]).append("'");
                continue;
            }
            rowFilter.append(",'").append(formKeys[i]).append("'");
        }
        rowFilter.append(" } ").append(" ) ");
        HashMap<String, String> regionKeyFormKeyMap = new HashMap<String, String>();
        ArrayList<LinkData> searchLinks = new ArrayList<LinkData>();
        for (String formKeyy : formKeys) {
            List<RegionData> formRegions = this.getRegionDatas(formKeyy);
            for (RegionData regionData : formRegions) {
                regionKeyFormKeyMap.put(regionData.getKey(), regionData.getFormKey());
                searchLinks.addAll(this.getLinkDatas(regionData.getKey()));
            }
        }
        HashMap<String, LinkData> fieldLinkMap = new HashMap<String, LinkData>();
        if (!searchLinks.isEmpty()) {
            rowFilter.append(" or ").append(" ( ");
            rowFilter.append(datalinkFiledTable.getName()).append("[").append("DATALINK_KEY").append("] in { ");
            for (int i = 0; i < searchLinks.size(); ++i) {
                LinkData linkData = (LinkData)searchLinks.get(i);
                fieldLinkMap.put(linkData.getFieldKey(), linkData);
                if (0 == i) {
                    rowFilter.append("'").append(linkData.getKey()).append("'");
                    continue;
                }
                rowFilter.append(",'").append(linkData.getKey()).append("'");
            }
            rowFilter.append(" } ").append(" ) ");
        }
        rowFilter.append(" ) ");
        LinkedHashMap<String, CellAnnotationContent> cellAnnotationContentMaps = new LinkedHashMap<String, CellAnnotationContent>();
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        boolean openAnnotationType = this.annotationTypeService.isOpenAnnotationType();
        if (openAnnotationType) {
            List<String> filterTypes = formAnnotationQueryInfo.getTypes();
            List typeFields = this.dataModelService.getColumnModelDefinesByTable(typeTable.getID());
            for (ColumnModelDefine typeField : typeFields) {
                queryModel.getColumns().add(new NvwaQueryColumn(typeField));
                if (!typeField.getCode().equals("FMCEANCO_UPDATE_DATE") || null == filterTypes || filterTypes.isEmpty()) continue;
                queryModel.getColumnFilters().put(typeField, filterTypes);
            }
            AnnotationDataFieldTypeJoinProviderWithNV annotationDataFieldTypeJoinProviderWithNV = new AnnotationDataFieldTypeJoinProviderWithNV(formScheme.getFormSchemeCode());
            context.setSqlJoinProvider((ISqlJoinProvider)annotationDataFieldTypeJoinProviderWithNV);
        } else {
            for (ColumnModelDefine filed : commentFields) {
                queryModel.getColumns().add(new NvwaQueryColumn(filed));
                if (!filed.getCode().equals("FMCEANCO_UPDATE_DATE")) continue;
                queryModel.getOrderByItems().add(new OrderByItem(filed, true));
            }
            FormAnnotationDataFieldCommentJoinProviderWithNV provider = new FormAnnotationDataFieldCommentJoinProviderWithNV(formScheme.getFormSchemeCode());
            context.setSqlJoinProvider((ISqlJoinProvider)provider);
        }
        queryModel.setFilter(rowFilter.toString());
        queryModel.setMainTableName(formAnnotationTable.getName());
        INvwaDataAccess dataAccess = this.iNvwaDataAccessProvider.createReadOnlyDataAccess(queryModel);
        MemoryDataSet dataTable = null;
        try {
            PagerInfo pagerInfo = formAnnotationQueryInfo.getPagerInfo();
            dataTable = null != pagerInfo ? dataAccess.executeQuery(context) : dataAccess.executeQuery(context);
        }
        catch (Exception exception) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + exception.getMessage(), exception);
        }
        int n2 = n = null == dataTable ? 0 : dataTable.size();
        if (n > 0) {
            HashMap<String, CellAnnotationRelation> cellAnnotationRelationMaps = new HashMap<String, CellAnnotationRelation>();
            HashMap<String, CellAnnotationComment> cellAnnotationCommentMaps = new HashMap<String, CellAnnotationComment>();
            this.handleAnnocationComents((MemoryDataSet<NvwaQueryColumn>)dataTable, cellAnnotationContentMaps, fieldLinkMap, regionKeyFormKeyMap, queryModel, cellAnnotationRelationMaps, cellAnnotationCommentMaps);
            PagerInfo pagerInfo = formAnnotationQueryInfo.getPagerInfo();
            if (null != pagerInfo) {
                int index;
                LinkedHashMap<String, CellAnnotationContent> pageAnnotationMap = new LinkedHashMap<String, CellAnnotationContent>();
                int annotationMapSize = cellAnnotationContentMaps.size();
                if (annotationMapSize < pagerInfo.getOffset() * pagerInfo.getLimit()) {
                    cellAnnotationContentMaps = pageAnnotationMap;
                } else if (annotationMapSize >= pagerInfo.getOffset() * pagerInfo.getLimit() && annotationMapSize <= (pagerInfo.getOffset() + 1) * pagerInfo.getLimit()) {
                    index = 0;
                    for (Map.Entry<String, CellAnnotationContent> keyAndAnnotationEntry : cellAnnotationContentMaps.entrySet()) {
                        if (index >= pagerInfo.getOffset() * pagerInfo.getLimit()) {
                            pageAnnotationMap.put(keyAndAnnotationEntry.getKey(), keyAndAnnotationEntry.getValue());
                        }
                        ++index;
                    }
                    cellAnnotationContentMaps = pageAnnotationMap;
                } else if (annotationMapSize > (pagerInfo.getOffset() + 1) * pagerInfo.getLimit()) {
                    index = 0;
                    for (Map.Entry<String, CellAnnotationContent> keyAndAnnotationEntry : cellAnnotationContentMaps.entrySet()) {
                        if (index >= pagerInfo.getOffset() * pagerInfo.getLimit() && index < (pagerInfo.getOffset() + 1) * pagerInfo.getLimit()) {
                            pageAnnotationMap.put(keyAndAnnotationEntry.getKey(), keyAndAnnotationEntry.getValue());
                        }
                        ++index;
                    }
                    cellAnnotationContentMaps = pageAnnotationMap;
                }
            }
        }
        if (!cellAnnotationContentMaps.isEmpty() && openAnnotationType) {
            ArrayList annotationIds = new ArrayList(cellAnnotationContentMaps.keySet());
            NvwaQueryModel commentQueryModel = new NvwaQueryModel();
            for (ColumnModelDefine filed : commentFields) {
                commentQueryModel.getColumns().add(new NvwaQueryColumn(filed));
                if (filed.getCode().equals("FMCEANCO_FMCEAN_ID")) {
                    commentQueryModel.getColumnFilters().put(filed, annotationIds);
                    continue;
                }
                if (!filed.getCode().equals("FMCEANCO_UPDATE_DATE")) continue;
                commentQueryModel.getOrderByItems().add(new OrderByItem(filed, true));
            }
            INvwaDataAccess commentDataAccess = this.iNvwaDataAccessProvider.createReadOnlyDataAccess(commentQueryModel);
            Iterator<Map.Entry<String, CellAnnotationContent>> commentContext = new DataAccessContext(this.dataModelService);
            try {
                MemoryDataSet dataSet = commentDataAccess.executeQuery(commentContext);
                List columns = commentQueryModel.getColumns();
                for (int i = 0; i < dataSet.size(); ++i) {
                    Object cellAnnotationContent;
                    List<CellAnnotationComment> comments;
                    DataRow data = dataSet.get(i);
                    String annotationId = "";
                    for (int j = 0; j < columns.size(); ++j) {
                        if (!((NvwaQueryColumn)columns.get(j)).getColumnModel().getCode().equals("FMCEANCO_FMCEAN_ID")) continue;
                        annotationId = data.getString(j);
                        break;
                    }
                    if (null == (comments = ((CellAnnotationContent)(cellAnnotationContent = cellAnnotationContentMaps.get(annotationId))).getComments())) {
                        comments = new ArrayList<CellAnnotationComment>();
                        ((CellAnnotationContent)cellAnnotationContent).setComments(comments);
                    }
                    CellAnnotationComment cellAnnotationComment = new CellAnnotationComment();
                    block33: for (int j = 0; j < columns.size(); ++j) {
                        String columnModelCode;
                        switch (columnModelCode = ((NvwaQueryColumn)columns.get(j)).getColumnModel().getCode()) {
                            case "FMCEANCO_ID": {
                                cellAnnotationComment.setId(data.getString(j));
                                continue block33;
                            }
                            case "FMCEANCO_CONTENT": {
                                cellAnnotationComment.setContent(data.getString(j));
                                continue block33;
                            }
                            case "FMCEANCO_USER_ID": {
                                cellAnnotationComment.setUsName(data.getString(j));
                                cellAnnotationComment.setUserName(data.getString(j));
                                continue block33;
                            }
                            case "FMCEANCO_REPY_USER_ID": {
                                cellAnnotationComment.setRepyUserName(data.getString(j));
                                continue block33;
                            }
                            case "FMCEANCO_UPDATE_DATE": {
                                Date updateDate = ((GregorianCalendar)data.getValue(j)).getTime();
                                cellAnnotationComment.setDate(updateDate.getTime());
                                continue block33;
                            }
                        }
                    }
                    comments.add(cellAnnotationComment);
                }
            }
            catch (Exception e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
        }
        ArrayList<CellAnnotationContent> result = new ArrayList<CellAnnotationContent>();
        if (!cellAnnotationContentMaps.isEmpty()) {
            HashMap<String, User> userNameMap = new HashMap<String, User>();
            String userName = formAnnotationQueryInfo.getUserName();
            for (Map.Entry<String, CellAnnotationContent> entry : cellAnnotationContentMaps.entrySet()) {
                CellAnnotationContent cellAnnotationContent = entry.getValue();
                String userId = cellAnnotationContent.getUserName();
                if (userName.equals(userId)) {
                    cellAnnotationContent.setCanEditOrDelete(true);
                }
                String name = this.findUserByUserName(userNameMap, cellAnnotationContent.getUserName());
                cellAnnotationContent.setUserName(name);
                List<CellAnnotationComment> comments = cellAnnotationContent.getComments();
                if (null != comments && comments.size() > 0) {
                    for (CellAnnotationComment comment : comments) {
                        userId = comment.getUserName();
                        if (userName.equals(userId)) {
                            comment.setCanEditOrDelete(true);
                        }
                        name = this.findUserByUserName(userNameMap, comment.getUserName());
                        comment.setUserName(name);
                        if (StringUtils.isEmpty((String)comment.getRepyUserName())) continue;
                        String repyUserName = this.findUserByUserName(userNameMap, comment.getRepyUserName());
                        comment.setRepyUserName(repyUserName);
                    }
                }
                result.add(cellAnnotationContent);
            }
            Collections.sort(result);
        }
        HashSet<String> typeCodes = new HashSet<String>();
        List typeMaps = result.stream().map(CellAnnotationContent::getTypeCodeTitleMap).collect(Collectors.toList());
        for (Map typeMap : typeMaps) {
            if (null == typeMap) continue;
            typeCodes.addAll(typeMap.keySet());
        }
        if (!typeCodes.isEmpty()) {
            Map<String, IEntityRow> typeCodeEntityRowMap = this.annotationTypeService.queryTypeEntityRow(dimensionCombination, formAnnotationQueryInfo.getFormSchemeKey(), typeCodes);
            for (CellAnnotationContent cellAnnotationContent : result) {
                HashMap<String, String> newTypeCodeTitleMap = new HashMap<String, String>();
                Map<String, String> typeCodeTitleMap = cellAnnotationContent.getTypeCodeTitleMap();
                if (null == typeCodeTitleMap) continue;
                for (String typeCode : typeCodeTitleMap.keySet()) {
                    IEntityRow entityRow = typeCodeEntityRowMap.get(typeCode);
                    if (null == entityRow) continue;
                    newTypeCodeTitleMap.put(typeCode, entityRow.getTitle());
                }
                cellAnnotationContent.setTypeCodeTitleMap(newTypeCodeTitleMap);
            }
        }
        return result;
    }

    private List<RegionData> getRegionDatas(String formKey) {
        ArrayList<RegionData> regionDatas = new ArrayList<RegionData>();
        List dataRegionDefines = this.runtimeView.getAllRegionsInForm(formKey);
        for (DataRegionDefine dataRegionDefine : dataRegionDefines) {
            RegionData regionData = new RegionData();
            regionData.setKey(dataRegionDefine.getKey());
            regionData.setFormKey(formKey);
            regionDatas.add(regionData);
        }
        return regionDatas;
    }

    private void handleAnnocationComents(MemoryDataSet<NvwaQueryColumn> dataTable, LinkedHashMap<String, CellAnnotationContent> cellAnnotationContentMaps, Map<String, LinkData> fieldDataMap, Map<String, String> regionKeyFormKeyMap, NvwaQueryModel queryModel, Map<String, CellAnnotationRelation> cellAnnotationRelationMaps, Map<String, CellAnnotationComment> cellAnnotationCommentMaps) {
        for (int index = 0; index < dataTable.size(); ++index) {
            int i;
            String dataFieldMapkey;
            LinkData linkData;
            CellAnnotationContent cellAnnotationContent = null;
            DataRow item = dataTable.get(index);
            String annotationId = "";
            String annotationContent = "";
            String annotationUserId = "";
            Date annotationUpdateDate = null;
            List columns = queryModel.getColumns();
            block43: for (int i2 = 0; i2 < columns.size(); ++i2) {
                String columnModelCode;
                switch (columnModelCode = ((NvwaQueryColumn)columns.get(i2)).getColumnModel().getCode()) {
                    case "FMCEAN_ID": {
                        annotationId = item.getString(i2);
                        continue block43;
                    }
                    case "FMCEAN_CONTENT": {
                        annotationContent = item.getString(i2);
                        continue block43;
                    }
                    case "FMCEAN_USER_ID": {
                        annotationUserId = item.getString(i2);
                        continue block43;
                    }
                    case "FMCEAN_UPDATE_DATE": {
                        annotationUpdateDate = ((GregorianCalendar)item.getValue(i2)).getTime();
                    }
                }
            }
            if (cellAnnotationContentMaps.containsKey(annotationId)) {
                cellAnnotationContent = cellAnnotationContentMaps.get(annotationId);
            } else {
                cellAnnotationContent = new CellAnnotationContent();
                cellAnnotationContent.setId(annotationId);
                cellAnnotationContent.setContent(annotationContent);
                cellAnnotationContent.setDate(annotationUpdateDate.getTime());
                cellAnnotationContent.setUserName(annotationUserId);
                cellAnnotationContentMaps.put(annotationId, cellAnnotationContent);
            }
            List<CellAnnotationRelation> relations = cellAnnotationContent.getRelations();
            String datalinkFieldIdValue = "";
            String datalinkFieldShowValue = "";
            String datalinkFieldValue = "";
            String rowIdFieldValue = "";
            String regionFieldValue = "";
            String formKeyFieldIdValue = "";
            String fieldFieldValue = "";
            block44: for (int i3 = 0; i3 < columns.size(); ++i3) {
                String columnModelCode;
                switch (columnModelCode = ((NvwaQueryColumn)columns.get(i3)).getColumnModel().getCode()) {
                    case "FMCEANDF_ID": {
                        datalinkFieldIdValue = item.getString(i3);
                        continue block44;
                    }
                    case "FMCEANDF_SHOW": {
                        datalinkFieldShowValue = item.getString(i3);
                        continue block44;
                    }
                    case "DATALINK_KEY": {
                        datalinkFieldValue = item.getString(i3);
                        continue block44;
                    }
                    case "ROW_ID": {
                        rowIdFieldValue = item.getString(i3);
                        continue block44;
                    }
                    case "REGION_KEY": {
                        regionFieldValue = item.getString(i3);
                        continue block44;
                    }
                    case "FIELD_KEY": {
                        fieldFieldValue = item.getString(i3);
                        continue block44;
                    }
                    case "FORM_KEY": {
                        formKeyFieldIdValue = item.getString(i3);
                    }
                }
            }
            if (StringUtils.isEmpty((String)regionFieldValue) && StringUtils.isEmpty((String)datalinkFieldValue) && !StringUtils.isEmpty((String)fieldFieldValue) && null != fieldDataMap && null != (linkData = fieldDataMap.get(fieldFieldValue))) {
                datalinkFieldValue = linkData.getKey();
                regionFieldValue = linkData.getRegionKey();
                if (null != regionKeyFormKeyMap) {
                    formKeyFieldIdValue = regionKeyFormKeyMap.get(regionFieldValue);
                }
            }
            if (!cellAnnotationRelationMaps.containsKey(dataFieldMapkey = cellAnnotationContent.getId() + datalinkFieldIdValue)) {
                CellAnnotationRelation cellAnnotationRelation = new CellAnnotationRelation();
                cellAnnotationRelation.setDataLinkKey(datalinkFieldValue);
                cellAnnotationRelation.setShow(datalinkFieldShowValue);
                cellAnnotationRelation.setRegionKey(regionFieldValue);
                cellAnnotationRelation.setRowId(rowIdFieldValue);
                cellAnnotationRelation.setFormKey(formKeyFieldIdValue);
                relations.add(cellAnnotationRelation);
                cellAnnotationRelationMaps.put(dataFieldMapkey, cellAnnotationRelation);
            }
            for (int i4 = 0; i4 < columns.size(); ++i4) {
                String type;
                String columnModelCode = ((NvwaQueryColumn)columns.get(i4)).getColumnModel().getCode();
                if (!"TYPE_CODE".equals(columnModelCode) || !StringUtils.isNotEmpty((String)(type = item.getString(i4)))) continue;
                Map<String, String> typeCodeTitleMap = cellAnnotationContent.getTypeCodeTitleMap();
                if (null == typeCodeTitleMap) {
                    typeCodeTitleMap = new HashMap<String, String>();
                    cellAnnotationContent.setTypeCodeTitleMap(typeCodeTitleMap);
                }
                if (typeCodeTitleMap.containsKey(type)) continue;
                typeCodeTitleMap.put(type, null);
            }
            List<CellAnnotationComment> comments = cellAnnotationContent.getComments();
            String commentFieldIdValue = "";
            String commentFieldContentValue = "";
            String commentFieldUserIdValue = "";
            Date commentFieldUpdateDateValue = null;
            String commentFieldRepyIdValue = "";
            for (i = 0; i < columns.size(); ++i) {
                if (!((NvwaQueryColumn)columns.get(i)).getColumnModel().getCode().equals("FMCEANCO_ID")) continue;
                commentFieldIdValue = item.getString(i);
            }
            if (StringUtils.isEmpty((String)commentFieldIdValue)) continue;
            block47: for (i = 0; i < columns.size(); ++i) {
                String columnModelCode;
                switch (columnModelCode = ((NvwaQueryColumn)columns.get(i)).getColumnModel().getCode()) {
                    case "FMCEANCO_CONTENT": {
                        commentFieldContentValue = item.getString(i);
                        continue block47;
                    }
                    case "FMCEANCO_USER_ID": {
                        commentFieldUserIdValue = item.getString(i);
                        continue block47;
                    }
                    case "FMCEANCO_UPDATE_DATE": {
                        commentFieldUpdateDateValue = ((GregorianCalendar)item.getValue(i)).getTime();
                        continue block47;
                    }
                    case "FMCEANCO_REPY_USER_ID": {
                        commentFieldRepyIdValue = item.getString(i);
                    }
                }
            }
            String commentMapKey = cellAnnotationContent.getId() + commentFieldIdValue;
            if (cellAnnotationCommentMaps.containsKey(commentMapKey)) continue;
            CellAnnotationComment comment = new CellAnnotationComment();
            comment.setContent(commentFieldContentValue);
            if (null != commentFieldUpdateDateValue) {
                comment.setDate(commentFieldUpdateDateValue.getTime());
            }
            comment.setId(commentFieldIdValue);
            comment.setUsName(commentFieldUserIdValue);
            comment.setUserName(commentFieldUserIdValue);
            comment.setRepyUserName(commentFieldRepyIdValue);
            comments.add(comment);
            cellAnnotationCommentMaps.put(commentMapKey, comment);
        }
    }

    private String findUserByUserName(Map<String, User> userNameMap, String userName) {
        if (userNameMap.containsKey(userName)) {
            User user = userNameMap.get(userName);
            return user.getNickname();
        }
        Optional list = this.userService.findByUsername(userName);
        if (list.isPresent()) {
            User user = (User)list.get();
            userNameMap.put(userName, user);
            userName = user.getNickname();
        } else {
            Optional slist = this.systemUserService.findByUsername(userName);
            if (slist.isPresent()) {
                User user = (User)slist.get();
                userNameMap.put(userName, user);
                userName = user.getNickname();
            }
        }
        return userName;
    }

    @Override
    @Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
    public CellAnnotationResult queryCellAnnotation(CellAnnotationQueryInfo cellAnnotationQueryInfo) {
        int totalFieldCount;
        FormSchemeDefine formScheme = null;
        try {
            formScheme = this.runtimeView.getFormScheme(cellAnnotationQueryInfo.getFormSchemeKey());
        }
        catch (Exception e) {
            throw new NotFoundFormSchemeException(ExceptionCodeCost.NOTFOUND_FORMSHCEME_BYKEY, new String[]{cellAnnotationQueryInfo.getFormSchemeKey()});
        }
        TableModelDefine formAnnotationTable = this.dataModelService.getTableModelDefineByName("SYS_FMCEAN_" + formScheme.getFormSchemeCode());
        TableModelDefine datalinkFiledTable = this.dataModelService.getTableModelDefineByName("SYS_FMCEANDF_" + formScheme.getFormSchemeCode());
        TableModelDefine commentTable = this.dataModelService.getTableModelDefineByName("SYS_FMCEANCO_" + formScheme.getFormSchemeCode());
        TableModelDefine typeTable = this.dataModelService.getTableModelDefineByName("SYS_FMCEANTYPE_" + formScheme.getFormSchemeCode());
        if (null == formAnnotationTable || null == datalinkFiledTable || null == commentTable || null == typeTable) {
            throw new NotFoundTableDefineException(new String[]{"SYS_FMCEAN_" + formScheme.getFormSchemeCode(), "SYS_FMCEANDF_" + formScheme.getFormSchemeCode(), "SYS_FMCEANCO_" + formScheme.getFormSchemeCode(), "SYS_FMCEANTYPE_" + formScheme.getFormSchemeCode()});
        }
        DimensionCombination dimensionCombination = cellAnnotationQueryInfo.getDimensionCombination();
        DimensionValueSet dimensionValueSet = dimensionCombination.toDimensionValueSet();
        List annotationFields = this.dataModelService.getColumnModelDefinesByTable(formAnnotationTable.getID());
        List datalinkFieldFields = this.dataModelService.getColumnModelDefinesByTable(datalinkFiledTable.getID());
        List commentFields = this.dataModelService.getColumnModelDefinesByTable(commentTable.getID());
        NvwaQueryModel queryModel = new NvwaQueryModel();
        for (ColumnModelDefine filed : annotationFields) {
            queryModel.getColumns().add(new NvwaQueryColumn(filed));
            if (filed.getCode().equals("FMCEAN_UPDATE_DATE")) {
                queryModel.getOrderByItems().add(new OrderByItem(filed, true));
            }
            if (!filed.getCode().equals("MDCODE") && !filed.getCode().equals("PERIOD")) continue;
            DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger("SYS_FMCEAN_" + formScheme.getFormSchemeCode());
            String dimensionName = dimensionChanger.getDimensionName(filed);
            queryModel.getColumnFilters().put(filed, dimensionValueSet.getValue(dimensionName));
        }
        for (ColumnModelDefine filed : datalinkFieldFields) {
            queryModel.getColumns().add(new NvwaQueryColumn(filed));
        }
        String dataLinkKey = cellAnnotationQueryInfo.getDataLinkKey();
        LinkData link = this.getLinkData(dataLinkKey);
        String linkKey = link.getKey();
        if (StringUtils.isEmpty((String)linkKey)) {
            throw new IllegalArgumentException(" linkKey must not be null !!");
        }
        StringBuffer rowFilter = new StringBuffer();
        rowFilter.append(" ( ").append(" ( ");
        rowFilter.append(datalinkFiledTable.getName()).append("[").append("DATALINK_KEY").append("] in { ");
        String[] dataLinkKeys = linkKey.split(";");
        for (int i = 0; i < dataLinkKeys.length; ++i) {
            if (i == 0) {
                rowFilter.append("'").append(dataLinkKeys[i]).append("'");
                continue;
            }
            rowFilter.append(",'").append(dataLinkKeys[i]).append("'");
        }
        rowFilter.append(" } ").append(" ) ").append(" ) ");
        String rowId = cellAnnotationQueryInfo.getRowId();
        if (StringUtils.isNotEmpty((String)rowId)) {
            rowFilter.append(" and ").append(" ( ");
            rowFilter.append(datalinkFiledTable.getName()).append("[").append("ROW_ID").append("] in { ");
            String[] rowIds = rowId.split(";");
            for (int i = 0; i < rowIds.length; ++i) {
                if (i == 0) {
                    rowFilter.append("'").append(rowIds[i]).append("'");
                    continue;
                }
                rowFilter.append(",'").append(rowIds[i]).append("'");
            }
            rowFilter.append(" } ").append(" ) ");
        }
        LinkedHashMap<String, CellAnnotationContent> cellAnnotationContentMaps = new LinkedHashMap<String, CellAnnotationContent>();
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        boolean openAnnotationType = this.annotationTypeService.isOpenAnnotationType();
        if (openAnnotationType) {
            List typeFields = this.dataModelService.getColumnModelDefinesByTable(typeTable.getID());
            Iterator iterator = typeFields.iterator();
            while (iterator.hasNext()) {
                ColumnModelDefine typeField = (ColumnModelDefine)iterator.next();
                queryModel.getColumns().add(new NvwaQueryColumn(typeField));
            }
            AnnotationDataFieldTypeJoinProviderWithNV provider = new AnnotationDataFieldTypeJoinProviderWithNV(formScheme.getFormSchemeCode());
            context.setSqlJoinProvider((ISqlJoinProvider)provider);
        } else {
            for (ColumnModelDefine filed : commentFields) {
                queryModel.getColumns().add(new NvwaQueryColumn(filed));
                if (!filed.getCode().equals("FMCEANCO_UPDATE_DATE")) continue;
                queryModel.getOrderByItems().add(new OrderByItem(filed, true));
            }
            FormAnnotationDataFieldCommentJoinProviderWithNV provider = new FormAnnotationDataFieldCommentJoinProviderWithNV(formScheme.getFormSchemeCode());
            context.setSqlJoinProvider((ISqlJoinProvider)provider);
        }
        queryModel.setFilter(rowFilter.toString());
        queryModel.setMainTableName(formAnnotationTable.getName());
        INvwaDataAccess readOnlyDataAccess = this.iNvwaDataAccessProvider.createReadOnlyDataAccess(queryModel);
        MemoryDataSet dataTable = null;
        try {
            dataTable = readOnlyDataAccess.executeQuery(context);
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        int count = null == dataTable ? 0 : dataTable.size();
        HashMap<String, CellAnnotationRelation> cellAnnotationRelationMaps = new HashMap<String, CellAnnotationRelation>();
        HashMap<String, CellAnnotationComment> cellAnnotationCommentMaps = new HashMap<String, CellAnnotationComment>();
        if (count > 0) {
            this.handleAnnocationComents((MemoryDataSet<NvwaQueryColumn>)dataTable, cellAnnotationContentMaps, null, null, queryModel, cellAnnotationRelationMaps, cellAnnotationCommentMaps);
        }
        NvwaQueryModel fieldQueryModel = new NvwaQueryModel();
        for (ColumnModelDefine filed : annotationFields) {
            fieldQueryModel.getColumns().add(new NvwaQueryColumn(filed));
            if (!filed.getCode().equals("MDCODE") && !filed.getCode().equals("PERIOD")) continue;
            DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger("SYS_FMCEAN_" + formScheme.getFormSchemeCode());
            String dimensionName = dimensionChanger.getDimensionName(filed);
            fieldQueryModel.getColumnFilters().put(filed, dimensionValueSet.getValue(dimensionName));
        }
        for (ColumnModelDefine filed : datalinkFieldFields) {
            fieldQueryModel.getColumns().add(new NvwaQueryColumn(filed));
        }
        String fieldKey = link.getFieldKey();
        if (StringUtils.isEmpty((String)fieldKey)) {
            throw new IllegalArgumentException(" fieldKey must not be null !!");
        }
        StringBuilder fieldRowFilter = new StringBuilder();
        fieldRowFilter.append(" ( ").append(" ( ");
        fieldRowFilter.append(datalinkFiledTable.getName()).append("[").append("FIELD_KEY").append("] in { ");
        String[] filedKeys = fieldKey.split(";");
        for (int i = 0; i < filedKeys.length; ++i) {
            if (i == 0) {
                fieldRowFilter.append("'").append(filedKeys[i]).append("'");
                continue;
            }
            fieldRowFilter.append(",'").append(filedKeys[i]).append("'");
        }
        fieldRowFilter.append(" } ").append(" ) ").append(" ) ");
        if (StringUtils.isNotEmpty((String)rowId)) {
            fieldRowFilter.append(" and ").append(" ( ");
            fieldRowFilter.append(datalinkFiledTable.getName()).append("[").append("ROW_ID").append("] in { ");
            String[] rowIds = rowId.split(";");
            for (int i = 0; i < rowIds.length; ++i) {
                if (i == 0) {
                    fieldRowFilter.append("'").append(rowIds[i]).append("'");
                    continue;
                }
                fieldRowFilter.append(",'").append(rowIds[i]).append("'");
            }
            fieldRowFilter.append(" } ").append(" ) ");
        }
        DataAccessContext fieldContext = new DataAccessContext(this.dataModelService);
        if (openAnnotationType) {
            List typeFields = this.dataModelService.getColumnModelDefinesByTable(typeTable.getID());
            Iterator iterator = typeFields.iterator();
            while (iterator.hasNext()) {
                ColumnModelDefine typeField = (ColumnModelDefine)iterator.next();
                fieldQueryModel.getColumns().add(new NvwaQueryColumn(typeField));
            }
            AnnotationDataFieldTypeJoinProviderWithNV provider = new AnnotationDataFieldTypeJoinProviderWithNV(formScheme.getFormSchemeCode());
            fieldContext.setSqlJoinProvider((ISqlJoinProvider)provider);
        } else {
            for (ColumnModelDefine filed : commentFields) {
                fieldQueryModel.getColumns().add(new NvwaQueryColumn(filed));
                if (!filed.getCode().equals("FMCEANCO_UPDATE_DATE")) continue;
                fieldQueryModel.getOrderByItems().add(new OrderByItem(filed, true));
            }
            FormAnnotationDataFieldCommentJoinProviderWithNV provider = new FormAnnotationDataFieldCommentJoinProviderWithNV(formScheme.getFormSchemeCode());
            fieldContext.setSqlJoinProvider((ISqlJoinProvider)provider);
        }
        fieldQueryModel.setFilter(fieldRowFilter.toString());
        fieldQueryModel.setMainTableName(formAnnotationTable.getName());
        INvwaDataAccess fieldReadOnlyDataAccess = this.iNvwaDataAccessProvider.createReadOnlyDataAccess(fieldQueryModel);
        MemoryDataSet dataFieldTable = null;
        try {
            dataFieldTable = fieldReadOnlyDataAccess.executeQuery(fieldContext);
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        int n = totalFieldCount = null == dataFieldTable ? 0 : dataFieldTable.size();
        if (totalFieldCount > 0) {
            HashMap<String, LinkData> fieldDataMap = new HashMap<String, LinkData>();
            fieldDataMap.put(fieldKey, link);
            this.handleAnnocationComents((MemoryDataSet<NvwaQueryColumn>)dataFieldTable, cellAnnotationContentMaps, fieldDataMap, null, fieldQueryModel, cellAnnotationRelationMaps, cellAnnotationCommentMaps);
        }
        if (openAnnotationType) {
            ArrayList<String> annotationIds = new ArrayList<String>(cellAnnotationContentMaps.keySet());
            NvwaQueryModel commentQueryModel = new NvwaQueryModel();
            for (ColumnModelDefine filed : commentFields) {
                commentQueryModel.getColumns().add(new NvwaQueryColumn(filed));
                if (filed.getCode().equals("FMCEANCO_FMCEAN_ID")) {
                    commentQueryModel.getColumnFilters().put(filed, annotationIds);
                    continue;
                }
                if (!filed.getCode().equals("FMCEANCO_UPDATE_DATE")) continue;
                commentQueryModel.getOrderByItems().add(new OrderByItem(filed, true));
            }
            INvwaDataAccess commentDataAccess = this.iNvwaDataAccessProvider.createReadOnlyDataAccess(commentQueryModel);
            DataAccessContext commentContext = new DataAccessContext(this.dataModelService);
            try {
                MemoryDataSet memoryDataSet = commentDataAccess.executeQuery(commentContext);
                List columns = commentQueryModel.getColumns();
                for (int i = 0; i < memoryDataSet.size(); ++i) {
                    CellAnnotationContent cellAnnotationContent;
                    List<CellAnnotationComment> comments;
                    DataRow data = memoryDataSet.get(i);
                    String annotationId = "";
                    for (int j = 0; j < columns.size(); ++j) {
                        if (!((NvwaQueryColumn)columns.get(j)).getColumnModel().getCode().equals("FMCEANCO_FMCEAN_ID")) continue;
                        annotationId = data.getString(j);
                        break;
                    }
                    if (null == (comments = (cellAnnotationContent = cellAnnotationContentMaps.get(annotationId)).getComments())) {
                        comments = new ArrayList<CellAnnotationComment>();
                        cellAnnotationContent.setComments(comments);
                    }
                    CellAnnotationComment cellAnnotationComment = new CellAnnotationComment();
                    block37: for (int j = 0; j < columns.size(); ++j) {
                        String columnModelCode;
                        switch (columnModelCode = ((NvwaQueryColumn)columns.get(j)).getColumnModel().getCode()) {
                            case "FMCEANCO_ID": {
                                cellAnnotationComment.setId(data.getString(j));
                                continue block37;
                            }
                            case "FMCEANCO_CONTENT": {
                                cellAnnotationComment.setContent(data.getString(j));
                                continue block37;
                            }
                            case "FMCEANCO_USER_ID": {
                                cellAnnotationComment.setUsName(data.getString(j));
                                cellAnnotationComment.setUserName(data.getString(j));
                                continue block37;
                            }
                            case "FMCEANCO_REPY_USER_ID": {
                                cellAnnotationComment.setRepyUserName(data.getString(j));
                                continue block37;
                            }
                            case "FMCEANCO_UPDATE_DATE": {
                                Date updateDate = ((GregorianCalendar)data.getValue(j)).getTime();
                                cellAnnotationComment.setDate(updateDate.getTime());
                            }
                        }
                    }
                    comments.add(cellAnnotationComment);
                }
            }
            catch (Exception exception) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + exception.getMessage(), exception);
            }
        }
        CellAnnotationResult result = new CellAnnotationResult();
        result.setDataLinkKey(dataLinkKey);
        result.setRowId(rowId);
        if (cellAnnotationContentMaps.size() > 0) {
            HashMap<String, User> userNameMap = new HashMap<String, User>();
            String userName = cellAnnotationQueryInfo.getUserName();
            for (Map.Entry entry : cellAnnotationContentMaps.entrySet()) {
                List<CellAnnotationComment> comments;
                CellAnnotationContent cellAnnotationContent = (CellAnnotationContent)entry.getValue();
                if (userName.equals(cellAnnotationContent.getUserName())) {
                    cellAnnotationContent.setCanEditOrDelete(true);
                }
                if (null != (comments = cellAnnotationContent.getComments()) && comments.size() > 0) {
                    for (CellAnnotationComment comment : comments) {
                        if (userName.equals(comment.getUserName())) {
                            comment.setCanEditOrDelete(true);
                        }
                        String name = this.findUserByUserName(userNameMap, comment.getUserName());
                        comment.setUserName(name);
                        if (StringUtils.isEmpty((String)comment.getRepyUserName())) continue;
                        String repyUserName = this.findUserByUserName(userNameMap, comment.getRepyUserName());
                        comment.setRepyUserName(repyUserName);
                    }
                }
                String name = this.findUserByUserName(userNameMap, cellAnnotationContent.getUserName());
                cellAnnotationContent.setUserName(name);
                result.getContents().add(cellAnnotationContent);
            }
            Collections.sort(result.getContents());
        }
        HashSet<String> typeCodes = new HashSet<String>();
        List typeMaps = result.getContents().stream().map(CellAnnotationContent::getTypeCodeTitleMap).collect(Collectors.toList());
        for (Map map : typeMaps) {
            if (null == map) continue;
            typeCodes.addAll(map.keySet());
        }
        if (!typeCodes.isEmpty()) {
            Map<String, IEntityRow> typeCodeEntityRowMap = this.annotationTypeService.queryTypeEntityRow(dimensionCombination, cellAnnotationQueryInfo.getFormSchemeKey(), typeCodes);
            for (CellAnnotationContent cellAnnotationContent : result.getContents()) {
                HashMap<String, String> newTypeCodeTitleMap = new HashMap<String, String>();
                Map<String, String> typeCodeTitleMap = cellAnnotationContent.getTypeCodeTitleMap();
                if (null == typeCodeTitleMap) continue;
                for (String typeCode : typeCodeTitleMap.keySet()) {
                    IEntityRow entityRow = typeCodeEntityRowMap.get(typeCode);
                    if (null == entityRow) continue;
                    newTypeCodeTitleMap.put(typeCode, entityRow.getTitle());
                }
                cellAnnotationContent.setTypeCodeTitleMap(newTypeCodeTitleMap);
            }
        }
        return result;
    }

    private LinkData getLinkData(String dataLinkKey) {
        DataLinkDefine dataLinkDefine = this.runtimeView.queryDataLinkDefine(dataLinkKey);
        DataRegionDefine dataRegionDefine = this.runtimeView.queryDataRegionDefine(dataLinkDefine.getRegionKey());
        LinkData linkData = new LinkData();
        linkData.setKey(dataLinkKey);
        linkData.setFieldKey(dataLinkDefine.getLinkExpression());
        linkData.setRegionKey(dataRegionDefine.getKey());
        return linkData;
    }

    @Override
    public List<ExpAnnotationResult> queryAnnotation(ExpAnnotationParam param) {
        FormSchemeDefine formScheme = this.runtimeView.getFormScheme(param.getFormSchemeKey());
        TableModelDefine formAnnotationTable = this.dataModelService.getTableModelDefineByName("SYS_FMCEAN_" + formScheme.getFormSchemeCode());
        TableModelDefine datalinkFiledTable = this.dataModelService.getTableModelDefineByName("SYS_FMCEANDF_" + formScheme.getFormSchemeCode());
        TableModelDefine commentTable = this.dataModelService.getTableModelDefineByName("SYS_FMCEANCO_" + formScheme.getFormSchemeCode());
        TableModelDefine typeTable = this.dataModelService.getTableModelDefineByName("SYS_FMCEANTYPE_" + formScheme.getFormSchemeCode());
        if (null == formAnnotationTable || null == datalinkFiledTable || null == commentTable || null == typeTable) {
            throw new NotFoundTableDefineException(new String[]{"SYS_FMCEAN_" + formScheme.getFormSchemeCode(), "SYS_FMCEANDF_" + formScheme.getFormSchemeCode(), "SYS_FMCEANCO_" + formScheme.getFormSchemeCode(), "SYS_FMCEANTYPE_" + formScheme.getFormSchemeCode()});
        }
        DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(formAnnotationTable.getName());
        HashSet<String> dimNames = new HashSet<String>();
        NvwaQueryModel queryModel = new NvwaQueryModel();
        MemoryDataSet<NvwaQueryColumn> dataTable = this.queryData(param, formScheme, formAnnotationTable, datalinkFiledTable, typeTable, dimensionChanger, dimNames, queryModel);
        Map<String, ExpAnnotationResult> expAnnotationResultMap = this.buildRes(dimensionChanger, queryModel, dimNames, dataTable);
        this.queryComment(commentTable, expAnnotationResultMap);
        ArrayList<ExpAnnotationResult> result = new ArrayList<ExpAnnotationResult>();
        if (!expAnnotationResultMap.isEmpty()) {
            result.addAll(expAnnotationResultMap.values());
        }
        return result;
    }

    @Nullable
    private MemoryDataSet<NvwaQueryColumn> queryData(ExpAnnotationParam param, FormSchemeDefine formScheme, TableModelDefine formAnnotationTable, TableModelDefine datalinkFiledTable, TableModelDefine typeTable, DimensionChanger dimensionChanger, Set<String> dimNames, NvwaQueryModel queryModel) {
        List annotationFields = this.dataModelService.getColumnModelDefinesByTable(formAnnotationTable.getID());
        List datalinkFieldFields = this.dataModelService.getColumnModelDefinesByTable(datalinkFiledTable.getID());
        HashMap<String, List> dimNameValueMap = new HashMap<String, List>();
        List dimensionCombinations = param.getDims().getDimensionCombinations();
        for (DimensionCombination dimensionCombination : dimensionCombinations) {
            for (String dimName : dimensionCombination.getNames()) {
                List dimValues = dimNameValueMap.computeIfAbsent(dimName, k -> new ArrayList());
                dimValues.add((String)dimensionCombination.getValue(dimName));
            }
        }
        for (Object filed : annotationFields) {
            String string;
            queryModel.getColumns().add(new NvwaQueryColumn((ColumnModelDefine)filed));
            if ("FMCEAN_ID".equals(filed.getCode()) || "FMCEAN_CONTENT".equals(filed.getCode()) || "FMCEAN_USER_ID".equals(filed.getCode()) || "FMCEAN_UPDATE_DATE".equals(filed.getCode()) || null == dimNameValueMap.get(string = dimensionChanger.getDimensionName((ColumnModelDefine)filed)) || ((List)dimNameValueMap.get(string)).isEmpty()) continue;
            dimNames.add(string);
            queryModel.getColumnFilters().put(filed, dimNameValueMap.get(string));
        }
        for (Object filed : datalinkFieldFields) {
            queryModel.getColumns().add(new NvwaQueryColumn((ColumnModelDefine)filed));
        }
        StringBuilder rowFilter = new StringBuilder();
        rowFilter.append(" ( ");
        rowFilter.append(datalinkFiledTable.getName()).append("[").append("FORM_KEY").append("] in { ");
        for (String string : param.getFormKeys()) {
            rowFilter.append("'").append(string).append("',");
        }
        rowFilter.delete(rowFilter.length() - 1, rowFilter.length());
        rowFilter.append(" } ").append(" ) ");
        List typeFields = this.dataModelService.getColumnModelDefinesByTable(typeTable.getID());
        for (ColumnModelDefine typeField : typeFields) {
            queryModel.getColumns().add(new NvwaQueryColumn(typeField));
        }
        DataAccessContext dataAccessContext = new DataAccessContext(this.dataModelService);
        AnnotationDataFieldTypeJoinProviderWithNV provider = new AnnotationDataFieldTypeJoinProviderWithNV(formScheme.getFormSchemeCode());
        dataAccessContext.setSqlJoinProvider((ISqlJoinProvider)provider);
        queryModel.setFilter(rowFilter.toString());
        queryModel.setMainTableName(formAnnotationTable.getName());
        INvwaDataAccess dataAccess = this.iNvwaDataAccessProvider.createReadOnlyDataAccess(queryModel);
        MemoryDataSet dataTable = null;
        try {
            dataTable = dataAccess.executeQuery(dataAccessContext);
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return dataTable;
    }

    private void queryComment(TableModelDefine commentTable, Map<String, ExpAnnotationResult> expAnnotationResultMap) {
        if (!expAnnotationResultMap.isEmpty()) {
            List commentFields = this.dataModelService.getColumnModelDefinesByTable(commentTable.getID());
            ArrayList<String> annotationIds = new ArrayList<String>(expAnnotationResultMap.keySet());
            NvwaQueryModel commentQueryModel = new NvwaQueryModel();
            for (ColumnModelDefine filed : commentFields) {
                commentQueryModel.getColumns().add(new NvwaQueryColumn(filed));
                if (!filed.getCode().equals("FMCEANCO_FMCEAN_ID")) continue;
                commentQueryModel.getColumnFilters().put(filed, annotationIds);
            }
            INvwaDataAccess commentDataAccess = this.iNvwaDataAccessProvider.createReadOnlyDataAccess(commentQueryModel);
            DataAccessContext commentContext = new DataAccessContext(this.dataModelService);
            try {
                MemoryDataSet dataSet = commentDataAccess.executeQuery(commentContext);
                List columns = commentQueryModel.getColumns();
                for (int i = 0; i < dataSet.size(); ++i) {
                    ExpAnnotationResult expAnnotationResult;
                    List<ExpAnnotationComment> comments;
                    DataRow data = dataSet.get(i);
                    String annotationId = "";
                    for (int j = 0; j < columns.size(); ++j) {
                        if (!((NvwaQueryColumn)columns.get(j)).getColumnModel().getCode().equals("FMCEANCO_FMCEAN_ID")) continue;
                        annotationId = data.getString(j);
                        break;
                    }
                    if (null == (comments = (expAnnotationResult = expAnnotationResultMap.get(annotationId)).getComments())) {
                        comments = new ArrayList<ExpAnnotationComment>();
                        expAnnotationResult.setComments(comments);
                    }
                    ExpAnnotationComment expAnnotationComment = new ExpAnnotationComment();
                    block19: for (int j = 0; j < columns.size(); ++j) {
                        String columnModelCode;
                        switch (columnModelCode = ((NvwaQueryColumn)columns.get(j)).getColumnModel().getCode()) {
                            case "FMCEANCO_ID": {
                                expAnnotationComment.setId(data.getString(j));
                                continue block19;
                            }
                            case "FMCEANCO_CONTENT": {
                                expAnnotationComment.setContent(data.getString(j));
                                continue block19;
                            }
                            case "FMCEANCO_USER_ID": {
                                expAnnotationComment.setUserName(data.getString(j));
                                continue block19;
                            }
                            case "FMCEANCO_REPY_USER_ID": {
                                expAnnotationComment.setRepyUserName(data.getString(j));
                                continue block19;
                            }
                            case "FMCEANCO_UPDATE_DATE": {
                                Date updateDate = ((GregorianCalendar)data.getValue(j)).getTime();
                                expAnnotationComment.setDate(updateDate.getTime());
                                continue block19;
                            }
                        }
                    }
                    comments.add(expAnnotationComment);
                }
            }
            catch (Exception e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
        }
    }

    @NotNull
    private Map<String, ExpAnnotationResult> buildRes(DimensionChanger dimensionChanger, NvwaQueryModel queryModel, Set<String> dimNames, MemoryDataSet<NvwaQueryColumn> dataTable) {
        HashMap<String, ExpAnnotationResult> expAnnotationResultMap = new HashMap<String, ExpAnnotationResult>();
        if (null != dataTable) {
            HashMap<String, ExpAnnotationRel> expAnnotationRelMap = new HashMap<String, ExpAnnotationRel>();
            for (int index = 0; index < dataTable.size(); ++index) {
                DataRow item = dataTable.get(index);
                ExpAnnotationResult expAnnotationResult = null;
                String annotationId = "";
                String annotationContent = "";
                String annotationUserId = "";
                Date annotationUpdateDate = null;
                HashMap<String, String> dimNameValue = new HashMap<String, String>();
                List columns = queryModel.getColumns();
                block31: for (int i = 0; i < columns.size(); ++i) {
                    String columnModelCode;
                    switch (columnModelCode = ((NvwaQueryColumn)columns.get(i)).getColumnModel().getCode()) {
                        case "FMCEAN_ID": {
                            annotationId = item.getString(i);
                            continue block31;
                        }
                        case "FMCEAN_CONTENT": {
                            annotationContent = item.getString(i);
                            continue block31;
                        }
                        case "FMCEAN_USER_ID": {
                            annotationUserId = item.getString(i);
                            continue block31;
                        }
                        case "FMCEAN_UPDATE_DATE": {
                            annotationUpdateDate = ((GregorianCalendar)item.getValue(i)).getTime();
                            continue block31;
                        }
                        default: {
                            String dimensionName = dimensionChanger.getDimensionName(columnModelCode);
                            if (null == dimensionName || !dimNames.contains(dimensionName)) continue block31;
                            dimNameValue.put(dimensionName, item.getString(i));
                        }
                    }
                }
                if (expAnnotationResultMap.containsKey(annotationId)) {
                    expAnnotationResult = (ExpAnnotationResult)expAnnotationResultMap.get(annotationId);
                } else {
                    expAnnotationResult = new ExpAnnotationResult();
                    expAnnotationResult.setId(annotationId);
                    expAnnotationResult.setDimNameValue(dimNameValue);
                    expAnnotationResult.setContent(annotationContent);
                    expAnnotationResult.setUserName(annotationUserId);
                    expAnnotationResult.setDate(annotationUpdateDate.getTime());
                    expAnnotationResultMap.put(annotationId, expAnnotationResult);
                }
                List<ExpAnnotationRel> relations = expAnnotationResult.getRelations();
                String relId = "";
                String relFormKey = "";
                String relRegionKey = "";
                String relDataLinkKey = "";
                String relFieldKey = "";
                String relRowId = "";
                String relShow = "";
                block32: for (int i = 0; i < columns.size(); ++i) {
                    String columnModelCode;
                    switch (columnModelCode = ((NvwaQueryColumn)columns.get(i)).getColumnModel().getCode()) {
                        case "FMCEANDF_ID": {
                            relId = item.getString(i);
                            continue block32;
                        }
                        case "FMCEANDF_SHOW": {
                            relShow = item.getString(i);
                            continue block32;
                        }
                        case "DATALINK_KEY": {
                            relDataLinkKey = item.getString(i);
                            continue block32;
                        }
                        case "ROW_ID": {
                            relRowId = item.getString(i);
                            continue block32;
                        }
                        case "REGION_KEY": {
                            relRegionKey = item.getString(i);
                            continue block32;
                        }
                        case "FIELD_KEY": {
                            relFieldKey = item.getString(i);
                            continue block32;
                        }
                        case "FORM_KEY": {
                            relFormKey = item.getString(i);
                        }
                    }
                }
                String dataFieldMapkey = expAnnotationResult.getId() + relId;
                if (!expAnnotationRelMap.containsKey(dataFieldMapkey)) {
                    ExpAnnotationRel expAnnotationRel = new ExpAnnotationRel();
                    expAnnotationRel.setId(relId);
                    expAnnotationRel.setFormKey(relFormKey);
                    expAnnotationRel.setRegionKey(relRegionKey);
                    expAnnotationRel.setDataLinkKey(relDataLinkKey);
                    expAnnotationRel.setFieldKey(relFieldKey);
                    expAnnotationRel.setRowId(relRowId);
                    expAnnotationRel.setShow(relShow);
                    relations.add(expAnnotationRel);
                    expAnnotationRelMap.put(dataFieldMapkey, expAnnotationRel);
                }
                for (int i = 0; i < columns.size(); ++i) {
                    String type;
                    String columnModelCode = ((NvwaQueryColumn)columns.get(i)).getColumnModel().getCode();
                    if (!"TYPE_CODE".equals(columnModelCode) || !StringUtils.isNotEmpty((String)(type = item.getString(i)))) continue;
                    List<String> typeCode = expAnnotationResult.getTypeCode();
                    if (null == typeCode) {
                        typeCode = new ArrayList<String>();
                        expAnnotationResult.setTypeCode(typeCode);
                    }
                    if (typeCode.contains(type)) continue;
                    typeCode.add(type);
                }
            }
        }
        return expAnnotationResultMap;
    }

    @Override
    public CellAnnotationContent saveFormAnnotation(SaveFormAnnotationInfo saveFormAnnotationInfo) {
        CellAnnotationInfo cellAnnotationInfo;
        String formKey = saveFormAnnotationInfo.getFormKey();
        FormSchemeDefine formScheme = null;
        try {
            formScheme = this.runtimeView.getFormScheme(saveFormAnnotationInfo.getFormSchemeKey());
        }
        catch (Exception e) {
            throw new NotFoundFormSchemeException(ExceptionCodeCost.NOTFOUND_FORMSHCEME_BYKEY, new String[]{saveFormAnnotationInfo.getFormSchemeKey()});
        }
        DimensionValueSet dimension = saveFormAnnotationInfo.getDimensionCombination().toDimensionValueSet();
        TaskDefine taskDefine = this.runtimeView.queryTaskDefine(formScheme.getTaskKey());
        String dwDimensionName = this.entityMetaService.getDimensionName(taskDefine.getDw());
        String periodDimensionName = this.periodAdapter.getPeriodDimensionName();
        String targetKey = String.valueOf(dimension.getValue(dwDimensionName));
        String periodCode = String.valueOf(dimension.getValue(periodDimensionName));
        LogDimensionCollection logDimensionCollection = new LogDimensionCollection();
        logDimensionCollection.setDw(taskDefine.getDw(), new String[]{targetKey});
        logDimensionCollection.setPeriod(taskDefine.getDateTime(), periodCode);
        LogHellperUtil logHellperUtil = new LogHellperUtil(this.dataServiceLoggerFactory, MOUDLE);
        CellAnnotationContent cellAnnotationContent = new CellAnnotationContent();
        cellAnnotationContent.setCanEditOrDelete(true);
        cellAnnotationContent.setContent(saveFormAnnotationInfo.getContent());
        cellAnnotationContent.setUserName(saveFormAnnotationInfo.getUserFullname());
        UUID annocationId = UUID.randomUUID();
        cellAnnotationContent.setId(annocationId.toString());
        Date updateDate = new Date();
        cellAnnotationContent.setDate(updateDate.getTime());
        DimensionCombination dimensionCombination = saveFormAnnotationInfo.getDimensionCombination();
        DimensionValueSet dimensionValueSet = dimensionCombination.toDimensionValueSet();
        HashMap<String, String> columMap = new HashMap<String, String>();
        List<String> dimensionNames = this.getEntityDefines(saveFormAnnotationInfo.getFormSchemeKey());
        for (String dimensionName : dimensionNames) {
            columMap.put(dimensionName, dimensionValueSet.getValue(dimensionName).toString());
        }
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        TableModelDefine formAnnotationTable = this.dataModelService.getTableModelDefineByName("SYS_FMCEAN_" + formScheme.getFormSchemeCode());
        if (null == formAnnotationTable) {
            logHellperUtil.error(taskDefine.getKey(), logDimensionCollection, "\u6279\u6ce8\u6dfb\u52a0\u5931\u8d25", "\u7cfb\u7edf\u9519\u8bef\uff1a\u6279\u6ce8\u4fe1\u606f\u8868\u672a\u521b\u5efa");
            throw new NotFoundTableDefineException(new String[]{"SYS_FMCEAN_" + formScheme.getFormSchemeCode()});
        }
        DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(formAnnotationTable.getName());
        NvwaQueryModel queryModel = new NvwaQueryModel();
        List formAnnotationTableFields = this.dataModelService.getColumnModelDefinesByTable(formAnnotationTable.getID());
        for (ColumnModelDefine filed : formAnnotationTableFields) {
            queryModel.getColumns().add(new NvwaQueryColumn(filed));
        }
        try {
            INvwaUpdatableDataAccess updatableDataAccess = this.iNvwaDataAccessProvider.createUpdatableDataAccess(queryModel);
            INvwaDataUpdator iNvwaDataUpdator = updatableDataAccess.openForUpdate(context);
            INvwaDataRow iNvwaDataRow = iNvwaDataUpdator.addInsertRow();
            List columns = queryModel.getColumns();
            block56: for (int i = 0; i < columns.size(); ++i) {
                ColumnModelDefine filed = ((NvwaQueryColumn)columns.get(i)).getColumnModel();
                switch (filed.getCode()) {
                    case "FMCEAN_ID": {
                        iNvwaDataRow.setValue(i, (Object)annocationId.toString());
                        continue block56;
                    }
                    case "FMCEAN_CONTENT": {
                        iNvwaDataRow.setValue(i, (Object)saveFormAnnotationInfo.getContent());
                        continue block56;
                    }
                    case "FMCEAN_USER_ID": {
                        iNvwaDataRow.setValue(i, (Object)saveFormAnnotationInfo.getUserName());
                        continue block56;
                    }
                    case "FMCEAN_UPDATE_DATE": {
                        iNvwaDataRow.setValue(i, (Object)updateDate);
                        continue block56;
                    }
                    default: {
                        Iterator<Object> dimensionName = dimensionChanger.getDimensionName(filed);
                        if (!columMap.containsKey(dimensionName) || !filed.getCode().equals(filed.getCode())) continue block56;
                        iNvwaDataRow.setValue(i, columMap.get(dimensionName));
                    }
                }
            }
            iNvwaDataUpdator.commitChanges(context);
        }
        catch (Exception e) {
            logHellperUtil.error(taskDefine.getKey(), logDimensionCollection, "\u6279\u6ce8\u6dfb\u52a0\u5931\u8d25", PromptConsts.fileOprateSystemError(e.getMessage()));
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            return null;
        }
        TableModelDefine dataLinkFieldTable = this.dataModelService.getTableModelDefineByName("SYS_FMCEANDF_" + formScheme.getFormSchemeCode());
        if (null == dataLinkFieldTable) {
            logHellperUtil.error(taskDefine.getKey(), logDimensionCollection, "\u6279\u6ce8\u6dfb\u52a0\u5931\u8d25", "\u7cfb\u7edf\u9519\u8bef\uff1a\u6279\u6ce8\u94fe\u63a5\u6307\u6807\u8868\u672a\u521b\u5efa");
            throw new NotFoundTableDefineException(new String[]{"SYS_FMCEANDF_" + formScheme.getFormSchemeCode()});
        }
        List dataLinkFieldFields = this.dataModelService.getColumnModelDefinesByTable(dataLinkFieldTable.getID());
        NvwaQueryModel dataLinkQueryModel = new NvwaQueryModel();
        for (ColumnModelDefine filed : dataLinkFieldFields) {
            dataLinkQueryModel.getColumns().add(new NvwaQueryColumn(filed));
        }
        List<CellAnnotationInfo> cells = saveFormAnnotationInfo.getCells();
        boolean addField = true;
        if (cells.size() > 1) {
            addField = false;
        } else {
            cellAnnotationInfo = cells.get(0);
            if (!StringUtils.isEmpty((String)cellAnnotationInfo.getRowId())) {
                addField = false;
            }
        }
        if (!addField && StringUtils.isEmpty((String)(cellAnnotationInfo = cells.get(0)).getDataLinkKey()) && !StringUtils.isEmpty((String)cellAnnotationInfo.getFieldKey())) {
            HashMap<String, String> fieldKeyLinkKeyMap = new HashMap<String, String>();
            List allRegionDefines = this.runtimeView.getAllRegionsInForm(formKey);
            if (null != allRegionDefines) {
                for (DataRegionDefine dataRegionDefine : allRegionDefines) {
                    List<LinkData> links = this.getLinkDatas(dataRegionDefine.getKey());
                    for (LinkData link : links) {
                        if (!StringUtils.isNotEmpty((String)link.getFieldKey())) continue;
                        fieldKeyLinkKeyMap.put(link.getFieldKey(), link.getKey());
                    }
                }
            }
            for (CellAnnotationInfo cellAnnotationInfo2 : cells) {
                if (!StringUtils.isEmpty((String)cellAnnotationInfo2.getDataLinkKey()) || StringUtils.isEmpty((String)cellAnnotationInfo2.getFieldKey())) continue;
                cellAnnotationInfo2.setDataLinkKey((String)fieldKeyLinkKeyMap.get(cellAnnotationInfo2.getFieldKey()));
                cellAnnotationInfo2.setFieldKey("");
            }
        }
        try {
            INvwaUpdatableDataAccess updatableDataAccess = this.iNvwaDataAccessProvider.createUpdatableDataAccess(dataLinkQueryModel);
            INvwaDataUpdator iNvwaDataUpdator = updatableDataAccess.openForUpdate(context);
            List columns = dataLinkQueryModel.getColumns();
            for (CellAnnotationInfo cellAnnotationInfo3 : cells) {
                String columnModelCode;
                int i;
                CellAnnotationRelation cellAnnotationRelation = new CellAnnotationRelation();
                LinkData link = null;
                String tempId = UUID.randomUUID().toString();
                INvwaDataRow iNvwaDataRow = iNvwaDataUpdator.addInsertRow();
                block62: for (i = 0; i < columns.size(); ++i) {
                    switch (columnModelCode = ((NvwaQueryColumn)columns.get(i)).getColumnModel().getCode()) {
                        case "FMCEANDF_ID": {
                            iNvwaDataRow.setValue(i, (Object)tempId);
                            continue block62;
                        }
                        case "FMCEANDF_SHOW": {
                            iNvwaDataRow.setValue(i, (Object)cellAnnotationInfo3.getShow());
                            continue block62;
                        }
                        case "FMCEANDF_FMCEAN_ID": {
                            iNvwaDataRow.setValue(i, (Object)annocationId.toString());
                        }
                    }
                }
                if (!StringUtils.isEmpty((String)cellAnnotationInfo3.getDataLinkKey())) {
                    link = this.getLinkData(cellAnnotationInfo3.getDataLinkKey());
                }
                if (null == link) {
                    for (i = 0; i < columns.size(); ++i) {
                        if (!((NvwaQueryColumn)columns.get(i)).getColumnModel().getCode().equals("FORM_KEY")) continue;
                        iNvwaDataRow.setValue(i, (Object)formKey);
                        break;
                    }
                } else if (addField) {
                    block64: for (i = 0; i < columns.size(); ++i) {
                        switch (columnModelCode = ((NvwaQueryColumn)columns.get(i)).getColumnModel().getCode()) {
                            case "FIELD_KEY": {
                                iNvwaDataRow.setValue(i, (Object)link.getFieldKey());
                                continue block64;
                            }
                            case "FORM_KEY": {
                                iNvwaDataRow.setValue(i, (Object)formKey);
                                continue block64;
                            }
                            case "DATALINK_KEY": {
                                iNvwaDataRow.setValue(i, (Object)cellAnnotationInfo3.getDataLinkKey());
                                continue block64;
                            }
                            case "REGION_KEY": {
                                iNvwaDataRow.setValue(i, (Object)link.getRegionKey());
                            }
                        }
                    }
                } else {
                    block65: for (i = 0; i < columns.size(); ++i) {
                        switch (columnModelCode = ((NvwaQueryColumn)columns.get(i)).getColumnModel().getCode()) {
                            case "ROW_ID": {
                                iNvwaDataRow.setValue(i, (Object)cellAnnotationInfo3.getRowId());
                                continue block65;
                            }
                            case "FORM_KEY": {
                                iNvwaDataRow.setValue(i, (Object)formKey);
                                continue block65;
                            }
                            case "DATALINK_KEY": {
                                iNvwaDataRow.setValue(i, (Object)cellAnnotationInfo3.getDataLinkKey());
                                continue block65;
                            }
                            case "REGION_KEY": {
                                iNvwaDataRow.setValue(i, (Object)link.getRegionKey());
                            }
                        }
                    }
                }
                cellAnnotationRelation.setDataLinkKey(null == link ? "" : link.getKey());
                cellAnnotationRelation.setRegionKey(null == link ? "" : link.getRegionKey());
                cellAnnotationRelation.setRowId(cellAnnotationInfo3.getRowId());
                cellAnnotationRelation.setShow(cellAnnotationInfo3.getShow());
                cellAnnotationRelation.setFormKey(formKey);
                cellAnnotationContent.getRelations().add(cellAnnotationRelation);
            }
            iNvwaDataUpdator.commitChanges(context);
        }
        catch (Exception e) {
            logHellperUtil.error(taskDefine.getKey(), logDimensionCollection, "\u6279\u6ce8\u6dfb\u52a0\u5931\u8d25", PromptConsts.fileOprateSystemError(e.getMessage()));
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            return null;
        }
        List<String> annotationTypes = saveFormAnnotationInfo.getTypes();
        if (null != annotationTypes && !annotationTypes.isEmpty()) {
            TableModelDefine annotationTypeTable = this.dataModelService.getTableModelDefineByName("SYS_FMCEANTYPE_" + formScheme.getFormSchemeCode());
            if (null == annotationTypeTable) {
                logHellperUtil.error(taskDefine.getKey(), logDimensionCollection, "\u6279\u6ce8\u6dfb\u52a0\u5931\u8d25", "\u7cfb\u7edf\u9519\u8bef\uff1a\u6279\u6ce8\u7c7b\u578b\u5173\u8054\u8868\u672a\u521b\u5efa");
                throw new NotFoundTableDefineException(new String[]{"SYS_FMCEANTYPE_" + formScheme.getFormSchemeCode()});
            }
            List annotationTypeFields = this.dataModelService.getColumnModelDefinesByTable(annotationTypeTable.getID());
            NvwaQueryModel typeQueryModel = new NvwaQueryModel();
            for (ColumnModelDefine filed : annotationTypeFields) {
                typeQueryModel.getColumns().add(new NvwaQueryColumn(filed));
            }
            try {
                INvwaUpdatableDataAccess iNvwaUpdatableDataAccess = this.iNvwaDataAccessProvider.createUpdatableDataAccess(typeQueryModel);
                INvwaDataUpdator iNvwaDataUpdator = iNvwaUpdatableDataAccess.openForUpdate(context);
                List columns = typeQueryModel.getColumns();
                for (String annotationType : annotationTypes) {
                    INvwaDataRow iNvwaDataRow = iNvwaDataUpdator.addInsertRow();
                    for (int i = 0; i < columns.size(); ++i) {
                        String columnModelCode = ((NvwaQueryColumn)columns.get(i)).getColumnModel().getCode();
                        if (columnModelCode.equals("TYPE_FMCEAN_ID")) {
                            iNvwaDataRow.setValue(i, (Object)annocationId.toString());
                            continue;
                        }
                        if (!columnModelCode.equals("TYPE_CODE")) continue;
                        iNvwaDataRow.setValue(i, (Object)annotationType);
                    }
                }
                iNvwaDataUpdator.commitChanges(context);
            }
            catch (Exception exception) {
                logHellperUtil.error(taskDefine.getKey(), logDimensionCollection, "\u6279\u6ce8\u6dfb\u52a0\u5931\u8d25", PromptConsts.fileOprateSystemError(exception.getMessage()));
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + exception.getMessage(), exception);
                return null;
            }
            Map<String, IEntityRow> map = this.annotationTypeService.queryTypeEntityRow(dimensionCombination, saveFormAnnotationInfo.getFormSchemeKey(), new HashSet<String>(annotationTypes));
            HashMap<String, String> typeCodeTitleMap = new HashMap<String, String>();
            for (String typeCode : map.keySet()) {
                typeCodeTitleMap.put(typeCode, map.get(typeCode).getTitle());
            }
            cellAnnotationContent.setTypeCodeTitleMap(typeCodeTitleMap);
        }
        logHellperUtil.info(taskDefine.getKey(), logDimensionCollection, "\u6279\u6ce8\u6dfb\u52a0\u6210\u529f", "\u6279\u6ce8\u6dfb\u52a0\u6210\u529f");
        return cellAnnotationContent;
    }

    @Override
    public CellAnnotationContent updateFormAnnotation(UpdateFormAnnotationInfo updateFormAnnotationInfo) {
        String updateId = updateFormAnnotationInfo.getId();
        if (StringUtils.isEmpty((String)updateId)) {
            throw new IllegalArgumentException("\u6279\u6ce8id\u4e0d\u80fd\u4e3a\u7a7a");
        }
        FormSchemeDefine formScheme = null;
        try {
            formScheme = this.runtimeView.getFormScheme(updateFormAnnotationInfo.getFormSchemeKey());
        }
        catch (Exception e) {
            throw new NotFoundFormSchemeException(ExceptionCodeCost.NOTFOUND_FORMSHCEME_BYKEY, new String[]{updateFormAnnotationInfo.getFormSchemeKey()});
        }
        DimensionCombination dimensionCombination = updateFormAnnotationInfo.getDimensionCombination();
        DimensionValueSet dimensionValueSet = dimensionCombination.toDimensionValueSet();
        TaskDefine taskDefine = this.runtimeView.queryTaskDefine(formScheme.getTaskKey());
        String dwDimensionName = this.entityMetaService.getDimensionName(taskDefine.getDw());
        String periodDimensionName = this.periodAdapter.getPeriodDimensionName();
        String targetKey = String.valueOf(dimensionValueSet.getValue(dwDimensionName));
        String periodCode = String.valueOf(dimensionValueSet.getValue(periodDimensionName));
        LogDimensionCollection logDimensionCollection = new LogDimensionCollection();
        logDimensionCollection.setDw(taskDefine.getDw(), new String[]{targetKey});
        logDimensionCollection.setPeriod(taskDefine.getDateTime(), periodCode);
        LogHellperUtil logHellperUtil = new LogHellperUtil(this.dataServiceLoggerFactory, MOUDLE);
        CellAnnotationContent cellAnnotationContent = new CellAnnotationContent();
        cellAnnotationContent.setCanEditOrDelete(true);
        cellAnnotationContent.setContent(updateFormAnnotationInfo.getContent());
        cellAnnotationContent.setId(updateId);
        String fullname = updateFormAnnotationInfo.getUserFullname();
        cellAnnotationContent.setUserName(fullname);
        TableModelDefine formAnnotationTable = this.dataModelService.getTableModelDefineByName("SYS_FMCEAN_" + formScheme.getFormSchemeCode());
        TableModelDefine annotationTypeTable = this.dataModelService.getTableModelDefineByName("SYS_FMCEANTYPE_" + formScheme.getFormSchemeCode());
        if (null == formAnnotationTable || null == annotationTypeTable) {
            logHellperUtil.error(taskDefine.getKey(), logDimensionCollection, "\u6279\u6ce8\u4fee\u6539\u5931\u8d25", PromptConsts.fileOprateSystemError("\u6279\u6ce8\u76f8\u5173\u5b58\u50a8\u8868\u672a\u521b\u5efa"));
            throw new NotFoundTableDefineException(new String[]{"SYS_FMCEAN_" + formScheme.getFormSchemeCode(), "SYS_FMCEANTYPE_" + formScheme.getFormSchemeCode()});
        }
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        NvwaQueryModel queryModel = new NvwaQueryModel();
        List formAnnotationTableFields = this.dataModelService.getColumnModelDefinesByTable(formAnnotationTable.getID());
        for (ColumnModelDefine filed : formAnnotationTableFields) {
            queryModel.getColumns().add(new NvwaQueryColumn(filed));
            if (!filed.getCode().equals("FMCEAN_ID")) continue;
            queryModel.getColumnFilters().put(filed, updateId);
        }
        Date updateDate = new Date();
        cellAnnotationContent.setDate(updateDate.getTime());
        String userName = updateFormAnnotationInfo.getUserName();
        INvwaUpdatableDataAccess updatableDataAccess = this.iNvwaDataAccessProvider.createUpdatableDataAccess(queryModel);
        try {
            INvwaUpdatableDataSet iNvwaDataRows = updatableDataAccess.executeQueryForUpdate(context);
            for (int index = 0; index < iNvwaDataRows.size(); ++index) {
                int i;
                INvwaDataRow dataRow = iNvwaDataRows.getRow(index);
                List columns = queryModel.getColumns();
                for (i = 0; i < columns.size(); ++i) {
                    if (!((NvwaQueryColumn)columns.get(i)).getColumnModel().getCode().equals("FMCEAN_USER_ID")) continue;
                    String oldUserId = (String)dataRow.getValue(i);
                    if (userName.equals(oldUserId)) break;
                    return null;
                }
                block19: for (i = 0; i < columns.size(); ++i) {
                    switch (((NvwaQueryColumn)columns.get(i)).getColumnModel().getCode()) {
                        case "FMCEAN_UPDATE_DATE": {
                            dataRow.setValue(i, (Object)updateDate);
                            continue block19;
                        }
                        case "FMCEAN_USER_ID": {
                            dataRow.setValue(i, (Object)userName);
                            continue block19;
                        }
                        case "FMCEAN_CONTENT": {
                            dataRow.setValue(i, (Object)updateFormAnnotationInfo.getContent());
                        }
                    }
                }
            }
            iNvwaDataRows.commitChanges(context);
        }
        catch (Exception e) {
            logHellperUtil.error(taskDefine.getKey(), logDimensionCollection, "\u6279\u6ce8\u4fee\u6539\u5931\u8d25", PromptConsts.fileOprateSystemError(e.getMessage()));
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            return null;
        }
        NvwaQueryModel typeQueryModel = new NvwaQueryModel();
        List typeFields = this.dataModelService.getColumnModelDefinesByTable(annotationTypeTable.getID());
        for (ColumnModelDefine filed : typeFields) {
            typeQueryModel.getColumns().add(new NvwaQueryColumn(filed));
            if (!"TYPE_FMCEAN_ID".equals(filed.getCode())) continue;
            typeQueryModel.getColumnFilters().put(filed, updateId);
        }
        INvwaUpdatableDataAccess typeUpdatable = this.iNvwaDataAccessProvider.createUpdatableDataAccess(typeQueryModel);
        try {
            INvwaDataUpdator typeUpdator = typeUpdatable.openForUpdate(context);
            typeUpdator.deleteAll();
            List<String> types = updateFormAnnotationInfo.getTypes();
            if (null != types && !types.isEmpty()) {
                List columns = typeQueryModel.getColumns();
                for (String type : types) {
                    INvwaDataRow iNvwaDataRow = typeUpdator.addInsertRow();
                    for (int i = 0; i < columns.size(); ++i) {
                        String columnModelCode = ((NvwaQueryColumn)columns.get(i)).getColumnModel().getCode();
                        if ("TYPE_FMCEAN_ID".equals(columnModelCode)) {
                            iNvwaDataRow.setValue(i, (Object)updateId);
                            continue;
                        }
                        if (!"TYPE_CODE".equals(columnModelCode)) continue;
                        iNvwaDataRow.setValue(i, (Object)type);
                    }
                }
                Map<String, IEntityRow> typeCodeEntityRowMap = this.annotationTypeService.queryTypeEntityRow(dimensionCombination, updateFormAnnotationInfo.getFormSchemeKey(), new HashSet<String>(types));
                HashMap<String, String> typeCodeTitleMap = new HashMap<String, String>();
                for (String typeCode : typeCodeEntityRowMap.keySet()) {
                    typeCodeTitleMap.put(typeCode, typeCodeEntityRowMap.get(typeCode).getTitle());
                }
                cellAnnotationContent.setTypeCodeTitleMap(typeCodeTitleMap);
            }
            typeUpdator.commitChanges(context);
        }
        catch (Exception e) {
            logHellperUtil.error(taskDefine.getKey(), logDimensionCollection, "\u6279\u6ce8\u4fee\u6539\u5931\u8d25", PromptConsts.fileOprateSystemError(e.getMessage()));
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            return null;
        }
        logHellperUtil.info(taskDefine.getKey(), logDimensionCollection, "\u6279\u6ce8\u4fee\u6539\u6210\u529f", "\u6279\u6ce8\u4fee\u6539\u6210\u529f");
        return cellAnnotationContent;
    }

    private List<String> getEntityDefines(String formSchemeKey) {
        ArrayList<String> dimensionNames = new ArrayList<String>();
        FormSchemeDefine formSchemeDefine = null;
        try {
            formSchemeDefine = this.runtimeView.getFormScheme(formSchemeKey);
        }
        catch (Exception e) {
            logger.error("\u62a5\u8868\u65b9\u6848\u672a\u627e\u5230:" + formSchemeKey, e);
            throw new NotFoundFormSchemeException(ExceptionCodeCost.NOTFOUND_FORMSHCEME_BYKEY, new String[]{formSchemeKey.toString()});
        }
        if (formSchemeDefine != null) {
            IEntityDefine entityDefine = this.entityMetaService.queryEntity(formSchemeDefine.getDw());
            dimensionNames.add(entityDefine.getDimensionName());
            IPeriodEntity periodEntity = this.periodAdapter.getPeriodEntity(formSchemeDefine.getDateTime());
            dimensionNames.add(periodEntity.getDimensionName());
            if (StringUtils.isNotEmpty((String)formSchemeDefine.getDims())) {
                String[] dimEntityIds;
                for (String dimEntityId : dimEntityIds = formSchemeDefine.getDims().split(";")) {
                    entityDefine = this.entityMetaService.queryEntity(dimEntityId);
                    dimensionNames.add(entityDefine.getDimensionName());
                }
            }
        }
        return dimensionNames;
    }

    @Override
    public void batchSaveFormAnnotation(FormAnnotationBatchSaveInfo formAnnotationBatchSaveInfo) throws ErrorParamException {
        if (!this.checkParam(formAnnotationBatchSaveInfo)) {
            throw new ErrorParamException("Parameter must not be empty!");
        }
        FormSchemeDefine formScheme = null;
        try {
            formScheme = this.runtimeView.getFormScheme(formAnnotationBatchSaveInfo.getFormSchemeKey());
        }
        catch (Exception e) {
            throw new ErrorParamException(ExceptionCodeCost.NOTFOUND_FORMSHCEME_BYKEY, new String[]{formAnnotationBatchSaveInfo.getFormSchemeKey()});
        }
        TaskDefine taskDefine = this.runtimeView.queryTaskDefine(formScheme.getTaskKey());
        LogHellperUtil logHellperUtil = new LogHellperUtil(this.dataServiceLoggerFactory, MOUDLE);
        try {
            TableModelDefine formAnnotationTable = this.dataModelService.getTableModelDefineByName("SYS_FMCEAN_" + formScheme.getFormSchemeCode());
            TableModelDefine dataLinkFieldTable = this.dataModelService.getTableModelDefineByName("SYS_FMCEANDF_" + formScheme.getFormSchemeCode());
            TableModelDefine annotationTypeTable = this.dataModelService.getTableModelDefineByName("SYS_FMCEANTYPE_" + formScheme.getFormSchemeCode());
            if (null == formAnnotationTable || null == dataLinkFieldTable || null == annotationTypeTable) {
                logHellperUtil.error(taskDefine.getKey(), null, "\u6279\u6ce8\u6279\u91cf\u6dfb\u52a0\u5931\u8d25", PromptConsts.fileOprateSystemError("\u6279\u6ce8\u76f8\u5173\u5b58\u50a8\u8868\u672a\u521b\u5efa"));
                throw new NotFoundTableDefineException(new String[]{"SYS_FMCEAN_" + formScheme.getFormSchemeCode(), "SYS_FMCEANDF_" + formScheme.getFormSchemeCode(), "SYS_FMCEANTYPE_" + formScheme.getFormSchemeCode()});
            }
            DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger("SYS_FMCEAN_" + formScheme.getFormSchemeCode());
            DataAccessContext context = new DataAccessContext(this.dataModelService);
            NvwaQueryModel queryModel = new NvwaQueryModel();
            List formAnnotationTableFields = this.dataModelService.getColumnModelDefinesByTable(formAnnotationTable.getID());
            for (ColumnModelDefine filed : formAnnotationTableFields) {
                queryModel.getColumns().add(new NvwaQueryColumn(filed));
            }
            INvwaUpdatableDataAccess updatableDataAccess = this.iNvwaDataAccessProvider.createUpdatableDataAccess(queryModel);
            INvwaDataUpdator iNvwaDataUpdator = updatableDataAccess.openForUpdate(context);
            NvwaQueryModel queryModelLink = new NvwaQueryModel();
            List dataLinkFieldFields = this.dataModelService.getColumnModelDefinesByTable(dataLinkFieldTable.getID());
            for (ColumnModelDefine dataLinkFieldField : dataLinkFieldFields) {
                queryModelLink.getColumns().add(new NvwaQueryColumn(dataLinkFieldField));
            }
            INvwaUpdatableDataAccess updatableDataAccessLink = this.iNvwaDataAccessProvider.createUpdatableDataAccess(queryModelLink);
            INvwaDataUpdator iNvwaDataUpdatorLink = updatableDataAccessLink.openForUpdate(context);
            NvwaQueryModel queryModelType = new NvwaQueryModel();
            List annotationTypeFields = this.dataModelService.getColumnModelDefinesByTable(annotationTypeTable.getID());
            for (ColumnModelDefine annotationTypeField : annotationTypeFields) {
                queryModelType.getColumns().add(new NvwaQueryColumn(annotationTypeField));
            }
            INvwaUpdatableDataAccess updatableDataAccessType = this.iNvwaDataAccessProvider.createUpdatableDataAccess(queryModelType);
            INvwaDataUpdator iNvwaDataUpdatorType = updatableDataAccessType.openForUpdate(context);
            List<String> dimensionNames = this.getEntityDefines(formAnnotationBatchSaveInfo.getFormSchemeKey());
            Date updateDate = new Date();
            String userName = formAnnotationBatchSaveInfo.getUserName();
            String content = formAnnotationBatchSaveInfo.getContent();
            LinkData linkData = this.getLinkData(formAnnotationBatchSaveInfo.getDataLinkKey());
            int numOfInfo = 0;
            int numOfRel = 0;
            int numOfType = 0;
            List<AnnotationInfo> annotationInfos = formAnnotationBatchSaveInfo.getAnnotationInfos();
            for (AnnotationInfo annotationInfo : annotationInfos) {
                DimensionValueSet dimensionValueSet = annotationInfo.getDimensionCombination().toDimensionValueSet();
                HashMap<String, String> columMap = new HashMap<String, String>();
                for (String dimensionName : dimensionNames) {
                    columMap.put(dimensionName, dimensionValueSet.getValue(dimensionName).toString());
                }
                UUID annocationId = UUID.randomUUID();
                List columns = queryModel.getColumns();
                INvwaDataRow iNvwaDataRow = iNvwaDataUpdator.addInsertRow();
                block21: for (int i = 0; i < columns.size(); ++i) {
                    ColumnModelDefine filed = ((NvwaQueryColumn)columns.get(i)).getColumnModel();
                    switch (filed.getCode()) {
                        case "FMCEAN_ID": {
                            iNvwaDataRow.setValue(i, (Object)annocationId.toString());
                            continue block21;
                        }
                        case "FMCEAN_CONTENT": {
                            iNvwaDataRow.setValue(i, (Object)content);
                            continue block21;
                        }
                        case "FMCEAN_USER_ID": {
                            iNvwaDataRow.setValue(i, (Object)userName);
                            continue block21;
                        }
                        case "FMCEAN_UPDATE_DATE": {
                            iNvwaDataRow.setValue(i, (Object)updateDate);
                            continue block21;
                        }
                        default: {
                            String dimensionName = dimensionChanger.getDimensionName(filed);
                            if (!columMap.containsKey(dimensionName) || !filed.getCode().equals(filed.getCode())) continue block21;
                            iNvwaDataRow.setValue(i, columMap.get(dimensionName));
                        }
                    }
                }
                if (++numOfInfo >= 500) {
                    iNvwaDataUpdator.commitChanges(context);
                    numOfInfo = 0;
                }
                List columnsLink = queryModelLink.getColumns();
                List<RowInfo> rowInfos = annotationInfo.getRowInfos();
                for (RowInfo rowInfo : rowInfos) {
                    String tempId = UUID.randomUUID().toString();
                    INvwaDataRow iNvwaDataRowLink = iNvwaDataUpdatorLink.addInsertRow();
                    for (int i = 0; i < columnsLink.size(); ++i) {
                        String columnModelCode = ((NvwaQueryColumn)columnsLink.get(i)).getColumnModel().getCode();
                        if (columnModelCode.equals("FMCEANDF_ID")) {
                            iNvwaDataRowLink.setValue(i, (Object)tempId);
                            continue;
                        }
                        if (columnModelCode.equals("FMCEANDF_FMCEAN_ID")) {
                            iNvwaDataRowLink.setValue(i, (Object)annocationId.toString());
                            continue;
                        }
                        if (columnModelCode.equals("FMCEANDF_SHOW")) {
                            iNvwaDataRowLink.setValue(i, (Object)rowInfo.getShow());
                            continue;
                        }
                        if (columnModelCode.equals("FORM_KEY")) {
                            iNvwaDataRowLink.setValue(i, (Object)formAnnotationBatchSaveInfo.getFormKey());
                            continue;
                        }
                        if (columnModelCode.equals("REGION_KEY")) {
                            iNvwaDataRowLink.setValue(i, (Object)linkData.getRegionKey());
                            continue;
                        }
                        if (columnModelCode.equals("DATALINK_KEY")) {
                            iNvwaDataRowLink.setValue(i, (Object)formAnnotationBatchSaveInfo.getDataLinkKey());
                            continue;
                        }
                        if (columnModelCode.equals("FIELD_KEY")) {
                            iNvwaDataRowLink.setValue(i, (Object)formAnnotationBatchSaveInfo.getFieldKey());
                            continue;
                        }
                        if (!columnModelCode.equals("ROW_ID") || !StringUtils.isNotEmpty((String)rowInfo.getRowId())) continue;
                        iNvwaDataRowLink.setValue(i, (Object)rowInfo.getRowId());
                    }
                    if (++numOfRel < 500) continue;
                    iNvwaDataUpdatorLink.commitChanges(context);
                    numOfRel = 0;
                }
                List<String> annotationTypes = formAnnotationBatchSaveInfo.getTypes();
                if (null == annotationTypes || annotationTypes.isEmpty()) continue;
                List columnsType = queryModelType.getColumns();
                for (String annotationType : annotationTypes) {
                    INvwaDataRow iNvwaDataRowType = iNvwaDataUpdatorType.addInsertRow();
                    for (int i = 0; i < columnsType.size(); ++i) {
                        String columnModelCode = ((NvwaQueryColumn)columnsLink.get(i)).getColumnModel().getCode();
                        if (columnModelCode.equals("TYPE_FMCEAN_ID")) {
                            iNvwaDataRowType.setValue(i, (Object)annocationId.toString());
                            continue;
                        }
                        if (!columnModelCode.equals("TYPE_CODE")) continue;
                        iNvwaDataRowType.setValue(i, (Object)annotationType);
                    }
                    if (++numOfType < 500) continue;
                    iNvwaDataUpdatorType.commitChanges(context);
                    numOfType = 0;
                }
            }
            if (0 != numOfInfo) {
                iNvwaDataUpdator.commitChanges(context);
            }
            if (0 != numOfRel) {
                iNvwaDataUpdatorLink.commitChanges(context);
            }
            if (0 != numOfType) {
                iNvwaDataUpdatorType.commitChanges(context);
            }
            logHellperUtil.info(taskDefine.getKey(), null, "\u6279\u6ce8\u6279\u91cf\u6dfb\u52a0\u6210\u529f", "\u6279\u6ce8\u6279\u91cf\u6dfb\u52a0\u6210\u529f");
        }
        catch (Exception e) {
            logHellperUtil.error(taskDefine.getKey(), null, "\u6279\u6ce8\u6279\u91cf\u6dfb\u52a0\u5931\u8d25", PromptConsts.fileOprateSystemError(e.getMessage()));
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
    }

    private boolean checkParam(FormAnnotationBatchSaveInfo formAnnotationBatchSaveInfo) {
        return !StringUtils.isEmpty((String)formAnnotationBatchSaveInfo.getFormSchemeKey()) && !StringUtils.isEmpty((String)formAnnotationBatchSaveInfo.getFormKey()) && null != formAnnotationBatchSaveInfo.getAnnotationInfos() && !formAnnotationBatchSaveInfo.getAnnotationInfos().isEmpty() && !StringUtils.isEmpty((String)formAnnotationBatchSaveInfo.getDataLinkKey()) && !StringUtils.isEmpty((String)formAnnotationBatchSaveInfo.getFieldKey()) && !StringUtils.isEmpty((String)formAnnotationBatchSaveInfo.getUserName());
    }

    @Override
    public String removeFormAnnotation(FormAnnotationDeleteInfo formAnnotationDeleteInfo) {
        ArrayList<String> deleteIds = new ArrayList<String>();
        for (String id : formAnnotationDeleteInfo.getIds()) {
            if (!StringUtils.isNotEmpty((String)id)) continue;
            deleteIds.add(id);
        }
        if (deleteIds.isEmpty()) {
            throw new IllegalArgumentException("\u6279\u6ce8id\u4e0d\u80fd\u4e3a\u7a7a");
        }
        FormSchemeDefine formScheme = null;
        try {
            formScheme = this.runtimeView.getFormScheme(formAnnotationDeleteInfo.getFormSchemeKey());
        }
        catch (Exception e) {
            throw new NotFoundFormSchemeException(ExceptionCodeCost.NOTFOUND_FORMSHCEME_BYKEY, new String[]{formAnnotationDeleteInfo.getFormSchemeKey()});
        }
        TaskDefine taskDefine = this.runtimeView.queryTaskDefine(formScheme.getTaskKey());
        LogHellperUtil logHellperUtil = new LogHellperUtil(this.dataServiceLoggerFactory, MOUDLE);
        TableModelDefine annotationTable = this.dataModelService.getTableModelDefineByName("SYS_FMCEAN_" + formScheme.getFormSchemeCode());
        TableModelDefine dataLinkFieldTable = this.dataModelService.getTableModelDefineByName("SYS_FMCEANDF_" + formScheme.getFormSchemeCode());
        TableModelDefine annotationCommentTable = this.dataModelService.getTableModelDefineByName("SYS_FMCEANCO_" + formScheme.getFormSchemeCode());
        TableModelDefine annotationTypeTable = this.dataModelService.getTableModelDefineByName("SYS_FMCEANTYPE_" + formScheme.getFormSchemeCode());
        if (null == annotationTable || null == dataLinkFieldTable || null == annotationCommentTable || null == annotationTypeTable) {
            logHellperUtil.error(taskDefine.getKey(), null, "\u6279\u6ce8\u5220\u9664\u5931\u8d25", PromptConsts.fileOprateSystemError("\u6279\u6ce8\u76f8\u5173\u5b58\u50a8\u8868\u672a\u521b\u5efa"));
            throw new NotFoundTableDefineException(new String[]{"SYS_FMCEAN_" + formScheme.getFormSchemeCode(), "SYS_FMCEANDF_" + formScheme.getFormSchemeCode(), "SYS_FMCEANCO_" + formScheme.getFormSchemeCode(), "SYS_FMCEANTYPE_" + formScheme.getFormSchemeCode()});
        }
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        NvwaQueryModel annotationQueryModel = new NvwaQueryModel();
        List annotationFields = this.dataModelService.getColumnModelDefinesByTable(annotationTable.getID());
        for (ColumnModelDefine filed : annotationFields) {
            annotationQueryModel.getColumns().add(new NvwaQueryColumn(filed));
            if (!"FMCEAN_ID".equals(filed.getCode())) continue;
            annotationQueryModel.getColumnFilters().put(filed, deleteIds);
        }
        INvwaDataAccess readOnlyDataAccess = this.iNvwaDataAccessProvider.createReadOnlyDataAccess(annotationQueryModel);
        try {
            MemoryDataSet dataSet = readOnlyDataAccess.executeQuery(context);
            List columns = annotationQueryModel.getColumns();
            block6: for (int index = 0; index < dataSet.size(); ++index) {
                DataRow dataRow = dataSet.get(index);
                String createUser = "";
                for (int i = 0; i < columns.size(); ++i) {
                    if (!((NvwaQueryColumn)columns.get(i)).getColumnModel().getCode().equals("FMCEAN_USER_ID")) continue;
                    createUser = dataRow.getString(i);
                    String userName = formAnnotationDeleteInfo.getUserName();
                    if (userName.equals(createUser)) continue block6;
                    return "no authority";
                }
            }
            INvwaUpdatableDataAccess annotationUpdatable = this.iNvwaDataAccessProvider.createUpdatableDataAccess(annotationQueryModel);
            INvwaDataUpdator annotationUpdator = annotationUpdatable.openForUpdate(context);
            annotationUpdator.deleteAll();
            annotationUpdator.commitChanges(context);
            NvwaQueryModel dataLinkQueryModel = new NvwaQueryModel();
            List dataLinkFields = this.dataModelService.getColumnModelDefinesByTable(dataLinkFieldTable.getID());
            for (ColumnModelDefine filed : dataLinkFields) {
                dataLinkQueryModel.getColumns().add(new NvwaQueryColumn(filed));
                if (!"FMCEANDF_FMCEAN_ID".equals(filed.getCode())) continue;
                dataLinkQueryModel.getColumnFilters().put(filed, deleteIds);
            }
            INvwaUpdatableDataAccess dataLinkUpdatable = this.iNvwaDataAccessProvider.createUpdatableDataAccess(dataLinkQueryModel);
            INvwaDataUpdator dataLinkUpdator = dataLinkUpdatable.openForUpdate(context);
            dataLinkUpdator.deleteAll();
            dataLinkUpdator.commitChanges(context);
            NvwaQueryModel commentQueryModel = new NvwaQueryModel();
            List commentFields = this.dataModelService.getColumnModelDefinesByTable(annotationCommentTable.getID());
            for (ColumnModelDefine filed : commentFields) {
                commentQueryModel.getColumns().add(new NvwaQueryColumn(filed));
                if (!"FMCEANCO_FMCEAN_ID".equals(filed.getCode())) continue;
                commentQueryModel.getColumnFilters().put(filed, deleteIds);
            }
            INvwaUpdatableDataAccess commentUpdatable = this.iNvwaDataAccessProvider.createUpdatableDataAccess(commentQueryModel);
            INvwaDataUpdator commentUpdator = commentUpdatable.openForUpdate(context);
            commentUpdator.deleteAll();
            commentUpdator.commitChanges(context);
            NvwaQueryModel typeQueryModel = new NvwaQueryModel();
            List typeFields = this.dataModelService.getColumnModelDefinesByTable(annotationTypeTable.getID());
            for (ColumnModelDefine filed : typeFields) {
                typeQueryModel.getColumns().add(new NvwaQueryColumn(filed));
                if (!"TYPE_FMCEAN_ID".equals(filed.getCode())) continue;
                typeQueryModel.getColumnFilters().put(filed, deleteIds);
            }
            INvwaUpdatableDataAccess typeUpdatable = this.iNvwaDataAccessProvider.createUpdatableDataAccess(typeQueryModel);
            INvwaDataUpdator typeUpdator = typeUpdatable.openForUpdate(context);
            typeUpdator.deleteAll();
            typeUpdator.commitChanges(context);
            logHellperUtil.info(taskDefine.getKey(), null, "\u6279\u6ce8\u5220\u9664\u6210\u529f", "\u6279\u6ce8\u5220\u9664\u6210\u529f");
            return "success";
        }
        catch (Exception e) {
            logHellperUtil.error(taskDefine.getKey(), null, "\u6279\u6ce8\u5220\u9664\u5931\u8d25", PromptConsts.fileOprateSystemError(e.getMessage()));
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            return "error";
        }
    }

    @Override
    public CellAnnotationComment saveFormAnnotationComment(SaveFormAnnotationCommentInfo saveFormAnnotationCommentInfo) {
        CellAnnotationComment cellAnnotationComment = new CellAnnotationComment();
        cellAnnotationComment.setCanEditOrDelete(true);
        cellAnnotationComment.setContent(saveFormAnnotationCommentInfo.getContent());
        FormSchemeDefine formScheme = null;
        try {
            formScheme = this.runtimeView.getFormScheme(saveFormAnnotationCommentInfo.getFormSchemeKey());
        }
        catch (Exception e) {
            throw new NotFoundFormSchemeException(ExceptionCodeCost.NOTFOUND_FORMSHCEME_BYKEY, new String[]{saveFormAnnotationCommentInfo.getFormSchemeKey().toString()});
        }
        TaskDefine taskDefine = this.runtimeView.queryTaskDefine(formScheme.getTaskKey());
        LogHellperUtil logHellperUtil = new LogHellperUtil(this.dataServiceLoggerFactory, MOUDLE);
        TableModelDefine formAnnotationCommentTable = this.dataModelService.getTableModelDefineByName("SYS_FMCEANCO_" + formScheme.getFormSchemeCode());
        if (null == formAnnotationCommentTable) {
            throw new NotFoundTableDefineException(new String[]{"SYS_FMCEANCO_" + formScheme.getFormSchemeCode()});
        }
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        NvwaQueryModel queryModel = new NvwaQueryModel();
        List annotationCommentFields = this.dataModelService.getColumnModelDefinesByTable(formAnnotationCommentTable.getID());
        String userName = saveFormAnnotationCommentInfo.getUserName();
        String fullname = saveFormAnnotationCommentInfo.getUserFullname();
        Date updateDate = new Date();
        cellAnnotationComment.setDate(updateDate.getTime());
        cellAnnotationComment.setUsName(userName);
        cellAnnotationComment.setUserName(fullname);
        if (StringUtils.isEmpty((String)saveFormAnnotationCommentInfo.getRepyId())) {
            cellAnnotationComment.setRepyUserName(saveFormAnnotationCommentInfo.getRepyId());
        } else {
            HashMap<String, User> userNameMap = new HashMap<String, User>();
            String name = this.findUserByUserName(userNameMap, saveFormAnnotationCommentInfo.getRepyId());
            cellAnnotationComment.setRepyUserName(name);
        }
        for (ColumnModelDefine filed : annotationCommentFields) {
            queryModel.getColumns().add(new NvwaQueryColumn(filed));
        }
        try {
            INvwaUpdatableDataAccess updatableDataAccess = this.iNvwaDataAccessProvider.createUpdatableDataAccess(queryModel);
            List columns = queryModel.getColumns();
            INvwaDataUpdator iNvwaDataUpdator = updatableDataAccess.openForUpdate(context);
            INvwaDataRow iNvwaDataRow = iNvwaDataUpdator.addInsertRow();
            block21: for (int i = 0; i < columns.size(); ++i) {
                String columnModelCode;
                switch (columnModelCode = ((NvwaQueryColumn)columns.get(i)).getColumnModel().getCode()) {
                    case "FMCEANCO_CONTENT": {
                        iNvwaDataRow.setValue(i, (Object)saveFormAnnotationCommentInfo.getContent());
                        continue block21;
                    }
                    case "FMCEANCO_USER_ID": {
                        iNvwaDataRow.setValue(i, (Object)userName);
                        continue block21;
                    }
                    case "FMCEANCO_UPDATE_DATE": {
                        iNvwaDataRow.setValue(i, (Object)updateDate);
                        continue block21;
                    }
                    case "FMCEANCO_FMCEAN_ID": {
                        iNvwaDataRow.setValue(i, (Object)saveFormAnnotationCommentInfo.getAnnotationId());
                        continue block21;
                    }
                    case "FMCEANCO_ID": {
                        UUID randomUUID = UUID.randomUUID();
                        iNvwaDataRow.setValue(i, (Object)randomUUID.toString());
                        cellAnnotationComment.setId(randomUUID.toString());
                        continue block21;
                    }
                    case "FMCEANCO_REPY_USER_ID": {
                        iNvwaDataRow.setValue(i, (Object)saveFormAnnotationCommentInfo.getRepyId());
                    }
                }
            }
            iNvwaDataUpdator.commitChanges(context);
            logHellperUtil.info(taskDefine.getKey(), null, "\u8bc4\u8bba\u6dfb\u52a0\u6210\u529f", "\u8bc4\u8bba\u6dfb\u52a0\u6210\u529f");
            return cellAnnotationComment;
        }
        catch (Exception e) {
            logHellperUtil.error(taskDefine.getKey(), null, "\u8bc4\u8bba\u6dfb\u52a0\u5931\u8d25", PromptConsts.fileOprateSystemError(e.getMessage()));
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            return null;
        }
    }

    @Override
    public CellAnnotationComment updateFormAnnotationComment(UpdateFormAnnotationCommentInfo updateFormAnnotationCommentInfo) {
        CellAnnotationComment cellAnnotationComment = new CellAnnotationComment();
        cellAnnotationComment.setCanEditOrDelete(true);
        ArrayList<String> updateIds = new ArrayList<String>();
        updateIds.add(updateFormAnnotationCommentInfo.getId());
        cellAnnotationComment.setId(updateFormAnnotationCommentInfo.getId());
        cellAnnotationComment.setContent(updateFormAnnotationCommentInfo.getContent());
        FormSchemeDefine formScheme = null;
        try {
            formScheme = this.runtimeView.getFormScheme(updateFormAnnotationCommentInfo.getFormSchemeKey());
        }
        catch (Exception e) {
            throw new NotFoundFormSchemeException(ExceptionCodeCost.NOTFOUND_FORMSHCEME_BYKEY, new String[]{updateFormAnnotationCommentInfo.getFormSchemeKey().toString()});
        }
        TaskDefine taskDefine = this.runtimeView.queryTaskDefine(formScheme.getTaskKey());
        LogHellperUtil logHellperUtil = new LogHellperUtil(this.dataServiceLoggerFactory, MOUDLE);
        TableModelDefine formAnnotationCommentTable = this.dataModelService.getTableModelDefineByName("SYS_FMCEANCO_" + formScheme.getFormSchemeCode());
        if (null == formAnnotationCommentTable) {
            throw new NotFoundTableDefineException(new String[]{"SYS_FMCEANCO_" + formScheme.getFormSchemeCode()});
        }
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        NvwaQueryModel queryModel = new NvwaQueryModel();
        List annotationCommentFields = this.dataModelService.getColumnModelDefinesByTable(formAnnotationCommentTable.getID());
        String userName = updateFormAnnotationCommentInfo.getUserName();
        String fullname = updateFormAnnotationCommentInfo.getUserFullname();
        Date updateDate = new Date();
        cellAnnotationComment.setDate(updateDate.getTime());
        cellAnnotationComment.setUsName(userName);
        cellAnnotationComment.setUserName(fullname);
        if (StringUtils.isEmpty((String)updateFormAnnotationCommentInfo.getRepyId())) {
            cellAnnotationComment.setRepyUserName(updateFormAnnotationCommentInfo.getRepyId());
        } else {
            HashMap<String, User> userNameMap = new HashMap<String, User>();
            String name = this.findUserByUserName(userNameMap, updateFormAnnotationCommentInfo.getRepyId());
            cellAnnotationComment.setRepyUserName(name);
        }
        for (ColumnModelDefine filed : annotationCommentFields) {
            if ("FMCEANCO_CONTENT".equals(filed.getCode()) || "FMCEANCO_USER_ID".equals(filed.getCode()) || "FMCEANCO_UPDATE_DATE".equals(filed.getCode())) {
                queryModel.getColumns().add(new NvwaQueryColumn(filed));
                continue;
            }
            if (!"FMCEANCO_ID".equals(filed.getCode())) continue;
            queryModel.getColumnFilters().put(filed, updateIds);
        }
        INvwaUpdatableDataAccess updatableDataAccess = this.iNvwaDataAccessProvider.createUpdatableDataAccess(queryModel);
        List columns = queryModel.getColumns();
        try {
            INvwaUpdatableDataSet iNvwaDataRows = updatableDataAccess.executeQueryForUpdate(context);
            int totalCount = iNvwaDataRows.size();
            for (int index = 0; index < totalCount; ++index) {
                INvwaDataRow modifiedRow = iNvwaDataRows.getRow(index);
                block16: for (int i = 0; i < columns.size(); ++i) {
                    String columnModelCode;
                    switch (columnModelCode = ((NvwaQueryColumn)columns.get(i)).getColumnModel().getCode()) {
                        case "FMCEANCO_CONTENT": {
                            modifiedRow.setValue(i, (Object)updateFormAnnotationCommentInfo.getContent());
                            continue block16;
                        }
                        case "FMCEANCO_USER_ID": {
                            String oldUserId = (String)modifiedRow.getValue(i);
                            if (!userName.equals(oldUserId)) {
                                return null;
                            }
                            modifiedRow.setValue(i, (Object)userName);
                            continue block16;
                        }
                        case "FMCEANCO_UPDATE_DATE": {
                            modifiedRow.setValue(i, (Object)updateDate);
                        }
                    }
                }
            }
            iNvwaDataRows.commitChanges(context);
            logHellperUtil.info(taskDefine.getKey(), null, "\u8bc4\u8bba\u4fee\u6539\u6210\u529f", "\u8bc4\u8bba\u4fee\u6539\u6210\u529f");
            return cellAnnotationComment;
        }
        catch (Exception e) {
            logHellperUtil.error(taskDefine.getKey(), null, "\u8bc4\u8bba\u4fee\u6539\u5931\u8d25", PromptConsts.fileOprateSystemError(e.getMessage()));
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            return null;
        }
    }

    @Override
    public String removeFormAnnotationComment(FormAnnotationDeleteInfo formAnnotationDeleteInfo) {
        ArrayList<String> deleteIds = new ArrayList<String>();
        for (String id : formAnnotationDeleteInfo.getIds()) {
            if (null == id || "".equals(id)) continue;
            deleteIds.add(id);
        }
        if (deleteIds.isEmpty()) {
            return "error";
        }
        FormSchemeDefine formScheme = null;
        try {
            formScheme = this.runtimeView.getFormScheme(formAnnotationDeleteInfo.getFormSchemeKey());
        }
        catch (Exception e) {
            throw new NotFoundFormSchemeException(ExceptionCodeCost.NOTFOUND_FORMSHCEME_BYKEY, new String[]{formAnnotationDeleteInfo.getFormSchemeKey().toString()});
        }
        TaskDefine taskDefine = this.runtimeView.queryTaskDefine(formScheme.getTaskKey());
        LogHellperUtil logHellperUtil = new LogHellperUtil(this.dataServiceLoggerFactory, MOUDLE);
        TableModelDefine formAnnotationCommnetTable = this.dataModelService.getTableModelDefineByName("SYS_FMCEANCO_" + formScheme.getFormSchemeCode());
        if (null == formAnnotationCommnetTable) {
            throw new NotFoundTableDefineException(new String[]{"SYS_FMCEANCO_" + formScheme.getFormSchemeCode()});
        }
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        NvwaQueryModel queryModel = new NvwaQueryModel();
        List annotationCommentFields = this.dataModelService.getColumnModelDefinesByTable(formAnnotationCommnetTable.getID());
        ColumnModelDefine createUserField = null;
        for (ColumnModelDefine filed : annotationCommentFields) {
            queryModel.getColumns().add(new NvwaQueryColumn(filed));
            if ("FMCEANCO_ID".equals(filed.getCode())) {
                queryModel.getColumnFilters().put(filed, deleteIds);
                continue;
            }
            if (!"FMCEANCO_USER_ID".equals(filed.getCode())) continue;
            createUserField = filed;
        }
        DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger("SYS_FMCEANCO_" + formScheme.getFormSchemeCode());
        ColumnModelDefine column = dimensionChanger.getColumn("FMCEANCO_ID");
        queryModel.getColumnFilters().put(column, deleteIds);
        INvwaUpdatableDataAccess updatableDataAccess = this.iNvwaDataAccessProvider.createUpdatableDataAccess(queryModel);
        String nowUserName = formAnnotationDeleteInfo.getUserName();
        try {
            INvwaUpdatableDataSet iNvwaDataRows = updatableDataAccess.executeQueryForUpdate(context);
            int totalCount = iNvwaDataRows.size();
            for (int index = 0; index < totalCount; ++index) {
                INvwaDataRow item = iNvwaDataRows.getRow(index);
                String createUser = (String)item.getValue(createUserField);
                if (nowUserName.equals(createUser)) continue;
                return "no authority";
            }
            iNvwaDataRows.deleteAll();
            iNvwaDataRows.commitChanges(context);
            logHellperUtil.info(taskDefine.getKey(), null, "\u8bc4\u8bba\u5220\u9664\u6210\u529f", "\u8bc4\u8bba\u5220\u9664\u6210\u529f");
            return "success";
        }
        catch (Exception e) {
            logHellperUtil.error(taskDefine.getKey(), null, "\u8bc4\u8bba\u5220\u9664\u5931\u8d25", PromptConsts.fileOprateSystemError(e.getMessage()));
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            return "error";
        }
    }
}

