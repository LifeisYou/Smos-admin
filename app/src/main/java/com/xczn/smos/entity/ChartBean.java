package com.xczn.smos.entity;

import java.util.List;

/**
 * Created by zhangxiao
 * Date on 2018/6/21.
 */
public class ChartBean {


    /**
     * time : 2018-06-21
     * itemCurrentA : [{"time":"2018-06-21 15","value":"0.101"},{"time":"2018-06-21 14","value":"0.101"},{"time":"2018-06-21 13","value":"0.101"},{"time":"2018-06-21 12","value":"0.154"},{"time":"2018-06-21 11","value":"0.114"},{"time":"2018-06-21 10","value":"0.167"},{"time":"2018-06-21 09","value":"0.169"},{"time":"2018-06-21 08","value":"0.121"},{"time":"2018-06-21 07","value":"0.111"},{"time":"2018-06-21 06","value":"0.167"},{"time":"2018-06-21 05","value":"0.161"},{"time":"2018-06-21 04","value":"0.168"},{"time":"2018-06-21 03","value":"0.119"},{"time":"2018-06-21 02","value":"0.164"},{"time":"2018-06-21 01","value":"0.132"},{"time":"2018-06-21 00","value":"0.121"}]
     * itemCurrentB : [{"time":"2018-06-21 15","value":"0.166"},{"time":"2018-06-21 14","value":"0.166"},{"time":"2018-06-21 13","value":"0.166"},{"time":"2018-06-21 12","value":"0.127"},{"time":"2018-06-21 11","value":"0.164"},{"time":"2018-06-21 10","value":"0.123"},{"time":"2018-06-21 09","value":"0.161"},{"time":"2018-06-21 08","value":"0.182"},{"time":"2018-06-21 07","value":"0.106"},{"time":"2018-06-21 06","value":"0.179"},{"time":"2018-06-21 05","value":"0.15"},{"time":"2018-06-21 04","value":"0.151"},{"time":"2018-06-21 03","value":"0.175"},{"time":"2018-06-21 02","value":"0.101"},{"time":"2018-06-21 01","value":"0.135"},{"time":"2018-06-21 00","value":"0.147"}]
     * itemCurrentC : [{"time":"2018-06-21 15","value":"0.133"},{"time":"2018-06-21 14","value":"0.133"},{"time":"2018-06-21 13","value":"0.133"},{"time":"2018-06-21 12","value":"0.178"},{"time":"2018-06-21 11","value":"0.107"},{"time":"2018-06-21 10","value":"0.181"},{"time":"2018-06-21 09","value":"0.098"},{"time":"2018-06-21 08","value":"0.16"},{"time":"2018-06-21 07","value":"0.166"},{"time":"2018-06-21 06","value":"0.143"},{"time":"2018-06-21 05","value":"0.135"},{"time":"2018-06-21 04","value":"0.139"},{"time":"2018-06-21 03","value":"0.131"},{"time":"2018-06-21 02","value":"0.156"},{"time":"2018-06-21 01","value":"0.147"},{"time":"2018-06-21 00","value":"0.164"}]
     * lineCurrentAB : [{"time":"2018-06-21 15","value":"0.133"},{"time":"2018-06-21 14","value":"0.133"},{"time":"2018-06-21 13","value":"0.133"},{"time":"2018-06-21 12","value":"0.178"},{"time":"2018-06-21 11","value":"0.107"},{"time":"2018-06-21 10","value":"0.181"},{"time":"2018-06-21 09","value":"0.098"},{"time":"2018-06-21 08","value":"0.16"},{"time":"2018-06-21 07","value":"0.166"},{"time":"2018-06-21 06","value":"0.143"},{"time":"2018-06-21 05","value":"0.135"},{"time":"2018-06-21 04","value":"0.139"},{"time":"2018-06-21 03","value":"0.131"},{"time":"2018-06-21 02","value":"0.156"},{"time":"2018-06-21 01","value":"0.147"},{"time":"2018-06-21 00","value":"0.164"}]
     * lineCurrentBC : [{"time":"2018-06-21 15","value":"0.133"},{"time":"2018-06-21 14","value":"0.133"},{"time":"2018-06-21 13","value":"0.133"},{"time":"2018-06-21 12","value":"0.178"},{"time":"2018-06-21 11","value":"0.107"},{"time":"2018-06-21 10","value":"0.181"},{"time":"2018-06-21 09","value":"0.098"},{"time":"2018-06-21 08","value":"0.16"},{"time":"2018-06-21 07","value":"0.166"},{"time":"2018-06-21 06","value":"0.143"},{"time":"2018-06-21 05","value":"0.135"},{"time":"2018-06-21 04","value":"0.139"},{"time":"2018-06-21 03","value":"0.131"},{"time":"2018-06-21 02","value":"0.156"},{"time":"2018-06-21 01","value":"0.147"},{"time":"2018-06-21 00","value":"0.164"}]
     * lineCurrentAC : [{"time":"2018-06-21 15","value":"0.133"},{"time":"2018-06-21 14","value":"0.133"},{"time":"2018-06-21 13","value":"0.133"},{"time":"2018-06-21 12","value":"0.178"},{"time":"2018-06-21 11","value":"0.107"},{"time":"2018-06-21 10","value":"0.181"},{"time":"2018-06-21 09","value":"0.098"},{"time":"2018-06-21 08","value":"0.16"},{"time":"2018-06-21 07","value":"0.166"},{"time":"2018-06-21 06","value":"0.143"},{"time":"2018-06-21 05","value":"0.135"},{"time":"2018-06-21 04","value":"0.139"},{"time":"2018-06-21 03","value":"0.131"},{"time":"2018-06-21 02","value":"0.156"},{"time":"2018-06-21 01","value":"0.147"},{"time":"2018-06-21 00","value":"0.164"}]
     * itemVoltageA : [{"time":"2018-06-21 15","value":"0.133"},{"time":"2018-06-21 14","value":"0.133"},{"time":"2018-06-21 13","value":"0.133"},{"time":"2018-06-21 12","value":"0.178"},{"time":"2018-06-21 11","value":"0.107"},{"time":"2018-06-21 10","value":"0.181"},{"time":"2018-06-21 09","value":"0.098"},{"time":"2018-06-21 08","value":"0.16"},{"time":"2018-06-21 07","value":"0.166"},{"time":"2018-06-21 06","value":"0.143"},{"time":"2018-06-21 05","value":"0.135"},{"time":"2018-06-21 04","value":"0.139"},{"time":"2018-06-21 03","value":"0.131"},{"time":"2018-06-21 02","value":"0.156"},{"time":"2018-06-21 01","value":"0.147"},{"time":"2018-06-21 00","value":"0.164"}]
     * itemVoltageB : [{"time":"2018-06-21 15","value":"0.133"},{"time":"2018-06-21 14","value":"0.133"},{"time":"2018-06-21 13","value":"0.133"},{"time":"2018-06-21 12","value":"0.178"},{"time":"2018-06-21 11","value":"0.107"},{"time":"2018-06-21 10","value":"0.181"},{"time":"2018-06-21 09","value":"0.098"},{"time":"2018-06-21 08","value":"0.16"},{"time":"2018-06-21 07","value":"0.166"},{"time":"2018-06-21 06","value":"0.143"},{"time":"2018-06-21 05","value":"0.135"},{"time":"2018-06-21 04","value":"0.139"},{"time":"2018-06-21 03","value":"0.131"},{"time":"2018-06-21 02","value":"0.156"},{"time":"2018-06-21 01","value":"0.147"},{"time":"2018-06-21 00","value":"0.164"}]
     * itemVoltageC : [{"time":"2018-06-21 15","value":"0.133"},{"time":"2018-06-21 14","value":"0.133"},{"time":"2018-06-21 13","value":"0.133"},{"time":"2018-06-21 12","value":"0.178"},{"time":"2018-06-21 11","value":"0.107"},{"time":"2018-06-21 10","value":"0.181"},{"time":"2018-06-21 09","value":"0.098"},{"time":"2018-06-21 08","value":"0.16"},{"time":"2018-06-21 07","value":"0.166"},{"time":"2018-06-21 06","value":"0.143"},{"time":"2018-06-21 05","value":"0.135"},{"time":"2018-06-21 04","value":"0.139"},{"time":"2018-06-21 03","value":"0.131"},{"time":"2018-06-21 02","value":"0.156"},{"time":"2018-06-21 01","value":"0.147"},{"time":"2018-06-21 00","value":"0.164"}]
     * modulusP : [{"time":"2018-06-21 15","value":"0.133"},{"time":"2018-06-21 14","value":"0.133"},{"time":"2018-06-21 13","value":"0.133"},{"time":"2018-06-21 12","value":"0.178"},{"time":"2018-06-21 11","value":"0.107"},{"time":"2018-06-21 10","value":"0.181"},{"time":"2018-06-21 09","value":"0.098"},{"time":"2018-06-21 08","value":"0.16"},{"time":"2018-06-21 07","value":"0.166"},{"time":"2018-06-21 06","value":"0.143"},{"time":"2018-06-21 05","value":"0.135"},{"time":"2018-06-21 04","value":"0.139"},{"time":"2018-06-21 03","value":"0.131"},{"time":"2018-06-21 02","value":"0.156"},{"time":"2018-06-21 01","value":"0.147"},{"time":"2018-06-21 00","value":"0.164"}]
     * modulusQ : [{"time":"2018-06-21 15","value":"0.133"},{"time":"2018-06-21 14","value":"0.133"},{"time":"2018-06-21 13","value":"0.133"},{"time":"2018-06-21 12","value":"0.178"},{"time":"2018-06-21 11","value":"0.107"},{"time":"2018-06-21 10","value":"0.181"},{"time":"2018-06-21 09","value":"0.098"},{"time":"2018-06-21 08","value":"0.16"},{"time":"2018-06-21 07","value":"0.166"},{"time":"2018-06-21 06","value":"0.143"},{"time":"2018-06-21 05","value":"0.135"},{"time":"2018-06-21 04","value":"0.139"},{"time":"2018-06-21 03","value":"0.131"},{"time":"2018-06-21 02","value":"0.156"},{"time":"2018-06-21 01","value":"0.147"},{"time":"2018-06-21 00","value":"0.164"}]
     * modulusCOS : [{"time":"2018-06-21 15","value":"0.133"},{"time":"2018-06-21 14","value":"0.133"},{"time":"2018-06-21 13","value":"0.133"},{"time":"2018-06-21 12","value":"0.178"},{"time":"2018-06-21 11","value":"0.107"},{"time":"2018-06-21 10","value":"0.181"},{"time":"2018-06-21 09","value":"0.098"},{"time":"2018-06-21 08","value":"0.16"},{"time":"2018-06-21 07","value":"0.166"},{"time":"2018-06-21 06","value":"0.143"},{"time":"2018-06-21 05","value":"0.135"},{"time":"2018-06-21 04","value":"0.139"},{"time":"2018-06-21 03","value":"0.131"},{"time":"2018-06-21 02","value":"0.156"},{"time":"2018-06-21 01","value":"0.147"},{"time":"2018-06-21 00","value":"0.164"}]
     * degreeP : [{"time":"2018-06-21 15","value":"0.133"},{"time":"2018-06-21 14","value":"0.133"},{"time":"2018-06-21 13","value":"0.133"},{"time":"2018-06-21 12","value":"0.178"},{"time":"2018-06-21 11","value":"0.107"},{"time":"2018-06-21 10","value":"0.181"},{"time":"2018-06-21 09","value":"0.098"},{"time":"2018-06-21 08","value":"0.16"},{"time":"2018-06-21 07","value":"0.166"},{"time":"2018-06-21 06","value":"0.143"},{"time":"2018-06-21 05","value":"0.135"},{"time":"2018-06-21 04","value":"0.139"},{"time":"2018-06-21 03","value":"0.131"},{"time":"2018-06-21 02","value":"0.156"},{"time":"2018-06-21 01","value":"0.147"},{"time":"2018-06-21 00","value":"0.164"}]
     * degreeN : [{"time":"2018-06-21 15","value":"0.133"},{"time":"2018-06-21 14","value":"0.133"},{"time":"2018-06-21 13","value":"0.133"},{"time":"2018-06-21 12","value":"0.178"},{"time":"2018-06-21 11","value":"0.107"},{"time":"2018-06-21 10","value":"0.181"},{"time":"2018-06-21 09","value":"0.098"},{"time":"2018-06-21 08","value":"0.16"},{"time":"2018-06-21 07","value":"0.166"},{"time":"2018-06-21 06","value":"0.143"},{"time":"2018-06-21 05","value":"0.135"},{"time":"2018-06-21 04","value":"0.139"},{"time":"2018-06-21 03","value":"0.131"},{"time":"2018-06-21 02","value":"0.156"},{"time":"2018-06-21 01","value":"0.147"},{"time":"2018-06-21 00","value":"0.164"}]
     */

