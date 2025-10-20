/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.dto.CustomBizModelDTO
 *  com.jiuqi.bde.bizmodel.client.enums.AggregateFuncEnum
 *  com.jiuqi.bde.bizmodel.client.vo.SelectField
 *  com.jiuqi.bde.bizmodel.define.FetchResult
 *  com.jiuqi.bde.common.constant.ColumnTypeEnum
 *  com.jiuqi.bde.common.dto.ExecuteSettingVO
 *  com.jiuqi.bde.common.util.BdeCommonUtil
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.NumberUtils
 */
package com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf;

import com.jiuqi.bde.bizmodel.client.dto.CustomBizModelDTO;
import com.jiuqi.bde.bizmodel.client.enums.AggregateFuncEnum;
import com.jiuqi.bde.bizmodel.client.vo.SelectField;
import com.jiuqi.bde.bizmodel.define.FetchResult;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.CustomFetchExecuteUtil;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf.CustomFetchCalcStepResult;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf.ResultColumnType;
import com.jiuqi.bde.common.constant.ColumnTypeEnum;
import com.jiuqi.bde.common.dto.ExecuteSettingVO;
import com.jiuqi.bde.common.util.BdeCommonUtil;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.NumberUtils;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CustomFetchCalcResult
extends FetchResult {
    private static final long serialVersionUID = -7816065326035114371L;
    private String fetchType;
    private ResultColumnType colType;
    private AggregateFuncEnum func;
    private boolean fdIsNum;
    private String fdTitle;
    private String modelName;
    private List<CustomFetchCalcStepResult> stepResultList = new ArrayList<CustomFetchCalcStepResult>();

    public CustomFetchCalcResult(CustomBizModelDTO bizModel, ExecuteSettingVO setting, ResultColumnType colType) {
        super(setting);
        if (bizModel.getSelectFieldMap().get(setting.getFetchType()) == null) {
            throw new BusinessRuntimeException(String.format("\u4e1a\u52a1\u6a21\u578b\u3010%1$s\u3011\u6ca1\u6709\u5305\u542b\u53d6\u6570\u5b57\u6bb5\u3010%2$s\u3011\uff0c\u8bf7\u68c0\u67e5\u6a21\u578b\u662f\u5426\u88ab\u4fee\u6539", bizModel.getName(), setting.getFetchType()));
        }
        this.func = AggregateFuncEnum.getEnumByCode((String)((SelectField)bizModel.getSelectFieldMap().get(setting.getFetchType())).getAggregateFuncCode());
        this.colType = colType;
        this.fetchType = setting.getFetchType();
        this.fdIsNum = this.fieldIsNum(setting, colType);
        this.fdTitle = CustomFetchExecuteUtil.getFieldDefineInfo(setting);
        this.modelName = bizModel.getName();
    }

    public AggregateFuncEnum getFunc() {
        return this.func;
    }

    public void setFunc(AggregateFuncEnum func) {
        this.func = func;
    }

    public boolean includeCt() {
        return AggregateFuncEnum.AVG == this.func || AggregateFuncEnum.COUNT == this.func;
    }

    public boolean isCtCol() {
        return AggregateFuncEnum.COUNT == this.func;
    }

    public String getFetchType() {
        return this.fetchType;
    }

    public void setFetchType(String fetchType) {
        this.fetchType = fetchType;
    }

    public boolean addStepVal(CustomFetchCalcStepResult stepResult) {
        this.stepResultList.add(stepResult);
        return AggregateFuncEnum.ORIGINAL != this.func;
    }

    public CustomFetchCalcResult calcVal() {
        switch (this.func) {
            case SUM: {
                this.calcSumVal();
                break;
            }
            case COUNT: {
                this.calcCountVal();
                break;
            }
            case AVG: {
                this.calcAvgVal();
                break;
            }
            default: {
                this.calcOriginalVal();
            }
        }
        return this;
    }

    private void calcSumVal() {
        BigDecimal val = BigDecimal.ZERO;
        block6: for (CustomFetchCalcStepResult stepResult : this.stepResultList) {
            switch (this.colType) {
                case NUMBER: {
                    val = NumberUtils.sum((BigDecimal)val, (BigDecimal)stepResult.getDecimalVal());
                    continue block6;
                }
                case INT: {
                    val = NumberUtils.sum((BigDecimal)val, (BigDecimal)stepResult.getIntVal());
                    continue block6;
                }
            }
            try {
                val = NumberUtils.sum((BigDecimal)val, (BigDecimal)new BigDecimal(stepResult.getStrVal()));
            }
            catch (Exception e) {
                throw new BusinessRuntimeException(String.format("%1$s\u4e1a\u52a1\u6a21\u578b\u3010%2$s\u3011\u53d6\u6570\u5b57\u6bb5\u3010%3$s\u3011\u53d6\u6570\u7ed3\u679c\u3010%4$s\u3011\u8f6c\u6362\u4e3a\u6570\u503c\u7c7b\u578b\u51fa\u73b0\u9519\u8bef\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458\u68c0\u67e5\u6307\u6807\u7c7b\u578b", this.fdTitle, this.modelName, this.fetchType, stepResult.getStrVal()));
            }
        }
        this.setZbValue(val);
        this.setZbValueType(ColumnTypeEnum.NUMBER);
    }

    private void calcCountVal() {
        BigDecimal count = BigDecimal.ZERO;
        for (CustomFetchCalcStepResult stepResult : this.stepResultList) {
            count = NumberUtils.sum((BigDecimal)count, (BigDecimal)stepResult.getStepCt());
        }
        this.setZbValue(count);
        this.setZbValueType(ColumnTypeEnum.NUMBER);
    }

    private void calcOriginalVal() {
        if (CollectionUtils.isEmpty(this.stepResultList)) {
            if (this.fdIsNum) {
                this.setZbValue(BigDecimal.ZERO);
                this.setZbValueType(ColumnTypeEnum.NUMBER);
            } else {
                this.setZbValue("");
                this.setZbValueType(ColumnTypeEnum.STRING);
            }
            return;
        }
        if (!this.fdIsNum) {
            this.setZbValue(this.stepResultList.get(0).getStrVal());
            this.setZbValueType(ColumnTypeEnum.STRING);
            return;
        }
        BigDecimal decimalVal = null;
        try {
            decimalVal = new BigDecimal(this.stepResultList.get(0).getStrVal());
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(String.format("%1$s\u4e1a\u52a1\u6a21\u578b\u3010%2$s\u3011\u4e3a\u6570\u503c\u578b\uff0c\u53d6\u6570\u5b57\u6bb5\u3010%3$s\u3011\u53d6\u6570\u7ed3\u679c\u3010%4$s\u3011\u65e0\u6cd5\u8f6c\u6362\u4e3a\u6570\u503c\u7c7b\u578b\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458\u68c0\u67e5\u6307\u6807\u7c7b\u578b", this.fdTitle, this.modelName, this.fetchType, this.stepResultList.get(0).getStrVal()));
        }
        this.setZbValue(decimalVal);
        this.setZbValueType(ColumnTypeEnum.NUMBER);
    }

    private void calcAvgVal() {
        BigDecimal val = BigDecimal.ZERO;
        BigDecimal count = BigDecimal.ZERO;
        for (CustomFetchCalcStepResult stepResult : this.stepResultList) {
            val = NumberUtils.sum((BigDecimal)val, (BigDecimal)stepResult.getDecimalVal());
            count = NumberUtils.sum((BigDecimal)count, (BigDecimal)stepResult.getStepCt());
        }
        if (BigDecimal.ZERO.compareTo(count) == 0) {
            this.setZbValue(BigDecimal.ZERO);
        } else {
            this.setZbValue(NumberUtils.div((BigDecimal)val, (BigDecimal)count));
        }
        this.setZbValueType(ColumnTypeEnum.NUMBER);
    }

    private boolean fieldIsNum(ExecuteSettingVO executeSettingVO, ResultColumnType columnTypeEnum) {
        boolean isNum = false;
        if (executeSettingVO.getFieldDefineType() != null) {
            isNum = BdeCommonUtil.fieldDefineTypeIsNum((Integer)executeSettingVO.getFieldDefineType());
        } else if (!columnTypeEnum.equals((Object)ResultColumnType.STRING)) {
            isNum = true;
        }
        return isNum;
    }
}

