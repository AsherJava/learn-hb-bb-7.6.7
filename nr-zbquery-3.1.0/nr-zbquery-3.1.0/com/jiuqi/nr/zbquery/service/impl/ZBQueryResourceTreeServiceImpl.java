/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.dataresource.DataResource
 *  com.jiuqi.nr.dataresource.DataResourceDefine
 *  com.jiuqi.nr.dataresource.DataResourceDefineGroup
 *  com.jiuqi.nr.dataresource.DataResourceKind
 *  com.jiuqi.nr.dataresource.DataResourceNode
 *  com.jiuqi.nr.dataresource.DimAttribute
 *  com.jiuqi.nr.dataresource.DimType
 *  com.jiuqi.nr.dataresource.NodeType
 *  com.jiuqi.nr.dataresource.TreeSearchQuery
 *  com.jiuqi.nr.dataresource.common.DataResourceConvert
 *  com.jiuqi.nr.dataresource.common.DataResourceException
 *  com.jiuqi.nr.dataresource.dto.DataResourceNodeDTO
 *  com.jiuqi.nr.dataresource.service.IDataLinkService
 *  com.jiuqi.nr.dataresource.service.IDataResourceDefineGroupService
 *  com.jiuqi.nr.dataresource.service.IDataResourceDefineService
 *  com.jiuqi.nr.dataresource.service.IDataResourceService
 *  com.jiuqi.nr.dataresource.service.IDataResourceTreeService
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.core.INode
 *  com.jiuqi.nr.datascheme.api.core.ITree
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.datascheme.common.NodeIconGetter
 *  com.jiuqi.nr.datascheme.internal.dto.DataFieldDTO
 *  com.jiuqi.nr.datascheme.internal.service.DataSchemeService
 *  com.jiuqi.nr.datascheme.internal.service.DataTableRelService
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.common.DataLinkType
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.model.ITreeStruct
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.common.utils.BqlTimeDimUtils
 *  com.jiuqi.nr.period.common.utils.NrPeriodConst
 *  com.jiuqi.nr.period.common.utils.PeriodTableColumn
 *  com.jiuqi.nr.period.common.utils.PeriodUtils
 *  com.jiuqi.nr.period.common.utils.TimeDimField
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.feign.client.BaseDataDefineClient
 *  com.jiuqi.va.feign.client.OrgCategoryClient
 */
package com.jiuqi.nr.zbquery.service.impl;