    private String time;
    private List<ChartData> phaseCurrentA;  //相电流
    private List<ChartData> phaseCurrentB;
    private List<ChartData> phaseCurrentC;
    private List<ChartData> lineCurrentAB;  //线电流
    private List<ChartData> lineCurrentBC;
    private List<ChartData> lineCurrentAC;
    private List<ChartData> phaseVoltageA;  //相电压
    private List<ChartData> phaseVoltageB;
    private List<ChartData> phaseVoltageC;
    private List<ChartData> lineVoltageAB;  //线电压
    private List<ChartData> lineVoltageBC;
    private List<ChartData> lineVoltageAC;
    private List<ChartData> modulusP;       //系数
    private List<ChartData> modulusQ;
    private List<ChartData> modulusCOS;
    private List<ChartData> degreeP;        //电度
    private List<ChartData> degreeN;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<ChartData> getPhaseCurrentA() {
        return phaseCurrentA;
    }

    public void setPhaseCurrentA(List<ChartData> phaseCurrentA) {
        this.phaseCurrentA = phaseCurrentA;
    }

    public List<ChartData> getPhaseCurrentB() {
        return phaseCurrentB;
    }

    public void setPhaseCurrentB(List<ChartData> phaseCurrentB) {
        this.phaseCurrentB = phaseCurrentB;
    }

