package produk;

import java.math.BigDecimal;

public class Celana extends Produk {
    private final String model;

    public Celana(String nama, BigDecimal harga, int stok, String model) {
        super(nama, harga, stok);
        if (model == null || model.isBlank()) {
            throw new IllegalArgumentException("Model celana tidak boleh kosong.");
        }
        this.model = model.trim();
    }

    public String getModel() {
        return model;
    }

    @Override
    public void tampilkanInfo() {
        System.out.println("Jenis: Celana | Nama: " + getNama()
                + " | Harga: " + getHarga().toPlainString()
                + " | Stok: " + getStok() + " | Model: " + model);
    }
}
