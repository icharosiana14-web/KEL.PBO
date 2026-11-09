package karyawan;

public abstract class Karyawan {
    private final String nama;
    private final String id;

    public Karyawan(String nama, String id) {
        if (nama == null || nama.isBlank()) {
            throw new IllegalArgumentException("Nama karyawan tidak boleh kosong.");
        }
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("ID karyawan tidak boleh kosong.");
        }
        this.nama = nama.trim();
        this.id = id.trim();
    }

    public String getNama() {
        return nama;
    }

    public String getId() {
        return id;
    }

    public abstract void tampilkanInfo();
}
