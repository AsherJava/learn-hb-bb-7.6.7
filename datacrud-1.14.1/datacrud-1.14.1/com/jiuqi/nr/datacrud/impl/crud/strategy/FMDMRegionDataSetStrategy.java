/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.nr.data.access.param.IAccessResult
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.definition.common.DataLinkType
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.entity.engine.data.AbstractData
 *  com.jiuqi.nr.entity.engine.data.DateTimeData
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.fmdm.FMDMAttributeDTO
 *  com.jiuqi.nr.fmdm.FMDMDataDTO
 *  com.jiuqi.nr.fmdm.IFMDMAttribute
 *  com.jiuqi.nr.fmdm.IFMDMAttributeService
 *  com.jiuqi.nr.fmdm.IFMDMData
 *  com.jiuqi.nr.fmdm.IFMDMDataService
 *  com.jiuqi.nr.fmdm.IFMDMUpdateResult
 *  com.jiuqi.nr.fmdm.exception.FMDMDataException
 *  com.jiuqi.nr.fmdm.exception.FMDMNoWriteAccessException
 *  com.jiuqi.nr.fmdm.exception.FMDMQueryException
 *  com.jiuqi.nr.fmdm.exception.FMDMUpdateException
 *  com.jiuqi.nr.fmdm.internal.check.CheckNodeInfo
 *  com.jiuqi.nr.fmdm.internal.check.FMDMCheckFailNodeInfo
 *  com.jiuqi.nr.fmdm.internal.check.FMDMCheckResult
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.datacrud.impl.crud.strategy;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.nr.data.access.param.IAccessResult;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.datacrud.IClearInfo;
import com.jiuqi.nr.datacrud.IDataValue;
import com.jiuqi.nr.datacrud.IQueryInfo;
import com.jiuqi.nr.datacrud.IRegionDataSet;
import com.jiuqi.nr.datacrud.IRowData;
import com.jiuqi.nr.datacrud.ISaveInfo;
import com.jiuqi.nr.datacrud.Measure;
import com.jiuqi.nr.datacrud.MultiDimensionalDataSet;
import com.jiuqi.nr.datacrud.ParamRelation;
import com.jiuqi.nr.datacrud.ReturnRes;
import com.jiuqi.nr.datacrud.SaveData;
import com.jiuqi.nr.datacrud.SaveResItem;
import com.jiuqi.nr.datacrud.SaveReturnRes;
import com.jiuqi.nr.datacrud.SaveRowData;
import com.jiuqi.nr.datacrud.impl.DataValue;
import com.jiuqi.nr.datacrud.impl.MetaData;
import com.jiuqi.nr.datacrud.impl.MultiDimDataSet;
import com.jiuqi.nr.datacrud.impl.RegionRelation;
import com.jiuqi.nr.datacrud.impl.RegionRelationFactory;
import com.jiuqi.nr.datacrud.impl.RowData;
import com.jiuqi.nr.datacrud.impl.crud.IRegionDataSetStrategy;
import com.jiuqi.nr.datacrud.impl.crud.IRegionDataSetUpdatableStrategy;
import com.jiuqi.nr.datacrud.impl.loggger.DataServiceLogWrapper;
import com.jiuqi.nr.datacrud.impl.loggger.DataServiceLogger;
import com.jiuqi.nr.datacrud.impl.measure.MeasureService;
import com.jiuqi.nr.datacrud.impl.out.CrudException;
import com.jiuqi.nr.datacrud.impl.out.CrudOperateException;
import com.jiuqi.nr.datacrud.impl.out.CrudSaveException;
import com.jiuqi.nr.datacrud.impl.service.DataEngineService;
import com.jiuqi.nr.datacrud.spi.IDataValueProcessor;
import com.jiuqi.nr.datacrud.spi.IExecutorContextFactory;
import com.jiuqi.nr.datacrud.util.DataValueProcessorFactory;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.entity.engine.data.AbstractData;
import com.jiuqi.nr.entity.engine.data.DateTimeData;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.fmdm.FMDMAttributeDTO;
import com.jiuqi.nr.fmdm.FMDMDataDTO;
import com.jiuqi.nr.fmdm.IFMDMAttribute;
import com.jiuqi.nr.fmdm.IFMDMAttributeService;
import com.jiuqi.nr.fmdm.IFMDMData;
import com.jiuqi.nr.fmdm.IFMDMDataService;
import com.jiuqi.nr.fmdm.IFMDMUpdateResult;
import com.jiuqi.nr.fmdm.exception.FMDMDataException;
import com.jiuqi.nr.fmdm.exception.FMDMNoWriteAccessException;
import com.jiuqi.nr.fmdm.exception.FMDMQueryException;
import com.jiuqi.nr.fmdm.exception.FMDMUpdateException;
import com.jiuqi.nr.fmdm.internal.check.CheckNodeInfo;
import com.jiuqi.nr.fmdm.internal.check.FMDMCheckFailNodeInfo;
import com.jiuqi.nr.fmdm.internal.check.FMDMCheckResult;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class FMDMRegionDataSetStrategy
implements IRegionDataSetStrategy,
IRegionDataSetUpdatableStrategy {
    private final Logger logger = LoggerFactory.getLogger(FMDMRegionDataSetStrategy.class);
    @Autowired
    private DataEngineService dataEngineService;
    @Autowired
    private RegionRelationFactory regionRelationFactory;
    @Autowired
    private IFMDMDataService fMDMDataService;
    @Autowired
    private IFMDMAttributeService ifmdmAttributeService;
    @Autowired
    private IEntityMetaService metaService;
    @Autowired
    private DataServiceLogWrapper dataServiceLogWrapper;
    @Autowired
    private IExecutorContextFactory executorContextFactory;
    @Autowired
    private MeasureService measureService;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private DataValueProcessorFactory dataValueProcessorFactory;
    private static final String DEFAULT_PARENT_CODE = "-";
    private static final String BBLX_VALUE1 = "7";
    private static final String BBLX_VALUE2 = "H";

    @Override
    public IRegionDataSet queryData(IQueryInfo queryInfo, RegionRelation relation) {
        return this.queryMultiDimensionalData(queryInfo, relation);
    }

    @Override
    public int queryDataCount(IQueryInfo queryInfo, RegionRelation relation) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int queryDataIndex(IQueryInfo queryInfo, RegionRelation relation, String bizKeyOrder) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IRegionDataSet regionDataLocate(IQueryInfo queryInfo, RegionRelation relation, String bizKeyOrder, int offset) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int queryDataIndex(IQueryInfo queryInfo, RegionRelation relation, DimensionCombination rowDim) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IRegionDataSet regionDataLocate(IQueryInfo queryInfo, RegionRelation relation, DimensionCombination rowDim, int offset) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ReturnRes clearData(IClearInfo clearInfo, RegionRelation relation) throws CrudOperateException {
        DataServiceLogger crudLogger = null;
        if (relation == null) {
            relation = this.regionRelationFactory.getRegionRelation(clearInfo.getRegionKey());
        }
        FMDMDataDTO fmdmDataDTO = new FMDMDataDTO();
        DimensionCollection dimensionCollection = clearInfo.getDimensionCollection();
        try {
            DimensionValueSet dimensionValueSet;
            if (dimensionCollection == null) {
                crudLogger = this.dataServiceLogWrapper.getCrudLogger(relation, clearInfo.getDimensionCombination());
                dimensionValueSet = clearInfo.getDimensionCombination().toDimensionValueSet();
            } else {
                crudLogger = this.dataServiceLogWrapper.getCrudLogger(relation, dimensionCollection);
                dimensionValueSet = DimensionValueSetUtil.mergeDimensionValueSet((DimensionCollection)dimensionCollection);
            }
            fmdmDataDTO.setIgnoreAccess(false);
            crudLogger.beginClearData("\u5f00\u59cb\u5220\u9664\u5c01\u9762\u4ee3\u7801\u6570\u636e");
            fmdmDataDTO.setDimensionValueSet(dimensionValueSet);
            fmdmDataDTO.setFormSchemeKey(relation.getFormSchemeDefine().getKey());
            this.fMDMDataService.delete(fmdmDataDTO);
        }
        catch (FMDMNoWriteAccessException e) {
            this.logger.error("\u5220\u9664\u5c01\u9762\u4ee3\u7801\u6570\u636e\u5931\u8d25\uff0c\u65e0\u7ba1\u7406\u6743\u9650", e);
            crudLogger.clearFail("\u5220\u9664\u5c01\u9762\u4ee3\u7801\u6570\u636e\u5931\u8d25\uff0c\u65e0\u7ba1\u7406\u6743\u9650");
            throw new CrudOperateException(1504, e.getMessage(), e);
        }
        catch (FMDMUpdateException e) {
            this.logger.error("\u5220\u9664\u5c01\u9762\u4ee3\u7801\u6570\u636e\u62a5\u9519", e);
            crudLogger.clearFail("\u5220\u9664\u5c01\u9762\u4ee3\u7801\u6570\u636e\u62a5\u9519");
            throw new CrudOperateException(1503, "\u5220\u9664\u5c01\u9762\u4ee3\u7801\u62a5\u9519", e);
        }
        crudLogger.clearSuccess("\u5220\u9664\u5c01\u9762\u4ee3\u7801\u6210\u529f");
        return ReturnRes.ok("\u5220\u9664\u5c01\u9762\u4ee3\u7801\u6210\u529f");
    }

    @Override
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public SaveReturnRes saveData(ISaveInfo saveInfo, RegionRelation relation) throws CrudOperateException {
        IFMDMUpdateResult ifmdmUpdateResult;
        SaveData saveData = saveInfo.getSaveData();
        List<String> links = saveData.getLinks();
        List<SaveRowData> rows = saveData.getAllRows();
        DataServiceLogger crudLogger = this.dataServiceLogWrapper.getCrudLogger(relation, saveInfo.getDimensionCombination());
        if (CollectionUtils.isEmpty(rows)) {
            crudLogger.saveSuccess("\u65e0\u6570\u636e\u9700\u8981\u4fdd\u5b58");
            return SaveReturnRes.ok("\u65e0\u6570\u636e\u9700\u8981\u4fdd\u5b58");
        }
        SaveRowData saveRowData = rows.get(0);
        FMDMDataDTO fmdmDataDTO = this.buildFmdmDataDto(relation, links, saveRowData);
        SaveReturnRes saveReturnRes = new SaveReturnRes();
        if (saveRowData.getType() == 1) {
            try {
                fmdmDataDTO.setIgnoreAccess(false);
                crudLogger.info("\u5f00\u59cb\u65b0\u589e\u5c01\u9762\u4ee3\u7801", "\u5f00\u59cb\u65b0\u589e\u5c01\u9762\u4ee3\u7801");
                ifmdmUpdateResult = this.fMDMDataService.add(fmdmDataDTO);
            }
            catch (FMDMNoWriteAccessException e) {
                this.logger.error("\u5220\u9664\u5c01\u9762\u4ee3\u7801\u6570\u636e\u5931\u8d25\uff0c\u65e0\u7ba1\u7406\u6743\u9650", e);
                crudLogger.clearFail("\u5220\u9664\u5c01\u9762\u4ee3\u7801\u6570\u636e\u5931\u8d25\uff0c\u65e0\u7ba1\u7406\u6743\u9650");
                throw new CrudOperateException(1504, e.getMessage(), e);
            }
            catch (FMDMUpdateException e) {
                crudLogger.saveFail("\u65b0\u589e\u5c01\u9762\u4ee3\u7801\u5931\u8d25");
                this.logger.error("\u65b0\u589e\u5c01\u9762\u4ee3\u7801\u62a5\u9519" + e.getMessage(), e);
                throw new CrudOperateException(1501, "\u65b0\u589e\u5c01\u9762\u4ee3\u7801\u62a5\u9519", e);
            }
        }
        try {
            crudLogger.info("\u5f00\u59cb\u66f4\u65b0\u5c01\u9762\u4ee3\u7801", "\u5f00\u59cb\u66f4\u65b0\u5c01\u9762\u4ee3\u7801");
            ifmdmUpdateResult = this.fMDMDataService.update(fmdmDataDTO);
        }
        catch (FMDMUpdateException e) {
            crudLogger.saveFail("\u66f4\u65b0\u5c01\u9762\u4ee3\u7801\u5931\u8d25");
            this.logger.error("\u66f4\u65b0\u5c01\u9762\u4ee3\u7801\u62a5\u9519", e);
            throw new CrudOperateException(1502, "\u66f4\u65b0\u5c01\u9762\u4ee3\u7801\u62a5\u9519", e);
        }
        catch (FMDMDataException error) {
            this.logger.error("\u66f4\u65b0\u5c01\u9762\u4ee3\u7801\u5931\u8d25", error);
            FMDMAttributeDTO fmdmAttributeDTO = new FMDMAttributeDTO();
            fmdmAttributeDTO.setEntityId(relation.getTaskDefine().getDw());
            fmdmAttributeDTO.setFormSchemeKey(relation.getFormSchemeDefine().getKey());
            IFMDMAttribute parentField = this.ifmdmAttributeService.getFMDMParentField(fmdmAttributeDTO);
            MetaData meta = relation.getMetaDataByFieldKey(parentField.getCode());
            List<SaveResItem> saveResItems = saveReturnRes.getSaveResItems();
            SaveResItem saveResItem = new SaveResItem();
            saveResItem.setLevel(-1);
            saveResItem.setLinkKey(meta.getLinkKey());
            saveResItem.setMessage("\u6307\u6807\uff1a\u7236\u8282\u70b9\u4e0d\u5141\u8bb8\u9009\u62e9\u81ea\u5df1\u6216\u81ea\u5df1\u7684\u4e0b\u7ea7");
            saveResItems.add(saveResItem);
            crudLogger.saveFail("\u6307\u6807\uff1a\u7236\u8282\u70b9\u4e0d\u5141\u8bb8\u9009\u62e9\u81ea\u5df1\u6216\u81ea\u5df1\u7684\u4e0b\u7ea7");
            throw new CrudSaveException(1502, "\u66f4\u65b0\u5c01\u9762\u4ee3\u7801\u62a5\u9519", saveResItems);
        }
        List updateKeys = ifmdmUpdateResult.getUpdateKeys();
        if (!CollectionUtils.isEmpty(updateKeys)) {
            saveReturnRes.setData((String)updateKeys.get(0));
        }
        FMDMCheckResult fmdmCheckResult = ifmdmUpdateResult.getFMDMCheckResult();
        List results = fmdmCheckResult.getResults();
        List<SaveResItem> saveResItems = saveReturnRes.getSaveResItems();
        for (FMDMCheckFailNodeInfo result : results) {
            MetaData meta = relation.getMetaDataByFieldKey(result.getFieldCode());
            if (meta == null) {
                for (MetaData item : relation.getMetaData()) {
                    if (!result.getFieldCode().equals(item.getCode())) continue;
                    meta = item;
                    break;
                }
            }
            if (meta == null) continue;
            List nodes = result.getNodes();
            for (CheckNodeInfo node : nodes) {
                SaveResItem saveResItem = new SaveResItem();
                saveResItem.setLinkKey(meta.getLinkKey());
                saveResItem.setLevel(node.getType());
                saveResItem.setMessage(node.getContent());
                saveResItems.add(saveResItem);
            }
        }
        if (!saveResItems.isEmpty()) {
            StringBuilder msg = new StringBuilder();
            for (SaveResItem saveResItem : saveResItems) {
                msg.append(saveResItem.getMessage()).append(";");
            }
            crudLogger.saveFail(msg.toString());
            throw new CrudSaveException(1502, "\u66f4\u65b0\u5c01\u9762\u4ee3\u7801\u62a5\u9519", saveResItems);
        }
        if (saveRowData.getType() == 1) {
            crudLogger.saveSuccess("\u65b0\u589e\u5c01\u9762\u4ee3\u7801\u5b8c\u6210");
        } else {
            crudLogger.info("\u66f4\u65b0\u5c01\u9762\u4ee3\u7801\u5b8c\u6210", "\u66f4\u65b0\u5c01\u9762\u4ee3\u7801\u5b8c\u6210");
        }
        return saveReturnRes;
    }

    private FMDMDataDTO buildFmdmDataDto(RegionRelation relation, List<String> links, SaveRowData saveRowData) {
        FMDMDataDTO fmdmDataDTO = new FMDMDataDTO();
        fmdmDataDTO.setDimensionValueSet(saveRowData.getCombination().toDimensionValueSet());
        fmdmDataDTO.setFormSchemeKey(relation.getFormSchemeDefine().getKey());
        fmdmDataDTO.setFormulaSchemeKey(relation.getFormulaSchemeKey());
        FMDMAttributeDTO fmdmAttributeDTO = new FMDMAttributeDTO();
        fmdmAttributeDTO.setEntityId(relation.getTaskDefine().getDw());
        fmdmAttributeDTO.setFormSchemeKey(relation.getFormSchemeDefine().getKey());
        IEntityModel entityModel = this.metaService.getEntityModel(relation.getTaskDefine().getDw());
        IFMDMAttribute fmdmParentField = null;
        try {
            IEntityDefine iEntityDefine = this.metaService.queryEntity(relation.getTaskDefine().getDw());
            if (iEntityDefine.isTree() != null && iEntityDefine.isTree().booleanValue()) {
                fmdmParentField = this.ifmdmAttributeService.getFMDMParentField(fmdmAttributeDTO);
            }
        }
        catch (FMDMQueryException e) {
            this.logger.error("\u5c01\u9762\u4ee3\u7801\u7236\u8282\u70b9\u67e5\u8be2\u62a5\u9519" + e.getMessage(), e);
            throw new CrudException(4301, "\u5c01\u9762\u4ee3\u7801\u7236\u8282\u70b9\u67e5\u8be2\u62a5\u9519");
        }
        IEntityAttribute bblxField = entityModel.getBblxField();
        com.jiuqi.np.dataengine.data.AbstractData[] linkValues = saveRowData.getLinkValues();
        block6: for (int i = 0; i < links.size(); ++i) {
            String link = links.get(i);
            MetaData metaData = relation.getMetaDataByLink(link);
            if (metaData == null) {
                fmdmDataDTO.setEntityValue(link, linkValues[i].getAsObject());
                continue;
            }
            DataLinkDefine dataLinkDefine = metaData.getDataLinkDefine();
            if (dataLinkDefine == null || dataLinkDefine.getType() == DataLinkType.DATA_LINK_TYPE_FORMULA) continue;
            boolean isEntityData = dataLinkDefine.getType() == DataLinkType.DATA_LINK_TYPE_FMDM;
            boolean isInfoData = dataLinkDefine.getType() == DataLinkType.DATA_LINK_TYPE_INFO;
            String linkExpression = dataLinkDefine.getLinkExpression();
            fmdmAttributeDTO.setZBKey(linkExpression);
            fmdmAttributeDTO.setAttributeCode(linkExpression);
            IFMDMAttribute fmdmAttribute = this.ifmdmAttributeService.queryByZbKey(fmdmAttributeDTO);
            if (fmdmAttribute == null) continue;
            String zbCode = fmdmAttribute.getCode();
            switch (fmdmAttribute.getColumnType()) {
                case BOOLEAN: {
                    com.jiuqi.np.dataengine.data.AbstractData linkValue = linkValues[i];
                    if (isEntityData) {
                        fmdmDataDTO.setEntityValue(zbCode, (Object)linkValue.getAsBool());
                        continue block6;
                    }
                    if (isInfoData) {
                        fmdmDataDTO.setInfoValue(zbCode, (Object)linkValue.getAsBool());
                        continue block6;
                    }
                    fmdmDataDTO.setDataValue(zbCode, (Object)linkValue.getAsBool());
                    continue block6;
                }
                case DATETIME: {
                    com.jiuqi.np.dataengine.data.AbstractData linkValue = linkValues[i];
                    if (isEntityData) {
                        fmdmDataDTO.setEntityValue(zbCode, (Object)linkValue.getAsDateObj());
                        continue block6;
                    }
                    if (isInfoData) {
                        fmdmDataDTO.setInfoValue(zbCode, (Object)linkValue.getAsDateObj());
                        continue block6;
                    }
                    fmdmDataDTO.setDataValue(zbCode, (Object)linkValue.getAsDateObj());
                    continue block6;
                }
                default: {
                    com.jiuqi.np.dataengine.data.AbstractData linkValue = linkValues[i];
                    Object object = linkValue.getAsObject();
                    if (fmdmParentField != null && fmdmParentField.getCode().equals(zbCode) && (object == null || linkValue.isNull || "null".equals(linkValue.getAsString()))) {
                        object = DEFAULT_PARENT_CODE;
                    }
                    if (isEntityData) {
                        fmdmDataDTO.setEntityValue(zbCode, object);
                    } else if (isInfoData) {
                        fmdmDataDTO.setInfoValue(zbCode, object);
                    } else {
                        fmdmDataDTO.setDataValue(zbCode, object);
                    }
                    if (bblxField == null || !bblxField.getCode().equals(zbCode) || !BBLX_VALUE1.equals(object) && !BBLX_VALUE2.equals(object)) continue block6;
                    fmdmDataDTO.setIgnoreCheck(true);
                }
            }
        }
        return fmdmDataDTO;
    }

    @Override
    public MultiDimensionalDataSet queryMultiDimensionalData(IQueryInfo queryInfo, RegionRelation relation) {
        List list;
        if (relation == null) {
            relation = this.regionRelationFactory.getRegionRelation(queryInfo.getRegionKey());
        }
        List<MetaData> metaData = relation.getMetaData(null);
        MultiDimDataSet fmSet = new MultiDimDataSet(metaData, Collections.emptyList());
        fmSet.setRegionKey(queryInfo.getRegionKey());
        DimensionCollection dimensionCollection = queryInfo.getDimensionCollection();
        fmSet.setDimensionCollection(dimensionCollection);
        List dims = dimensionCollection.getDimensionCombinations();
        fmSet.setDims(new HashSet<DimensionCombination>(dims));
        FormSchemeDefine formSchemeDefine = relation.getFormSchemeDefine();
        FMDMDataDTO fmdmDataDTO = new FMDMDataDTO();
        DimensionValueSet masterKeys = DimensionValueSetUtil.mergeDimensionValueSet((DimensionCollection)dimensionCollection);
        fmdmDataDTO.setFormSchemeKey(formSchemeDefine.getKey());
        ExecutorContext context = this.executorContextFactory.getExecutorContext((ParamRelation)relation, masterKeys);
        IDataValueProcessor dataValueProcessor = this.measureBalance(queryInfo, relation);
        fmdmDataDTO.setContext((IContext)context);
        fmdmDataDTO.setDataMasking(queryInfo.isDesensitized());
        try {
            list = this.fMDMDataService.list(fmdmDataDTO, dimensionCollection);
        }
        catch (FMDMQueryException e) {
            this.logger.error("\u5c01\u9762\u4ee3\u7801\u6570\u636e\u67e5\u8be2\u51fa\u9519" + e.getMessage(), e);
            return fmSet;
        }
        if (!CollectionUtils.isEmpty(list)) {
            IFMDMAttribute fmdmParentField;
            fmSet.setRows(new ArrayList<IRowData>());
            try {
                FMDMAttributeDTO fmdmAttributeDTO = new FMDMAttributeDTO();
                fmdmAttributeDTO.setEntityId(relation.getTaskDefine().getDw());
                fmdmAttributeDTO.setFormSchemeKey(relation.getFormSchemeDefine().getKey());
                fmdmParentField = this.ifmdmAttributeService.getFMDMParentField(fmdmAttributeDTO);
            }
            catch (FMDMQueryException e) {
                this.logger.error("\u5c01\u9762\u4ee3\u7801\u7236\u8282\u70b9\u67e5\u8be2\u62a5\u9519" + e.getMessage(), e);
                throw new CrudException(4301, "\u5c01\u9762\u4ee3\u7801\u7236\u8282\u70b9\u67e5\u8be2\u62a5\u9519");
            }
            for (IFMDMData ifmdmData : list) {
                RowData rowData = new RowData();
                DimensionValueSet masterKey = ifmdmData.getMasterKey();
                if (masterKey != null) {
                    DimensionCombinationBuilder builder = new DimensionCombinationBuilder(masterKey);
                    DimensionCombination combination = builder.getCombination();
                    rowData.setMasterDimension(combination);
                    rowData.setDimension(combination);
                }
                fmSet.getRowData().add(rowData);
                ArrayList<IDataValue> values = new ArrayList<IDataValue>(metaData.size());
                rowData.setDataValues(values);
                for (MetaData metaDatum : metaData) {
                    com.jiuqi.np.dataengine.data.AbstractData data;
                    DataLinkDefine linkDefine = metaDatum.getDataLinkDefine();
                    if (metaDatum.isFormulaLink()) {
                        data = this.dataEngineService.expressionEvaluate(linkDefine.getLinkExpression(), context, masterKey, relation);
                    } else {
                        String defaultValue;
                        AbstractData fData;
                        IFMDMAttribute fmAttribute = metaDatum.getFmAttribute();
                        if (fmAttribute != null) {
                            fData = ifmdmData.getEntityValue(fmAttribute.getCode());
                            defaultValue = fmAttribute.getDefaultValue();
                            if (fmAttribute.getColumnType() == ColumnModelType.DATETIME && fData instanceof DateTimeData) {
                                fData = AbstractData.valueOf((Object)fData.getAsDateObj(), (int)5);
                            }
                        } else {
                            DataField dataField = metaDatum.getDataField();
                            defaultValue = dataField.getDefaultValue();
                            fData = linkDefine.getType() == DataLinkType.DATA_LINK_TYPE_INFO ? ifmdmData.getInfoValue(dataField.getCode()) : ifmdmData.getDataValue(dataField.getCode());
                        }
                        if (fmdmParentField != null && fmAttribute != null && fmAttribute.getCode().equals(fmdmParentField.getCode())) {
                            if (!fData.isNull) {
                                String parentValue = fData.getAsString();
                                if (DEFAULT_PARENT_CODE.equals(parentValue)) {
                                    parentValue = "";
                                }
                                data = com.jiuqi.np.dataengine.data.AbstractData.valueOf((Object)parentValue, (int)fData.dataType);
                            } else {
                                data = com.jiuqi.np.dataengine.data.AbstractData.valueOf((Object)"", (int)fData.dataType);
                            }
                        } else if (fData.isNull && StringUtils.hasLength(defaultValue)) {
                            com.jiuqi.np.dataengine.data.AbstractData dev = this.dataEngineService.expressionEvaluate(defaultValue, context, masterKeys, relation);
                            data = com.jiuqi.np.dataengine.data.AbstractData.valueOf((Object)dev.getAsObject(), (int)dev.dataType);
                        } else {
                            data = com.jiuqi.np.dataengine.data.AbstractData.valueOf((Object)fData.getAsObject(), (int)fData.dataType);
                        }
                    }
                    if (dataValueProcessor != null) {
                        data = dataValueProcessor.processValue(metaDatum, data);
                    }
                    DataValue value = new DataValue(metaDatum, data);
                    metaDatum.setAccessResult(new FmAccessResult());
                    values.add(value);
                }
            }
        }
        return fmSet;
    }

    protected IDataValueProcessor measureBalance(IQueryInfo queryInfo, RegionRelation relation) {
        Measure measure = queryInfo.getMeasure();
        if (measure != null) {
            return this.dataValueProcessorFactory.initMeasureDataValueProcessor(relation, measure);
        }
        return null;
    }

    private static class FmAccessResult
    implements IAccessResult {
        private FmAccessResult() {
        }

        public boolean haveAccess() {
            return true;
        }

        public String getMessage() {
            return null;
        }
    }
}

