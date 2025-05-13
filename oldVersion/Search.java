import java.util.ArrayList;
import java.util.List;

public class Search {

    public static List<Integer> search(List<String> list, String term) {
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).contains(term)) {
                indices.add(i);
            }
        }
        return indices;
    }
}
