/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.service.IFormSchemeService
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.var.ReferRelation
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.model.IEntityRefer
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.modal.IPeriodRow
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccess
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider
 *  com.jiuqi.nvwa.dataengine.common.DataAccessContext
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryModel
 *  com.jiuqi.nvwa.dataengine.model.OrderByItem
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.nr.jtable.service.impl;

import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.service.IFormSchemeService;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.var.ReferRelation;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.model.IEntityRefer;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.jtable.cache.EntitysReferMap;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.EnumLinkData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.base.LinkData;
import com.jiuqi.nr.jtable.params.input.EntityQueryByKeyInfo;
import com.jiuqi.nr.jtable.params.input.EntityQueryByKeysInfo;
import com.jiuqi.nr.jtable.params.input.EntityQueryByViewInfo;
import com.jiuqi.nr.jtable.params.input.EntityQueryInfo;
import com.jiuqi.nr.jtable.params.input.MeasureQueryInfo;
import com.jiuqi.nr.jtable.params.input.RelEntityExtraQueryInfo;
import com.jiuqi.nr.jtable.params.input.TableRelationField;
import com.jiuqi.nr.jtable.params.output.EntityByKeyReturnInfo;
import com.jiuqi.nr.jtable.params.output.EntityByKeysReturnInfo;
import com.jiuqi.nr.jtable.params.output.EntityData;
import com.jiuqi.nr.jtable.params.output.EntityReturnInfo;
import com.jiuqi.nr.jtable.params.output.MeasureData;
import com.jiuqi.nr.jtable.service.IEntityExtraService;
import com.jiuqi.nr.jtable.service.IJtableEntityQueryService;
import com.jiuqi.nr.jtable.service.IJtableEntityService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nr.jtable.util.EntityDataLoader;
import com.jiuqi.nr.jtable.util.JsonUtil;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.modal.IPeriodRow;
import com.jiuqi.nvwa.dataengine.INvwaDataAccess;
import com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider;
import com.jiuqi.nvwa.dataengine.common.DataAccessContext;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryModel;
import com.jiuqi.nvwa.dataengine.model.OrderByItem;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class JtableEntityServiceImpl
implements IJtableEntityService {
    private static final Logger logger = LoggerFactory.getLogger(JtableEntityServiceImpl.class);
    @Autowired
    private IJtableParamService jtableParamService;
    @Autowired
    private IJtableEntityQueryService jtableEntityQueryService;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private INvwaDataAccessProvider iNvwaDataAccessProvider;
    @Autowired(required=false)
    private IEntityExtraService entityExtraService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IPeriodEntityAdapter periodEntityAdapter;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IRunTimeViewController runtimeView;
    @Autowired
    private IFormSchemeService formSchemeService;
    @Autowired
    private EntitysReferMap entitysReferMap;

    private String getLanguage() {
        String language = NpContextHolder.getContext().getLocale().getLanguage();
        if (language == null || language.equals("")) {
            return "zh";
        }
        return language;
    }

    @Override
    public EntityReturnInfo queryEntityData(EntityQueryByViewInfo entityQueryByViewInfo) {
        return this.queryEntity(entityQueryByViewInfo, false);
    }

    @Override
    public EntityReturnInfo queryDimEntityData(EntityQueryByViewInfo entityQueryByViewInfo) {
        return this.queryEntity(entityQueryByViewInfo, true);
    }

    private EntityReturnInfo queryEntity(EntityQueryByViewInfo entityQueryByViewInfo, boolean queryDim) {
        IEntityTable entityTable;
        boolean isFMDMform;
        ArrayList<ReferRelation> referRelations;
        boolean sorted;
        EntityViewData entityView;
        JtableContext jtableContext;
        EntityReturnInfo returnInfo;
        block26: {
            FormDefine formDefine;
            EnumLinkData enumLink;
            block27: {
                List<String> preLinks;
                block28: {
                    Object preEnumLink;
                    returnInfo = new EntityReturnInfo();
                    jtableContext = entityQueryByViewInfo.getContext();
                    entityView = this.jtableParamService.getEntity(entityQueryByViewInfo.getEntityViewKey());
                    sorted = entityQueryByViewInfo.isSorted();
                    referRelations = new ArrayList<ReferRelation>();
                    if (jtableContext == null) {
                        jtableContext = new JtableContext();
                        entityQueryByViewInfo.setContext(jtableContext);
                    } else {
                        EntityViewData dwEntity = this.jtableParamService.getDwEntity(jtableContext.getFormSchemeKey());
                        if (entityView.getKey().equals(dwEntity.getKey())) {
                            entityView = dwEntity;
                        } else if (queryDim) {
                            HashMap<String, Object> iEntityReferMap = new HashMap<String, Object>();
                            List dwEntityReferList = this.entityMetaService.getEntityRefer(dwEntity.getKey());
                            String dimAttributeByReportDim = this.formSchemeService.getDimAttributeByReportDim(entityQueryByViewInfo.getContext().getFormSchemeKey(), entityView.getKey());
                            for (Object iEntityReferInfo : dwEntityReferList) {
                                if (!iEntityReferInfo.getOwnField().equals(dimAttributeByReportDim)) continue;
                                iEntityReferMap.put(entityView.getKey(), iEntityReferInfo);
                                break;
                            }
                            List<EntityViewData> dimEntityList = this.jtableParamService.getDimEntityList(jtableContext.getFormSchemeKey());
                            for (EntityViewData dimEntity : dimEntityList) {
                                String string;
                                if (!dimEntity.getKey().equals(entityView.getKey())) continue;
                                boolean isSummaryEntry = jtableContext.getVariableMap() != null && jtableContext.getVariableMap().containsKey("batchGatherSchemeCode");
                                boolean entityRefer = this.entityMetaService.estimateEntityRefer(dwEntity.getKey(), entityView.getKey());
                                if (isSummaryEntry || !entityRefer || !jtableContext.getDimensionSet().containsKey(dwEntity.getDimensionName()) || !StringUtils.isNotEmpty((String)(string = jtableContext.getDimensionSet().get(dwEntity.getDimensionName()).getValue()))) continue;
                                EntityViewDefine dwEntityView = dwEntity.getEntityViewDefine();
                                ReferRelation referRelation = new ReferRelation();
                                referRelation.setViewDefine(dwEntityView);
                                ArrayList<String> range = new ArrayList<String>();
                                range.add(string);
                                referRelation.setRange(range);
                                if (!iEntityReferMap.containsKey(entityView.getKey())) continue;
                                referRelation.setRefer((IEntityRefer)iEntityReferMap.get(entityView.getKey()));
                                referRelations.add(referRelation);
                            }
                        }
                    }
                    this.setEntityAttributes(entityView, entityQueryByViewInfo);
                    isFMDMform = false;
                    if (queryDim || !StringUtils.isNotEmpty((String)entityQueryByViewInfo.getDataLinkKey())) break block26;
                    sorted = true;
                    LinkData link = this.jtableParamService.getLink(entityQueryByViewInfo.getDataLinkKey());
                    if (!(link instanceof EnumLinkData)) break block26;
                    enumLink = (EnumLinkData)link;
                    EntityViewDefine entityViewDefine = this.runtimeView.getViewByLinkDefineKey(enumLink.getKey());
                    entityView.setEntityViewDefine(entityViewDefine);
                    if (enumLink.getEnumLink() == null || enumLink.getEnumLink().getPreLinks() == null || enumLink.getEnumLink().getPreLinks().isEmpty()) break block27;
                    preLinks = enumLink.getEnumLink().getPreLinks();
                    if (preLinks.size() != 1) break block28;
                    LinkData preLink = this.jtableParamService.getLink(preLinks.get(0));
                    if (!(preLink instanceof EnumLinkData) || ((EnumLinkData)(preEnumLink = (EnumLinkData)preLink)).getEntityKey().equals(enumLink.getEntityKey())) break block27;
                    EntityViewDefine preEntityView = this.runtimeView.getViewByLinkDefineKey(((LinkData)preEnumLink).getKey());
                    if (!StringUtils.isNotEmpty((String)entityQueryByViewInfo.getParentKey())) break block27;
                    ReferRelation referRelation = new ReferRelation();
                    referRelation.setViewDefine(preEntityView);
                    ArrayList<String> arrayList = new ArrayList<String>();
                    String parentKey = entityQueryByViewInfo.getParentKey();
                    if (((EnumLinkData)preEnumLink).isMultipleSelect() && parentKey.contains(";")) {
                        String[] preValues = parentKey.split(";");
                        if (preValues != null && preValues.length > 0) {
                            Collections.addAll(arrayList, preValues);
                        }
                    } else {
                        arrayList.add(entityQueryByViewInfo.getParentKey());
                    }
                    entityQueryByViewInfo.setParentKey("");
                    referRelation.setRange(arrayList);
                    referRelations.add(referRelation);
                    break block27;
                }
                if (preLinks.size() > 1) {
                    Map<String, String> perEntityValues = entityQueryByViewInfo.getPerEntityValues();
                    for (String preLinkKey : preLinks) {
                        EnumLinkData enumLinkData;
                        LinkData preLink = this.jtableParamService.getLink(preLinkKey);
                        if (!(preLink instanceof EnumLinkData) || (enumLinkData = (EnumLinkData)preLink).getEntityKey().equals(enumLink.getEntityKey())) continue;
                        EntityViewDefine preEntityView = this.runtimeView.getViewByLinkDefineKey(enumLinkData.getKey());
                        String preEnumValue = perEntityValues.get(enumLinkData.getEntityKey());
                        if (!StringUtils.isNotEmpty((String)preEnumValue)) continue;
                        ReferRelation referRelation = new ReferRelation();
                        referRelation.setViewDefine(preEntityView);
                        ArrayList<String> range = new ArrayList<String>();
                        if (enumLinkData.isMultipleSelect() && preEnumValue.contains(";")) {
                            String[] preValues = preEnumValue.split(";");
                            if (preValues != null && preValues.length > 0) {
                                Collections.addAll(range, preValues);
                            }
                        } else {
                            range.add(preEnumValue);
                        }
                        referRelation.setRange(range);
                        referRelations.add(referRelation);
                    }
                }
            }
            if ((formDefine = this.runtimeView.queryFormById(jtableContext.getFormKey())).getFormType() == FormType.FORM_TYPE_NEWFMDM) {
                isFMDMform = true;
                String entityId = entityView.getKey();
                IEntityModel entityModel = this.entityMetaService.getEntityModel(entityId);
                IEntityAttribute parentField = entityModel.getParentField();
                if (enumLink.getZbcode().equalsIgnoreCase(parentField.getCode())) {
                    entityQueryByViewInfo.setReadAuth(false);
                }
            }
        }
        List<Object> extraDatas = new ArrayList();
        if (this.entityExtraService != null && entityQueryByViewInfo.isUseExtra()) {
            extraDatas = this.queryExtraDatas(entityView, entityQueryByViewInfo);
        }
        if (queryDim) {
            EntityViewDefine dimViewDefine = this.runtimeView.getDimensionViewByFormSchemeAndEntity(jtableContext.getFormSchemeKey(), entityView.getKey());
            entityView.setEntityViewDefine(dimViewDefine);
            entityTable = this.jtableEntityQueryService.queryDimEntity(entityView, referRelations, entityQueryByViewInfo.getContext(), entityQueryByViewInfo.isReadAuth(), !entityQueryByViewInfo.isAllChildren(), true);
        } else {
            entityQueryByViewInfo.setDesensitized(true);
            entityTable = this.jtableEntityQueryService.queryEntity(entityView, referRelations, entityQueryByViewInfo.getContext(), entityQueryByViewInfo.isReadAuth(), !entityQueryByViewInfo.isAllChildren(), sorted, isFMDMform, entityQueryByViewInfo.isIgnoreIsolate(), entityQueryByViewInfo.isDesensitized());
        }
        EntityDataLoader loader = new EntityDataLoader(entityTable, StringUtils.isNotEmpty((String)entityView.getRowFilter()), !entityQueryByViewInfo.isAllChildren());
        loader.setFields(entityQueryByViewInfo);
        List<String> cells = loader.getCells();
        returnInfo.getCells().addAll(cells);
        IEntityModel entityModel = this.entityMetaService.getEntityModel(entityQueryByViewInfo.getEntityViewKey());
        ArrayList<String> dataMaskFields = new ArrayList<String>();
        for (String cell : cells) {
            IEntityAttribute iEntityAttribute = entityModel.getAttribute(cell);
            if (!StringUtils.isNotEmpty((String)iEntityAttribute.masked())) continue;
            dataMaskFields.add(cell);
        }
        returnInfo.setDataMaskFields(dataMaskFields);
        if (CollectionUtils.isEmpty(extraDatas)) {
            returnInfo.getEntitys().addAll(loader.getEntityDatas(entityQueryByViewInfo));
            if (!queryDim) {
                this.buildReferEntityTitle(entityView.getKey(), returnInfo.getEntitys(), returnInfo.getCells());
            }
        } else {
            ArrayList<EntityData> entityDatas = new ArrayList<EntityData>();
            for (String string : extraDatas) {
                EntityData entityData = loader.getEntityDataByKey(string);
                if (entityData == null || entityQueryByViewInfo.isSearchLeaf() && !entityData.isLeaf()) continue;
                entityDatas.add(entityData);
            }
            returnInfo.getEntitys().clear();
            returnInfo.getEntitys().addAll(entityDatas);
        }
        if (returnInfo.getEntitys().isEmpty()) {
            returnInfo.setDimensionSet(loader.getParentEntityKeys());
        }
        returnInfo.setMessage("success");
        return returnInfo;
    }

    private List<String> queryExtraDatas(EntityViewData entityView, EntityQueryByViewInfo entityQueryByViewInfo) {
        Map<String, String> perEntityValues = entityQueryByViewInfo.getPerEntityValues();
        ArrayList<String> extraDatas = new ArrayList<String>();
        JtableContext jtableContext = entityQueryByViewInfo.getContext();
        RelEntityExtraQueryInfo relEntityExtraQueryInfo = new RelEntityExtraQueryInfo();
        relEntityExtraQueryInfo.setContext(jtableContext);
        List<String> tableNames = relEntityExtraQueryInfo.getTableNames();
        Map<String, String> tableValues = relEntityExtraQueryInfo.getTableValues();
        if (null != entityQueryByViewInfo.getDataLinkKey()) {
            LinkData querylink = this.jtableParamService.getLink(entityQueryByViewInfo.getDataLinkKey());
            EnumLinkData queryEnumData = null;
            if (querylink instanceof EnumLinkData) {
                queryEnumData = (EnumLinkData)querylink;
            }
            HashMap<String, EnumLinkData> linkMap = new HashMap<String, EnumLinkData>();
            for (LinkData link : this.jtableParamService.getLinks(querylink.getRegionKey())) {
                if (!(link instanceof EnumLinkData)) continue;
                EnumLinkData enumLinkData = (EnumLinkData)link;
                linkMap.put(link.getKey(), enumLinkData);
            }
            ArrayList<String> enumLinks = new ArrayList<String>();
            while (queryEnumData != null) {
                if (!enumLinks.contains(queryEnumData.getKey())) {
                    enumLinks.add(0, queryEnumData.getKey());
                }
                if (queryEnumData.getEnumLink() != null && !queryEnumData.getEnumLink().getPreLinks().isEmpty()) {
                    List<String> preLinks = queryEnumData.getEnumLink().getPreLinks();
                    for (int i = preLinks.size() - 1; i >= 0; --i) {
                        String preLink = preLinks.get(i);
                        if (enumLinks.contains(preLink)) continue;
                        enumLinks.add(0, preLink);
                    }
                    queryEnumData = (EnumLinkData)linkMap.get(preLinks.get(preLinks.size() - 1));
                    continue;
                }
                queryEnumData = null;
            }
            String parentTableName = "";
            for (int enumLinkIndex = 0; enumLinkIndex < enumLinks.size(); ++enumLinkIndex) {
                String enumLinkKey = (String)enumLinks.get(enumLinkIndex);
                EnumLinkData enumLinkData = (EnumLinkData)linkMap.get(enumLinkKey);
                String entityKey = enumLinkData.getEntityKey();
                EntityViewData entityViewData = this.jtableParamService.getEntity(entityKey);
                tableNames.add(entityViewData.getTableName());
                if (perEntityValues != null && perEntityValues.containsKey(entityKey)) {
                    tableValues.put(entityViewData.getTableName(), perEntityValues.get(entityKey));
                }
                if (enumLinkIndex > 0) {
                    TableRelationField tableRelationField = new TableRelationField();
                    tableRelationField.setParentTableName(parentTableName);
                    tableRelationField.setChildTableName(entityViewData.getTableName());
                    String formula = enumLinkData.getEnumLink().getFormula();
                    tableRelationField.setRelationFieldName(formula.substring(formula.indexOf("[") + 1, formula.indexOf("]")));
                    relEntityExtraQueryInfo.getTableRelationFields().add(tableRelationField);
                }
                parentTableName = entityViewData.getTableName();
            }
            List<List<String>> queryRelEntityDatas = this.entityExtraService.queryRelEntityDatas(relEntityExtraQueryInfo);
            int queryEntityIndex = tableNames.indexOf(entityView.getTableName());
            if (queryEntityIndex > 0) {
                for (List<String> relEntityData : queryRelEntityDatas) {
                    extraDatas.add(relEntityData.get(queryEntityIndex));
                }
            }
        }
        return extraDatas;
    }

    @Override
    public List<List<String>> queryRelEntityDatas(List<String> fillLinks, JtableContext context) {
        ArrayList<List<String>> allEntityDatas = new ArrayList<List<String>>();
        if (this.entityExtraService != null) {
            RelEntityExtraQueryInfo relEntityExtraQueryInfo = new RelEntityExtraQueryInfo();
            relEntityExtraQueryInfo.setContext(context);
            List<String> tableNames = relEntityExtraQueryInfo.getTableNames();
            HashMap<String, EntityDataLoader> entityLoaderMap = new HashMap<String, EntityDataLoader>();
            HashMap<String, EnumLinkData> entityLinkMap = new HashMap<String, EnumLinkData>();
            String parentTableName = "";
            for (int enumLinkIndex = 0; enumLinkIndex < fillLinks.size(); ++enumLinkIndex) {
                String enumLinkKey = fillLinks.get(enumLinkIndex);
                LinkData linkData = this.jtableParamService.getLink(enumLinkKey);
                if (!(linkData instanceof EnumLinkData)) continue;
                EnumLinkData enumLinkData = (EnumLinkData)linkData;
                entityLinkMap.put(enumLinkKey, enumLinkData);
                String entityKey = enumLinkData.getEntityKey();
                EntityViewData entityViewData = this.jtableParamService.getEntity(entityKey);
                tableNames.add(entityViewData.getTableName());
                EntityQueryByKeyInfo entityQueryByKeyInfo = new EntityQueryByKeyInfo();
                entityQueryByKeyInfo.setContext(context);
                entityQueryByKeyInfo.setEntityViewKey(entityViewData.getKey());
                entityQueryByKeyInfo.setCaptionFields(new HashSet<String>(enumLinkData.getCapnames()));
                EntityDataLoader entityDataLoader = this.getEntityDataLoader(entityQueryByKeyInfo);
                entityLoaderMap.put(enumLinkKey, entityDataLoader);
                EntityQueryByViewInfo entityQueryByViewInfo = new EntityQueryByViewInfo();
                entityQueryByViewInfo.setContext(context);
                entityQueryByViewInfo.setEntityViewKey(entityViewData.getKey());
                entityQueryByViewInfo.setAllChildren(true);
                entityQueryByViewInfo.setSearchLeaf(enumLinkData.isOnlyLeaf());
                EntityReturnInfo returnInfo = new EntityReturnInfo();
                returnInfo.getCells().addAll(entityDataLoader.getCells());
                returnInfo.getEntitys().addAll(entityDataLoader.getEntityDatas(entityQueryByViewInfo));
                List<String> allEntityKey = DimensionValueSetUtil.getAllEntityKey(returnInfo, false);
                relEntityExtraQueryInfo.getTableRangeValues().put(entityViewData.getTableName(), allEntityKey);
                if (enumLinkIndex > 0) {
                    TableRelationField tableRelationField = new TableRelationField();
                    tableRelationField.setParentTableName(parentTableName);
                    tableRelationField.setChildTableName(entityViewData.getTableName());
                    String formula = enumLinkData.getEnumLink().getFormula();
                    tableRelationField.setRelationFieldName(formula.substring(formula.indexOf("[") + 1, formula.indexOf("]")));
                    relEntityExtraQueryInfo.getTableRelationFields().add(tableRelationField);
                }
                parentTableName = entityViewData.getTableName();
            }
            List<List<String>> extraEntityDatas = this.entityExtraService.queryRelEntityDatas(relEntityExtraQueryInfo);
            for (List<String> extraEntityData : extraEntityDatas) {
                boolean readAuth = true;
                for (int i = 0; i < fillLinks.size(); ++i) {
                    String enumLinkKey = fillLinks.get(i);
                    EntityDataLoader entityDataLoader = (EntityDataLoader)entityLoaderMap.get(enumLinkKey);
                    EnumLinkData enumLinkData = (EnumLinkData)entityLinkMap.get(enumLinkKey);
                    String entityDataKey = extraEntityData.get(i);
                    EntityData entityData = entityDataLoader.getEntityDataByKey(entityDataKey);
                    if (entityData == null) {
                        readAuth = false;
                        continue;
                    }
                    if (!enumLinkData.isOnlyLeaf() || entityData.isLeaf()) continue;
                    readAuth = false;
                }
                if (!readAuth) continue;
                allEntityDatas.add(extraEntityData);
            }
        }
        return allEntityDatas;
    }

    @Override
    public EntityByKeyReturnInfo queryEntityDataByKey(EntityQueryByKeyInfo entityQueryByKeyInfo) {
        return this.queryEntityDataByKeyWithDim(entityQueryByKeyInfo, null);
    }

    @Override
    public EntityByKeyReturnInfo queryEntityDataByKey(EntityQueryByKeyInfo entityQueryByKeyInfo, DimensionValueSet dimensionValueSet) {
        return this.queryEntityDataByKeyWithDim(entityQueryByKeyInfo, dimensionValueSet);
    }

    private EntityByKeyReturnInfo queryEntityDataByKeyWithDim(EntityQueryByKeyInfo entityQueryByKeyInfo, DimensionValueSet dimensionValueSet) {
        EntityByKeyReturnInfo returnInfo = new EntityByKeyReturnInfo();
        EntityViewData entityView = this.jtableParamService.getEntity(entityQueryByKeyInfo.getEntityViewKey());
        this.setEntityAttributes(entityView, entityQueryByKeyInfo);
        ArrayList<ReferRelation> referRelations = new ArrayList<ReferRelation>();
        IEntityTable entityTable = this.jtableEntityQueryService.queryEntityWithDimVal(entityView, referRelations, entityQueryByKeyInfo.getContext(), entityQueryByKeyInfo.isReadAuth(), true, dimensionValueSet);
        EntityDataLoader loader = new EntityDataLoader(entityTable, StringUtils.isNotEmpty((String)entityView.getRowFilter()), true);
        loader.setFields(entityQueryByKeyInfo);
        returnInfo.getCells().addAll(loader.getCells());
        EntityData entityData = loader.getEntityDataByKey(entityQueryByKeyInfo.getEntityKey());
        if (entityData == null) {
            logger.error("\u6ca1\u67e5\u5230\u5355\u4f4d\uff0c\u5168\u90e8\u7f6e\u6210unitKey,\u5f53\u524d\u67e5\u8be2\u73af\u5883:" + JsonUtil.objectToJson(entityQueryByKeyInfo));
            entityData = new EntityData();
            entityData.setId(entityQueryByKeyInfo.getEntityKey());
            entityData.setRowCaption(entityQueryByKeyInfo.getEntityKey());
            entityData.setCode(entityQueryByKeyInfo.getEntityKey());
            entityData.setTitle(entityQueryByKeyInfo.getEntityKey());
        }
        returnInfo.setEntity(entityData);
        returnInfo.setMessage("success");
        return returnInfo;
    }

    @Override
    public EntityByKeysReturnInfo queryEntityDataByKeys(EntityQueryByKeysInfo entityQueryByKeysInfo) {
        EntityByKeysReturnInfo returnInfo = new EntityByKeysReturnInfo();
        EntityViewData entityView = this.jtableParamService.getEntity(entityQueryByKeysInfo.getEntityViewKey());
        this.setEntityAttributes(entityView, entityQueryByKeysInfo);
        ArrayList<ReferRelation> referRelations = new ArrayList<ReferRelation>();
        IEntityTable entityTable = this.jtableEntityQueryService.queryEntity(entityView, referRelations, entityQueryByKeysInfo.getContext(), entityQueryByKeysInfo.isReadAuth(), true, entityQueryByKeysInfo.isIgnoreIsolate(), entityQueryByKeysInfo.isDesensitized());
        EntityDataLoader loader = new EntityDataLoader(entityTable, StringUtils.isNotEmpty((String)entityView.getRowFilter()), true);
        loader.setFields(entityQueryByKeysInfo);
        returnInfo.getCells().addAll(loader.getCells());
        HashSet<String> entityDataKeys = new HashSet<String>(entityQueryByKeysInfo.getEntityKeys());
        returnInfo.getEntitys().putAll(loader.getEntityDataByKeys(entityDataKeys));
        returnInfo.setMessage("success");
        return returnInfo;
    }

    @Override
    public EntityByKeyReturnInfo queryEntityDataByKey(String keyData, EntityDataLoader entityDataLoader) {
        EntityByKeyReturnInfo returnInfo = new EntityByKeyReturnInfo();
        returnInfo.getCells().addAll(entityDataLoader.getCells());
        returnInfo.setEntity(entityDataLoader.getEntityDataByKey(keyData));
        returnInfo.setMessage("success");
        return returnInfo;
    }

    @Override
    public EntityDataLoader getEntityDataLoader(EntityQueryByKeyInfo entityQueryByKeyInfo) {
        return this.getEntityDataLoader(entityQueryByKeyInfo, false);
    }

    @Override
    public EntityDataLoader getEntityDataLoader(EntityQueryByKeyInfo entityQueryByKeyInfo, boolean lazyLoad) {
        LinkData link;
        EntityViewData entityView = this.jtableParamService.getEntity(entityQueryByKeyInfo.getEntityViewKey());
        if (StringUtils.isNotEmpty((String)entityQueryByKeyInfo.getDataLinkKey()) && (link = this.jtableParamService.getLink(entityQueryByKeyInfo.getDataLinkKey())) instanceof EnumLinkData) {
            EntityViewDefine entityViewDefine = this.runtimeView.getViewByLinkDefineKey(link.getKey());
            entityView.setEntityViewDefine(entityViewDefine);
        }
        this.setEntityAttributes(entityView, entityQueryByKeyInfo);
        ArrayList<ReferRelation> referRelations = new ArrayList<ReferRelation>();
        IEntityTable entityTable = this.jtableEntityQueryService.queryEntity(entityView, referRelations, entityQueryByKeyInfo.getContext(), entityQueryByKeyInfo.isReadAuth(), lazyLoad, entityQueryByKeyInfo.isIgnoreIsolate(), entityQueryByKeyInfo.isDesensitized());
        EntityDataLoader loader = new EntityDataLoader(entityTable, StringUtils.isNotEmpty((String)entityView.getRowFilter()), lazyLoad);
        loader.setFields(entityQueryByKeyInfo);
        return loader;
    }

    private void setEntityAttributes(EntityViewData entityView, EntityQueryInfo entityQueryInfo) {
        LinkData link;
        Set<String> captionFields = entityQueryInfo.getCaptionFields();
        Set<String> dropDownFields = entityQueryInfo.getDropDownFields();
        if (StringUtils.isNotEmpty((String)entityQueryInfo.getDataLinkKey()) && (link = this.jtableParamService.getLink(entityQueryInfo.getDataLinkKey())) instanceof EnumLinkData) {
            EnumLinkData enumLink = (EnumLinkData)link;
            IEntityModel entityModel = this.entityMetaService.getEntityModel(entityView.getKey());
            Iterator attributes = entityModel.getAttributes();
            HashMap<String, Object> attributeMap = new HashMap<String, Object>();
            while (attributes.hasNext()) {
                IEntityAttribute attribute = (IEntityAttribute)attributes.next();
                attributeMap.put(attribute.getCode(), attribute);
            }
            if (enumLink.getCapnames() != null) {
                for (String captionField : enumLink.getCapnames()) {
                    if (!attributeMap.containsKey(captionField)) continue;
                    captionFields.add(captionField);
                }
            }
            if (enumLink.getDropnames() != null) {
                for (String dropDownField : enumLink.getDropnames()) {
                    if (!attributeMap.containsKey(dropDownField)) continue;
                    dropDownFields.add(dropDownField);
                }
            }
            if (enumLink.getEnumFieldPosMap() != null) {
                for (String captionField : enumLink.getEnumFieldPosMap().keySet()) {
                    if (!attributeMap.containsKey(captionField)) continue;
                    captionFields.add(captionField);
                }
            }
            if (this.periodEntityAdapter.isPeriodEntity(entityView.getKey())) {
                IEntityAttribute endDateField;
                IEntityAttribute beginDateField = entityModel.getBeginDateField();
                if (beginDateField != null) {
                    captionFields.add(beginDateField.getCode());
                }
                if ((endDateField = entityModel.getEndDateField()) != null) {
                    captionFields.add(endDateField.getCode());
                }
            }
        }
    }

    @Override
    public List<String> getAllEntityKey(String entityKey, Map<String, DimensionValue> dimensionSet, String formSchemeKey) {
        return this.getEntityKey(entityKey, dimensionSet, formSchemeKey, false, false);
    }

    @Override
    public List<String> getAllEntityKey(String entityKey, JtableContext context, EntityViewDefine entityViewDefine) {
        ArrayList<String> valueIDList = new ArrayList<String>();
        EntityViewData entityView = this.jtableParamService.getEntity(entityKey);
        entityView.setEntityViewDefine(entityViewDefine);
        HashSet<String> fields = new HashSet<String>();
        fields.add("ALL");
        ArrayList<ReferRelation> referRelations = new ArrayList<ReferRelation>();
        IEntityTable entityTable = this.jtableEntityQueryService.queryEntity(entityView, referRelations, context, true, false, true, false, false, false);
        if (entityTable == null) {
            return valueIDList;
        }
        List allRows = entityTable.getAllRows();
        for (IEntityRow row : allRows) {
            valueIDList.add(row.getEntityKeyData());
        }
        return valueIDList;
    }

    @Override
    public List<String> getAllDimEntityKey(String entityKey, Map<String, DimensionValue> dimensionSet, String formSchemeKey) {
        return this.getEntityKey(entityKey, dimensionSet, formSchemeKey, true, false);
    }

    private List<String> getEntityKey(String entityKey, Map<String, DimensionValue> dimensionSet, String formSchemeKey, boolean queryDim, boolean sorted) {
        IEntityTable entityTable;
        ArrayList<String> valueIDList = new ArrayList<String>();
        EntityViewData entityView = this.jtableParamService.getEntity(entityKey);
        HashSet<String> fields = new HashSet<String>();
        fields.add("ALL");
        JtableContext jtableContext = new JtableContext();
        jtableContext.setFormSchemeKey(formSchemeKey);
        if (dimensionSet != null && dimensionSet.containsKey("DATATIME")) {
            HashMap<String, DimensionValue> currentMap = new HashMap<String, DimensionValue>();
            currentMap.put("DATATIME", dimensionSet.get("DATATIME"));
            jtableContext.setDimensionSet(currentMap);
        }
        ArrayList<ReferRelation> referRelations = new ArrayList<ReferRelation>();
        if (queryDim) {
            EntityViewDefine dimViewDefine = this.runtimeView.getDimensionViewByFormSchemeAndEntity(formSchemeKey, entityKey);
            entityView.setEntityViewDefine(dimViewDefine);
            entityTable = this.jtableEntityQueryService.queryDimEntity(entityView, referRelations, jtableContext, true, false, false);
        } else {
            entityTable = this.jtableEntityQueryService.queryEntity(entityView, referRelations, jtableContext, true, false, sorted, false, false, false);
        }
        if (entityTable == null) {
            return valueIDList;
        }
        List allRows = entityTable.getAllRows();
        for (IEntityRow row : allRows) {
            valueIDList.add(row.getEntityKeyData());
        }
        return valueIDList;
    }

    @Override
    public int getMaxDepth(String entityKey, JtableContext context) {
        ArrayList<ReferRelation> referRelations;
        IEntityTable entityTable;
        EntityViewData entityView = this.jtableParamService.getEntity(entityKey);
        JtableContext jtableContext = new JtableContext(context);
        Map<String, DimensionValue> dimensionSet = context.getDimensionSet();
        if (dimensionSet != null && dimensionSet.containsKey("DATATIME")) {
            HashMap<String, DimensionValue> currentMap = new HashMap<String, DimensionValue>();
            currentMap.put("DATATIME", dimensionSet.get("DATATIME"));
            jtableContext.setDimensionSet(currentMap);
        }
        if ((entityTable = this.jtableEntityQueryService.queryEntity(entityView, referRelations = new ArrayList<ReferRelation>(), jtableContext, true, true, false, false)) == null) {
            return 1;
        }
        return entityTable.getMaxDepth();
    }

    @Override
    public EntityReturnInfo queryCustomPeriodData(EntityQueryByViewInfo entityQueryInfo) {
        EntityReturnInfo returnInfo = new EntityReturnInfo();
        EntityViewData entityView = this.jtableParamService.getEntity(entityQueryInfo.getEntityViewKey());
        if (entityView == null || !this.periodEntityAdapter.isPeriodEntity(entityView.getKey())) {
            return returnInfo;
        }
        ArrayList<EntityData> entitys = new ArrayList<EntityData>();
        returnInfo.setEntitys(entitys);
        IPeriodProvider periodProvider = this.periodEntityAdapter.getPeriodProvider(entityView.getKey());
        List periodItems = periodProvider.getPeriodItems();
        for (IPeriodRow periodRow : periodItems) {
            EntityData entityData = new EntityData();
            entityData.setId(periodRow.getKey());
            entityData.setCode(periodRow.getCode());
            entityData.setTitle(periodRow.getTitle());
            entitys.add(entityData);
        }
        return returnInfo;
    }

    @Override
    public List<MeasureData> queryMeasureData(MeasureQueryInfo measureQueryInfo) {
        ArrayList<MeasureData> measureDatas = new ArrayList<MeasureData>();
        if (StringUtils.isEmpty((String)measureQueryInfo.getMeasureViewKey())) {
            return measureDatas;
        }
        NvwaQueryModel queryModel = new NvwaQueryModel();
        TableModelDefine table = this.dataModelService.getTableModelDefineById(measureQueryInfo.getMeasureViewKey());
        List allColumns = this.dataModelService.getColumnModelDefinesByTable(table.getID());
        allColumns = allColumns.stream().sorted((a, b) -> Double.compare(a.getOrder(), b.getOrder())).collect(Collectors.toList());
        for (ColumnModelDefine column : allColumns) {
            if (column.getCode().equalsIgnoreCase("MN_ORDERL")) {
                queryModel.getOrderByItems().add(new OrderByItem(column));
            }
            queryModel.getColumns().add(new NvwaQueryColumn(column));
        }
        try {
            INvwaDataAccess dataAccess = this.iNvwaDataAccessProvider.createReadOnlyDataAccess(queryModel);
            DataAccessContext context = new DataAccessContext(this.dataModelService);
            MemoryDataSet result = dataAccess.executeQuery(context);
            for (DataRow dataRow : result) {
                MeasureData measureData = this.getMeasureData(dataRow);
                if (measureData == null) continue;
                measureDatas.add(measureData);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return measureDatas;
    }

    @Override
    public MeasureData queryMeasureDataByCode(MeasureQueryInfo measureQueryInfo) {
        if (StringUtils.isEmpty((String)measureQueryInfo.getMeasureViewKey()) && StringUtils.isEmpty((String)measureQueryInfo.getMeasureValue())) {
            return null;
        }
        NvwaQueryModel queryModel = new NvwaQueryModel();
        TableModelDefine table = this.dataModelService.getTableModelDefineById(measureQueryInfo.getMeasureViewKey());
        List allColumns = this.dataModelService.getColumnModelDefinesByTable(table.getID());
        allColumns = allColumns.stream().sorted((a, b) -> Double.compare(a.getOrder(), b.getOrder())).collect(Collectors.toList());
        for (ColumnModelDefine column : allColumns) {
            queryModel.getColumns().add(new NvwaQueryColumn(column));
        }
        queryModel.getColumnFilters().put(allColumns.get(1), measureQueryInfo.getMeasureValue());
        try {
            INvwaDataAccess dataAccess = this.iNvwaDataAccessProvider.createReadOnlyDataAccess(queryModel);
            DataAccessContext context = new DataAccessContext(this.dataModelService);
            MemoryDataSet result = dataAccess.executeQuery(context);
            if (result.size() == 1) {
                DataRow dataRow = result.get(0);
                return this.getMeasureData(dataRow);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    private MeasureData getMeasureData(DataRow row) {
        if (row == null || row.getString(3).equals("0")) {
            return null;
        }
        MeasureData measureData = new MeasureData();
        measureData.setId(row.getString(0));
        measureData.setCode(row.getString(1));
        measureData.setTitle(row.getString(2));
        measureData.setRate(row.getString(4) != null ? new BigDecimal(row.getString(4)).doubleValue() : new BigDecimal(0).doubleValue());
        measureData.setBase(row.getInt(5) != 0);
        if (!this.getLanguage().equals("zh")) {
            switch (measureData.getTitle()) {
                case "\u5143": {
                    measureData.setTitle("Yuan");
                    break;
                }
                case "\u767e\u5143": {
                    measureData.setTitle("Hundred");
                    break;
                }
                case "\u5343\u5143": {
                    measureData.setTitle("Thousand");
                    break;
                }
                case "\u4e07\u5143": {
                    measureData.setTitle("Ten Thousand");
                    break;
                }
                case "\u767e\u4e07": {
                    measureData.setTitle("Million");
                    break;
                }
                case "\u5343\u4e07": {
                    measureData.setTitle("Ten Million");
                    break;
                }
                case "\u4ebf\u5143": {
                    measureData.setTitle("Hundred Million");
                }
            }
        }
        return measureData;
    }

    private Map<String, String> getReferEntityIdMap(String entityKey, List<String> fieldList) {
        List<IEntityRefer> entityRefer;
        HashMap<String, String> referEntityIdMap = new HashMap<String, String>();
        IEntityModel entityModel = this.entityMetaService.getEntityModel(entityKey);
        boolean existReferEntity = false;
        for (String code : fieldList) {
            IEntityAttribute attribute = entityModel.getAttribute(code);
            if (attribute == null || !StringUtils.isNotEmpty((String)attribute.getReferTableID())) continue;
            existReferEntity = true;
            break;
        }
        if (existReferEntity && (entityRefer = this.entitysReferMap.getEntityReferList(entityKey)) != null && entityRefer.size() > 0) {
            for (String code : fieldList) {
                entityRefer.forEach(refer -> {
                    if (refer.getOwnField().equals(code)) {
                        referEntityIdMap.put(code, refer.getReferEntityId());
                    }
                });
            }
        }
        return referEntityIdMap;
    }

    private void buildReferEntityTitle(String entityKey, List<EntityData> entityDatas, List<String> cells) {
        if (cells == null || cells.size() == 0) {
            return;
        }
        if (entityDatas == null || entityDatas.size() == 0) {
            return;
        }
        Map<String, String> referEntityIdMap = this.getReferEntityIdMap(entityKey, cells);
        if (referEntityIdMap == null || referEntityIdMap.size() == 0) {
            return;
        }
        HashMap<String, IEntityTable> referEntityTableMap = new HashMap<String, IEntityTable>();
        for (String code : referEntityIdMap.keySet()) {
            String entityId = referEntityIdMap.get(code);
            if (!StringUtils.isNotEmpty((String)entityId)) continue;
            EntityViewData entityView = this.jtableParamService.getEntity(entityId);
            EntityViewDefine entityViewDefine = this.entityViewRunTimeController.buildEntityView(entityId, null, false);
            entityView.setEntityViewDefine(entityViewDefine);
            IEntityTable iEntityTable = this.jtableEntityQueryService.querySimplEntityTable(entityView);
            referEntityTableMap.put(entityId, iEntityTable);
        }
        if (referEntityTableMap == null || referEntityTableMap.size() == 0) {
            return;
        }
        for (EntityData entityData : entityDatas) {
            if (entityData == null) continue;
            List<String> datas = entityData.getData();
            for (String code : referEntityIdMap.keySet()) {
                IEntityRow entityRow;
                int index;
                if (!cells.contains(code) || !StringUtils.isNotEmpty((String)datas.get(index = cells.indexOf(code)))) continue;
                String entityKeyData = datas.get(index);
                IEntityTable iEntityTable = (IEntityTable)referEntityTableMap.get(referEntityIdMap.get(code));
                if (iEntityTable == null || (entityRow = iEntityTable.quickFindByEntityKey(entityKeyData)) == null) continue;
                String showTitle = entityKeyData + "|" + entityRow.getTitle();
                entityData.getReferData().put(code, showTitle);
            }
        }
    }
}

