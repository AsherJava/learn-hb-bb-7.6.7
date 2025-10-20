/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 */
package com.jiuqi.va.basedata.service;

import com.jiuqi.va.basedata.domain.BaseDataDefineBatchOperateDTO;
import com.jiuqi.va.basedata.domain.BaseDataDummyDTO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import java.util.List;

public interface BaseDataDefineService {
    public BaseDataDefineDO get(BaseDataDefineDTO var1);

    public PageVO<BaseDataDefineDO> list(BaseDataDefineDTO var1);

    public R exist(BaseDataDefineDTO var1);

    public R existData(BaseDataDefineDTO var1);

    public R add(BaseDataDefineDTO var1);

    public R update(BaseDataDefineDTO var1);

    public R remove(BaseDataDefineDTO var1);

    public R updown(BaseDataDefineDTO var1);

    public R batchOperate(BaseDataDefineBatchOperateDTO var1);

    public List<DataModelColumn> isolationList(BaseDataDefineDTO var1);

    public R checkZbExistData(BaseDataDefineDTO var1);

    public R checkSQLDefine(BaseDataDummyDTO var1);
}

