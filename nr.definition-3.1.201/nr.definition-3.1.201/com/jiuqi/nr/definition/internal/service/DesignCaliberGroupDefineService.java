/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.UUIDUtils
 */
package com.jiuqi.nr.definition.internal.service;

import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.nr.definition.facade.DesignCaliberGroupDefine;
import com.jiuqi.nr.definition.internal.dao.DesignCaliberGroupDao;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DesignCaliberGroupDefineService {
    @Autowired
    private DesignCaliberGroupDao caliberGroupdao;

    public String insertCaliberGroupDefine(DesignCaliberGroupDefine define) throws Exception {
        if (define.getKey() == null) {
            define.setKey(UUIDUtils.getKey());
        }
        this.caliberGroupdao.insert(define);
        return define.getKey();
    }

    public void updateCaliberGroupDefine(DesignCaliberGroupDefine define) throws Exception {
        this.caliberGroupdao.update(define);
    }

    public List<DesignCaliberGroupDefine> queryCaliberGroupDefineByGroupId(String id) throws Exception {
        return this.caliberGroupdao.listByGroup(id);
    }

    public DesignCaliberGroupDefine queryCaliberGroupDefine(String id) throws Exception {
        return this.caliberGroupdao.getDefineByKey(id);
    }

    public DesignCaliberGroupDefine queryCaliberGroupDefineByCode(String code) throws Exception {
        return this.caliberGroupdao.queryDefinesByCode(code);
    }

    public List<DesignCaliberGroupDefine> queryAllCaliberGroupDefine() throws Exception {
        return this.caliberGroupdao.list();
    }

    public void delete(String[] keys) throws Exception {
        this.caliberGroupdao.delete(keys);
    }

    public void delete(String key) throws Exception {
        this.caliberGroupdao.delete(key);
    }

    public void deleteAllCaliberGroups() throws Exception {
        this.caliberGroupdao.deleteAll();
    }

    public List<DesignCaliberGroupDefine> queryCaliberGroupDefinesByTitle(String CaliberGroupTitle) throws Exception {
        return this.caliberGroupdao.queryDefinesByTitle(CaliberGroupTitle);
    }

    public List<DesignCaliberGroupDefine> queryDefinesByParent(String parentKey) throws Exception {
        return this.caliberGroupdao.queryDefinesByParent(parentKey);
    }

    public List<DesignCaliberGroupDefine> queryDefinesByParent(String parentKey, boolean isRecursion) throws Exception {
        List<DesignCaliberGroupDefine> list = this.caliberGroupdao.queryDefinesByParent(parentKey);
        if (isRecursion && list != null) {
            for (DesignCaliberGroupDefine group : list) {
                this.queryDefinesByParentToList(list, group.getKey());
            }
        }
        return list;
    }

    private void queryDefinesByParentToList(List<DesignCaliberGroupDefine> list, String parentKey) throws Exception {
        List<DesignCaliberGroupDefine> list2 = this.caliberGroupdao.queryDefinesByParent(parentKey);
        list.addAll(list2);
        if (list2.size() > 0) {
            for (DesignCaliberGroupDefine group : list2) {
                this.queryDefinesByParentToList(list, group.getKey());
            }
        }
    }

    public DesignCaliberGroupDefine queryCaliberGroupDefinesByCode(String code) throws Exception {
        return this.caliberGroupdao.queryDefinesByCode(code);
    }

    public List<String> insertCaliberGroupDefines(DesignCaliberGroupDefine[] defines) throws Exception {
        ArrayList<String> result = new ArrayList<String>();
        for (int i = 0; i < defines.length; ++i) {
            String key = UUIDUtils.getKey();
            defines[i].setKey(key);
            result.add(key);
        }
        this.caliberGroupdao.insert(defines);
        return result;
    }

    public void updateCaliberGroupDefines(DesignCaliberGroupDefine[] defines) throws Exception {
        this.caliberGroupdao.update(defines);
    }

    public List<DesignCaliberGroupDefine> queryCaliberGroupDefines(String[] keys) throws Exception {
        ArrayList<DesignCaliberGroupDefine> list = new ArrayList<DesignCaliberGroupDefine>();
        for (String key : keys) {
            DesignCaliberGroupDefine define = this.caliberGroupdao.getDefineByKey(key);
            if (define == null) continue;
            list.add(define);
        }
        return list;
    }

    public List<DesignCaliberGroupDefine> getAllGroupsFromCaliber(String CaliberKey) throws Exception {
        List<DesignCaliberGroupDefine> list = this.caliberGroupdao.getAllGroupsFromCaliber(CaliberKey);
        return list;
    }

    public List<DesignCaliberGroupDefine> getAllCaliberGroupLink() throws Exception {
        List<DesignCaliberGroupDefine> list = this.caliberGroupdao.getAllGroupsFromLink();
        return list;
    }
}

