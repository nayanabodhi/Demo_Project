package Tests;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;

public class ToDelete {
    public static void main(String[] args) throws FileNotFoundException, YamlException {
        YamlReader reader = new YamlReader(new FileReader(System.getProperty("user.dir")+"\\src\\test\\java\\Configuration\\Users.yaml"));
        Object object = reader.read();
        System.out.println(object);
        Map map = (Map)object;
        System.out.println(map.get("username"));
        System.out.println(map.get("password"));
    }
}
