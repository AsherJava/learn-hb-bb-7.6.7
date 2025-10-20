/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.meta.MetaInfoDTO
 *  com.jiuqi.va.domain.meta.OperateType
 */
package com.jiuqi.va.bizmeta.service;

import com.jiuqi.va.bizmeta.domain.metaauth.AuthMetaVO;
import com.jiuqi.va.bizmeta.domain.metaauth.MetaAuthDTO;
import com.jiuqi.va.bizmeta.domain.metaauth.MetaAuthVO;
import com.jiuqi.va.bizmeta.domain.metagroup.MetaGroupDTO;
import com.jiuqi.va.bizmeta.domain.metagroup.MetaGroupVO;
import com.jiuqi.va.bizmeta.domain.metainfo.MetaInfoPageDTO;
import com.jiuqi.va.bizmeta.domain.metamodel.MetaModelDTO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.meta.MetaInfoDTO;
import com.jiuqi.va.domain.meta.OperateType;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public interface IMetaAuthService {
    public List<AuthMetaVO> listDetail(MetaAuthDTO var1);

    public R updateDetail(List<MetaAuthDTO> var1);

    public MetaAuthVO getUserAuth(MetaInfoPageDTO var1);

    public R get(MetaAuthDTO var1);

    public Set<String> checkUserAuth(MetaAuthDTO var1);

    public Set<String> getAllGroupAuth(List<MetaGroupDTO> var1, String var2);

    public MetaGroupVO getAllGroup(MetaModelDTO var1);

    public <T> void addRelationshipToMap(T var1, HashMap<String, String> var2);

    public List<MetaGroupDTO> getAuthGroupList(String var1, String var2, OperateType var3);

    public List<MetaInfoDTO> getAuthMetaList(MetaInfoPageDTO var1);
}

