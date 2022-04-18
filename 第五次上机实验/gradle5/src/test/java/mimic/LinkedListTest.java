package mimic;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LinkedListTest {

    @ParameterizedTest
    @CsvSource({
            "null, null, null, 1",
            "A,null,null,1",
            "A, B, null, 2",
            "A,A,A,1",
            "A,B,B,1",
            "B,B,A,2"
    })
    void test(String a, String b, String c, int results){
        List<String> list = new LinkedList();
        if(a.equals("null"))
            list.add(null);
        else
            list.add(a);

        if(b.equals("null"))
            list.add(null);
        else
            list.add(b);

        if(c.equals("null"))
            list.remove(null);
        else
            list.remove(c);

        assertEquals(results, list.size());
    }
}