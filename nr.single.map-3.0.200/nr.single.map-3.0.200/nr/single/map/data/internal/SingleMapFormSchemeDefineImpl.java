/*
 * Decompiled with CFR 0.152.
 */
package nr.single.map.data.internal;

import java.util.ArrayList;
import java.util.List;
import nr.single.map.data.facade.SingleFileEnumInfo;
import nr.single.map.data.facade.SingleFileFmdmInfo;
import nr.single.map.data.facade.SingleFileFormulaInfo;
import nr.single.map.data.facade.SingleFilePrintInfo;
import nr.single.map.data.facade.SingleFileTableInfo;
import nr.single.map.data.facade.SingleFileTaskInfo;
import nr.single.map.data.facade.SingleMapFormSchemeDefine;
import nr.single.map.data.internal.SingleFileEnumInfoImpl;
import nr.single.map.data.internal.SingleFileFmdmInfoImpl;
import nr.single.map.data.internal.SingleFileFormulaInfoImpl;
import nr.single.map.data.internal.SingleFilePrintInfoImpl;
import nr.single.map.data.internal.SingleFileTableInfoImpl;
import nr.single.map.data.internal.SingleFileTaskInfoImpl;

public class SingleMapFormSchemeDefineImpl
implements SingleMapFormSchemeDefine {
    private static final long serialVersionUID = -6832217786736316569L;
    private SingleFileTaskInfo taskInfo = new SingleFileTaskInfoImpl();
    private List<SingleFileEnumInfo> enumInfos = new ArrayList<SingleFileEnumInfo>();
    private SingleFileFmdmInfo fmdmInfo = new SingleFileFmdmInfoImpl();
    private SingleFileEnumInfo taskPeriodEnum;
    private List<SingleFileTableInfo> tableInfos = new ArrayList<SingleFileTableInfo>();
    private List<SingleFileFormulaInfo> formulaInfos = new ArrayList<SingleFileFormulaInfo>();
    private List<SingleFilePrintInfo> printInfos = new ArrayList<SingleFilePrintInfo>();
    private String mapSchemeTitle;
    private List<SingleMapFormSchemeDefine> subMapSchemes;

    public SingleMapFormSchemeDefineImpl() {
        this.taskPeriodEnum = new SingleFileEnumInfoImpl();
        this.subMapSchemes = new ArrayList<SingleMapFormSchemeDefine>();
    }

    @Override
    public SingleFileTaskInfo getTaskInfo() {
        return this.taskInfo;
    }

    @Override
    public List<SingleFileEnumInfo> getEnumInfos() {
        return this.enumInfos;
    }

    @Override
    public SingleFileEnumInfo getEnumInfoByCode(String code) {
        SingleFileEnumInfo data = null;
        for (SingleFileEnumInfo item : this.enumInfos) {
            if (!item.getEnumCode().equals(code)) continue;
            data = item;
        }
        return data;
    }

    @Override
    public SingleFileFmdmInfo getFmdmInfo() {
        return this.fmdmInfo;
    }

    @Override
    public List<SingleFileTableInfo> getTableInfos() {
        return this.tableInfos;
    }

    @Override
    public List<SingleFileFormulaInfo> getFormulaInfos() {
        return this.formulaInfos;
    }

    @Override
    public List<SingleFilePrintInfo> getPrintInfos() {
        return this.printInfos;
    }

    @Override
    public String getMapSchemeTitle() {
        return this.mapSchemeTitle;
    }

    @Override
    public List<SingleMapFormSchemeDefine> getSubMapSchemes() {
        return this.subMapSchemes;
    }

    @Override
    public void setMapSchemeTitle(String title) {
        this.mapSchemeTitle = title;
    }

    @Override
    public SingleFileEnumInfo getNewEnumInfo() {
        return new SingleFileEnumInfoImpl();
    }

    @Override
    public SingleFileTableInfo getNewTableInfo() {
        return new SingleFileTableInfoImpl();
    }

    @Override
    public SingleFileFormulaInfo getNewFormulaInfo() {
        return new SingleFileFormulaInfoImpl();
    }

    @Override
    public SingleFilePrintInfo getNewPrintInfo() {
        return new SingleFilePrintInfoImpl();
    }

    @Override
    public SingleFileEnumInfo getTaskPeriodEnum() {
        return this.taskPeriodEnum;
    }
}

