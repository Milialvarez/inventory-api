package com.inventoryapi.config;

import com.inventoryapi.entity.*;
import com.inventoryapi.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired private GroupRepository groupRepository;
    @Autowired private ArticleRepository articleRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private RoleRepository roleRepository;
    @Autowired private org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        if (articleRepository.count() > 0) return;

        System.out.println("---- INICIANDO CARGA DE DATOS DE PRUEBA ----");

        Role adminRole = new Role();
        adminRole.setName("ADMIN");

        Role userRole = new Role();
        userRole.setName("USER");

        roleRepository.saveAll(Set.of(adminRole, userRole));

        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setRoles(Set.of(adminRole));

        User empleado = new User();
        empleado.setUsername("pepe");
        empleado.setPassword(passwordEncoder.encode("pepe123"));
        empleado.setRoles(Set.of(userRole));

        userRepository.saveAll(Set.of(admin, empleado));

        Group electronics = new Group();
        electronics.setName("Electronics");

        Group furniture = new Group();
        furniture.setName("Furniture");

        groupRepository.saveAll(Set.of(electronics, furniture));

        Article tv = new Article();
        tv.setName("Samsung Smart TV 55");
        tv.setUnitPrice(600.00);
        tv.setStock(10);
        tv.setGroup(electronics);

        Article laptop = new Article();
        laptop.setName("Dell XPS 13");
        laptop.setUnitPrice(1200.00);
        laptop.setStock(5);
        laptop.setGroup(electronics);

        Article chair = new Article();
        chair.setName("Office Chair");
        chair.setUnitPrice(150.00);
        chair.setStock(20);
        chair.setGroup(furniture);

        articleRepository.saveAll(Set.of(tv, laptop, chair));

        System.out.println("---- CARGA DE DATOS COMPLETADA ----");
    }
}