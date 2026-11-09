package pembayaran;

import java.math.BigDecimal;

public class EWallet extends Pembayaran {
    private final boolean sudahDikonfirmasi;

    public EWallet(BigDecimal total, boolean sudahDikonfirmasi) {
        super(total);
        this.sudahDikonfirmasi = sudahDikonfirmasi;
    }

    @Override
    public boolean prosesPembayaran() {
        System.out.println("Metode pembayaran: E-Wallet");
        System.out.println("Total pembayaran: Rp" + getTotal().toPlainString());
        if (!sudahDikonfirmasi) {
            System.out.println("Pembayaran belum dikonfirmasi. Transaksi dibatalkan.");
            return false;
        }
        System.out.println("Simulasi pembayaran E-Wallet dikonfirmasi oleh petugas.");
        return true;
    }
}
