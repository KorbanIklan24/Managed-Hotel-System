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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Room {
    // Variables.
    private String number;
    private Room_Type type;
    private int price_per_night;
    private Room_Status status;
    private List <Reservation_Item> items = new ArrayList <> ();
    
    // Constructor.
    public Room (String number, Room_Type type, int price_per_night) {
        this.number = number;
        this.type = type;
        this.price_per_night = price_per_night;
        this.status = Room_Status.AVAILABLE;
    }
    
    // Getter no Setter.
    public String get_number() {
        return number;
    }

    public Room_Type get_type() {
        return type;
    }

    public int get_price_per_night() {
        return price_per_night;
    }

    public Room_Status get_status() {
        return status;
    }
    
    // Methods.
    public void add_reservation_item(Reservation_Item item) {
        items.add(item);
    }

    public boolean is_available(LocalDate enter, LocalDate out) {
        if (enter == null || out == null || !out.isAfter(enter)) {
            return false;
        }
        
        for (Reservation_Item item : items) {
            Reservation r = item.get_reservation();
            if (r.get_status() == Reservation_Status.CANCELLED
                    || r.get_status() == Reservation_Status.CHECKED_OUT) {
                continue;
            }
            
            // Cek apakah rentang tanggal bertabrakan.
            boolean overlap = enter.isBefore(r.get_out()) && out.isAfter(r.get_enter());
            if (overlap) {
                return false;
            }
        }
        return true;
    }
    
    public void change_status(Room_Status status) {
        this.status = status;
    }
    
    public void change_price(int new_price) {
        if (new_price > 0) {
            this.price_per_night = new_price;
        } else {
            System.out.println("Nggak bisa, woy! Kau mau kerja bakti?");
        }
    }
}