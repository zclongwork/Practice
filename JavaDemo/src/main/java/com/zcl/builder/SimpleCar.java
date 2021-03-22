package com.zcl.builder;


/**
 * 建造者模式的简单实现
 */
public class SimpleCar {

    /** 汽车发动机 必须 */
    private String engine;
    /** 汽车车架 必须 */
    private String frame;
    /** 汽车轮子 必须 */
    private String wheel;

    /** 空调 可选 */
    private String airCondition;
    /** 电视 可选 */
    private String tv;

    private SimpleCar(Builder builder) {
        this.engine = builder.engine;
        this.frame = builder.frame;
        this.wheel = builder.wheel;

        this.airCondition = builder.airCondition;
        this.tv = builder.tv;
    }

    public static class Builder {
        private String engine;
        private String frame;
        private String wheel;

        private String airCondition;//可选
        private String tv;//可选

        public Builder(String engine, String frame, String wheel) {
            this.engine = engine;
            this.frame = frame;
            this.wheel = wheel;
        }

        public Builder setAirCondition(String airCondition) {
            this.airCondition = airCondition;
            return this;
        }

        public Builder setTv(String tv) {
            this.tv = tv;
            return this;
        }


        public SimpleCar build() {
            return new SimpleCar(this);
        }
    }

    @Override
    public String toString() {
        return "SimpleCar{" +
                "engine='" + engine + '\'' +
                ", frame='" + frame + '\'' +
                ", wheel='" + wheel + '\'' +
                ", airCondition='" + airCondition + '\'' +
                ", tv='" + tv + '\'' +
                '}';
    }
}
