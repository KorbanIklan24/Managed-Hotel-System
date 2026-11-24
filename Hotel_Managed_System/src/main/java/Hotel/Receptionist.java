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
import Module.Bill;
import Module.Reservation;
import Module.Reservation_Item;
import Module.Reservation_Status;
import Module.Room;
import Module.Room_Status;
import Money.Payment;

public class Receptionist extends User {
   // Constructor.
    public Receptionist(String user_id, String name, String password) {
        super(user_id, name, password);
    }
    
    // Methods.
    @Override
    public String role() {
        return "Receptionist";
    }

    public Reservation reservating(Client client, LocalDate enter, LocalDate out, Room room) {
        return client.reservating(enter, out, room);
    }
    
    public void check_in(Reservation r) {
        if (r.get_status() != Reservation_Status.ORDERED) {
            System.out.println("Order dulu, anjir!");
            return;
        }
        r.change_status(Reservation_Status.CHECKED_IN);
        for (Reservation_Item item : r.get_items()) {
            item.get_room().change_status(Room_Status.OCCUPIED);
        }
        System.out.println("Check-in berhasil, nih ID-nya [" + r.get_id() + "]");
    }
    
    public Bill check_out(Reservation r, int extra_fee) {
        if (r.get_status() != Reservation_Status.CHECKED_IN) {
            System.out.println("Check-out gagal: tamu belum check-in.");
            return null;
        }
        r.change_status(Reservation_Status.CHECKED_OUT);
        for (Reservation_Item item : r.get_items()) {
            item.get_room().change_status(Room_Status.AVAILABLE);
        }
        Bill bill = new Bill(r, extra_fee);
        System.out.println("Check-out berhasil. Tagihanmu segini: Rp" + bill.total() + ",00");
        return bill;
    }
    
    public boolean receive_payment(Bill b, Payment p) {
        return b.pay(p);
    }
}