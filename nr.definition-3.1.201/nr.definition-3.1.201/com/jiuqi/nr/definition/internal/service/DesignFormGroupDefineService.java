/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.UUIDUtils
 */
package com.jiuqi.nr.definition.internal.service;

import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.nr.definition.facade.DesignFormGroupDefine;
import com.jiuqi.nr.definition.internal.dao.DesignFormGroupDefineDao;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DesignFormGroupDefineService {
    @Autowired
    private DesignFormGroupDefineDao formGroupdao;
    public static final String BIG_ANALYSIS_FORM_GROUP_PARAM = "ANALYSIS_GROUP_PARAM";

    public void setFormGroupdao(DesignFormGroupDefineDao formGroupdao) {
        this.formGroupdao = formGroupdao;
    }

    public String insertFormGroupDefine(DesignFormGroupDefine define) throws Exception {
        if (define.getKey() == null) {
            define.setKey(UUIDUtils.getKey());
        }
        if (define.getUpdateTime() == null) {
            define.setUpdateTime(new Date());
        }
        this.formGroupdao.insert(define);
        return define.getKey();
    }

    public void updateFormGroupDefine(DesignFormGroupDefine define) throws Exception {
        if (define.getUpdateTime() == null) {
            define.setUpdateTime(new Date());
        }
        this.formGroupdao.update(define);
    }

    public List<DesignFormGroupDefine> queryFormGroupDefineByGroupId(String id) throws Exception {
        return this.formGroupdao.listByGroup(id);
    }

    public DesignFormGroupDefine queryFormGroupDefine(String id) throws Exception {
        return this.formGroupdao.getDefineByKey(id);
    }

    public DesignFormGroupDefine queryFormGroupDefineByCode(String code) throws Exception {
        return this.formGroupdao.queryDefinesByCode(code);
    }

    public List<DesignFormGroupDefine> queryAllFormGroupDefine() throws Exception {
        return this.formGroupdao.list();
    }

    public void delete(String[] keys) throws Exception {
        this.formGroupdao.delete(keys);
    }

    public void delete(String key) throws Exception {
        this.formGroupdao.delete(key);
    }

    public void deleteByScheme(String Schemekey) throws Exception {
        this.formGroupdao.deleteByScheme(Schemekey);
    }

    public void deleteAllFormGroups() throws Exception {
        this.formGroupdao.deleteAll();
    }

    public List<DesignFormGroupDefine> queryFormGroupDefinesByScheme(String formSchemeId) throws Exception {
        return this.formGroupdao.queryDefinesByFormScheme(formSchemeId);
    }

    public List<DesignFormGroupDefine> queryFormGroupDefinesByScheme(String formSchemeKey, String formGroupTitle) throws Exception {
        return this.formGroupdao.queryDefinesByFormScheme(formSchemeKey, formGroupTitle);
    }

    public List<DesignFormGroupDefine> queryFormGroupDefinesByTitle(String formGroupTitle) throws Exception {
        return this.formGroupdao.queryDefinesByTitle(formGroupTitle);
    }

    public List<DesignFormGroupDefine> queryFormGroupDefinesBySchemeAndParent(String formSchemeKey, String parentKey) throws Exception {
        return this.formGroupdao.queryDefinesByFormSchemeAndParent(formSchemeKey, parentKey);
    }

    public List<DesignFormGroupDefine> queryDefinesByParent(String parentKey) throws Exception {
        return this.formGroupdao.queryDefinesByParent(parentKey);
    }

    public List<DesignFormGroupDefine> queryDefinesByParent(String parentKey, boolean isRecursion) throws Exception {
        List<DesignFormGroupDefine> list = this.formGroupdao.queryDefinesByParent(parentKey);
        if (isRecursion && list != null) {
            for (DesignFormGroupDefine group : list) {
                this.queryDefinesByParentToList(list, group.getKey());
            }
        }
        return list;
    }

    private void queryDefinesByParentToList(List<DesignFormGroupDefine> list, String parentKey) throws Exception {
        List<DesignFormGroupDefine> list2 = this.formGroupdao.queryDefinesByParent(parentKey);
        list.addAll(list2);
        if (list2.size() > 0) {
            for (DesignFormGroupDefine group : list2) {
                this.queryDefinesByParentToList(list, group.getKey());
            }
        }
    }

    public List<DesignFormGroupDefine> queryFormGroupDefinesBySchemes(String[] formSchemeIds) throws Exception {
        ArrayList<DesignFormGroupDefine> list = new ArrayList<DesignFormGroupDefine>();
        for (String id : formSchemeIds) {
            List<DesignFormGroupDefine> ids = this.queryFormGroupDefinesByScheme(id);
            if (ids == null) continue;
            list.addAll(ids);
        }
        return list;
    }

    public DesignFormGroupDefine queryFormGroupDefinesByCode(String code) throws Exception {
        return this.formGroupdao.queryDefinesByCode(code);
    }

    public List<String> insertFormGroupDefines(DesignFormGroupDefine[] defines) throws Exception {
        ArrayList<String> result = new ArrayList<String>();
        for (int i = 0; i < defines.length; ++i) {
            if (defines[i].getKey() == null) {
                defines[i].setKey(UUIDUtils.getKey());
            }
            if (defines[i].getUpdateTime() == null) {
                defines[i].setUpdateTime(new Date());
            }
            result.add(defines[i].getKey());
        }
        this.formGroupdao.insert(defines);
        return result;
    }

    public void updateFormGroupDefines(DesignFormGroupDefine[] defines) throws Exception {
        for (DesignFormGroupDefine define : defines) {
            if (define.getUpdateTime() != null) continue;
            define.setUpdateTime(new Date());
        }
        this.formGroupdao.update(defines);
    }

    public List<DesignFormGroupDefine> queryFormGroupDefines(String[] keys) throws Exception {
        ArrayList<DesignFormGroupDefine> list = new ArrayList<DesignFormGroupDefine>();
        for (String key : keys) {
            DesignFormGroupDefine define = this.formGroupdao.getDefineByKey(key);
            if (define == null) continue;
            list.add(define);
        }
        return list;
    }

    public List<DesignFormGroupDefine> getAllGroupsFromForm(String formKey) throws Exception {
        List<DesignFormGroupDefine> list = this.formGroupdao.getAllGroupsFromForm(formKey);
        return list;
    }

    public List<DesignFormGroupDefine> getAllFormGroupLink() throws Exception {
        List<DesignFormGroupDefine> list = this.formGroupdao.getAllGroupsFromLink();
        return list;
    }

    public void exchangeFormGroup(String groupKey1, String groupKey2) throws Exception {
        DesignFormGroupDefine group1 = this.queryFormGroupDefine(groupKey1);
        DesignFormGroupDefine group2 = this.queryFormGroupDefine(groupKey2);
        if (null != group1 && null != group2) {
            String oldOrder = group1.getOrder();
            group1.setOrder(group2.getOrder());
            group2.setOrder(oldOrder);
            this.updateFormGroupDefine(group1);
            this.updateFormGroupDefine(group2);
        }
    }

    public List<DesignFormGroupDefine> listGhostGroup() {
        return this.formGroupdao.listGhostGroup();
    }
}

