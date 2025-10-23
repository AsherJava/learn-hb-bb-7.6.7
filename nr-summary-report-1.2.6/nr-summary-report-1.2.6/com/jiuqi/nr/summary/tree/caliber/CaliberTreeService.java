/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.nr.calibre2.ICalibreDataService
 *  com.jiuqi.nr.calibre2.common.CalibreDataOption$DataTreeType
 *  com.jiuqi.nr.calibre2.common.Result
 *  com.jiuqi.nr.calibre2.domain.CalibreDataDTO
 *  com.jiuqi.nr.calibre2.domain.CalibreDefineDTO
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.option.DimGroupOptionService
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  org.json.JSONObject
 */
package com.jiuqi.nr.summary.tree.caliber;

import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.nr.calibre2.ICalibreDataService;
import com.jiuqi.nr.calibre2.common.CalibreDataOption;
import com.jiuqi.nr.calibre2.common.Result;
import com.jiuqi.nr.calibre2.domain.CalibreDataDTO;
import com.jiuqi.nr.calibre2.domain.CalibreDefineDTO;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.option.DimGroupOptionService;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.summary.api.service.IDesignSummarySolutionService;
import com.jiuqi.nr.summary.exception.SummaryCommonException;
import com.jiuqi.nr.summary.model.soulution.SummarySolution;
import com.jiuqi.nr.summary.service.SummaryParamService;
import com.jiuqi.nr.summary.tree.caliber.CaliberTreeNodeType;
import com.jiuqi.nr.summary.tree.core.ITreeService;
import com.jiuqi.nr.summary.tree.core.TreeNode;
import com.jiuqi.nr.summary.tree.core.TreeQueryParam;
import com.jiuqi.nr.summary.utils.EntityQueryUtil;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class CaliberTreeService
implements ITreeService {
    @Autowired
    private SummaryParamService summaryParamService;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private ICalibreDataService calibreDataService;
    @Autowired
    private DimGroupOptionService dimGroupOptionService;
    @Autowired
    private IDesignSummarySolutionService designSummarySolutionService;

    @Override
    public String getId() {
        return "caliber_tree";
    }

    @Override
    public List<TreeNode> getRoots(TreeQueryParam treeQueryParam) throws SummaryCommonException {
        String sumTaskId = treeQueryParam.getCustomValue("sumTaskKey").toString();
        String taskKey = treeQueryParam.getCustomValue("taskKey").toString();
        SummarySolution solutionDefine = this.designSummarySolutionService.getSummarySolutionDefine(sumTaskId);
        IEntityDefine dimension = this.summaryParamService.getDimension(taskKey);
        boolean mainIsBase = dimension.getId().endsWith("@BASE");
        String targetDimension = solutionDefine.getTargetDimension();
        boolean targetIsOrg = targetDimension.endsWith("@ORG");
        ArrayList<TreeNode> roots = new ArrayList<TreeNode>();
        roots.add(new TreeNode("00000000-0000-0000-0000-000000000000", null, "\u4e3b\u7ef4\u5ea6", "#icon16_DH_A_NW_gongnengfenzushouqi"));
        roots.add(new TreeNode("00000000-0000-0000-0000-000000000001", null, "\u4e3b\u7ef4\u5ea6\u7684\u5c5e\u6027\u7ef4\u5ea6", "#icon16_DH_A_NW_gongnengfenzushouqi"));
        roots.add(new TreeNode("00000000-0000-0000-0000-000000000002", null, "\u8868\u5185\u7ef4\u5ea6", "#icon16_DH_A_NW_gongnengfenzushouqi"));
        roots.add(new TreeNode("00000000-0000-0000-0000-000000000003", null, "\u53e3\u5f84", "#icon16_DH_A_NW_gongnengfenzushouqi"));
        if (mainIsBase && targetIsOrg) {
            roots.add(new TreeNode("00000000-0000-0000-0000-000000000004", null, "\u76ee\u6807\u7ef4\u5ea6", "#icon16_DH_A_NW_gongnengfenzushouqi"));
            roots.add(new TreeNode("00000000-0000-0000-0000-000000000005", null, "\u76ee\u6807\u7ef4\u5ea6\u7684\u5c5e\u6027\u7ef4\u5ea6", "#icon16_DH_A_NW_gongnengfenzushouqi"));
            roots.add(new TreeNode("00000000-0000-0000-0000-000000000006", null, "\u76ee\u6807\u7ef4\u5ea6\u53e3\u5f84", "#icon16_DH_A_NW_gongnengfenzushouqi"));
        }
        return roots;
    }

    @Override
    public List<TreeNode> getChilds(TreeQueryParam treeQueryParam) throws SummaryCommonException {
        ArrayList<TreeNode> treeNodes = new ArrayList<TreeNode>();
        String nodeKey = treeQueryParam.getNodeKey();
        JSONObject extDataParamJson = new JSONObject();
        Object extValue = treeQueryParam.getCustomValue("ext");
        if (extValue != null) {
            extDataParamJson = new JSONObject(extValue.toString());
        }
        Object sumTaskIdValue = treeQueryParam.getCustomValue("sumTaskKey");
        String sumTaskId = null;
        if (sumTaskIdValue != null) {
            sumTaskId = treeQueryParam.getCustomValue("sumTaskKey").toString();
        }
        String taskKey = treeQueryParam.getCustomValue("taskKey").toString();
        CaliberTreeNodeType nodeType = CaliberTreeNodeType.valueOf(extDataParamJson.optInt("type"));
        switch (nodeKey) {
            case "00000000-0000-0000-0000-000000000000": {
                this.buildMasterDimensionNodes(treeNodes, taskKey);
                break;
            }
            case "00000000-0000-0000-0000-000000000001": {
                this.buildMasterFieldDimensionNodes(treeNodes, taskKey);
                break;
            }
            case "00000000-0000-0000-0000-000000000002": {
                this.buildInnerDimensionNodes(treeNodes, taskKey);
                break;
            }
            case "00000000-0000-0000-0000-000000000003": {
                this.buildCaliberNodes(treeNodes, taskKey);
                break;
            }
            case "00000000-0000-0000-0000-000000000004": {
                this.buildTargetDimNodes(treeNodes, sumTaskId);
                break;
            }
            case "00000000-0000-0000-0000-000000000005": {
                this.buildTargetFieldDimensionNodes(treeNodes, sumTaskId);
                break;
            }
            case "00000000-0000-0000-0000-000000000006": {
                this.buildTargetCaliberNodes(treeNodes, sumTaskId);
                break;
            }
        }
        if (nodeType != null) {
            switch (nodeType) {
                case MASTERDIM: 
                case MASTERDIM_ITEM: {
                    this.buildMasterDimensionItemNodes(treeNodes, treeQueryParam, nodeType.equals((Object)CaliberTreeNodeType.MASTERDIM));
                    break;
                }
                case MASTERDIMFIELD: 
                case MASTERDIM_FIELD_ITEM: {
                    this.buildMasterFieldDimensionItemNodes(treeNodes, treeQueryParam, nodeType.equals((Object)CaliberTreeNodeType.MASTERDIMFIELD));
                    break;
                }
                case INNERDIM: 
                case INNERDIM_ITEM: {
                    this.buildInnerDimensionItemNodes(treeNodes, treeQueryParam, nodeType.equals((Object)CaliberTreeNodeType.INNERDIM));
                    break;
                }
                case CALIBER: 
                case CALIBER_ITEM: {
                    this.buildCaliberItemNodes(treeNodes, treeQueryParam);
                    break;
                }
            }
        }
        return treeNodes;
    }

    private String getRealNodeKey(TreeQueryParam treeQueryParam) {
        String nodeKey = treeQueryParam.getNodeKey();
        int i = nodeKey.indexOf("@");
        return nodeKey.substring(i + 1);
    }

    private void buildMasterDimensionNodes(List<TreeNode> treeNodes, String taskKey) throws SummaryCommonException {
        IEntityDefine dimension = this.summaryParamService.getDimension(taskKey);
        String dimId = dimension.getId();
        IEntityTable entityTable = EntityQueryUtil.getEntityTable(dimId);
        List rootRows = entityTable.getRootRows();
        TreeNode treeNode = new TreeNode("masterdim@" + dimId, dimension.getCode(), dimension.getTitle(), "#icon-16_SHU_A_NR_weidumulu");
        treeNode.setDraggable(true);
        treeNode.setLeaf(rootRows == null || rootRows.isEmpty());
        JSONObject extDataJson = new JSONObject();
        extDataJson.put("type", CaliberTreeNodeType.MASTERDIM.getCode());
        extDataJson.put("fieldName", (Object)(dimId + (this.isOrg(dimId) ? ".CODE" : ".OBJECTCODE")));
        extDataJson.put("referEntity", (Object)dimId);
        extDataJson.put("entityType", (Object)(this.isOrg(dimId) ? "ORG" : "BASE"));
        extDataJson.put("treeStruct", (Object)dimension.isTree());
        extDataJson.put("maxLevel", entityTable.getMaxDepth());
        treeNode.setData(extDataJson.toString());
        treeNodes.add(treeNode);
    }

    private void buildMasterDimensionItemNodes(List<TreeNode> treeNodes, TreeQueryParam treeQueryParam, boolean isRoot) throws SummaryCommonException {
        List entityRows;
        JSONObject extDataParamJson = new JSONObject(treeQueryParam.getCustomValue("ext").toString());
        String entityType = extDataParamJson.optString("entityType");
        String entityId = extDataParamJson.optString("referEntity");
        IEntityDefine entityDefine = this.entityMetaService.queryEntity(entityId);
        IEntityTable entityTable = EntityQueryUtil.getEntityTable(entityId);
        List list = entityRows = isRoot ? entityTable.getRootRows() : entityTable.getChildRows(this.getRealNodeKey(treeQueryParam));
        if (entityRows != null) {
            List entityRowNodes = entityRows.stream().map(entityRow -> {
                int directChildCount = entityTable.getDirectChildCount(entityRow.getEntityKeyData());
                String icon = entityId.endsWith("@ORG") ? "#icon-_GJWzuzhijigou" : "#icon16_DH_A_NW_jichushuju";
                TreeNode treeNode = new TreeNode("masterdim@" + entityRow.getEntityKeyData(), entityRow.getCode(), entityRow.getTitle(), icon);
                treeNode.setDraggable(true);
                treeNode.setLeaf(directChildCount == 0);
                JSONObject extDataJson = new JSONObject();
                extDataJson.put("entityCode", (Object)entityRow.getCode());
                extDataJson.put("type", CaliberTreeNodeType.MASTERDIM_ITEM.getCode());
                extDataJson.put("entityType", (Object)entityType);
                extDataJson.put("parentTitle", (Object)entityDefine.getTitle());
                extDataJson.put("fieldName", (Object)(entityDefine.getId() + (this.isOrg(entityDefine.getId()) ? ".CODE" : ".OBJECTCODE")));
                extDataJson.put("referEntity", (Object)entityId);
                treeNode.setData(extDataJson.toString());
                return treeNode;
            }).collect(Collectors.toList());
            treeNodes.addAll(entityRowNodes);
        }
    }

    private void buildMasterFieldDimensionNodes(List<TreeNode> treeNodes, String taskKey) throws SummaryCommonException {
        IEntityDefine dimension = this.summaryParamService.getDimension(taskKey);
        List<IEntityAttribute> attrs = this.summaryParamService.getDimensionAttributes(dimension.getId());
        if (attrs != null) {
            List attrNodes = attrs.stream().filter(attr -> StringUtils.hasLength(attr.getReferTableID())).map(attr -> {
                TableModelDefine tableModelDefine = this.dataModelService.getTableModelDefineById(attr.getReferTableID());
                String referEntityId = this.entityMetaService.getEntityIdByCode(tableModelDefine.getCode());
                IEntityTable entityTable = null;
                try {
                    entityTable = EntityQueryUtil.getEntityTable(referEntityId);
                }
                catch (Exception e) {
                    throw new RuntimeException(e);
                }
                TreeNode treeNode = new TreeNode("masterdimfield@" + attr.getID(), attr.getCode(), attr.getTitle(), "#icon-16_SHU_A_NR_weidumulu");
                treeNode.setDraggable(true);
                JSONObject extDataJson = new JSONObject();
                extDataJson.put("type", CaliberTreeNodeType.MASTERDIMFIELD.getCode());
                extDataJson.put("referEntity", (Object)referEntityId);
                extDataJson.put("fieldName", (Object)(dimension.getId() + "." + attr.getCode()));
                extDataJson.put("entityType", (Object)(referEntityId.endsWith("@ORG") ? "ORG" : "BASE"));
                extDataJson.put("treeStruct", (Object)dimension.isTree());
                extDataJson.put("maxLevel", entityTable.getMaxDepth());
                treeNode.setData(extDataJson.toString());
                return treeNode;
            }).collect(Collectors.toList());
            treeNodes.addAll(attrNodes);
        }
    }

    private void buildMasterFieldDimensionItemNodes(List<TreeNode> treeNodes, TreeQueryParam treeQueryParam, boolean isRoot) throws SummaryCommonException {
        List entityRows;
        JSONObject extDataParamJson = new JSONObject(treeQueryParam.getCustomValue("ext").toString());
        String entityType = extDataParamJson.optString("entityType");
        String refreEntityId = extDataParamJson.optString("referEntity");
        IEntityDefine entityDefine = this.entityMetaService.queryEntity(refreEntityId);
        IEntityTable entityTable = EntityQueryUtil.getEntityTable(refreEntityId);
        List list = entityRows = isRoot ? entityTable.getRootRows() : entityTable.getChildRows(this.getRealNodeKey(treeQueryParam));
        if (entityRows != null) {
            List entityRowNodes = entityRows.stream().map(entityRow -> {
                int directChildCount = entityTable.getDirectChildCount(entityRow.getEntityKeyData());
                String icon = refreEntityId.endsWith("@ORG") ? "#icon-_GJWzuzhijigou" : "#icon16_DH_A_NW_jichushuju";
                TreeNode treeNode = new TreeNode("masterdimfield@" + entityRow.getEntityKeyData(), entityRow.getCode(), entityRow.getTitle(), icon);
                treeNode.setDraggable(true);
                treeNode.setLeaf(directChildCount == 0);
                JSONObject extDataJson = new JSONObject();
                extDataJson.put("entityCode", (Object)entityRow.getCode());
                extDataJson.put("type", CaliberTreeNodeType.MASTERDIM_FIELD_ITEM.getCode());
                extDataJson.put("entityType", (Object)entityType);
                extDataJson.put("parentTitle", (Object)entityDefine.getTitle());
                extDataJson.put("fieldName", (Object)extDataParamJson.optString("fieldName"));
                extDataJson.put("referEntity", (Object)refreEntityId);
                treeNode.setData(extDataJson.toString());
                return treeNode;
            }).collect(Collectors.toList());
            treeNodes.addAll(entityRowNodes);
        }
    }

    private void buildInnerDimensionNodes(List<TreeNode> treeNodes, String taskKey) throws SummaryCommonException {
        ArrayList<String> taskKeys = new ArrayList<String>();
        taskKeys.add(taskKey);
        IEntityDefine dimension = this.summaryParamService.getDimension(taskKey);
        List<DataField> innerDims = this.summaryParamService.getTableDimensions(taskKeys);
        if (!CollectionUtils.isEmpty(innerDims)) {
            HashMap referEntityMap = new HashMap();
            List innerDimNodes = innerDims.stream().filter(field -> this.filtInnerDim((DataField)field, referEntityMap)).map(field -> {
                IEntityTable entityTable = null;
                try {
                    entityTable = EntityQueryUtil.getEntityTable(field.getRefDataEntityKey());
                }
                catch (Exception e) {
                    throw new RuntimeException(e);
                }
                TreeNode treeNode = new TreeNode("innerDim@" + field.getKey(), field.getCode(), field.getTitle(), "#icon-16_SHU_A_NR_weidumulu");
                treeNode.setDraggable(true);
                JSONObject extDataJson = new JSONObject();
                extDataJson.put("type", CaliberTreeNodeType.INNERDIM.getCode());
                extDataJson.put("referEntity", (Object)field.getRefDataEntityKey());
                extDataJson.put("fieldName", (Object)(field.getRefDataEntityKey() + (this.isOrg(field.getRefDataEntityKey()) ? ".CODE" : ".OBJECTCODE")));
                extDataJson.put("entityType", (Object)(field.getRefDataEntityKey().endsWith("@ORG") ? "ORG" : "BASE"));
                extDataJson.put("treeStruct", (Object)dimension.isTree());
                extDataJson.put("maxLevel", entityTable.getMaxDepth());
                treeNode.setData(extDataJson.toString());
                return treeNode;
            }).collect(Collectors.toList());
            treeNodes.addAll(innerDimNodes);
        }
    }

    private void buildInnerDimensionItemNodes(List<TreeNode> treeNodes, TreeQueryParam treeQueryParam, boolean isRoot) throws SummaryCommonException {
        List entityRows;
        JSONObject extDataParamJson = new JSONObject(treeQueryParam.getCustomValue("ext").toString());
        String entityType = extDataParamJson.optString("entityType");
        String refreEntityId = extDataParamJson.optString("referEntity");
        IEntityDefine entityDefine = this.entityMetaService.queryEntity(refreEntityId);
        IEntityTable entityTable = EntityQueryUtil.getEntityTable(refreEntityId);
        List list = entityRows = isRoot ? entityTable.getRootRows() : entityTable.getChildRows(this.getRealNodeKey(treeQueryParam));
        if (entityRows != null) {
            List entityRowNodes = entityRows.stream().map(entityRow -> {
                int directChildCount = entityTable.getDirectChildCount(entityRow.getEntityKeyData());
                String icon = refreEntityId.endsWith("@ORG") ? "#icon-_GJWzuzhijigou" : "#icon16_DH_A_NW_jichushuju";
                TreeNode treeNode = new TreeNode("innerDim@" + entityRow.getEntityKeyData(), entityRow.getCode(), entityRow.getTitle(), icon);
                treeNode.setDraggable(true);
                treeNode.setLeaf(directChildCount == 0);
                JSONObject extDataJson = new JSONObject();
                extDataJson.put("entityCode", (Object)entityRow.getCode());
                extDataJson.put("type", CaliberTreeNodeType.INNERDIM_ITEM.getCode());
                extDataJson.put("entityType", (Object)entityType);
                extDataJson.put("parentTitle", (Object)entityDefine.getTitle());
                extDataJson.put("fieldName", (Object)(entityDefine.getId() + (this.isOrg(entityDefine.getId()) ? ".CODE" : ".OBJECTCODE")));
                extDataJson.put("referEntity", (Object)refreEntityId);
                treeNode.setData(extDataJson.toString());
                return treeNode;
            }).collect(Collectors.toList());
            treeNodes.addAll(entityRowNodes);
        }
    }

    private boolean filtInnerDim(DataField dataField, Map<String, List<String>> referEntityMap) {
        String referEntity = dataField.getRefDataEntityKey();
        if (StringUtils.hasLength(referEntity)) {
            List<String> referFieldCodes = referEntityMap.get(referEntity);
            if (!referEntityMap.containsKey(referEntity)) {
                referFieldCodes = new ArrayList<String>();
                referFieldCodes.add(dataField.getCode());
                referEntityMap.put(referEntity, referFieldCodes);
                return true;
            }
            if (!referFieldCodes.contains(dataField.getCode())) {
                referFieldCodes.add(dataField.getCode());
                return true;
            }
        }
        return false;
    }

    private void buildCaliberNodes(List<TreeNode> treeNodes, String taskKey) throws SummaryCommonException {
        TaskDefine taskDefine = this.summaryParamService.getTaskDefine(taskKey);
        List<CalibreDefineDTO> calibers = this.summaryParamService.getCalibreDefines(taskDefine.getDw());
        if (calibers != null) {
            List caliberNodes = calibers.stream().map(caliber -> {
                TreeNode treeNode = new TreeNode(caliber.getKey(), caliber.getCode(), caliber.getName(), "#icon-56_ZT_A_NR_koujing");
                treeNode.setDraggable(true);
                JSONObject extDataJson = new JSONObject();
                extDataJson.put("type", CaliberTreeNodeType.CALIBER.getCode());
                extDataJson.put("entityType", (Object)"CB");
                extDataJson.put("treeStruct", caliber.getStructType() == 1);
                extDataJson.put("fieldName", (Object)caliber.getCode());
                extDataJson.put("caliberDefineKey", (Object)caliber.getKey());
                treeNode.setData(extDataJson.toString());
                return treeNode;
            }).collect(Collectors.toList());
            treeNodes.addAll(caliberNodes);
        }
    }

    private void buildCaliberItemNodes(List<TreeNode> treeNodes, TreeQueryParam treeQueryParam) {
        JSONObject extDataParamJson = new JSONObject(treeQueryParam.getCustomValue("ext").toString());
        String caliberDefineKey = extDataParamJson.optString("caliberDefineKey");
        String calibreDataCode = extDataParamJson.optString("entityCode");
        int type = extDataParamJson.optInt("type");
        CalibreDataDTO calibreDataDTO = new CalibreDataDTO();
        calibreDataDTO.setDefineKey(caliberDefineKey);
        if (type == CaliberTreeNodeType.CALIBER.getCode()) {
            calibreDataDTO.setDataTreeType(CalibreDataOption.DataTreeType.ROOT);
        } else {
            calibreDataDTO.setCode(calibreDataCode);
            calibreDataDTO.setDataTreeType(CalibreDataOption.DataTreeType.DIRECT_CHILDREN);
        }
        Result list = this.calibreDataService.list(calibreDataDTO);
        List calibreDatas = (List)list.getData();
        if (calibreDatas != null) {
            List caliberItemNodes = calibreDatas.stream().map(caliberData -> {
                TreeNode treeNode = new TreeNode(caliberData.getKey(), caliberData.getCode(), caliberData.getName(), "#icon-56_ZT_A_NR_koujing");
                treeNode.setDraggable(true);
                CalibreDataDTO childQueryParam = new CalibreDataDTO();
                childQueryParam.setDefineKey(caliberDefineKey);
                childQueryParam.setCode(caliberData.getCode());
                childQueryParam.setDataTreeType(CalibreDataOption.DataTreeType.DIRECT_CHILDREN);
                Result childDatas = this.calibreDataService.list(childQueryParam);
                treeNode.setLeaf(childDatas == null || CollectionUtils.isEmpty((Collection)childDatas.getData()));
                JSONObject extDataJson = new JSONObject();
                extDataJson.put("entityCode", (Object)caliberData.getCode());
                extDataJson.put("entityType", (Object)"CB");
                extDataJson.put("type", CaliberTreeNodeType.CALIBER_ITEM.getCode());
                extDataJson.put("fieldName", (Object)treeQueryParam.getParentCode());
                extDataJson.put("caliberDefineKey", (Object)caliberDefineKey);
                treeNode.setData(extDataJson.toString());
                return treeNode;
            }).collect(Collectors.toList());
            treeNodes.addAll(caliberItemNodes);
        }
    }

    private void buildTargetDimNodes(List<TreeNode> treeNodes, String sumTaskId) throws SummaryCommonException {
        SummarySolution solutionDefine = this.designSummarySolutionService.getSummarySolutionDefine(sumTaskId);
        String targetDimensionId = solutionDefine.getTargetDimension();
        IEntityDefine entityDefine = this.entityMetaService.queryEntity(targetDimensionId);
        IEntityTable entityTable = EntityQueryUtil.getEntityTable(targetDimensionId);
        List rootRows = entityTable.getRootRows();
        TreeNode treeNode = new TreeNode("targetdim@" + targetDimensionId, entityDefine.getCode(), entityDefine.getTitle(), "#icon-16_SHU_A_NR_weidumulu");
        treeNode.setDraggable(true);
        treeNode.setLeaf(rootRows == null || rootRows.isEmpty());
        JSONObject extDataJson = new JSONObject();
        extDataJson.put("type", CaliberTreeNodeType.MASTERDIM.getCode());
        extDataJson.put("fieldName", (Object)(targetDimensionId + (this.isOrg(targetDimensionId) ? ".CODE" : ".OBJECTCODE")));
        extDataJson.put("referEntity", (Object)targetDimensionId);
        extDataJson.put("entityType", (Object)(this.isOrg(targetDimensionId) ? "ORG" : "BASE"));
        extDataJson.put("treeStruct", (Object)entityDefine.isTree());
        extDataJson.put("maxLevel", entityTable.getMaxDepth());
        treeNode.setData(extDataJson.toString());
        treeNodes.add(treeNode);
    }

    private void buildTargetFieldDimensionNodes(List<TreeNode> treeNodes, String sumTaskId) {
        SummarySolution solutionDefine = this.designSummarySolutionService.getSummarySolutionDefine(sumTaskId);
        String targetDimensionId = solutionDefine.getTargetDimension();
        IEntityDefine entityDefine = this.entityMetaService.queryEntity(targetDimensionId);
        List<IEntityAttribute> attrs = this.summaryParamService.getDimensionAttributes(targetDimensionId);
        if (attrs != null) {
            List attrNodes = attrs.stream().filter(attr -> StringUtils.hasLength(attr.getReferTableID())).map(attr -> {
                TableModelDefine tableModelDefine = this.dataModelService.getTableModelDefineById(attr.getReferTableID());
                String referEntityId = this.entityMetaService.getEntityIdByCode(tableModelDefine.getCode());
                IEntityTable entityTable = null;
                try {
                    entityTable = EntityQueryUtil.getEntityTable(referEntityId);
                }
                catch (Exception e) {
                    throw new RuntimeException(e);
                }
                TreeNode treeNode = new TreeNode("masterdimfield@" + attr.getID(), attr.getCode(), attr.getTitle(), "#icon-16_SHU_A_NR_weidumulu");
                treeNode.setDraggable(true);
                JSONObject extDataJson = new JSONObject();
                extDataJson.put("type", CaliberTreeNodeType.MASTERDIMFIELD.getCode());
                extDataJson.put("referEntity", (Object)referEntityId);
                extDataJson.put("fieldName", (Object)(targetDimensionId + "." + attr.getCode()));
                extDataJson.put("entityType", (Object)(this.isOrg(referEntityId) ? "ORG" : "BASE"));
                extDataJson.put("treeStruct", (Object)entityDefine.isTree());
                extDataJson.put("maxLevel", entityTable.getMaxDepth());
                treeNode.setData(extDataJson.toString());
                return treeNode;
            }).collect(Collectors.toList());
            treeNodes.addAll(attrNodes);
        }
    }

    private void buildTargetCaliberNodes(List<TreeNode> treeNodes, String sumTaskId) throws SummaryCommonException {
        SummarySolution solutionDefine = this.designSummarySolutionService.getSummarySolutionDefine(sumTaskId);
        String targetDimensionId = solutionDefine.getTargetDimension();
        List<CalibreDefineDTO> calibers = this.summaryParamService.getCalibreDefines(targetDimensionId);
        if (calibers != null) {
            List caliberNodes = calibers.stream().map(caliber -> {
                TreeNode treeNode = new TreeNode(caliber.getKey(), caliber.getCode(), caliber.getName(), "#icon-56_ZT_A_NR_koujing");
                treeNode.setDraggable(true);
                JSONObject extDataJson = new JSONObject();
                extDataJson.put("type", CaliberTreeNodeType.CALIBER.getCode());
                extDataJson.put("entityType", (Object)"CB");
                extDataJson.put("treeStruct", caliber.getStructType() == 1);
                extDataJson.put("fieldName", (Object)caliber.getCode());
                extDataJson.put("caliberDefineKey", (Object)caliber.getKey());
                treeNode.setData(extDataJson.toString());
                return treeNode;
            }).collect(Collectors.toList());
            treeNodes.addAll(caliberNodes);
        }
    }

    private boolean isOrg(String entityId) {
        return entityId.endsWith("@ORG");
    }
}

