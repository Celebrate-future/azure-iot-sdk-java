// Copyright (c) Microsoft. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package com.microsoft.azure.sdk.iot.service.twin;

import com.microsoft.azure.sdk.iot.service.Helpers;
import mockit.Deencapsulation;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Unit tests for the TwinMetadata
 * 100% methods, 100% lines covered
 */
public class TwinMetadataTest
{
    /* SRS_TWIN_METADATA_21_001: [The constructor shall parse the provided `lastUpdated` String to the Date and store it as the TwinMetadata lastUpdated.] */
    @Test
    public void constructorParseLastUpdatedSucceed()
    {
        // arrange
        String lastUpdated = "2017-09-21T02:07:44.238Z";

        // act
        TwinMetadata twinMetadata = Deencapsulation.newInstance(TwinMetadata.class, new Class[] {String.class, Integer.class, String.class, String.class},
                lastUpdated, 5, null, null);

        // assert
        assertNotNull(twinMetadata);
        Helpers.assertDateWithError((Date)Deencapsulation.getField(twinMetadata, "lastUpdated"), lastUpdated);
    }

    /* SRS_TWIN_METADATA_21_001: [The constructor shall parse the provided `lastUpdated` String to the Date and store it as the TwinMetadata lastUpdated.] */
    @Test
    public void constructorLastUpdatedNullSucceed()
    {
        // arrange
        String lastUpdated = null;

        // act
        TwinMetadata twinMetadata = Deencapsulation.newInstance(TwinMetadata.class, new Class[] {String.class, Integer.class, String.class, String.class},
                lastUpdated, 5, null, null);

        // assert
        assertNotNull(twinMetadata);
        assertNull(Deencapsulation.getField(twinMetadata, "lastUpdated"));
    }

    /* SRS_TWIN_METADATA_21_001: [The constructor shall parse the provided `lastUpdated` String to the Date and store it as the TwinMetadata lastUpdated.] */
    @Test
    public void constructorLastUpdatedEmptySucceed()
    {
        // arrange
        String lastUpdated = "";

        // act
        TwinMetadata twinMetadata = Deencapsulation.newInstance(TwinMetadata.class, new Class[] {String.class, Integer.class, String.class, String.class},
                lastUpdated, 5, "testConfig", "12345");

        // assert
        assertNotNull(twinMetadata);
        assertNull(Deencapsulation.getField(twinMetadata, "lastUpdated"));
    }

    /* SRS_TWIN_METADATA_21_002: [The constructor shall throw IllegalArgumentException if it cannot convert the provided `lastUpdated` String to Date.] */
    @Test (expected = IllegalArgumentException.class)
    public void constructorLastUpdatedInvalidSucceed()
    {
        // arrange
        String lastUpdated = "This is a invalid date";

        // act
        Deencapsulation.newInstance(TwinMetadata.class, new Class[] {String.class, Integer.class, String.class, String.class},
                lastUpdated, 5, null, null);

        // assert
    }

    /* SRS_TWIN_METADATA_21_003: [The constructor shall store the provided lastUpdatedVersion as is.] */
    @Test
    public void constructorPositiveLastUpdatedVersionSucceed()
    {
        // arrange
        Integer lastUpdatedVersion = 5;

        // act
        TwinMetadata twinMetadata = Deencapsulation.newInstance(TwinMetadata.class, new Class[] {String.class, Integer.class, String.class, String.class},
                "2017-09-21T02:07:44.238Z", lastUpdatedVersion, null, null);

        // assert
        assertNotNull(twinMetadata);
        assertEquals(lastUpdatedVersion, Deencapsulation.getField(twinMetadata, "lastUpdatedVersion"));
    }

    /* SRS_TWIN_METADATA_21_003: [The constructor shall store the provided lastUpdatedVersion as is.] */
    @Test
    public void constructorNegativeLastUpdatedVersionSucceed()
    {
        // arrange
        Integer lastUpdatedVersion = -5;

        // act
        TwinMetadata twinMetadata = Deencapsulation.newInstance(TwinMetadata.class, new Class[] {String.class, Integer.class, String.class, String.class},
                "2017-09-21T02:07:44.238Z", lastUpdatedVersion, null, null);

        // assert
        assertNotNull(twinMetadata);
        assertEquals(lastUpdatedVersion, Deencapsulation.getField(twinMetadata, "lastUpdatedVersion"));
    }

