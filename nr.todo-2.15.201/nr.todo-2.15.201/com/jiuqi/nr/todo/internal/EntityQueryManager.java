/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.util.NpRollbackException
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  javax.annotation.Resource
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.todo.internal;

import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.util.NpRollbackException;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.todo.internal.TodoEntityUpgraderImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor={NpRollbackException.class})
@Service(value="todo.entityManager")
public class EntityQueryManager
extends TodoEntityUpgraderImpl {
    @Resource
    IDataAccessProvider dataAccessProvider;

    public List<Map<String, String>> getUnitDetails(String formSchemeKey, List<String> unitIds, String period) throws Exception {
        if (formSchemeKey == null) {
            return null;
        }
        if (unitIds == null || unitIds.isEmpty()) {
            return null;
        }
        String fristEntity = this.getFristEntity(formSchemeKey);
        EntityViewDefine entityViewDefine = this.getEntityViewDefine(fristEntity);
        DimensionValueSet dimvalue = new DimensionValueSet();
        dimvalue.setValue(this.getMainDimName(entityViewDefine), unitIds);
        dimvalue.setValue("DATATIME", (Object)period);
        List<IEntityRow> entityRows = this.getEntityAllRows(formSchemeKey, dimvalue, entityViewDefine);
        return entityRows.stream().map(e -> {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("unitTitle", e.getTitle());
            map.put("unitCode", e.getCode());
            return map;
        }).collect(Collectors.toList());
    }

    public List<Map<String, String>> getFormOrGroupDetails(String formSchemeId, List<String> fromOrGroups) throws Exception {
        ArrayList<Map<String, String>> result = new ArrayList<Map<String, String>>();
        if (formSchemeId == null) {
            return null;
        }
        if (fromOrGroups == null || fromOrGroups.isEmpty()) {
            return null;
        }
        for (String formOrGroupKey : fromOrGroups) {
            FormGroupDefine formGroupDefine;
            if (formOrGroupKey == null || formOrGroupKey.isEmpty()) continue;
            String[] formKeys = formOrGroupKey.split("\u3001");
            if (formKeys.length > 0) {
                for (String formKey : formKeys) {
                    FormGroupDefine formGroupDefine2;
                    FormDefine formDefine = this.runTimeViewController.queryFormById(formKey);
                    if (formDefine != null) {
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("unitTitle", formDefine.getTitle());
                        map.put("unitCode", formDefine.getFormCode());
                        result.add(map);
                    }
                    if ((formGroupDefine2 = this.runTimeViewController.queryFormGroup(formKey)) == null) continue;
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("unitTitle", formGroupDefine2.getTitle());
                    map.put("unitCode", formGroupDefine2.getCode());
                    result.add(map);
                }
                continue;
            }
            FormDefine formDefine = this.runTimeViewController.queryFormById(formOrGroupKey);
            if (formDefine != null) {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("unitTitle", formDefine.getTitle());
                map.put("unitCode", formDefine.getFormCode());
                result.add(map);
            }
            if ((formGroupDefine = this.runTimeViewController.queryFormGroup(formOrGroupKey)) == null) continue;
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("unitTitle", formGroupDefine.getTitle());
            map.put("unitCode", formGroupDefine.getCode());
            result.add(map);
        }
        return result;
    }

    public String getMainDimName(EntityViewDefine viewDefine) {
        return this.entityMetaService.getDimensionName(viewDefine.getEntityId());
    }
}

