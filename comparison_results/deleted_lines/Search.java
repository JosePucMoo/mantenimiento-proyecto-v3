import java.util.ArrayList;  // línea borrada
import java.util.List;  // línea borrada
public class Search {  // línea borrada
    public static List<Integer> search(List<String> list, String term) {  // línea borrada
        List<Integer> indices = new ArrayList<>();  // línea borrada
        for (int i = 0; i < list.size(); i++) {  // línea borrada
            if (list.get(i).contains(term)) {  // línea borrada
                indices.add(i);  // línea borrada
            }  // línea borrada
        }  // línea borrada
        return indices;  // línea borrada
    }  // línea borrada
}  // línea borrada
