/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.dc.base.client.orgcomb.vo.OrgCombDefineVO
 *  com.jiuqi.dc.base.common.nvwaTransfer.DcTransferModule
 *  com.jiuqi.va.paramsync.domain.VaParamTransferBusinessNode
 *  com.jiuqi.va.paramsync.domain.VaParamTransferCategory
 *  com.jiuqi.va.paramsync.domain.VaParamTransferFolderNode
 */
package com.jiuqi.dc.base.impl.orgcomb.service.impl;

import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.dc.base.client.orgcomb.vo.OrgCombDefineVO;
import com.jiuqi.dc.base.common.nvwaTransfer.DcTransferModule;
import com.jiuqi.dc.base.impl.orgcomb.domain.OrgCombDefineDO;
import com.jiuqi.dc.base.impl.orgcomb.domain.OrgCombGroupDO;
import com.jiuqi.dc.base.impl.orgcomb.domain.OrgCombItemDefineDO;
import com.jiuqi.dc.base.impl.orgcomb.dto.OrgCombDefineAndGroupDTO;
import com.jiuqi.dc.base.impl.orgcomb.mapper.OrgCombDefineMapper;
import com.jiuqi.dc.base.impl.orgcomb.mapper.OrgCombGroupMapper;
import com.jiuqi.dc.base.impl.orgcomb.mapper.OrgCombItemDefineMapper;
import com.jiuqi.dc.base.impl.orgcomb.service.OrgCombDefineService;
import com.jiuqi.va.paramsync.domain.VaParamTransferBusinessNode;
import com.jiuqi.va.paramsync.domain.VaParamTransferCategory;
import com.jiuqi.va.paramsync.domain.VaParamTransferFolderNode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class OrgCombGroupDcTransferModule
implements DcTransferModule {
    private static final String ORG_COMB_GROUP = "OrgCombGroup";
    private static final String ORG_COMB_GROUP_TITLE = "\u5355\u4f4d\u7ec4\u5408";
    private static final String ORG_COMB_GROUP_PRFX = "OrgCombGroup##";
    @Autowired
    private OrgCombGroupMapper orgCombGroupMapper;
    @Autowired
    private OrgCombDefineService orgCombDefineService;
    @Autowired
    private OrgCombDefineMapper orgCombDefineMapper;
    @Autowired
    private OrgCombItemDefineMapper orgCombItemDefineMapper;

    public List<VaParamTransferCategory> getCategorys() {
        ArrayList<VaParamTransferCategory> categorys = new ArrayList<VaParamTransferCategory>();
        VaParamTransferCategory category = new VaParamTransferCategory();
        category.setName(ORG_COMB_GROUP);
        category.setTitle(ORG_COMB_GROUP_TITLE);
        category.setSupportExport(true);
        category.setSupportExportData(false);
        categorys.add(category);
        return categorys;
    }

    public List<VaParamTransferFolderNode> getFolderNodes(String category, String parent) {
        if (StringUtils.hasText(parent)) {
            return null;
        }
        ArrayList<VaParamTransferFolderNode> businessNodes = new ArrayList<VaParamTransferFolderNode>();
        List<OrgCombGroupDO> orgCombGroupDOList = this.orgCombGroupMapper.getAllTreeNodes(new OrgCombGroupDO());
        orgCombGroupDOList.forEach(orgCombGroupDO -> {
            VaParamTransferFolderNode node = new VaParamTransferFolderNode();
            node.setId(ORG_COMB_GROUP_PRFX + orgCombGroupDO.getId());
            node.setName(orgCombGroupDO.getId());
            node.setTitle(orgCombGroupDO.getTitle());
            businessNodes.add(node);
        });
        return businessNodes;
    }

    public List<VaParamTransferFolderNode> getPathFolders(String category, String nodeId) {
        ArrayList<VaParamTransferFolderNode> folders = new ArrayList<VaParamTransferFolderNode>();
        if (!StringUtils.hasText(nodeId)) {
            return null;
        }
        OrgCombDefineDO orgCombDefineDO = new OrgCombDefineDO();
        orgCombDefineDO.setId(nodeId);
        List select = this.orgCombDefineMapper.select((Object)orgCombDefineDO);
        if (select.isEmpty()) {
            return null;
        }
        String groupId = ((OrgCombDefineDO)((Object)select.get(0))).getGroupId();
        List<OrgCombGroupDO> orgCombGroupDOList = this.orgCombGroupMapper.getAllTreeNodes(new OrgCombGroupDO());
        orgCombGroupDOList.forEach(orgCombGroupDO -> {
            if (groupId.equals(orgCombGroupDO.getId())) {
                VaParamTransferFolderNode node = new VaParamTransferFolderNode();
                node.setId(ORG_COMB_GROUP_PRFX + orgCombGroupDO.getId());
                node.setName(orgCombGroupDO.getId());
                node.setTitle(orgCombGroupDO.getTitle());
                folders.add(node);
            }
        });
        return folders;
    }

    public List<VaParamTransferBusinessNode> getBusinessNodes(String category, String parent) {
        if (!StringUtils.hasText(parent) || !parent.startsWith(ORG_COMB_GROUP_PRFX)) {
            return null;
        }
        ArrayList<VaParamTransferBusinessNode> businessNodes = new ArrayList<VaParamTransferBusinessNode>();
        OrgCombDefineDO orgCombDefineDO = new OrgCombDefineDO();
        parent = parent.substring(ORG_COMB_GROUP_PRFX.length());
        orgCombDefineDO.setGroupId(parent);
        List orgCombDefines = this.orgCombDefineMapper.select((Object)orgCombDefineDO);
        orgCombDefines.forEach(item -> {
            VaParamTransferBusinessNode node = new VaParamTransferBusinessNode();
            node.setId(item.getId());
            node.setName(item.getCode());
            node.setTitle(item.getName());
            node.setType(ORG_COMB_GROUP);
            node.setTypeTitle(ORG_COMB_GROUP_TITLE);
            businessNodes.add(node);
        });
        return businessNodes;
    }

    public VaParamTransferBusinessNode getBusinessNode(String category, String nodeId) {
        if (!StringUtils.hasText(nodeId)) {
            return null;
        }
        VaParamTransferBusinessNode node = new VaParamTransferBusinessNode();
        OrgCombDefineVO combDefine = this.orgCombDefineService.findData(nodeId);
        if (Objects.isNull(combDefine)) {
            return null;
        }
        node.setId(combDefine.getId());
        node.setName(combDefine.getCode());
        node.setTitle(combDefine.getName());
        node.setType(ORG_COMB_GROUP);
        node.setTypeTitle(ORG_COMB_GROUP_TITLE);
        return node;
    }

    public String getExportModelInfo(String category, String nodeId) {
        if (!StringUtils.hasText(nodeId)) {
            return null;
        }
        OrgCombDefineVO combDefine = this.orgCombDefineService.findData(nodeId);
        String preGroupId = combDefine.getGroupId();
        OrgCombGroupDO queryWrapper = new OrgCombGroupDO();
        queryWrapper.setId(preGroupId);
        OrgCombGroupDO group = (OrgCombGroupDO)((Object)this.orgCombGroupMapper.selectOne((Object)queryWrapper));
        OrgCombDefineAndGroupDTO temp = new OrgCombDefineAndGroupDTO();
        temp.setGroup(group);
        temp.setDefine(combDefine);
        return JsonUtils.writeValueAsString((Object)temp);
    }

    public void importModelInfo(String category, String info) {
        if (!StringUtils.hasText(info)) {
            return;
        }
        OrgCombDefineAndGroupDTO orgCombDefineAndGroup = (OrgCombDefineAndGroupDTO)JsonUtils.readValue((String)info, OrgCombDefineAndGroupDTO.class);
        OrgCombGroupDO group = orgCombDefineAndGroup.getGroup();
        OrgCombDefineVO combDefine = orgCombDefineAndGroup.getDefine();
        int isUpdate = this.orgCombGroupMapper.updateByPrimaryKey((Object)group);
        if (isUpdate == 0) {
            this.orgCombGroupMapper.insert((Object)group);
        }
        OrgCombDefineDO orgCombDefineDO = new OrgCombDefineDO();
        BeanUtils.copyProperties(combDefine, (Object)orgCombDefineDO);
        Integer sortOrder = orgCombDefineDO.getSortOrder();
        if (sortOrder == null) {
            orgCombDefineDO.setSortOrder(0);
        }
        if ((isUpdate = this.orgCombDefineMapper.updateByPrimaryKey((Object)orgCombDefineDO)) == 0) {
            this.orgCombDefineMapper.insert((Object)orgCombDefineDO);
        }
        List schemeItems = combDefine.getSchemeItems();
        schemeItems.forEach(item -> {
            OrgCombItemDefineDO orgCombItemDefineDO = new OrgCombItemDefineDO();
            BeanUtils.copyProperties(item, (Object)orgCombItemDefineDO);
            int res = this.orgCombItemDefineMapper.updateByPrimaryKey((Object)orgCombItemDefineDO);
            if (res == 0) {
                this.orgCombItemDefineMapper.insert((Object)orgCombItemDefineDO);
            }
        });
    }
}

