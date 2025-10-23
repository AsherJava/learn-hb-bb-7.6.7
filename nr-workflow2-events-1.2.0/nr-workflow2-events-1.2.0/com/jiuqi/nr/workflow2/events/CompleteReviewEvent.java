/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nr.workflow2.engine.core.event.ActionEventRegisteration
 *  com.jiuqi.nr.workflow2.engine.core.event.IActionEventExecutor
 *  com.jiuqi.nr.workflow2.engine.core.event.IActionEventExecutorFactory
 *  com.jiuqi.nr.workflow2.engine.core.settings.utils.JavaBeanUtils
 *  org.json.JSONObject
 */
package com.jiuqi.nr.workflow2.events;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.workflow2.engine.core.event.ActionEventRegisteration;
import com.jiuqi.nr.workflow2.engine.core.event.IActionEventExecutor;
import com.jiuqi.nr.workflow2.engine.core.event.IActionEventExecutorFactory;
import com.jiuqi.nr.workflow2.engine.core.settings.utils.JavaBeanUtils;
import com.jiuqi.nr.workflow2.events.enumeration.ReviewType;
import com.jiuqi.nr.workflow2.events.executor.CompleteReviewAllEventExecutor;
import com.jiuqi.nr.workflow2.events.executor.CompleteReviewEventExecutor;
import com.jiuqi.nr.workflow2.events.executor.EventDependentServiceHelper;
import com.jiuqi.nr.workflow2.events.executor.MultiCheckReviewEventExecutorV1;
import com.jiuqi.nr.workflow2.events.executor.MultiCheckReviewEventExecutorV2;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;

public class CompleteReviewEvent
extends ActionEventRegisteration {
    public static final String ID = "complete-review-event";
    public static final String TITLE = "\u5ba1\u6838";
    private static final String DEF_VERSION = "1.0";
    @Value(value="${jiuqi.nr.workflow.mul-check.version:2.0}")
    private String mulCheckVersion;
    protected static final String attr_key_reviewType = "reviewType";
    protected static final String attr_key_isReviewAllBeforeUpload = "isReviewAllBeforeUpload";

    public CompleteReviewEvent() {
        super(ID, TITLE);
    }

    public String getDescription() {
        return "\u6267\u884c\u5168\u5ba1\uff0c\u6279\u91cf\u5ba1\u6838";
    }

    public short getOrder() {
        return 30;
    }

    public IActionEventExecutorFactory getExecutorFactory() {
        return new IActionEventExecutorFactory(){

            public String getActionEventDefinitionId() {
                return CompleteReviewEvent.ID;
            }

            public IActionEventExecutor createActionEventExecutor(String parameter) {
                EventDependentServiceHelper helper = (EventDependentServiceHelper)SpringBeanUtils.getBean(EventDependentServiceHelper.class);
                JSONObject eventJsonConfig = JavaBeanUtils.toJSONObject((String)parameter);
                assert (eventJsonConfig != null);
                ReviewType reviewType = CompleteReviewEvent.this.getReviewType(eventJsonConfig);
                if (ReviewType.COMPREHENSIVE_REVIEW == reviewType) {
                    if (CompleteReviewEvent.this.mulCheckVersion.equals(CompleteReviewEvent.DEF_VERSION)) {
                        return new MultiCheckReviewEventExecutorV1(eventJsonConfig, helper);
                    }
                    return new MultiCheckReviewEventExecutorV2(eventJsonConfig, helper);
                }
                if (CompleteReviewEvent.this.isReviewAllBeforeUpload(eventJsonConfig)) {
                    return new CompleteReviewAllEventExecutor(eventJsonConfig, helper);
                }
                return new CompleteReviewEventExecutor(eventJsonConfig, helper);
            }
        };
    }

    protected ReviewType getReviewType(JSONObject eventConfig) {
        if (eventConfig.has(attr_key_reviewType)) {
            String reviewType = eventConfig.getString(attr_key_reviewType);
            return ReviewType.valueOf(reviewType);
        }
        return null;
    }

    protected boolean isReviewAllBeforeUpload(JSONObject eventConfig) {
        if (eventConfig.has(attr_key_isReviewAllBeforeUpload)) {
            return eventConfig.getBoolean(attr_key_isReviewAllBeforeUpload);
        }
        return false;
    }
}

