package com.example.application.dca.core;
/*
 * © Udsm All rights reserved.
 *
 *
 * Date: 7/22/2020
 * Time: 10:26 AM
 */


import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Ramadhan Juma <ramaj93@yahoo.com>
 * @since 0.0.1
 */
public class Factory {

    public static double[][] lineData = {
            {1, 1, 2, 0.0922, 0.0470},
            {2, 2, 3, 0.4930, 0.2511},
            {3, 3, 4, 0.3660, 0.1864},
            {4, 4, 5, 0.3811, 0.1941},
            {5, 5, 6, 0.8190, 0.7070},
            {6, 6, 7, 0.1872, 0.6188},
            {7, 7, 8, 0.7114, 0.2351},
            {8, 8, 9, 1.0300, 0.7400},
            {9, 9, 10, 1.0440, 0.7400},
            {10, 10, 11, 0.1966, 0.0650},
            {11, 11, 12, 0.3744, 0.1298},
            {12, 12, 13, 1.4680, 1.1550},
            {13, 13, 14, 0.5416, 0.7129},
            {14, 14, 15, 0.5910, 0.5260},
            {15, 15, 16, 0.7463, 0.5450},
            {16, 16, 17, 1.2890, 1.7210},
            {17, 17, 18, 0.7320, 0.5740},
            {18, 2, 19, 0.1640, 0.1565},
            {19, 19, 20, 1.5042, 1.3554},
            {20, 20, 21, 0.4095, 0.4784},
            {21, 21, 22, 0.7089, 0.9373},
            {22, 3, 23, 0.4512, 0.3083},
            {23, 23, 24, 0.8980, 0.7091},
            {24, 24, 25, 0.8960, 0.7011},
            {25, 6, 26, 0.2030, 0.1034},
            {26, 26, 27, 0.2842, 0.1447},
            {27, 27, 28, 1.0590, 0.9337},
            {28, 28, 29, 0.8042, 0.7006},
            {29, 29, 30, 0.5075, 0.2585},
            {30, 30, 31, 0.9744, 0.9630},
            {31, 31, 32, 0.3105, 0.3619},
            {32, 32, 33, 0.3410, 0.5302},
    };

    public static double[][] busData = {
            {1, 0, 0, 0, 1},
            {2, 100, 60, 0, 0.5},
            {3, 90, 40, 0, 0.5},
            {4, 120, 80, 0, 1},
            {5, 60, 30, 0, 0.5},
            {6, 60, 20, 0, 0.1},
            {7, 200, 100, 0, 0.5},
            {8, 200, 100, 0, 0.1},
            {9, 60, 20, 0, 0.1},
            {10, 60, 20, 0, 0.1},
            {11, 45, 30, 0, 0.5},
            {12, 60, 35, 0, 1},
            {13, 60, 35, 0, 0.1},
            {14, 120, 80, 0, 0.5},
            {15, 60, 10, 0, 0.1},
            {16, 60, 20, 0, 0.1},
            {17, 60, 20, 0, 0.1},
            {18, 90, 40, 0, 0.5},
            {19, 90, 40, 0, 0.5},
            {20, 90, 40, 0, 1},
            {21, 90, 40, 0, 1},
            {22, 90, 40, 0, 0.5},
            {23, 90, 50, 0, 0.1},
            {24, 420, 200, 0, 0.5},
            {25, 420, 200, 0, 0.5},
            {26, 60, 25, 0, 0.1},
            {27, 60, 25, 0, 0.1},
            {28, 60, 20, 0, 0.5},
            {29, 120, 70, 0, 1},
            {30, 200, 600, 0, 0.1},
            {31, 150, 70, 0, 0.5},
            {32, 210, 100, 0, 0.1},
            {33, 60, 40, 0, 0.5}
    };

    public static double randomPriority() {
        return Math.random();
    }

    public static double randomRealPower(int index) {
        if (index != -1) {
            return busData[index][1];
        }
        return Math.random() * 500;
    }

    public static double randomReactivePower(int index) {
        if (index != -1) {
            return busData[index][2];
        }
        return Math.random() * 500;
    }

    public static double randomResistance(int index) {
        if (index != -1) {
            return lineData[index][3];
        }
        return Math.random() * 500;
    }

    public static double randomReactance(int index) {
        if (index != -1) {
            return lineData[index][4];
        }
        return Math.random() * 500;
    }

    public static int randomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public static Grid generateNetwork(int nodes, int zones) {
        Grid grid = new Grid();
        Map<Integer, List<Integer>> zoneNodes = new HashMap<>();
        for (int i = 1; i <= nodes; i++) {
            Bus bus = new Bus(i);
            bus.setPriority(randomPriority());
            bus.setRealPower(randomRealPower(i - 1));
            bus.setReactivePower(randomReactivePower(i - 1));
            bus.setStatus(1);
            int zone = i == 1 ? 1 : randomInt(1, zones);
            bus.setZone(zone);
            if (!zoneNodes.containsKey(zone)) {
                zoneNodes.put(zone, new LinkedList<>());
            }
            zoneNodes.get(zone).add(bus.getId());
            grid.addNode(i, bus);
        }

        List<Integer> edges = new LinkedList<>();
        for (int z = 1; z <= zones; z++) {
            List<Integer> li = zoneNodes.get(z);
            if (z > 1) {
                edges.add(li.get(0));
            }
            for (int n = 0; n < li.size() - 1; n++) {
                grid.connect(li.get(n), li.get(n + 1), randomResistance(grid.getLineIndex() - 1), randomReactance(grid.getLineIndex() - 1));
            }
        }

        edges.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                if (o1 < o2) {
                    return -1;
                } else if (o1.equals(o2)) {
                    return 0;
                }
                return 1;
            }
        });

        for (int edge : edges) {
            grid.connect(edge - 1, edge, randomResistance(grid.getLineIndex() - 1), randomReactance(grid.getLineIndex() - 1));
        }

        return grid;
    }

    public static Grid loadCsvNetwork(String file) {
        CsvData data = new CsvData(file);
        Grid grid = new Grid();
        double[][] buses = data.busData;
        double[][] lines = data.lineData;
        for (int i = 1; i <= buses.length; i++) {
            Bus bus = new Bus(i);
            bus.setRealPower(buses[i - 1][1]);
            bus.setReactivePower(buses[i - 1][2]);
            bus.setStatus((int) buses[i - 1][3]);
            bus.setPriority(buses[i - 1][4]);
            grid.addNode(i, bus);
        }
        for (int i = 0; i < lines.length; i++) {
            grid.connect((int) lines[i][1], (int) lines[i][2], lines[i][3], lines[i][4]);
        }
        return grid;
    }
}
