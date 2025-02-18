/*
 * Copyright (c) Microsoft. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package com.microsoft.azure.sdk.iot.service.auth;

import com.microsoft.azure.sdk.iot.service.auth.AuthenticationMethod;
import com.microsoft.azure.sdk.iot.service.auth.IotHubConnectionString;
import com.microsoft.azure.sdk.iot.service.auth.IotHubConnectionStringBuilder;
import com.microsoft.azure.sdk.iot.service.auth.ServiceAuthenticationWithSharedAccessPolicyKey;
import com.microsoft.azure.sdk.iot.service.auth.ServiceAuthenticationWithSharedAccessPolicyToken;
import mockit.Deencapsulation;
import mockit.Expectations;
import mockit.integration.junit4.JMockit;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(JMockit.class)
public class IotHubConnectionStringBuilderTest
{
	protected static final String HOST_NAME_PROPERTY_NAME = "HostName";
    protected static final String SHARED_ACCESS_KEY_NAME_PROPERTY_NAME = "SharedAccessKeyName";
    protected static final String SHARED_ACCESS_KEY_PROPERTY_NAME = "SharedAccessKey";
    protected static final String SHARED_ACCESS_SIGNATURE_PROPERTY_NAME = "SharedAccessSignature";
    
    // Tests_SRS_SERVICE_SDK_JAVA_IOTHUBCONNECTIONSTRINGBUILDER_12_001: [The function shall throw IllegalArgumentException if the input string is empty or null]
    // Assert
    @Test (expected = IllegalArgumentException.class)
    public void createConnectionString_input_null() throws Exception
    {
        // Arrange
        String connectionString = null;
        // Act
        IotHubConnectionString iotHubConnectionString = IotHubConnectionStringBuilder.createIotHubConnectionString(connectionString);
    }

    // Tests_SRS_SERVICE_SDK_JAVA_IOTHUBCONNECTIONSTRINGBUILDER_12_001: [The function shall throw IllegalArgumentException if the input string is empty or null]
    // Assert
    @Test (expected = IllegalArgumentException.class)
    public void createConnectionString_input_empty() throws Exception
    {
        // Arrange
        String connectionString = "";
        // Act
        IotHubConnectionString iotHubConnectionString = IotHubConnectionStringBuilder.createIotHubConnectionString(connectionString);
    }

    // Tests_SRS_SERVICE_SDK_JAVA_IOTHUBCONNECTIONSTRINGBUILDER_12_002: [The function shall create a new IotHubConnectionString object deserializing the given string]
    @Test
    public void createConnectionString_good_case() throws Exception
    {
        // Arrange
        String iotHubName = "IOTHUBNAME";
        String iotHostName = "HOSTNAME";
        String sharedAccessKeyName = "ACCESSKEYNAME";
        String policyName = "SharedAccessKey";
        String sharedAccessKey = "1234567890abcdefghijklmnopqrstvwxyz=";
        String connectionString = "HostName=" + iotHubName + "." + iotHostName + ";SharedAccessKeyName=" + sharedAccessKeyName + ";" + policyName + "=" + sharedAccessKey;
        String userString = sharedAccessKeyName +  "@SAS.root." + iotHubName;
        // Act
        IotHubConnectionString iotHubConnectionString = IotHubConnectionStringBuilder.createIotHubConnectionString(connectionString);
        // Assert
        assertEquals("Parser error: HostName mismatch!", iotHubName + "." + iotHostName, iotHubConnectionString.getHostName());
        assertEquals("Parser error: SharedAccessKeyName mismatch!", sharedAccessKeyName, iotHubConnectionString.getSharedAccessKeyName());
        assertEquals("Parser error: SharedAccessKey mismatch!", iotHubConnectionString.getSharedAccessKey(), sharedAccessKey);
        assertEquals("Parser error: SharedAccessSignature mismatch!", "", iotHubConnectionString.getSharedAccessSignature());
        assertEquals("Parser error: IotHubName mismatch!", iotHubName, iotHubConnectionString.getIotHubName());
    }

    // Tests_SRS_SERVICE_SDK_JAVA_IOTHUBCONNECTIONSTRINGBUILDER_12_003: [The function shall throw IllegalArgumentException if the input string is empty or null]
    // Assert
    @Test (expected = IllegalArgumentException.class)
    public void createConnectionString_hostName_null() throws Exception
    {
        // Arrange
        AuthenticationMethod authenticationMethod = new ServiceAuthenticationWithSharedAccessPolicyKey("", "");
        // Act
        IotHubConnectionString iotHubConnectionString = IotHubConnectionStringBuilder.createIotHubConnectionString(null, authenticationMethod);
    }

    // Tests_SRS_SERVICE_SDK_JAVA_IOTHUBCONNECTIONSTRINGBUILDER_12_003: [The function shall throw IllegalArgumentException if the input string is empty or null]
    // Assert
    @Test (expected = IllegalArgumentException.class)
    public void createConnectionString_hosName_empty() throws Exception
    {
        // Arrange
        AuthenticationMethod authenticationMethod = new ServiceAuthenticationWithSharedAccessPolicyKey("", "");
        // Act
        IotHubConnectionString iotHubConnectionString = IotHubConnectionStringBuilder.createIotHubConnectionString("", authenticationMethod);
    }

    // Tests_SRS_SERVICE_SDK_JAVA_IOTHUBCONNECTIONSTRINGBUILDER_12_004: [The function shall throw IllegalArgumentException if the input authenticationMethod is null]
    // Assert
    @Test (expected = IllegalArgumentException.class)
    public void createConnectionString_authenticationMethod_null() throws Exception
    {
        // Arrange
        AuthenticationMethod authenticationMethod = null;
        // Act
        IotHubConnectionString iotHubConnectionString = IotHubConnectionStringBuilder.createIotHubConnectionString("test", authenticationMethod);
    }

    // Tests_SRS_SERVICE_SDK_JAVA_IOTHUBCONNECTIONSTRINGBUILDER_12_005: [The function shall create a new IotHubConnectionString object using the given hostname and auhtenticationMethod]
    @Test
    public void createConnectionString_good_case_policy_key() throws Exception
    {
        // Arrange
        ServiceAuthenticationWithSharedAccessPolicyKey auth = new ServiceAuthenticationWithSharedAccessPolicyKey("myPolicy", "<key>");
        // Act
        IotHubConnectionString iotHubConnectionString = IotHubConnectionStringBuilder.createIotHubConnectionString("hostname", auth);
        String connString = iotHubConnectionString.toString();
        // Assert
        assertEquals("Connection string mismatch!", "HostName=hostname;SharedAccessKeyName=myPolicy;SharedAccessKey=<key>;SharedAccessSignature=null", connString);
    }

    // Tests_SRS_SERVICE_SDK_JAVA_IOTHUBCONNECTIONSTRINGBUILDER_12_005: [The function shall create a new IotHubConnectionString object using the given hostname and auhtenticationMethod]
    @Test
    public void createConnectionString_good_case_policy_token() throws Exception
    {
        // Arrange
        ServiceAuthenticationWithSharedAccessPolicyToken auth = new ServiceAuthenticationWithSharedAccessPolicyToken("myPolicy", "<key>");
        // Act
        IotHubConnectionString iotHubConnectionString = IotHubConnectionStringBuilder.createIotHubConnectionString("hostname", auth);
        String connString = iotHubConnectionString.toString();
        // Assert
        assertEquals("Connection string mismatch!", "HostName=hostname;SharedAccessKeyName=myPolicy;SharedAccessKey=null;SharedAccessSignature=<key>", connString);
    }

    // Tests_SRS_SERVICE_SDK_JAVA_IOTHUBCONNECTIONSTRINGBUILDER_12_006: [The function shall throw IllegalArgumentException if the input string is empty or null]
    // Assert
    @Test (expected = IllegalArgumentException.class)
    public void parse_connectionString_null() throws Exception
    {
        // Arrange
        String iotHubName = "IOTHUBNAME";
        String iotHostName = "HOSTNAME";
        String sharedAccessKeyName = "ACCESSKEYNAME";
        String policyName = "SharedAccessKey";
        String sharedAccessKey = "1234567890abcdefghijklmnopqrstvwxyz=";
        String connectionString = "HostName=" + iotHubName + "." + iotHostName + ";SharedAccessKeyName=" + sharedAccessKeyName + ";" + policyName + "=" + sharedAccessKey;
        IotHubConnectionString iotHubConnectionString = IotHubConnectionStringBuilder.createIotHubConnectionString(connectionString);
        // Act
        Deencapsulation.invoke(iotHubConnectionString, "parse", String.class, iotHubConnectionString);        
    }

    // Tests_SRS_SERVICE_SDK_JAVA_IOTHUBCONNECTIONSTRINGBUILDER_12_006: [The function shall throw IllegalArgumentException if the input string is empty or null]
    // Assert
    @Test (expected = IllegalArgumentException.class)
    public void parse_connectionString_empty() throws Exception
    {
        // Arrange
        String iotHubName = "IOTHUBNAME";
        String iotHostName = "HOSTNAME";
        String sharedAccessKeyName = "ACCESSKEYNAME";
        String policyName = "SharedAccessKey";
        String sharedAccessKey = "1234567890abcdefghijklmnopqrstvwxyz=";
        String connectionString = "HostName=" + iotHubName + "." + iotHostName + ";SharedAccessKeyName=" + sharedAccessKeyName + ";" + policyName + "=" + sharedAccessKey;
        IotHubConnectionString iotHubConnectionString = IotHubConnectionStringBuilder.createIotHubConnectionString(connectionString);
        // Act
        Deencapsulation.invoke(iotHubConnectionString, "parse", "" , iotHubConnectionString);   
    }

    // Tests_SRS_SERVICE_SDK_JAVA_IOTHUBCONNECTIONSTRINGBUILDER_12_007: [The function shall throw IllegalArgumentException if the input target itoHubConnectionString is null]
    // Assert
    @Test (expected = IllegalArgumentException.class)
    public void parse_iotHubConnectionString_null() throws Exception
    {
        // Arrange
        String iotHubName = "IOTHUBNAME";
        String iotHostName = "HOSTNAME";
        String sharedAccessKeyName = "ACCESSKEYNAME";
        String policyName = "SharedAccessKey";
        String sharedAccessKey = "1234567890abcdefghijklmnopqrstvwxyz=";
        String connectionString = "HostName=" + iotHubName + "." + iotHostName + ";SharedAccessKeyName=" + sharedAccessKeyName + ";" + policyName + "=" + sharedAccessKey;
        IotHubConnectionString iotHubConnectionString = IotHubConnectionStringBuilder.createIotHubConnectionString(connectionString);
        // Act
        Deencapsulation.invoke(iotHubConnectionString, "parse", connectionString, String.class);  
    }

    // Tests_SRS_SERVICE_SDK_JAVA_IOTHUBCONNECTIONSTRINGBUILDER_12_008: [The function shall throw exception if tokenizing or parsing failed]
    // Assert
    @Test (expected = Exception.class)
    public void parse_invalid_host_name() throws Exception
    {
        // Arrange
        String iotHubName = "b.c.d";
        String hostName = "@" + iotHubName;
        String sharedAccessKeyName = "ACCESSKEYNAME";
        String policyName = "SharedAccessKey";
        String sharedAccessKey = "1234567890abcdefghijklmnopqrstvwxyz=";
        String connectionString = "HostName=" + hostName + ";SharedAccessKeyName=" + sharedAccessKeyName + ";" + policyName + "=" + sharedAccessKey;
        String userString = sharedAccessKeyName +  "@SAS.root." + iotHubName;
        // Act
        IotHubConnectionString iotHubConnectionString = IotHubConnectionStringBuilder.createIotHubConnectionString(connectionString);
    }

    // Tests_SRS_SERVICE_SDK_JAVA_IOTHUBCONNECTIONSTRINGBUILDER_12_008: [The function shall throw exception if tokenizing or parsing failed]
    // Assert
    @Test (expected = Exception.class)
    public void parse_invalid_key_missing() throws Exception
    {
        // Arrange
        String connectionString = "a=A;b=B;HostName=";
        // Act
        IotHubConnectionString iotHubConnectionString = IotHubConnectionStringBuilder.createIotHubConnectionString(connectionString);
    }

    // Tests_SRS_SERVICE_SDK_JAVA_IOTHUBCONNECTIONSTRINGBUILDER_12_008: [The function shall throw exception if tokenizing or parsing failed]
    // Assert
    @Test (expected = Exception.class)
    public void parse_invalid_missing_iot_hubName() throws Exception
    {
        // Arrange
        String iotHubName = "";
        String hostName = "" + iotHubName;
        String sharedAccessKeyName = "XXX";
        String policyName = "SharedAccessKey";
        String sharedAccessKey = "1234567890abcdefghijklmnopqrstvwxyz=";
        String sharedAccessSignature = "";
        String connectionString = "HostName=" + hostName + ";SharedAccessKeyName=" + sharedAccessKeyName + ";" + policyName + "=" + sharedAccessKey + "SharedAccessSignature=" + sharedAccessSignature;
        // Act
        IotHubConnectionString iotHubConnectionString = IotHubConnectionStringBuilder.createIotHubConnectionString(connectionString);
    }

    // Tests_SRS_SERVICE_SDK_JAVA_IOTHUBCONNECTIONSTRINGBUILDER_12_009: [The function shall tokenize and parse the given connection string and fill up the target IotHubConnectionString object with proper values]
    @Test
    public void parse_good_case_simple_host_name() throws Exception
    {
        // Arrange
        String iotHubName = "";
        String hostName = "HOSTNAME" + iotHubName;
        String sharedAccessKeyName = "ACCESSKEYNAME";
        String policyName = "SharedAccessKey";
        String sharedAccessKey = "1234567890abcdefghijklmnopqrstvwxyz=";
        String connectionString = "HostName=" + hostName + ";SharedAccessKeyName=" + sharedAccessKeyName + ";" + policyName + "=" + sharedAccessKey;
        String userString = sharedAccessKeyName +  "@SAS.root." + iotHubName;
        // Act
        IotHubConnectionString iotHubConnectionString = IotHubConnectionStringBuilder.createIotHubConnectionString(connectionString);
        // Assert
        assertEquals("Parser error: HostName mismatch!", hostName, iotHubConnectionString.getHostName());
        assertEquals("Parser error: SharedAccessKeyName mismatch!", sharedAccessKeyName, iotHubConnectionString.getSharedAccessKeyName());
        assertEquals("Parser error: SharedAccessKey mismatch!", iotHubConnectionString.getSharedAccessKey(), sharedAccessKey);
        assertEquals("Parser error: SharedAccessSignature mismatch!", "", iotHubConnectionString.getSharedAccessSignature());
        assertEquals("Parser error: IotHubName mismatch!", iotHubName, iotHubConnectionString.getIotHubName());
    }

    // Tests_SRS_SERVICE_SDK_JAVA_IOTHUBCONNECTIONSTRINGBUILDER_12_009: [The function shall tokenize and parse the given connection string and fill up the target IotHubConnectionString object with proper values]
    @Test
    public void parse_good_case() throws Exception
    {
        // Arrange
        String iotHubName = "IOTHUBNAME";
        String iotHostName = "HOSTNAME";
        String sharedAccessKeyName = "ACCESSKEYNAME";
        String policyName = "SharedAccessKey";
        String sharedAccessKey = "1234567890abcdefghijklmnopqrstvwxyz=";
        String connectionString = "HostName=" + iotHubName + "." + iotHostName + ";SharedAccessKeyName=" + sharedAccessKeyName + ";" + policyName + "=" + sharedAccessKey;
        String userString = sharedAccessKeyName +  "@SAS.root." + iotHubName;
        IotHubConnectionString iotHubConnectionString = IotHubConnectionStringBuilder.createIotHubConnectionString(connectionString);
        // Act
        Deencapsulation.invoke(iotHubConnectionString, "parse", connectionString, iotHubConnectionString);
        
        // Assert
        assertEquals("Parser error: HostName mismatch!", iotHubName + "." + iotHostName, iotHubConnectionString.getHostName());
        assertEquals("Parser error: SharedAccessKeyName mismatch!", sharedAccessKeyName, iotHubConnectionString.getSharedAccessKeyName());
        assertEquals("Parser error: SharedAccessKey mismatch!", iotHubConnectionString.getSharedAccessKey(), sharedAccessKey);
        assertEquals("Parser error: SharedAccessSignature mismatch!", "", iotHubConnectionString.getSharedAccessSignature());
        assertEquals("Parser error: IotHubName mismatch!", iotHubName, iotHubConnectionString.getIotHubName());
    }

    // Tests_SRS_SERVICE_SDK_JAVA_IOTHUBCONNECTIONSTRINGBUILDER_12_010: [The function shall create new ServiceAuthenticationWithSharedAccessPolicyToken and set the authenticationMethod if sharedAccessKey is not defined]
    @Test
    public void parse_SharedAccessKey_not_defined() throws Exception
    {
        // Arrange
        String iotHubName = "b.c.d";
        String hostName = "HOSTNAME." + iotHubName;
        String sharedAccessKeyName = "ACCESSKEYNAME";
        String policyName = "SharedAccessSignature";
        String sharedAccessSignature = "1234567890abcdefghijklmnopqrstvwxyz";
        String connectionString = "HostName=" + hostName + ";SharedAccessKeyName=" + sharedAccessKeyName + ";" + policyName + "=" + sharedAccessSignature;
        // Assert
        new Expectations()
        {
            {
                new ServiceAuthenticationWithSharedAccessPolicyToken(anyString, anyString);
            }
        };
        // Act
        IotHubConnectionString iotHubConnectionString = IotHubConnectionStringBuilder.createIotHubConnectionString(connectionString);
    }

    // Tests_SRS_SERVICE_SDK_JAVA_IOTHUBCONNECTIONSTRINGBUILDER_12_011: [The function shall create new ServiceAuthenticationWithSharedAccessPolicyKey and set the authenticationMethod if the sharedAccessSignature is not defined]
    @Test
    public void parse_SharedAccessSignature_not_defined() throws Exception
    {
        // Arrange
        String iotHubName = "b.c.d";
        String hostName = "HOSTNAME." + iotHubName;
        String sharedAccessKeyName = "ACCESSKEYNAME";
        String policyName = "SharedAccessKey";
        String sharedAccessKey = "1234567890abcdefghijklmnopqrstvwxyz=";
        String connectionString = "HostName=" + hostName + ";SharedAccessKeyName=" + sharedAccessKeyName + ";" + policyName + "=" + sharedAccessKey;
        // Assert
        new Expectations()
        {
            {
                new ServiceAuthenticationWithSharedAccessPolicyKey(anyString, anyString);
            }
        };
        // Act
        IotHubConnectionString iotHubConnectionString = IotHubConnectionStringBuilder.createIotHubConnectionString(connectionString);
    }

    // Tests_SRS_SERVICE_SDK_JAVA_IOTHUBCONNECTIONSTRINGBUILDER_12_012: [The function shall validate the connection string object]
    @Test
    public void parse_call_validate() throws Exception
    {
        // Arrange
        String iotHubName = "b.c.d";
        String hostName = "HOSTNAME." + iotHubName;
        String sharedAccessKeyName = "ACCESSKEYNAME";
        String policyName = "SharedAccessKey";
        String sharedAccessKey = "1234567890abcdefghijklmnopqrstvwxyz=";
        String connectionString = "HostName=" + hostName + ";SharedAccessKeyName=" + sharedAccessKeyName + ";" + policyName + "=" + sharedAccessKey;
        IotHubConnectionString iotHubConnectionString = IotHubConnectionStringBuilder.createIotHubConnectionString(connectionString);
        // Assert
        new Expectations()
        {
            {
                
                Deencapsulation.invoke(iotHubConnectionString, "validate", iotHubConnectionString);
            }
        };
        // Act
        Deencapsulation.invoke(iotHubConnectionString, "parse", connectionString, iotHubConnectionString);
    }

    // Tests_SRS_SERVICE_SDK_JAVA_IOTHUBCONNECTIONSTRINGBUILDER_12_013: [The function shall return the substring of the host name until the first “.” character]
    @Test
    public void parseIotHubName_good_case_one_separator() throws Exception
    {
        // Arrange
        String iotHubName = "IOTHUBNAME";
        String iotHostName = "HOSTNAME";
        String sharedAccessKeyName = "ACCESSKEYNAME";
        String policyName = "SharedAccessKey";
        String sharedAccessKey = "1234567890abcdefghijklmnopqrstvwxyz=";
        String connectionString = "HostName=" + iotHubName + "." + iotHostName + ";SharedAccessKeyName=" + sharedAccessKeyName + ";" + policyName + "=" + sharedAccessKey;
        String userString = sharedAccessKeyName +  "@SAS.root." + iotHubName;
        IotHubConnectionString iotHubConnectionString = IotHubConnectionStringBuilder.createIotHubConnectionString(connectionString);
        // Act
        String actual = Deencapsulation.invoke(iotHubConnectionString, "parseIotHubName", iotHubConnectionString);
        // Assert
        assertEquals("Parser error: IotHubName mismatch!", iotHubName, actual);
    }

    // Tests_SRS_SERVICE_SDK_JAVA_IOTHUBCONNECTIONSTRINGBUILDER_12_013: [The function shall return the substring of the host name until the first “.” character]
    @Test
    public void parseIotHubName_good_case_multi_separator() throws Exception
    {
        // Arrange
        String iotHubName = "IOTHUBNAME";
        String iotHostName = "HOSTNAME.POSTFIX1.POSTFIX2";
        String sharedAccessKeyName = "ACCESSKEYNAME";
        String policyName = "SharedAccessKey";
        String sharedAccessKey = "1234567890abcdefghijklmnopqrstvwxyz=";
        String connectionString = "HostName=" + iotHubName + "." + iotHostName + ";SharedAccessKeyName=" + sharedAccessKeyName + ";" + policyName + "=" + sharedAccessKey;
        String userString = sharedAccessKeyName +  "@SAS.root." + iotHubName;
        IotHubConnectionString iotHubConnectionString = IotHubConnectionStringBuilder.createIotHubConnectionString(connectionString);
        // Act
        String actual = Deencapsulation.invoke(iotHubConnectionString, "parseIotHubName", iotHubConnectionString);
        // Assert
        assertEquals("Parser error: IotHubName mismatch!", iotHubName, actual);
    }

    // Tests_SRS_SERVICE_SDK_JAVA_IOTHUBCONNECTIONSTRINGBUILDER_12_014: [The function shall return empty string if “.” character was not found]
    @Test
    public void parseIotHubName_no_separator() throws Exception
    {
        // Arrange
        String iotHubName = "IOTHUBNAME";
        String iotHostName = "HOSTNAME";
        String sharedAccessKeyName = "ACCESSKEYNAME";
        String policyName = "SharedAccessKey";
        String sharedAccessKey = "1234567890abcdefghijklmnopqrstvwxyz=";
        String connectionString = "HostName=" + iotHubName + ";SharedAccessKeyName=" + sharedAccessKeyName + ";" + policyName + "=" + sharedAccessKey;
        String userString = sharedAccessKeyName +  "@SAS.root." + iotHubName;
        IotHubConnectionString iotHubConnectionString = IotHubConnectionStringBuilder.createIotHubConnectionString(connectionString);
        String expected = "";
        // Act
        String actual = Deencapsulation.invoke(iotHubConnectionString, "parseIotHubName", iotHubConnectionString);
        // Assert
        assertEquals("Parser error: IotHubName mismatch!", expected, actual);
    }

    // Tests_SRS_SERVICE_SDK_JAVA_IOTHUBCONNECTIONSTRINGBUILDER_12_015: [The function shall throw IllegalArgumentException if the sharedAccessKeyName of the input itoHubConnectionString is null or empty]
    // Assert
    @Test (expected = IllegalArgumentException.class)
    public void validate_sharedAccessKeyName_empty() throws Exception
    {
        // Arrange
        String iotHubName = "IOTHUBNAME";
        String iotHostName = "HOSTNAME";
        String sharedAccessKeyName = "";
        String policyName = "SharedAccessKey";
        String sharedAccessKey = "1234567890abcdefghijklmnopqrstvwxyz=";
        String connectionString = "HostName=" + iotHubName + ";SharedAccessKeyName=" + sharedAccessKeyName + ";" + policyName + "=" + sharedAccessKey;
        String userString = sharedAccessKeyName +  "@SAS.root." + iotHubName;
        IotHubConnectionString iotHubConnectionString = IotHubConnectionStringBuilder.createIotHubConnectionString(connectionString);
        // Act
        Deencapsulation.invoke(iotHubConnectionString, "validate", iotHubConnectionString);
    }

    // Tests_SRS_SERVICE_SDK_JAVA_IOTHUBCONNECTIONSTRINGBUILDER_12_016: [The function shall throw IllegalArgumentException if either of the sharedAccessKey or the sharedAccessSignature of the input itoHubConnectionString is null or empty]
    // Assert
    @Test (expected = IllegalArgumentException.class)
    public void validate_sharedAccessKey_empty() throws Exception
    {
        // Arrange
        String iotHubName = "IOTHUBNAME";
        String iotHostName = "HOSTNAME";
        String sharedAccessKeyName = "ACCESSKEYNAME";
        String policyName = "SharedAccessKey";
        String sharedAccessKey = "";
        String connectionString = "HostName=" + iotHubName + ";SharedAccessKeyName=" + sharedAccessKeyName + ";" + policyName + "=" + sharedAccessKey;
        String userString = sharedAccessKeyName +  "@SAS.root." + iotHubName;
        IotHubConnectionString iotHubConnectionString = IotHubConnectionStringBuilder.createIotHubConnectionString(connectionString);
        // Act
        Deencapsulation.invoke(iotHubConnectionString, "validate", iotHubConnectionString);
    }

    // Tests_SRS_SERVICE_SDK_JAVA_IOTHUBCONNECTIONSTRINGBUILDER_12_016: [The function shall throw IllegalArgumentException if either of the sharedAccessKey or the sharedAccessSignature of the input itoHubConnectionString is null or empty]
    // Assert
    @Test (expected = IllegalArgumentException.class)
    public void validate_sharedAccessSignature_empty() throws Exception
    {
        // Arrange
        String iotHubName = "IOTHUBNAME";
        String iotHostName = "HOSTNAME";
        String sharedAccessKeyName = "ACCESSKEYNAME";
        String policyName = "SharedAccessSignature";
        String sharedAccessKey = "";
        String connectionString = "HostName=" + iotHubName + ";SharedAccessKeyName=" + sharedAccessKeyName + ";" + policyName + "=" + sharedAccessKey;
        String userString = sharedAccessKeyName +  "@SAS.root." + iotHubName;
        IotHubConnectionString iotHubConnectionString = IotHubConnectionStringBuilder.createIotHubConnectionString(connectionString);
        // Act
        Deencapsulation.invoke(iotHubConnectionString, "validate", iotHubConnectionString);
    }

    // Tests_SRS_SERVICE_SDK_JAVA_IOTHUBCONNECTIONSTRINGBUILDER_12_017: [The function shall call property validation functions for hostname, sharedAccessKeyName, sharedAccessKey, sharedAccessSignature]
    @Test
    public void validate_call_validation_functions() throws Exception
    {
        // Arrange
        String iotHubName = "b.c.d";
        String hostName = "HOSTNAME." + iotHubName;
        String sharedAccessKeyName = "ACCESSKEYNAME";
        String policyName = "SharedAccessKey";
        String sharedAccessKey = "1234567890abcdefghijklmnopqrstvwxyz=";
        String connectionString = "HostName=" + hostName + ";SharedAccessKeyName=" + sharedAccessKeyName + ";" + policyName + "=" + sharedAccessKey;
        IotHubConnectionString iotHubConnectionString = IotHubConnectionStringBuilder.createIotHubConnectionString(connectionString);

        // Assert
        new Expectations()
        {
            {
                Deencapsulation.invoke(iotHubConnectionString, "validateFormat", anyString, anyString);
                Deencapsulation.invoke(iotHubConnectionString, "validateFormatIfSpecified", anyString, anyString);
                Deencapsulation.invoke(iotHubConnectionString, "validateFormatIfSpecified", anyString, anyString);
                Deencapsulation.invoke(iotHubConnectionString, "validateFormatIfSpecified", anyString, anyString);
            }
        };
        // Act
        Deencapsulation.invoke(iotHubConnectionString, "validate", iotHubConnectionString);
    }

    // Tests_SRS_SERVICE_SDK_JAVA_IOTHUBCONNECTIONSTRINGBUILDER_12_018: [The function shall validate the property value against the given regex]
    @Test
    public void validateFormat_good_case() throws Exception
    {
        // Arrange
        String regex = "[a-zA-Z0-9_\\-\\.]+$";
        String iotHubName = "b.c.d";
        String hostName = "HOSTNAME." + iotHubName;
        String sharedAccessKeyName = "ACCESSKEYNAME";
        String policyName = "SharedAccessKey";
        String sharedAccessKey = "1234567890abcdefghijklmnopqrstvwxyz=";
        String connectionString = "HostName=" + hostName + ";SharedAccessKeyName=" + sharedAccessKeyName + ";" + policyName + "=" + sharedAccessKey;
        IotHubConnectionString iotHubConnectionString = IotHubConnectionStringBuilder.createIotHubConnectionString(connectionString);
        // Act - Assert
        
        Deencapsulation.invoke(iotHubConnectionString, "validateFormat", hostName, regex);
    }

    // Tests_SRS_SERVICE_SDK_JAVA_IOTHUBCONNECTIONSTRINGBUILDER_12_019: [The function shall throw IllegalArgumentException if the value did not match with the pattern]
    @Test (expected = IllegalArgumentException.class)
    public void validateFormat_value_did_not_match() throws Exception
    {
        // Arrange
        String regex = "[a-zA-Z0-9_\\-\\.]+$";
        String iotHubName = "b.c.d";
        String hostName = "HOSTNAME." + iotHubName;
        String sharedAccessKeyName = "ACCESSKEYNAME";
        String policyName = "SharedAccessKey";
        String sharedAccessKey = "1234567890abcdefghijklmnopqrstvwxyz=";
        String connectionString = "HostName=" + hostName + ";SharedAccessKeyName=" + sharedAccessKeyName + ";" + policyName + "=" + sharedAccessKey;
        IotHubConnectionString iotHubConnectionString = IotHubConnectionStringBuilder.createIotHubConnectionString(connectionString);
        // Act - Assert
        Deencapsulation.invoke(iotHubConnectionString, "validateFormat", "+++", "hostName", regex);
    }

    // Tests_SRS_SERVICE_SDK_JAVA_IOTHUBCONNECTIONSTRINGBUILDER_12_020: [The function shall validate the property value against the given regex if the value is not null or empty]
    @Test
    public void validateFormatIfSpecified_good_case() throws Exception
    {
        // Arrange
        String regex = "[a-zA-Z0-9_\\-\\.]+$";
        String iotHubName = "b.c.d";
        String hostName = "HOSTNAME." + iotHubName;
        String sharedAccessKeyName = "ACCESSKEYNAME";
        String policyName = "SharedAccessKey";
        String sharedAccessKey = "1234567890abcdefghijklmnopqrstvwxyz=";
        String connectionString = "HostName=" + hostName + ";SharedAccessKeyName=" + sharedAccessKeyName + ";" + policyName + "=" + sharedAccessKey;
        IotHubConnectionString iotHubConnectionString = IotHubConnectionStringBuilder.createIotHubConnectionString(connectionString);
        // Assert
        new Expectations()
        {
            {
            	Deencapsulation.invoke(iotHubConnectionString, "validateFormat", anyString, anyString);
            }
        };
        // Act
        Deencapsulation.invoke(iotHubConnectionString, "validateFormatIfSpecified", hostName, regex);
    }

    // Tests_SRS_SERVICE_SDK_JAVA_IOTHUBCONNECTIONSTRINGBUILDER_12_020: [The function shall validate the property value against the given regex if the value is not null or empty]
    @Test
    public void validateFormatIfSpecified_value_empty() throws Exception
    {
        // Arrange
        String regex = "[a-zA-Z0-9_\\-\\.]+$";
        String iotHubName = "b.c.d";
        String hostName = "HOSTNAME." + iotHubName;
        String sharedAccessKeyName = "ACCESSKEYNAME";
        String policyName = "SharedAccessKey";
        String sharedAccessKey = "1234567890abcdefghijklmnopqrstvwxyz=";
        String connectionString = "HostName=" + hostName + ";SharedAccessKeyName=" + sharedAccessKeyName + ";" + policyName + "=" + sharedAccessKey;
        IotHubConnectionString iotHubConnectionString = IotHubConnectionStringBuilder.createIotHubConnectionString(connectionString);
        // Act - Assert
        Deencapsulation.invoke(iotHubConnectionString, "validateFormatIfSpecified", "", regex);
    }

    // Tests_SRS_SERVICE_SDK_JAVA_IOTHUBCONNECTIONSTRINGBUILDER_12_021: [The function shall validate the given hostName]
    // Tests_SRS_SERVICE_SDK_JAVA_IOTHUBCONNECTIONSTRINGBUILDER_12_022: [The function shall parse and set the hostname to the given target iotHubConnectionString object]
    @Test
    public void setHostName_good_case() throws Exception
    {
        // Arrange
        String regex = "[a-zA-Z0-9_\\-\\.]+$";
        String iotHubName = "b.c.d";
        String hostName = "HOSTNAME." + iotHubName;
        String sharedAccessKeyName = "ACCESSKEYNAME";
        String policyName = "SharedAccessKey";
        String sharedAccessKey = "1234567890abcdefghijklmnopqrstvwxyz=";
        String connectionString = "HostName=" + hostName + ";SharedAccessKeyName=" + sharedAccessKeyName + ";" + policyName + "=" + sharedAccessKey;
        IotHubConnectionString iotHubConnectionString = IotHubConnectionStringBuilder.createIotHubConnectionString(connectionString);
        new Expectations()
        {
            {
                Deencapsulation.invoke(iotHubConnectionString, "validateFormat", hostName, regex);
            }
        };
        // Act
        Deencapsulation.invoke(iotHubConnectionString, "setHostName", hostName, iotHubConnectionString);
        
        String actualHostName = Deencapsulation.getField(iotHubConnectionString, "hostName");

        // Assert
        assertEquals(hostName, actualHostName);
    }

    // Tests_SRS_SERVICE_SDK_JAVA_IOTHUBCONNECTIONSTRINGBUILDER_12_023: [The function shall populate and set the authenticationMethod on the given target iotHubConnectionString object]
    @Test
    public void setAuthenticationMethod_good_case_key() throws Exception
    {
        // Arrange
        String regex = "[a-zA-Z0-9_\\-\\.]+$";
        String iotHubName = "b.c.d";
        String hostName = "HOSTNAME." + iotHubName;
        String sharedAccessKeyName = "ACCESSKEYNAME";
        String policyName = "SharedAccessKey";
        String sharedAccessKey = "1234567890abcdefghijklmnopqrstvwxyz=";
        String connectionString = "HostName=" + hostName + ";SharedAccessKeyName=" + sharedAccessKeyName + ";" + policyName + "=" + sharedAccessKey;
        IotHubConnectionString iotHubConnectionString = IotHubConnectionStringBuilder.createIotHubConnectionString(connectionString);
        String newPolicyName = "XXX";
        String newPolicyKey = "YYY";
        ServiceAuthenticationWithSharedAccessPolicyKey auth = new ServiceAuthenticationWithSharedAccessPolicyKey(newPolicyName, newPolicyKey);
        new Expectations()
        {
            {
                Deencapsulation.invoke(iotHubConnectionString, "validateFormat", hostName, regex);
            }
        };
        // Act
        Deencapsulation.invoke(iotHubConnectionString, "setAuthenticationMethod", auth, iotHubConnectionString);
        
        // Assert
        assertEquals(newPolicyName, iotHubConnectionString.getSharedAccessKeyName());
        assertEquals(newPolicyKey, iotHubConnectionString.getSharedAccessKey());
        assertNull(iotHubConnectionString.getSharedAccessSignature());
    }

    // Tests_SRS_SERVICE_SDK_JAVA_IOTHUBCONNECTIONSTRINGBUILDER_12_023: [The function shall populate and set the authenticationMethod on the given target iotHubConnectionString object]
    @Test
    public void setAuthenticationMethod_good_case_token() throws Exception
    {
        // Arrange
        String regex = "[a-zA-Z0-9_\\-\\.]+$";
        String iotHubName = "b.c.d";
        String hostName = "HOSTNAME." + iotHubName;
        String sharedAccessKeyName = "ACCESSKEYNAME";
        String policyName = "SharedAccessKey";
        String sharedAccessKey = "1234567890abcdefghijklmnopqrstvwxyz=";
        String connectionString = "HostName=" + hostName + ";SharedAccessKeyName=" + sharedAccessKeyName + ";" + policyName + "=" + sharedAccessKey;
        IotHubConnectionString iotHubConnectionString = IotHubConnectionStringBuilder.createIotHubConnectionString(connectionString);
        String newPolicyName = "XXX";
        String newPolicyKey = "YYY";
        ServiceAuthenticationWithSharedAccessPolicyToken auth = new ServiceAuthenticationWithSharedAccessPolicyToken(newPolicyName, newPolicyKey);
        new Expectations()
        {
            {
                Deencapsulation.invoke(iotHubConnectionString, "validateFormat", hostName, regex);
            }
        };
        // Act
        Deencapsulation.invoke(iotHubConnectionString, "setAuthenticationMethod", auth, iotHubConnectionString);
        // Assert
        assertEquals(auth, iotHubConnectionString.getAuthenticationMethod());
        assertEquals(newPolicyName, iotHubConnectionString.getSharedAccessKeyName());
        assertEquals(newPolicyKey, iotHubConnectionString.getSharedAccessSignature());
        assertNull(iotHubConnectionString.getSharedAccessKey());
    }
}