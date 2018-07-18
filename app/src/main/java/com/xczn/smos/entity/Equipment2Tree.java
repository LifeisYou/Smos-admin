package com.xczn.smos.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhangxiao
 * Date on 2018/5/11.
 */
public class Equipment2Tree {

    /**
     * factory_id : 1
     * factory_name : 第一厂站
     * factory_description : 厂站描述
     * intervals : [{"interval_id":1,"interval_name":"第一间隔","interval_description":"间隔描述","interval_type":"间隔类型","voltage_level":"电压等级","factory_id":1,"factory_name":"第一厂站","equipments":[{"equipment_id":1,"equipment_name":"第一设备","communication_status":0,"equipment_type":"装置类型","equipment_stencil":"模版id","factory_id":1,"factory_name":"第一厂站","interval_id":1,"interval_name":"第一间隔"},{"equipment_id":2,"equipment_name":"第二设备","communication_status":1,"equipment_type":"装置类型","equipment_stencil":"模版id","factory_id":1,"factory_name":"第一厂站","interval_id":1,"interval_name":"第一间隔"}]},{"interval_id":2,"interval_name":"第二间隔","interval_description":"间隔描述","interval_type":"间隔类型","voltage_level":"电压等级","factory_id":1,"factory_name":"第一厂站","equipments":[{"equipment_id":3,"equipment_name":"第三设备","communication_status":1,"equipment_type":"装置类型","equipment_stencil":"模版id","factory_id":1,"factory_name":"第一厂站","interval_id":2,"interval_name":"第二间隔"},{"equipment_id":4,"equipment_name":"第四设备","communication_status":0,"equipment_type":"装置类型","equipment_stencil":"模版id","factory_id":1,"factory_name":"第一厂站","interval_id":2,"interval_name":"第二间隔"}]}]
     */

    private int factory_id;
    private String factory_name;
    private String factory_description;
    private List<IntervalsBean> intervals;

    public int getFactory_id() {
        return factory_id;
    }

    public void setFactory_id(int factory_id) {
        this.factory_id = factory_id;
    }

    public String getFactory_name() {
        return factory_name;
    }

    public void setFactory_name(String factory_name) {
        this.factory_name = factory_name;
    }

    public String getFactory_description() {
        return factory_description;
    }

    public void setFactory_description(String factory_description) {
        this.factory_description = factory_description;
    }

    public List<IntervalsBean> getIntervals() {
        return intervals;
    }

    public void setIntervals(List<IntervalsBean> intervals) {
        this.intervals = intervals;
    }

    public static class IntervalsBean {
        /**
         * interval_id : 1
         * interval_name : 第一间隔
         * interval_description : 间隔描述
         * interval_type : 间隔类型
         * voltage_level : 电压等级
         * factory_id : 1
         * factory_name : 第一厂站
         * equipments : [{"equipment_id":1,"equipment_name":"第一设备","communication_status":0,"equipment_type":"装置类型","equipment_stencil":"模版id","factory_id":1,"factory_name":"第一厂站","interval_id":1,"interval_name":"第一间隔"},{"equipment_id":2,"equipment_name":"第二设备","communication_status":1,"equipment_type":"装置类型","equipment_stencil":"模版id","factory_id":1,"factory_name":"第一厂站","interval_id":1,"interval_name":"第一间隔"}]
         */

        private int interval_id;
        private String interval_name;
        private String interval_description;
        private String interval_type;
        private String voltage_level;
        private int factory_id;
        private String factory_name;
        private List<EquipmentsBean> equipments;

        public int getInterval_id() {
            return interval_id;
        }

        public void setInterval_id(int interval_id) {
            this.interval_id = interval_id;
        }

        public String getInterval_name() {
            return interval_name;
        }

        public void setInterval_name(String interval_name) {
            this.interval_name = interval_name;
        }

        public String getInterval_description() {
            return interval_description;
        }

        public void setInterval_description(String interval_description) {
            this.interval_description = interval_description;
        }

        public String getInterval_type() {
            return interval_type;
        }

        public void setInterval_type(String interval_type) {
            this.interval_type = interval_type;
        }

        public String getVoltage_level() {
            return voltage_level;
        }

        public void setVoltage_level(String voltage_level) {
            this.voltage_level = voltage_level;
        }

        public int getFactory_id() {
            return factory_id;
        }

        public void setFactory_id(int factory_id) {
            this.factory_id = factory_id;
        }

        public String getFactory_name() {
            return factory_name;
        }

        public void setFactory_name(String factory_name) {
            this.factory_name = factory_name;
        }

        public List<EquipmentsBean> getEquipments() {
            return equipments;
        }

        public void setEquipments(List<EquipmentsBean> equipments) {
            this.equipments = equipments;
        }

        public static class EquipmentsBean implements Serializable{
            /**
             * equipment_id : 1
             * equipment_name : 第一设备
             * communication_status : 0
             * equipment_type : 装置类型
             * equipment_stencil : 模版id
             * factory_id : 1
             * factory_name : 第一厂站
             * interval_id : 1
             * interval_name : 第一间隔
             */

            private int equipment_id;
            private String equipment_name;
            private int communication_status;
            private String equipment_type;
            private String equipment_stencil;
            private int factory_id;
            private String factory_name;
            private int interval_id;
            private String interval_name;

            public int getEquipment_id() {
                return equipment_id;
            }

            public void setEquipment_id(int equipment_id) {
                this.equipment_id = equipment_id;
            }

            public String getEquipment_name() {
                return equipment_name;
            }

            public void setEquipment_name(String equipment_name) {
                this.equipment_name = equipment_name;
            }

            public int getCommunication_status() {
                return communication_status;
            }

            public void setCommunication_status(int communication_status) {
                this.communication_status = communication_status;
            }

            public String getEquipment_type() {
                return equipment_type;
            }

            public void setEquipment_type(String equipment_type) {
                this.equipment_type = equipment_type;
            }

            public String getEquipment_stencil() {
                return equipment_stencil;
            }

            public void setEquipment_stencil(String equipment_stencil) {
                this.equipment_stencil = equipment_stencil;
            }

            public int getFactory_id() {
                return factory_id;
            }

            public void setFactory_id(int factory_id) {
                this.factory_id = factory_id;
            }

            public String getFactory_name() {
                return factory_name;
            }

            public void setFactory_name(String factory_name) {
                this.factory_name = factory_name;
            }

            public int getInterval_id() {
                return interval_id;
            }

            public void setInterval_id(int interval_id) {
                this.interval_id = interval_id;
            }

            public String getInterval_name() {
                return interval_name;
            }

            public void setInterval_name(String interval_name) {
                this.interval_name = interval_name;
            }
        }
    }
}
