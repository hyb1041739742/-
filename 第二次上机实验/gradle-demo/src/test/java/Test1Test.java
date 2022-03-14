import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class Test1Test {

    Triangle triangle = new Triangle();

    @ParameterizedTest
    @CsvSource({
            "0, 4, 5, 输入错误",
            "3, 4, 6, 不等边三角形",
            "3, 1, 1, 非三角形"
    })
    void test(int a, int b, int c, String str){
        assertEquals(str, triangle.classify(a, b, c));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/边界值分析.csv")
    void test_csv_file_source1(int a, int b, int c, String expected) {
        assertEquals(expected, triangle.classify(a,b,c));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/健壮值分析.csv")
    void test_csv_file_source2(int a, int b, int c, String expected) {
        assertEquals(expected, triangle.classify(a,b,c));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/最坏值分析.csv")
    void test_csv_file_source3(int a, int b, int c, String expected) {
        assertEquals(expected, triangle.classify(a,b,c));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/最坏健壮值分析.csv")
    void test_csv_file_source4(int a, int b, int c, String expected) {
        assertEquals(expected, triangle.classify(a,b,c));
    }
}