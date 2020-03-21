import java.util.Arrays;
import java.util.List;


public class Test {
    public static void main(String[] args) {
//        System.out.println("11b550150216h101^80.0".toUpperCase());
        List<String> fields = Arrays.asList("dddd ffHHHdddf".split(" "));

        for(int i=0;i<fields.size();i++){
            fields.set(i,fields.get(i).toUpperCase());
        }
        System.out.println(fields);

    }


    private static class QueryField {
    }
}
