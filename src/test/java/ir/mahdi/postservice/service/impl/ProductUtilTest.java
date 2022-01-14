package ir.mahdi.postservice.service.impl;

import ir.mahdi.postservice.util.ProductUtil;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;


class ProductUtilTest {

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "'(1,53.38,$45) (2,88.62,$98) (3,78.48,$3) (4,72.30,$76) (5,30.18,$9)(6,46.34,$48)' | 81 | 4",
            "' (1,23.9,$45) (2,80.5,$83) (3,62.48,$53)' | 95 | 3,1",
            "' (1,15.3,$34)' | 8 | ",
            "'(1,85.31,$29) (2,14.55,$74) (3,3.98,$16) (4,26.24,$55) (5,63.69,$52) (6,76.25,$75) (7,60.02,$74) " +
                    "(8,93.18,$35) (9,89.95,$78)' | 75 | 7,2",
            "'(1,90.72,$13) (2,33.80,$40) (3,43.15,$10) (4,37.97,$16) (5,46.81,$36)(6,48.77,$79) (7,81.80,$45) " +
                    "(8,19.36,$79) (9,6.76,$64)' | 56 | 9,8",

    })
    void pack(String packOfProduct, Integer maxWeight, String expected) {
        List<Long> actual = ProductUtil.pack(packOfProduct, maxWeight);
        if (expected == null) {
            assertEquals(0, actual.size());
        } else {
            List<Long> expectedIDS = Arrays.stream(expected.split(","))
                    .map(Long::valueOf)
                    .sorted((o1, o2) -> {
                        return -1 * Long.compare(o1, o2);
                    })
                    .collect(Collectors.toList());

            Long[] expectedArray = expectedIDS.toArray(new Long[0]);

            Long[] actualArray = actual.toArray(new Long[0]);

            assertArrayEquals(expectedArray, actualArray);
        }

    }
}