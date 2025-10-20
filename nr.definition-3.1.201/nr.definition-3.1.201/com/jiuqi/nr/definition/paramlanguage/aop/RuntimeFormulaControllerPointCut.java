/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.node.CalcExpression
 *  com.jiuqi.np.dataengine.node.CheckExpression
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  org.aspectj.lang.ProceedingJoinPoint
 *  org.aspectj.lang.annotation.Around
 *  org.aspectj.lang.annotation.Aspect
 *  org.aspectj.lang.annotation.Pointcut
 */
package com.jiuqi.nr.definition.paramlanguage.aop;

import com.jiuqi.np.dataengine.node.CalcExpression;
import com.jiuqi.np.dataengine.node.CheckExpression;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.paramlanguage.bean.I18nFormula;
import com.jiuqi.nr.definition.paramlanguage.bean.I18nRunTimeFormulaDefine;
import com.jiuqi.nr.definition.paramlanguage.bean.I18nRunTimeFormulaSchemeDefine;
import com.jiuqi.nr.definition.paramlanguage.bean.I18nRunTimeICalcParsedExpression;
import com.jiuqi.nr.definition.paramlanguage.bean.I18nRunTimeICheckParsedExpression;
import com.jiuqi.nr.definition.paramlanguage.bean.I18nRunTimeIParsedExpression;
import com.jiuqi.nr.definition.paramlanguage.cache.ParamLanguageCache;
import com.jiuqi.nr.definition.paramlanguage.common.LanguageTypeUtil;
import com.jiuqi.nr.definition.paramlanguage.service.DefaultLanguageService;
import java.util.ArrayList;
import java.util.List;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

@Aspect
public class RuntimeFormulaControllerPointCut {
    private static final Logger logger = LoggerFactory.getLogger(RuntimeFormulaControllerPointCut.class);
    @Autowired
    private ParamLanguageCache paramLanguageCache;
    @Autowired
    private DefaultLanguageService defaultLanguageService;
    @Autowired
    private LanguageTypeUtil languageTypeUtil;

    @Pointcut(value="execution (* com.jiuqi.nr.definition.internal.runtime.controller.RuntimeFormulaController.getAllRPTFormulaSchemeDefinesByFormScheme(..))")
    public void getAllRPTFormulaSchemePointcut() {
    }

    @Around(value="getAllRPTFormulaSchemePointcut()")
    public Object afterGetAllRPTFormulaScheme(ProceedingJoinPoint point) throws Throwable {
        List formulaSchemeDefines = (List)point.proceed();
        boolean enableMultiLanguage = this.defaultLanguageService.checkEnableMultiLanguage();
        if (!enableMultiLanguage || CollectionUtils.isEmpty(formulaSchemeDefines) || this.isDefaultLanguage()) {
            return formulaSchemeDefines;
        }
        ArrayList<I18nRunTimeFormulaSchemeDefine> list = new ArrayList<I18nRunTimeFormulaSchemeDefine>(formulaSchemeDefines.size());
        for (FormulaSchemeDefine formulaSchemeDefine : formulaSchemeDefines) {
            I18nRunTimeFormulaSchemeDefine i18nRunTimeFormulaSchemeDefine = new I18nRunTimeFormulaSchemeDefine(formulaSchemeDefine);
            i18nRunTimeFormulaSchemeDefine.setTitle(this.paramLanguageCache.queryParamLanguage(formulaSchemeDefine.getKey(), this.languageTypeUtil.getCurrentLanguageType()));
            list.add(i18nRunTimeFormulaSchemeDefine);
        }
        return list;
    }

    @Pointcut(value="execution (* com.jiuqi.nr.definition.internal.runtime.controller.RuntimeFormulaController.getAllCWFormulaSchemeDefinesByFormScheme(..))")
    public void getAllCWFormulaSchemePointcut() {
    }

    @Around(value="getAllCWFormulaSchemePointcut()")
    public Object afterGetAllCWFormulaScheme(ProceedingJoinPoint point) throws Throwable {
        List formulaSchemeDefines = (List)point.proceed();
        boolean enableMultiLanguage = this.defaultLanguageService.checkEnableMultiLanguage();
        if (!enableMultiLanguage || CollectionUtils.isEmpty(formulaSchemeDefines) || this.isDefaultLanguage()) {
            return formulaSchemeDefines;
        }
        ArrayList<I18nRunTimeFormulaSchemeDefine> list = new ArrayList<I18nRunTimeFormulaSchemeDefine>(formulaSchemeDefines.size());
        for (FormulaSchemeDefine formulaSchemeDefine : formulaSchemeDefines) {
            I18nRunTimeFormulaSchemeDefine i18nRunTimeFormulaSchemeDefine = new I18nRunTimeFormulaSchemeDefine(formulaSchemeDefine);
            i18nRunTimeFormulaSchemeDefine.setTitle(this.paramLanguageCache.queryParamLanguage(formulaSchemeDefine.getKey(), this.languageTypeUtil.getCurrentLanguageType()));
            list.add(i18nRunTimeFormulaSchemeDefine);
        }
        return list;
    }

