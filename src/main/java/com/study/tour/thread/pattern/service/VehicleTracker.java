package com.study.tour.thread.pattern.service;

import com.study.tour.thread.pattern.entity.Location;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 车辆状态追踪器
 */
public class VehicleTracker {


    private Map<String, Location> locMap = new ConcurrentHashMap<>();

    public void updateLocation(String vehicleId, Location newLocation) {
        locMap.put(vehicleId, newLocation);
    }


}
