package edu.hawaii.its.api.wrapper;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import edu.hawaii.its.api.util.JsonUtil;
import edu.hawaii.its.api.type.OptType;

import edu.internet2.middleware.grouperClient.ws.beans.WsGetAttributeAssignmentsResults;

import java.io.FileInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AttributeAssignmentsResultsTest {

    private static Properties properties;

    @BeforeAll
    public static void beforeAll() throws Exception {
        Path path = Paths.get("src/test/resources");
        Path file = path.resolve("grouper.test.properties");
        properties = new Properties();
        properties.load(new FileInputStream(file.toFile()));
    }

    @Test
    public void construction() {
        String json = propertyValue("attribute.assignment.opt.in.result");
        WsGetAttributeAssignmentsResults wsResults = JsonUtil.asObject(json, WsGetAttributeAssignmentsResults.class);
        assertNotNull(wsResults);
        AttributeAssignmentsResults results = new AttributeAssignmentsResults(wsResults);
        assertNotNull(results);
        assertEquals(results.getResultCode(), "SUCCESS");

        assertNotNull(wsResults.getWsAttributeDefNames());
        assertNotEquals(0, wsResults.getWsAttributeDefNames().length);
        assertEquals(wsResults.getWsAttributeDefNames()[0].getName(), results.getAttributeDefName());
    }

    @Test
    public void nullConstruction() {
        AttributeAssignmentsResults results = new AttributeAssignmentsResults(null);
        assertNotNull(results);
        assertNull(results.getAttributeDefName());
        assertFalse(results.isOptInOn());
        assertFalse(results.isOptOutOn());
        assertEquals(results.getResultCode(), null);
    }

    @Test
    public void getOwnerGroupNamesTest() {
        String json = propertyValue("attribute.assignment.opt.in.result");
        WsGetAttributeAssignmentsResults wsResults = JsonUtil.asObject(json, WsGetAttributeAssignmentsResults.class);
        assertNotNull(wsResults);
        AttributeAssignmentsResults results = new AttributeAssignmentsResults(wsResults);
        assertNotNull(results);

        List<String> ownerGroupNames = results.getOwnerGroupNames();
        assertNotNull(ownerGroupNames);

        assertEquals(1, ownerGroupNames.size());
        assertEquals("tmp:grouping-path:grouping-path-many", ownerGroupNames.get(0));

        Set<String> ownerGroupNamesSet = new HashSet<>();
        for (String name : ownerGroupNames) {
            assertTrue(ownerGroupNamesSet.add(name));
        }

        results = new AttributeAssignmentsResults(null);
        assertTrue(results.getOwnerGroupNames().isEmpty());

    }

    @Test
    public void isAttributeDefNameTest() {
        String json = propertyValue("attribute.assignment.opt.in.result");
        WsGetAttributeAssignmentsResults wsResults = JsonUtil.asObject(json, WsGetAttributeAssignmentsResults.class);
        assertNotNull(wsResults);
        AttributeAssignmentsResults results = new AttributeAssignmentsResults(wsResults);
        assertNotNull(results);
        assertTrue(results.isAttributeDefName(OptType.IN.value()));
        assertFalse(results.isAttributeDefName(OptType.OUT.value()));

        results = new AttributeAssignmentsResults(null);
        assertFalse(results.isAttributeDefName(OptType.IN.value()));

        json = propertyValue("attribute.assignment.opt.out.result");
        wsResults = JsonUtil.asObject(json, WsGetAttributeAssignmentsResults.class);
        assertNotNull(wsResults);
        results = new AttributeAssignmentsResults(wsResults);
        assertNotNull(results);
        assertTrue(results.isAttributeDefName(OptType.OUT.value()));
        assertFalse(results.isAttributeDefName(OptType.IN.value()));

        json = propertyValue("attribute.assignment.empty.result");
        wsResults = JsonUtil.asObject(json, WsGetAttributeAssignmentsResults.class);
        assertNotNull(wsResults);
        results = new AttributeAssignmentsResults(wsResults);
        assertNotNull(results);
        assertFalse(results.isAttributeDefName(OptType.OUT.value()));
        assertFalse(results.isAttributeDefName(OptType.IN.value()));
    }

    @Test
    public void getGroupNamesTest() {
        String json = propertyValue("attribute.assignment.opt.in.result");
        WsGetAttributeAssignmentsResults wsResults = JsonUtil.asObject(json, WsGetAttributeAssignmentsResults.class);
        assertNotNull(wsResults);
        AttributeAssignmentsResults results = new AttributeAssignmentsResults(wsResults);
        assertNotNull(results);

        List<String> groupNames = results.getGroupNames();
        assertNotNull(groupNames);
        assertFalse(groupNames.isEmpty());
        assertEquals(1, groupNames.size());
        assertEquals("tmp:grouping-path:grouping-path-many", groupNames.get(0));

        results = new AttributeAssignmentsResults(null);
        groupNames = results.getGroupNames();
        assertNotNull(groupNames);
        assertTrue(groupNames.isEmpty());
    }

    private String propertyValue(String key) {
        return properties.getProperty(key);
    }
}