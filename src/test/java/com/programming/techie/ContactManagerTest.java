package com.programming.techie;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class ContactManagerTest {


    ContactManager contactManager;
    @BeforeAll
    public static void setupAll(){
        System.out.println("Should Print Before All Tests");
    };

    @BeforeEach
//    Create instance of object before each @test
    public void setup(){
        contactManager=new ContactManager();
    }

    @Test
    @DisplayName("Should create Contact")
    public void shouldCreateContact() {

//        ContactManager contactManager = new ContactManager();
        contactManager.addContact("John", "Doe", "0123456789");

//       Postive Test Case
        Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
        Assertions.assertEquals(1, contactManager.getAllContacts().size());
    }
        @Test
        @DisplayName("Should Not Create Contact When First Name is Null")
        public void shouldThrowRuntimeExceptionWhenFirstNameIsNull(){
//            ContactManager contactManager= new ContactManager();
            contactManager.addContact("John","Doe","0123456789");
            Assertions.assertThrows(RuntimeException.class,() -> {
                contactManager.addContact(null,"Doe","0123456789");
            });

        }

    @Test
    @DisplayName("Should Not Create Contact When Last Name is Null")
    public void shouldThrowRuntimeExceptionWhenLastNameIsNull() {
//        ContactManager contactManager = new ContactManager();
        Assertions.assertThrows(RuntimeException.class, () -> {
            contactManager.addContact("John", null, "0123456789");
        });
    }

    @Test
    @DisplayName("Should Not Create Contact When Phone Number is Null")
    public void shouldThrowRuntimeExceptionWhenPhoneNumberIsNull() {
//        ContactManager contactManager = new ContactManager();
        Assertions.assertThrows(RuntimeException.class, () -> {
            contactManager.addContact("John", "Doe", "null");
        });


    }

    @Test
    @DisplayName("Should Create Contact Only on a MAC OS")
    @EnabledOnOs(value=OS.MAC,disabledReason = "Enabled Only On MAC OS")

    public void shouldCreateContactOnMAC(){
        contactManager.addContact("John","Doe","012345678");
        Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
        Assertions.assertEquals(1,contactManager.getAllContacts().size());
        Assertions.assertTrue(contactManager.getAllContacts().stream()
                .filter(contact -> contact.getFirstName().equals("John") &&
                        contact.getLastName().equals("Doe") &&
                        contact.getPhoneNumber().equals("012345678"))
                .findAny()
                .isPresent()

        );
    }


    @Test
    @DisplayName("Should Create Contact Only on a MAC OS")
    @EnabledOnOs(value= OS.WINDOWS,disabledReason = "Enabled Only On MAC OS")

    public void shouldCreateContactOnWindows(){
        contactManager.addContact("John","Doe","0123456789");
        Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
        Assertions.assertEquals(1,contactManager.getAllContacts().size());
        Assertions.assertTrue(contactManager.getAllContacts().stream()
                .filter(contact -> contact.getFirstName().equals("John") &&
                        contact.getLastName().equals("Doe") &&
                        contact.getPhoneNumber().equals("012345678"))
                .findAny()
                .isPresent()

        );
    }

    @Test
    @DisplayName("Test Contact Creation on Developer Machine")
    public void shoudlTestContactCreationOnDEV(){
        Assumptions.assumeTrue("DEV".equals(System.getProperty("ENV")));
        contactManager.addContact("John", "Doe", "0123456789");
        Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
        Assertions.assertEquals(1, contactManager.getAllContacts().size());

    }


    @DisplayName("Repeat Creation Test 5 Times")
    @RepeatedTest(value = 5,
    name = "Repeating contact creation test {currentRepetition} of {totalRepetitions}"
    )
    public void shouldTestContactCreationRepeatedly(){
        contactManager.addContact("John","Doe","0123456789");
        assertFalse(contactManager.getAllContacts().isEmpty());
        assertEquals(1,contactManager.getAllContacts().size());
    }


    @DisplayName("Repeat contact creation test 5 times")
    @ParameterizedTest
    @ValueSource(strings = {"0917474191","0917454191","0917404191","0917494191","0917424191"})
    public void shouldTestContactCreationsUsingValueSource(String phoneNumber){
        contactManager.addContact("John","Doe",phoneNumber);
        assertFalse(contactManager.getAllContacts().isEmpty());
        assertEquals(1,contactManager.getAllContacts().size());
    }

    @DisplayName("Method Source Case -Phone Number should match the required format")
    @ParameterizedTest
    @MethodSource("phoneNumberList")
    public void shouldTestPhoneNumberFormatUsingMethodSource(String phoneNumber){
        contactManager.addContact("John","Doe",phoneNumber);
        assertFalse(contactManager.getAllContacts().isEmpty());
        assertEquals(1,contactManager.getAllContacts().size());
    }

    private static List<String> phoneNumberList(){
        return Arrays.asList("0123456780","0267835677","0127639487");
    }

    @DisplayName("CSV File Source Case - Phone Number Should match the required format ")
    @ParameterizedTest
    @CsvFileSource(resources="/data.csv")
    public void shouldTestPhoneNumberFormatUsingCsvFileSource(String phoneNumber){
        contactManager.addContact("John","Doe",phoneNumber);
        assertFalse(contactManager.getAllContacts().isEmpty());
        assertEquals(1,contactManager.getAllContacts().size());
    }










}

