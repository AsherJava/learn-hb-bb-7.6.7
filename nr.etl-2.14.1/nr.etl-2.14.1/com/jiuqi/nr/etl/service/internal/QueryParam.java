/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 */
package com.jiuqi.nr.etl.service.internal;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.etl.common.EtlInfo;
import com.jiuqi.nr.etl.service.internal.QueryEntity;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class QueryParam {
    private Set<String> unitid = new LinkedHashSet<String>();
    private Set<String> unidCode = new LinkedHashSet<String>();
    private Set<String> formCode = new LinkedHashSet<String>();
    private Set<String> formKeySet = new LinkedHashSet<String>();

    /*
     * WARNING - void declaration
     */
    public QueryParam(EtlInfo etlInfo, QueryEntity queryEntity, IRunTimeViewController runTimeViewController, IJtableParamService jtableParamService) {
        String formKeys;
        String formSchemeKey = etlInfo.getFormSchemeKey();
        String period = etlInfo.getPeriod();
        String unitIds = etlInfo.getUnitIds();
        ArrayList<String> unitIdList = new ArrayList<String>();
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        if (StringUtils.isEmpty((String)unitIds)) {
            List<IEntityRow> entityData = queryEntity.getEntityData(formSchemeKey, period);
            for (IEntityRow iEntityRow : entityData) {
                String string = iEntityRow.getCode();
                String string2 = iEntityRow.getEntityKeyData();
                this.unitid.add(string2);
                this.unidCode.add(string);
            }
        } else {
            String[] unitSplit = unitIds.split(";");
            unitIdList.addAll(Arrays.asList(unitSplit));
            Map<String, DimensionValue> dimensionSet = etlInfo.getDimensionSet();
            for (Map.Entry<String, DimensionValue> entry : dimensionSet.entrySet()) {
                if ("DATATIME".equals(entry.getKey())) continue;
                dimensionValueSet.setValue(entry.getKey(), unitIdList);
            }
            List<IEntityRow> list = queryEntity.getEntityData(formSchemeKey, dimensionValueSet);
            for (IEntityRow iEntityRow : list) {
                String code = iEntityRow.getCode();
                String entityKeyData = iEntityRow.getEntityKeyData();
                this.unidCode.add(code);
                this.unitid.add(entityKeyData);
            }
        }
        if (StringUtils.isEmpty((String)(formKeys = etlInfo.getFormKey()))) {
            if (unitIdList.size() > 1) {
                EntityViewData masterEntity = jtableParamService.getDwEntity(etlInfo.getFormSchemeKey());
                HashSet<String> hashSet = new HashSet<String>();
                Map<String, DimensionValue> map = etlInfo.getDimensionSet();
                for (String unit : unitIdList) {
                    String[] formlist;
                    map.get(masterEntity.getDimensionName()).setValue(unit);
                    String formList = queryEntity.getFormList(etlInfo.getFormSchemeKey(), etlInfo.getTaskKey(), etlInfo.getDimensionSet());
                    for (String form : formlist = formList.split(";")) {
                        hashSet.add(form);
                    }
                }
                Iterator iterator = hashSet.iterator();
                StringBuffer formKey = new StringBuffer();
                while (iterator.hasNext()) {
                    formKey.append((String)iterator.next()).append(";");
                }
                if (formKey.length() > 0) {
                    formKey.deleteCharAt(formKey.length() - 1);
                }
                etlInfo.setFormKey(formKey.toString());
            } else {
                String formList = queryEntity.getFormList(etlInfo.getFormSchemeKey(), etlInfo.getTaskKey(), etlInfo.getDimensionSet());
                etlInfo.setFormKey(formList);
            }
            formKeys = etlInfo.getFormKey();
        }
        if (StringUtils.isNotEmpty((String)formKeys)) {
            void var14_33;
            String[] formKey;
            String[] stringArray = formKey = etlInfo.getFormKey().split(";");
            int n = stringArray.length;
            boolean bl = false;
            while (var14_33 < n) {
                String key = stringArray[var14_33];
                FormDefine queryForm = runTimeViewController.queryFormById(key);
                this.formCode.add(queryForm.getFormCode());
                this.formKeySet.add(queryForm.getKey());
                ++var14_33;
            }
        }
    }

    public Set<String> getUnitid() {
        return this.unitid;
    }

    public Set<String> getUnidCode() {
        return this.unidCode;
    }

    public Set<String> getFormCode() {
        return this.formCode;
    }

    public Set<String> getFormKeySet() {
        return this.formKeySet;
    }
}

