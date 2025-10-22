/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.org.OrgCategoryDO
 */
package nr.single.para.basedata;

import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.org.OrgCategoryDO;

public interface IOrgDataDefineService {
    public OrgCategoryDO queryOrgDatadefine(String var1);

    public R InsertOrgDataDefine(OrgCategoryDO var1);

    public R update(OrgCategoryDO var1);
}