    public List<ChartData> getPhaseCurrentC() {
        return phaseCurrentC;
    }

    public void setPhaseCurrentC(List<ChartData> phaseCurrentC) {
        this.phaseCurrentC = phaseCurrentC;
    }

    public List<ChartData> getLineCurrentAB() {
        return lineCurrentAB;
    }

    public void setLineCurrentAB(List<ChartData> lineCurrentAB) {
        this.lineCurrentAB = lineCurrentAB;
    }

    public List<ChartData> getLineCurrentBC() {
        return lineCurrentBC;
    }

    public void setLineCurrentBC(List<ChartData> lineCurrentBC) {
        this.lineCurrentBC = lineCurrentBC;
    }

    public List<ChartData> getLineCurrentAC() {
        return lineCurrentAC;
    }

    public void setLineCurrentAC(List<ChartData> lineCurrentAC) {
        this.lineCurrentAC = lineCurrentAC;
    }

    public List<ChartData> getPhaseVoltageA() {
        return phaseVoltageA;
    }

    public void setPhaseVoltageA(List<ChartData> phaseVoltageA) {
        this.phaseVoltageA = phaseVoltageA;
    }

    public List<ChartData> getPhaseVoltageB() {
        return phaseVoltageB;
    }

    public void setPhaseVoltageB(List<ChartData> phaseVoltageB) {
        this.phaseVoltageB = phaseVoltageB;
    }

