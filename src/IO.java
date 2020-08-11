import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.LinkedList;

public class IO {
    private static final byte[] HEADER = new byte[]{0x01, 0x10, 0x09, 0x18, 0x19, 0x00, (byte) 0xFF, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
    private static final byte[] BUFFER = new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};

    /**
     * Test
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        IO io = new IO();
        LinkedList<String> ls = new LinkedList<>();
        ls.add("0aca");
        ls.add("0acb");
        ls.add("0acc");
        io.save(ls, "test.slt");

        LinkedList<Integer> items = io.read("test.slt");
        Items allItems = new Items("resources_cht.properties");
        Iterator<Integer> it = items.iterator();
        while (it.hasNext()) {
            Integer item = it.next();
            if (allItems.getItemMap().containsKey(item)) {
                System.out.println(allItems.getItemMap().get(item));
            }
        }

    }

    int byteArrToInt(byte[] bytes) {
        return ((bytes[0] & 0xFF) << 8) |
                ((bytes[1] & 0xFF) << 0);
    }

    public LinkedList<Integer> read(String path) throws Exception {
        byte[] input = Files.readAllBytes(Paths.get(path));

        int counter = 0;

        //Check Header bytes.
        for (; counter < HEADER.length && counter < input.length; counter++) {
            if (HEADER[counter] != input[counter]) {
                return null;
            }
        }

        byte[] itemID = new byte[2];
        LinkedList<Integer> itemIDs = new LinkedList<>();
        //Read all items.
        for (; counter + 13 < input.length; counter += 13) {
            itemID[1] = input[counter];
            itemID[0] = input[++counter];

            itemIDs.add(byteArrToInt(itemID));
        }

        return itemIDs;
    }

    public void save(LinkedList<String> itemKeys, String path) {
        int counter = 0;
        byte[] items = new byte[3600];

        //Insert the header bytes.
        for (int i = 0; i < HEADER.length; i++, counter++) {
            items[counter] = HEADER[i];
        }

        //Insert a single item data.
        for (String item : itemKeys) {
            items[counter++] = (byte) Integer.parseInt(item.substring(2), 16);
            items[counter++] = (byte) Integer.parseInt(item.substring(0, 2), 16);
            //Insert buffer bytes.
            for (int i = 0; i < BUFFER.length; i++, counter++) {
                items[counter] = HEADER[i];
            }
        }

        try {
            FileOutputStream fos = new FileOutputStream(new File(path));
            fos.write(items, 0, counter);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
