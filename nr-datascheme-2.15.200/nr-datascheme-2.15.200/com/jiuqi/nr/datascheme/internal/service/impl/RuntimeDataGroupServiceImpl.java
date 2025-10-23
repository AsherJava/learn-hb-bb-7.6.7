/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.type.DataGroupKind
 */
package com.jiuqi.nr.datascheme.internal.service.impl;

import com.jiuqi.nr.datascheme.api.type.DataGroupKind;
import com.jiuqi.nr.datascheme.internal.convert.Convert;
import com.jiuqi.nr.datascheme.internal.dao.IDataGroupDao;
import com.jiuqi.nr.datascheme.internal.dto.DataGroupDTO;
import com.jiuqi.nr.datascheme.internal.entity.DataGroupDO;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataGroupDO;
import com.jiuqi.nr.datascheme.internal.service.DataGroupService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RuntimeDataGroupServiceImpl
implements DataGroupService {
    @Autowired
    private IDataGroupDao<DataGroupDO> dataGroupDao;
    @Autowired
    private IDataGroupDao<DesignDataGroupDO> desDataGroupDao;
    private final Function<DataGroupDO, DataGroupDTO> toDto = Convert::iDg2Dto;
    private final Function<List<DataGroupDO>, List<DataGroupDTO>> list2Dto = r -> r.stream().map(this.toDto).filter(Objects::nonNull).collect(Collectors.toList());
    private final Function<List<DesignDataGroupDO>, List<DataGroupDTO>> desList2Dto = r -> r.stream().map(this.toDto).filter(Objects::nonNull).collect(Collectors.toList());

    @Override
    public List<DataGroupDTO> searchBy(String scheme, String title, int kind) {
        ArrayList<DataGroupDTO> list = new ArrayList<DataGroupDTO>();
        if ((DataGroupKind.SCHEME_GROUP.getValue() & kind) != 0) {
            List<DesignDataGroupDO> groups = this.desDataGroupDao.searchBy((String)null, title, DataGroupKind.SCHEME_GROUP.getValue());
            list.addAll((Collection<DataGroupDTO>)this.desList2Dto.apply(groups));
        }
        if ((DataGroupKind.TABLE_GROUP.getValue() & kind) != 0) {
            List<DataGroupDO> tableGroups = this.dataGroupDao.searchBy(scheme, title, kind);
            list.addAll((Collection<DataGroupDTO>)this.list2Dto.apply(tableGroups));
        }
        return list;
    }

    @Override
    public List<DataGroupDTO> searchBy(List<String> schemes, String title, int kind) {
        ArrayList<DataGroupDTO> list = new ArrayList<DataGroupDTO>();
        if ((DataGroupKind.SCHEME_GROUP.getValue() & kind) != 0) {
            List<DesignDataGroupDO> groups = this.desDataGroupDao.searchBy(schemes, title, DataGroupKind.SCHEME_GROUP.getValue());
            list.addAll((Collection<DataGroupDTO>)this.desList2Dto.apply(groups));
        }
        if ((DataGroupKind.TABLE_GROUP.getValue() & kind) != 0) {
            List<DataGroupDO> tableGroups = this.dataGroupDao.searchBy(schemes, title, kind);
            list.addAll((Collection<DataGroupDTO>)this.list2Dto.apply(tableGroups));
        }
        return list;
    }

    @Override
    public List<DataGroupDTO> searchBy(String title) {
        return null;
    }

    @Override
    public List<DataGroupDTO> searchBy(String scheme, String title) {
        List<DataGroupDO> dataGroups = this.dataGroupDao.searchBy(scheme, title, DataGroupKind.TABLE_GROUP.getValue());
        return this.list2Dto.apply(dataGroups);
    }
}

