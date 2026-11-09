package pembayaran;

import java.math.BigDecimal;

public class Transfer extends Pembayaran {
    private final boolean sudahDikonfirmasi;

    public Transfer(BigDecimal total, boolean sudahDikonfirmasi) {
        super(total);
        this.sudahDikonfirmasi = sudahDikonfirmasi;
    }

    @Override
    public boolean prosesPembayaran() {
        System.out.println("Metode pembayaran: Transfer Bank");
        System.out.println("Total transfer: Rp" + getTotal().toPlainString());
        if (!sudahDikonfirmasi) {
            System.out.println("Transfer belum dikonfirmasi. Pembayaran dibatalkan.");
            return false;
        }
        System.out.println("Simulasi transfer dikonfirmasi oleh petugas.");
        return true;
    }
}