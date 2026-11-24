/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Module;

/**
 *
 * @author caitlyn
 */
public class Reservation_Item {
    // Variables.
    private Room room;
    private Reservation reservation;
    private int price_at_booking;
    
    // Constructor.
    public Reservation_Item(Room room, Reservation reservation) {
        this.room = room;
        this.reservation = reservation;
        this.price_at_booking = room.get_price_per_night();
    }
    
    // Getter no Setter.
    public Room get_room() {
        return room;
    }

    public Reservation get_reservation() {
        return reservation;
    }

    public int get_price_at_booking() {
        return price_at_booking;
    }
    
    // Method.
    public int subtotal(int nights) { // Total Bill (Tagihan) per Kamar.
        return price_at_booking * nights;
    }
}