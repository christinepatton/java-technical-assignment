package kata.supermarket;

import java.math.BigDecimal;
import java.util.Objects;

import static java.math.RoundingMode.HALF_UP;

public class ItemByWeight implements Item {

    private final WeighedProduct product;
    private final BigDecimal weightInKilos;

    ItemByWeight(final WeighedProduct product, final BigDecimal weightInKilos) {
        this.product = product;
        this.weightInKilos = weightInKilos;
    }

    public BigDecimal price() {
        return product.pricePerKilo().multiply(weightInKilos).setScale(2, HALF_UP);
    }

    public Boolean isWeighed() { return true; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemByWeight that = (ItemByWeight) o;
        return Objects.equals(product, that.product) && Objects.equals(weightInKilos, that.weightInKilos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, weightInKilos);
    }
}
