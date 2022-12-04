package kata.supermarket.discount;

import kata.supermarket.Item;
import kata.supermarket.Product;
import kata.supermarket.WeighedProduct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BOGOFDiscountTest {

    private Set<Item> itemsToDiscount;
    private Product productByUnit;
    private WeighedProduct productByWeight;
    private BOGOFDiscount discount;

    @BeforeEach
    void setUp() {
        itemsToDiscount = new HashSet<>();
        productByUnit = new Product(BigDecimal.ONE);
        productByWeight = new WeighedProduct(BigDecimal.TEN);

        itemsToDiscount.add(productByUnit.oneOf());
        // It should arguably throw an exception to try and add a weighed item to a discount that only
        // applies to products sold by unit - but the code should correctly handle this case and skip
        // over this product.
        itemsToDiscount.add(productByWeight.weighing(BigDecimal.TEN));

        discount = new BOGOFDiscount(itemsToDiscount);
    }

    @Test
    void testEmptyBasketReturnsZeroDiscount() {
        assertEquals(BigDecimal.ZERO, discount.calculateDiscount(new ArrayList<Item>()));
    }

    @Test
    void testBasketWithOnlyWeighedItemsReturnsZeroDiscount() {
        Item weighedItem1 = productByWeight.weighing(BigDecimal.ONE);
        Item weighedItem2 = productByWeight.weighing(BigDecimal.ONE);

        List<Item> items = new ArrayList<>();
        items.add(weighedItem1);
        items.add(weighedItem2);

        assertEquals(BigDecimal.ZERO, discount.calculateDiscount(items));
    }

    @Test
    void testBasketWithTwoApplicableItemsReturnsCorrectDiscount() {
        Item matchingItem1 = productByUnit.oneOf();
        Item matchingItem2 = productByUnit.oneOf();

        List<Item> items = new ArrayList<>();
        items.add(matchingItem1);
        items.add(matchingItem2);

        assertEquals(BigDecimal.ONE, discount.calculateDiscount(items));
    }

    @Test
    void testBasketWithThreeApplicableItemsReturnsCorrectDiscount() {
        Item matchingItem1 = productByUnit.oneOf();
        Item matchingItem2 = productByUnit.oneOf();
        Item matchingItem3 = productByUnit.oneOf();

        List<Item> items = new ArrayList<>();
        items.add(matchingItem1);
        items.add(matchingItem2);
        items.add(matchingItem3);

        assertEquals(BigDecimal.ONE, discount.calculateDiscount(items));
    }
}
