package upc.dsa.minim1;

import upc.dsa.minim1.exceptions.UserNotFoundException;
import upc.dsa.minim1.models.ElemType;
import upc.dsa.minim1.models.InterPoint;
import upc.dsa.minim1.models.User;

import java.util.Date;
import java.util.List;

public interface UserManager {
    public User addUser(String id, String name, String sname, String mail, Date date);
    public User addUser(String name, String sname, String mail, Date date);
    public User addUser(User u);
    public User getUser(String id);
    public User getUser2(String id) throws UserNotFoundException;

    public List<User> findAllUsers();
    public List<User> UssersIP(InterPoint ip);

    public List<InterPoint> UpassIP(User U);
    public List<InterPoint> TypeIp(ElemType e);
    public void deleteUser(String id);
    public User updateUser(User u);

    public void clear();
    public int Usize();
}