    /* SRS_TWIN_METADATA_21_003: [The constructor shall store the provided lastUpdatedVersion as is.] */
    @Test
    public void constructorLastUpdatedVersionNullSucceed()
    {
        // arrange
        Integer lastUpdatedVersion = null;

        // act
        TwinMetadata twinMetadata = Deencapsulation.newInstance(TwinMetadata.class, new Class[] {String.class, Integer.class, String.class, String.class},
                "2017-09-21T02:07:44.238Z", lastUpdatedVersion, null, null);

        // assert
        assertNotNull(twinMetadata);
        assertNull(Deencapsulation.getField(twinMetadata, "lastUpdatedVersion"));
    }

    /* SRS_TWIN_METADATA_21_010: [The constructor shall throw IllegalArgumentException if the provided metadata is null.] */
    @Test (expected = IllegalArgumentException.class)
    public void constructorCopyThrowsOnNull()
    {
        // arrange
        TwinMetadata originalTwinMetadata = null;

        // act - assert
        Deencapsulation.newInstance(TwinMetadata.class, new Class[] {TwinMetadata.class}, originalTwinMetadata);
    }

    /* SRS_TWIN_METADATA_21_011: [The constructor shall copy the content of the provided metadata.] */
    @Test
    public void constructorCopySucceed()
    {
        // arrange
        final String lastUpdated = "2017-09-21T02:07:44.238Z";
        final Integer lastUpdatedVersion = 10;
        final String lastUpdatedBy = "testConfig";
        final String lastUpdatedByDigest = "12345";
        TwinMetadata originalTwinMetadata = Deencapsulation.newInstance(TwinMetadata.class, new Class[] {String.class, Integer.class, String.class, String.class},
                lastUpdated, lastUpdatedVersion, lastUpdatedBy, lastUpdatedByDigest);

        // act
        TwinMetadata twinMetadata = Deencapsulation.newInstance(TwinMetadata.class, new Class[] {TwinMetadata.class}, originalTwinMetadata);

        // assert
        assertNotNull(twinMetadata);
        Helpers.assertDateWithError((Date)Deencapsulation.getField(twinMetadata, "lastUpdated"), lastUpdated);
        assertEquals(lastUpdatedVersion, Deencapsulation.getField(twinMetadata, "lastUpdatedVersion"));
        assertEquals(lastUpdatedBy, Deencapsulation.getField(twinMetadata, "lastUpdatedBy"));
        assertEquals(lastUpdatedByDigest, Deencapsulation.getField(twinMetadata, "lastUpdatedByDigest"));
    }

    /* SRS_TWIN_METADATA_21_012: [The constructor shall throw IllegalArgumentException if both lastUpdated and lastUpdatedVersion are null.] */
    @Test (expected = IllegalArgumentException.class)
    public void constructorThrowsOnDateAndVersionNull()
    {
        // act - assert
        Deencapsulation.newInstance(
                TwinMetadata.class,
                new Class[] {String.class, Integer.class, String.class, String.class},
                null, null, null, null);
    }


    /* SRS_TWIN_METADATA_21_004: [The tryExtractFromMap shall return null if the provided metadata is not a Map.] */
    @Test
    public void tryExtractFromMapNotMapSucceed()
    {
        // arrange
        Object metadata = "This is not a Map";

        // act
        TwinMetadata twinMetadata = Deencapsulation.invoke(TwinMetadata.class, "tryExtractFromMap", new Class[] {Object.class}, metadata);

        // assert
        assertNull(twinMetadata);
    }

    /* SRS_TWIN_METADATA_21_005: [If the provide metadata contains date or version, the tryExtractFromMap shall return a new instance of TwinMetadata with this information.] */
    @Test
    public void tryExtractFromMapDateSucceed()
    {
        // arrange
        final String lastUpdated = "2017-09-21T02:07:44.238Z";
        Object metadata = new HashMap<String, Object>()
        {
            {
                put("key1", "value1");
                put("$lastUpdated", lastUpdated);
                put("key2", "value2");
            }
        };

        // act
        TwinMetadata twinMetadata = Deencapsulation.invoke(TwinMetadata.class, "tryExtractFromMap", new Class[] {Object.class}, metadata);

        // assert
        assertNotNull(twinMetadata);
        Helpers.assertDateWithError((Date)Deencapsulation.getField(twinMetadata, "lastUpdated"), lastUpdated);
    }

