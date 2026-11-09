package karyawan;

public class Kasir extends Karyawan {
    public Kasir(String nama, String id) {
        super(nama, id);
    }

    @Override
    public void tampilkanInfo() {
        System.out.println("Peran: Kasir | Nama: " + getNama() + " | ID: " + getId());
    }
}
