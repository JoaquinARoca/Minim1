package upc.dsa.minim1;

import org.apache.log4j.Logger;
import upc.dsa.minim1.exceptions.InterestPointNotFoundException;
import upc.dsa.minim1.exceptions.UserNotFoundException;
import upc.dsa.minim1.models.ElemType;
import upc.dsa.minim1.models.InterPoint;
import upc.dsa.minim1.models.User;
import upc.dsa.minim1.models.UserPasses;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class UserImplementation implements UserManager{
    private static UserManager instance;
    protected List<User> users;
    protected List<InterPoint> ips;
    protected List<UserPasses> ups;
    final static Logger logger = Logger.getLogger(UserImplementation.class);

    private void UsersManagerImpl() {
        this.users = new LinkedList<>();
    }

    public static UserManager getInstance() {
        if (instance==null) instance = new UserImplementation();
        return instance;
    }

    public int Usize() {
        int ret = this.users.size();
        logger.info("Users size " + ret);

        return ret;
    }
    public int Psize() {
        int ret = this.ips.size();
        logger.info("Interest Points size " + ret);

        return ret;
    }
    public int UPPsize() {
        int ret = this.ups.size();
        logger.info("Interest Points passes size " + ret);

        return ret;
    }

    public User addUser(User t) {
        logger.info("new User " + t);

        this.users.add (t);
        logger.info("new User added");
        return t;
    }

    public InterPoint addIPoint(InterPoint ip){
        logger.info("new interest point " + ip);
        this.ips.add (ip);
        logger.info("new interest point added");
        return ip;
    }

    private InterPoint buscaIP(UserPasses u){
        for (InterPoint ip : ips){
            if(u.getX() == ip.getX() && u.getY() == ip.getY() )
                return ip;
        }
        return null;
    }
    public UserPasses addUPP(UserPasses up) throws UserNotFoundException, InterestPointNotFoundException {
        logger.info("new User passes " + up);
        String idT = up.getIdU();
        if(idT == null) throw new UserNotFoundException();
        boolean encontrado = false;

        if(encontrado==false) throw new InterestPointNotFoundException();
        for (InterPoint ip : ips){
            if(up.getX() == ip.getX() && up.getY() == ip.getY() )
                encontrado = true;
        }
        this.ups.add (up);
        logger.info("new User pass inter point added");
        return up;
    }

    public User addUser(String name, String sname, String mail, Date date){
        return this.addUser(null, name, sname,mail, date);
    }

    public User addUser(String id, String name, String sname, String mail, Date date) {
        return this.addUser(new User(id,  name, sname,mail, date));
    }

    public InterPoint addIPoint( int x, int y, ElemType e){
        return this.addIPoint(new InterPoint(x,y,e));
    }
    public User getUser(String id) {
        logger.info("getUser("+id+")");

        for (User t: this.users) {
            if (t.getIdU().equals(id)) {
                logger.info("getUser("+id+"): "+t);

                return t;
            }
        }

        logger.warn("not found " + id);
        return null;
    }

    public User getUser2(String id) throws UserNotFoundException {
        User t = getUser(id);
        if (t == null) throw new UserNotFoundException();
        return t;
    }

    public List<User> findAllUsers() {
        return this.users;
    }

    public List<InterPoint> UpassIP(User u){
        List<InterPoint> uip = new LinkedList<>();
        for(UserPasses up : ups ){
            if(up.getIdU().equals(u.getIdU()))
                uip.add(buscaIP(up));
        }

        return uip;
    }

    private User buscaUs(String idU){
        for(User u : users){
            if(u.getIdU().equals(idU))
                return u;
        }
        return null;
    }
    public List<User> UssersIP(InterPoint ip){
        List<User> us= new LinkedList<>();
        for(UserPasses up : ups){
            if(ip.equals(buscaIP(up)))
                us.add(buscaUs(up.getIdU()));
        }
        return us;
    }

    public List<InterPoint> TypeIp(ElemType type){
        List<InterPoint> uip = new LinkedList<>();
        for(InterPoint ip : ips){
            if(ip.getE().equals(type))
                uip.add(ip);
        }

        return uip;
    }
    @Override
    public void deleteUser(String id) {

        User t = this.getUser(id);
        if (t==null) {
            logger.warn("not found " + t);
        }
        else logger.info(t+" deleted ");

        this.users.remove(t);

    }

    @Override
    public User updateUser(User p) {
        User t = this.getUser(p.getIdU());

        if (t!=null) {
            logger.info(p+" rebut!!!! ");

            t.setName(p.getName());
            t.setSname(p.getSname());
            t.setMail(p.getMail());
            t.setDate(p.getDate());
            logger.info(t+" updated ");
        }
        else {
            logger.warn("not found "+p);
        }

        return t;
    }

    public void clear() {
        this.users.clear();
    }
}
