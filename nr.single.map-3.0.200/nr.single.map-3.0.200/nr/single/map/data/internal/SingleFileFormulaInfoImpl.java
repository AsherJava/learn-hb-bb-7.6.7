/*
 * Decompiled with CFR 0.152.
 */
package nr.single.map.data.internal;

import java.util.LinkedHashMap;
import java.util.Map;
import nr.single.map.data.facade.SingleFileFormulaInfo;
import nr.single.map.data.facade.SingleFileTableFormulaInfo;
import nr.single.map.data.internal.SingleFileTableFormulaInfoImpl;

public class SingleFileFormulaInfoImpl
implements SingleFileFormulaInfo {
    private static final long serialVersionUID = -5679445463835037596L;
    private String singleSchemeName;
    private String netSchemeName;
    private String netSchemeKey;
    private Map<String, SingleFileTableFormulaInfo> tableFormulaInfos = new LinkedHashMap<String, SingleFileTableFormulaInfo>();

    @Override
    public String getSingleSchemeName() {
        return this.singleSchemeName;
    }

    @Override
    public void setSingleSchemeName(String schemeName) {
        this.singleSchemeName = schemeName;
    }

    @Override
    public String getNetSchemeName() {
        return this.netSchemeName;
    }

    @Override
    public void setNetSchemeName(String schemeName) {
        this.netSchemeName = schemeName;
    }

    @Override
    public String getNetSchemeKey() {
        return this.netSchemeKey;
    }

    @Override
    public void setNetSchemeKey(String schemeKey) {
        this.netSchemeKey = schemeKey;
    }

    @Override
    public Map<String, SingleFileTableFormulaInfo> getTableFormulaInfos() {
        return this.tableFormulaInfos;
    }

    @Override
    public void copyFrom(SingleFileFormulaInfo info) {
        this.netSchemeKey = info.getNetSchemeKey();
        this.netSchemeName = info.getNetSchemeName();
        this.singleSchemeName = info.getSingleSchemeName();
        this.tableFormulaInfos.clear();
        for (SingleFileTableFormulaInfo tableInfo : info.getTableFormulaInfos().values()) {
            SingleFileTableFormulaInfoImpl tableInfoNew = new SingleFileTableFormulaInfoImpl();
            tableInfoNew.copyFrom(tableInfo);
            this.tableFormulaInfos.put(tableInfoNew.getSingleTableCode(), tableInfoNew);
        }
    }
}

