/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 */
package com.jiuqi.gcreport.intermediatelibrary.dto;

import com.jiuqi.gcreport.intermediatelibrary.vo.DataDockingBlockVO;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;

public class DataDockingBlockDTO {
    private TaskDefine taskDefine;
    private DimensionValueSet dimensionValueSet;
    private FormDefine formDefine;
    private TableDefine tableDefine;
    private DataRegionDefine dataRegionDefine;
    private DataDockingBlockVO dataDockingBlockVO;
    private int blockLocation;

    public TaskDefine getTaskDefine() {
        return this.taskDefine;
    }

    public void setTaskDefine(TaskDefine taskDefine) {
        this.taskDefine = taskDefine;
    }

    public DimensionValueSet getDimensionValueSet() {
        return this.dimensionValueSet;
    }

    public void setDimensionValueSet(DimensionValueSet dimensionValueSet) {
        this.dimensionValueSet = dimensionValueSet;
    }

    public FormDefine getFormDefine() {
        return this.formDefine;
    }

    public void setFormDefine(FormDefine formDefine) {
        this.formDefine = formDefine;
    }

    public TableDefine getTableDefine() {
        return this.tableDefine;
    }

    public void setTableDefine(TableDefine tableDefine) {
        this.tableDefine = tableDefine;
    }

    public DataRegionDefine getDataRegionDefine() {
        return this.dataRegionDefine;
    }

    public void setDataRegionDefine(DataRegionDefine dataRegionDefine) {
        this.dataRegionDefine = dataRegionDefine;
    }

    public DataDockingBlockVO getDataDockingBlockVO() {
        return this.dataDockingBlockVO;
    }

    public void setDataDockingBlockVO(DataDockingBlockVO dataDockingBlockVO) {
        this.dataDockingBlockVO = dataDockingBlockVO;
    }

    public int getBlockLocation() {
        return this.blockLocation;
    }

    public void setBlockLocation(int blockLocation) {
        this.blockLocation = blockLocation;
    }
}

