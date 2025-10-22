/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.util.NpRollbackException
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.efdc.pojo;

import com.jiuqi.np.util.NpRollbackException;
import com.jiuqi.nr.efdc.internal.utils.ParameterUtils;
import com.jiuqi.nr.efdc.pojo.QueryEntity;
import com.jiuqi.nr.efdc.pojo.QueryEntityInfo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor={NpRollbackException.class})
public class QueryEntityImpl
implements QueryEntity {
    private List<DimessionValuePair> dimessionValuePairs = new ArrayList<DimessionValuePair>();

    public QueryEntityImpl() {
    }

    public QueryEntityImpl(QueryEntityInfo masterEntityInfo) {
        this();
        for (String dimessionName : masterEntityInfo.getDimessionNames()) {
            this.setQueryEntityDimessionValue(dimessionName, masterEntityInfo.getQueryEntityKey(dimessionName));
        }
    }

    @Override
    public Collection<String> getDimessionNames() {
        return this.dimessionValuePairs.stream().map(o -> ((DimessionValuePair)o).dimessionName).collect(Collectors.toList());
    }

    @Override
    public String getQueryEntityKey(String dimessionName) {
        ParameterUtils.AssertNotNull("dimessionName", dimessionName);
        Optional<DimessionValuePair> pairFound = this.dimessionValuePairs.stream().filter(o -> o.isMatch(dimessionName)).findFirst();
        if (pairFound.isPresent()) {
            return pairFound.get().value;
        }
        return null;
    }

    @Override
    public QueryEntity setQueryEntityDimessionValue(String dimessionName, String entityKey) {
        ParameterUtils.AssertNotNull("dimessionName", dimessionName);
        ParameterUtils.AssertNotNull("entityKey", entityKey);
        Optional<DimessionValuePair> pairFound = this.dimessionValuePairs.stream().filter(o -> o.isMatch(dimessionName)).findFirst();
        if (pairFound.isPresent()) {
            pairFound.get().value = entityKey;
        } else {
            this.dimessionValuePairs.add(new DimessionValuePair(dimessionName, entityKey));
        }
        return this;
    }

    private class DimessionValuePair {
        private String dimessionName;
        private String value;

        public DimessionValuePair(String dimessionName, String value) {
            this.dimessionName = dimessionName;
            this.value = value;
        }

        public boolean isMatch(String dimessionName) {
            return this.dimessionName.equals(dimessionName);
        }
    }
}

