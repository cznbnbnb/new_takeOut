import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
/**
 *
 * @author zhoufy
 * @date 2019年2月20日 下午2:19:13
 */

public class Demo {
    @Test
    public void test() {
        List<String> list = Arrays.asList("aaaa", "bbbb", "cccc");

        //静态方法语法	ClassName::methodName
        System.out.println();
    }

    public static void print(String content){
        System.out.println(content);
    }
}
