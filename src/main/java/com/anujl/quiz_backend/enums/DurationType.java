package com.anujl.quiz_backend.enums;

public enum DurationType {

    SEC_30(30),
    SEC_60(60),
    SEC_120(120),
    SEC_300(300),   // 5 minutes
    SEC_600(600),   // 10 minutes
    SEC_1200(1200); // 20 minutes

    public final int seconds;

    DurationType(int seconds) {
        this.seconds = seconds;
    }
}
