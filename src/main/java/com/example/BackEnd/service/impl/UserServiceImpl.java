package com.example.BackEnd.service.impl;

import com.example.BackEnd.config.Constants;
import com.example.BackEnd.dto.DataMailDTO;
import com.example.BackEnd.dto.UpdateUserDTO;
import com.example.BackEnd.dto.UserDTO;
import com.example.BackEnd.dto.request.RegisterRequest;
import com.example.BackEnd.dto.response.ApiResponse;
import com.example.BackEnd.entity.User;
import com.example.BackEnd.entity.UserRole;
import com.example.BackEnd.exception.NotFoundException;
import com.example.BackEnd.service.MailService;
import com.example.BackEnd.service.mapper.impl.UserMapperImpl;
import com.example.BackEnd.repository.UserRepository;
import com.example.BackEnd.service.UserService;
import com.example.BackEnd.service.utils.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ApiResponse userResponse;
    @Autowired
    private UserMapperImpl userMapperImpl;
    @Autowired
    private MailService mailService;

    @Override
    public ApiResponse getUserByID(Integer userId) {
        try {
            Optional<User> userOptional = userRepository.findById(userId);
            User users = userRepository.getUserById(userOptional.get().getUserId());
            UserDTO userDTO;
            if (userOptional.isPresent()) {
                userDTO = userMapperImpl.entityToDto(users);
                userResponse = new ApiResponse(HttpServletResponse.SC_OK, Constants.SUCCESS, userDTO);
            } else {
                userResponse = new ApiResponse(HttpServletResponse.SC_OK, Constants.GET_FALSE, null);
            }
            return userResponse;
        } catch (Exception e) {
            throw new NotFoundException(Constants.GET_FALSE + userId);
        }
    }

    @Override
    public Boolean createUser(RegisterRequest request) {
        try {
            if (userRepository.existsByEmail(request.getEmail())) {
                throw new NotFoundException("Email already exists!");
            }
            User user = new User();
            user.setUsername(request.getUsername());
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String passwordNew = DataUtils.generatePwd(6);
            user.setPassword(passwordEncoder.encode(passwordNew));
            user.setEmail(request.getEmail());
            user.setUserFullName(request.getUserFullName());
            user.setUserBirthdate(request.getUserBirthdate());
            user.setUserAddress(request.getUserAddress());

            UserRole defaultUserRole = new UserRole();
            defaultUserRole.setUserRoleId(2);
            user.setUserRole(defaultUserRole);

            userRepository.save(user);

            DataMailDTO dataMailDTO = new DataMailDTO();
            dataMailDTO.setTo(request.getEmail());
            dataMailDTO.setSubject(Constants.SUBJECT_MAIL);
            Map<String, Object> props = new HashMap<>();
            props.put("userName", request.getUserFullName());
            props.put("email", request.getEmail());
            props.put("userBirthdate", request.getUserBirthdate());
            props.put("password", passwordNew);
            dataMailDTO.setProps(props);
            mailService.sendHtmlMail(dataMailDTO, "templates");
            return true;
        } catch (MessagingException exp) {
            exp.printStackTrace();
        } catch (NotFoundException e) {
            throw new NotFoundException(Constants.REGISTER_FALSE);
        }
        return false;
    }

    @Override
    public Boolean forgetPassword(RegisterRequest request) {
        try {
            Optional<User> userOptional = userRepository.findByEmail(request.getEmail());
            if (userOptional.isPresent()) {
                User existingUser = userOptional.get();
                String newPassword = DataUtils.generatePwd(6); // Tạo mật khẩu mới
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                existingUser.setPassword(passwordEncoder.encode(newPassword)); // Cập nhật mật khẩu mới
                userRepository.save(existingUser);

                DataMailDTO dataMailDTO = new DataMailDTO();
                dataMailDTO.setTo(request.getEmail());
                dataMailDTO.setSubject(Constants.SUBJECT_MAIL);
                Map<String, Object> props = new HashMap<>();
                props.put("userName", request.getUserFullName());
                props.put("email", request.getEmail());
                props.put("password", newPassword);
                dataMailDTO.setProps(props);
                mailService.sendHtmlMail(dataMailDTO, "forget");
                return true;
            } else {
                throw new NotFoundException(Constants.INVALID_EMAIL);
            }
        } catch (MessagingException exp) {
            exp.printStackTrace();
        } catch (NotFoundException e) {
            throw new NotFoundException(Constants.REGISTER_FALSE);
        }
        return false;
    }
//
//
    @Override
    public ApiResponse listUser(String userFullName, String ordUserFullName, int offset, int limit) {
        List<UserDTO> userDTOS = new ArrayList<>();
        long total = 0;
        int page = offset / limit;
        String escapeGameName = userFullName != null ? userFullName
                .replace("\\", "\\\\")
                .replace("%", "\\%")
                .replace("_", "\\_")
                .replace(";", "\\;") : null;
        Sort sort = Sort.by(
                "DESC".equalsIgnoreCase(ordUserFullName) ? Sort.Order.desc("userFullName") : Sort.Order.asc("userFullName"));
        Pageable pageable = PageRequest.of(page, limit, sort);
        Page<User> listUser = userRepository.listUser(userFullName, pageable);
        total = listUser.getTotalElements();
        userDTOS = userMapperImpl.pageToDtosList(listUser);
        userResponse = new ApiResponse(HttpServletResponse.SC_OK, Constants.GET_ALL_SUCCESS, total, userDTOS);
        return userResponse;
    }

    @Override
    public ApiResponse deleteUsers(Integer userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            userRepository.delete(user.get());
            userResponse = new ApiResponse(HttpServletResponse.SC_OK, Constants.DELETE_SUCCESS, userId);
        } else {
            userResponse = new ApiResponse(HttpServletResponse.SC_OK, Constants.DELETE_FALSE, userId);
        }
        return userResponse;
    }

    @Override
    @Transactional
    public ApiResponse updateUser(UpdateUserDTO userDTO, Integer userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User users = optionalUser.get();
            users.setUsername(userDTO.getUsername());
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String passwordNew = userDTO.getPassword();
            if(passwordNew.isEmpty() || passwordNew.equals("")) {
                users.setPassword(users.getPassword());
            } else {
                users.setPassword(passwordEncoder.encode(passwordNew));
            }
            users.setEmail(userDTO.getEmail());
            users.setUserFullName(userDTO.getUserFullName());
            users.setUserSex(userDTO.getUserSex());
            users.setUserBirthdate(userDTO.getUserBirthdate());
            users.setUserImage(userDTO.getUserImage());
            userRepository.save(users);
            userResponse = new ApiResponse(HttpServletResponse.SC_OK, Constants.UPDATE_SUCCESS, userDTO);
        } else {
            userResponse = new ApiResponse(HttpServletResponse.SC_OK, Constants.UPDATE_SUCCESS, null);
        }
        return userResponse;
    }
}