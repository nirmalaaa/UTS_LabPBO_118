import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

class BankAccount {
    private final String nama;
    private final String alamat;
    private final String nomor_telepon;
    private final String nomor_akun;
    private int saldo;
    private final LocalDateTime tanggal_registrasi;

    public BankAccount(String nama, String alamat, String nomor_telepon, int saldo) {
        this.nama = nama;
        this.alamat = alamat;
        this.nomor_telepon = nomor_telepon;
        this.saldo = saldo;
        this.nomor_akun = generateNomorAkun();
        this.tanggal_registrasi = LocalDateTime.now();
    }

    private String generateNomorAkun() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 7; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    public void topUp(int jumlahTopUp) {
        saldo += jumlahTopUp;
        System.out.println();
        System.out.println(" Top up berhasil.");
        System.out.println(" Saldo Anda saat ini              : Rp. " + saldo);
        System.out.println();
    }

    public void transfer(int jumlahTransfer) {
        if (saldo >= jumlahTransfer) {
            saldo -= jumlahTransfer;
            System.out.println();
            System.out.println(" Top up berhasil.");
            System.out.println(" Saldo Anda saat ini              : Rp. " + saldo);
            System.out.println();
        } else {
            System.out.println();
            System.out.println(" Transfer gagal. Saldo tidak mencukupi.");
            System.out.println();
        }
    }

    public void showDescription() {
        System.out.println("==========================================================");
        System.out.println("    Informasi Rekening Anda !");
        System.out.println("    Nama                   : " + nama);
        System.out.println("    Alamat                 : " + alamat);
        System.out.println("    Nomor Telepon          : " + nomor_telepon);
        System.out.println("    Nomor Akun             : " + nomor_akun);
        System.out.println("    Saldo                  : Rp. " + saldo);
        System.out.println("    Tanggal Registrasi     : " + tanggal_registrasi);
        System.out.println("==========================================================");
    }

    public String getNomorAkun() {
        return nomor_akun;
    }

    public int getSaldo() {
        return saldo;
    }

    public String getNama() {
        return nama;
    }
}


class BankingSystem {
    private final ArrayList<BankAccount> accounts;
    private final Scanner scanner;

    public BankingSystem() {
        accounts = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    public void showMenu() {
        int choice;
        do {
            System.out.println();
            System.out.println("+--------------------------------------------------------+");
            System.out.println("| Silahkan pilih program yang Anda mau !                 |");
            System.out.println("| 1. Registrasi akun baru                                |");
            System.out.println("| 2. Mengirim uang                                       |");
            System.out.println("| 3. Menyimpan uang                                      |");
            System.out.println("| 4. Mengecek informasi rekening pribadi                 |");
            System.out.println("| 5. Keluar                                              |");
            System.out.println("+--------------------------------------------------------+");
            System.out.print(" Pilih nomor program              : ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            switch (choice) {
                case 1:
                    registrasiAkunBaru();
                    break;
                case 2:
                    mengirimUang();
                    break;
                case 3:
                    menyimpanUang();
                    break;
                case 4:
                    mengecekInformasiRekening();
                    break;
                case 5:
                    System.out.println(" Terima kasih telah menggunakan layanan kami.");
                    break;
                default:
                    System.out.println(" Pilihan tidak valid.");
                    break;
            }
        } while (choice != 5);
    }

    private void registrasiAkunBaru() {
        System.out.print(" Masukkan nama Anda               : ");
        String nama = scanner.nextLine();
        System.out.print(" Masukkan alamat Anda             : ");
        String alamat = scanner.nextLine();
        System.out.print(" Masukkan nomor HP Anda           : ");
        String nomorTelepon = scanner.nextLine();
        System.out.print(" Masukkan saldo awal Anda         : Rp. ");
        int saldo = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        BankAccount account = new BankAccount(nama, alamat, nomorTelepon, saldo);
        accounts.add(account);
        System.out.println();
        System.out.println(" Akun bank anda berhasil dibuat. ");
        System.out.println(" Silahkan menikmati layanan yang kami sediakan");
        System.out.println();
        account.showDescription();
    }

    private void mengirimUang() {
        System.out.print(" Masukkan nomor akun pengirim     : ");
        String nomorAkunPengirim = scanner.nextLine();
        System.out.print(" Masukkan nomor akun penerima     : ");
        String nomorAkunPenerima = scanner.nextLine();
        System.out.print(" Masukkan jumlah uang             : Rp. ");
        int jumlahUang = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.println();

        BankAccount pengirim = findAccountByNomorAkun(nomorAkunPengirim);
        BankAccount penerima = findAccountByNomorAkun(nomorAkunPenerima);

        if (pengirim != null && penerima != null) {
            pengirim.transfer(jumlahUang);
            penerima.topUp(jumlahUang);
            System.out.println(" Transaksi berhasil.");
            System.out.println("==========================================================");
            System.out.println("    Nomor akun pengirim        : " + pengirim.getNomorAkun());
            System.out.println("    Nama akun pengirim         : " + pengirim.getNama());
            System.out.println("    Nomor akun penerima        : " + penerima.getNomorAkun());
            System.out.println("    Nama akun penerima         : " + penerima.getNama());
            System.out.println("    Jumlah transfer            : Rp " + jumlahUang);
            System.out.println("    Waktu transaksi            : " + LocalDateTime.now());
            System.out.println("==========================================================");
            System.out.println();
        } else {
            System.out.println(" Transaksi gagal.");
            System.out.println(" Nomor akun pengirim atau penerima tidak ditemukan.");
            System.out.println();
        }
    }

    private void menyimpanUang() {
        System.out.print(" Masukkan nomor akun Anda         : ");
        String nomorAkun = scanner.nextLine();
        BankAccount account = findAccountByNomorAkun(nomorAkun);
        if (account != null) {
            System.out.print(" Masukkan jumlah uang             : Rp. ");
            int jumlahUang = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            account.topUp(jumlahUang);
            System.out.println(" Deposit berhasil.");
            System.out.println();
            System.out.println("==========================================================");
            System.out.println("    Nomor akun                : " + account.getNomorAkun());
            System.out.println("    Saldo Anda                : Rp " + account.getSaldo());
            System.out.println("    Waktu transaksi           : " + LocalDateTime.now());
            System.out.println("==========================================================");
        } else {
            System.out.println();
            System.out.println(" Akun tidak ditemukan.");
        }
    }

    private void mengecekInformasiRekening() {
        System.out.print(" Masukkan nomor akun Anda         : ");
        String nomorAkun = scanner.nextLine();
        BankAccount account = findAccountByNomorAkun(nomorAkun);
        if (account != null) {
            account.showDescription();
        } else {
            System.out.println();
            System.out.println(" Akun tidak ditemukan.");
        }
    }

    private BankAccount findAccountByNomorAkun(String nomorAkun) {
        for (BankAccount account : accounts) {
            if (account.getNomorAkun().equals(nomorAkun)) {
                return account;
            }
        }
        return null;
    }
}

public class Bank {
    public static void main(String[] args) {
        try {
            BankingSystem bankingSystem = new BankingSystem();
            bankingSystem.showMenu();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
