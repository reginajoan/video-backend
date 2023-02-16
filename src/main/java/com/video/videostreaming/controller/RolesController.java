//package com.video.videostreaming.controller;
//
//import com.video.videostreaming.dto.ResponseData;
//import com.video.videostreaming.dto.RolesDTO;
//import com.video.videostreaming.helper.ERole;
//import com.video.videostreaming.model.entity.Role;
//import com.video.videostreaming.service.RoleService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.HashSet;
//import java.util.Set;
//
//@RestController
//@RequestMapping("/roles")
//public class RolesController {
//    @Autowired
//    private RoleService roleService;
//
//    @GetMapping("/findAll")
//    public ResponseEntity<?> findAll(){
//        ResponseData response = new ResponseData();
//        try {
//            response.setStatus(true);
//            response.getMessage().add("success to get all data");
//            response.setPayload(roleService.findAllRole());
//            return ResponseEntity.status(HttpStatus.OK).body(response);
//        }catch (Exception e){
//            response.setStatus(false);
//            response.getMessage().add("fail to get all data with error : " + e.getMessage());
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
//        }
//    }
//    @PostMapping("/save")
//    public ResponseEntity<?> createRole(@RequestBody RolesDTO rolesDTO){
//        ResponseData response = new ResponseData();
//        try{
//            Set<Role> roles = rolesDTO.getRoles();
//            Set<ERole> eRoles = new HashSet<>();
//            Role newRole = new Role();
//            roles.forEach(role -> {
//                if (role.equals("USER")) {
//                    newRole.setRole(ERole.ROLE_USER);
//                } else if (role.equals("ADMIN")) {
//                    newRole.setRole(ERole.ROLE_ADMIN);
//                } else if (role.equals("MODERATOR")) {
//                    newRole.setRole(ERole.ROLE_MODERATOR);
//                }
//            });
//
//            response.setStatus(true);
//            response.getMessage().add("success create role");
//            response.setPayload(roleService.save(newRole));
//            return ResponseEntity.status(HttpStatus.CREATED).body(response);
//        }catch (Exception e){
//            if(!response.getMessage().isEmpty()){
//                response.getMessage().clear();
//            }
//            response.setStatus(false);
//            response.getMessage().add("fail to create new role with error : " + e.getMessage());
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
//        }
//    }
//
//
//}
