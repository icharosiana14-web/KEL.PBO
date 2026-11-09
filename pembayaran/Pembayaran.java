package pembayaran;

import java.math.BigDecimal;

public abstract class Pembayaran implements DapatDibayar {
    private final BigDecimal total;

    public Pembayaran(BigDecimal total) {
        if (total == null || total.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Total pembayaran harus lebih dari nol.");
        }
        this.total = total;
    }

    public BigDecimal getTotal() {
        return total;
    }

    @Override
    public abstract boolean prosesPembayaran();
}
