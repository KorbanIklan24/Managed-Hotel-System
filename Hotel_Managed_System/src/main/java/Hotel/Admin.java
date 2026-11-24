/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Hotel;

/**
 *
 * @author caitlyn
 */

// Imports.
import java.time.LocalDate;
import java.util.List;
import Module.Bill;
import Module.Reservation;
import Module.Reservation_Status;
import Module.Room;

public class Admin extends User {
    // Constructor.
    public Admin (String user_id, String name, String password) {
        super(user_id, name, password);
    }
    
    // Methods.
    @Override
    public String role() {
        return "Admin";
    }
    
    public void add_room(List<Room> rooms, Room r) {
        rooms.add(r);
        System.out.println("Kamar " + r.get_number() + " berhasil ditambahkan.");
    }
    
    public void change_price(Room r, int new_price) {
        r.change_price(new_price);
        System.out.println("Tarif kamar " + r.get_number() + " sekarang: Rp" + r.get_price_per_night());
    }

    public void create_receptionist(List<User> users, String id, String nama, String password) {
        users.add(new Receptionist(id, nama, password));
        System.out.println("Akun resepsionis " + nama + " [ID: " + id + "] berhasil dibuat.");
    }
    
    public String calculate_report(LocalDate date, List<Room> rooms, List<Reservation> reservations, List<Bill> bills) {
        int occupied_count = 0;
        for (Reservation r : reservations) {
            if (r.get_status() == Reservation_Status.CANCELLED) {
                continue;
            }
            // Dihitung aktif jika tanggal berada di rentang masuk <= date < keluar
            if (!date.isBefore(r.get_enter()) && date.isBefore(r.get_out())) {
                occupied_count += r.get_items().size();
            }
        }
        
        int total_revenue = 0;

        for (Bill b : bills) {
            if (b.is_paid()) {
                total_revenue += b.total();
            }
        }
        
        double occupancy_percent = rooms.isEmpty() ? 0 : (occupied_count * 100.0) / rooms.size();
        return "\n=== REKAP TANGGAL " + date + " ===\n"
                + "Kamar terpakai : " + occupied_count + " / " + rooms.size()
                + String.format(" (%.1f%%)\n", occupancy_percent)
                + "Pendapatan lunas: Rp" + total_revenue + "\n";
    }
}