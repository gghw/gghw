package com.ff.gghw.rules;

import org.joda.time.LocalTime;

public class LoanRules {
    public static final int MIN_LOAN = 5000;
    public static final int MAX_LOAN = 50000;

    public static final float INTEREST_PER_DAY = (0.1f / 30.0f);

    public static final int MIN_TERM_DAYS = 7;
    public static final int MAX_TERM_DAYS = 30;

    public static final int EXTEND_DAYS = 7;
    public static final float EXTEND_INTEREST = 0.5f;

    public static final int PREVIOUS_APPLICTION_TIME_LIMIT_HOURS = 24;
    public static final int PREVIOUS_APPLICTION_COUNT_LIMIT = 3;

    public static final LocalTime DENY_MAX_LOAN_INTERVAL_START = new LocalTime(0, 0);
    public static final LocalTime DENY_MAX_LOAN_INTERVAL_END = new LocalTime(8, 0);
}

