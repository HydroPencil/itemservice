package hello.itemservice.domain.item;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ItemRepository {

    //멀티스레드 환경에서는 HashMap 사용 절대 지양, 싱글톤 환경에서 돌아가기 때문
    private static final Map<Long, Item> store = new HashMap<>();//static
    private static long sequence = 0L;//static

    public Item save(Item item) {
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    public Item findById(Long id) {
        return store.get(id);
    }

    public List<Item> findAll() {
        //감싸는 이유는 만일의 경우 반환받은 객체에 변화가 있을 경우 원본 객체를 보호하기 위해서
        return new ArrayList<>(store.values());
    }

    //정석은 DTO를 사용해서 DTO에 아래 정보 세개를 담은 전용 DTO를 만드는게 맞다. 언제나 명확한게 좋다.
    public void update(Long itemId, Item updateParam) {
        Item findItem = findById(itemId);
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }

    //테스트를 위해 사용
    public void clearStore() {
        store.clear();
    }
}
