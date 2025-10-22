/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContextHolder
 */
package com.jiuqi.nr.datacrud.i18n;

import com.jiuqi.np.core.context.NpContextHolder;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;

@Component
public class CrudMessageSource {
    @Autowired
    @Qualifier(value="com.jiuqi.nr.crud.CrudMessageSource")
    private MessageSource messageSource;

    public String getMessage(String code, Object arg) throws NoSuchMessageException {
        Object[] args = new Object[]{arg};
        return this.messageSource.getMessage(code, args, NpContextHolder.getContext().getLocale());
    }

    public String getMessage(String code, Object ... args) throws NoSuchMessageException {
        return this.messageSource.getMessage(code, args, NpContextHolder.getContext().getLocale());
    }

    public String getMessage(String code, Locale locale, Object ... args) throws NoSuchMessageException {
        return this.messageSource.getMessage(code, args, locale);
    }

    public String getMessage(String code, Locale locale, Object arg) throws NoSuchMessageException {
        Object[] args = new Object[]{arg};
        return this.messageSource.getMessage(code, args, locale);
    }
}

