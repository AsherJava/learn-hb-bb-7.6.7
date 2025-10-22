/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.service;

import com.jiuqi.nr.definition.facade.BigDataDefine;
import com.jiuqi.nr.definition.facade.PrintComTemDefine;
import com.jiuqi.nr.definition.internal.dao.PrintComTemDefineDao;
import com.jiuqi.nr.definition.internal.dao.RunTimeBigDataTableDao;
import com.jiuqi.nr.definition.internal.impl.PrintComTemDefineImpl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RunTimePrintComTemDefineService {
    @Autowired
    private PrintComTemDefineDao printComTemDefineDao;
    @Autowired
    private RunTimeBigDataTableDao bigDataTableDao;

    private BigDataDefine getTemplateData(String key) {
        return this.bigDataTableDao.queryigDataDefine(key, "PRINTS_COMTEM_DATA");
    }

    private void addTemplateData(PrintComTemDefine define) {
        if (null == define) {
            return;
        }
        BigDataDefine data = this.getTemplateData(define.getKey());
        if (null != data && define instanceof PrintComTemDefineImpl) {
            ((PrintComTemDefineImpl)define).setTemplateData(data.getData());
        }
    }

    public PrintComTemDefine getByKey(String key) {
        PrintComTemDefine define = this.printComTemDefineDao.getByKey(key);
        if (null != define) {
            this.addTemplateData(define);
        }
        return define;
    }

    public PrintComTemDefine getBySchemeAndCode(String scheme, String code) {
        PrintComTemDefine define = this.printComTemDefineDao.getBySchemeAndCode(scheme, code);
        if (null != define) {
            this.addTemplateData(define);
        }
        return define;
    }

    public List<PrintComTemDefine> listByScheme(String scheme) {
        List<PrintComTemDefine> list = this.printComTemDefineDao.listByScheme(scheme);
        for (PrintComTemDefine define : list) {
            this.addTemplateData(define);
        }
        return list;
    }
}

