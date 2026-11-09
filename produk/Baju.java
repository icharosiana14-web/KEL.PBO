package produk;

import java.math.BigDecimal;

public class Baju extends Produk {
    private final String ukuran;

    public Baju(String nama, BigDecimal harga, int stok, String ukuran) {
        super(nama, harga, stok);
        if (ukuran == null || ukuran.isBlank()) {
            throw new IllegalArgumentException("Ukuran baju tidak boleh kosong.");
        }
        this.ukuran = ukuran.trim().toUpperCase();
    }

    public String getUkuran() {
        return ukuran;
    }

    @Override
    public void tampilkanInfo() {
        System.out.println("Jenis: Baju | Nama: " + getNama()
                + " | Harga: " + getHarga().toPlainString()
                + " | Stok: " + getStok() + " | Ukuran: " + ukuran);
    }
}