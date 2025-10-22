/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.domain.basedata.BaseDataGroupDO
 *  com.jiuqi.va.domain.basedata.BaseDataGroupDTO
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  com.jiuqi.va.feign.client.BaseDataDefineClient
 *  com.jiuqi.va.feign.client.DataModelClient
 */
package nr.single.para.basedata.impl;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.basedata.BaseDataGroupDO;
import com.jiuqi.va.domain.basedata.BaseDataGroupDTO;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.feign.client.BaseDataClient;
import com.jiuqi.va.feign.client.BaseDataDefineClient;
import com.jiuqi.va.feign.client.DataModelClient;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import nr.single.para.basedata.IBaseDataDefineService;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseDataDefineServiceImpl
implements IBaseDataDefineService {
    @Autowired
    private BaseDataClient baseDataClient;
    @Autowired
    private BaseDataDefineClient baseDataDefineClient;
    @Autowired
    private DataModelClient dataModelClient;

    @Override
    public BaseDataGroupDO quertBaseDataGroup(String groupName) {
        BaseDataGroupDO group = null;
        BaseDataGroupDTO param = new BaseDataGroupDTO();
        param.setName(groupName);
        if (NpContextHolder.getContext() != null) {
            param.setTenantName(NpContextHolder.getContext().getTenant());
        }
        PageVO list = this.baseDataDefineClient.list(param);
        List rows = list.getRows();
        for (BaseDataGroupDO bdg : rows) {
            if (!bdg.getParentname().equals("-") && !bdg.getParentname().equals("\u5168\u90e8") || bdg.getName().equals("\u5168\u90e8") || !bdg.getName().equalsIgnoreCase(groupName)) continue;
            group = bdg;
            break;
        }
        return group;
    }

    @Override
    public void insertBaseDataGroup(BaseDataGroupDTO group) {
        this.baseDataDefineClient.add(group);
    }

    @Override
    public BaseDataGroupDO getAndInsertBaseDataGroup(String groupName, String groupTitle, String parentName) {
        BaseDataGroupDTO group = new BaseDataGroupDTO();
        group.setCreator("");
        group.setId(UUID.randomUUID());
        group.setName(groupName);
        group.setParentname(parentName);
        group.setOrdernum(new BigDecimal(OrderGenerator.newOrderID()));
        group.setTitle(groupTitle);
        if (NpContextHolder.getContext() != null) {
            group.setTenantName(NpContextHolder.getContext().getTenant());
        }
        this.baseDataDefineClient.add(group);
        return group;
    }

    @Override
    public BaseDataDefineDO queryBaseDatadefine(String baseName) {
        BaseDataDefineDTO param = new BaseDataDefineDTO();
        param.setName(baseName);
        if (NpContextHolder.getContext() != null) {
            param.setTenantName(NpContextHolder.getContext().getTenant());
        }
        BaseDataDefineDO baseDefine = this.baseDataDefineClient.get(param);
        return baseDefine;
    }

    @Override
    public R insertBaseDataDefine(BaseDataDefineDTO baseDefine) {
        R r = this.baseDataDefineClient.add(baseDefine);
        return r;
    }

    @Override
    public R update(BaseDataDefineDTO baseDefine) {
        return this.baseDataDefineClient.upate(baseDefine);
    }
}

