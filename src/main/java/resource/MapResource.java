package resource;

import base.Resource;

public class MapResource implements Resource {
    MapResource(){

    }

    public MapResource(int width,int height, String name){
        this.width  = width;
        this.height = height;
        this.name   = name;

    }

    public int    width, height;
    public String name;

}
