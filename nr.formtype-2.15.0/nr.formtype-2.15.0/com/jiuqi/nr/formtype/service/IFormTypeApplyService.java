/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.ext.formtype.IGetOrgFormTypeZbService
 */
package com.jiuqi.nr.formtype.service;

import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.ext.formtype.IGetOrgFormTypeZbService;
import com.jiuqi.nr.formtype.common.EntityRowBizCodeGetter;
import com.jiuqi.nr.formtype.common.EntityUnitNatureGetter;
import com.jiuqi.nr.formtype.common.UnitNature;
import com.jiuqi.nr.formtype.common.UnitNatureGetter;
import com.jiuqi.nr.formtype.facade.FormTypeDataDefine;
import java.util.Map;

public interface IFormTypeApplyService
extends IGetOrgFormTypeZbService {
    public boolean enableNrFormTypeMgr();

    default public boolean isMdOrg(String categoryname) {
        return "MD_ORG".equals(categoryname);
    }

    public boolean isFormType(String var1);

    public String getEntityFormTypeCode(String var1);

    public EntityUnitNatureGetter getEntityFormTypeGetter(String var1);

    public EntityRowBizCodeGetter getEntityRowBizCodeGetter(IEntityTable var1);

    public UnitNatureGetter getUnitNatureGetter(String var1);

    public String getAutoGenUnitCode(String var1, UnitNature var2);

    public String getIcon(FormTypeDataDefine var1);

    public String getSysIconByUnitNature(UnitNature var1);

    public String getSysIcon(String var1);

    public Map<String, String> getAllSysIcon();

    public Map<String, String> getAllSysIcon(String var1);

    public Map<String, String> getIcon(String var1);

    public String getIcon(String var1, String var2);
}

