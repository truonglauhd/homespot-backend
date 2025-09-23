package com.homespot.controller;

import com.homespot.dto.EstateDTO;
import com.homespot.dto.CreateEstateRequest;
import com.homespot.service.EstateService;
import com.homespot.service.EstateTypeService;
import com.homespot.repository.*;
import com.homespot.repository.UserRepository;
import com.homespot.model.User;
import com.homespot.model.Estate;
import com.homespot.model.AgentHistory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/estates")
public class EstateController {
    @Autowired private EstateService estateService;
    @Autowired private EstateTypeService estateTypeService;
    @Autowired private ProvinceRepository provinceRepository;
    @Autowired private WardRepository wardRepository;
    @Autowired private AddressRepository addressRepository;
    @Autowired private HouseNumberRepository houseNumberRepository;
    @Autowired private AmenityRepository amenityRepository;
    @Autowired private FeatureRepository featureRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private EstateRepository estateRepository;
    @Autowired private AgentHistoryRepository agentHistoryRepository;

    @GetMapping
    public List<EstateDTO> getAllEstates() {
        return estateService.getAllEstates();
    }

    @GetMapping("/{id}")
    public EstateDTO getEstate(@PathVariable Integer id) {
        return estateService.getEstateById(id);
    }

    @GetMapping("/my-estates")
    public List<EstateDTO> getMyEstates() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        System.out.println("=== AUTHENTICATION DEBUG ===");
        System.out.println("Auth object: " + auth);
        System.out.println("Is authenticated: " + (auth != null && auth.isAuthenticated()));
        System.out.println("Principal: " + (auth != null ? auth.getPrincipal() : "null"));
        System.out.println("Name: " + (auth != null ? auth.getName() : "null"));
        System.out.println("Authorities: " + (auth != null ? auth.getAuthorities() : "null"));
        System.out.println("============================");

        if (auth == null || !auth.isAuthenticated()) {
            System.out.println("THROWING UNAUTHORIZED - AUTH IS NULL OR NOT AUTHENTICATED");
            throw new RuntimeException("Unauthorized");
        }

        String email = auth.getName();
        System.out.println("Looking for user with email: " + email);

        User user = userRepository.findByEmail(email).orElseThrow(() -> {
            System.out.println("USER NOT FOUND FOR EMAIL: " + email);
            return new RuntimeException("User not found");
        });

