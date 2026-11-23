/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Money;

/**
 *
 * @author caitlyn
 */
public class Transfer_Payment implements Payment {
    private String reference_number;
    
    public Transfer_Payment (String reference_number) {
        this.reference_number = reference_number;
    }
    
    @Override
    public boolean pay (int amount) {
        if (amount <= 0 || reference_number == null || reference_number.trim().isEmpty()) {
            System.out.println ("Pembayaran Error—Kalau Nggak Ada Saldo Mending Cash Aja, Mas.");
            return false;
        }
        
        System.out.println("Transfer Sebesar Rp" + amount + ",00 Berhasil.");
        System.out.println("No Referensi: " + reference_number);
        return true;
    }
}