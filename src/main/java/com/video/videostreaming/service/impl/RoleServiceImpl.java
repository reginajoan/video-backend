//package com.video.videostreaming.service.impl;
//
//import com.video.videostreaming.exception.BadRequestException;
//import com.video.videostreaming.helper.ERole;
//import com.video.videostreaming.model.entity.Role;
//import com.video.videostreaming.model.repository.RoleRepo;
//import com.video.videostreaming.service.RoleService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import javax.transaction.Transactional;
//import java.util.List;
//import java.util.Optional;
//import java.util.Set;
//
//@Service
//public class RoleServiceImpl implements RoleService {
//
//    @Autowired
//    private RoleRepo roleRepo;
//    @Override
//    public Role save(Role role) {
//        return roleRepo.save(role);
//    }
//
//    @Override
//    public Role findById(Long id) {
//        return roleRepo.findById(id).orElseThrow(() -> new BadRequestException("role is not found"));
//    }
//
//    @Override
//    public List<Role> findAllRole() {
//        return roleRepo.findAll();
//    }
//
//    @Override
//    public void deleteById(Long id) {
//        roleRepo.deleteById(id);
//    }
//
//    @Override
//    public Role findByName(ERole eRole) {
//        return roleRepo.findByName(eRole);
//    }
////    @Override
////    public Object saveAll(Set<ERole> roles) {
////        Optional<Object> saveAll = roleRepo.saveAll(roles);
////        return saveAll;
////    }
//}