    /* SRS_TWIN_METADATA_21_005: [If the provide metadata contains date or version, the tryExtractFromMap shall return a new instance of TwinMetadata with this information.] */
    @Test
    public void tryExtractFromMapVersionSucceed()
    {
        // arrange
        final Integer lastUpdatedVersion = 10;
        Object metadata = new HashMap<String, Object>()
        {
            {
                put("key1", "value1");
                put("$lastUpdatedVersion", lastUpdatedVersion);
                put("key2", "value2");
            }
        };

        // act
        TwinMetadata twinMetadata = Deencapsulation.invoke(TwinMetadata.class, "tryExtractFromMap", new Class[] {Object.class}, metadata);

        // assert
        assertNotNull(twinMetadata);
        assertEquals(lastUpdatedVersion, Deencapsulation.getField(twinMetadata, "lastUpdatedVersion"));
    }

    @Test
    public void tryExtractFromMapUpdatedBySucceed()
    {
        // arrange
        final String lastUpdatedBy = "testConfig";
        final String lastUpdated = "2017-09-21T02:07:44.238Z";
        Object metadata = new HashMap<String, Object>()
        {
            {
                put("key1", "value1");
                put("$lastUpdated", lastUpdated);
                put("$lastUpdatedBy", lastUpdatedBy);
                put("key2", "value2");
            }
        };

        // act
        TwinMetadata twinMetadata = Deencapsulation.invoke(TwinMetadata.class, "tryExtractFromMap", new Class[] {Object.class}, metadata);

        // assert
        assertNotNull(twinMetadata);
        assertEquals(lastUpdatedBy, Deencapsulation.getField(twinMetadata, "lastUpdatedBy"));
    }

    @Test
    public void tryExtractFromMapDigestucceed()
    {
        // arrange
        final String lastUpdatedByDigest = "12345";
        final String lastUpdated = "2017-09-21T02:07:44.238Z";
        Object metadata = new HashMap<String, Object>()
        {
            {
                put("key1", "value1");
                put("$lastUpdated", lastUpdated);
                put("$lastUpdatedByDigest", lastUpdatedByDigest);
                put("key2", "value2");
            }
        };

        // act
        TwinMetadata twinMetadata = Deencapsulation.invoke(TwinMetadata.class, "tryExtractFromMap", new Class[] {Object.class}, metadata);

        // assert
        assertNotNull(twinMetadata);
        assertEquals(lastUpdatedByDigest, Deencapsulation.getField(twinMetadata, "lastUpdatedByDigest"));
    }

    @Test
    public void tryExtractFromMapAllSucceed()
    {
        // arrange
        final String lastUpdated = "2017-09-21T02:07:44.238Z";
        final String lastUpdatedBy = "testConfig";
        final String lastUpdatedByDigest = "12345";
        final Integer lastUpdatedVersion = 10;
        Object metadata = new HashMap<String, Object>()
        {
            {
                put("key1", "value1");
                put("$lastUpdated", lastUpdated);
                put("key2", "value2");
                put("$lastUpdatedVersion", lastUpdatedVersion);
                put("$lastUpdatedBy", lastUpdatedBy);
                put("$lastUpdatedByDigest", lastUpdatedByDigest);
            }
        };

        // act
        TwinMetadata twinMetadata = Deencapsulation.invoke(TwinMetadata.class, "tryExtractFromMap", new Class[] {Object.class}, metadata);

        // assert
        assertNotNull(twinMetadata);
        Helpers.assertDateWithError((Date)Deencapsulation.getField(twinMetadata, "lastUpdated"), lastUpdated);
        assertEquals(lastUpdatedVersion, Deencapsulation.getField(twinMetadata, "lastUpdatedVersion"));
        assertEquals(lastUpdatedBy, Deencapsulation.getField(twinMetadata, "lastUpdatedBy"));
        assertEquals(lastUpdatedByDigest, Deencapsulation.getField(twinMetadata, "lastUpdatedByDigest"));
    }

