/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.businesskey;

import com.jiuqi.nr.bpm.businesskey.MasterEntity;
import com.jiuqi.nr.bpm.businesskey.MasterEntityInfo;
import com.jiuqi.nr.bpm.exception.ParameterUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MasterEntityImpl
implements MasterEntity,
Serializable {
    private static final long serialVersionUID = 1L;
    private final List<DimessionValuePair> dimessionValuePairs = new ArrayList<DimessionValuePair>();

    public MasterEntityImpl() {
    }

    public MasterEntityImpl(MasterEntityInfo masterEntityInfo) {
        this();
        for (String dimessionName : masterEntityInfo.getDimessionNames()) {
            this.setMasterEntityDimessionValue(dimessionName, masterEntityInfo.getMasterEntityKey(dimessionName));
        }
    }

    @Override
    public Collection<String> getDimessionNames() {
        return this.dimessionValuePairs.stream().map(o -> ((DimessionValuePair)o).dimessionName).collect(Collectors.toList());
    }

    @Override
    public String getMasterEntityKey(String dimessionName) {
        ParameterUtils.AssertNotNull("dimessionName", dimessionName);
        Optional<DimessionValuePair> pairFound = this.dimessionValuePairs.stream().filter(o -> o.isMatch(dimessionName)).findFirst();
        if (pairFound.isPresent()) {
            return pairFound.get().value;
        }
        return null;
    }

    @Override
    public MasterEntity setMasterEntityDimessionValue(String dimessionName, String entityKey) {
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

    @Override
    public int dimessionSize() {
        return this.dimessionValuePairs.size();
    }

    private static class DimessionValuePair
    implements Serializable {
        private static final long serialVersionUID = 1L;
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

