/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.logging.LogFactory
 *  com.jiuqi.np.logging.Logger
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.facade.DesignDataRegionDefine
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.examine.examiner;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.logging.LogFactory;
import com.jiuqi.np.logging.Logger;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.facade.DesignDataRegionDefine;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.examine.common.ErrorType;
import com.jiuqi.nr.examine.common.ParaType;
import com.jiuqi.nr.examine.examiner.AbstractExaminer;
import com.jiuqi.nr.examine.task.ExamineTask;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RegionExaminer
extends AbstractExaminer {
    private final Logger logger = LogFactory.getLogger(this.getClass());
    private List<DesignDataRegionDefine> regionDefines;

    public RegionExaminer(ExamineTask task) {
        super(task);
    }

    @Override
    protected ParaType getParaType() {
        return ParaType.REGION;
    }

    @Override
    protected void checkRefuse() {
        if (StringUtils.isEmpty((String)this.task.getKey())) {
            HashMap formMap = new HashMap();
            this.regionDefines.forEach(region -> {
                String formKey = region.getFormKey();
                Boolean b = (Boolean)formMap.get(formKey);
                if (b == null) {
                    DesignFormDefine formDefine = this.task.getEnv().getNrDesignController().queryFormById(formKey);
                    b = formDefine == null;
                    formMap.put(formKey, b);
                }
                if (b.booleanValue()) {
                    this.writeRefuse(region.getKey(), region.getTitle(), ErrorType.REGION_REFUSE_FORM);
                }
            });
        }
    }

    @Override
    protected void checkQuote() {
        this.regionDefines.stream().filter(r -> r.getRegionKind() != DataRegionKind.DATA_REGION_SIMPLE).forEach(r -> {
            try {
                List tables = this.task.getEnv().getNrDesignController().queryAllTableDefineInRegion(r.getKey(), false);
                if (tables.size() > 1) {
                    this.writeQuotError(r.getKey(), r.getTitle(), ErrorType.REGION_QUOTE_FLOATREGION_MULTIPLETABLES);
                }
            }
            catch (JQException e) {
                this.logger.error("\u53c2\u6570\u68c0\u67e5\u53d1\u751f\u9519\u8bef\uff01", (Throwable)e);
            }
        });
    }

    @Override
    protected void checkError() {
        HashMap form_regionMap = new HashMap();
        this.regionDefines.forEach(region -> {
            ArrayList<DesignDataRegionDefine> regionList = (ArrayList<DesignDataRegionDefine>)form_regionMap.get(region.getFormKey());
            if (regionList == null) {
                regionList = new ArrayList<DesignDataRegionDefine>();
            }
            regionList.add((DesignDataRegionDefine)region);
            form_regionMap.put(region.getFormKey(), regionList);
        });
    }

    @Override
    protected void init() {
        String paraKey = this.task.getKey();
        if (StringUtils.isEmpty((String)paraKey)) {
            this.regionDefines = this.task.getEnv().getNrDesignController().getAllRegions();
        } else {
            DesignDataRegionDefine regionDefine = this.task.getEnv().getNrDesignController().queryDataRegionDefine(paraKey);
            this.regionDefines = new ArrayList<DesignDataRegionDefine>();
            if (regionDefine != null) {
                this.regionDefines.add(regionDefine);
            }
        }
    }
}

