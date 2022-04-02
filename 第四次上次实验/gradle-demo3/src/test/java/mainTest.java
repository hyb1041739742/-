import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class mainTest {
    Solve solve;

    @ParameterizedTest
    @CsvSource({
            "20220321161205,0,20220321161105,0,输入错误",
            "20220321161205,0,20230321161205,0,超过30小时",
            "20220321140905,1,20220321161206,0,17.40",
            "20220321140905,0,20220321161206,1,5.40",
            "20220321140905,1,20220321161206,1,11.40",
            "20220321140905,0,20220321161206,0,11.40"
    })
    //输入year-month-day-minute-seconds 组成的字符串
    void test(String startTime, int inDayLightSavingOfStartTime, String endTime, int inDayLightSavingOfEndTime, String results){
        solve = new Solve(startTime, inDayLightSavingOfStartTime, endTime, inDayLightSavingOfEndTime);
        String result = solve.getResults();
        assertEquals(results, solve.getResults());
    }
}