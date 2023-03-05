package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;

public class MainApp {
   public static void main(String[] args) throws SQLException {
      AnnotationConfigApplicationContext context = 
            new AnnotationConfigApplicationContext(AppConfig.class);

      UserService userService = context.getBean(UserService.class);

       userService.add(new User("Дмитрий", "Хаританович", "dima@gmail.com", new Car("Audi", 151478)));
       userService.add(new User("Наталья", "Сачко", "nata@gmail.com", new Car("Pegeot", 5878935)));
       userService.add(new User("Юлия", "Лучникова", "julia@gmail.com", new Car("Bently", 11478)));
       userService.add(new User("Ольга", "Сакель", "olsa@gmail.com", new Car("Infinity", 45789)));


       List<User> users = userService.listUsers();
       for (User user : users) {
           System.out.println("Id = " + user.getId());
           System.out.println("First Name = " + user.getFirstName());
           System.out.println("Last Name = " + user.getLastName());
           System.out.println("Email = " + user.getEmail());
           System.out.println("Car = " + user.getCar());
           System.out.println("________________________________________");

      }
       List<User> userList = userService.getUserByCarId("Audi", 151478);
       for (User user : userList) {
           System.out.println("Id " + user.getId());
           System.out.println("FirstName " + user.getFirstName());
           System.out.println("LastName " + user.getLastName());
           System.out.println("Email " + user.getEmail());
           System.out.println(user.getCar());
       }
       context.close();
   }
}