    /* SRS_TWIN_METADATA_21_005: [If the provide metadata contains date or version, the tryExtractFromMap shall return a new instance of TwinMetadata with this information.] */
    @Test
    public void tryExtractFromMapMinDateAndVersionSucceed()
    {
        // arrange
        final String lastUpdated = "0000-00-00T00:00:00.000Z";
        final Integer lastUpdatedVersion = 10;
        Object metadata = new HashMap<String, Object>()
        {
            {
                put("key1", "value1");
                put("$lastUpdated", lastUpdated);
                put("key2", "value2");
                put("$lastUpdatedVersion", lastUpdatedVersion);
            }
        };

        // act
        TwinMetadata twinMetadata = Deencapsulation.invoke(TwinMetadata.class, "tryExtractFromMap", new Class[] {Object.class}, metadata);

        // assert
        assertNotNull(twinMetadata);
        Helpers.assertDateWithError((Date)Deencapsulation.getField(twinMetadata, "lastUpdated"), lastUpdated);
        assertEquals(lastUpdatedVersion, Deencapsulation.getField(twinMetadata, "lastUpdatedVersion"));
    }

    /* SRS_TWIN_METADATA_21_005: [If the provide metadata contains date or version, the tryExtractFromMap shall return a new instance of TwinMetadata with this information.] */
    @Test
    public void tryExtractFromMapNoMetadataSucceed()
    {
        // arrange
        Object metadata = new HashMap<String, Object>()
        {
            {
                put("key1", "value1");
                put("key2", "value2");
            }
        };

        // act
        TwinMetadata twinMetadata = Deencapsulation.invoke(TwinMetadata.class, "tryExtractFromMap", new Class[] {Object.class}, metadata);

        // assert
        assertNull(twinMetadata);
    }

    /* SRS_TWIN_METADATA_21_006: [The tryExtractFromMap shall throw IllegalArgumentException if it cannot convert the provided `lastUpdated` String to Date or the version in a Number.] */
    @Test (expected = IllegalArgumentException.class)
    public void tryExtractFromMapValidDateAndInvalidVersionSucceed()
    {
        // arrange
        final String lastUpdated = "2017-09-21T02:07:44.238Z";
        final String lastUpdatedVersion = "This is not a Number";
        Object metadata = new HashMap<String, Object>()
        {
            {
                put("key1", "value1");
                put("$lastUpdated", lastUpdated);
                put("key2", "value2");
                put("$lastUpdatedVersion", lastUpdatedVersion);
            }
        };

        // act
        TwinMetadata twinMetadata = Deencapsulation.invoke(TwinMetadata.class, "tryExtractFromMap", new Class[] {Object.class}, metadata);

        // assert
        assertNotNull(twinMetadata);
        Helpers.assertDateWithError((Date)Deencapsulation.getField(twinMetadata, "lastUpdated"), lastUpdated);
        assertNull(Deencapsulation.getField(twinMetadata, "lastUpdatedVersion"));
    }

    /* SRS_TWIN_METADATA_21_006: [The tryExtractFromMap shall throw IllegalArgumentException if it cannot convert the provided `lastUpdated` String to Date or the version in a Number.] */
    @Test (expected = IllegalArgumentException.class)
    public void tryExtractFromMapThrowsOnInvalidDateAndValidVersion()
    {
        // arrange
        final String lastUpdated = "This is not a valid date";
        final Integer lastUpdatedVersion = 10;
        Object metadata = new HashMap<String, Object>()
        {
            {
                put("key1", "value1");
                put("$lastUpdated", lastUpdated);
                put("key2", "value2");
                put("$lastUpdatedVersion", lastUpdatedVersion);
            }
        };

        // act
        Deencapsulation.invoke(TwinMetadata.class, "tryExtractFromMap", new Class[] {Object.class}, metadata);

        // assert
    }

