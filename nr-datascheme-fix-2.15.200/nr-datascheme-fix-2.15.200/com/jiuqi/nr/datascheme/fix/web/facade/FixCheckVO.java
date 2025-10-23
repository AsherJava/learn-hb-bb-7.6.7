/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.fix.web.facade;

import com.jiuqi.nr.datascheme.fix.common.DeployExType;
import com.jiuqi.nr.datascheme.fix.common.DeployFixType;
import com.jiuqi.nr.datascheme.fix.core.DeployExCheckResultDTO;
import com.jiuqi.nr.datascheme.fix.web.facade.FixItem;
import com.jiuqi.nr.datascheme.fix.web.facade.FixParam;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FixCheckVO
extends FixItem {
    private DeployExType exType;
    private String exDesc;
    private int fixParamValue;
    private List<FixParam> fixParam;
    private Map<String, String> tmKeyAndLtName;

    public FixCheckVO(List<DeployFixType> fixParamsType, DeployExCheckResultDTO deployExCheckResult) {
        if (!deployExCheckResult.isScheme()) {
            this.isScheme = false;
            this.dataSchemeKey = deployExCheckResult.getDataSchemeKey();
            this.dataTableKey = deployExCheckResult.getDataTableKey();
            this.dataTableCode = deployExCheckResult.getDataTableCode();
            this.dataTableTitle = deployExCheckResult.getDataTableTitle();
            this.exType = deployExCheckResult.getExType();
            ArrayList<FixParam> fixParams = new ArrayList<FixParam>();
            for (DeployFixType deployFixType : fixParamsType) {
                FixParam fixParam = new FixParam(deployFixType);
                fixParams.add(fixParam);
            }
            if (((FixParam)fixParams.get(0)).getFixTypeValue() != 310) {
                FixParam defaultFixParam = new FixParam();
                fixParams.add(defaultFixParam);
            }
            this.tmKeyAndLtName = deployExCheckResult.getTmKeyAndLtName();
            this.fixParam = fixParams;
            this.exDesc = deployExCheckResult.getExType().getTitle();
            int fixParamValue = 0;
            if (fixParams.size() == 2) {
                for (FixParam fixParam : fixParams) {
                    if (fixParam.getFixTypeValue() == 310) continue;
                    fixParamValue = fixParam.getFixTypeValue();
                    break;
                }
            } else if (fixParams.size() == 1 && ((FixParam)fixParams.get(0)).getFixTypeValue() == 310) {
                fixParamValue = ((FixParam)fixParams.get(0)).getFixTypeValue();
            } else {
                for (FixParam fixParam : fixParams) {
                    if (fixParam.getFixTypeValue() != 303 && fixParam.getFixTypeValue() != 307) continue;
                    fixParamValue = fixParam.getFixTypeValue();
                    break;
                }
            }
            this.fixParamValue = fixParamValue;
        } else {
            this.isScheme = true;
            this.dataSchemeKey = deployExCheckResult.getDataSchemeKey();
            this.dataSchemeCode = deployExCheckResult.getDataSchemeCode();
            this.dataSchemeTitle = deployExCheckResult.getDataSchemeTitle();
            this.exDesc = deployExCheckResult.getDeployMessage();
            ArrayList<FixParam> fixParams = new ArrayList<FixParam>();
            FixParam fixParam = new FixParam();
            fixParams.add(fixParam);
            this.fixParam = fixParams;
            this.fixParamValue = 310;
        }
    }

    public FixCheckVO() {
    }

    public DeployExType getExType() {
        return this.exType;
    }

    public void setExType(DeployExType exType) {
        this.exType = exType;
    }

    public String getExDesc() {
        return this.exDesc;
    }

    public void setExDesc(String exDesc) {
        this.exDesc = exDesc;
    }

    public List<FixParam> getFixParam() {
        return this.fixParam;
    }

    public void setFixParam(List<FixParam> fixParam) {
        this.fixParam = fixParam;
    }

    public Map<String, String> getTmKeyAndLtName() {
        return this.tmKeyAndLtName;
    }

    public void setTmKeyAndLtName(Map<String, String> tmKeyAndLtName) {
        this.tmKeyAndLtName = tmKeyAndLtName;
    }

    public int getFixParamValue() {
        return this.fixParamValue;
    }

    public void setFixParamValue(int fixParamValue) {
        this.fixParamValue = fixParamValue;
    }
}

