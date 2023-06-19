package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.example.demo.repositories.AppointmentRepository;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import java.time.LocalDateTime;
import java.time.format.*;
import com.example.demo.entities.*;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@TestInstance(Lifecycle.PER_CLASS)
class EntityUnitTest {

	@Autowired
	private TestEntityManager entityManager;

    @Autowired
    AppointmentRepository appointmentRepository;

    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();
	private Doctor d1;
    private Doctor d2;

	private Patient p1;
    private Patient p2;

    private Room r1;
    private Room r2;

    private Appointment a1;
    private Appointment a2;
    private Appointment a3;

    @Test
    void testDoctor(){
        //assertThat(false).isEqualTo(true);

        d1 = new Doctor();
        d1.setFirstName("Mock_doctor");
        d1.setLastName("Mock_lastname");
        d1.setAge(30);
        d1.setEmail("mockdoctor@gmail.com");

        assertEquals("Mock_doctor", d1.getFirstName());
        assertEquals("Mock_lastname", d1.getLastName());
        assertEquals(30, d1.getAge());
        assertEquals("mockdoctor@gmail.com", d1.getEmail());

    }

    @Test
    void testPatient(){

        p1 = new Patient();
        p1.setFirstName("Mock_patient");
        p1.setLastName("Mock_lastname");
        p1.setAge(20);
        p1.setEmail("patient1@mail.com");


        assertEquals("Mock_patient", p1.getFirstName());
        assertEquals("Mock_lastname", p1.getLastName());
        assertEquals(20, p1.getAge());
        assertEquals("patient1@mail.com", p1.getEmail());

    }

    @Test
    void testRoom(){


        r1 = new Room("Urology");
        assertEquals("Urology", r1.getRoomName());

    }

    @Test
    void testAppointment() throws Exception
    {
        p1 = new Patient("Jose Luis", "Olaya", 37, "j.olaya@email.com");
        p2 = new Patient("Paulino", "Antunez", 37, "p.antunez@email.com");
        d1 = new Doctor ("Perla", "Amalia", 24, "p.amalia@hospital.accwe");
        d2 = new Doctor ("Miren", "Iniesta", 24, "m.iniesta@hospital.accwe");
        r1 = new Room("Dermatology");
        r2 = new Room("Urology");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");

        LocalDateTime startsAt= LocalDateTime.parse("19:30 24/04/2023", formatter);
        LocalDateTime finishesAt = LocalDateTime.parse("20:30 24/04/2023", formatter);

        a1 = new Appointment(p1, d1, r1, startsAt, finishesAt);
        a2 = new Appointment(p2, d1, r1, startsAt, finishesAt);
        a3 = new Appointment(p2, d2, r2, startsAt, finishesAt);

        appointmentRepository.save(a1);
        mockMvc.perform(post("/api/appointment").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(a2)))
                .andExpect(status().isNotAcceptable());
        mockMvc.perform(post("/api/appointment").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(a3)))
                .andExpect(status().isNotAcceptable());

    }

    /** TODO
     * Implement tests for each Entity class: Doctor, Patient, Room and Appointment.
     * Make sure you are as exhaustive as possible. Coverage is checked ;)
     */
}
