package game;

import base.Resource;
import base.ResourceSystem;
import resource.MapResource;

public class ResourceSystemMock implements ResourceSystem{

	public Resource getResource(Class<?> className) {
		if (className.equals(MapResource.class)) {
			MapResource map = new MapResource();
			map.height = 500;
			map.width = 900;
			map.name = "Map1";
			return map;
		}
		return null;
	}

	public void globalInit() {

	}
}
