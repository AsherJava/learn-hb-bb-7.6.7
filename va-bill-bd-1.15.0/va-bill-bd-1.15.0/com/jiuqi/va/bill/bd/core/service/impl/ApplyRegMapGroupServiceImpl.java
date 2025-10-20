/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.BuildTreeUtil
 *  com.jiuqi.va.domain.common.OrderNumUtil
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.TreeVO
 */
package com.jiuqi.va.bill.bd.core.service.impl;

import com.jiuqi.va.bill.bd.core.dao.ApplyRegMapDao;
import com.jiuqi.va.bill.bd.core.dao.ApplyRegMapGroupDao;
import com.jiuqi.va.bill.bd.core.domain.ApplyRegMapConfigItemDTO;
import com.jiuqi.va.bill.bd.core.domain.ApplyRegMapDO;
import com.jiuqi.va.bill.bd.core.domain.ApplyRegMapGroupDO;
import com.jiuqi.va.bill.bd.core.service.ApplyRegMapGroupService;
import com.jiuqi.va.domain.common.BuildTreeUtil;
import com.jiuqi.va.domain.common.OrderNumUtil;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.TreeVO;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplyRegMapGroupServiceImpl
implements ApplyRegMapGroupService {
    @Autowired
    ApplyRegMapGroupDao groupDao;
    @Autowired
    ApplyRegMapDao mapDao;

    @Override
    public R delete(ApplyRegMapGroupDO applyRegMapGroupDO) {
        if (applyRegMapGroupDO.getId() == null) {
            return R.error((String)"\u8bf7\u6c42\u53c2\u6570\u6709\u8bef");
        }
        ApplyRegMapGroupDO groupDO = new ApplyRegMapGroupDO();
        groupDO.setId(applyRegMapGroupDO.getId());
        int i = this.groupDao.delete((Object)groupDO);
        if (i < 1) {
            return R.error((String)"\u5220\u9664\u5931\u8d25");
        }
        return R.ok();
    }

    @Override
    public R update(ApplyRegMapGroupDO applyRegMapGroupDO) {
        if (applyRegMapGroupDO.getId() == null) {
            return R.error((String)"\u8bf7\u6c42\u53c2\u6570\u6709\u8bef");
        }
        this.groupDao.updateByPrimaryKey((Object)applyRegMapGroupDO);
        return R.ok();
    }

    @Override
    public R add(ApplyRegMapGroupDO applyRegMapGroupDO) {
        applyRegMapGroupDO.setId(UUID.randomUUID().toString());
        applyRegMapGroupDO.setVer(OrderNumUtil.getOrderNumByCurrentTimeMillis());
        applyRegMapGroupDO.setCreatetime(new Date());
        this.groupDao.insert((Object)applyRegMapGroupDO);
        return R.ok();
    }

    @Override
    public PageVO<TreeVO<ApplyRegMapConfigItemDTO>> tree(ApplyRegMapGroupDO applyRegMapGroupDO) {
        PageVO page = new PageVO();
        TreeVO root = new TreeVO();
        root.setId("root");
        root.setText("\u914d\u7f6e\u6a21\u677f");
        root.setHasParent(false);
        root.setHasChildren(true);
        root.setChecked(true);
        HashMap<String, Boolean> state = new HashMap<String, Boolean>(16);
        state.put("opened", true);
        HashMap<String, Object> attributes = new HashMap<String, Object>();
        HashMap<String, String> rootNode = new HashMap<String, String>();
        rootNode.put("code", "root");
        attributes.put("param", rootNode);
        root.setAttributes(attributes);
        ArrayList<TreeVO> nodes = new ArrayList<TreeVO>();
        List groups = this.groupDao.select((Object)new ApplyRegMapGroupDO());
        String splitKey = "###";
        for (Object group : groups) {
            TreeVO node = new TreeVO();
            node.setId("group" + splitKey + ((ApplyRegMapConfigItemDTO)((Object)group)).getName());
            node.setParentid("root");
            node.setText(((ApplyRegMapConfigItemDTO)((Object)group)).getTitle());
            state = new HashMap();
            state.put("opened", false);
            node.setState(state);
            node.setChecked(false);
            attributes = new HashMap();
            attributes.put("param", group);
            attributes.put("type", "group");
            node.setAttributes(attributes);
            nodes.add(node);
        }
        List defines = this.mapDao.select((Object)new ApplyRegMapDO());
        for (ApplyRegMapDO define : defines) {
            TreeVO node = new TreeVO();
            node.setId("define" + splitKey + define.getName());
            node.setParentid("group" + splitKey + define.getGroupcode());
            node.setText(define.getTitle());
            state = new HashMap();
            state.put("opened", false);
            node.setState(state);
            node.setChecked(false);
            attributes = new HashMap();
            attributes.put("param", (Object)define);
            attributes.put("type", "define");
            node.setAttributes(attributes);
            nodes.add(node);
        }
        TreeVO tree = BuildTreeUtil.build(nodes, (TreeVO)root);
        ArrayList<TreeVO> rows = new ArrayList<TreeVO>();
        rows.add(tree);
        page.setRows(rows);
        page.setRs(R.ok());
        return page;
    }
}

