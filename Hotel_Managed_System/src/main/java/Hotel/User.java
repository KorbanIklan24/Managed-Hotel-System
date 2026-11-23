/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Hotel;

/**
 *
 * @author caitlyn
 */
public abstract class User {
    protected String user_id;
    protected String name;
    private String password;
    
    
    public User (String user_id, String name, String password) {
        this.user_id = user_id;
        this.name = name;
        this.password = password;
    }
    
    public String get_user_id () {
        return user_id;
    }
    
    public String get_name () {
        return name;
    }
    
    public boolean login (String user_id, String password) {
        return this.user_id != null && this.user_id.equals(user_id) // Simbol "&&" adalah "and" atau "dan"
            && this.password != null && this.password.equals(password);
    }
    
    public abstract String role ();
}