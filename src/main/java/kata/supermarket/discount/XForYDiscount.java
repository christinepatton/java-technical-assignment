package kata.supermarket.discount;

import kata.supermarket.Item;

import java.math.BigDecimal;
import java.util.*;

// Buy One Get One Free discount
public class XForYDiscount implements Discount {

    private final Set<Item> appliesTo;
    private final int numBought;
    private final int numPaidFor;

    public XForYDiscount(final Set<Item> appliesTo, final int numBought, final int numPaidFor) {
        this.appliesTo = appliesTo;
        this.numBought = numBought;
        this.numPaidFor = numPaidFor;
    }

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
                    totalDiscount = totalDiscount.add(item.price().multiply(BigDecimal.valueOf(numBought - numPaidFor)));
                    seen.remove(item);
                } else {
                    seen.put(item, howMany + 1);
                }
            }
        }

        return totalDiscount;
    }


}
