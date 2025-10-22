/*
 * Decompiled with CFR 0.152.
 */
package nr.single.map.data.facade;

import java.io.Serializable;
import java.util.List;
import nr.single.map.data.facade.SingleFileEnumInfo;
import nr.single.map.data.facade.SingleFileFmdmInfo;
import nr.single.map.data.facade.SingleFileFormulaInfo;
import nr.single.map.data.facade.SingleFilePrintInfo;
import nr.single.map.data.facade.SingleFileTableInfo;
import nr.single.map.data.facade.SingleFileTaskInfo;

public interface SingleMapFormSchemeDefine
extends Serializable {
    public SingleFileTaskInfo getTaskInfo();

    public SingleFileEnumInfo getTaskPeriodEnum();

    public List<SingleFileEnumInfo> getEnumInfos();

    public SingleFileEnumInfo getNewEnumInfo();

    public SingleFileEnumInfo getEnumInfoByCode(String var1);

    public SingleFileFmdmInfo getFmdmInfo();

    public List<SingleFileTableInfo> getTableInfos();

    public SingleFileTableInfo getNewTableInfo();

    public List<SingleFileFormulaInfo> getFormulaInfos();

    public SingleFileFormulaInfo getNewFormulaInfo();

    public List<SingleFilePrintInfo> getPrintInfos();

    public SingleFilePrintInfo getNewPrintInfo();

    public String getMapSchemeTitle();

    public void setMapSchemeTitle(String var1);

    public List<SingleMapFormSchemeDefine> getSubMapSchemes();
}

