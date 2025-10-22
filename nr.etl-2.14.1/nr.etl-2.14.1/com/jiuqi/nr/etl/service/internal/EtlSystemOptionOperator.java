/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionItemValue
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionOperator
 *  com.jiuqi.nvwa.systemoption.vo.ResultObject
 */
package com.jiuqi.nr.etl.service.internal;

import com.jiuqi.nr.etl.common.EtlServeEntity;
import com.jiuqi.nr.etl.service.internal.EtlServeEntityDao;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionItemValue;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionOperator;
import com.jiuqi.nvwa.systemoption.vo.ResultObject;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EtlSystemOptionOperator
implements ISystemOptionOperator {
    @Autowired
    private EtlServeEntityDao etlServeEntityDao;
    private final Logger logger = LoggerFactory.getLogger(EtlSystemOptionOperator.class);

    public ResultObject save(List<ISystemOptionItemValue> itemValues) {
        EtlServeEntity stateInfo = null;
        if (itemValues != null && !itemValues.isEmpty()) {
            ISystemOptionItemValue itemValue = itemValues.get(0);
            String value = itemValue.getValue();
            try {
                stateInfo = EtlServeEntity.valueOf(value);
            }
            catch (Exception e) {
                this.logger.error("\u4fdd\u5b58Etl\u4fe1\u606f\u5931\u8d25", e);
                return new ResultObject(false, "\u4fdd\u5b58Etl\u4fe1\u606f\u5931\u8d25");
            }
        }
        this.etlServeEntityDao.delete();
        if (stateInfo != null) {
            this.etlServeEntityDao.save(stateInfo);
        }
        return new ResultObject(true);
    }

    public String query(String optionItemKey) {
        Optional<EtlServeEntity> serverInfo = this.etlServeEntityDao.getServerInfo();
        if (serverInfo.isPresent()) {
            return serverInfo.toString();
        }
        return null;
    }

    public List<String> query(List<String> optionItemKeys) {
        String query = this.query((String)null);
        if (query != null) {
            return Collections.singletonList(query);
        }
        return Collections.emptyList();
    }
}

