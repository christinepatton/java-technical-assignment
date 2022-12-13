package kata.supermarket;

import kata.supermarket.discount.TwoForAPoundDiscount;
import kata.supermarket.discount.XForYDiscount;
import kata.supermarket.discount.Discount;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BasketTest {

    @DisplayName("basket provides its total value when containing...")
    @MethodSource
    @ParameterizedTest(name = "{0}")
    void basketProvidesTotalValue(String description, String expectedTotal, Iterable<Item> items, Iterable<Discount> discounts) {
        final Basket basket = new Basket();
        items.forEach(basket::add);
        discounts.forEach(basket::add);
        assertEquals(new BigDecimal(expectedTotal), basket.total());
    }

    static Stream<Arguments> basketProvidesTotalValue() {
        return Stream.of(
                noItems(),
                aSingleItemPricedPerUnit(),
                multipleItemsPricedPerUnit(),
                aSingleItemPricedByWeight(),
                multipleItemsPricedByWeight(),
                multipleItemsPricedPerUnitWithBOGOFDiscount(),
                multipleItemsPricedPerUnitWithThreeForTwoDiscount(),
                multipleItemsPricedPerUnitWithTwoForAPound()
        );
    }

    private static Arguments aSingleItemPricedByWeight() {
        return Arguments.of("a single weighed item", "1.25", Collections.singleton(twoFiftyGramsOfAmericanSweets()), Collections.emptyList());
    }

    private static Arguments multipleItemsPricedByWeight() {
        return Arguments.of("multiple weighed items", "1.85",
                Arrays.asList(twoFiftyGramsOfAmericanSweets(), twoHundredGramsOfPickAndMix()),
                Collections.emptyList()
        );
    }

    private static Arguments multipleItemsPricedPerUnit() {
        return Arguments.of("multiple items priced per unit", "2.04",
                Arrays.asList(aPackOfDigestives(), aPintOfMilk()),
                Collections.emptyList());
    }

    private static Arguments multipleItemsPricedPerUnitWithBOGOFDiscount() {
        Product digestives = new Product(new BigDecimal("1.55"));
        return Arguments.of("multiple identical items priced per unit with BOGOF", "1.55",
                Arrays.asList(digestives.oneOf(), digestives.oneOf()),
                Arrays.asList(bogofOnDigestives(digestives)));
    }

    private static Arguments multipleItemsPricedPerUnitWithThreeForTwoDiscount() {
        Product digestives = new Product(new BigDecimal("1.55"));
        return Arguments.of("multiple identical items priced per unit with 3 for 2", "3.10",
                Arrays.asList(digestives.oneOf(), digestives.oneOf(), digestives.oneOf()),
                Arrays.asList(threeForTwoOnDigestives(digestives)));
    }

    private static Arguments multipleItemsPricedPerUnitWithTwoForAPound() {
        Product digestives = new Product(new BigDecimal("1.55"));
        return Arguments.of("multiple identical items priced per unit with two for a pound", "1.00",
                Arrays.asList(digestives.oneOf(), digestives.oneOf()),
                Arrays.asList(twoForAPoundOnDigestives(digestives)));
    }


    private static Arguments aSingleItemPricedPerUnit() {
        return Arguments.of("a single item priced per unit", "0.49", Collections.singleton(aPintOfMilk()), Collections.emptyList());
    }

    private static Arguments noItems() {
        return Arguments.of("no items", "0.00", Collections.emptyList(), Collections.emptyList());
    }

    private static Item aPintOfMilk() {
        return new Product(new BigDecimal("0.49")).oneOf();
    }

    private static Item aPackOfDigestives() {
        return new Product(new BigDecimal("1.55")).oneOf();
    }

    private static WeighedProduct aKiloOfAmericanSweets() {
        return new WeighedProduct(new BigDecimal("4.99"));
    }

    private static Item twoFiftyGramsOfAmericanSweets() {
        return aKiloOfAmericanSweets().weighing(new BigDecimal(".25"));
    }

    private static WeighedProduct aKiloOfPickAndMix() {
        return new WeighedProduct(new BigDecimal("2.99"));
    }

    private static Item twoHundredGramsOfPickAndMix() {
        return aKiloOfPickAndMix().weighing(new BigDecimal(".2"));
    }

    private static Discount bogofOnDigestives(final Product theDigestives) {
        Set<Item> appliesTo = new HashSet<>();
        appliesTo.add(theDigestives.oneOf());
        // TODO: consider not hard-coding 1 and 2
        return new XForYDiscount(appliesTo, 2, 1);
    }

    private static Discount threeForTwoOnDigestives(final Product theDigestives) {
        Set<Item> appliesTo = new HashSet<>();
        appliesTo.add(theDigestives.oneOf());
        return new XForYDiscount(appliesTo, 3, 2);
    }

    private static Discount twoForAPoundOnDigestives(final Product theDigestives) {
        Set<Item> appliesTo = new HashSet<>();
        appliesTo.add(theDigestives.oneOf());
        return new TwoForAPoundDiscount(appliesTo);
    }
}