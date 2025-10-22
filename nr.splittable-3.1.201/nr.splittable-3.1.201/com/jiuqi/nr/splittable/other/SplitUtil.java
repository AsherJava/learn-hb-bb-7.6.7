/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.grid2.Grid2Data
 */
package com.jiuqi.nr.splittable.other;

import com.jiuqi.nr.splittable.other.LinkHeap;
import com.jiuqi.nr.splittable.other.LinkObj;
import com.jiuqi.nvwa.grid2.Grid2Data;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SplitUtil {
    private static final Logger logger = LoggerFactory.getLogger(SplitUtil.class);
    static Set<String> skipSet = new HashSet<String>();

    public static void main(String[] args) {
        List<LinkObj> initList = SplitUtil.init0();
        SplitUtil.printList(initList);
        List<LinkHeap> heaps = SplitUtil.split(initList);
        for (LinkHeap heap : heaps) {
            SplitUtil.printList(heap.getList());
        }
    }

    public static List<LinkHeap> split(List<LinkObj> list) {
        Collections.sort(list);
        ArrayList<LinkHeap> heapList = new ArrayList<LinkHeap>();
        for (LinkObj l : list) {
            boolean heaped = false;
            for (int i = heapList.size() - 1; i > -1 && !heaped; --i) {
                LinkHeap heap = (LinkHeap)heapList.get(i);
                LinkObj lastLink = heap.getLast();
                if (lastLink.posy == l.posy) {
                    if (lastLink.posx != l.posx - 1) continue;
                    heap.addObj(l);
                    heaped = true;
                    break;
                }
                if (lastLink.posy != l.posy - 1) continue;
                LinkObj firstLink = heap.getFirst();
                if (firstLink.posx != l.posx) continue;
                heap.addObj(l);
                heaped = true;
            }
            if (heaped) continue;
            LinkHeap heap = new LinkHeap();
            heap.addObj(l);
            heapList.add(heap);
        }
        ArrayList<LinkHeap> finalList = new ArrayList<LinkHeap>();
        for (LinkHeap heap : heapList) {
            finalList.addAll(heap.splitHeap());
        }
        return finalList;
    }

    private static List<LinkObj> init0() {
        ArrayList<LinkObj> list = new ArrayList<LinkObj>();
        SplitUtil.fixList(list, 1, 4, 1, 4);
        SplitUtil.fixList(list, 1, 4, 7, 10);
        SplitUtil.fixList(list, 6, 8, 1, 4);
        SplitUtil.fixList(list, 6, 8, 7, 10);
        return list;
    }

    private static List<LinkObj> init1() {
        ArrayList<LinkObj> list = new ArrayList<LinkObj>();
        SplitUtil.fixList(list, 1, 3, 1, 7);
        SplitUtil.fixList(list, 3, 6, 1, 3);
        SplitUtil.fixList(list, 3, 6, 5, 7);
        return list;
    }

    private static List<LinkObj> init2() {
        ArrayList<LinkObj> list = new ArrayList<LinkObj>();
        SplitUtil.fixList(list, 1, 3, 1, 9);
        SplitUtil.fixList(list, 3, 6, 1, 3);
        SplitUtil.fixList(list, 3, 6, 5, 7);
        SplitUtil.fixList(list, 6, 8, 1, 9);
        SplitUtil.fixList(list, 8, 10, 1, 3);
        SplitUtil.fixList(list, 8, 10, 4, 7);
        SplitUtil.fixList(list, 8, 10, 8, 9);
        return list;
    }

    private static void fixList(List<LinkObj> list, int beginx, int endx, int beginy, int endy) {
        for (int i = beginx; i < endx; ++i) {
            for (int j = beginy; j < endy; ++j) {
                list.add(new LinkObj(i, j));
            }
        }
    }

    public static void printList(List<LinkObj> list) {
        logger.info("==========begin==============");
        StringBuffer buffer = new StringBuffer();
        Collections.sort(list);
        int curx = -1;
        for (LinkObj link : list) {
            if (curx < 0) {
                curx = link.posy;
            } else if (curx != link.posy) {
                logger.info("");
                curx = link.posy;
            }
            buffer.append("\u3010" + link.posy + "," + link.posx + "\u3011\t");
        }
        logger.info(buffer.toString());
    }

    public static void printHeap(LinkHeap heap, Grid2Data grid2Data, Set<String> linkSet) {
        LinkObj firstLink = heap.getFirst();
        LinkObj lastLink = heap.getLast();
        boolean finished = false;
        LinkObj linkObj = SplitUtil.getFirstCell(linkSet, firstLink, false);
    }

    private static LinkObj getFirstCell(Set<String> linkSet, LinkObj firstLink, boolean isFindDataCell) {
        int beginy;
        int beginx;
        for (beginx = firstLink.getPosx(); beginx > -1 && !(linkSet.contains(firstLink.getPosy() + "_" + beginx) ^ isFindDataCell); --beginx) {
        }
        for (beginy = firstLink.getPosy(); beginy > -1 && !(linkSet.contains(firstLink.getPosx() + "_" + beginy) ^ isFindDataCell); --beginy) {
        }
        return new LinkObj(beginy, beginx);
    }

    static {
        skipSet.add("--");
        skipSet.add("\u2014\u2014");
    }
}

