/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.service;

import com.jiuqi.nr.definition.facade.EnumLinkageSettingDefine;
import com.jiuqi.nr.definition.internal.dao.RunTimeEnumLinkageSettingDefineDao;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RunTimeEnumLinkageSettingDefineService {
    @Autowired
    private RunTimeEnumLinkageSettingDefineDao enumLinkageEao;

    public void setEnumLinkageEao(RunTimeEnumLinkageSettingDefineDao enumLinkageEao) {
        this.enumLinkageEao = enumLinkageEao;
    }

    public List<EnumLinkageSettingDefine> queryDefinesByLinkId(String linkId) throws Exception {
        return this.enumLinkageEao.listByLink(linkId);
    }

    public EnumLinkageSettingDefine queryDefineByLinkId(String linkId) throws Exception {
        List<EnumLinkageSettingDefine> defines = this.queryDefinesByLinkId(linkId);
        if (null != null && defines.size() > 0) {
            return defines.get(0);
        }
        return null;
    }

    public EnumLinkageSettingDefine queryDefine(String id) throws Exception {
        return this.enumLinkageEao.getDefineByKey(id);
    }

    public List<EnumLinkageSettingDefine> queryDefines(String[] ids) throws Exception {
        ArrayList<EnumLinkageSettingDefine> list = new ArrayList<EnumLinkageSettingDefine>();
        for (int i = 0; i < ids.length; ++i) {
            EnumLinkageSettingDefine define = this.enumLinkageEao.getDefineByKey(ids[0]);
            list.add(define);
        }
        return list;
    }

    public EnumLinkageSettingDefine queryDefineByCode(String code) throws Exception {
        return this.enumLinkageEao.queryDefinesByCode(code);
    }

    public List<EnumLinkageSettingDefine> queryAllDefine() throws Exception {
        return this.enumLinkageEao.list();
    }

    public void delete(String[] keys) throws Exception {
        this.enumLinkageEao.delete(keys);
    }

    public void delete(String key) throws Exception {
        this.enumLinkageEao.delete(key);
    }

    public EnumLinkageSettingDefine queryDefinesByCode(String code) throws Exception {
        return this.enumLinkageEao.queryDefinesByCode(code);
    }

    public void updateDefines(EnumLinkageSettingDefine[] defines) throws Exception {
        this.enumLinkageEao.update(defines);
    }

    public List<EnumLinkageSettingDefine> queryDefine(String[] keys) throws Exception {
        ArrayList<EnumLinkageSettingDefine> list = new ArrayList<EnumLinkageSettingDefine>();
        for (String key : keys) {
            EnumLinkageSettingDefine define = this.enumLinkageEao.getDefineByKey(key);
            if (define == null) continue;
            list.add(define);
        }
        return list;
    }

    public void updateDefine(EnumLinkageSettingDefine define) throws Exception {
        this.enumLinkageEao.update(define);
    }

    public void insertDefine(EnumLinkageSettingDefine define) throws Exception {
        this.enumLinkageEao.insert(define);
    }

    public void insertDefines(EnumLinkageSettingDefine[] defines) throws Exception {
        this.enumLinkageEao.insert(defines);
    }
}

