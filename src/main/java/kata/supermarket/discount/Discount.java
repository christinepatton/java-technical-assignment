package kata.supermarket.discount;

import kata.supermarket.Item;

import java.math.BigDecimal;
import java.util.List;

public interface Discount {
    BigDecimal calculateDiscount(List<Item> items);
}
