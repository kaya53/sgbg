package com.sgbg.api.request;

import lombok.Getter;

@Getter
public enum Review {
    BEST(10), GOOD(5), BAD(0);

    private final int score;
    Review(int score) {
        this.score = score;
    }

    public static Review valueOfReview(int score) {
        for (Review r : values()) {
            if(r.score == score) {
                return r;
            }
        }
        return null;
    }
}
