package kata.supermarket.discount;

import kata.supermarket.Item;
import kata.supermarket.Product;
import kata.supermarket.WeighedProduct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TwoForAPoundDiscountTest {

    private Set<Item> itemsToDiscount;
    private Product productByUnit;
    private WeighedProduct productByWeight;
    private TwoForAPoundDiscount discount;

    @BeforeEach
    void setUp() {
        itemsToDiscount = new HashSet<>();
        productByUnit = new Product(BigDecimal.TEN);
        productByWeight = new WeighedProduct(BigDecimal.TEN);

        itemsToDiscount.add(productByUnit.oneOf());
        // It should arguably throw an exception to try and add a weighed item to a discount that only
        // applies to products sold by unit - but the code should correctly handle this case and skip
        // over this product.
        itemsToDiscount.add(productByWeight.weighing(BigDecimal.TEN));

        discount = new TwoForAPoundDiscount(itemsToDiscount);
    }

    @Test
    void testTwoIdenticalItemsCostAPound() {
        Item matchingItem1 = productByUnit.oneOf();
        Item matchingItem2 = productByUnit.oneOf();

        List<Item> items = Arrays.asList(matchingItem1, matchingItem2);

        assertEquals(BigDecimal.valueOf(19l), discount.calculateDiscount(items));
    }

}