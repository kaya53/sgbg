package com.sgbg.api.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReviewRes {

    @Schema(name = "best", example = "3")
    private int best;

    @Schema(name = "good", example = "2")
    private int good;

    @Schema(name = "bad", example = "1")
    private int bad;

    public static ReviewRes of(int best, int good, int bad) {
        ReviewRes res = new ReviewRes();
        res.setBest(best);
        res.setGood(good);
        res.setBad(bad);
        return res;
    }
}
