package karyawan;

public class Pramuniaga extends Karyawan {
    public Pramuniaga(String nama, String id) {
        super(nama, id);
    }

    @Override
    public void tampilkanInfo() {
        System.out.println("Peran: Pramuniaga | Nama: " + getNama() + " | ID: " + getId());
    }
}
