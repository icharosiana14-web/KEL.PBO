package karyawan;

public class Admin extends Karyawan {
    public Admin(String nama, String id) {
        super(nama, id);
    }

    @Override
    public void tampilkanInfo() {
        System.out.println("Peran: Admin | Nama: " + getNama() + " | ID: " + getId());
    }
}