    public List<ChartData> getPhaseVoltageC() {
        return phaseVoltageC;
    }

    public void setPhaseVoltageC(List<ChartData> phaseVoltageC) {
        this.phaseVoltageC = phaseVoltageC;
    }

    public List<ChartData> getLineVoltageAB() {
        return lineVoltageAB;
    }

    public void setLineVoltageAB(List<ChartData> lineVoltageAB) {
        this.lineVoltageAB = lineVoltageAB;
    }

    public List<ChartData> getLineVoltageBC() {
        return lineVoltageBC;
    }

    public void setLineVoltageBC(List<ChartData> lineVoltageBC) {
        this.lineVoltageBC = lineVoltageBC;
    }

    public List<ChartData> getLineVoltageAC() {
        return lineVoltageAC;
    }

    public void setLineVoltageAC(List<ChartData> lineVoltageAC) {
        this.lineVoltageAC = lineVoltageAC;
    }

    public List<ChartData> getModulusP() {
        return modulusP;
    }

    public void setModulusP(List<ChartData> modulusP) {
        this.modulusP = modulusP;
    }

    public List<ChartData> getModulusQ() {
        return modulusQ;
    }

    public void setModulusQ(List<ChartData> modulusQ) {
        this.modulusQ = modulusQ;
    }

    public List<ChartData> getModulusCOS() {
        return modulusCOS;
    }

    public void setModulusCOS(List<ChartData> modulusCOS) {
        this.modulusCOS = modulusCOS;
    }

    public List<ChartData> getDegreeP() {
        return degreeP;
    }

    public void setDegreeP(List<ChartData> degreeP) {
        this.degreeP = degreeP;
    }

    public List<ChartData> getDegreeN() {
        return degreeN;
    }

    public void setDegreeN(List<ChartData> degreeN) {
        this.degreeN = degreeN;
    }

    public static class ChartData {
        /**
         * time : 2018-06-21 15
         * value : 0.101
         */

        private String time;
        private String value;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