    /* SRS_TWIN_METADATA_21_006: [The tryExtractFromMap shall throw IllegalArgumentException if it cannot convert the provided `lastUpdated` String to Date or the version in a Number.] */
    @Test (expected = IllegalArgumentException.class)
    public void tryExtractFromMapInvalidDateAndInvalidVersionSucceed()
    {
        // arrange
        final String lastUpdated = "This is not a date";
        final String lastUpdatedVersion = "This is not a Number";
        Object metadata = new HashMap<String, Object>()
        {
            {
                put("key1", "value1");
                put("$lastUpdated", lastUpdated);
                put("key2", "value2");
                put("$lastUpdatedVersion", lastUpdatedVersion);
            }
        };

        // act
        Deencapsulation.invoke(TwinMetadata.class, "tryExtractFromMap", new Class[] {Object.class}, metadata);

        // assert
    }

    /* SRS_TWIN_METADATA_21_007: [The getLastUpdatedVersion shall return the stored lastUpdatedVersion.] */
    @Test
    public void getLastUpdatedNullSucceed()
    {
        // arrange
        final String lastUpdated = null;
        final Integer lastUpdatedVersion = 10;
        TwinMetadata twinMetadata = Deencapsulation.newInstance(TwinMetadata.class, new Class[] {String.class, Integer.class, String.class, String.class},
                lastUpdated, lastUpdatedVersion, null, null);

        // act - assert
        assertNull(twinMetadata.getLastUpdated());
    }

    /* SRS_TWIN_METADATA_21_008: [The getLastUpdated shall return the stored lastUpdated.] */
    @Test
    public void getLastUpdatedVersionNullSucceed()
    {
        // arrange
        final String lastUpdated = "2017-09-21T02:07:44.238Z";
        final Integer lastUpdatedVersion = null;
        TwinMetadata twinMetadata = Deencapsulation.newInstance(TwinMetadata.class, new Class[] {String.class, Integer.class, String.class, String.class},
                lastUpdated, lastUpdatedVersion, null, null);

        // act - assert
        assertNull(twinMetadata.getLastUpdatedVersion());
    }

    /* SRS_TWIN_METADATA_21_007: [The getLastUpdatedVersion shall return the stored lastUpdatedVersion.] */
    /* SRS_TWIN_METADATA_21_008: [The getLastUpdated shall return the stored lastUpdated.] */
    @Test
    public void gettersSucceed()
    {
        // arrange
        final String lastUpdated = "2017-09-21T02:07:44.238Z";
        final Integer lastUpdatedVersion = 10;
        final String lastUpdatedBy = "testConfig";
        final String lastUpdatedByDigest = "637570515479675333";
        Object metadata = new HashMap<String, Object>()
        {
            {
                put("key1", "value1");
                put("$lastUpdated", lastUpdated);
                put("key2", "value2");
                put("$lastUpdatedVersion", lastUpdatedVersion);
                put("$lastUpdatedBy", lastUpdatedBy);
                put("$lastUpdatedByDigest", lastUpdatedByDigest);
            }
        };
        TwinMetadata twinMetadata = Deencapsulation.invoke(TwinMetadata.class, "tryExtractFromMap", new Class[] {Object.class}, metadata);

        // act - assert
        Helpers.assertDateWithError(twinMetadata.getLastUpdated(), lastUpdated);
        assertEquals(lastUpdatedVersion, twinMetadata.getLastUpdatedVersion());
        assertEquals(lastUpdatedBy, twinMetadata.getLastUpdatedBy());
        assertEquals(lastUpdatedByDigest, twinMetadata.getLastUpdatedByDigest());
    }

    /* SRS_TWIN_METADATA_21_009: [The toJsonElement shall return a JsonElement with the information in this class in a JSON format.] */
    @Test
    public void toJsonElementSucceed()
    {
        // arrange
        final String lastUpdated = "2017-09-21T02:07:44.238Z";
        final Integer lastUpdatedVersion = 10;
        final String lastUpdatedBy = "testConfig";
        final String lastUpdatedByDigest = "637570515479675333";
        Object metadata = new HashMap<String, Object>()
        {
            {
                put("key1", "value1");
                put("$lastUpdated", lastUpdated);
                put("key2", "value2");
                put("$lastUpdatedVersion", lastUpdatedVersion);
                put("$lastUpdatedBy", lastUpdatedBy);
                put("$lastUpdatedByDigest", lastUpdatedByDigest);
            }
        };
        String expectedJson = "{\"$lastUpdated\":\"" + lastUpdated + "\",\"$lastUpdatedVersion\":" + lastUpdatedVersion + ",\"$lastUpdatedBy\":\"" + lastUpdatedBy + "\",\"$lastUpdatedByDigest\":\"" + lastUpdatedByDigest + "\"}";
        TwinMetadata twinMetadata = Deencapsulation.invoke(TwinMetadata.class, "tryExtractFromMap", new Class[] {Object.class}, metadata);

        // act - assert
        Helpers.assertJson(Deencapsulation.invoke(twinMetadata, "toJsonElement").toString(), expectedJson);
    }

