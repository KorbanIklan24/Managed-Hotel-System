/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Hotel;

/**
 *
 * @author caitlyn
 */

// Imports.
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import Module.Bill;
import Module.Reservation;
import Module.Room;
import Module.Room_Type;
import Money.Cash_Payment;
import Money.Payment;
import Money.Transfer_Payment;

public class Main {

    /**
     * @param args the command line arguments
     */
    
    // Variables.
    private static List<User> users = new ArrayList<>();
    private static List<Room> rooms = new ArrayList<>();
    private static List<Reservation> reservations = new ArrayList<>();
    private static List<Bill> bills = new ArrayList<>();
    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        init_dummy_data();
        
        // Landing Page / Main Menu.
        while (true) {
            System.out.println("\n===> [SISTEM MANAJEMEN HOTEL] <===");
            System.out.println("1. Sign-In :)");
            System.out.println("2. Sign-Up :)");
            System.out.println("0. KELUAR!!!");
            int choice = read_int(input, "Mau Mana, Kau? Pilih! ");

            if (choice == 0) {
                System.out.println("Program Ditutup. Dah, Sana Pergi!");
                break;
            
            } else if (choice == 1) {
                User active_user = login_process(input);
                if (active_user != null) {
                    if (active_user.role().equals("Client")) {
                        menu_client(input, (Client) active_user);
                    } else if (active_user.role().equals("Receptionist")) {
                        menu_receptionist(input, (Receptionist) active_user);
                    } else if (active_user.role().equals("Admin")) {
                        menu_admin(input, (Admin) active_user);
                    }
                }
            
            } else if (choice == 2) {
                register_client(input);
            }
        }
        input.close();
    }

    // Data awal untuk memudahkan demo.
    private static void init_dummy_data() {
        users.add(new Client("T01", "Patrick Star", "123", "08123456789"));
        
        users.add(new Receptionist("R01", "Meila", "123"));
        users.add(new Receptionist("R02", "Squidward Tenpoles", "123"));
        
        users.add(new Admin("A01", "Dimas", "123"));
        users.add(new Admin("A02", "Eugene Krabs", "123"));

        rooms.add(new Room("101", Room_Type.STANDARD, 300000));
        rooms.add(new Room("102", Room_Type.DELUXE, 450000));
        rooms.add(new Room("201", Room_Type.SUITE, 750000));
    }

    private static User login_process(Scanner input) {
        System.out.print("User ID  : ");
        String id = input.nextLine();
        System.out.print("Password : ");
        String pass = input.nextLine();

        for (User u : users) {
            if (u.login(id, pass)) {
                System.out.println("Login berhasil! " + u.get_name() + " Iya, kan? Dengan posisi sebagai ... " + u.role() + ". Oke.");
                return u;
            }
        }
        System.out.println("Login gagal: ID atau password salah. Ingat, nggak? Kalau nggak ya udah.");
        return null;
    }
    
    private static void register_client(Scanner input) {
        System.out.println("\n--- DAFTAR AKUN TAMU ---");
        System.out.print("Buat User ID : ");
        String id = input.nextLine().trim();

        for (User u : users) {
            if (u.get_user_id().equals(id)) {
                System.out.println("ID sudah dipakai. Pakai ID lain!");
                return;
        }
    }

    System.out.print("Nama         : ");
    String name = input.nextLine().trim();
    System.out.print("Password     : ");
    String pass = input.nextLine().trim();
    System.out.print("No. Telepon  : ");
    String phone = input.nextLine().trim();

    users.add(new Client(id, name, pass, phone));
    System.out.println("Akun berhasil dibuat! Silakan login pakai ID: " + id);
}

    // ==================================== MENU TAMU ========================================
    private static void menu_client(Scanner input, Client client) {
        while (true) {
            System.out.println("\n--- MENU TAMU (" + client.get_name() + ") ---");
            System.out.println("1. Lihat Kamar Tersedia");
            System.out.println("2. Buat Reservasi");
            System.out.println("3. Bayar Hutang");
            System.out.println("0. Logout");
            int choice = read_int(input, "Pilih, cepat! ");

            if (choice == 0) {
                break;
            } else if (choice == 1) {
                LocalDate in = read_date(input, "Tanggal Masuk (YYYY-MM-DD): ");
                LocalDate out = read_date(input, "Tanggal Keluar (YYYY-MM-DD): ");
                show_available_room(in, out);
            } else if (choice == 2) {
                interactive_reservate(input, client, null);
            } else if (choice == 3) {
                bill_payment_process(input, null);
            }
        }
    }

    // ====================================== MENU RESEPSIONIS ===================================
    private static void menu_receptionist(Scanner input, Receptionist r) {
        while (true) {
            System.out.println("\n--- MENU RESEPSIONIS (" + r.get_name() + ") ---");
            System.out.println("1. Lihat Kamar Tersedia");
            System.out.println("2. Buat Reservasi Untuk Tamu");
            System.out.println("3. Check-in");
            System.out.println("4. Check-out & Tunjukkan Hutang");
            System.out.println("5. Terima pembayaran");
            System.out.println("0. Logout");
            int choice = read_int(input, "Cepat, Pilih! ");

            if (choice == 0) {
                break;
            } else if (choice == 1) {
                LocalDate in = read_date(input, "Tanggal Masuk (YYYY-MM-DD): ");
                LocalDate out = read_date(input, "Tanggal Keluar (YYYY-MM-DD): ");
                show_available_room(in, out);
            } else if (choice == 2) {
                System.out.print("Masukkan ID Tamu: ");
                String client_id = input.nextLine();
                Client client = find_client(client_id);
                if (client == null) {
                    System.out.println("Tamu Nggak Ada.");
                } else {
                    interactive_reservate(input, client, r);
                }
            } else if (choice == 3) {
                Reservation res = select_reservation(input);
                if (res != null) {
                    r.check_in(res);
                }
            } else if (choice == 4) {
                Reservation res = select_reservation(input);
                if (res != null) {
                    int extra_fee = read_int(input, "Biaya Tambahan (Kalau nggak ada, dikarang aja. Anggap aja tips.): ");
                    Bill bill = r.check_out(res, extra_fee);
                    if (bill != null) {
                        bills.add(bill);
                    }
                }
            } else if (choice == 5) {
                bill_payment_process(input, r);
            }
        }
    }

    // ================================ MENU ADMIN ====================================
    private static void menu_admin(Scanner input, Admin admin) {
        while (true) {
            System.out.println("\n--- MENU ADMIN (" + admin.get_name() + ") ---");
            System.out.println("1. Tambah Kamar");
            System.out.println("2. Ubah Tarif Kamar");
            System.out.println("3. Buat Akun Resepsionis");
            System.out.println("4. Lihat Rekap Okupansi & Pendapatan");
            System.out.println("0. Logout");
            int choice = read_int(input, "Pilih: ");

            if (choice == 0) {
                break;
            } else if (choice == 1) {
                System.out.print("Nomor Kamar: ");
                String number = input.nextLine();
                System.out.println("Tipe: 1. STANDARD | 2. DELUXE | 3. SUITE");
                int t = read_int(input, "Pilih tipe: ");
                Room_Type type = (t == 2) ? Room_Type.DELUXE : (t == 3 ? Room_Type.SUITE : Room_Type.STANDARD);
                int price = read_int(input, "Tarif per Malam: ");
                admin.add_room(rooms, new Room(number, type, price));
            } else if (choice == 2) {
                Room room = select_room(input);
                if (room != null) {
                    int new_price = read_int(input, "Tarif Baru: ");
                    admin.change_price(room, new_price);
                }
            } else if (choice == 3) {
                System.out.print("ID Resepsionis Baru: ");
                String id = input.nextLine();
                System.out.print("Nama: ");
                String name = input.nextLine();
                System.out.print("Password: ");
                String pass = input.nextLine();
                admin.create_receptionist(users, id, name, pass);
            } else if (choice == 4) {
                LocalDate date = read_date(input, "Tanggal Rekap (YYYY-MM-DD): ");
                System.out.println(admin.calculate_report(date, rooms, reservations, bills));
            }
        }
    }

    // =================================== HELPER OPERASIONAL ==================================
    private static void show_available_room(LocalDate in, LocalDate out) {
        System.out.println("\nDaftar Kamar Tersedia (" + in + " s/d " + out + "):");
        for (Room room : rooms) {
            if (room.is_available(in, out)) {
                System.out.println("- Kamar " + room.get_number() + " | " + room.get_type()
                        + " | Rp" + room.get_price_per_night() + "/malam");
            }
        }
    }

    private static void interactive_reservate(Scanner input, Client client, Receptionist receptionist) {
        LocalDate in = read_date(input, "Tanggal Masuk (YYYY-MM-DD): ");
        LocalDate out = read_date(input, "Tanggal Keluar (YYYY-MM-DD): ");
        show_available_room(in, out);

        Room first_room = select_room(input);
        if (first_room == null) {
            return;
        }

        Reservation res = (receptionist != null)
                ? receptionist.reservating(client, in, out, first_room)
                : client.reservating(in, out, first_room);

        if (res == null) {
            return;
        }

        // Opsi tambah kamar lain dalam reservasi yang sama (KF-02).
        while (true) {
            System.out.print("Tambah Kamar Lain ke Reservasi Ini? (y/n): ");
            String more = input.nextLine();
            if (!more.equalsIgnoreCase("y")) {
                break;
            }
            Room extra_room = select_room(input);
            if (extra_room != null) {
                res.add_item(extra_room);
            }
        }

        reservations.add(res);
        System.out.println("Reservasi Tersimpan! ID: " + res.get_id()
                + " | Lama: " + res.nights() + " malam | Total sewa: Rp" + res.total_rent());
    }

    private static void bill_payment_process(Scanner input, Receptionist receptionist) {
        if (bills.isEmpty()) {
            System.out.println("Belum ada hutang. Check-out dulu sana!");
            return;
        }
        System.out.println("\nDaftar Hutang:");
        for (int i = 0; i < bills.size(); i++) {
            Bill b = bills.get(i);
            System.out.println((i + 1) + ". Reservasi " + b.get_reservation().get_id()
                    + " | Total: Rp" + b.total() + " | Status: " + (b.is_paid() ? "LUNAS" : "BELUM LUNAS"));
        }
        int idx = read_int(input, "Pilih nomor tagihan: ") - 1;
        if (idx < 0 || idx >= bills.size()) {
            System.out.println("Pilih yang Benar!");
            return;
        }
        Bill target = bills.get(idx);

        System.out.println("Metode Pembayaran: 1. Tunai | 2. Transfer");
        int method = read_int(input, "Pilih Metode: ");
        Payment payment;
        if (method == 2) {
            System.out.print("Masukkan Bukti (Nomor Referensi Transfer): ");
            String ref = input.nextLine();
            payment = new Transfer_Payment(ref);
        } else {
            payment = new Cash_Payment();
        }

        if (receptionist != null) {
            receptionist.receive_payment(target, payment);
        } else {
            target.pay(payment);
        }
    }

    private static Client find_client(String id) {
        for (User u : users) {
            if (u instanceof Client && u.get_user_id().equals(id)) {
                return (Client) u;
            }
        }
        return null;
    }

    private static Room select_room(Scanner input) {
        System.out.print("Masukkan Nomor Kamar: ");
        String number = input.nextLine();
        for (Room r : rooms) {
            if (r.get_number().equals(number)) {
                return r;
            }
        }
        System.out.println("Kamar nggak ada, jangan ngarang!");
        return null;
    }

    private static Reservation select_reservation(Scanner input) {
        if (reservations.isEmpty()) {
            System.out.println("Belum ada data reservasi.");
            return null;
        }
        System.out.println("\nDaftar Reservasi:");
        for (int i = 0; i < reservations.size(); i++) {
            Reservation r = reservations.get(i);
            System.out.println((i + 1) + ". " + r.get_id() + " | Tamu: " + r.get_client().get_name()
                    + " | " + r.get_enter() + " s/d " + r.get_out() + " | Status: " + r.get_status());
        }
        int idx = read_int(input, "Pilih Nomor Urut Reservasi: ") - 1;
        if (idx < 0 || idx >= reservations.size()) {
            System.out.println("Pilih yang Benar!");
            return null;
        }
        return reservations.get(idx);
    }

    private static int read_int(Scanner input, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(input.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Input harus angka!");
            }
        }
    }

    private static LocalDate read_date(Scanner input, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return LocalDate.parse(input.nextLine().trim());
            } catch (DateTimeParseException e) {
                System.out.println("Format tanggal salah! Pake YYYY-MM-DD. Paham!");
            }
        }
    }
}