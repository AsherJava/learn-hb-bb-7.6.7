/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.domain.basedata.BaseDataGroupDO
 *  com.jiuqi.va.domain.basedata.BaseDataGroupDTO
 *  com.jiuqi.va.domain.common.BuildTreeUtil
 *  com.jiuqi.va.domain.common.OrderNumUtil
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.TreeVO
 *  com.jiuqi.va.feign.util.LogUtil
 */
package com.jiuqi.va.basedata.service.impl;

import com.jiuqi.va.basedata.common.BaseDataCoreI18nUtil;
import com.jiuqi.va.basedata.dao.VaBaseDataDefineDao;
import com.jiuqi.va.basedata.dao.VaBaseDataGroupDao;
import com.jiuqi.va.basedata.service.BaseDataGroupService;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.basedata.BaseDataGroupDO;
import com.jiuqi.va.domain.basedata.BaseDataGroupDTO;
import com.jiuqi.va.domain.common.BuildTreeUtil;
import com.jiuqi.va.domain.common.OrderNumUtil;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.TreeVO;
import com.jiuqi.va.feign.util.LogUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service(value="vaBaseDataGroupServiceImpl")
public class BaseDataGroupServiceImpl
implements BaseDataGroupService {
    @Autowired
    private VaBaseDataGroupDao baseDataGroupDao;
    @Autowired
    private VaBaseDataDefineDao baseDataDefineDao;

    @Override
    public R exist(BaseDataGroupDTO baseDataDTO) {
        BaseDataGroupDO param = new BaseDataGroupDO();
        param.setName(baseDataDTO.getName());
        int count = this.baseDataGroupDao.selectCount(param);
        R r = R.ok();
        r.put("exist", (Object)(count > 0 ? 1 : 0));
        return r;
    }

    @Override
    public BaseDataGroupDO get(BaseDataGroupDTO param) {
        List<BaseDataGroupDO> rows = this.baseDataGroupDao.list(param);
        if (rows != null && rows.size() == 1) {
            return rows.get(0);
        }
        return null;
    }

    @Override
    public PageVO<BaseDataGroupDO> list(BaseDataGroupDTO param) {
        if (param.getParentname() != null && "root".equals(param.getParentname())) {
            param.setParentname("-");
        }
        int cnt = 0;
        boolean isPagination = param.isPagination();
        if (isPagination && (cnt = this.baseDataGroupDao.count(param)) == 0) {
            return new PageVO(true);
        }
        param.setPagination(isPagination);
        List<BaseDataGroupDO> rows = this.baseDataGroupDao.list(param);
        for (BaseDataGroupDO group : rows) {
            if (!"root".equals(group.getParentname())) continue;
            group.setParentname("-");
        }
        if (!isPagination) {
            cnt = rows.size();
        }
        PageVO page = new PageVO();
        page.setTotal(cnt);
        page.setRows(rows);
        page.setRs(R.ok());
        return page;
    }

    @Override
    public PageVO<TreeVO<BaseDataGroupDO>> tree(BaseDataGroupDTO param) {
        if (param.getParentname() != null && "root".equals(param.getParentname())) {
            param.setParentname("-");
        }
        param.setPagination(false);
        List<BaseDataGroupDO> list = this.baseDataGroupDao.list(param);
        if (list == null) {
            return new PageVO(true);
        }
        TreeVO root = new TreeVO();
        root.setId("-");
        root.setParentid("#");
        root.setText(BaseDataCoreI18nUtil.getMessage("basedata.attribute.group.root", new Object[0]));
        root.setHasParent(false);
        root.setHasChildren(true);
        HashMap<String, BaseDataGroupDO> attributes = new HashMap<String, BaseDataGroupDO>();
        BaseDataGroupDO rootGroup = new BaseDataGroupDO();
        rootGroup.setName("-");
        rootGroup.setTitle(BaseDataCoreI18nUtil.getMessage("basedata.attribute.group.root", new Object[0]));
        attributes.put("param", rootGroup);
        root.setAttributes(attributes);
        ArrayList<TreeVO> nodes = new ArrayList<TreeVO>();
        for (BaseDataGroupDO group : list) {
            if ("root".equals(group.getParentname())) {
                group.setParentname("-");
            }
            TreeVO node = new TreeVO();
            node.setId(group.getName());
            node.setParentid(group.getParentname());
            node.setText(group.getTitle());
            attributes = new HashMap();
            attributes.put("param", group);
            node.setAttributes(attributes);
            nodes.add(node);
        }
        TreeVO tree = BuildTreeUtil.build(nodes, (TreeVO)root);
        ArrayList<TreeVO> rows = new ArrayList<TreeVO>();
        rows.add(tree);
        PageVO page = new PageVO();
        page.setRows(rows);
        page.setRs(R.ok());
        return page;
    }

    @Override
    public R add(BaseDataGroupDTO baseDataDTO) {
        if (!StringUtils.hasText(baseDataDTO.getParentname()) || "root".equals(baseDataDTO.getParentname())) {
            baseDataDTO.setParentname("-");
        }
        BaseDataGroupDO param = new BaseDataGroupDO();
        BaseDataGroupDO old = null;
        if (!"-".equals(baseDataDTO.getParentname())) {
            param.setName(baseDataDTO.getParentname());
            old = (BaseDataGroupDO)this.baseDataGroupDao.selectOne(param);
            if (old == null) {
                return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.bddefineGroup.check.parent.not.exist", new Object[0]));
            }
        }
        param.setName(baseDataDTO.getName());
        old = (BaseDataGroupDO)this.baseDataGroupDao.selectOne(param);
        if (old != null) {
            return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.bddefineGroup.check.duplicate", new Object[0]));
        }
        baseDataDTO.setId(UUID.randomUUID());
        baseDataDTO.setOrdernum(OrderNumUtil.getOrderNumByCurrentTimeMillis());
        this.baseDataGroupDao.insert(baseDataDTO);
        LogUtil.add((String)"\u57fa\u7840\u6570\u636e\u5b9a\u4e49\u5206\u7ec4", (String)"\u65b0\u589e", (String)"", (String)baseDataDTO.getName(), (String)baseDataDTO.getTitle());
        return R.ok();
    }

    @Override
    public R update(BaseDataGroupDTO baseDataDTO) {
        if (!StringUtils.hasText(baseDataDTO.getParentname()) || "root".equals(baseDataDTO.getParentname())) {
            baseDataDTO.setParentname("-");
        }
        BaseDataGroupDTO param = new BaseDataGroupDTO();
        param.setName(baseDataDTO.getName());
        BaseDataGroupDO old = (BaseDataGroupDO)this.baseDataGroupDao.selectOne(param);
        if (old == null) {
            return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.bddefineGroup.check.not.exist", new Object[0]));
        }
        baseDataDTO.setId(old.getId());
        this.baseDataGroupDao.updateByPrimaryKeySelective(baseDataDTO);
        LogUtil.add((String)"\u57fa\u7840\u6570\u636e\u5b9a\u4e49\u5206\u7ec4", (String)"\u66f4\u65b0", (String)"", (String)baseDataDTO.getName(), (String)baseDataDTO.getTitle());
        return R.ok();
    }

    @Override
    public R delete(BaseDataGroupDTO param) {
        BaseDataGroupDTO basedata = new BaseDataGroupDTO();
        basedata.setName(param.getName());
        BaseDataGroupDO groupdo = (BaseDataGroupDO)this.baseDataGroupDao.selectOne(basedata);
        if (groupdo == null) {
            return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.bddefineGroup.check.not.exist", new Object[0]));
        }
        BaseDataGroupDTO basegroupdata = new BaseDataGroupDTO();
        basegroupdata.setParentname(param.getName());
        int result = this.baseDataGroupDao.selectCount(basegroupdata);
        if (result > 0) {
            return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.bddefineGroup.remove.children.existed", new Object[0]));
        }
        BaseDataDefineDTO define = new BaseDataDefineDTO();
        define.setGroupname(param.getName());
        result = this.baseDataDefineDao.selectCount(define);
        if (result > 0) {
            return R.error((String)BaseDataCoreI18nUtil.getMessage("basedata.error.bddefineGroup.remove.children.existed", new Object[0]));
        }
        this.baseDataGroupDao.delete(param);
        LogUtil.add((String)"\u57fa\u7840\u6570\u636e\u5b9a\u4e49\u5206\u7ec4", (String)"\u5220\u9664", (String)"", (String)param.getName(), (String)"");
        return R.ok();
    }
}

