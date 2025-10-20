/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.i18n;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class I18NSurveyItem {

    public static enum I18NSurvey {
        I18N_BASICS_QUESTION("BasicsQuestion", "\u57fa\u7840\u9898"),
        I18N_CHOICE_QUESTION("ChoiceQuestion", "\u9009\u62e9\u9898"),
        I18N_EXPERT_QUESTION("ExpertQuestion", "\u9ad8\u7ea7\u9898"),
        I18N_ASSIST_QUESTION("AssistQuestion", "\u8f85\u52a9\u9898");

        public String code;
        public String title;
        private static Map<String, I18NSurvey> mappings;

        private static synchronized Map<String, I18NSurvey> getMappings() {
            if (null == mappings) {
                mappings = new HashMap<String, I18NSurvey>();
            }
            return mappings;
        }

        public static ArrayList<String> getI18NSurveyCode() {
            return new ArrayList<String>(I18NSurvey.getMappings().keySet());
        }

        private I18NSurvey(String code, String title) {
            this.code = code;
            this.title = title;
            I18NSurvey.getMappings().put(code, this);
        }

        public String getCode() {
            return this.code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getTitle() {
            return this.title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}

