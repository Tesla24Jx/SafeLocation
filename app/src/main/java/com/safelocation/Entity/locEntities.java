package com.safelocation.Entity;

import java.util.List;

/**
 * Created by Juliet on 2017/3/13.
 */

public class locEntities {

    /**
     * status : 0
     * message : 成功
     * size : 1
     * total : 1
     * entities : [{"entity_name":"13212345678","create_time":"2017-03-13 17:11:49","modify_time":"2017-03-13 17:35:49","realtime_point":{"loc_time":1489397750,"location":[113.95740412192,22.54956376362],"direction":0,"height":40,"radius":55,"speed":0}}]
     */

    private int status;
    private String message;
    private int size;
    private int total;
    private List<EntitiesBean> entities;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<EntitiesBean> getEntities() {
        return entities;
    }

    public void setEntities(List<EntitiesBean> entities) {
        this.entities = entities;
    }

    public static class EntitiesBean {
        /**
         * entity_name : 13212345678
         * create_time : 2017-03-13 17:11:49
         * modify_time : 2017-03-13 17:35:49
         * realtime_point : {"loc_time":1489397750,"location":[113.95740412192,22.54956376362],"direction":0,"height":40,"radius":55,"speed":0}
         */

        private String entity_name;
        private String create_time;
        private String modify_time;
        private RealtimePointBean realtime_point;

        public String getEntity_name() {
            return entity_name;
        }

        public void setEntity_name(String entity_name) {
            this.entity_name = entity_name;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getModify_time() {
            return modify_time;
        }

        public void setModify_time(String modify_time) {
            this.modify_time = modify_time;
        }

        public RealtimePointBean getRealtime_point() {
            return realtime_point;
        }

        public void setRealtime_point(RealtimePointBean realtime_point) {
            this.realtime_point = realtime_point;
        }

        public static class RealtimePointBean {
            /**
             * loc_time : 1489397750
             * location : [113.95740412192,22.54956376362]
             * direction : 0
             * height : 40
             * radius : 55
             * speed : 0
             */

            private int loc_time;
            private int direction;
            private int height;
            private int radius;
            private int speed;
            private List<Double> location;

            public int getLoc_time() {
                return loc_time;
            }

            public void setLoc_time(int loc_time) {
                this.loc_time = loc_time;
            }

            public int getDirection() {
                return direction;
            }

            public void setDirection(int direction) {
                this.direction = direction;
            }

            public int getHeight() {
                return height;
            }

            public void setHeight(int height) {
                this.height = height;
            }

            public int getRadius() {
                return radius;
            }

            public void setRadius(int radius) {
                this.radius = radius;
            }

            public int getSpeed() {
                return speed;
            }

            public void setSpeed(int speed) {
                this.speed = speed;
            }

            public List<Double> getLocation() {
                return location;
            }

            public void setLocation(List<Double> location) {
                this.location = location;
            }
        }
    }
}
