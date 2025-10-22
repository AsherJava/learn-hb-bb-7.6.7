/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.basedata.select.bean.BaseDataInfo
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.dataentity.entity.IDataEntity
 *  com.jiuqi.nr.dataentity.service.DataEntityFullService
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 */
package com.jiuqi.nr.annotation.service.impl;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.annotation.constant.PromptConsts;
import com.jiuqi.nr.annotation.service.IAnnotationTypeService;
import com.jiuqi.nr.basedata.select.bean.BaseDataInfo;
import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.dataentity.entity.IDataEntity;
import com.jiuqi.nr.dataentity.service.DataEntityFullService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnnotationTypeServiceImpl
implements IAnnotationTypeService {
    private static final Logger logger = LoggerFactory.getLogger(AnnotationTypeServiceImpl.class);
    @Autowired
    private INvwaSystemOptionService iNvwaSystemOptionService;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private DataEntityFullService dataEntityFullService;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IEntityMetaService metaService;
    private static final String ROOT = "root";

    @Override
    public boolean isOpenAnnotationType() {
        String isOpenAnnotationType = this.iNvwaSystemOptionService.get("annotationManagement", "OPEN_ANNOTATION_TYPE");
        return !"0".equals(isOpenAnnotationType);
    }

    @Override
    public boolean onlyLeafNode() {
        String onlyLeafNode = this.iNvwaSystemOptionService.get("annotationManagement", "ANNOTATION_ONLY_LEAF_NODE");
        return !"0".equals(onlyLeafNode);
    }

    @Override
    public String getTypeBaseDataKey() {
        return this.iNvwaSystemOptionService.get("annotationManagement", "BASE_DATA_KEY");
    }

    @Override
    public ITree<BaseDataInfo> queryTypeBaseData(DimensionCombination dim, String formSchemeKey, String entityKeyData) {
        String typeBaseDataKey = this.getTypeBaseDataKey();
        if (StringUtils.isNotEmpty((String)typeBaseDataKey)) {
            EntityViewDefine entityViewDefine = this.entityViewRunTimeController.buildEntityView(typeBaseDataKey);
            IEntityQuery iEntityQuery = this.entityDataService.newEntityQuery();
            iEntityQuery.setMasterKeys(dim.toDimensionValueSet());
            iEntityQuery.setEntityView(entityViewDefine);
            iEntityQuery.sorted(true);
            iEntityQuery.setAuthorityOperations(AuthorityType.Read);
            ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
            executorContext.setPeriodView(formScheme.getDateTime());
            try {
                IDataEntity iDataEntity = this.dataEntityFullService.executeEntityReader(iEntityQuery, executorContext, entityViewDefine, formSchemeKey);
                IEntityTable iEntityTable = iDataEntity.getEntityTable();
                if (null != iEntityTable) {
                    if (StringUtils.isEmpty((String)entityKeyData)) {
                        ITree<BaseDataInfo> virtualRootNode = this.buildVirtualRootNode(typeBaseDataKey);
                        List rootRows = iEntityTable.getRootRows();
                        for (IEntityRow rootRow : rootRows) {
                            ITree<BaseDataInfo> node = this.buildNode(rootRow, iEntityTable);
                            virtualRootNode.appendChild(node);
                        }
                        return virtualRootNode;
                    }
                    IEntityRow entityRow = iEntityTable.findByEntityKey(entityKeyData);
                    ITree<BaseDataInfo> node = this.buildNode(entityRow, iEntityTable);
                    List childRows = iEntityTable.getChildRows(entityKeyData);
                    for (IEntityRow childRow : childRows) {
                        ITree<BaseDataInfo> childNode = this.buildNode(childRow, iEntityTable);
                        node.appendChild(childNode);
                    }
                    return node;
                }
            }
            catch (Exception e) {
                logger.error(PromptConsts.causeIfError(e.getMessage()), e);
            }
        }
        return this.buildVirtualRootNode(typeBaseDataKey);
    }

    private ITree<BaseDataInfo> buildVirtualRootNode(String entityKey) {
        IEntityDefine entityDefine = this.entityMetaService.queryEntity(entityKey);
        BaseDataInfo baseDataInfo = new BaseDataInfo();
        baseDataInfo.setKey(ROOT);
        baseDataInfo.setCode(ROOT);
        baseDataInfo.setTitle(entityDefine.getTitle());
        ITree baseDataTreeInfo = new ITree((INode)baseDataInfo);
        baseDataTreeInfo.setLeaf(false);
        return baseDataTreeInfo;
    }

    private ITree<BaseDataInfo> buildNode(IEntityRow entityRow, IEntityTable entityTable) {
        String[] parentspath;
        BaseDataInfo baseDataInfo = new BaseDataInfo();
        baseDataInfo.setKey(entityRow.getEntityKeyData());
        baseDataInfo.setCode(entityRow.getCode());
        baseDataInfo.setTitle(entityRow.getTitle());
        baseDataInfo.setParentId(entityRow.getParentEntityKey());
        Object entityOrder = entityRow.getEntityOrder();
        if (entityOrder instanceof Double) {
            baseDataInfo.setOrder(((Double)entityOrder).doubleValue());
        }
        if ((parentspath = entityRow.getParentsEntityKeyDataPath()) != null && parentspath.length > 0) {
            baseDataInfo.setParents(Arrays.asList(parentspath));
        }
        ITree baseDataInfoITree = new ITree((INode)baseDataInfo);
        int directChildCount = entityTable.getDirectChildCount(entityRow.getEntityKeyData());
        baseDataInfoITree.setLeaf(directChildCount == 0);
        return baseDataInfoITree;
    }

    @Override
    public ITree<BaseDataInfo> positioningTypebaseData(DimensionCombination dim, String formSchemeKey, String entityKeyData) {
        String typeBaseDataKey = this.getTypeBaseDataKey();
        if (StringUtils.isNotEmpty((String)typeBaseDataKey)) {
            EntityViewDefine entityViewDefine = this.entityViewRunTimeController.buildEntityView(typeBaseDataKey);
            IEntityQuery iEntityQuery = this.entityDataService.newEntityQuery();
            iEntityQuery.setMasterKeys(dim.toDimensionValueSet());
            iEntityQuery.setEntityView(entityViewDefine);
            iEntityQuery.sorted(true);
            iEntityQuery.setAuthorityOperations(AuthorityType.Read);
            ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
            executorContext.setPeriodView(formScheme.getDateTime());
            try {
                IDataEntity iDataEntity = this.dataEntityFullService.executeEntityReader(iEntityQuery, executorContext, entityViewDefine, formSchemeKey);
                IEntityTable iEntityTable = iDataEntity.getEntityTable();
                if (null != iEntityTable) {
                    ITree<BaseDataInfo> virtualRootNode = this.buildVirtualRootNode(typeBaseDataKey);
                    IEntityRow entityRow = iEntityTable.findByEntityKey(entityKeyData);
                    String[] parentsEntityKeyDataPath = entityRow.getParentsEntityKeyDataPath();
                    if (parentsEntityKeyDataPath.length == 0) {
                        List rootRows = iEntityTable.getRootRows();
                        for (IEntityRow rootRow : rootRows) {
                            ITree<BaseDataInfo> node = this.buildNode(rootRow, iEntityTable);
                            if (rootRow.getEntityKeyData().equals(entityKeyData)) {
                                node.setSelected(true);
                            }
                            virtualRootNode.appendChild(node);
                        }
                        return virtualRootNode;
                    }
                    ITree nowNode = null;
                    List rootRows = iEntityTable.getRootRows();
                    for (IEntityRow rootRow : rootRows) {
                        ITree node = this.buildNode(rootRow, iEntityTable);
                        virtualRootNode.appendChild(node);
                        if (!rootRow.getEntityKeyData().equals(parentsEntityKeyDataPath[0])) continue;
                        nowNode = node;
                    }
                    if (null != nowNode) {
                        block4: for (int i = 0; i < parentsEntityKeyDataPath.length; ++i) {
                            List childRows = iEntityTable.getChildRows(parentsEntityKeyDataPath[i]);
                            for (IEntityRow childRow : childRows) {
                                ITree<BaseDataInfo> node = this.buildNode(childRow, iEntityTable);
                                nowNode.appendChild(node);
                            }
                            for (ITree child : nowNode.getChildren()) {
                                if (i + 1 == parentsEntityKeyDataPath.length) {
                                    if (!((BaseDataInfo)child.getData()).getKey().equals(entityKeyData)) continue;
                                    child.setSelected(true);
                                    continue;
                                }
                                if (!((BaseDataInfo)child.getData()).getKey().equals(parentsEntityKeyDataPath[i + 1])) continue;
                                nowNode = child;
                                continue block4;
                            }
                        }
                    }
                    return virtualRootNode;
                }
            }
            catch (Exception e) {
                logger.error(PromptConsts.causeIfError(e.getMessage()), e);
            }
        }
        return this.buildVirtualRootNode(typeBaseDataKey);
    }

    @Override
    public ITree<BaseDataInfo> searchTypeBaseData(DimensionCombination dim, String formSchemeKey, String searchInfo) {
        String typeBaseDataKey = this.getTypeBaseDataKey();
        if (StringUtils.isNotEmpty((String)typeBaseDataKey)) {
            IEntityDefine entityDefine = this.entityMetaService.queryEntity(typeBaseDataKey);
            String tableName = entityDefine.getCode();
            IEntityModel entityModel = this.metaService.getEntityModel(entityDefine.getId());
            IEntityAttribute nameField = entityModel.getNameField();
            IEntityAttribute codeField = entityModel.getCodeField();
            String expression = String.format("%s OR %s", String.format("POS('%s', %s[%s]) > 0", searchInfo, tableName, nameField.getCode()), String.format("POS('%s', %s[%s]) > 0", searchInfo, tableName, codeField.getCode()));
            EntityViewDefine entityViewDefine = this.entityViewRunTimeController.buildEntityView(typeBaseDataKey);
            IEntityQuery iEntityQuery = this.entityDataService.newEntityQuery();
            iEntityQuery.setExpression(expression);
            iEntityQuery.setMasterKeys(dim.toDimensionValueSet());
            iEntityQuery.setEntityView(entityViewDefine);
            iEntityQuery.sorted(true);
            iEntityQuery.setAuthorityOperations(AuthorityType.Read);
            ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
            executorContext.setPeriodView(formScheme.getDateTime());
            try {
                IDataEntity iDataEntity = this.dataEntityFullService.executeEntityReader(iEntityQuery, executorContext, entityViewDefine, formSchemeKey);
                IEntityTable iEntityTable = iDataEntity.getEntityTable();
                if (null != iEntityTable) {
                    ITree<BaseDataInfo> virtualRootNode = this.buildVirtualRootNode(typeBaseDataKey);
                    for (IEntityRow entityRow : iEntityTable.getAllRows()) {
                        ITree<BaseDataInfo> node = this.buildNode(entityRow, iEntityTable);
                        node.setLeaf(true);
                        virtualRootNode.appendChild(node);
                    }
                    return virtualRootNode;
                }
            }
            catch (Exception e) {
                logger.error(PromptConsts.causeIfError(e.getMessage()), e);
            }
        }
        return this.buildVirtualRootNode(typeBaseDataKey);
    }

    @Override
    public Map<String, IEntityRow> queryTypeEntityRow(DimensionCombination dim, String formSchemeKey, Set<String> entityKeyDatas) {
        String typeBaseDataKey = this.getTypeBaseDataKey();
        if (StringUtils.isNotEmpty((String)typeBaseDataKey)) {
            EntityViewDefine entityViewDefine = this.entityViewRunTimeController.buildEntityView(typeBaseDataKey);
            IEntityQuery iEntityQuery = this.entityDataService.newEntityQuery();
            iEntityQuery.setMasterKeys(dim.toDimensionValueSet());
            iEntityQuery.setEntityView(entityViewDefine);
            iEntityQuery.setAuthorityOperations(AuthorityType.Read);
            ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
            executorContext.setPeriodView(formScheme.getDateTime());
            try {
                IDataEntity iDataEntity = this.dataEntityFullService.executeEntityReader(iEntityQuery, executorContext, entityViewDefine, formSchemeKey);
                IEntityTable iEntityTable = iDataEntity.getEntityTable();
                if (null != iEntityTable) {
                    return iEntityTable.findByEntityKeys(entityKeyDatas);
                }
            }
            catch (Exception e) {
                logger.error(PromptConsts.causeIfError(e.getMessage()), e);
            }
        }
        return Collections.emptyMap();
    }
}

