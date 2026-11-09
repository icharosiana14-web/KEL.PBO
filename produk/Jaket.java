package produk;

import java.math.BigDecimal;

public class Jaket extends Produk {
    private final String bahan;

    public Jaket(String nama, BigDecimal harga, int stok, String bahan) {
        super(nama, harga, stok);
        if (bahan == null || bahan.isBlank()) {
            throw new IllegalArgumentException("Bahan jaket tidak boleh kosong.");
        }
        this.bahan = bahan.trim();
    }

    public String getBahan() {
        return bahan;
    }

    @Override
    public void tampilkanInfo() {
        System.out.println("Jenis: Jaket | Nama: " + getNama()
                + " | Harga: " + getHarga().toPlainString()
                + " | Stok: " + getStok() + " | Bahan: " + bahan);
    }
}

