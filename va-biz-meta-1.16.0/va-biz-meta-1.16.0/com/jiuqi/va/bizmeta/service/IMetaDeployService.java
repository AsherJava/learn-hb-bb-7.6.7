/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.bizmeta.service;

import com.jiuqi.va.bizmeta.domain.metadeploy.MetaDataDeployDTO;
import com.jiuqi.va.bizmeta.domain.metamodel.MetaModelDTO;

public interface IMetaDeployService {
    public MetaDataDeployDTO getDeployDatas(String var1, MetaModelDTO var2);

    public MetaDataDeployDTO publishMetaData(String var1, MetaDataDeployDTO var2);

    public MetaDataDeployDTO getDeployDataByUniqueCode(String var1);
}

