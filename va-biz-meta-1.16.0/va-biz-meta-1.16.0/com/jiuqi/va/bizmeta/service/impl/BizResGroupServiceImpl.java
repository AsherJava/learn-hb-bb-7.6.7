/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 */
package com.jiuqi.va.bizmeta.service.impl;

import com.jiuqi.va.bizmeta.dao.IBizResGroupDao;
import com.jiuqi.va.bizmeta.dao.IBizResInfoDao;
import com.jiuqi.va.bizmeta.domain.bizres.BizResGroupDO;
import com.jiuqi.va.bizmeta.domain.bizres.BizResGroupDTO;
import com.jiuqi.va.bizmeta.domain.bizres.BizResInfoDO;
import com.jiuqi.va.bizmeta.service.IBizResGroupService;
import com.jiuqi.va.domain.common.R;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BizResGroupServiceImpl
implements IBizResGroupService {
    @Autowired
    private IBizResGroupDao bizResGroupDao;
    @Autowired
    private IBizResInfoDao bizResInfoDao;

    @Override
    public R add(BizResGroupDO bizResGroupDO) {
        try {
            bizResGroupDO.setId(UUID.randomUUID());
            bizResGroupDO.setVer(System.currentTimeMillis());
            return this.bizResGroupDao.insert((Object)bizResGroupDO) == 1 ? R.ok() : R.error();
        }
        catch (Exception e) {
            return R.error();
        }
    }

    @Override
    public R list() {
        try {
            R r = new R();
            BizResGroupDTO root = new BizResGroupDTO();
            root.setName("ZYGL");
            root.setTitle("\u8d44\u6e90\u7ba1\u7406");
            root.setCatalog(true);
            root.setId(UUID.randomUUID());
            List<BizResGroupDTO> children = this.bizResGroupDao.selectChildren(root);
            if (children.isEmpty()) {
                return r.put("data", (Object)root);
            }
            for (BizResGroupDTO child : children) {
                this.addChildren(child);
            }
            root.setChildren(children);
            return r.put("data", (Object)root);
        }
        catch (Exception e) {
            return R.error();
        }
    }

    private void addChildren(BizResGroupDTO root) {
        List<BizResGroupDTO> childs = this.bizResGroupDao.selectChildren(root);
        if (childs.isEmpty()) {
            root.setCatalog(false);
            return;
        }
        for (BizResGroupDTO child : childs) {
            this.addChildren(child);
        }
        root.setChildren(childs);
        root.setCatalog(true);
    }

    @Override
    public R delete(BizResGroupDO bizResGroupDO) {
        try {
            String name = bizResGroupDO.getName();
            BizResInfoDO bizResInfoDO = new BizResInfoDO();
            bizResInfoDO.setGroupname(name);
            int i = this.bizResInfoDao.selectCount((Object)bizResInfoDO);
            BizResGroupDO bizResGroupDO1 = new BizResGroupDO();
            bizResGroupDO1.setParentname(name);
            int i1 = this.bizResGroupDao.selectCount((Object)bizResGroupDO1);
            if (i == 0 && i1 == 0) {
                return this.bizResGroupDao.delete((Object)bizResGroupDO) == 1 ? R.ok() : R.error((String)"\u5f53\u524d\u5206\u7ec4\u4e0d\u5b58\u5728");
            }
            return R.error((String)"\u5f53\u524d\u5206\u7ec4\u4e0b\u5b58\u5728\u8d44\u6e90\u4fe1\u606f");
        }
        catch (Exception e) {
            return R.error((String)e.getMessage());
        }
    }

    @Override
    public R update(BizResGroupDO bizResGroupDO) {
        try {
            return this.bizResGroupDao.updateByPrimaryKey((Object)bizResGroupDO) == 1 ? R.ok() : R.error();
        }
        catch (Exception e) {
            return R.error();
        }
    }

    @Override
    public R checkName(String name) {
        BizResGroupDO bizResGroupDO = new BizResGroupDO();
        bizResGroupDO.setName(name);
        int i = this.bizResGroupDao.selectCount((Object)bizResGroupDO);
        return i == 0 ? R.ok() : R.error((String)"\u6807\u8bc6\u91cd\u590d");
    }
}

