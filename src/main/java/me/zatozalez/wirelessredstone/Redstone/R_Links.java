package me.zatozalez.wirelessredstone.Redstone;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

public class R_Links {
    private static final LinkedHashMap<String, R_Link> links = new LinkedHashMap<>();
    private static final LinkedHashMap<String, R_Link> broken_links = new LinkedHashMap<>();

    public static LinkedHashMap<String, R_Link> getList(){
        return links;
    }
    public static List<R_Link> getList(UUID playerId){
        List<R_Link> list = new ArrayList<>();
        for(R_Link link : links.values())
            if(link.getPlayerId().equals(playerId) && link.isLinked())
                list.add(link);
        return list;
    }
    public static LinkedHashMap<String, R_Link> getBrokenList(){
        return broken_links;
    }

    public static R_Link add(R_Link link){
        add(false, link);
        return link;
    }
    public static R_Link add(boolean override, R_Link link){
        R_Link placeHolderLink = null;
        for(R_Link l : links.values())
            if (l.getId().equalsIgnoreCase(link.getId()))
                placeHolderLink = l;

        if(placeHolderLink != null)
            if (override)
            {
                links.remove(placeHolderLink);
                link = placeHolderLink;
            }

        links.put(link.getId(), link);
        return link;
    }
    public static R_Link get(String linkid){
        return links.get(linkid);
    }
    public static void remove(String deviceid){
        remove(get(deviceid));
    }
    public static void remove(R_Link link){
        if(link == null)
            return;
        links.remove(link.getId());
        broken_links.put(link.getId(), link);
    }
}