    @Pointcut(value="execution (* com.jiuqi.nr.definition.internal.runtime.controller.RuntimeFormulaController.getCalculateFormulasInForm(..))")
    public void getCalculateFormulasInFormPointcut() {
    }

    @Around(value="getCalculateFormulasInFormPointcut()")
    public Object afterGetCalculateFormulasInForm(ProceedingJoinPoint point) throws Throwable {
        List formulaDefines = (List)point.proceed();
        boolean enableMultiLanguage = this.defaultLanguageService.checkEnableMultiLanguage();
        if (!enableMultiLanguage || CollectionUtils.isEmpty(formulaDefines) || this.isDefaultLanguage()) {
            return formulaDefines;
        }
        ArrayList<I18nRunTimeFormulaDefine> list = new ArrayList<I18nRunTimeFormulaDefine>(formulaDefines.size());
        for (FormulaDefine formulaDefine : formulaDefines) {
            I18nRunTimeFormulaDefine i18nRunTimeFormulaDefine = new I18nRunTimeFormulaDefine(formulaDefine);
            i18nRunTimeFormulaDefine.setDescription(this.paramLanguageCache.queryParamLanguage(formulaDefine.getKey(), this.languageTypeUtil.getCurrentLanguageType()));
            list.add(i18nRunTimeFormulaDefine);
        }
        return list;
    }

    @Pointcut(value="execution (* com.jiuqi.nr.definition.internal.runtime.controller.RuntimeFormulaController.getCheckFormulasInForm(..))")
    public void getCheckFormulasInFormPointcut() {
    }

    @Around(value="getCheckFormulasInFormPointcut()")
    public Object afterGetCheckFormulasInForm(ProceedingJoinPoint point) throws Throwable {
        List formulaDefines = (List)point.proceed();
        boolean enableMultiLanguage = this.defaultLanguageService.checkEnableMultiLanguage();
        if (!enableMultiLanguage || CollectionUtils.isEmpty(formulaDefines) || this.isDefaultLanguage()) {
            return formulaDefines;
        }
        ArrayList<I18nRunTimeFormulaDefine> list = new ArrayList<I18nRunTimeFormulaDefine>(formulaDefines.size());
        for (FormulaDefine formulaDefine : formulaDefines) {
            I18nRunTimeFormulaDefine i18nRunTimeFormulaDefine = new I18nRunTimeFormulaDefine(formulaDefine);
            i18nRunTimeFormulaDefine.setDescription(this.paramLanguageCache.queryParamLanguage(formulaDefine.getKey(), this.languageTypeUtil.getCurrentLanguageType()));
            list.add(i18nRunTimeFormulaDefine);
        }
        return list;
    }

    @Pointcut(value="execution (* com.jiuqi.nr.definition.internal.runtime.controller.RuntimeFormulaController.getParsedExpressionByDataLink(..))")
    public void getParsedExpressionByDataLinkPointcut() {
    }

    @Around(value="getParsedExpressionByDataLinkPointcut()")
    public Object afterGetParsedExpressionByDataLink(ProceedingJoinPoint point) throws Throwable {
        List formulaDefines = (List)point.proceed();
        boolean enableMultiLanguage = this.defaultLanguageService.checkEnableMultiLanguage();
        if (!enableMultiLanguage || CollectionUtils.isEmpty(formulaDefines) || this.isDefaultLanguage()) {
            return formulaDefines;
        }
        ArrayList<Object> list = new ArrayList<Object>(formulaDefines.size());
        for (IParsedExpression formulaDefine : formulaDefines) {
            Object expression;
            I18nFormula formula = new I18nFormula(formulaDefine.getSource());
            String formulaMeaning = this.paramLanguageCache.queryParamLanguage(formulaDefine.getSource().getId(), this.languageTypeUtil.getCurrentLanguageType());
            formula.setMeaning(formulaMeaning);
            if (formulaDefine instanceof CalcExpression) {
                expression = new I18nRunTimeICalcParsedExpression((CalcExpression)formulaDefine);
                ((I18nRunTimeICalcParsedExpression)((Object)expression)).setSource(formula);
                ((I18nRunTimeICalcParsedExpression)((Object)expression)).setMeaning(formulaMeaning);
                list.add(expression);
                continue;
            }
            if (formulaDefine instanceof CheckExpression) {
                expression = new I18nRunTimeICheckParsedExpression((CheckExpression)formulaDefine);
                ((I18nRunTimeICheckParsedExpression)((Object)expression)).setSource(formula);
                ((I18nRunTimeICheckParsedExpression)((Object)expression)).setMeaning(formulaMeaning);
                list.add(expression);
                continue;
            }
            expression = new I18nRunTimeIParsedExpression(formulaDefine);
            ((I18nRunTimeIParsedExpression)expression).setMeaning(this.paramLanguageCache.queryParamLanguage(formulaDefine.getSource().getId(), this.languageTypeUtil.getCurrentLanguageType()));
            ((I18nRunTimeIParsedExpression)expression).setSource(formula);
            ((I18nRunTimeIParsedExpression)expression).setMeaning(formulaMeaning);
            list.add(expression);
        }
        return list;
    }

