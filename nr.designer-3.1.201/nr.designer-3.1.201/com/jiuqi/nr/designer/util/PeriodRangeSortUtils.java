/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.period.common.utils.PeriodUtils
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 */
package com.jiuqi.nr.designer.util;

import com.jiuqi.nr.designer.util.SchemePeriodObj;
import com.jiuqi.nr.period.common.utils.PeriodUtils;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public class PeriodRangeSortUtils {
    private static final Logger logger = LoggerFactory.getLogger(PeriodRangeSortUtils.class);

    private PeriodRangeSortUtils() {
    }

    public static List<SchemePeriodObj> resort(String minPeriod, String maxPeriod, List<SchemePeriodObj> schemePeriodObjs, IPeriodProvider iPeriodProvider) {
        return PeriodRangeSortUtils.resort(schemePeriodObjs, periodRanges -> new PeriodRangeSorter(minPeriod, maxPeriod, periodRanges, iPeriodProvider).resort().get());
    }

    public static List<SchemePeriodObj> addAndResort(String minPeriod, String maxPeriod, List<SchemePeriodObj> schemePeriodObjs, SchemePeriodObj addSchemePeriodObj, IPeriodProvider iPeriodProvider) {
        return PeriodRangeSortUtils.resort(schemePeriodObjs, periodRanges -> new PeriodRangeSorter<SchemePeriodObj>(minPeriod, maxPeriod, (List<SchemePeriodObj>)periodRanges, iPeriodProvider).add(addSchemePeriodObj).get());
    }

    public static List<SchemePeriodObj> deleteAndResort(String minPeriod, String maxPeriod, List<SchemePeriodObj> schemePeriodObjs, SchemePeriodObj deleteSchemePeriodObj, IPeriodProvider iPeriodProvider) {
        return PeriodRangeSortUtils.resort(schemePeriodObjs, periodRanges -> new PeriodRangeSorter<SchemePeriodObj>(minPeriod, maxPeriod, (List<SchemePeriodObj>)periodRanges, iPeriodProvider).delete(deleteSchemePeriodObj).get());
    }

    private static List<SchemePeriodObj> resort(List<SchemePeriodObj> schemePeriodObjs, IPeriodRangeSortExecuter<List<SchemePeriodObj>> sorter) {
        if (null == schemePeriodObjs || schemePeriodObjs.isEmpty()) {
            return Collections.emptyList();
        }
        List<SchemePeriodObj> periodRanges = new ArrayList();
        ArrayList<SchemePeriodObj> nullPeriodRanges = new ArrayList<SchemePeriodObj>();
        for (SchemePeriodObj p : schemePeriodObjs) {
            if (StringUtils.hasLength(p.getStart()) && StringUtils.hasLength(p.getEnd())) {
                periodRanges.add(p);
                continue;
            }
            nullPeriodRanges.add(p);
        }
        periodRanges = sorter.execute(periodRanges);
        if (!nullPeriodRanges.isEmpty()) {
            Set schemeKeySet = periodRanges.stream().map(SchemePeriodObj::getScheme).collect(Collectors.toSet());
            for (SchemePeriodObj p : nullPeriodRanges) {
                if (schemeKeySet.contains(p.getScheme())) continue;
                periodRanges.add(p);
            }
        }
        return periodRanges;
    }

    public static void main(String[] args) {
        String minPeriod = "2020Y0001";
        String maxPeriod = "2022Y0012";
        ArrayList objs = new ArrayList();
        objs.forEach(x -> logger.info(x.getStart() + "--" + x.getEnd()));
        logger.info("\n");
        objs.forEach(x -> logger.info(x.getStart() + "--" + x.getEnd()));
        logger.info("\n");
    }

    private static SchemePeriodObj createSchemePeriodObj(String scheme, String start, String end) {
        SchemePeriodObj obj = new SchemePeriodObj();
        obj.setScheme(scheme);
        obj.setStart(start);
        obj.setEnd(end);
        return obj;
    }

    @FunctionalInterface
    private static interface IPeriodRangeSortExecuter<T> {
        public T execute(T var1);
    }

    public static class PeriodRangeSorter<P extends IPeriodRange> {
        private String minPeriod;
        private String maxPeriod;
        private List<P> periodRanges;
        private IPeriodProvider iPeriodProvider;

        public PeriodRangeSorter(String minPeriod, String maxPeriod, List<P> periodRanges, IPeriodProvider iPeriodProvider) {
            Assert.notNull((Object)minPeriod, "minPeriod must not be null.");
            Assert.notNull((Object)maxPeriod, "maxPeriod must not be null.");
            Assert.notNull(periodRanges, "periodRanges must not be null.");
            Assert.notNull((Object)iPeriodProvider, "iPeriodProvider must not be null.");
            this.minPeriod = minPeriod;
            this.maxPeriod = maxPeriod;
            this.periodRanges = periodRanges;
            this.iPeriodProvider = iPeriodProvider;
        }

        public List<P> get() {
            return this.periodRanges;
        }

        public PeriodRangeSorter<P> add(P periodRange) {
            List<P> oldPeriodRanges = this.periodRanges;
            this.periodRanges = new ArrayList<P>();
            oldPeriodRanges.sort(null);
            String newPeriodRangeStrat = periodRange.getStart();
            String newPeriodRangeEnd = periodRange.getEnd();
            String newPeriodRangeNextStrat = this.iPeriodProvider.nextPeriod(newPeriodRangeEnd);
            for (int i = 0; i < oldPeriodRanges.size(); ++i) {
                IPeriodRange oldPeriod = (IPeriodRange)oldPeriodRanges.get(i);
                if (this.iPeriodProvider.comparePeriod(oldPeriod.getEnd(), newPeriodRangeStrat) < 0) {
                    this.periodRanges.add(oldPeriod);
                    continue;
                }
                if (this.iPeriodProvider.comparePeriod(oldPeriod.getStart(), newPeriodRangeEnd) > 0) {
                    this.periodRanges.add(oldPeriod);
                    continue;
                }
                if (this.iPeriodProvider.comparePeriod(oldPeriod.getStart(), newPeriodRangeStrat) < 0) {
                    this.periodRanges.add(oldPeriod);
                }
                IPeriodRange copy = periodRange.copy();
                copy.setStart(newPeriodRangeNextStrat);
                this.periodRanges.add(copy);
                if (this.iPeriodProvider.comparePeriod(oldPeriod.getEnd(), newPeriodRangeEnd) <= 0) continue;
                copy = oldPeriod.copy();
                copy.setStart(newPeriodRangeNextStrat);
                this.periodRanges.add(copy);
            }
            this.periodRanges.add(periodRange);
            return this.resort();
        }

        public PeriodRangeSorter<P> delete(P periodRange) {
            this.periodRanges.remove(periodRange);
            return this.resort();
        }

        public PeriodRangeSorter<P> resort() {
            this.periodRanges.sort(null);
            if (this.periodRanges.size() > 1) {
                for (int i = 0; i < this.periodRanges.size() - 1; ++i) {
                    String lastEndPeriod = this.iPeriodProvider.priorPeriod(((IPeriodRange)this.periodRanges.get(i + 1)).getStart());
                    ((IPeriodRange)this.periodRanges.get(i)).setEnd(lastEndPeriod);
                }
            }
            this.trim();
            this.merge();
            return this;
        }

        private void trim() {
            int i;
            IPeriodRange period = null;
            for (i = 0; i < this.periodRanges.size(); ++i) {
                period = (IPeriodRange)this.periodRanges.get(i);
                if (this.iPeriodProvider.comparePeriod(this.minPeriod, period.getEnd()) <= 0) {
                    period.setStart(this.minPeriod);
                    break;
                }
                this.periodRanges.remove(period);
            }
            for (i = this.periodRanges.size() - 1; i >= 0; --i) {
                period = (IPeriodRange)this.periodRanges.get(i);
                if (this.iPeriodProvider.comparePeriod(this.maxPeriod, period.getStart()) >= 0) {
                    period.setEnd(this.maxPeriod);
                    break;
                }
                this.periodRanges.remove(i);
            }
        }

        private void merge() {
            if (this.periodRanges.size() <= 1) {
                return;
            }
            IPeriodRange periodRange = (IPeriodRange)this.periodRanges.get(this.periodRanges.size() - 1);
            String scheme = periodRange.getScheme();
            for (int i = this.periodRanges.size() - 2; i >= 0; --i) {
                IPeriodRange lastPeriodRange = (IPeriodRange)this.periodRanges.get(i);
                String lastScheme = lastPeriodRange.getScheme();
                if (lastScheme.equals(scheme)) {
                    lastPeriodRange.setEnd(periodRange.getEnd());
                    this.periodRanges.remove(i + 1);
                }
                periodRange = lastPeriodRange;
                scheme = lastScheme;
            }
        }
    }

    public static interface IPeriodRange
    extends Comparable<IPeriodRange> {
        public String getScheme();

        public void setStart(String var1);

        public String getStart();

        public void setEnd(String var1);

        public String getEnd();

        public IPeriodRange copy();

        @Override
        default public int compareTo(IPeriodRange periodRange) {
            return PeriodUtils.comparePeriod((String)this.getStart(), (String)periodRange.getStart());
        }
    }
}

