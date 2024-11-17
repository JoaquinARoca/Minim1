package upc.dsa.minim1;

import org.apache.log4j.Logger;
import upc.dsa.minim1.exceptions.InterestPointNotFoundException;
import upc.dsa.minim1.exceptions.UserNotFoundException;
import upc.dsa.minim1.models.ElemType;
import upc.dsa.minim1.models.InterPoint;
import upc.dsa.minim1.models.User;
import upc.dsa.minim1.models.UserPasses;

import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class UserImplementation implements UserManager{
    private static UserManager instance;
    protected List<User> users;
    protected List<InterPoint> ips;
    protected List<UserPasses> ups;
    final static Logger logger = Logger.getLogger(UserImplementation.class);

    // Constructor corregido
    public UserImplementation() {
        this.users = new LinkedList<>();
        this.ips = new LinkedList<>();
        this.ups = new LinkedList<>();
    }

    public static UserManager getInstance() {
        if (instance==null) instance = new UserImplementation();
        return instance;
    }

    public int Usize() {
        if (this.users == null) {
            return 0; // Si `users` es nulo, retornamos 0 como tamaño
        }
        int ret =this.users.size();
        logger.info("Users size " + ret);
        return ret;
    }
    public int Psize() {
        if (this.ips == null) {
            return 0; // Si `users` es nulo, retornamos 0 como tamaño
        }
        int ret = this.ips.size();
        logger.info("Interest Points size " + ret);

        return ret;
    }
    public int UPPsize() {
        if (this.ups == null) {
            return 0; // Si `users` es nulo, retornamos 0 como tamaño
        }
        int ret = this.ups.size();
        logger.info("Interest Points passes size " + ret);

        return ret;
    }

    @Override
    public User addUser(User t) {
        logger.info("new User " + t);

        this.users.add (t);
        logger.info("new User added");
        return t;
    }

    @Override
    public InterPoint addIPoint(InterPoint ip){
        logger.info("new interest point " + ip);
        this.ips.add (ip);
        logger.info("new interest point added");
        return ip;
    }

    @Override
    public UserPasses addUPP(UserPasses up) throws UserNotFoundException, InterestPointNotFoundException {
        logger.info("new User passes " + up);
        String idT = up.getIdU();
        if(idT == null || this.buscaUs(idT)== null) throw new UserNotFoundException();
        boolean encontrado = false;
        for (InterPoint ip : ips){
            if (up.getX() == ip.getX() && up.getY() == ip.getY()) {
                encontrado = true;
                break;
            }
        }
        if(!encontrado) throw new InterestPointNotFoundException();
        this.ups.add (up);
        logger.info("new User pass inter point added");
        return up;
    }

    @Override
    public User addUser(String name, String sname, String mail, String date){
        return this.addUser(null, name, sname, mail, date);
    }

    @Override
    public User addUser(String id, String name, String sname, String mail, String date) {
        return this.addUser(new User(id, name, sname, mail, date));
    }

    @Override
    public InterPoint addIPoint(int x, int y, ElemType e){
        return this.addIPoint(new InterPoint(x, y, e));
    }

    @Override
    public UserPasses addUPP(String idT, int x, int y) throws UserNotFoundException, InterestPointNotFoundException{
        if(idT == null || this.buscaUs(idT)== null) throw new UserNotFoundException();
        boolean encontrado = false;
        for (InterPoint ip : ips){
            if (x == ip.getX() && y == ip.getY()) {
                encontrado = true;
                break;
            }
        }
        if(!encontrado) throw new InterestPointNotFoundException();
        UserPasses up = new UserPasses(idT, x, y);
        this.ups.add(up);
        logger.info("new User pass inter point added");
        return up;
    }

    @Override
    public User getUser(String id) {
        logger.info("getUser(" + id + ")");

        for (User t: this.users) {
            if (t.getIdU().equals(id)) {
                logger.info("getUser(" + id + "): " + t);

                return t;
            }
        }

        logger.warn("not found " + id);
        return null;
    }

    @Override
    public User getUser2(String id) throws UserNotFoundException {
        User t = getUser(id);
        if (t == null) throw new UserNotFoundException();
        return t;
    }

    @Override
    public InterPoint getIP(int x, int y){
        InterPoint IP = buscaIP(new UserPasses("1000000", x, y));
        return IP;
    }

    @Override
    public List<User> findAllUsers() {
        return this.users.stream()
                .sorted(Comparator.comparing(User::getSname).thenComparing(User::getName))
                .collect(Collectors.toList());
    }

    private InterPoint buscaIP(UserPasses u){
        for (InterPoint ip: ips){
            if (u.getX() == ip.getX() && u.getY() == ip.getY())
                return ip;
        }
        return null;
    }

    @Override
    public List<InterPoint> UpassIP(User u){
        List<InterPoint> uip = new LinkedList<>();
        for(UserPasses up: ups){
            if(up.getIdU().equals(u.getIdU()))
                uip.add(buscaIP(up));
        }

        return uip;
    }
    @Override
    public List<InterPoint> UpassIP(String idU){
        List<InterPoint> uip = new LinkedList<>();
        for(UserPasses up: ups){
            if(up.getIdU().equals(idU))
                uip.add(buscaIP(up));
        }

        return uip;
    }

    private User buscaUs(String idU){
        for(User u: users){
            if(u.getIdU().equals(idU))
                return u;
        }
        return null;
    }
    @Override
    public List<User> UssersIP(InterPoint ip){
        List<User> us= new LinkedList<>();
        for(UserPasses up: ups){
            if(ip.equals(buscaIP(up)))
                us.add(buscaUs(up.getIdU()));
        }
        return us;
    }
    @Override
    public List<User> UssersIP(int x, int y, ElemType t){
        return UssersIP(new InterPoint(x, y, t));
    }
    @Override
    public List<InterPoint> TypeIp(ElemType type){
        List<InterPoint> uip = new LinkedList<>();
        for(InterPoint ip: ips){
            if(ip.getE().equals(type))
                uip.add(ip);
        }

        return uip;
    }
    @Override
    public void deleteUser(String id) {

        User t = this.getUser(id);
        if (t == null) {
            logger.warn("not found " + id);
        }
        else logger.info(t + " deleted ");

        this.users.remove(t);

    }
    @Override
    public void deleteIPoint(int x, int y) {
        InterPoint t = getIP(x, y);
        if (t == null) {
            logger.warn("not found " + x + " , " + y);
        }
        else logger.info(t.toString() + " deleted ");

        this.ips.remove(t);
    }

    @Override
    public User updateUser(User p) {
        User t = this.getUser(p.getIdU());

        if (t != null) {
            logger.info(p + " actualizar!!!! ");

            t.setName(p.getName());
            t.setSname(p.getSname());
            t.setMail(p.getMail());
            t.setDate(p.getDate());
            logger.info(t + " actualizado ");
        }
        else {
            logger.warn("no encontrado " + p);
        }

        return t;
    }

    @Override
    public void clear() {
        this.users.clear();
        this.ips.clear();
        this.ups.clear();
    }
}