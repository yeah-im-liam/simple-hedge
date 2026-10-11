package yeah.im.liam.simplehedge;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import yeah.im.liam.simplehedge.registry.HedgeFlower;

class ResourcesTest {
    private static final String ASSETS = "assets/simple_hedge_mod/";

    @Test
    void everyFlowerHasItsAssets() throws IOException {
        JsonObject lang = json(ASSETS + "lang/en_us.json");
        for (HedgeFlower flower : HedgeFlower.values()) {
            String path = flower.blockPath();
            JsonObject blockstate = json(ASSETS + "blockstates/" + path + ".json");
            String model = blockstate.getAsJsonObject("variants").getAsJsonObject("").get("model").getAsString();
            assertEquals("simple_hedge_mod:block/" + path, model);

            JsonObject textures = json(ASSETS + "models/block/" + path + ".json").getAsJsonObject("textures");
            String overlay = textures.get("flower_overlay").getAsString();
            assertTrue(exists(ASSETS + "textures/" + overlay.substring("simple_hedge_mod:".length()) + ".png"), overlay);

            assertTrue(lang.has("block.simple_hedge_mod." + path), path);
        }
    }

    @Test
    void tagsListEveryFlower() throws IOException {
        Set<String> flowers = tag("flowers");
        Set<String> hoe = tag("mineable/hoe");
        Set<String> bees = tag("bee_attractive");
        assertTrue(hoe.contains("simple_hedge_mod:hedge"));
        for (HedgeFlower flower : HedgeFlower.values()) {
            String id = "simple_hedge_mod:" + flower.blockPath();
            assertTrue(flowers.contains(id), id);
            assertTrue(hoe.contains(id), id);
            boolean vanillaAttractsBees = flower != HedgeFlower.CLOSED_EYEBLOSSOM && flower != HedgeFlower.GOLDEN_DANDELION;
            assertEquals(vanillaAttractsBees, bees.contains(id), id);
        }
    }

    @Test
    void recipeMakesFourHedges() throws IOException {
        JsonObject result = json("data/simple_hedge_mod/recipe/hedge.json").getAsJsonObject("result");
        assertEquals("simple_hedge_mod:hedge", result.get("id").getAsString());
        assertEquals(4, result.get("count").getAsInt());
    }

    @Test
    void itemModelsTintOnlyOnce() throws IOException {
        JsonObject definition = json(ASSETS + "items/hedge.json").getAsJsonObject("model");
        assertEquals("simple_hedge_mod:item/hedge_base", definition.get("model").getAsString());
        JsonObject legacy = json(ASSETS + "models/item/hedge.json");
        assertEquals("simple_hedge_mod:item/hedge_base", legacy.get("parent").getAsString());
        assertTrue(exists(ASSETS + "textures/item/hedge.png"));
    }

    private static Set<String> tag(String name) throws IOException {
        Set<String> ids = new HashSet<>();
        for (JsonElement value : json("data/minecraft/tags/block/" + name + ".json").getAsJsonArray("values")) {
            ids.add(value.isJsonObject() ? value.getAsJsonObject().get("id").getAsString() : value.getAsString());
        }
        return ids;
    }

    private static boolean exists(String path) {
        return ResourcesTest.class.getClassLoader().getResource(path) != null;
    }

    private static JsonObject json(String path) throws IOException {
        try (InputStream in = ResourcesTest.class.getClassLoader().getResourceAsStream(path)) {
            assertNotNull(in, path);
            return JsonParser.parseReader(new InputStreamReader(in, StandardCharsets.UTF_8)).getAsJsonObject();
        }
    }
}
