/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nvwa.jobmanager.sysjob.AbstractSysJob
 */
package com.jiuqi.nr.dataentity_ext.internal;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.nr.dataentity_ext.api.IEntityDataService;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nvwa.jobmanager.sysjob.AbstractSysJob;
import java.io.Serializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TmpTableCleanJob
extends AbstractSysJob
implements Serializable {
    private static final Logger log = LoggerFactory.getLogger(TmpTableCleanJob.class);
    private static final long serialVersionUID = 8034149493036649701L;

    public String getId() {
        return "NR_CLEAN_NRDE_TMP_TABLE";
    }

    public String getTitle() {
        return "NR\u81ea\u5b9a\u4e49\u5b9e\u4f53\u6570\u636e\u6269\u5c55\u4e34\u65f6\u8868\u6e05\u7406";
    }

    public void exec(JobContext context, String config) throws Exception {
        log.info("\u5f00\u59cb{}", (Object)this.getTitle());
        IEntityDataService entityDataService = (IEntityDataService)BeanUtil.getBean(IEntityDataService.class);
        entityDataService.dropByTime(86400000L);
        log.info("{}\u5b8c\u6210", (Object)this.getTitle());
    }

    public String getModelName() {
        return null;
    }

    public String getSysJobType() {
        return "SYS_CLEAN_JOB_TYPE";
    }
}

