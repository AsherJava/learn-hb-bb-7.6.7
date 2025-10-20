/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 */
package com.jiuqi.common.subject.impl.subject.utils;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.subject.impl.subject.data.SubjectChecker;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubjectCheckerGather
implements InitializingBean {
    @Autowired(required=false)
    private List<SubjectChecker> checkerList;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private void init() {
        if (CollectionUtils.isEmpty(this.checkerList)) {
            this.checkerList.clear();
            return;
        }
        this.checkerList.sort(new Comparator<SubjectChecker>(){

            @Override
            public int compare(SubjectChecker o1, SubjectChecker o2) {
                return o2.getOrder() - o1.getOrder();
            }
        });
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            this.init();
        }
        catch (Exception e) {
            this.logger.error("\u79d1\u76ee\u6821\u9a8c\u5668\u521d\u59cb\u53d1\u751f\u9519\u8bef", e);
        }
    }

    public List<SubjectChecker> getCheckers() {
        return Collections.unmodifiableList(this.checkerList);
    }
}

