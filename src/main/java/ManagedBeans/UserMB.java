/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ManagedBeans;

import entities.Korisnik;
import java.io.Serializable;
import static java.lang.Math.random;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.annotation.FacesConfig;
import javax.servlet.http.HttpSession;
import util.SessionUtils;
import util.dao.KorisnikDAO;

/**
 *
 * @author pc
 */
@Named(value = "userMB")
@SessionScoped
@FacesConfig(version = FacesConfig.Version.JSF_2_3)
public class UserMB implements Serializable{

    /**
     * Creates a new instance of UserMBB
     */
    public UserMB() {
    }
    
    
    private Korisnik korisnik = new Korisnik();
    private boolean b=false;
    private int iznos=0;
    
    public String login() {
        korisnik = new KorisnikDAO().login(korisnik);
        
        if(korisnik.isGreska()) {
            return "";
        } 
        
        else {
            HttpSession session = SessionUtils.getSession();
            session.setAttribute("korisnik", korisnik);
            return korisnik.getTip();
        }
    }
    
    public String logout(){
        HttpSession session = SessionUtils.getSession();
        session.invalidate();
        korisnik = new Korisnik();
        return "index";
    }
    
    public String registracija() {
        new KorisnikDAO().registracija(korisnik);
        
        if(korisnik.isGreska()) {
            return "";
        }
        
        else {
            return "index";
        }
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    public boolean isB() {
        return b;
    }

    public void setB(boolean b) {
        this.b = b;
    }

    public int getIznos() {
        return iznos;
    }

    public void setIznos(int iznos) {
        this.iznos = iznos;
    }
    
    
    public int r(){
        return (int)(random()*((96-16)+1))+16;
    }
    
    public void uplata(){
        new KorisnikDAO().uplata(korisnik.getUsername(), iznos);
    }
    
}
