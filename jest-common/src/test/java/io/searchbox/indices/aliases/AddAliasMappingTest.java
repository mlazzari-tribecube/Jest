package io.searchbox.indices.aliases;

import com.google.gson.Gson;
import org.elasticsearch.common.collect.ImmutableMap;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * @author cihat keser
 */
public class AddAliasMappingTest {

    public static final Map<String, Object> USER_FILTER_JSON = ImmutableMap.<String, Object>builder()
            .put("term", ImmutableMap.<String, String>builder()
                    .put("user", "kimchy")
                    .build())
            .build();

    @Test
    public void testBasicGetDataForJson() {
        AddAliasMapping addAliasMapping = new AddAliasMapping
                .Builder("tIndex", "tAlias")
                .build();
        String actualJson = new Gson().toJson(addAliasMapping.getData()).toString();
        String expectedJson = "[{\"add\":{\"index\":\"tIndex\",\"alias\":\"tAlias\"}}]";

        assertEquals(expectedJson, actualJson);
    }

    @Test
    public void testGetDataForJsonWithFilter() {
        AddAliasMapping addAliasMapping = new AddAliasMapping
                .Builder("tIndex", "tAlias")
                .setFilter(USER_FILTER_JSON)
                .build();
        String actualJson = new Gson().toJson(addAliasMapping.getData()).toString();
        String expectedJson = "[{\"add\":{\"index\":\"tIndex\",\"alias\":\"tAlias\",\"filter\":{\"term\":{\"user\":\"kimchy\"}}}}]";

        assertEquals(expectedJson, actualJson);
    }

    @Test
    public void testGetDataForJsonWithFilterAndRouting() {
        AddAliasMapping addAliasMapping = new AddAliasMapping
                .Builder("tIndex", "tAlias")
                .setFilter(USER_FILTER_JSON)
                .addRouting("1")
                .build();
        String actualJson = new Gson().toJson(addAliasMapping.getData()).toString();
        String expectedJson = "[{\"add\":{\"search_routing\":\"1\",\"index\":\"tIndex\",\"alias\":\"tAlias\"," +
                "\"index_routing\":\"1\",\"filter\":{\"term\":{\"user\":\"kimchy\"}}}}]";

        assertEquals(expectedJson, actualJson);
    }

}
