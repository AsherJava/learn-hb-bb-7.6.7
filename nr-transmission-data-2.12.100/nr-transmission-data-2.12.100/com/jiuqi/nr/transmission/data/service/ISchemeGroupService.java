/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.ITree
 */
package com.jiuqi.nr.transmission.data.service;

import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.transmission.data.dto.SyncSchemeGroupDTO;
import com.jiuqi.nr.transmission.data.exception.SchemeGroupServiceException;
import com.jiuqi.nr.transmission.data.vo.SchemeGroupNodeVO;
import java.util.List;

public interface ISchemeGroupService {
    public List<ITree<SchemeGroupNodeVO>> getRoot();

    public List<ITree<SchemeGroupNodeVO>> getChildren(SyncSchemeGroupDTO var1);

    public List<SyncSchemeGroupDTO> search(SyncSchemeGroupDTO var1);

    public SyncSchemeGroupDTO get(SyncSchemeGroupDTO var1);

    public SyncSchemeGroupDTO getByTitle(SyncSchemeGroupDTO var1);

    public List<ITree<SchemeGroupNodeVO>> location(SyncSchemeGroupDTO var1);

    public boolean insert(SyncSchemeGroupDTO var1) throws SchemeGroupServiceException;

    public boolean delete(SyncSchemeGroupDTO var1) throws Exception;

    public boolean update(SyncSchemeGroupDTO var1) throws SchemeGroupServiceException;
}

