/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.definition.common.FormulaSyntaxStyle
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskFlowsDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 */
package nr.single.map.data.bean;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.definition.common.FormulaSyntaxStyle;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import java.util.ArrayList;
import java.util.List;

public class TaskData {
    private String key;
    private String code;
    private String title;
    private String groupName;
    private List<EntityViewData> entitys = new ArrayList<EntityViewData>();
    private String measureUnit;
    private int periodType;
    private String fromPeriod;
    private String toPeriod;
    private int periodOffset;
    private FormulaSyntaxStyle formulaStyle;
    private boolean dataVersion;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGroupName() {
        return this.groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<EntityViewData> getEntitys() {
        return this.entitys;
    }

    public void setEntitys(List<EntityViewData> entitys) {
        this.entitys = entitys;
    }

    public String getMeasureUnit() {
        return this.measureUnit;
    }

    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }

    public int getPeriodType() {
        return this.periodType;
    }

    public void setPeriodType(int periodType) {
        this.periodType = periodType;
    }

    public String getFromPeriod() {
        return this.fromPeriod;
    }

    public void setFromPeriod(String fromPeriod) {
        this.fromPeriod = fromPeriod;
    }

    public String getToPeriod() {
        return this.toPeriod;
    }

    public void setToPeriod(String toPeriod) {
        this.toPeriod = toPeriod;
    }

    public int getPeriodOffset() {
        return this.periodOffset;
    }

    public void setPeriodOffset(int periodOffset) {
        this.periodOffset = periodOffset;
    }

    public void init(TaskDefine taskDefine) {
        PeriodWrapper fromPeriodWrapper;
        IJtableParamService jtableParamService = (IJtableParamService)BeanUtil.getBean(IJtableParamService.class);
        this.key = taskDefine.getKey();
        this.code = taskDefine.getTaskCode();
        this.title = taskDefine.getTitle();
        this.groupName = taskDefine.getGroupName();
        this.measureUnit = taskDefine.getMeasureUnit();
        this.periodType = taskDefine.getPeriodType().type();
        if (StringUtils.isEmpty((String)taskDefine.getFromPeriod())) {
            fromPeriodWrapper = new PeriodWrapper(1970, taskDefine.getPeriodType().type(), 1);
            this.fromPeriod = fromPeriodWrapper.toString();
        } else {
            this.fromPeriod = taskDefine.getFromPeriod();
        }
        if (StringUtils.isEmpty((String)taskDefine.getToPeriod())) {
            fromPeriodWrapper = new PeriodWrapper(9999, taskDefine.getPeriodType().type(), 1);
            this.toPeriod = fromPeriodWrapper.toString();
        } else {
            this.toPeriod = taskDefine.getToPeriod();
        }
        this.periodOffset = taskDefine.getTaskPeriodOffset();
        this.formulaStyle = taskDefine.getFormulaSyntaxStyle();
        String masterEntitiesKey = taskDefine.getMasterEntitiesKey();
        try {
            this.entitys = jtableParamService.getEntityList(masterEntitiesKey);
        }
        catch (Exception exception) {
            // empty catch block
        }
        TaskFlowsDefine flowsSetting = taskDefine.getFlowsSetting();
        this.dataVersion = null != flowsSetting ? flowsSetting.isReturnVersion() : false;
    }

    public FormulaSyntaxStyle getFormulaStyle() {
        return this.formulaStyle;
    }

    public void setFormulaStyle(FormulaSyntaxStyle formulaStyle) {
        this.formulaStyle = formulaStyle;
    }

    public boolean isDataVersion() {
        return this.dataVersion;
    }

    public void setDataVersion(boolean dataVersion) {
        this.dataVersion = dataVersion;
    }
}

