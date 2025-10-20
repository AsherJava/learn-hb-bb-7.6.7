/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.quickreport.engine.layer;

import com.jiuqi.bi.quickreport.ReportLog;
import com.jiuqi.bi.quickreport.engine.area.ExpandingArea;
import com.jiuqi.bi.quickreport.engine.area.FixedArea;
import com.jiuqi.bi.quickreport.engine.area.GridArea;
import com.jiuqi.bi.quickreport.engine.layer.CalcLayer;
import com.jiuqi.bi.quickreport.engine.layer.CalcLayerException;
import com.jiuqi.bi.quickreport.engine.parser.CellBindingInfo;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public final class CalcLayerAnalyzer {
    private List<GridArea> gridAreas;
    private List<CalcLayer> calcLayers;

    public CalcLayerAnalyzer(List<GridArea> gridAreas) {
        this.gridAreas = new ArrayList<GridArea>(gridAreas);
        this.calcLayers = new ArrayList<CalcLayer>();
    }

    public void analyse() throws CalcLayerException {
        List<GridArea> atomAreas = this.expandAreas();
        this.initAreas(atomAreas);
        this.scanAreas(atomAreas);
    }

    private List<GridArea> expandAreas() {
        LinkedList<GridArea> allAreas = new LinkedList<GridArea>();
        for (GridArea area : this.gridAreas) {
            if (area instanceof FixedArea) {
                for (CellBindingInfo cell : area.getCells()) {
                    FixedArea fixedArea = new FixedArea();
                    fixedArea.setSheetName(area.getSheetName());
                    fixedArea.getCells().add(cell);
                    cell.setOwnerArea(fixedArea);
                    allAreas.add(fixedArea);
                }
                continue;
            }
            area._affects.clear();
            area._depends.clear();
            allAreas.add(area);
        }
        return allAreas;
    }

    private void initAreas(List<GridArea> atomAreas) {
        for (GridArea area : atomAreas) {
            for (CellBindingInfo cell : area.getCells()) {
                for (CellBindingInfo prev : cell.getDepends()) {
                    if (prev.getOwnerArea() == area) continue;
                    GridArea.linkAreas(prev.getOwnerArea(), area);
                }
            }
            area._indegree = area._depends.size();
        }
    }

    private void scanAreas(List<GridArea> atomAreas) throws CalcLayerException {
        while (!atomAreas.isEmpty()) {
            List<GridArea> layerAreas = this.fetchAreas(atomAreas);
            if (layerAreas.isEmpty()) {
                this.checkDeadRoute(atomAreas);
            }
            this.markAreas(layerAreas);
            CalcLayer layer = this.createLayer(layerAreas);
            this.calcLayers.add(layer);
        }
    }

    private List<GridArea> fetchAreas(List<GridArea> atomAreas) {
        ArrayList<GridArea> topAreas = new ArrayList<GridArea>();
        Iterator<GridArea> i = atomAreas.iterator();
        while (i.hasNext()) {
            GridArea area = i.next();
            if (area._indegree != 0) continue;
            topAreas.add(area);
            i.remove();
        }
        return topAreas;
    }

    private void checkDeadRoute(List<GridArea> atomAreas) throws CalcLayerException {
        ArrayDeque<GridArea> route = new ArrayDeque<GridArea>();
        this.scanRoute(route, atomAreas.get(0));
        ReportLog.openLogger().error("\u5206\u6790\u5355\u5143\u683c\u5faa\u73af\u8def\u5f84\u65f6\u51fa\u73b0\u9519\u8bef\uff0c\u65e0\u6cd5\u5b8c\u6210\u8def\u5f84\u5206\u6790\uff1a" + this.toString(atomAreas));
        throw new CalcLayerException("\u5206\u6790\u5355\u5143\u683c\u5faa\u73af\u8def\u5f84\u65f6\u51fa\u73b0\u9519\u8bef\uff0c\u65e0\u6cd5\u5b8c\u6210\u8def\u5f84\u5206\u6790\u3002");
    }

    private void scanRoute(Deque<GridArea> route, GridArea current) throws CalcLayerException {
        if (route.contains(current)) {
            String routeMessage = this.toString(route);
            long typeCount = route.stream().map(Object::getClass).distinct().count();
            route.push(current);
            if (typeCount > 1L) {
                throw new CalcLayerException("\u68c0\u6d4b\u5230\u56fa\u5b9a\u548c\u6d6e\u52a8\u533a\u57df" + routeMessage + "\u4e4b\u95f4\u5b58\u5728\u5faa\u73af\u4f9d\u8d56\uff0c\u8bf7\u68c0\u67e5\u76f8\u5173\u5355\u5143\u683c\u8bbe\u7f6e" + this.getRegionCellMessage(route) + "\uff0c\u53ef\u4ee5\u5c1d\u8bd5\u5c06\u56fa\u5b9a\u533a\u57df\u5355\u5143\u683c\u6539\u4e3a\u6570\u636e\u96c6\u53d6\u6570\u3002");
            }
            throw new CalcLayerException("\u68c0\u6d4b\u5230\u5faa\u73af\u4f9d\u8d56\u7684\u6d6e\u52a8\u533a\u57df" + routeMessage + "\uff0c\u8bf7\u68c0\u67e5\u76f8\u5173\u5355\u5143\u683c\u8bbe\u7f6e" + this.getRegionCellMessage(route) + "\u3002");
        }
        route.push(current);
        for (GridArea next : current._affects) {
            if (next._indegree == 0) continue;
            this.scanRoute(route, next);
        }
        route.pop();
    }

    private String getRegionCellMessage(Deque<GridArea> route) {
        List<CellBindingInfo[]> cellRoute = this.getCellRoute(route);
        return this.toCellRouteString(cellRoute);
    }

    private String toCellRouteString(List<CellBindingInfo[]> cellRoute) {
        StringBuilder buffer = new StringBuilder();
        buffer.append('[');
        boolean started = false;
        for (CellBindingInfo[] route : cellRoute) {
            if (started) {
                buffer.append(", ");
            } else {
                started = true;
            }
            buffer.append(route[1].getPosition()).append("->").append(route[0].getPosition());
        }
        buffer.append(']');
        return buffer.toString();
    }

    private List<CellBindingInfo[]> getCellRoute(Deque<GridArea> route) {
        if (route.size() <= 1) {
            return Collections.emptyList();
        }
        ArrayList<CellBindingInfo[]> cells = new ArrayList<CellBindingInfo[]>();
        CellBindingInfo nextCell = null;
        Iterator<GridArea> curItr = route.iterator();
        Iterator<GridArea> nextItr = route.iterator();
        nextItr.next();
        while (curItr.hasNext() && nextItr.hasNext()) {
            GridArea current = curItr.next();
            GridArea next = nextItr.next();
            nextCell = this.findNextRef(nextCell, current, next, cells);
        }
        return cells;
    }

    private CellBindingInfo findNextRef(CellBindingInfo curCell, GridArea current, GridArea next, List<CellBindingInfo[]> cells) {
        if (curCell != null) {
            for (CellBindingInfo nextCell : curCell.getAffects()) {
                if (nextCell.getOwnerArea() != next) continue;
                cells.add(new CellBindingInfo[]{curCell, nextCell});
                return nextCell;
            }
        }
        for (CellBindingInfo cell : current.getCells()) {
            for (CellBindingInfo nextCell : cell.getAffects()) {
                if (nextCell.getOwnerArea() != next) continue;
                cells.add(new CellBindingInfo[]{cell, nextCell});
                return nextCell;
            }
        }
        return null;
    }

    private String toString(Collection<GridArea> route) {
        StringBuilder buffer = new StringBuilder("[");
        boolean started = false;
        for (GridArea area : route) {
            if (started) {
                buffer.append(", ");
            } else {
                started = true;
            }
            if (area instanceof ExpandingArea) {
                ExpandingArea expandingArea = (ExpandingArea)area;
                buffer.append(expandingArea.getSheetName()).append("!").append(expandingArea.getRegion());
                continue;
            }
            buffer.append(area.getCells().get(0).getPosition());
        }
        buffer.append(']');
        return buffer.toString();
    }

    private void markAreas(List<GridArea> layerAreas) {
        for (GridArea area : layerAreas) {
            for (GridArea next : area._affects) {
                --next._indegree;
            }
            area._affects.clear();
            area._depends.clear();
        }
    }

    private CalcLayer createLayer(List<GridArea> layerAreas) {
        CalcLayer layer = new CalcLayer();
        HashMap<String, GridArea> finder = new HashMap<String, GridArea>();
        for (GridArea area : layerAreas) {
            if (area instanceof FixedArea) {
                GridArea prevArea = (GridArea)finder.get(area.getSheetName());
                if (prevArea == null) {
                    layer.getAreas().add(area);
                    finder.put(area.getSheetName(), area);
                    continue;
                }
                for (CellBindingInfo cell : area.getCells()) {
                    prevArea.getCells().add(cell);
                    cell.setOwnerArea(prevArea);
                }
                continue;
            }
            layer.getAreas().add(area);
        }
        return layer;
    }

    public List<CalcLayer> getCalcLayers() {
        return this.calcLayers;
    }
}