    /* SRS_TWIN_METADATA_21_009: [The toJsonElement shall return a JsonElement with the information in this class in a JSON format.] */
    @Test
    public void toJsonElementNoDateSucceed()
    {
        // arrange
        final Integer lastUpdatedVersion = 10;
        Object metadata = new HashMap<String, Object>()
        {
            {
                put("key1", "value1");
                put("key2", "value2");
                put("$lastUpdatedVersion", lastUpdatedVersion);
            }
        };
        String expectedJson = "{\"$lastUpdatedVersion\":" + lastUpdatedVersion + "}";
        TwinMetadata twinMetadata = Deencapsulation.invoke(TwinMetadata.class, "tryExtractFromMap", new Class[] {Object.class}, metadata);

        // act - assert
        Helpers.assertJson(Deencapsulation.invoke(twinMetadata, "toJsonElement").toString(), expectedJson);
    }

    /* SRS_TWIN_METADATA_21_009: [The toJsonElement shall return a JsonElement with the information in this class in a JSON format.] */
    @Test
    public void toJsonElementNoVersionSucceed()
    {
        // arrange
        final String lastUpdated = "2017-09-21T02:07:44.238Z";
        Object metadata = new HashMap<String, Object>()
        {
            {
                put("key1", "value1");
                put("$lastUpdated", lastUpdated);
                put("key2", "value2");
            }
        };
        String expectedJson = "{\"$lastUpdated\":\"" + lastUpdated + "\"}";
        TwinMetadata twinMetadata = Deencapsulation.invoke(TwinMetadata.class, "tryExtractFromMap", new Class[] {Object.class}, metadata);

        // act - assert
        Helpers.assertJson(Deencapsulation.invoke(twinMetadata, "toJsonElement").toString(), expectedJson);
    }

    @Test
    public void toJsonElementNoUpdatedByAndDigestSucceed()
    {
        // arrange
        final String lastUpdated = "2017-09-21T02:07:44.238Z";
        final Integer lastUpdatedVersion = 10;
        Object metadata = new HashMap<String, Object>()
        {
            {
                put("key1", "value1");
                put("$lastUpdated", lastUpdated);
                put("key2", "value2");
                put("$lastUpdatedVersion", lastUpdatedVersion);
            }
        };
        String expectedJson = "{\"$lastUpdated\":\"" + lastUpdated + "\",\"$lastUpdatedVersion\":" + lastUpdatedVersion + "}";
        TwinMetadata twinMetadata = Deencapsulation.invoke(TwinMetadata.class, "tryExtractFromMap", new Class[] {Object.class}, metadata);

        // act - assert
        Helpers.assertJson(Deencapsulation.invoke(twinMetadata, "toJsonElement").toString(), expectedJson);
    }

    /* SRS_TWIN_METADATA_21_010: [The toString shall return a String with the information in this class in a pretty print JSON.] */
    @Test
    public void toStringSucceed()
    {
        // arrange
        final String lastUpdated = "2017-09-21T02:07:44.238Z";
        final Integer lastUpdatedVersion = 10;
        final String lastUpdatedBy = "testConfig";
        final String lastUpdatedByDigest = "637570515479675333";

        TwinMetadata twinMetadata = Deencapsulation.newInstance(TwinMetadata.class, new Class[] {String.class, Integer.class, String.class, String.class},
                lastUpdated, lastUpdatedVersion, lastUpdatedBy, lastUpdatedByDigest);

        // act - assert
        Helpers.assertJson(twinMetadata.toString(), "{\"$lastUpdated\":\"2017-09-21T02:07:44.238Z\",\"$lastUpdatedVersion\":10, " +
                "\"$lastUpdatedBy\":\"testConfig\", \"$lastUpdatedByDigest\":\"637570515479675333\"}");
    }
}
