import org.postgresql.ds.PGSimpleDataSource;

import java.io.IOException;
import java.sql.*;

public class Main {
    public static void main(String[] args) throws Exception {
        DataBase dataBase = new DataBase();

        //signup
//        try {
//            dataBase.signup("kimik", "kimia", "hosseini", "09374239758", "boz", "Kiarash1383", "C:\\Users\\Lenovo\\Desktop\\k.png");
//        }catch (Exception e){
//            System.out.println(e.getMessage());
//        }
//
//        try {
//            dataBase.signup("kiasoh", "kia", "soh", "09374239758", "boz", "Kiarash1383", "C:\\Users\\Lenovo\\Desktop\\k.png");
//        }catch (Exception e){
//            System.out.println(e.getMessage());
//        }
//        dataBase.seeAllUsers();
//        System.out.println();
//        System.out.println();
//        System.out.println();
//
//        //login
//        dataBase.printLogin("kimik", "Kiarash138");
//        System.out.println();
//        System.out.println();
//        System.out.println();
//
////        delete account
//        try {
//            dataBase.deleteAccount("kimik","Kiarash1383");
//        } catch (Exception e){
//            System.out.println(e.getMessage());
//        }
//        dataBase.seeAllUsers();
//        System.out.println();
//        System.out.println();
//        System.out.println();
//
////        change bio
//        try {
//            dataBase.changeBio("kimik", "new Biooooo");
//        } catch (Exception e){
//            System.out.println(e.getMessage());
//        }
//        dataBase.seeAllUsers();
//        System.out.println();
//        System.out.println();
//        System.out.println();
//
//        dataBase.addChat("chat1",null,"group");
//        dataBase.joinChat(1,"kiasoh",false);
//
//        dataBase.sendMessage(1,"kiasoh","C:\\Users\\Lenovo\\Desktop\\coverage.png","hello");
//        dataBase.printMessages("kiasoh");
//
//
//        dataBase.addPVChat("kimik","kiasoh",null);
//        dataBase.seeAllChats();
//
//        dataBase.editMessage(1,"bye");
//
//        dataBase.deleteMessage(1);
//
//        System.out.println(dataBase.getNumMessagesFromOneUser("kiasoh"));
//        dataBase.printMessages("kiasoh");
//
//        dataBase.joinChat(1,"kimik",false);
//
//        try {
//            dataBase.signup("kimikkkk", "kimia", "hosseini", "09374239758", "boz", "Kiarash1383", "C:\\Users\\Lenovo\\Desktop\\k.png");
//        }catch (Exception e){
//            System.out.println(e.getMessage());
//        }
//
//        dataBase.joinChat(1,"kimikkkk",false);
//        System.out.println(dataBase.numUsersHasRelationshipWithOneUser("kiasoh"));

//        dataBase.sendMessage(1,"kimikkkk",null,"lasmckfmek");
//        dataBase.setLastSeenMessage("kimikkkk",1,7);
        dataBase.setLastSeenMessage("kiasoh",1,6);
        System.out.println(dataBase.getViewersOfOneMessage(5));
//        System.out.println(dataBase.getAvgNumberMessagesOfOneUser("kiasoh"));
    }
}
