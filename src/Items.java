import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Items {
    private LinkedList<Item> items;
    private LinkedHashMap<Integer, Item> itemMap;

    public Items (String res){
        items = new LinkedList<Item>();
        itemMap = new LinkedHashMap<>();
        Properties properties = new Properties();

        try {
            InputStreamReader input = new InputStreamReader(new FileInputStream(res), "UTF-8");
            properties.load(input);
            for (String key : properties.stringPropertyNames()) {
                items.add(new Item(key,properties.getProperty(key)));
            }
        } catch (IOException io) {
            io.printStackTrace();
        }

        Collections.sort(items);

        Iterator<Item> it = items.iterator();

        while(it.hasNext()){
            Item item = it.next();
            itemMap.put(item.getHex(), item);
        }

    }

    public LinkedHashMap<Integer, Item> getItemMap() {
        return itemMap;
    }

    public LinkedList<Item> getItems() {
        return items;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        Iterator<Item> it = itemMap.values().iterator();
        while(it.hasNext()){
            stringBuilder.append(it.next()).append("\n");
        }
        return stringBuilder.toString();
    }

    class Item implements Comparable{
        private String hex;
        private String value;

        public Item(String key, String value) {
            this.hex = key.substring(4);
            this.value = value;
        }

        public int getHex() {
            return Integer.parseInt(hex, 16);
        }

        public void setHex(String hex) {
            this.hex = hex;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Item{" +
                    "ID='" + getHex() + '\'' +
                    ", value='" + value + '\'' +
                    '}';
        }

        @Override
        public int compareTo(Object o) {
                int h1 = getHex();
                int h2 = ((Item)o).getHex();
                return h1 > h2 ? 1 : -1;
        }
    }
}