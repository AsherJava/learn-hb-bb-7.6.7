/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignIndexModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 *  com.jiuqi.nvwa.definition.interval.deploy.DeployTableType
 */
package com.jiuqi.nr.datascheme.internal.entity;

import com.jiuqi.nr.datascheme.internal.deploy.common.DeployType;
import com.jiuqi.nr.datascheme.internal.entity.DataFieldDeployInfoDO;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignIndexModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import com.jiuqi.nvwa.definition.interval.deploy.DeployTableType;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PreDeployInfoDO {
    private String dataSchemeKey;
    private String dataTableKey;
    private PreDeployType type;
    private Instant createTime;
    private PreDeployDetails deployDetails;

    public PreDeployInfoDO(String dataSchemeKey, String dataTableKey, PreDeployType type) {
        this(dataSchemeKey, dataTableKey, type, Instant.now());
    }

    public PreDeployInfoDO(String dataSchemeKey, String dataTableKey, PreDeployType type, Instant createTime) {
        this.dataSchemeKey = dataSchemeKey;
        this.dataTableKey = dataTableKey;
        this.type = type;
        this.createTime = createTime;
    }

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public String getDataTableKey() {
        return this.dataTableKey;
    }

    public PreDeployType getType() {
        return this.type;
    }

    public Instant getCreateTime() {
        return this.createTime;
    }

    public PreDeployDetails getDeployDetails() {
        return this.deployDetails;
    }

    public void setDeployDetails(PreDeployDetails details) {
        this.deployDetails = details;
    }

    public static class PreDeployDetails
    implements Serializable {
        private static final long serialVersionUID = 1L;
        private Map<String, DesignTableModelDefine> tableModels;
        private List<DesignColumnModelDefine> addColumns;
        private List<DesignColumnModelDefine> updateColumns;
        private List<DesignColumnModelDefine> deleteColumns;
        private List<DesignIndexModelDefine> addIndexs;
        private List<DesignIndexModelDefine> updateIndexs;
        private List<DesignIndexModelDefine> deleteIndexs;
        private Set<DataFieldDeployInfoDO> addDeployInfos;
        private Set<DataFieldDeployInfoDO> updateDeployInfos;
        private Set<DataFieldDeployInfoDO> deleteDeployInfos;

        public Map<String, DesignTableModelDefine> getTableModels() {
            if (null == this.tableModels) {
                this.tableModels = new HashMap<String, DesignTableModelDefine>();
            }
            return this.tableModels;
        }

        public void setTableModels(Map<String, DesignTableModelDefine> tableModels) {
            this.tableModels = tableModels;
        }

        public List<DesignColumnModelDefine> getAddColumns() {
            if (null == this.addColumns) {
                this.addColumns = new ArrayList<DesignColumnModelDefine>();
            }
            return this.addColumns;
        }

        public void setAddColumns(List<DesignColumnModelDefine> addColumns) {
            this.addColumns = addColumns;
        }

        public List<DesignColumnModelDefine> getUpdateColumns() {
            if (null == this.updateColumns) {
                this.updateColumns = new ArrayList<DesignColumnModelDefine>();
            }
            return this.updateColumns;
        }

        public void setUpdateColumns(List<DesignColumnModelDefine> updateColumns) {
            this.updateColumns = updateColumns;
        }

        public List<DesignColumnModelDefine> getDeleteColumns() {
            if (null == this.deleteColumns) {
                this.deleteColumns = new ArrayList<DesignColumnModelDefine>();
            }
            return this.deleteColumns;
        }

        public void setDeleteColumns(List<DesignColumnModelDefine> deleteColumns) {
            this.deleteColumns = deleteColumns;
        }

        public List<DesignIndexModelDefine> getAddIndexs() {
            if (null == this.addIndexs) {
                this.addIndexs = new ArrayList<DesignIndexModelDefine>();
            }
            return this.addIndexs;
        }

        public void setAddIndexs(List<DesignIndexModelDefine> addIndexs) {
            this.addIndexs = addIndexs;
        }

        public List<DesignIndexModelDefine> getUpdateIndexs() {
            if (null == this.updateIndexs) {
                this.updateIndexs = new ArrayList<DesignIndexModelDefine>();
            }
            return this.updateIndexs;
        }

        public void setUpdateIndexs(List<DesignIndexModelDefine> updateIndexs) {
            this.updateIndexs = updateIndexs;
        }

        public List<DesignIndexModelDefine> getDeleteIndexs() {
            if (null == this.deleteIndexs) {
                this.deleteIndexs = new ArrayList<DesignIndexModelDefine>();
            }
            return this.deleteIndexs;
        }

        public void setDeleteIndexs(List<DesignIndexModelDefine> deleteIndexs) {
            this.deleteIndexs = deleteIndexs;
        }

        public Set<DataFieldDeployInfoDO> getAddDeployInfos() {
            if (null == this.addDeployInfos) {
                this.addDeployInfos = new HashSet<DataFieldDeployInfoDO>();
            }
            return this.addDeployInfos;
        }

        public void setAddDeployInfos(Set<DataFieldDeployInfoDO> addDeployInfos) {
            this.addDeployInfos = addDeployInfos;
        }

        public Set<DataFieldDeployInfoDO> getUpdateDeployInfos() {
            if (null == this.updateDeployInfos) {
                this.updateDeployInfos = new HashSet<DataFieldDeployInfoDO>();
            }
            return this.updateDeployInfos;
        }

        public void setUpdateDeployInfos(Set<DataFieldDeployInfoDO> updateDeployInfos) {
            this.updateDeployInfos = updateDeployInfos;
        }

        public Set<DataFieldDeployInfoDO> getDeleteDeployInfos() {
            if (null == this.deleteDeployInfos) {
                this.deleteDeployInfos = new HashSet<DataFieldDeployInfoDO>();
            }
            return this.deleteDeployInfos;
        }

        public void setDeleteDeployInfos(Set<DataFieldDeployInfoDO> deleteDeployInfos) {
            this.deleteDeployInfos = deleteDeployInfos;
        }
    }

    public static enum PreDeployType {
        ADD(1, DeployTableType.ADD),
        DELETE(2, DeployTableType.DELETE),
        UPDATE(3, DeployTableType.UPDATE);

        private int value;
        private DeployTableType type;
        private static final Map<Integer, PreDeployType> VALUE_MAP;
        private static final EnumMap<DeployTableType, PreDeployType> TYPE_MAP;
        private static final EnumMap<DeployType, PreDeployType> DEPLOY_TYPE_MAP;

        private PreDeployType(int value, DeployTableType type) {
            this.value = value;
            this.type = type;
        }

        public int getValue() {
            return this.value;
        }

        public static PreDeployType valueOf(int value) {
            return VALUE_MAP.get(value);
        }

        public static PreDeployType valueOf(DeployTableType type) {
            return TYPE_MAP.get(type);
        }

        public static PreDeployType valueOf(DeployType type) {
            return DEPLOY_TYPE_MAP.get((Object)type);
        }

        static {
            VALUE_MAP = new HashMap<Integer, PreDeployType>();
            TYPE_MAP = new EnumMap(DeployTableType.class);
            DEPLOY_TYPE_MAP = new EnumMap(DeployType.class);
            for (PreDeployType preDeployType : PreDeployType.values()) {
                VALUE_MAP.put(preDeployType.value, preDeployType);
                TYPE_MAP.put(preDeployType.type, preDeployType);
            }
            block5: for (Enum enum_ : DeployType.values()) {
                switch (1.$SwitchMap$com$jiuqi$nr$datascheme$internal$deploy$common$DeployType[enum_.ordinal()]) {
                    case 1: {
                        DEPLOY_TYPE_MAP.put((DeployType)enum_, ADD);
                        continue block5;
                    }
                    case 2: {
                        DEPLOY_TYPE_MAP.put((DeployType)enum_, DELETE);
                        continue block5;
                    }
                    default: {
                        DEPLOY_TYPE_MAP.put((DeployType)enum_, UPDATE);
                    }
                }
            }
        }
    }
}

