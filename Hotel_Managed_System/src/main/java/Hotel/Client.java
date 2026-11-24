/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Hotel;

/**
 *
 * @author caitlyn
 */

import java.time.LocalDate;
import Module.Room;
import Module.Reservation;
import Module.Reservation_Status;

public class Client extends User {
    protected String phone;
    
    
    public Client (String user_id, String name, String password, String phone) {
        super(user_id, name, password);
        this.phone = phone;
    }
    
    @Override
    public String role() {
        return "Client";
    }
    
    public Reservation reservating (LocalDate enter, LocalDate out, Room room) {
        if (!room.is_available(enter, out)) { // Kondisi kamar penuh.
            System.out.println("Kamar sedang tidak tersedia. Mohon maaf atas ketidaknyamanannya :(");
            return null;
        }
        
        // Kondisi kamar tersedia.
        String id_reservation = "RSV—" + System.currentTimeMillis();
        Reservation reservation = new Reservation (id_reservation, this, enter, out, Reservation_Status.ORDERED);
        reservation.add_item(room);
        return reservation;
    }
}