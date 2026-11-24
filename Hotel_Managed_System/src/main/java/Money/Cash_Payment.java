/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Money;

/**
 *
 * @author caitlyn
 */
public class Cash_Payment implements Payment {
    @Override
    public boolean pay(int amount) {
        if (amount <= 0) { // Uangnya nggak ada atau malah minus.
            System.out.println("Bayar, Anjing!");
            return false;
        }
        // Uangnya ada.
        System.out.println("Pembayaran Tunai Sebesar Rp" + amount + ",00 Telah Berhasil.");
        return true;
    }
}