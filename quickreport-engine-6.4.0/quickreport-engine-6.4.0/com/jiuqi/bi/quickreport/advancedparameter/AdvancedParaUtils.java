/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.parameter.model.ParameterModel
 */
package com.jiuqi.bi.quickreport.advancedparameter;

import com.jiuqi.bi.parameter.model.ParameterModel;
import com.jiuqi.bi.quickreport.advancedparameter.QuickFilterConfig;
import com.jiuqi.bi.quickreport.model.QuickReport;
import java.util.List;

public class AdvancedParaUtils {
    private AdvancedParaUtils() {
    }

    public static String getQuickFilterParaStr(QuickReport report, QuickFilterConfig quickConfig) {
        if (quickConfig == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        if (quickConfig.isAllParameter() || quickConfig.getParaNames().size() == 0) {
            for (ParameterModel model : report.getParameterModels()) {
                if (model == null || model.isHidden()) continue;
                if (sb.length() > 0) {
                    sb.append(";");
                }
                sb.append(model.getName());
            }
        } else {
            for (String paraName : quickConfig.getParaNames()) {
                if (sb.length() > 0) {
                    sb.append(";");
                }
                sb.append(paraName);
            }
        }
        return sb.toString();
    }

    public static boolean isAutoLayoutPara(List<ParameterModel> models) {
        for (ParameterModel parameterModel : models) {
            if (parameterModel == null || parameterModel.isHidden() || parameterModel.getWidth() == 0) continue;
            return false;
        }
        return true;
    }

    public static String getParaStr(List<ParameterModel> models) {
        StringBuilder sb = new StringBuilder();
        for (ParameterModel parameterModel : models) {
            if (parameterModel == null || parameterModel.isHidden()) continue;
            sb.append(parameterModel.getName());
            if (models.indexOf(parameterModel) == models.size() - 1) continue;
            sb.append(";");
        }
        return sb.toString();
    }
}

