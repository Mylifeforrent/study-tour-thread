package com.study.tour.thread.pattern.service;

/**
 * 与运维中心对接的类
 * 模式角色: ImmutableObject.Manipulator
 */
public class OMCAgent extends Thread {

    boolean isTableModificationMsg = false;
    String updatedTableName = null;
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000 * 2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            isTableModificationMsg = true;
            updatedTableName = "MMSCInfo";
            //...
            /**
             * 从与OMC连接的Socket中读取消息并解析
             * 解析到数据更新消息后,重置MMSCRouter实例
             */
            if (isTableModificationMsg) {
                System.out.println("data change begin to get new data");
                if ("MMSCInfo".equals(updatedTableName)) {
                    MMSCRouter.setInstance(new MMSCRouter());
                }
            }
        }
    }
}
