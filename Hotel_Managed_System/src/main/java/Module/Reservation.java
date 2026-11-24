/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Module;

/**
 *
 * @author caitlyn
 */

// Imports.
import Hotel.Client;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Reservation {
    // Variables.
    private String id;
    private Client client;
    private LocalDate enter;
    private LocalDate out;
    private Reservation_Status status;
    private List <Reservation_Item> items = new ArrayList <> ();
    
    // Constructor.
    public Reservation(String id, Client client, LocalDate enter, LocalDate out, Reservation_Status status) {
        this.id = id;
        this.client = client;
        this.enter = enter;
        this.out = out;
        this.status = status;
    }
    
    // Getter no Setter.
    public String get_id() {
        return id;
    }

    public Client get_client() {
        return client;
    }

    public LocalDate get_enter() {
        return enter;
    }

    public LocalDate get_out() {
        return out;
    }

    public Reservation_Status get_status() {
        return status;
    }

    public List <Reservation_Item> get_items() {
        return items;
    }
    
    // Methods.
    public void add_item(Room room) {
        if (!room.is_available(enter, out)) {
            System.out.println("Kamar " + room.get_number() + " tidak tersedia pada tanggal tersebut.");
            return;
        }
        Reservation_Item item = new Reservation_Item(room, this);
        items.add(item);
        room.add_reservation_item(item);
        room.change_status(Room_Status.ORDERED);
    }

    public int nights() {
        int days = (int) ChronoUnit.DAYS.between(enter, out);
        return Math.max(days, 1);
    }

    public int total_rent() {
        // Local Variables.
        int total = 0;
        int total_nights = nights();
        
        for (Reservation_Item item : items) {
            total += item.subtotal(total_nights);
        }
        return total;
    }

    public void change_status(Reservation_Status status) {
        this.status = status;
    }
}