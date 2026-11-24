/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Module;

/**
 *
 * @author caitlyn
 */

// Import.
import Money.Payment;

public class Bill {
    // Variables.
    private Reservation reservation;
    private int extra_fee;
    private boolean paid;
    
    // Constructor.
    public Bill(Reservation reservation, int extra_fee) {
        this.reservation = reservation;
        this.extra_fee = Math.max(extra_fee, 0);
        this.paid = false;
    }
    
    // Getter no Setter.
    public Reservation get_reservation() {
        return reservation;
    }

    public int get_extra_fee() {
        return extra_fee;
    }
    
    // Methods.
    public boolean is_paid() {
        return paid;
    }

    public int total() {
        return reservation.total_rent() + extra_fee;
    }

    public boolean pay(Payment p) {
        if (paid) {
            System.out.println("Lunas, dah sana pulang, punya rumah kan, kau?");
            return true;
        }
        
        if (p.pay(total())) {
            this.paid = true;
            return true;
            
        }
        return false;
    }
}