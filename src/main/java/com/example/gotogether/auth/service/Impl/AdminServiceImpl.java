package com.example.gotogether.auth.service.Impl;

import com.example.gotogether.auth.dto.GroupDTO;
import com.example.gotogether.auth.dto.UserDTO;
import com.example.gotogether.auth.entity.Grouping;
import com.example.gotogether.auth.entity.User;
import com.example.gotogether.auth.repository.GroupingRepository;
import com.example.gotogether.auth.repository.UserRepository;
import com.example.gotogether.auth.service.AdminService;
import com.example.gotogether.global.response.PageResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static com.example.gotogether.global.config.PageSizeConfig.User_List_Size;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final UserRepository userRepository;
    private final GroupingRepository groupingRepository;

    @Override
    @Transactional
    public ResponseEntity<?> setUserToAdmin(String email) {
        try {
            User user = userRepository.findByEmail(email).orElseThrow(IllegalArgumentException::new);
            user.setRole("ROLE_ADMIN");
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> setAdminToUser(String email) {
        try {
            User user = userRepository.findByEmail(email).orElseThrow(IllegalArgumentException::new);
            user.setRole("ROLE_USER");
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<?> findUserList(int page) {
        try {
            PageRequest pageable = PageRequest.of(page - 1, User_List_Size);
            Page<UserDTO.UserListDto> userList = userRepository.findAll(pageable)
                    .map(UserDTO.UserListDto::new);
            if (userList.getTotalElements() < 1) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(new PageResponseDTO(userList), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> findUser(Long id) {
        try {
            User user = userRepository.findById(id).orElseThrow(IllegalArgumentException::new);
            return new ResponseEntity<>(new UserDTO.UserDetailsForAdmin(user), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateUserInfo(Long userId, UserDTO.PatchUserByAdminReqDTO dto) {
        try {
            if (dto.getDeleteCheck() != null && !dto.getDeleteCheck().equals("withdraw") && !dto.getDeleteCheck().equals("available")) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
            if (dto.getUserName() != null) {
                user.setName(dto.getUserName());
            }
            if (dto.getUserPhoneNumber() != null) {
                user.setPhoneNumber(dto.getUserPhoneNumber());
            }
            if (dto.getUserGender() != null) {
                user.setGender(dto.getUserGender());
            }
            if (dto.getUserBirthday() != null) {
                user.setBirthday(dto.getUserBirthday());
            }
            if (dto.getPassportLastName() != null) {
                user.setPassportLastName(dto.getPassportLastName());
            }
            if (dto.getPassportFirstName() != null) {
                user.setPassportFirstName(dto.getPassportFirstName());
            }
            if (dto.getDeleteCheck() != null) {
                user.setDeleteCheck(dto.getDeleteCheck());
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> makeGroup(GroupDTO groupDTO) {
        if (groupingRepository.existsById(groupDTO.getUserType())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        groupingRepository.save(new Grouping(groupDTO.getUserType(), groupDTO.getGroup()));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> findAllGrouping() {
        List<GroupDTO> list = groupingRepository.findAll().stream().map(GroupDTO::new).collect(Collectors.toList());
        if (list.size() < 1) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getGroupingByType(String type) {
        try {
            return new ResponseEntity<>(groupingRepository.findById(type).orElseThrow(NoSuchElementException::new), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<?> getGroupingByGroup(String group) {
        List<GroupDTO> list = groupingRepository.findAllByGroup(group).stream().map(GroupDTO::new).collect(Collectors.toList());
        if (list.size() < 1) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateGrouping(GroupDTO dto) {
        try {
            Grouping grouping = groupingRepository.findById(dto.getUserType()).orElseThrow(NoSuchElementException::new);
            grouping.setGroup(dto.getGroup());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> deleteGrouping(String type) {
        try {
            Grouping grouping = groupingRepository.findById(type).orElseThrow(NoSuchElementException::new);
            groupingRepository.delete(grouping);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
