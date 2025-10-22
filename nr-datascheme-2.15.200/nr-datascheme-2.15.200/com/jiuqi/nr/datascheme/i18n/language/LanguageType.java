/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContextHolder
 */
package com.jiuqi.nr.datascheme.i18n.language;

import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.datascheme.i18n.language.ILanguageType;
import com.jiuqi.nr.datascheme.i18n.language.LanguageTypeService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public enum LanguageType implements ILanguageType
{
    DEFAULT{

        @Override
        public String getKey() {
            return LanguageType.getDefault().getKey();
        }

        @Override
        public String getLanguage() {
            return LanguageType.getDefault().getLanguage();
        }

        @Override
        public boolean isDefault() {
            return true;
        }

        @Override
        public String getMessage() {
            return LanguageType.getDefault().getLanguage();
        }
    }
    ,
    CHINESE{

        @Override
        public String getKey() {
            return LanguageType.getChinese().getKey();
        }

        @Override
        public String getLanguage() {
            return LanguageType.getChinese().getLanguage();
        }

        @Override
        public boolean isDefault() {
            return LanguageType.getChinese().isDefault();
        }

        @Override
        public String getMessage() {
            return LanguageType.getChinese().getLanguage();
        }
    };

    private static Map<String, ILanguageType> all;
    private static final Logger logger;

    private static synchronized void init() {
        if (null == all) {
            all = new HashMap<String, ILanguageType>();
            try {
                List<? extends ILanguageType> allTypes = LanguageTypeService.getAll();
                if (null != allTypes && !allTypes.isEmpty()) {
                    for (ILanguageType iLanguageType : allTypes) {
                        all.put(iLanguageType.getKey(), iLanguageType);
                    }
                }
            }
            catch (Exception e) {
                logger.error("\u83b7\u53d6\u7cfb\u7edf\u591a\u8bed\u8a00\u4fe1\u606f\u5f02\u5e38(\u53ef\u80fd\u662f\u670d\u52a1\u5c1a\u672a\u5b8c\u5168\u542f\u52a8)");
            }
        }
    }

    private static ILanguageType getDefault() {
        if (null == all) {
            LanguageType.init();
        }
        if (null != all) {
            for (ILanguageType iLanguageType : all.values()) {
                if (!iLanguageType.isDefault()) continue;
                return iLanguageType;
            }
        }
        return new ILanguageType(){

            @Override
            public String getKey() {
                return "1";
            }

            @Override
            public String getLanguage() {
                return "\u4e2d\u6587";
            }

            @Override
            public boolean isDefault() {
                return true;
            }

            @Override
            public String getMessage() {
                return "zh";
            }
        };
    }

    private static ILanguageType getChinese() {
        if (null == all) {
            LanguageType.init();
        }
        for (ILanguageType iLanguageType : all.values()) {
            if (!StringUtils.hasText(iLanguageType.getLanguage()) || !iLanguageType.getLanguage().contains("\u4e2d\u6587")) continue;
            return iLanguageType;
        }
        return new ILanguageType(){

            @Override
            public String getKey() {
                return "1";
            }

            @Override
            public String getLanguage() {
                return "\u4e2d\u6587";
            }

            @Override
            public boolean isDefault() {
                return true;
            }

            @Override
            public String getMessage() {
                return "zh";
            }
        };
    }

    public static List<ILanguageType> allValues() {
        if (null == all) {
            LanguageType.init();
        }
        return all.values().stream().sorted((l1, l2) -> l1.getKey().compareTo(l2.getKey())).collect(Collectors.toList());
    }

    public static ILanguageType valueOfKey(String key) {
        if (!StringUtils.hasLength(key)) {
            return null;
        }
        if (null == all) {
            LanguageType.init();
        }
        return all.get(key);
    }

    public static ILanguageType valueOfName(String name) {
        if (!StringUtils.hasLength(name)) {
            return null;
        }
        if (null == all) {
            LanguageType.init();
        }
        for (ILanguageType iLanguageType : all.values()) {
            if (!name.equals(iLanguageType.getLanguage())) continue;
            return iLanguageType;
        }
        return null;
    }

    public static ILanguageType valueOfCode(String code) {
        if (!StringUtils.hasLength(code)) {
            return null;
        }
        if (null == all) {
            LanguageType.init();
        }
        for (ILanguageType iLanguageType : all.values()) {
            if (!code.equals(iLanguageType.getMessage())) continue;
            return iLanguageType;
        }
        return null;
    }

    public static boolean enableMultiLanguage() {
        try {
            return LanguageTypeService.enableMultiLanguage();
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    public static ILanguageType getCurrentLanguageType() {
        ILanguageType type = null;
        String language = null;
        try {
            language = NpContextHolder.getContext().getLocale().getLanguage();
        }
        catch (Exception e) {
            logger.error("\u672a\u80fd\u5728\u4e0a\u4e0b\u6587\u4e2d\u83b7\u53d6\u5230\u5f53\u524d\u8bed\u8a00\u4fe1\u606f");
        }
        try {
            type = LanguageType.valueOfCode(language);
        }
        catch (Exception e) {
            logger.error("\u83b7\u53d6\u5f53\u524d\u8bed\u79cd\u5f02\u5e38\uff0c\u8fd4\u56de\u9ed8\u8ba4\u8bed\u79cd\u4fe1\u606f");
        }
        if (null == type) {
            type = DEFAULT;
        }
        return type;
    }

    static {
        logger = LoggerFactory.getLogger(LanguageType.class);
    }
}

