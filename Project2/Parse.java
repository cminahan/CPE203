public class Parse {
    public Parse(){}

    public static boolean parseBackground(String [] properties,
                                          WorldModel world, ImageStore imageStore)
    {
        if (properties.length == Functions.BGND_NUM_PROPERTIES)
        {
            Point pt = new Point(Integer.parseInt(properties[Functions.BGND_COL]),
                    Integer.parseInt(properties[Functions.BGND_ROW]));
            String id = properties[Functions.BGND_ID];
            world.setBackground(pt, new Background(id, imageStore.getImageList(id)));
        }

        return properties.length == Functions.BGND_NUM_PROPERTIES;
    }

    public static boolean parseOcto(String [] properties, WorldModel world,
                                    ImageStore imageStore)
    {
        if (properties.length == Functions.OCTO_NUM_PROPERTIES)
        {
            Point pt = new Point(Integer.parseInt(properties[Functions.OCTO_COL]),
                    Integer.parseInt(properties[Functions.OCTO_ROW]));
            Entity entity = OctoNotFull.createOctoNotFull(properties[Functions.OCTO_ID],
                    Integer.parseInt(properties[Functions.OCTO_LIMIT]), pt,
                    Integer.parseInt(properties[Functions.OCTO_ACTION_PERIOD]),
                    Integer.parseInt(properties[Functions.OCTO_ANIMATION_PERIOD]),
                    imageStore.getImageList(Functions.OCTO_KEY));
            world.tryAddEntity(entity);
        }

        return properties.length == Functions.OCTO_NUM_PROPERTIES;
    }

    public static boolean parseObstacle(String [] properties, WorldModel world,
                                        ImageStore imageStore)
    {
        if (properties.length == Functions.OBSTACLE_NUM_PROPERTIES)
        {
            Point pt = new Point(
                    Integer.parseInt(properties[Functions.OBSTACLE_COL]),
                    Integer.parseInt(properties[Functions.OBSTACLE_ROW]));
            Entity entity = Obstacle.createObstacle(properties[Functions.OBSTACLE_ID], pt,
                    imageStore.getImageList(Functions.OBSTACLE_KEY));
            world.tryAddEntity(entity);
        }

        return properties.length == Functions.OBSTACLE_NUM_PROPERTIES;
    }

    public static boolean parseFish(String [] properties, WorldModel world,
                                    ImageStore imageStore)
    {
        if (properties.length == Functions.FISH_NUM_PROPERTIES)
        {
            Point pt = new Point(Integer.parseInt(properties[Functions.FISH_COL]),
                    Integer.parseInt(properties[Functions.FISH_ROW]));
            Entity entity = Fish.createFish(properties[Functions.FISH_ID], pt,
                    Integer.parseInt(properties[Functions.FISH_ACTION_PERIOD]),
                    imageStore.getImageList(Functions.FISH_KEY));
            world.tryAddEntity(entity);
        }

        return properties.length == Functions.FISH_NUM_PROPERTIES;
    }

    public static boolean parseAtlantis(String [] properties, WorldModel world,
                                        ImageStore imageStore)
    {
        if (properties.length == Functions.ATLANTIS_NUM_PROPERTIES)
        {
            Point pt = new Point(Integer.parseInt(properties[Functions.ATLANTIS_COL]),
                    Integer.parseInt(properties[Functions.ATLANTIS_ROW]));
            Entity entity = Atlantis.createAtlantis(properties[Functions.ATLANTIS_ID], pt,
                    imageStore.getImageList(Functions.ATLANTIS_KEY));
            world.tryAddEntity(entity);
        }

        return properties.length == Functions.ATLANTIS_NUM_PROPERTIES;
    }

    public static boolean parseSgrass(String [] properties, WorldModel world,
                                      ImageStore imageStore)
    {
        if (properties.length == Functions.SGRASS_NUM_PROPERTIES)
        {
            Point pt = new Point(Integer.parseInt(properties[Functions.SGRASS_COL]),
                    Integer.parseInt(properties[Functions.SGRASS_ROW]));
            Entity entity = SGrass.createSgrass(properties[Functions.SGRASS_ID], pt,
                    Integer.parseInt(properties[Functions.SGRASS_ACTION_PERIOD]),
                    imageStore.getImageList(Functions.SGRASS_KEY));
            world.tryAddEntity(entity);
        }

        return properties.length == Functions.SGRASS_NUM_PROPERTIES;
    }
}
