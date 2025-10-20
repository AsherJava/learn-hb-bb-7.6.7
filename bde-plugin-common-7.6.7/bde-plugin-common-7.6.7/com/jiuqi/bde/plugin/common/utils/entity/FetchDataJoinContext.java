/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.vo.AssistExtendDimVO
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 */
package com.jiuqi.bde.plugin.common.utils.entity;

import com.jiuqi.bde.bizmodel.client.vo.AssistExtendDimVO;
import com.jiuqi.bde.plugin.common.utils.entity.AssistExtendDimHolder;
import com.jiuqi.bde.plugin.common.utils.entity.BaseDataHolder;
import com.jiuqi.bde.plugin.common.utils.entity.OrgDataHolder;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class FetchDataJoinContext {
    private final AtomicBoolean dataReady = new AtomicBoolean(false);
    private BaseDataHolder baseDataHolder;
    private OrgDataHolder orgDataHolder;
    private AssistExtendDimHolder assistExtendDimHolder;
    private QueriedDimensionHolder queriedDimensionHolder;

    public FetchDataJoinContext() {
    }

    public FetchDataJoinContext(BaseDataHolder baseDataHolder, OrgDataHolder orgDataHolder, AssistExtendDimHolder assistExtendDimHolder, QueriedDimensionHolder queriedDimensionHolder) {
        this.baseDataHolder = baseDataHolder;
        this.orgDataHolder = orgDataHolder;
        this.assistExtendDimHolder = assistExtendDimHolder;
        this.queriedDimensionHolder = queriedDimensionHolder;
    }

    public BaseDataHolder getBaseDataHolder() {
        return this.baseDataHolder;
    }

    public void setBaseDataHolder(BaseDataHolder baseDataHolder) {
        this.baseDataHolder = baseDataHolder;
    }

    public OrgDataHolder getOrgDataHolder() {
        return this.orgDataHolder;
    }

    public void setOrgDataHolder(OrgDataHolder orgDataHolder) {
        this.orgDataHolder = orgDataHolder;
    }

    public AssistExtendDimHolder getAssistExtendDimHolder() {
        return this.assistExtendDimHolder;
    }

    public void setAssistExtendDimHolder(AssistExtendDimHolder assistExtendDimHolder) {
        this.assistExtendDimHolder = assistExtendDimHolder;
    }

    public boolean isDataReady() {
        return this.dataReady.get();
    }

    public void setDataReady(boolean dataReady) {
        this.dataReady.set(dataReady);
    }

    public QueriedDimensionHolder getQueriedDimensionHolder() {
        return this.queriedDimensionHolder;
    }

    public void setQueriedDimensionHolder(QueriedDimensionHolder queriedDimensionHolder) {
        this.queriedDimensionHolder = queriedDimensionHolder;
    }

    public static class QueriedDimensionHolder {
        private List<DimensionVO> queriedAssistDimensions;
        private List<AssistExtendDimVO> queriedAssistExtendDimensions;

        public QueriedDimensionHolder() {
        }

        public QueriedDimensionHolder(List<DimensionVO> queriedAssistDimensions, List<AssistExtendDimVO> queriedAssistExtendDimensions) {
            this.queriedAssistDimensions = queriedAssistDimensions;
            this.queriedAssistExtendDimensions = queriedAssistExtendDimensions;
        }

        public List<DimensionVO> getQueriedAssistDimensions() {
            return this.queriedAssistDimensions;
        }

        public void setQueriedAssistDimensions(List<DimensionVO> queriedAssistDimensions) {
            this.queriedAssistDimensions = queriedAssistDimensions;
        }

        public List<AssistExtendDimVO> getQueriedAssistExtendDimensions() {
            return this.queriedAssistExtendDimensions;
        }

        public void setQueriedAssistExtendDimensions(List<AssistExtendDimVO> queriedAssistExtendDimensions) {
            this.queriedAssistExtendDimensions = queriedAssistExtendDimensions;
        }
    }
}

