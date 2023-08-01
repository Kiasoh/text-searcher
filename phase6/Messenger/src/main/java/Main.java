import org.postgresql.ds.PGSimpleDataSource;

import java.io.IOException;
import java.sql.*;

public class Main {
    public static void main(String[] args) throws Exception {
        DataBase dataBase = new DataBase();

        //signup
        try {
            dataBase.signup("user1", "kimia", "hosseini", "09374239758", "boz", "Kiarash1383", "C:\\Users\\Lenovo\\Desktop\\k.png");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        try {
            dataBase.signup("user2", "kia", "soh", "09374239758", "boz", "Kiarash1383", "C:\\Users\\Lenovo\\Desktop\\k.png");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        try {
            dataBase.signup("user3", "kimia", "hosseini", "09374239758", "boz", "Kiarash1383", "C:\\Users\\Lenovo\\Desktop\\k.png");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        try {
            dataBase.signup("user4", "kimia", "hosseini", "09374239758", "boz", "Kiarash1383", "C:\\Users\\Lenovo\\Desktop\\k.png");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        dataBase.seeAllUsers();
        System.out.println();
        System.out.println();
        System.out.println();


        //login
        //wrong pass
        dataBase.printLogin("user1", "Kiarash138");
        //correct pass
        dataBase.printLogin("user1", "Kiarash1383");
        System.out.println();
        System.out.println();
        System.out.println();

        //delete account
        try {
            dataBase.deleteAccount("user4","Kiarash1383");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        dataBase.seeAllUsers();
        System.out.println();
        System.out.println();
        System.out.println();

        //change bio
        try {
            dataBase.changeBio("user1", "new Bio");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        dataBase.seeAllUsers();
        System.out.println();
        System.out.println();
        System.out.println();

        //create group
        dataBase.addChat("chat1",null,"group");

        //create pv
        dataBase.addPVChat("user1","user2",null);

        //print all chats
        dataBase.seeAllChats();

        //join to group
        dataBase.joinChat(1,"user2",false);

        //send message to group
        dataBase.sendMessage(1,"user2","C:\\Users\\Lenovo\\Desktop\\coverage.png","hello");

        //print messages of a user
        dataBase.printMessages("user2");

        //edit a message
        dataBase.editMessage(1,"bye");

        //delete a message
        dataBase.deleteMessage(1);

        dataBase.printMessages("user2");

        //print number of messages of a user
        System.out.println(dataBase.getNumMessagesFromOneUser("user3"));


        dataBase.joinChat(1,"user1",false);

        dataBase.joinChat(1,"user3",false);

        System.out.println(dataBase.numUsersHasRelationshipWithOneUser("user2"));

        System.out.println(dataBase.getAvgNumberMessagesOfOneUser("user3"));
        dataBase.sendMessage(1,"user3",null,"msg msg msg");

        dataBase.setLastSeenMessage("user3",1,7);
        dataBase.setLastSeenMessage("user2",1,6);
        System.out.println(dataBase.getViewersOfOneMessage(5));
    }
}