import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.dataresource.DataResource;
import com.jiuqi.nr.dataresource.DataResourceDefine;
import com.jiuqi.nr.dataresource.DataResourceDefineGroup;
import com.jiuqi.nr.dataresource.DataResourceKind;
import com.jiuqi.nr.dataresource.DataResourceNode;
import com.jiuqi.nr.dataresource.DimAttribute;
import com.jiuqi.nr.dataresource.DimType;
import com.jiuqi.nr.dataresource.TreeSearchQuery;
import com.jiuqi.nr.dataresource.common.DataResourceConvert;
import com.jiuqi.nr.dataresource.common.DataResourceException;
import com.jiuqi.nr.dataresource.dto.DataResourceNodeDTO;
import com.jiuqi.nr.dataresource.service.IDataLinkService;
import com.jiuqi.nr.dataresource.service.IDataResourceDefineGroupService;
import com.jiuqi.nr.dataresource.service.IDataResourceDefineService;
import com.jiuqi.nr.dataresource.service.IDataResourceService;
import com.jiuqi.nr.dataresource.service.IDataResourceTreeService;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.core.INode;
import com.jiuqi.nr.datascheme.api.core.ITree;
import com.jiuqi.nr.datascheme.api.core.NodeType;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.datascheme.common.NodeIconGetter;
import com.jiuqi.nr.datascheme.internal.dto.DataFieldDTO;
import com.jiuqi.nr.datascheme.internal.service.DataSchemeService;
import com.jiuqi.nr.datascheme.internal.service.DataTableRelService;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.model.ITreeStruct;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.common.utils.BqlTimeDimUtils;
import com.jiuqi.nr.period.common.utils.NrPeriodConst;
import com.jiuqi.nr.period.common.utils.PeriodTableColumn;
import com.jiuqi.nr.period.common.utils.PeriodUtils;
import com.jiuqi.nr.period.common.utils.TimeDimField;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.zbquery.bean.facade.IResourceTreeNode;
import com.jiuqi.nr.zbquery.bean.impl.ChildQueryDimension;
import com.jiuqi.nr.zbquery.bean.impl.ResourceTreeNode2DTO;
import com.jiuqi.nr.zbquery.bean.impl.ResourceTreeNodeDTO;
import com.jiuqi.nr.zbquery.bean.impl.SearchTreeNodeDTO;
import com.jiuqi.nr.zbquery.bean.impl.SelectedFieldDefineEx;
import com.jiuqi.nr.zbquery.bean.impl.SpecialQueryDimension;
import com.jiuqi.nr.zbquery.bean.impl.ZBFieldEx;
import com.jiuqi.nr.zbquery.model.DimensionAttributeField;
import com.jiuqi.nr.zbquery.model.FieldGroup;
import com.jiuqi.nr.zbquery.model.FieldGroupType;
import com.jiuqi.nr.zbquery.model.FormulaField;
import com.jiuqi.nr.zbquery.model.QueryDimension;
import com.jiuqi.nr.zbquery.model.QueryDimensionType;
import com.jiuqi.nr.zbquery.model.QueryObject;
import com.jiuqi.nr.zbquery.model.QueryObjectType;
import com.jiuqi.nr.zbquery.model.ZBQueryModel;
import com.jiuqi.nr.zbquery.rest.param.ResourceTreeQueryParam;
import com.jiuqi.nr.zbquery.rest.param.SearchNodeQueryParam;
import com.jiuqi.nr.zbquery.service.ZBQueryResourceTreeService;
import com.jiuqi.nr.zbquery.util.DataChangeUtils;
import com.jiuqi.nr.zbquery.util.EnumTransfer;
import com.jiuqi.nr.zbquery.util.FullNameWrapper;
import com.jiuqi.nr.zbquery.util.ResTreeUtils;
import com.jiuqi.nr.zbquery.util.ZBQueryI18nUtils;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.feign.client.BaseDataDefineClient;
import com.jiuqi.va.feign.client.OrgCategoryClient;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Service
public class ZBQueryResourceTreeServiceImpl
implements ZBQueryResourceTreeService<IResourceTreeNode> {
    private final Logger logger = LoggerFactory.getLogger(ZBQueryResourceTreeServiceImpl.class);
    @Autowired
    private IDataResourceTreeService<DataResourceNode> treeService;
    @Autowired
    private IDataResourceDefineService defineService;
    @Autowired
    private IDataResourceDefineGroupService defineGroupService;
    @Autowired
    private IDataLinkService linkService;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IDataResourceService dataResourceService;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private DataSchemeService dataSchemeService;
    @Autowired
    private BaseDataDefineClient baseDataDefineClient;
    @Autowired
    private OrgCategoryClient orgCategoryClient;
    @Autowired
    private DataTableRelService dataTableRelService;
    @Autowired
    INvwaSystemOptionService nvwaSystemOptionService;
    @Autowired
    private IRunTimeViewController viewController;
    private final Map<String, DataScheme> defaultDataSchemes = new HashMap<String, DataScheme>();

    @Override
    public List<ITree<IResourceTreeNode>> queryResourceTreePath(IResourceTreeNode resourceTreeNode) {
        ArrayList<ITree<IResourceTreeNode>> iTreeList = new ArrayList<ITree<IResourceTreeNode>>();
        Assert.notNull((Object)resourceTreeNode, "node must not be null");
        int type = resourceTreeNode.getType();
        ResourceTreeNodeDTO allDataTreeNode = new ResourceTreeNodeDTO();
        allDataTreeNode.setDisplayTiered(this.isDisplayTiered());
        allDataTreeNode.setKey("00000000-0000-0000-0000-000000000000");
        List treeGroups = this.treeService.getGroupSpecifiedTree(com.jiuqi.nr.dataresource.NodeType.TREE_GROUP, (DataResourceNode)allDataTreeNode.change2DataResourceNode());
        if (CollectionUtils.isEmpty(treeGroups)) {
            return iTreeList;
        }
        ITree allDataResourceNodeITree = (ITree)treeGroups.get(0);
        ITree<IResourceTreeNode> allTreeGroupNode = this.change2ResourceTreeITree((ITree<DataResourceNode>)allDataResourceNodeITree, resourceTreeNode);
        if (!ObjectUtils.isEmpty(allTreeGroupNode)) {
            allTreeGroupNode.getChildren().addAll(this.defineService.getByGroupKey(allDataResourceNodeITree.getKey()).stream().map(dataResourceDefine -> this.change2ResourceTreeITree((DataResourceDefine)dataResourceDefine, (IResourceTreeNode)allTreeGroupNode.getData())).collect(Collectors.toList()));
            this.queryToResourceDefine(allTreeGroupNode);
            if (type == 0) {
                iTreeList.addAll(allTreeGroupNode.getChildren());
            } else {
                ITree<IResourceTreeNode> treenode = this.filterResourceTreeNode(allTreeGroupNode, resourceTreeNode);
                if (treenode != null) {
                    iTreeList.add(treenode);
                }
            }
        }
        if (iTreeList.size() == 1) {
            ((ITree)iTreeList.get(0)).setExpanded(true);
        }
        return iTreeList;
    }

    private ITree<IResourceTreeNode> filterResourceTreeNode(ITree<IResourceTreeNode> parentTreeGroupNode, IResourceTreeNode filterNode) {
        for (ITree child : parentTreeGroupNode.getChildren()) {
            if (Objects.equals(ResTreeUtils.getRealKey(filterNode.getKey()), ResTreeUtils.getRealKey(child.getKey())) && ((IResourceTreeNode)child.getData()).getType() == filterNode.getType()) {
                return child;
            }
            ITree<IResourceTreeNode> filterResourceTreeNode = this.filterResourceTreeNode((ITree<IResourceTreeNode>)child, filterNode);
            if (ObjectUtils.isEmpty(filterResourceTreeNode)) continue;
            return filterResourceTreeNode;
        }
        return null;
    }

    private void queryToResourceDefine(ITree<IResourceTreeNode> itree) {
        itree.getChildren().forEach(childITree -> {
            IResourceTreeNode childITreeNode = (IResourceTreeNode)childITree.getData();
            int type = childITreeNode.getType();
            if (type == com.jiuqi.nr.dataresource.NodeType.TREE_GROUP.getValue()) {
                ArrayList iTreeChildren = new ArrayList();
                iTreeChildren.addAll(this.treeService.getGroupChildTree(com.jiuqi.nr.dataresource.NodeType.TREE_GROUP, childITreeNode.change2DataResourceNode()).stream().map(treeGroupNode -> this.change2ResourceTreeITree((ITree<DataResourceNode>)treeGroupNode, childITreeNode)).collect(Collectors.toList()));
                iTreeChildren.addAll(this.defineService.getByGroupKey(ResTreeUtils.getRealKey(childITreeNode.getKey())).stream().map(dataResourceDefine -> this.change2ResourceTreeITree((DataResourceDefine)dataResourceDefine, childITreeNode)).collect(Collectors.toList()));
                childITree.setChildren(iTreeChildren);
                this.queryToResourceDefine((ITree<IResourceTreeNode>)childITree);
            }
        });
    }

    @Override
    public List<ITree<IResourceTreeNode>> queryResourceTreeChildren(IResourceTreeNode resourceTreeNode) {
        ArrayList<ITree<IResourceTreeNode>> iTreeList;
        block7: {
            int type;
            block9: {
                List dimAttributeByGroup;
                block10: {
                    block11: {
                        block8: {
                            block6: {
                                iTreeList = new ArrayList<ITree<IResourceTreeNode>>();
                                type = resourceTreeNode.getType();
                                if (type != com.jiuqi.nr.dataresource.NodeType.RESOURCE_GROUP.getValue()) break block6;
                                List fields = this.linkService.getByGroup(ResTreeUtils.getRealKey(resourceTreeNode.getKey())).stream().map(dataField -> this.change2ResourceTreeITree((DataField)dataField, resourceTreeNode)).collect(Collectors.toList());
                                List groups = this.treeService.getChildTree(null, resourceTreeNode.change2DataResourceNode()).stream().map(resGroupNode -> this.change2ResourceTreeITree((ITree<DataResourceNode>)resGroupNode, resourceTreeNode)).collect(Collectors.toList());
                                iTreeList.addAll(groups);
                                iTreeList.addAll(fields);
                                break block7;
                            }
                            if (type != com.jiuqi.nr.dataresource.NodeType.MD_INFO.getValue()) break block8;
                            List fields = this.linkService.getByGroup(ResTreeUtils.getRealKey(resourceTreeNode.getKey())).stream().map(dataField -> this.change2ResourceTreeITree((DataField)dataField, resourceTreeNode)).collect(Collectors.toList());
                            iTreeList.addAll(fields);
                            break block7;
                        }
                        if (type != com.jiuqi.nr.dataresource.NodeType.DIM_GROUP.getValue() && type != com.jiuqi.nr.dataresource.NodeType.TABLE_DIM_GROUP.getValue()) break block9;
                        QueryDimension queryDimension = (QueryDimension)resourceTreeNode.getQueryObject();
                        dimAttributeByGroup = this.linkService.getDimAttributeByGroup(ResTreeUtils.getRealKey(resourceTreeNode.getKey()));
                        if (queryDimension.getDimensionType() != QueryDimensionType.PERIOD) break block10;
                        if (queryDimension.getPeriodType() != PeriodType.WEEK) break block11;
                        queryDimension.setSpecialPeriodType(true);
                        DataChangeUtils.getZPeriodChildDimFields(queryDimension).forEach(timeDimField -> iTreeList.add(this.change2ResourceTreeITree((TimeDimField)timeDimField, resourceTreeNode)));
                        break block7;
                    }
                    if (CollectionUtils.isEmpty(dimAttributeByGroup)) break block7;
                    Map<String, TimeDimField> dimFieldMap = BqlTimeDimUtils.getTimeDimFields((String)resourceTreeNode.getDimKey(), null).stream().collect(Collectors.toMap(TimeDimField::getName, timeDimField -> timeDimField));
                    dimAttributeByGroup.stream().filter(dimAttribute -> !dimAttribute.isHidden()).forEach(dimAttribute -> {
                        String code = dimAttribute.getCode();
                        if (dimFieldMap.containsKey(code)) {
                            TimeDimField timeDimField = (TimeDimField)dimFieldMap.get(code);
                            iTreeList.add(this.change2ResourceTreeITree(timeDimField, resourceTreeNode));
                        }
                    });
                    break block7;
                }
                if (CollectionUtils.isEmpty(dimAttributeByGroup)) break block7;
                iTreeList.addAll(dimAttributeByGroup.stream().filter(dimAttribute -> !dimAttribute.isHidden()).map(dimAttribute -> this.change2ResourceTreeITree((DimAttribute)dimAttribute, resourceTreeNode)).collect(Collectors.toList()));
                break block7;
            }
            if (type == com.jiuqi.nr.dataresource.NodeType.DIM_FMDM_GROUP.getValue()) {
                List dimAttributeByGroup = this.linkService.getFmDimAttribute(resourceTreeNode.getDefineKey(), ResTreeUtils.getRealKey(resourceTreeNode.getKey()), resourceTreeNode.getDimKey());
                if (!CollectionUtils.isEmpty(dimAttributeByGroup)) {
                    iTreeList.addAll(dimAttributeByGroup.stream().filter(dimAttribute -> !dimAttribute.isHidden()).map(dimAttribute -> this.change2ResourceTreeITree((DimAttribute)dimAttribute, resourceTreeNode)).collect(Collectors.toList()));
                }
            } else if (type == com.jiuqi.nr.dataresource.NodeType.TREE.getValue()) {
                List specifiedTrees = this.treeService.getSpecifiedTree(null, null, ResTreeUtils.getRealKey(resourceTreeNode.getKey()));
                for (int i = 0; i < specifiedTrees.size(); ++i) {
                    ITree treeNode = (ITree)specifiedTrees.get(i);
                    List children = treeNode.getChildren();
                    for (int j = 0; j < children.size(); ++j) {
                        iTreeList.add(this.change2ResourceTreeITree((ITree<DataResourceNode>)((ITree)children.get(j)), resourceTreeNode));
                    }
                }
            }
        }
        return iTreeList;
    }

    @Override
    public List<ITree<IResourceTreeNode>> queryAllResourceTreeChildren(IResourceTreeNode resourceTreeNode) {
        List<ITree<IResourceTreeNode>> children = this.queryResourceTreeChildren(resourceTreeNode);
        children.forEach(this::queryAllResourceTreeChildren);
        return children;
    }

    private void queryAllResourceTreeChildren(ITree<IResourceTreeNode> itree) {
        IResourceTreeNode resourceTreeNode = (IResourceTreeNode)itree.getData();
        int type = resourceTreeNode.getType();
        if (type == com.jiuqi.nr.dataresource.NodeType.TREE.getValue() || type == com.jiuqi.nr.dataresource.NodeType.TREE_GROUP.getValue() || type == com.jiuqi.nr.dataresource.NodeType.RESOURCE_GROUP.getValue()) {
            List<ITree<IResourceTreeNode>> children = this.queryResourceTreeChildren(resourceTreeNode);
            itree.setChildren(children);
            children.forEach(this::queryAllResourceTreeChildren);
        }
    }

    @Override
    public List<SearchTreeNodeDTO> filterResourceTree(SearchNodeQueryParam param) {
        ArrayList<SearchTreeNodeDTO> nodes = new ArrayList<SearchTreeNodeDTO>();
        String filter = param.getFilter();
        Assert.notNull((Object)filter, "filter must not be null");
        if (!CollectionUtils.isEmpty(param.getDefineKeys())) {
            param.getDefineKeys().parallelStream().forEach(defineKeyID -> {
                String[] splits = defineKeyID.split("_");
                String defineKey = splits[splits.length - 1];
                TreeSearchQuery treeSearchQuery = new TreeSearchQuery();
                treeSearchQuery.setDefineKey(defineKey);
                treeSearchQuery.setKeyword(filter);
                treeSearchQuery.setSearchType(com.jiuqi.nr.dataresource.NodeType.TREE.getValue() | com.jiuqi.nr.dataresource.NodeType.DIM_GROUP.getValue() | com.jiuqi.nr.dataresource.NodeType.RESOURCE_GROUP.getValue());
                List dataResourceNodes = this.treeService.search(treeSearchQuery);
                dataResourceNodes.forEach(dataResourceNode -> {
                    SearchTreeNodeDTO searchTreeNode = new SearchTreeNodeDTO((DataResourceNode)dataResourceNode);
                    ArrayList<String> keyPath = new ArrayList<String>();
                    ArrayList<String> titlePath = new ArrayList<String>();
                    if (dataResourceNode.getType() == com.jiuqi.nr.dataresource.NodeType.TREE.getValue()) {
                        DataResourceDefine dataResourceDefine = this.defineService.get(dataResourceNode.getKey());
                        String defineId = ResTreeUtils.generateKey(dataResourceDefine);
                        keyPath.add(defineId);
                        titlePath.add(dataResourceDefine.getTitle());
                        String groupKey = dataResourceDefine.getGroupKey();
                        if (!"00000000-0000-0000-0000-000000000000".equals(groupKey)) {
                            this.findTreeNodePath4TreeGroup(groupKey, keyPath, titlePath);
                        }
                    } else {
                        this.findTreeNodePath4ResGroup(dataResourceNode.getKey(), keyPath, titlePath);
                    }
                    Collections.reverse(keyPath);
                    Collections.reverse(titlePath);
                    searchTreeNode.setKeyPath(keyPath);
                    searchTreeNode.setTitlePath(titlePath);
                    nodes.add(searchTreeNode);
                });
                List searchDataFields = this.linkService.searchByDefineKey(defineKey, filter);
                searchDataFields.forEach(searchDataField -> {
                    DataField dataField = this.runtimeDataSchemeService.getDataField(searchDataField.getKey());
                    DataResourceNodeDTO group = searchDataField.getGroup();
                    ResourceTreeNodeDTO resourceTreeNode = new ResourceTreeNodeDTO((DataResourceNode)group, null);
                    resourceTreeNode.setDisplayTiered(this.isDisplayTiered());
                    SearchTreeNodeDTO searchTreeNode = new SearchTreeNodeDTO(dataField, resourceTreeNode);
                    searchTreeNode.setTitle(searchDataField.getTitle());
                    ArrayList<String> keyPath = new ArrayList<String>();
                    ArrayList<String> titlePath = new ArrayList<String>();
                    keyPath.add(searchTreeNode.getKey());
                    titlePath.add(searchDataField.getTitle());
                    this.findTreeNodePath4ResGroup(group.getKey(), keyPath, titlePath);
                    Collections.reverse(keyPath);
                    Collections.reverse(titlePath);
                    searchTreeNode.setKeyPath(keyPath);
                    searchTreeNode.setTitlePath(titlePath);
                    nodes.add(searchTreeNode);
                });
            });
        }
        return nodes;
    }

    private ITree<IResourceTreeNode> change2ResourceTreeITree(ITree<DataResourceNode> dataResourceITree, IResourceTreeNode parentResourceTreeNode) {
        String linkZb;
        DataField dataField;
        DataResourceNode dataResourceNode = (DataResourceNode)dataResourceITree.getData();
        ResourceTreeNodeDTO resourceTreeNode = new ResourceTreeNodeDTO(dataResourceNode, parentResourceTreeNode);
        resourceTreeNode.setDisplayTiered(this.isDisplayTiered());
        int type = dataResourceNode.getType();
        if (type == com.jiuqi.nr.dataresource.NodeType.DIM_GROUP.getValue() || type == com.jiuqi.nr.dataresource.NodeType.TABLE_DIM_GROUP.getValue() || type == com.jiuqi.nr.dataresource.NodeType.DIM_FMDM_GROUP.getValue()) {
            DataResource dataResource;
            String dataSchemeKey;
            if (parentResourceTreeNode.getType() == com.jiuqi.nr.dataresource.NodeType.TREE.getValue() && StringUtils.hasLength(dataSchemeKey = (dataResource = this.dataResourceService.get(dataResourceNode.getKey())).getDataSchemeKey())) {
                this.putDataScheme(dataResource.getResourceDefineKey(), dataSchemeKey);
            }
            QueryDimension queryDimension = this.change2QueryDimension(resourceTreeNode, parentResourceTreeNode, null, null);
            resourceTreeNode.setCode(queryDimension.getName());
            resourceTreeNode.setQueryObject(queryDimension);
        } else if (type == com.jiuqi.nr.dataresource.NodeType.MD_INFO.getValue()) {
            DataTable mdinfoTable = this.isMDINFOTable(dataResourceNode);
            if (mdinfoTable != null) {
                QueryDimension queryDimension = this.createMdInfoQueryDimesion(resourceTreeNode, parentResourceTreeNode, mdinfoTable);
                resourceTreeNode.setCode(queryDimension.getName());
                resourceTreeNode.setQueryObject(queryDimension);
            }
        } else if (DataChangeUtils.isRelZBNode(dataResourceNode) && (dataField = this.runtimeDataSchemeService.getDataField(linkZb = dataResourceNode.getLinkZb())) != null) {
            ITree<IResourceTreeNode> iResourceTreeNodeITree = this.change2ResourceTreeITree(dataField, parentResourceTreeNode);
            QueryObject queryObject = ((IResourceTreeNode)iResourceTreeNodeITree.getData()).getQueryObject();
            resourceTreeNode.setCode(queryObject.getName());
            resourceTreeNode.setQueryObject(queryObject);
        }
        ITree<IResourceTreeNode> resourceTreeITree = this.chang2ResourceTreeITree(resourceTreeNode);
        List children = dataResourceITree.getChildren();
        if (!CollectionUtils.isEmpty(children)) {
            resourceTreeITree.getChildren().addAll(children.stream().map(child -> this.change2ResourceTreeITree((ITree<DataResourceNode>)child, (IResourceTreeNode)resourceTreeNode)).collect(Collectors.toList()));
        }
        if (type == com.jiuqi.nr.dataresource.NodeType.DIM_GROUP.getValue() || type == com.jiuqi.nr.dataresource.NodeType.TABLE_DIM_GROUP.getValue() || type == com.jiuqi.nr.dataresource.NodeType.DIM_FMDM_GROUP.getValue() || type == com.jiuqi.nr.dataresource.NodeType.MD_INFO.getValue()) {
            resourceTreeITree.getChildren().addAll(this.queryResourceTreeChildren(resourceTreeNode));
        }
        return resourceTreeITree;
    }

    private QueryDimension createMdInfoQueryDimesion(ResourceTreeNodeDTO resourceTreeNode, IResourceTreeNode parentResourceTreeNode, DataTable mdinfoTable) {
        QueryDimension queryDimension = new QueryDimension();
        queryDimension.setId(resourceTreeNode.getKey());
        queryDimension.setTitle(resourceTreeNode.getTitle());
        queryDimension.setType(QueryObjectType.DIMENSION);
        queryDimension.setDimensionType(QueryDimensionType.MDINFO);
        String schemeName = "";
        if (StringUtils.hasLength(mdinfoTable.getDataSchemeKey())) {
            DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(mdinfoTable.getDataSchemeKey());
            schemeName = dataScheme.getCode();
            queryDimension.setSchemeName(schemeName);
        }
        String fullName = mdinfoTable.getCode();
        queryDimension.setFullName(fullName);
        queryDimension.setName(fullName);
        queryDimension.setMessageAlias(fullName);
        queryDimension.setParent(null);
        return queryDimension;
    }

    private QueryDimension change2QueryDimension(ResourceTreeNodeDTO dimResourceTreeNode, IResourceTreeNode parentTreeNode, String schemeName0, QueryDimensionType queryDimensionType) {
        IEntityModel entityModel;
        String schemeName;
        QueryDimension queryDimension = new QueryDimension();
        if (queryDimensionType == QueryDimensionType.MDINFO) {
            queryDimension = new SpecialQueryDimension();
        }
        queryDimension.setId(dimResourceTreeNode.getKey());
        queryDimension.setTitle(dimResourceTreeNode.getTitle());
        queryDimension.setType(QueryObjectType.DIMENSION);
        String string = schemeName = StringUtils.hasLength(schemeName0) ? schemeName0 : this.getDefaultSchemeName(parentTreeNode);
        if (StringUtils.hasLength(dimResourceTreeNode.getDataSchemeKey())) {
            DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(dimResourceTreeNode.getDataSchemeKey());
            schemeName = dataScheme.getCode();
        }
        queryDimension.setSchemeName(schemeName);
        String dimKey = dimResourceTreeNode.getDimKey();
        IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
        boolean isPeriodEntity = periodAdapter.isPeriodEntity(dimKey);
        if (isPeriodEntity) {
            DataScheme dataScheme;
            queryDimension.setDimensionType(QueryDimensionType.PERIOD);
            IPeriodEntity periodEntity = periodAdapter.getPeriodEntity(dimKey);
            queryDimension.setPeriodType(periodEntity.getPeriodType());
            if (PeriodUtils.isPeriod13((String)periodEntity.getCode(), (PeriodType)periodEntity.getPeriodType())) {
                queryDimension.setMinFiscalMonth(periodEntity.getMinFiscalMonth());
                queryDimension.setMaxFiscalMonth(periodEntity.getMaxFiscalMonth());
            }
            String timeDimTable = BqlTimeDimUtils.getBqlTimeDimTable((String)periodEntity.getCode());
            queryDimension.setFullName(timeDimTable);
            queryDimension.setName(timeDimTable);
            if (!StringUtils.hasLength(dimResourceTreeNode.getTitle())) {
                queryDimension.setTitle(periodEntity.getTitle());
            }
            PeriodType periodType = periodEntity.getPeriodType();
            if (periodEntity.getPeriodType() != PeriodType.CUSTOM && !ObjectUtils.isEmpty(dataScheme = this.runtimeDataSchemeService.getDataSchemeByCode(schemeName))) {
                queryDimension.setEnableAdjust(this.runtimeDataSchemeService.enableAdjustPeriod(dataScheme.getKey()));
            }
            if (queryDimension.getPeriodType() == PeriodType.WEEK || queryDimension.getPeriodType() == PeriodType.CUSTOM) {
                queryDimension.setSpecialPeriodType(true);
            }
        } else {
            if (ObjectUtils.isEmpty((Object)queryDimensionType)) {
                DataScheme dataScheme;
                queryDimension.setDimensionType(QueryDimensionType.MASTER);
                if (parentTreeNode != null && parentTreeNode.getDefineKey() != null && dimKey != null) {
                    DimType dimType = this.defineService.determineByDimKey(parentTreeNode.getDefineKey(), dimKey);
                    if (DimType.DIMENSION == dimType) {
                        queryDimension.setDimensionType(QueryDimensionType.SCENE);
                    }
                } else if (StringUtils.hasLength(schemeName) && (dataScheme = this.runtimeDataSchemeService.getDataSchemeByCode(schemeName)) != null) {
                    List dataSchemeDimensions = this.runtimeDataSchemeService.getDataSchemeDimension(dataScheme.getKey());
                    for (Object dataDimension : dataSchemeDimensions) {
                        if (!dataDimension.getDimKey().equals(dimKey)) continue;
                        DimensionType dimensionType = dataDimension.getDimensionType();
                        if (dimensionType == DimensionType.DIMENSION) {
                            queryDimension.setDimensionType(QueryDimensionType.SCENE);
                        } else {
                            queryDimension.setDimensionType(QueryDimensionType.MASTER);
                        }
                        break;
                    }
                }
            } else {
                queryDimension.setDimensionType(queryDimensionType);
            }
            IEntityDefine entity = this.entityMetaService.queryEntity(dimKey);
            if (Objects.nonNull(entity)) {
                ITreeStruct treeStruct = entity.getTreeStruct();
                queryDimension.setTreeStructure(entity.isTree());
                queryDimension.setFullName(entity.getCode());
                queryDimension.setName(entity.getCode());
                queryDimension.setEnableVersion(new Integer(1).equals(entity.getVersion()));
                if (dimKey != null && dimKey.endsWith("@ORG")) {
                    queryDimension.setOrgDimension(true);
                } else if (dimKey != null && dimKey.endsWith("@CB")) {
                    queryDimension.setCalibreDimension(true);
                }
                if (!StringUtils.hasLength(dimResourceTreeNode.getTitle())) {
                    queryDimension.setTitle(entity.getTitle());
                }
                queryDimension.setIsolation(entity.getIsolation());
            }
            try {
                if (dimKey != null && dimKey.endsWith("@CB")) {
                    queryDimension.setBizKey("CODE");
                } else {
                    entityModel = this.entityMetaService.getEntityModel(dimKey);
                    IEntityAttribute bizKeyField = entityModel.getBizKeyField();
                    queryDimension.setBizKey(bizKeyField.getName());
                }
            }
            catch (Exception e) {
                this.logger.error(e.getMessage(), e);
            }
        }
        if (dimResourceTreeNode.getType() == com.jiuqi.nr.dataresource.NodeType.TABLE_DIM_GROUP.getValue()) {
            DataField dataField;
            queryDimension.setDimensionType(QueryDimensionType.INNER);
            if (StringUtils.hasLength(dimResourceTreeNode.getSource()) && !ObjectUtils.isEmpty(dataField = this.runtimeDataSchemeService.getDataField(dimResourceTreeNode.getSource()))) {
                queryDimension.setRelatedPublicParameter(dataField.getRefParameter());
                queryDimension.setTitle(dataField.getTitle());
                List schemeDimensions = this.runtimeDataSchemeService.getDataSchemeDimension(dataField.getDataSchemeKey());
                boolean mainOrg = false;
                for (DataDimension dataDimension : schemeDimensions) {
                    if (dataDimension.getDimensionType() != DimensionType.UNIT || !dataDimension.getDimKey().endsWith("@ORG")) continue;
                    mainOrg = true;
                    break;
                }
                if (mainOrg && queryDimension.isOrgDimension()) {
                    String fullName = dataField.getCode() + "_" + "MD_ORG";
                    queryDimension.setFullName(fullName);
                    queryDimension.setName(fullName);
                    queryDimension.setEntityId(dimKey);
                    dimResourceTreeNode.setCode(fullName);
                } else {
                    DataTable dataTable = this.runtimeDataSchemeService.getDataTable(dataField.getDataTableKey());
                    ArrayList dimKeys = new ArrayList();
                    dimKeys.addAll(this.runtimeDataSchemeService.getDataFieldByTableKeyAndKind(dataTable.getKey(), new DataFieldKind[]{DataFieldKind.TABLE_FIELD_DIM}).stream().filter(dataField1 -> !Objects.equals(dataField1.getKey(), dataField.getKey()) && DataChangeUtils.isChangeTableDim(dataField1)).map(DataField::getRefDataEntityKey).filter(StringUtils::hasLength).collect(Collectors.toList()));
                    dimKeys.addAll(schemeDimensions.stream().map(DataDimension::getDimKey).collect(Collectors.toList()));
                    if (dimKeys.stream().anyMatch(dimKey1 -> dimKey1.equals(dimKey))) {
                        String fullName = dataField.getCode() + "_" + queryDimension.getFullName();
                        queryDimension.setFullName(fullName);
                        queryDimension.setEntityId(dimKey);
                        queryDimension.setName(fullName);
                        dimResourceTreeNode.setCode(fullName);
                    }
                }
            }
        }
        queryDimension.setMessageAlias(FullNameWrapper.getMessageAlias(queryDimension));
        if (QueryDimensionType.SCENE == queryDimension.getDimensionType() && "MD_CURRENCY".equals(queryDimension.getFullName())) {
            DataResourceDefine dataResourceDefine;
            String dw = dimResourceTreeNode.getDw();
            if (!StringUtils.hasLength(dw) && StringUtils.hasLength(dimResourceTreeNode.getDefineKey()) && !ObjectUtils.isEmpty(dataResourceDefine = this.defineService.get(dimResourceTreeNode.getDefineKey()))) {
                dw = dataResourceDefine.getDimKey();
            }
            if (StringUtils.hasLength(dw)) {
                entityModel = this.entityMetaService.getEntityModel(dw);
                Iterator attributes = entityModel.getAttributes();
                while (attributes.hasNext()) {
                    IEntityAttribute attribute = (IEntityAttribute)attributes.next();
                    if (!"CURRENCYID".equals(attribute.getCode())) continue;
                    queryDimension.setEnableStandardCurrency(true);
                    break;
                }
            }
        }
        return queryDimension;
    }

    private ITree<IResourceTreeNode> change2ResourceTreeITree(DataField dataField, IResourceTreeNode parentResourceTreeNode) {
        this.putDataScheme(ResTreeUtils.getRealKey(parentResourceTreeNode.getKey()), dataField.getDataSchemeKey());
        ResourceTreeNodeDTO resourceTreeNode = new ResourceTreeNodeDTO(dataField, parentResourceTreeNode);
        resourceTreeNode.setDisplayTiered(this.isDisplayTiered());
        if (dataField.getDataFieldKind() == DataFieldKind.TABLE_FIELD_DIM && DataChangeUtils.isChangeTableDim(dataField)) {
            resourceTreeNode.setQueryObject(this.change2QueryDimension(dataField, resourceTreeNode));
        } else {
            DataResourceDefine dataResourceDefine;
            boolean md_info;
            DataTable dataTable = this.runtimeDataSchemeService.getDataTable(dataField.getDataTableKey());
            boolean bl = md_info = dataTable != null && dataTable.getDataTableType().equals((Object)DataTableType.MD_INFO);
            if (md_info && StringUtils.hasLength(dataField.getRefDataEntityKey())) {
                return this.changeMDInfoZB2TreeNode(dataField, parentResourceTreeNode);
            }
            resourceTreeNode.setType(com.jiuqi.nr.dataresource.NodeType.FIELD_LINK.getValue());
            ZBFieldEx zbFieldEx = this.change2ZBField(dataField, resourceTreeNode);
            if (StringUtils.hasLength(parentResourceTreeNode.getDefineKey()) && (dataResourceDefine = this.defineService.get(parentResourceTreeNode.getDefineKey())) != null) {
                String dimKey = dataResourceDefine.getDimKey();
                Map<String, QueryDimensionType> relatedDimensionMap = zbFieldEx.getRelatedDimensionMap();
                LinkedHashMap<String, QueryDimensionType> relatedDimensions = new LinkedHashMap<String, QueryDimensionType>();
                relatedDimensions.put(dimKey, relatedDimensionMap.get(dimKey));
                relatedDimensions.putAll(relatedDimensionMap);
                zbFieldEx.setRelatedDimensionMap(relatedDimensions);
            }
            resourceTreeNode.setQueryObject(zbFieldEx);
        }
        return this.chang2ResourceTreeITree(resourceTreeNode);
    }

    private SpecialQueryDimension createSpecialQueryDimByMdinfo(DataField dataField, IResourceTreeNode parentResourceTreeNode) {
        SpecialQueryDimension queryDimension = new SpecialQueryDimension();
        queryDimension.setTitle(dataField.getTitle());
        queryDimension.setType(QueryObjectType.DIMENSION);
        String schemeName = "";
        if (StringUtils.hasLength(dataField.getDataSchemeKey())) {
            DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(dataField.getDataSchemeKey());
            schemeName = dataScheme.getCode();
        }
        queryDimension.setSchemeName(schemeName);
        queryDimension.setEntityId(dataField.getRefDataEntityKey());
        DataTable dataTable = this.runtimeDataSchemeService.getDataTable(dataField.getDataTableKey());
        String enumName = dataField.getRefDataEntityKey().indexOf("@") > -1 ? dataField.getRefDataEntityKey().split("@")[0] : null;
        String fullName = dataTable.getCode() + "." + dataField.getCode() + "." + enumName;
        queryDimension.setFullName(fullName);
        queryDimension.setName(enumName);
        queryDimension.setDimensionType(QueryDimensionType.CHILD);
        queryDimension.setParent(dataTable.getCode());
        queryDimension.setDimAttribute(DataChangeUtils.change2DimAttribute(queryDimension, dataTable.getCode() + "." + dataField.getCode(), dataField.getCode(), enumName));
        LinkedHashMap<String, QueryDimensionType> relatedDimensionMap = new LinkedHashMap<String, QueryDimensionType>();
        DataChangeUtils.handleDataSchemeDimension(dataField.getDataSchemeKey(), dataField.getKey(), null, relatedDimensionMap);
        queryDimension.setRelatedDimensionMap(relatedDimensionMap);
        queryDimension.setOrgDimension(dataField.getRefDataEntityKey().endsWith("@ORG"));
        queryDimension.setMessageAlias(FullNameWrapper.getMessageAlias(queryDimension));
        queryDimension.setRelatedPublicParameter(dataField.getRefParameter());
        return queryDimension;
    }

    private ITree<IResourceTreeNode> changeMDInfoZB2TreeNode(DataField dataField, IResourceTreeNode parentResourceTreeNode) {
        String id = "nrfs_dim_" + dataField.getKey();
        String dimKey = dataField.getRefDataEntityKey();
        ResourceTreeNodeDTO dimTreeNodeDTO = new ResourceTreeNodeDTO(dataField, parentResourceTreeNode);
        dimTreeNodeDTO.setDisplayTiered(this.isDisplayTiered());
        dimTreeNodeDTO.setType(com.jiuqi.nr.dataresource.NodeType.DIM_GROUP.getValue());
        dimTreeNodeDTO.setDimKey(dimKey);
        SpecialQueryDimension queryDimension = this.createSpecialQueryDimByMdinfo(dataField, parentResourceTreeNode);
        queryDimension.setId(dimTreeNodeDTO.getKey());
        dimTreeNodeDTO.setQueryObject(queryDimension);
        dimTreeNodeDTO.setCode(queryDimension.getName());
        dimTreeNodeDTO.setTitle(queryDimension.getTitle());
        ITree<IResourceTreeNode> newITree = this.chang2ResourceTreeITree(dimTreeNodeDTO);
        List<Object> childrenITree = new ArrayList(0);
        try {
            IEntityModel entityModel = this.entityMetaService.getEntityModel(dimKey);
            childrenITree = DataResourceConvert.build((List)entityModel.getShowFields(), null, null, (String)dimKey, null).stream().filter(dimAttribute -> "CODE".equals(dimAttribute.getCode()) || "NAME".equals(dimAttribute.getCode())).map(dimAttribute -> this.change2ResourceTreeITree((DimAttribute)dimAttribute, (IResourceTreeNode)dimTreeNodeDTO)).collect(Collectors.toList());
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }
        newITree.setChildren(childrenITree);
        newITree.setIcons(NodeIconGetter.getIconByType((NodeType)NodeType.DIM));
        newITree.setLeaf(false);
        return newITree;
    }

    private QueryDimension change2QueryDimension(DataField dataField, ResourceTreeNodeDTO fieldNodeDTO) {
        String dimKey = dataField.getRefDataEntityKey();
        String dataSchemeKey = dataField.getDataSchemeKey();
        DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(dataSchemeKey);
        String schemeName = dataScheme.getCode();
        if (StringUtils.hasLength(dimKey)) {
            DataTable dataTable = this.runtimeDataSchemeService.getDataTable(dataField.getDataTableKey());
            dimKey = schemeName + "." + dataTable.getCode() + "." + dataField.getCode();
            return (QueryDimension)((IResourceTreeNode)this.queryDefaultDim(dimKey, QueryDimensionType.INNER, schemeName, null).getData()).getQueryObject();
        }
        QueryDimension queryDimension = new QueryDimension();
        queryDimension.setSchemeName(schemeName);
        String dataTableKey = dataField.getDataTableKey();
        DataTable dataTable = this.runtimeDataSchemeService.getDataTable(dataTableKey);
        String fullName = schemeName + "." + dataTable.getCode() + "." + dataField.getCode();
        queryDimension.setName(dataField.getCode());
        queryDimension.setTitle(dataField.getTitle());
        queryDimension.setFullName(fullName);
        queryDimension.setId(fieldNodeDTO.getKey());
        queryDimension.setType(QueryObjectType.DIMENSION);
        queryDimension.setDimensionType(QueryDimensionType.INNER);
        queryDimension.setVirtualDimension(true);
        queryDimension.setRelatedPublicParameter(dataField.getRefParameter());
        return queryDimension;
    }

    private ZBFieldEx change2ZBField(DataField dataField, IResourceTreeNode dataFieldNode) {
        return DataChangeUtils.change2ZBField(dataField, dataFieldNode.getKey());
    }

    private ZBFieldEx change2ZBField(DataField dataField, IResourceTreeNode dataFieldNode, List<String> dws) {
        return DataChangeUtils.change2ZBField(dataField, dataFieldNode.getKey(), dws);
    }

    private ITree<IResourceTreeNode> change2ResourceTreeITree(TimeDimField timeDimField, IResourceTreeNode parentResourceTreeNode) {
        ResourceTreeNodeDTO resourceTreeNode = new ResourceTreeNodeDTO(timeDimField, parentResourceTreeNode);
        resourceTreeNode.setDisplayTiered(this.isDisplayTiered());
        QueryObject dimAttrField = this.change2DimAttrField(timeDimField, (IResourceTreeNode)resourceTreeNode, parentResourceTreeNode);
        resourceTreeNode.setQueryObject(dimAttrField);
        ITree<IResourceTreeNode> resourceTreeITree = this.chang2ResourceTreeITree(resourceTreeNode);
        if (dimAttrField instanceof ChildQueryDimension) {
            this.handleChildDim(resourceTreeITree, resourceTreeNode, (ChildQueryDimension)dimAttrField);
        }
        return resourceTreeITree;
    }

    private QueryObject change2DimAttrField(TimeDimField timeDimField, IResourceTreeNode dimAttributeNode, IResourceTreeNode dimNode) {
        DimensionAttributeField dimAttrField = new DimensionAttributeField();
        String dimKey = dimAttributeNode.getDimKey();
        String code = timeDimField.getName();
        dimAttrField.setName(timeDimField.getName());
        dimAttrField.setTitle(timeDimField.getTitle());
        dimAttrField.setId(dimAttributeNode.getKey());
        dimAttrField.setType(QueryObjectType.DIMENSIONATTRIBUTE);
        IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
        IPeriodEntity periodEntity = periodAdapter.getPeriodEntity(dimKey);
        dimAttrField.setPeriodType(EnumTransfer.toPeriodType(timeDimField.getTimeGranularity()));
        dimAttrField.setSchemeName(dimNode.getQueryObject().getSchemeName());
        String parentFullName = dimNode.getQueryObject().getFullName();
        String fullName = parentFullName + "." + code;
        dimAttrField.setFullName(fullName);
        dimAttrField.setParent(parentFullName);
        dimAttrField.setTimekey(timeDimField.isTimeKey());
        dimAttrField.setDataType(timeDimField.getDataType());
        if (timeDimField.getDataType() == DataFieldType.INTEGER.getValue()) {
            dimAttrField.setShowFormat("#,##0");
        } else if (timeDimField.getDataType() == DataFieldType.BIGDECIMAL.getValue()) {
            dimAttrField.setShowFormat("#,##0.00");
        }
        dimAttrField.setMessageAlias(FullNameWrapper.getMessageAlias((QueryDimension)dimNode.getQueryObject(), dimAttrField));
        if (!timeDimField.isTimeKey() && !PeriodTableColumn.TITLE.getCode().equals(timeDimField.getName())) {
            return DataChangeUtils.change2ChildDim(dimAttrField);
        }
        return dimAttrField;
    }

    private ITree<IResourceTreeNode> change2ResourceTreeITree(DimAttribute dimAttribute, IResourceTreeNode parentResourceTreeNode) {
        ResourceTreeNodeDTO resourceTreeNode = new ResourceTreeNodeDTO(dimAttribute, parentResourceTreeNode);
        resourceTreeNode.setDisplayTiered(this.isDisplayTiered());
        QueryObject dimAttrField = this.change2DimAttrField(dimAttribute, (IResourceTreeNode)resourceTreeNode, parentResourceTreeNode);
        resourceTreeNode.setQueryObject(dimAttrField);
        ITree<IResourceTreeNode> resourceTreeITree = this.chang2ResourceTreeITree(resourceTreeNode);
        if (dimAttrField instanceof ChildQueryDimension) {
            this.handleChildDim(resourceTreeITree, resourceTreeNode, (ChildQueryDimension)dimAttrField);
        }
        return resourceTreeITree;
    }

    private ITree<IResourceTreeNode> change2ResourceTreeITree(DataResourceDefine dataResourceDefine, IResourceTreeNode parentResourceTreeNode) {
        ResourceTreeNodeDTO resourceTreeNode = new ResourceTreeNodeDTO(dataResourceDefine, parentResourceTreeNode);
        resourceTreeNode.setDisplayTiered(this.isDisplayTiered());
        return this.chang2ResourceTreeITree(resourceTreeNode);
    }

    private ITree<IResourceTreeNode> chang2ResourceTreeITree(ResourceTreeNodeDTO resourceTreeNode) {
        ITree newITree = new ITree((INode)resourceTreeNode);
        int type = resourceTreeNode.getType();
        if (type == 0 || type == com.jiuqi.nr.dataresource.NodeType.TREE_GROUP.getValue() || type == com.jiuqi.nr.dataresource.NodeType.TREE.getValue() || type == com.jiuqi.nr.dataresource.NodeType.RESOURCE_GROUP.getValue()) {
            newITree.setIcons(NodeIconGetter.getIconByType((NodeType)NodeType.GROUP));
            if (StringUtils.hasLength(resourceTreeNode.getLinkZb())) {
                newITree.setIcons(NodeIconGetter.getIconByType((NodeType)NodeType.FIELD));
            }
            newITree.setExpanded(false);
            newITree.setLeaf(false);
        } else if (type == com.jiuqi.nr.dataresource.NodeType.DIM_GROUP.getValue() || type == com.jiuqi.nr.dataresource.NodeType.DIM_FMDM_GROUP.getValue() || type == com.jiuqi.nr.dataresource.NodeType.MD_INFO.getValue()) {
            newITree.setIcons(NodeIconGetter.getIconByType((NodeType)NodeType.DIM));
            newITree.setExpanded(false);
            newITree.setLeaf(false);
        } else if (type == com.jiuqi.nr.dataresource.NodeType.TABLE_DIM_GROUP.getValue()) {
            newITree.setIcons(NodeIconGetter.getIconByType((NodeType)NodeType.DIM));
            newITree.setLeaf(true);
        } else if (type == com.jiuqi.nr.dataresource.NodeType.FIELD_ZB_LINK.getValue()) {
            newITree.setIcons(NodeIconGetter.getIconByType((NodeType)NodeType.FIELD_ZB));
            newITree.setLeaf(true);
        } else if (type == com.jiuqi.nr.dataresource.NodeType.FIELD_LINK.getValue()) {
            newITree.setIcons(NodeIconGetter.getIconByType((NodeType)NodeType.FIELD));
            newITree.setLeaf(true);
        } else if (type == 99) {
            newITree.setIcons(NodeIconGetter.getIconByType((NodeType)NodeType.ENTITY_ATTRIBUTE));
            newITree.setLeaf(true);
        }
        newITree.setChildren(new ArrayList());
        return newITree;
    }

    private String getDefaultSchemeName(IResourceTreeNode parentTreeNode) {
        DataResourceDefine dataResourceDefine;
        String dimKey;
        List dataSchemes;
        if (parentTreeNode == null) {
            return "";
        }
        String parentNodeKey = parentTreeNode.getKey();
        if (this.defaultDataSchemes.containsKey(parentNodeKey)) {
            return this.defaultDataSchemes.get(parentNodeKey).getCode();
        }
        String defineKey = parentTreeNode.getDefineKey();
        if (!this.defaultDataSchemes.containsKey(defineKey) && !(dataSchemes = this.runtimeDataSchemeService.getDataSchemeByDimKey(new String[]{dimKey = (dataResourceDefine = this.defineService.get(defineKey)).getDimKey()})).isEmpty()) {
            this.defaultDataSchemes.put(defineKey, (DataScheme)dataSchemes.get(0));
        }
        return this.defaultDataSchemes.get(defineKey) == null ? "" : this.defaultDataSchemes.get(defineKey).getCode();
    }

    private void putDataScheme(String parentRealKey, String dataSchemeKey) {
        if (!this.defaultDataSchemes.containsKey(parentRealKey)) {
            DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(dataSchemeKey);
            this.defaultDataSchemes.put(parentRealKey, dataScheme);
        }
    }

    private QueryObject change2DimAttrField(DimAttribute dimAttribute, IResourceTreeNode dimAttributeNode, IResourceTreeNode dimNode) {
        QueryDimension queryDimension;
        QueryObject queryObject;
        DimensionAttributeField dimAttrField = new DimensionAttributeField();
        String dimKey = dimAttribute.getDimKey();
        String code = dimAttribute.getCode();
        dimAttrField.setName(dimAttribute.getCode());
        dimAttrField.setTitle(dimAttributeNode.getTitle());
        dimAttrField.setId(dimAttributeNode.getKey());
        dimAttrField.setAlias(dimNode.getQueryObject().getTitle() + dimAttributeNode.getTitle());
        if (StringUtils.hasLength(dimKey) && dimKey.endsWith("@ORG") && (queryObject = dimNode.getQueryObject()) instanceof QueryDimension && (queryDimension = (QueryDimension)queryObject).getDimensionType() == QueryDimensionType.MASTER) {
            dimAttrField.setAlias(ZBQueryI18nUtils.getMessage("zbquery.unit", new Object[0]) + dimAttributeNode.getTitle());
        }
        dimAttrField.setType(QueryObjectType.DIMENSIONATTRIBUTE);
        IEntityDefine entity = this.entityMetaService.queryEntity(dimKey);
        if (Objects.nonNull(entity)) {
            dimAttrField.setSchemeName(dimNode.getQueryObject().getSchemeName());
            String parentFullName = dimNode.getQueryObject().getFullName();
            String fullName = parentFullName + "." + code;
            dimAttrField.setFullName(fullName);
            dimAttrField.setParent(parentFullName);
            try {
                IEntityModel entityModel = this.entityMetaService.getEntityModel(dimKey);
                IEntityAttribute attribute = entityModel.getAttribute(dimAttribute.getCode());
                if (attribute != null) {
                    TableModelDefine tableModelDefine;
                    dimAttrField.setDataType(attribute.getColumnType().getValue());
                    if (attribute.getColumnType().getValue() == DataFieldType.INTEGER.getValue()) {
                        dimAttrField.setShowFormat("#,##0");
                    } else if (attribute.getColumnType().getValue() == DataFieldType.BIGDECIMAL.getValue()) {
                        dimAttrField.setShowFormat("#,##0.00");
                    }
                    String referTableID = attribute.getReferTableID();
                    if (StringUtils.hasLength(referTableID) && !"PARENTCODE".equals(attribute.getCode()) && Objects.nonNull(tableModelDefine = this.dataModelService.getTableModelDefineById(referTableID))) {
                        dimAttrField.setRelatedDimension(tableModelDefine.getCode());
                        String childDimKey = this.entityMetaService.getEntityIdByCode(tableModelDefine.getCode());
                        if (!childDimKey.endsWith("@ORG")) {
                            BaseDataDefineDTO filterCond = new BaseDataDefineDTO();
                            filterCond.setName(tableModelDefine.getCode());
                            boolean isChildDim = true;
                            if (dimNode.getQueryObject() instanceof QueryDimension) {
                                QueryDimension queryDimension2 = (QueryDimension)dimNode.getQueryObject();
                                boolean bl = isChildDim = !QueryDimensionType.SCENE.equals((Object)queryDimension2.getDimensionType());
                            }
                            if (isChildDim) {
                                dimAttrField.setMessageAlias(FullNameWrapper.getMessageAlias((QueryDimension)dimNode.getQueryObject(), dimAttrField));
                                return DataChangeUtils.change2ChildDim(dimAttrField);
                            }
                        }
                    }
                }
            }
            catch (Exception e) {
                throw new DataResourceException((Throwable)e);
            }
        }
        dimAttrField.setMessageAlias(FullNameWrapper.getMessageAlias((QueryDimension)dimNode.getQueryObject(), dimAttrField));
        return dimAttrField;
    }

    @Override
    public ITree<IResourceTreeNode> queryDimensionAttributeField(QueryObject queryObject) {
        String dimId = queryObject.getId();
        String schemeName = queryObject.getSchemeName();
        ITree<IResourceTreeNode> dimITree = new ITree<IResourceTreeNode>();
        String dimGroupKey = ResTreeUtils.getDimGroupKey(dimId);
        try {
            UUID uuid = UUID.fromString(dimGroupKey);
            QueryDimensionType dimensionType = ((QueryDimension)queryObject).getDimensionType();
            DataField dataField = this.runtimeDataSchemeService.getDataField(uuid.toString());
            if (ObjectUtils.isEmpty(dataField)) {
                DataResource dataResource = this.dataResourceService.get(dimGroupKey);
                if (Objects.nonNull(dataResource)) {
                    ResourceTreeNodeDTO resourceTreeNode = new ResourceTreeNodeDTO(dataResource);
                    resourceTreeNode.setDisplayTiered(this.isDisplayTiered());
                    QueryDimension queryDimension = this.change2QueryDimension(resourceTreeNode, this.generateParentNode(dataResource), null, null);
                    resourceTreeNode.setCode(queryDimension.getName());
                    resourceTreeNode.setQueryObject(queryDimension);
                    dimITree = this.chang2ResourceTreeITree(resourceTreeNode);
                    List<ITree<IResourceTreeNode>> childrenITrees = this.queryResourceTreeChildren(resourceTreeNode);
                    dimITree.setChildren(childrenITrees);
                }
            } else {
                DataTable dataTable = this.runtimeDataSchemeService.getDataTable(dataField.getDataTableKey());
                if (dataTable.getDataTableType() == DataTableType.MD_INFO) {
                    ResourceTreeNodeDTO parentResourceTreeNode = this.generateParentNode(dataTable);
                    ResourceTreeNodeDTO mdinfoTableTreeNode = this.generateNode(dataTable, parentResourceTreeNode);
                    QueryDimension queryDimension = this.createMdInfoQueryDimesion(mdinfoTableTreeNode, parentResourceTreeNode, dataTable);
                    mdinfoTableTreeNode.setCode(queryDimension.getName());
                    mdinfoTableTreeNode.setQueryObject(queryDimension);
                    dimITree = this.changeMDInfoZB2TreeNode(dataField, mdinfoTableTreeNode);
                } else {
                    String dimKey = queryObject.getSchemeName() + "." + dataTable.getCode() + "." + dataField.getCode();
                    dimITree = this.queryDefaultDim(dimKey, dimensionType, schemeName, null);
                }
            }
        }
        catch (IllegalArgumentException e) {
            IPeriodEntityAdapter periodAdapter;
            boolean isPeriodEntity;
            QueryDimensionType dimensionType = ((QueryDimension)queryObject).getDimensionType();
            if (dimGroupKey.startsWith(NrPeriodConst.PREFIX_CODE)) {
                dimGroupKey = PeriodUtils.removePerfix((String)dimGroupKey);
            }
            if (QueryDimensionType.CHILD == dimensionType && (isPeriodEntity = (periodAdapter = this.periodEngineService.getPeriodAdapter()).isPeriodEntity(dimGroupKey))) {
                dimensionType = QueryDimensionType.PERIOD;
            }
            if (QueryDimensionType.MDINFO.equals((Object)dimensionType)) {
                return dimITree;
            }
            dimITree = this.queryDefaultDim(dimGroupKey, dimensionType, schemeName, null);
        }
        if (dimId.contains("dimattr")) {
            dimITree = dimITree.getChildren().stream().filter(iResourceTreeNodeITree -> iResourceTreeNodeITree.getKey().equals(dimId)).findFirst().orElse(new ITree());
        }
        return dimITree;
    }

    private IResourceTreeNode generateParentNode(DataResource dataResource) {
        ResourceTreeNodeDTO parentNodeDTO = new ResourceTreeNodeDTO();
        parentNodeDTO.setDisplayTiered(this.isDisplayTiered());
        parentNodeDTO.setKey(dataResource.getParentKey());
        parentNodeDTO.setDefineKey(dataResource.getResourceDefineKey());
        parentNodeDTO.setDimKey(dataResource.getDimKey());
        return parentNodeDTO;
    }

    @Override
    public List<ITree<IResourceTreeNode>> queryDimsByFieldNode(ResourceTreeQueryParam param) {
        ArrayList<ITree<IResourceTreeNode>> iTreeList = new ArrayList<ITree<IResourceTreeNode>>();
        ResourceTreeNodeDTO resourceTreeNode = param.getDataTreeNode();
        String defineKey = resourceTreeNode.getDefineKey();
        QueryObject queryObject = resourceTreeNode.getQueryObject();
        String schemeName = queryObject.getSchemeName();
        QueryDimensionType dimensionType = null;
        if (queryObject.getType().equals((Object)QueryObjectType.DIMENSION)) {
            dimensionType = ((QueryDimension)queryObject).getDimensionType();
        }
        if (queryObject instanceof ZBFieldEx || Objects.equals((Object)dimensionType, (Object)QueryDimensionType.CHILD)) {
            Map<String, QueryDimensionType> srcMap = param.getRelatedDimensionMap();
            HashMap<String, QueryDimensionType> relatedDimensionMap = new HashMap<String, QueryDimensionType>(srcMap.size());
            if (queryObject instanceof ZBFieldEx) {
                ZBFieldEx zbField = (ZBFieldEx)queryObject;
                srcMap = zbField.getRelatedDimensionMap();
            }
            srcMap.forEach((key, value) -> {
                String dimKey = key;
                if (dimKey.startsWith(NrPeriodConst.PREFIX_CODE)) {
                    dimKey = PeriodUtils.removePerfix((String)dimKey);
                }
                relatedDimensionMap.put(dimKey, (QueryDimensionType)((Object)value));
            });
            ArrayList hasRelatedDimensions = new ArrayList();
            int resourceKind = DataResourceKind.DIM_GROUP.getValue() | DataResourceKind.TABLE_DIM_GROUP.getValue();
            List dataResources = this.dataResourceService.getBy(defineKey, resourceKind, new ArrayList(relatedDimensionMap.keySet()));
            dataResources.forEach(dataResource -> {
                hasRelatedDimensions.add(dataResource.getDimKey());
                ResourceTreeNodeDTO resourceTreeNodeDTO = new ResourceTreeNodeDTO((DataResource)dataResource);
                resourceTreeNodeDTO.setDisplayTiered(this.isDisplayTiered());
                QueryDimension queryDimension = this.change2QueryDimension(resourceTreeNodeDTO, this.generateParentNode((DataResource)dataResource), null, null);
                resourceTreeNodeDTO.setCode(queryDimension.getName());
                resourceTreeNodeDTO.setQueryObject(queryDimension);
                ITree<IResourceTreeNode> dimITree = this.chang2ResourceTreeITree(resourceTreeNodeDTO);
                List<ITree<IResourceTreeNode>> childrenITrees = this.queryResourceTreeChildren(resourceTreeNode);
                dimITree.setChildren(childrenITrees);
                iTreeList.add(dimITree);
            });
            hasRelatedDimensions.forEach(relatedDimensionMap::remove);
            DataResourceDefine dataResourceDefine = this.defineService.get(defineKey);
            String dw = ObjectUtils.isEmpty(dataResourceDefine) ? null : dataResourceDefine.getDimKey();
            relatedDimensionMap.forEach((dimKey, queryDimensionType) -> iTreeList.add(this.queryDefaultDim((String)dimKey, (QueryDimensionType)((Object)queryDimensionType), schemeName, dw)));
        }
        return iTreeList;
    }

    public ITree<IResourceTreeNode> queryDefaultDim(String dimKey, QueryDimensionType queryDimensionType, String schemeName, String dw) {
        if (dimKey.contains(".") && dimKey.startsWith(schemeName)) {
            String[] strings = dimKey.split("\\.");
            DataTable dataTable = this.runtimeDataSchemeService.getDataTableByCode(strings[1]);
            DataField dataField = this.runtimeDataSchemeService.getDataFieldByTableKeyAndCode(dataTable.getKey(), strings[2]);
            if (StringUtils.hasLength(dataField.getRefDataEntityKey())) {
                dimKey = dataField.getRefDataEntityKey();
                String id = "nrfs_dim_" + dataField.getKey();
                ResourceTreeNodeDTO dimTreeNodeDTO = new ResourceTreeNodeDTO();
                dimTreeNodeDTO.setDisplayTiered(this.isDisplayTiered());
                dimTreeNodeDTO.setType(com.jiuqi.nr.dataresource.NodeType.TABLE_DIM_GROUP.getValue());
                dimTreeNodeDTO.setParentKey("00000000-0000-0000-0000-000000000000");
                dimTreeNodeDTO.setDimKey(dimKey);
                dimTreeNodeDTO.setDefineKey("00000000-0000-0000-0000-000000000000");
                dimTreeNodeDTO.setKey(id);
                dimTreeNodeDTO.setDataTableKey(dataTable.getKey());
                dimTreeNodeDTO.setSource(dataField.getKey());
                dimTreeNodeDTO.setDw(dw);
                QueryDimension queryDimension = this.change2QueryDimension(dimTreeNodeDTO, null, schemeName, queryDimensionType);
                dimTreeNodeDTO.setQueryObject(queryDimension);
                dimTreeNodeDTO.setCode(queryDimension.getName());
                dimTreeNodeDTO.setTitle(queryDimension.getTitle());
                ITree<IResourceTreeNode> newITree = this.chang2ResourceTreeITree(dimTreeNodeDTO);
                List<Object> childrenITree = new ArrayList(0);
                try {
                    IEntityModel entityModel = this.entityMetaService.getEntityModel(dimKey);
                    childrenITree = DataResourceConvert.build((List)entityModel.getShowFields(), null, null, (String)dimKey, null).stream().filter(dimAttribute -> !dimAttribute.isHidden()).map(dimAttribute -> this.change2ResourceTreeITree((DimAttribute)dimAttribute, (IResourceTreeNode)dimTreeNodeDTO)).collect(Collectors.toList());
                }
                catch (Exception e) {
                    this.logger.error(e.getMessage(), e);
                }
                newITree.setChildren(childrenITree);
                return newITree;
            }
            ResourceTreeNodeDTO fieldNodeDTO = new ResourceTreeNodeDTO();
            fieldNodeDTO.setDisplayTiered(this.isDisplayTiered());
            String id = "nrfs_dim_" + dataField.getKey();
            fieldNodeDTO.setKey(id);
            fieldNodeDTO.setCode(dataField.getCode());
            fieldNodeDTO.setTitle(dataField.getTitle());
            fieldNodeDTO.setOrder(dataField.getOrder());
            fieldNodeDTO.setParentKey("00000000-0000-0000-0000-000000000000");
            fieldNodeDTO.setDefineKey("00000000-0000-0000-0000-000000000000");
            fieldNodeDTO.setType(com.jiuqi.nr.dataresource.NodeType.TABLE_DIM_GROUP.getValue());
            fieldNodeDTO.setQueryObject(this.change2QueryDimension(dataField, fieldNodeDTO));
            ITree fieldITree = new ITree((INode)fieldNodeDTO);
            fieldITree.setLeaf(true);
            fieldITree.setIcons(NodeIconGetter.getIconByType((NodeType)NodeType.DIM));
            return fieldITree;
        }
        String id = "nrfs_dim_" + dimKey;
        ResourceTreeNodeDTO dimTreeNodeDTO = new ResourceTreeNodeDTO();
        dimTreeNodeDTO.setDisplayTiered(this.isDisplayTiered());
        dimTreeNodeDTO.setType(com.jiuqi.nr.dataresource.NodeType.DIM_GROUP.getValue());
        dimTreeNodeDTO.setParentKey("00000000-0000-0000-0000-000000000000");
        dimTreeNodeDTO.setDimKey(dimKey);
        dimTreeNodeDTO.setDefineKey("00000000-0000-0000-0000-000000000000");
        dimTreeNodeDTO.setKey(id);
        dimTreeNodeDTO.setDw(dw);
        QueryDimension queryDimension = this.change2QueryDimension(dimTreeNodeDTO, null, schemeName, QueryDimensionType.CHILD.equals((Object)queryDimensionType) ? null : queryDimensionType);
        dimTreeNodeDTO.setQueryObject(queryDimension);
        dimTreeNodeDTO.setCode(queryDimension.getName());
        dimTreeNodeDTO.setTitle(queryDimension.getTitle());
        ITree<IResourceTreeNode> newITree = this.chang2ResourceTreeITree(dimTreeNodeDTO);
        List<Object> childrenITree = new ArrayList(0);
        if (QueryDimensionType.PERIOD.equals((Object)queryDimensionType)) {
            List timeDimFields = queryDimension.getPeriodType() == PeriodType.WEEK ? DataChangeUtils.getZPeriodChildDimFields(queryDimension) : BqlTimeDimUtils.getTimeDimFields((String)dimKey, null);
            childrenITree = timeDimFields.stream().map(timeDimField -> this.change2ResourceTreeITree((TimeDimField)timeDimField, (IResourceTreeNode)dimTreeNodeDTO)).collect(Collectors.toList());
        } else if (dimKey.endsWith("@CB")) {
            childrenITree = DataChangeUtils.getCBDimFields(dimKey).stream().map(dimAttribute -> this.change2ResourceTreeITree((DimAttribute)dimAttribute, (IResourceTreeNode)dimTreeNodeDTO)).collect(Collectors.toList());
        } else {
            try {
                IEntityModel entityModel = this.entityMetaService.getEntityModel(dimKey);
                childrenITree = DataResourceConvert.build((List)entityModel.getShowFields(), null, null, (String)dimKey, null).stream().filter(dimAttribute -> !dimAttribute.isHidden()).map(dimAttribute -> this.change2ResourceTreeITree((DimAttribute)dimAttribute, (IResourceTreeNode)dimTreeNodeDTO)).collect(Collectors.toList());
            }
            catch (Exception e) {
                this.logger.error(e.getMessage(), e);
            }
        }
        newITree.setChildren(childrenITree);
        return newITree;
    }

    private void handleChildDim(ITree<IResourceTreeNode> fieldITree, ResourceTreeNodeDTO fieldNodeDTO, ChildQueryDimension childDimension) {
        fieldITree.setIcons(NodeIconGetter.getIconByType((NodeType)NodeType.DIM));
        fieldITree.setLeaf(false);
        fieldNodeDTO.setType(com.jiuqi.nr.dataresource.NodeType.DIM_GROUP.getValue());
        DimensionAttributeField childDimAttribute = childDimension.getDimAttribute();
        if (!ObjectUtils.isEmpty(childDimAttribute.getPeriodType()) && childDimAttribute.getPeriodType().type() > 0) {
            List<TimeDimField> timeDimFields = DataChangeUtils.getPeriodChildDimFields(childDimension);
            List childrenITree = timeDimFields.stream().map(timeDimField -> {
                ResourceTreeNodeDTO resourceTreeNode = new ResourceTreeNodeDTO((TimeDimField)timeDimField, (IResourceTreeNode)fieldNodeDTO);
                resourceTreeNode.setDisplayTiered(this.isDisplayTiered());
                childDimAttribute.setId(resourceTreeNode.getKey());
                resourceTreeNode.setQueryObject(childDimAttribute);
                ITree<IResourceTreeNode> resourceTreeITree = this.chang2ResourceTreeITree(resourceTreeNode);
                return resourceTreeITree;
            }).collect(Collectors.toList());
            fieldITree.setChildren(childrenITree);
        } else {
            String childDimKey = this.entityMetaService.getEntityIdByCode(childDimAttribute.getRelatedDimension());
            try {
                IEntityModel childEntityModel = this.entityMetaService.getEntityModel(childDimKey);
                List childrenITree2 = DataResourceConvert.build((List)childEntityModel.getShowFields(), null, null, (String)childDimKey, null).stream().filter(dimAttribute2 -> "CODE".equals(dimAttribute2.getCode()) || "NAME".equals(dimAttribute2.getCode())).map(dimAttribute2 -> {
                    ResourceTreeNodeDTO childFieldNodeDTO = new ResourceTreeNodeDTO((DimAttribute)dimAttribute2, (IResourceTreeNode)fieldNodeDTO);
                    childFieldNodeDTO.setDisplayTiered(this.isDisplayTiered());
                    childFieldNodeDTO.setDefineKey(fieldNodeDTO.getDefineKey());
                    childFieldNodeDTO.setQueryObject(this.change2DimAttrField((DimAttribute)dimAttribute2, (IResourceTreeNode)childFieldNodeDTO, (IResourceTreeNode)fieldNodeDTO));
                    childFieldNodeDTO.setParentKey(fieldNodeDTO.getKey());
                    ITree childFieldITree = new ITree((INode)childFieldNodeDTO);
                    childFieldITree.setIcons(NodeIconGetter.getIconByType((NodeType)NodeType.ENTITY_ATTRIBUTE));
                    childFieldITree.setLeaf(true);
                    return childFieldITree;
                }).collect(Collectors.toList());
                fieldITree.setChildren(childrenITree2);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<String> queryTreeNodePath(QueryObject queryObject) {
        if (queryObject instanceof QueryDimension) {
            return this.queryTreeNodePath((QueryDimension)queryObject);
        }
        if (queryObject instanceof DimensionAttributeField) {
            return this.queryTreeNodePath((DimensionAttributeField)queryObject);
        }
        if (queryObject instanceof ZBFieldEx) {
            return this.queryTreeNodePath((ZBFieldEx)queryObject);
        }
        return Collections.EMPTY_LIST;
    }

    private void findTreeNodePath4ResGroup(String resGroupKey, List<String> keyPath, List<String> titlePath) {
        DataResource dataResource = this.dataResourceService.get(resGroupKey);
        if (Objects.nonNull(dataResource)) {
            String resGroupId = ResTreeUtils.generateKey(dataResource);
            keyPath.add(resGroupId);
            titlePath.add(dataResource.getTitle());
            String parentKey = dataResource.getParentKey();
            if (StringUtils.hasLength(parentKey)) {
                this.findTreeNodePath4ResGroup(parentKey, keyPath, titlePath);
            } else {
                String defineKey = dataResource.getResourceDefineKey();
                DataResourceDefine dataResourceDefine = this.defineService.get(defineKey);
                String defineId = ResTreeUtils.generateKey(dataResourceDefine);
                keyPath.add(defineId);
                titlePath.add(dataResourceDefine.getTitle());
                String groupKey = dataResourceDefine.getGroupKey();
                if (!"00000000-0000-0000-0000-000000000000".equals(groupKey)) {
                    this.findTreeNodePath4TreeGroup(groupKey, keyPath, titlePath);
                }
            }
        }
    }

    private void findTreeNodePath4TreeGroup(String defineGroupKey, List<String> keyPath, List<String> titlePath) {
        DataResourceDefineGroup resourceDefineGroup = this.defineGroupService.get(defineGroupKey);
        if (Objects.nonNull(resourceDefineGroup)) {
            String defineGroupId = ResTreeUtils.generateKey(resourceDefineGroup);
            keyPath.add(defineGroupId);
            titlePath.add(resourceDefineGroup.getTitle());
            String parentKey = resourceDefineGroup.getParentKey();
            if (!"00000000-0000-0000-0000-000000000000".equals(parentKey)) {
                this.findTreeNodePath4TreeGroup(parentKey, keyPath, titlePath);
            }
        }
    }

    private List<String> queryTreeNodePath(QueryDimension queryDimension) {
        String resGroupKey;
        ArrayList<String> keyPath = new ArrayList<String>();
        ArrayList<String> titlePath = new ArrayList<String>();
        String id = queryDimension.getId();
        if (queryDimension.getDimensionType() == QueryDimensionType.INNER) {
            String[] splits = id.split("_");
            resGroupKey = splits[2];
            keyPath.add(id);
            titlePath.add(queryDimension.getTitle());
        } else {
            resGroupKey = ResTreeUtils.getRealKey(id);
        }
        this.findTreeNodePath4ResGroup(resGroupKey, keyPath, titlePath);
        Collections.reverse(keyPath);
        return keyPath;
    }

    private List<String> queryTreeNodePath(DimensionAttributeField dimensionAttributeField) {
        ArrayList<String> keyPath = new ArrayList<String>();
        ArrayList<String> titlePath = new ArrayList<String>();
        String id = dimensionAttributeField.getId();
        keyPath.add(id);
        titlePath.add(dimensionAttributeField.getTitle());
        String[] splits = id.split("_");
        String dimGroupKey = splits[2];
        this.findTreeNodePath4ResGroup(dimGroupKey, keyPath, titlePath);
        Collections.reverse(keyPath);
        return keyPath;
    }

    private List<String> queryTreeNodePath(ZBFieldEx zbField) {
        ArrayList<String> keyPath = new ArrayList<String>();
        ArrayList<String> titlePath = new ArrayList<String>();
        String id = zbField.getId();
        keyPath.add(id);
        titlePath.add(zbField.getTitle());
        String[] splits = id.split("_");
        String resGroupKey = splits[2];
        this.findTreeNodePath4ResGroup(resGroupKey, keyPath, titlePath);
        Collections.reverse(keyPath);
        return keyPath;
    }

    @Override
    public Map<String, List<QueryObject>> handleSceneGrouping(ZBQueryModel zbQueryModel) {
        HashMap<String, List<QueryObject>> sceneMap = new HashMap<String, List<QueryObject>>();
        zbQueryModel.getQueryObjects().forEach(queryObject -> this.handleSceneField((QueryObject)queryObject, (Map<String, List<QueryObject>>)sceneMap));
        return sceneMap;
    }

    private void handleSceneField(QueryObject queryObject, Map<String, List<QueryObject>> sceneMap) {
        QueryObjectType objectType = queryObject.getType();
        if (QueryObjectType.ZB == objectType) {
            String schemeName = queryObject.getSchemeName();
            if (!StringUtils.hasLength(schemeName)) {
                schemeName = queryObject.getFullName().split("\\.")[0];
            }
            DataScheme dataScheme = this.runtimeDataSchemeService.getDataSchemeByCode(schemeName);
            this.dataSchemeService.getDataSchemeDimension(dataScheme.getKey()).forEach(dataDimDTO -> {
                DimensionType dimensionType = dataDimDTO.getDimensionType();
                if (DimensionType.DIMENSION == dimensionType) {
                    String dimFullName = dataDimDTO.getDimKey().split("@")[0];
                    if (!sceneMap.containsKey(dimFullName)) {
                        sceneMap.put(dimFullName, new ArrayList());
                    }
                    ((List)sceneMap.get(dimFullName)).add(queryObject);
                }
            });
        } else if (QueryObjectType.GROUP == objectType && FieldGroupType.ZB == ((FieldGroup)queryObject).getGroupType()) {
            FieldGroup fieldGroup = (FieldGroup)queryObject;
            fieldGroup.getChildren().forEach(queryObject1 -> this.handleSceneField((QueryObject)queryObject1, sceneMap));
        }
    }

    private ResourceTreeNode2DTO changeToNode2(ITree<IResourceTreeNode> treeNode, boolean isChecked, boolean allChild) {
        ResourceTreeNode2DTO node2 = new ResourceTreeNode2DTO();
        node2.setDisplayTiered(this.isDisplayTiered());
        IResourceTreeNode node = (IResourceTreeNode)treeNode.getData();
        BeanUtils.copyProperties(node, node2);
        String[] ids = node2.getKey().split("_");
        if (ids.length > 1) {
            ids[0] = "nrfs";
            String id = Arrays.stream(ids).reduce((pre, cur) -> pre + "_" + cur).get();
            node2.setKey(id);
            node.getQueryObject().setId(id);
        }
        node2.setChecked(isChecked);
        ArrayList<IResourceTreeNode> childrenList = new ArrayList<IResourceTreeNode>();
        List children = treeNode.getChildren();
        if (!CollectionUtils.isEmpty(children)) {
            QueryDimension queryDimension;
            String defaultZBCode = "NAME";
            QueryObject queryObject = node2.getQueryObject();
            if (queryObject instanceof QueryDimension && QueryDimensionType.PERIOD == (queryDimension = (QueryDimension)queryObject).getDimensionType()) {
                defaultZBCode = "P_TIMEKEY";
            }
            for (ITree childTree : children) {
                if (!defaultZBCode.equals(childTree.getCode()) && !allChild) continue;
                childrenList.add(this.changeToNode2((ITree<IResourceTreeNode>)childTree, isChecked, false));
            }
            if (childrenList.size() <= 0) {
                childrenList.add(this.changeToNode2((ITree<IResourceTreeNode>)((ITree)children.get(0)), isChecked, false));
            }
        }
        node2.setChildren(childrenList);
        return node2;
    }

    private void handleFMDMFieldSelect(SelectedFieldDefineEx selectedField, Map<String, ResourceTreeNode2DTO> dims) {
        block6: {
            try {
                ResourceTreeNode2DTO node2DTO;
                boolean anyMatch;
                String tableName = selectedField.getTableName();
                String dimKey = this.entityMetaService.getEntityIdByCode(tableName);
                TaskDefine taskDefine = this.viewController.getTask(selectedField.getTaskId());
                String dataSchemeKey = taskDefine.getDataScheme();
                DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(dataSchemeKey);
                String schemeName = dataScheme.getCode();
                ITree<IResourceTreeNode> dimTree = this.queryDefaultDim(dimKey, QueryDimensionType.MASTER, schemeName, taskDefine.getDw());
                ResourceTreeNode2DTO dimNode2DTO = this.changeToNode2(dimTree, true, true);
                if (!dims.containsKey(dimKey)) {
                    dims.put(dimKey, this.changeToNode2(dimTree, true, false));
                }
                if (anyMatch = (node2DTO = dims.get(dimKey)).getChildren().stream().anyMatch(treeNode -> Objects.equals(selectedField.getCode(), treeNode.getCode()))) break block6;
                for (IResourceTreeNode child : dimNode2DTO.getChildren()) {
                    if (!selectedField.getCode().equals(child.getCode())) continue;
                    if (child instanceof ResourceTreeNode2DTO) {
                        ResourceTreeNode2DTO resourceTreeNode2DTO = (ResourceTreeNode2DTO)child;
                        resourceTreeNode2DTO.setTitle(selectedField.getFieldTitle());
                        if (resourceTreeNode2DTO.getQueryObject() instanceof DimensionAttributeField) {
                            DimensionAttributeField dimensionAttributeField = (DimensionAttributeField)resourceTreeNode2DTO.getQueryObject();
                            dimensionAttributeField.setTitle(selectedField.getFieldTitle());
                        }
                    }
                    node2DTO.getChildren().add(child);
                    break;
                }
            }
            catch (Exception e) {
                this.logger.error(e.getMessage(), e);
            }
        }
    }

    private void getRelatedDimensionNode(String dimKey, QueryDimensionType queryDimensionType, String schemeName, String dw, Map<String, String> extendedDatas, Map<String, ResourceTreeNode2DTO> dims) {
        if (!dims.containsKey(dimKey)) {
            dimKey = DataChangeUtils.handleDimKey(dimKey, queryDimensionType, extendedDatas);
            ITree<IResourceTreeNode> dimTree = this.queryDefaultDim(dimKey, queryDimensionType, schemeName, dw);
            dims.put(dimKey, this.changeToNode2(dimTree, true, false));
        }
    }

    private List<String> getDWs(TaskDefine taskDefine, Map<String, List<String>> mapDWs) {
        if (taskDefine != null) {
            if (!mapDWs.containsKey(taskDefine.getKey())) {
                ArrayList<String> dws = new ArrayList<String>();
                dws.addAll(this.viewController.listTaskOrgLinkByTask(taskDefine.getKey()).stream().map(taskOrgLinkDefine -> taskOrgLinkDefine.getEntity()).collect(Collectors.toList()));
                if (CollectionUtils.isEmpty(dws)) {
                    dws.add(taskDefine.getDw());
                }
                mapDWs.put(taskDefine.getKey(), dws);
            }
            return mapDWs.get(taskDefine.getKey());
        }
        return Collections.emptyList();
    }

    @Override
    public List<IResourceTreeNode> handleFieldSelect(List<SelectedFieldDefineEx> zbs, Map<String, String> extendedDatas) {
        ArrayList<IResourceTreeNode> nodeList = new ArrayList<IResourceTreeNode>();
        if (CollectionUtils.isEmpty(zbs)) {
            return nodeList;
        }
        LinkedHashMap<String, ResourceTreeNode2DTO> dims = new LinkedHashMap<String, ResourceTreeNode2DTO>();
        LinkedHashMap<String, ResourceTreeNode2DTO> relDims = new LinkedHashMap<String, ResourceTreeNode2DTO>();
        HashMap<String, List<String>> mapDWs = new HashMap<String, List<String>>();
        for (SelectedFieldDefineEx selectedField : zbs) {
            if (selectedField.getDataLinkType() == DataLinkType.DATA_LINK_TYPE_FORMULA) {
                TaskDefine taskDefine = this.viewController.getTask(selectedField.getTaskId());
                DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(taskDefine.getDataScheme());
                String schemeName = dataScheme.getCode();
                FormulaField formulaField = DataChangeUtils.change2FormulaField(selectedField);
                formulaField.setSchemeName(schemeName);
                ResourceTreeNode2DTO formulaFieldNode = new ResourceTreeNode2DTO(formulaField);
                formulaFieldNode.setDisplayTiered(this.isDisplayTiered());
                nodeList.add(formulaFieldNode);
                LinkedHashMap<String, QueryDimensionType> relatedDimensionMap = new LinkedHashMap<String, QueryDimensionType>();
                List<String> dws = this.getDWs(taskDefine, mapDWs);
                DataChangeUtils.handleDataSchemeDimension(dataScheme.getKey(), null, dws, relatedDimensionMap);
                relatedDimensionMap.forEach((dimKey, queryDimensionType) -> this.getRelatedDimensionNode((String)dimKey, (QueryDimensionType)((Object)queryDimensionType), schemeName, taskDefine.getDw(), extendedDatas, (Map<String, ResourceTreeNode2DTO>)dims));
                continue;
            }
            if (selectedField.getDataLinkType() == DataLinkType.DATA_LINK_TYPE_FMDM) {
                this.handleFMDMFieldSelect(selectedField, dims);
                continue;
            }
            String fieldCode = selectedField.getFieldCode();
            DataField dataField = this.runtimeDataSchemeService.getDataField(fieldCode);
            DataFieldDTO dataFieldDTO = DataFieldDTO.valueOf((DataField)dataField);
            dataFieldDTO.setTitle(selectedField.getFieldTitle());
            dataField = dataFieldDTO;
            ResourceTreeNode2DTO fieldNodeDTO = new ResourceTreeNode2DTO(dataField);
            fieldNodeDTO.setDisplayTiered(this.isDisplayTiered());
            if (selectedField.getDataLinkType() == DataLinkType.DATA_LINK_TYPE_INFO && StringUtils.hasLength(dataField.getRefDataEntityKey())) {
                DataTable mdinfoTable = this.runtimeDataSchemeService.getDataTable(dataField.getDataTableKey());
                ResourceTreeNodeDTO parentResourceTreeNode = this.generateParentNode(mdinfoTable);
                ResourceTreeNodeDTO mdinfoTableTreeNode = this.generateNode(mdinfoTable, parentResourceTreeNode);
                QueryDimension queryDimension = this.createMdInfoQueryDimesion(mdinfoTableTreeNode, parentResourceTreeNode, mdinfoTable);
                mdinfoTableTreeNode.setCode(queryDimension.getName());
                mdinfoTableTreeNode.setQueryObject(queryDimension);
                ITree<IResourceTreeNode> mdinfoTableTreeITree = this.chang2ResourceTreeITree(mdinfoTableTreeNode);
                ResourceTreeNode2DTO mdinfoTableTreeNode2DTO = this.changeToNode2(mdinfoTableTreeITree, true, true);
                ITree<IResourceTreeNode> treeNodeITree = this.changeMDInfoZB2TreeNode(dataField, mdinfoTableTreeNode);
                ResourceTreeNode2DTO resourceTreeNode2DTO = this.changeToNode2(treeNodeITree, true, true);
                TaskDefine taskDefine = this.viewController.getTask(selectedField.getTaskId());
                SpecialQueryDimension specialQueryDimension = (SpecialQueryDimension)resourceTreeNode2DTO.getQueryObject();
                List<String> dws = this.getDWs(taskDefine, mapDWs);
                specialQueryDimension.getRelatedDimensionMap().entrySet().removeIf(entry -> ((QueryDimensionType)((Object)((Object)entry.getValue()))).equals((Object)QueryDimensionType.MASTER) && !dws.contains(entry.getKey()));
                specialQueryDimension.getRelatedDimensionMap().forEach((dimKey, queryDimensionType) -> this.getRelatedDimensionNode((String)dimKey, (QueryDimensionType)((Object)queryDimensionType), specialQueryDimension.getSchemeName(), taskDefine.getDw(), extendedDatas, (Map<String, ResourceTreeNode2DTO>)relDims));
                mdinfoTableTreeNode2DTO.setChildren(Arrays.asList(resourceTreeNode2DTO));
                nodeList.add(mdinfoTableTreeNode2DTO);
                continue;
            }
            if (dataField.getDataFieldKind() == DataFieldKind.TABLE_FIELD_DIM && DataChangeUtils.isChangeTableDim(dataField)) {
                fieldNodeDTO.setQueryObject(this.change2QueryDimension(dataField, fieldNodeDTO));
                if (dims.containsKey(fieldNodeDTO.getQueryObject().getFullName())) continue;
                dims.put(fieldNodeDTO.getQueryObject().getFullName(), fieldNodeDTO);
                continue;
            }
            TaskDefine taskDefine = this.viewController.getTask(selectedField.getTaskId());
            List<String> dws = this.getDWs(taskDefine, mapDWs);
            fieldNodeDTO.setQueryObject(this.change2ZBField(dataField, fieldNodeDTO, dws));
            ZBFieldEx zbField = (ZBFieldEx)fieldNodeDTO.getQueryObject();
            zbField.getRelatedDimensionMap().forEach((dimKey, queryDimensionType) -> this.getRelatedDimensionNode((String)dimKey, (QueryDimensionType)((Object)queryDimensionType), zbField.getSchemeName(), taskDefine.getDw(), extendedDatas, (Map<String, ResourceTreeNode2DTO>)relDims));
            nodeList.add(fieldNodeDTO);
        }
        relDims.forEach((dimKey, dimNode) -> {
            if (!dims.containsKey(dimNode.getQueryObject().getFullName())) {
                dims.put(dimNode.getQueryObject().getFullName(), (ResourceTreeNode2DTO)dimNode);
            }
        });
        nodeList.addAll(0, dims.values());
        DataChangeUtils.sortNodes(nodeList);
        return nodeList;
    }

    private ResourceTreeNodeDTO generateParentNode(DataTable mdinfoTable) {
        ResourceTreeNodeDTO parentNodeDTO = new ResourceTreeNodeDTO();
        parentNodeDTO.setDisplayTiered(this.isDisplayTiered());
        String id = "nrfs_resgroup_00000000-0000-0000-0000-000000000000";
        parentNodeDTO.setKey(id);
        parentNodeDTO.setParentKey("00000000-0000-0000-0000-000000000000");
        parentNodeDTO.setDefineKey("00000000-0000-0000-0000-000000000000");
        parentNodeDTO.setType(com.jiuqi.nr.dataresource.NodeType.RESOURCE_GROUP.getValue());
        return parentNodeDTO;
    }

    private ResourceTreeNodeDTO generateNode(DataTable mdinfoTable, ResourceTreeNodeDTO parentNodeDTO) {
        ResourceTreeNodeDTO treeNodeDTO = new ResourceTreeNodeDTO();
        treeNodeDTO.setDisplayTiered(this.isDisplayTiered());
        String id = "nrfs_dim_" + mdinfoTable.getKey();
        treeNodeDTO.setKey(id);
        treeNodeDTO.setParentKey(parentNodeDTO.getKey());
        treeNodeDTO.setDefineKey(parentNodeDTO.getDefineKey());
        treeNodeDTO.setType(com.jiuqi.nr.dataresource.NodeType.MD_INFO.getValue());
        return treeNodeDTO;
    }

    private DataTable isMDINFOTable(DataResourceNode resourceNode) {
        DataTable dataTable;
        if (resourceNode.getType() == com.jiuqi.nr.dataresource.NodeType.MD_INFO.getValue() && StringUtils.hasLength(resourceNode.getSource()) && (dataTable = this.runtimeDataSchemeService.getDataTable(resourceNode.getSource())) != null && dataTable.getDataTableType().equals((Object)DataTableType.MD_INFO)) {
            return dataTable;
        }
        return null;
    }

    private boolean isDisplayTiered() {
        String displayTiered = this.nvwaSystemOptionService.get("NrZbQuerySystemOption", "DISPLAY_TIERED");
        return "1".equals(displayTiered);
    }
}

