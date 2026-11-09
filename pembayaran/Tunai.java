package pembayaran;

import java.math.BigDecimal;

public class Tunai extends Pembayaran {
    private final BigDecimal uangDibayar;

    public Tunai(BigDecimal total, BigDecimal uangDibayar) {
        super(total);
        if (uangDibayar == null || uangDibayar.compareTo(total) < 0) {
            throw new IllegalArgumentException("Uang tunai kurang dari total pembayaran.");
        }
        this.uangDibayar = uangDibayar;
    }

    public BigDecimal getUangDibayar() {
        return uangDibayar;
    }

    @Override
    public boolean prosesPembayaran() {
        System.out.println("Metode pembayaran: Tunai");
        System.out.println("Uang dibayar: Rp" + uangDibayar.toPlainString());
        System.out.println("Kembalian: Rp" + uangDibayar.subtract(getTotal()).toPlainString());
        return true;
    }
}
