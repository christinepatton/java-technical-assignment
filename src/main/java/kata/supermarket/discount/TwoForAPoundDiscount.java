package kata.supermarket.discount;

import kata.supermarket.Item;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TwoForAPoundDiscount implements Discount {

    private final Set<Item> appliesTo;
    private final int numBought = 2;
    private final BigDecimal discountedPrice = BigDecimal.ONE;

    public TwoForAPoundDiscount(final Set<Item> appliesTo) {
        this.appliesTo = appliesTo;
    }

    @Override
    public BigDecimal calculateDiscount(List<Item> items) {
        BigDecimal totalDiscount = BigDecimal.ZERO;
        final Map<Item, Integer> seen = new HashMap<>();

        for (Item item: items) {
            if (!item.isWeighed() && appliesTo.contains(item)) {
                // Keep track of whether we've seen an odd number of this item so far.
                // If so, this item is the second half of the BOGOF combo, so apply the discount.
                Integer howMany = seen.get(item);
                if (howMany == null) {
                    howMany = 0;
                }
                if (howMany == numBought - 1) {
                    BigDecimal discountToAdd = item.price().multiply(BigDecimal.valueOf(numBought)).subtract(discountedPrice);
                    totalDiscount = totalDiscount.add(discountToAdd);
                    seen.remove(item);
                } else {
                    seen.put(item, howMany + 1);
                }
            }
        }

        return totalDiscount;
    }
}
