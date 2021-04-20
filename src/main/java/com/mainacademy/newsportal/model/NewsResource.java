package com.mainacademy.newsportal.model;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewsResource {

    private Integer id;
    private String name;
    private Rating rating;

    @Getter
    @AllArgsConstructor
    private enum Rating {
        ONE(1),
        TWO(2),
        THREE(3),
        FOUR(4),
        FIVE(5);

        private final Integer rValue;

        public static Rating byRatingVelue(Integer rValue) {
            switch (rValue) {
                case 1: return Rating.ONE;
                case 2: return Rating.TWO;
                case 3: return Rating.THREE;
                case 4: return Rating.FOUR;
                case 5: return Rating.FIVE;
                default: throw new IllegalArgumentException(rValue + "can not be parsed to Rating");
            }
        }
    }

}
