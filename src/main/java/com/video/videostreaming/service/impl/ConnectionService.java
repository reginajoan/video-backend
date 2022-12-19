package com.video.videostreaming.service.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConnectionService {
    
    @Autowired
    private DataSource dataSource;

    private Connection connection(){
        try {
            return dataSource.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Map<String, String> view(){
        Map<String, String> listGenre = new HashMap<>();
        try{
            
            String Sql="SELECT * FROM genre";
            Statement stmt = connection().createStatement();
            ResultSet rs=stmt.executeQuery(Sql);
            while(rs.next()){
                //System.out.println(rs.getString("Salary"));
                listGenre.put(rs.getString("id"), rs.getString("genre_name"));
            }
        }catch(Exception e){
            System.err.println(e);
            return null;
        }
        return listGenre;
    }
}
