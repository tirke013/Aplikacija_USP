/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.dao;

import entities.Korisnik;
import entities.Putnik;
import entities.Vozac;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.DB;

/**
 *
 * @author pc
 */
public class KorisnikDAO {
    
    public Korisnik login(Korisnik k) {
        
        
        
        if("vozac".equals(k.getTip())) {
            Connection conn = DB.getInstance().getConnection();
            String query = "select * from vozaci where username = ? and password = ?";
            
            try(PreparedStatement ts = conn.prepareStatement(query)) {
                ts.setString(1, k.getUsername());
                ts.setString(2, k.getPassword());
                
                ResultSet rs = ts.executeQuery();
                
                if(rs.next()) {
                    Vozac v = new Vozac();
                    v.setTip("vozac");
                    v.setUsername(rs.getString("username"));
                    v.setPassword(rs.getString("password"));
                    v.setRuta(rs.getInt("ruta"));
                    
                    return v;
                }
                else {
                    k.setGreska(true);
                    return k;
                }
            } catch (SQLException ex) {
                Logger.getLogger(KorisnikDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            finally{
                DB.getInstance().putConnection(conn);
            }
        }
        
        else {
            Connection conn = DB.getInstance().getConnection();
            String query = "select * from putnici where username = ? and password = ?";
            
            System.out.println("\n\n\n\n\n");
            System.out.println("dzi");
            System.out.println(conn);
            System.out.println("\n\n\n\n\n");
            
            try(PreparedStatement ts = conn.prepareStatement(query)) {
                ts.setString(1, k.getUsername());
                ts.setString(2, k.getPassword());
                
                ResultSet rs = ts.executeQuery();
                
                if(rs.next()) {
                    Putnik p = new Putnik();
                    p.setTip("putnik");
                    p.setUsername(rs.getString("username"));
                    p.setPassword(rs.getString("password"));
                    p.setKredit(rs.getInt("kredit"));
                    
                    return p;
                }
                else {
                    k.setGreska(true);
                    return k;
                }
            } catch (SQLException ex) {
                Logger.getLogger(KorisnikDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            finally{
                DB.getInstance().putConnection(conn);
            }
            
        }
        
        System.out.println("Bila je greska \n");
        return k;
    }
    
    public void registracija(Korisnik k) {
        Connection conn = DB.getInstance().getConnection();
        
        String test = "Select * from putnici where username = ?";
        String query = "insert into putnici values (?, ?, 0)";
        
        try(PreparedStatement ps = conn.prepareStatement(test)) {
            ps.setString(1, k.getUsername());
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()) { //Vec postoji korisnik sa datim username-om
                k.setGreska(true);
                return;
            }
           
        } catch (SQLException ex) {
            Logger.getLogger(KorisnikDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            DB.getInstance().putConnection(conn);
        }
        
        
        try(PreparedStatement ps = conn.prepareStatement(query)) {
            k.setGreska(false);
            ps.setString(1, k.getUsername());
            ps.setString(2, k.getPassword());
            
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(KorisnikDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            DB.getInstance().putConnection(conn);
        }
    }
    
    public void uplata(String u, int iznos){
        Connection conn = DB.getInstance().getConnection();
        try(PreparedStatement stmt = conn.prepareStatement("update putnici set kredit=kredit+? where username=?")){
            stmt.setInt(1, iznos);
            stmt.setString(2, u);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(KorisnikDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            DB.getInstance().putConnection(conn);
        }
    }
}
