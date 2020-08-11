import java.io.*;
import java.util.Properties;

public class Test {
    public static void main(String[] args) {
        Items items = new Items("resources_cht.properties");
        System.out.println(items);
    }
}
