package kata.supermarket.discount;

import kata.supermarket.Item;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// Buy One Get One Free discount
public class BOGOFDiscount implements Discount {

    private final Set<Item> appliesTo;

    public BOGOFDiscount(final Set<Item> appliesTo) {
        this.appliesTo = appliesTo;
    }

    public BigDecimal calculateDiscount(List<Item> items) {
        BigDecimal totalDiscount = BigDecimal.ZERO;
        final Set<Item> seen = new HashSet<>();

        for (Item item: items) {
            if (!item.isWeighed() && appliesTo.contains(item)) {
                // Keep track of whether we've seen an odd number of this item so far.
                // If so, this item is the second half of the BOGOF combo, so apply the discount.
                if (seen.contains(item)) {
                    totalDiscount = totalDiscount.add(item.price());
                    seen.remove(item);
                } else {
                    seen.add(item);
                }
            }
        }

        return totalDiscount;
    }


}
