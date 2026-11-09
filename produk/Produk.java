package produk;

import java.math.BigDecimal;

public abstract class Produk {
    private final String nama;
    private final BigDecimal harga;
    private int stok;

    public Produk(String nama, BigDecimal harga, int stok) {
        if (nama == null || nama.isBlank()) {
            throw new IllegalArgumentException("Nama produk tidak boleh kosong.");
        }
        if (harga == null || harga.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Harga produk harus lebih dari nol.");
        }
        if (stok < 0) {
            throw new IllegalArgumentException("Stok tidak boleh negatif.");
        }
        this.nama = nama.trim();
        this.harga = harga;
        this.stok = stok;
    }

    public String getNama() {
        return nama;
    }

    public BigDecimal getHarga() {
        return harga;
    }

    public int getStok() {
        return stok;
    }

    public boolean kurangiStok(int jumlah) {
        if (jumlah <= 0 || jumlah > stok) {
            return false;
        }
        stok -= jumlah;
        return true;
    }

    public abstract void tampilkanInfo();
}