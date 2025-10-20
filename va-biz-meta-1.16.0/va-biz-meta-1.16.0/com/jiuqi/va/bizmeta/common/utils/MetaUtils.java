/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.biz.ModelDTO
 *  com.jiuqi.va.domain.meta.MetaInfoEditionDO
 *  com.jiuqi.va.domain.meta.OperateType
 *  com.jiuqi.va.domain.metadeploy.MetaDataDeployDim
 */
package com.jiuqi.va.bizmeta.common.utils;

import com.jiuqi.va.bizmeta.common.consts.MetaDataConst;
import com.jiuqi.va.bizmeta.common.consts.MetaState;
import com.jiuqi.va.bizmeta.common.consts.MetaTypeEnum;
import com.jiuqi.va.bizmeta.domain.metagroup.MetaGroupDO;
import com.jiuqi.va.bizmeta.domain.metagroup.MetaGroupDTO;
import com.jiuqi.va.bizmeta.domain.metagroup.MetaGroupEditionDO;
import com.jiuqi.va.bizmeta.service.IMetaGroupService;
import com.jiuqi.va.domain.biz.ModelDTO;
import com.jiuqi.va.domain.meta.MetaInfoEditionDO;
import com.jiuqi.va.domain.meta.OperateType;
import com.jiuqi.va.domain.metadeploy.MetaDataDeployDim;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class MetaUtils {
    private static final String SEP = "_";

    public static String buildUniqueCode(String module, String metaType, String name) {
        StringBuffer uniqueCode = new StringBuffer();
        uniqueCode.append(module.toUpperCase()).append(SEP).append(Objects.requireNonNull(MetaUtils.getMetaTypeByName(metaType)).getCode()).append(SEP).append(name.toUpperCase());
        return uniqueCode.toString();
    }

    private static String pathFormat(String title, String name) {
        return String.format("%s(%s)", title, name.toUpperCase());
    }

    public static String getGroupPath(List<MetaGroupDTO> groupDTOs, MetaGroupDO groupDO) {
        StringBuilder path = new StringBuilder();
        path.append(MetaUtils.pathFormat(MetaUtils.getModuleTitleByName(groupDO.getModuleName()), groupDO.getModuleName())).append("/");
        path.append(MetaUtils.pathFormat(Objects.requireNonNull(MetaUtils.getMetaTypeByName(groupDO.getMetaType())).getTitle(), groupDO.getMetaType())).append("/");
        StringBuffer pPath = new StringBuffer();
        MetaGroupDTO groupDTO = new MetaGroupDTO();
        groupDTO.setMetaType(groupDO.getMetaType());
        groupDTO.setModuleName(groupDO.getModuleName());
        groupDTO.setParentName(groupDO.getParentName());
        new MetaUtils().addParentGroup(groupDTOs, groupDTO, pPath);
        path.append(pPath).append(MetaUtils.pathFormat(groupDO.getTitle(), groupDO.getName()));
        return path.toString();
    }

    public static String getGroupPath(List<MetaGroupDTO> groupDTOs, MetaInfoEditionDO infoEditionDO) {
        StringBuilder path = new StringBuilder();
        path.append(MetaUtils.pathFormat(MetaUtils.getModuleTitleByName(infoEditionDO.getModuleName()), infoEditionDO.getModuleName())).append("/");
        path.append(MetaUtils.pathFormat(Objects.requireNonNull(MetaUtils.getMetaTypeByName(infoEditionDO.getMetaType())).getTitle(), infoEditionDO.getMetaType())).append("/");
        StringBuffer pPath = new StringBuffer();
        MetaGroupDTO groupDTO = new MetaGroupDTO();
        groupDTO.setMetaType(infoEditionDO.getMetaType());
        groupDTO.setModuleName(infoEditionDO.getModuleName());
        groupDTO.setParentName(infoEditionDO.getGroupName());
        new MetaUtils().addParentGroup(groupDTOs, groupDTO, pPath);
        String groupTitle = infoEditionDO.getGroupName();
        Optional<MetaGroupDTO> metaGroupDTO = groupDTOs.stream().filter(o -> o.getName().equals(infoEditionDO.getGroupName())).findFirst();
        if (metaGroupDTO.isPresent()) {
            groupTitle = metaGroupDTO.get().getTitle();
        }
        path.append(MetaUtils.pathFormat(groupTitle, infoEditionDO.getGroupName())).append("/");
        path.append(MetaUtils.pathFormat(infoEditionDO.getTitle(), infoEditionDO.getName()));
        return path.toString();
    }

    private void addParentGroup(List<MetaGroupDTO> groupDTOs, MetaGroupDTO groupDTO, StringBuffer pPath) {
        for (MetaGroupDTO metaGroupDTO : groupDTOs) {
            if (!metaGroupDTO.getModuleName().equals(groupDTO.getModuleName()) || !metaGroupDTO.getMetaType().equals(groupDTO.getMetaType()) || !metaGroupDTO.getName().equals(groupDTO.getParentName()) || metaGroupDTO.getName().equals(groupDTO.getName())) continue;
            pPath.append(MetaUtils.pathFormat(metaGroupDTO.getTitle(), metaGroupDTO.getName())).append("/");
            this.addParentGroup(groupDTOs, metaGroupDTO, pPath);
        }
    }

    public static void gatherUnPublishGroup(List<MetaGroupEditionDO> groupEditionDOs, List<MetaDataDeployDim> dataDeployDims) {
        boolean includeMetaData;
        Iterator<MetaGroupEditionDO> iterator = groupEditionDOs.iterator();
        block0: while (iterator.hasNext()) {
            MetaGroupEditionDO editionDO = iterator.next();
            for (MetaDataDeployDim dataDeployDim : dataDeployDims) {
                if (!editionDO.getId().equals(dataDeployDim.getId())) continue;
                iterator.remove();
                continue block0;
            }
        }
        ArrayList<MetaDataDeployDim> parentUnDeployGroups = new ArrayList<MetaDataDeployDim>();
        block2: for (MetaGroupEditionDO editionDO : groupEditionDOs) {
            boolean isOver = false;
            for (MetaDataDeployDim dataDeployDim : dataDeployDims) {
                String[] paths = dataDeployDim.getPath().split("/");
                String module = MetaUtils.pathReFormat(paths[0]);
                String metaType = MetaUtils.pathReFormat(paths[1]);
                for (int i = paths.length - 2; i > 1; --i) {
                    if (!editionDO.getName().equals(MetaUtils.pathReFormat(paths[i])) || !editionDO.getModuleName().equals(module) || !editionDO.getMetaType().equals(metaType.toLowerCase())) continue;
                    MetaDataDeployDim deployDim = new MetaDataDeployDim();
                    deployDim.setId(editionDO.getId());
                    deployDim.setPath(dataDeployDim.getPath().substring(0, dataDeployDim.getPath().indexOf(paths[i + 1])));
                    deployDim.setState(editionDO.getMetaState().intValue());
                    deployDim.setVersion(editionDO.getVersionNo().longValue());
                    deployDim.setType("group");
                    dataDeployDims.add(deployDim);
                    isOver = true;
                    MetaUtils.packageUnDeployParentGroup(parentUnDeployGroups, deployDim, groupEditionDOs, dataDeployDims);
                    break;
                }
                if (!isOver) continue;
                continue block2;
            }
        }
        if (!parentUnDeployGroups.isEmpty() && (includeMetaData = dataDeployDims.stream().anyMatch(x -> x.getType().equalsIgnoreCase("metaData")))) {
            dataDeployDims.addAll(parentUnDeployGroups);
        }
    }

    private static void packageUnDeployParentGroup(List<MetaDataDeployDim> parentUnDeployGroups, MetaDataDeployDim deployDim, List<MetaGroupEditionDO> groupEditionDos, List<MetaDataDeployDim> dataDeployDims) {
        boolean flag = parentUnDeployGroups.stream().anyMatch(m -> m.getId().equals(deployDim.getId()));
        if (deployDim.getPath().endsWith("/")) {
            deployDim.setPath(deployDim.getPath().substring(0, deployDim.getPath().length() - 1));
        }
        if (!flag && !dataDeployDims.contains(deployDim)) {
            parentUnDeployGroups.add(deployDim);
        }
        String path = deployDim.getPath();
        String[] paths = path.split("/");
        String module = MetaUtils.pathReFormat(paths[0]);
        String metaType = MetaUtils.pathReFormat(paths[1]);
        String currentGroup = paths[paths.length - 1];
        for (MetaGroupEditionDO groupEditionDO : groupEditionDos) {
            String tempStr = groupEditionDO.getTitle() + "(" + groupEditionDO.getName() + ")";
            String parentName = groupEditionDO.getParentName();
            if (!tempStr.equals(currentGroup) || !groupEditionDO.getModuleName().equals(module) || !groupEditionDO.getMetaType().equalsIgnoreCase(metaType)) continue;
            for (MetaGroupEditionDO item : groupEditionDos) {
                String itemName = item.getName();
                String newPath = item.getTitle() + "(" + itemName + ")";
                if (!itemName.equals(parentName)) continue;
                MetaDataDeployDim deployItem = new MetaDataDeployDim();
                deployItem.setId(item.getId());
                deployItem.setPath(paths[0] + "/" + paths[1] + "/" + newPath);
                deployItem.setState(item.getMetaState().intValue());
                deployItem.setVersion(item.getVersionNo().longValue());
                deployItem.setType("group");
                deployItem.setModuleName(module);
                MetaUtils.packageUnDeployParentGroup(parentUnDeployGroups, deployItem, groupEditionDos, dataDeployDims);
            }
        }
    }

    private static String pathReFormat(String path) {
        return path.substring(path.indexOf(40) + 1, path.length() - 1);
    }

    public static List<String> getGroupNamesByParent(IMetaGroupService metaGroupService, MetaGroupDTO groupDTO) {
        ArrayList<MetaGroupDO> groupDOs = new ArrayList<MetaGroupDO>();
        List<MetaGroupDTO> groupDTOs = metaGroupService.getGroupList(null, null, OperateType.DESIGN);
        for (MetaGroupDTO metaGroupDTO : groupDTOs) {
            if (!metaGroupDTO.getModuleName().equals(groupDTO.getModuleName()) || !metaGroupDTO.getMetaType().equals(groupDTO.getMetaType())) continue;
            MetaGroupDO groupDO = new MetaGroupDO();
            groupDO.setName(metaGroupDTO.getName());
            groupDO.setParentName(metaGroupDTO.getParentName());
            groupDO.setMetaType(metaGroupDTO.getMetaType());
            groupDOs.add(groupDO);
        }
        MetaGroupDO rootNode = new MetaGroupDO();
        rootNode.setName(groupDTO.getName());
        rootNode.setParentName(groupDTO.getParentName());
        rootNode.setMetaType(groupDTO.getMetaType());
        ArrayList<String> nameList = new ArrayList<String>();
        nameList.add(groupDTO.getName());
        MetaUtils.getGroupNamesByParent(groupDOs, rootNode, nameList);
        return nameList;
    }

    public static void getGroupNamesByParent(List<MetaGroupDO> groupDOs, MetaGroupDO groupDO, List<String> nameList) {
        if (Objects.isNull(groupDOs)) {
            return;
        }
        for (MetaGroupDO metaGroupDO : groupDOs) {
            if (!groupDO.getName().equals(metaGroupDO.getParentName()) || !groupDO.getMetaType().equals(metaGroupDO.getMetaType()) || groupDO.getName().equals(metaGroupDO.getName())) continue;
            nameList.add(metaGroupDO.getName());
            MetaUtils.getGroupNamesByParent(groupDOs, metaGroupDO, nameList);
        }
    }

    public static void sortPublishDatas(List<MetaDataDeployDim> dataDeployDims) {
        dataDeployDims.sort(new Comparator<MetaDataDeployDim>(){

            @Override
            public int compare(MetaDataDeployDim o1, MetaDataDeployDim o2) {
                if ("metaData".equals(o1.getType()) && "group".equals(o2.getType())) {
                    return -1;
                }
                if ("metaData".equals(o2.getType()) && "group".equals(o1.getType())) {
                    return 1;
                }
                if (MetaState.DELETED.getValue() == o1.getState() && MetaState.DELETED.getValue() != o2.getState()) {
                    return -1;
                }
                if (MetaState.DELETED.getValue() == o2.getState() && MetaState.DELETED.getValue() != o1.getState()) {
                    return 1;
                }
                if (MetaState.APPENDED.getValue() == o1.getState() && MetaState.APPENDED.getValue() != o2.getState() && MetaState.DELETED.getValue() != o2.getState()) {
                    return -1;
                }
                if (MetaState.APPENDED.getValue() == o2.getState() && MetaState.APPENDED.getValue() != o1.getState() && MetaState.DELETED.getValue() != o1.getState()) {
                    return 1;
                }
                if (MetaState.MODIFIED.getValue() == o1.getState() && MetaState.APPENDED.getValue() != o2.getState() && MetaState.DELETED.getValue() != o2.getState() && MetaState.MODIFIED.getValue() != o2.getState()) {
                    return -1;
                }
                if (MetaState.APPENDED.getValue() == o2.getState() && MetaState.APPENDED.getValue() != o1.getState() && MetaState.DELETED.getValue() != o1.getState() && MetaState.MODIFIED.getValue() != o1.getState()) {
                    return 1;
                }
                return 0;
            }
        });
    }

    public static String getModelTitle(List<ModelDTO> modelDTOs, String modelName) {
        for (ModelDTO modelDTO : modelDTOs) {
            if (!modelDTO.getModelName().equals(modelName)) continue;
            return modelDTO.getModelTitle();
        }
        return "";
    }

    public static MetaTypeEnum getMetaTypeByName(String name) {
        for (MetaTypeEnum metaType : MetaTypeEnum.values()) {
            if (!metaType.getName().equals(name)) continue;
            return metaType;
        }
        return null;
    }

    public static String getDefalutModuleName() {
        return (String)MetaDataConst.getModuleMap().keySet().stream().findFirst().get();
    }

    public static String getDefalutModuleTitle() {
        return MetaDataConst.getModuleMap().values().stream().findFirst().get();
    }

    public static String getModuleTitleByName(String moduleName) {
        return MetaDataConst.getModuleMap().get(moduleName);
    }
}

