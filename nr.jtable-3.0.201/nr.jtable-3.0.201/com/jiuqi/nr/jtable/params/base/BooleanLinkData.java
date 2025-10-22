/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.data.BoolData
 *  com.jiuqi.np.dataengine.data.StringData
 *  com.jiuqi.np.dataengine.exception.DataTypeException
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.common.constant.ErrorCode
 *  com.jiuqi.nr.common.importdata.ImportErrorDataInfo
 *  com.jiuqi.nr.common.importdata.SaveErrorDataInfo
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.jtable.params.base;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.data.BoolData;
import com.jiuqi.np.dataengine.data.StringData;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.common.constant.ErrorCode;
import com.jiuqi.nr.common.importdata.ImportErrorDataInfo;
import com.jiuqi.nr.common.importdata.SaveErrorDataInfo;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.jtable.common.LinkType;
import com.jiuqi.nr.jtable.params.base.LinkData;
import com.jiuqi.nr.jtable.util.BooleanUtil;
import com.jiuqi.nr.jtable.util.DataFormaterCache;
import com.jiuqi.nr.jtable.util.DataLinkStyleUtil;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BooleanLinkData
extends LinkData {
    private static final Logger logger = LoggerFactory.getLogger(BooleanLinkData.class);

    public BooleanLinkData(DataLinkDefine dataLinkDefine, FieldDefine fieldDefine) {
        super(dataLinkDefine, fieldDefine);
        this.type = LinkType.LINK_TYPE_BOOLEAN.getValue();
        this.style = DataLinkStyleUtil.getOtherLinkStyle(dataLinkDefine, fieldDefine);
    }

    public BooleanLinkData(DataLinkDefine dataLinkDefine, ColumnModelDefine columnModelDefine) {
        super(dataLinkDefine, columnModelDefine);
        this.type = LinkType.LINK_TYPE_BOOLEAN.getValue();
        this.style = DataLinkStyleUtil.getOtherLinkStyle(dataLinkDefine, columnModelDefine);
    }

    @Override
    public Object getFormatData(AbstractData data, DataFormaterCache dataFormaterCache) {
        Object superData = super.getFormatData(data, dataFormaterCache);
        if (!(superData instanceof AbstractData)) {
            return superData;
        }
        if (data instanceof BoolData) {
            try {
                return data.getAsBool();
            }
            catch (DataTypeException e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
        } else {
            if (data instanceof StringData) {
                return BooleanUtil.parseBoolean(data.getAsString());
            }
            try {
                int intValue = data.getAsInt();
                return intValue > 0;
            }
            catch (DataTypeException e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
        }
        return null;
    }

    @Override
    public Object getData(Object value, DataFormaterCache dataFormaterCache, SaveErrorDataInfo saveErrorDataInfo) {
        if (saveErrorDataInfo == null) {
            saveErrorDataInfo = new ImportErrorDataInfo();
        }
        super.getData(value, dataFormaterCache, saveErrorDataInfo);
        if (value == null || StringUtils.isEmpty((String)value.toString()) || "null".equals(value.toString())) {
            return null;
        }
        Boolean returnBoolean = BooleanUtil.parseBoolean(value.toString());
        if (null == returnBoolean) {
            saveErrorDataInfo.setFieldTitle(this.zbtitle);
            saveErrorDataInfo.getDataError().setErrorCode(ErrorCode.DATAERROR);
            saveErrorDataInfo.getDataError().setErrorInfo("\u6307\u6807:" + this.zbtitle + " ;\u53ea\u80fd\u8f93\u5165\u5e03\u5c14\u503c\u3002\u9519\u8bef\u503c:" + value.toString());
            return false;
        }
        return returnBoolean;
    }
}

