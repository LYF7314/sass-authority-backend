package edu.hitwh.utils;
import edu.hitwh.dto.InfoDTO;

public class InfoHolder {
    private static final ThreadLocal<InfoDTO> tl = new ThreadLocal<>();

    public static void saveInfo(InfoDTO user){
        tl.set(user);
    }

    public static InfoDTO getInfo(){
        return tl.get();
    }

    public static void removeInfo(){
        tl.remove();
    }
}
