import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import produk.*;
import karyawan.*;
import pembayaran.*;

public class Main {
    private static final Scanner input = new Scanner(System.in);
    private static final NumberFormat RUPIAH = NumberFormat.getNumberInstance(Locale.forLanguageTag("id-ID"));

    private static class ItemKeranjang {
        private final Produk produk;
        private int jumlah;

        private ItemKeranjang(Produk produk, int jumlah) {
            this.produk = produk;
            this.jumlah = jumlah;
        }

        private BigDecimal subtotal() {
            return produk.getHarga().multiply(BigDecimal.valueOf(jumlah));
        }
    }

    private static String bacaTeks(String pesan) {
        while (true) {
            System.out.print(pesan);
            String nilai = input.nextLine().trim();
            if (!nilai.isEmpty()) return nilai;
            System.out.println("Input tidak boleh kosong.");
        }
    }

    private static int bacaBilangan(String pesan, int minimum, int maksimum) {
        while (true) {
            System.out.print(pesan);
            try {
                int nilai = Integer.parseInt(input.nextLine().trim());
                if (nilai >= minimum && nilai <= maksimum) return nilai;
            } catch (NumberFormatException e) {
                System.out.println("Masukkan bilangan bulat yang valid.");
                continue;
            }
            System.out.println("Masukkan angka antara " + minimum + " sampai " + maksimum + ".");
        }
    }

    private static BigDecimal bacaNominal(String pesan) {
        while (true) {
            System.out.print(pesan);
            String teks = input.nextLine().trim().replace(",", ".");
            try {
                BigDecimal nilai = new BigDecimal(teks);
                if (nilai.compareTo(BigDecimal.ZERO) > 0) return nilai;
            } catch (NumberFormatException e) {
                System.out.println("Masukkan nominal yang valid, contoh: 75000 atau 75000.50.");
                continue;
            }
            System.out.println("Nominal harus lebih dari nol.");
        }
    }

    private static boolean konfirmasi(String pesan) {
        while (true) {
            String jawaban = bacaTeks(pesan + " (y/n): ");
            if (jawaban.equalsIgnoreCase("y")) return true;
            if (jawaban.equalsIgnoreCase("n")) return false;
            System.out.println("Jawab dengan y atau n.");
        }
    }

    private static String rupiah(BigDecimal nilai) {
        return "Rp" + RUPIAH.format(nilai);
    }

    private static List<Produk> buatKatalog() {
        List<Produk> katalog = new ArrayList<>();
        System.out.println("\n=== INPUT KATALOG TOKO ===");
        System.out.println("Masukkan data untuk tiga jenis produk.");

        String namaBaju = bacaTeks("Nama baju: ");
        BigDecimal hargaBaju = bacaNominal("Harga baju: Rp");
        int stokBaju = bacaBilangan("Stok baju: ", 0, Integer.MAX_VALUE);
        String ukuran = bacaTeks("Ukuran baju: ");
        katalog.add(new Baju(namaBaju, hargaBaju, stokBaju, ukuran));

        String namaCelana = bacaTeks("\nNama celana: ");
        BigDecimal hargaCelana = bacaNominal("Harga celana: Rp");
        int stokCelana = bacaBilangan("Stok celana: ", 0, Integer.MAX_VALUE);
        String model = bacaTeks("Model celana: ");
        katalog.add(new Celana(namaCelana, hargaCelana, stokCelana, model));

        String namaJaket = bacaTeks("\nNama jaket: ");
        BigDecimal hargaJaket = bacaNominal("Harga jaket: Rp");
        int stokJaket = bacaBilangan("Stok jaket: ", 0, Integer.MAX_VALUE);
        String bahan = bacaTeks("Bahan jaket: ");
        katalog.add(new Jaket(namaJaket, hargaJaket, stokJaket, bahan));
        return katalog;
    }

    private static void tampilkanKatalog(List<Produk> katalog) {
        System.out.println("\n=== DAFTAR PRODUK ===");
        for (int i = 0; i < katalog.size(); i++) {
            System.out.print((i + 1) + ". ");
            katalog.get(i).tampilkanInfo();
        }
    }

    private static int jumlahDiKeranjang(List<ItemKeranjang> keranjang, Produk produk) {
        for (ItemKeranjang item : keranjang) {
            if (item.produk == produk) return item.jumlah;
        }
        return 0;
    }

    private static void tambahKeKeranjang(List<Produk> katalog, List<ItemKeranjang> keranjang) {
        tampilkanKatalog(katalog);
        int pilihan = bacaBilangan("Pilih nomor produk (1-3): ", 1, katalog.size());
        Produk produk = katalog.get(pilihan - 1);
        int stokTersedia = produk.getStok() - jumlahDiKeranjang(keranjang, produk);
        if (stokTersedia <= 0) {
            System.out.println("Stok produk ini sudah habis atau seluruhnya ada di keranjang.");
            return;
        }
        int jumlah = bacaBilangan("Jumlah yang dibeli (tersedia " + stokTersedia + "): ", 1, stokTersedia);
        for (ItemKeranjang item : keranjang) {
            if (item.produk == produk) {
                item.jumlah += jumlah;
                System.out.println("Jumlah produk berhasil diperbarui di keranjang.");
                return;
            }
        }
        keranjang.add(new ItemKeranjang(produk, jumlah));
        System.out.println("Produk berhasil ditambahkan ke keranjang.");
    }

