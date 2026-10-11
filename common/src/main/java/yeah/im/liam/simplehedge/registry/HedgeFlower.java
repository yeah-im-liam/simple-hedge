package yeah.im.liam.simplehedge.registry;

public enum HedgeFlower {
    DANDELION("dandelion"),
    POPPY("poppy"),
    BLUE_ORCHID("blue_orchid"),
    ALLIUM("allium"),
    AZURE_BLUET("azure_bluet"),
    RED_TULIP("red_tulip"),
    ORANGE_TULIP("orange_tulip"),
    WHITE_TULIP("white_tulip"),
    PINK_TULIP("pink_tulip"),
    OXEYE_DAISY("oxeye_daisy"),
    CORNFLOWER("cornflower"),
    LILY_OF_THE_VALLEY("lily_of_the_valley"),
    WITHER_ROSE("wither_rose"),
    TORCHFLOWER("torchflower"),
    GOLDEN_DANDELION("golden_dandelion"),
    CLOSED_EYEBLOSSOM("closed_eyeblossom"),
    OPEN_EYEBLOSSOM("open_eyeblossom");

    private final String name;

    HedgeFlower(String name) {
        this.name = name;
    }

    public String flowerId() {
        return "minecraft:" + this.name;
    }

    public String blockPath() {
        return "flowering_" + this.name + "_hedge";
    }

    public boolean isEyeblossom() {
        return this == CLOSED_EYEBLOSSOM || this == OPEN_EYEBLOSSOM;
    }

    public int lightLevel() {
        return this == TORCHFLOWER ? 10 : 0;
    }
}
