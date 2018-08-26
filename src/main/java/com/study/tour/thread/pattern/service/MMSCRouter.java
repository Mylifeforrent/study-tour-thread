package com.study.tour.thread.pattern.service;

import com.study.tour.thread.pattern.entity.MMSCInfo;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 彩票中心路由规则管理器
 * 模式角色: ImmutableObject.ImmutableObject
 */
public final class MMSCRouter {

    //用volatile修饰,保证多线程环境下,该变量的可见性
    private static  volatile MMSCRouter instance = new MMSCRouter();

    //维护手机号码前缀到彩信中心之间的映射关系
    private final Map<String, MMSCInfo> routeMap;

    public MMSCRouter() {
        //数据库的数据加载到内存,存为map
        this.routeMap = MMSCRouter.retrieveRouterMapFromDB();
    }

    private static Map<String, MMSCInfo> retrieveRouterMapFromDB() {
        Map<String, MMSCInfo> result = new HashMap<>();
        //模拟数据
        for (int i=0; i<10; i++) {
            result.put(i + "", mockData(i));
        }
        return result;
    }

    private static MMSCInfo mockData(int size) {
        int nextInt = new Random().nextInt(10);
        return new MMSCInfo("device:" + size, "url:" + size + nextInt, size);
    }


    public static MMSCRouter getInstance() {
        return instance;
    }


    /**
     *  根据手机号码前缀获取彩信中心信息
     * @param msisdnPrefix 手机号=号码前缀
     * @return 彩信中心信息
     */
    public MMSCInfo getMMSC(String msisdnPrefix) {
        return routeMap.get(msisdnPrefix);
    }


    /**
     * 当前MMSCRouter 更新为新的实例
     * @param newInstance
     */
    public static void setInstance(MMSCRouter newInstance) {
        instance = newInstance;
    }


    private static Map<String, MMSCInfo> deepCopy(Map<String, MMSCInfo> m) {
        Map<String, MMSCInfo> result = new HashMap<>();
        for (String key : m.keySet()) {
            result.put(key, new MMSCInfo(m.get(key)));
        }
        return result;
    }

    public Map<String, MMSCInfo> getRouteMap() {
        //做防御性复制
        return Collections.unmodifiableMap(deepCopy(routeMap));
    }



}