    @Pointcut(value="execution (* com.jiuqi.nr.definition.internal.runtime.controller.RuntimeFormulaController.getParsedExpressionBetweenTable(..))")
    public void getParsedExpressionBetweenTablePointcut() {
    }

    @Around(value="getParsedExpressionBetweenTablePointcut()")
    public Object afterGetParsedExpressionBetweenTable(ProceedingJoinPoint point) throws Throwable {
        List formulaDefines = (List)point.proceed();
        boolean enableMultiLanguage = this.defaultLanguageService.checkEnableMultiLanguage();
        if (!enableMultiLanguage || CollectionUtils.isEmpty(formulaDefines) || this.isDefaultLanguage()) {
            return formulaDefines;
        }
        ArrayList<Object> list = new ArrayList<Object>(formulaDefines.size());
        for (IParsedExpression formulaDefine : formulaDefines) {
            Object expression;
            I18nFormula formula = new I18nFormula(formulaDefine.getSource());
            String formulaMeaning = this.paramLanguageCache.queryParamLanguage(formulaDefine.getSource().getId(), this.languageTypeUtil.getCurrentLanguageType());
            formula.setMeaning(formulaMeaning);
            if (formulaDefine instanceof CalcExpression) {
                expression = new I18nRunTimeICalcParsedExpression((CalcExpression)formulaDefine);
                ((I18nRunTimeICalcParsedExpression)((Object)expression)).setSource(formula);
                ((I18nRunTimeICalcParsedExpression)((Object)expression)).setMeaning(formulaMeaning);
                list.add(expression);
                continue;
            }
            if (formulaDefine instanceof CheckExpression) {
                expression = new I18nRunTimeICheckParsedExpression((CheckExpression)formulaDefine);
                ((I18nRunTimeICheckParsedExpression)((Object)expression)).setSource(formula);
                ((I18nRunTimeICheckParsedExpression)((Object)expression)).setMeaning(formulaMeaning);
                list.add(expression);
                continue;
            }
            expression = new I18nRunTimeIParsedExpression(formulaDefine);
            ((I18nRunTimeIParsedExpression)expression).setMeaning(this.paramLanguageCache.queryParamLanguage(formulaDefine.getSource().getId(), this.languageTypeUtil.getCurrentLanguageType()));
            ((I18nRunTimeIParsedExpression)expression).setSource(formula);
            ((I18nRunTimeIParsedExpression)expression).setMeaning(formulaMeaning);
            list.add(expression);
        }
        return list;
    }

    @Pointcut(value="execution (* com.jiuqi.nr.definition.internal.runtime.controller.RuntimeFormulaController.getParsedExpressionByForm(..))")
    public void getParsedExpressionByFormPointcut() {
    }

