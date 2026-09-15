package com.nivasasignal.property;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nivasasignal.property.dto.CreatePropertyRequest;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class PropertyControllerTest {

    @Mock
    private PropertyService propertyService;

    @InjectMocks
    private PropertyController propertyController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private Property sampleProperty;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(propertyController).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        sampleProperty = Property.builder()
                .id(1L)
                .title("Green Acres Villa")
                .propertyType(PropertyType.VILLA)
                .bhk("4BHK")
                .city("Pune")
                .locality("Baner")
                .address("Baner Hills Road")
                .priceInr(25000000L)
                .areaSqft(new BigDecimal("3200.00"))
                .availabilityStatus(AvailabilityStatus.AVAILABLE)
                .truthScore(92)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @Test
    @DisplayName("POST /api/v1/properties should return 201 Created and property response")
    void testCreateProperty() throws Exception {
        CreatePropertyRequest request = CreatePropertyRequest.builder()
                .title("Green Acres Villa")
                .propertyType(PropertyType.VILLA)
                .bhk("4BHK")
                .city("Pune")
                .locality("Baner")
                .address("Baner Hills Road")
                .priceInr(25000000L)
                .areaSqft(new BigDecimal("3200.00"))
                .availabilityStatus(AvailabilityStatus.AVAILABLE)
                .build();

        when(propertyService.createProperty(any(Property.class))).thenReturn(sampleProperty);

        mockMvc.perform(post("/api/v1/properties")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Green Acres Villa")))
                .andExpect(jsonPath("$.propertyType", is("VILLA")))
                .andExpect(jsonPath("$.city", is("Pune")))
                .andExpect(jsonPath("$.priceInr", is(25000000)))
                .andExpect(jsonPath("$.availabilityStatus", is("AVAILABLE")));

        verify(propertyService).createProperty(any(Property.class));
    }

    @Test
    @DisplayName("GET /api/v1/properties should return 200 OK and list of properties")
    void testGetAllProperties() throws Exception {
        when(propertyService.getAllProperties()).thenReturn(List.of(sampleProperty));

        mockMvc.perform(get("/api/v1/properties"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("Green Acres Villa")))
                .andExpect(jsonPath("$[0].city", is("Pune")));

        verify(propertyService).getAllProperties();
    }

    @Test
    @DisplayName("GET /api/v1/properties/{id} should return 200 OK when found")
    void testGetPropertyById_Found() throws Exception {
        when(propertyService.getPropertyById(1L)).thenReturn(sampleProperty);

        mockMvc.perform(get("/api/v1/properties/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Green Acres Villa")))
                .andExpect(jsonPath("$.city", is("Pune")));

        verify(propertyService).getPropertyById(1L);
    }

    @Test
    @DisplayName("GET /api/v1/properties/{id} should return 404 Not Found when entity does not exist")
    void testGetPropertyById_NotFound() throws Exception {
        when(propertyService.getPropertyById(99L))
                .thenThrow(new EntityNotFoundException("Property not found with id: 99"));

        mockMvc.perform(get("/api/v1/properties/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error", is("Property not found with id: 99")));

        verify(propertyService).getPropertyById(99L);
    }
}
