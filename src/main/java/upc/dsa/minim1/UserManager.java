package upc.dsa.minim1;

import upc.dsa.minim1.exceptions.InterestPointNotFoundException;
import upc.dsa.minim1.exceptions.UserNotFoundException;
import upc.dsa.minim1.models.ElemType;
import upc.dsa.minim1.models.InterPoint;
import upc.dsa.minim1.models.User;
import upc.dsa.minim1.models.UserPasses;

import java.util.Date;
import java.util.List;

public interface UserManager {
    public User addUser(String id, String name, String sname, String mail, String date);
    public User addUser(String name, String sname, String mail, String date);
    public User addUser(User u);
    public User getUser(String id);
    public User getUser2(String id) throws UserNotFoundException;

    public InterPoint addIPoint(InterPoint ip);
    public InterPoint addIPoint(int x, int y, ElemType e);
    public InterPoint getIP(int x, int y);

    public UserPasses addUPP(UserPasses up) throws UserNotFoundException, InterestPointNotFoundException;
    public UserPasses addUPP(String id,int x,int y) throws UserNotFoundException, InterestPointNotFoundException;

//    Utility
    public List<User> findAllUsers();
    public List<User> UssersIP(InterPoint ip);
    public List<User> UssersIP(int x, int y, ElemType e);

    public List<InterPoint> UpassIP(User U);
    public List<InterPoint> UpassIP(String idU);
    public List<InterPoint> TypeIp(ElemType e);

    public void deleteUser(String id);
    public void deleteIPoint(int x, int y);
    public User updateUser(User u);

    public void clear();
    public int Usize();
    public int Psize();
    public int UPPsize();
}