    @Around(value="getParsedExpressionByFormPointcut()")
    public Object afterGetParsedExpressionByFormPointcut(ProceedingJoinPoint point) throws Throwable {
        List formulaDefines = (List)point.proceed();
        boolean enableMultiLanguage = this.defaultLanguageService.checkEnableMultiLanguage();
        if (!enableMultiLanguage || CollectionUtils.isEmpty(formulaDefines) || this.isDefaultLanguage()) {
            return formulaDefines;
        }
        ArrayList<Object> list = new ArrayList<Object>(formulaDefines.size());
        for (IParsedExpression formulaDefine : formulaDefines) {
            Object expression;
            I18nFormula formula = new I18nFormula(formulaDefine.getSource());
            String formulaMeaning = this.paramLanguageCache.queryParamLanguage(formulaDefine.getSource().getId(), this.languageTypeUtil.getCurrentLanguageType());
            formula.setMeaning(formulaMeaning);
            if (formulaDefine instanceof CalcExpression) {
                expression = new I18nRunTimeICalcParsedExpression((CalcExpression)formulaDefine);
                ((I18nRunTimeICalcParsedExpression)((Object)expression)).setSource(formula);
                ((I18nRunTimeICalcParsedExpression)((Object)expression)).setMeaning(formulaMeaning);
                list.add(expression);
                continue;
            }
            if (formulaDefine instanceof CheckExpression) {
                expression = new I18nRunTimeICheckParsedExpression((CheckExpression)formulaDefine);
                ((I18nRunTimeICheckParsedExpression)((Object)expression)).setSource(formula);
                ((I18nRunTimeICheckParsedExpression)((Object)expression)).setMeaning(formulaMeaning);
                list.add(expression);
                continue;
            }
            expression = new I18nRunTimeIParsedExpression(formulaDefine);
            ((I18nRunTimeIParsedExpression)expression).setMeaning(this.paramLanguageCache.queryParamLanguage(formulaDefine.getSource().getId(), this.languageTypeUtil.getCurrentLanguageType()));
            ((I18nRunTimeIParsedExpression)expression).setSource(formula);
            ((I18nRunTimeIParsedExpression)expression).setMeaning(formulaMeaning);
            list.add(expression);
        }
        return list;
    }

    @Pointcut(value="execution (* com.jiuqi.nr.definition.internal.runtime.controller.RuntimeFormulaController.queryFormulaDefine(..))")
    public void queryFormulaDefinePointcut() {
    }

    @Around(value="queryFormulaDefinePointcut()")
    public Object afterQueryFormulaDefinePointcut(ProceedingJoinPoint point) throws Throwable {
        FormulaDefine formulaDefine = (FormulaDefine)point.proceed();
        boolean enableMultiLanguage = this.defaultLanguageService.checkEnableMultiLanguage();
        if (!enableMultiLanguage || null == formulaDefine || this.isDefaultLanguage()) {
            return formulaDefine;
        }
        I18nRunTimeFormulaDefine i18nRunTimeFormulaDefine = new I18nRunTimeFormulaDefine(formulaDefine);
        i18nRunTimeFormulaDefine.setDescription(this.paramLanguageCache.queryParamLanguage(formulaDefine.getKey(), this.languageTypeUtil.getCurrentLanguageType()));
        return i18nRunTimeFormulaDefine;
    }

    private boolean isDefaultLanguage() {
        return this.languageTypeUtil.getCurrentLanguageType() == this.defaultLanguageService.getDefaultLanguage();
    }

    @Pointcut(value="execution (* com.jiuqi.nr.definition.internal.runtime.controller.RuntimeFormulaController.getParsedExpression(..))")
    public void getParsedExpressionPointcut() {
    }

    @Around(value="getParsedExpressionPointcut()")
    public Object afterGetParsedExpressionPointcut(ProceedingJoinPoint point) throws Throwable {
        IParsedExpression formulaDefine = (IParsedExpression)point.proceed();
        boolean enableMultiLanguage = this.defaultLanguageService.checkEnableMultiLanguage();
        if (!enableMultiLanguage || null == formulaDefine || this.isDefaultLanguage()) {
            return formulaDefine;
        }
        I18nFormula formula = new I18nFormula(formulaDefine.getSource());
        String formulaMeaning = this.paramLanguageCache.queryParamLanguage(formulaDefine.getSource().getId(), this.languageTypeUtil.getCurrentLanguageType());
        formula.setMeaning(formulaMeaning);
        if (formulaDefine instanceof CalcExpression) {
            I18nRunTimeICalcParsedExpression expression = new I18nRunTimeICalcParsedExpression((CalcExpression)formulaDefine);
            expression.setSource(formula);
            expression.setMeaning(formulaMeaning);
            return expression;
        }
        if (formulaDefine instanceof CheckExpression) {
            I18nRunTimeICheckParsedExpression expression = new I18nRunTimeICheckParsedExpression((CheckExpression)formulaDefine);
            expression.setSource(formula);
            expression.setMeaning(formulaMeaning);
            return expression;
        }
        I18nRunTimeIParsedExpression expression = new I18nRunTimeIParsedExpression(formulaDefine);
        expression.setMeaning(this.paramLanguageCache.queryParamLanguage(formulaDefine.getSource().getId(), this.languageTypeUtil.getCurrentLanguageType()));
        expression.setSource(formula);
        expression.setMeaning(formulaMeaning);
        return expression;
    }
}