    private static BigDecimal hitungTotal(List<ItemKeranjang> keranjang) {
        BigDecimal total = BigDecimal.ZERO;
        for (ItemKeranjang item : keranjang) total = total.add(item.subtotal());
        return total;
    }

    private static void tampilkanKeranjang(List<ItemKeranjang> keranjang) {
        System.out.println("\n=== KERANJANG BELANJA ===");
        for (ItemKeranjang item : keranjang) {
            System.out.println(item.produk.getNama() + " x" + item.jumlah
                    + " = " + rupiah(item.subtotal()));
        }
        System.out.println("Total sementara: " + rupiah(hitungTotal(keranjang)));
    }

    private static Karyawan pilihKaryawan() {
        System.out.println("\n=== PETUGAS YANG MELAYANI ===");
        System.out.println("1. Kasir\n2. Admin\n3. Pramuniaga");
        int pilihan = bacaBilangan("Pilih peran: ", 1, 3);
        String nama = bacaTeks("Nama karyawan: ");
        String id = bacaTeks("ID karyawan: ");
        switch (pilihan) {
            case 1: return new Kasir(nama, id);
            case 2: return new Admin(nama, id);
            default: return new Pramuniaga(nama, id);
        }
    }

    private static DapatDibayar pilihPembayaran(BigDecimal total) {
        System.out.println("\n=== METODE PEMBAYARAN ===");
        System.out.println("1. Tunai\n2. Transfer Bank\n3. E-Wallet");
        int pilihan = bacaBilangan("Pilih metode: ", 1, 3);
        if (pilihan == 1) {
            BigDecimal uang;
            do {
                uang = bacaNominal("Uang yang dibayarkan: Rp");
                if (uang.compareTo(total) < 0) {
                    System.out.println("Uang kurang. Total belanja " + rupiah(total));
                }
            } while (uang.compareTo(total) < 0);
            return new Tunai(total, uang);
        }
        if (pilihan == 2) {
            System.out.println("Silakan lakukan transfer di luar aplikasi ini.");
            return new Transfer(total, konfirmasi("Apakah transfer sudah diterima/diperiksa oleh petugas?"));
        }
        System.out.println("Silakan lakukan pembayaran E-Wallet di luar aplikasi ini.");
        return new EWallet(total, konfirmasi("Apakah pembayaran sudah diterima/diperiksa oleh petugas?"));
    }

    private static void cetakStruk(List<ItemKeranjang> keranjang, BigDecimal total, Karyawan karyawan) {
        System.out.println("\n========================================");
        System.out.println("             TOKO PAKAIAN STYLE");
        System.out.println("========================================");
        for (ItemKeranjang item : keranjang) {
            System.out.println(item.produk.getNama() + " x" + item.jumlah
                    + " @ " + rupiah(item.produk.getHarga())
                    + " = " + rupiah(item.subtotal()));
        }
        System.out.println("----------------------------------------");
        System.out.println("TOTAL       : " + rupiah(total));
        System.out.println("Petugas     : " + karyawan.getNama() + " (" + karyawan.getId() + ")");
        System.out.println("Status      : LUNAS");
        System.out.println("Terima kasih telah berbelanja.");
        System.out.println("========================================");
    }

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("          SISTEM TOKO PAKAIAN STYLE");
        System.out.println("========================================");

        List<Produk> katalog = buatKatalog();
        List<ItemKeranjang> keranjang = new ArrayList<>();
        boolean lanjut = true;
        while (lanjut) {
            tambahKeKeranjang(katalog, keranjang);
            if (keranjang.isEmpty()) {
                System.out.println("Keranjang masih kosong.");
                lanjut = konfirmasi("Coba pilih produk lagi?");
            } else {
                tampilkanKeranjang(keranjang);
                lanjut = konfirmasi("Tambah produk lain?");
            }
        }

        if (keranjang.isEmpty()) {
            System.out.println("Tidak ada transaksi karena keranjang kosong.");
            input.close();
            return;
        }

        BigDecimal total = hitungTotal(keranjang);
        Karyawan karyawan = pilihKaryawan();
        karyawan.tampilkanInfo();
        DapatDibayar pembayaran = pilihPembayaran(total);
        boolean berhasil = pembayaran.prosesPembayaran();

        if (berhasil) {
            for (ItemKeranjang item : keranjang) {
                boolean stokBerhasil = item.produk.kurangiStok(item.jumlah);
                if (!stokBerhasil) {
                    throw new IllegalStateException("Stok berubah dan tidak mencukupi saat penyelesaian transaksi.");
                }
            }
            cetakStruk(keranjang, total, karyawan);
            System.out.println("\n=== SISA STOK ===");
            for (Produk produk : katalog) {
                System.out.println(produk.getNama() + ": " + produk.getStok());
            }
        } else {
            System.out.println("Transaksi tidak diselesaikan. Stok tidak dikurangi.");
        }
        input.close();
    }
}


