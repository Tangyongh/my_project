package com.dingdingyijian.ddyj.mvp.bean;

public class NeedsCountBean {


    private long receiveCount;
    private long waitCount;
    private long sendCount;

    public long getReceiveCount() {
        return receiveCount;
    }

    public void setReceiveCount(long receiveCount) {
        this.receiveCount = receiveCount;
    }

    public long getWaitCount() {
        return waitCount;
    }

    public void setWaitCount(long waitCount) {
        this.waitCount = waitCount;
    }

    public long getSendCount() {
        return sendCount;
    }

    public void setSendCount(long sendCount) {
        this.sendCount = sendCount;
    }
}
