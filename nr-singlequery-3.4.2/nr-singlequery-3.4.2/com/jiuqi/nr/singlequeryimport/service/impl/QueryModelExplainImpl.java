/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.singlequeryimport.intf.bean.QueryModelExplain
 *  com.jiuqi.nr.singlequeryimport.intf.service.IQueryModelExplainService
 *  com.jiuqi.nr.singlequeryimport.intf.utils.ResultObject
 */
package com.jiuqi.nr.singlequeryimport.service.impl;

import com.jiuqi.nr.singlequeryimport.dao.QueryModelExplainDao;
import com.jiuqi.nr.singlequeryimport.intf.bean.QueryModelExplain;
import com.jiuqi.nr.singlequeryimport.intf.service.IQueryModelExplainService;
import com.jiuqi.nr.singlequeryimport.intf.utils.ResultObject;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QueryModelExplainImpl
implements IQueryModelExplainService {
    @Autowired
    QueryModelExplainDao modelExplainDao;

    public ResultObject getExplainByModelIdAndCode(String modelId, String orgCode, String period) throws Exception {
        QueryModelExplain explain = this.modelExplainDao.getExplain(modelId, orgCode, period);
        if (null == explain) {
            return ResultObject.error((String)"\u6ca1\u6709\u627e\u8be5\u60c5\u51b5\u8bf4\u660e\uff01");
        }
        return ResultObject.ok((Object)explain);
    }

    public ResultObject deleteExplainByModelIdAndCode(String modelId, String orgCode, String period) throws Exception {
        return this.modelExplainDao.deleteExplain(modelId, orgCode, period) == 1 ? ResultObject.ok() : ResultObject.error((String)"\u5220\u9664\u5931\u8d25");
    }

    public ResultObject upDataExplain(QueryModelExplain explain) throws Exception {
        return this.modelExplainDao.updateExplain(explain) == 1 ? ResultObject.ok() : ResultObject.error((String)"\u66f4\u65b0\u5931\u8d25");
    }

    public ResultObject addExplain(QueryModelExplain explain) throws Exception {
        QueryModelExplain model = this.modelExplainDao.getExplain(explain.getModelId(), explain.getOrgCode(), explain.getPeriod());
        if (null != model) {
            return ResultObject.error((String)"\u8be5\u60c5\u51b5\u8bf4\u660e\u5df2\u5b58\u5728\u65e0\u6cd5\u6dfb\u52a0\uff01");
        }
        return this.modelExplainDao.insertExplain(explain) == 0 ? ResultObject.ok() : ResultObject.error((String)"\u6dfb\u52a0\u5931\u8d25");
    }

    public ResultObject getExplainByModelId(String modelId, String period) throws Exception {
        List<QueryModelExplain> explainListByModeId = this.modelExplainDao.getExplainListByModeId(modelId, period);
        return ResultObject.ok(explainListByModeId);
    }

    public ResultObject batchUpDataExplain(List<QueryModelExplain> explains) throws Exception {
        return ResultObject.ok((Object)this.modelExplainDao.upDataExplainList(explains));
    }

    public ResultObject batchAddExplainByModelId(List<QueryModelExplain> explains) throws Exception {
        return ResultObject.ok((Object)this.modelExplainDao.insertExplainList(explains));
    }

    public ResultObject deleteExplainByModelId(String modelId, String period) throws Exception {
        return this.modelExplainDao.deleteExplainByModelId(modelId, period) == 0 ? ResultObject.ok() : ResultObject.error((String)"\u5220\u9664\u5931\u8d25");
    }
}

