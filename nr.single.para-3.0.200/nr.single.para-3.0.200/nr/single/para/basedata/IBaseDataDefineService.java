/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.domain.basedata.BaseDataGroupDO
 *  com.jiuqi.va.domain.basedata.BaseDataGroupDTO
 *  com.jiuqi.va.domain.common.R
 */
package nr.single.para.basedata;

import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.basedata.BaseDataGroupDO;
import com.jiuqi.va.domain.basedata.BaseDataGroupDTO;
import com.jiuqi.va.domain.common.R;

public interface IBaseDataDefineService {
    public BaseDataGroupDO quertBaseDataGroup(String var1);

    public void insertBaseDataGroup(BaseDataGroupDTO var1);

    public BaseDataGroupDO getAndInsertBaseDataGroup(String var1, String var2, String var3);

    public BaseDataDefineDO queryBaseDatadefine(String var1);

    public R insertBaseDataDefine(BaseDataDefineDTO var1);

    public R update(BaseDataDefineDTO var1);
}

