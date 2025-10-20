/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.budget.common.consts.CommonConst
 *  com.jiuqi.budget.common.exception.BudgetException
 *  com.jiuqi.budget.common.utils.CommonUtil
 */
package com.jiuqi.budget.autoconfigure;

import com.jiuqi.budget.common.consts.CommonConst;
import com.jiuqi.budget.common.exception.BudgetException;
import com.jiuqi.budget.common.utils.CommonUtil;
import com.jiuqi.budget.components.SysDim;
import com.jiuqi.budget.masterdata.intf.FBaseDataObj;
import com.jiuqi.budget.masterdata.intf.SummaryBaseDataObject;

public class DimensionConst
implements CommonConst {
    public static final BudSystemDimensionDefine DATATIME = SystemDimensionFactory.createSystemDimension(SysDim.DATATIME);
    public static final BudSystemDimensionDefine MD_ORG = SystemDimensionFactory.createSystemDimension(SysDim.MDCODE);
    public static final String VERSION = "VERSION";
    public static final String VERSION_BD = "VERSION_1";
    public static final String BUDACCOUNT = "MD_BUDACCOUNT";
    public static final String MD_HYPERVIEW = "MD_HYPERVIEW";
    public static final String MDGATHERALG = "MD_GATHERALG";
    public static final String MD_BUDTASK = "MD_BUDTASK";
    public static final BudSystemDimensionDefine MD_CURRENCY = SystemDimensionFactory.createSystemDimension(SysDim.MD_CURRENCY);
    public static final BudSystemDimensionDefine MD_STAGE = SystemDimensionFactory.createSystemDimension(SysDim.MD_STAGE);
    public static final BudSystemDimensionDefine MD_MGRVER = SystemDimensionFactory.createSystemDimension(SysDim.MD_MGRVER);
    public static final BudSystemDimensionDefine MD_SCENE = SystemDimensionFactory.createSystemDimension(SysDim.MD_SCENE);
    public static final BudInlayDimensionDefine MD_BUDACCOUNT = InlayDimensionFactory.createInlayDimension("MD_BUDACCOUNT");
    public static final BudInlayDimensionDefine MD_GATHERALG = InlayDimensionFactory.createInlayDimension("MD_GATHERALG");
    public static final String SUM_VAL = "00000000";
    public static final String DIFF_VAL = "*0000000";
    public static final FBaseDataObj VIRTUAL_OBJ = SummaryBaseDataObject.instance;
    private static BudSystemDimensionDefine[] SYSTEM_DIMENSIONS = null;
    public static final String SCENE_GROUP = "MD_SCENE_GROUP";

    public static BudSystemDimensionDefine[] getSystemDimensions() {
        if (SYSTEM_DIMENSIONS == null) {
            return new BudSystemDimensionDefine[]{DATATIME, MD_ORG, MD_SCENE, MD_CURRENCY, MD_MGRVER};
        }
        return SYSTEM_DIMENSIONS;
    }

    static void setSystemDimensions(BudSystemDimensionDefine[] systemDimensions) {
        SYSTEM_DIMENSIONS = systemDimensions;
    }

    public static boolean isOrg(String str) {
        if ("MD_ORG".equals(str)) {
            return true;
        }
        return CommonUtil.startWith((String)str, (String)"MD_ORG_");
    }

    public static boolean isSystemDim(String dimCode) {
        for (BudSystemDimensionDefine budSystemDimensionDefine : DimensionConst.getSystemDimensions()) {
            if (!budSystemDimensionDefine.getCode().equals(dimCode)) continue;
            return true;
        }
        return false;
    }

    public static class MD_SCENE {
        public static final String ID = "MD_SCENE";
        public static final String TITLE = "\u60c5\u666f";
        public static final SystemDimensionVal[] DEFAULT_VALS = new SystemDimensionVal[]{new SystemDimensionVal("BUDGET", "\u9884\u7b97\u6570"), new SystemDimensionVal("ACTUAL", "\u5b9e\u9645\u6570"), new SystemDimensionVal("FORECAST", "\u9884\u8ba1\u6570"), new SystemDimensionVal("ACTUALTOTAL", "\u5b9e\u9645\u7d2f\u8ba1"), new SystemDimensionVal("FORECASTTOTAL", "\u9884\u8ba1\u7d2f\u8ba1"), new SystemDimensionVal("EXECDIFF", "\u6267\u884c\u5dee\u5f02"), new SystemDimensionVal("RATE", "\u589e\u957f\u7387")};
    }

    public static class SystemDimensionVal {
        private final String code;
        private final String name;

        SystemDimensionVal(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return this.code;
        }

        public String getName() {
            return this.name;
        }
    }

    public static class SceneDefine
    extends BudSystemDimensionDefine {
        public static final String SCENE_BUDGET = "BUDGET";
        public static final String SCENE_ACTUAL = "ACTUAL";
        public static final String SCENE_INITBUDGET = "INITBUDGET";
        public static final String SCENE_CURADJUST = "CURADJUST";
        public static final String SCENE_EXPECTCONFIRM = "EXPECTCONFIRM";
        public static final String SCENE_TOTALADJUST = "TOTALADJUST";
        public static final String SCENE_ADJUSTREASON = "ADJUSTREASON";
        public static final String SCENE_AVAILBUDGET = "AVAILBUDGET";

        SceneDefine() {
            super(SysDim.MD_SCENE);
        }
    }

    public static class StageDefine
    extends BudSystemDimensionDefine {
        public static final String STAGE_TJ_LATEST = "LATEST";
        public static final String STAGE_FINAL = "FINAL";
        public static final String STAGE_INIT = "INIT";
        public static final String[] INNER_UP_STAGES = new String[]{"FINAL", "U1", "U2", "U3", "U4", "U5", "U6", "U7"};
        public static final String[] INNER_DOWN_STAGES = new String[]{"FINAL", "D1", "D2", "D3", "D4", "D5", "D6", "D7"};

        StageDefine() {
            super(SysDim.MD_STAGE);
        }
    }

    public static class VersionDefine
    extends BudSystemDimensionDefine {
        public static final String FINAL_VERSION = "00000000-0000-0000-0000-000000000000";
        public static final String MINUS_ONE_VERSION = "10000000-0000-0000-0000-000000000001";

        VersionDefine(SysDim sysDim) {
            super(sysDim);
        }

        public static String getStoreTable(String verStr, String model) {
            if (VersionDefine.storeInOriTable(verStr)) {
                if (model.length() > 4) {
                    boolean endWithVer = model.endsWith("_VER");
                    if (endWithVer) {
                        return model;
                    }
                    return model;
                }
                return model;
            }
            if (model.length() > 4) {
                boolean endWithVer = model.endsWith("_VER");
                if (endWithVer) {
                    return model;
                }
                return model + "_VER";
            }
            return model + "_VER";
        }

        public static boolean storeInOriTable(String verStr) {
            return verStr == null;
        }

        public static String getCurrent() {
            return FINAL_VERSION;
        }
    }

    public static class BudInlayDimensionDefine
    extends BudSystemDimensionDefine {
        private final String code;
        private final String name;
        private SystemDimensionVal[] vals;

        BudInlayDimensionDefine(String code, String name) {
            super(SysDim.MD_CURRENCY);
            this.code = code;
            this.name = name;
        }

        @Override
        public String getCode() {
            return this.code;
        }

        @Override
        public String getName() {
            return this.name;
        }

        @Override
        public SystemDimensionVal[] getVals() {
            return this.vals;
        }

        @Override
        void setVals(SystemDimensionVal[] vals) {
            this.vals = vals;
        }
    }

    public static class BudSystemDimensionDefine {
        private final String code;
        private final String name;
        private SystemDimensionVal[] vals;

        BudSystemDimensionDefine(SysDim sysDim) {
            this.code = sysDim.alias();
            this.name = sysDim.getTitle();
        }

        public String getCode() {
            return this.code;
        }

        public String getName() {
            return this.name;
        }

        public SystemDimensionVal[] getVals() {
            return this.vals;
        }

        void setVals(SystemDimensionVal[] vals) {
            this.vals = vals;
        }
    }

    public static class SystemDimensionFactory {
        private SystemDimensionFactory() {
            throw new BudgetException("Utility class");
        }

        static BudSystemDimensionDefine createSystemDimension(SysDim dimensionCode) {
            switch (dimensionCode) {
                case DATATIME: 
                case MDCODE: {
                    return new BudSystemDimensionDefine(dimensionCode);
                }
                case MD_CURRENCY: {
                    BudSystemDimensionDefine dimension = new BudSystemDimensionDefine(dimensionCode);
                    SystemDimensionVal[] vals = new SystemDimensionVal[]{new SystemDimensionVal("CNY", "\u4eba\u6c11\u5e01"), new SystemDimensionVal("USD", "\u7f8e\u5143"), new SystemDimensionVal("HKD", "\u6e2f\u5e01"), new SystemDimensionVal("EUR", "\u6b27\u5143")};
                    dimension.setVals(vals);
                    return dimension;
                }
                case MD_MGRVER: {
                    BudSystemDimensionDefine dimension = new BudSystemDimensionDefine(dimensionCode);
                    SystemDimensionVal[] vals = new SystemDimensionVal[]{new SystemDimensionVal("REPORT", "\u4e0a\u62a5\u7248"), new SystemDimensionVal("MGR", "\u7ba1\u7406\u7248"), new SystemDimensionVal("GZWYB", "\u56fd\u8d44\u59d4\u9884\u62a5\u7248"), new SystemDimensionVal("GZWZSB", "\u56fd\u8d44\u59d4\u6b63\u5f0f\u7248")};
                    dimension.setVals(vals);
                    return dimension;
                }
                case MD_SCENE: {
                    BudSystemDimensionDefine dimension = new BudSystemDimensionDefine(dimensionCode);
                    SystemDimensionVal[] vals = new SystemDimensionVal[]{new SystemDimensionVal("BUDGET", "\u9884\u7b97\u6570"), new SystemDimensionVal("ACTUAL", "\u5b9e\u9645\u6570"), new SystemDimensionVal("FORECAST", "\u9884\u8ba1\u6570"), new SystemDimensionVal("ACTUALTOTAL", "\u5b9e\u9645\u7d2f\u8ba1"), new SystemDimensionVal("FORECASTTOTAL", "\u9884\u8ba1\u7d2f\u8ba1"), new SystemDimensionVal("EXECDIFF", "\u6267\u884c\u5dee\u5f02"), new SystemDimensionVal("RATE", "\u589e\u957f\u7387"), new SystemDimensionVal("INITBUDGET", "\u521d\u59cb\u9884\u7b97\u6570"), new SystemDimensionVal("AVAILBUDGET", "\u53ef\u7528\u9884\u7b97"), new SystemDimensionVal("CURADJUST", "\u672c\u6b21\u8c03\u6574\u6570"), new SystemDimensionVal("EXPECTCONFIRM", "\u9884\u8ba1\u8c03\u6574\u540e\u6570\u636e"), new SystemDimensionVal("TOTALADJUST", "\u7d2f\u8ba1\u8c03\u6574\u6570"), new SystemDimensionVal("EXECPROGRESS", "\u6267\u884c\u8fdb\u5ea6"), new SystemDimensionVal("1EMACTUAL", "1-{EM}\u6708\u5b9e\u9645"), new SystemDimensionVal("EM12FORECAST", "{EM+1}-12\u6708\u9884\u8ba1")};
                    dimension.setVals(vals);
                    return dimension;
                }
                case MD_STAGE: {
                    BudSystemDimensionDefine dimension = new BudSystemDimensionDefine(dimensionCode);
                    SystemDimensionVal[] vals = new SystemDimensionVal[]{new SystemDimensionVal("FINAL", "\u7f16\u5236\u7248"), new SystemDimensionVal("LATEST", "\u6700\u65b0\u7248"), new SystemDimensionVal("INIT", "\u521d\u59cb\u7248"), new SystemDimensionVal("U1", "\u4e00\u4e0a"), new SystemDimensionVal("D1", "\u4e00\u4e0b"), new SystemDimensionVal("U2", "\u4e8c\u4e0a"), new SystemDimensionVal("D2", "\u4e8c\u4e0b"), new SystemDimensionVal("U3", "\u4e09\u4e0a"), new SystemDimensionVal("D3", "\u4e09\u4e0b"), new SystemDimensionVal("2024U1", "\u4e00\u4e0a"), new SystemDimensionVal("2024D1", "\u4e00\u4e0b"), new SystemDimensionVal("2024U2", "\u4e8c\u4e0a"), new SystemDimensionVal("2024D2", "\u4e8c\u4e0b"), new SystemDimensionVal("2024U3", "\u4e09\u4e0a"), new SystemDimensionVal("2024D3", "\u4e09\u4e0b"), new SystemDimensionVal("2025U1", "\u4e00\u4e0a"), new SystemDimensionVal("2025D1", "\u4e00\u4e0b"), new SystemDimensionVal("2025U2", "\u4e8c\u4e0a"), new SystemDimensionVal("2025D2", "\u4e8c\u4e0b"), new SystemDimensionVal("2025U3", "\u4e09\u4e0a"), new SystemDimensionVal("2025D3", "\u4e09\u4e0b"), new SystemDimensionVal("2026U1", "\u4e00\u4e0a"), new SystemDimensionVal("2026D1", "\u4e00\u4e0b"), new SystemDimensionVal("2026U2", "\u4e8c\u4e0a"), new SystemDimensionVal("2026D2", "\u4e8c\u4e0b"), new SystemDimensionVal("2026U3", "\u4e09\u4e0a"), new SystemDimensionVal("2026D3", "\u4e09\u4e0b"), new SystemDimensionVal("2027U1", "\u4e00\u4e0a"), new SystemDimensionVal("2027D1", "\u4e00\u4e0b"), new SystemDimensionVal("2027U2", "\u4e8c\u4e0a"), new SystemDimensionVal("2027D2", "\u4e8c\u4e0b"), new SystemDimensionVal("2027U3", "\u4e09\u4e0a"), new SystemDimensionVal("2027D3", "\u4e09\u4e0b"), new SystemDimensionVal("2028U1", "\u4e00\u4e0a"), new SystemDimensionVal("2028D1", "\u4e00\u4e0b"), new SystemDimensionVal("2028U2", "\u4e8c\u4e0a"), new SystemDimensionVal("2028D2", "\u4e8c\u4e0b"), new SystemDimensionVal("2028U3", "\u4e09\u4e0a"), new SystemDimensionVal("2028D3", "\u4e09\u4e0b"), new SystemDimensionVal("2029U1", "\u4e00\u4e0a"), new SystemDimensionVal("2029D1", "\u4e00\u4e0b"), new SystemDimensionVal("2029U2", "\u4e8c\u4e0a"), new SystemDimensionVal("2029D2", "\u4e8c\u4e0b"), new SystemDimensionVal("2029U3", "\u4e09\u4e0a"), new SystemDimensionVal("2029D3", "\u4e09\u4e0b"), new SystemDimensionVal("2030U1", "\u4e00\u4e0a"), new SystemDimensionVal("2030D1", "\u4e00\u4e0b"), new SystemDimensionVal("2030U2", "\u4e8c\u4e0a"), new SystemDimensionVal("2030D2", "\u4e8c\u4e0b"), new SystemDimensionVal("2030U3", "\u4e09\u4e0a"), new SystemDimensionVal("2030D3", "\u4e09\u4e0b"), new SystemDimensionVal("2031U1", "\u4e00\u4e0a"), new SystemDimensionVal("2031D1", "\u4e00\u4e0b"), new SystemDimensionVal("2031U2", "\u4e8c\u4e0a"), new SystemDimensionVal("2031D2", "\u4e8c\u4e0b"), new SystemDimensionVal("2031U3", "\u4e09\u4e0a"), new SystemDimensionVal("2031D3", "\u4e09\u4e0b"), new SystemDimensionVal("2032U1", "\u4e00\u4e0a"), new SystemDimensionVal("2032D1", "\u4e00\u4e0b"), new SystemDimensionVal("2032U2", "\u4e8c\u4e0a"), new SystemDimensionVal("2032D2", "\u4e8c\u4e0b"), new SystemDimensionVal("2032U3", "\u4e09\u4e0a"), new SystemDimensionVal("2032D3", "\u4e09\u4e0b"), new SystemDimensionVal("2033U1", "\u4e00\u4e0a"), new SystemDimensionVal("2033D1", "\u4e00\u4e0b"), new SystemDimensionVal("2033U2", "\u4e8c\u4e0a"), new SystemDimensionVal("2033D2", "\u4e8c\u4e0b"), new SystemDimensionVal("2033U3", "\u4e09\u4e0a"), new SystemDimensionVal("2033D3", "\u4e09\u4e0b"), new SystemDimensionVal("2034U1", "\u4e00\u4e0a"), new SystemDimensionVal("2034D1", "\u4e00\u4e0b"), new SystemDimensionVal("2034U2", "\u4e8c\u4e0a"), new SystemDimensionVal("2034D2", "\u4e8c\u4e0b"), new SystemDimensionVal("2034U3", "\u4e09\u4e0a"), new SystemDimensionVal("2034D3", "\u4e09\u4e0b")};
                    dimension.setVals(vals);
                    return dimension;
                }
            }
            throw new BudgetException("\u672a\u77e5\u7684\u7cfb\u7edf\u7ef4\u5ea6\u6807\u8bc6\u3010" + (Object)((Object)dimensionCode) + "\u3011");
        }
    }

    public static class InlayDimensionFactory {
        private InlayDimensionFactory() {
            throw new BudgetException("Utility class");
        }

        static BudInlayDimensionDefine createInlayDimension(String dimensionCode) {
            switch (dimensionCode) {
                case "MD_BUDACCOUNT": {
                    BudInlayDimensionDefine dimension = new BudInlayDimensionDefine(dimensionCode, "\u9884\u7b97\u79d1\u76ee");
                    SystemDimensionVal[] vals = new SystemDimensionVal[]{new SystemDimensionVal("001", "\u8d44\u4ea7\u8d1f\u503a\u79d1\u76ee"), new SystemDimensionVal("002", "\u5229\u6da6\u79d1\u76ee"), new SystemDimensionVal("003", "\u73b0\u91d1\u6d41\u91cf\u79d1\u76ee"), new SystemDimensionVal("004", "\u5173\u952e\u6307\u6807")};
                    dimension.setVals(vals);
                    return dimension;
                }
                case "MD_GATHERALG": {
                    BudInlayDimensionDefine dimension = new BudInlayDimensionDefine(dimensionCode, "\u805a\u5408\u7b97\u6cd5");
                    SystemDimensionVal[] vals = new SystemDimensionVal[]{new SystemDimensionVal("ADD", "\u52a0"), new SystemDimensionVal("SUB", "\u51cf"), new SystemDimensionVal("IGNORE", "\u5ffd\u7565")};
                    dimension.setVals(vals);
                    return dimension;
                }
            }
            throw new BudgetException("\u672a\u77e5\u7684\u975e\u7cfb\u7edf\u7ef4\u5ea6\u6807\u8bc6\u3010" + dimensionCode + "\u3011");
        }
    }

    public static interface OrgConst {
        public static final String MD_BDORG = "MD_ORG_BUD";
        public static final String MD_BDORG_CN = "\u9884\u7b97\u7ec4\u7ec7";
        public static final String MD_BDORG_CODE = "BUD";
        public static final String ORG = "ORG";
        public static final String MD_ORG = "MD_ORG";
        public static final String MD_ORG_CN = "\u7ec4\u7ec7\u673a\u6784";
        public static final String ORG_CATEGORY_PREFIX = "MD_ORG_";
    }
}

