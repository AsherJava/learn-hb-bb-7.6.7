/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.BuildTreeUtil
 *  com.jiuqi.va.domain.common.OrderNumUtil
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.TreeVO
 *  com.jiuqi.va.domain.datamodel.DataModelGroupExternalDO
 *  com.jiuqi.va.domain.datamodel.DataModelGroupExternalDTO
 */
package com.jiuqi.va.datamodel.service.impl;

import com.jiuqi.va.datamodel.common.DataModelCoreI18nUtil;
import com.jiuqi.va.datamodel.dao.VaDataModelGroupDao;
import com.jiuqi.va.datamodel.domain.DataModelGroupDO;
import com.jiuqi.va.datamodel.domain.DataModelGroupDTO;
import com.jiuqi.va.datamodel.service.VaDataModelGroupService;
import com.jiuqi.va.domain.common.BuildTreeUtil;
import com.jiuqi.va.domain.common.OrderNumUtil;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.TreeVO;
import com.jiuqi.va.domain.datamodel.DataModelGroupExternalDO;
import com.jiuqi.va.domain.datamodel.DataModelGroupExternalDTO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class VaDataModelGroupServiceImpl
implements VaDataModelGroupService {
    @Autowired
    VaDataModelGroupDao vaDataModalGroupDao;

    @Override
    public int add(DataModelGroupDO param) {
        param.setId(UUID.randomUUID());
        param.setOrdernum(OrderNumUtil.getOrderNumByCurrentTimeMillis());
        return this.vaDataModalGroupDao.insert((Object)param);
    }

    @Override
    public int delete(DataModelGroupDO param) {
        DataModelGroupDO delParam = new DataModelGroupDO();
        delParam.setId(param.getId());
        return this.vaDataModalGroupDao.delete((Object)delParam);
    }

    @Override
    public int update(DataModelGroupDO param) {
        return this.vaDataModalGroupDao.updateByPrimaryKeySelective((Object)param);
    }

    @Override
    public List<DataModelGroupDO> list(DataModelGroupDTO param) {
        return this.vaDataModalGroupDao.list(param);
    }

    @Override
    public boolean exist(DataModelGroupDO param) {
        return this.vaDataModalGroupDao.selectCount((Object)param) > 0;
    }

    @Override
    public PageVO<TreeVO<DataModelGroupDO>> tree(DataModelGroupDTO param) {
        PageVO page = new PageVO();
        ArrayList<TreeVO> rows = new ArrayList<TreeVO>();
        TreeVO root = new TreeVO();
        root.setId("-");
        root.setParentid("-");
        root.setText(DataModelCoreI18nUtil.getMessage("datamodel.attribute.group.root", new Object[0]));
        List<DataModelGroupDO> list = this.list(param);
        if (list != null && !list.isEmpty()) {
            if (list.size() > 1) {
                Collections.sort(list, (o1, o2) -> {
                    if (o1.getOrdernum() == null) {
                        return -1;
                    }
                    if (o2.getOrdernum() == null) {
                        return 1;
                    }
                    return o1.getOrdernum().compareTo(o2.getOrdernum());
                });
            }
            ArrayList<TreeVO> nodes = new ArrayList<TreeVO>();
            for (DataModelGroupDO data : list) {
                TreeVO node = new TreeVO();
                node.setId(data.getName());
                node.setParentid(data.getParentname());
                node.setText(data.getTitle());
                nodes.add(node);
            }
            rows.add(BuildTreeUtil.build(nodes, (TreeVO)root));
        } else {
            rows.add(root);
        }
        page.setRows(rows);
        page.setRs(R.ok((String)DataModelCoreI18nUtil.getMessage("datamodel.success.common.operate", new Object[0])));
        return page;
    }

    @Override
    public R externalAdd(DataModelGroupExternalDTO param) {
        if (!StringUtils.hasText(param.getBiztype()) || !StringUtils.hasText(param.getName())) {
            return R.error((String)DataModelCoreI18nUtil.getMessage("datamodel.error.parameter.missing", new Object[0]));
        }
        DataModelGroupDO oldParam = new DataModelGroupDO();
        oldParam.setBiztype(param.getBiztype());
        oldParam.setName(param.getName());
        if (this.exist(oldParam)) {
            return R.error((String)DataModelCoreI18nUtil.getMessage("datamodel.error.group.add.existed", new Object[0]));
        }
        DataModelGroupDO dataModelGroupDO = new DataModelGroupDO();
        dataModelGroupDO.setBiztype(param.getBiztype());
        dataModelGroupDO.setExtInfo(param.getExtInfo());
        dataModelGroupDO.setId(param.getId());
        dataModelGroupDO.setName(param.getName());
        dataModelGroupDO.setOrdernum(param.getOrdernum());
        dataModelGroupDO.setParentname(param.getParentname());
        dataModelGroupDO.setTenantName(param.getTenantName());
        dataModelGroupDO.setTitle(param.getTitle());
        if (this.add(dataModelGroupDO) > 0) {
            return R.ok((String)DataModelCoreI18nUtil.getMessage("datamodel.success.common.operate", new Object[0]));
        }
        return R.error((String)DataModelCoreI18nUtil.getMessage("datamodel.error.common.operate", new Object[0]));
    }

    @Override
    public DataModelGroupExternalDO externalGet(DataModelGroupExternalDTO param) {
        List<DataModelGroupExternalDO> list = this.externalList(param);
        if (list == null || list.isEmpty()) {
            return null;
        }
        for (DataModelGroupExternalDO group : list) {
            if (!group.getName().equals(param.getName())) continue;
            return group;
        }
        return null;
    }

    @Override
    public List<DataModelGroupExternalDO> externalList(DataModelGroupExternalDTO param) {
        DataModelGroupDTO dataModelGroupDTO = new DataModelGroupDTO();
        dataModelGroupDTO.setBiztype(param.getBiztype());
        dataModelGroupDTO.setExtInfo(param.getExtInfo());
        dataModelGroupDTO.setId(param.getId());
        dataModelGroupDTO.setName(param.getName());
        dataModelGroupDTO.setOrdernum(param.getOrdernum());
        dataModelGroupDTO.setParentname(param.getParentname());
        dataModelGroupDTO.setTenantName(param.getTenantName());
        dataModelGroupDTO.setTitle(param.getTitle());
        dataModelGroupDTO.setLimit(param.getLimit());
        dataModelGroupDTO.setOffset(param.getOffset());
        dataModelGroupDTO.setPagination(param.getPagination());
        dataModelGroupDTO.setSearchKey(param.getSearchKey());
        List<DataModelGroupDO> list = this.list(dataModelGroupDTO);
        if (list == null) {
            return null;
        }
        ArrayList<DataModelGroupExternalDO> resultList = new ArrayList<DataModelGroupExternalDO>();
        for (DataModelGroupDO group : list) {
            DataModelGroupExternalDO dataModelGroupExternalDO = new DataModelGroupExternalDO();
            dataModelGroupExternalDO.setBiztype(group.getBiztype());
            dataModelGroupExternalDO.setExtInfo(group.getExtInfo());
            dataModelGroupExternalDO.setId(group.getId());
            dataModelGroupExternalDO.setName(group.getName());
            dataModelGroupExternalDO.setOrdernum(group.getOrdernum());
            dataModelGroupExternalDO.setParentname(group.getParentname());
            dataModelGroupExternalDO.setTenantName(group.getTenantName());
            dataModelGroupExternalDO.setTitle(group.getTitle());
            resultList.add(dataModelGroupExternalDO);
        }
        return resultList;
    }
}