        System.out.println("User found: " + user.getId());
        return estateService.getEstatesByUserId(user.getId());
    }

    @GetMapping("/my-unassigned-estates")
    public List<EstateDTO> getMyUnassignedEstates() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        System.out.println("=== MY UNASSIGNED ESTATES ENDPOINT CALLED ===");
        
        if (auth == null || !auth.isAuthenticated()) {
            System.out.println("THROWING UNAUTHORIZED - AUTH IS NULL OR NOT AUTHENTICATED");
            throw new RuntimeException("Unauthorized");
        }

        String email = auth.getName();
        System.out.println("Looking for user with email: " + email);

        User user = userRepository.findByEmail(email).orElseThrow(() -> {
            System.out.println("USER NOT FOUND FOR EMAIL: " + email);
            return new RuntimeException("User not found");
        });

        System.out.println("User found: " + user.getId());
        
        List<Estate> userEstates = estateRepository.findByOwnerId(user.getId());
        System.out.println("Total user estates: " + userEstates.size());
        
        List<Estate> unassignedEstates = userEstates.stream()
            .filter(estate -> {
                Optional<AgentHistory> activeAssignment = agentHistoryRepository
                    .findByEstateAndUnassignedAtIsNull(estate);
                boolean hasActiveAgent = activeAssignment.isPresent();
                System.out.println("Estate ID " + estate.getId() + " has active agent: " + hasActiveAgent);
                return !hasActiveAgent; 
            })
            .collect(Collectors.toList());

        System.out.println("Unassigned estates: " + unassignedEstates.size());
        
        return estateService.convertEstatesToDTOs(unassignedEstates);
    }

    @PutMapping("/{id}")
    public EstateDTO updateEstate(@PathVariable Integer id, @RequestBody CreateEstateRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new RuntimeException("Unauthorized");
        }
        String email = auth.getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        return estateService.updateEstate(id, user.getId(), request);
    }

    @DeleteMapping("/{id}")
    public void deleteEstate(@PathVariable Integer id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new RuntimeException("Unauthorized");
        }
        String email = auth.getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        estateService.deleteEstate(id, user.getId());
    }

    @GetMapping("/types")
    public List<OptionDTO> getAllEstateTypes() {
        return estateTypeService.getAllEstateTypes().stream()
                .map(t -> new OptionDTO(t.getId(), t.getName()))
                .collect(Collectors.toList());
    }

    @GetMapping("/provinces")
    public List<OptionDTO> listProvinces() {
        return provinceRepository.findAll().stream()
                .map(p -> new OptionDTO(p.getId(), p.getName()))
                .collect(Collectors.toList());
    }

    @GetMapping("/wards")
    public List<OptionDTO> listWards(@RequestParam Integer provinceId) {
        return wardRepository.findByProvinceId(provinceId).stream()
                .map(w -> new OptionDTO(w.getId(), w.getName()))
                .collect(Collectors.toList());
    }

    @GetMapping("/addresses")
    public List<OptionDTO> listAddresses(@RequestParam Integer wardId) {
        return addressRepository.findByWardId(wardId).stream()
                .map(a -> new OptionDTO(a.getId(), a.getName()))
                .collect(Collectors.toList());
    }

    @GetMapping("/house-numbers")
    public List<OptionDTO> listHouseNumbers(@RequestParam Integer addressId) {
        return houseNumberRepository.findByAddressId(addressId).stream()
                .map(h -> new OptionDTO(h.getId(), h.getNumber()))
                .collect(Collectors.toList());
    }

    @GetMapping("/amenities")
    public List<OptionDTO> listAmenities() {
        return amenityRepository.findAll().stream()
                .map(am -> new OptionDTO(am.getId(), am.getName()))
                .collect(Collectors.toList());
    }

    @GetMapping("/features")
    public List<OptionDTO> listFeatures() {
        return featureRepository.findAll().stream()
                .map(f -> new OptionDTO(f.getId(), f.getName()))
                .collect(Collectors.toList());
    }

    @PostMapping
    public Object createEstate(@RequestBody CreateEstateRequest request) {
        System.out.println("=== ESTATE CONTROLLER CALLED ===");
        System.out.println("CreateEstateRequest received: " + request);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Authentication: " + auth);
        System.out.println("Is authenticated: " + (auth != null && auth.isAuthenticated()));

        if (auth == null || !auth.isAuthenticated()) {
            System.out.println("AUTHENTICATION FAILED - throwing RuntimeException");
            throw new RuntimeException("Unauthorized");
        }
        String email = auth.getName();
        System.out.println("User email: " + email);

        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        System.out.println("User found: " + user.getId());
        System.out.println("Calling estateService.createEstate...");

        return estateService.createEstate(user.getId(), request);
    }

    @PostMapping("/test")
    public String testPost() {
        System.out.println("=== TEST POST ENDPOINT CALLED ===");
        return "Test POST endpoint working";
    }

    @PostMapping("/assign-agent")
    public ResponseEntity<?> assignAgent(@RequestBody AgentAssignmentRequest request) {
        try {
            System.out.println("=== ASSIGN AGENT ENDPOINT CALLED ===");
            System.out.println("Request: estateId=" + request.getEstateId() + ", agentId=" + request.getAgentId());
            
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth == null || !auth.isAuthenticated()) {
                System.out.println("Authentication failed");
                return ResponseEntity.status(401).body(Map.of("error", "Unauthorized"));
            }
            
            String email = auth.getName();
            System.out.println("Authenticated user email: " + email);
            
            User currentUser = userRepository.findByEmail(email).orElseThrow(() -> {
                System.out.println("User not found for email: " + email);
                return new RuntimeException("User not found");
            });
            
            System.out.println("Current user ID: " + currentUser.getId());
            
            if (request.getEstateId() == null || request.getAgentId() == null) {
                System.out.println("Missing estateId or agentId");
                return ResponseEntity.badRequest().body(Map.of("error", "Estate ID and Agent ID are required"));
            }
            
            Estate estate = estateRepository.findById(request.getEstateId()).orElse(null);
            if (estate == null) {
                System.out.println("Estate not found with ID: " + request.getEstateId());
                return ResponseEntity.notFound().build();
            }
            
            System.out.println("Estate found, owner ID: " + estate.getOwnerId());
            
            if (!estate.getOwnerId().equals(currentUser.getId())) {
                System.out.println("Access denied - user " + currentUser.getId() + " does not own estate " + estate.getId());
                return ResponseEntity.status(403).body(Map.of("error", "You can only assign agents to your own properties"));
            }
            
            User agent = userRepository.findById(request.getAgentId()).orElse(null);
            if (agent == null) {
                System.out.println("Agent not found with ID: " + request.getAgentId());
                return ResponseEntity.badRequest().body(Map.of("error", "Agent not found"));
            }
            
            System.out.println("Agent found: " + agent.getName());
            
            Optional<AgentHistory> existingAssignment = agentHistoryRepository
                .findByEstateAndUnassignedAtIsNull(estate);
            
            if (existingAssignment.isPresent()) {
                System.out.println("Found existing assignment, ending it");
                AgentHistory currentAssignment = existingAssignment.get();
                currentAssignment.setUnassignedAt(LocalDateTime.now());
                agentHistoryRepository.save(currentAssignment);
            }
            
            AgentHistory newAssignment = new AgentHistory();
            newAssignment.setEstate(estate);
            newAssignment.setAgent(agent);
            newAssignment.setAssignedAt(LocalDateTime.now());
            agentHistoryRepository.save(newAssignment);
            
            System.out.println("New assignment created successfully");
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Agent assigned successfully");
            response.put("estateId", estate.getId());
            response.put("agentId", agent.getId());
            response.put("agentName", agent.getName());
            response.put("assignedAt", newAssignment.getAssignedAt());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            System.out.println("Error in assignAgent: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", "Internal server error: " + e.getMessage()));
        }
    }

    public static class OptionDTO {
        private final Integer id;
        private final String name;

        public OptionDTO(Integer id, String name) {
            this.id = id; this.name = name;
        }
        public Integer getId() { return id; }
        public String getName() { return name; }
    }

    public static class AgentAssignmentRequest {
        private Integer estateId;
        private Integer agentId;
        
        public Integer getEstateId() { return estateId; }
        public void setEstateId(Integer estateId) { this.estateId = estateId; }
        
        public Integer getAgentId() { return agentId; }
        public void setAgentId(Integer agentId) { this.agentId = agentId; }
    }
}