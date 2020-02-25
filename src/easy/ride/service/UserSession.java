/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easy.ride.service;

/**
 *
 * @author ASUS
 */
public final class UserSession {

    private static UserSession instance;

    private String userName;
    private String privileges;
    private int id;

    private UserSession(String userName, String privileges,int id) {
        this.userName = userName;
        this.privileges = privileges;
        this.id=id;
        
    }

    public static UserSession getInstace(String userName, String privileges,int id) {
        if(instance == null) {
            instance = new UserSession(userName, privileges,id);
        }
        return instance;
    }

    public String getUserName() {
        return userName;
    }

    public String getPrivileges() {
        return privileges;
    }

    public int getId() {
        return id;
    }
    
    
    public void cleanUserSession() {
        userName = "";// or null
        privileges = "";// or null
    }

    @Override
    public String toString() {
        return "UserSession{" +
                "userName='" + userName + '\'' +
                ", privileges=" + privileges +
                '}';
    }
}