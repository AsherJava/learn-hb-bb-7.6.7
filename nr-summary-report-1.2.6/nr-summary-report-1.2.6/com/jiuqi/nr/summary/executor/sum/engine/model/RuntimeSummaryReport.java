/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.nr.summary.executor.sum.engine.model;

import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.summary.executor.sum.SumContext;
import com.jiuqi.nr.summary.executor.sum.engine.model.RuntimeSummaryRegion;
import com.jiuqi.nr.summary.executor.sum.engine.runtime.SummaryDimensionInfo;
import com.jiuqi.nr.summary.executor.sum.engine.runtime.SummaryRegionExecutor;
import com.jiuqi.nr.summary.executor.sum.engine.runtime.SummaryRegionMemroyExecutor;
import com.jiuqi.nr.summary.executor.sum.engine.runtime.SummaryRegionSQLExecutor;
import com.jiuqi.nr.summary.model.report.SummaryFloatRegion;
import com.jiuqi.nr.summary.model.report.SummaryReportModel;
import com.jiuqi.nr.summary.utils.SummaryReportModelHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.util.CollectionUtils;

public class RuntimeSummaryReport {
    private SummaryReportModel modelDefine;
    private RuntimeSummaryRegion fixRegion;
    private Map<Integer, RuntimeSummaryRegion> floatRegions = new HashMap<Integer, RuntimeSummaryRegion>();
    private List<SummaryRegionExecutor> executors = new ArrayList<SummaryRegionExecutor>();
    private IExpression reportCondition;
    private SummaryReportModelHelper helper;

    public RuntimeSummaryReport(SummaryReportModel modelDefine) {
        this.modelDefine = modelDefine;
        this.fixRegion = new RuntimeSummaryRegion(this);
        this.helper = new SummaryReportModelHelper(modelDefine);
    }

    public void doInit(SumContext sumContext) throws Exception {
        String reportFilter = this.helper.getReportFilter();
        if (StringUtils.isNotEmpty((String)reportFilter)) {
            this.reportCondition = sumContext.getFormulaParser().parseCond(reportFilter, (IContext)sumContext);
        }
        this.fixRegion.doInit(sumContext);
        this.createRegionExecutor(sumContext, this.fixRegion);
        List<SummaryFloatRegion> floatRegionList = this.helper.getFloatRegions();
        if (!CollectionUtils.isEmpty(floatRegionList)) {
            for (SummaryFloatRegion region : floatRegionList) {
                RuntimeSummaryRegion rRegion = new RuntimeSummaryRegion(region, this);
                rRegion.doInit(sumContext);
                if (rRegion.getCells().size() == 0) continue;
                this.floatRegions.put(region.getPosition(), rRegion);
                this.createRegionExecutor(sumContext, rRegion);
            }
        }
    }

    public void doExecute(SumContext sumContext, SummaryDimensionInfo dimensionInfo) throws Exception {
        for (SummaryRegionExecutor executor : this.executors) {
            SumContext context = new SumContext(sumContext, executor.getRegion(), dimensionInfo);
            Throwable throwable = null;
            try {
                if (dimensionInfo.getMdTempAssistantTable() != null) {
                    context.getTempAssistantTables().put(sumContext.getParam().getTargetMdEntity().getDimensionName(), dimensionInfo.getMdTempAssistantTable());
                }
                executor.clearData(context);
                executor.execute(context);
            }
            catch (Throwable throwable2) {
                throwable = throwable2;
                throw throwable2;
            }
            finally {
                if (context == null) continue;
                if (throwable != null) {
                    try {
                        context.close();
                    }
                    catch (Throwable throwable3) {
                        throwable.addSuppressed(throwable3);
                    }
                    continue;
                }
                context.close();
            }
        }
    }

    public List<RuntimeSummaryRegion> getAllRegions() {
        ArrayList<RuntimeSummaryRegion> allRegions = new ArrayList<RuntimeSummaryRegion>();
        if (this.fixRegion != null) {
            allRegions.add(this.fixRegion);
        }
        allRegions.addAll(this.floatRegions.values());
        return allRegions;
    }

    private void createRegionExecutor(SumContext sumContext, RuntimeSummaryRegion region) {
        if (region.supportSQLMode(sumContext)) {
            this.executors.add(new SummaryRegionSQLExecutor(region));
        } else {
            this.executors.add(new SummaryRegionMemroyExecutor(region));
        }
    }

    public SummaryReportModel getModelDefine() {
        return this.modelDefine;
    }

    public RuntimeSummaryRegion getFixRegion() {
        return this.fixRegion;
    }

    public List<RuntimeSummaryRegion> getFloatRegions() {
        return new ArrayList<RuntimeSummaryRegion>(this.floatRegions.values());
    }

    public IExpression getCondition() {
        return this.reportCondition;
    }

    public SummaryReportModelHelper getHelper() {
        return this.helper;
    }

    public String toString() {
        return "RuntimeSummaryReport [" + this.modelDefine.getName() + "]";
    }
}

